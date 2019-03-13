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

package com.liferay.portal.dao.orm.hibernate.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.orm.hibernate.CriterionImpl;
import com.liferay.portal.dao.orm.hibernate.DisjunctionImpl;
import com.liferay.portal.dao.orm.hibernate.RestrictionsFactoryImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class RestrictionsFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInWithDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(
			_DATABASE_IN_MAX_PARAMETERS, CriterionImpl.class);
	}

	@Test
	public void testInWithMoreThanDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(
			_DATABASE_IN_MAX_PARAMETERS + 1, DisjunctionImpl.class);
	}

	private void _testInMaxParametersValue(int length, Class<?> expectedClass) {
		RestrictionsFactory restrictionsFactory = new RestrictionsFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			restrictionsFactory, "_databaseInMaxParameters",
			_DATABASE_IN_MAX_PARAMETERS);

		List<Integer> values = new ArrayList<>(length);

		for (int i = 0; i < length; i++) {
			values.add(i);
		}

		Criterion criterion = restrictionsFactory.in("property", values);

		Assert.assertSame(expectedClass, criterion.getClass());
	}

	private static final int _DATABASE_IN_MAX_PARAMETERS = 1000;

}