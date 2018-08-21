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

package com.liferay.structure.apio.internal.util;

import com.liferay.apio.architect.functional.Try;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.structure.apio.architect.model.FormLayoutPage;
import com.liferay.structure.apio.architect.util.StructureRepresentorUtil;
import com.liferay.structure.apio.internal.model.FormLayoutPageImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Paulo Cruz
 */
@Component(immediate = true, service = StructureRepresentorUtil.class)
public final class StructureRepresentorUtilImpl implements
	StructureRepresentorUtil {

	public Function
		<DDMFormField, List<Map.Entry<String, LocalizedValue>>> getFieldOptions(
		Function<DDMFormField, DDMFormFieldOptions> function) {

		return ddmFormField -> Try.fromFallible(
			() -> function.apply(ddmFormField)
		).map(
			DDMFormFieldOptions::getOptions
		).map(
			Map::entrySet
		).map(
			ArrayList::new
		).orElse(
			null
		);
	}

	public Function
		<DDMFormField, List<Map.Entry<String, LocalizedValue>>> getFieldOptions(
		String key) {

		return getFieldOptions(
			ddmFormField -> (DDMFormFieldOptions)ddmFormField.getProperty(key));
	}

	@Override
	public <T> Function<DDMFormField, T> getFieldProperty(
		Function<Object, T> parseFunction, String key) {

		return ddmFormField -> Try.fromFallible(
			() -> ddmFormField.getProperty(key)
		).map(
			parseFunction::apply
		).orElse(
			null
		);
	}

	@Override
	public List<FormLayoutPage> getFormLayoutPages(DDMStructure ddmStructure) {
		return Try.fromFallible(
			ddmStructure::getDDMFormLayout
		).map(
			DDMFormLayout::getDDMFormLayoutPages
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			_getFormLayoutPage(ddmStructure)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public BiFunction<DDMFormField, Locale, String> getLocalizedString(
		String key) {

		return LocalizedValueUtil.getLocalizedString(
			ddmFormField -> (LocalizedValue)ddmFormField.getProperty(key));
	}

	private static List<String> _getFieldNames(
		DDMFormLayoutPage ddmFormLayoutPage, DDMStructure ddmStructure) {

		return Optional.ofNullable(
			ddmFormLayoutPage.getDDMFormLayoutRows()
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			DDMFormLayoutRow::getDDMFormLayoutColumns
		).flatMap(
			List::stream
		).map(
			DDMFormLayoutColumn::getDDMFormFieldNames
		).map(
			formFieldNames -> _getNestedFieldNames(formFieldNames, ddmStructure)
		).flatMap(
			List::stream
		).collect(
			Collectors.toList()
		);
	}

	private static Function<List<String>, List<DDMFormField>>
		_getFieldsPerPage(DDMStructure ddmStructure) {

		return fieldNamesPerPage -> Try.fromFallible(
			() -> ddmStructure.getDDMFormFields(true)
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).filter(
			ddmFormField -> fieldNamesPerPage.contains(ddmFormField.getName())
		).collect(
			Collectors.toList()
		);
	}

	private static Function<DDMFormLayoutPage, FormLayoutPage>
		_getFormLayoutPage(DDMStructure ddmStructure) {

		return ddmFormLayoutPage -> Optional.ofNullable(
			_getFieldNames(ddmFormLayoutPage, ddmStructure)
		).map(
			_getFieldsPerPage(ddmStructure)
		).map(
			ddmFormFields -> new FormLayoutPageImpl(
				ddmFormLayoutPage, ddmFormFields)
		).orElse(
			null
		);
	}

	private static List<String> _getNestedFieldNames(
		List<String> ddmFormFieldNames, DDMStructure ddmStructure) {

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		return ddmFormFieldStream.filter(
			formField -> ddmFormFieldNames.contains(formField.getName())
		).map(
			formField -> formField.getNestedDDMFormFields(
			).stream(
			).map(
				DDMFormField::getName
			).collect(
				Collectors.toList()
			)
		).map(
			fieldNames -> _getNestedFieldNames(fieldNames, ddmStructure)
		).peek(
			fieldNames -> fieldNames.addAll(ddmFormFieldNames)
		).flatMap(
			Collection::stream
		).collect(
			Collectors.toList()
		);
	}

}