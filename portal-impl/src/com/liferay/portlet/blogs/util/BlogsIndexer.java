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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BlogsIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.BLOGS;

	public BlogsIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return BlogsEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property displayDateProperty = PropertyFactoryUtil.forName(
			"displayDate");

		dynamicQuery.add(displayDateProperty.lt(new Date()));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		Integer[] statuses = {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_IN_TRASH
		};

		dynamicQuery.add(statusProperty.in(statuses));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		deleteDocument(entry.getCompanyId(), entry.getEntryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		Document document = getBaseModelDocument(PORTLET_ID, entry);

		document.addText(
			Field.CONTENT, HtmlUtil.extractText(entry.getContent()));
		document.addText(Field.DESCRIPTION, entry.getDescription());
		document.addDate(Field.MODIFIED_DATE, entry.getDisplayDate());
		document.addText(Field.TITLE, entry.getTitle());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("entryId", entryId);

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		if (!entry.isApproved() && !entry.isInTrash()) {
			return;
		}

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), entry.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogsEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minEntryIdProjection = ProjectionFactoryUtil.min("entryId");
		Projection maxEntryIdProjection = ProjectionFactoryUtil.max("entryId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minEntryIdProjection);
		projectionList.add(maxEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = BlogsEntryLocalServiceUtil.dynamicQuery(
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
			reindexEntries(companyId, startEntryId, endEntryId);

			startEntryId = endEntryId;
			endEntryId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexEntries(
			long companyId, long startEntryId, long endEntryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogsEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("entryId");

		dynamicQuery.add(property.ge(startEntryId));
		dynamicQuery.add(property.lt(endEntryId));

		addReindexCriteria(dynamicQuery, companyId);

		List<BlogsEntry> entries = BlogsEntryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (entries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			entries.size());

		for (BlogsEntry entry : entries) {
			Document document = getDocument(entry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}