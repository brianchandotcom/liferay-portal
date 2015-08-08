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
import com.liferay.portal.kernel.util.SetUtil;

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
				"DDM form layout does not have a default locale");
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

					Set<String> intersectDDMFormFieldNames = SetUtil.intersect(
						ddmFormFieldNames,
						ddmFormLayoutColumn.getDDMFormFieldNames());

					if (!intersectDDMFormFieldNames.isEmpty()) {
						throw new DDMFormLayoutValidationException(
							"Field names " + intersectDDMFormFieldNames +
								" were defined more than once");
					}

					ddmFormFieldNames.addAll(
						ddmFormLayoutColumn.getDDMFormFieldNames());
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
					"DDM form layout page title's default locale is not the " +
						"same as the DDM form layout's default locale");
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
							"Invalid column size, it must be positive and " +
								"less than maximum row size of 12");
					}

					rowSize += ddmFormLayoutColumn.getSize();
				}

				if (rowSize != _MAX_ROW_SIZE) {
					throw new DDMFormLayoutValidationException(
						"Invalid row size, the sum of all column sizes of a " +
							"row must be less than maximum row size of 12");
				}
			}
		}
	}

	private static final int _MAX_ROW_SIZE = 12;

}