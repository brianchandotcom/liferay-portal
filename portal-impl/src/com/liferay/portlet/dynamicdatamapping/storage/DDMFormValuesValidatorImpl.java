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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldNameException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldValueException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesValidatorImpl implements DDMFormValuesValidator {

	@Override
	public void validate(DDMFormValues ddmFormValues) throws StorageException {
		DDMForm ddmForm = ddmFormValues.getDDMForm();

		if (ddmForm == null) {
			throw new NullPointerException("A DDM Form instance was never set");
		}

		validateDDMFormFieldValues(
			ddmForm.getDDMFormFieldsMap(false),
			ddmFormValues.getDDMFormFieldValues());
	}

	protected boolean isNull(Value value) {
		if ((value == null) || MapUtil.isEmpty(value.getValues())) {
			return true;
		}

		return false;
	}

	protected void validateDDMFormFieldValue(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws StorageException {

		if (ddmFormField == null) {
			throw new StorageFieldNameException(
				"There is no such field name defined on DDM Form " +
					ddmFormFieldValue.getName());
		}

		validateDDMFormFieldValue(
			ddmFormField, ddmFormFieldValue.getDDMFormValues(),
			ddmFormFieldValue.getValue());

		validateDDMFormFieldValues(
			ddmFormField.getNestedDDMFormFieldsMap(),
			ddmFormFieldValue.getNestedDDMFormFieldValues());
	}

	protected void validateDDMFormFieldValue(
			DDMFormField ddmFormField, DDMFormValues ddmFormValues, Value value)
		throws StorageException {

		if (isNull(value)) {
			if (ddmFormField.isRequired()) {
				throw new StorageFieldRequiredException(
					"No value defined for field " + ddmFormField.getName());
			}
		}
		else {
			if (Validator.isNull(ddmFormField.getDataType())) {
				throw new StorageFieldValueException(
					"Value should not be set for field " +
						ddmFormField.getName());
			}

			if ((ddmFormField.isLocalizable() && !value.isLocalized()) ||
				(!ddmFormField.isLocalizable() && value.isLocalized())) {

				throw new StorageFieldValueException(
					"Invalid value set for field " + ddmFormField.getName());
			}

			validateDDMFormFieldValueLocales(
				ddmFormField, ddmFormValues.getAvailableLocales(),
				ddmFormValues.getDefaultLocale(), value);
		}
	}

	protected void validateDDMFormFieldValueLocales(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, Value value)
		throws StorageException {

		if (!value.isLocalized()) {
			return;
		}

		if (!availableLocales.equals(value.getAvailableLocales())) {
			throw new StorageFieldValueException(
				"Invalid available locales set for field " +
					ddmFormField.getName());
		}

		if (!defaultLocale.equals(value.getDefaultLocale())) {
			throw new StorageFieldValueException(
				"Invalid default locale set for field " +
					ddmFormField.getName());
		}
	}

	protected void validateDDMFormFieldValues(
			Map<String, DDMFormField> ddmFormFieldsMap,
			List<DDMFormFieldValue> ddmFormFieldValues)
		throws StorageException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			validateDDMFormFieldValue(
				ddmFormFieldsMap.get(ddmFormFieldValue.getName()),
				ddmFormFieldValue);
		}
	}

}