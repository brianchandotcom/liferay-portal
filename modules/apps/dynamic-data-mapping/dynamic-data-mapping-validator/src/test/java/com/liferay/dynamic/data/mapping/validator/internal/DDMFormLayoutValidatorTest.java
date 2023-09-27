/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.validator.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.InvalidRowSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustSetDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustSetEqualLocaleForLayoutAndTitle;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class DDMFormLayoutValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDuplicateFieldNames() throws Exception {
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

		try {
			_ddmFormLayoutValidator.validate(ddmFormLayout);

			Assert.fail();
		}
		catch (MustNotDuplicateFieldName mustNotDuplicateFieldName) {
			Set<String> duplicatedFieldNames =
				mustNotDuplicateFieldName.getDuplicatedFieldNames();

			Assert.assertTrue(duplicatedFieldNames.contains("field1"));
			Assert.assertFalse(duplicatedFieldNames.contains("field2"));
			Assert.assertTrue(duplicatedFieldNames.contains("field3"));
		}
	}

	@Test(expected = InvalidRowSize.class)
	public void testInvalidRowSize() throws Exception {
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

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = MustSetDefaultLocale.class)
	public void testNullDefaultLocale() throws Exception {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(null);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
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

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = MustSetEqualLocaleForLayoutAndTitle.class)
	public void testWrongDefaultLocaleSetForPageTitle() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn = _createDDMFormLayoutColumn(
			12, "field");

		DDMFormLayoutRow ddmFormLayoutRow = _createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue title = _createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = _createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = _createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.BRAZIL);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
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

		return new DDMFormLayoutColumn(size, fieldNames);
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

	private final DDMFormLayoutValidator _ddmFormLayoutValidator =
		new DDMFormLayoutValidatorImpl();

}