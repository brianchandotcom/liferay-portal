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

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
				_createJournalArticleContent(ddmStructure),
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
				_createJournalArticleContent(ddmStructure),
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

	private String _createJournalArticleContent(DDMStructure ddmStructure)
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
			}
		};
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DDMStructureService _ddmStructureService;

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
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}