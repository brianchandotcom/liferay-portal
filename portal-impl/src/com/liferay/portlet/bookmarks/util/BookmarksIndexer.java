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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BookmarksIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.BOOKMARKS;

	public BookmarksIndexer() {
		setPermissionAware(true);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds != null) && (folderIds.length > 0)) {
			if (folderIds[0] ==
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return;
			}

			BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long folderId : folderIds) {
				try {
					BookmarksFolderServiceUtil.getFolder(folderId);
				}
				catch (Exception e) {
					continue;
				}

				folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
			}

			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long groupId, long folderId) {

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property folderIdProperty = PropertyFactoryUtil.forName("folderId");

		dynamicQuery.add(folderIdProperty.eq(folderId));
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
		document.addText(Field.URL, entry.getUrl());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

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
			getSearchEngineId(), entry.getCompanyId(), document);
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

	protected void reindexEntries(long companyId, long groupId, long folderId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BookmarksEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minEntryIdProjection = ProjectionFactoryUtil.min("entryId");
		Projection maxEntryIdProjection = ProjectionFactoryUtil.max("entryId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minEntryIdProjection);
		projectionList.add(maxEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, groupId, folderId);

		List<Object[]> results = BookmarksEntryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxEntryIds = results.get(0);

		if ((minAndMaxEntryIds[0] == null) || (minAndMaxEntryIds[1] == null)) {
			return;
		}

		long minEntryId = (Long)minAndMaxEntryIds[0];
		long maxEntryId = (Long)minAndMaxEntryIds[1];

		long startEntryId = minEntryId;
		long endEntryId = startEntryId + DEFAULT_INTERVAL;

		while (startEntryId <= maxEntryId) {
			reindexEntries(
				companyId, groupId, folderId, startEntryId, endEntryId);

			startEntryId = endEntryId;
			endEntryId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexEntries(
			long companyId, long groupId, long folderId, long startEntryId,
			long endEntryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BookmarksEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("entryId");

		dynamicQuery.add(property.ge(startEntryId));
		dynamicQuery.add(property.lt(endEntryId));

		addReindexCriteria(dynamicQuery, groupId, folderId);

		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.dynamicQuery(dynamicQuery);

		if (entries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			entries.size());

		for (BookmarksEntry entry : entries) {
			Document document = getDocument(entry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexFolders(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BookmarksFolder.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minFolderIdProjection = ProjectionFactoryUtil.min(
			"folderId");
		Projection maxFolderIdProjection = ProjectionFactoryUtil.max(
			"folderId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minFolderIdProjection);
		projectionList.add(maxFolderIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = BookmarksFolderLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxFolderIds = results.get(0);

		if ((minAndMaxFolderIds[0] == null) ||
			(minAndMaxFolderIds[1] == null)) {

			return;
		}

		long minFolderId = (Long)minAndMaxFolderIds[0];
		long maxFolderId = (Long)minAndMaxFolderIds[1];

		long startFolderId = minFolderId;
		long endFolderId = startFolderId + DEFAULT_INTERVAL;

		while (startFolderId <= maxFolderId) {
			reindexFolders(companyId, startFolderId, endFolderId);

			startFolderId = endFolderId;
			endFolderId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexFolders(
			long companyId, long startFolderId, long endFolderId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BookmarksFolder.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("folderId");

		dynamicQuery.add(property.ge(startFolderId));
		dynamicQuery.add(property.lt(endFolderId));

		addReindexCriteria(dynamicQuery, companyId);

		List<BookmarksFolder> folders =
			BookmarksFolderLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (BookmarksFolder folder : folders) {
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			reindexEntries(companyId, groupId, folderId);
		}
	}

	protected void reindexRoot(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minGroupIdProjection = ProjectionFactoryUtil.min("groupId");
		Projection maxGroupIdProjection = ProjectionFactoryUtil.max("groupId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minGroupIdProjection);
		projectionList.add(maxGroupIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = GroupLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxGroupIds = results.get(0);

		if ((minAndMaxGroupIds[0] == null) || (minAndMaxGroupIds[1] == null)) {
			return;
		}

		long minGroupId = (Long)minAndMaxGroupIds[0];
		long maxGroupId = (Long)minAndMaxGroupIds[1];

		long startGroupId = minGroupId;
		long endGroupId = startGroupId + DEFAULT_INTERVAL;

		while (startGroupId <= maxGroupId) {
			reindexRoot(companyId, startGroupId, endGroupId);

			startGroupId = endGroupId;
			endGroupId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexRoot(
			long companyId, long startGroupId, long endGroupId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(property.ge(startGroupId));
		dynamicQuery.add(property.lt(endGroupId));

		addReindexCriteria(dynamicQuery, companyId);

		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			reindexEntries(companyId, groupId, folderId);
		}
	}

}