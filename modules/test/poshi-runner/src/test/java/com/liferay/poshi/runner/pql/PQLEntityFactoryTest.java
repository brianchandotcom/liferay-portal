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
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLEntityFactoryTest extends TestCase {

	@Test
	public void testPQLValueGetValue() throws Exception {
		_validateQueryResult("false", Boolean.valueOf(false));
		_validateQueryResult("'false'", Boolean.valueOf(false));
		_validateQueryResult("\"false\"", Boolean.valueOf(false));
		_validateQueryResult("true", Boolean.valueOf(true));
		_validateQueryResult("'true'", Boolean.valueOf(true));
		_validateQueryResult("\"true\"", Boolean.valueOf(true));

		_validateQueryResult("3.2", Double.valueOf(3.2));
		_validateQueryResult("'3.2'", Double.valueOf(3.2));
		_validateQueryResult("\"3.2\"", Double.valueOf(3.2));
		_validateQueryResult("2016.0", Double.valueOf(2016));
		_validateQueryResult("'2016.0'", Double.valueOf(2016));
		_validateQueryResult("\"2016.0\"", Double.valueOf(2016));

		_validateQueryResult("2016", Integer.valueOf(2016));
		_validateQueryResult("'2016'", Integer.valueOf(2016));
		_validateQueryResult("\"2016\"", Integer.valueOf(2016));

		_validateQueryResult("test", "test");
		_validateQueryResult("'test'", "test");
		_validateQueryResult("\"test\"", "test");

		_validateQueryResult("'test test'", "test test");
		_validateQueryResult("\"test test\"", "test test");
	}

	private static void _validateQueryResult(String query, Object expected)
		throws Exception {

		_validateQueryResult(query, expected, new Properties());
	}

	private static void _validateQueryResult(
			String query, Object expected, Properties properties)
		throws Exception {

		PQLEntity pqlEntity = PQLEntityFactory.newPQLEntity(query);

		Object actual = pqlEntity.getValue(properties);

		if (!actual.equals(expected)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched result within the following query:\n");
			sb.append(query);
			sb.append("\n* Actual:   ");
			sb.append(actual);
			sb.append("\n* Expected: ");
			sb.append(expected);

			throw new Exception(sb.toString());
		}
	}

}