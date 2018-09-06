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

import static com.liferay.structure.apio.internal.util.LocalizedValueUtil.getLocalizedString;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.structure.apio.architect.model.FormLayoutPage;
import com.liferay.structure.apio.architect.util.StructureRepresentorBuilderHelper;
import com.liferay.structure.apio.internal.model.FormLayoutPageImpl;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Provides the information necessary to expose Structure resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Paulo Cruz
 */
@Component(immediate = true, service = StructureRepresentorBuilderHelper.class)
public class StructureRepresentorBuilderHelperImpl
	implements StructureRepresentorBuilderHelper {

	@Override
	public NestedRepresentor.FirstStep<DDMFormField> buildDDMFormFieldFirstStep(
		NestedRepresentor.Builder<DDMFormField> builder) {

		return builder.types(
			"FormField"
		).addBoolean(
			"autocomplete",
			getDDMFormFieldPropertyFunction(Boolean.class::cast, "autocomplete")
		).addBoolean(
			"inline",
			getDDMFormFieldPropertyFunction(Boolean.class::cast, "inline")
		).addBoolean(
			"localizable", DDMFormField::isLocalizable
		).addBoolean(
			"multiple", DDMFormField::isMultiple
		).addBoolean(
			"readOnly", DDMFormField::isReadOnly
		).addBoolean(
			"repeatable", DDMFormField::isRepeatable
		).addBoolean(
			"required", DDMFormField::isRequired
		).addBoolean(
			"showAsSwitcher",
			getDDMFormFieldPropertyFunction(
				Boolean.class::cast, "showAsSwitcher")
		).addBoolean(
			"showLabel", DDMFormField::isShowLabel
		).addLocalizedStringByLocale(
			"label", getLocalizedString(DDMFormField::getLabel)
		).addLocalizedStringByLocale(
			"placeholder", getLocalizedStringBiFunction("placeholder")
		).addLocalizedStringByLocale(
			"predefinedValue",
			getLocalizedString(DDMFormField::getPredefinedValue)
		).addLocalizedStringByLocale(
			"style", getLocalizedString(DDMFormField::getStyle)
		).addLocalizedStringByLocale(
			"tip", getLocalizedString(DDMFormField::getTip)
		).addNested(
			"validation", DDMFormField::getDDMFormFieldValidation,
			StructureRepresentorBuilderHelperImpl::_buildValidationProperties
		).addNestedList(
			"options",
			getLocalizedValueEntriesFunction(
				DDMFormField::getDDMFormFieldOptions),
			this::_buildFieldOptions
		).addString(
			"additionalType", DDMFormField::getType
		).addString(
			"dataSourceType",
			getDDMFormFieldPropertyFunction(
				String.class::cast, "dataSourceType")
		).addString(
			"dataType", DDMFormField::getDataType
		).addString(
			"displayStyle",
			getDDMFormFieldPropertyFunction(String.class::cast, "displayStyle")
		).addString(
			"name", DDMFormField::getName
		).addString(
			"text", getDDMFormFieldPropertyFunction(String.class::cast, "text")
		);
	}

	@Override
	public Representor.FirstStep<DDMStructure> buildDDMStructureFirstStep(
		Representor.Builder<DDMStructure, Long> builder) {

		return builder.types(
			"Structure"
		).identifier(
			DDMStructure::getStructureId
		).addDate(
			"dateCreated", DDMStructure::getCreateDate
		).addDate(
			"dateModified", DDMStructure::getCreateDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, DDMStructure::getUserId
		).addLocalizedStringByLocale(
			"description", DDMStructure::getDescription
		).addLocalizedStringByLocale(
			"name", DDMStructure::getName
		).addStringList(
			"availableLanguages",
			ddmStructure -> Arrays.asList(
				ddmStructure.getAvailableLanguageIds())
		);
	}

	@Override
	public NestedRepresentor.FirstStep<FormLayoutPage>
		buildFormLayoutPageFirstStep(NestedRepresentor.Builder<FormLayoutPage>
		builder) {

		return builder.types(
			"FormLayoutPage"
		).addLocalizedStringByLocale(
			"headline", FormLayoutPage::getTitle
		).addLocalizedStringByLocale(
			"text", FormLayoutPage::getDescription
		);
	}

	@Override
	public <T> Function<DDMFormField, T> getDDMFormFieldPropertyFunction(
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
			_getFormLayoutPageFunction(ddmStructure)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public BiFunction<DDMFormField, Locale, String>
		getLocalizedStringBiFunction(String key) {

		return LocalizedValueUtil.getLocalizedString(
			ddmFormField -> (LocalizedValue)ddmFormField.getProperty(key));
	}

	@Override
	public Function<DDMFormField, List<Map.Entry<String, LocalizedValue>>>
		getLocalizedValueEntriesFunction(
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

	@Override
	public Function<DDMFormField, List<Map.Entry<String, LocalizedValue>>>
		getLocalizedValueEntriesFunction(String key) {

		return getLocalizedValueEntriesFunction(
			ddmFormField -> (DDMFormFieldOptions)ddmFormField.getProperty(key));
	}

	private static NestedRepresentor<DDMFormFieldValidation>
		_buildValidationProperties(
			NestedRepresentor.Builder<DDMFormFieldValidation> builder) {

		return builder.types(
			"FormFieldProperties"
		).addString(
			"error", DDMFormFieldValidation::getErrorMessage
		).addString(
			"expression", DDMFormFieldValidation::getExpression
		).build();
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
		_getFieldsPerPageFunction(DDMStructure ddmStructure) {

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
		_getFormLayoutPageFunction(DDMStructure ddmStructure) {

		return ddmFormLayoutPage -> Optional.ofNullable(
			_getFieldNames(ddmFormLayoutPage, ddmStructure)
		).map(
			_getFieldsPerPageFunction(ddmStructure)
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

	private NestedRepresentor<Map.Entry<String, LocalizedValue>>
		_buildFieldOptions(
			NestedRepresentor.Builder<Map.Entry<String, LocalizedValue>>
				builder) {

		return builder.types(
			"FormFieldOptions"
		).addLocalizedStringByLocale(
			"label", getLocalizedString(Map.Entry::getValue)
		).addString(
			"value", Map.Entry::getKey
		).build();
	}

}