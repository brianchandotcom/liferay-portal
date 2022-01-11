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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.helper.CPPublisherWebHelper;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.data.source.CPDataSourceRegistry;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
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
			CPContentListEntryRendererRegistry contentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPDataSourceRegistry cpDataSourceRegistry,
			CPDefinitionHelper cpDefinitionHelper,
			CPPublisherWebHelper cpPublisherWebHelper,
			CPTypeServicesTracker cpTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		super(
			contentListEntryRendererRegistry, cpContentListRendererRegistry,
			cpPublisherWebHelper, cpTypeServicesTracker, httpServletRequest);

		_cpDataSourceRegistry = cpDataSourceRegistry;
		_cpDefinitionHelper = cpDefinitionHelper;
	}

	public Map<String, String> getCPContentListEntryRendererKeys() {
		Map<String, String> cpContentListEntryRendererKeys = new HashMap<>();

		for (CPType cpType : getCPTypes()) {
			String cpTypeName = cpType.getName();

			cpContentListEntryRendererKeys.put(
				cpTypeName, getCPTypeListEntryRendererKey(cpTypeName));
		}

		return cpContentListEntryRendererKeys;
	}

	public CPDataSourceResult getCPDataSourceResult() throws Exception {
		CPDataSourceResult cpDataSourceResult = null;

		if (isSelectionStyleDynamic()) {
			cpDataSourceResult = _getDynamicCPDataSourceResult(
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

		return cpDataSourceResult;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			cpContentRequestHelper.getLiferayPortletResponse();

		String delta = ParamUtil.getString(
			cpContentRequestHelper.getRequest(), "delta",
			String.valueOf(getPaginationDelta()));

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setParameter(
			"delta", delta
		).buildPortletURL();
	}

	public SearchContainer<CPCatalogEntry> getSearchContainer()
		throws Exception {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpContentRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, "there-are-no-products");

		_searchContainer.setDelta(
			cpPublisherPortletInstanceConfiguration.paginationDelta());

		CPDataSourceResult cpDataSourceResult = getCPDataSourceResult();

		if (cpDataSourceResult != null) {
			_searchContainer.setResultsAndTotal(
				cpDataSourceResult::getCPCatalogEntries,
				cpDataSourceResult.getLength());
		}

		return _searchContainer;
	}

	private CPDataSourceResult _getDynamicCPDataSourceResult(int start, int end)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"params", new LinkedHashMap<String, Object>()
			).build());

		searchContext.setCompanyId(cpContentRequestHelper.getCompanyId());

		CPQuery cpQuery = new CPQuery();

		Company company = cpContentRequestHelper.getCompany();

		cpPublisherWebHelper.setCategoriesAndTags(
			company.getGroupId(), cpQuery,
			cpContentRequestHelper.getPortletPreferences());

		cpPublisherWebHelper.setOrdering(
			cpQuery, cpContentRequestHelper.getPortletPreferences());

		return _cpDefinitionHelper.search(
			cpContentRequestHelper.getScopeGroupId(), searchContext, cpQuery,
			start, end);
	}

	private final CPDataSourceRegistry _cpDataSourceRegistry;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private SearchContainer<CPCatalogEntry> _searchContainer;

}