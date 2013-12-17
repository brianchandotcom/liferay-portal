/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class FolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<Folder> {

	public static final String[] CLASS_NAMES = {
		DLFolder.class.getName(), Folder.class.getName(),
		LiferayFolder.class.getName()
	};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		Folder folder = FolderUtil.fetchByUUID_R(uuid, groupId);

		if (folder != null) {
			DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Folder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		Element folderElement = portletDataContext.getExportDataElement(
			folder, Folder.class);

		String folderPath = ExportImportPathUtil.getModelPath(folder);

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, Folder.class,
				folder.getParentFolder(), Folder.class,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		exportFolderFileEntryTypes(portletDataContext, folderElement, folder);

		portletDataContext.addClassedModel(
			folderElement, folderPath, folder, DLFolder.class);
	}

	@Override
	protected void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, String uuid, long folderId)
		throws Exception {

		Folder existingFolder = FolderUtil.fetchByUUID_R(
			uuid, portletDataContext.getCompanyGroupId());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		folderIds.put(folderId, existingFolder.getFolderId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, folder, Folder.class,
				folder.getParentFolderId());
		}

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, DLFolder.class);

		serviceContext.setUserId(userId);

		Folder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Folder existingFolder = FolderUtil.fetchByUUID_R(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = DLAppLocalServiceUtil.addFolder(
					userId, portletDataContext.getScopeGroupId(),
					parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), portletDataContext.getScopeGroupId(),
					parentFolderId, folder.getName(), 2);

				importedFolder = DLAppLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = DLAppLocalServiceUtil.addFolder(
				userId, portletDataContext.getScopeGroupId(), parentFolderId,
				name, folder.getDescription(), serviceContext);
		}

		Element folderElement = portletDataContext.getImportDataElement(folder);

		importFolderFileEntryTypes(
			portletDataContext, folderElement, folder, importedFolder,
			serviceContext);

		portletDataContext.importClassedModel(
			folder, importedFolder, DLFolder.class);

		folderIds.put(folder.getFolderId(), importedFolder.getFolderId());
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Folder existingFolder = FolderUtil.fetchByUUID_R(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) ||
			!(existingFolder.getModel() instanceof DLFolder)) {

			return;
		}

		DLFolder dlFolder = (DLFolder)existingFolder.getModel();

		if (!dlFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFolder.class.getName());

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFolder.getFolderId());
		}
	}

	protected void exportFolderFileEntryTypes(
			PortletDataContext portletDataContext, Element folderElement,
			Folder folder)
		throws Exception {

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(
				new long[] {
					portletDataContext.getCompanyGroupId(),
					portletDataContext.getScopeGroupId()
				},
				folder.getFolderId(), false);

		long defaultFileEntryTypeId =
			DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
				folder.getFolderId());

		String defaultFileEntryTypeUuid = StringPool.BLANK;

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (dlFileEntryType.getFileEntryTypeId() ==
					DLFileEntryTypeConstants.
						FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

				folderElement.addAttribute("basic-document", "true");

				continue;
			}

			if (defaultFileEntryTypeId ==
					dlFileEntryType.getFileEntryTypeId()) {

				defaultFileEntryTypeUuid = dlFileEntryType.getUuid();
			}

			if (dlFileEntryType.isExportable()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, folder, Folder.class, dlFileEntryType,
					DLFileEntryType.class,
					PortletDataContext.REFERENCE_TYPE_STRONG);
			}
		}

		folderElement.addAttribute(
			"defaultFileEntryTypeUuid", defaultFileEntryTypeUuid);
	}

	/**
	 * @see com.liferay.portal.lar.PortletImporter#getAssetCategoryName(String,
	 *      long, long, String, long, int)
	 * @see com.liferay.portal.lar.PortletImporter#getAssetVocabularyName(
	 *      String, long, String, int)
	 */
	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		Folder folder = FolderUtil.fetchByR_P_N(groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	protected void importFolderFileEntryTypes(
			PortletDataContext portletDataContext, Element folderElement,
			Folder folder, Folder importedFolder, ServiceContext serviceContext)
		throws Exception {

		List<Long> currentFolderFileEntryTypeIds = new ArrayList<Long>();

		String defaultFileEntryTypeUuid = GetterUtil.getString(
			folderElement.attributeValue("defaultFileEntryTypeUuid"));

		long defaultFileEntryTypeId = 0;

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				folder, DLFileEntryType.class);

		for (Element referenceElement : referenceElements) {
			long referenceDlFileEntryTypeId = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));
			String referenceDlFileEntryTypeUuid =
				referenceElement.attributeValue("uuid");

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, folder, DLFileEntryType.class,
				referenceDlFileEntryTypeId);

			Map<Long, Long> dlFileEntryTypeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntryType.class);

			long dlFileEntryTypeId = MapUtil.getLong(
				dlFileEntryTypeIds, referenceDlFileEntryTypeId,
				referenceDlFileEntryTypeId);

			DLFileEntryType existingDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
					dlFileEntryTypeId);

			if (existingDLFileEntryType == null) {
				continue;
			}

			currentFolderFileEntryTypeIds.add(
				existingDLFileEntryType.getFileEntryTypeId());

			if (defaultFileEntryTypeUuid.equals(referenceDlFileEntryTypeUuid)) {
				defaultFileEntryTypeId =
					existingDLFileEntryType.getFileEntryTypeId();
			}
		}

		if (GetterUtil.getBoolean(
				folderElement.attributeValue("basic-document"))) {

			currentFolderFileEntryTypeIds.add(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		}

		if (!currentFolderFileEntryTypeIds.isEmpty()) {
			DLFolder dlFolder = (DLFolder)importedFolder.getModel();

			dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
			dlFolder.setOverrideFileEntryTypes(true);

			DLFolderLocalServiceUtil.updateDLFolder(dlFolder);

			DLFileEntryTypeLocalServiceUtil.updateFolderFileEntryTypes(
				dlFolder, currentFolderFileEntryTypeIds, defaultFileEntryTypeId,
				serviceContext);
		}
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, Folder folder)
		throws PortletDataException {

		if ((folder.getGroupId() != portletDataContext.getGroupId()) &&
			(folder.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.INVALID_GROUP);

			pde.setStagedModel(folder);

			throw pde;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFolder.class.getName());

		if (trashHandler != null) {
			try {
				if (trashHandler.isInTrash(folder.getFolderId()) ||
					trashHandler.isInTrashContainer(folder.getFolderId())) {

					throw new PortletDataException(
						PortletDataException.STATUS_IN_TRASH);
				}
			}
			catch (PortletDataException pde) {
				throw pde;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to check trash status for " +
							DLFolder.class.getName());
				}
			}
		}
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		DLFolder dlFolder =
			DLFolderLocalServiceUtil.fetchDLFolderByUuidAndGroupId(
				uuid, groupId);

		if (dlFolder == null) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FolderStagedModelDataHandler.class);

}