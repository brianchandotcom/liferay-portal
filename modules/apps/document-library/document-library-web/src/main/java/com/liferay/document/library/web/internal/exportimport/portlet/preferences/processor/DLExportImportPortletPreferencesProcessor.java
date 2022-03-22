/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
	service = {
		DLExportImportPortletPreferencesProcessor.class,
		ExportImportPortletPreferencesProcessor.class
	}
)
public class DLExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(
			_dlCommentsAndRatingsExporterImporterCapability, _exportCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(
			_dlCommentsAndRatingsExporterImporterCapability, _importCapability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			return portletPreferences;
		}

		// Root folder ID is set, only export that

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				Folder folder = _getFolder(rootFolderId, portletDataContext);

				portletPreferences.setValue(
					"selectedRepositoryId",
					String.valueOf(folder.getRepositoryId()));

				if ((folder.getGroupId() == portletDataContext.getGroupId()) ||
					!ExportImportThreadLocal.isStagingInProcess()) {

					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletDataContext.getPortletId(),
						folder);
				}
				else {
					_saveStagingPreferencesMapping(
						folder.getRepositoryId(), folder.getUuid(),
						portletDataContext);
				}

				return portletPreferences;
			}
			catch (ReadOnlyException readOnlyException) {
				throw new PortletDataException(
					"Unable to update portlet preferences during import",
					readOnlyException);
			}
		}

		long selectedRepositoryId = GetterUtil.getLong(
			portletPreferences.getValue("selectedRepositoryId", null));

		if (!_exportImportHelper.isExportPortletData(portletDataContext) ||
			(selectedRepositoryId != portletDataContext.getGroupId())) {

			if (ExportImportThreadLocal.isStagingInProcess()) {
				_saveStagingPreferencesMapping(
					selectedRepositoryId, null, portletDataContext);
			}

			return portletPreferences;
		}

		// Root folder ID is not set, we need to export everything

		try {
			portletDataContext.addPortletPermissions(DLConstants.RESOURCE_NAME);
		}
		catch (PortalException portalException) {
			PortletDataException portletDataException =
				new PortletDataException(portalException);

			portletDataException.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			portletDataException.setType(
				PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw portletDataException;
		}

		try {
			if (portletDataContext.getBooleanParameter(
					_dlPortletDataHandler.getNamespace(), "folders")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFolder.class.getName());

				ActionableDynamicQuery folderActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				folderActionableDynamicQuery.setPerformActionMethod(
					(DLFolder dlFolder) -> {
						if (dlFolder.isInTrash()) {
							return;
						}

						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext,
							portletDataContext.getPortletId(),
							_dlAppLocalService.getFolder(
								dlFolder.getFolderId()));
					});

				folderActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					_dlPortletDataHandler.getNamespace(), "documents")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFileEntry.class.getName());

				ActionableDynamicQuery fileEntryActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				fileEntryActionableDynamicQuery.setPerformActionMethod(
					(DLFileEntry dlFileEntry) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext,
							portletDataContext.getPortletId(),
							_dlAppLocalService.getFileEntry(
								dlFileEntry.getFileEntryId())));

				fileEntryActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					_dlPortletDataHandler.getNamespace(), "document-types")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFileEntryType.class.getName());

				ActionableDynamicQuery fileEntryTypeActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				fileEntryTypeActionableDynamicQuery.setPerformActionMethod(
					(DLFileEntryType dlFileEntryType) -> {
						if (dlFileEntryType.isExportable()) {
							StagedModelDataHandlerUtil.
								exportReferenceStagedModel(
									portletDataContext,
									portletDataContext.getPortletId(),
									dlFileEntryType);
						}
					});

				fileEntryTypeActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					_dlPortletDataHandler.getNamespace(), "repositories")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						Repository.class.getName());

				ActionableDynamicQuery repositoryActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				repositoryActionableDynamicQuery.setPerformActionMethod(
					(Repository repository) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext,
							portletDataContext.getPortletId(), repository));

				repositoryActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					_dlPortletDataHandler.getNamespace(), "shortcuts")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFileShortcut.class.getName());

				ActionableDynamicQuery fileShortcutActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				fileShortcutActionableDynamicQuery.setPerformActionMethod(
					(DLFileShortcut dlFileShortcut) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext,
							portletDataContext.getPortletId(),
							_dlAppLocalService.getFileShortcut(
								dlFileShortcut.getFileShortcutId())));

				fileShortcutActionableDynamicQuery.performActions();
			}
		}
		catch (PortalException portalException) {
			PortletDataException portletDataException =
				new PortletDataException(portalException);

			portletDataException.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			portletDataException.setType(
				PortletDataException.EXPORT_PORTLET_DATA);

			throw portletDataException;
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		JSONObject stagingPreferencesMappingJSONObject =
			_fetchStagingPreferencesMappingJSONObject(portletDataContext);

		if (stagingPreferencesMappingJSONObject != null) {
			try {
				long folderRepositoryId =
					stagingPreferencesMappingJSONObject.getLong(
						"folderRepositoryId");
				String folderUuid =
					stagingPreferencesMappingJSONObject.getString("folderUuid");

				long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (Validator.isNotNull(folderUuid)) {
					DLFolder dlFolder =
						_dlFolderLocalService.getDLFolderByUuidAndGroupId(
							folderUuid, folderRepositoryId);

					folderId = dlFolder.getFolderId();
				}

				portletPreferences.setValue(
					"rootFolderId", String.valueOf(folderId));
				portletPreferences.setValue(
					"selectedRepositoryId", String.valueOf(folderRepositoryId));

				return portletPreferences;
			}
			catch (PortalException | ReadOnlyException exception) {
				throw new PortletDataException(exception);
			}
		}

		// Root folder ID is set, only import that

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(DLFolder.class);

			List<Element> folderElements = foldersElement.elements();

			if (!folderElements.isEmpty()) {
				try {
					StagedModelDataHandlerUtil.importStagedModel(
						portletDataContext, folderElements.get(0));

					Map<Long, Long> folderIds =
						(Map<Long, Long>)
							portletDataContext.getNewPrimaryKeysMap(
								Folder.class +
									".folderIdsAndRepositoryEntryIds");

					long importedRootFolderId = MapUtil.getLong(
						folderIds, rootFolderId, rootFolderId);

					portletPreferences.setValue(
						"rootFolderId", String.valueOf(importedRootFolderId));

					Folder folder = _getFolder(
						importedRootFolderId, portletDataContext);

					portletPreferences.setValue(
						"selectedRepositoryId",
						String.valueOf(folder.getRepositoryId()));

					return portletPreferences;
				}
				catch (ReadOnlyException readOnlyException) {
					throw new PortletDataException(
						"Unable to update portlet preferences during import",
						readOnlyException);
				}
			}
		}

		try {
			long selectedRepositoryId = GetterUtil.getLong(
				portletPreferences.getValue("selectedRepositoryId", null));

			if (selectedRepositoryId == portletDataContext.getSourceGroupId()) {
				portletPreferences.setValue(
					"selectedRepositoryId",
					String.valueOf(portletDataContext.getGroupId()));
			}
		}
		catch (ReadOnlyException readOnlyException) {
			throw new PortletDataException(
				"Unable to update portlet preferences during import",
				readOnlyException);
		}

		// Root folder is not set, need to import everything

		try {
			portletDataContext.importPortletPermissions(
				DLConstants.RESOURCE_NAME);
		}
		catch (PortalException portalException) {
			PortletDataException portletDataException =
				new PortletDataException(portalException);

			portletDataException.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			portletDataException.setType(
				PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw portletDataException;
		}

		if (portletDataContext.getBooleanParameter(
				_dlPortletDataHandler.getNamespace(), "folders")) {

			Element foldersElement =
				portletDataContext.getImportDataGroupElement(DLFolder.class);

			for (Element folderElement : foldersElement.elements()) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_dlPortletDataHandler.getNamespace(), "documents")) {

			Element fileEntriesElement =
				portletDataContext.getImportDataGroupElement(DLFileEntry.class);

			for (Element fileEntryElement : fileEntriesElement.elements()) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_dlPortletDataHandler.getNamespace(), "document-types")) {

			Element fileEntryTypesElement =
				portletDataContext.getImportDataGroupElement(
					DLFileEntryType.class);

			for (Element fileEntryTypeElement :
					fileEntryTypesElement.elements()) {

				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryTypeElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_dlPortletDataHandler.getNamespace(), "repositories")) {

			Element repositoriesElement =
				portletDataContext.getImportDataGroupElement(Repository.class);

			for (Element repositoryElement : repositoriesElement.elements()) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, repositoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_dlPortletDataHandler.getNamespace(), "shortcuts")) {

			Element fileShortcutsElement =
				portletDataContext.getImportDataGroupElement(
					DLFileShortcut.class);

			for (Element fileShortcutElement :
					fileShortcutsElement.elements()) {

				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileShortcutElement);
			}
		}

		return portletPreferences;
	}

	private JSONObject _fetchStagingPreferencesMappingJSONObject(
			PortletDataContext portletDataContext)
		throws PortletDataException {

		try {
			String stagingPreferencesMappingJSON =
				portletDataContext.getZipEntryAsString(
					String.format(
						"%s/staging-preferences-mapping.json",
						portletDataContext.getPortletId()));

			if (Validator.isNull(stagingPreferencesMappingJSON)) {
				return null;
			}

			return JSONFactoryUtil.createJSONObject(
				stagingPreferencesMappingJSON);
		}
		catch (JSONException jsonException) {
			throw new PortletDataException(jsonException);
		}
	}

	private Folder _getFolder(
			long folderId, PortletDataContext portletDataContext)
		throws PortletDataException {

		try {
			return _dlAppLocalService.getFolder(folderId);
		}
		catch (PortalException portalException) {
			String errorMessage = StringBundler.concat(
				"Portlet ", portletDataContext.getPortletId(),
				" refers to an invalid root folder ID ", folderId);

			_log.error(errorMessage);

			throw new PortletDataException(errorMessage, portalException);
		}
	}

	private long _getMirrorRepositoryId(long repositoryId) {
		Group group = _groupLocalService.fetchGroup(repositoryId);

		if (group == null) {
			return repositoryId;
		}

		Group stagingGroup = group.getStagingGroup();

		if (stagingGroup != null) {
			return stagingGroup.getGroupId();
		}

		long liveGroupId = group.getLiveGroupId();

		if (group.isStagedRemotely()) {
			liveGroupId = group.getRemoteLiveGroupId();
		}

		if (liveGroupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			liveGroupId = group.getGroupId();
		}

		return liveGroupId;
	}

	private void _saveStagingPreferencesMapping(
		long folderRepositoryId, String folderUuid,
		PortletDataContext portletDataContext) {

		if (ExportImportThreadLocal.isStagingInProcess()) {
			portletDataContext.addZipEntry(
				String.format(
					"%s/staging-preferences-mapping.json",
					portletDataContext.getPortletId()),
				JSONUtil.put(
					"folderRepositoryId",
					_getMirrorRepositoryId(folderRepositoryId)
				).put(
					"folderUuid", folderUuid
				).toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLExportImportPortletPreferencesProcessor.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLCommentsAndRatingsExporterImporterCapability
		_dlCommentsAndRatingsExporterImporterCapability;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")"
	)
	private PortletDataHandler _dlPortletDataHandler;

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _exportCapability;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _importCapability;

}