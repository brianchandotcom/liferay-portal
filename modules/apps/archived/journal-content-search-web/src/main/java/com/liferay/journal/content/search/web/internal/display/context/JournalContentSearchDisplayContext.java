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

package com.liferay.journal.content.search.web.internal.display.context;

import com.liferay.journal.content.search.web.internal.configuration.JournalContentSearchPortletInstanceConfiguration;
import com.liferay.journal.content.search.web.internal.constants.JournalContentSearchWebKeys;
import com.liferay.journal.content.search.web.internal.util.ContentHits;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.summary.Summary;
import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.search.summary.SummaryBuilderFactory;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class JournalContentSearchDisplayContext {

	public JournalContentSearchDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		JournalContentSearchPortletInstanceConfiguration
			journalContentSearchPortletInstanceConfiguration) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_journalContentSearchPortletInstanceConfiguration =
			journalContentSearchPortletInstanceConfiguration;

		_summaryBuilderFactory =
			(SummaryBuilderFactory)httpServletRequest.getAttribute(
				JournalContentSearchWebKeys.SUMMARY_BUILDER_FACTORY);
	}

	public Hits getHits() throws Exception {
		if (_hits != null) {
			return _hits;
		}

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setGroupIds(null);
		searchContext.setKeywords(getKeywords());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(
			_journalContentSearchPortletInstanceConfiguration.
				enableHighlighting());

		_hits = indexer.search(searchContext);

		return _hits;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		String defaultKeywords =
			LanguageUtil.get(_httpServletRequest, "search") +
				StringPool.TRIPLE_PERIOD;

		_keywords = StringUtil.unquote(
			ParamUtil.getString(
				_httpServletRequest, "keywords", defaultKeywords));

		return _keywords;
	}

	public SearchContainer<Document> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletURL renderURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/search.jsp"
		).setKeywords(
			getKeywords()
		).buildPortletURL();

		String originalKeywords = ParamUtil.getString(
			_httpServletRequest, "keywords", getKeywords());

		_searchContainer = new SearchContainer(
			_liferayPortletRequest, null, null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			renderURL, null,
			LanguageUtil.format(
				_httpServletRequest,
				"no-pages-were-found-that-matched-the-keywords-x",
				"<strong>" + HtmlUtil.escape(originalKeywords) + "</strong>",
				false));

		Hits hits = getHits();

		ContentHits contentHits = new ContentHits();

		contentHits.setShowListed(
			_journalContentSearchPortletInstanceConfiguration.showListed());

		contentHits.recordHits(
			hits, layout.getGroupId(), layout.isPrivateLayout(),
			_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setTotal(hits.getLength());
		_searchContainer.setResults(ListUtil.fromArray(hits.getDocs()));

		return _searchContainer;
	}

	public Summary getSummary(Document document) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		com.liferay.portal.kernel.search.Summary summary = indexer.getSummary(
			document, StringPool.BLANK, _liferayPortletRequest,
			_liferayPortletResponse);

		SummaryBuilder summaryBuilder = _summaryBuilderFactory.newInstance();

		summaryBuilder.setContent(summary.getContent());
		summaryBuilder.setHighlight(
			_journalContentSearchPortletInstanceConfiguration.
				enableHighlighting());
		summaryBuilder.setLocale(themeDisplay.getLocale());
		summaryBuilder.setMaxContentLength(summary.getMaxContentLength());
		summaryBuilder.setTitle(summary.getTitle());

		return summaryBuilder.build();
	}

	private Hits _hits;
	private final HttpServletRequest _httpServletRequest;
	private final JournalContentSearchPortletInstanceConfiguration
		_journalContentSearchPortletInstanceConfiguration;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<Document> _searchContainer;
	private final SummaryBuilderFactory _summaryBuilderFactory;

}