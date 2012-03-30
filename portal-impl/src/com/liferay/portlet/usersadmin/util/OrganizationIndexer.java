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

package com.liferay.portlet.usersadmin.util;

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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
public class OrganizationIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {Organization.class.getName()};

	public static final String PORTLET_ID = PortletKeys.USERS_ADMIN;

	public OrganizationIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean isIndexerEnabled() {
		return PropsValues.ORGANIZATIONS_INDEXER_ENABLED;
	}

	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		List<Organization> organizationsTree = (List<Organization>)params.get(
			"organizationsTree");

		if ((organizationsTree != null) && !organizationsTree.isEmpty()) {
			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (Organization organization : organizationsTree) {
				String treePath = organization.buildTreePath();

				booleanQuery.addTerm("treePath", treePath, true);
			}

			contextQuery.add(booleanQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "city", false);
		addSearchTerm(searchQuery, searchContext, "country", false);
		addSearchTerm(searchQuery, searchContext, "name", false);
		addSearchTerm(
			searchQuery, searchContext, "parentOrganizationId", false);
		addSearchTerm(searchQuery, searchContext, "region", false);
		addSearchTerm(searchQuery, searchContext, "street", false);
		addSearchTerm(searchQuery, searchContext, "type", false);
		addSearchTerm(searchQuery, searchContext, "zip", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		deleteDocument(
			organization.getCompanyId(), organization.getOrganizationId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		Document document = getBaseModelDocument(PORTLET_ID, organization);

		document.addKeyword(Field.COMPANY_ID, organization.getCompanyId());
		document.addText(Field.NAME, organization.getName());
		document.addKeyword(
			Field.ORGANIZATION_ID, organization.getOrganizationId());
		document.addKeyword(Field.TYPE, organization.getType());

		document.addKeyword(
			"parentOrganizationId", organization.getParentOrganizationId());

		String treePath = organization.buildTreePath();

		document.addKeyword("treePath", treePath);

		populateAddresses(
			document, organization.getAddresses(), organization.getRegionId(),
			organization.getCountryId());

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("name")) {
			return "name";
		}
		else if (orderByCol.equals("type")) {
			return "type";
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get("name");

		String content = null;

		String organizationId = document.get(Field.ORGANIZATION_ID);

		portletURL.setParameter(
			"struts_action", "/users_admin/edit_organization");
		portletURL.setParameter("organizationId", organizationId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof List<?>) {
			List<Organization> organizations = (List<Organization>)obj;

			for (Organization organization : organizations) {
				doReindex(organization);
			}
		}
		else if (obj instanceof Long) {
			long organizationId = (Long)obj;

			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			doReindex(organization);
		}
		else if (obj instanceof long[]) {
			long[] organizationIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap =
				new HashMap<Long, Collection<Document>>();

			for (long organizationId : organizationIds) {
				Organization organization =
					OrganizationLocalServiceUtil.fetchOrganization(
						organizationId);

				if (organization == null) {
					continue;
				}

				Document document = getDocument(organization);

				long companyId = organization.getCompanyId();

				Collection<Document> documents = documentsMap.get(companyId);

				if (documents == null) {
					documents = new ArrayList<Document>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(
					getSearchEngineId(), companyId, documents);
			}
		}
		else if (obj instanceof Organization) {
			Organization organization = (Organization)obj;

			Document document = getDocument(organization);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), organization.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(classPK);

		doReindex(organization);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexOrganizations(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexOrganizations(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Organization.class, PortalClassLoaderUtil.getClassLoader());

		Projection minOrganizationIdProjection = ProjectionFactoryUtil.min(
			"organizationId");
		Projection maxOrganizationIdProjection = ProjectionFactoryUtil.max(
			"organizationId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minOrganizationIdProjection);
		projectionList.add(maxOrganizationIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = OrganizationLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxOrganizationIds = results.get(0);

		if ((minAndMaxOrganizationIds[0] == null) ||
			(minAndMaxOrganizationIds[1] == null)) {

			return;
		}

		long minOrganizationId = (Long)minAndMaxOrganizationIds[0];
		long maxOrganizationId = (Long)minAndMaxOrganizationIds[1];

		long startOrganizationId = minOrganizationId;
		long endOrganizationId = startOrganizationId + DEFAULT_INTERVAL;

		while (startOrganizationId <= maxOrganizationId) {
			reindexOrganizations(
				companyId, startOrganizationId, endOrganizationId);

			startOrganizationId = endOrganizationId;
			endOrganizationId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexOrganizations(
			long companyId, long startOrganizationId, long endOrganizationId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Organization.class, PortalClassLoaderUtil.getClassLoader());

		Property property = PropertyFactoryUtil.forName("organizationId");

		dynamicQuery.add(property.ge(startOrganizationId));
		dynamicQuery.add(property.lt(endOrganizationId));

		addReindexCriteria(dynamicQuery, companyId);

		List<Organization> organizations =
			OrganizationLocalServiceUtil.dynamicQuery(dynamicQuery);

		if (organizations.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (Organization organization : organizations) {
			Document document = getDocument(organization);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	private static final boolean _PERMISSION_AWARE = true;

}