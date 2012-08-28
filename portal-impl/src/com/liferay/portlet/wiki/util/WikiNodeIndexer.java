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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class WikiNodeIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {WikiNode.class.getName()};

	public static final String PORTLET_ID = PortletKeys.WIKI;

	public WikiNodeIndexer() {
		setFilterSearch(false);
		setPermissionAware(false);
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

		WikiNode wikiNode = WikiNodeLocalServiceUtil.getNode(entryClassPK);

		return WikiNodePermission.contains(
			permissionChecker, wikiNode, ActionKeys.VIEW);
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_IN_TRASH));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		WikiNode wikiNode = (WikiNode)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, wikiNode.getNodeId(), wikiNode.getName());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), wikiNode.getCompanyId(),
			document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		WikiNode wikiNode = (WikiNode)obj;

		Document document = getBaseModelDocument(PORTLET_ID, wikiNode);

		document.addUID(PORTLET_ID, wikiNode.getNodeId(), wikiNode.getName());

		document.addText(Field.TITLE, wikiNode.getName());

		document.addText(Field.DESCRIPTION, wikiNode.getDescription());

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		WikiNode wikiNode = (WikiNode)obj;

		Document document = getDocument(obj);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), wikiNode.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiNode wikiNode = WikiNodeLocalServiceUtil.getNode(classPK);

		doReindex(wikiNode);
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
			WikiNode.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minEntryIdProjection = ProjectionFactoryUtil.min("nodeId");
		Projection maxEntryIdProjection = ProjectionFactoryUtil.max("nodeId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minEntryIdProjection);
		projectionList.add(maxEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = WikiNodeLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxThreadIds = results.get(0);

		if ((minAndMaxThreadIds[0] == null) ||
				(minAndMaxThreadIds[1] == null)) {

			return;
		}

		long minThreadId = (Long)minAndMaxThreadIds[0];
		long maxThreadId = (Long)minAndMaxThreadIds[1];

		long startThreadId = minThreadId;
		long endThreadId = startThreadId + DEFAULT_INTERVAL;

		while (startThreadId <= maxThreadId) {
			reindexEntries(companyId, startThreadId, endThreadId);

			startThreadId = endThreadId;
			endThreadId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexEntries(
			long companyId, long startThreadId, long endThreadId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WikiNode.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("nodeId");

		dynamicQuery.add(property.ge(startThreadId));
		dynamicQuery.add(property.lt(endThreadId));

		addReindexCriteria(dynamicQuery, companyId);

		List<WikiNode> wikiNodes = WikiNodeLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (wikiNodes.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			wikiNodes.size());

		for (WikiNode wikiNode : wikiNodes) {
			Document document = getDocument(wikiNode);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}