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
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
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
		_allClassNames = ClassNameLocalServiceUtil.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Test
	public void testCriterion() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		ClassName className = _allClassNames.get(10);

		dynamicQuery.add(classNameIdProperty.eq(className.getClassNameId()));

		List<ClassName> classNames = ClassNameLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(classNames.toString(), 1, classNames.size());
		Assert.assertEquals(className, classNames.get(0));
	}

	@Test
	public void testLowerBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, _allClassNames.size());

		Assert.assertEquals(
			_allClassNames.subList(10, _allClassNames.size()),
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testLowerUpperBound() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, _allClassNames.size());

		Assert.assertEquals(
			_allClassNames.subList(10, _allClassNames.size()),
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
		dynamicQuery.setLimit(-50, _allClassNames.size());

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

		List<ClassName> classNames = ClassNameLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		for (ClassName className : _allClassNames) {
			if (!classNames.contains(className)) {
				Assert.fail("Class names do not contain " + className);
			}
		}
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
	public void testOrderBy() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		ClassName lastClassName = _allClassNames.get(_allClassNames.size() - 1);

		dynamicQuery.add(
			classNameIdProperty.le(lastClassName.getClassNameId()));

		dynamicQuery.addOrder(OrderFactoryUtil.desc("classNameId"));

		_allClassNames = new ArrayList<>(_allClassNames);

		Collections.reverse(_allClassNames);

		Assert.assertEquals(
			_allClassNames,
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testSingleResult() {
		DynamicQuery dynamicQuery = ClassNameLocalServiceUtil.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, 11);

		List<ClassName> dynamicQueryClassNames =
			ClassNameLocalServiceUtil.dynamicQuery(dynamicQuery);

		Assert.assertEquals(
			dynamicQueryClassNames.toString(), 1,
			dynamicQueryClassNames.size());
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
		dynamicQuery.setLimit(QueryUtil.ALL_POS, 10);

		Assert.assertEquals(
			_allClassNames.subList(0, 10),
			ClassNameLocalServiceUtil.<ClassName>dynamicQuery(dynamicQuery));
	}

	private List<ClassName> _allClassNames;

}