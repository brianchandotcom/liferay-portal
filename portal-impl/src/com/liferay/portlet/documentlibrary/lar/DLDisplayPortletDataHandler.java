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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryActionableDynamicQuery;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderActionableDynamicQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class DLDisplayPortletDataHandler extends DLPortletDataHandler {

	public DLDisplayPortletDataHandler() {
		setAlwaysExportable(false);
		setDataLevel(DataLevel.PORTLET_INSTANCE);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("enable-comment-ratings", StringPool.BLANK);
		portletPreferences.setValue("fileEntriesPerPage", StringPool.BLANK);
		portletPreferences.setValue("fileEntryColumns", StringPool.BLANK);
		portletPreferences.setValue("folderColumns", StringPool.BLANK);
		portletPreferences.setValue("foldersPerPage", StringPool.BLANK);
		portletPreferences.setValue("rootFolderId", StringPool.BLANK);
		portletPreferences.setValue("showFoldersSearch", StringPool.BLANK);
		portletPreferences.setValue("showSubfolders", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			DLPermission.RESOURCE_NAME, portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			ActionableDynamicQuery folderActionableDynamicQuery =
				new DLFolderActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException, SystemException {

					DLFolder dlFolder = (DLFolder)object;

					Folder folder = DLAppLocalServiceUtil.getFolder(
						dlFolder.getFolderId());

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, folder);
				}

			};

			folderActionableDynamicQuery.setGroupId(
				portletDataContext.getScopeGroupId());

			folderActionableDynamicQuery.performActions();

			ActionableDynamicQuery fileEntryActionableDynamicQuery =
				new DLFileEntryActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException, SystemException {

					DLFileEntry dlFileEntry = (DLFileEntry)object;

					FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
						dlFileEntry.getFileEntryId());

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, fileEntry);
				}

			};

			fileEntryActionableDynamicQuery.setGroupId(
				portletDataContext.getScopeGroupId());

			fileEntryActionableDynamicQuery.performActions();

			return getExportDataRootElementString(rootElement);
		}

		Folder rootFolder = DLAppLocalServiceUtil.getFolder(rootFolderId);

		rootElement.addAttribute(
			"root-folder-id", String.valueOf(rootFolder.getFolderId()));
		rootElement.addAttribute(
			"default-repository",
			String.valueOf(rootFolder.isDefaultRepository()));

		final List<Long> folderIds = new ArrayList<Long>();

		DLFolderLocalServiceUtil.getSubfolderIds(
			folderIds, portletDataContext.getScopeGroupId(), rootFolderId);

		folderIds.add(rootFolderId);

		for (long folderId : folderIds) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, folder);
		}

		ActionableDynamicQuery fileEntryActionableDynamicQuery =
			new DLFileEntryActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");

				Property property = PropertyFactoryUtil.forName("folderId");

				Long[] folderIdsArray = folderIds.toArray(
					new Long[folderIds.size()]);

				dynamicQuery.add(property.in(folderIdsArray));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				DLFileEntry dlFileEntry = (DLFileEntry)object;

				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
					dlFileEntry.getFileEntryId());

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);
			}

		};

		fileEntryActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		fileEntryActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			DLPermission.RESOURCE_NAME, portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element fileEntryTypesElement =
			portletDataContext.getImportDataGroupElement(DLFileEntryType.class);

		List<Element> fileEntryTypeElements = fileEntryTypesElement.elements();

		for (Element fileEntryTypeElement : fileEntryTypeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fileEntryTypeElement);
		}

		Element foldersElement = portletDataContext.getImportDataGroupElement(
			Folder.class);

		List<Element> folderElements = foldersElement.elements();

		for (Element folderElement : folderElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folderElement);
		}

		Element fileEntriesElement =
			portletDataContext.getImportDataGroupElement(FileEntry.class);

		List<Element> fileEntryElements = fileEntriesElement.elements();

		for (Element fileEntryElement : fileEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fileEntryElement);
		}

		if (portletDataContext.getBooleanParameter(
				DLPortletDataHandler.NAMESPACE, "shortcuts")) {

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

		if (portletDataContext.getBooleanParameter(
				DLPortletDataHandler.NAMESPACE, "ranks")) {

			Element fileRanksElement =
				portletDataContext.getImportDataGroupElement(DLFileRank.class);

			List<Element> fileRankElements = fileRanksElement.elements();

			for (Element fileRankElement : fileRankElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileRankElement);
			}
		}

		Element rootElement = portletDataContext.getImportDataRootElement();

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));
		boolean defaultRepository = GetterUtil.getBoolean(
			rootElement.attributeValue("default-repository"), true);

		if (rootFolderId > 0) {
			Map<Long, Long> folderIds = null;

			if (defaultRepository) {
				folderIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						Folder.class);
			}
			else {
				folderIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						RepositoryEntry.class);
			}

			rootFolderId = MapUtil.getLong(
				folderIds, rootFolderId, rootFolderId);

			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}

		return portletPreferences;
	}

}