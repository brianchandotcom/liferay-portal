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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DDMFormValuesInfoFieldSetProvider.class)
public class DDMFormValuesInfoFieldSetProvider<T extends GroupedModel> {

	public List<InfoFieldValue> getInfoFieldValues(
		T t, DDMFormValues ddmFormValues) {

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		return stream.flatMap(
			ddmFormFieldValue -> {
				List<InfoFieldValue> infoFieldValues = _getInfoFieldValues(
					t, ddmFormFieldValue);

				return infoFieldValues.stream();
			}
		).collect(
			Collectors.toList()
		);
	}

	private void _addDDMFormFieldValue(
		T t, DDMFormFieldValue ddmFormFieldValue,
		List<InfoFieldValue> infoFieldValues) {

		_addNestedFields(t, ddmFormFieldValue, infoFieldValues);

		_getInfoFieldValue(
			t, ddmFormFieldValue
		).map(
			infoFieldValues::add
		);
	}

	private void _addNestedFields(
		T t, DDMFormFieldValue ddmFormFieldValue,
		List<InfoFieldValue> infoFieldValues) {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				nestedDDMFormFieldValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			ddmFormFieldValues.forEach(
				nestedDDMFormFieldValue -> _addDDMFormFieldValue(
					t, nestedDDMFormFieldValue, infoFieldValues));
		}
	}

	private Optional<InfoFieldValue> _getInfoFieldValue(
		T t, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(
			new InfoFieldValue<>(
				new InfoField(
					TextInfoFieldType.INSTANCE,
					InfoLocalizedValue.localize(
						getClass(), ddmFormFieldValue.getName()),
					ddmFormFieldValue.getName()),
				InfoLocalizedValue.builder(
				).defaultLocale(
					value.getDefaultLocale()
				).addValues(
					value.getValues(
					).entrySet(
					).stream(
					).collect(
						Collectors.toMap(
							Map.Entry::getKey,
							entry -> _sanitizeDDMFormFieldValue(
								t, ddmFormFieldValue, entry.getKey()))
					)
				).build()));
	}

	private List<InfoFieldValue> _getInfoFieldValues(
		T t, DDMFormFieldValue ddmFormFieldValue) {

		List<InfoFieldValue> infoFieldValues = new ArrayList<>();

		_addDDMFormFieldValue(t, ddmFormFieldValue, infoFieldValues);

		return infoFieldValues;
	}

	private Object _sanitizeDDMFormFieldValue(
		T t, DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		try {
			if (Objects.equals(ddmFormFieldValue.getType(), "date") ||
				Objects.equals(ddmFormFieldValue.getType(), "ddm-date")) {

				DateFormat dateFormat = DateFormat.getDateInstance(
					DateFormat.SHORT, locale);

				Date date = DateUtil.parseDate(
					"yyyy-MM-dd", valueString, locale);

				return dateFormat.format(date);
			}
			else if (Objects.equals(
						ddmFormFieldValue.getType(), "ddm-decimal") ||
					 Objects.equals(ddmFormFieldValue.getType(), "numeric")) {

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					locale);

				return numberFormat.format(GetterUtil.getDouble(valueString));
			}
			else if (Objects.equals(ddmFormFieldValue.getType(), "ddm-image") ||
					 Objects.equals(ddmFormFieldValue.getType(), "image")) {

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
		catch (Exception exception) {
			_log.error(
				"Unable to sanitize field " + ddmFormFieldValue.getName(),
				exception);

			return valueString;
		}
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesInfoFieldSetProvider.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}