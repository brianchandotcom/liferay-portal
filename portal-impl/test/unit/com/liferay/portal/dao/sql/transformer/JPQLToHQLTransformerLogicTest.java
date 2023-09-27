/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class JPQLToHQLTransformerLogicTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReplaceCount() {
		Function<String, String> function =
			JPQLToHQLTransformerLogic.getCountFunction();

		Assert.assertEquals(
			"SELECT COUNT(*) FROM Foo foo",
			function.apply("SELECT COUNT(foo) FROM Foo foo"));
	}

	@Test
	public void testReplaceCountWithIncorrectAlias() {
		String sql = "SELECT COUNT(bar) FROM Foo foo";

		Function<String, String> function =
			JPQLToHQLTransformerLogic.getCountFunction();

		Assert.assertEquals(sql, function.apply(sql));
	}

	@Test
	public void testReplaceCountWithNoCount() {
		String sql = "SELECT * FROM Foo where foo != 1";

		Function<String, String> function =
			JPQLToHQLTransformerLogic.getCountFunction();

		Assert.assertEquals(sql, function.apply(sql));
	}

}