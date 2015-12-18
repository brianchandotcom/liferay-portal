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

import com.liferay.portal.kernel.dao.db.DBType;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 * @author Miguel Pastor
 */
public class OracleSQLTransformerTest extends BaseSQLTransformerTestCase {

	@Test
	public void testReplaceNotEqualsBlankStringComparison() {
		Assert.assertEquals(
			"SELECT * FROM User_ WHERE emailAddress IS NOT NULL",
			transformSQL("SELECT * FROM User_ WHERE emailAddress != ''"));
	}

	@Override
	protected DBType getDBType() {
		return DBType.ORACLE;
	}

}