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
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.StructuredContentDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.DDMFormValuesUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.DDMValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RenderedContentValueUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
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
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
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

		return getSiteStructuredContentsPage(
			assetLibraryId, flatten, search, aggregation, filter, pagination,
			sorts);
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
					"VIEW", ddmStructure.getStructureId(),
					"getContentStructureStructuredContentsPage",
					ddmStructure.getUserId(), "com.liferay.journal",
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
			ddmStructure.getGroupId(), search, aggregation, filter, pagination,
			sorts);
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
					"ADD_ARTICLE", "postSiteStructuredContent",
					"com.liferay.journal", siteId)
			).put(
				"get",
				addAction(
					"VIEW", "getSiteStructuredContentsPage",
					"com.liferay.journal", siteId)
			).build(),
			booleanQuery -> {
				if (!GetterUtil.getBoolean(flatten)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							com.liferay.portal.kernel.search.Field.FOLDER_ID,
							String.valueOf(
								JournalFolderConstants.
									DEFAULT_PARENT_FOLDER_ID)),
						BooleanClauseOccur.MUST);
				}
			},
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
					"ADD_ARTICLE", journalFolder.getFolderId(),
					"postStructuredContentFolderStructuredContent",
					journalFolder.getUserId(), "com.liferay.journal",
					journalFolder.getGroupId())
			).put(
				"get",
				addAction(
					"VIEW", journalFolder.getFolderId(),
					"getStructuredContentFolderStructuredContentsPage",
					journalFolder.getUserId(), "com.liferay.journal",
					journalFolder.getGroupId())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				String field = com.liferay.portal.kernel.search.Field.FOLDER_ID;

				if (GetterUtil.getBoolean(flatten)) {
					field = "treePath";
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
	public String getStructuredContentRenderedContentTemplate(
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
						structuredContent.getContentFields(), journalArticle)),
				journalArticle.getDDMStructureKey(),
				_getDDMTemplateKey(ddmStructure),
				journalArticle.getLayoutUuid(),
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				ServiceContextRequestUtil.createServiceContext(
					structuredContent.getTaxonomyCategoryIds(),
					structuredContent.getKeywords(),
					_getExpandoBridgeAttributes(structuredContent),
					journalArticle.getGroupId(), contextHttpServletRequest,
					structuredContent.getViewableByAsString())));
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
			siteId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			structuredContent);
	}

	@Override
	public StructuredContent postStructuredContentFolderStructuredContent(
			Long structuredContentFolderId, StructuredContent structuredContent)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _addStructuredContent(
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
	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

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
						structuredContent.getContentFields(), journalArticle)),
				journalArticle.getDDMStructureKey(),
				_getDDMTemplateKey(ddmStructure),
				journalArticle.getLayoutUuid(),
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				ServiceContextRequestUtil.createServiceContext(
					structuredContent.getTaxonomyCategoryIds(),
					structuredContent.getKeywords(),
					_getExpandoBridgeAttributes(structuredContent),
					journalArticle.getGroupId(), contextHttpServletRequest,
					structuredContent.getViewableByAsString())));
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
		return "com.liferay.journal";
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return JournalArticle.class.getName();
	}

	private StructuredContent _addStructuredContent(
			Long siteId, Long parentStructuredContentFolderId,
			StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = _checkDDMStructurePermission(
			structuredContent);

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
				siteId, parentStructuredContentFolderId, 0, 0, null, true,
				titleMap, descriptionMap, friendlyUrlMap,
				_createJournalArticleContent(
					DDMFormValuesUtil.toDDMFormValues(
						structuredContent.getContentFields(),
						ddmStructure.getDDMForm(), _dlAppService, siteId,
						_journalArticleService, _layoutLocalService,
						contextAcceptLanguage.getPreferredLocale(),
						_getRootDDMFormFields(ddmStructure)),
					ddmStructure),
				ddmStructure.getStructureKey(),
				_getDDMTemplateKey(ddmStructure), null,
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null,
				null,
				ServiceContextRequestUtil.createServiceContext(
					structuredContent.getTaxonomyCategoryIds(),
					structuredContent.getKeywords(),
					_getExpandoBridgeAttributes(structuredContent), siteId,
					contextHttpServletRequest,
					structuredContent.getViewableByAsString())));
	}

	private DDMStructure _checkDDMStructurePermission(
			StructuredContent structuredContent)
		throws Exception {

		try {
			return _ddmStructureService.getStructure(
				structuredContent.getContentStructureId());
		}
		catch (PrincipalException.MustHavePermission principalException) {
			throw new ForbiddenException(
				"You do not have permission to create a structured content " +
					"using the content structure ID " +
						structuredContent.getContentStructureId(),
				principalException);
		}
	}

	private String _createJournalArticleContent(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws Exception {

		_validateDDMFormValues(ddmFormValues);

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
							setDDMFormFieldValues(
								ddmFormValues.getDDMFormFieldValues());
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

	private DDMFormField _getDDMFormField(
		DDMStructure ddmStructure, String name) {

		try {
			return ddmStructure.getDDMFormField(name);
		}
		catch (Exception exception) {
			throw new BadRequestException(
				StringBundler.concat(
					"Unable to get content field value for \"", name,
					"\" for content structure ", ddmStructure.getStructureId()),
				exception);
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

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		StructuredContent structuredContent) {

		return CustomFieldsUtil.toMap(
			JournalArticle.class.getName(), contextCompany.getCompanyId(),
			structuredContent.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private List<DDMFormField> _getRootDDMFormFields(
		DDMStructure ddmStructure) {

		return transform(
			ddmStructure.getRootFieldNames(),
			fieldName -> _getDDMFormField(ddmStructure, fieldName));
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
							"UPDATE", journalArticle.getResourcePrimKey(),
							"postStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"delete",
						addAction(
							"UPDATE", journalArticle.getResourcePrimKey(),
							"deleteStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"get",
						addAction(
							"VIEW", journalArticle.getResourcePrimKey(),
							"getStructuredContentMyRating",
							journalArticle.getUserId(),
							JournalArticle.class.getName(),
							journalArticle.getGroupId())
					).put(
						"replace",
						addAction(
							"UPDATE", journalArticle.getResourcePrimKey(),
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
			actions, booleanQueryUnsafeConsumer, filter, JournalArticle.class,
			keywords, pagination,
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
			},
			sorts,
			document -> _toStructuredContent(
				_journalArticleService.getLatestArticle(
					GetterUtil.getLong(
						document.get(
							com.liferay.portal.kernel.search.Field.
								SCOPE_GROUP_ID)),
					document.get(
						com.liferay.portal.kernel.search.Field.ARTICLE_ID),
					WorkflowConstants.STATUS_APPROVED)));
	}

	private Fields _toFields(
			ContentField[] contentFields, JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields fields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		ServiceContext serviceContext = new ServiceContext();

		DDMFormValues ddmFormValues = DDMFormValuesUtil.toDDMFormValues(
			contentFields, ddmStructure.getDDMForm(), _dlAppService,
			journalArticle.getGroupId(), _journalArticleService,
			_layoutLocalService, contextAcceptLanguage.getPreferredLocale(),
			_getRootDDMFormFields(ddmStructure));

		serviceContext.setAttribute("ddmFormValues", _toString(ddmFormValues));

		Fields newFields = _ddm.getFields(
			ddmStructure.getStructureId(), serviceContext);

		for (Field field : fields) {
			Field newField = newFields.get(field.getName());

			field.setValues(
				contextAcceptLanguage.getPreferredLocale(),
				newField.getValues(contextAcceptLanguage.getPreferredLocale()));
		}

		return fields;
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

		for (ContentField contentField : contentFields) {
			Field field = fields.get(contentField.getName());

			Value value = DDMValueUtil.toDDMValue(
				contentField,
				_getDDMFormField(ddmStructure, contentField.getName()),
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

		DDMFormValues ddmFormValues = _journalConverter.getDDMFormValues(
			ddmStructure, fields);

		_validateDDMFormValues(ddmFormValues);

		return fields;
	}

	private String _toString(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
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
						"DELETE", journalArticle.getResourcePrimKey(),
						"deleteStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"get",
					addAction(
						"VIEW", journalArticle.getResourcePrimKey(),
						"getStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"get-rendered-content",
					addAction(
						"VIEW", journalArticle.getResourcePrimKey(),
						"getStructuredContentRenderedContentTemplate",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"replace",
					addAction(
						"UPDATE", journalArticle.getResourcePrimKey(),
						"putStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"subscribe",
					addAction(
						"SUBSCRIBE", journalArticle.getResourcePrimKey(),
						"putStructuredContentSubscribe",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"unsubscribe",
					addAction(
						"SUBSCRIBE", journalArticle.getResourcePrimKey(),
						"putStructuredContentUnsubscribe",
						journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).put(
					"update",
					addAction(
						"UPDATE", journalArticle.getResourcePrimKey(),
						"patchStructuredContent", journalArticle.getUserId(),
						JournalArticle.class.getName(),
						journalArticle.getGroupId())
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				journalArticle.getResourcePrimKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private void _validateContentFields(
		ContentField[] contentFields, DDMStructure ddmStructure) {

		if (ArrayUtil.isEmpty(contentFields)) {
			return;
		}

		for (ContentField contentField : contentFields) {
			DDMFormField ddmFormField = _getDDMFormField(
				ddmStructure, contentField.getName());

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

	private void _validateDDMFormValues(DDMFormValues ddmFormValues) {
		try {
			_ddmFormValuesValidator.validate(ddmFormValues);
		}
		catch (DDMFormValuesValidationException
					ddmFormValuesValidationException) {

			throw new BadRequestException(
				"Validation error: " +
					ddmFormValuesValidationException.getMessage(),
				ddmFormValuesValidationException);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

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
	private GroupLocalService _groupLocalService;

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
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private StructuredContentDTOConverter _structuredContentDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}