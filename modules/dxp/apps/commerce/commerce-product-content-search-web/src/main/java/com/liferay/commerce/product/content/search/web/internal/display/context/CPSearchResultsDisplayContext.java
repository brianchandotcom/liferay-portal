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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSearchResultsPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.links.CPDefinitionLinkTypeRegistry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPSearchResultsDisplayContext {

	public CPSearchResultsDisplayContext(
			CPContentListEntryRendererRegistry
				cpContentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionLinkTypeRegistry cpDefinitionLinkTypeRegistry,
			CPInstanceHelper cpInstanceHelper,
			CPTypeServicesTracker cpTypeServicesTracker,
			HttpServletRequest httpServletRequest,
			PortletSharedSearchResponse portletSharedSearchResponse)
		throws ConfigurationException {

		_cpContentListEntryRendererRegistry =
			cpContentListEntryRendererRegistry;
		_cpContentListRendererRegistry = cpContentListRendererRegistry;
		_cpDefinitionHelper = cpDefinitionHelper;
		_cpDefinitionLinkTypeRegistry = cpDefinitionLinkTypeRegistry;
		_cpInstanceHelper = cpInstanceHelper;
		_cpTypeServicesTracker = cpTypeServicesTracker;
		_httpServletRequest = httpServletRequest;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpSearchResultsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPSearchResultsPortletInstanceConfiguration.class);
	}

	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpType) {

		return
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderers(
				CPPortletKeys.CP_SEARCH_RESULTS, cpType);
	}

	public String getCPContentListRendererKey() {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			"cpContentListRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListRenderer> cpContentListRenderers =
			getCPContentListRenderers();

		if (cpContentListRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListRenderer cpContentListRenderer =
			cpContentListRenderers.get(0);

		if (cpContentListRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListRenderer.getKey();
	}

	public List<CPContentListRenderer> getCPContentListRenderers() {
		return _cpContentListRendererRegistry.getCPContentListRenderers(
			CPPortletKeys.CP_SEARCH_RESULTS);
	}

	public List<String> getCPDefinitionLinkTypes() {
		return _cpDefinitionLinkTypeRegistry.getTypes();
	}

	public String getCPTypeListEntryRendererKey(String cpType) {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			cpType + "--cpTypeListEntryRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListEntryRenderer> cpContentListEntryRenderers =
			getCPContentListEntryRenderers(cpType);

		if (cpContentListEntryRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListEntryRenderer cpContentListEntryRenderer =
			cpContentListEntryRenderers.get(0);

		if (cpContentListEntryRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListEntryRenderer.getKey();
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	public CPInstance getDefaultCPInstance(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		return _cpInstanceHelper.getCPInstance(
			cpCatalogEntry.getCPDefinitionId(), null);
	}

	public String getDisplayStyle() {
		return _cpSearchResultsPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_cpSearchResultsPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public SearchContainer<CPCatalogEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = buildSearchContainer(
			_portletSharedSearchResponse.getDocuments(),
			_portletSharedSearchResponse.getTotalHits(),
			_portletSharedSearchResponse.getPaginationStart(), "start",
			_portletSharedSearchResponse.getPaginationDelta(), "delta");

		return _searchContainer;
	}

	public String getSelectionStyle() {
		return _cpSearchResultsPortletInstanceConfiguration.selectionStyle();
	}

	public boolean isSelectionStyleADT() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("adt")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleCustomRenderer() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("custom")) {
			return true;
		}

		return false;
	}

	public void renderCPContentList() throws Exception {
		CPContentListRenderer cpContentListRenderer =
			_cpContentListRendererRegistry.getCPContentListRenderer(
				getCPContentListRendererKey());

		if (cpContentListRenderer != null) {
			SearchContainer<CPCatalogEntry> cpCatalogEntrySearchContainer =
				getSearchContainer();

			cpContentListRenderer.render(
				cpCatalogEntrySearchContainer.getResults(),
				_cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	public void renderCPContentListEntry(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		String cpType = cpCatalogEntry.getProductTypeName();

		CPContentListEntryRenderer cpContentListEntryRenderer =
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderer(
				getCPTypeListEntryRendererKey(cpType),
				CPPortletKeys.CP_SEARCH_RESULTS, cpType);

		if (cpContentListEntryRenderer != null) {
			cpContentListEntryRenderer.render(
				cpCatalogEntry, _cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	protected SearchContainer<CPCatalogEntry> buildSearchContainer(
			List<Document> documents, int totalHits, int paginationStart,
			String paginationStartParameterName, int paginationDelta,
			String paginationDeltaParameterName)
		throws PortalException {

		PortletRequest portletRequest =
			_cpRequestHelper.getLiferayPortletRequest();
		DisplayTerms displayTerms = null;
		DisplayTerms searchTerms = null;
		String curParam = paginationStartParameterName;
		int cur = paginationStart;
		int delta = paginationDelta;
		PortletURL portletURL = getPortletURL(
			paginationStartParameterName, paginationDeltaParameterName);
		List<String> headerNames = null;
		String emptyResultsMessage = null;
		String cssClass = null;

		SearchContainer<CPCatalogEntry> searchContainer = new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);

		searchContainer.setDeltaParam(paginationDeltaParameterName);
		searchContainer.setResults(getSearchContainerResults(documents));
		searchContainer.setTotal(totalHits);

		return searchContainer;
	}

	protected PortletURL getPortletURL(
		String paginationStartParameterName,
		String paginationDeltaParameterName) {

		final String urlString = getURLString(
			paginationStartParameterName, paginationDeltaParameterName);

		return new NullPortletURL() {

			@Override
			public String toString() {
				return urlString;
			}

		};
	}

	protected List<CPCatalogEntry> getSearchContainerResults(
		List<Document> documents) {

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		for (Document document : documents) {
			cpCatalogEntries.add(
				_cpDefinitionHelper.getCPCatalogEntry(
					document, _cpRequestHelper.getLocale()));
		}

		return cpCatalogEntries;
	}

	protected HttpServletRequest getSharedRequest() {
		return PortalUtil.getOriginalServletRequest(_httpServletRequest);
	}

	protected String getURLString(
		String paginationStartParameterName,
		String paginationDeltaParameterName) {

		String urlString = HttpUtil.getCompleteURL(getSharedRequest());

		return urlString;
	}

	private final CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;
	private final CPContentListRendererRegistry _cpContentListRendererRegistry;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final CPDefinitionLinkTypeRegistry _cpDefinitionLinkTypeRegistry;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPRequestHelper _cpRequestHelper;
	private final CPSearchResultsPortletInstanceConfiguration
		_cpSearchResultsPortletInstanceConfiguration;
	private final CPTypeServicesTracker _cpTypeServicesTracker;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private SearchContainer<CPCatalogEntry> _searchContainer;

}