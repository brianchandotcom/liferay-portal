/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.testray.internal.dispatch.executor;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray",
		"dispatch.task.executor.type=testray"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Invoking doExecute");
		}

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		_invoke(() -> _loadCache(dispatchTrigger.getCompanyId()));
		_invoke(() -> _uploadToTestray(unicodeProperties));
	}

	@Override
	public String getName() {
		return "testray";
	}

	private void _invoke(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		long startTime = System.currentTimeMillis();

		unsafeRunnable.run();

		if (_log.isInfoEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.info(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(),
					" took ", System.currentTimeMillis() - startTime, " ms"));
		}
	}

	private void _loadCache(long companyId) throws Exception {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitionIds.put(
				objectDefinition.getShortName(),
				objectDefinition.getObjectDefinitionId());
		}

		_loadObjectEntryIds(
			"CaseType", "c_caseTypeId", "CaseType#[$name$]",
			Arrays.asList("name"));
		_loadObjectEntryIds(
			"Component", "c_componentId",
			"Component#[$name$]#testrayTeamId#[$r_teamToComponents_c_teamId$]",
			Arrays.asList("name", "r_teamToComponents_c_teamId"));
		_loadObjectEntryIds(
			"FactorCategory", "c_factorCategoryId", "FactorCategory#[$name$]",
			Arrays.asList("name"));
		_loadObjectEntryIds(
			"FactorOption", "c_factorOptionId",
			"FactorOption#[$name$]#testrayFactorCategoryId#" +
				"[$r_factorCategoryToOptions_c_factorCategoryId$]",
			Arrays.asList(
				"name", "r_factorCategoryToOptions_c_factorCategoryId"));
		_loadObjectEntryIds(
			"Project", "c_projectId", "Project#[$name$]",
			Arrays.asList("name"));
		_loadObjectEntryIds(
			"Team", "c_teamId",
			"Team#[$name$]#testrayProjectId#[$r_projectToTeams_c_projectId$]",
			Arrays.asList("name", "r_projectToTeams_c_projectId"));
	}

	private void _loadObjectEntryIds(
			String objectShortName, String pkObjectFieldDBColumnName,
			String key, List<String> placeholders)
		throws Exception {

		List<Map<String, Serializable>> values =
			_objectEntryLocalService.getValuesList(
				_objectDefinitionIds.get(objectShortName), null, 0, 0);

		if (ListUtil.isEmpty(values)) {
			return;
		}

		Map<String, String> placeholderMap = new HashMap<>();

		for (Map<String, Serializable> map : values) {
			Long id = (Long)map.get(pkObjectFieldDBColumnName);

			for (String placeholder : placeholders) {
				placeholderMap.put(
					placeholder, String.valueOf(map.get(placeholder)));
			}

			key = StringUtil.replace(key, "[$", "$]", placeholderMap);

			_objectEntryIds.put(key, id.longValue());
		}
	}

	private void _uploadToTestray(UnicodeProperties unicodeProperties)
		throws Exception {

		// TODO validate properties

		String s3APIKey = unicodeProperties.getProperty("s3APIKey");

		try (InputStream inputStream = new ByteArrayInputStream(
				s3APIKey.getBytes())) {

			Storage storage = StorageOptions.newBuilder(
			).setCredentials(
				GoogleCredentials.fromStream(inputStream)
			).build(
			).getService();

			String s3InboxFolderName = unicodeProperties.getProperty(
				"s3InboxFolderName");

			Page<Blob> page = storage.list(
				unicodeProperties.getProperty("s3BucketName"),
				Storage.BlobListOption.prefix(s3InboxFolderName + "/"));

			for (Blob blob : page.iterateAll()) {
				String name = blob.getName();

				if (name.equals(s3InboxFolderName + "/")) {
					continue;
				}

				try {
					if (_log.isInfoEnabled()) {
						_log.info("Processing archive " + name);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayDispatchTaskExecutor.class);

	private final Map<String, Long> _objectDefinitionIds = new HashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}