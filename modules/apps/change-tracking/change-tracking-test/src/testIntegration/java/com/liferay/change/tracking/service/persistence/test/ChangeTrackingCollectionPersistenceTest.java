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

import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.ChangeTrackingCollection;
import com.liferay.change.tracking.service.ChangeTrackingCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.persistence.ChangeTrackingCollectionPersistence;
import com.liferay.change.tracking.service.persistence.ChangeTrackingCollectionUtil;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ChangeTrackingCollectionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.service"));

	@Before
	public void setUp() {
		_persistence = ChangeTrackingCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangeTrackingCollection> iterator = _changeTrackingCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingCollection changeTrackingCollection = _persistence.create(pk);

		Assert.assertNotNull(changeTrackingCollection);

		Assert.assertEquals(changeTrackingCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		_persistence.remove(newChangeTrackingCollection);

		ChangeTrackingCollection existingChangeTrackingCollection = _persistence.fetchByPrimaryKey(newChangeTrackingCollection.getPrimaryKey());

		Assert.assertNull(existingChangeTrackingCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangeTrackingCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingCollection newChangeTrackingCollection = _persistence.create(pk);

		newChangeTrackingCollection.setCompanyId(RandomTestUtil.nextLong());

		newChangeTrackingCollection.setUserId(RandomTestUtil.nextLong());

		newChangeTrackingCollection.setUserName(RandomTestUtil.randomString());

		newChangeTrackingCollection.setCreateDate(RandomTestUtil.nextDate());

		newChangeTrackingCollection.setModifiedDate(RandomTestUtil.nextDate());

		newChangeTrackingCollection.setName(RandomTestUtil.randomString());

		newChangeTrackingCollection.setDescription(RandomTestUtil.randomString());

		newChangeTrackingCollection.setStatus(RandomTestUtil.nextInt());

		newChangeTrackingCollection.setStatusByUserId(RandomTestUtil.nextLong());

		newChangeTrackingCollection.setStatusByUserName(RandomTestUtil.randomString());

		newChangeTrackingCollection.setStatusDate(RandomTestUtil.nextDate());

		_changeTrackingCollections.add(_persistence.update(
				newChangeTrackingCollection));

		ChangeTrackingCollection existingChangeTrackingCollection = _persistence.findByPrimaryKey(newChangeTrackingCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingCollection.getChangeTrackingCollectionId(),
			newChangeTrackingCollection.getChangeTrackingCollectionId());
		Assert.assertEquals(existingChangeTrackingCollection.getCompanyId(),
			newChangeTrackingCollection.getCompanyId());
		Assert.assertEquals(existingChangeTrackingCollection.getUserId(),
			newChangeTrackingCollection.getUserId());
		Assert.assertEquals(existingChangeTrackingCollection.getUserName(),
			newChangeTrackingCollection.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeTrackingCollection.getCreateDate()),
			Time.getShortTimestamp(newChangeTrackingCollection.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeTrackingCollection.getModifiedDate()),
			Time.getShortTimestamp(
				newChangeTrackingCollection.getModifiedDate()));
		Assert.assertEquals(existingChangeTrackingCollection.getName(),
			newChangeTrackingCollection.getName());
		Assert.assertEquals(existingChangeTrackingCollection.getDescription(),
			newChangeTrackingCollection.getDescription());
		Assert.assertEquals(existingChangeTrackingCollection.getStatus(),
			newChangeTrackingCollection.getStatus());
		Assert.assertEquals(existingChangeTrackingCollection.getStatusByUserId(),
			newChangeTrackingCollection.getStatusByUserId());
		Assert.assertEquals(existingChangeTrackingCollection.getStatusByUserName(),
			newChangeTrackingCollection.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeTrackingCollection.getStatusDate()),
			Time.getShortTimestamp(newChangeTrackingCollection.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		ChangeTrackingCollection existingChangeTrackingCollection = _persistence.findByPrimaryKey(newChangeTrackingCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingCollection,
			newChangeTrackingCollection);
	}

	@Test(expected = NoSuchCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangeTrackingCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangeTrackingCollection",
			"changeTrackingCollectionId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true, "description", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		ChangeTrackingCollection existingChangeTrackingCollection = _persistence.fetchByPrimaryKey(newChangeTrackingCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingCollection,
			newChangeTrackingCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingCollection missingChangeTrackingCollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangeTrackingCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection1 = addChangeTrackingCollection();
		ChangeTrackingCollection newChangeTrackingCollection2 = addChangeTrackingCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingCollection1.getPrimaryKey());
		primaryKeys.add(newChangeTrackingCollection2.getPrimaryKey());

		Map<Serializable, ChangeTrackingCollection> changeTrackingCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changeTrackingCollections.size());
		Assert.assertEquals(newChangeTrackingCollection1,
			changeTrackingCollections.get(
				newChangeTrackingCollection1.getPrimaryKey()));
		Assert.assertEquals(newChangeTrackingCollection2,
			changeTrackingCollections.get(
				newChangeTrackingCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangeTrackingCollection> changeTrackingCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeTrackingCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangeTrackingCollection> changeTrackingCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeTrackingCollections.size());
		Assert.assertEquals(newChangeTrackingCollection,
			changeTrackingCollections.get(
				newChangeTrackingCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangeTrackingCollection> changeTrackingCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeTrackingCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingCollection.getPrimaryKey());

		Map<Serializable, ChangeTrackingCollection> changeTrackingCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeTrackingCollections.size());
		Assert.assertEquals(newChangeTrackingCollection,
			changeTrackingCollections.get(
				newChangeTrackingCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangeTrackingCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangeTrackingCollection>() {
				@Override
				public void performAction(
					ChangeTrackingCollection changeTrackingCollection) {
					Assert.assertNotNull(changeTrackingCollection);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changeTrackingCollectionId",
				newChangeTrackingCollection.getChangeTrackingCollectionId()));

		List<ChangeTrackingCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangeTrackingCollection existingChangeTrackingCollection = result.get(0);

		Assert.assertEquals(existingChangeTrackingCollection,
			newChangeTrackingCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changeTrackingCollectionId", RandomTestUtil.nextLong()));

		List<ChangeTrackingCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangeTrackingCollection newChangeTrackingCollection = addChangeTrackingCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeTrackingCollectionId"));

		Object newChangeTrackingCollectionId = newChangeTrackingCollection.getChangeTrackingCollectionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changeTrackingCollectionId",
				new Object[] { newChangeTrackingCollectionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangeTrackingCollectionId = result.get(0);

		Assert.assertEquals(existingChangeTrackingCollectionId,
			newChangeTrackingCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeTrackingCollectionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changeTrackingCollectionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ChangeTrackingCollection addChangeTrackingCollection()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingCollection changeTrackingCollection = _persistence.create(pk);

		changeTrackingCollection.setCompanyId(RandomTestUtil.nextLong());

		changeTrackingCollection.setUserId(RandomTestUtil.nextLong());

		changeTrackingCollection.setUserName(RandomTestUtil.randomString());

		changeTrackingCollection.setCreateDate(RandomTestUtil.nextDate());

		changeTrackingCollection.setModifiedDate(RandomTestUtil.nextDate());

		changeTrackingCollection.setName(RandomTestUtil.randomString());

		changeTrackingCollection.setDescription(RandomTestUtil.randomString());

		changeTrackingCollection.setStatus(RandomTestUtil.nextInt());

		changeTrackingCollection.setStatusByUserId(RandomTestUtil.nextLong());

		changeTrackingCollection.setStatusByUserName(RandomTestUtil.randomString());

		changeTrackingCollection.setStatusDate(RandomTestUtil.nextDate());

		_changeTrackingCollections.add(_persistence.update(
				changeTrackingCollection));

		return changeTrackingCollection;
	}

	private List<ChangeTrackingCollection> _changeTrackingCollections = new ArrayList<ChangeTrackingCollection>();
	private ChangeTrackingCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}