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

import com.liferay.commerce.forecast.exception.NoSuchForecastEntryException;
import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.service.CommerceForecastEntryLocalServiceUtil;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastEntryPersistence;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastEntryUtil;

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
public class CommerceForecastEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.forecast.service"));

	@Before
	public void setUp() {
		_persistence = CommerceForecastEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceForecastEntry> iterator = _commerceForecastEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastEntry commerceForecastEntry = _persistence.create(pk);

		Assert.assertNotNull(commerceForecastEntry);

		Assert.assertEquals(commerceForecastEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		_persistence.remove(newCommerceForecastEntry);

		CommerceForecastEntry existingCommerceForecastEntry = _persistence.fetchByPrimaryKey(newCommerceForecastEntry.getPrimaryKey());

		Assert.assertNull(existingCommerceForecastEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceForecastEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastEntry newCommerceForecastEntry = _persistence.create(pk);

		newCommerceForecastEntry.setCompanyId(RandomTestUtil.nextLong());

		newCommerceForecastEntry.setUserId(RandomTestUtil.nextLong());

		newCommerceForecastEntry.setUserName(RandomTestUtil.randomString());

		newCommerceForecastEntry.setCreateDate(RandomTestUtil.nextDate());

		newCommerceForecastEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceForecastEntry.setTime(RandomTestUtil.nextLong());

		newCommerceForecastEntry.setPeriod(RandomTestUtil.nextInt());

		newCommerceForecastEntry.setTarget(RandomTestUtil.nextInt());

		newCommerceForecastEntry.setCustomerId(RandomTestUtil.nextLong());

		newCommerceForecastEntry.setSku(RandomTestUtil.randomString());

		newCommerceForecastEntry.setAssertivity(new BigDecimal(
				RandomTestUtil.nextDouble()));

		_commerceForecastEntries.add(_persistence.update(
				newCommerceForecastEntry));

		CommerceForecastEntry existingCommerceForecastEntry = _persistence.findByPrimaryKey(newCommerceForecastEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastEntry.getCommerceForecastEntryId(),
			newCommerceForecastEntry.getCommerceForecastEntryId());
		Assert.assertEquals(existingCommerceForecastEntry.getCompanyId(),
			newCommerceForecastEntry.getCompanyId());
		Assert.assertEquals(existingCommerceForecastEntry.getUserId(),
			newCommerceForecastEntry.getUserId());
		Assert.assertEquals(existingCommerceForecastEntry.getUserName(),
			newCommerceForecastEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceForecastEntry.getCreateDate()),
			Time.getShortTimestamp(newCommerceForecastEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceForecastEntry.getModifiedDate()),
			Time.getShortTimestamp(newCommerceForecastEntry.getModifiedDate()));
		Assert.assertEquals(existingCommerceForecastEntry.getTime(),
			newCommerceForecastEntry.getTime());
		Assert.assertEquals(existingCommerceForecastEntry.getPeriod(),
			newCommerceForecastEntry.getPeriod());
		Assert.assertEquals(existingCommerceForecastEntry.getTarget(),
			newCommerceForecastEntry.getTarget());
		Assert.assertEquals(existingCommerceForecastEntry.getCustomerId(),
			newCommerceForecastEntry.getCustomerId());
		Assert.assertEquals(existingCommerceForecastEntry.getSku(),
			newCommerceForecastEntry.getSku());
		Assert.assertEquals(existingCommerceForecastEntry.getAssertivity(),
			newCommerceForecastEntry.getAssertivity());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_P_T_C_S() throws Exception {
		_persistence.countByC_P_T_C_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextLong(), "");

		_persistence.countByC_P_T_C_S(0L, 0, 0, 0L, "null");

		_persistence.countByC_P_T_C_S(0L, 0, 0, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		CommerceForecastEntry existingCommerceForecastEntry = _persistence.findByPrimaryKey(newCommerceForecastEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastEntry,
			newCommerceForecastEntry);
	}

	@Test(expected = NoSuchForecastEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceForecastEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceForecastEntry",
			"commerceForecastEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "time",
			true, "period", true, "target", true, "customerId", true, "sku",
			true, "assertivity", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		CommerceForecastEntry existingCommerceForecastEntry = _persistence.fetchByPrimaryKey(newCommerceForecastEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceForecastEntry,
			newCommerceForecastEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastEntry missingCommerceForecastEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceForecastEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceForecastEntry newCommerceForecastEntry1 = addCommerceForecastEntry();
		CommerceForecastEntry newCommerceForecastEntry2 = addCommerceForecastEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastEntry1.getPrimaryKey());
		primaryKeys.add(newCommerceForecastEntry2.getPrimaryKey());

		Map<Serializable, CommerceForecastEntry> commerceForecastEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceForecastEntries.size());
		Assert.assertEquals(newCommerceForecastEntry1,
			commerceForecastEntries.get(
				newCommerceForecastEntry1.getPrimaryKey()));
		Assert.assertEquals(newCommerceForecastEntry2,
			commerceForecastEntries.get(
				newCommerceForecastEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceForecastEntry> commerceForecastEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceForecastEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceForecastEntry> commerceForecastEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceForecastEntries.size());
		Assert.assertEquals(newCommerceForecastEntry,
			commerceForecastEntries.get(
				newCommerceForecastEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceForecastEntry> commerceForecastEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceForecastEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceForecastEntry.getPrimaryKey());

		Map<Serializable, CommerceForecastEntry> commerceForecastEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceForecastEntries.size());
		Assert.assertEquals(newCommerceForecastEntry,
			commerceForecastEntries.get(
				newCommerceForecastEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceForecastEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceForecastEntry>() {
				@Override
				public void performAction(
					CommerceForecastEntry commerceForecastEntry) {
					Assert.assertNotNull(commerceForecastEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceForecastEntryId",
				newCommerceForecastEntry.getCommerceForecastEntryId()));

		List<CommerceForecastEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceForecastEntry existingCommerceForecastEntry = result.get(0);

		Assert.assertEquals(existingCommerceForecastEntry,
			newCommerceForecastEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceForecastEntryId",
				RandomTestUtil.nextLong()));

		List<CommerceForecastEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceForecastEntryId"));

		Object newCommerceForecastEntryId = newCommerceForecastEntry.getCommerceForecastEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceForecastEntryId",
				new Object[] { newCommerceForecastEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceForecastEntryId = result.get(0);

		Assert.assertEquals(existingCommerceForecastEntryId,
			newCommerceForecastEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceForecastEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceForecastEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceForecastEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceForecastEntry newCommerceForecastEntry = addCommerceForecastEntry();

		_persistence.clearCache();

		CommerceForecastEntry existingCommerceForecastEntry = _persistence.findByPrimaryKey(newCommerceForecastEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceForecastEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceForecastEntry,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingCommerceForecastEntry.getPeriod()),
			ReflectionTestUtil.<Integer>invoke(existingCommerceForecastEntry,
				"getOriginalPeriod", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingCommerceForecastEntry.getTarget()),
			ReflectionTestUtil.<Integer>invoke(existingCommerceForecastEntry,
				"getOriginalTarget", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceForecastEntry.getCustomerId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceForecastEntry,
				"getOriginalCustomerId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceForecastEntry.getSku(),
				ReflectionTestUtil.invoke(existingCommerceForecastEntry,
					"getOriginalSku", new Class<?>[0])));
	}

	protected CommerceForecastEntry addCommerceForecastEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceForecastEntry commerceForecastEntry = _persistence.create(pk);

		commerceForecastEntry.setCompanyId(RandomTestUtil.nextLong());

		commerceForecastEntry.setUserId(RandomTestUtil.nextLong());

		commerceForecastEntry.setUserName(RandomTestUtil.randomString());

		commerceForecastEntry.setCreateDate(RandomTestUtil.nextDate());

		commerceForecastEntry.setModifiedDate(RandomTestUtil.nextDate());

		commerceForecastEntry.setTime(RandomTestUtil.nextLong());

		commerceForecastEntry.setPeriod(RandomTestUtil.nextInt());

		commerceForecastEntry.setTarget(RandomTestUtil.nextInt());

		commerceForecastEntry.setCustomerId(RandomTestUtil.nextLong());

		commerceForecastEntry.setSku(RandomTestUtil.randomString());

		commerceForecastEntry.setAssertivity(new BigDecimal(
				RandomTestUtil.nextDouble()));

		_commerceForecastEntries.add(_persistence.update(commerceForecastEntry));

		return commerceForecastEntry;
	}

	private List<CommerceForecastEntry> _commerceForecastEntries = new ArrayList<CommerceForecastEntry>();
	private CommerceForecastEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}