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

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.util.SearchUtil;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryBuilder {

	public SearchResultSummary build() throws Exception {
		String className = _document.get(Field.ENTRY_CLASS_NAME);
		long classPK = GetterUtil.getLong(_document.get(Field.ENTRY_CLASS_PK));

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		AssetRenderer<?> assetRenderer = null;

		if (assetRendererFactory != null) {
			long resourcePrimKey = GetterUtil.getLong(
				_document.get(Field.ROOT_ENTRY_CLASS_PK));

			if (resourcePrimKey > 0) {
				classPK = resourcePrimKey;
			}

			assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
		}

		String viewURL = SearchUtil.getSearchResultViewURL(
			_renderRequest, _renderResponse, className, classPK,
			_searchResultPreferences.isViewInContext(), _currentURL);

		Summary summary = getSummary(
			_highlightEnabled, _queryTerms, assetRenderer, className);

		if (summary == null) {
			return null;
		}

		SearchResultSummary srs = new SearchResultSummary();

		srs.assetRenderer = assetRenderer;
		srs.className = className;
		srs.classPK = classPK;
		srs.currentURL = _currentURL;
		srs.document = _document;
		srs.locale = _locale;
		srs.searchResultPreferences = _searchResultPreferences;
		srs.summary = summary;
		srs.viewURL = viewURL;

		return srs;
	}

	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	public void setHighlightEnabled(boolean highlightEnabled) {
		_highlightEnabled = highlightEnabled;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setRenderResponse(RenderResponse renderResponse) {
		_renderResponse = renderResponse;
	}

	public void setSearchResultPreferences(
		SearchResultPreferences searchResultPreferences) {

		_searchResultPreferences = searchResultPreferences;
	}

	protected Summary getSummary(
			boolean highlightEnabled, String[] queryTerms,
			AssetRenderer<?> assetRenderer, String className)
		throws SearchException {

		Summary summary = null;

		Indexer indexer = IndexerRegistryUtil.getIndexer(className);

		if (indexer != null) {
			String snippet = _document.get(Field.SNIPPET);

			summary = indexer.getSummary(
				_document, snippet, _renderRequest, _renderResponse);
		}
		else if (assetRenderer != null) {
			summary = new Summary(
				_locale, assetRenderer.getTitle(_locale),
				assetRenderer.getSearchSummary(_locale));
		}

		if (summary != null) {
			summary.setHighlight(highlightEnabled);
			summary.setQueryTerms(queryTerms);
		}

		return summary;
	}

	private String _currentURL;
	private Document _document;
	private boolean _highlightEnabled;
	private Locale _locale;
	private String[] _queryTerms;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private SearchResultPreferences _searchResultPreferences;

}