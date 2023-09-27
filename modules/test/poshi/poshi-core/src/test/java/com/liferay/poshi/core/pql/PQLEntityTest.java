/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

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