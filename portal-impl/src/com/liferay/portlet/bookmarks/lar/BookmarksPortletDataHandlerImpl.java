/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class BookmarksPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndEntries
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "bookmarks", true, _metadataControls)
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndEntries
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "bookmarks", true, _metadataControls)
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				BookmarksPortletDataHandlerImpl.class, "deleteData")) {

			BookmarksFolderLocalServiceUtil.deleteFolders(
				portletDataContext.getScopeGroupId());

			BookmarksEntryLocalServiceUtil.deleteEntries(
				portletDataContext.getScopeGroupId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.bookmarks",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("bookmarks-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element foldersElement = rootElement.addElement("folders");
		Element entriesElement = rootElement.addElement("entries");

		List<BookmarksFolder> folders = BookmarksFolderUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (BookmarksFolder folder : folders) {
			exportFolder(
				portletDataContext, foldersElement, entriesElement, folder);
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			portletDataContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksEntry entry : entries) {
			exportEntry(portletDataContext, null, entriesElement, entry);
		}

		return document.formattedString();
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

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		for (Element folderElement : foldersElement.elements("folder")) {
			String path = folderElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksFolder folder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, folder);
		}

		Element entriesElement = rootElement.element("entries");

		for (Element entryElement : entriesElement.elements("entry")) {
			String path = entryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksEntry entry =
				(BookmarksEntry)portletDataContext.getZipEntryAsObject(path);

			importEntry(portletDataContext, entryElement, entry);
		}

		return null;
	}

	protected void exportEntry(
			PortletDataContext portletDataContext, Element foldersElement,
			Element entriesElement, BookmarksEntry entry)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, foldersElement, entry.getFolderId());
		}

		String path = getEntryPath(portletDataContext, entry);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element entryElement = entriesElement.addElement("entry");

			portletDataContext.addClassedModel(
				entryElement, path, entry, _NAMESPACE);
		}
	}

	protected void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element entriesElement, BookmarksFolder folder)
		throws Exception {

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				portletDataContext, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(portletDataContext, folder);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				portletDataContext.addClassedModel(
					folderElement, path, folder, _NAMESPACE);
			}
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksEntry entry : entries) {
			exportEntry(
				portletDataContext, foldersElement, entriesElement, entry);
		}
	}

	protected void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			portletDataContext.addClassedModel(
				folderElement, path, folder, _NAMESPACE);
		}
	}

	protected String getEntryPath(
		PortletDataContext portletDataContext, BookmarksEntry entry) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getFolderPath(
		PortletDataContext portletDataContext, BookmarksFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEntry(
			PortletDataContext portletDataContext, Element entryElement,
			BookmarksEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, entry.getFolderId(), entry.getFolderId());

		if ((folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == entry.getFolderId())) {

			String path = getImportFolderPath(portletDataContext, folderId);

			BookmarksFolder folder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, folder);

			folderId = MapUtil.getLong(
				folderPKs, entry.getFolderId(), entry.getFolderId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entryElement, entry, _NAMESPACE);

		BookmarksEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = BookmarksEntryUtil.fetchByUUID_G(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, portletDataContext.getScopeGroupId(), folderId,
				entry.getName(), entry.getUrl(), entry.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(entry, importedEntry, _NAMESPACE);
	}

	protected void importFolder(
			PortletDataContext portletDataContext, String folderPath,
			BookmarksFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		if ((parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			BookmarksFolder parentFolder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folderPath, folder, _NAMESPACE);

		BookmarksFolder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksFolder existingFolder = BookmarksFolderUtil.fetchByUUID_G(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = BookmarksFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(
			folder, importedFolder, _NAMESPACE);
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "bookmarks";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static PortletDataHandlerBoolean _foldersAndEntries =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-entries", true, true);

	private static PortletDataHandlerControl[] _metadataControls =
		new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(_NAMESPACE, "categories"),
			new PortletDataHandlerBoolean(_NAMESPACE, "ratings"),
			new PortletDataHandlerBoolean(_NAMESPACE, "tags")
		};

}