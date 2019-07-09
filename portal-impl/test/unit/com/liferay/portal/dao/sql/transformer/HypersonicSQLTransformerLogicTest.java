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

import com.liferay.portal.dao.db.HypersonicDB;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class HypersonicSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	public HypersonicSQLTransformerLogicTest() {
		super(new HypersonicDB(1, 0));
	}

	@Override
	public String getDropTableIfExistsTextTransformedSQL() {
		return "DROP TABLE Foo IF EXISTS";
	}

	@Override
	@Test
	public void testReplaceBitwiseCheckWithExtraWhitespace() {
		Assert.assertEquals(
			getBitwiseCheckTransformedSQL(),
			sqlTransformer.transform(getBitwiseCheckOriginalSQL()));
	}

	@Override
	@Test
	public void testReplaceModWithExtraWhitespace() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformer.transform(getModOriginalSQL()));
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CONVERT(foo, SQL_VARCHAR) from Foo";
	}

	@Override
	protected String getCastLongTransformedSQL() {
		return "select CONVERT(foo, SQL_BIGINT) from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo / bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select CAST(NULL AS DATE) from Foo";
	}

}