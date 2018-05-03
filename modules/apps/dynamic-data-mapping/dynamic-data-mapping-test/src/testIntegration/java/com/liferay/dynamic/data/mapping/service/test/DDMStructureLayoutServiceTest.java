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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMStructureLayoutServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMForm();
		setUpDDMFormLayout();
	}

	@Test
	public void testAddStructureLayout() throws Exception {
		DDMStructure structure = ddmStructureTestHelper.addStructure(
			_ddmForm, _ddmFormLayout);

		DDMFormLayout actualDDMFormLayout = structure.getDDMFormLayout();

		DDMFormLayoutPage actualDDMFormLayoutPage =
			actualDDMFormLayout.getDDMFormLayoutPage(0);

		DDMFormLayoutRow ddmFormLayoutRow =
			actualDDMFormLayoutPage.getDDMFormLayoutRow(0);

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			ddmFormLayoutRow.getDDMFormLayoutColumns();

		Assert.assertEquals(
			ddmFormLayoutColumns.toString(), 2, ddmFormLayoutColumns.size());

		Assert.assertEquals(
			"Text1",
			getDDMFormLayoutColumnFieldName(ddmFormLayoutColumns.get(0), 0));
		Assert.assertEquals(
			"Text2",
			getDDMFormLayoutColumnFieldName(ddmFormLayoutColumns.get(1), 0));
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... ddmFormFieldNames) {

		return ddmStructureLayoutTestHelper.createDDMFormLayoutColumns(
			"Text1", "Text2");
	}

	protected DDMFormLayoutPage createDDMFormLayoutPage(
		DDMFormLayoutRow ddmFormLayoutRow) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue ddmFormLayoutPageTitle = new LocalizedValue(
			LocaleUtil.US);

		ddmFormLayoutPageTitle.addString(LocaleUtil.US, "Page1");

		ddmFormLayoutPage.setTitle(ddmFormLayoutPageTitle);

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		return ddmFormLayoutPage;
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		return ddmStructureLayoutTestHelper.createDDMFormLayoutRow(
			ddmFormLayoutColumns);
	}

	protected String getDDMFormLayoutColumnFieldName(
		DDMFormLayoutColumn ddmFormLayoutColumn, int index) {

		List<String> ddmFormFieldNames =
			ddmFormLayoutColumn.getDDMFormFieldNames();

		return ddmFormFieldNames.get(index);
	}

	protected void setUpDDMForm() {
		_ddmForm = DDMFormTestUtil.createDDMForm("Text1", "Text2");
	}

	protected void setUpDDMFormLayout() {
		_ddmFormLayout = new DDMFormLayout();

		_ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			createDDMFormLayoutColumns("Text1", "Text2");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumns);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow);

		_ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);
	}

	private DDMForm _ddmForm;
	private DDMFormLayout _ddmFormLayout;

}