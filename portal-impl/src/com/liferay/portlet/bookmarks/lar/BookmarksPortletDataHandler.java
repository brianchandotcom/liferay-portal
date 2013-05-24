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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryExportActionableDynamicQuery;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class BookmarksPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "bookmarks";

	public BookmarksPortletDataHandler() {
		setExportControls(new PortletDataHandlerBoolean(NAMESPACE, "entries"));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "bookmarks", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				BookmarksPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		BookmarksFolderLocalServiceUtil.deleteFolders(
			portletDataContext.getScopeGroupId());

		BookmarksEntryLocalServiceUtil.deleteEntries(
			portletDataContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			return getExportDataRootElementString(rootElement);
		}

		portletDataContext.addPermissions(
			BookmarksPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery folderActionableDynamicQuery =
			new BookmarksFolderExportActionableDynamicQuery(portletDataContext);

		folderActionableDynamicQuery.performActions();

		ActionableDynamicQuery entryActionableDynamicQuery =
			new BookmarksEntryExportActionableDynamicQuery(portletDataContext);

		entryActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			return null;
		}

		portletDataContext.importPermissions(
			BookmarksPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element foldersElement = portletDataContext.getImportDataGroupElement(
			BookmarksFolder.class);

		List<Element> folderElements = foldersElement.elements();

		for (Element folderElement : folderElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folderElement);
		}

		Element entriesElement = portletDataContext.getImportDataGroupElement(
			BookmarksEntry.class);

		List<Element> entryElements = entriesElement.elements();

		for (Element entryElement : entryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, entryElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ActionableDynamicQuery entryExportActionableDynamicQuery =
			new BookmarksEntryExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			BookmarksEntry.class,
			entryExportActionableDynamicQuery.performCount());

		ActionableDynamicQuery folderExportActionableDynamicQuery =
			new BookmarksFolderExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			BookmarksFolder.class,
			folderExportActionableDynamicQuery.performCount());
	}

}