/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.rest.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.dto.v1_0.ImportProcess;
import com.liferay.exportimport.rest.dto.v1_0.ImportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.InstanceImportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.ProcessProgress;
import com.liferay.exportimport.rest.dto.v1_0.Status;
import com.liferay.exportimport.rest.internal.util.BackgroundTaskUtil;
import com.liferay.exportimport.rest.internal.util.GroupUtil;
import com.liferay.exportimport.rest.internal.util.ImportPreviewUtil;
import com.liferay.exportimport.rest.internal.util.ImportTempFileEntryUtil;
import com.liferay.exportimport.rest.internal.util.ImportUploadUtil;
import com.liferay.exportimport.rest.internal.util.LayoutUtil;
import com.liferay.exportimport.rest.internal.util.ParameterMapUtil;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.internal.util.PortletDataHandlerUtil;
import com.liferay.exportimport.rest.resource.v1_0.ImportProcessResource;
import com.liferay.exportimport.upload.ImportUpload;
import com.liferay.exportimport.upload.ImportUploadException;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.BadRequestException;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 * @author Daniel Raposo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/import-process.properties",
	scope = ServiceScope.PROTOTYPE, service = ImportProcessResource.class
)
public class ImportProcessResourceImpl extends BaseImportProcessResourceImpl {

	@Override
	public void deleteImportProcess(Long importProcessId) throws Exception {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(importProcessId);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), backgroundTask.getGroupId(),
			PermissionUtil.getGroupActionId(backgroundTask));

		BackgroundTaskUtil.checkTaskExecutorClassName(
			backgroundTask, _CLASS_NAMES_TASK_EXECUTOR);

		if (!backgroundTask.isCompleted() &&
			(backgroundTask.getStatus() !=
				BackgroundTaskConstants.STATUS_IN_PROGRESS)) {

			_deleteImportFile(backgroundTask);
		}

		_backgroundTaskLocalService.deleteBackgroundTask(backgroundTask);
	}

	@Override
	public Page<ImportProcess> getAssetLibraryImportProcessesPage(
			String assetLibraryExternalReferenceCode, Long creatorId,
			String portletId, String search, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getImportProcessesPage(
			creatorId,
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			pagination, portletId, search, sorts, status);
	}

	@Override
	public ImportProcess getImportProcess(Long importProcessId)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(importProcessId);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), backgroundTask.getGroupId(),
			PermissionUtil.getGroupActionId(backgroundTask));

		BackgroundTaskUtil.checkTaskExecutorClassName(
			backgroundTask, _CLASS_NAMES_TASK_EXECUTOR);

		return _toImportProcess(backgroundTask);
	}

	@Override
	public ProcessProgress getImportProcessProgress(Long importProcessId)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(importProcessId);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), backgroundTask.getGroupId(),
			PermissionUtil.getGroupActionId(backgroundTask));

		BackgroundTaskUtil.checkTaskExecutorClassName(
			backgroundTask, _CLASS_NAMES_TASK_EXECUTOR);

		return new ProcessProgress() {
			{
				setPercentage(
					() -> BackgroundTaskUtil.getPercentage(
						backgroundTask.getBackgroundTaskId()));
			}
		};
	}

	@Override
	public Page<ImportProcess> getImportProcessesPage(
			Long creatorId, String portletId, String search, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getImportProcessesPage(
			creatorId, GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			pagination, portletId, search, sorts, status);
	}

	@Override
	public Page<ImportProcess> getSiteImportProcessesPage(
			String siteExternalReferenceCode, Long creatorId, String portletId,
			String search, String sitePageExternalReferenceCode, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Group group = GroupUtil.getSiteGroup(
			contextCompany.getCompanyId(), siteExternalReferenceCode,
			portletId);

		Layout layout = LayoutUtil.fetchPortletLayout(
			group, portletId, sitePageExternalReferenceCode);

		if ((layout == null) &&
			Validator.isNotNull(sitePageExternalReferenceCode)) {

			return Page.of(Collections.emptyList());
		}

		return _getImportProcessesPage(
			creatorId, GroupUtil.getScopeGroup(group, layout, portletId),
			pagination, portletId, search, sorts, status);
	}

	@Override
	public ImportProcess postAssetLibraryImportProcess(
			String assetLibraryExternalReferenceCode, String portletId,
			ImportProcessRequest importProcessRequest)
		throws Exception {

		return _postImportProcess(
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			importProcessRequest, portletId, null, null);
	}

	@Override
	public ImportProcess postImportProcess(
			String portletId,
			InstanceImportProcessRequest instanceImportProcessRequest)
		throws Exception {

		return _postImportProcess(
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			_toImportProcessRequest(instanceImportProcessRequest), portletId,
			GroupUtil.getGroupExternalReferenceCodes(
				contextCompany.getCompanyId(),
				instanceImportProcessRequest.getAssetLibraries(),
				instanceImportProcessRequest.getSites()),
			null);
	}

	@Override
	public ImportProcess postSiteImportProcess(
			String siteExternalReferenceCode, String portletId,
			String sitePageExternalReferenceCode,
			ImportProcessRequest importProcessRequest)
		throws Exception {

		return _postImportProcess(
			GroupUtil.getSiteGroup(
				contextCompany.getCompanyId(), siteExternalReferenceCode,
				portletId),
			importProcessRequest, portletId, null,
			sitePageExternalReferenceCode);
	}

	private ImportProcess _addImportProcess(
			String backgroundTaskExecutorName,
			ExportImportConfiguration exportImportConfiguration,
			Map<String, Serializable> fileTaskContextMap,
			boolean resolvePrivateLayout)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				contextUser.getUserId(), exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), backgroundTaskExecutorName,
				HashMapBuilder.<String, Serializable>put(
					"exportImportConfigurationId",
					exportImportConfiguration.getExportImportConfigurationId()
				).put(
					"resolvePrivateLayout", resolvePrivateLayout
				).putAll(
					fileTaskContextMap
				).build(),
				new ServiceContext());

		return _toImportProcess(backgroundTask);
	}

	private void _deleteImportFile(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		if (taskContextMap.containsKey("importUploadId")) {
			_importUploadManager.deleteImportUpload(
				MapUtil.getLong(taskContextMap, "uploadGroupId"),
				MapUtil.getString(taskContextMap, "importUploadId"),
				backgroundTask.getUserId());

			return;
		}

		long tempFileEntryId = MapUtil.getLong(
			taskContextMap, "tempFileEntryId");

		if (tempFileEntryId > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(tempFileEntryId);
		}
	}

	private List<BackgroundTask> _getBackgroundTasks(
			Long creatorId, long groupId, Pagination pagination,
			String portletId, String search, Sort[] sorts, Integer status)
		throws Exception {

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), groupId,
			PermissionUtil.getGroupActionId(portletId));

		DynamicQuery dynamicQuery = _getDynamicQuery(
			creatorId, groupId, portletId, search, status);

		BackgroundTaskUtil.addOrders(dynamicQuery, sorts);

		return _backgroundTaskLocalService.dynamicQuery(
			dynamicQuery, pagination.getStartPosition(),
			pagination.getEndPosition());
	}

	private DynamicQuery _getDynamicQuery(
		Long creatorId, long groupId, String portletId, String search,
		Integer status) {

		DynamicQuery dynamicQuery = _backgroundTaskLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", contextCompany.getCompanyId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", groupId));

		if (!Validator.isBlank(search)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(search, StringPool.PERCENT)));
		}

		if (status != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("status", status));
		}

		if (Validator.isBlank(portletId)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"taskExecutorClassName",
					BackgroundTaskExecutorNames.
						LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR));
		}
		else {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("name", portletId));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"taskExecutorClassName",
					BackgroundTaskExecutorNames.
						PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR));
		}

		if (creatorId != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", creatorId));
		}

		return dynamicQuery;
	}

	private Map<String, Serializable> _getFileTaskContextMap(
			long groupId, ImportProcessRequest importProcessRequest,
			long uploadGroupId)
		throws Exception {

		String importUploadId = importProcessRequest.getImportUploadId();

		if (Validator.isNull(importUploadId)) {
			FileEntry fileEntry = ImportTempFileEntryUtil.getTempFileEntry(
				groupId, contextUser.getUserId());

			return HashMapBuilder.<String, Serializable>put(
				"fileName", ImportTempFileEntryUtil.getFileName(fileEntry)
			).put(
				"tempFileEntryId", fileEntry.getFileEntryId()
			).build();
		}

		ImportUpload importUpload = null;

		try {
			importUpload = _importUploadManager.fetchImportUpload(
				uploadGroupId, importUploadId, contextUser.getUserId());
		}
		catch (ImportUploadException importUploadException) {
			throw new BadRequestException(
				importUploadException.getMessage(), importUploadException);
		}

		if (importUpload == null) {
			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-uploaded-file-has-expired-please-upload-it-again"));
		}

		if (ImportUploadUtil.hasPendingImport(
				new long[] {groupId, uploadGroupId}, importUploadId)) {

			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-upload-is-already-being-imported"));
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ImportUploadUtil.fetchPreviewBackgroundTaskId(
					uploadGroupId, importUploadId));

		if (backgroundTask == null) {
			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-upload-is-not-complete"));
		}

		int status = backgroundTask.getStatus();

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			String errorMessage = ImportUploadUtil.getErrorMessage(
				backgroundTask.getStatusMessage());

			if (errorMessage == null) {
				errorMessage = _language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"an-unexpected-error-occurred");
			}

			throw new BadRequestException(errorMessage);
		}

		if (status != BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-upload-is-not-complete"));
		}

		if (!importUpload.isComplete()) {
			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-uploaded-file-has-expired-please-upload-it-again"));
		}

		return HashMapBuilder.<String, Serializable>put(
			"fileName", importUpload.getFileName()
		).put(
			"importUploadId", importUploadId
		).put(
			"uploadGroupId", uploadGroupId
		).build();
	}

	private PortletDataHandler[] _getImportPortletDataHandlers(
			Map<String, Serializable> fileTaskContextMap,
			UnsafeFunction<FileEntry, PortletDataHandler[], Exception>
				unsafeFunction,
			long uploadGroupId)
		throws Exception {

		String importUploadId = MapUtil.getString(
			fileTaskContextMap, "importUploadId");

		if (Validator.isNull(importUploadId)) {
			return unsafeFunction.apply(
				PortletFileRepositoryUtil.getPortletFileEntry(
					MapUtil.getLong(fileTaskContextMap, "tempFileEntryId")));
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(
				ImportUploadUtil.fetchPreviewBackgroundTaskId(
					uploadGroupId, importUploadId));

		ImportPreview importPreview = ImportPreviewUtil.toImportPreview(
			backgroundTask.getStatusMessage());

		return importPreview.getPortletDataHandlers();
	}

	private Page<ImportProcess> _getImportProcessesPage(
			Long creatorId, Group group, Pagination pagination,
			String portletId, String search, Sort[] sorts, Integer status)
		throws Exception {

		long groupId = group.getGroupId();

		return Page.of(
			transform(
				_getBackgroundTasks(
					creatorId, groupId, pagination, portletId, search, sorts,
					status),
				this::_toImportProcess),
			pagination,
			_backgroundTaskLocalService.dynamicQueryCount(
				_getDynamicQuery(
					creatorId, groupId, portletId, search, status)));
	}

	private ImportProcess _postImportProcess(
			Group group, ImportProcessRequest importProcessRequest,
			String portletId, String[] groupExternalReferenceCodes,
			String sitePageExternalReferenceCode)
		throws Exception {

		Layout layout = LayoutUtil.getPortletLayout(
			group, portletId, sitePageExternalReferenceCode);

		if (layout == null) {
			return _postLayoutImportProcess(
				group, importProcessRequest, groupExternalReferenceCodes);
		}

		return _postPortletImportProcess(
			GroupUtil.getScopeGroup(group, layout, portletId),
			importProcessRequest, layout, portletId, group.getGroupId());
	}

	private ImportProcess _postLayoutImportProcess(
			Group group, ImportProcessRequest importProcessRequest,
			String[] groupExternalReferenceCodes)
		throws Exception {

		long groupId = group.getGroupId();

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), groupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		Map<String, Serializable> fileTaskContextMap = _getFileTaskContextMap(
			groupId, importProcessRequest, groupId);

		String name = importProcessRequest.getName();

		if (Validator.isBlank(name)) {
			name = MapUtil.getString(fileTaskContextMap, "fileName");
		}

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			importProcessRequest,
			_getImportPortletDataHandlers(
				fileTaskContextMap,
				fileEntry -> {
					ManifestSummary manifestSummary =
						_exportImportHelper.getManifestSummary(
							contextUser.getUserId(), groupId, new HashMap<>(),
							fileEntry);

					return PortletDataHandlerUtil.getImportPortletDataHandlers(
						false, group,
						contextAcceptLanguage.getPreferredLocale(),
						manifestSummary, manifestSummary.getDataPortlets(),
						false);
				},
				groupId),
			false);

		ParameterMapUtil.putGroupExternalReferenceCodes(
			parameterMap, groupExternalReferenceCodes);

		Map<String, Serializable> settingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					contextUser.getUserId(), groupId,
					group.isLayoutSetPrototype(), null, parameterMap,
					contextAcceptLanguage.getPreferredLocale(),
					contextUser.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					contextUser.getUserId(), name,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					settingsMap);

		return _addImportProcess(
			BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
			exportImportConfiguration, fileTaskContextMap,
			!group.isLayoutSetPrototype());
	}

	private ImportProcess _postPortletImportProcess(
			Group group, ImportProcessRequest importProcessRequest,
			Layout layout, String portletId, long uploadGroupId)
		throws Exception {

		long groupId = group.getGroupId();

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), groupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		Map<String, Serializable> fileTaskContextMap = _getFileTaskContextMap(
			groupId, importProcessRequest, uploadGroupId);

		String name = importProcessRequest.getName();

		if (Validator.isBlank(name)) {
			name = MapUtil.getString(fileTaskContextMap, "fileName");
		}

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			importProcessRequest,
			_getImportPortletDataHandlers(
				fileTaskContextMap,
				fileEntry ->
					PortletDataHandlerUtil.getImportPortletDataHandlers(
						false, group,
						contextAcceptLanguage.getPreferredLocale(),
						_exportImportHelper.getManifestSummary(
							contextUser.getUserId(), groupId, new HashMap<>(),
							fileEntry),
						ListUtil.fromArray(
							_portletLocalService.getPortletById(
								contextCompany.getCompanyId(), portletId)),
						true),
				uploadGroupId),
			true);

		Map<String, Serializable> settingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildImportPortletSettingsMap(
					contextUser.getUserId(), layout.getPlid(), groupId,
					portletId, parameterMap,
					contextAcceptLanguage.getPreferredLocale(),
					contextUser.getTimeZone());

		settingsMap.put("name", name);

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					contextUser.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					settingsMap);

		return _addImportProcess(
			BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR,
			exportImportConfiguration, fileTaskContextMap, false);
	}

	private ImportProcess _toImportProcess(BackgroundTask backgroundTask)
		throws PortalException {

		return new ImportProcess() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						null, _portal,
						_userLocalService.fetchUser(
							backgroundTask.getUserId())));
				setDateCompleted(backgroundTask::getCompletionDate);
				setDateCreated(backgroundTask::getCreateDate);
				setDateModified(backgroundTask::getModifiedDate);
				setErrorMessage(
					() -> BackgroundTaskUtil.getErrorMessage(
						backgroundTask,
						contextAcceptLanguage.getPreferredLocale()));
				setId(backgroundTask::getBackgroundTaskId);
				setName(() -> BackgroundTaskUtil.getName(backgroundTask));
				setStatus(
					() -> new Status() {
						{
							setCode(backgroundTask::getStatus);
							setLabel(
								() -> _language.get(
									contextUser.getLocale(),
									BackgroundTaskConstants.getStatusLabel(
										backgroundTask.getStatus())));
						}
					});
			}
		};
	}

	private ImportProcessRequest _toImportProcessRequest(
		InstanceImportProcessRequest instanceImportProcessRequest) {

		return new ImportProcessRequest() {
			{
				setComments(instanceImportProcessRequest::getComments);
				setDataStrategy(
					() -> {
						InstanceImportProcessRequest.DataStrategy dataStrategy =
							instanceImportProcessRequest.getDataStrategy();

						if (dataStrategy == null) {
							return null;
						}

						return ImportProcessRequest.DataStrategy.create(
							dataStrategy.getValue());
					});
				setDeletions(instanceImportProcessRequest::getDeletions);
				setImportUploadId(
					instanceImportProcessRequest::getImportUploadId);
				setName(instanceImportProcessRequest::getName);
				setPermissions(instanceImportProcessRequest::getPermissions);
				setPortletDataHandlers(
					instanceImportProcessRequest::getPortletDataHandlers);
				setRatings(instanceImportProcessRequest::getRatings);
				setUserIdStrategy(
					() -> {
						InstanceImportProcessRequest.UserIdStrategy
							userIdStrategy =
								instanceImportProcessRequest.
									getUserIdStrategy();

						if (userIdStrategy == null) {
							return null;
						}

						return ImportProcessRequest.UserIdStrategy.create(
							userIdStrategy.getValue());
					});
			}
		};
	}

	private static final String[] _CLASS_NAMES_TASK_EXECUTOR = {
		BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
	};

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ImportUploadManager _importUploadManager;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private UserLocalService _userLocalService;

}