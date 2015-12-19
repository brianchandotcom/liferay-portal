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
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
@OSGiBeanProperties
public class OrganizationIndexer extends BaseIndexer<Organization> {

	public static final String CLASS_NAME = Organization.class.getName();

	public OrganizationIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ORGANIZATION_ID, Field.UID);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		List<Long> excludedOrganizationIds = (List<Long>)params.get(
			"excludedOrganizationIds");

		if (ListUtil.isNotEmpty(excludedOrganizationIds)) {
			TermsFilter termsFilter = new TermsFilter("organizationId");

			termsFilter.addValues(
				ArrayUtil.toStringArray(
					excludedOrganizationIds.toArray(
						new Long[excludedOrganizationIds.size()])));

			contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST_NOT);
		}

		List<Organization> organizationsTree = (List<Organization>)params.get(
			"organizationsTree");

		if (ListUtil.isNotEmpty(organizationsTree)) {
			BooleanFilter booleanFilter = new BooleanFilter();

			for (Organization organization : organizationsTree) {
				String treePath = organization.buildTreePath();

				WildcardQuery wildcardQuery = new WildcardQueryImpl(
					Field.TREE_PATH, treePath);

				booleanFilter.add(new QueryFilter(wildcardQuery));
			}

			contextBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
		}
		else {
			long parentOrganizationId = GetterUtil.getLong(
				searchContext.getAttribute("parentOrganizationId"));

			if (parentOrganizationId !=
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

				contextBooleanFilter.addRequiredTerm(
					"parentOrganizationId", parentOrganizationId);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
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
	protected void doDelete(Organization organization) throws Exception {
		deleteDocument(
			organization.getCompanyId(), organization.getOrganizationId());
	}

	@Override
	protected Document doGetDocument(Organization organization)
		throws Exception {

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
	protected void doReindex(Organization organization) throws Exception {
		Document document = getDocument(organization);

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), organization.getCompanyId(), document,
			isCommitImmediately());
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
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			OrganizationLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Organization>() {

				@Override
				public void performAction(Organization organization) {
					try {
						Document document = getDocument(organization);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index organization " +
									organization.getOrganizationId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationIndexer.class);

}