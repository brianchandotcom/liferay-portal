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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.InvalidColumnSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.InvalidRowSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustSetDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustSetEqualLocaleForLayoutAndTitle;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.internal.util.DDMFormRuleValidatorUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(immediate = true, service = DDMFormLayoutValidator.class)
public class DDMFormLayoutValidatorImpl implements DDMFormLayoutValidator {

	@Override
	public void validate(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException, DDMFormValidationException {

		DDMFormRuleValidatorUtil.validateDDMFormRules(
			_ddmExpressionFactory, ddmFormLayout.getDDMFormRules());

		_validateDDMFormLayoutDefaultLocale(ddmFormLayout);

		_validateDDMFormFieldNames(ddmFormLayout);
		_validateDDMFormLayoutPageTitles(ddmFormLayout);
		_validateDDMFormLayoutRowSizes(ddmFormLayout);
	}

	@Reference(unbind = "-")
	protected void setDDMExpressionFactory(
		DDMExpressionFactory ddmExpressionFactory) {

		_ddmExpressionFactory = ddmExpressionFactory;
	}

	private Stream<String> _getDDMFormFieldNamesStream(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		List<String> ddmFormFieldNames =
			ddmFormLayoutColumn.getDDMFormFieldNames();

		return ddmFormFieldNames.stream();
	}

	private Stream<DDMFormLayoutColumn> _getDDMFormLayoutColumnStream(
		DDMFormLayoutRow ddmFormLayoutRow) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			ddmFormLayoutRow.getDDMFormLayoutColumns();

		return ddmFormLayoutColumns.stream();
	}

	private Stream<DDMFormLayoutRow> _getDDMFormLayoutRowStream(
		DDMFormLayoutPage ddmFormLayoutPage) {

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayoutPage.getDDMFormLayoutRows();

		return ddmFormLayoutRows.stream();
	}

	private void _validateDDMFormFieldNames(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayout.getDDMFormLayoutPages();

		Stream<DDMFormLayoutPage> stream = ddmFormLayoutPages.stream();

		Map<String, Long> ddmFormFieldNamesCount = stream.flatMap(
			this::_getDDMFormLayoutRowStream
		).flatMap(
			this::_getDDMFormLayoutColumnStream
		).flatMap(
			this::_getDDMFormFieldNamesStream
		).collect(
			Collectors.groupingBy(String::valueOf, Collectors.counting())
		);

		Set<Map.Entry<String, Long>> entrySet =
			ddmFormFieldNamesCount.entrySet();

		Stream<Map.Entry<String, Long>> entrySetStream = entrySet.stream();

		Set<String> duplicatedFieldNames = entrySetStream.filter(
			entry -> entry.getValue() > 1
		).map(
			Map.Entry::getKey
		).collect(
			Collectors.toSet()
		);

		if (SetUtil.isNotEmpty(duplicatedFieldNames)) {
			throw new MustNotDuplicateFieldName(duplicatedFieldNames);
		}
	}

	private void _validateDDMFormLayoutDefaultLocale(
			DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		if (defaultLocale == null) {
			throw new MustSetDefaultLocale();
		}
	}

	private void _validateDDMFormLayoutPageTitles(DDMFormLayout ddmFormLayout)
		throws DDMFormLayoutValidationException {

		Locale defaultLocale = ddmFormLayout.getDefaultLocale();

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			if (!defaultLocale.equals(title.getDefaultLocale())) {
				throw new MustSetEqualLocaleForLayoutAndTitle();
			}
		}
	}

	private void _validateDDMFormLayoutRowSizes(DDMFormLayout ddmFormLayout)
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
						throw new InvalidColumnSize();
					}

					rowSize += ddmFormLayoutColumn.getSize();
				}

				if (rowSize != _MAX_ROW_SIZE) {
					throw new InvalidRowSize();
				}
			}
		}
	}

	private static final int _MAX_ROW_SIZE = 12;

	private DDMExpressionFactory _ddmExpressionFactory;

}