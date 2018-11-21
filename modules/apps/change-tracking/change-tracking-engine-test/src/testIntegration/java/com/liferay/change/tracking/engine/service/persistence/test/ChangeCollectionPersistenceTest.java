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

package com.liferay.change.tracking.engine.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.change.tracking.engine.exception.NoSuchChangeCollectionException;
import com.liferay.change.tracking.engine.model.ChangeCollection;
import com.liferay.change.tracking.engine.service.ChangeCollectionLocalServiceUtil;
import com.liferay.change.tracking.engine.service.persistence.ChangeCollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.ChangeCollectionUtil;

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
public class ChangeCollectionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.engine.service"));

	@Before
	public void setUp() {
		_persistence = ChangeCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangeCollection> iterator = _changeCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeCollection changeCollection = _persistence.create(pk);

		Assert.assertNotNull(changeCollection);

		Assert.assertEquals(changeCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		_persistence.remove(newChangeCollection);

		ChangeCollection existingChangeCollection = _persistence.fetchByPrimaryKey(newChangeCollection.getPrimaryKey());

		Assert.assertNull(existingChangeCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangeCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeCollection newChangeCollection = _persistence.create(pk);

		newChangeCollection.setCompanyId(RandomTestUtil.nextLong());

		newChangeCollection.setUserId(RandomTestUtil.nextLong());

		newChangeCollection.setUserName(RandomTestUtil.randomString());

		newChangeCollection.setCreateDate(RandomTestUtil.nextDate());

		newChangeCollection.setModifiedDate(RandomTestUtil.nextDate());

		newChangeCollection.setName(RandomTestUtil.randomString());

		newChangeCollection.setDescription(RandomTestUtil.randomString());

		_changeCollections.add(_persistence.update(newChangeCollection));

		ChangeCollection existingChangeCollection = _persistence.findByPrimaryKey(newChangeCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeCollection.getChangeCollectionId(),
			newChangeCollection.getChangeCollectionId());
		Assert.assertEquals(existingChangeCollection.getCompanyId(),
			newChangeCollection.getCompanyId());
		Assert.assertEquals(existingChangeCollection.getUserId(),
			newChangeCollection.getUserId());
		Assert.assertEquals(existingChangeCollection.getUserName(),
			newChangeCollection.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeCollection.getCreateDate()),
			Time.getShortTimestamp(newChangeCollection.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeCollection.getModifiedDate()),
			Time.getShortTimestamp(newChangeCollection.getModifiedDate()));
		Assert.assertEquals(existingChangeCollection.getName(),
			newChangeCollection.getName());
		Assert.assertEquals(existingChangeCollection.getDescription(),
			newChangeCollection.getDescription());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		ChangeCollection existingChangeCollection = _persistence.findByPrimaryKey(newChangeCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeCollection, newChangeCollection);
	}

	@Test(expected = NoSuchChangeCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangeCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangeCollection",
			"changeCollectionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		ChangeCollection existingChangeCollection = _persistence.fetchByPrimaryKey(newChangeCollection.getPrimaryKey());

		Assert.assertEquals(existingChangeCollection, newChangeCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeCollection missingChangeCollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangeCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangeCollection newChangeCollection1 = addChangeCollection();
		ChangeCollection newChangeCollection2 = addChangeCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeCollection1.getPrimaryKey());
		primaryKeys.add(newChangeCollection2.getPrimaryKey());

		Map<Serializable, ChangeCollection> changeCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changeCollections.size());
		Assert.assertEquals(newChangeCollection1,
			changeCollections.get(newChangeCollection1.getPrimaryKey()));
		Assert.assertEquals(newChangeCollection2,
			changeCollections.get(newChangeCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangeCollection> changeCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangeCollection> changeCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeCollections.size());
		Assert.assertEquals(newChangeCollection,
			changeCollections.get(newChangeCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangeCollection> changeCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeCollection.getPrimaryKey());

		Map<Serializable, ChangeCollection> changeCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeCollections.size());
		Assert.assertEquals(newChangeCollection,
			changeCollections.get(newChangeCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangeCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangeCollection>() {
				@Override
				public void performAction(ChangeCollection changeCollection) {
					Assert.assertNotNull(changeCollection);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeCollectionId",
				newChangeCollection.getChangeCollectionId()));

		List<ChangeCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangeCollection existingChangeCollection = result.get(0);

		Assert.assertEquals(existingChangeCollection, newChangeCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeCollectionId",
				RandomTestUtil.nextLong()));

		List<ChangeCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangeCollection newChangeCollection = addChangeCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeCollectionId"));

		Object newChangeCollectionId = newChangeCollection.getChangeCollectionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeCollectionId",
				new Object[] { newChangeCollectionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangeCollectionId = result.get(0);

		Assert.assertEquals(existingChangeCollectionId, newChangeCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeCollectionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeCollectionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ChangeCollection addChangeCollection() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeCollection changeCollection = _persistence.create(pk);

		changeCollection.setCompanyId(RandomTestUtil.nextLong());

		changeCollection.setUserId(RandomTestUtil.nextLong());

		changeCollection.setUserName(RandomTestUtil.randomString());

		changeCollection.setCreateDate(RandomTestUtil.nextDate());

		changeCollection.setModifiedDate(RandomTestUtil.nextDate());

		changeCollection.setName(RandomTestUtil.randomString());

		changeCollection.setDescription(RandomTestUtil.randomString());

		_changeCollections.add(_persistence.update(changeCollection));

		return changeCollection;
	}

	private List<ChangeCollection> _changeCollections = new ArrayList<ChangeCollection>();
	private ChangeCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}