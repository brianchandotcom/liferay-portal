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

package com.liferay.bookmarks.exportimport.portlet.preferences.processor;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.lar.BookmarksPortletDataHandler;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS},
	service = ExportImportPortletPreferencesProcessor.class
)
public class BookmarksExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return null;
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return portletPreferences;
		}

		BookmarksFolder folder =
			_bookmarksFolderLocalService.fetchBookmarksFolder(rootFolderId);

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		portletDataContext.addReferenceElement(
			portlet, portletDataContext.getExportDataRootElement(), folder,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			!portletDataContext.getBooleanParameter(
				BookmarksPortletDataHandler.NAMESPACE, "entries"));

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId <= 0) {
			return portletPreferences;
		}

		String rootFolderPath = ExportImportPathUtil.getModelPath(
			portletDataContext, BookmarksFolder.class.getName(), rootFolderId);

		BookmarksFolder folder =
			(BookmarksFolder)portletDataContext.getZipEntryAsObject(
				rootFolderPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, folder);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		rootFolderId = MapUtil.getLong(folderIds, rootFolderId, rootFolderId);

		try {
			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update preference \"rootFolderId\"", roe);
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;
	private volatile PortletLocalService _portletLocalService;

}