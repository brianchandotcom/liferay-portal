/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.site.generator.internal.object.action.executor;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.object.action.executor.BaseObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Standalone Object Action that commits a Content Generator Run by submitting
 * each of its artifacts to the Headless Batch Engine, one at a time, in the
 * order defined by the artifact's loadOrder field. Each artifact carries a
 * full {configuration, items} envelope and is dispatched to whatever batch
 * delegate the envelope's className / taskItemDelegateName resolves to.
 *
 * Invoked by the auto-generated endpoint:
 *   PUT /o/content-site-generator/runs/by-external-reference-code/{erc}/object-actions/commit
 *   PUT /o/content-site-generator/runs/{id}/object-actions/commit
 *
 * @author Gabriel Albuquerque
 */
@Component(
	property = "object.action.executor.key=" + CommitContentSiteGeneratorRunObjectActionExecutor.KEY,
	service = ObjectActionExecutor.class
)
public class CommitContentSiteGeneratorRunObjectActionExecutor
	extends BaseObjectActionExecutor {

	public static final String EXECUTOR_NAME = "CSG-CommitMonitor";

	public static final String KEY = "commit-content-site-generator-run";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	protected void doExecute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		// Standalone Object Actions execute as a transaction commit callback,
		// so by this point the originating request transaction is already
		// closed. Defer all DB work to a background thread where each local
		// service call manages its own transaction.

		long runId = payloadJSONObject.getLong("classPK");

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(EXECUTOR_NAME);

		executorService.submit(() -> _runCommit(companyId, userId, runId));
	}

	private boolean _awaitTerminal(String fileName, long taskId)
		throws InterruptedException {

		long deadline = System.currentTimeMillis() + _TIMEOUT_MS;

		while (System.currentTimeMillis() < deadline) {
			Thread.sleep(_POLL_INTERVAL_MS);

			BatchEngineImportTask task =
				_batchEngineImportTaskLocalService.fetchBatchEngineImportTask(
					taskId);

			if (task == null) {
				continue;
			}

			String status = task.getExecuteStatus();

			if (Objects.equals(
					BatchEngineTaskExecuteStatus.FAILED.name(), status)) {

				return false;
			}

			if (Objects.equals(
					BatchEngineTaskExecuteStatus.COMPLETED.name(), status)) {

				return true;
			}
		}

		_log.error(
			StringBundler.concat(
				"Artifact ", fileName, " timed out after ", _TIMEOUT_MS, "ms"));

		return false;
	}

	private void _finalizeRun(
		long userId, long runId, boolean failed, String failureReason) {

		try {
			Map<String, Serializable> updates = new HashMap<>(
				_objectEntryLocalService.getValues(runId));

			if (failed) {
				updates.put("runStatus", _RUN_STATUS_FAILED);

				if (failureReason != null) {
					updates.put("failureReason", failureReason);
				}
			}
			else {
				updates.put("committedAt", new Date());
				updates.put("failureReason", "");
				updates.put("runStatus", _RUN_STATUS_COMMITTED);
			}

			_objectEntryLocalService.updateObjectEntry(
				userId, runId, 0L, updates, new ServiceContext());
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to finalize run ", runId, " (failed=", failed, ")"),
				exception);
		}
	}

	private String _readFileEntry(long fileEntryId) throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		try (InputStream inputStream = fileEntry.getContentStream()) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to read file entry " + fileEntryId, ioException);
		}
	}

	private void _runCommit(long companyId, long userId, long runId) {
		try {
			ObjectEntry runObjectEntry =
				_objectEntryLocalService.getObjectEntry(runId);

			String runERC = runObjectEntry.getExternalReferenceCode();

			Map<String, Serializable> runValues =
				_objectEntryLocalService.getValues(runId);

			String currentStatus = GetterUtil.getString(
				runValues.get("runStatus"));

			if (!_committableStates.contains(currentStatus)) {
				throw new PortalException(
					StringBundler.concat(
						"Run ", runERC, " is not in a committable state: ",
						currentStatus));
			}

			ObjectDefinition artifactObjectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						_ARTIFACT_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODE,
						companyId);

			if (artifactObjectDefinition == null) {
				throw new PortalException(
					"ContentGeneratorArtifact ObjectDefinition is not " +
						"registered");
			}

			List<ObjectEntry> allArtifacts =
				_objectEntryLocalService.getObjectEntries(
					0, artifactObjectDefinition.getObjectDefinitionId(), -1,
					-1);

			Map<Long, Map<String, Serializable>> artifactValues =
				new HashMap<>();

			List<ObjectEntry> artifacts = new ArrayList<>();

			for (ObjectEntry artifact : allArtifacts) {
				Map<String, Serializable> values =
					_objectEntryLocalService.getValues(
						artifact.getObjectEntryId());

				if (GetterUtil.getLong(values.get(_ARTIFACT_RUN_FK_FIELD)) ==
						runId) {

					artifactValues.put(artifact.getObjectEntryId(), values);
					artifacts.add(artifact);
				}
			}

			artifacts.sort(
				(a, b) -> Integer.compare(
					GetterUtil.getInteger(
						artifactValues.get(
							a.getObjectEntryId()
						).get(
							"loadOrder"
						)),
					GetterUtil.getInteger(
						artifactValues.get(
							b.getObjectEntryId()
						).get(
							"loadOrder"
						))));

			if (artifacts.isEmpty()) {
				throw new PortalException(
					"Run " + runERC + " has no artifacts to commit");
			}

			Map<String, Serializable> updates = new HashMap<>(runValues);

			updates.put("committedAt", null);
			updates.put("runStatus", _RUN_STATUS_GENERATING);

			_objectEntryLocalService.updateObjectEntry(
				userId, runId, 0L, updates, new ServiceContext());

			for (ObjectEntry artifact : artifacts) {
				Map<String, Serializable> values = artifactValues.get(
					artifact.getObjectEntryId());

				String fileName = GetterUtil.getString(values.get("fileName"));
				long fileEntryId = GetterUtil.getLong(values.get("json"));

				if (fileEntryId <= 0) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Skipping artifact " + fileName +
								" due to missing envelope file entry");
					}

					continue;
				}

				String envelopeJSON = _readFileEntry(fileEntryId);

				if (Validator.isBlank(envelopeJSON)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Skipping artifact ", fileName,
								" due to empty envelope file entry ",
								fileEntryId));
					}

					continue;
				}

				BatchEngineImportTask batchEngineImportTask = _submitEnvelope(
					companyId, userId, fileName, envelopeJSON);

				if (batchEngineImportTask == null) {
					continue;
				}

				boolean succeeded = _awaitTerminal(
					fileName,
					batchEngineImportTask.getBatchEngineImportTaskId());

				if (!succeeded) {
					_finalizeRun(
						userId, runId, true,
						StringBundler.concat(
							"Artifact ", fileName,
							" failed; see batch engine task errors"));

					return;
				}
			}

			_finalizeRun(userId, runId, false, null);
		}
		catch (Exception exception1) {
			_log.error("Commit failed for run " + runId, exception1);

			try {
				_finalizeRun(
					userId, runId, true,
					"Commit failed: " + exception1.getMessage());
			}
			catch (Exception exception2) {
				_log.error("Unable to mark run failed: " + runId, exception2);
			}
		}
	}

	private BatchEngineImportTask _submitEnvelope(
			long companyId, long userId, String fileName, String envelopeJSON)
		throws Exception {

		JSONObject envelopeJSONObject = _jsonFactory.createJSONObject(
			envelopeJSON);

		JSONObject configurationJSONObject = envelopeJSONObject.getJSONObject(
			"configuration");

		if (configurationJSONObject == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Skipping artifact " + fileName +
						" because envelope has no configuration");
			}

			return null;
		}

		String className = configurationJSONObject.getString("className");

		if (Validator.isNull(className)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Skipping artifact " + fileName +
						" because configuration has no className");
			}

			return null;
		}

		JSONArray itemsJSONArray = envelopeJSONObject.getJSONArray("items");

		if ((itemsJSONArray == null) || (itemsJSONArray.length() == 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Skipping artifact " + fileName +
						" because items is empty");
			}

			return null;
		}

		String taskItemDelegateName = GetterUtil.getString(
			configurationJSONObject.getString("taskItemDelegateName"),
			"DEFAULT");

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateRegistry.getBatchEngineTaskItemDelegate(
				companyId, className, taskItemDelegateName);

		if (batchEngineTaskItemDelegate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Skipping artifact ", fileName, " because no delegate ",
						"registered for ", className, " / ",
						taskItemDelegateName));
			}

			return null;
		}

		Map<String, Serializable> parameters = _toSerializableMap(
			configurationJSONObject.getJSONObject("parameters"));

		Map<String, String> fieldNameMappingMap = _toStringMap(
			configurationJSONObject.getJSONObject("fieldNameMappingMap"));

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, companyId, userId, 100, null, className,
				_zipJSON(fileName, itemsJSONArray.toString()), "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				fieldNameMappingMap,
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				"CREATE", parameters, taskItemDelegateName);

		_batchEngineImportTaskExecutor.execute(
			batchEngineImportTask, batchEngineTaskItemDelegate, true);

		return batchEngineImportTask;
	}

	private Map<String, Serializable> _toSerializableMap(
		JSONObject jsonObject) {

		if (jsonObject == null) {
			return null;
		}

		Map<String, Serializable> map = new HashMap<>();

		for (String key : jsonObject.keySet()) {
			Object value = jsonObject.get(key);

			if (value instanceof Serializable) {
				map.put(key, (Serializable)value);
			}
			else if (value != null) {
				map.put(key, value.toString());
			}
		}

		return map;
	}

	private Map<String, String> _toStringMap(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		for (String key : jsonObject.keySet()) {
			map.put(key, jsonObject.getString(key));
		}

		return map;
	}

	private byte[] _zipJSON(String fileName, String json) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				byteArrayOutputStream)) {

			zipOutputStream.putNextEntry(new ZipEntry(fileName));
			zipOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
			zipOutputStream.closeEntry();
		}

		return byteArrayOutputStream.toByteArray();
	}

	private static final String
		_ARTIFACT_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODE = "L_CSG_ARTIFACT";

	private static final String _ARTIFACT_RUN_FK_FIELD =
		"r_artifacts_l_contentGeneratorRunId";

	private static final long _POLL_INTERVAL_MS = 2_000L;

	private static final String _RUN_STATUS_COMMITTED = "committed";

	private static final String _RUN_STATUS_FAILED = "failed";

	private static final String _RUN_STATUS_GENERATING = "generating";

	private static final long _TIMEOUT_MS = 10L * 60L * 1_000L;

	private static final Log _log = LogFactoryUtil.getLog(
		CommitContentSiteGeneratorRunObjectActionExecutor.class);

	private static final List<String> _committableStates = List.of(
		"failed", "ready");

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}