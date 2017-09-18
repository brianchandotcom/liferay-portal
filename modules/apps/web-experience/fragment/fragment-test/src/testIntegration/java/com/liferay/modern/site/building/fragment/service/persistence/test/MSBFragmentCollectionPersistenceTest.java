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

package com.liferay.modern.site.building.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionLocalServiceUtil;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentCollectionPersistence;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentCollectionUtil;

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
import com.liferay.portal.kernel.util.StringPool;
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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class MSBFragmentCollectionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.modern.site.building.fragment.service"));

	@Before
	public void setUp() {
		_persistence = MSBFragmentCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MSBFragmentCollection> iterator = _msbFragmentCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentCollection msbFragmentCollection = _persistence.create(pk);

		Assert.assertNotNull(msbFragmentCollection);

		Assert.assertEquals(msbFragmentCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		_persistence.remove(newMSBFragmentCollection);

		MSBFragmentCollection existingMSBFragmentCollection = _persistence.fetchByPrimaryKey(newMSBFragmentCollection.getPrimaryKey());

		Assert.assertNull(existingMSBFragmentCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMSBFragmentCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentCollection newMSBFragmentCollection = _persistence.create(pk);

		newMSBFragmentCollection.setGroupId(RandomTestUtil.nextLong());

		newMSBFragmentCollection.setCompanyId(RandomTestUtil.nextLong());

		newMSBFragmentCollection.setUserId(RandomTestUtil.nextLong());

		newMSBFragmentCollection.setUserName(RandomTestUtil.randomString());

		newMSBFragmentCollection.setCreateDate(RandomTestUtil.nextDate());

		newMSBFragmentCollection.setModifiedDate(RandomTestUtil.nextDate());

		newMSBFragmentCollection.setName(RandomTestUtil.randomString());

		newMSBFragmentCollection.setDescription(RandomTestUtil.randomString());

		_msbFragmentCollections.add(_persistence.update(
				newMSBFragmentCollection));

		MSBFragmentCollection existingMSBFragmentCollection = _persistence.findByPrimaryKey(newMSBFragmentCollection.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentCollection.getMsbFragmentCollectionId(),
			newMSBFragmentCollection.getMsbFragmentCollectionId());
		Assert.assertEquals(existingMSBFragmentCollection.getGroupId(),
			newMSBFragmentCollection.getGroupId());
		Assert.assertEquals(existingMSBFragmentCollection.getCompanyId(),
			newMSBFragmentCollection.getCompanyId());
		Assert.assertEquals(existingMSBFragmentCollection.getUserId(),
			newMSBFragmentCollection.getUserId());
		Assert.assertEquals(existingMSBFragmentCollection.getUserName(),
			newMSBFragmentCollection.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMSBFragmentCollection.getCreateDate()),
			Time.getShortTimestamp(newMSBFragmentCollection.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMSBFragmentCollection.getModifiedDate()),
			Time.getShortTimestamp(newMSBFragmentCollection.getModifiedDate()));
		Assert.assertEquals(existingMSBFragmentCollection.getName(),
			newMSBFragmentCollection.getName());
		Assert.assertEquals(existingMSBFragmentCollection.getDescription(),
			newMSBFragmentCollection.getDescription());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_LikeN(0L, StringPool.NULL);

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		MSBFragmentCollection existingMSBFragmentCollection = _persistence.findByPrimaryKey(newMSBFragmentCollection.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentCollection,
			newMSBFragmentCollection);
	}

	@Test(expected = NoSuchFragmentCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MSBFragmentCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MSBFragmentCollection",
			"msbFragmentCollectionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		MSBFragmentCollection existingMSBFragmentCollection = _persistence.fetchByPrimaryKey(newMSBFragmentCollection.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentCollection,
			newMSBFragmentCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentCollection missingMSBFragmentCollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMSBFragmentCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MSBFragmentCollection newMSBFragmentCollection1 = addMSBFragmentCollection();
		MSBFragmentCollection newMSBFragmentCollection2 = addMSBFragmentCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentCollection1.getPrimaryKey());
		primaryKeys.add(newMSBFragmentCollection2.getPrimaryKey());

		Map<Serializable, MSBFragmentCollection> msbFragmentCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, msbFragmentCollections.size());
		Assert.assertEquals(newMSBFragmentCollection1,
			msbFragmentCollections.get(
				newMSBFragmentCollection1.getPrimaryKey()));
		Assert.assertEquals(newMSBFragmentCollection2,
			msbFragmentCollections.get(
				newMSBFragmentCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MSBFragmentCollection> msbFragmentCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(msbFragmentCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MSBFragmentCollection> msbFragmentCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, msbFragmentCollections.size());
		Assert.assertEquals(newMSBFragmentCollection,
			msbFragmentCollections.get(newMSBFragmentCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MSBFragmentCollection> msbFragmentCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(msbFragmentCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentCollection.getPrimaryKey());

		Map<Serializable, MSBFragmentCollection> msbFragmentCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, msbFragmentCollections.size());
		Assert.assertEquals(newMSBFragmentCollection,
			msbFragmentCollections.get(newMSBFragmentCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MSBFragmentCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<MSBFragmentCollection>() {
				@Override
				public void performAction(
					MSBFragmentCollection msbFragmentCollection) {
					Assert.assertNotNull(msbFragmentCollection);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("msbFragmentCollectionId",
				newMSBFragmentCollection.getMsbFragmentCollectionId()));

		List<MSBFragmentCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MSBFragmentCollection existingMSBFragmentCollection = result.get(0);

		Assert.assertEquals(existingMSBFragmentCollection,
			newMSBFragmentCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("msbFragmentCollectionId",
				RandomTestUtil.nextLong()));

		List<MSBFragmentCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"msbFragmentCollectionId"));

		Object newMsbFragmentCollectionId = newMSBFragmentCollection.getMsbFragmentCollectionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("msbFragmentCollectionId",
				new Object[] { newMsbFragmentCollectionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMsbFragmentCollectionId = result.get(0);

		Assert.assertEquals(existingMsbFragmentCollectionId,
			newMsbFragmentCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"msbFragmentCollectionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("msbFragmentCollectionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MSBFragmentCollection newMSBFragmentCollection = addMSBFragmentCollection();

		_persistence.clearCache();

		MSBFragmentCollection existingMSBFragmentCollection = _persistence.findByPrimaryKey(newMSBFragmentCollection.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingMSBFragmentCollection.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingMSBFragmentCollection,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingMSBFragmentCollection.getName(),
				ReflectionTestUtil.invoke(existingMSBFragmentCollection,
					"getOriginalName", new Class<?>[0])));
	}

	protected MSBFragmentCollection addMSBFragmentCollection()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentCollection msbFragmentCollection = _persistence.create(pk);

		msbFragmentCollection.setGroupId(RandomTestUtil.nextLong());

		msbFragmentCollection.setCompanyId(RandomTestUtil.nextLong());

		msbFragmentCollection.setUserId(RandomTestUtil.nextLong());

		msbFragmentCollection.setUserName(RandomTestUtil.randomString());

		msbFragmentCollection.setCreateDate(RandomTestUtil.nextDate());

		msbFragmentCollection.setModifiedDate(RandomTestUtil.nextDate());

		msbFragmentCollection.setName(RandomTestUtil.randomString());

		msbFragmentCollection.setDescription(RandomTestUtil.randomString());

		_msbFragmentCollections.add(_persistence.update(msbFragmentCollection));

		return msbFragmentCollection;
	}

	private List<MSBFragmentCollection> _msbFragmentCollections = new ArrayList<MSBFragmentCollection>();
	private MSBFragmentCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}