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

package com.liferay.bookmarks.indexer;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BookmarksEntryIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	public static final String PORTLET_ID = BookmarksPortletKeys.BOOKMARKS;

	public BookmarksEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.TITLE, Field.UID, Field.URL);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return BookmarksEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addStatus(contextQuery, searchContext);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		deleteDocument(entry.getCompanyId(), entry.getEntryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = getBaseModelDocument(PORTLET_ID, entry);

		document.addText(Field.DESCRIPTION, entry.getDescription());
		document.addKeyword(Field.FOLDER_ID, entry.getFolderId());
		document.addText(Field.TITLE, entry.getName());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(entry.getTreePath(), CharPool.SLASH));
		document.addText(Field.URL, entry.getUrl());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/bookmarks/view_entry");
		portletURL.setParameter("entryId", entryId);

		Summary summary = createSummary(document, Field.TITLE, Field.URL);

		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), entry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(
			long companyId, final long groupId, final long folderId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			BookmarksEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
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
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					BookmarksEntry entry = (BookmarksEntry)object;

					Document document = getDocument(entry);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	protected void reindexFolders(final long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			BookmarksFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					BookmarksFolder folder = (BookmarksFolder)object;

					long groupId = folder.getGroupId();
					long folderId = folder.getFolderId();

					reindexEntries(companyId, groupId, folderId);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					Group group = (Group)object;

					long groupId = group.getGroupId();
					long folderId =
						BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					reindexEntries(companyId, groupId, folderId);
				}

			});

		actionableDynamicQuery.performActions();
	}

}