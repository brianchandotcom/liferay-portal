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

package com.liferay.dynamic.data.lists.form.web.internal.converter;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDLFormRulesToDDMFormRulesConverterTest
	extends BaseDDLDDMConverterTest {

	@Before
	public void setUp() {
		_ddlFormRulesToDDMFormRulesConverter =
			new DDLFormRulesToDDMFormRulesConverter();
	}

	@Test
	public void testAndOrCondition() throws Exception {
		assertConversion(
			"ddl-form-rules-and-or-condition.json",
			"ddm-form-rules-and-or-condition.json");
	}

	@Test
	public void testBooleanActions() throws Exception {
		assertConversion(
			"ddl-form-rules-boolean-actions.json",
			"ddm-form-rules-boolean-actions.json");
	}

	@Test
	public void testComparisonOperatorsCondition() throws Exception {
		assertConversion(
			"ddl-form-rules-comparison-operators-condition.json",
			"ddm-form-rules-comparison-operators-condition.json");
	}

	@Test
	public void testJumpToPageActions() throws Exception {
		assertConversion(
			"ddl-form-rules-jump-to-page-actions.json",
			"ddm-form-rules-jump-to-page-actions.json");
	}

	protected void assertConversion(String fromFileName, String toFileName)
		throws Exception {

		String serializedDDLFormRules = read(fromFileName);

		DDLFormRule[] ddlFormRules = deserialize(
			serializedDDLFormRules, DDLFormRule[].class);

		List<DDMFormRule> actualDDMFormRules =
			_ddlFormRulesToDDMFormRulesConverter.convert(
				ListUtil.toList(ddlFormRules));

		JSONAssert.assertEquals(
			read(toFileName), serialize(actualDDMFormRules), false);
	}

	private DDLFormRulesToDDMFormRulesConverter
		_ddlFormRulesToDDMFormRulesConverter;

}