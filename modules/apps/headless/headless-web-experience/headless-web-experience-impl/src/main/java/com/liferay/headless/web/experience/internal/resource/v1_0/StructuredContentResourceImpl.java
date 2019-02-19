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
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.dto.v1_0.Values;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentDocumentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.GeoImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValueImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValuesImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.time.LocalDateTime;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
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
			queryConfig -> {
				queryConfig.setSelectedFieldNames(
					Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);
			},
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(company.getCompanyId());
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
							acceptLanguage.getPreferredLocale(),
							structuredContent.getTitle());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							acceptLanguage.getPreferredLocale(),
							structuredContent.getDescription());
					}
				},
				_createJournalArticleContent(
					ddmStructure, structuredContent.getValues()),
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

		Fields ddmFields = _toDDMFields(
			journalArticle, structuredContent.getValues());

		return _toStructuredContent(
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				LocalizedMapUtil.merge(
					journalArticle.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						acceptLanguage.getPreferredLocale(),
						structuredContent.getTitle())),
				LocalizedMapUtil.merge(
					journalArticle.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						acceptLanguage.getPreferredLocale(),
						structuredContent.getDescription())),
				LocalizedMapUtil.merge(
					journalArticle.getFriendlyURLMap(),
					new AbstractMap.SimpleEntry<>(
						acceptLanguage.getPreferredLocale(),
						structuredContent.getTitle())),
				_journalConverter.getContent(ddmStructure, ddmFields),
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
			DDMStructure ddmStructure,
			List<DDMFormFieldValue> ddmFormFieldValues)
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
							setDefaultLocale(ddmForm.getDefaultLocale());
							setDDMFormFieldValues(ddmFormFieldValues);
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

	private String _createJournalArticleContent(
			DDMStructure ddmStructure, Values[] values)
		throws Exception {

		List<DDMFormFieldValue> ddmFormFieldValues = _toDDMFormFieldValues(
			ddmStructure, acceptLanguage.getPreferredLocale(), values);

		return _createJournalArticleContent(ddmStructure, ddmFormFieldValues);
	}

	private String _getDDMTemplateKey(DDMStructure ddmStructure) {
		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		if (ddmTemplates.isEmpty()) {
			return StringPool.BLANK;
		}

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		return ddmTemplate.getTemplateKey();
	}

	private ServiceContext _getServiceContext(
		long contentSpaceId, StructuredContent structuredContent) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (structuredContent.getKeywords() != null) {
			serviceContext.setAssetTagNames(structuredContent.getKeywords());
		}

		serviceContext.setScopeGroupId(contentSpaceId);

		return serviceContext;
	}

	private Fields _toDDMFields(JournalArticle journalArticle, Values[] values)
		throws PortalException {

		Fields ddmFields = _journalConverter.getDDMFields(
			journalArticle.getDDMStructure(), journalArticle.getContent());

		List<DDMFormFieldValue> ddmFormFieldValues = _toDDMFormFieldValues(
			journalArticle.getDDMStructure(),
			acceptLanguage.getPreferredLocale(), values);

		Iterator<com.liferay.dynamic.data.mapping.storage.Field> iterator =
			ddmFields.iterator();

		while (iterator.hasNext()) {
			com.liferay.dynamic.data.mapping.storage.Field field =
				iterator.next();

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				if (Objects.equals(
						ddmFormFieldValue.getName(), field.getName())) {

					field.addValue(
						acceptLanguage.getPreferredLocale(),
						ddmFormFieldValue.getValue());
				}
			}
		}

		return ddmFields;
	}

	private List<DDMFormFieldValue> _toDDMFormFieldValues(
			DDMStructure ddmStructure, Locale locale, Values[] valuesArray)
		throws PortalException {

		if ((valuesArray == null) || (valuesArray.length == 0)) {
			return Collections.emptyList();
		}

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		for (Values values : valuesArray) {
			ddmFormFieldValues.add(
				new DDMFormFieldValue() {
					{
						setName(values.getName());
						setValue(_toDDMValue(ddmStructure, locale, values));
					}
				});
		}

		return ddmFormFieldValues;
	}

	private com.liferay.dynamic.data.mapping.model.Value _toDDMValue(
			DDMStructure ddmStructure, Locale locale, Values values)
		throws PortalException {

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			values.getName());

		final Value value = values.getValue();

		if (ddmFormField.isLocalizable()) {
			return new LocalizedValue() {
				{
					if (Objects.equals(
							DDMFormFieldType.DOCUMENT_LIBRARY,
							ddmFormField.getType())) {

						FileEntry fileEntry = _dlAppService.getFileEntry(
							value.getDocumentId());

						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject();

						jsonObject.put("alt", value.getData());
						jsonObject.put("classPK", fileEntry.getFileEntryId());
						jsonObject.put(
							"fileEntryId", fileEntry.getFileEntryId());
						jsonObject.put("groupId", fileEntry.getGroupId());
						jsonObject.put("name", fileEntry.getFileName());
						jsonObject.put(
							"resourcePrimKey", fileEntry.getPrimaryKey());
						jsonObject.put("title", fileEntry.getFileName());
						jsonObject.put("type", "document");
						jsonObject.put("uuid", fileEntry.getUuid());

						addString(locale, jsonObject.toString());
					}
					else if (Objects.equals(
								DDMFormFieldType.JOURNAL_ARTICLE,
								ddmFormField.getType())) {

						JournalArticle journalArticle =
							_journalArticleService.getLatestArticle(
								value.getStructuredContentId());

						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject();

						jsonObject.put(
							"className", JournalArticle.class.getName());
						jsonObject.put(
							"classPK", journalArticle.getResourcePrimKey());
						jsonObject.put("title", journalArticle.getTitle());

						addString(locale, jsonObject.toString());
					}
					else {
						addString(locale, value.getData());
					}
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			Geo geo = value.getGeo();

			jsonObject.put("latitude", geo.getLatitude());
			jsonObject.put("longitude", geo.getLongitude());

			return new UnlocalizedValue(jsonObject.toString());
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

		return new StructuredContentImpl() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				contentSpace = journalArticle.getGroupId();
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					acceptLanguage.getPreferredLocale());
				id = journalArticle.getResourcePrimKey();
				lastReviewed = journalArticle.getReviewDate();
				title = journalArticle.getTitle(
					acceptLanguage.getPreferredLocale());
				values = _toValues(journalArticle);
			}
		};
	}

	private Value _toValue(
			Locale locale,
			com.liferay.dynamic.data.mapping.storage.Field ddmField)
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

			return new ValueImpl() {
				{
					setDocument(
						new ContentDocumentImpl() {
							{
								setCreator(
									CreatorUtil.toCreator(
										_portal,
										_userLocalService.getUser(
											fileEntry.getUserId())));
								setContentUrl(
									_dlurlHelper.getPreviewURL(
										fileEntry, fileEntry.getFileVersion(),
										null, "", false, false));
								setDateCreated(fileEntry.getCreateDate());
								setDateModified(fileEntry.getModifiedDate());
								setEncodingFormat(fileEntry.getMimeType());
								setFileExtension(fileEntry.getExtension());
								setId(fileEntry.getFileEntryId());
								setTitle(fileEntry.getTitle());
								setSizeInBytes(fileEntry.getSize());
							}
						});
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			DDMForm ddmForm = ddmFormField.getDDMForm();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(ddmField.getValue(ddmForm.getDefaultLocale())));

			return new ValueImpl() {
				{
					setGeo(
						new GeoImpl() {
							{
								setLatitude(jsonObject.getDouble("latitude"));
								setLongitude(jsonObject.getDouble("longitude"));
							}
						});
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

			return new ValueImpl() {
				{
					setStructuredContent(_toStructuredContent(journalArticle));
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

			return new ValueImpl() {
				{
					setLink(layoutByUuidAndGroupId.getFriendlyURL());
				}
			};
		}

		return new ValueImpl() {
			{
				setData(String.valueOf(ddmField.getValue(locale)));
			}
		};
	}

	private Values[] _toValues(JournalArticle journalArticle) throws Exception {
		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields ddmFields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		List<Values> values = new ArrayList<>();

		Iterator<com.liferay.dynamic.data.mapping.storage.Field> iterator =
			ddmFields.iterator();

		while (iterator.hasNext()) {
			com.liferay.dynamic.data.mapping.storage.Field ddmField =
				iterator.next();

			DDMFormField ddmFormField = ddmStructure.getDDMFormField(
				ddmField.getName());

			values.add(
				new ValuesImpl() {
					{
						setDataType(
							ContentStructureUtil.toDataType(ddmFormField));
						setInputControl(
							ContentStructureUtil.toInputControl(ddmFormField));
						setName(ddmField.getName());
						setValue(
							_toValue(
								acceptLanguage.getPreferredLocale(), ddmField));
					}
				});
		}

		return values.toArray(new Values[0]);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DDMStructureService _ddmStructureService;

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