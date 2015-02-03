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

package com.liferay.social.networking.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.social.networking.exception.NoSuchWallEntryException;
import com.liferay.social.networking.model.WallEntry;
import com.liferay.social.networking.service.WallEntryLocalServiceUtil;
import com.liferay.social.networking.service.persistence.WallEntryPersistence;
import com.liferay.social.networking.service.persistence.WallEntryUtil;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class WallEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = WallEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WallEntry> iterator = _wallEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WallEntry wallEntry = _persistence.create(pk);

		Assert.assertNotNull(wallEntry);

		Assert.assertEquals(wallEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WallEntry newWallEntry = addWallEntry();

		_persistence.remove(newWallEntry);

		WallEntry existingWallEntry = _persistence.fetchByPrimaryKey(newWallEntry.getPrimaryKey());

		Assert.assertNull(existingWallEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWallEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WallEntry newWallEntry = _persistence.create(pk);

		newWallEntry.setGroupId(RandomTestUtil.nextLong());

		newWallEntry.setCompanyId(RandomTestUtil.nextLong());

		newWallEntry.setUserId(RandomTestUtil.nextLong());

		newWallEntry.setUserName(RandomTestUtil.randomString());

		newWallEntry.setCreateDate(RandomTestUtil.nextDate());

		newWallEntry.setModifiedDate(RandomTestUtil.nextDate());

		newWallEntry.setComments(RandomTestUtil.randomString());

		_wallEntries.add(_persistence.update(newWallEntry));

		WallEntry existingWallEntry = _persistence.findByPrimaryKey(newWallEntry.getPrimaryKey());

		Assert.assertEquals(existingWallEntry.getWallEntryId(),
			newWallEntry.getWallEntryId());
		Assert.assertEquals(existingWallEntry.getGroupId(),
			newWallEntry.getGroupId());
		Assert.assertEquals(existingWallEntry.getCompanyId(),
			newWallEntry.getCompanyId());
		Assert.assertEquals(existingWallEntry.getUserId(),
			newWallEntry.getUserId());
		Assert.assertEquals(existingWallEntry.getUserName(),
			newWallEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWallEntry.getCreateDate()),
			Time.getShortTimestamp(newWallEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWallEntry.getModifiedDate()),
			Time.getShortTimestamp(newWallEntry.getModifiedDate()));
		Assert.assertEquals(existingWallEntry.getComments(),
			newWallEntry.getComments());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U() {
		try {
			_persistence.countByG_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WallEntry newWallEntry = addWallEntry();

		WallEntry existingWallEntry = _persistence.findByPrimaryKey(newWallEntry.getPrimaryKey());

		Assert.assertEquals(existingWallEntry, newWallEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchWallEntryException");
		}
		catch (NoSuchWallEntryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<WallEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WallEntry", "wallEntryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"comments", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WallEntry newWallEntry = addWallEntry();

		WallEntry existingWallEntry = _persistence.fetchByPrimaryKey(newWallEntry.getPrimaryKey());

		Assert.assertEquals(existingWallEntry, newWallEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WallEntry missingWallEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWallEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WallEntry newWallEntry1 = addWallEntry();
		WallEntry newWallEntry2 = addWallEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWallEntry1.getPrimaryKey());
		primaryKeys.add(newWallEntry2.getPrimaryKey());

		Map<Serializable, WallEntry> wallEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, wallEntries.size());
		Assert.assertEquals(newWallEntry1,
			wallEntries.get(newWallEntry1.getPrimaryKey()));
		Assert.assertEquals(newWallEntry2,
			wallEntries.get(newWallEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WallEntry> wallEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wallEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WallEntry newWallEntry = addWallEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWallEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WallEntry> wallEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wallEntries.size());
		Assert.assertEquals(newWallEntry,
			wallEntries.get(newWallEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WallEntry> wallEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wallEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WallEntry newWallEntry = addWallEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWallEntry.getPrimaryKey());

		Map<Serializable, WallEntry> wallEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wallEntries.size());
		Assert.assertEquals(newWallEntry,
			wallEntries.get(newWallEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WallEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WallEntry wallEntry = (WallEntry)object;

					Assert.assertNotNull(wallEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WallEntry newWallEntry = addWallEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WallEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wallEntryId",
				newWallEntry.getWallEntryId()));

		List<WallEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WallEntry existingWallEntry = result.get(0);

		Assert.assertEquals(existingWallEntry, newWallEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WallEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wallEntryId",
				RandomTestUtil.nextLong()));

		List<WallEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WallEntry newWallEntry = addWallEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WallEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("wallEntryId"));

		Object newWallEntryId = newWallEntry.getWallEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("wallEntryId",
				new Object[] { newWallEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWallEntryId = result.get(0);

		Assert.assertEquals(existingWallEntryId, newWallEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WallEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("wallEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("wallEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected WallEntry addWallEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WallEntry wallEntry = _persistence.create(pk);

		wallEntry.setGroupId(RandomTestUtil.nextLong());

		wallEntry.setCompanyId(RandomTestUtil.nextLong());

		wallEntry.setUserId(RandomTestUtil.nextLong());

		wallEntry.setUserName(RandomTestUtil.randomString());

		wallEntry.setCreateDate(RandomTestUtil.nextDate());

		wallEntry.setModifiedDate(RandomTestUtil.nextDate());

		wallEntry.setComments(RandomTestUtil.randomString());

		_wallEntries.add(_persistence.update(wallEntry));

		return wallEntry;
	}

	private List<WallEntry> _wallEntries = new ArrayList<WallEntry>();
	private WallEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}