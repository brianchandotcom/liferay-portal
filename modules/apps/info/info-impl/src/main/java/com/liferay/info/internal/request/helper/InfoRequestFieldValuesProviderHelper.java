/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.request.helper;

import com.liferay.info.exception.InfoFormFileUploadException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.DateTimeInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class InfoRequestFieldValuesProviderHelper {

	public InfoRequestFieldValuesProviderHelper(
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	public Map<String, InfoFieldValue<Object>> getInfoFieldValues(
			HttpServletRequest httpServletRequest)
		throws InfoFormFileUploadException {

		Map<String, InfoFieldValue<Object>> infoFieldValues = new HashMap<>();

		UploadServletRequest uploadServletRequest =
			PortalUtil.getUploadServletRequest(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] checkboxNames = StringUtil.split(
			ParamUtil.getString(uploadServletRequest, "checkboxNames"));
		String className = PortalUtil.getClassName(
			ParamUtil.getLong(uploadServletRequest, "classNameId"));
		String classTypeId = ParamUtil.getString(
			uploadServletRequest, "classTypeId");
		long groupId = ParamUtil.getLong(uploadServletRequest, "groupId");

		Map<String, FileItem[]> multipartParameterMap =
			uploadServletRequest.getMultipartParameterMap();

		Map<String, List<String>> regularParameterMap =
			uploadServletRequest.getRegularParameterMap();

		for (InfoField<?> infoField :
				_getInfoFields(className, classTypeId, groupId)) {

			if (!infoField.isEditable()) {
				continue;
			}

			FileItem[] multipartParameters = multipartParameterMap.get(
				infoField.getName());

			if ((multipartParameters != null) &&
				(infoField.getInfoFieldType() instanceof FileInfoFieldType)) {

				for (FileItem fileItem : multipartParameters) {
					InfoFieldValue<Object> infoFieldValue =
						_getFileInfoFieldValue(
							fileItem, groupId, infoField, themeDisplay);

					if (infoFieldValue != null) {
						infoFieldValues.put(
							infoField.getUniqueId(), infoFieldValue);
					}
				}
			}

			if (infoField.getInfoFieldType() instanceof
					MultiselectInfoFieldType) {

				infoFieldValues.put(
					infoField.getUniqueId(),
					_getMultiselectInfoFieldValue(
						infoField, themeDisplay.getLocale(),
						regularParameterMap.get(infoField.getName())));

				continue;
			}

			if (FeatureFlagManagerUtil.isEnabled("LPD-37927") &&
				infoField.isLocalizable()) {

				infoFieldValues.put(
					infoField.getUniqueId(),
					new InfoFieldValue<>(
						infoField,
						InfoLocalizedValue.builder(
						).defaultLocale(
							themeDisplay.getSiteDefaultLocale()
						).value(
							unsafeBiConsumer -> {
								for (Locale locale :
										LanguageUtil.getAvailableLocales(
											themeDisplay.getSiteGroupId())) {

									String languageId =
										LanguageUtil.getLanguageId(locale);

									List<String> values =
										regularParameterMap.get(
											infoField.getName() +
												StringPool.UNDERLINE +
													languageId);

									if (ListUtil.isNotEmpty(values)) {
										unsafeBiConsumer.accept(
											locale,
											_parseValue(
												infoField, locale,
												values.get(0)));
									}
								}
							}
						).build()));

				continue;
			}

			List<String> regularParameters = regularParameterMap.get(
				infoField.getName());

			if (regularParameters == null) {
				if ((infoField.getInfoFieldType() instanceof
						BooleanInfoFieldType) &&
					ArrayUtil.contains(checkboxNames, infoField.getName())) {

					infoFieldValues.put(
						infoField.getUniqueId(),
						_getInfoFieldValue(
							infoField, themeDisplay.getLocale(), false));
				}

				continue;
			}

			for (String value : regularParameters) {
				InfoFieldValue<Object> infoFieldValue = _getInfoFieldValue(
					infoField, themeDisplay.getLocale(), value);

				if (infoFieldValue != null) {
					infoFieldValues.put(
						infoField.getUniqueId(), infoFieldValue);
				}
			}
		}

		return infoFieldValues;
	}

	private InfoFieldValue<Object> _getFileInfoFieldValue(
			FileItem fileItem, long groupId, InfoField infoField,
			ThemeDisplay themeDisplay)
		throws InfoFormFileUploadException {

		if ((fileItem.getSize() < 0) ||
			Validator.isNull(fileItem.getFileName())) {

			return _getInfoFieldValue(
				infoField, themeDisplay.getLocale(), (Object)StringPool.BLANK);
		}

		try (InputStream inputStream = fileItem.getInputStream()) {
			if (inputStream == null) {
				throw new InfoFormFileUploadException(infoField.getUniqueId());
			}

			File file = FileUtil.createTempFile(inputStream);

			if (file == null) {
				throw new InfoFormFileUploadException(infoField.getUniqueId());
			}

			FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
				groupId, themeDisplay.getUserId(),
				InfoRequestFieldValuesProviderHelper.class.getName(),
				TempFileEntryUtil.getTempFileName(fileItem.getFileName()), file,
				fileItem.getContentType());

			return _getInfoFieldValue(
				infoField, themeDisplay.getLocale(),
				String.valueOf(fileEntry.getFileEntryId()));
		}
		catch (IOException | PortalException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new InfoFormFileUploadException(infoField.getUniqueId());
		}
	}

	private <T> List<InfoField<?>> _getInfoFields(
		String className, String formVariationKey, long groupId) {

		InfoItemFormProvider<T> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			return new ArrayList<>();
		}

		try {
			InfoForm infoForm = infoItemFormProvider.getInfoForm(
				formVariationKey, groupId);

			return infoForm.getAllInfoFields();
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormVariationException);
			}

			return new ArrayList<>();
		}
	}

	private InfoFieldValue<Object> _getInfoFieldValue(
		InfoField<?> infoField, Locale locale, Object object) {

		if (infoField.isLocalizable()) {
			return new InfoFieldValue<>(
				infoField,
				InfoLocalizedValue.builder(
				).defaultLocale(
					locale
				).value(
					locale, object
				).build());
		}

		return new InfoFieldValue<>(infoField, object);
	}

	private InfoFieldValue<Object> _getInfoFieldValue(
		InfoField<?> infoField, Locale locale, String value) {

		Object parsedValue = _parseValue(infoField, locale, value);

		if (infoField.isLocalizable()) {
			return new InfoFieldValue<>(
				infoField,
				InfoLocalizedValue.builder(
				).defaultLocale(
					locale
				).value(
					locale, parsedValue
				).build());
		}

		return new InfoFieldValue<>(infoField, parsedValue);
	}

	private InfoFieldValue<Object> _getMultiselectInfoFieldValue(
		InfoField infoField, Locale locale, Object value) {

		if (Validator.isNull(value)) {
			value = Collections.emptyList();
		}

		return _getInfoFieldValue(infoField, locale, value);
	}

	private Object _parseValue(
		InfoField<?> infoField, Locale locale, String value) {

		if (infoField.getInfoFieldType() instanceof BooleanInfoFieldType) {
			return GetterUtil.getBoolean(value);
		}

		if (infoField.getInfoFieldType() instanceof DateInfoFieldType) {
			if (Validator.isBlank(value)) {
				return StringPool.BLANK;
			}

			try {
				return DateUtil.parseDate("yyyy-MM-dd", value, locale);
			}
			catch (ParseException parseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(parseException);
				}
			}
		}

		if (infoField.getInfoFieldType() instanceof DateTimeInfoFieldType) {
			if (Validator.isBlank(value)) {
				return StringPool.BLANK;
			}

			try {
				return LocalDateTime.parse(
					value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			}
			catch (DateTimeParseException dateTimeParseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(dateTimeParseException);
				}
			}
		}

		if (infoField.getInfoFieldType() instanceof NumberInfoFieldType) {
			InfoField<NumberInfoFieldType> numberInfoFieldTypeInfoField =
				(InfoField<NumberInfoFieldType>)infoField;

			if (GetterUtil.getBoolean(
					numberInfoFieldTypeInfoField.getAttribute(
						NumberInfoFieldType.DECIMAL))) {

				if (Validator.isBlank(value)) {
					return StringPool.BLANK;
				}

				return new BigDecimal(value);
			}

			return GetterUtil.getLong(value);
		}

		if (infoField.getInfoFieldType() instanceof FileInfoFieldType ||
			infoField.getInfoFieldType() instanceof HTMLInfoFieldType ||
			infoField.getInfoFieldType() instanceof MultiselectInfoFieldType ||
			infoField.getInfoFieldType() instanceof LongTextInfoFieldType ||
			infoField.getInfoFieldType() instanceof RelationshipInfoFieldType ||
			infoField.getInfoFieldType() instanceof SelectInfoFieldType ||
			infoField.getInfoFieldType() instanceof TextInfoFieldType) {

			return value;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoRequestFieldValuesProviderHelper.class);

	private final InfoItemServiceRegistry _infoItemServiceRegistry;

}