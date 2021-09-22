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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
public class SQLServerLimitStringUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDistinct() throws Exception {
		String sql = SQLServerLimitStringUtil.getLimitString(
			"SELECT DISTINCT JournalArticle.* FROM JournalArticle ORDER BY " +
				"userName ASC",
			10, 30);

		Assert.assertFalse(sql.contains("SELECT top DISTINCT"));
		Assert.assertTrue(sql.contains("SELECT DISTINCT top"));
	}

	@Test
	public void testInnerOrderBy() throws Exception {
		String sql = SQLServerLimitStringUtil.getLimitString(
			"SELECT articleId, userName FROM JournalArticle ORDER BY " +
				"modifiedDate ASC",
			10, 30);

		Assert.assertTrue(sql.indexOf("30") > 0);
		Assert.assertTrue(sql.indexOf("11") > 0);
		Assert.assertTrue(sql.indexOf("top") > 0);
	}

	@Test
	public void testNoInnerOrderBy() throws Exception {
		String sql = SQLServerLimitStringUtil.getLimitString(
			"SELECT articleId, userName FROM JournalArticle ORDER BY " +
				"userName ASC",
			10, 30);

		Assert.assertTrue(sql.indexOf("30") > 0);
		Assert.assertTrue(sql.indexOf("11") > 0);
		Assert.assertTrue(sql.indexOf("top") != 0);
	}

	@Test
	public void testUnionWithFieldsQuery() throws Exception {
		String sql = SQLServerLimitStringUtil.getLimitString(
			"( SELECT articleId, userName FROM JournalArticle ) UNION ALL ( " +
				"SELECT articleId, userName FROM JournalArticle )",
			10, 30);

		Assert.assertTrue(sql.indexOf("30") > 0);
		Assert.assertTrue(sql.indexOf("11") > 0);
		Assert.assertTrue(sql.indexOf("top") != 0);
	}

}