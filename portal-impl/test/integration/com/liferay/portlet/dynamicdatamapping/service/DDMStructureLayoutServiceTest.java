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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.test.BaseDDMServiceTestCase;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMStructureLayoutServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

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

		Assert.assertEquals(2, ddmFormLayoutColumns.size());

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

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			createDDMFormLayoutColumns("Text1", "Text2");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumns);

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		_ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);
	}

	private DDMForm _ddmForm;
	private DDMFormLayout _ddmFormLayout;

}