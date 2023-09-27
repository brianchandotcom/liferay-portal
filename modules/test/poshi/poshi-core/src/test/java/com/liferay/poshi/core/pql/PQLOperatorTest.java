/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLOperatorTest extends TestCase {

	@Test
	public void testGetOperator() throws Exception {
		Set<String> availableOperators = PQLOperator.getAvailableOperators();

		for (String operator : availableOperators) {
			PQLOperator pqlOperator = PQLOperatorFactory.newPQLOperator(
				operator);

			_compareString(pqlOperator.getOperator(), operator);
		}
	}

	@Test
	public void testGetPQLResultConditionalOperatorAND() throws Exception {
		_validateGetPQLResult("true", "AND", "true", Boolean.TRUE);
		_validateGetPQLResult("true", "AND", "false", Boolean.FALSE);
		_validateGetPQLResult("false", "AND", "false", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultConditionalOperatorErrors() throws Exception {
		Set<String> conditionalOperators = new HashSet<>();

		conditionalOperators.add("AND");
		conditionalOperators.add("OR");

		for (String operator : conditionalOperators) {
			String expectedError =
				"Operators must be surrounded by 2 boolean values: " + operator;

			_validateGetPQLResultError(null, operator, null, expectedError);
			_validateGetPQLResultError("test", operator, "test", expectedError);
			_validateGetPQLResultError("123", operator, "123", expectedError);
			_validateGetPQLResultError("12.3", operator, "12.3", expectedError);

			_validateGetPQLResultError("true", operator, null, expectedError);
			_validateGetPQLResultError("true", operator, "test", expectedError);
			_validateGetPQLResultError("true", operator, "123", expectedError);
			_validateGetPQLResultError("true", operator, "12.3", expectedError);

			_validateGetPQLResultError(null, operator, "false", expectedError);
			_validateGetPQLResultError(
				"test", operator, "false", expectedError);
			_validateGetPQLResultError("123", operator, "false", expectedError);
			_validateGetPQLResultError(
				"12.3", operator, "false", expectedError);
		}
	}

	@Test
	public void testGetPQLResultConditionalOperatorOR() throws Exception {
		_validateGetPQLResult("true", "OR", "true", Boolean.TRUE);
		_validateGetPQLResult("true", "OR", "false", Boolean.TRUE);
		_validateGetPQLResult("false", "OR", "false", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultEqualityOperatorEquals() throws Exception {
		_validateGetPQLResult("test", "==", "test", Boolean.TRUE);
		_validateGetPQLResult(null, "==", "test", Boolean.FALSE);
		_validateGetPQLResult("test", "==", null, Boolean.FALSE);
		_validateGetPQLResult(null, "==", null, Boolean.TRUE);
		_validateGetPQLResult("test1", "==", "test2", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultEqualityOperatorNotEquals() throws Exception {
		_validateGetPQLResult("test", "!=", "test", Boolean.FALSE);
		_validateGetPQLResult(null, "!=", "test", Boolean.TRUE);
		_validateGetPQLResult("test", "!=", null, Boolean.TRUE);
		_validateGetPQLResult(null, "!=", null, Boolean.FALSE);
		_validateGetPQLResult("test1", "!=", "test2", Boolean.TRUE);
	}

	@Test
	public void testGetPQLResultRelationalOperatorErrors() throws Exception {
		Set<String> conditionalOperators = new HashSet<>();

		conditionalOperators.add("<");
		conditionalOperators.add("<=");
		conditionalOperators.add(">");
		conditionalOperators.add(">=");

		for (String operator : conditionalOperators) {
			String expectedError =
				"Operator only works for number values: " + operator;

			_validateGetPQLResultError("123", operator, null, expectedError);
			_validateGetPQLResultError("12.3", operator, null, expectedError);
			_validateGetPQLResultError(null, operator, null, expectedError);
			_validateGetPQLResultError(null, operator, "123", expectedError);
			_validateGetPQLResultError(null, operator, "12.3", expectedError);

			_validateGetPQLResultError("123", operator, "true", expectedError);
			_validateGetPQLResultError("12.3", operator, "true", expectedError);
			_validateGetPQLResultError(
				"false", operator, "true", expectedError);
			_validateGetPQLResultError("false", operator, "123", expectedError);
			_validateGetPQLResultError(
				"false", operator, "12.3", expectedError);

			_validateGetPQLResultError("123", operator, "test", expectedError);
			_validateGetPQLResultError("12.3", operator, "test", expectedError);
			_validateGetPQLResultError("test", operator, "test", expectedError);
			_validateGetPQLResultError("test", operator, "123", expectedError);
			_validateGetPQLResultError("test", operator, "12.3", expectedError);
		}
	}

	@Test
	public void testGetPQLResultRelationalOperatorGreaterThan()
		throws Exception {

		_validateGetPQLResult("2", ">", "1", Boolean.TRUE);
		_validateGetPQLResult("2.1", ">", "1", Boolean.TRUE);
		_validateGetPQLResult("2", ">", "1.1", Boolean.TRUE);
		_validateGetPQLResult("2.1", ">", "1.1", Boolean.TRUE);

		_validateGetPQLResult("2", ">", "2", Boolean.FALSE);
		_validateGetPQLResult("2.1", ">", "2.1", Boolean.FALSE);

		_validateGetPQLResult("1", ">", "2", Boolean.FALSE);
		_validateGetPQLResult("1.1", ">", "2", Boolean.FALSE);
		_validateGetPQLResult("1", ">", "2.1", Boolean.FALSE);
		_validateGetPQLResult("1.1", ">", "2.1", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultRelationalOperatorGreaterThanEquals()
		throws Exception {

		_validateGetPQLResult("2", ">=", "1", Boolean.TRUE);
		_validateGetPQLResult("2.1", ">=", "1", Boolean.TRUE);
		_validateGetPQLResult("2", ">=", "1.1", Boolean.TRUE);
		_validateGetPQLResult("2.1", ">=", "1.1", Boolean.TRUE);

		_validateGetPQLResult("2", ">=", "2", Boolean.TRUE);
		_validateGetPQLResult("2.1", ">=", "2.1", Boolean.TRUE);

		_validateGetPQLResult("1", ">=", "2", Boolean.FALSE);
		_validateGetPQLResult("1.1", ">=", "2", Boolean.FALSE);
		_validateGetPQLResult("1", ">=", "2.1", Boolean.FALSE);
		_validateGetPQLResult("1.1", ">=", "2.1", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultRelationalOperatorLessThan() throws Exception {
		_validateGetPQLResult("2", "<", "1", Boolean.FALSE);
		_validateGetPQLResult("2.1", "<", "1", Boolean.FALSE);
		_validateGetPQLResult("2", "<", "1.1", Boolean.FALSE);
		_validateGetPQLResult("2.1", "<", "1.1", Boolean.FALSE);

		_validateGetPQLResult("2", "<", "2", Boolean.FALSE);
		_validateGetPQLResult("2.1", "<", "2.1", Boolean.FALSE);

		_validateGetPQLResult("1", "<", "2", Boolean.TRUE);
		_validateGetPQLResult("1.1", "<", "2", Boolean.TRUE);
		_validateGetPQLResult("1", "<", "2.1", Boolean.TRUE);
		_validateGetPQLResult("1.1", "<", "2.1", Boolean.TRUE);
	}

	@Test
	public void testGetPQLResultRelationalOperatorLessThanEquals()
		throws Exception {

		_validateGetPQLResult("2", "<=", "1", Boolean.FALSE);
		_validateGetPQLResult("2.1", "<=", "1", Boolean.FALSE);
		_validateGetPQLResult("2", "<=", "1.1", Boolean.FALSE);
		_validateGetPQLResult("2.1", "<=", "1.1", Boolean.FALSE);

		_validateGetPQLResult("2", "<=", "2", Boolean.TRUE);
		_validateGetPQLResult("2.1", "<=", "2.1", Boolean.TRUE);

		_validateGetPQLResult("1", "<=", "2", Boolean.TRUE);
		_validateGetPQLResult("1.1", "<=", "2", Boolean.TRUE);
		_validateGetPQLResult("1", "<=", "2.1", Boolean.TRUE);
		_validateGetPQLResult("1.1", "<=", "1.1", Boolean.TRUE);
	}

	@Test
	public void testGetPQLResultStringOperatorContains() throws Exception {
		_validateGetPQLResult("test", "~", "test", Boolean.TRUE);
		_validateGetPQLResult("test1", "~", "test", Boolean.TRUE);
		_validateGetPQLResult(null, "~", "test", Boolean.FALSE);
		_validateGetPQLResult("test", "~", null, Boolean.FALSE);
		_validateGetPQLResult(null, "~", null, Boolean.FALSE);
		_validateGetPQLResult("test1", "~", "test2", Boolean.FALSE);

		_validateGetPQLResult("fal", "~", "false", Boolean.FALSE);
		_validateGetPQLResult("false", "~", "fal", Boolean.TRUE);
		_validateGetPQLResult("false", "~", "false", Boolean.TRUE);
		_validateGetPQLResult("false", "~", "true", Boolean.FALSE);

		_validateGetPQLResult("tru", "~", "true", Boolean.FALSE);
		_validateGetPQLResult("true", "~", "false", Boolean.FALSE);
		_validateGetPQLResult("true", "~", "tru", Boolean.TRUE);
		_validateGetPQLResult("true", "~", "true", Boolean.TRUE);

		_validateGetPQLResult("12", "~", "12.3", Boolean.FALSE);
		_validateGetPQLResult("12.3", "~", "12", Boolean.TRUE);
		_validateGetPQLResult("12.3", "~", "12.3", Boolean.TRUE);
		_validateGetPQLResult("12.3", "~", "test", Boolean.FALSE);
		_validateGetPQLResult("test", "~", "12.3", Boolean.FALSE);

		_validateGetPQLResult("12", "~", "123", Boolean.FALSE);
		_validateGetPQLResult("123", "~", "12", Boolean.TRUE);
		_validateGetPQLResult("123", "~", "123", Boolean.TRUE);
		_validateGetPQLResult("123", "~", "test", Boolean.FALSE);
		_validateGetPQLResult("test", "~", "123", Boolean.FALSE);
	}

	@Test
	public void testGetPQLResultStringOperatorNotContains() throws Exception {
		_validateGetPQLResult("test", "!~", "test", Boolean.FALSE);
		_validateGetPQLResult("test1", "!~", "test", Boolean.FALSE);
		_validateGetPQLResult(null, "!~", "test", Boolean.FALSE);
		_validateGetPQLResult("test", "!~", null, Boolean.FALSE);
		_validateGetPQLResult(null, "!~", null, Boolean.FALSE);
		_validateGetPQLResult("test1", "!~", "test2", Boolean.TRUE);

		_validateGetPQLResult("fal", "!~", "false", Boolean.TRUE);
		_validateGetPQLResult("false", "!~", "fal", Boolean.FALSE);
		_validateGetPQLResult("false", "!~", "false", Boolean.FALSE);
		_validateGetPQLResult("false", "!~", "true", Boolean.TRUE);

		_validateGetPQLResult("tru", "!~", "true", Boolean.TRUE);
		_validateGetPQLResult("true", "!~", "false", Boolean.TRUE);
		_validateGetPQLResult("true", "!~", "tru", Boolean.FALSE);
		_validateGetPQLResult("true", "!~", "true", Boolean.FALSE);

		_validateGetPQLResult("12", "!~", "12.3", Boolean.TRUE);
		_validateGetPQLResult("12.3", "!~", "12", Boolean.FALSE);
		_validateGetPQLResult("12.3", "!~", "12.3", Boolean.FALSE);
		_validateGetPQLResult("12.3", "!~", "test", Boolean.TRUE);
		_validateGetPQLResult("test", "!~", "12.3", Boolean.TRUE);

		_validateGetPQLResult("12", "!~", "123", Boolean.TRUE);
		_validateGetPQLResult("123", "!~", "12", Boolean.FALSE);
		_validateGetPQLResult("123", "!~", "123", Boolean.FALSE);
		_validateGetPQLResult("123", "!~", "test", Boolean.TRUE);
		_validateGetPQLResult("test", "!~", "123", Boolean.TRUE);
	}

	@Test
	public void testOperatorValidate() throws Exception {
		Set<String> availableOperators = PQLOperator.getAvailableOperators();

		for (String operator : availableOperators) {
			PQLOperator.validateOperator(operator);
		}
	}

	@Test
	public void testOperatorValidateError() throws Exception {
		Set<String> operators = new HashSet<>();

		operators.add(null);
		operators.add("bad");
		operators.add("bad value");

		for (String operator : operators) {
			_validatePQLOperatorError(
				operator, "Invalid operator: " + operator);
		}
	}

	private void _compareString(String actualString, String expectedString)
		throws Exception {

		if (!actualString.equals(expectedString)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched string values:");
			sb.append("\n* Actual:   ");
			sb.append(actualString);
			sb.append("\n* Expected: ");
			sb.append(expectedString);

			throw new Exception(sb.toString());
		}
	}

	private void _validateGetPQLResult(
			String value1, String operator, String value2,
			Object expectedPQLResult)
		throws Exception {

		String pql = value1 + " " + operator + " " + value2;

		PQLEntity pqlEntity1 = PQLEntityFactory.newPQLEntity(value1);
		PQLOperator pqlOperator = PQLOperatorFactory.newPQLOperator(operator);
		PQLEntity pqlEntity2 = PQLEntityFactory.newPQLEntity(value2);

		Object actualPQLResult = pqlOperator.getPQLResult(
			pqlEntity1, pqlEntity2, new Properties());

		if (!actualPQLResult.equals(expectedPQLResult)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched PQL result within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualPQLResult);
			sb.append("\n* Expected: ");
			sb.append(expectedPQLResult);

			throw new Exception(sb.toString());
		}
	}

	private void _validateGetPQLResultError(
			String value1, String operator, String value2, String expectedError)
		throws Exception {

		String pql = value1 + " " + operator + " " + value2;

		PQLEntity pqlEntity1 = PQLEntityFactory.newPQLEntity(value1);
		PQLOperator pqlOperator = PQLOperatorFactory.newPQLOperator(operator);
		PQLEntity pqlEntity2 = PQLEntityFactory.newPQLEntity(value2);

		String actualError = null;

		try {
			pqlOperator.getPQLResult(pqlEntity1, pqlEntity2, new Properties());
		}
		catch (Exception exception) {
			actualError = exception.getMessage();

			if (!actualError.equals(expectedError)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error within the following PQL:\n");
				sb.append(pql);
				sb.append(value2);
				sb.append("\n* Actual:   ");
				sb.append(actualError);
				sb.append("\n* Expected: ");
				sb.append(expectedError);

				throw new Exception(sb.toString(), exception);
			}
		}
		finally {
			if (actualError == null) {
				throw new Exception(
					"No error thrown for the following PQL: " + pql);
			}
		}
	}

	private void _validatePQLOperatorError(
			String operator, String expectedError)
		throws Exception {

		String actualError = null;

		try {
			PQLOperator.validateOperator(operator);
		}
		catch (Exception exception) {
			actualError = exception.getMessage();

			if (!actualError.equals(expectedError)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error for PQL operator validation:");
				sb.append("\n* Actual:   ");
				sb.append(actualError);
				sb.append("\n* Expected: ");
				sb.append(expectedError);

				throw new Exception(sb.toString(), exception);
			}
		}
		finally {
			if (actualError == null) {
				throw new Exception(
					"No error thrown for PQL operator validation: " + operator);
			}
		}
	}

}