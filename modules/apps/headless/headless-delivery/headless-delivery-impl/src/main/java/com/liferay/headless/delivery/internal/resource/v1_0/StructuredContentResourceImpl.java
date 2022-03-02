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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.dto.v1_0.util.DDMFormValuesUtil;
import com.liferay.headless.delivery.dto.v1_0.util.DDMValueUtil;
import com.liferay.headless.delivery.dto.v1_0.util.StructuredContentUtil;
import com.liferay.headless.delivery.dynamic.data.mapping.DDMFormFieldUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.StructuredContentDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.DisplayPageRendererUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RenderedContentValueUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.search.aggregation.AggregationUtil;
import com.liferay.headless.delivery.search.filter.FilterUtil;
import com.liferay.headless.delivery.search.sort.SortUtil;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.exception.NoSuchFolderException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ContentLanguageUtil;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
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
	public void deleteSiteStructuredContentByExternalReferenceCode(
			Long siteId, String externalReferenceCode)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticleByExternalReferenceCode(
				siteId, externalReferenceCode);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());
	}

	@Override
	public void deleteStructuredContent(Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());
	}

	@Override
	public void deleteStructuredContentMyRating(Long structuredContentId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		spiRatingResource.deleteRating(structuredContentId);
	}

	@Override
	public Page<StructuredContent> getAssetLibraryStructuredContentsPage(
			Long assetLibraryId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getStructuredContentsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_ARTICLE, "postAssetLibraryStructuredContent",
					JournalConstants.RESOURCE_NAME, assetLibraryId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getAssetLibraryStructuredContentsPage",
					JournalConstants.RESOURCE_NAME, assetLibraryId)
			).build(),
			_createStructuredContentsPageBooleanQueryUnsafeConsumer(flatten),
			assetLibraryId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			Long contentStructureId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			contentStructureId);

		return _getStructuredContentsPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.VIEW, ddmStructure.getStructureId(),
					"getContentStructureStructuredContentsPage",
					ddmStructure.getUserId(), JournalConstants.RESOURCE_NAME,
					ddmStructure.getGroupId())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						com.liferay.portal.kernel.search.Field.CLASS_TYPE_ID,
						contentStructureId.toString()),
					BooleanClauseOccur.MUST);
			},
			null, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		List<EntityField> entityFields = null;

		long contentStructureId = GetterUtil.getLong(
			(String)multivaluedMap.getFirst("contentStructureId"));

		if (contentStructureId > 0) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				contentStructureId);

			entityFields = _entityFieldsProvider.provide(ddmStructure);
		}

		return new StructuredContentEntityModel(
			entityFields,
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(JournalArticle.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public StructuredContent getSiteStructuredContentByExternalReferenceCode(
			Long siteId, String externalReferenceCode)
		throws Exception {

		return _getStructuredContent(
			_journalArticleService.getLatestArticleByExternalReferenceCode(
				siteId, externalReferenceCode));
	}

	@Override
	public StructuredContent getSiteStructuredContentByKey(
			Long siteId, String key)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			siteId, key);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public StructuredContent getSiteStructuredContentByUuid(
			Long siteId, String uuid)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.getJournalArticleByUuidAndGroupId(
				uuid, siteId);

		_journalArticleModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			journalArticle.getResourcePrimKey(), ActionKeys.VIEW);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public Page<StructuredContent> getSiteStructuredContentsPage(
			Long siteId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getStructuredContentsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_ARTICLE, "postSiteStructuredContent",
					JournalConstants.RESOURCE_NAME, siteId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getSiteStructuredContentsPage",
					JournalConstants.RESOURCE_NAME, siteId)
			).build(),
			_createStructuredContentsPageBooleanQueryUnsafeConsumer(flatten),
			siteId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public StructuredContent getStructuredContent(Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				Long structuredContentFolderId, Boolean flatten, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _getStructuredContentsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_ARTICLE, journalFolder.getFolderId(),
					"postStructuredContentFolderStructuredContent",
					journalFolder.getUserId(), JournalConstants.RESOURCE_NAME,
					journalFolder.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, journalFolder.getFolderId(),
					"getStructuredContentFolderStructuredContentsPage",
					journalFolder.getUserId(), JournalConstants.RESOURCE_NAME,
					journalFolder.getGroupId())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				String field = com.liferay.portal.kernel.search.Field.FOLDER_ID;

				if (GetterUtil.getBoolean(flatten)) {
					field = com.liferay.portal.kernel.search.Field.TREE_PATH;
				}

				booleanFilter.add(
					new TermFilter(field, structuredContentFolderId.toString()),
					BooleanClauseOccur.MUST);
			},
			journalFolder.getGroupId(), search, aggregation, filter, pagination,
			sorts);
	}

	@Override
	public Rating getStructuredContentMyRating(Long structuredContentId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRating(structuredContentId);
	}

	@Override
	public String
			getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
				Long structuredContentId, String displayPageKey)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return DisplayPageRendererUtil.toHTML(
			JournalArticle.class.getName(), ddmStructure.getStructureId(),
			displayPageKey, journalArticle.getGroupId(),
			contextHttpServletRequest, contextHttpServletResponse,
			journalArticle, _infoItemServiceTracker,
			_layoutDisplayPageProviderTracker, _layoutLocalService,
			_layoutPageTemplateEntryService);
	}

	@Override
	public String getStructuredContentRenderedContentContentTemplate(
			Long structuredContentId, String templateId)
		throws Exception {

		return RenderedContentValueUtil.renderTemplate(
			_classNameLocalService, _ddmTemplateLocalService,
			_groupLocalService, contextHttpServletRequest,
			_journalArticleService, _journalContent,
			contextAcceptLanguage.getPreferredLocale(), structuredContentId,
			templateId, contextUriInfo);
	}

	@Override
	public StructuredContent patchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		if (!ArrayUtil.contains(
				journalArticle.getAvailableLanguageIds(),
				contextAcceptLanguage.getPreferredLanguageId())) {

			throw new BadRequestException(
				StringBundler.concat(
					"Unable to patch structured content with language ",
					LocaleUtil.toW3cLanguageId(
						contextAcceptLanguage.getPreferredLanguageId()),
					" because it is only available in the following languages ",
					LocaleUtil.toW3cLanguageIds(
						journalArticle.getAvailableLanguageIds())));
		}

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		_validateContentFields(
			structuredContent.getContentFields(), ddmStructure);

		LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
			structuredContent.getDatePublished(),
			journalArticle.getDisplayDate());

		return _toStructuredContent(
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				LocalizedMapUtil.patch(
					journalArticle.getTitleMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getTitle(),
					structuredContent.getTitle_i18n()),
				LocalizedMapUtil.patch(
					journalArticle.getDescriptionMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getDescription(),
					structuredContent.getDescription_i18n()),
				LocalizedMapUtil.patch(
					journalArticle.getFriendlyURLMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getFriendlyUrlPath(),
					structuredContent.getFriendlyUrlPath_i18n()),
				_journalConverter.getContent(
					ddmStructure,
					_toPatchedFields(
						structuredContent.getContentFields(), journalArticle),
					journalArticle.getGroupId()),
				journalArticle.getDDMStructureKey(),
				_getDDMTemplateKey(ddmStructure),
				journalArticle.getLayoutUuid(),
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				_createServiceContext(
					journalArticle.getGroupId(), structuredContent)));
	}

	@Override
	public StructuredContent postAssetLibraryStructuredContent(
			Long assetLibraryId, StructuredContent structuredContent)
		throws Exception {

		return postSiteStructuredContent(assetLibraryId, structuredContent);
	}

	@Override
	public StructuredContent postSiteStructuredContent(
			Long siteId, StructuredContent structuredContent)
		throws Exception {

		return _addStructuredContent(
			structuredContent.getExternalReferenceCode(), siteId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, structuredContent);
	}

	@Override
	public StructuredContent postStructuredContentFolderStructuredContent(
			Long structuredContentFolderId, StructuredContent structuredContent)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _addStructuredContent(
			structuredContent.getExternalReferenceCode(),
			journalFolder.getGroupId(), structuredContentFolderId,
			structuredContent);
	}

	@Override
	public Rating postStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), structuredContentId);
	}

	@Override
	public StructuredContent putSiteStructuredContentByExternalReferenceCode(
			Long siteId, String externalReferenceCode,
			StructuredContent structuredContent)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.
				fetchLatestArticleByExternalReferenceCode(
					siteId, externalReferenceCode);

		if (journalArticle != null) {
			return _updateStructuredContent(journalArticle, structuredContent);
		}

		return _addStructuredContent(
			externalReferenceCode, siteId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, structuredContent);
	}

	@Override
	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _updateStructuredContent(journalArticle, structuredContent);
	}

	@Override
	public Rating putStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), structuredContentId);
	}

	@Override
	public void putStructuredContentSubscribe(Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		_journalArticleService.subscribe(
			journalArticle.getGroupId(), journalArticle.getResourcePrimKey());
	}

	@Override
	public void putStructuredContentUnsubscribe(Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		_journalArticleService.unsubscribe(
			journalArticle.getGroupId(), journalArticle.getResourcePrimKey());
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle((Long)id);

		return journalArticle.getGroupId();
	}

	@Override
	protected String getPermissionCheckerPortletName(Object id) {
		return JournalConstants.RESOURCE_NAME;
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return JournalArticle.class.getName();
	}

	private StructuredContent _addStructuredContent(
			String externalReferenceCode, Long groupId,
			Long parentStructuredContentFolderId,
			StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			structuredContent.getContentStructureId());

		LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
			structuredContent.getDatePublished());

		Map<Locale, String> titleMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getTitle(), structuredContent.getTitle_i18n());

		Map<Locale, String> descriptionMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getDescription(),
			structuredContent.getDescription_i18n());

		Set<Locale> notFoundLocales = new HashSet<>(descriptionMap.keySet());

		Map<Locale, String> friendlyUrlMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getFriendlyUrlPath(),
			structuredContent.getFriendlyUrlPath_i18n(), titleMap);

		notFoundLocales.addAll(friendlyUrlMap.keySet());

		LocalizedMapUtil.validateI18n(
			true, LocaleUtil.getSiteDefault(), "Structured content", titleMap,
			notFoundLocales);

		_validateContentFields(
			structuredContent.getContentFields(), ddmStructure);

		return _toStructuredContent(
			_journalArticleService.addArticle(
				externalReferenceCode, groupId, parentStructuredContentFolderId,
				0, 0, null, true, titleMap, descriptionMap, friendlyUrlMap,
				StructuredContentUtil.getJournalArticleContent(
					_ddm,
					DDMFormValuesUtil.toDDMFormValues(
						structuredContent.getContentFields(),
						ddmStructure.getDDMForm(), _dlAppService, groupId,
						_journalArticleService, _layoutLocalService,
						contextAcceptLanguage.getPreferredLocale(),
						_getRootDDMFormFields(ddmStructure)),
					_jsonDDMFormValuesSerializer, _ddmFormValuesValidator,
					ddmStructure, _journalConverter),
				ddmStructure.getStructureKey(),
				_getDDMTemplateKey(ddmStructure), null,
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null, _createServiceContext(groupId, structuredContent)));
	}

	private ServiceContext _createServiceContext(
		long groupId, StructuredContent structuredContent) {

		ServiceContext serviceContext =
			ServiceContextRequestUtil.createServiceContext(
				structuredContent.getTaxonomyCategoryIds(),
				structuredContent.getKeywords(),
				_getExpandoBridgeAttributes(structuredContent), groupId,
				contextHttpServletRequest,
				structuredContent.getViewableByAsString());

		Optional.ofNullable(
			structuredContent.getPriority()
		).ifPresent(
			serviceContext::setAssetPriority
		);

		return serviceContext;
	}

	private UnsafeConsumer<BooleanQuery, Exception>
		_createStructuredContentsPageBooleanQueryUnsafeConsumer(
			Boolean flatten) {

		return booleanQuery -> {
			if (!GetterUtil.getBoolean(flatten)) {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						com.liferay.portal.kernel.search.Field.FOLDER_ID,
						String.valueOf(
							JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)),
					BooleanClauseOccur.MUST);
			}
		};
	}

	private String _getDDMTemplateKey(DDMStructure ddmStructure) {
		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		if (ddmTemplates.isEmpty()) {
			return StringPool.BLANK;
		}

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		return ddmTemplate.getTemplateKey();
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		StructuredContent structuredContent) {

		return CustomFieldsUtil.toMap(
			JournalArticle.class.getName(), contextCompany.getCompanyId(),
			structuredContent.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private List<DDMFormField> _getRootDDMFormFields(
		DDMStructure ddmStructure) {

		return TransformUtil.transform(
			ddmStructure.getRootFieldNames(),
			fieldName -> DDMFormFieldUtil.getDDMFormField(
				_ddmStructureService, ddmStructure, fieldName));
	}

	private SPIRatingResource<Rating> _getSPIRatingResource() {
		return new SPIRatingResource<>(
			JournalArticle.class.getName(), _ratingsEntryLocalService,
			ratingsEntry -> {
				JournalArticle journalArticle =
					_journalArticleService.getLatestArticle(
						ratingsEntry.getClassPK());

				return RatingUtil.toRating(
					HashMapBuilder.put(
						"create",
						addAction(
							ActionKeys.VIEW,
							journalArticle.getResourcePrimKey(),
							"postStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"delete",
						addAction(
							ActionKeys.VIEW,
							journalArticle.getResourcePrimKey(),
							"deleteStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"get",
						addAction(
							ActionKeys.VIEW,
							journalArticle.getResourcePrimKey(),
							"getStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"replace",
						addAction(
							ActionKeys.VIEW,
							journalArticle.getResourcePrimKey(),
							"putStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).build(),
					_portal, ratingsEntry, _userLocalService);
			},
			contextUser);
	}

	private StructuredContent _getStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		ContentLanguageUtil.addContentLanguageHeader(
			journalArticle.getAvailableLanguageIds(),
			journalArticle.getDefaultLanguageId(), contextHttpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		return _toStructuredContent(journalArticle);
	}

	private Page<StructuredContent> _getStructuredContentsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Long siteId, String keywords, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions, booleanQueryUnsafeConsumer,
			FilterUtil.processFilter(_ddmIndexer, filter),
			JournalArticle.class.getName(), keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				com.liferay.portal.kernel.search.Field.ARTICLE_ID,
				com.liferay.portal.kernel.search.Field.SCOPE_GROUP_ID),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					com.liferay.portal.kernel.search.Field.STATUS,
					WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (siteId != null) {
					searchContext.setGroupIds(new long[] {siteId});
				}

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				AggregationUtil.processVulcanAggregation(
					_aggregations, _ddmIndexer, _queries, searchRequestBuilder,
					aggregation);

				SortUtil.processSorts(
					_ddmIndexer, searchRequestBuilder, searchContext.getSorts(),
					_queries, _sorts);
			},
			sorts, this::_toStructuredContent);
	}

	private Fields _toFields(
			ContentField[] contentFields, JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		ServiceContext serviceContext = new ServiceContext();

		DDMFormValues ddmFormValues = DDMFormValuesUtil.toDDMFormValues(
			contentFields, ddmStructure.getDDMForm(), _dlAppService,
			journalArticle.getGroupId(), _journalArticleService,
			_layoutLocalService, contextAcceptLanguage.getPreferredLocale(),
			_getRootDDMFormFields(ddmStructure));

		serviceContext.setAttribute(
			"ddmFormValues",
			DDMFormValuesUtil.getContent(
				_jsonDDMFormValuesSerializer, ddmStructure.getDDMForm(),
				ddmFormValues.getDDMFormFieldValues()));

		return _ddm.getFields(ddmStructure.getStructureId(), serviceContext);
	}

	private Fields _toPatchedFields(
			ContentField[] contentFields, JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields fields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		if (ArrayUtil.isEmpty(contentFields)) {
			return fields;
		}

		for (Field field : fields) {
			if (field.isRepeatable()) {
				throw new BadRequestException(
					"Unable to patch a structured content with a repeatable " +
						"field. Instead, update the structured content.");
			}
		}

		DDMFormValues ddmFormValues = DDMFormValuesUtil.toDDMFormValues(
			contentFields, ddmStructure.getDDMForm(), _dlAppService,
			journalArticle.getGroupId(), _journalArticleService,
			_layoutLocalService, contextAcceptLanguage.getPreferredLocale(),
			_getRootDDMFormFields(ddmStructure));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap = stream.collect(
			Collectors.toMap(
				DDMFormFieldValue::getFieldReference, Function.identity()));

		for (ContentField contentField : contentFields) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValuesMap.get(
				contentField.getName());

			if (ddmFormFieldValue == null) {
				continue;
			}

			Field field = fields.get(ddmFormFieldValue.getName());

			Value value = DDMValueUtil.toDDMValue(
				contentField,
				DDMFormFieldUtil.getDDMFormField(
					_ddmStructureService, ddmStructure, contentField.getName()),
				_dlAppService, journalArticle.getGroupId(),
				_journalArticleService, _layoutLocalService,
				contextAcceptLanguage.getPreferredLocale());

			field.setValue(
				contextAcceptLanguage.getPreferredLocale(),
				value.getString(contextAcceptLanguage.getPreferredLocale()));

			ContentField[] nestedContentFields =
				contentField.getNestedContentFields();

			if (nestedContentFields != null) {
				_toPatchedFields(nestedContentFields, journalArticle);
			}
		}

		ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		_ddmFormValuesValidator.validate(ddmFormValues);

		return fields;
	}

	private StructuredContent _toStructuredContent(Document document)
		throws Exception {

		try {
			return _toStructuredContent(
				_journalArticleService.getLatestArticle(
					GetterUtil.getLong(
						document.get(
							com.liferay.portal.kernel.search.Field.
								SCOPE_GROUP_ID)),
					document.get(
						com.liferay.portal.kernel.search.Field.ARTICLE_ID),
					WorkflowConstants.STATUS_APPROVED));
		}
		catch (NoSuchFolderException noSuchFolderException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFolderException);
			}

			return null;
		}
	}

	private StructuredContent _toStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		return _structuredContentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, journalArticle.getResourcePrimKey(),
						"deleteStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, journalArticle.getResourcePrimKey(),
						"getStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"get-rendered-content",
					addAction(
						ActionKeys.VIEW, journalArticle.getResourcePrimKey(),
						"getStructuredContentRenderedContentContentTemplate",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"get-rendered-content-by-display-page",
					addAction(
						ActionKeys.VIEW, journalArticle.getResourcePrimKey(),
						"getStructuredContentRenderedContentByDisplayPage" +
							"DisplayPageKey",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"replace",
					addAction(
						ActionKeys.UPDATE, journalArticle.getResourcePrimKey(),
						"putStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"subscribe",
					addAction(
						ActionKeys.SUBSCRIBE,
						journalArticle.getResourcePrimKey(),
						"putStructuredContentSubscribe",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"unsubscribe",
					addAction(
						ActionKeys.SUBSCRIBE,
						journalArticle.getResourcePrimKey(),
						"putStructuredContentUnsubscribe",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, journalArticle.getResourcePrimKey(),
						"patchStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				journalArticle.getResourcePrimKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			journalArticle);
	}

	private StructuredContent _updateStructuredContent(
			JournalArticle journalArticle, StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Map<Locale, String> titleMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getTitle(), structuredContent.getTitle_i18n(),
			journalArticle.getTitleMap());

		Map<Locale, String> descriptionMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getDescription(),
			structuredContent.getDescription_i18n(),
			journalArticle.getDescriptionMap());

		Set<Locale> notFoundLocales = new HashSet<>(descriptionMap.keySet());

		Map<Locale, String> friendlyUrlMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			structuredContent.getFriendlyUrlPath(),
			structuredContent.getFriendlyUrlPath_i18n(),
			journalArticle.getFriendlyURLMap());

		friendlyUrlMap =
			friendlyUrlMap.isEmpty() ? journalArticle.getFriendlyURLMap() :
				friendlyUrlMap;

		notFoundLocales.addAll(friendlyUrlMap.keySet());

		LocalizedMapUtil.validateI18n(
			false, LocaleUtil.getSiteDefault(), "Structured content", titleMap,
			notFoundLocales);

		_validateContentFields(
			structuredContent.getContentFields(), ddmStructure);

		LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
			structuredContent.getDatePublished(),
			journalArticle.getDisplayDate());

		return _toStructuredContent(
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				titleMap, descriptionMap, friendlyUrlMap,
				_journalConverter.getContent(
					ddmStructure,
					_toFields(
						structuredContent.getContentFields(), journalArticle),
					journalArticle.getGroupId()),
				journalArticle.getDDMStructureKey(),
				_getDDMTemplateKey(ddmStructure),
				journalArticle.getLayoutUuid(),
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				_createServiceContext(
					journalArticle.getGroupId(), structuredContent)));
	}

	private void _validateContentFields(
		ContentField[] contentFields, DDMStructure ddmStructure) {

		if (ArrayUtil.isEmpty(contentFields)) {
			return;
		}

		for (ContentField contentField : contentFields) {
			DDMFormField ddmFormField = DDMFormFieldUtil.getDDMFormField(
				_ddmStructureService, ddmStructure, contentField.getName());

			if (ddmFormField == null) {
				throw new BadRequestException(
					StringBundler.concat(
						"Unable to get content field value for \"",
						contentField.getName(), "\" for content structure ",
						ddmStructure.getStructureId()));
			}

			_validateContentFields(
				contentField.getNestedContentFields(), ddmStructure);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentResourceImpl.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private EntityFieldsProvider _entityFieldsProvider;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

	@Reference
	private StructuredContentDTOConverter _structuredContentDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}