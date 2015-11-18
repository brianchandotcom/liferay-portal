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

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.service.permission.BookmarksEntryPermissionChecker;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
@Component(immediate = true, service = Indexer.class)
public class BookmarksEntryIndexer extends BaseIndexer<BookmarksEntry> {

	public static final String CLASS_NAME = BookmarksEntry.class.getName();

	public BookmarksEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID, Field.URL);
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

		return BookmarksEntryPermissionChecker.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);
	}

	@Override
	protected void doDelete(BookmarksEntry bookmarksEntry) throws Exception {
		deleteDocument(
			bookmarksEntry.getCompanyId(), bookmarksEntry.getEntryId());
	}

	@Override
	protected Document doGetDocument(BookmarksEntry bookmarksEntry)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, bookmarksEntry);

		document.addText(Field.DESCRIPTION, bookmarksEntry.getDescription());
		document.addKeyword(Field.FOLDER_ID, bookmarksEntry.getFolderId());
		document.addText(Field.TITLE, bookmarksEntry.getName());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(bookmarksEntry.getTreePath(), CharPool.SLASH));
		document.addText(Field.URL, bookmarksEntry.getUrl());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.URL);

		return summary;
	}

	@Override
	protected void doReindex(BookmarksEntry bookmarksEntry) throws Exception {
		Document document = getDocument(bookmarksEntry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), bookmarksEntry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	protected void reindexEntries(
			long companyId, final long groupId, final long folderId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_bookmarksEntryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property folderIdProperty = PropertyFactoryUtil.forName(
						"folderId");

					dynamicQuery.add(folderIdProperty.eq(folderId));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					Integer[] statuses = {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_IN_TRASH
					};

					dynamicQuery.add(statusProperty.in(statuses));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BookmarksEntry>() {

				@Override
				public void performAction(BookmarksEntry entry) {
					try {
						Document document = getDocument(entry);

						indexableActionableDynamicQuery.addDocument(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index bookmarks entry " +
									entry.getEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexFolders(final long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			_bookmarksFolderLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BookmarksFolder>() {

				@Override
				public void performAction(BookmarksFolder folder)
					throws PortalException {

					long groupId = folder.getGroupId();
					long folderId = folder.getFolderId();

					reindexEntries(companyId, groupId, folderId);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					long groupId = group.getGroupId();
					long folderId =
						BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					reindexEntries(companyId, groupId, folderId);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksEntryIndexer.class);

	private BookmarksEntryLocalService _bookmarksEntryLocalService;
	private BookmarksFolderLocalService _bookmarksFolderLocalService;
	private GroupLocalService _groupLocalService;

}