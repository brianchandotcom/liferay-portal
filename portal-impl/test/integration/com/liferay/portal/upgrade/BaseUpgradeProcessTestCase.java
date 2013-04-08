/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseUpgradeProcessTestCase {

	@Before
	public void setUp() throws Exception {
		String createSql = readOriginDatabaseFile();

		UpgradeProcessTestUtil.setUpOriginDatabase(createSql);

		preConditions();
	}

	@After
	public void tearDown() throws Exception {
		try {
			UpgradeProcessTestUtil.verifyUpgradedDatabase();

			postConditions();

			UpgradeProcessTestUtil.tearDownOriginDatabase();
		}
		finally {
			UpgradeProcessTestUtil.reloadCurrentSpringDatasources();
		}
	}

	protected void postConditions() {
	}

	protected void preConditions() {
	}

	protected String readOriginDatabaseFile() throws Exception {
		StringBundler sb = new StringBundler(3);

		String type = DBFactoryUtil.getDB().getType();

		sb.append("dependencies/portal-");
		sb.append(type.toLowerCase());
		sb.append(".sql");

		Class<?> clazz = this.getClass();

		InputStream inputStream = clazz.getResourceAsStream(sb.toString());

		String sql = StringUtil.read(inputStream);

		return StringUtil.replace(
			sql, "lportal", UpgradeProcessTestUtil.DATABASE_NAME);
	}

}