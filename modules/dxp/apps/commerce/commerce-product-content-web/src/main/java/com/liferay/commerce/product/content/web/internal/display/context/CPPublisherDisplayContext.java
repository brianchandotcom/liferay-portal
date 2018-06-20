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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.util.CPPublisherWebHelper;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.data.source.CPDataSourceRegistry;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPPublisherDisplayContext extends BaseCPPublisherDisplayContext {

	public CPPublisherDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CPContentListEntryRendererRegistry contentListEntryRendererRegistry,
		CPContentListRendererRegistry cpContentListRendererRegistry,
		CPDataSourceRegistry cpDataSourceRegistry,
		CPDefinitionHelper cpDefinitionHelper,
		CPPublisherWebHelper cpPublisherWebHelper,
		HttpServletRequest httpServletRequest) {

		super(
			contentListEntryRendererRegistry, cpContentListRendererRegistry,
			cpPublisherWebHelper, httpServletRequest);

		_commerceOrganizationHelper = commerceOrganizationHelper;
		_cpDataSourceRegistry = cpDataSourceRegistry;
		_cpDefinitionHelper = cpDefinitionHelper;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			cpContentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			cpContentRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		return portletURL;
	}

	public SearchContainer<CPCatalogEntry> getSearchContainer()
		throws Exception {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpContentRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, "there-are-no-products");

		CPDataSourceResult cpDataSourceResult = null;

		if (isSelectionStyleDynamic()) {
			cpDataSourceResult = _getDynamicCPDataSourceResult(
				cpContentRequestHelper.getScopeGroupId(),
				_searchContainer.getStart(), _searchContainer.getEnd());
		}
		else if (isSelectionStyleDataSource()) {
			String dataSourceName = getDataSource();

			CPDataSource cpDataSource = _cpDataSourceRegistry.getCPDataSource(
				dataSourceName);

			if (cpDataSource == null) {
				cpDataSourceResult = new CPDataSourceResult(
					new ArrayList<>(), 0);
			}
			else {
				cpDataSourceResult = cpDataSource.getResult(
					cpContentRequestHelper.getRequest(),
					_searchContainer.getStart(), _searchContainer.getEnd());
			}
		}
		else if (isSelectionStyleManual()) {
			List<CPCatalogEntry> catalogEntries = getCPCatalogEntries();

			int end = _searchContainer.getEnd();

			if (end > catalogEntries.size()) {
				end = catalogEntries.size();
			}

			List<CPCatalogEntry> results = catalogEntries.subList(
				_searchContainer.getStart(), end);

			cpDataSourceResult = new CPDataSourceResult(
				results, catalogEntries.size());
		}

		_searchContainer.setTotal(cpDataSourceResult.getLength());
		_searchContainer.setResults(cpDataSourceResult.getCPCatalogEntries());

		return _searchContainer;
	}

	public void renderCPContentList() throws Exception {
		CPContentListRenderer cpContentListRenderer =
			cpContentListRendererRegistry.getCPContentListRenderer(
				getCPContentListRendererKey());

		if (cpContentListRenderer != null) {
			SearchContainer<CPCatalogEntry> cpCatalogEntrySearchContainer =
				getSearchContainer();

			cpContentListRenderer.render(
				cpCatalogEntrySearchContainer.getResults(),
				cpContentRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					cpContentRequestHelper.getLiferayPortletResponse()));
		}
	}

	public void renderCPContentListEntry(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		String cpType = cpCatalogEntry.getProductTypeName();

		CPContentListEntryRenderer cpContentListEntryRenderer =
			contentListEntryRendererRegistry.getCPContentListEntryRenderer(
				getCPTypeListEntryRendererKey(cpType),
				getCPContentListRendererKey(), cpType);

		if (cpContentListEntryRenderer != null) {
			cpContentListEntryRenderer.render(
				cpCatalogEntry, cpContentRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					cpContentRequestHelper.getLiferayPortletResponse()));
		}
	}

	private CPDataSourceResult _getDynamicCPDataSourceResult(
			long groupId, int start, int end)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", StringPool.STAR);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(cpContentRequestHelper.getCompanyId());
		searchContext.setGroupIds(new long[] {groupId});

		searchContext.setKeywords(StringPool.STAR);

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				cpContentRequestHelper.getRequest());

		long organizationId = 0;

		if (organization != null) {
			organizationId = organization.getOrganizationId();
		}

		CPQuery cpQuery = new CPQuery(
			cpContentRequestHelper.getUserId(), organizationId);

		cpPublisherWebHelper.setCategoriesAndTags(
			cpContentRequestHelper.getScopeGroupId(), cpQuery,
			cpContentRequestHelper.getPortletPreferences());

		cpPublisherWebHelper.setOrdering(
			cpQuery, cpContentRequestHelper.getPortletPreferences());

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			cpContentRequestHelper.getScopeGroupId(), searchContext, cpQuery,
			start, end);

		return cpDataSourceResult;
	}

	private final CommerceOrganizationHelper _commerceOrganizationHelper;
	private final CPDataSourceRegistry _cpDataSourceRegistry;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private SearchContainer<CPCatalogEntry> _searchContainer;

}