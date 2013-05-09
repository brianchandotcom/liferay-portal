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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryActionableDynamicQuery;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderActionableDynamicQuery;

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
		setAlwaysExportable(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "folders", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "entries", true, true));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "bookmarks", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
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

		portletDataContext.addPermissions(
			"com.liferay.portlet.bookmarks",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		initializeActionableDynamicQueries(portletDataContext);

		_folderActionableDynamicQuery.performActions();

		_entryActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.bookmarks",
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
	protected void doPrepareData(PortletDataContext portletDataContext)
		throws Exception {

		initializeActionableDynamicQueries(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		long folderCount = _folderActionableDynamicQuery.performCount();

		manifestSummary.addModelCount(BookmarksFolder.class, folderCount);

		long entryCount = _entryActionableDynamicQuery.performCount();

		manifestSummary.addModelCount(BookmarksEntry.class, entryCount);
	}

	private void initializeActionableDynamicQueries(
			final PortletDataContext portletDataContext)
		throws SystemException {

		_folderActionableDynamicQuery =
			new BookmarksFolderActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				BookmarksFolder folder = (BookmarksFolder)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, folder);
			}

		};

		_folderActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		_entryActionableDynamicQuery =
			new BookmarksEntryActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				BookmarksEntry entry = (BookmarksEntry)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, entry);
			}

		};

		_entryActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());
	}

	private ActionableDynamicQuery _entryActionableDynamicQuery;
	private ActionableDynamicQuery _folderActionableDynamicQuery;

}