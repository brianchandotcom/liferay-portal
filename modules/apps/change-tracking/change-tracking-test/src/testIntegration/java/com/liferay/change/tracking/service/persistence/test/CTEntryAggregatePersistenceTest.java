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

package com.liferay.change.tracking.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.exception.NoSuchEntryAggregateException;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.service.CTEntryAggregateLocalServiceUtil;
import com.liferay.change.tracking.service.persistence.CTEntryAggregatePersistence;
import com.liferay.change.tracking.service.persistence.CTEntryAggregateUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CTEntryAggregatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.change.tracking.service"));

	@Before
	public void setUp() {
		_persistence = CTEntryAggregateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CTEntryAggregate> iterator = _ctEntryAggregates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryAggregate ctEntryAggregate = _persistence.create(pk);

		Assert.assertNotNull(ctEntryAggregate);

		Assert.assertEquals(ctEntryAggregate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		_persistence.remove(newCTEntryAggregate);

		CTEntryAggregate existingCTEntryAggregate =
			_persistence.fetchByPrimaryKey(newCTEntryAggregate.getPrimaryKey());

		Assert.assertNull(existingCTEntryAggregate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCTEntryAggregate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryAggregate newCTEntryAggregate = _persistence.create(pk);

		newCTEntryAggregate.setCompanyId(RandomTestUtil.nextLong());

		newCTEntryAggregate.setUserId(RandomTestUtil.nextLong());

		newCTEntryAggregate.setUserName(RandomTestUtil.randomString());

		newCTEntryAggregate.setCreateDate(RandomTestUtil.nextDate());

		newCTEntryAggregate.setModifiedDate(RandomTestUtil.nextDate());

		newCTEntryAggregate.setOwnerCTEntryId(RandomTestUtil.nextLong());

		_ctEntryAggregates.add(_persistence.update(newCTEntryAggregate));

		CTEntryAggregate existingCTEntryAggregate =
			_persistence.findByPrimaryKey(newCTEntryAggregate.getPrimaryKey());

		Assert.assertEquals(
			existingCTEntryAggregate.getCtEntryAggregateId(),
			newCTEntryAggregate.getCtEntryAggregateId());
		Assert.assertEquals(
			existingCTEntryAggregate.getCompanyId(),
			newCTEntryAggregate.getCompanyId());
		Assert.assertEquals(
			existingCTEntryAggregate.getUserId(),
			newCTEntryAggregate.getUserId());
		Assert.assertEquals(
			existingCTEntryAggregate.getUserName(),
			newCTEntryAggregate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCTEntryAggregate.getCreateDate()),
			Time.getShortTimestamp(newCTEntryAggregate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCTEntryAggregate.getModifiedDate()),
			Time.getShortTimestamp(newCTEntryAggregate.getModifiedDate()));
		Assert.assertEquals(
			existingCTEntryAggregate.getOwnerCTEntryId(),
			newCTEntryAggregate.getOwnerCTEntryId());
	}

	@Test
	public void testCountByOwnerCTEntryID() throws Exception {
		_persistence.countByOwnerCTEntryID(RandomTestUtil.nextLong());

		_persistence.countByOwnerCTEntryID(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		CTEntryAggregate existingCTEntryAggregate =
			_persistence.findByPrimaryKey(newCTEntryAggregate.getPrimaryKey());

		Assert.assertEquals(existingCTEntryAggregate, newCTEntryAggregate);
	}

	@Test(expected = NoSuchEntryAggregateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CTEntryAggregate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CTEntryAggregate", "ctEntryAggregateId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "ownerCTEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		CTEntryAggregate existingCTEntryAggregate =
			_persistence.fetchByPrimaryKey(newCTEntryAggregate.getPrimaryKey());

		Assert.assertEquals(existingCTEntryAggregate, newCTEntryAggregate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryAggregate missingCTEntryAggregate =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCTEntryAggregate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CTEntryAggregate newCTEntryAggregate1 = addCTEntryAggregate();
		CTEntryAggregate newCTEntryAggregate2 = addCTEntryAggregate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryAggregate1.getPrimaryKey());
		primaryKeys.add(newCTEntryAggregate2.getPrimaryKey());

		Map<Serializable, CTEntryAggregate> ctEntryAggregates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ctEntryAggregates.size());
		Assert.assertEquals(
			newCTEntryAggregate1,
			ctEntryAggregates.get(newCTEntryAggregate1.getPrimaryKey()));
		Assert.assertEquals(
			newCTEntryAggregate2,
			ctEntryAggregates.get(newCTEntryAggregate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CTEntryAggregate> ctEntryAggregates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctEntryAggregates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryAggregate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CTEntryAggregate> ctEntryAggregates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctEntryAggregates.size());
		Assert.assertEquals(
			newCTEntryAggregate,
			ctEntryAggregates.get(newCTEntryAggregate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CTEntryAggregate> ctEntryAggregates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctEntryAggregates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryAggregate.getPrimaryKey());

		Map<Serializable, CTEntryAggregate> ctEntryAggregates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctEntryAggregates.size());
		Assert.assertEquals(
			newCTEntryAggregate,
			ctEntryAggregates.get(newCTEntryAggregate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CTEntryAggregateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CTEntryAggregate>() {

				@Override
				public void performAction(CTEntryAggregate ctEntryAggregate) {
					Assert.assertNotNull(ctEntryAggregate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTEntryAggregate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ctEntryAggregateId",
				newCTEntryAggregate.getCtEntryAggregateId()));

		List<CTEntryAggregate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CTEntryAggregate existingCTEntryAggregate = result.get(0);

		Assert.assertEquals(existingCTEntryAggregate, newCTEntryAggregate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTEntryAggregate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ctEntryAggregateId", RandomTestUtil.nextLong()));

		List<CTEntryAggregate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CTEntryAggregate newCTEntryAggregate = addCTEntryAggregate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTEntryAggregate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ctEntryAggregateId"));

		Object newCtEntryAggregateId =
			newCTEntryAggregate.getCtEntryAggregateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ctEntryAggregateId", new Object[] {newCtEntryAggregateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCtEntryAggregateId = result.get(0);

		Assert.assertEquals(existingCtEntryAggregateId, newCtEntryAggregateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTEntryAggregate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ctEntryAggregateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ctEntryAggregateId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CTEntryAggregate addCTEntryAggregate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryAggregate ctEntryAggregate = _persistence.create(pk);

		ctEntryAggregate.setCompanyId(RandomTestUtil.nextLong());

		ctEntryAggregate.setUserId(RandomTestUtil.nextLong());

		ctEntryAggregate.setUserName(RandomTestUtil.randomString());

		ctEntryAggregate.setCreateDate(RandomTestUtil.nextDate());

		ctEntryAggregate.setModifiedDate(RandomTestUtil.nextDate());

		ctEntryAggregate.setOwnerCTEntryId(RandomTestUtil.nextLong());

		_ctEntryAggregates.add(_persistence.update(ctEntryAggregate));

		return ctEntryAggregate;
	}

	private List<CTEntryAggregate> _ctEntryAggregates =
		new ArrayList<CTEntryAggregate>();
	private CTEntryAggregatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}