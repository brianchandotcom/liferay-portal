/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
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
		try {
			List<JournalFolder> ancestorFolders = folder.getAncestors();

			StringBundler sb = new StringBundler(
				(4 * ancestorFolders.size()) + 1);

			Collections.reverse(ancestorFolders);

			for (JournalFolder ancestorFolder : ancestorFolders) {
				sb.append(ancestorFolder.getName());
				sb.append(StringPool.SPACE);
				sb.append(StringPool.GREATER_THAN);
				sb.append(StringPool.SPACE);
			}

			sb.append(folder.getName());

			return sb.toString();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

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

		_exportFolderDDMStructures(portletDataContext, folder);

		portletDataContext.addClassedModel(
			folderElement, ExportImportPathUtil.getModelPath(folder), folder);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long folderId) {

		JournalFolder existingJournalFolder = fetchMissingReference(
			uuid, groupId);

		if (existingJournalFolder == null) {
			return;
		}

		Map<Long, Long> journalFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		journalFolderIds.put(folderId, existingJournalFolder.getFolderId());
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
				String name = _journalFolderLocalService.getUniqueFolderName(
					null, groupId, parentFolderId, folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = _journalFolderLocalService.addFolder(
					folder.getExternalReferenceCode(), userId, groupId,
					parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = _journalFolderLocalService.getUniqueFolderName(
					folder.getUuid(), groupId, parentFolderId, folder.getName(),
					2);

				importedFolder = _journalFolderLocalService.updateFolder(
					userId, serviceContext.getScopeGroupId(),
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), false, serviceContext);
			}
		}
		else {
			String name = _journalFolderLocalService.getUniqueFolderName(
				null, groupId, parentFolderId, folder.getName(), 2);

			importedFolder = _journalFolderLocalService.addFolder(
				folder.getExternalReferenceCode(), userId, groupId,
				parentFolderId, name, folder.getDescription(), serviceContext);
		}

		importedFolder.setRestrictionType(folder.getRestrictionType());

		importedFolder = _journalFolderLocalService.updateJournalFolder(
			importedFolder);

		_importFolderDDMStructures(portletDataContext, folder, importedFolder);

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalFolder.class.getName());

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				portletDataContext.getUserId(folder.getUserUuid()),
				existingFolder.getFolderId());
		}
	}

	private void _exportFolderDDMStructures(
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
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

		for (DDMStructure ddmStructure : ddmStructures) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
	}

	private void _importFolderDDMStructures(
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

			importedFolder = _journalFolderLocalService.updateJournalFolder(
				importedFolder);

			_journalFolderLocalService.updateFolderDDMStructures(
				importedFolder,
				ArrayUtil.toLongArray(currentFolderDDMStructureIds));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFolderStagedModelDataHandler.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

}