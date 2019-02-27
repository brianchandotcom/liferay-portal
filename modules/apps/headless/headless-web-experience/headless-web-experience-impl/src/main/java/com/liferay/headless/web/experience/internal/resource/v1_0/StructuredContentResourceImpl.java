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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import static com.liferay.portal.vulcan.util.LocalDateTimeUtil.toLocalDateTime;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.headless.web.experience.dto.v1_0.Categories;
import com.liferay.headless.web.experience.dto.v1_0.ContentDocument;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsURL;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.time.LocalDateTime;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {EntityModelResource.class, StructuredContentResource.class}
)
public class StructuredContentResourceImpl
	extends BaseStructuredContentResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteStructuredContent(Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());

		return true;
	}

	@Override
	public Page<StructuredContent>
			getContentSpaceContentStructureStructuredContentsPage(
				Long contentSpaceId, Long contentStructureId, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(JournalArticle.class),
			pagination,
			booleanQuery -> {
				if (contentStructureId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.CLASS_TYPE_ID, contentStructureId.toString()),
						BooleanClauseOccur.MUST);
				}
			},
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ARTICLE_ID, Field.SCOPE_GROUP_ID),
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			_searchResultPermissionFilterFactory, sorts);

		return Page.of(
			transform(
				_journalHelper.getArticles(hits), this::_toStructuredContent),
			pagination, hits.getLength());
	}

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return getContentSpaceContentStructureStructuredContentsPage(
			contentSpaceId, null, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		List<EntityField> entityFields = null;

		Long contentStructureId = GetterUtil.getLong(
			(String)multivaluedMap.getFirst("content-structure-id"));

		if (contentStructureId > 0) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				contentStructureId);

			entityFields = _entityFieldsProvider.provide(ddmStructure);
		}
		else {
			entityFields = Collections.emptyList();
		}

		return new StructuredContentEntityModel(entityFields);
	}

	@Override
	public StructuredContent getStructuredContent(Long structuredContentId)
		throws Exception {

		return _toStructuredContent(
			_journalArticleService.getLatestArticle(structuredContentId));
	}

	@Override
	public String getStructuredContentTemplate(
			Long structuredContentId, Long templateId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, _contextHttpServletRequest,
			new DummyHttpServletResponse());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLocale(_contextHttpServletRequest.getLocale());
		themeDisplay.setScopeGroupId(journalArticle.getGroupId());
		themeDisplay.setSiteGroupId(journalArticle.getGroupId());

		DDMTemplate ddmTemplate = _ddmTemplateService.getTemplate(templateId);

		JournalArticleDisplay journalArticleDisplay =
			_journalContent.getDisplay(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				ddmTemplate.getTemplateKey(), null,
				LocaleUtil.toLanguageId(
					contextAcceptLanguage.getPreferredLocale()),
				themeDisplay);

		String content = journalArticleDisplay.getContent();

		return content.replaceAll("[\\t\\n]", "");
	}

	@Override
	public StructuredContent postContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			structuredContent.getContentStructureId());
		LocalDateTime localDateTime = toLocalDateTime(
			structuredContent.getDatePublished());

		return _toStructuredContent(
			_journalArticleService.addArticle(
				contentSpaceId, 0, 0, 0, null, true,
				new HashMap<Locale, String>() {
					{
						put(
							contextAcceptLanguage.getPreferredLocale(),
							structuredContent.getTitle());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							contextAcceptLanguage.getPreferredLocale(),
							structuredContent.getDescription());
					}
				},
				_createJournalArticleContent(
					_toDDMFormFieldValues(
						structuredContent.getContentFields(), ddmStructure,
						contextAcceptLanguage.getPreferredLocale()),
					ddmStructure),
				ddmStructure.getStructureKey(),
				_getDDMTemplateKey(ddmStructure), null,
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, null,
				_getServiceContext(contentSpaceId, structuredContent)));
	}

	@Override
	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();
		LocalDateTime localDateTime = toLocalDateTime(
			structuredContent.getDatePublished(),
			journalArticle.getDisplayDate());

		return _toStructuredContent(
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				LocalizedMapUtil.merge(
					journalArticle.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						structuredContent.getTitle())),
				LocalizedMapUtil.merge(
					journalArticle.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						structuredContent.getDescription())),
				LocalizedMapUtil.merge(
					journalArticle.getFriendlyURLMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						structuredContent.getTitle())),
				_journalConverter.getContent(
					ddmStructure,
					_toDDMFields(
						structuredContent.getContentFields(), journalArticle)),
				journalArticle.getDDMStructureKey(),
				_getDDMTemplateKey(ddmStructure),
				journalArticle.getLayoutUuid(),
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				_getServiceContext(
					journalArticle.getGroupId(), structuredContent)));
	}

	private String _createJournalArticleContent(
			List<DDMFormFieldValue> ddmFormFieldValues,
			DDMStructure ddmStructure)
		throws Exception {

		Locale originalSiteDefaultLocale =
			LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()));

			ServiceContext serviceContext = new ServiceContext();

			DDMForm ddmForm = ddmStructure.getDDMForm();

			serviceContext.setAttribute(
				"ddmFormValues",
				_toString(
					new DDMFormValues(ddmForm) {
						{
							setAvailableLocales(ddmForm.getAvailableLocales());
							setDDMFormFieldValues(ddmFormFieldValues);
							setDefaultLocale(ddmForm.getDefaultLocale());
						}
					}));

			return _journalConverter.getContent(
				ddmStructure,
				_ddm.getFields(ddmStructure.getStructureId(), serviceContext));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(originalSiteDefaultLocale);
		}
	}

	private Categories[] _getCategories(JournalArticle journalArticle) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> new Categories() {
				{
					setCategoryId(assetCategory.getCategoryId());
					setCategoryName(assetCategory.getName());
				}
			}
		).toArray(
			Categories[]::new
		);
	}

	private String _getDDMTemplateKey(DDMStructure ddmStructure) {
		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		if (ddmTemplates.isEmpty()) {
			return StringPool.BLANK;
		}

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		return ddmTemplate.getTemplateKey();
	}

	private RenderedContentsURL[] _getRenderedContentsURLs(
		DDMStructure ddmStructure, JournalArticle journalArticle) {

		List<RenderedContentsURL> renderedContentsURLs = transform(
			ddmStructure.getTemplates(),
			ddmTemplate -> new RenderedContentsURL() {
				{
					renderedContentURL = getJAXRSLink(
						"getStructuredContentTemplate",
						journalArticle.getResourcePrimKey(),
						ddmTemplate.getTemplateId());
					templateName = ddmTemplate.getName(
						contextAcceptLanguage.getPreferredLocale());
				}
			});

		return renderedContentsURLs.toArray(
			new RenderedContentsURL[renderedContentsURLs.size()]);
	}

	private ServiceContext _getServiceContext(
		long contentSpaceId, StructuredContent structuredContent) {

		return new ServiceContext() {
			{
				setAddGroupPermissions(true);
				setAddGuestPermissions(true);

				if (structuredContent.getKeywords() != null) {
					setAssetTagNames(structuredContent.getKeywords());
				}

				setScopeGroupId(contentSpaceId);
			}
		};
	}

	private ContentField _toContentField(
			DDMStructure ddmStructure, Fields fields, String fieldName)
		throws Exception {

		com.liferay.dynamic.data.mapping.storage.Field ddmField = fields.get(
			fieldName);

		if (ddmField == null) {
			return null;
		}

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(fieldName);

		return new ContentField() {
			{
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				name = ddmField.getName();
				nestedFields = _toContentFields(
					ddmFormField.getNestedDDMFormFields(), ddmStructure,
					fields);
				value = _toValue(
					ddmField, contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private ContentField[] _toContentFields(JournalArticle journalArticle)
		throws Exception {

		List<ContentField> contentFields = new ArrayList<>();

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields ddmFields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		List<String> rootFieldNames = ddmStructure.getRootFieldNames();

		for (String rootFieldName : rootFieldNames) {
			ContentField contentFieldValue = _toContentField(
				ddmStructure, ddmFields, rootFieldName);

			if (contentFieldValue != null) {
				contentFields.add(contentFieldValue);
			}
		}

		return contentFields.toArray(new ContentField[contentFields.size()]);
	}

	private ContentField[] _toContentFields(
			List<DDMFormField> ddmFormFields, DDMStructure ddmStructure,
			Fields fields)
		throws Exception {

		List<ContentField> contentFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			ContentField contentFieldValue = _toContentField(
				ddmStructure, fields, ddmFormField.getName());

			if (contentFieldValue != null) {
				contentFields.add(contentFieldValue);
			}
		}

		return contentFields.toArray(new ContentField[contentFields.size()]);
	}

	private Fields _toDDMFields(
			ContentField[] contentFields, JournalArticle journalArticle)
		throws PortalException {

		Fields ddmFields = _journalConverter.getDDMFields(
			journalArticle.getDDMStructure(), journalArticle.getContent());

		List<DDMFormFieldValue> ddmFormFieldValues = _toDDMFormFieldValues(
			contentFields, journalArticle.getDDMStructure(),
			contextAcceptLanguage.getPreferredLocale());

		Iterator<com.liferay.dynamic.data.mapping.storage.Field> iterator =
			ddmFields.iterator();

		while (iterator.hasNext()) {
			com.liferay.dynamic.data.mapping.storage.Field ddmField =
				iterator.next();

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				if (Objects.equals(
						ddmFormFieldValue.getName(), ddmField.getName())) {

					ddmField.addValue(
						contextAcceptLanguage.getPreferredLocale(),
						ddmFormFieldValue.getValue());
				}
			}
		}

		return ddmFields;
	}

	private List<DDMFormFieldValue> _toDDMFormFieldValues(
		ContentField[] contentFields, DDMStructure ddmStructure,
		Locale locale) {

		if (contentFields == null) {
			return Collections.emptyList();
		}

		return transform(
			Arrays.asList(contentFields),
			contentFieldValue -> new DDMFormFieldValue() {
				{
					setName(contentFieldValue.getName());
					setNestedDDMFormFields(
						_toDDMFormFieldValues(
							contentFieldValue.getNestedFields(), ddmStructure,
							locale));
					setValue(
						_toDDMValue(contentFieldValue, ddmStructure, locale));
				}
			});
	}

	private com.liferay.dynamic.data.mapping.model.Value _toDDMValue(
			ContentField contentFieldValue, DDMStructure ddmStructure,
			Locale locale)
		throws PortalException {

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			contentFieldValue.getName());

		final Value value = contentFieldValue.getValue();

		if (ddmFormField.isLocalizable()) {
			return new LocalizedValue() {
				{
					if (Objects.equals(
							DDMFormFieldType.DOCUMENT_LIBRARY,
							ddmFormField.getType())) {

						FileEntry fileEntry = _dlAppService.getFileEntry(
							value.getDocumentId());

						addString(
							locale,
							JSONUtil.put(
								"alt", value.getData()
							).put(
								"classPK", fileEntry.getFileEntryId()
							).put(
								"fileEntryId", fileEntry.getFileEntryId()
							).put(
								"groupId", fileEntry.getGroupId()
							).put(
								"name", fileEntry.getFileName()
							).put(
								"resourcePrimKey", fileEntry.getPrimaryKey()
							).put(
								"title", fileEntry.getFileName()
							).put(
								"type", "document"
							).put(
								"uuid", fileEntry.getUuid()
							).toString());
					}
					else if (Objects.equals(
								DDMFormFieldType.JOURNAL_ARTICLE,
								ddmFormField.getType())) {

						JournalArticle journalArticle =
							_journalArticleService.getLatestArticle(
								value.getStructuredContentId());

						addString(
							locale,
							JSONUtil.put(
								"className", JournalArticle.class.getName()
							).put(
								"classPK", journalArticle.getResourcePrimKey()
							).put(
								"title", journalArticle.getTitle()
							).toString());
					}
					else {
						addString(locale, value.getData());
					}
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			Geo geo = value.getGeo();

			return new UnlocalizedValue(
				JSONUtil.put(
					"latitude", geo.getLatitude()
				).put(
					"longitude", geo.getLongitude()
				).toString());
		}

		return new UnlocalizedValue(value.getData());
	}

	private String _toString(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			_ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	private StructuredContent _toStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				categories = _getCategories(journalArticle);
				contentFields = _toContentFields(journalArticle);
				contentSpace = journalArticle.getGroupId();
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = journalArticle.getResourcePrimKey();
				lastReviewed = journalArticle.getReviewDate();
				renderedContentsURL = _getRenderedContentsURLs(
					ddmStructure, journalArticle);
				title = journalArticle.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private Value _toValue(
			com.liferay.dynamic.data.mapping.storage.Field ddmField,
			Locale locale)
		throws Exception {

		DDMStructure ddmStructure = ddmField.getDDMStructure();

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			ddmField.getName());

		if (Objects.equals(
				DDMFormFieldType.DOCUMENT_LIBRARY, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(ddmField.getValue(locale)));

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntry(classPK);

			return new Value() {
				{
					document = new ContentDocument() {
						{
							creator = CreatorUtil.toCreator(
								_portal,
								_userLocalService.getUser(
									fileEntry.getUserId()));
							contentUrl = _dlurlHelper.getPreviewURL(
								fileEntry, fileEntry.getFileVersion(), null, "",
								false, false);
							dateCreated = fileEntry.getCreateDate();
							dateModified = fileEntry.getModifiedDate();
							encodingFormat = fileEntry.getMimeType();
							fileExtension = fileEntry.getExtension();
							id = fileEntry.getFileEntryId();
							sizeInBytes = fileEntry.getSize();
							title = fileEntry.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			DDMForm ddmForm = ddmFormField.getDDMForm();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(ddmField.getValue(ddmForm.getDefaultLocale())));

			return new Value() {
				{
					geo = new Geo() {
						{
							latitude = jsonObject.getDouble("latitude");
							longitude = jsonObject.getDouble("longitude");
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.JOURNAL_ARTICLE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(ddmField.getValue(locale)));

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			JournalArticle journalArticle =
				_journalArticleService.getLatestArticle(classPK);

			return new Value() {
				{
					structuredContent = _toStructuredContent(journalArticle);
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.LINK_TO_PAGE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(ddmField.getValue(locale)));

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId == 0) {
				return null;
			}

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			Layout layoutByUuidAndGroupId = _layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			return new Value() {
				{
					link = layoutByUuidAndGroupId.getFriendlyURL();
				}
			};
		}

		return new Value() {
			{
				data = String.valueOf(ddmField.getValue(locale));
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private EntityFieldsProvider _entityFieldsProvider;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}