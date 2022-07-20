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

package com.liferay.info.internal.request.helper;

import com.liferay.info.exception.InfoFormFileUploadException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class InfoRequestFieldValuesProviderHelper {

	public InfoRequestFieldValuesProviderHelper(
		InfoItemServiceTracker infoItemServiceTracker) {

		_infoItemServiceTracker = infoItemServiceTracker;
	}

	public List<InfoFieldValue<Object>> getInfoFieldValues(
			HttpServletRequest httpServletRequest)
		throws InfoFormFileUploadException {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		UploadServletRequest uploadServletRequest =
			PortalUtil.getUploadServletRequest(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

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

			FileItem[] multipartParameters = multipartParameterMap.get(
				infoField.getName());

			if ((multipartParameters != null) &&
				(infoField.getInfoFieldType() instanceof FileInfoFieldType)) {

				for (FileItem fileItem : multipartParameters) {
					if ((fileItem.getSize() < 0) ||
						Validator.isNull(fileItem.getFileName())) {

						continue;
					}

					InfoFieldValue<Object> infoFieldValue =
						_getFileInfoFieldValue(
							fileItem, groupId, infoField, themeDisplay);

					if (infoFieldValue != null) {
						infoFieldValues.add(infoFieldValue);
					}
				}
			}

			List<String> regularParameters = regularParameterMap.get(
				infoField.getName());

			if (regularParameters == null) {
				continue;
			}

			for (String value : regularParameters) {
				InfoFieldValue<Object> infoFieldValue = _getInfoFieldValue(
					infoField, themeDisplay.getLocale(), value);

				if (infoFieldValue != null) {
					infoFieldValues.add(infoFieldValue);
				}
			}
		}

		return infoFieldValues;
	}

	private InfoFieldValue<Object> _getBooleanInfoFieldValue(
		InfoField<?> infoField, Locale locale, String value) {

		return _getInfoFieldValue(
			infoField, locale, GetterUtil.getBoolean(value));
	}

	private InfoFieldValue<Object> _getDateInfoFieldValue(
		InfoField<?> infoField, Locale locale, String value) {

		try {
			Date date = DateUtil.parseDate("yyyy-MM-dd", value, locale);

			return _getInfoFieldValue(infoField, locale, date);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}
		}

		return null;
	}

	private InfoFieldValue<Object> _getFileInfoFieldValue(
			FileItem fileItem, long groupId, InfoField infoField,
			ThemeDisplay themeDisplay)
		throws InfoFormFileUploadException {

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
			_infoItemServiceTracker.getFirstInfoItemService(
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

		if (Validator.isBlank(value)) {
			return null;
		}

		if (infoField.getInfoFieldType() instanceof BooleanInfoFieldType) {
			return _getBooleanInfoFieldValue(infoField, locale, value);
		}

		if (infoField.getInfoFieldType() instanceof DateInfoFieldType) {
			return _getDateInfoFieldValue(infoField, locale, value);
		}

		if (infoField.getInfoFieldType() instanceof NumberInfoFieldType) {
			return _getNumberInfoFieldValue(infoField, locale, value);
		}

		if (infoField.getInfoFieldType() instanceof FileInfoFieldType ||
			infoField.getInfoFieldType() instanceof RelationshipInfoFieldType ||
			infoField.getInfoFieldType() instanceof SelectInfoFieldType ||
			infoField.getInfoFieldType() instanceof TextInfoFieldType) {

			return _getInfoFieldValue(infoField, locale, (Object)value);
		}

		return null;
	}

	private InfoFieldValue<Object> _getNumberInfoFieldValue(
		InfoField infoField, Locale locale, String value) {

		Object objectValue = null;

		Optional<Boolean> decimalOptional = infoField.getAttributeOptional(
			NumberInfoFieldType.DECIMAL);

		if (decimalOptional.orElse(false)) {
			objectValue = new BigDecimal(value);
		}
		else {
			objectValue = GetterUtil.getLong(value);
		}

		return _getInfoFieldValue(infoField, locale, objectValue);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoRequestFieldValuesProviderHelper.class);

	private final InfoItemServiceTracker _infoItemServiceTracker;

}