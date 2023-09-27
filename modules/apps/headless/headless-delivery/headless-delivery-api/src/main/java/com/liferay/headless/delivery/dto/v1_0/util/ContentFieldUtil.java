/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.dto.v1_0.Geo;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentLink;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class ContentFieldUtil {

	public static ContentField toContentField(
		DDMFormFieldValue ddmFormFieldValue, DLAppService dlAppService,
		DLURLHelper dlURLHelper, DTOConverterContext dtoConverterContext,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		if (ddmFormField == null) {
			return null;
		}

		LocalizedValue localizedValue = ddmFormField.getLabel();

		return new ContentField() {
			{
				contentFieldValue = _toContentFieldValue(
					ddmFormField, dlAppService, dlURLHelper,
					dtoConverterContext, journalArticleService,
					layoutLocalService, dtoConverterContext.getLocale(),
					ddmFormFieldValue.getValue());
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				label = localizedValue.getString(
					dtoConverterContext.getLocale());
				label_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					localizedValue.getValues());
				name = ddmFormField.getFieldReference();
				nestedContentFields = TransformUtil.transformToArray(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					value -> toContentField(
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

						Value value = ddmFormFieldValue.getValue();

						Map<Locale, String> values = value.getValues();

						if (values == null) {
							values = Collections.emptyMap();
						}

						for (Map.Entry<Locale, String> entry :
								values.entrySet()) {

							Locale locale = entry.getKey();

							map.put(
								LocaleUtil.toBCP47LanguageId(locale),
								_getContentFieldValue(
									ddmFormField, dlAppService, dlURLHelper,
									dtoConverterContext, journalArticleService,
									layoutLocalService, locale,
									entry.getValue()));
						}

						return map;
					});
			}
		};
	}

	private static ContentFieldValue _getContentFieldValue(
		DDMFormField ddmFormField, DLAppService dlAppService,
		DLURLHelper dlURLHelper, DTOConverterContext dtoConverterContext,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale,
		String valueString) {

		try {
			UriInfo uriInfo = dtoConverterContext.getUriInfo();

			if (Objects.equals(DDMFormFieldType.DATE, ddmFormField.getType()) ||
				Objects.equals(ddmFormField.getType(), "date")) {

				return new ContentFieldValue() {
					{
						data = _toDateString(locale, valueString);
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldType.DOCUMENT_LIBRARY,
						ddmFormField.getType()) ||
					 Objects.equals(
						 ddmFormField.getType(),
						 DDMFormFieldTypeConstants.DOCUMENT_LIBRARY)) {

				FileEntry fileEntry = _getFileEntry(dlAppService, valueString);

				if (fileEntry == null) {
					return new ContentFieldValue();
				}

				return new ContentFieldValue() {
					{
						document = ContentDocumentUtil.toContentDocument(
							dlURLHelper,
							"contentFields.contentFieldValue.document",
							fileEntry, uriInfo);
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldTypeConstants.GEOLOCATION,
						ddmFormField.getType())) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				return new ContentFieldValue() {
					{
						geo = new Geo() {
							{
								latitude = jsonObject.getDouble("lat");
								longitude = jsonObject.getDouble("lng");
							}
						};
					}
				};
			}
			else if (Objects.equals(
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
			else if (Objects.equals(
						DDMFormFieldTypeConstants.GRID,
						ddmFormField.getType())) {

				Map<String, Object> properties = ddmFormField.getProperties();

				DDMFormFieldOptions rowsDDMFormFieldOptions =
					(DDMFormFieldOptions)properties.get("rows");
				DDMFormFieldOptions columnsDDMFormFieldOptions =
					(DDMFormFieldOptions)properties.get("columns");

				JSONObject localizedSelectedDataJSONObject =
					JSONFactoryUtil.createJSONObject();
				JSONObject selectedValuesJSONObject =
					JSONFactoryUtil.createJSONObject();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				Iterator<String> iterator = jsonObject.keys();

				while (iterator.hasNext()) {
					String key = iterator.next();

					LocalizedValue optionKeyLabelsLocalizedValue =
						rowsDDMFormFieldOptions.getOptionLabels(key);

					String value = jsonObject.getString(key);

					LocalizedValue optionValueLabelsLocalizedValue =
						columnsDDMFormFieldOptions.getOptionLabels(value);

					localizedSelectedDataJSONObject.put(
						optionKeyLabelsLocalizedValue.getString(locale),
						optionValueLabelsLocalizedValue.getString(locale));

					selectedValuesJSONObject.put(
						rowsDDMFormFieldOptions.getOptionReference(key),
						columnsDDMFormFieldOptions.getOptionReference(value));
				}

				return new ContentFieldValue() {
					{
						data = JSONUtil.toString(
							localizedSelectedDataJSONObject);
						value = JSONUtil.toString(selectedValuesJSONObject);
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldType.IMAGE, ddmFormField.getType()) ||
					 Objects.equals(
						 DDMFormFieldTypeConstants.IMAGE,
						 ddmFormField.getType())) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				long fileEntryId = jsonObject.getLong("fileEntryId");

				if (fileEntryId == 0) {
					return new ContentFieldValue();
				}

				return new ContentFieldValue() {
					{
						image = ContentDocumentUtil.toContentDocument(
							dlURLHelper,
							"contentFields.contentFieldValue.image",
							dlAppService.getFileEntry(fileEntryId), uriInfo);

						String alt = jsonObject.getString("alt");

						if (Validator.isNotNull(alt) &&
							JSONUtil.isJSONObject(alt)) {

							JSONObject altJSONObject = jsonObject.getJSONObject(
								"alt");

							alt = altJSONObject.getString(
								LocaleUtil.toLanguageId(locale));
						}

						image.setDescription(alt);
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldType.JOURNAL_ARTICLE,
						ddmFormField.getType()) ||
					 Objects.equals(
						 ddmFormField.getType(),
						 JournalArticleDDMFormFieldTypeConstants.
							 JOURNAL_ARTICLE)) {

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
								embeddedStructuredContent =
									_toStructuredContent(
										classPK, dtoConverterContext);
								id = journalArticle.getResourcePrimKey();
								title = journalArticle.getTitle();
							}
						};
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldType.LINK_TO_PAGE,
						ddmFormField.getType()) ||
					 Objects.equals(
						 LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT,
						 ddmFormField.getType())) {

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
			else if (Objects.equals(
						DDMFormFieldTypeConstants.SELECT,
						ddmFormField.getType()) ||
					 Objects.equals(
						 ddmFormField.getType(),
						 DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE)) {

				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormField.getDDMFormFieldOptions();

				List<String> values = new ArrayList<>();

				List<String> list = TransformUtil.transform(
					JSONUtil.toStringList(
						JSONFactoryUtil.createJSONArray(valueString)),
					value -> {
						LocalizedValue localizedValue =
							ddmFormFieldOptions.getOptionLabels(value);

						values.add(
							ddmFormFieldOptions.getOptionReference(value));

						return localizedValue.getString(locale);
					});

				return new ContentFieldValue() {
					{
						setData(
							() -> {
								if (!ddmFormField.isMultiple() &&
									(list.size() == 1)) {

									return list.get(0);
								}

								return String.valueOf(
									JSONFactoryUtil.createJSONArray(list));
							});
						setValue(
							() -> {
								if (!ddmFormField.isMultiple() &&
									(values.size() == 1)) {

									return values.get(0);
								}

								return String.valueOf(
									JSONFactoryUtil.createJSONArray(values));
							});
					}
				};
			}
			else if (Objects.equals(
						DDMFormFieldTypeConstants.RADIO,
						ddmFormField.getType())) {

				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormField.getDDMFormFieldOptions();

				LocalizedValue selectedOptionLabelLocalizedValue =
					ddmFormFieldOptions.getOptionLabels(valueString);

				return new ContentFieldValue() {
					{
						data = selectedOptionLabelLocalizedValue.getString(
							locale);
						value = ddmFormFieldOptions.getOptionReference(
							valueString);
					}
				};
			}

			return new ContentFieldValue() {
				{
					data = valueString;
				}
			};
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return new ContentFieldValue();
		}
	}

	private static FileEntry _getFileEntry(
			DLAppService dlAppService, String valueString)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);

		long classPK = jsonObject.getLong("classPK");

		if (classPK != 0) {
			return dlAppService.getFileEntry(classPK);
		}

		long groupId = jsonObject.getLong("groupId");

		if (groupId == 0) {
			return null;
		}

		return dlAppService.getFileEntryByUuidAndGroupId(
			jsonObject.getString("uuid"), groupId);
	}

	private static ContentFieldValue _toContentFieldValue(
		DDMFormField ddmFormField, DLAppService dlAppService,
		DLURLHelper dlURLHelper, DTOConverterContext dtoConverterContext,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale, Value value) {

		if (value == null) {
			return new ContentFieldValue();
		}

		String valueString = String.valueOf(value.getString(locale));

		return _getContentFieldValue(
			ddmFormField, dlAppService, dlURLHelper, dtoConverterContext,
			journalArticleService, layoutLocalService, locale, valueString);
	}

	private static String _toDateString(Locale locale, String valueString) {
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

	private static StructuredContent _toStructuredContent(
			long classPK, DTOConverterContext dtoConverterContext)
		throws Exception {

		UriInfo uriInfo = dtoConverterContext.getUriInfo();

		if (uriInfo == null) {
			return null;
		}

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		String nestedFields = queryParameters.getFirst("nestedFields");

		if ((nestedFields == null) ||
			!nestedFields.contains("embeddedStructuredContent")) {

			return null;
		}

		DTOConverterRegistry dtoConverterRegistry =
			dtoConverterContext.getDTOConverterRegistry();

		DTOConverter<?, ?> dtoConverter = dtoConverterRegistry.getDTOConverter(
			JournalArticle.class.getName());

		if (dtoConverter == null) {
			return null;
		}

		return (StructuredContent)dtoConverter.toDTO(
			new DefaultDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(),
				Collections.emptyMap(), dtoConverterRegistry,
				dtoConverterContext.getHttpServletRequest(), classPK,
				dtoConverterContext.getLocale(), uriInfo,
				dtoConverterContext.getUser()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentFieldUtil.class);

}