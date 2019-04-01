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
import com.liferay.portal.dao.orm.hibernate.RestrictionsFactoryImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Máté Thurzó
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class DynamicQueryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_allClassNames = _classNameLocalService.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Test
	public void testCriterion() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		ClassName className = _allClassNames.get(10);

		dynamicQuery.add(classNameIdProperty.eq(className.getClassNameId()));

		List<ClassName> classNames = _classNameLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(classNames.toString(), 1, classNames.size());
		Assert.assertEquals(className, classNames.get(0));
	}

	@Test
	public void testInRestrictionCriterion() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		List<Long> values = new ArrayList<>(2);

		ClassName className1 = _allClassNames.get(1);
		ClassName className2 = _allClassNames.get(2);

		values.add(className1.getClassNameId());
		values.add(className2.getClassNameId());

		dynamicQuery.add(RestrictionsFactoryUtil.in("classNameId", values));

		List<ClassName> classNames = _classNameLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(classNames.toString(), 2, classNames.size());
		Assert.assertTrue(
			classNames.toString(), classNames.contains(className1));
		Assert.assertTrue(
			classNames.toString(), classNames.contains(className2));
	}

	@Test
	public void testInRestrictionCriterionWithMoreThanDatabaseInMaxParametersValue() {
		RestrictionsFactory restrictionsFactory = new RestrictionsFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			restrictionsFactory, "_databaseInMaxParameters",
			_DATABASE_IN_MAX_PARAMETERS);

		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		List<Long> values = new ArrayList<>(_DATABASE_IN_MAX_PARAMETERS + 1);

		ClassName className1 = _allClassNames.get(1);
		ClassName className2 = _allClassNames.get(2);

		values.add(className1.getClassNameId());

		for (long i = 1; i < _DATABASE_IN_MAX_PARAMETERS; i++) {
			values.add(-i);
		}

		values.add(className2.getClassNameId());

		Assert.assertEquals(
			values.toString(), _DATABASE_IN_MAX_PARAMETERS + 1, values.size());

		dynamicQuery.add(restrictionsFactory.in("classNameId", values));

		List<ClassName> classNames = _classNameLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(classNames.toString(), 2, classNames.size());
		Assert.assertTrue(
			classNames.toString(), classNames.contains(className1));
		Assert.assertTrue(
			classNames.toString(), classNames.contains(className2));
	}

	@Test
	public void testLowerBound() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, _allClassNames.size());

		Assert.assertEquals(
			_allClassNames.subList(10, _allClassNames.size()),
			_classNameLocalService.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testLowerUpperBound() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, _allClassNames.size());

		Assert.assertEquals(
			_allClassNames.subList(10, _allClassNames.size()),
			_classNameLocalService.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testNegativeBoundaries() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.setLimit(-1985, -625);

		List<ClassName> dynamicQueryClassNames =
			_classNameLocalService.dynamicQuery(dynamicQuery);

		Assert.assertTrue(
			dynamicQueryClassNames.toString(),
			dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testNegativeLowerBound() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(-50, _allClassNames.size());

		Assert.assertEquals(
			_allClassNames,
			_classNameLocalService.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testNegativeUpperBound() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.setLimit(QueryUtil.ALL_POS, -50);

		List<ClassName> dynamicQueryClassNames =
			_classNameLocalService.dynamicQuery(dynamicQuery);

		Assert.assertTrue(
			dynamicQueryClassNames.toString(),
			dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testNoLimit() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));

		List<ClassName> classNames = _classNameLocalService.dynamicQuery(
			dynamicQuery);

		for (ClassName className : _allClassNames) {
			Assert.assertTrue(
				"Class names do not contain " + className,
				classNames.contains(className));
		}
	}

	@Test
	public void testNoResults() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.setLimit(10, 10);

		List<ClassName> dynamicQueryClassNames =
			_classNameLocalService.dynamicQuery(dynamicQuery);

		Assert.assertTrue(
			dynamicQueryClassNames.toString(),
			dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testOrderBy() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

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
			_classNameLocalService.<ClassName>dynamicQuery(dynamicQuery));
	}

	@Test
	public void testSingleResult() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(10, 11);

		List<ClassName> dynamicQueryClassNames =
			_classNameLocalService.dynamicQuery(dynamicQuery);

		Assert.assertEquals(
			dynamicQueryClassNames.toString(), 1,
			dynamicQueryClassNames.size());
		Assert.assertEquals(
			_allClassNames.get(10), dynamicQueryClassNames.get(0));
	}

	@Test
	public void testStartHigherThanEnd() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.setLimit(1984, 309);

		List<ClassName> dynamicQueryClassNames =
			_classNameLocalService.dynamicQuery(dynamicQuery);

		Assert.assertTrue(
			dynamicQueryClassNames.toString(),
			dynamicQueryClassNames.isEmpty());
	}

	@Test
	public void testUpperBound() {
		DynamicQuery dynamicQuery = _classNameLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("classNameId"));
		dynamicQuery.setLimit(QueryUtil.ALL_POS, 10);

		Assert.assertEquals(
			_allClassNames.subList(0, 10),
			_classNameLocalService.<ClassName>dynamicQuery(dynamicQuery));
	}

	private static final int _DATABASE_IN_MAX_PARAMETERS = 1000;

	private List<ClassName> _allClassNames;

	@Inject
	private ClassNameLocalService _classNameLocalService;

}