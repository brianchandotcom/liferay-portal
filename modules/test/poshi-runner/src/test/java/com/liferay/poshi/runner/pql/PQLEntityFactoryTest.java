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

import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLEntityFactoryTest extends TestCase {

	@Test
	public void testPQLValueGetPQLResult() throws Exception {
		_validateGetPQLResult("false", Boolean.valueOf(false));
		_validateGetPQLResult("'false'", Boolean.valueOf(false));
		_validateGetPQLResult("\"false\"", Boolean.valueOf(false));
		_validateGetPQLResult("true", Boolean.valueOf(true));
		_validateGetPQLResult("'true'", Boolean.valueOf(true));
		_validateGetPQLResult("\"true\"", Boolean.valueOf(true));

		_validateGetPQLResult("3.2", Double.valueOf(3.2));
		_validateGetPQLResult("'3.2'", Double.valueOf(3.2));
		_validateGetPQLResult("\"3.2\"", Double.valueOf(3.2));
		_validateGetPQLResult("2016.0", Double.valueOf(2016));
		_validateGetPQLResult("'2016.0'", Double.valueOf(2016));
		_validateGetPQLResult("\"2016.0\"", Double.valueOf(2016));

		_validateGetPQLResult("2016", Integer.valueOf(2016));
		_validateGetPQLResult("'2016'", Integer.valueOf(2016));
		_validateGetPQLResult("\"2016\"", Integer.valueOf(2016));

		_validateGetPQLResult("test", "test");
		_validateGetPQLResult("'test'", "test");
		_validateGetPQLResult("\"test\"", "test");

		_validateGetPQLResult("'test test'", "test test");
		_validateGetPQLResult("\"test test\"", "test test");
	}

	@Test
	public void testPQLVariableGetPQLResult() throws Exception {
		_validateGetPQLResultFromVariable("false", Boolean.valueOf(false));
		_validateGetPQLResultFromVariable("'false'", Boolean.valueOf(false));
		_validateGetPQLResultFromVariable("\"false\"", Boolean.valueOf(false));
		_validateGetPQLResultFromVariable("true", Boolean.valueOf(true));
		_validateGetPQLResultFromVariable("'true'", Boolean.valueOf(true));
		_validateGetPQLResultFromVariable("\"true\"", Boolean.valueOf(true));

		_validateGetPQLResultFromVariable("3.2", Double.valueOf(3.2));
		_validateGetPQLResultFromVariable("'3.2'", Double.valueOf(3.2));
		_validateGetPQLResultFromVariable("\"3.2\"", Double.valueOf(3.2));
		_validateGetPQLResultFromVariable("2016.0", Double.valueOf(2016));
		_validateGetPQLResultFromVariable("'2016.0'", Double.valueOf(2016));
		_validateGetPQLResultFromVariable("\"2016.0\"", Double.valueOf(2016));

		_validateGetPQLResultFromVariable("2016", Integer.valueOf(2016));
		_validateGetPQLResultFromVariable("'2016'", Integer.valueOf(2016));
		_validateGetPQLResultFromVariable("\"2016\"", Integer.valueOf(2016));

		_validateGetPQLResultFromVariable("test", "test");
		_validateGetPQLResultFromVariable("'test'", "test");
		_validateGetPQLResultFromVariable("\"test\"", "test");

		_validateGetPQLResultFromVariable("'test test'", "test test");
		_validateGetPQLResultFromVariable("\"test test\"", "test test");
	}

	private static void _validateGetPQLResult(
			String pql, Object expectedPQLResult)
		throws Exception {

		_validateGetPQLResult(pql, expectedPQLResult, new Properties());
	}

	private static void _validateGetPQLResult(
			String pql, Object expectedPQLResult, Properties properties)
		throws Exception {

		PQLEntity pqlEntity = PQLEntityFactory.newPQLEntity(pql);

		Object actualPQLResult = pqlEntity.getPQLResult(properties);

		if (!actualPQLResult.equals(expectedPQLResult)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched PQLResult within the following PQL:\n");
			sb.append(pql);
			sb.append("\n* Actual:   ");
			sb.append(actualPQLResult);
			sb.append("\n* Expected: ");
			sb.append(expectedPQLResult);

			throw new Exception(sb.toString());
		}
	}

	private static void _validateGetPQLResultFromVariable(
			String pql, Object expectedPQLResult)
		throws Exception {

		Properties properties = new Properties();

		properties.put("portal.smoke", pql);

		_validateGetPQLResult("portal.smoke", expectedPQLResult, properties);
	}

}