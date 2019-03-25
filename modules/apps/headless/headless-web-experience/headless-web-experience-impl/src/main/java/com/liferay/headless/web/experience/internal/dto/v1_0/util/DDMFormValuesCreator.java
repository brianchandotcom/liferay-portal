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

package com.liferay.headless.web.experience.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * Create the {@link DDMFormValues} exracting information from the {@link
 * ContentField}.
 *
 * <p>
 * It always generates valid DDMFormValues. If some of the fields are not
 * present in the ContentField array, it will create it using the {@link
 * DDMFormField#getPredefinedValue()}
 * </p>
 *
 * @author Víctor Galán
 * @review
 */
public class DDMFormValuesCreator {

	public DDMFormValuesCreator(
		DLAppService dlAppService,
		JournalArticleService journalArticleService) {

		_dlAppService = dlAppService;
		_journalArticleService = journalArticleService;
	}

	public DDMFormValues createDDMFormValues(
		ContentField[] contentFields, DDMForm ddmForm, Locale locale,
		List<DDMFormField> rootDDMFormFields) {

		Map<String, List<ContentField>> contentFieldMap = _getContentFieldMap(
			contentFields);

		return new DDMFormValues(ddmForm) {
			{
				DDMForm ddmForm = getDDMForm();

				setAvailableLocales(ddmForm.getAvailableLocales());

				setDDMFormFieldValues(
					_flattenDDMFormFieldValues(
						rootDDMFormFields,
						field -> _createDDMFormFieldValue(
							contentFieldMap.get(field.getName()), field,
							locale)));
				setDefaultLocale(ddmForm.getDefaultLocale());
			}
		};
	}

	private static Map<String, List<ContentField>> _getContentFieldMap(
		ContentField[] contentFields) {

		return Stream.of(
			contentFields
		).collect(
			Collectors.groupingBy(ContentField::getName)
		);
	}

	private List<DDMFormFieldValue> _createDDMFormFieldValue(
		List<ContentField> contentFields, DDMFormField ddmFormField,
		Locale locale) {

		if (ListUtil.isEmpty(contentFields)) {
			if (ddmFormField.isRequired()) {
				throw new BadRequestException(
					"No value defined for field name " +
						ddmFormField.getName());
			}

			return Collections.singletonList(
				_toDDMFormFieldValue(
					Collections.emptyList(), ddmFormField, locale,
					_getPredefinedValue(ddmFormField, locale)));
		}

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>(
			contentFields.size());

		for (ContentField contentField : contentFields) {
			DDMFormFieldValue ddmFormFieldValue = _toDDMFormFieldValue(
				ListUtil.toList(contentField.getNestedFields()), ddmFormField,
				locale,
				DDMValueUtil.toDDMValue(
					contentField, ddmFormField, _dlAppService,
					_journalArticleService, locale));

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		return ddmFormFieldValues;
	}

	private List<DDMFormFieldValue> _flattenDDMFormFieldValues(
		List<DDMFormField> list,
		UnsafeFunction<DDMFormField, List<DDMFormFieldValue>, Exception>
			unsafeFunction) {

		if (ListUtil.isEmpty(list)) {
			return Collections.emptyList();
		}

		Stream<DDMFormField> stream = list.stream();

		return stream.map(
			ddmFormField -> {
				try {
					return unsafeFunction.apply(ddmFormField);
				}
				catch (RuntimeException re) {
					throw re;
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		).flatMap(
			List::stream
		).collect(
			Collectors.toList()
		);
	}

	private Value _getPredefinedValue(
		DDMFormField ddmFormField, Locale locale) {

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		String value = predefinedValue.getString(
			predefinedValue.getDefaultLocale());

		if (ddmFormField.isLocalizable()) {
			return new LocalizedValue() {
				{
					setDefaultLocale(locale);
					addString(locale, value);
				}
			};
		}

		return new UnlocalizedValue(value);
	}

	private DDMFormFieldValue _toDDMFormFieldValue(
		List<ContentField> contentFields, DDMFormField ddmFormField,
		Locale locale, Value fieldValue) {

		Map<String, List<ContentField>> contentFieldMap = _getContentFieldMap(
			contentFields.toArray(new ContentField[0]));

		return new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
				setNestedDDMFormFields(
					_flattenDDMFormFieldValues(
						ddmFormField.getNestedDDMFormFields(),
						field -> _createDDMFormFieldValue(
							contentFieldMap.get(field.getName()), field,
							locale)));

				setValue(fieldValue);
			}
		};
	}

	private final DLAppService _dlAppService;
	private final JournalArticleService _journalArticleService;

}