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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal;

import static java.util.Arrays.asList;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorTest {

	@Test
	public void testDisabledRuleShouldNotRunConditionEvaluation()
		throws Exception {

		DDMFormRule ddmFormRule = new DDMFormRule("eval()", asList("true"));

		ddmFormRule.setEnabled(false);

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory);

		EvalFunction evalFunction = new EvalFunction();

		ddmFormRuleEvaluator.setDDMExpressionFunction("eval", evalFunction);

		ddmFormRuleEvaluator.evaluate();

		Assert.assertFalse(evalFunction.hasExecuted());
	}

	@Test
	public void testEnableRuleShouldRunConditionEvaluation() throws Exception {
		DDMFormRule ddmFormRule = new DDMFormRule("eval()", asList("true"));

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory);

		EvalFunction evalFunction = new EvalFunction();

		ddmFormRuleEvaluator.setDDMExpressionFunction("eval", evalFunction);

		ddmFormRuleEvaluator.evaluate();

		Assert.assertTrue(evalFunction.hasExecuted());
	}

	@Test
	public void testFalseConditionShouldNotRunActionEvaluation()
		throws Exception {

		DDMFormRule ddmFormRule = new DDMFormRule("false", asList("eval()"));

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory);

		EvalFunction evalFunction = new EvalFunction();

		ddmFormRuleEvaluator.setDDMExpressionFunction("eval", evalFunction);

		ddmFormRuleEvaluator.evaluate();

		Assert.assertFalse(evalFunction.hasExecuted());
	}

	@Test
	public void testTrueConditionShouldRunActionEvaluation() throws Exception {
		DDMFormRule ddmFormRule = new DDMFormRule("true", asList("eval()"));

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory);

		EvalFunction evalFunction = new EvalFunction();

		ddmFormRuleEvaluator.setDDMExpressionFunction("eval", evalFunction);

		ddmFormRuleEvaluator.evaluate();

		Assert.assertTrue(evalFunction.hasExecuted());
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();

	private static class EvalFunction implements DDMExpressionFunction {

		public Object evaluate(Object... parameters) {
			_executed = true;

			return true;
		}

		public boolean hasExecuted() {
			return _executed;
		}

		private boolean _executed;

	}

}