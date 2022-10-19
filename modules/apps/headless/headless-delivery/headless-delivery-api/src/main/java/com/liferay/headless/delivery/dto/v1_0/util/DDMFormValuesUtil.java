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
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * @author Víctor Galán
 */
public class DDMFormValuesUtil {

	public static String getContent(
		DDMFormValuesSerializer jsonDDMFormValuesSerializer, DDMForm ddmForm,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm) {
			{
				setAvailableLocales(ddmForm.getAvailableLocales());
				setDDMFormFieldValues(ddmFormFieldValues);
				setDefaultLocale(ddmForm.getDefaultLocale());
			}
		};

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	public static DDMFormValues toDDMFormValues(
		Set<Locale> availableLocales, ContentField[] contentFields,
		DDMForm ddmForm, DLAppService dlAppService, long groupId,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale,
		List<DDMFormField> rootDDMFormFields) {

		Map<String, List<ContentField>> contentFieldMap = Optional.ofNullable(
			contentFields
		).map(
			fields -> _toContentFieldsMap(Stream.of(fields))
		).orElse(
			new HashMap<>()
		);

		return new DDMFormValues(ddmForm) {
			{
				setAvailableLocales(
					_getAvailableLocales(availableLocales, ddmForm, groupId));
				setDDMFormFieldValues(
					_flattenDDMFormFieldValues(
						rootDDMFormFields,
						ddmFormField -> _toDDMFormFieldValues(
							contentFieldMap.get(
								ddmFormField.getFieldReference()),
							ddmFormField, dlAppService, groupId,
							journalArticleService, layoutLocalService,
							locale)));
				setDefaultLocale(_getDefaultLocale(ddmForm, locale));
			}
		};
	}

	private static List<DDMFormFieldValue> _flattenDDMFormFieldValues(
		List<DDMFormField> ddmFormFields,
		UnsafeFunction<DDMFormField, List<DDMFormFieldValue>, Exception>
			unsafeFunction) {

		if (ListUtil.isEmpty(ddmFormFields)) {
			return Collections.emptyList();
		}

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.map(
			ddmFormField -> {
				try {
					return unsafeFunction.apply(ddmFormField);
				}
				catch (RuntimeException runtimeException) {
					throw runtimeException;
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		).flatMap(
			List::stream
		).collect(
			Collectors.toList()
		);
	}

	private static Set<Locale> _getAvailableLocales(
		Set<Locale> availableLocales, DDMForm ddmForm, long groupId) {

		if (SetUtil.isEmpty(availableLocales)) {
			return ddmForm.getAvailableLocales();
		}

		Set<Locale> locales = new HashSet<>();

		Set<Locale> siteAvailableLocales = LanguageUtil.getAvailableLocales(
			groupId);

		for (Locale availableLocale : availableLocales) {
			if (siteAvailableLocales.contains(availableLocale)) {
				locales.add(availableLocale);
			}
		}

		return locales;
	}

	private static Locale _getDefaultLocale(
		DDMForm ddmForm, Locale defaultLocale) {

		if (defaultLocale == null) {
			return ddmForm.getDefaultLocale();
		}

		return defaultLocale;
	}

	private static Map<String, List<ContentField>> _toContentFieldsMap(
		Stream<ContentField> stream) {

		return stream.collect(Collectors.groupingBy(ContentField::getName));
	}

	private static DDMFormFieldValue _toDDMFormFieldValue(
		List<ContentField> contentFields, DDMFormField ddmFormField,
		DLAppService dlAppService, long groupId,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale, Value value) {

		Map<String, List<ContentField>> contentFieldMap = _toContentFieldsMap(
			contentFields.stream());

		return new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
				setFieldReference(ddmFormField.getFieldReference());
				setNestedDDMFormFields(
					_flattenDDMFormFieldValues(
						ddmFormField.getNestedDDMFormFields(),
						field -> _toDDMFormFieldValues(
							contentFieldMap.get(field.getFieldReference()),
							field, dlAppService, groupId, journalArticleService,
							layoutLocalService, locale)));
				setValue(value);
			}
		};
	}

	private static List<DDMFormFieldValue> _toDDMFormFieldValues(
		List<ContentField> contentFields, DDMFormField ddmFormField,
		DLAppService dlAppService, long groupId,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale) {

		if (ListUtil.isEmpty(contentFields)) {
			if (ddmFormField.isRequired()) {
				throw new BadRequestException(
					"No value is specified for field " +
						ddmFormField.getFieldReference());
			}

			return Collections.singletonList(
				_toDDMFormFieldValue(
					Collections.emptyList(), ddmFormField, dlAppService,
					groupId, journalArticleService, layoutLocalService, locale,
					_toPredefinedValue(ddmFormField, locale)));
		}

		return TransformUtil.transform(
			contentFields,
			contentField -> _toDDMFormFieldValue(
				ListUtil.fromArray(contentField.getNestedContentFields()),
				ddmFormField, dlAppService, groupId, journalArticleService,
				layoutLocalService, locale,
				DDMValueUtil.toDDMValue(
					contentField, ddmFormField, dlAppService, groupId,
					journalArticleService, layoutLocalService, locale)));
	}

	private static Value _toPredefinedValue(
		DDMFormField ddmFormField, Locale locale) {

		if (Objects.equals(
				DDMFormFieldTypeConstants.SEPARATOR, ddmFormField.getType())) {

			return null;
		}

		LocalizedValue localizedValue = ddmFormField.getPredefinedValue();

		String valueString = GetterUtil.getString(
			localizedValue.getString(localizedValue.getDefaultLocale()));

		if (Objects.equals(valueString, "[]")) {
			valueString = StringPool.BLANK;
		}

		if (ddmFormField.isLocalizable()) {
			String finalValueString = valueString;

			return new LocalizedValue(locale) {
				{
					addString(locale, finalValueString);
				}
			};
		}

		return new UnlocalizedValue(valueString);
	}

}