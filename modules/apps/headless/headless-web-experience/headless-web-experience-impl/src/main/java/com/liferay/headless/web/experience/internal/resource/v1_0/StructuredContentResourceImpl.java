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

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.DDMFormValuesUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.DDMValueUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.StructuredContentUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
import com.liferay.portal.vulcan.util.ContentLanguageUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.time.LocalDateTime;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
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
	public StructuredContent getContentSpaceStructuredContentByKey(
			Long contentSpaceId, String key)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			contentSpaceId, key);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public StructuredContent getContentSpaceStructuredContentByUuid(
			Long contentSpaceId, String uuid)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.fetchJournalArticleByUuidAndGroupId(
				uuid, contentSpaceId);

		_journalArticleModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			journalArticle.getResourcePrimKey(), ActionKeys.VIEW);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getStructuredContentsPage(
			contentSpaceId, null, filter, pagination, sorts);
	}

	@Override
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			Long contentStructureId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getStructuredContentsPage(
			null, contentStructureId, filter, pagination, sorts);
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

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _getStructuredContent(journalArticle);
	}

	@Override
	public String getStructuredContentRenderedContentTemplate(
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
				contextAcceptLanguage.getPreferredLanguageId(), themeDisplay);

		String content = journalArticleDisplay.getContent();

		return content.replaceAll("[\\t\\n]", "");
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
					contextAcceptLanguage.getPreferredLanguageId(),
					" because it is only available in the following languages ",
					journalArticle.getAvailableLanguageIds()));
		}

		DDMStructure ddmStructure = journalArticle.getDDMStructure();
		LocalDateTime localDateTime = toLocalDateTime(
			structuredContent.getDatePublished(),
			journalArticle.getDisplayDate());

		return _toStructuredContent(
			_journalArticleService.updateArticle(
				journalArticle.getGroupId(), journalArticle.getFolderId(),
				journalArticle.getArticleId(), journalArticle.getVersion(),
				LocalizedMapUtil.patch(
					journalArticle.getTitleMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getTitle()),
				LocalizedMapUtil.patch(
					journalArticle.getDescriptionMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getDescription()),
				LocalizedMapUtil.patch(
					journalArticle.getFriendlyURLMap(),
					contextAcceptLanguage.getPreferredLocale(),
					structuredContent.getTitle()),
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
				ServiceContextUtil.createServiceContext(
					structuredContent.getKeywords(),
					structuredContent.getTaxonomyCategoryIds(),
					journalArticle.getGroupId(),
					structuredContent.getViewableByAsString())));
	}

	@Override
	public StructuredContent postContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = _checkDDMStructurePermission(
			structuredContent);

		LocalDateTime localDateTime = toLocalDateTime(
			structuredContent.getDatePublished());

		if (!LocaleUtil.equals(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()),
				contextAcceptLanguage.getPreferredLocale())) {

			String w3cLanguageId = LocaleUtil.toW3cLanguageId(
				ddmStructure.getDefaultLanguageId());

			throw new BadRequestException(
				"Structured contents can only be created with the default " +
					"language " + w3cLanguageId);
		}

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
					DDMFormValuesUtil.toDDMFormValues(
						structuredContent.getContentFields(),
						ddmStructure.getDDMForm(), _dlAppService,
						contentSpaceId, _journalArticleService,
						_layoutLocalService,
						contextAcceptLanguage.getPreferredLocale(),
						_getRootDDMFormFields(ddmStructure)),
					ddmStructure),
				ddmStructure.getStructureKey(),
				_getDDMTemplateKey(ddmStructure), null,
				localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, null,
				ServiceContextUtil.createServiceContext(
					structuredContent.getKeywords(),
					structuredContent.getTaxonomyCategoryIds(), contentSpaceId,
					structuredContent.getViewableByAsString())));
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
				ServiceContextUtil.createServiceContext(
					structuredContent.getKeywords(),
					structuredContent.getTaxonomyCategoryIds(),
					journalArticle.getGroupId(),
					structuredContent.getViewableByAsString())));
	}

	private DDMStructure _checkDDMStructurePermission(
			StructuredContent structuredContent)
		throws PortalException {

		try {
			return _ddmStructureService.getStructure(
				structuredContent.getContentStructureId());
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new ForbiddenException(
				"You do not have permission to create a structured content " +
					"using the content structure ID " +
						structuredContent.getContentStructureId(),
				mhp);
		}
	}

	private String _createJournalArticleContent(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws Exception {

		_validateFomFieldValues(ddmFormValues);

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
		catch (Exception sfe) {
			throw new BadRequestException(
				StringBundler.concat(
					"Unable to get content field value for \"", name,
					"\" for content structure ", ddmStructure.getStructureId()),
				sfe);
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

	private List<DDMFormField> _getRootDDMFormFields(
		DDMStructure ddmStructure) {

		return transform(
			ddmStructure.getRootFieldNames(),
			fieldName -> _getDDMFormField(ddmStructure, fieldName));
	}

	private StructuredContent _getStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		ContentLanguageUtil.addContentLanguageHeader(
			journalArticle.getAvailableLanguageIds(),
			journalArticle.getDefaultLanguageId(), _contextHttpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		return _toStructuredContent(journalArticle);
	}

	private Page<StructuredContent> _getStructuredContentsPage(
			Long contentSpaceId, Long contentStructureId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				if (contentStructureId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							com.liferay.portal.kernel.search.Field.
								CLASS_TYPE_ID,
							contentStructureId.toString()),
						BooleanClauseOccur.MUST);
				}
			},
			filter, JournalArticle.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				com.liferay.portal.kernel.search.Field.ARTICLE_ID,
				com.liferay.portal.kernel.search.Field.SCOPE_GROUP_ID),
			searchContext -> {
				searchContext.setAttribute(
					com.liferay.portal.kernel.search.Field.STATUS,
					WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (contentSpaceId != null) {
					searchContext.setGroupIds(new long[] {contentSpaceId});
				}
			},
			document -> _toStructuredContent(
				_journalArticleService.getLatestArticle(
					GetterUtil.getLong(
						document.get(
							com.liferay.portal.kernel.search.Field.
								SCOPE_GROUP_ID)),
					document.get(
						com.liferay.portal.kernel.search.Field.ARTICLE_ID),
					WorkflowConstants.STATUS_APPROVED)),
			sorts);
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

		Iterator<Field> iterator = fields.iterator();

		while (iterator.hasNext()) {
			Field field = iterator.next();

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

		Iterator<Field> iterator = fields.iterator();

		while (iterator.hasNext()) {
			Field field = iterator.next();

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

			ContentField[] nestedContentFields = contentField.getNestedFields();

			if (nestedContentFields != null) {
				_toPatchedFields(nestedContentFields, journalArticle);
			}
		}

		DDMFormValues ddmFormValues = _journalConverter.getDDMFormValues(
			ddmStructure, fields);

		_validateFomFieldValues(ddmFormValues);

		return fields;
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

		return StructuredContentUtil.toStructuredContent(
			journalArticle, contextAcceptLanguage, _assetCategoryLocalService,
			_assetTagLocalService, _commentManager, _dlAppService, _dlurlHelper,
			_fieldsToDDMFormValuesConverter, _journalArticleService,
			_journalConverter, _layoutLocalService, _portal,
			_ratingsStatsLocalService, contextUriInfo, _userLocalService);
	}

	private void _validateFomFieldValues(DDMFormValues ddmFormValues) {
		try {
			_ddmFormValuesValidator.validate(ddmFormValues);
		}
		catch (DDMFormValuesValidationException ddmfvve) {
			throw new BadRequestException(
				"Validation error: " + ddmfvve.getMessage(), ddmfvve);
		}
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommentManager _commentManager;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Context
	private HttpServletResponse _contextHttpServletResponse;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private EntityFieldsProvider _entityFieldsProvider;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

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
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}