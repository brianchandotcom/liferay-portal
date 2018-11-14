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

package com.liferay.structured.content.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalHelper;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.structure.apio.architect.identifier.ContentStructureIdentifier;
import com.liferay.structure.apio.architect.util.StructureFieldConverter;
import com.liferay.structured.content.apio.architect.identifier.StructuredContentIdentifier;
import com.liferay.structured.content.apio.architect.resource.StructuredContentField;
import com.liferay.structured.content.apio.architect.util.StructuredContentUtil;
import com.liferay.structured.content.apio.internal.architect.filter.StructuredContentEntityModel;
import com.liferay.structured.content.apio.internal.architect.form.StructuredContentCreatorForm;
import com.liferay.structured.content.apio.internal.architect.form.StructuredContentUpdaterForm;
import com.liferay.structured.content.apio.internal.model.JournalArticleWrapper;
import com.liferay.structured.content.apio.internal.model.RenderedJournalArticle;
import com.liferay.structured.content.apio.internal.util.JournalArticleContentHelper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Provides the information necessary to expose a StructuredContent resources
 * through a web API. The resources are mapped from the internal model {@code
 * JournalArticle}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = NestedCollectionResource.class)
public class StructuredContentNestedCollectionResource
	implements NestedCollectionResource
		<JournalArticleWrapper, Long, StructuredContentIdentifier, Long,
		 ContentSpaceIdentifier> {

	public static String encodeFilterAndSortIdentifier(
		DDMStructure ddmStructure, String name) {

		return StringBundler.concat(
			StringPool.UNDERLINE, ddmStructure.getStructureId(),
			StringPool.UNDERLINE, name);
	}

	@Override
	public NestedCollectionRoutes<JournalArticleWrapper, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<JournalArticleWrapper, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems, AcceptLanguage.class, ThemeDisplay.class,
			Filter.class, Sort.class
		).addCreator(
			this::_addJournalArticle, AcceptLanguage.class, ThemeDisplay.class,
			_hasPermission.forAddingIn(ContentSpaceIdentifier.class),
			StructuredContentCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "structured-contents";
	}

	@Override
	public ItemRoutes<JournalArticleWrapper, Long> itemRoutes(
		ItemRoutes.Builder<JournalArticleWrapper, Long> builder) {

		return builder.addGetter(
			this::_getJournalArticleWrapper, AcceptLanguage.class,
			ThemeDisplay.class
		).addRemover(
			idempotent(this::_deleteJournalArticle), _hasPermission::forDeleting
		).addUpdater(
			this::_updateJournalArticle, AcceptLanguage.class,
			ThemeDisplay.class, _hasPermission::forUpdating,
			StructuredContentUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<JournalArticleWrapper> representor(
		Representor.Builder<JournalArticleWrapper, Long> builder) {

		return builder.types(
			"StructuredContent"
		).identifier(
			JournalArticle::getResourcePrimKey
		).addBidirectionalModel(
			"contentSpace", "structuredContents", ContentSpaceIdentifier.class,
			JournalArticle::getGroupId
		).addDate(
			"dateCreated", JournalArticle::getCreateDate
		).addDate(
			"dateModified", JournalArticle::getModifiedDate
		).addDate(
			"datePublished", JournalArticle::getDisplayDate
		).addDate(
			"lastReviewed", JournalArticle::getReviewDate
		).addLinkedModel(
			"aggregateRating", AggregateRatingIdentifier.class,
			this::_createClassNameClassPK
		).addLinkedModel(
			"contentStructure", ContentStructureIdentifier.class,
			this::_getJournalArticleStructureId
		).addLinkedModel(
			"creator", PersonIdentifier.class, JournalArticle::getUserId
		).addLocalizedStringByLocale(
			"description", JournalArticleWrapper::getDescription
		).addLocalizedStringByLocale(
			"title", JournalArticle::getTitle
		).addNestedList(
			"renderedContentsByTemplate", this::_getRenderedJournalArticles,
			nestedBuilder -> nestedBuilder.types(
				"templates"
			).addLocalizedStringByLocale(
				"template", RenderedJournalArticle::getTemplateName
			).addLocalizedStringByLocale(
				"renderedContent", RenderedJournalArticle::getRenderedContent
			).build()
		).addNestedList(
			"values", this::_getStructuredContentFields,
			fieldValuesBuilder -> fieldValuesBuilder.types(
				"ContentFieldValue"
			).addLinkedModel(
				"document", MediaObjectIdentifier.class,
				structuredContentField -> Try.fromFallible(
					() -> structuredContentField.getLocalizedValue(
						LocaleUtil.getDefault())
				).map(
					value -> StructuredContentUtil.getFileEntryId(
						value, _dlAppService)
				).orElse(
					null
				)
			).addLinkedModel(
				"structuredContent", StructuredContentIdentifier.class,
				this::_getStructuredContentId
			).addLocalizedStringByLocale(
				"label", StructuredContentField::getLocalizedLabel
			).addLocalizedStringByLocale(
				"value", StructuredContentField::getLocalizedValue
			).addNested(
				"geo", this::_getGeoJSONObject,
				geoBuilder -> geoBuilder.types(
					"GeoCoordinates"
				).addNumber(
					"latitude", jsonObject -> jsonObject.getDouble("latitude")
				).addNumber(
					"longitude", jsonObject -> jsonObject.getDouble("longitude")
				).build()
			).addRelativeURL(
				"link", this::_getLink
			).addString(
				"dataType", StructuredContentField::getDataType
			).addString(
				"filterAndSortIdentifier",
				StructuredContentField::getFilterAndSortIdentifier
			).addString(
				"inputControl", StructuredContentField::getInputControl
			).addString(
				"name", StructuredContentField::getName
			).build()
		).addRelatedCollection(
			"category", CategoryIdentifier.class
		).addRelatedCollection(
			"comment", CommentIdentifier.class
		).addStringList(
			"availableLanguages",
			journalArticle -> Arrays.asList(
				LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds()))
		).addStringList(
			"keywords", this::_getJournalArticleAssetTags
		).build();
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + StructuredContentEntityModel.NAME + ")",
		unbind = "unbind"
	)
	protected void setEntityModel(EntityModel entityModel) {
		if (_log.isInfoEnabled()) {
			_log.info("Binding " + entityModel);
		}

		_entityModel = entityModel;
	}

	protected void unbind(EntityModel entityModel) {
		if (_log.isInfoEnabled()) {
			_log.info("Unbinding " + entityModel);
		}

		_entityModel = null;
	}

	private JournalArticleWrapper _addJournalArticle(
			long contentSpaceId,
			StructuredContentCreatorForm structuredContentCreatorForm,
			AcceptLanguage acceptLanguage, ThemeDisplay themeDisplay)
		throws PortalException {

		Long ddmStructureId =
			structuredContentCreatorForm.getContentStructureId();

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			ddmStructureId);

		Locale locale = themeDisplay.getLocale();

		String content =
			_journalArticleContentHelper.createJournalArticleContent(
				structuredContentCreatorForm.getStructuredContentValuesForms(),
				ddmStructure, locale);

		String ddmStructureKey = ddmStructure.getStructureKey();
		String ddmTemplateKey = _getDDMTemplateKey(ddmStructure);
		Calendar calendar = Calendar.getInstance();

		ServiceContext serviceContext =
			structuredContentCreatorForm.getServiceContext(contentSpaceId);

		JournalArticle journalArticle = _journalArticleService.addArticle(
			contentSpaceId, 0, 0, 0, null, true,
			structuredContentCreatorForm.getTitleMap(locale),
			structuredContentCreatorForm.getDescriptionMap(locale), content,
			ddmStructureKey, ddmTemplateKey, null,
			_getDefaultValue(
				structuredContentCreatorForm.getPublishedDateMonthOptional(),
				calendar.get(Calendar.MONTH)),
			_getDefaultValue(
				structuredContentCreatorForm.getPublishedDateDayOptional(),
				calendar.get(Calendar.DATE)),
			_getDefaultValue(
				structuredContentCreatorForm.getPublishedDateYearOptional(),
				calendar.get(Calendar.YEAR)),
			_getDefaultValue(
				structuredContentCreatorForm.getPublishedDateHourOptional(),
				calendar.get(Calendar.HOUR)),
			_getDefaultValue(
				structuredContentCreatorForm.getPublishedDateMinuteOptional(),
				calendar.get(Calendar.MINUTE)),
			0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, null,
			serviceContext);

		return new JournalArticleWrapper(
			journalArticle, acceptLanguage.getPreferredLocale(), themeDisplay);
	}

	private ClassNameClassPK _createClassNameClassPK(
		JournalArticle journalArticle) {

		return ClassNameClassPK.create(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());
	}

	private SearchContext _createSearchContext(
		long companyId, long groupId, Locale locale, Sort sort, int start,
		int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		List<com.liferay.portal.kernel.search.Sort> sorts = _getSorts(
			sort.getSortFields(), locale);

		if (!sorts.isEmpty()) {
			searchContext.setSorts(
				sorts.toArray(new com.liferay.portal.kernel.search.Sort[0]));
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private void _deleteJournalArticle(long journalArticleId)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			journalArticleId);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());
	}

	private String _getDDMTemplateKey(DDMStructure ddmStructure) {
		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		return ddmTemplate.getTemplateKey();
	}

	private <T> T _getDefaultValue(Optional<T> optional, T defaultValue) {
		return optional.orElse(defaultValue);
	}

	private Query _getFullQuery(
			Filter filter, Locale locale, SearchContext searchContext)
		throws SearchException {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		com.liferay.portal.kernel.search.filter.Filter searchFilter =
			_getSearchFilter(filter, locale);

		if (searchFilter != null) {
			BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

			preBooleanFilter.add(searchFilter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private JSONObject _getGeoJSONObject(
		StructuredContentField structuredContentField) {

		return Try.fromFallible(
			() -> structuredContentField.getLocalizedValue(
				LocaleUtil.getDefault())
		).filter(
			StructuredContentUtil::isJSONObject
		).filter(
			string -> string.contains("latitude")
		).map(
			JSONFactoryUtil::createJSONObject
		).orElse(
			null
		);
	}

	private JournalArticle _getJournalArticle(JSONObject jsonObject)
		throws PortalException {

		long classPK = jsonObject.getLong("classPK");

		return _journalArticleService.getLatestArticle(classPK);
	}

	private List<String> _getJournalArticleAssetTags(
		JournalArticle journalArticle) {

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		return ListUtil.toList(assetTags, AssetTag::getName);
	}

	private Long _getJournalArticleStructureId(
		JournalArticleWrapper journalArticleWrapper) {

		DDMStructure ddmStructure = journalArticleWrapper.getDDMStructure();

		return ddmStructure.getStructureId();
	}

	private JournalArticleWrapper _getJournalArticleWrapper(
			long journalArticleId, AcceptLanguage acceptLanguage,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			journalArticleId);

		return new JournalArticleWrapper(
			journalArticle, acceptLanguage.getPreferredLocale(), themeDisplay);
	}

	private String _getLayoutLink(JSONObject jsonObject)
		throws PortalException {

		long groupId = jsonObject.getLong("groupId");
		boolean privateLayout = jsonObject.getBoolean("privateLayout");
		long layoutId = jsonObject.getLong("layoutId");

		Layout layoutByUuidAndGroupId = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		return layoutByUuidAndGroupId.getFriendlyURL();
	}

	private String _getLink(StructuredContentField structuredContentField) {
		return Try.fromFallible(
			() -> structuredContentField.getLocalizedValue(
				LocaleUtil.getDefault())
		).filter(
			StructuredContentUtil::isJSONObject
		).filter(
			string -> string.contains("layoutId")
		).map(
			JSONFactoryUtil::createJSONObject
		).map(
			this::_getLayoutLink
		).orElse(
			null
		);
	}

	private PageItems<JournalArticleWrapper> _getPageItems(
			Pagination pagination, long contentSpaceId,
			AcceptLanguage acceptLanguage, ThemeDisplay themeDisplay,
			Filter filter, Sort sort)
		throws PortalException {

		SearchContext searchContext = _createSearchContext(
			themeDisplay.getCompanyId(), contentSpaceId,
			acceptLanguage.getPreferredLocale(), sort,
			pagination.getStartPosition(), pagination.getEndPosition());

		Query fullQuery = _getFullQuery(
			filter, acceptLanguage.getPreferredLocale(), searchContext);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Hits hits = null;

		if (permissionChecker != null) {
			if (searchContext.getUserId() == 0) {
				searchContext.setUserId(permissionChecker.getUserId());
			}

			SearchResultPermissionFilter searchResultPermissionFilter =
				_searchResultPermissionFilterFactory.create(
					searchContext1 -> IndexSearcherHelperUtil.search(
						searchContext1, fullQuery),
					permissionChecker);

			hits = searchResultPermissionFilter.search(searchContext);
		}
		else {
			hits = IndexSearcherHelperUtil.search(searchContext, fullQuery);
		}

		List<JournalArticleWrapper> journalArticleWrappers = Stream.of(
			_journalHelper.getArticles(hits)
		).flatMap(
			List::stream
		).map(
			journalArticle -> new JournalArticleWrapper(
				journalArticle, acceptLanguage.getPreferredLocale(),
				themeDisplay)
		).collect(
			Collectors.toList()
		);

		return new PageItems<>(journalArticleWrappers, hits.getLength());
	}

	private String _getRenderedContent(
		JournalArticleWrapper journalArticleWrapper, DDMTemplate ddmTemplate) {

		Locale locale = journalArticleWrapper.getLocale();

		return Try.fromFallible(
			() -> _journalContent.getDisplay(
				journalArticleWrapper.getGroupId(),
				journalArticleWrapper.getArticleId(),
				ddmTemplate.getTemplateKey(), null, locale.toString(),
				journalArticleWrapper.getThemeDisplay())
		).map(
			JournalArticleDisplay::getContent
		).map(
			content -> content.replaceAll("[\\t\\n]", "")
		).orElse(
			null
		);
	}

	private List<RenderedJournalArticle> _getRenderedJournalArticles(
		JournalArticleWrapper journalArticleWrapper) {

		DDMStructure ddmStructure = journalArticleWrapper.getDDMStructure();

		return Stream.of(
			ddmStructure.getTemplates()
		).flatMap(
			List::stream
		).map(
			ddmTemplate -> RenderedJournalArticle.create(
				ddmTemplate::getName,
				locale -> _getRenderedContent(
					journalArticleWrapper, ddmTemplate))
		).collect(
			Collectors.toList()
		);
	}

	@SuppressWarnings("unchecked")
	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
		Filter filter, Locale locale) {

		if ((filter == null) || (filter == Filter.emptyFilter())) {
			return null;
		}

		try {
			return _expressionConvert.convert(
				filter.getExpression(), locale, _entityModel);
		}
		catch (Exception e) {
			throw new InvalidFilterException(
				"Invalid filter: " + e.getMessage(), e);
		}
	}

	private List<com.liferay.portal.kernel.search.Sort> _getSorts(
		List<SortField> sortFields, Locale locale) {

		Stream<SortField> stream = sortFields.stream();

		return stream.map(
			sortField -> new com.liferay.portal.kernel.search.Sort(
				sortField.getSortableFieldName(locale),
				!sortField.isAscending())
		).collect(
			Collectors.toList()
		);
	}

	private List<StructuredContentField> _getStructuredContentFields(
		JournalArticle journalArticle) {

		return Try.fromFallible(
			() ->
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class)
		).map(
			assetRendererFactory -> assetRendererFactory.getAssetRenderer(
				journalArticle, AssetRendererFactory.TYPE_LATEST_APPROVED)
		).map(
			AssetRenderer::getDDMFormValuesReader
		).map(
			DDMFormValuesReader::getDDMFormValues
		).map(
			DDMFormValues::getDDMFormFieldValues
		).map(
			ddmFormFieldValueList -> _toStructuredContentFields(
				ddmFormFieldValueList, journalArticle.getDDMStructure())
		).map(
			this::_getStructuredContentFields
		).orElse(
			null
		);
	}

	private List<StructuredContentField> _getStructuredContentFields(
		List<StructuredContentField> structuredContentFields) {

		Stream<StructuredContentField> stream =
			structuredContentFields.stream();

		List<StructuredContentField> nestedStructureContentFields =
			stream.flatMap(
				structuredContentField -> _getStructuredContentFields(
					structuredContentField.getNestedStructuredContentFields()
				).stream()
			).collect(
				Collectors.toList()
			);

		nestedStructureContentFields.addAll(structuredContentFields);

		return nestedStructureContentFields;
	}

	private Long _getStructuredContentId(
		StructuredContentField structuredContentField) {

		return Try.fromFallible(
			() -> structuredContentField.getLocalizedValue(
				LocaleUtil.getDefault())
		).filter(
			StructuredContentUtil::isJSONObject
		).map(
			JSONFactoryUtil::createJSONObject
		).map(
			this::_getJournalArticle
		).map(
			JournalArticle::getResourcePrimKey
		).orElse(
			null
		);
	}

	private List<StructuredContentField> _toStructuredContentFields(
		List<DDMFormFieldValue> ddmFormFieldValues, DDMStructure ddmStructure) {

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		return stream.filter(
			ddmFormFieldValue -> !Objects.equals(
				DDMFormFieldType.SEPARATOR, ddmFormFieldValue.getType())
		).map(
			ddmFormFieldValue -> new StructuredContentFieldImpl(
				ddmFormFieldValue, ddmStructure)
		).collect(
			Collectors.toList()
		);
	}

	private JournalArticleWrapper _updateJournalArticle(
			long journalArticleId,
			StructuredContentUpdaterForm structuredContentUpdaterForm,
			AcceptLanguage acceptLanguage, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			journalArticleId);

		ServiceContext serviceContext =
			structuredContentUpdaterForm.getServiceContext(
				journalArticle.getGroupId());

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Locale locale = acceptLanguage.getPreferredLocale();

		String content =
			_journalArticleContentHelper.createJournalArticleContent(
				structuredContentUpdaterForm.getStructuredContentValuesForms(),
				ddmStructure, locale);

		String ddmTemplateKey = _getDDMTemplateKey(ddmStructure);

		Date displayDate = journalArticle.getDisplayDate();

		JournalArticle updatedJournalArticle =
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				_getDefaultValue(
					structuredContentUpdaterForm.getTitleMapOptional(locale),
					journalArticle.getTitleMap()),
				_getDefaultValue(
					structuredContentUpdaterForm.getDescriptionMapOptional(
						locale),
					journalArticle.getDescriptionMap()),
				journalArticle.getFriendlyURLMap(), content,
				journalArticle.getDDMStructureKey(), ddmTemplateKey,
				journalArticle.getLayoutUuid(),
				_getDefaultValue(
					structuredContentUpdaterForm.
						getPublishedDateMonthOptional(),
					displayDate.getMonth()),
				_getDefaultValue(
					structuredContentUpdaterForm.getPublishedDateDayOptional(),
					displayDate.getDate()),
				_getDefaultValue(
					structuredContentUpdaterForm.getPublishedDateYearOptional(),
					displayDate.getYear()),
				_getDefaultValue(
					structuredContentUpdaterForm.getPublishedDateHourOptional(),
					displayDate.getHours()),
				_getDefaultValue(
					structuredContentUpdaterForm.
						getPublishedDateMinuteOptional(),
					displayDate.getMinutes()),
				0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null,
				null, null, null, serviceContext);

		return new JournalArticleWrapper(
			updatedJournalArticle, locale, themeDisplay);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentNestedCollectionResource.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DLAppService _dlAppService;

	private volatile EntityModel _entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleContentHelper _journalArticleContentHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private StructureFieldConverter _structureFieldConverter;

	private class StructuredContentFieldImpl implements StructuredContentField {

		public StructuredContentFieldImpl(
			DDMFormFieldValue ddmFormFieldValue, DDMStructure ddmStructure) {

			_ddmFormFieldValue = ddmFormFieldValue;
			_ddmStructure = ddmStructure;
		}

		@Override
		public String getDataType() {
			try {
				String dataType = _ddmStructure.getFieldDataType(
					_ddmFormFieldValue.getName());

				return _structureFieldConverter.getFieldDataType(dataType);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get data type for field name " +
							_ddmFormFieldValue.getName(),
						pe);
				}

				return null;
			}
		}

		@Override
		public String getFilterAndSortIdentifier() {
			return encodeFilterAndSortIdentifier(
				_ddmStructure, _ddmFormFieldValue.getName());
		}

		@Override
		public String getInputControl() {
			return Try.fromFallible(
				() -> _ddmStructure.getFieldType(_ddmFormFieldValue.getName())
			).map(
				fieldType ->
					_structureFieldConverter.getFieldInputControl(fieldType)
			).recover(
				pe -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get input control for field name " +
								_ddmFormFieldValue.getName(),
							pe);
					}

					return null;
				}
			);
		}

		@Override
		public String getLocalizedLabel(Locale locale) {
			try {
				return _ddmStructure.getFieldLabel(
					_ddmFormFieldValue.getName(), locale);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to get localized label for value name ",
							_ddmFormFieldValue.getName(), " and locale ",
							locale),
						pe);
				}

				return null;
			}
		}

		@Override
		public String getLocalizedValue(Locale locale) {
			Value value = _ddmFormFieldValue.getValue();

			String localizedValue = value.getString(locale);

			if (!StructuredContentUtil.isJSONObject(localizedValue)) {
				return localizedValue;
			}

			return null;
		}

		@Override
		public String getName() {
			return _ddmFormFieldValue.getName();
		}

		@Override
		public List<StructuredContentField> getNestedStructuredContentFields() {
			return _toStructuredContentFields(
				_ddmFormFieldValue.getNestedDDMFormFieldValues(),
				_ddmStructure);
		}

		private final DDMFormFieldValue _ddmFormFieldValue;
		private final DDMStructure _ddmStructure;

	}

}