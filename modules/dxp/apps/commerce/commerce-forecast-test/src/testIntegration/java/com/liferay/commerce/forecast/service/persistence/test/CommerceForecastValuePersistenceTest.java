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

package com.liferay.commerce.forecast.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.forecast.exception.NoSuchForecastValueException;
import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalServiceUtil;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValuePersistence;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValueUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommerceForecastValuePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.forecast.service"));

	@Before
	public void setUp() {
		_persistence = CommerceForecastValueUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceForecastValue> iterator = _commerceForecastValues.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastValue commerceForecastValue = _persistence.create(pk);

		Assert.assertNotNull(commerceForecastValue);

		Assert.assertEquals(commerceForecastValue.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		_persistence.remove(newCommerceForecastValue);

		CommerceForecastValue existingCommerceForecastValue = _persistence.fetchByPrimaryKey(newCommerceForecastValue.getPrimaryKey());

		Assert.assertNull(existingCommerceForecastValue);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceForecastValue();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastValue newCommerceForecastValue = _persistence.create(pk);

		newCommerceForecastValue.setCompanyId(RandomTestUtil.nextLong());

		newCommerceForecastValue.setUserId(RandomTestUtil.nextLong());

		newCommerceForecastValue.setUserName(RandomTestUtil.randomString());

		newCommerceForecastValue.setCreateDate(RandomTestUtil.nextDate());

		newCommerceForecastValue.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceForecastValue.setCommerceForecastEntryId(RandomTestUtil.nextLong());

		newCommerceForecastValue.setDate(RandomTestUtil.nextDate());

		newCommerceForecastValue.setLowerValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceForecastValue.setValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		newCommerceForecastValue.setUpperValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		_commerceForecastValues.add(_persistence.update(
				newCommerceForecastValue));

		CommerceForecastValue existingCommerceForecastValue = _persistence.findByPrimaryKey(newCommerceForecastValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastValue.getCommerceForecastValueId(),
			newCommerceForecastValue.getCommerceForecastValueId());
		Assert.assertEquals(existingCommerceForecastValue.getCompanyId(),
			newCommerceForecastValue.getCompanyId());
		Assert.assertEquals(existingCommerceForecastValue.getUserId(),
			newCommerceForecastValue.getUserId());
		Assert.assertEquals(existingCommerceForecastValue.getUserName(),
			newCommerceForecastValue.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceForecastValue.getCreateDate()),
			Time.getShortTimestamp(newCommerceForecastValue.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceForecastValue.getModifiedDate()),
			Time.getShortTimestamp(newCommerceForecastValue.getModifiedDate()));
		Assert.assertEquals(existingCommerceForecastValue.getCommerceForecastEntryId(),
			newCommerceForecastValue.getCommerceForecastEntryId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceForecastValue.getDate()),
			Time.getShortTimestamp(newCommerceForecastValue.getDate()));
		Assert.assertEquals(existingCommerceForecastValue.getLowerValue(),
			newCommerceForecastValue.getLowerValue());
		Assert.assertEquals(existingCommerceForecastValue.getValue(),
			newCommerceForecastValue.getValue());
		Assert.assertEquals(existingCommerceForecastValue.getUpperValue(),
			newCommerceForecastValue.getUpperValue());
	}

	@Test
	public void testCountByCommerceForecastEntryId() throws Exception {
		_persistence.countByCommerceForecastEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceForecastEntryId(0L);
	}

	@Test
	public void testCountByC_D() throws Exception {
		_persistence.countByC_D(RandomTestUtil.nextLong(),
			RandomTestUtil.nextDate());

		_persistence.countByC_D(0L, RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		CommerceForecastValue existingCommerceForecastValue = _persistence.findByPrimaryKey(newCommerceForecastValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastValue,
			newCommerceForecastValue);
	}

	@Test(expected = NoSuchForecastValueException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceForecastValue> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceForecastValue",
			"commerceForecastValueId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"commerceForecastEntryId", true, "date", true, "lowerValue", true,
			"value", true, "upperValue", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		CommerceForecastValue existingCommerceForecastValue = _persistence.fetchByPrimaryKey(newCommerceForecastValue.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastValue,
			newCommerceForecastValue);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastValue missingCommerceForecastValue = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceForecastValue);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceForecastValue newCommerceForecastValue1 = addCommerceForecastValue();
		CommerceForecastValue newCommerceForecastValue2 = addCommerceForecastValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastValue1.getPrimaryKey());
		primaryKeys.add(newCommerceForecastValue2.getPrimaryKey());

		Map<Serializable, CommerceForecastValue> commerceForecastValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceForecastValues.size());
		Assert.assertEquals(newCommerceForecastValue1,
			commerceForecastValues.get(
				newCommerceForecastValue1.getPrimaryKey()));
		Assert.assertEquals(newCommerceForecastValue2,
			commerceForecastValues.get(
				newCommerceForecastValue2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceForecastValue> commerceForecastValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceForecastValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastValue.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceForecastValue> commerceForecastValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceForecastValues.size());
		Assert.assertEquals(newCommerceForecastValue,
			commerceForecastValues.get(newCommerceForecastValue.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceForecastValue> commerceForecastValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceForecastValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastValue.getPrimaryKey());

		Map<Serializable, CommerceForecastValue> commerceForecastValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceForecastValues.size());
		Assert.assertEquals(newCommerceForecastValue,
			commerceForecastValues.get(newCommerceForecastValue.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceForecastValueLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceForecastValue>() {
				@Override
				public void performAction(
					CommerceForecastValue commerceForecastValue) {
					Assert.assertNotNull(commerceForecastValue);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceForecastValueId",
				newCommerceForecastValue.getCommerceForecastValueId()));

		List<CommerceForecastValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceForecastValue existingCommerceForecastValue = result.get(0);

		Assert.assertEquals(existingCommerceForecastValue,
			newCommerceForecastValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceForecastValueId",
				RandomTestUtil.nextLong()));

		List<CommerceForecastValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceForecastValueId"));

		Object newCommerceForecastValueId = newCommerceForecastValue.getCommerceForecastValueId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceForecastValueId",
				new Object[] { newCommerceForecastValueId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceForecastValueId = result.get(0);

		Assert.assertEquals(existingCommerceForecastValueId,
			newCommerceForecastValueId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastValue.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceForecastValueId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceForecastValueId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceForecastValue newCommerceForecastValue = addCommerceForecastValue();

		_persistence.clearCache();

		CommerceForecastValue existingCommerceForecastValue = _persistence.findByPrimaryKey(newCommerceForecastValue.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceForecastValue.getCommerceForecastEntryId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceForecastValue,
				"getOriginalCommerceForecastEntryId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceForecastValue.getDate(),
				ReflectionTestUtil.invoke(existingCommerceForecastValue,
					"getOriginalDate", new Class<?>[0])));
	}

	protected CommerceForecastValue addCommerceForecastValue()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastValue commerceForecastValue = _persistence.create(pk);

		commerceForecastValue.setCompanyId(RandomTestUtil.nextLong());

		commerceForecastValue.setUserId(RandomTestUtil.nextLong());

		commerceForecastValue.setUserName(RandomTestUtil.randomString());

		commerceForecastValue.setCreateDate(RandomTestUtil.nextDate());

		commerceForecastValue.setModifiedDate(RandomTestUtil.nextDate());

		commerceForecastValue.setCommerceForecastEntryId(RandomTestUtil.nextLong());

		commerceForecastValue.setDate(RandomTestUtil.nextDate());

		commerceForecastValue.setLowerValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		commerceForecastValue.setValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		commerceForecastValue.setUpperValue(new BigDecimal(
				RandomTestUtil.nextDouble()));

		_commerceForecastValues.add(_persistence.update(commerceForecastValue));

		return commerceForecastValue;
	}

	private List<CommerceForecastValue> _commerceForecastValues = new ArrayList<CommerceForecastValue>();
	private CommerceForecastValuePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}