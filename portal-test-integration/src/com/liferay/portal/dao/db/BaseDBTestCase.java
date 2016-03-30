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

		sb.append("select * from SomeTable where someColumn1 = ");
		sb.append(_db.getTemplateFalse());
		sb.append(" and someColumn2 = ");
		sb.append(_db.getTemplateTrue());
		sb.append(StringPool.NEW_LINE);

		Assert.assertEquals(sb.toString(), buildSQL(_BOOLEAN_LITERAL_QUERY));
		Assert.assertEquals(
			_BOOLEAN_PATTERN_QUERY + StringPool.NEW_LINE,
			buildSQL(_BOOLEAN_PATTERN_QUERY));
	}

	protected String buildSQL(String query) throws IOException {
		return _db.buildSQL(query);
	}

	protected abstract DB getDB();

	protected static final String RENAME_TABLE_QUERY = "alter_table_name a b";

	private static final String _BOOLEAN_LITERAL_QUERY =
		"select * from SomeTable where someColumn1 = FALSE and someColumn2 = " +
			"TRUE";

	private static final String _BOOLEAN_PATTERN_QUERY =
		"select * from SomeTable where someColumn1 = [$FALSE$] and " +
			"someColumn2 = [$TRUE$]";

	private final DB _db = getDB();

}