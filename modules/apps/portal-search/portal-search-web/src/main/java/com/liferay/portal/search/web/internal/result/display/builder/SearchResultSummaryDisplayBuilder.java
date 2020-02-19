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
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.legacy.document.DocumentBuilderFactory;
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
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
			if (_documentBuilderFactory != null) {
				_document = _documentBuilderFactory.builder(
					_legacyDocument
				).build();
			}

			String className = getFieldValueString(Field.ENTRY_CLASS_NAME);

			long classPK = getEntryClassPK();

			if (Validator.isBlank(className) && (classPK == 0)) {
				return buildFromPlainDocument();
			}

			return build(className, classPK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

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

	public SearchResultSummaryDisplayBuilder setDocument(
		com.liferay.portal.kernel.search.Document document) {

		_legacyDocument = document;

		return this;
	}

	public SearchResultSummaryDisplayBuilder setDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;

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
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

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
				getFieldValueString(Field.ROOT_ENTRY_CLASS_PK));

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
				"category");
			searchResultSummaryDisplayContext.setFieldAssetTagNames("tag");
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
			getFieldValueString(Field.CREATE_DATE));

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

		String creatorUserName = getFieldValueString(Field.USER_NAME);

		if (!Validator.isBlank(creatorUserName)) {
			searchResultSummaryDisplayContext.setCreatorUserName(
				creatorUserName);
			searchResultSummaryDisplayContext.setCreatorVisible(true);
		}
	}

	protected void buildDocumentForm(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		if (_searchResultPreferences.isDisplayResultsInDocumentForm()) {
			Collection<String> allFieldNames = getAllFieldNames();

			searchResultSummaryDisplayContext.
				setDocumentFormFieldDisplayContexts(
					buildFieldDisplayContexts(allFieldNames.stream()));

			searchResultSummaryDisplayContext.setDocumentFormVisible(true);
		}
	}

	protected List<SearchResultFieldDisplayContext> buildFieldDisplayContexts(
		Stream<String> fieldNames) {

		return fieldNames.sorted(
		).map(
			this::buildFieldWithHighlight
		).collect(
			Collectors.toList()
		);
	}

	protected SearchResultFieldDisplayContext buildFieldWithHighlight(
		String fieldName) {

		SearchResultFieldDisplayContext searchResultFieldDisplayContext =
			new SearchResultFieldDisplayContext();

		searchResultFieldDisplayContext.setName(fieldName);
		searchResultFieldDisplayContext.setValuesToString(
			getHighlightedFieldValue(fieldName));

		return searchResultFieldDisplayContext;
	}

	protected SearchResultSummaryDisplayContext buildFromPlainDocument()
		throws PortletException {

		Set<String> set = new LinkedHashSet<>(
			Arrays.asList(
				SearchStringUtil.splitAndUnquote(
					_searchResultPreferences.getFieldsToDisplayOptional())));

		boolean star = set.remove(StringPool.STAR);

		boolean all;

		if (star || set.isEmpty()) {
			all = true;
		}
		else {
			all = false;
		}

		List<String> fieldNames = new ArrayList<>(set);

		List<String> allFieldNames = getAllFieldNames();

		String contentFieldName;
		boolean contentVisible;
		boolean fieldsVisible;
		String titleFieldName;

		if (fieldNames.isEmpty()) {
			contentFieldName = null;
			contentVisible = false;
			fieldsVisible = true;
			titleFieldName = allFieldNames.get(0);
		}
		else if (fieldNames.size() == 1) {
			contentFieldName = null;
			contentVisible = false;
			fieldsVisible = false;
			titleFieldName = fieldNames.get(0);
		}
		else {
			contentFieldName = fieldNames.get(1);
			contentVisible = true;
			fieldsVisible = fieldNames.size() > 2;
			titleFieldName = fieldNames.get(0);
		}

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			new SearchResultSummaryDisplayContext();

		searchResultSummaryDisplayContext.setContentVisible(contentVisible);
		searchResultSummaryDisplayContext.setFieldsVisible(fieldsVisible);

		SummaryBuilder summaryBuilder = _summaryBuilderFactory.newInstance();

		if (contentFieldName != null) {
			summaryBuilder.setContent(
				getHighlightedValuesToString(contentFieldName));
		}

		summaryBuilder.setHighlight(_highlightEnabled);
		summaryBuilder.setTitle(getHighlightedValuesToString(titleFieldName));

		Summary summary = summaryBuilder.build();

		if (contentVisible) {
			searchResultSummaryDisplayContext.setContent(summary.getContent());
		}

		if (fieldsVisible) {
			List<String> visibleFieldNames;

			if (all) {
				visibleFieldNames = allFieldNames;
			}
			else {
				visibleFieldNames = fieldNames;
			}

			searchResultSummaryDisplayContext.setFieldDisplayContexts(
				buildFieldDisplayContexts(visibleFieldNames.stream()));
		}

		searchResultSummaryDisplayContext.setHighlightedTitle(
			summary.getTitle());
		searchResultSummaryDisplayContext.setPortletURL(
			_portletURLFactory.getPortletURL());
		searchResultSummaryDisplayContext.setViewURL(StringPool.BLANK);

		buildDocumentForm(searchResultSummaryDisplayContext);

		return searchResultSummaryDisplayContext;
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
			searchResultImageContributor ->
				searchResultImageContributor.contribute(searchResultImage));
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
					_httpServletRequest,
					"this-result-comes-from-the-x-version-of-this-content",
					summaryLocale.getDisplayLanguage(_locale), false));

			searchResultSummaryDisplayContext.setLocaleReminderVisible(true);
		}
	}

	protected void buildModelResource(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		String className) {

		String modelResource = _resourceActions.getModelResource(
			_themeDisplay.getLocale(), className);

		if (!Validator.isBlank(modelResource)) {
			searchResultSummaryDisplayContext.setModelResource(modelResource);
			searchResultSummaryDisplayContext.setModelResourceVisible(true);
		}
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

		AssetEntry childAssetEntry = _assetEntryLocalService.fetchEntry(
			className, getEntryClassPK());

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

	protected List<String> getAllFieldNames() {
		if (_document != null) {
			Map<String, com.liferay.portal.search.document.Field> map =
				_document.getFields();

			return new ArrayList<>(map.keySet());
		}

		Map<String, Field> map = _legacyDocument.getFields();

		return new ArrayList<>(map.keySet());
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
		catch (Exception exception) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Unable to get asset renderer for class ", className,
					" with primary key ", classPK),
				exception);
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
		return getFieldValueLong(Field.ENTRY_CLASS_PK);
	}

	protected long getFieldValueLong(String fieldName) {
		if (_document != null) {
			return GetterUtil.getLong(_document.getLong(fieldName));
		}

		return GetterUtil.getLong(_legacyDocument.get(fieldName));
	}

	protected String getFieldValueString(String fieldName) {
		if (_document != null) {
			return _document.getString(fieldName);
		}

		return _legacyDocument.get(fieldName);
	}

	protected List<String> getFieldValueStrings(String fieldName) {
		if (_document != null) {
			return _document.getStrings(fieldName);
		}

		Field field = _legacyDocument.getField(fieldName);

		return Arrays.asList(field.getValues());
	}

	protected String getHighlightedFieldName(String fieldName) {
		if (!_highlightEnabled) {
			return fieldName;
		}

		String snippetFieldName = Field.SNIPPET.concat(
			StringPool.UNDERLINE
		).concat(
			fieldName
		);

		if (isFieldPresent(snippetFieldName)) {
			return snippetFieldName;
		}

		return fieldName;
	}

	protected String getHighlightedFieldValue(String fieldName) {
		SummaryBuilder summaryBuilder = _summaryBuilderFactory.newInstance();

		summaryBuilder.setContent(getHighlightedValuesToString(fieldName));
		summaryBuilder.setHighlight(_highlightEnabled);

		Summary summary = summaryBuilder.build();

		return summary.getContent();
	}

	protected String getHighlightedValuesToString(String fieldName) {
		return getValuesToString(getHighlightedFieldName(fieldName));
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
			String snippet = getFieldValueString(Field.SNIPPET);

			com.liferay.portal.kernel.search.Summary summary =
				indexer.getSummary(
					_legacyDocument, snippet, _renderRequest, _renderResponse);

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

	protected String getValuesToString(String fieldName) {
		List<String> values = getFieldValueStrings(fieldName);

		if (values.isEmpty()) {
			return StringPool.BLANK;
		}

		if (values.size() == 1) {
			return values.get(0);
		}

		return String.valueOf(values);
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

	protected boolean isFieldPresent(String fieldName) {
		if (_document != null) {
			Map<String, com.liferay.portal.search.document.Field> map =
				_document.getFields();

			return map.containsKey(fieldName);
		}

		return _legacyDocument.hasField(fieldName);
	}

	protected Date parseDateStringFieldValue(String dateStringFieldValue) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			return dateFormat.parse(dateStringFieldValue);
		}
		catch (Exception exception) {
			throw new IllegalArgumentException(
				"Unable to parse date string: " + dateStringFieldValue,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultSummaryDisplayBuilder.class);

	private boolean _abridged;
	private AssetEntryLocalService _assetEntryLocalService;
	private AssetRendererFactoryLookup _assetRendererFactoryLookup;
	private String _currentURL;
	private Document _document;
	private DocumentBuilderFactory _documentBuilderFactory;
	private FastDateFormatFactory _fastDateFormatFactory;
	private boolean _highlightEnabled;
	private HttpServletRequest _httpServletRequest;
	private boolean _imageRequested;
	private IndexerRegistry _indexerRegistry;
	private Language _language;
	private com.liferay.portal.kernel.search.Document _legacyDocument;
	private Locale _locale;
	private PortletURLFactory _portletURLFactory;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private ResourceActions _resourceActions;
	private Stream<SearchResultImageContributor>
		_searchResultImageContributorsStream = Stream.empty();
	private SearchResultPreferences _searchResultPreferences;
	private SearchResultViewURLSupplier _searchResultViewURLSupplier;
	private SummaryBuilderFactory _summaryBuilderFactory;
	private ThemeDisplay _themeDisplay;

}