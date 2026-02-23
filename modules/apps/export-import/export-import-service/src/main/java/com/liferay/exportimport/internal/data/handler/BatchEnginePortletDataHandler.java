/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.exportimport.internal.lar.ExportImportDescriptorThreadLocal;
import com.liferay.exportimport.internal.lar.PortletDataContextImpl;
import com.liferay.exportimport.internal.lar.PortletDataContextThreadLocal;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.staging.StagingGroupHelper;

import jakarta.portlet.PortletPreferences;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Vendel Toreki
 * @author Alejandro Tardín
 */
public class BatchEnginePortletDataHandler extends BasePortletDataHandler {

	public static final String BATCH_DELETE_CLASS_NAME_POSTFIX =
		"_batchDeleteExternalReferenceCodes";

	public static final String SCHEMA_VERSION = "4.0.0";

	public BatchEnginePortletDataHandler(
		BatchEngineExportTaskExecutor batchEngineExportTaskExecutor,
		BatchEngineExportTaskLocalService batchEngineExportTaskLocalService,
		BatchEngineImportTaskExecutor batchEngineImportTaskExecutor,
		BatchEngineImportTaskService batchEngineImportTaskService,
		ChangesetEntryLocalService changesetEntryLocalService,
		ClassNameLocalService classNameLocalService,
		ExportImportHelper exportImportHelper,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		StagingGroupHelper stagingGroupHelper) {

		_batchEngineExportTaskExecutor = batchEngineExportTaskExecutor;
		_batchEngineExportTaskLocalService = batchEngineExportTaskLocalService;
		_batchEngineImportTaskExecutor = batchEngineImportTaskExecutor;
		_batchEngineImportTaskService = batchEngineImportTaskService;
		_changesetEntryLocalService = changesetEntryLocalService;
		_classNameLocalService = classNameLocalService;
		_exportImportHelper = exportImportHelper;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_stagingGroupHelper = stagingGroupHelper;
	}

	public void exportDeletionSystemEvents(
			PortletDataContext portletDataContext)
		throws Exception {

		for (Registration registration :
				_getActiveRegistrations(portletDataContext)) {

			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					registration.getExportImportDescriptor();

			Map<String, String> newPrimaryKeysMap =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					exportImportDescriptor.getModelClassName() +
						BATCH_DELETE_CLASS_NAME_POSTFIX);

			portletDataContext.addZipEntry(
				_normalize(
					registration.getDeletionsFileName(),
					portletDataContext.getScopeGroupId()),
				JSONUtil.toJSONArray(
					ListUtil.filter(
						ListUtil.fromCollection(newPrimaryKeysMap.keySet()),
						exportImportDescriptor::
							isApplicableExternalReferenceCode),
					externalReferenceCode -> JSONUtil.put(
						"externalReferenceCode", externalReferenceCode)
				).toString());
		}
	}

	@Override
	public String[] getClassNames() {
		return TransformUtil.transformToArray(
			TransformUtil.transform(
				_registrations, Registration::getExportImportDescriptor),
			ExportImportVulcanBatchEngineTaskItemDelegate.
				ExportImportDescriptor::getModelClassName,
			String.class);
	}

	@Override
	public String getDescription(Locale locale) {
		return _getSoleProperty(
			exportImportDescriptor -> exportImportDescriptor.getDescription(
				locale));
	}

	@Override
	public String getName() {
		return getPortletId();
	}

	@Override
	public int getRank() {
		if (_registrations.isEmpty()) {
			return super.getRank();
		}

		int rank = Integer.MAX_VALUE;

		for (Registration registration : _registrations) {
			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					registration.getExportImportDescriptor();

			if (exportImportDescriptor.getRank() < rank) {
				rank = exportImportDescriptor.getRank();
			}
		}

		return rank;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getTag(Locale locale) {
		return _getSoleProperty(
			exportImportDescriptor -> exportImportDescriptor.getTag(locale));
	}

	@Override
	public boolean isBatch() {
		return true;
	}

	@Override
	public boolean isConfigurationEnabled() {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-41367")) {
			return false;
		}

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			null, false);

		portletDataContext.setCompanyId(companyId);

		List<Registration> activeRegistrations = _getActiveRegistrations(
			portletDataContext, true);

		return !activeRegistrations.isEmpty();
	}

	@Override
	public boolean isHidden() {
		return Boolean.TRUE.equals(
			_getSoleProperty(
				ExportImportVulcanBatchEngineTaskItemDelegate.
					ExportImportDescriptor::isHidden));
	}

	@Override
	public boolean isStaged() {
		return !StringUtil.startsWith(
			getPortletId(), ObjectPortletKeys.OBJECT_DEFINITIONS);
	}

	public void registerExportImportVulcanBatchEngineTaskItemDelegate(
		String batchEngineClassName,
		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor,
		String taskItemDelegateName) {

		String fileNamePrefix = GetterUtil.getString(
			taskItemDelegateName, batchEngineClassName);

		_registrations.add(
			new Registration() {

				@Override
				public String getBatchEngineClassName() {
					return batchEngineClassName;
				}

				@Override
				public String getDeletionsFileName() {
					return fileNamePrefix + "_deletions.json";
				}

				@Override
				public ExportImportVulcanBatchEngineTaskItemDelegate.
					ExportImportDescriptor getExportImportDescriptor() {

					return exportImportDescriptor;
				}

				@Override
				public String getFileName() {
					return fileNamePrefix + ".json";
				}

				@Override
				public String getTaskItemDelegateName() {
					return taskItemDelegateName;
				}

			});

		if (ExportImportVulcanBatchEngineTaskItemDelegate.Scope.COMPANY.equals(
				exportImportDescriptor.getScope())) {

			setDataLevel(DataLevel.PORTAL);
		}
		else if (ExportImportVulcanBatchEngineTaskItemDelegate.Scope.DEPOT.
					equals(exportImportDescriptor.getScope())) {

			setDataLevel(DataLevel.DEPOT);
		}
		else if (ExportImportVulcanBatchEngineTaskItemDelegate.Scope.SITE.
					equals(exportImportDescriptor.getScope())) {

			setDataLevel(DataLevel.SITE);
		}

		setPublishToLiveByDefault(true);
		_updateDeletionSystemEventStagedModelTypes();
		_updatePortletDataHandlerControls();
	}

	public void unregisterExportImportVulcanBatchEngineTaskItemDelegate(
		String batchEngineClassName, String taskItemDelegateName) {

		Iterator<Registration> iterator = _registrations.iterator();

		while (iterator.hasNext()) {
			Registration registration = iterator.next();

			if (Objects.equals(
					registration.getBatchEngineClassName(),
					batchEngineClassName) &&
				Objects.equals(
					registration.getTaskItemDelegateName(),
					taskItemDelegateName)) {

				iterator.remove();

				_updateDeletionSystemEventStagedModelTypes();
				_updatePortletDataHandlerControls();

				return;
			}
		}
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.getZipReader() == null) {
			return portletPreferences;
		}

		for (Registration registration :
				_getActiveRegistrations(portletDataContext)) {

			InputStream inputStream =
				portletDataContext.getZipEntryAsInputStream(
					_normalize(
						registration.getDeletionsFileName(),
						portletDataContext.getSourceGroupId()));

			if (inputStream == null) {
				return portletPreferences;
			}

			BatchEngineImportTask batchEngineDeleteTask =
				_batchEngineImportTaskService.addBatchEngineImportTask(
					null, portletDataContext.getCompanyId(), _getUserId(), 100,
					null, registration.getBatchEngineClassName(),
					_getBytes(
						_normalize(
							registration.getFileName(),
							portletDataContext.getSourceGroupId()),
						inputStream),
					"JSON", BatchEngineTaskExecuteStatus.INITIAL.name(),
					Collections.emptyMap(),
					BatchEngineImportTaskConstants.
						IMPORT_STRATEGY_ON_ERROR_CONTINUE,
					BatchEngineTaskOperation.DELETE.name(),
					BatchEnginePortletDataHandlerUtil.buildDeleteParameters(
						registration.getExportImportDescriptor(),
						_groupLocalService, portletDataContext,
						_stagingGroupHelper),
					registration.getTaskItemDelegateName());

			_batchEngineImportTaskExecutor.execute(batchEngineDeleteTask);
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try (SafeCloseable safeCloseable1 =
				PortletDataContextThreadLocal.
					setPortletDataContextWithSafeCloseable(
						portletDataContext)) {

			List<Registration> activeRegistrations = _getActiveRegistrations(
				portletDataContext);

			for (Registration registration : activeRegistrations) {
				ExportImportVulcanBatchEngineTaskItemDelegate.
					ExportImportDescriptor exportImportDescriptor =
						registration.getExportImportDescriptor();

				if ((activeRegistrations.size() > 1) &&
					!portletDataContext.getBooleanParameter(
						getPortletId(),
						exportImportDescriptor.getResourceClassName())) {

					continue;
				}

				try (SafeCloseable safeCloseable2 =
						ExportImportDescriptorThreadLocal.
							setExportImportDescriptorWithSafeCloseable(
								exportImportDescriptor)) {

					BatchEngineExportTaskExecutor.Result result =
						_executeExportTask(
							Integer.MAX_VALUE, portletDataContext,
							registration);

					if (result == null) {
						continue;
					}

					BatchEngineExportTask batchEngineExportTask =
						result.getBatchEngineExportTask();

					if (batchEngineExportTask.getTotalItemsCount() == 0) {
						continue;
					}

					portletDataContext.addZipEntry(
						_normalize(
							registration.getFileName(),
							portletDataContext.getScopeGroupId()),
						result.getInputStream());

					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					manifestSummary.addModelAdditionCount(
						new StagedModelType(
							exportImportDescriptor.getResourceClassName()),
						batchEngineExportTask.getProcessedItemsCount());
				}
			}

			if (_stagingGroupHelper.isCompanyGroup(
					portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId())) {

				portletDataContext.setValidateExistingDataHandler(false);
			}
			else {
				portletDataContext.setValidateExistingDataHandler(true);
			}

			return getExportDataRootElementString(
				addExportDataRootElement(portletDataContext));
		}
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		List<Registration> activeRegistrations = _getActiveRegistrations(
			portletDataContext);

		for (Registration registration : activeRegistrations) {
			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					registration.getExportImportDescriptor();

			if ((activeRegistrations.size() > 1) &&
				!portletDataContext.getBooleanParameter(
					getPortletId(),
					exportImportDescriptor.getResourceClassName())) {

				continue;
			}

			String normalizedFileName = _normalize(
				registration.getFileName(),
				portletDataContext.getSourceGroupId());

			InputStream inputStream =
				portletDataContext.getZipEntryAsInputStream(normalizedFileName);

			if (inputStream == null) {
				continue;
			}

			BatchEngineImportTask batchEngineImportTask =
				_batchEngineImportTaskService.addBatchEngineImportTask(
					null, portletDataContext.getCompanyId(), _getUserId(), 100,
					null, registration.getBatchEngineClassName(),
					_getBytes(normalizedFileName, inputStream), "JSON",
					BatchEngineTaskExecuteStatus.INITIAL.name(),
					Collections.emptyMap(),
					BatchEngineImportTaskConstants.
						IMPORT_STRATEGY_ON_ERROR_CONTINUE,
					BatchEngineTaskOperation.CREATE.name(),
					BatchEnginePortletDataHandlerUtil.buildImportParameters(
						registration.getExportImportDescriptor(),
						_groupLocalService, portletDataContext,
						_stagingGroupHelper),
					registration.getTaskItemDelegateName());

			try (SafeCloseable safeCloseable1 =
					ExportImportDescriptorThreadLocal.
						setExportImportDescriptorWithSafeCloseable(
							exportImportDescriptor);
				SafeCloseable safeCloseable2 =
					PortletDataContextThreadLocal.
						setPortletDataContextWithSafeCloseable(
							portletDataContext)) {

				_batchEngineImportTaskExecutor.execute(batchEngineImportTask);
			}

			batchEngineImportTask =
				_batchEngineImportTaskService.getBatchEngineImportTask(
					batchEngineImportTask.getBatchEngineImportTaskId());

			BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
				BatchEngineTaskExecuteStatus.valueOf(
					batchEngineImportTask.getExecuteStatus());

			if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.FAILED) {

				throw new PortletDataException(
					"Unable to import batch data: " +
						batchEngineImportTask.getErrorMessage());
			}

			if (Objects.equals(
					Layout.class.getName(),
					exportImportDescriptor.getModelClassName()) &&
				!portletDataContext.isPrivateLayout() &&
				MapUtil.getBoolean(
					portletDataContext.getParameterMap(),
					PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS, false)) {

				try {
					_deleteMissingLayouts(portletDataContext, registration);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to delete missing layouts after batch " +
								"import",
							exception);
					}
				}
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
		PortletDataContext portletDataContext,
		PortletPreferences portletPreferences) {

		for (Registration registration :
				_getActiveRegistrations(portletDataContext)) {

			try (SafeCloseable safeCloseable =
					PortletDataContextThreadLocal.
						setPortletDataContextWithSafeCloseable(
							portletDataContext)) {

				BatchEngineExportTaskExecutor.Result result =
					_executeExportTask(1, portletDataContext, registration);

				if (result == null) {
					continue;
				}

				ManifestSummary manifestSummary =
					portletDataContext.getManifestSummary();

				ExportImportVulcanBatchEngineTaskItemDelegate.
					ExportImportDescriptor exportImportDescriptor =
						registration.getExportImportDescriptor();
				BatchEngineExportTask batchEngineExportTask =
					result.getBatchEngineExportTask();

				manifestSummary.addModelAdditionCount(
					new StagedModelType(
						exportImportDescriptor.getResourceClassName()),
					batchEngineExportTask.getTotalItemsCount());
			}
		}
	}

	@Override
	protected long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls) {

		if (_registrations.size() == 1) {
			return super.getExportModelCount(
				manifestSummary,
				new PortletDataHandlerControl[] {
					_getPortletDataHandlerControl(_registrations.get(0))
				});
		}

		return super.getExportModelCount(
			manifestSummary, portletDataHandlerControls);
	}

	protected static final TransactionConfig transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private void _deleteMissingLayouts(
			PortletDataContext portletDataContext, Registration registration)
		throws Exception {

		Set<String> exportedLayoutERCs = _getExportedLayoutERCs(
			portletDataContext, registration);

		if (exportedLayoutERCs.isEmpty()) {
			return;
		}

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		for (Layout layout :
				_layoutLocalService.getLayouts(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout())) {

			String layoutERC = layout.getExternalReferenceCode();

			if ((layoutERC == null) || layoutERC.isEmpty() ||
				exportedLayoutERCs.contains(layoutERC) ||
				layoutPlids.containsValue(layout.getPlid())) {

				continue;
			}

			layout = _layoutLocalService.fetchLayout(layout.getPlid());

			if (layout == null) {
				continue;
			}

			try {
				long sourceGroupId = portletDataContext.getSourceGroupId();
				long targetGroupId = portletDataContext.getGroupId();

				if (sourceGroupId != targetGroupId) {
					Layout stagedLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							layout.getUuid(), sourceGroupId,
							!layout.isPublicLayout());

					if (stagedLayout != null) {
						continue;
					}
				}

				_layoutLocalService.deleteLayout(layout, serviceContext);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to delete layout with external reference " +
							"code " + layoutERC,
						exception);
				}
			}
		}
	}

	private BatchEngineExportTaskExecutor.Result _executeExportTask(
		int maxItems, PortletDataContext portletDataContext,
		Registration registration) {

		return _batchEngineExportTaskExecutor.execute(
			_batchEngineExportTaskLocalService.createBatchEngineExportTask(
				0L, null, portletDataContext.getCompanyId(), _getUserId(), null,
				registration.getBatchEngineClassName(), "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				Collections.emptyList(),
				BatchEnginePortletDataHandlerUtil.buildExportParameters(
					_changesetEntryLocalService, _classNameLocalService,
					registration.getExportImportDescriptor(),
					_groupLocalService, portletDataContext,
					_stagingGroupHelper),
				registration.getTaskItemDelegateName()),
			new BatchEngineExportTaskExecutor.Settings() {

				@Override
				public int getMaxItems() {
					return maxItems;
				}

				@Override
				public boolean isCompressContent() {
					return false;
				}

				@Override
				public boolean isPersist() {
					return false;
				}

			});
	}

	private List<Registration> _getActiveRegistrations(
		PortletDataContext portletDataContext) {

		return _getActiveRegistrations(
			portletDataContext, ExportImportThreadLocal.isStagingInProcess());
	}

	private List<Registration> _getActiveRegistrations(
		PortletDataContext portletDataContext, boolean stagingSupportedOnly) {

		return ListUtil.filter(
			_registrations,
			registration -> {
				ExportImportVulcanBatchEngineTaskItemDelegate.
					ExportImportDescriptor exportImportDescriptor =
						registration.getExportImportDescriptor();

				if (!exportImportDescriptor.isActive(portletDataContext) ||
					(stagingSupportedOnly &&
					 !exportImportDescriptor.isStagingSupported())) {

					return false;
				}

				return true;
			});
	}

	private byte[] _getBytes(String fileName, InputStream inputStream)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				unsyncByteArrayOutputStream)) {

			ZipEntry zipEntry = new ZipEntry(fileName);

			zipOutputStream.putNextEntry(zipEntry);

			StreamUtil.transfer(inputStream, zipOutputStream, false);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private Set<String> _getExportedLayoutERCs(
			PortletDataContext portletDataContext, Registration registration)
		throws Exception {

		Set<String> exportedLayoutERCs = new HashSet<>();

		String normalizedFileName = _normalize(
			registration.getFileName(), portletDataContext.getSourceGroupId());

		InputStream inputStream = portletDataContext.getZipEntryAsInputStream(
			normalizedFileName);

		if (inputStream == null) {
			return exportedLayoutERCs;
		}

		try {
			String jsonContent = StreamUtil.toString(inputStream);

			if ((jsonContent == null) || jsonContent.isEmpty()) {
				return exportedLayoutERCs;
			}

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(jsonContent);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				if (jsonObject == null) {
					continue;
				}

				String externalReferenceCode = jsonObject.getString(
					"externalReferenceCode");

				if ((externalReferenceCode != null) &&
					!externalReferenceCode.isEmpty()) {

					exportedLayoutERCs.add(externalReferenceCode);
				}
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return exportedLayoutERCs;
	}

	private PortletDataHandlerControl _getPortletDataHandlerControl(
		Registration registration) {

		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor = registration.getExportImportDescriptor();

		return new PortletDataHandlerBoolean(
			getPortletId(), exportImportDescriptor.getResourceClassName(),
			exportImportDescriptor.getLabelLanguageKey(), true, false, null,
			exportImportDescriptor.getResourceClassName(), null);
	}

	private <T> T _getSoleProperty(
		Function
			<ExportImportVulcanBatchEngineTaskItemDelegate.
				ExportImportDescriptor,
			 T> function) {

		if (_registrations.size() != 1) {
			return null;
		}

		Registration registration = _registrations.get(0);

		return function.apply(registration.getExportImportDescriptor());
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private String _normalize(String fileName, long groupId) {
		return StringBundler.concat(
			StringPool.FORWARD_SLASH, ExportImportPathUtil.PATH_PREFIX_GROUP,
			StringPool.FORWARD_SLASH, groupId, StringPool.FORWARD_SLASH,
			fileName);
	}

	private void _updateDeletionSystemEventStagedModelTypes() {
		setDeletionSystemEventStagedModelTypes(
			TransformUtil.transformToArray(
				_registrations,
				registration -> {
					ExportImportVulcanBatchEngineTaskItemDelegate.
						ExportImportDescriptor exportImportDescriptor =
							registration.getExportImportDescriptor();

					return new StagedModelType(
						exportImportDescriptor.getModelClassName());
				},
				StagedModelType.class));
	}

	private void _updatePortletDataHandlerControls() {
		if (_registrations.size() > 1) {
			setEmptyControlsAllowed(false);
			setExportPortletDataHandlerControls(
				TransformUtil.transformToArray(
					_registrations, this::_getPortletDataHandlerControl,
					PortletDataHandlerControl.class));
			setStagingPortletDataHandlerControls(
				getExportPortletDataHandlerControls());
		}
		else {
			setEmptyControlsAllowed(true);
			setExportPortletDataHandlerControls();
			setStagingPortletDataHandlerControls();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEnginePortletDataHandler.class);

	private final BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;
	private final BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;
	private final BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;
	private final BatchEngineImportTaskService _batchEngineImportTaskService;
	private final ChangesetEntryLocalService _changesetEntryLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final ExportImportHelper _exportImportHelper;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final List<Registration> _registrations = new ArrayList<>();
	private final StagingGroupHelper _stagingGroupHelper;

	private interface Registration {

		public String getBatchEngineClassName();

		public String getDeletionsFileName();

		public
			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				getExportImportDescriptor();

		public String getFileName();

		public String getTaskItemDelegateName();

	}

}