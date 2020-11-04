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

package com.liferay.translation.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewDisplayContext {

	public ViewDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TranslationEntryLocalService translationEntryLocalService) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_translationEntryLocalService = translationEntryLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getActionURL() throws PortalException {
		SearchContainer<TranslationEntry> searchContainer =
			getSearchContainer();

		return searchContainer.getIteratorURL();
	}

	public String getModelName(TranslationEntry translationEntry) {
		return ResourceActionsUtil.getModelResource(
			_themeDisplay.getLocale(), translationEntry.getClassName());
	}

	public SearchContainer<TranslationEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, "no-entries-were-found");

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute("paginationType", "regular");

		searchContext.setCompanyId(_themeDisplay.getCompanyId());

		searchContext.setEnd(_searchContainer.getEnd());

		searchContext.setKeywords(
			ParamUtil.getString(_httpServletRequest, "keywords"));

		searchContext.setStart(_searchContainer.getStart());

		searchContext.setUserId(_themeDisplay.getUserId());

		Indexer<TranslationEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(TranslationEntry.class);

		Hits hits = indexer.search(searchContext);

		List<TranslationEntry> results = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long translationEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			results.add(
				_translationEntryLocalService.getTranslationEntry(
					translationEntryId));
		}

		_searchContainer.setResults(results);

		_searchContainer.setTotal(hits.getLength());

		return _searchContainer;
	}

	public String getTitle(TranslationEntry translationEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				TranslationEntry.class.getName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			translationEntry.getTranslationEntryId());

		return assetRenderer.getTitle(_themeDisplay.getLocale());
	}

	public TranslationEntryManagementToolbarDisplayContext
			getTranslationEntryManagementToolbarDisplayContext()
		throws PortalException {

		return new TranslationEntryManagementToolbarDisplayContext(
			_httpServletRequest, _liferayPortletRequest,
			_liferayPortletResponse, getSearchContainer());
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<TranslationEntry> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TranslationEntryLocalService _translationEntryLocalService;

}