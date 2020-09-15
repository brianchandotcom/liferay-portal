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

package com.liferay.poshi.runner.pql;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLValueTest extends TestCase {

	@Test
	public void testGetPQLResult() throws Exception {
		_validateGetPQLResult("false", Boolean.FALSE);
		_validateGetPQLResult("'false'", Boolean.FALSE);
		_validateGetPQLResult("\"false\"", Boolean.FALSE);
		_validateGetPQLResult("true", Boolean.TRUE);
		_validateGetPQLResult("'true'", Boolean.TRUE);
		_validateGetPQLResult("\"true\"", Boolean.TRUE);

		_validateGetPQLResult("3.2", 3.2D);
		_validateGetPQLResult("'3.2'", 3.2D);
		_validateGetPQLResult("\"3.2\"", 3.2D);
		_validateGetPQLResult("2016.0", 2016D);
		_validateGetPQLResult("'2016.0'", 2016D);
		_validateGetPQLResult("\"2016.0\"", 2016D);

		_validateGetPQLResult("2016", 2016);
		_validateGetPQLResult("'2016'", 2016);
		_validateGetPQLResult("\"2016\"", 2016);

		_validateGetPQLResult("test", "test");
		_validateGetPQLResult("'test'", "test");
		_validateGetPQLResult("\"test\"", "test");

		_validateGetPQLResult("'test test'", "test test");
		_validateGetPQLResult("\"test test\"", "test test");
	}

	@Test
	public void testGetPQLResultError() throws Exception {
		Set<String> pqls = new HashSet<>();

		pqls.add("test == test");
		pqls.add("test test");
		pqls.add("true AND true");

		for (String pql : pqls) {
			_validateGetPQLResultError(pql, "Invalid value: " + pql);
		}
	}

	@Test
	public void testGetPQLResultModifier() throws Exception {
		_validateGetPQLResult("NOT true", Boolean.FALSE);
		_validateGetPQLResult("NOT false", Boolean.TRUE);
	}

	@Test
	public void testGetPQLResultModifierError() throws Exception {
		_validateGetPQLResultError(
			"NOT 3.2", "Modifier must be used with a boolean value: NOT");
		_validateGetPQLResultError(
			"NOT 2016", "Modifier must be used with a boolean value: NOT");
		_validateGetPQLResultError(
			"NOT test", "Modifier must be used with a boolean value: NOT");
		_validateGetPQLResultError(
			"NOT 'test test'",
			"Modifier must be used with a boolean value: NOT");
	}

	@Test
	public void testGetPQLResultNull() throws Exception {
		_validateGetPQLResultNull(null);
		_validateGetPQLResultNull("'null'");
		_validateGetPQLResultNull("\"null\"");
	}

	private void _validateGetPQLResult(String pql, Object expectedResult)
		throws Exception {

		Properties properties = new Properties();

		Class<?> clazz = expectedResult.getClass();

		PQLValue pqlValue = new PQLValue(pql);

		Object actualResult = pqlValue.getPQLResult(properties);

		if (!clazz.isInstance(actualResult)) {
			throw new Exception(pql + " should be of type: " + clazz.getName());
		}

		if (!actualResult.equals(expectedResult)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched PQL result within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualResult);
			sb.append("\n* Expected: ");
			sb.append(expectedResult);

			throw new Exception(sb.toString());
		}
	}

	private void _validateGetPQLResultError(String pql, String expectedError)
		throws Exception {

		String actualError = null;

		try {
			PQLValue pqlValue = new PQLValue(pql);

			pqlValue.getPQLResult(new Properties());
		}
		catch (Exception exception) {
			actualError = exception.getMessage();

			if (!actualError.equals(expectedError)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error for the following PQL:\n");
				sb.append(pql);
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

	private void _validateGetPQLResultNull(String pql) throws Exception {
		Properties properties = new Properties();

		PQLValue pqlValue = new PQLValue(pql);

		Object actualResult = pqlValue.getPQLResult(properties);

		if (actualResult != null) {
			Object expectedResult = null;

			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched PQL result within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualResult);
			sb.append("\n* Expected: ");
			sb.append(expectedResult);

			throw new Exception(sb.toString());
		}
	}

}