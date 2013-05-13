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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.persistence.WikiNodeActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
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

		WikiNode node = WikiNodeLocalServiceUtil.getNode(entryClassPK);

		return WikiNodePermission.contains(
			permissionChecker, node, ActionKeys.VIEW);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		WikiNode node = (WikiNode)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, node.getNodeId(), node.getName());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), node.getCompanyId(), document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		WikiNode node = (WikiNode)obj;

		Document document = getBaseModelDocument(PORTLET_ID, node);

		document.addUID(PORTLET_ID, node.getNodeId(), node.getName());

		document.addText(Field.DESCRIPTION, node.getDescription());
		document.addText(Field.TITLE, node.getName());

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
		WikiNode node = (WikiNode)obj;

		Document document = getDocument(obj);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), node.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		doReindex(node);
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

	protected void reindexEntries(long companyId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new WikiNodeActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property property = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					property.eq(WorkflowConstants.STATUS_IN_TRASH));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				WikiNode node = (WikiNode)object;

				Document document = getDocument(node);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}