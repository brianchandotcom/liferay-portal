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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class WikiIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.WIKI;

	public WikiIndexer() {
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

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		long[] nodeIds = searchContext.getNodeIds();

		if ((nodeIds != null) && (nodeIds.length > 0)) {
			BooleanQuery nodeIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long nodeId : nodeIds) {
				try {
					WikiNodeServiceUtil.getNode(nodeId);
				}
				catch (Exception e) {
					continue;
				}

				nodeIdsQuery.addTerm(Field.NODE_ID, nodeId);
			}

			contextQuery.add(nodeIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long groupId, long nodeId) {

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property nodeIdProperty = PropertyFactoryUtil.forName("nodeId");

		dynamicQuery.add(nodeIdProperty.eq(nodeId));

		Property headProperty = PropertyFactoryUtil.forName("head");

		dynamicQuery.add(headProperty.eq(true));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getSearchEngineId());

		if (obj instanceof Object[]) {
			Object[] array = (Object[])obj;

			long companyId = (Long)array[0];
			long nodeId = (Long)array[1];
			String title = (String)array[2];

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, nodeId, title);

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), companyId, document.get(Field.UID));

		}
		else if (obj instanceof WikiNode) {
			WikiNode node = (WikiNode)obj;

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm("nodeId", node.getNodeId());

			Hits hits = SearchEngineUtil.search(
				getSearchEngineId(), node.getCompanyId(), booleanQuery,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					getSearchEngineId(), node.getCompanyId(),
					document.get(Field.UID));
			}
		}
		else if (obj instanceof WikiPage) {
			WikiPage page = (WikiPage)obj;

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, page.getNodeId(), page.getTitle());

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), page.getCompanyId(),
				document.get(Field.UID));
		}
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		Document document = getBaseModelDocument(PORTLET_ID, page);

		document.addUID(PORTLET_ID, page.getNodeId(), page.getTitle());

		String content = HtmlUtil.extractText(
			WikiUtil.convert(page, null, null, null));

		document.addText(Field.CONTENT, content);

		document.addKeyword(Field.NODE_ID, page.getNodeId());
		document.addText(Field.TITLE, page.getTitle());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);

		String nodeId = document.get("nodeId");

		portletURL.setParameter("struts_action", "/wiki/view");
		portletURL.setParameter("nodeId", nodeId);
		portletURL.setParameter("title", summary.getTitle());

		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		if (Validator.isNotNull(page.getRedirectTitle())) {
			return;
		}

		Document document = getDocument(page);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), page.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		doReindex(page);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexNodes(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexNodes(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WikiNode.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minNodeIdProjection = ProjectionFactoryUtil.min("nodeId");
		Projection maxNodeIdProjection = ProjectionFactoryUtil.max("nodeId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minNodeIdProjection);
		projectionList.add(maxNodeIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = WikiNodeLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxNodeIds = results.get(0);

		if ((minAndMaxNodeIds[0] == null) || (minAndMaxNodeIds[1] == null)) {
			return;
		}

		long minNodeId = (Long)minAndMaxNodeIds[0];
		long maxNodeId = (Long)minAndMaxNodeIds[1];

		long startNodeId = minNodeId;
		long endNodeId = startNodeId + DEFAULT_INTERVAL;

		while (startNodeId <= maxNodeId) {
			reindexNodes(companyId, startNodeId, endNodeId);

			startNodeId = endNodeId;
			endNodeId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexNodes(
			long companyId, long startNodeId, long endNodeId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WikiNode.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("nodeId");

		dynamicQuery.add(property.ge(startNodeId));
		dynamicQuery.add(property.lt(endNodeId));

		addReindexCriteria(dynamicQuery, companyId);

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		for (WikiNode node : nodes) {
			long groupId = node.getGroupId();
			long nodeId = node.getNodeId();

			reindexPages(companyId, groupId, nodeId);
		}
	}

	protected void reindexPages(long companyId, long groupId, long nodeId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WikiPage.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minPageIdProjection = ProjectionFactoryUtil.min("pageId");
		Projection maxPageIdProjection = ProjectionFactoryUtil.max("pageId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minPageIdProjection);
		projectionList.add(maxPageIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, groupId, nodeId);

		List<Object[]> results = WikiPageLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxPageIds = results.get(0);

		if ((minAndMaxPageIds[0] == null) || (minAndMaxPageIds[1] == null)) {
			return;
		}

		long minPageId = (Long)minAndMaxPageIds[0];
		long maxPageId = (Long)minAndMaxPageIds[1];

		long startPageId = minPageId;
		long endPageId = startPageId + DEFAULT_INTERVAL;

		while (startPageId <= maxPageId) {
			reindexPages(companyId, groupId, nodeId, startPageId, endPageId);

			startPageId = endPageId;
			endPageId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexPages(
			long companyId, long groupId, long nodeId, long startPageId,
			long endPageId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WikiPage.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("pageId");

		dynamicQuery.add(property.ge(startPageId));
		dynamicQuery.add(property.lt(endPageId));

		addReindexCriteria(dynamicQuery, groupId, nodeId);

		List<WikiPage> pages = WikiPageLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (pages.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(pages.size());

		for (WikiPage page : pages) {
			Document document = getDocument(page);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}