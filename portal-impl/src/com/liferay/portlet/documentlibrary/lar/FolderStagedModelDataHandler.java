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

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeUtil;

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
	public String getClassName(StagedModel stagedModel) {
		return Folder.class.getName();
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

		String folderPath = ExportImportPathUtil.getModelPath(
			folder.getGroupId(), Folder.class.getName(), folder.getFolderId());

		if (folder.isMountPoint()) {
			Repository repository = RepositoryUtil.findByPrimaryKey(
				folder.getRepositoryId());

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, repository);

			portletDataContext.addReferenceElement(
				folder, folderElement, repository, false);

			portletDataContext.addClassedModel(
				folderElement, folderPath, folder,
				DLPortletDataHandler.NAMESPACE);

			return;
		}
		else if (!folder.isDefaultRepository()) {

			// No need to export non-Liferay repository items since they would
			// be exported as part of repository export

			return;
		}

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, folder.getParentFolder());
		}

		exportFolderFileEntryTypes(portletDataContext, folder, folderElement);

		portletDataContext.addClassedModel(
			folderElement, folderPath, folder, DLPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		String path = ExportImportPathUtil.getModelPath(
			portletDataContext, Folder.class.getName(), folder.getFolderId());

		Element folderElement = portletDataContext.getImportDataElement(
			Folder.class.getSimpleName(), "path", path);

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				folderElement, Repository.class);

		for (Element referenceDataElement : referenceDataElements) {
			String referencePath = referenceDataElement.attributeValue("path");

			StagedModel referenceStagedModel =
				(StagedModel)portletDataContext.getZipEntryAsObject(
					referencePath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceStagedModel);

			return;
		}

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			String parentFolderPath = ExportImportPathUtil.getModelPath(
				portletDataContext, Folder.class.getName(),
				folder.getParentFolderId());

			Folder parentFolder =
				(Folder)portletDataContext.getZipEntryAsObject(
					parentFolderPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, parentFolder);
		}

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, DLPortletDataHandler.NAMESPACE);

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

		importFolderFileEntryTypes(
			portletDataContext, folderElement, folder, serviceContext);

		portletDataContext.importClassedModel(
			folder, importedFolder, DLPortletDataHandler.NAMESPACE);

		folderIds.put(folder.getFolderId(), importedFolder.getFolderId());
	}

	protected void exportFolderFileEntryTypes(
			PortletDataContext portletDataContext, Folder folder,
			Element folderElement)
		throws Exception {

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(
				new long[]{portletDataContext.getScopeGroupId()},
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
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, dlFileEntryType);

				portletDataContext.addReferenceElement(
					folder, folderElement, dlFileEntryType, false);
			}
		}

		folderElement.addAttribute(
			"defaultFileEntryTypeUuid", defaultFileEntryTypeUuid);
	}

	/**
	 * @see com.liferay.portal.lar.PortletImporter#getAssetCategoryName(String,
	 *      long, long, String, int)
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
			Folder folder, ServiceContext serviceContext)
		throws Exception {

		List<Long> fileEntryTypeIds = new ArrayList<Long>();

		String defaultFileEntryTypeUuid = GetterUtil.getString(
			folderElement.attributeValue("defaultFileEntryTypeUuid"));

		long defaultFileEntryTypeId = 0;

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				folderElement, DLFileEntryType.class);

		for (Element referenceDataElement : referenceDataElements) {
			String referencePath = referenceDataElement.attributeValue("path");

			DLFileEntryType referenceDLFileEntryType =
				(DLFileEntryType)portletDataContext.getZipEntryAsObject(
					referencePath);

			String fileEntryTypeUuid = referenceDLFileEntryType.getUuid();

			DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
				fileEntryTypeUuid, portletDataContext.getScopeGroupId());

			if (dlFileEntryType == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
					fileEntryTypeUuid, companyGroup.getGroupId());
			}

			if (dlFileEntryType == null) {
				continue;
			}

			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());

			if (defaultFileEntryTypeUuid.equals(dlFileEntryType.getUuid())) {
				defaultFileEntryTypeId = dlFileEntryType.getFileEntryTypeId();
			}
		}

		if (GetterUtil.getBoolean(
				folderElement.attributeValue("basic-document"))) {

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(0);

			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
		}

		if (!fileEntryTypeIds.isEmpty()) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
			dlFolder.setOverrideFileEntryTypes(true);

			DLFolderLocalServiceUtil.updateDLFolder(dlFolder);

			DLFileEntryTypeLocalServiceUtil.updateFolderFileEntryTypes(
				dlFolder, fileEntryTypeIds, defaultFileEntryTypeId,
				serviceContext);
		}
	}

}