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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayBuilder {

	public SearchResultSummaryDisplayContext build() throws Exception {
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

		Summary summary = getSummary(className, assetRenderer);

		if (summary == null) {
			return null;
		}

		return build(summary, className, classPK, assetRenderer, viewURL);
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
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

	public void setLanguage(Language language) {
		_language = language;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setPortletURLFactory(PortletURLFactory portletURLFactory) {
		_portletURLFactory = portletURLFactory;
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

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	public void setSearchResultPreferences(
		SearchResultPreferences searchResultPreferences) {

		_searchResultPreferences = searchResultPreferences;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected SearchResultSummaryDisplayContext build(
			Summary summary, String className, long classPK,
			AssetRenderer<?> assetRenderer, String viewURL)
		throws PortletException {

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			new SearchResultSummaryDisplayContext();

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry != null) {
			searchResultSummaryDisplayContext.setAssetEntryUserId(
				getAssetEntryUserId(assetEntry));
			searchResultSummaryDisplayContext.setUserPortraitVisible(true);
		}

		searchResultSummaryDisplayContext.setViewURL(checkViewURL(viewURL));

		searchResultSummaryDisplayContext.setHighlightedTitle(
			summary.getHighlightedTitle());

		if (hasAssetRendererURLDownload(assetRenderer)) {
			searchResultSummaryDisplayContext.setAssetRendererURLDownload(
				assetRenderer.getURLDownload(_themeDisplay));
			searchResultSummaryDisplayContext.
				setAssetRendererURLDownloadVisible(true);
			searchResultSummaryDisplayContext.setTitle(summary.getTitle());
		}

		searchResultSummaryDisplayContext.setModelResource(
			_resourceActions.getModelResource(
				_themeDisplay.getLocale(), className));

		if (_locale != summary.getLocale()) {
			Locale summaryLocale = summary.getLocale();

			searchResultSummaryDisplayContext.setLocaleLanguageId(
				LocaleUtil.toLanguageId(summaryLocale));
			searchResultSummaryDisplayContext.setLocaleReminder(
				_language.format(
					_request,
					"this-result-comes-from-the-x-version-of-this-content",
					summaryLocale.getDisplayLanguage(_locale), false));

			searchResultSummaryDisplayContext.setLocaleReminderVisible(true);
		}

		if (Validator.isNotNull(summary.getContent())) {
			searchResultSummaryDisplayContext.setContent(
				summary.getHighlightedContent());
			searchResultSummaryDisplayContext.setContentVisible(true);
		}

		if (hasAssetCategoriesOrTags(assetEntry)) {
			searchResultSummaryDisplayContext.setClassName(className);
			searchResultSummaryDisplayContext.setClassPK(classPK);
			searchResultSummaryDisplayContext.setAssetCategoriesOrTagsVisible(
				true);
			searchResultSummaryDisplayContext.setFieldAssetCategoryIds(
				Field.ASSET_CATEGORY_IDS);
			searchResultSummaryDisplayContext.setFieldAssetTagNames(
				Field.ASSET_TAG_NAMES);
			searchResultSummaryDisplayContext.setPortletURL(
				_portletURLFactory.getPortletURL());
		}

		if (_searchResultPreferences.isDisplayResultsInDocumentForm()) {
			searchResultSummaryDisplayContext.
				setDocumentFormFieldDisplayContexts(buildFields());
			searchResultSummaryDisplayContext.setDocumentFormVisible(true);
		}

		return searchResultSummaryDisplayContext;
	}

	protected SearchResultFieldDisplayContext buildField(Field field) {
		SearchResultFieldDisplayContext searchResultFieldDisplayContext =
			new SearchResultFieldDisplayContext();

		searchResultFieldDisplayContext.setBoost(field.getBoost());
		searchResultFieldDisplayContext.setArray(isArray(field));
		searchResultFieldDisplayContext.setNumeric(field.isNumeric());
		searchResultFieldDisplayContext.setTokenized(field.isTokenized());
		searchResultFieldDisplayContext.setName(field.getName());
		searchResultFieldDisplayContext.setValuesToString(
			getValuesToString(field));

		return searchResultFieldDisplayContext;
	}

	protected List<SearchResultFieldDisplayContext> buildFields() {
		Map<String, Field> map = _document.getFields();

		List<Map.Entry<String, Field>> entries = new LinkedList(map.entrySet());

		Collections.sort(
			entries,
			new Comparator<Map.Entry<String, Field>>() {

				@Override
				public int compare(
					Map.Entry<String, Field> entry1,
					Map.Entry<String, Field> entry2) {

					String key = entry1.getKey();

					return key.compareTo(entry2.getKey());
				}

			});

		List<SearchResultFieldDisplayContext> searchResultFieldDisplayContexts =
			new ArrayList<>(entries.size());

		for (Map.Entry<String, Field> entry : entries) {
			Field field = entry.getValue();

			String fieldName = field.getName();

			if (fieldName.equals(Field.UID)) {
				continue;
			}

			searchResultFieldDisplayContexts.add(buildField(field));
		}

		return searchResultFieldDisplayContexts;
	}

	protected String checkViewURL(String viewURL) {
		if (Validator.isNotNull(viewURL) &&
			viewURL.startsWith(_themeDisplay.getURLPortal())) {

			viewURL = HttpUtil.setParameter(
				viewURL, "inheritRedirect",
				_searchResultPreferences.isViewInContext());

			if (!_searchResultPreferences.isViewInContext()) {
				viewURL = HttpUtil.setParameter(
					viewURL, "redirect", _currentURL);
			}
		}

		return viewURL;
	}

	protected long getAssetEntryUserId(AssetEntry assetEntry) {
		if (Objects.equals(assetEntry.getClassName(), User.class.getName())) {
			return assetEntry.getClassPK();
		}

		return assetEntry.getUserId();
	}

	protected Summary getSummary(
			String className, AssetRenderer<?> assetRenderer)
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
			summary.setHighlight(_highlightEnabled);
			summary.setQueryTerms(_queryTerms);
		}

		return summary;
	}

	protected String getValuesToString(Field field) {
		String[] values = field.getValues();

		StringBundler sb = new StringBundler(4 * values.length);

		for (int i = 0; i < values.length; i++) {
			if (field.isNumeric()) {
				sb.append(HtmlUtil.escape(values[i]));
			}
			else {
				sb.append(StringPool.QUOTE);
				sb.append(HtmlUtil.escape(values[i]));
				sb.append(StringPool.QUOTE);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		if (values.length > 1) {
			sb.setStringAt(StringPool.OPEN_BRACKET, 0);

			sb.append(StringPool.CLOSE_BRACKET);
		}

		return sb.toString();
	}

	protected boolean hasAssetCategoriesOrTags(AssetEntry assetEntry) {
		if (assetEntry == null) {
			return false;
		}

		if (ArrayUtil.isNotEmpty(assetEntry.getCategoryIds())) {
			return true;
		}

		if (ArrayUtil.isNotEmpty(assetEntry.getTagNames())) {
			return true;
		}

		return false;
	}

	protected boolean hasAssetRendererURLDownload(
		AssetRenderer<?> assetRenderer) {

		if (assetRenderer == null) {
			return false;
		}

		if (Validator.isNull(assetRenderer.getURLDownload(_themeDisplay))) {
			return false;
		}

		return true;
	}

	protected boolean isArray(Field field) {
		String[] values = field.getValues();

		if (values.length > 1) {
			return true;
		}

		return false;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private String _currentURL;
	private Document _document;
	private boolean _highlightEnabled;
	private Language _language;
	private Locale _locale;
	private PortletURLFactory _portletURLFactory;
	private String[] _queryTerms;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private HttpServletRequest _request;
	private ResourceActions _resourceActions;
	private SearchResultPreferences _searchResultPreferences;
	private ThemeDisplay _themeDisplay;

}