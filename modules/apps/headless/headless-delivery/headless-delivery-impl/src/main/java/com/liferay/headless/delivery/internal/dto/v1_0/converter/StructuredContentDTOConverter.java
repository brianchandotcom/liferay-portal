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
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.Geo;
import com.liferay.headless.delivery.dto.v1_0.RenderedContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentLink;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.dto.v1_0.Value;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.resource.v1_0.BaseStructuredContentResourceImpl;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.text.ParseException;

import java.util.Locale;
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
	property = "asset.entry.class.name=com.liferay.journal.model.JournalArticle",
	service = {DTOConverter.class, StructuredContentDTOConverter.class}
)
public class StructuredContentDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return StructuredContent.class.getSimpleName();
	}

	@Override
	public StructuredContent toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			dtoConverterContext.getResourcePrimKey());

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				contentFields = _toContentFields(
					journalArticle, dtoConverterContext.getLocale(),
					_dlAppService, _dlURLHelper,
					_fieldsToDDMFormValuesConverter, _journalArticleService,
					_journalConverter, _layoutLocalService);
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					JournalArticle.class.getName(), journalArticle.getId(),
					journalArticle.getCompanyId(),
					dtoConverterContext.getLocale());
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					dtoConverterContext.getLocale());
				friendlyUrlPath = journalArticle.getUrlTitle(
					dtoConverterContext.getLocale());
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
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey(),
					dtoConverterContext.getLocale());
				renderedContents = _toRenderedContents(
					ddmStructure, journalArticle,
					dtoConverterContext.getLocale(),
					dtoConverterContext.getUriInfoOptional());
				siteId = journalArticle.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					journalArticle.getCompanyId(),
					dtoConverterContext.getUserId(),
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
				taxonomyCategories = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
				title = journalArticle.getTitle(
					dtoConverterContext.getLocale());
				uuid = journalArticle.getUuid();
			}
		};
	}

	private ContentField _toContentField(
			DDMFormFieldValue ddmFormFieldValue, Locale locale,
			DLAppService dlAppService, DLURLHelper dlURLHelper,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService)
		throws Exception {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return new ContentField() {
			{
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				name = ddmFormField.getName();
				nestedContentFields = TransformUtil.transformToArray(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					value -> _toContentField(
						value, locale, dlAppService, dlURLHelper,
						journalArticleService, layoutLocalService),
					ContentField.class);
				repeatable = ddmFormField.isRepeatable();
				value = _toValue(
					ddmFormFieldValue, dlAppService, dlURLHelper,
					journalArticleService, layoutLocalService, locale);

				setLabel(
					() -> {
						LocalizedValue localizedValue = ddmFormField.getLabel();

						return localizedValue.getString(locale);
					});
			}
		};
	}

	private ContentField[] _toContentFields(
			JournalArticle journalArticle, Locale locale,
			DLAppService dlAppService, DLURLHelper dlURLHelper,
			FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
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
			aDDMFormFieldValue -> _toContentField(
				aDDMFormFieldValue, locale, dlAppService, dlURLHelper,
				journalArticleService, layoutLocalService),
			ContentField.class);
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
		catch (ParseException pe) {
			throw new BadRequestException(
				"Unable to parse date that does not conform to ISO-8601", pe);
		}
	}

	private RenderedContent[] _toRenderedContents(
		DDMStructure ddmStructure, JournalArticle journalArticle, Locale locale,
		Optional<UriInfo> uriInfoOptional) {

		if (!uriInfoOptional.isPresent()) {
			return null;
		}

		return TransformUtil.transformToArray(
			ddmStructure.getTemplates(),
			ddmTemplate -> new RenderedContent() {
				{
					renderedContentURL = JaxRsLinkUtil.getJaxRsLink(
						BaseStructuredContentResourceImpl.class,
						"getStructuredContentRenderedContentTemplate",
						uriInfoOptional.get(),
						journalArticle.getResourcePrimKey(),
						ddmTemplate.getTemplateId());
					templateName = ddmTemplate.getName(locale);
				}
			},
			RenderedContent.class);
	}

	private Value _toValue(
			DDMFormFieldValue ddmFormFieldValue, DLAppService dlAppService,
			DLURLHelper dlURLHelper,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService, Locale locale)
		throws Exception {

		com.liferay.dynamic.data.mapping.model.Value value =
			ddmFormFieldValue.getValue();

		if (value == null) {
			return new Value();
		}

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		String valueString = String.valueOf(value.getString(locale));

		if (Objects.equals(DDMFormFieldType.DATE, ddmFormField.getType())) {
			return new Value() {
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
				return new Value();
			}

			return new Value() {
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

		if (Objects.equals(DDMFormFieldType.IMAGE, ddmFormField.getType())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long fileEntryId = jsonObject.getLong("fileEntryId");

			if (fileEntryId == 0) {
				return new Value();
			}

			return new Value() {
				{
					image = ContentDocumentUtil.toContentDocument(
						dlURLHelper, dlAppService.getFileEntry(fileEntryId));

					image.setDescription(jsonObject.getString("alt"));
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.JOURNAL_ARTICLE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return new Value();
			}

			JournalArticle journalArticle =
				journalArticleService.getLatestArticle(classPK);

			return new Value() {
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
				return new Value();
			}

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			Layout layoutByUuidAndGroupId = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			return new Value() {
				{
					link = layoutByUuidAndGroupId.getFriendlyURL();
				}
			};
		}

		return new Value() {
			{
				data = valueString;
			}
		};
	}

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