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

package com.liferay.portal.dao.sql.transformer;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class JpqlToHqlTransformerLogicTest {

	@Test
	public void testReplaceCount() {
		Function<String, String> function =
			JpqlToHqlTransformerLogic.getCountFunction();

		Assert.assertEquals(
			"SELECT COUNT(*) FROM Foo foo",
			function.apply("SELECT COUNT(foo) FROM Foo foo"));
	}

	@Test
	public void testReplaceCountNotMatching() {
		Function<String, String> function =
			JpqlToHqlTransformerLogic.getCountFunction();

		String sql = "SELECT * FROM Foo where foo != 1";

		Assert.assertEquals(sql, function.apply(sql));
	}

	@Test
	public void testReplaceCountWithAliasDifferentThanFieldCount() {
		Function<String, String> function =
			JpqlToHqlTransformerLogic.getCountFunction();

		String sql = "SELECT COUNT(bar) FROM Foo foo";

		Assert.assertEquals(sql, function.apply(sql));
	}

}