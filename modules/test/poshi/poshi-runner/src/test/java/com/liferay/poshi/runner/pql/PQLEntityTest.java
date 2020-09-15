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

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLEntityTest extends TestCase {

	@Test
	public void testFixPQL() throws Exception {
		_compareString(PQLEntity.fixPQL("(((test)))"), "test");
		_compareString(PQLEntity.fixPQL(" (((test)) )"), "test");
		_compareString(PQLEntity.fixPQL(" ((( test test ))) "), "test test");
		_compareString(PQLEntity.fixPQL(")test("), ")test(");
		_compareString(PQLEntity.fixPQL(" )test( "), ")test(");
		_compareString(PQLEntity.fixPQL(" ) test"), ") test");
		_compareString(PQLEntity.fixPQL(" ( test"), "( test");
		_compareString(PQLEntity.fixPQL("test ) "), "test )");
		_compareString(PQLEntity.fixPQL("test ( "), "test (");
		_compareString(
			PQLEntity.fixPQL("( (test) OR (test))"), "(test) OR (test)");
		_compareString(
			PQLEntity.fixPQL(" ( (( test ( test ))) "), "( (( test ( test )))");
	}

	@Test
	public void testRemoveModifierFromPQL() throws Exception {
		_compareString(PQLEntity.removeModifierFromPQL("test"), "test");
		_compareString(PQLEntity.removeModifierFromPQL("NOT test"), "test");
		_compareString(PQLEntity.removeModifierFromPQL(" NOT test"), "test");
		_compareString(
			PQLEntity.removeModifierFromPQL(" test NOT"), "test NOT");
		_compareString(PQLEntity.removeModifierFromPQL("OR test"), "OR test");
		_compareString(PQLEntity.removeModifierFromPQL(" OR test"), "OR test");
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

}