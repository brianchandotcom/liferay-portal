/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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