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

package com.liferay.journal.lar;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.persistence.JournalFolderUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class JournalFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFolder> {

	public static final String[] CLASS_NAMES = {JournalFolder.class.getName()};

	@Override
	public void deleteStagedModel(JournalFolder folder) throws PortalException {
		_journalFolderLocalService.deleteFolder(folder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFolder folder = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (folder != null) {
			deleteStagedModel(folder);
		}
	}

	@Override
	public JournalFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFolderLocalService.fetchJournalFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFolderLocalService.getJournalFoldersByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFolder>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(JournalFolder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		if (folder.getParentFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element folderElement = portletDataContext.getExportDataElement(folder);

		exportFolderDDMStructures(portletDataContext, folder);

		portletDataContext.addClassedModel(
			folderElement, ExportImportPathUtil.getModelPath(folder), folder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder);

		JournalFolder importedFolder = null;

		long groupId = portletDataContext.getScopeGroupId();

		if (portletDataContext.isDataStrategyMirror()) {
			JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
				folder.getUuid(), groupId);

			if (existingFolder == null) {
				String name = getFolderName(
					null, groupId, parentFolderId, folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = _journalFolderLocalService.addFolder(
					userId, groupId, parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), groupId, parentFolderId, folder.getName(),
					2);

				importedFolder = _journalFolderLocalService.updateFolder(
					userId, serviceContext.getScopeGroupId(),
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), false, serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, groupId, parentFolderId, folder.getName(), 2);

			importedFolder = _journalFolderLocalService.addFolder(
				userId, groupId, parentFolderId, name, folder.getDescription(),
				serviceContext);
		}

		importFolderDDMStructures(portletDataContext, folder, importedFolder);

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFolder.getFolderId());
		}
	}

	protected void exportFolderDDMStructures(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		List<DDMStructure> ddmStructures =
			_journalFolderLocalService.getDDMStructures(
				new long[] {
					portletDataContext.getCompanyGroupId(),
					portletDataContext.getScopeGroupId()
				},
				folder.getFolderId(),
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW
			);

		for (DDMStructure ddmStructure : ddmStructures) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
	}

	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		JournalFolder folder = JournalFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	protected void importFolderDDMStructures(
			PortletDataContext portletDataContext, JournalFolder folder,
			JournalFolder importedFolder)
		throws Exception {

		List<Long> currentFolderDDMStructureIds = new ArrayList<>();

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(folder, DDMStructure.class);

		for (Element referenceElement : referenceElements) {
			long referenceDDMStructureId = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Map<Long, Long> ddmStructureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			long ddmStructureId = MapUtil.getLong(
				ddmStructureIds, referenceDDMStructureId,
				referenceDDMStructureId);

			DDMStructure existingDDMStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (existingDDMStructure == null) {
				continue;
			}

			currentFolderDDMStructureIds.add(
				existingDDMStructure.getStructureId());
		}

		if (!currentFolderDDMStructureIds.isEmpty()) {
			importedFolder.setRestrictionType(
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

			_journalFolderLocalService.updateJournalFolder(importedFolder);

			_journalFolderLocalService.updateFolderDDMStructures(
				importedFolder,
				ArrayUtil.toLongArray(currentFolderDDMStructureIds));
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile JournalFolderLocalService _journalFolderLocalService;

}