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
import static com.liferay.structure.apio.internal.util.StructureRepresentorUtil.hasFormRules;

import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.NestedRepresentor.Builder;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.structure.apio.architect.util.StructureRepresentorBuilderHelper;
import com.liferay.structure.apio.internal.model.FormLayoutPage;

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
		).addNested(
			"successPage", StructureRepresentorUtil::getSuccessPage,
			StructureRepresentorBuilderHelperImpl::_buildSuccessPageSettings
		).addNested(
			"version", StructureRepresentorUtil::getVersion,
			StructureRepresentorBuilderHelperImpl::_buildVersion
		).addNestedList(
			"pages", StructureRepresentorUtil::getPages,
			StructureRepresentorBuilderHelperImpl::_buildFormPages
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
			"hasFormRules", hasFormRules()
		).addBoolean(
			"isAutocomplete",
			getFieldProperty(Boolean.class::cast, "autocomplete")
		).addBoolean(
			"isInline", getFieldProperty(Boolean.class::cast, "inline")
		).addBoolean(
			"isLocalizable", DDMFormField::isLocalizable
		).addBoolean(
			"isMultiple", DDMFormField::isMultiple
		).addBoolean(
			"isReadOnly", DDMFormField::isReadOnly
		).addBoolean(
			"isRepeatable", DDMFormField::isRepeatable
		).addBoolean(
			"isRequired", DDMFormField::isRequired
		).addBoolean(
			"isShowAsSwitcher",
			getFieldProperty(Boolean.class::cast, "showAsSwitcher")
		).addBoolean(
			"isShowLabel", DDMFormField::isShowLabel
		).addBoolean(
			"isTransient", DDMFormField::isTransient
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
			"grid", ddmFormField -> ddmFormField,
			StructureRepresentorBuilderHelperImpl::_buildGridProperties
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
			"indexType", DDMFormField::getIndexType
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

	private static NestedRepresentor<DDMFormField> _buildGridProperties(
		Builder<DDMFormField> builder) {

		return builder.types(
			"FormFieldProperties"
		).addNestedList(
			"columns", getFieldOptions("columns"),
			StructureRepresentorBuilderHelperImpl::_buildFieldOptions
		).addNestedList(
			"rows", getFieldOptions("rows"),
			StructureRepresentorBuilderHelperImpl::_buildFieldOptions
		).build();
	}

	private static NestedRepresentor<DDMFormSuccessPageSettings>
		_buildSuccessPageSettings(Builder<DDMFormSuccessPageSettings> builder) {

		return builder.types(
			"FormSuccessPageSettings"
		).addBoolean(
			"isEnabled", DDMFormSuccessPageSettings::isEnabled
		).addLocalizedStringByLocale(
			"headline", getLocalizedString(DDMFormSuccessPageSettings::getTitle)
		).addLocalizedStringByLocale(
			"text", getLocalizedString(DDMFormSuccessPageSettings::getBody)
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

	private static NestedRepresentor<DDMStructureVersion> _buildVersion(
		Builder<DDMStructureVersion> nestedBuilder) {

		return nestedBuilder.types(
			"StructureVersion"
		).addLinkedModel(
			"creator", PersonIdentifier.class, DDMStructureVersion::getUserId
		).addString(
			"name", DDMStructureVersion::getVersion
		).build();
	}

}