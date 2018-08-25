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
import static com.liferay.structure.apio.internal.util.StructureRepresentorUtil.getFieldOptions;
import static com.liferay.structure.apio.internal.util.StructureRepresentorUtil.getFieldProperty;

import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.NestedRepresentor.Builder;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.structure.apio.architect.util.StructureRepresentorBuilderHelper;
import com.liferay.structure.apio.internal.model.FormLayoutPage;

import java.util.Arrays;
import java.util.Map.Entry;

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
		).addNestedList(
			"formPages", StructureRepresentorUtil::getPages,
			StructureRepresentorBuilderHelperImpl::_buildFormPages
		).addStringList(
			"availableLanguages",
			ddmStructure -> Arrays.asList(
				ddmStructure.getAvailableLanguageIds())
		);
	}

	private static NestedRepresentor<Entry<String, LocalizedValue>>
		_buildFieldOptions(Builder<Entry<String, LocalizedValue>> builder) {

		return builder.types(
			"FormFieldOptions"
		).addLocalizedStringByLocale(
			"label", getLocalizedString(Entry::getValue)
		).addString(
			"value", Entry::getKey
		).build();
	}

	private static NestedRepresentor<DDMFormField> _buildFormFields(
		Builder<DDMFormField> builder) {

		return builder.types(
			"FormField"
		).addBoolean(
			"autocomplete",
			getFieldProperty(Boolean.class::cast, "autocomplete")
		).addBoolean(
			"inline", getFieldProperty(Boolean.class::cast, "inline")
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
			getFieldProperty(Boolean.class::cast, "showAsSwitcher")
		).addBoolean(
			"showLabel", DDMFormField::isShowLabel
		).addLocalizedStringByLocale(
			"label", getLocalizedString(DDMFormField::getLabel)
		).addLocalizedStringByLocale(
			"placeholder",
			StructureRepresentorUtil.getLocalizedString("placeholder")
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
			"options", getFieldOptions(DDMFormField::getDDMFormFieldOptions),
			StructureRepresentorBuilderHelperImpl::_buildFieldOptions
		).addString(
			"additionalType", DDMFormField::getType
		).addString(
			"dataSourceType",
			getFieldProperty(String.class::cast, "dataSourceType")
		).addString(
			"dataType", DDMFormField::getDataType
		).addString(
			"displayStyle", getFieldProperty(String.class::cast, "displayStyle")
		).addString(
			"name", DDMFormField::getName
		).addString(
			"text", getFieldProperty(String.class::cast, "text")
		).build();
	}

	private static NestedRepresentor<FormLayoutPage> _buildFormPages(
		Builder<FormLayoutPage> builder) {

		return builder.types(
			"FormLayoutPage"
		).addLocalizedStringByLocale(
			"headline", FormLayoutPage::getTitle
		).addLocalizedStringByLocale(
			"text", FormLayoutPage::getDescription
		).addNestedList(
			"fields", FormLayoutPage::getFields,
			StructureRepresentorBuilderHelperImpl::_buildFormFields
		).build();
	}

	private static NestedRepresentor<DDMFormFieldValidation>
		_buildValidationProperties(Builder<DDMFormFieldValidation> builder) {

		return builder.types(
			"FormFieldProperties"
		).addString(
			"error", DDMFormFieldValidation::getErrorMessage
		).addString(
			"expression", DDMFormFieldValidation::getExpression
		).build();
	}

}