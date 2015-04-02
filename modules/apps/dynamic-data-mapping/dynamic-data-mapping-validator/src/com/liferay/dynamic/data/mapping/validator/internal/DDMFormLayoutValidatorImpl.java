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

import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pablo Carvalho
 */
@Component(immediate = true, service = DDMFormLayoutValidator.class)
public class DDMFormLayoutValidatorImpl implements DDMFormLayoutValidator {

	@Override
	public void validate(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		validateDDMFormLayoutDefaultLocale(ddmFormLayout);

		validateDDMFormFieldNames(ddmFormLayout);
		validateDDMFormLayoutPageTitles(ddmFormLayout);
		validateDDMFormLayoutRowSizes(ddmFormLayout);
	}

	protected void validateDDMFormLayoutDefaultLocale(
			DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		if (defaultLocale == null) {
			throw new DDMFormLayoutValidationException(
				"The default locale property was never set for DDM form " +
					"layout");
		}
	}

	private void validateDDMFormFieldNames(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		Set<String> ddmFormFieldNames = new HashSet<>();

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					String ddmFormFieldName =
						ddmFormLayoutColumn.getDDMFormFieldName();

					if (ddmFormFieldNames.contains(ddmFormFieldName)) {
						throw new DDMFormLayoutValidationException(
							"The field name " + ddmFormFieldName +
								" was defined more than once");
					}

					ddmFormFieldNames.add(ddmFormFieldName);
				}
			}
		}
	}

	private void validateDDMFormLayoutPageTitles(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			if (!defaultLocale.equals(title.getDefaultLocale())) {
				throw new DDMFormLayoutValidationException(
					"Invalid default locale set for page title. Page titles " +
						"default locale should be equal to DDM Form Layout " +
							"default locale");
			}
		}
	}

	private void validateDDMFormLayoutRowSizes(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				int rowSize = 0;

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					int columnSize = ddmFormLayoutColumn.getSize();

					if ((columnSize <= 0) || (columnSize > _MAX_ROW_SIZE)) {
						throw new DDMFormLayoutValidationException(
							"Invalid column size. Column size must be " +
								"positive and less than maximum row size (12)");
					}

					rowSize += ddmFormLayoutColumn.getSize();
				}

				if (rowSize != _MAX_ROW_SIZE) {
					throw new DDMFormLayoutValidationException(
						"Invalid row size. The sum of all columns of a row " +
							"must be less than maximum row size (12)");
				}
			}
		}
	}

	private static final int _MAX_ROW_SIZE = 12;

}