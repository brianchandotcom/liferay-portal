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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 * @author László Csontos
 */
public abstract class BaseDBTestCase {

	@Test
	public void testReplaceTemplate() throws IOException {
		StringBundler sb = new StringBundler(5);

		sb.append("SELECT * FROM someTable WHERE someColumn = ");
		sb.append(getDB().getTemplateFalse());
		sb.append(" AND anotherColumn = ");
		sb.append(getDB().getTemplateTrue());
		sb.append(StringPool.NEW_LINE);

		Assert.assertEquals(
			sb.toString(), buildSQL(QUERY_WITH_BOOLEAN_LITERAL));

		Assert.assertEquals(
			QUERY_WITH_BOOLEAN_PATTERN + StringPool.NEW_LINE,
			buildSQL(QUERY_WITH_BOOLEAN_PATTERN));
	}

	protected String buildSQL(String query) throws IOException {
		DB db = getDB();

		return db.buildSQL(query);
	}

	protected abstract DB getDB();

	protected static final String QUERY_WITH_BOOLEAN_LITERAL =
		"SELECT * FROM someTable WHERE someColumn = FALSE " +
			"AND anotherColumn = TRUE";

	protected static final String QUERY_WITH_BOOLEAN_PATTERN =
		"SELECT * FROM someTable WHERE someColumn = [$FALSE$] " +
			"AND anotherColumn = [$TRUE$]";

	protected static final String RENAME_TABLE_QUERY = "alter_table_name a b";

}