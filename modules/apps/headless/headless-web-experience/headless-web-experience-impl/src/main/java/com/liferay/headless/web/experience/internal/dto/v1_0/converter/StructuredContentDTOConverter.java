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

package com.liferay.headless.web.experience.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.headless.common.spi.dto.converter.DTOConverter;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentLink;
import com.liferay.headless.web.experience.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.internal.resource.v1_0.BaseStructuredContentResourceImpl;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.net.URI;

import java.text.ParseException;

import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "dto.converter.class.name=com.liferay.journal.model.JournalArticle",
	service = DTOConverter.class
)
public class StructuredContentDTOConverter implements DTOConverter {

	@Override
	public Object toDTO(
		AcceptLanguage acceptLanguage, AssetEntry assetEntry, UriInfo uriInfo) {

		try {
			return toDTO(
				acceptLanguage,
				_journalArticleService.getLatestArticle(
					assetEntry.getClassPK()),
				uriInfo);
		}
		catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	public StructuredContent toDTO(
			AcceptLanguage acceptLanguage, JournalArticle journalArticle,
			UriInfo uriInfo)
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
				contentFields = _toContentFields(
					journalArticle, acceptLanguage, _dlAppService, _dlURLHelper,
					_fieldsToDDMFormValuesConverter, _journalArticleService,
					_journalConverter, _layoutLocalService);
				contentSpaceId = journalArticle.getGroupId();
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
				key = journalArticle.getArticleId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					AssetTag.NAME_ACCESSOR);
				lastReviewed = journalArticle.getReviewDate();
				numberOfComments = _commentManager.getCommentsCount(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
				renderedContents = TransformUtil.transformToArray(
					ddmStructure.getTemplates(),
					ddmTemplate -> new RenderedContent() {
						{
							renderedContentURL = _getJAXRSLink(
								BaseStructuredContentResourceImpl.class,
								"getStructuredContentRenderedContentTemplate",
								uriInfo, journalArticle.getResourcePrimKey(),
								ddmTemplate.getTemplateId());
							templateName = ddmTemplate.getName(
								acceptLanguage.getPreferredLocale());
						}
					},
					RenderedContent.class);
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
					acceptLanguage.getPreferredLocale());
				uuid = journalArticle.getUuid();
			}
		};
	}

	private String _getJAXRSLink(
		Class clazz, String methodName, UriInfo uriInfo, Object... values) {

		String baseURIString = String.valueOf(uriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			clazz
		).build();

		URI methodURI = UriBuilder.fromMethod(
			clazz, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	private ContentField _toContentField(
			DDMFormFieldValue ddmFormFieldValue, AcceptLanguage acceptLanguage,
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
				nestedFields = TransformUtil.transformToArray(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					value -> _toContentField(
						value, acceptLanguage, dlAppService, dlURLHelper,
						journalArticleService, layoutLocalService),
					ContentField.class);
				repeatable = ddmFormField.isRepeatable();
				value = _toValue(
					ddmFormFieldValue, dlAppService, dlURLHelper,
					journalArticleService, layoutLocalService,
					acceptLanguage.getPreferredLocale());
			}
		};
	}

	private ContentField[] _toContentFields(
			JournalArticle journalArticle, AcceptLanguage acceptLanguage,
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
				aDDMFormFieldValue, acceptLanguage, dlAppService, dlURLHelper,
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

	private Value _toValue(
			DDMFormFieldValue ddmFormFieldValue, DLAppService dlAppService,
			DLURLHelper dlURLHelper,
			JournalArticleService journalArticleService,
			LayoutLocalService layoutLocalService, Locale locale)
		throws Exception {

		com.liferay.dynamic.data.mapping.model.Value value =
			ddmFormFieldValue.getValue();

		if (value == null) {
			return null;
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
				return null;
			}

			FileEntry fileEntry = dlAppService.getFileEntry(classPK);

			return new Value() {
				{
					document = ContentDocumentUtil.toContentDocument(
						dlURLHelper, fileEntry);
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
				return null;
			}

			FileEntry fileEntry = dlAppService.getFileEntry(fileEntryId);

			return new Value() {
				{
					image = ContentDocumentUtil.toContentDocument(
						dlURLHelper, fileEntry);

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
				return null;
			}

			JournalArticle journalArticle =
				journalArticleService.getLatestArticle(classPK);

			return new Value() {
				{
					structuredContentLink = new StructuredContentLink() {
						{
							id = journalArticle.getId();
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
				return null;
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
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommentManager _commentManager;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Context
	private HttpServletResponse _contextHttpServletResponse;

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
	private UserLocalService _userLocalService;

}