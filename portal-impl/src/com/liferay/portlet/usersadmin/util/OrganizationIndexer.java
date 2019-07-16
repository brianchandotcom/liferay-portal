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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Raymond Augé
 * @author     Zsigmond Rab
 * @author     Hugo Huijser
 * @author     Marco Leo
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
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
					excludedOrganizationIds.toArray(new Long[0])));

			contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST_NOT);
		}

		List<Organization> organizationsTree = (List<Organization>)params.get(
			"organizationsTree");

		if (organizationsTree != null) {
			BooleanFilter booleanFilter = new BooleanFilter();

			if (organizationsTree.isEmpty()) {
				TermQuery termQuery = new TermQueryImpl(
					Field.TREE_PATH, StringPool.BLANK);

				booleanFilter.add(new QueryFilter(termQuery));
			}

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

	protected String buildNameTreePath(Organization organization)
		throws PortalException {

		List<Organization> organizations = new ArrayList<>();

		while (organization != null) {
			organizations.add(organization);

			organization = OrganizationLocalServiceUtil.fetchOrganization(
				organization.getParentOrganizationId());
		}

		int size = organizations.size();

		StringBundler sb = new StringBundler(((size - 1) * 4) + 1);

		organization = organizations.get(size - 1);

		sb.append(organization.getName());

		for (int i = size - 2; i >= 0; i--) {
			organization = organizations.get(i);

			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(organization.getName());
		}

		return sb.toString();
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
		document.addTextSortable(
			"nameTreePath", buildNameTreePath(organization));
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

		return orderByCol;
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
		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), organization.getCompanyId(),
			getDocument(organization), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(OrganizationLocalServiceUtil.getOrganization(classPK));
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
			(Organization organization) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(organization));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index organization " +
								organization.getOrganizationId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationIndexer.class);

}