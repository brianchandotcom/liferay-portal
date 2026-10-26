/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.constants.ExportImportBackgroundTaskExecutorNames;
import com.liferay.exportimport.rest.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.dto.v1_0.ImportUpload;
import com.liferay.exportimport.rest.dto.v1_0.ImportUploadRequest;
import com.liferay.exportimport.rest.internal.util.GroupUtil;
import com.liferay.exportimport.rest.internal.util.ImportPreviewUtil;
import com.liferay.exportimport.rest.internal.util.ImportUploadUtil;
import com.liferay.exportimport.rest.internal.util.LayoutUtil;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.resource.v1_0.ImportUploadResource;
import com.liferay.exportimport.upload.ImportUploadException;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.io.Serializable;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 * @author Jaime León
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/import-upload.properties",
	scope = ServiceScope.PROTOTYPE, service = ImportUploadResource.class
)
public class ImportUploadResourceImpl extends BaseImportUploadResourceImpl {

	@Override
	public void deleteAssetLibraryImportUpload(
			String assetLibraryExternalReferenceCode, String importUploadId)
		throws Exception {

		_deleteImportUpload(
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			importUploadId);
	}

	@Override
	public void deleteImportUpload(String importUploadId) throws Exception {
		_deleteImportUpload(
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			importUploadId);
	}

	@Override
	public void deleteSiteImportUpload(
			String siteExternalReferenceCode, String importUploadId)
		throws Exception {

		_deleteImportUpload(
			_getSiteGroup(siteExternalReferenceCode), importUploadId);
	}

	@Override
	public ImportUpload getAssetLibraryImportUpload(
			String assetLibraryExternalReferenceCode, String importUploadId)
		throws Exception {

		return _getImportUpload(
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			importUploadId);
	}

	@Override
	public ImportUpload getImportUpload(String importUploadId)
		throws Exception {

		return _getImportUpload(
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			importUploadId);
	}

	@Override
	public ImportUpload getSiteImportUpload(
			String siteExternalReferenceCode, String importUploadId)
		throws Exception {

		return _getImportUpload(
			_getSiteGroup(siteExternalReferenceCode), importUploadId);
	}

	@Override
	public ImportUpload postAssetLibraryImportUpload(
			String assetLibraryExternalReferenceCode, String portletId,
			ImportUploadRequest importUploadRequest)
		throws Exception {

		return _postImportUpload(
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			importUploadRequest, portletId, null);
	}

	@Override
	public ImportUpload postImportUpload(
			String portletId, ImportUploadRequest importUploadRequest)
		throws Exception {

		return _postImportUpload(
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			importUploadRequest, portletId, null);
	}

	@Override
	public ImportUpload postSiteImportUpload(
			String siteExternalReferenceCode, String portletId,
			String sitePageExternalReferenceCode,
			ImportUploadRequest importUploadRequest)
		throws Exception {

		return _postImportUpload(
			GroupUtil.getSiteGroup(
				contextCompany.getCompanyId(), siteExternalReferenceCode,
				portletId),
			importUploadRequest, portletId, sitePageExternalReferenceCode);
	}

	@Override
	public ImportUpload putAssetLibraryImportUploadChunkChunkIndex(
			String assetLibraryExternalReferenceCode, String importUploadId,
			Integer chunkIndex)
		throws Exception {

		return _putChunk(
			chunkIndex,
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			importUploadId);
	}

	@Override
	public ImportUpload putImportUploadChunkChunkIndex(
			String importUploadId, Integer chunkIndex)
		throws Exception {

		return _putChunk(
			chunkIndex,
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			importUploadId);
	}

	@Override
	public ImportUpload putSiteImportUploadChunkChunkIndex(
			String siteExternalReferenceCode, String importUploadId,
			Integer chunkIndex)
		throws Exception {

		return _putChunk(
			chunkIndex, _getSiteGroup(siteExternalReferenceCode),
			importUploadId);
	}

	private BackgroundTask _addPreviewBackgroundTask(
			Group group,
			com.liferay.exportimport.upload.ImportUpload importUpload)
		throws Exception {

		String owner = PortalUUIDUtil.generate();

		Lock lock = LockManagerUtil.lock(
			ImportUploadResource.class.getName(),
			importUpload.getImportUploadId(), owner);

		if (!owner.equals(lock.getOwner()) && _isExpired(lock)) {
			lock = LockManagerUtil.lock(
				ImportUploadResource.class.getName(),
				importUpload.getImportUploadId(), lock.getOwner(), owner);
		}

		if (!owner.equals(lock.getOwner())) {
			return ImportUploadUtil.fetchPreviewBackgroundTask(
				group.getGroupId(), importUpload.getImportUploadId());
		}

		try {
			BackgroundTask backgroundTask =
				ImportUploadUtil.fetchPreviewBackgroundTask(
					group.getGroupId(), importUpload.getImportUploadId());

			if (backgroundTask != null) {
				return backgroundTask;
			}

			return _backgroundTaskManager.addBackgroundTask(
				contextUser.getUserId(), group.getGroupId(),
				importUpload.getImportUploadId(),
				ExportImportBackgroundTaskExecutorNames.
					IMPORT_PREVIEW_BACKGROUND_TASK_EXECUTOR,
				HashMapBuilder.<String, Serializable>put(
					"importUploadId", importUpload.getImportUploadId()
				).put(
					"languageId",
					LocaleUtil.toLanguageId(
						contextAcceptLanguage.getPreferredLocale())
				).build(),
				new ServiceContext());
		}
		finally {
			LockManagerUtil.unlock(
				ImportUploadResource.class.getName(),
				importUpload.getImportUploadId(), owner);
		}
	}

	private void _deleteImportUpload(Group group, String importUploadId)
		throws Exception {

		com.liferay.exportimport.upload.ImportUpload importUpload =
			_getImportUploadWithPermission(group, importUploadId);

		Group scopeGroup = _getScopeGroup(group, importUpload);

		if (ImportUploadUtil.hasPendingImport(
				new long[] {group.getGroupId(), scopeGroup.getGroupId()},
				importUploadId)) {

			throw new BadRequestException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"the-upload-is-already-being-imported"));
		}

		_importUploadManager.deleteImportUpload(
			group.getGroupId(), importUploadId, contextUser.getUserId());
	}

	private String _getErrorMessage(
		BackgroundTask backgroundTask, ImportUpload.Status status) {

		if (status != ImportUpload.Status.FAILED) {
			return null;
		}

		if (backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_SUCCESSFUL) {

			return _language.get(
				contextAcceptLanguage.getPreferredLocale(),
				"the-uploaded-file-has-expired-please-upload-it-again");
		}

		String errorMessage = ImportUploadUtil.getErrorMessage(
			backgroundTask.getStatusMessage());

		if (errorMessage != null) {
			return errorMessage;
		}

		return _language.get(
			contextAcceptLanguage.getPreferredLocale(),
			"an-unexpected-error-occurred");
	}

	private ImportPreview _getImportPreview(
			BackgroundTask backgroundTask, ImportUpload.Status status)
		throws Exception {

		if (status != ImportUpload.Status.READY) {
			return null;
		}

		return ImportPreviewUtil.toImportPreview(
			backgroundTask.getStatusMessage());
	}

	private ImportUpload _getImportUpload(Group group, String importUploadId)
		throws Exception {

		com.liferay.exportimport.upload.ImportUpload importUpload =
			_getImportUploadWithPermission(group, importUploadId);

		BackgroundTask backgroundTask =
			ImportUploadUtil.fetchPreviewBackgroundTask(
				group.getGroupId(), importUploadId);

		if ((backgroundTask == null) && importUpload.isComplete()) {
			backgroundTask = _addPreviewBackgroundTask(group, importUpload);
		}

		return _toImportUpload(backgroundTask, importUpload);
	}

	private com.liferay.exportimport.upload.ImportUpload
			_getImportUploadWithPermission(Group group, String importUploadId)
		throws Exception {

		com.liferay.exportimport.upload.ImportUpload importUpload = null;

		try {
			importUpload = _importUploadManager.fetchImportUpload(
				group.getGroupId(), importUploadId, contextUser.getUserId());
		}
		catch (ImportUploadException importUploadException) {
			throw new NotFoundException(importUploadException);
		}

		if (importUpload == null) {
			throw new NotFoundException(
				"No upload exists with ID " + importUploadId);
		}

		Group scopeGroup = _getScopeGroup(group, importUpload);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), scopeGroup.getGroupId(),
			PermissionUtil.getGroupActionId(importUpload.getPortletId()));

		return importUpload;
	}

	private Integer _getPercentage(
		BackgroundTask backgroundTask, ImportUpload.Status status) {

		if (status != ImportUpload.Status.VALIDATING) {
			return null;
		}

		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		if (backgroundTaskStatus == null) {
			return 0;
		}

		return GetterUtil.getInteger(
			backgroundTaskStatus.getAttribute("percentage"));
	}

	private Group _getScopeGroup(
			Group group,
			com.liferay.exportimport.upload.ImportUpload importUpload)
		throws Exception {

		return GroupUtil.getScopeGroup(
			group, _layoutLocalService.fetchLayout(importUpload.getPlid()),
			importUpload.getPortletId());
	}

	private Group _getSiteGroup(String siteExternalReferenceCode) {
		Group group = groupLocalService.fetchGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		if ((group != null) && group.isLayout()) {
			return group;
		}

		return GroupUtil.getSiteGroup(
			contextCompany.getCompanyId(), siteExternalReferenceCode);
	}

	private ImportUpload.Status _getStatus(
		BackgroundTask backgroundTask,
		com.liferay.exportimport.upload.ImportUpload importUpload) {

		if (backgroundTask == null) {
			return ImportUpload.Status.UPLOADING;
		}

		int status = backgroundTask.getStatus();

		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			if (importUpload.isComplete()) {
				return ImportUpload.Status.READY;
			}

			return ImportUpload.Status.FAILED;
		}

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			return ImportUpload.Status.FAILED;
		}

		return ImportUpload.Status.VALIDATING;
	}

	private boolean _isExpired(Lock lock) {
		Date createDate = lock.getCreateDate();

		if ((System.currentTimeMillis() - createDate.getTime()) > Time.MINUTE) {
			return true;
		}

		return false;
	}

	private ImportUpload _postImportUpload(
			Group group, ImportUploadRequest importUploadRequest,
			String portletId, String sitePageExternalReferenceCode)
		throws Exception {

		Layout layout = LayoutUtil.getPortletLayout(
			group, portletId, sitePageExternalReferenceCode);

		Group scopeGroup = GroupUtil.getScopeGroup(group, layout, portletId);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), scopeGroup.getGroupId(),
			PermissionUtil.getGroupActionId(portletId));

		try {
			return _toImportUpload(
				null,
				_importUploadManager.addImportUpload(
					importUploadRequest.getContentType(),
					importUploadRequest.getFileName(), group.getGroupId(),
					(layout == null) ? 0 : layout.getPlid(), portletId,
					GetterUtil.getLong(importUploadRequest.getSize()),
					contextUser.getUserId()));
		}
		catch (ImportUploadException importUploadException) {
			throw new BadRequestException(
				importUploadException.getMessage(), importUploadException);
		}
	}

	private ImportUpload _putChunk(
			Integer chunkIndex, Group group, String importUploadId)
		throws Exception {

		com.liferay.exportimport.upload.ImportUpload importUpload =
			_getImportUploadWithPermission(group, importUploadId);

		try {
			importUpload = _importUploadManager.putChunk(
				GetterUtil.getInteger(chunkIndex), group.getGroupId(),
				importUpload, contextHttpServletRequest.getInputStream(),
				contextUser.getUserId());
		}
		catch (ImportUploadException importUploadException) {
			throw new BadRequestException(
				importUploadException.getMessage(), importUploadException);
		}

		BackgroundTask backgroundTask = null;

		if (importUpload.isComplete()) {
			backgroundTask = _addPreviewBackgroundTask(group, importUpload);
		}

		return _toImportUpload(backgroundTask, importUpload);
	}

	private ImportUpload _toImportUpload(
		BackgroundTask backgroundTask,
		com.liferay.exportimport.upload.ImportUpload importUpload) {

		ImportUpload.Status importUploadStatus = _getStatus(
			backgroundTask, importUpload);

		return new ImportUpload() {
			{
				setChunkSize(importUpload::getChunkSize);
				setErrorMessage(
					() -> _getErrorMessage(backgroundTask, importUploadStatus));
				setFileName(importUpload::getFileName);
				setId(importUpload::getImportUploadId);
				setImportPreview(
					() -> _getImportPreview(
						backgroundTask, importUploadStatus));
				setPercentage(
					() -> _getPercentage(backgroundTask, importUploadStatus));
				setSize(importUpload::getSize);
				setStatus(() -> importUploadStatus);
				setTotalChunksCount(importUpload::getTotalChunksCount);
				setUploadedChunksCount(importUpload::getUploadedChunksCount);
			}
		};
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ImportUploadManager _importUploadManager;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

}