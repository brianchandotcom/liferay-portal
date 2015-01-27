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

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

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

		DDMFormLayoutRow ddmFormLayoutRow =
			actualDDMFormLayout.getDDMFormLayoutRow(0);

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			ddmFormLayoutRow.getDDMFormLayoutColumns();

		Assert.assertEquals(2, ddmFormLayoutColumns.size());

		Assert.assertEquals(
			"Text1",
			getDDMFormLayoutColumnFieldName(ddmFormLayoutColumns.get(0)));
		Assert.assertEquals(
			"Text2",
			getDDMFormLayoutColumnFieldName(ddmFormLayoutColumns.get(1)));
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

	protected String getDDMFormFieldName(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return ddmFormLayoutColumn.getDDMFormFieldName();
	}

	protected String getDDMFormLayoutColumnFieldName(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return ddmFormLayoutColumn.getDDMFormFieldName();
	}

	protected void setUpDDMForm() {
		_ddmForm = ddmStructureTestHelper.createDDMForm("Text1", "Text2");
	}

	protected void setUpDDMFormLayout() {
		_ddmFormLayout = new DDMFormLayout();

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			createDDMFormLayoutColumns("Text1", "Text2");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumns);

		_ddmFormLayout.addDDMFormLayoutRow(ddmFormLayoutRow);
	}

	private DDMForm _ddmForm;
	private DDMFormLayout _ddmFormLayout;

}