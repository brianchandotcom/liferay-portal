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

import com.liferay.commerce.product.content.search.web.internal.configuration.CPSearchResultsPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPSearchResultsDisplayContext {

	public CPSearchResultsDisplayContext(
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper, DLAppService dlAppService,
			HttpServletRequest httpServletRequest,
			PortletSharedSearchResponse portletSharedSearchResponse)
		throws ConfigurationException {

		_cpDefinitionHelper = cpDefinitionHelper;
		_cpInstanceHelper = cpInstanceHelper;
		_dlAppService = dlAppService;
		_httpServletRequest = httpServletRequest;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		_liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();

		_locale = httpServletRequest.getLocale();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpSearchResultsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPSearchResultsPortletInstanceConfiguration.class);
	}

	public String getCategoryIds() {
		return StringUtil.merge(
			_cpSearchResultsPortletInstanceConfiguration.assetCategoryIds(),
			StringPool.COMMA);
	}

	public String getConfigurationMethod() {
		return _cpSearchResultsPortletInstanceConfiguration.
			configurationMethod();
	}

	public long getCPDefinitionId(Document document) {
		String entryClassPK = document.get(_locale, Field.ENTRY_CLASS_PK);

		return GetterUtil.getLong(entryClassPK);
	}

	public CPInstance getDefaultCPInstance(Document document) throws Exception {
		long cpDefinitionId = getCPDefinitionId(document);

		return _cpInstanceHelper.getCPInstance(cpDefinitionId, null);
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

	public String getProductDefaultImage(
			Document document, ThemeDisplay themeDisplay)
		throws Exception {

		long fileEntryId = GetterUtil.getLong(
			document.get(
				CPDefinitionIndexer.FIELD_DEFAULT_IMAGE_FILE_ENTRY_ID));

		FileEntry fileEntry = null;

		if (fileEntryId > 0) {
			try {
				fileEntry = _dlAppService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}

		if (fileEntry != null) {
			return DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
		}

		return StringPool.BLANK;
	}

	public String getProductFriendlyURL(Document document)
		throws PortalException {

		long cpDefinitionId = getCPDefinitionId(document);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public SearchContainer<Document> getSearchContainer()
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

	public String getTitle(Document document) {
		String title = document.get(_locale, Field.TITLE);

		if (Validator.isNull(title)) {
			title = document.get(Field.TITLE);
		}

		return title;
	}

	public boolean isIgnoreSkuCombinations(Document document) {
		return GetterUtil.getBoolean(
			document.get(CPDefinitionIndexer.FIELD_IS_IGNORE_SKU_COMBINATIONS));
	}

	protected SearchContainer<Document> buildSearchContainer(
			List<Document> documents, int totalHits, int paginationStart,
			String paginationStartParameterName, int paginationDelta,
			String paginationDeltaParameterName)
		throws PortalException {

		PortletRequest portletRequest = _liferayPortletRequest;
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

		SearchContainer<Document> searchContainer = new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);

		searchContainer.setDeltaParam(paginationDeltaParameterName);
		searchContainer.setResults(documents);
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

	protected HttpServletRequest getSharedRequest() {
		return PortalUtil.getOriginalServletRequest(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));
	}

	protected String getURLString(
		String paginationStartParameterName,
		String paginationDeltaParameterName) {

		String urlString = HttpUtil.getCompleteURL(getSharedRequest());

		return urlString;
	}

	private final CPDefinitionHelper _cpDefinitionHelper;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPSearchResultsPortletInstanceConfiguration
		_cpSearchResultsPortletInstanceConfiguration;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private long _displayStyleGroupId;
	private final DLAppService _dlAppService;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Locale _locale;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private SearchContainer<Document> _searchContainer;

}