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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentCollectionPersistence;
import com.liferay.fragment.service.persistence.FragmentCollectionUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class FragmentCollectionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentCollection> iterator = _fragmentCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentCollection fragmentCollection = _persistence.create(pk);

		Assert.assertNotNull(fragmentCollection);

		Assert.assertEquals(fragmentCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		_persistence.remove(newFragmentCollection);

		FragmentCollection existingFragmentCollection =
			_persistence.fetchByPrimaryKey(
				newFragmentCollection.getPrimaryKey());

		Assert.assertNull(existingFragmentCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentCollection newFragmentCollection = _persistence.create(pk);

		newFragmentCollection.setMvccVersion(RandomTestUtil.nextLong());

		newFragmentCollection.setUuid(RandomTestUtil.randomString());

		newFragmentCollection.setGroupId(RandomTestUtil.nextLong());

		newFragmentCollection.setCompanyId(RandomTestUtil.nextLong());

		newFragmentCollection.setUserId(RandomTestUtil.nextLong());

		newFragmentCollection.setUserName(RandomTestUtil.randomString());

		newFragmentCollection.setCreateDate(RandomTestUtil.nextDate());

		newFragmentCollection.setModifiedDate(RandomTestUtil.nextDate());

		newFragmentCollection.setFragmentCollectionKey(
			RandomTestUtil.randomString());

		newFragmentCollection.setName(RandomTestUtil.randomString());

		newFragmentCollection.setDescription(RandomTestUtil.randomString());

		newFragmentCollection.setLastPublishDate(RandomTestUtil.nextDate());

		_fragmentCollections.add(_persistence.update(newFragmentCollection));

		FragmentCollection existingFragmentCollection =
			_persistence.findByPrimaryKey(
				newFragmentCollection.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentCollection.getMvccVersion(),
			newFragmentCollection.getMvccVersion());
		Assert.assertEquals(
			existingFragmentCollection.getUuid(),
			newFragmentCollection.getUuid());
		Assert.assertEquals(
			existingFragmentCollection.getFragmentCollectionId(),
			newFragmentCollection.getFragmentCollectionId());
		Assert.assertEquals(
			existingFragmentCollection.getGroupId(),
			newFragmentCollection.getGroupId());
		Assert.assertEquals(
			existingFragmentCollection.getCompanyId(),
			newFragmentCollection.getCompanyId());
		Assert.assertEquals(
			existingFragmentCollection.getUserId(),
			newFragmentCollection.getUserId());
		Assert.assertEquals(
			existingFragmentCollection.getUserName(),
			newFragmentCollection.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentCollection.getCreateDate()),
			Time.getShortTimestamp(newFragmentCollection.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentCollection.getModifiedDate()),
			Time.getShortTimestamp(newFragmentCollection.getModifiedDate()));
		Assert.assertEquals(
			existingFragmentCollection.getFragmentCollectionKey(),
			newFragmentCollection.getFragmentCollectionKey());
		Assert.assertEquals(
			existingFragmentCollection.getName(),
			newFragmentCollection.getName());
		Assert.assertEquals(
			existingFragmentCollection.getDescription(),
			newFragmentCollection.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentCollection.getLastPublishDate()),
			Time.getShortTimestamp(newFragmentCollection.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByG_FCK() throws Exception {
		_persistence.countByG_FCK(RandomTestUtil.nextLong(), "");

		_persistence.countByG_FCK(0L, "null");

		_persistence.countByG_FCK(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeN(0L, "null");

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeNArrayable() throws Exception {
		_persistence.countByG_LikeN(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomString());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		FragmentCollection existingFragmentCollection =
			_persistence.findByPrimaryKey(
				newFragmentCollection.getPrimaryKey());

		Assert.assertEquals(existingFragmentCollection, newFragmentCollection);
	}

	@Test(expected = NoSuchCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FragmentCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FragmentCollection", "mvccVersion", true, "uuid", true,
			"fragmentCollectionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "fragmentCollectionKey", true, "name", true,
			"description", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		FragmentCollection existingFragmentCollection =
			_persistence.fetchByPrimaryKey(
				newFragmentCollection.getPrimaryKey());

		Assert.assertEquals(existingFragmentCollection, newFragmentCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentCollection missingFragmentCollection =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FragmentCollection newFragmentCollection1 = addFragmentCollection();
		FragmentCollection newFragmentCollection2 = addFragmentCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentCollection1.getPrimaryKey());
		primaryKeys.add(newFragmentCollection2.getPrimaryKey());

		Map<Serializable, FragmentCollection> fragmentCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentCollections.size());
		Assert.assertEquals(
			newFragmentCollection1,
			fragmentCollections.get(newFragmentCollection1.getPrimaryKey()));
		Assert.assertEquals(
			newFragmentCollection2,
			fragmentCollections.get(newFragmentCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentCollection> fragmentCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FragmentCollection newFragmentCollection = addFragmentCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentCollection> fragmentCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentCollections.size());
		Assert.assertEquals(
			newFragmentCollection,
			fragmentCollections.get(newFragmentCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentCollection> fragmentCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentCollection.getPrimaryKey());

		Map<Serializable, FragmentCollection> fragmentCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentCollections.size());
		Assert.assertEquals(
			newFragmentCollection,
			fragmentCollections.get(newFragmentCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FragmentCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FragmentCollection>() {

				@Override
				public void performAction(
					FragmentCollection fragmentCollection) {

					Assert.assertNotNull(fragmentCollection);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentCollectionId",
				newFragmentCollection.getFragmentCollectionId()));

		List<FragmentCollection> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentCollection existingFragmentCollection = result.get(0);

		Assert.assertEquals(existingFragmentCollection, newFragmentCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentCollectionId", RandomTestUtil.nextLong()));

		List<FragmentCollection> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentCollectionId"));

		Object newFragmentCollectionId =
			newFragmentCollection.getFragmentCollectionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentCollectionId",
				new Object[] {newFragmentCollectionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentCollectionId = result.get(0);

		Assert.assertEquals(
			existingFragmentCollectionId, newFragmentCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentCollectionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentCollectionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FragmentCollection newFragmentCollection = addFragmentCollection();

		_persistence.clearCache();

		FragmentCollection existingFragmentCollection =
			_persistence.findByPrimaryKey(
				newFragmentCollection.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingFragmentCollection.getUuid(),
				ReflectionTestUtil.invoke(
					existingFragmentCollection, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingFragmentCollection.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentCollection, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingFragmentCollection.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentCollection, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingFragmentCollection.getFragmentCollectionKey(),
				ReflectionTestUtil.invoke(
					existingFragmentCollection,
					"getOriginalFragmentCollectionKey", new Class<?>[0])));
	}

	protected FragmentCollection addFragmentCollection() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentCollection fragmentCollection = _persistence.create(pk);

		fragmentCollection.setMvccVersion(RandomTestUtil.nextLong());

		fragmentCollection.setUuid(RandomTestUtil.randomString());

		fragmentCollection.setGroupId(RandomTestUtil.nextLong());

		fragmentCollection.setCompanyId(RandomTestUtil.nextLong());

		fragmentCollection.setUserId(RandomTestUtil.nextLong());

		fragmentCollection.setUserName(RandomTestUtil.randomString());

		fragmentCollection.setCreateDate(RandomTestUtil.nextDate());

		fragmentCollection.setModifiedDate(RandomTestUtil.nextDate());

		fragmentCollection.setFragmentCollectionKey(
			RandomTestUtil.randomString());

		fragmentCollection.setName(RandomTestUtil.randomString());

		fragmentCollection.setDescription(RandomTestUtil.randomString());

		fragmentCollection.setLastPublishDate(RandomTestUtil.nextDate());

		_fragmentCollections.add(_persistence.update(fragmentCollection));

		return fragmentCollection;
	}

	private List<FragmentCollection> _fragmentCollections =
		new ArrayList<FragmentCollection>();
	private FragmentCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}