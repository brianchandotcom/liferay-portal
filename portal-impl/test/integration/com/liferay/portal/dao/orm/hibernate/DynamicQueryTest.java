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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mate Thurzo
 * @author Shuyang Zhou
 */
public class DynamicQueryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_allClassNames = new ArrayList<>(
			ClassNameLocalServiceUtil.getClassNames(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		_oldClassNameCount = _allClassNames.size();

		for (int i = 0; i < _BATCH_SIZE; i++) {
			ClassName className = ClassNameLocalServiceUtil.addClassName(
				RandomTestUtil.randomString(
					UniqueStringRandomizerBumper.INSTANCE));

			_newClassNames.add(className);

			_allClassNames.add(className);
		}
	}

	@Test
	public void testLowerBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(_oldClassNameCount, QueryUtil.ALL_POS);

		Assert.assertEquals(
			_newClassNames,
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testLowerUpperBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(
			_oldClassNameCount, _oldClassNameCount + _BATCH_SIZE);

		Assert.assertEquals(
			_newClassNames,
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testNegativeBoundaries() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(-1985, -625);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertTrue(dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testNegativeLowerBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(-50, QueryUtil.ALL_POS);

		Assert.assertEquals(
			_allClassNames,
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testNegativeUpperBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(QueryUtil.ALL_POS, -50);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertTrue(dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testNoLimit() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));

		Assert.assertEquals(
			_allClassNames,
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testNoResults() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(10, 10);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertTrue(dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testSingleResult() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, 11);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertEquals(1, dynamicQueryClassNames.size());
		Assert.assertEquals(
			_allClassNames.get(10), dynamicQueryClassNames.get(0));
	}

	@Test
	public void testStartHigherThanEnd() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(1984, 309);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertTrue(dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testUpperBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(QueryUtil.ALL_POS, _BATCH_SIZE);

		Assert.assertEquals(
			_allClassNames.subList(0, _BATCH_SIZE),
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	private static final int _BATCH_SIZE = 50;

	private List<ClassName> _allClassNames;

	@DeleteAfterTestRun
	private final List<ClassName> _newClassNames = new ArrayList<>();

	private int _oldClassNameCount;

}