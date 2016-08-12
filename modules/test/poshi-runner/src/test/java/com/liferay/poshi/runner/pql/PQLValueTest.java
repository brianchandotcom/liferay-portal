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
	public void testGetValue() throws Exception {
		_validateValueResult("false", Boolean.valueOf(false));
		_validateValueResult("'false'", Boolean.valueOf(false));
		_validateValueResult("\"false\"", Boolean.valueOf(false));
		_validateValueResult("true", Boolean.valueOf(true));
		_validateValueResult("'true'", Boolean.valueOf(true));
		_validateValueResult("\"true\"", Boolean.valueOf(true));

		_validateValueResult("3.2", Double.valueOf(3.2));
		_validateValueResult("'3.2'", Double.valueOf(3.2));
		_validateValueResult("\"3.2\"", Double.valueOf(3.2));
		_validateValueResult("2016.0", Double.valueOf(2016));
		_validateValueResult("'2016.0'", Double.valueOf(2016));
		_validateValueResult("\"2016.0\"", Double.valueOf(2016));

		_validateValueResult("2016", Integer.valueOf(2016));
		_validateValueResult("'2016'", Integer.valueOf(2016));
		_validateValueResult("\"2016\"", Integer.valueOf(2016));

		_validateValueResult("test", "test");
		_validateValueResult("'test'", "test");
		_validateValueResult("\"test\"", "test");

		_validateValueResult("'test test'", "test test");
		_validateValueResult("\"test test\"", "test test");
	}

	@Test
	public void testGetValueNull() throws Exception {
		_validateValueResultNull(null);
		_validateValueResultNull("'null'");
		_validateValueResultNull("\"null\"");
	}

	@Test
	public void testValueError() throws Exception {
		Set<String> pqls = new HashSet<>();

		pqls.add("test test");
		pqls.add("true AND true");
		pqls.add("test == test");

		for (String pql : pqls) {
			_validateValueError(pql, "Invalid value: " + pql);
		}
	}

	private void _validateValueError(String pql, String expectedError)
		throws Exception {

		String actualError = null;

		try {
			PQLValue pqlValue = new PQLValue(pql);

			Object valueObject = pqlValue.getValue(new Properties());
		}
		catch (Exception e) {
			actualError = e.getMessage();

			if (!actualError.equals(expectedError)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error for the following PQL:\n");
				sb.append(pql);
				sb.append("\n* Actual:   ");
				sb.append(actualError);
				sb.append("\n* Expected: ");
				sb.append(expectedError);

				throw new Exception(sb.toString(), e);
			}
		}
		finally {
			if (actualError == null) {
				throw new Exception(
					"No error thrown for the following PQL:\n" + pql);
			}
		}
	}

	private void _validateValueResult(String pql, Object expectedObject)
		throws Exception {

		Properties properties = new Properties();

		Class clazz = expectedObject.getClass();

		PQLValue pqlValue = new PQLValue(pql);

		Object actualObject = pqlValue.getValue(properties);

		if (!clazz.isInstance(actualObject)) {
			throw new Exception(pql + " should be of type: " + clazz.getName());
		}

		if (!actualObject.equals(expectedObject)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched value within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualObject);
			sb.append("\n* Expected: ");
			sb.append(expectedObject);

			throw new Exception(sb.toString());
		}
	}

	private void _validateValueResultNull(String pql) throws Exception {
		Properties properties = new Properties();

		PQLValue pqlValue = new PQLValue(pql);

		Object actualObject = pqlValue.getValue(properties);
		Object expectedObject = null;

		if (actualObject != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched value within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualObject);
			sb.append("\n* Expected: ");
			sb.append(expectedObject);

			throw new Exception(sb.toString());
		}
	}

}