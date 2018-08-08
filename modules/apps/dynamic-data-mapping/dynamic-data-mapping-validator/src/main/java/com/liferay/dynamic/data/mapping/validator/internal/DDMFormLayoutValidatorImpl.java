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

package com.liferay.dynamic.data.mapping.validator.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorErrorStatus;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorValidateRequest;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pablo Carvalho
 */
@Component(immediate = true)
public class DDMFormLayoutValidatorImpl implements DDMFormLayoutValidator {

	@Override
	public void validate(
			DDMFormLayoutValidatorValidateRequest validateFormLayoutRequest)
		throws DDMFormLayoutValidationException {

		List<DDMFormLayoutValidatorError> validateFormLayoutErrors =
			new ArrayList<>();

		DDMFormLayout ddmFormLayout =
			validateFormLayoutRequest.getDDMFormLayout();

		validateFormLayoutErrors.addAll(
			validateDDMFormLayoutDefaultLocale(ddmFormLayout));

		validateFormLayoutErrors.addAll(
			validateDDMFormFieldNames(ddmFormLayout));
		validateFormLayoutErrors.addAll(
			validateDDMFormLayoutPageTitles(ddmFormLayout));
		validateFormLayoutErrors.addAll(
			validateDDMFormLayoutRowSizes(ddmFormLayout));

		if (!validateFormLayoutErrors.isEmpty()) {
			throw new DDMFormLayoutValidationException(
				validateFormLayoutErrors);
		}
	}

	protected List<DDMFormLayoutValidatorError> validateDDMFormFieldNames(
		DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutValidatorError> validateFormLayoutErrors =
			new ArrayList<>();

		Set<String> ddmFormFieldNames = new HashSet<>();

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					Set<String> intersectDDMFormFieldNames = SetUtil.intersect(
						ddmFormFieldNames,
						ddmFormLayoutColumn.getDDMFormFieldNames());

					if (!intersectDDMFormFieldNames.isEmpty()) {
						String errorMessage = String.format(
							"Field names %s were defined more than once",
							intersectDDMFormFieldNames);

						DDMFormLayoutValidatorError.Builder builder =
							DDMFormLayoutValidatorError.Builder.newBuilder(
								errorMessage,
								DDMFormLayoutValidatorErrorStatus.
									MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION
							).withProperty(
								"fields", intersectDDMFormFieldNames
							);

						validateFormLayoutErrors.add(builder.build());
					}

					ddmFormFieldNames.addAll(
						ddmFormLayoutColumn.getDDMFormFieldNames());
				}
			}
		}

		return validateFormLayoutErrors;
	}

	protected List<DDMFormLayoutValidatorError>
		validateDDMFormLayoutDefaultLocale(DDMFormLayout ddmFormLayout) {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		if (defaultLocale == null) {
			DDMFormLayoutValidatorError validateFormLayoutResponseError =
				DDMFormLayoutValidatorError.Builder.of(
					"DDM form layout does not have a default locale",
					DDMFormLayoutValidatorErrorStatus.
						MUST_SET_DEFAULT_LOCALE_EXCEPTION);

			return Arrays.asList(validateFormLayoutResponseError);
		}

		return Collections.emptyList();
	}

	protected List<DDMFormLayoutValidatorError> validateDDMFormLayoutPageTitles(
		DDMFormLayout ddmFormLayout) {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		if (defaultLocale == null) {
			return Collections.emptyList();
		}

		List<DDMFormLayoutValidatorError> validateFormLayoutResponseErrors =
			new ArrayList<>();

		String errorMessage =
			"The default locale for the DDM form layout's page title is not " +
				"the same as the DDM form layout's default locale";

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			if (!defaultLocale.equals(title.getDefaultLocale())) {
				DDMFormLayoutValidatorError validateFormLayoutResponseError =
					DDMFormLayoutValidatorError.Builder.of(
						errorMessage,
						DDMFormLayoutValidatorErrorStatus.
							MUST_SET_EQUAL_LOCALE_FOR_LAYOUT_EXCEPTION);

				validateFormLayoutResponseErrors.add(
					validateFormLayoutResponseError);
			}
		}

		return validateFormLayoutResponseErrors;
	}

	protected List<DDMFormLayoutValidatorError> validateDDMFormLayoutRowSizes(
		DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutValidatorError> validateFormLayoutResponseErrors =
			new ArrayList<>();

		boolean invalidColumnSize = false;
		boolean invalidRowSize = false;

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				int rowSize = 0;

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					int columnSize = ddmFormLayoutColumn.getSize();

					if ((columnSize <= 0) || (columnSize > _MAX_ROW_SIZE)) {
						invalidColumnSize = true;
					}

					rowSize += ddmFormLayoutColumn.getSize();
				}

				if (rowSize != _MAX_ROW_SIZE) {
					invalidRowSize = true;
				}
			}
		}

		if (invalidColumnSize) {
			DDMFormLayoutValidatorError validateFormLayoutResponseError =
				DDMFormLayoutValidatorError.Builder.of(
					"Column size must be positive and less than maximum row " +
						"size of 12",
					DDMFormLayoutValidatorErrorStatus.
						INVALID_COLUMN_SIZE_EXCEPTION);

			validateFormLayoutResponseErrors.add(
				validateFormLayoutResponseError);
		}

		if (invalidRowSize) {
			DDMFormLayoutValidatorError validateFormLayoutResponseError =
				DDMFormLayoutValidatorError.Builder.of(
					"The sum of all column sizes of a row must be less than " +
						"the maximum row size of 12",
					DDMFormLayoutValidatorErrorStatus.
						INVALID_ROW_SIZE_EXCEPTION);

			validateFormLayoutResponseErrors.add(
				validateFormLayoutResponseError);
		}

		return validateFormLayoutResponseErrors;
	}

	private static final int _MAX_ROW_SIZE = 12;

}