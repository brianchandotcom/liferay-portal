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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.dto.v1_0.Geo;
import com.liferay.headless.delivery.dto.v1_0.RenderedContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentLink;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.headless.delivery.internal.resource.v1_0.BaseStructuredContentResourceImpl;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.text.ParseException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "dto.class.name=com.liferay.journal.model.JournalArticle",
	service = {DTOConverter.class, StructuredContentDTOConverter.class}
)
public class StructuredContentDTOConverter
	implements DTOConverter<JournalArticle, StructuredContent> {

	@Override
	public String getContentType() {
		return StructuredContent.class.getSimpleName();
	}

	@Override
	public StructuredContent toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			(Long)dtoConverterContext.getId());

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				actions = dtoConverterContext.getActions();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				contentFields = _toContentFields(
					_dlAppService, _dlURLHelper, dtoConverterContext,
					_fieldsToDDMFormValuesConverter, journalArticle,
					_journalArticleService, _journalConverter,
					_layoutLocalService);
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					JournalArticle.class.getName(), journalArticle.getId(),
					journalArticle.getCompanyId(),
					dtoConverterContext.getLocale());
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					journalArticle.getDescriptionMap());
				friendlyUrlPath = journalArticle.getUrlTitle(
					dtoConverterContext.getLocale());
				friendlyUrlPath_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					journalArticle.getFriendlyURLMap());
				id = journalArticle.getResourcePrimKey();
				key = journalArticle.getArticleId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					AssetTag.NAME_ACCESSOR);
				numberOfComments = _commentManager.getCommentsCount(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
				relatedContents = RelatedContentUtil.toRelatedContents(
					_assetEntryLocalService, _assetLinkLocalService,
					dtoConverterContext.getDTOConverterRegistry(),
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey(),
					dtoConverterContext.getLocale());
				renderedContents = _toRenderedContents(
					dtoConverterContext.isAcceptAllLanguages(), ddmStructure,
					journalArticle, dtoConverterContext.getLocale(),
					dtoConverterContext.getUriInfoOptional());
				siteId = journalArticle.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					journalArticle.getCompanyId(),
					dtoConverterContext.getUserId(),
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
				taxonomyCategoryBriefs = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					assetCategory ->
						TaxonomyCategoryBriefUtil.toTaxonomyCategoryBrief(
							dtoConverterContext.isAcceptAllLanguages(),
							assetCategory, dtoConverterContext.getLocale()),
					TaxonomyCategoryBrief.class);
				title = journalArticle.getTitle(
					dtoConverterContext.getLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					journalArticle.getTitleMap());
				uuid = journalArticle.getUuid();
			}
		};
	}

	private ContentFieldValue _getContentFieldValue(
			DDMFormField ddmFormField, DLAppService dlAppService,
			DLURLHelper dlURLHelper,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService, Locale locale,
			String valueString)
		throws Exception {

		if (Objects.equals(DDMFormFieldType.DATE, ddmFormField.getType())) {
			return new ContentFieldValue() {
				{
					data = _toDateString(locale, valueString);
				}
			};
		}
		else if (Objects.equals(
					DDMFormFieldType.DOCUMENT_LIBRARY,
					ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return new ContentFieldValue();
			}

			return new ContentFieldValue() {
				{
					document = ContentDocumentUtil.toContentDocument(
						dlURLHelper, dlAppService.getFileEntry(classPK));
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			return new ContentFieldValue() {
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

		if (Objects.equals(DDMFormFieldType.IMAGE, ddmFormField.getType())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long fileEntryId = jsonObject.getLong("fileEntryId");

			if (fileEntryId == 0) {
				return new ContentFieldValue();
			}

			try {
				return new ContentFieldValue() {
					{
						image = ContentDocumentUtil.toContentDocument(
							dlURLHelper,
							dlAppService.getFileEntry(fileEntryId));

						image.setDescription(jsonObject.getString("alt"));
					}
				};
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						noSuchFileEntryException, noSuchFileEntryException);
				}

				return new ContentFieldValue();
			}
		}

		if (Objects.equals(
				DDMFormFieldType.JOURNAL_ARTICLE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return new ContentFieldValue();
			}

			JournalArticle journalArticle =
				journalArticleService.getLatestArticle(classPK);

			return new ContentFieldValue() {
				{
					structuredContentLink = new StructuredContentLink() {
						{
							contentType = "StructuredContent";
							id = journalArticle.getResourcePrimKey();
							title = journalArticle.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.LINK_TO_PAGE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId == 0) {
				return new ContentFieldValue();
			}

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			Layout layoutByUuidAndGroupId = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			return new ContentFieldValue() {
				{
					link = layoutByUuidAndGroupId.getFriendlyURL();
				}
			};
		}

		return new ContentFieldValue() {
			{
				data = valueString;
			}
		};
	}

	private ContentField _toContentField(
			DDMFormFieldValue ddmFormFieldValue, DLAppService dlAppService,
			DLURLHelper dlURLHelper, DTOConverterContext dtoConverterContext,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService)
		throws Exception {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		LocalizedValue localizedValue = ddmFormField.getLabel();

		return new ContentField() {
			{
				contentFieldValue = _toContentFieldValue(
					ddmFormField, dlAppService, dlURLHelper,
					journalArticleService, layoutLocalService,
					dtoConverterContext.getLocale(),
					ddmFormFieldValue.getValue());
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				label = localizedValue.getString(
					dtoConverterContext.getLocale());
				label_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					localizedValue.getValues());
				name = ddmFormField.getName();
				nestedContentFields = TransformUtil.transformToArray(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					value -> _toContentField(
						value, dlAppService, dlURLHelper, dtoConverterContext,
						journalArticleService, layoutLocalService),
					ContentField.class);
				repeatable = ddmFormField.isRepeatable();

				setContentFieldValue_i18n(
					() -> {
						if (!dtoConverterContext.isAcceptAllLanguages()) {
							return null;
						}

						Map<String, ContentFieldValue> map = new HashMap<>();

						Map<Locale, String> valueValues = Optional.ofNullable(
							ddmFormFieldValue.getValue()
						).map(
							Value::getValues
						).orElse(
							Collections.emptyMap()
						);

						for (Map.Entry<Locale, String> entry :
								valueValues.entrySet()) {

							Locale locale = entry.getKey();

							map.put(
								LocaleUtil.toBCP47LanguageId(locale),
								_getContentFieldValue(
									ddmFormField, dlAppService, dlURLHelper,
									journalArticleService, layoutLocalService,
									entry.getKey(), entry.getValue()));
						}

						return map;
					});
			}
		};
	}

	private ContentField[] _toContentFields(
			DLAppService dlAppService, DLURLHelper dlURLHelper,
			DTOConverterContext dtoConverterContext,
			FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
			JournalArticle journalArticle,
			JournalArticleService journalArticleService,
			JournalConverter journalConverter,
			LayoutLocalService layoutLocalService)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields fields = journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		DDMFormValues ddmFormValues = fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		return TransformUtil.transformToArray(
			ddmFormValues.getDDMFormFieldValues(),
			ddmFormFieldValue -> _toContentField(
				ddmFormFieldValue, dlAppService, dlURLHelper,
				dtoConverterContext, journalArticleService, layoutLocalService),
			ContentField.class);
	}

	private ContentFieldValue _toContentFieldValue(
			DDMFormField ddmFormField, DLAppService dlAppService,
			DLURLHelper dlURLHelper,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService, Locale locale, Value value)
		throws Exception {

		if (value == null) {
			return new ContentFieldValue();
		}

		String valueString = String.valueOf(value.getString(locale));

		return _getContentFieldValue(
			ddmFormField, dlAppService, dlURLHelper, journalArticleService,
			layoutLocalService, locale, valueString);
	}

	private String _toDateString(Locale locale, String valueString) {
		if (Validator.isNull(valueString)) {
			return "";
		}

		try {
			return DateUtil.getDate(
				DateUtil.parseDate("yyyy-MM-dd", valueString, locale),
				"yyyy-MM-dd'T'HH:mm:ss'Z'", locale,
				TimeZone.getTimeZone("UTC"));
		}
		catch (ParseException parseException) {
			throw new BadRequestException(
				"Unable to parse date that does not conform to ISO-8601",
				parseException);
		}
	}

	private RenderedContent[] _toRenderedContents(
		boolean acceptAllLanguages, DDMStructure ddmStructure,
		JournalArticle journalArticle, Locale locale,
		Optional<UriInfo> uriInfoOptional) {

		if (!uriInfoOptional.isPresent()) {
			return null;
		}

		return TransformUtil.transformToArray(
			ddmStructure.getTemplates(),
			ddmTemplate -> new RenderedContent() {
				{
					renderedContentURL = JaxRsLinkUtil.getJaxRsLink(
						"headless-delivery",
						BaseStructuredContentResourceImpl.class,
						"getStructuredContentRenderedContentTemplate",
						uriInfoOptional.get(),
						journalArticle.getResourcePrimKey(),
						ddmTemplate.getTemplateId());
					templateName = ddmTemplate.getName(locale);
					templateName_i18n = LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, ddmTemplate.getNameMap());
				}
			},
			RenderedContent.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentDTOConverter.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}