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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.web.internal.display.context.helper.WikiPortletInstanceSettingsHelper;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class WikiSearchDisplayContext {

	public WikiSearchDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		WikiPortletInstanceSettingsHelper wikiPortletInstanceSettingsHelper) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_wikiPortletInstanceSettingsHelper = wikiPortletInstanceSettingsHelper;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Hits getHits() throws SearchException {
		if (_hits != null) {
			return _hits;
		}

		Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(
			WikiPage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(
			_wikiPortletInstanceSettingsHelper.isEnableHighlighting());

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(_searchContainer.getEnd());
		searchContext.setIncludeAttachments(true);
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(getKeywords());
		searchContext.setNodeIds(_getNodeIds());
		searchContext.setStart(_searchContainer.getStart());

		_hits = indexer.search(searchContext);

		return _hits;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<SearchResult> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer(
			_renderRequest,
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/wiki/search"
			).setRedirect(
				getRedirect()
			).setKeywords(
				getKeywords()
			).setParameter(
				"nodeId", _getNodeId()
			).buildPortletURL(),
			null,
			LanguageUtil.format(
				_httpServletRequest,
				"no-pages-were-found-that-matched-the-keywords-x",
				"<strong>" + HtmlUtil.escape(getKeywords()) + "</strong>",
				false));

		Hits hits = getHits();

		_searchContainer.setResultsAndTotal(
			() -> SearchResultUtil.getSearchResults(
				hits, _themeDisplay.getLocale()),
			hits.getLength());

		return _searchContainer;
	}

	private long _getNodeId() {
		if (_nodeId != null) {
			return _nodeId;
		}

		_nodeId = BeanParamUtil.getLong(
			(WikiNode)_httpServletRequest.getAttribute(WikiWebKeys.WIKI_NODE),
			_httpServletRequest, "nodeId");

		return _nodeId;
	}

	private long[] _getNodeIds() {
		if (_nodeIds != null) {
			return _nodeIds;
		}

		WikiNode node = (WikiNode)_httpServletRequest.getAttribute(
			WikiWebKeys.WIKI_NODE);

		if (node != null) {
			_nodeIds = new long[] {_getNodeId()};
		}

		return _nodeIds;
	}

	private Hits _hits;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Long _nodeId;
	private long[] _nodeIds;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<SearchResult> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final WikiPortletInstanceSettingsHelper
		_wikiPortletInstanceSettingsHelper;

}