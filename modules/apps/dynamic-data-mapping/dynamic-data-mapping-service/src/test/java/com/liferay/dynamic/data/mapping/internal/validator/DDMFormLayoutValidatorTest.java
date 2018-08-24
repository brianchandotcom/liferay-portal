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

package com.liferay.dynamic.data.mapping.internal.validator;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorValidateRequest;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class DDMFormLayoutValidatorTest {

	@Test
	public void testDuplicateFieldNames() {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = _createDDMFormLayoutColumn(
			6, "field1", "field2", "field3");

		DDMFormLayoutColumn ddmFormLayoutColumn2 = _createDDMFormLayoutColumn(
			6, "field1", "field3");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> errorStatusList =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				errorStatusList.contains(
					DDMFormLayoutValidatorError.Status.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testFormThrowsMultipleValidationExceptions() {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = _createDDMFormLayoutColumn(
			-2, "field1", "field2");

		DDMFormLayoutColumn ddmFormLayoutColumn2 = _createDDMFormLayoutColumn(
			15, "field2");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						INVALID_ROW_SIZE_EXCEPTION));

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						INVALID_COLUMN_SIZE_EXCEPTION));

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION));
		}
	}

	@Test
	public void testInvalidColumnSize1() {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = _createDDMFormLayoutColumn(
			_MIN_COLUMN_SIZE - 1, "field1");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						INVALID_COLUMN_SIZE_EXCEPTION));
		}
	}

	@Test
	public void testInvalidColumnSize2() {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = _createDDMFormLayoutColumn(
			_MAX_COLUMN_SIZE + 1, "field1");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						INVALID_COLUMN_SIZE_EXCEPTION));
		}
	}

	@Test
	public void testInvalidRowSize() {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = _createDDMFormLayoutColumn(
			6, "field1");

		DDMFormLayoutColumn ddmFormLayoutColumn2 = _createDDMFormLayoutColumn(
			7, "field2");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						INVALID_ROW_SIZE_EXCEPTION));
		}
	}

	@Test
	public void testNullDefaultLocale() {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(null);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						MUST_SET_DEFAULT_LOCALE_EXCEPTION));
		}
	}

	@Test
	public void testValidDDMFormLayout() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn = _createDDMFormLayoutColumn(
			12, "field");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		_ddmFormLayoutValidator.validate(builder.build());
	}

	@Test
	public void testWrongDefaultLocaleSetForPageTitle() {
		DDMFormLayoutColumn ddmFormLayoutColumn = _createDDMFormLayoutColumn(
			12, "field");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.BRAZIL);

		DDMFormLayoutValidatorValidateRequest.Builder builder =
			DDMFormLayoutValidatorValidateRequest.Builder.newBuilder(
				ddmFormLayout);

		try {
			_ddmFormLayoutValidator.validate(builder.build());
		}
		catch (DDMFormLayoutValidationException ddmflve) {
			List<DDMFormLayoutValidatorError.Status> status =
				getDDMFormLayoutValidatorErrorStatus(ddmflve);

			Assert.assertTrue(
				status.contains(
					DDMFormLayoutValidatorError.Status.
						MUST_SET_EQUAL_LOCALE_FOR_LAYOUT_EXCEPTION));
		}
	}

	protected List<DDMFormLayoutValidatorError.Status>
		getDDMFormLayoutValidatorErrorStatus(
			DDMFormLayoutValidationException ddmflve) {

		List<DDMFormLayoutValidatorError> ddmFormLayoutValidatorErrors =
			ddmflve.getDDMFormLayoutValidatorErrors();

		Stream<DDMFormLayoutValidatorError> ddmFormLayoutValidatorErrorStream =
			ddmFormLayoutValidatorErrors.stream();

		return ddmFormLayoutValidatorErrorStream.map(
			ddmFormValidatorError -> ddmFormValidatorError.getErrorStatus()
		).collect(
			Collectors.toList()
		);
	}

	private DDMFormLayout _createDDMFormLayout(
		DDMFormLayoutPage ddmFormLayoutPage, Locale defaultLocale) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		ddmFormLayout.setDefaultLocale(defaultLocale);

		return ddmFormLayout;
	}

	private DDMFormLayoutColumn _createDDMFormLayoutColumn(
		int size, String... fieldNames) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			size, fieldNames);

		return ddmFormLayoutColumn;
	}

	private DDMFormLayoutPage _createDDMFormLayoutPage(
		DDMFormLayoutRow ddmFormLayoutRow, LocalizedValue title) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayoutPage.setTitle(title);

		return ddmFormLayoutPage;
	}

	private DDMFormLayoutRow _createDDMFormLayoutRow(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	private LocalizedValue _createLocalizedValue(
		String value, Locale defaultLocale) {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(defaultLocale, value);

		return localizedValue;
	}

	private static final int _MAX_COLUMN_SIZE = 12;

	private static final int _MIN_COLUMN_SIZE = 1;

	private final DDMFormLayoutValidator _ddmFormLayoutValidator =
		new DDMFormLayoutValidatorImpl();

}