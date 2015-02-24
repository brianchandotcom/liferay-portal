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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
@OSGiBeanProperties
public class OrganizationIndexer extends BaseIndexer {

	public static final String CLASS_NAME = Organization.class.getName();

	public OrganizationIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ORGANIZATION_ID, Field.UID);
		setIndexerEnabled(PropsValues.ORGANIZATIONS_INDEXER_ENABLED);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
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

		List<Long> excludedOrganizationIds = (List<Long>)params.get(
			"excludedOrganizationIds");

		if ((excludedOrganizationIds != null) &&
			!excludedOrganizationIds.isEmpty()) {

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long excludedOrganizationId : excludedOrganizationIds) {
				booleanQuery.addTerm(
					"organizationId", String.valueOf(excludedOrganizationId));
			}

			contextQuery.add(booleanQuery, BooleanClauseOccur.MUST_NOT);
		}

		List<Organization> organizationsTree = (List<Organization>)params.get(
			"organizationsTree");

		if ((organizationsTree != null) && !organizationsTree.isEmpty()) {
			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (Organization organization : organizationsTree) {
				String treePath = organization.buildTreePath();

				booleanQuery.addTerm(Field.TREE_PATH, treePath, true);
			}

			contextQuery.add(booleanQuery, BooleanClauseOccur.MUST);
		}
		else {
			long parentOrganizationId = GetterUtil.getLong(
				searchContext.getAttribute("parentOrganizationId"));

			if (parentOrganizationId !=
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

				contextQuery.addRequiredTerm(
					"parentOrganizationId", parentOrganizationId);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "city", false);
		addSearchTerm(searchQuery, searchContext, "country", false);
		addSearchTerm(searchQuery, searchContext, "name", false);
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

	@Override
	protected void doDelete(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		deleteDocument(
			organization.getCompanyId(), organization.getOrganizationId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		Document document = getBaseModelDocument(CLASS_NAME, organization);

		document.addKeyword(Field.COMPANY_ID, organization.getCompanyId());
		document.addText(Field.NAME, organization.getName());
		document.addKeyword(
			Field.ORGANIZATION_ID, organization.getOrganizationId());
		document.addKeyword(Field.TREE_PATH, organization.buildTreePath());
		document.addKeyword(Field.TYPE, organization.getType());

		document.addKeyword(
			"parentOrganizationId", organization.getParentOrganizationId());

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
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String title = document.get("name");

		String content = null;

		return new Summary(title, content);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof Long) {
			long organizationId = (Long)obj;

			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			doReindex(organization);
		}
		else if (obj instanceof long[]) {
			long[] organizationIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap = new HashMap<>();

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
					documents = new ArrayList<>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(
					getSearchEngineId(), companyId, documents,
					isCommitImmediately());
			}
		}
		else if (obj instanceof Organization) {
			Organization organization = (Organization)obj;

			Document document = getDocument(organization);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), organization.getCompanyId(), document,
				isCommitImmediately());
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

	protected void reindexOrganizations(long companyId) throws Exception {
		final ActionableDynamicQuery actionableDynamicQuery =
			OrganizationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					Organization organization = (Organization)object;

					Document document = getDocument(organization);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}