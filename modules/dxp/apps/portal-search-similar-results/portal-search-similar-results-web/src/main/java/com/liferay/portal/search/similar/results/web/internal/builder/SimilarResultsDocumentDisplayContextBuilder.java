/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatConstants;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.legacy.document.DocumentBuilderFactory;
import com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDocumentDisplayContext;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.summary.Summary;
import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.search.summary.SummaryBuilderFactory;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Wade Cao
 */
public class SimilarResultsDocumentDisplayContextBuilder {

	public SimilarResultsDocumentDisplayContextBuilder(
		SimilarResultsRoute similarResultsRoute) {

		_similarResultsRoute = similarResultsRoute;
	}

	public SimilarResultsDocumentDisplayContext build() {
		try {
			if (_documentBuilderFactory != null) {
				_document = _documentBuilderFactory.builder(
					_legacyDocument
				).build();
			}

			String className = getFieldValueString(Field.ENTRY_CLASS_NAME);

			long classPK = getEntryClassPK();

			return build(className, classPK);
		}
		catch (Exception e) {
			return buildTemporarilyUnavailable();
		}
	}

	public SimilarResultsDocumentDisplayContextBuilder
		setAssetEntryLocalService(
			AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setDocument(
		com.liferay.portal.kernel.search.Document legacyDocument) {

		_legacyDocument = legacyDocument;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setDocument(
		Document document) {

		_document = document;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder
		setDocumentBuilderFactory(
			DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setFastDateFormatFactory(
		FastDateFormatFactory fastDateFormatFactory) {

		_fastDateFormatFactory = fastDateFormatFactory;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setHighlightEnabled(
		boolean highlightEnabled) {

		_highlightEnabled = highlightEnabled;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setHttp(Http http) {
		_http = http;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setIndexerRegistry(
		IndexerRegistry indexerRegistry) {

		_indexerRegistry = indexerRegistry;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setLocale(
		Locale locale) {

		_locale = locale;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setPortal(
		Portal portal) {

		_portal = portal;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setRenderRequest(
		RenderRequest renderRequest) {

		_renderRequest = renderRequest;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setRenderResponse(
		RenderResponse renderResponse) {

		_renderResponse = renderResponse;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setResourceActions(
		ResourceActions resourceActions) {

		_resourceActions = resourceActions;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setSummaryBuilderFactory(
		SummaryBuilderFactory summaryBuilderFactory) {

		_summaryBuilderFactory = summaryBuilderFactory;

		return this;
	}

	public SimilarResultsDocumentDisplayContextBuilder setThemeDisplay(
		ThemeDisplay themeDisplay) {

		_themeDisplay = themeDisplay;

		return this;
	}

	protected SimilarResultsDocumentDisplayContext build(
			String className, long classPK)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

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
			SummaryBuilder summaryBuilder =
				_summaryBuilderFactory.newInstance();

			summary = summaryBuilder.build();
		}

		return build(summary, className, classPK, assetRenderer);
	}

	protected SimilarResultsDocumentDisplayContext build(
			Summary summary, String className, long classPK,
			AssetRenderer<?> assetRenderer)
		throws Exception {

		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext =
				new SimilarResultsDocumentDisplayContext();

		if (Validator.isNotNull(summary.getContent())) {
			similarResultsDocumentDisplayContext.setContent(
				summary.getContent());
		}
		else {
			similarResultsDocumentDisplayContext.setContent(StringPool.BLANK);
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, classPK);

		buildCreationDateString(similarResultsDocumentDisplayContext);
		buildCreatorUserName(similarResultsDocumentDisplayContext);

		buildImage(
			similarResultsDocumentDisplayContext, className, classPK,
			assetRenderer);

		buildModelResource(similarResultsDocumentDisplayContext, className);

		buildCategoriesString(similarResultsDocumentDisplayContext, assetEntry);

		similarResultsDocumentDisplayContext.setTitle(
			getTitle(assetEntry, summary));

		similarResultsDocumentDisplayContext.setViewURL(
			getViewURL(assetEntry, assetRenderer, className, classPK));

		return similarResultsDocumentDisplayContext;
	}

	protected void buildCategoriesString(
		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext,
		AssetEntry assetEntry) {

		similarResultsDocumentDisplayContext.setCategoriesString(
			StringPool.BLANK);

		if (assetEntry == null) {
			return;
		}

		List<AssetCategory> assetCategories = assetEntry.getCategories();

		assetCategories.forEach(
			assetCategory -> {
				if (Validator.isBlank(
						similarResultsDocumentDisplayContext.
							getCategoriesString())) {

					similarResultsDocumentDisplayContext.setCategoriesString(
						assetCategory.getName());
				}
				else {
					similarResultsDocumentDisplayContext.setCategoriesString(
						"," + assetCategory.getName());
				}
			});
	}

	protected void buildCreationDateString(
		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext) {

		Optional<String> dateStringOptional = SearchStringUtil.maybe(
			getFieldValueString(Field.CREATE_DATE));

		Optional<Date> dateOptional = dateStringOptional.map(
			this::parseDateStringFieldValue);

		dateOptional.ifPresent(
			date -> similarResultsDocumentDisplayContext.setCreationDateString(
				formatCreationDate(date)));
	}

	protected void buildCreatorUserName(
		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext) {

		String creatorUserName = getFieldValueString(Field.USER_NAME);

		if (!Validator.isBlank(creatorUserName)) {
			similarResultsDocumentDisplayContext.setCreatorUserName(
				creatorUserName);
		}
		else {
			similarResultsDocumentDisplayContext.setCreatorUserName(
				StringPool.BLANK);
		}
	}

	protected void buildImage(
		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext,
		String className, long classPK, AssetRenderer<?> assetRenderer) {

		String thumbnailURLString = null;
		String assetClassName = BlogsEntry.class.getName();

		if (assetClassName.equals(className)) {
			BlogsEntry blogsEntry = (BlogsEntry)assetRenderer.getAssetObject();

			try {
				thumbnailURLString = blogsEntry.getCoverImageURL(_themeDisplay);

				if (Validator.isNull(thumbnailURLString)) {
					thumbnailURLString = blogsEntry.getSmallImageURL(
						_themeDisplay);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Blogs entry thumnamil url exception and contains " +
							"blogs entry id " + blogsEntry.getEntryId());
				}
			}

			similarResultsDocumentDisplayContext.setThumbnailURLString(
				thumbnailURLString);
			similarResultsDocumentDisplayContext.setIconId("blogs");

			return;
		}

		assetClassName = JournalArticle.class.getName();

		if (assetClassName.equals(className)) {
			try {
				thumbnailURLString = assetRenderer.getThumbnailPath(
					_renderRequest);
				similarResultsDocumentDisplayContext.setIconId("web-content");
				similarResultsDocumentDisplayContext.setThumbnailURLString(
					thumbnailURLString);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"JournalArticle entry thumnamil url exception and " +
							"contains journal article classPK " + classPK);
				}
			}

			return;
		}

		assetClassName = DLFolder.class.getName();

		if (assetClassName.equals(className)) {
			similarResultsDocumentDisplayContext.setIconId("folder");

			return;
		}

		assetClassName = DLFileEntry.class.getName();

		if (assetClassName.equals(className)) {
			Object assetObject = assetRenderer.getAssetObject();

			if (assetObject instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry)assetObject;

				similarResultsDocumentDisplayContext.setIconId(
					fileEntry.getIcon());

				try {
					thumbnailURLString = DLURLHelperUtil.getThumbnailSrc(
						fileEntry, _themeDisplay);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"File entry thumnamil url exception and contains " +
								"file classPK " + classPK);
					}
				}

				similarResultsDocumentDisplayContext.setThumbnailURLString(
					thumbnailURLString);
			}
			else {
				DLFileEntry dlFileEntry =
					(DLFileEntry)assetRenderer.getAssetObject();

				similarResultsDocumentDisplayContext.setIconId(
					dlFileEntry.getIcon());
			}
		}
	}

	protected void buildModelResource(
		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext,
		String className) {

		String modelResource = _resourceActions.getModelResource(
			_themeDisplay.getLocale(), className);

		if (!Validator.isBlank(modelResource)) {
			similarResultsDocumentDisplayContext.setModelResource(
				modelResource);
		}
	}

	protected SimilarResultsDocumentDisplayContext
		buildTemporarilyUnavailable() {

		SimilarResultsDocumentDisplayContext
			similarResultsDocumentDisplayContext =
				new SimilarResultsDocumentDisplayContext();

		similarResultsDocumentDisplayContext.setTemporarilyUnavailable(true);

		return similarResultsDocumentDisplayContext;
	}

	protected String formatCreationDate(Date date) {
		Format format = _fastDateFormatFactory.getDateTime(
			FastDateFormatConstants.MEDIUM, FastDateFormatConstants.SHORT,
			_locale, _themeDisplay.getTimeZone());

		return format.format(date);
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

	protected FileEntry getFileEntryByClassPK(long fileEntryId) {
		FileEntry fileEntry = null;

		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Documents and Media search index is stale and contains " +
						"file entry " + fileEntryId);
			}
		}

		return fileEntry;
	}

	protected Indexer<Object> getIndexer(String className) {
		if (_indexerRegistry != null) {
			return _indexerRegistry.getIndexer(className);
		}

		return IndexerRegistryUtil.getIndexer(className);
	}

	protected Summary getSummary(
			String className, AssetRenderer<?> assetRenderer)
		throws SearchException {

		SummaryBuilder summaryBuilder = _summaryBuilderFactory.newInstance();

		summaryBuilder.setHighlight(_highlightEnabled);

		Indexer<?> indexer = getIndexer(className);

		if (indexer != null) {
			String snippet = _document.getString(Field.SNIPPET);

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
			else if (assetRenderer != null) {
				summaryBuilder.setContent(
					assetRenderer.getSearchSummary(_locale));
				summaryBuilder.setLocale(_locale);
				summaryBuilder.setTitle(assetRenderer.getTitle(_locale));

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

	protected String getTitle(AssetEntry assetEntry, Summary summary) {
		String title = summary.getTitle();

		if (Validator.isBlank(title)) {
			title = assetEntry.getTitle(_locale);
		}

		return title;
	}

	protected String getViewURL(
		AssetEntry assetEntry, AssetRenderer<?> assetRenderer, String className,
		long classPK) {

		String urlString = _portal.getCurrentURL(_renderRequest);

		if (_similarResultsRoute == null) {
			return urlString;
		}

		SimilarResultsContributor similarResultsContributor =
			_similarResultsRoute.getContributor();

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(urlString, _http);

		DestinationHelper destinationHelper = new DestinationHelper() {

			@Override
			public AssetEntry getAssetEntry() {
				return assetEntry;
			}

			@Override
			public AssetRenderer<?> getAssetRenderer() {
				return assetRenderer;
			}

			@Override
			public String getClassName() {
				return className;
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

			@Override
			public Object getRouteParameter(String name) {
				return _similarResultsRoute.getRouteParameter(name);
			}

			@Override
			public String getUID() {
				return getFieldValueString(Field.UID);
			}

		};

		similarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		return destinationBuilderImpl.build();
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
		SimilarResultsDocumentDisplayContextBuilder.class);

	private AssetEntryLocalService _assetEntryLocalService;
	private Document _document;
	private DocumentBuilderFactory _documentBuilderFactory;
	private FastDateFormatFactory _fastDateFormatFactory;
	private boolean _highlightEnabled;
	private Http _http;
	private IndexerRegistry _indexerRegistry;
	private com.liferay.portal.kernel.search.Document _legacyDocument;
	private Locale _locale;
	private Portal _portal;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private ResourceActions _resourceActions;
	private final SimilarResultsRoute _similarResultsRoute;
	private SummaryBuilderFactory _summaryBuilderFactory;
	private ThemeDisplay _themeDisplay;

}