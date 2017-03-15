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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.db.HypersonicDB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest(DBManagerUtil.class)
@RunWith(PowerMockRunner.class)
public class HypersonicSQLTransformerTest extends BaseSQLTransformerTestCase {

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
		return "select foo /  bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select NULL from Foo";
	}

	@Override
	protected void mockDB() {
		PowerMockito.mockStatic(DBManagerUtil.class);

		Mockito.when(DBManagerUtil.getDB()).thenReturn(new HypersonicDB(1, 0));
	}

}