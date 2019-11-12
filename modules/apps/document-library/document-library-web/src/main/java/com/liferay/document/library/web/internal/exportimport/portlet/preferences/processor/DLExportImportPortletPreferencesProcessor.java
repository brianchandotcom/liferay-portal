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
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
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

		String portletId = portletDataContext.getPortletId();

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = null;

			try {
				folder = _dlAppLocalService.getFolder(rootFolderId);
			}
			catch (PortalException pe) {
				StringBundler sb = new StringBundler(4);

				sb.append("Portlet ");
				sb.append(portletId);
				sb.append(" refers to an invalid root folder ID ");
				sb.append(rootFolderId);

				_log.error(sb.toString());

				throw new PortletDataException(sb.toString(), pe);
			}

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, folder);

			return portletPreferences;
		}

		if (!_exportImportHelper.isExportPortletData(portletDataContext)) {
			return portletPreferences;
		}

		// Root folder ID is not set, we need to export everything

		try {
			portletDataContext.addPortletPermissions(DLConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		try {
			String namespace = _dlPortletDataHandler.getNamespace();

			if (portletDataContext.getBooleanParameter(namespace, "folders")) {
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

						Folder folder = _dlAppLocalService.getFolder(
							dlFolder.getFolderId());

						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, folder);
					});

				folderActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "documents")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFileEntry.class.getName());

				ActionableDynamicQuery fileEntryActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				fileEntryActionableDynamicQuery.setPerformActionMethod(
					(DLFileEntry dlFileEntry) -> {
						FileEntry fileEntry = _dlAppLocalService.getFileEntry(
							dlFileEntry.getFileEntryId());

						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, fileEntry);
					});

				fileEntryActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "document-types")) {

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
									portletDataContext, portletId,
									dlFileEntryType);
						}
					});

				fileEntryTypeActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "repositories")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						Repository.class.getName());

				ActionableDynamicQuery repositoryActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				repositoryActionableDynamicQuery.setPerformActionMethod(
					(Repository repository) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, repository));

				repositoryActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "shortcuts")) {

				StagedModelRepository<?> stagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						DLFileShortcut.class.getName());

				ActionableDynamicQuery fileShortcutActionableDynamicQuery =
					stagedModelRepository.getExportActionableDynamicQuery(
						portletDataContext);

				fileShortcutActionableDynamicQuery.setPerformActionMethod(
					(DLFileShortcut dlFileShortcut) -> {
						FileShortcut fileShortcut =
							_dlAppLocalService.getFileShortcut(
								dlFileShortcut.getFileShortcutId());

						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, fileShortcut);
					});

				fileShortcutActionableDynamicQuery.performActions();
			}
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			pde.setType(PortletDataException.EXPORT_PORTLET_DATA);

			throw pde;
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		// Root folder ID is set, only import that

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId > 0) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(DLFolder.class);

			List<Element> folderElements = foldersElement.elements();

			if (!folderElements.isEmpty()) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElements.get(0));

				Map<Long, Long> folderIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						Folder.class + ".folderIdsAndRepositoryEntryIds");

				rootFolderId = MapUtil.getLong(
					folderIds, rootFolderId, rootFolderId);

				try {
					portletPreferences.setValue(
						"rootFolderId", String.valueOf(rootFolderId));
				}
				catch (ReadOnlyException roe) {
					throw new PortletDataException(
						"Unable to update portlet preferences during import",
						roe);
				}
			}
		}

		// Root folder is is not set, need to import everything

		try {
			portletDataContext.importPortletPermissions(
				DLConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		String namespace = _dlPortletDataHandler.getNamespace();

		if (portletDataContext.getBooleanParameter(namespace, "folders")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(DLFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "documents")) {
			Element fileEntriesElement =
				portletDataContext.getImportDataGroupElement(DLFileEntry.class);

			List<Element> fileEntryElements = fileEntriesElement.elements();

			for (Element fileEntryElement : fileEntryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				namespace, "document-types")) {

			Element fileEntryTypesElement =
				portletDataContext.getImportDataGroupElement(
					DLFileEntryType.class);

			List<Element> fileEntryTypeElements =
				fileEntryTypesElement.elements();

			for (Element fileEntryTypeElement : fileEntryTypeElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryTypeElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "repositories")) {
			Element repositoriesElement =
				portletDataContext.getImportDataGroupElement(Repository.class);

			List<Element> repositoryElements = repositoriesElement.elements();

			for (Element repositoryElement : repositoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, repositoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "shortcuts")) {
			Element fileShortcutsElement =
				portletDataContext.getImportDataGroupElement(
					DLFileShortcut.class);

			List<Element> fileShortcutElements =
				fileShortcutsElement.elements();

			for (Element fileShortcutElement : fileShortcutElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileShortcutElement);
			}
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLExportImportPortletPreferencesProcessor.class);

	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLCommentsAndRatingsExporterImporterCapability
		_dlCommentsAndRatingsExporterImporterCapability;

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")"
	)
	private PortletDataHandler _dlPortletDataHandler;

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _exportCapability;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _importCapability;

}