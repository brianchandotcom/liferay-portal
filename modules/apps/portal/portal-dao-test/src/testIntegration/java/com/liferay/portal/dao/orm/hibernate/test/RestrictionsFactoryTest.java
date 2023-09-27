/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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