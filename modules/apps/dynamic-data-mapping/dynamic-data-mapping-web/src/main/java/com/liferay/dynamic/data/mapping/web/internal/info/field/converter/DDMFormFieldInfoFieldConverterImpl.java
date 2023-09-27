/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.info.field.converter;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.info.field.converter.DDMFormFieldInfoFieldConverter;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.GridInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DDMFormFieldInfoFieldConverter.class)
public class DDMFormFieldInfoFieldConverterImpl
	implements DDMFormFieldInfoFieldConverter {

	@Override
	public InfoField convert(DDMFormField ddmFormField) {
		InfoFieldType infoFieldType = _getInfoFieldType(ddmFormField);
		LocalizedValue label = ddmFormField.getLabel();

		return _addAttributes(
			ddmFormField,
			InfoField.builder(
			).infoFieldType(
				infoFieldType
			).namespace(
				DDMStructure.class.getSimpleName()
			).name(
				ddmFormField.getName()
			)
		).editable(
			_isInfoFieldEditable(infoFieldType)
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).values(
				label.getValues()
			).defaultLocale(
				label.getDefaultLocale()
			).build()
		).localizable(
			ddmFormField.isLocalizable()
		).required(
			ddmFormField.isRequired()
		).build();
	}

	private InfoField.FinalStep _addAttributes(
		DDMFormField ddmFormField, InfoField.FinalStep finalStep) {

		if (Objects.equals(
				ddmFormField.getType(),
				DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE)) {

			finalStep.attribute(
				MultiselectInfoFieldType.OPTIONS,
				_getOptionInfoFieldTypes(ddmFormField));
		}

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.NUMERIC) &&
			Objects.equals(ddmFormField.getDataType(), FieldConstants.DOUBLE)) {

			finalStep.attribute(NumberInfoFieldType.DECIMAL, true);
		}

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.RADIO)) {

			finalStep.attribute(
				SelectInfoFieldType.OPTIONS,
				_getOptionInfoFieldTypes(ddmFormField));
		}

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.SELECT)) {

			if (GetterUtil.getBoolean(ddmFormField.getProperty("multiple"))) {
				finalStep.attribute(
					MultiselectInfoFieldType.OPTIONS,
					_getOptionInfoFieldTypes(ddmFormField));
			}
			else {
				finalStep.attribute(
					SelectInfoFieldType.OPTIONS,
					_getOptionInfoFieldTypes(ddmFormField));
			}
		}

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.TEXT) &&
			Objects.equals(
				ddmFormField.getProperty("displayStyle"), "multiline")) {

			finalStep.attribute(TextInfoFieldType.MULTILINE, true);
		}

		return finalStep;
	}

	private InfoFieldType _getInfoFieldType(DDMFormField ddmFormField) {
		String ddmFormFieldType = ddmFormField.getType();

		if (Objects.equals(
				ddmFormFieldType, DDMFormFieldTypeConstants.CHECKBOX)) {

			return BooleanInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType,
					DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE)) {

			return MultiselectInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.DATE)) {

			return DateInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.DATE_TIME)) {

			return DateInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.GRID)) {

			return GridInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.IMAGE)) {

			return ImageInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType,
					DDMFormFieldTypeConstants.LINK_TO_LAYOUT)) {

			return URLInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.NUMERIC)) {

			return NumberInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormFieldType, DDMFormFieldTypeConstants.RADIO) ||
				 Objects.equals(
					 ddmFormFieldType, DDMFormFieldTypeConstants.SELECT)) {

			return SelectInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.RICH_TEXT)) {

			return HTMLInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private List<OptionInfoFieldType> _getOptionInfoFieldTypes(
		DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		return TransformUtil.transform(
			ddmFormFieldOptions.getOptionsValues(),
			value -> {
				LocalizedValue localizedValue =
					ddmFormFieldOptions.getOptionLabels(value);

				return new OptionInfoFieldType(
					new FunctionInfoLocalizedValue<>(localizedValue::getString),
					value);
			});
	}

	private boolean _isInfoFieldEditable(InfoFieldType infoFieldType) {
		if (Objects.equals(infoFieldType, BooleanInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, SelectInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, DateInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, HTMLInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, ImageInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, MultiselectInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, NumberInfoFieldType.INSTANCE) ||
			Objects.equals(infoFieldType, TextInfoFieldType.INSTANCE)) {

			return true;
		}

		return false;
	}

}