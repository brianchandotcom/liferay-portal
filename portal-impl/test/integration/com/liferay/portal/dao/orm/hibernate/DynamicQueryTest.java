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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class DynamicQueryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_originalClassNames = ClassNameLocalServiceUtil.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < _TEST_BATCH_SIZE; i++) {
			ClassName className = ClassNameLocalServiceUtil.addClassName(
				RandomTestUtil.randomString());

			_classNames.add(className);
		}
	}

	@Test
	public void testLowerBound() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(_originalClassNames.size(), QueryUtil.ALL_POS);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(_TEST_BATCH_SIZE, dynamicQueryClassNames.size());
	}

	@Test
	public void testLowerUpperBound() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(
			_originalClassNames.size(),
			_originalClassNames.size() + _TEST_BATCH_SIZE);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(_TEST_BATCH_SIZE, dynamicQueryClassNames.size());

		for (ClassName className : dynamicQueryClassNames) {
			if (_originalClassNames.contains(className)) {
				Assert.fail("Returned class name is invalid");
			}
		}
	}

	@Test
	public void testNegativeBoundaries() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(-1985, -625);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(0, dynamicQueryClassNames.size());
	}

	@Test
	public void testNegativeLowerBound() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(-50, QueryUtil.ALL_POS);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(
			_originalClassNames.size() + _TEST_BATCH_SIZE,
			dynamicQueryClassNames.size());
	}

	@Test
	public void testNegativeUpperBound() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(QueryUtil.ALL_POS, -50);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(0, dynamicQueryClassNames.size());
	}

	@Test
	public void testNoLimit() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(
			_originalClassNames.size() + _TEST_BATCH_SIZE,
			dynamicQueryClassNames.size());
	}

	@Test
	public void testNoResults() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(10, 10);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(0, dynamicQueryClassNames.size());
	}

	@Test
	public void testSingleResult() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(10, 11);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(1, dynamicQueryClassNames.size());
	}

	@Test
	public void testStartHigherThanEnd() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(1984, 309);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(0, dynamicQueryClassNames.size());
	}

	@Test
	public void testUpperBound() throws Exception {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.setLimit(QueryUtil.ALL_POS, _TEST_BATCH_SIZE);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertNotNull(dynamicQueryClassNames);

		Assert.assertEquals(_TEST_BATCH_SIZE, dynamicQueryClassNames.size());
	}

	private static final int _TEST_BATCH_SIZE = 50;

	@DeleteAfterTestRun
	private final List<ClassName> _classNames = new ArrayList<>();

	private List<ClassName> _originalClassNames = null;

}