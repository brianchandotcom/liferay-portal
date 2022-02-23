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

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.headless.delivery.dto.v1_0.ContentDocument;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.dto.v1_0.Geo;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentLink;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * @author Víctor Galán
 */
public class DDMValueUtil {

	public static Value toDDMValue(
		ContentField contentField, DDMFormField ddmFormField,
		DLAppService dlAppService, long groupId,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale preferredLocale) {

		ContentFieldValue contentFieldValue =
			contentField.getContentFieldValue();

		if (contentFieldValue == null) {
			if (Validator.isNull(ddmFormField.getDataType())) {
				return null;
			}

			throw new BadRequestException(
				"No value is specified for field " +
					ddmFormField.getFieldReference());
		}

		if (ddmFormField.isLocalizable()) {
			Map<String, ContentFieldValue> localizedContentFieldValues =
				contentField.getContentFieldValue_i18n();

			if (Objects.equals(DDMFormFieldType.DATE, ddmFormField.getType()) ||
				Objects.equals(
					DDMFormFieldTypeConstants.DATE, ddmFormField.getType())) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					DDMValueUtil::_toLocalizedDateString, preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldType.DOCUMENT_LIBRARY,
						ddmFormField.getType()) ||
					 Objects.equals(
						 ddmFormField.getType(),
						 DDMFormFieldTypeConstants.DOCUMENT_LIBRARY)) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) ->
						_toLocalizedDocument(
							localizedContentFieldValue, dlAppService),
					preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldType.IMAGE, ddmFormField.getType()) ||
					 Objects.equals(
						 DDMFormFieldTypeConstants.IMAGE,
						 ddmFormField.getType())) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) -> _toLocalizedImage(
						localizedContentFieldValue, dlAppService),
					preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldType.JOURNAL_ARTICLE,
						ddmFormField.getType()) ||
					 Objects.equals(
						 ddmFormField.getType(),
						 JournalArticleDDMFormFieldTypeConstants.
							 JOURNAL_ARTICLE)) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) ->
						_toLocalizedJournalArticle(
							localizedContentFieldValue, journalArticleService,
							locale),
					preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldTypeConstants.RADIO,
						ddmFormField.getType()) ||
					 Objects.equals(
						 DDMFormFieldTypeConstants.SELECT,
						 ddmFormField.getType()) ||
					 Objects.equals(
						 DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE,
						 ddmFormField.getType())) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) -> {
						try {
							String data = localizedContentFieldValue.getData();

							List<String> values = new ArrayList<>();

							if (!ddmFormField.isMultiple() &&
								!Objects.equals(
									DDMFormFieldType.CHECKBOX_MULTIPLE,
									ddmFormField.getType())) {

								values.add(data);
							}
							else {
								values.addAll(
									JSONUtil.toStringList(
										JSONFactoryUtil.createJSONArray(data)));
							}

							List<String> collect = _transformValuesToKeys(
								ddmFormField, locale, values);

							if ((collect.size() == 1) &&
								DDMFormFieldType.RADIO.equals(
									ddmFormField.getType())) {

								return collect.get(0);
							}

							return JSONUtil.toString(
								JSONFactoryUtil.createJSONArray(collect));
						}
						catch (JSONException jsonException) {
							if (_log.isDebugEnabled()) {
								_log.debug(jsonException);
							}

							return null;
						}
					},
					preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldType.LINK_TO_PAGE,
						ddmFormField.getType()) ||
					 Objects.equals(
						 LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT,
						 ddmFormField.getType())) {

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) ->
						_toLocalizedLinkToPage(
							localizedContentFieldValue, groupId,
							layoutLocalService, locale),
					preferredLocale);
			}
			else if (Objects.equals(
						DDMFormFieldType.GEOLOCATION, ddmFormField.getType()) ||
					 Objects.equals(
						 DDMFormFieldTypeConstants.GEOLOCATION,
						 ddmFormField.getType())) {

				Geo geo = contentFieldValue.getGeo();

				if (Objects.isNull(geo) || Objects.isNull(geo.getLatitude()) ||
					Objects.isNull(geo.getLongitude())) {

					throw new BadRequestException("Invalid geo " + geo);
				}

				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) -> JSONUtil.put(
						"lat", geo.getLatitude()
					).put(
						"lng", geo.getLongitude()
					).toString(),
					preferredLocale);
			}
			else {
				return _toLocalizedValue(
					contentFieldValue, localizedContentFieldValues,
					(localizedContentFieldValue, locale) ->
						GetterUtil.getString(
							localizedContentFieldValue.getData()),
					preferredLocale);
			}
		}

		return new UnlocalizedValue(
			GetterUtil.getString(contentFieldValue.getData()));
	}

	private static Layout _getLayout(
		long groupId, LayoutLocalService layoutLocalService, String link) {

		Layout layout = layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, link);

		if (layout == null) {
			layout = layoutLocalService.fetchLayoutByFriendlyURL(
				groupId, true, link);
		}

		if (layout == null) {
			throw new BadRequestException(
				"No page found with friendly URL " + link);
		}

		try {
			LayoutPermissionUtil.check(
				PermissionThreadLocal.getPermissionChecker(), layout,
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new BadRequestException(
				"No page found with friendly URL " + link, portalException);
		}

		return layout;
	}

	private static String _toJSON(
		String description, DLAppService dlAppService, long fileEntryId) {

		FileEntry fileEntry = null;

		try {
			fileEntry = dlAppService.getFileEntry(fileEntryId);
		}
		catch (Exception exception) {
			throw new BadRequestException(
				"No document exists with ID " + fileEntryId, exception);
		}

		return JSONUtil.put(
			"alt", description
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
		).toString();
	}

	private static String _toLocalizedDateString(
		ContentFieldValue contentFieldValue, Locale locale) {

		if (Validator.isNull(contentFieldValue.getData())) {
			return StringPool.BLANK;
		}

		try {
			return DateUtil.getDate(
				DateUtil.parseDate(
					"yyyy-MM-dd'T'HH:mm:ss'Z'", contentFieldValue.getData(),
					locale),
				"yyyy-MM-dd", locale);
		}
		catch (ParseException parseException) {
			throw new BadRequestException(
				"Unable to parse date that does not conform to ISO-8601",
				parseException);
		}
	}

	private static String _toLocalizedDocument(
		ContentFieldValue contentFieldValue, DLAppService dlAppService) {

		String valueString = StringPool.BLANK;

		ContentDocument contentDocument = contentFieldValue.getDocument();

		if ((contentDocument != null) && (contentDocument.getId() != null)) {
			valueString = _toJSON(
				StringPool.BLANK, dlAppService, contentDocument.getId());
		}

		return valueString;
	}

	private static String _toLocalizedImage(
		ContentFieldValue contentFieldValue, DLAppService dlAppService) {

		String valueString = StringPool.BLANK;

		ContentDocument contentDocument = contentFieldValue.getImage();

		if ((contentDocument != null) && (contentDocument.getId() != null)) {
			valueString = _toJSON(
				contentDocument.getDescription(), dlAppService,
				contentDocument.getId());
		}

		return valueString;
	}

	private static String _toLocalizedJournalArticle(
		ContentFieldValue contentFieldValue,
		JournalArticleService journalArticleService, Locale locale) {

		String valueString = StringPool.BLANK;

		StructuredContentLink structuredContentLink =
			contentFieldValue.getStructuredContentLink();

		if ((structuredContentLink != null) &&
			(structuredContentLink.getId() != null)) {

			JournalArticle journalArticle = null;

			try {
				journalArticle = journalArticleService.getLatestArticle(
					structuredContentLink.getId());
			}
			catch (Exception exception) {
				throw new BadRequestException(
					"No structured content exists with ID " +
						structuredContentLink.getId(),
					exception);
			}

			valueString = JSONUtil.put(
				"className", JournalArticle.class.getName()
			).put(
				"classPK", journalArticle.getResourcePrimKey()
			).put(
				"title", journalArticle.getTitle(locale)
			).toString();
		}

		return valueString;
	}

	private static String _toLocalizedLinkToPage(
		ContentFieldValue contentFieldValue, long groupId,
		LayoutLocalService layoutLocalService, Locale locale) {

		String valueString = StringPool.BLANK;

		if (contentFieldValue.getLink() != null) {
			Layout layout = _getLayout(
				groupId, layoutLocalService, contentFieldValue.getLink());

			valueString = JSONUtil.put(
				"groupId", String.valueOf(layout.getGroupId())
			).put(
				"id", layout.getUuid()
			).put(
				"label", layout.getFriendlyURL()
			).put(
				"layoutId", layout.getLayoutId()
			).put(
				"name",
				() -> {
					try {
						return layout.getBreadcrumb(locale);
					}
					catch (Exception exception) {
						return StringPool.BLANK;
					}
				}
			).put(
				"privateLayout", layout.isPrivateLayout()
			).toString();
		}

		return valueString;
	}

	private static LocalizedValue _toLocalizedValue(
		ContentFieldValue contentFieldValue,
		Map<String, ContentFieldValue> localizedContentFieldValues,
		BiFunction<ContentFieldValue, Locale, String> localizedValueBiFunction,
		Locale preferredLocale) {

		LocalizedValue localizedValue = new LocalizedValue(preferredLocale);

		localizedValue.addString(
			preferredLocale,
			localizedValueBiFunction.apply(contentFieldValue, preferredLocale));

		Optional.ofNullable(
			localizedContentFieldValues
		).orElse(
			Collections.emptyMap()
		).forEach(
			(languageId, localizedContentFieldValue) -> {
				Locale locale = LocaleUtil.fromLanguageId(
					languageId, true, false);

				if (locale != null) {
					localizedValue.addString(
						locale,
						localizedValueBiFunction.apply(
							localizedContentFieldValue, locale));
				}
			}
		);

		return localizedValue;
	}

	private static List<String> _transformValuesToKeys(
		DDMFormField ddmFormField, Locale locale, List<String> values) {

		Stream<String> stream = values.stream();

		return stream.map(
			value -> {
				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormField.getDDMFormFieldOptions();

				Map<String, LocalizedValue> options =
					ddmFormFieldOptions.getOptions();

				Set<Map.Entry<String, LocalizedValue>> set = options.entrySet();

				Stream<Map.Entry<String, LocalizedValue>> setStream =
					set.stream();

				return setStream.filter(
					entry -> {
						LocalizedValue localizedValue = entry.getValue();

						return Objects.equals(
							localizedValue.getString(locale), value);
					}
				).map(
					Map.Entry::getKey
				).findFirst(
				).orElse(
					""
				);
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMValueUtil.class);

}