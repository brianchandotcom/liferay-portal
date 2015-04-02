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

import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.portlet.dynamicdatamapping.StructureLayoutException;
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
@Component
public class DDMFormLayoutValidatorImpl implements DDMFormLayoutValidator {

	public static int MAX_ROW_SIZE = 12;

	@Override
	public void validate(DDMFormLayout ddmFormLayout)
		throws StructureLayoutException {

		validateFieldNames(ddmFormLayout);
		validatePageTitleLocales(ddmFormLayout);
		validateRowsSizes(ddmFormLayout);
	}

	private void validateFieldNames(DDMFormLayout ddmFormLayout)
		throws StructureLayoutException {

		Set<String> fieldNames = new HashSet<>();

		for (DDMFormLayoutPage ddmFormLayoutPage
			: ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow
				: ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn
					: ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					String fieldName =
						ddmFormLayoutColumn.getDDMFormFieldName();

					if (fieldNames.contains(fieldName)) {
						throw new StructureLayoutException(
							"Field " + fieldName + " is duplicated");
					}

					fieldNames.add(fieldName);
				}
			}
		}
	}

	private void validatePageTitleLocales(DDMFormLayout ddmFormLayout)
		throws StructureLayoutException {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		for (DDMFormLayoutPage ddmFormLayoutPage
			: ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue pageTitle = ddmFormLayoutPage.getTitle();

			Locale pageTitleDefaultLocale = pageTitle.getDefaultLocale();

			if (!pageTitleDefaultLocale.equals(defaultLocale)) {
				throw new StructureLayoutException(
					"The default language of a page does not match " +
					"the layout's default language");
			}
		}
	}

	private void validateRowsSizes(DDMFormLayout ddmFormLayout)
		throws StructureLayoutException {

		for (DDMFormLayoutPage ddmFormLayoutPage
			: ddmFormLayout.getDDMFormLayoutPages()) {

			for (DDMFormLayoutRow ddmFormLayoutRow
				: ddmFormLayoutPage.getDDMFormLayoutRows()) {

				int rowSize = 0;

				for (DDMFormLayoutColumn ddmFormLayoutColumn
					: ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					int columnSize = ddmFormLayoutColumn.getSize();

					if ((columnSize <= 0) || (columnSize > MAX_ROW_SIZE)) {
						throw new StructureLayoutException(
							"Invalid column size: " + columnSize);
					}

					rowSize += ddmFormLayoutColumn.getSize();
				}

				if (rowSize != MAX_ROW_SIZE) {
					throw new StructureLayoutException(
						"Invalid row size: " + rowSize);
				}
			}
		}
	}

}