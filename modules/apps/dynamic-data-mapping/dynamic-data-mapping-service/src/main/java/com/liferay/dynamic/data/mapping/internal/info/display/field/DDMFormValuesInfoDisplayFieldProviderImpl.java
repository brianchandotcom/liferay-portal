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

package com.liferay.dynamic.data.mapping.internal.info.display.field;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.info.display.field.DDMFormValuesInfoDisplayFieldProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.NumberFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DDMFormValuesInfoDisplayFieldProvider.class)
public class DDMFormValuesInfoDisplayFieldProviderImpl<T extends GroupedModel>
	implements DDMFormValuesInfoDisplayFieldProvider<T> {

	public Map<String, Object> getInfoDisplayFieldsValues(
			T t, DDMFormValues ddmFormValues, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldValues = new HashMap<>();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				ddmFormFieldsValuesMap.entrySet()) {

			_addDDMFormFieldValues(
				t, entry.getKey(), entry.getValue(), infoDisplayFieldValues,
				locale);
		}

		return infoDisplayFieldValues;
	}

	private void _addDDMFormFieldValues(
			T t, String key, List<DDMFormFieldValue> ddmFormFieldValues,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Object fieldValue = null;

		if (ddmFormFieldValues.size() == 1) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

			_addNestedFields(t, ddmFormFieldValue, classTypeValues, locale);

			fieldValue = _sanitizeFieldValue(t, ddmFormFieldValue, locale);
		}
		else {
			Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

			fieldValue = stream.map(
				ddmFormFieldValue -> {
					try {
						_addNestedFields(
							t, ddmFormFieldValue, classTypeValues, locale);

						return _sanitizeFieldValue(
							t, ddmFormFieldValue, locale);
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to sanitize field " +
								ddmFormFieldValue.getName(),
							pe);

						return null;
					}
				}
			).filter(
				value -> value != null
			).collect(
				Collectors.toList()
			);
		}

		classTypeValues.put(key, fieldValue);
	}

	private void _addNestedFields(
			T t, DDMFormFieldValue ddmFormFieldValue,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldsValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				nestedDDMFormFieldsValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			_addDDMFormFieldValues(
				t, entry.getKey(), ddmFormFieldValues, classTypeValues, locale);
		}
	}

	private Object _sanitizeFieldValue(
			T t, DDMFormFieldValue ddmFormFieldValue, Locale locale)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Objects.equals(ddmFormFieldValue.getType(), "ddm-date")) {
			try {
				DateFormat dateFormat = DateFormat.getDateInstance(
					DateFormat.SHORT, locale);

				Date date = DateUtil.parseDate(
					"yyyy-MM-dd", valueString, locale);

				return dateFormat.format(date);
			}
			catch (Exception e) {
				return valueString;
			}
		}
		else if (Objects.equals(ddmFormFieldValue.getType(), "ddm-decimal")) {
			NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

			return numberFormat.format(GetterUtil.getDouble(valueString));
		}
		else if (Objects.equals(ddmFormFieldValue.getType(), "ddm-image")) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			jsonObject.put("url", _transformFileEntryURL(valueString));

			return jsonObject;
		}

		return SanitizerUtil.sanitize(
			t.getCompanyId(), t.getGroupId(), t.getUserId(),
			t.getModelClassName(), (long)t.getPrimaryKeyObj(),
			ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, valueString, null);
	}

	private String _transformFileEntryURL(String data) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return _dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesInfoDisplayFieldProviderImpl.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}