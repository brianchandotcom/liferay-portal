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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatConstants;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.summary.Summary;
import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.search.summary.SummaryBuilderFactory;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.web.internal.util.SearchUtil;
import com.liferay.portal.search.web.search.result.SearchResultImage;
import com.liferay.portal.search.web.search.result.SearchResultImageContributor;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayBuilder {

	public SearchResultSummaryDisplayContext build() throws Exception {
		try {
			return build(
				_document.get(Field.ENTRY_CLASS_NAME), getEntryClassPK());
		}
		catch (Exception e) {
			_log.error(e, e);

			return buildTemporarilyUnavailable();
		}
	}

	public SearchResultSummaryDisplayBuilder setAbridged(boolean abridged) {
		_abridged = abridged;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setAssetRendererFactoryLookup(
		AssetRendererFactoryLookup assetRendererFactoryLookup) {

		_assetRendererFactoryLookup = assetRendererFactoryLookup;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setCurrentURL(String currentURL) {
		_currentURL = currentURL;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setDocument(Document document) {
		_document = document;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setFastDateFormatFactory(
		FastDateFormatFactory fastDateFormatFactory) {

		_fastDateFormatFactory = fastDateFormatFactory;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setHighlightEnabled(
		boolean highlightEnabled) {

		_highlightEnabled = highlightEnabled;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setImageRequested(
		boolean imageRequested) {

		_imageRequested = imageRequested;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setIndexerRegistry(
		IndexerRegistry indexerRegistry) {

		_indexerRegistry = indexerRegistry;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setLanguage(Language language) {
		_language = language;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setLocale(Locale locale) {
		_locale = locale;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setPortletURLFactory(
		PortletURLFactory portletURLFactory) {

		_portletURLFactory = portletURLFactory;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setRenderRequest(
		RenderRequest renderRequest) {

		_renderRequest = renderRequest;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setRenderResponse(
		RenderResponse renderResponse) {

		_renderResponse = renderResponse;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setRequest(
		HttpServletRequest request) {

		_request = request;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setResourceActions(
		ResourceActions resourceActions) {

		_resourceActions = resourceActions;

		return this;
	}

	public SearchResultSummaryDisplayBuilder
		setSearchResultImageContributorsStream(
			Stream<SearchResultImageContributor>
				searchResultImageContributorsStream) {

		_searchResultImageContributorsStream =
			searchResultImageContributorsStream;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setSearchResultPreferences(
		SearchResultPreferences searchResultPreferences) {

		_searchResultPreferences = searchResultPreferences;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setSearchResultViewURLSupplier(
		SearchResultViewURLSupplier searchResultViewURLSupplier) {

		_searchResultViewURLSupplier = searchResultViewURLSupplier;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setSummaryBuilderFactory(
		SummaryBuilderFactory summaryBuilderFactory) {

		_summaryBuilderFactory = summaryBuilderFactory;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setThemeDisplay(
		ThemeDisplay themeDisplay) {

		_themeDisplay = themeDisplay;

		return this;
	}

	protected SearchResultSummaryDisplayContext build(
			String className, long classPK)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactoryByClassName(className);

		AssetRenderer<?> assetRenderer = null;

		if (assetRendererFactory != null) {
			long resourcePrimKey = GetterUtil.getLong(
				_document.get(Field.ROOT_ENTRY_CLASS_PK));

			if (resourcePrimKey > 0) {
				classPK = resourcePrimKey;
			}

			assetRenderer = getAssetRenderer(
				className, classPK, assetRendererFactory);
		}

		Summary summary = getSummary(className, assetRenderer);

		if (summary == null) {
			return null;
		}

		return build(summary, className, classPK, assetRenderer);
	}

	protected SearchResultSummaryDisplayContext build(
			Summary summary, String className, long classPK,
			AssetRenderer<?> assetRenderer)
		throws PortalException, PortletException {

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			new SearchResultSummaryDisplayContext();

		searchResultSummaryDisplayContext.setClassName(className);
		searchResultSummaryDisplayContext.setClassPK(classPK);

		if (Validator.isNotNull(summary.getContent())) {
			searchResultSummaryDisplayContext.setContent(summary.getContent());
			searchResultSummaryDisplayContext.setContentVisible(true);
		}

		searchResultSummaryDisplayContext.setHighlightedTitle(
			summary.getTitle());
		searchResultSummaryDisplayContext.setPortletURL(
			_portletURLFactory.getPortletURL());

		if (_abridged) {
			return searchResultSummaryDisplayContext;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, classPK);

		buildAssetCategoriesOrTags(
			searchResultSummaryDisplayContext, assetEntry);

		buildAssetRendererURLDownload(
			searchResultSummaryDisplayContext, assetRenderer, summary);
		buildCreationDateString(searchResultSummaryDisplayContext);
		buildCreatorUserName(searchResultSummaryDisplayContext);
		buildDocumentForm(searchResultSummaryDisplayContext);
		buildImage(searchResultSummaryDisplayContext, className, classPK);
		buildLocaleReminder(searchResultSummaryDisplayContext, summary);
		buildModelResource(searchResultSummaryDisplayContext, className);
		buildUserPortrait(
			searchResultSummaryDisplayContext, assetEntry, className);
		buildViewURL(className, classPK, searchResultSummaryDisplayContext);

		return searchResultSummaryDisplayContext;
	}

	protected void buildAssetCategoriesOrTags(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		AssetEntry assetEntry) {

		if (hasAssetCategoriesOrTags(assetEntry)) {
			searchResultSummaryDisplayContext.setAssetCategoriesOrTagsVisible(
				true);
			searchResultSummaryDisplayContext.setFieldAssetCategoryIds(
				Field.ASSET_CATEGORY_IDS);
			searchResultSummaryDisplayContext.setFieldAssetTagNames(
				Field.ASSET_TAG_NAMES);
		}
	}

	protected void buildAssetRendererURLDownload(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		AssetRenderer<?> assetRenderer, Summary summary) {

		if (hasAssetRendererURLDownload(assetRenderer)) {
			searchResultSummaryDisplayContext.setAssetRendererURLDownload(
				assetRenderer.getURLDownload(_themeDisplay));
			searchResultSummaryDisplayContext.
				setAssetRendererURLDownloadVisible(true);
			searchResultSummaryDisplayContext.setTitle(
				assetRenderer.getTitle(summary.getLocale()));
		}
	}

	protected void buildCreationDateString(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		Optional<String> dateStringOptional = SearchStringUtil.maybe(
			_document.get(Field.CREATE_DATE));

		Optional<Date> dateOptional = dateStringOptional.map(
			this::parseDateStringFieldValue);

		dateOptional.ifPresent(
			date -> {
				searchResultSummaryDisplayContext.setCreationDateString(
					formatCreationDate(date));
				searchResultSummaryDisplayContext.setCreationDateVisible(true);
			});
	}

	protected void buildCreatorUserName(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		String creatorUserName = _document.get(Field.USER_NAME);

		if (!Validator.isBlank(creatorUserName)) {
			searchResultSummaryDisplayContext.setCreatorUserName(
				creatorUserName);
			searchResultSummaryDisplayContext.setCreatorVisible(true);
		}
	}

	protected void buildDocumentForm(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		if (_searchResultPreferences.isDisplayResultsInDocumentForm()) {
			searchResultSummaryDisplayContext.
				setDocumentFormFieldDisplayContexts(buildFields());
			searchResultSummaryDisplayContext.setDocumentFormVisible(true);
		}
	}

	protected SearchResultFieldDisplayContext buildField(Field field) {
		SearchResultFieldDisplayContext searchResultFieldDisplayContext =
			new SearchResultFieldDisplayContext();

		searchResultFieldDisplayContext.setArray(isArray(field));
		searchResultFieldDisplayContext.setName(field.getName());
		searchResultFieldDisplayContext.setNumeric(field.isNumeric());
		searchResultFieldDisplayContext.setTokenized(field.isTokenized());
		searchResultFieldDisplayContext.setValuesToString(
			getValuesToString(field));

		return searchResultFieldDisplayContext;
	}

	protected List<SearchResultFieldDisplayContext> buildFields() {
		Map<String, Field> map = _document.getFields();

		List<Map.Entry<String, Field>> entries = new LinkedList<>(
			map.entrySet());

		Collections.sort(
			entries,
			(entry1, entry2) -> {
				String key = entry1.getKey();

				return key.compareTo(entry2.getKey());
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

	protected void buildImage(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		String className, long classPK) {

		if (!_imageRequested) {
			return;
		}

		SearchResultImage searchResultImage = new SearchResultImage() {

			@Override
			public String getClassName() {
				return className;
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

			@Override
			public void setIcon(String iconName) {
				searchResultSummaryDisplayContext.setIconId(iconName);
				searchResultSummaryDisplayContext.setIconVisible(true);
				searchResultSummaryDisplayContext.setPathThemeImages(
					_themeDisplay.getPathThemeImages());
			}

			@Override
			public void setThumbnail(String thumbnailURLString) {
				searchResultSummaryDisplayContext.setThumbnailURLString(
					thumbnailURLString);
				searchResultSummaryDisplayContext.setThumbnailVisible(true);
			}

		};

		_searchResultImageContributorsStream.forEach(
			searchResultImageContributor -> {
				searchResultImageContributor.contribute(searchResultImage);
			});
	}

	protected void buildLocaleReminder(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		Summary summary) {

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
	}

	protected void buildModelResource(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		String className) {

		searchResultSummaryDisplayContext.setModelResource(
			_resourceActions.getModelResource(
				_themeDisplay.getLocale(), className));
	}

	protected SearchResultSummaryDisplayContext buildTemporarilyUnavailable() {
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			new SearchResultSummaryDisplayContext();

		searchResultSummaryDisplayContext.setTemporarilyUnavailable(true);

		return searchResultSummaryDisplayContext;
	}

	protected void buildUserPortrait(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		AssetEntry assetEntry, String className) {

		long entryClassPK = getEntryClassPK();

		AssetEntry childAssetEntry = _assetEntryLocalService.fetchEntry(
			className, entryClassPK);

		if (childAssetEntry != null) {
			assetEntry = childAssetEntry;
		}

		if (assetEntry != null) {
			searchResultSummaryDisplayContext.setAssetEntryUserId(
				getAssetEntryUserId(assetEntry));
			searchResultSummaryDisplayContext.setUserPortraitVisible(true);
		}
	}

	protected void buildViewURL(
		String className, long classPK,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		String viewURL = getSearchResultViewURL(className, classPK);

		searchResultSummaryDisplayContext.setViewURL(viewURL);
	}

	protected String formatCreationDate(Date date) {
		Format format = _fastDateFormatFactory.getDateTime(
			FastDateFormatConstants.MEDIUM, FastDateFormatConstants.SHORT,
			_locale, _themeDisplay.getTimeZone());

		return format.format(date);
	}

	protected long getAssetEntryUserId(AssetEntry assetEntry) {
		if (Objects.equals(assetEntry.getClassName(), User.class.getName())) {
			return assetEntry.getClassPK();
		}

		return assetEntry.getUserId();
	}

	protected AssetRenderer<?> getAssetRenderer(
		String className, long classPK,
		AssetRendererFactory<?> assetRendererFactory) {

		try {
			return assetRendererFactory.getAssetRenderer(classPK);
		}
		catch (Exception e) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Unable to get asset renderer for class ", className,
					" with primary key ", classPK),
				e);
		}
	}

	protected AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		if (_assetRendererFactoryLookup != null) {
			return _assetRendererFactoryLookup.
				getAssetRendererFactoryByClassName(className);
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(className);
	}

	protected long getEntryClassPK() {
		return GetterUtil.getLong(_document.get(Field.ENTRY_CLASS_PK));
	}

	protected Indexer<Object> getIndexer(String className) {
		if (_indexerRegistry != null) {
			return _indexerRegistry.getIndexer(className);
		}

		return IndexerRegistryUtil.getIndexer(className);
	}

	protected String getSearchResultViewURL(String className, long classPK) {
		if (_searchResultViewURLSupplier != null) {
			return _searchResultViewURLSupplier.getSearchResultViewURL();
		}

		return SearchUtil.getSearchResultViewURL(
			_renderRequest, _renderResponse, className, classPK,
			_searchResultPreferences.isViewInContext(), _currentURL);
	}

	protected Summary getSummary(
			String className, AssetRenderer<?> assetRenderer)
		throws SearchException {

		SummaryBuilder summaryBuilder = _summaryBuilderFactory.newInstance();

		summaryBuilder.setHighlight(_highlightEnabled);

		Indexer<?> indexer = getIndexer(className);

		if (indexer != null) {
			String snippet = _document.get(Field.SNIPPET);

			com.liferay.portal.kernel.search.Summary summary =
				indexer.getSummary(
					_document, snippet, _renderRequest, _renderResponse);

			if (summary != null) {
				summaryBuilder.setContent(summary.getContent());
				summaryBuilder.setLocale(summary.getLocale());
				summaryBuilder.setMaxContentLength(
					summary.getMaxContentLength());
				summaryBuilder.setTitle(summary.getTitle());

				return summaryBuilder.build();
			}
		}
		else if (assetRenderer != null) {
			summaryBuilder.setContent(assetRenderer.getSearchSummary(_locale));
			summaryBuilder.setLocale(_locale);
			summaryBuilder.setTitle(assetRenderer.getTitle(_locale));

			return summaryBuilder.build();
		}

		return null;
	}

	protected String getValuesToString(Field field) {
		String[] values = field.getValues();

		if (ArrayUtil.isEmpty(values)) {
			return StringPool.BLANK;
		}

		if (values.length == 1) {
			return values[0];
		}

		return String.valueOf(Arrays.asList(values));
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

	protected Date parseDateStringFieldValue(String dateStringFieldValue) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			return dateFormat.parse(dateStringFieldValue);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
				"Unable to parse date string: " + dateStringFieldValue, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultSummaryDisplayBuilder.class);

	private boolean _abridged;
	private AssetEntryLocalService _assetEntryLocalService;
	private AssetRendererFactoryLookup _assetRendererFactoryLookup;
	private String _currentURL;
	private Document _document;
	private FastDateFormatFactory _fastDateFormatFactory;
	private boolean _highlightEnabled;
	private boolean _imageRequested;
	private IndexerRegistry _indexerRegistry;
	private Language _language;
	private Locale _locale;
	private PortletURLFactory _portletURLFactory;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private HttpServletRequest _request;
	private ResourceActions _resourceActions;
	private Stream<SearchResultImageContributor>
		_searchResultImageContributorsStream = Stream.empty();
	private SearchResultPreferences _searchResultPreferences;
	private SearchResultViewURLSupplier _searchResultViewURLSupplier;
	private SummaryBuilderFactory _summaryBuilderFactory;
	private ThemeDisplay _themeDisplay;

}