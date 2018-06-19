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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class RestrictionsFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		_databaseInMaxParameters = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.DATABASE_IN_MAX_PARAMETERS,
				new Filter(dbType.getName())));

		Assume.assumeTrue(_databaseInMaxParameters > 0);
	}

	@Test
	public void testInWithDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(_databaseInMaxParameters, false);
	}

	@Test
	public void testInWithMoreThanDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(_databaseInMaxParameters + 1, true);
	}

	private void _testInMaxParametersValue(
		int length, boolean expectedDisjunction) {

		List<Integer> values = new ArrayList<>(length);

		for (int i = 0; i < length; i++) {
			values.add(i);
		}

		Criterion criterion = RestrictionsFactoryUtil.in("property", values);

		Class<?> clazz = criterion.getClass();

		Assert.assertEquals(
			clazz.getName(), expectedDisjunction,
			criterion instanceof Disjunction);
	}

	private int _databaseInMaxParameters;

}