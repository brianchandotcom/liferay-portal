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
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRuleToDDLFormRuleConverterTest
	extends BaseDDLDDMConverterTest {

	@Before
	public void setUp() {
		_ddmFormRulesToDDLFormRulesConverter =
			new DDMFormRuleToDDLFormRuleConverter();

		_ddmFormRulesToDDLFormRulesConverter.ddmExpressionFactory =
			new DDMExpressionFactoryImpl();
	}

	@Test
	public void testAndOrCondition() throws Exception {
		assertConversion(
			"ddm-form-rules-and-or-condition.json",
			"ddl-form-rules-and-or-condition.json");
	}

	@Test
	public void testAutoFillActions() throws Exception {
		assertConversion(
			"ddm-form-rules-auto-fill-actions.json",
			"ddl-form-rules-auto-fill-actions.json");
	}

	@Test
	public void testBelongsToCondition() throws Exception {
		assertConversion(
			"ddm-form-rules-belongs-to-condition.json",
			"ddl-form-rules-belongs-to-condition-without-user-operand.json");
	}

	@Test
	public void testBooleanActions() throws Exception {
		assertConversion(
			"ddm-form-rules-boolean-actions.json",
			"ddl-form-rules-boolean-actions.json");
	}

	@Test
	public void testCalculateAction() throws Exception {
		assertConversion(
			"ddm-form-rules-calculate-action.json",
			"ddl-form-rules-calculate-action.json");
	}

	@Test
	public void testComparisonOperatorsCondition() throws Exception {
		assertConversion(
			"ddm-form-rules-comparison-operators-condition.json",
			"ddl-form-rules-comparison-operators-condition.json");
	}

	@Test
	public void testIsEmptyCondition() throws Exception {
		assertConversion(
			"ddm-form-rules-is-empty-condition.json",
			"ddl-form-rules-is-empty-condition.json");
	}

	@Test
	public void testIsNotEmptyCondition() throws Exception {
		assertConversion(
			"ddm-form-rules-is-not-empty-condition.json",
			"ddl-form-rules-is-not-empty-condition.json");
	}

	@Test
	public void testJumpToPageActions() throws Exception {
		assertConversion(
			"ddm-form-rules-jump-to-page-actions.json",
			"ddl-form-rules-jump-to-page-actions.json");
	}

	protected void assertConversion(String fromFileName, String toFileName)
		throws Exception {

		String serializedDDMFormRules = read(fromFileName);

		DDMFormRule[] ddmFormRules = deserialize(
			serializedDDMFormRules, DDMFormRule[].class);

		List<DDLFormRule> actualDDLFormRules =
			_ddmFormRulesToDDLFormRulesConverter.convert(
				ListUtil.toList(ddmFormRules));

		JSONAssert.assertEquals(
			read(toFileName), serialize(actualDDLFormRules), false);
	}

	private DDMFormRuleToDDLFormRuleConverter
		_ddmFormRulesToDDLFormRulesConverter;

}