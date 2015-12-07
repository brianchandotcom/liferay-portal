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

package com.liferay.bookmarks.search;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = Indexer.class)
public class BookmarksFolderIndexer extends BaseIndexer<BookmarksFolder> {

	public static final String CLASS_NAME = BookmarksFolder.class.getName();

	public BookmarksFolderIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		BookmarksFolder folder = _bookmarksFolderLocalService.getFolder(
			entryClassPK);

		return BookmarksFolderPermissionChecker.contains(
			permissionChecker, folder, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);
	}

	@Override
	protected void doDelete(BookmarksFolder bookmarksFolder) throws Exception {
		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, bookmarksFolder.getFolderId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), bookmarksFolder.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(BookmarksFolder bookmarksFolder)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing folder " + bookmarksFolder);
		}

		Document document = getBaseModelDocument(CLASS_NAME, bookmarksFolder);

		document.addText(Field.DESCRIPTION, bookmarksFolder.getDescription());
		document.addKeyword(
			Field.FOLDER_ID, bookmarksFolder.getParentFolderId());
		document.addText(Field.TITLE, bookmarksFolder.getName());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(bookmarksFolder.getTreePath(), CharPool.SLASH));

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + bookmarksFolder + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(BookmarksFolder bookmarksFolder) throws Exception {
		Document document = getDocument(bookmarksFolder);

		if (!bookmarksFolder.isApproved() && !bookmarksFolder.isInTrash()) {
			return;
		}

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), bookmarksFolder.getCompanyId(), document,
				isCommitImmediately());
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), bookmarksFolder.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		BookmarksFolder folder = _bookmarksFolderLocalService.getFolder(
			classPK);

		doReindex(folder);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
	}

	protected void reindexFolders(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_bookmarksFolderLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BookmarksFolder>() {

				@Override
				public void performAction(BookmarksFolder folder) {
					try {
						Document document = getDocument(folder);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index bookmarks folder " +
									folder.getFolderId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksFolderIndexer.class);

	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;

}