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

package com.liferay.forms.apio.internal.helper;

import static com.liferay.forms.apio.internal.util.LocalizedValueUtil.getLocalizedString;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.structure.apio.architect.model.FormLayoutPage;
import com.liferay.structure.apio.architect.util.StructureRepresentorBuilderHelper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose DDMFormStructure resources
 * through a web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(
	immediate = true, service = FormStructureRepresentorBuilderHelper.class
)
public class FormStructureRepresentorBuilderHelper {

	public Representor.FirstStep<DDMStructure> buildDDMStructureFirstStep(
		Representor.Builder<DDMStructure, Long> builder) {

		Representor.FirstStep<DDMStructure> ddmStructureFirstStep =
			_structureRepresentorBuilderHelper.buildDDMStructureFirstStep(
				builder);

		Representor.FirstStep<DDMStructure> bidirectionalModelStepBuilder =
			ddmStructureFirstStep.addBidirectionalModel(
				"contentSpace", "formStructures", ContentSpaceIdentifier.class,
				DDMStructure::getGroupId);

		bidirectionalModelStepBuilder.addNested(
			"version", this::_getDDMStructureVersion,
			this::_buildDDMStructureVersion
		).addNested(
			"successPage", this::_getDDMFormSuccessPageSettings,
			this::_buildDDMFormSuccessPageSettings
		).addNestedList(
			"formPages", _structureRepresentorBuilderHelper::getFormLayoutPages,
			nestedBuilder -> _buildFormLayoutPage(nestedBuilder).build()
		);

		return ddmStructureFirstStep;
	}

	private static Function<DDMFormField, Boolean> _getHasFormRulesFunction() {
		return ddmFormField -> Try.fromFallible(
			ddmFormField::getDDMForm
		).map(
			DDMForm::getDDMFormRules
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			DDMFormRule::getCondition
		).anyMatch(
			ruleCondition -> ruleCondition.contains(ddmFormField.getName())
		);
	}

	private NestedRepresentor<DDMFormField> _buildDDMFormField(
		NestedRepresentor.Builder<DDMFormField> builder) {

		return builder.types(
			"FormFieldProperties"
		).addNestedList(
			"columns",
			_structureRepresentorBuilderHelper.getLocalizedValueEntriesFunction(
				"columns"),
			this::_buildFieldOptions
		).addNestedList(
			"rows",
			_structureRepresentorBuilderHelper.getLocalizedValueEntriesFunction(
				"rows"),
			this::_buildFieldOptions
		).build();
	}

	private NestedRepresentor.FirstStep<DDMFormField> _buildDDMFormFields(
		NestedRepresentor.Builder<DDMFormField> builder) {

		NestedRepresentor.FirstStep<DDMFormField> ddmFormFieldFirstStepBuilder =
			_structureRepresentorBuilderHelper.buildDDMFormFieldFirstStep(
				builder);

		ddmFormFieldFirstStepBuilder.addBoolean(
			"hasFormRules", _getHasFormRulesFunction()
		).addBoolean(
			"transient", DDMFormField::isTransient
		).addNested(
			"grid", ddmFormField -> ddmFormField, this::_buildDDMFormField
		).addString(
			"indexType", DDMFormField::getIndexType
		);

		return ddmFormFieldFirstStepBuilder;
	}

	private NestedRepresentor<DDMFormSuccessPageSettings>
		_buildDDMFormSuccessPageSettings(
			NestedRepresentor.Builder<DDMFormSuccessPageSettings> builder) {

		return builder.types(
			"FormSuccessPageSettings"
		).addBoolean(
			"enabled", DDMFormSuccessPageSettings::isEnabled
		).addLocalizedStringByLocale(
			"headline", getLocalizedString(DDMFormSuccessPageSettings::getTitle)
		).addLocalizedStringByLocale(
			"text", getLocalizedString(DDMFormSuccessPageSettings::getBody)
		).build();
	}

	private NestedRepresentor<DDMStructureVersion> _buildDDMStructureVersion(
		NestedRepresentor.Builder<DDMStructureVersion> nestedBuilder) {

		return nestedBuilder.types(
			"StructureVersion"
		).addLinkedModel(
			"creator", PersonIdentifier.class, DDMStructureVersion::getUserId
		).addString(
			"name", DDMStructureVersion::getVersion
		).build();
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

	private NestedRepresentor.FirstStep<FormLayoutPage> _buildFormLayoutPage(
		NestedRepresentor.Builder<FormLayoutPage> builder) {

		NestedRepresentor.FirstStep<FormLayoutPage> formLayoutPageFirstStep =
			_structureRepresentorBuilderHelper.buildFormLayoutPageFirstStep(
				builder);

		formLayoutPageFirstStep.addNestedList(
			"fields", FormLayoutPage::getFields,
			ddmFormFieldBuilder -> _buildDDMFormFields(
				ddmFormFieldBuilder).build());

		return formLayoutPageFirstStep;
	}

	private DDMFormSuccessPageSettings _getDDMFormSuccessPageSettings(
		DDMStructure ddmStructure) {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return ddmForm.getDDMFormSuccessPageSettings();
	}

	private DDMStructureVersion _getDDMStructureVersion(
		DDMStructure ddmStructure) {

		return Try.fromFallible(
			ddmStructure::getStructureVersion
		).orElse(
			null
		);
	}

	@Reference
	private StructureRepresentorBuilderHelper
		_structureRepresentorBuilderHelper;

}