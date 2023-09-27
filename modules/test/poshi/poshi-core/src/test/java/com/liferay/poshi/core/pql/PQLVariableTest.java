/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import com.liferay.poshi.core.PoshiContext;

import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLVariableTest extends TestCase {

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
		_validateGetPQLResultError(
			"invalid.property", "Invalid testcase property: invalid.property");
		_validateGetPQLResultError(null, "Invalid variable: null");
		_validateGetPQLResultError(
			"test == test", "Invalid value: test == test");
		_validateGetPQLResultError(
			"test OR test", "Invalid value: test OR test");
	}

	private void _validateGetPQLResult(String pql, Object expectedPQLResult)
		throws Exception {

		Properties properties = new Properties();

		properties.put("portal.smoke", pql);

		PoshiContext.addPoshiPropertyNames(properties.stringPropertyNames());

		Class<?> clazz = expectedPQLResult.getClass();

		PQLVariable pqlVariable = new PQLVariable("portal.smoke");

		Object actualPQLResult = pqlVariable.getPQLResult(properties);

		if (!clazz.isInstance(actualPQLResult)) {
			throw new Exception(pql + " should be of type: " + clazz.getName());
		}

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

	private void _validateGetPQLResultError(String pql, String expectedError)
		throws Exception {

		String actualError = null;

		try {
			PQLVariable pqlVariable = new PQLVariable(pql);

			pqlVariable.getPQLResult(new Properties());
		}
		catch (Exception exception) {
			actualError = exception.getMessage();

			if (!actualError.equals(expectedError)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error within the following PQL:\n");
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

}