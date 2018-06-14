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

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testInWithDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(
			PropsValues.DATABASE_IN_MAX_PARAMETERS, false);
	}

	@Test
	public void testInWithMoreThanDatabaseInMaxParametersValue() {
		_testInMaxParametersValue(
			PropsValues.DATABASE_IN_MAX_PARAMETERS + 1, true);
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

}