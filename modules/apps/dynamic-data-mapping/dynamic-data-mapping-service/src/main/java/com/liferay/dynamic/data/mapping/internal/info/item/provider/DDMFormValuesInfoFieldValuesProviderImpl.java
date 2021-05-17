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

package com.liferay.dynamic.data.mapping.internal.info.item.provider;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.info.field.converter.DDMFormFieldInfoFieldConverter;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMFormValuesInfoFieldValuesProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
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
import com.liferay.portal.kernel.util.LocaleUtil;
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
@Component(service = DDMFormValuesInfoFieldValuesProvider.class)
public class DDMFormValuesInfoFieldValuesProviderImpl
	implements DDMFormValuesInfoFieldValuesProvider<GroupedModel> {

	public List<InfoFieldValue<InfoLocalizedValue<Object>>> getInfoFieldValues(
		GroupedModel groupedModel, DDMFormValues ddmFormValues) {

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		return stream.flatMap(
			ddmFormFieldValue -> {
				List<InfoFieldValue<InfoLocalizedValue<Object>>>
					infoFieldValues = _getInfoFieldValues(
						groupedModel, ddmFormFieldValue);

				return infoFieldValues.stream();
			}
		).collect(
			Collectors.toList()
		);
	}

	private void _addDDMFormFieldValue(
		GroupedModel groupedModel, DDMFormFieldValue ddmFormFieldValue,
		List<InfoFieldValue<InfoLocalizedValue<Object>>> infoFieldValues) {

		_addNestedFields(groupedModel, ddmFormFieldValue, infoFieldValues);

		_getInfoFieldValue(
			groupedModel, ddmFormFieldValue
		).map(
			infoFieldValues::add
		);
	}

	private void _addNestedFields(
		GroupedModel groupedModel, DDMFormFieldValue ddmFormFieldValue,
		List<InfoFieldValue<InfoLocalizedValue<Object>>> infoFieldValues) {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				nestedDDMFormFieldValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			ddmFormFieldValues.forEach(
				nestedDDMFormFieldValue -> _addDDMFormFieldValue(
					groupedModel, nestedDDMFormFieldValue, infoFieldValues));
		}
	}

	private Optional<InfoFieldValue<InfoLocalizedValue<Object>>>
		_getInfoFieldValue(
			GroupedModel groupedModel, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(
			new InfoFieldValue<>(
				_ddmFormFieldInfoFieldConverter.convert(
					_ddmBeanTranslator.translate(
						ddmFormFieldValue.getDDMFormField())),
				InfoLocalizedValue.builder(
				).defaultLocale(
					value.getDefaultLocale()
				).value(
					consumer -> {
						for (Locale locale : value.getAvailableLocales()) {
							consumer.accept(
								locale,
								_sanitizeDDMFormFieldValue(
									groupedModel, ddmFormFieldValue, locale));
						}
					}
				).build()));
	}

	private List<InfoFieldValue<InfoLocalizedValue<Object>>>
		_getInfoFieldValues(
			GroupedModel groupedModel, DDMFormFieldValue ddmFormFieldValue) {

		List<InfoFieldValue<InfoLocalizedValue<Object>>> infoFieldValues =
			new ArrayList<>();

		_addDDMFormFieldValue(groupedModel, ddmFormFieldValue, infoFieldValues);

		return infoFieldValues;
	}

	private WebImage _getWebImage(JSONObject jsonObject) {
		try {
			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			WebImage webImage = new WebImage(
				_dlURLHelper.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK),
				new InfoItemReference(
					FileEntry.class.getName(),
					new ClassPKInfoItemIdentifier(fileEntry.getFileEntryId())));

			webImage.setAlt(jsonObject.getString("alt"));

			return webImage;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	private Object _sanitizeDDMFormFieldValue(
		GroupedModel groupedModel, DDMFormFieldValue ddmFormFieldValue,
		Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		try {
			if (Objects.equals(
					ddmFormFieldValue.getType(), DDMFormFieldType.DATE) ||
				Objects.equals(ddmFormFieldValue.getType(), "date")) {

				if (Validator.isNull(valueString)) {
					return null;
				}

				if (locale.equals(LocaleUtil.ROOT)) {
					locale = LocaleUtil.getSiteDefault();
				}

				DateFormat dateFormat = DateFormat.getDateInstance(
					DateFormat.SHORT, locale);

				Date date = DateUtil.parseDate(
					"yyyy-MM-dd", valueString, locale);

				return dateFormat.format(date);
			}
			else if (Objects.equals(
						ddmFormFieldValue.getType(),
						DDMFormFieldType.DECIMAL) ||
					 Objects.equals(
						 ddmFormFieldValue.getType(),
						 DDMFormFieldType.NUMERIC)) {

				if (Validator.isNull(valueString)) {
					return null;
				}

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					locale);

				DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

				String dataType = ddmFormField.getDataType();

				if (dataType.equals(FieldConstants.DOUBLE) ||
					dataType.equals(FieldConstants.FLOAT)) {

					numberFormat.setMinimumFractionDigits(1);
				}

				return numberFormat.format(numberFormat.parse(valueString));
			}
			else if (Objects.equals(
						ddmFormFieldValue.getType(), DDMFormFieldType.IMAGE) ||
					 Objects.equals(ddmFormFieldValue.getType(), "image")) {

				return _getWebImage(
					JSONFactoryUtil.createJSONObject(valueString));
			}

			return SanitizerUtil.sanitize(
				groupedModel.getCompanyId(), groupedModel.getGroupId(),
				groupedModel.getUserId(), groupedModel.getModelClassName(),
				(long)groupedModel.getPrimaryKeyObj(), ContentTypes.TEXT_HTML,
				Sanitizer.MODE_ALL, valueString, null);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to sanitize field " + ddmFormFieldValue.getName(),
				exception);

			return valueString;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesInfoFieldValuesProviderImpl.class);

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DDMFormFieldInfoFieldConverter _ddmFormFieldInfoFieldConverter;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}