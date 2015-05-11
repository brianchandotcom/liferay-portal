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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.social.networking.exception.NoSuchMeetupsEntryException;
import com.liferay.social.networking.model.MeetupsEntry;
import com.liferay.social.networking.service.MeetupsEntryLocalServiceUtil;
import com.liferay.social.networking.service.persistence.MeetupsEntryPersistence;
import com.liferay.social.networking.service.persistence.MeetupsEntryUtil;

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
public class MeetupsEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MeetupsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MeetupsEntry> iterator = _meetupsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsEntry meetupsEntry = _persistence.create(pk);

		Assert.assertNotNull(meetupsEntry);

		Assert.assertEquals(meetupsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		_persistence.remove(newMeetupsEntry);

		MeetupsEntry existingMeetupsEntry = _persistence.fetchByPrimaryKey(newMeetupsEntry.getPrimaryKey());

		Assert.assertNull(existingMeetupsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMeetupsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsEntry newMeetupsEntry = _persistence.create(pk);

		newMeetupsEntry.setCompanyId(RandomTestUtil.nextLong());

		newMeetupsEntry.setUserId(RandomTestUtil.nextLong());

		newMeetupsEntry.setUserName(RandomTestUtil.randomString());

		newMeetupsEntry.setCreateDate(RandomTestUtil.nextDate());

		newMeetupsEntry.setModifiedDate(RandomTestUtil.nextDate());

		newMeetupsEntry.setTitle(RandomTestUtil.randomString());

		newMeetupsEntry.setDescription(RandomTestUtil.randomString());

		newMeetupsEntry.setStartDate(RandomTestUtil.nextDate());

		newMeetupsEntry.setEndDate(RandomTestUtil.nextDate());

		newMeetupsEntry.setTotalAttendees(RandomTestUtil.nextInt());

		newMeetupsEntry.setMaxAttendees(RandomTestUtil.nextInt());

		newMeetupsEntry.setPrice(RandomTestUtil.nextDouble());

		newMeetupsEntry.setThumbnailId(RandomTestUtil.nextLong());

		_meetupsEntries.add(_persistence.update(newMeetupsEntry));

		MeetupsEntry existingMeetupsEntry = _persistence.findByPrimaryKey(newMeetupsEntry.getPrimaryKey());

		Assert.assertEquals(existingMeetupsEntry.getMeetupsEntryId(),
			newMeetupsEntry.getMeetupsEntryId());
		Assert.assertEquals(existingMeetupsEntry.getCompanyId(),
			newMeetupsEntry.getCompanyId());
		Assert.assertEquals(existingMeetupsEntry.getUserId(),
			newMeetupsEntry.getUserId());
		Assert.assertEquals(existingMeetupsEntry.getUserName(),
			newMeetupsEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsEntry.getCreateDate()),
			Time.getShortTimestamp(newMeetupsEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsEntry.getModifiedDate()),
			Time.getShortTimestamp(newMeetupsEntry.getModifiedDate()));
		Assert.assertEquals(existingMeetupsEntry.getTitle(),
			newMeetupsEntry.getTitle());
		Assert.assertEquals(existingMeetupsEntry.getDescription(),
			newMeetupsEntry.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsEntry.getStartDate()),
			Time.getShortTimestamp(newMeetupsEntry.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsEntry.getEndDate()),
			Time.getShortTimestamp(newMeetupsEntry.getEndDate()));
		Assert.assertEquals(existingMeetupsEntry.getTotalAttendees(),
			newMeetupsEntry.getTotalAttendees());
		Assert.assertEquals(existingMeetupsEntry.getMaxAttendees(),
			newMeetupsEntry.getMaxAttendees());
		AssertUtils.assertEquals(existingMeetupsEntry.getPrice(),
			newMeetupsEntry.getPrice());
		Assert.assertEquals(existingMeetupsEntry.getThumbnailId(),
			newMeetupsEntry.getThumbnailId());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		MeetupsEntry existingMeetupsEntry = _persistence.findByPrimaryKey(newMeetupsEntry.getPrimaryKey());

		Assert.assertEquals(existingMeetupsEntry, newMeetupsEntry);
	}

	@Test(expected = NoSuchMeetupsEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MeetupsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MeetupsEntry",
			"meetupsEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"title", true, "description", true, "startDate", true, "endDate",
			true, "totalAttendees", true, "maxAttendees", true, "price", true,
			"thumbnailId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		MeetupsEntry existingMeetupsEntry = _persistence.fetchByPrimaryKey(newMeetupsEntry.getPrimaryKey());

		Assert.assertEquals(existingMeetupsEntry, newMeetupsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsEntry missingMeetupsEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMeetupsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MeetupsEntry newMeetupsEntry1 = addMeetupsEntry();
		MeetupsEntry newMeetupsEntry2 = addMeetupsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsEntry1.getPrimaryKey());
		primaryKeys.add(newMeetupsEntry2.getPrimaryKey());

		Map<Serializable, MeetupsEntry> meetupsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, meetupsEntries.size());
		Assert.assertEquals(newMeetupsEntry1,
			meetupsEntries.get(newMeetupsEntry1.getPrimaryKey()));
		Assert.assertEquals(newMeetupsEntry2,
			meetupsEntries.get(newMeetupsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MeetupsEntry> meetupsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(meetupsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MeetupsEntry> meetupsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, meetupsEntries.size());
		Assert.assertEquals(newMeetupsEntry,
			meetupsEntries.get(newMeetupsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MeetupsEntry> meetupsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(meetupsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsEntry.getPrimaryKey());

		Map<Serializable, MeetupsEntry> meetupsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, meetupsEntries.size());
		Assert.assertEquals(newMeetupsEntry,
			meetupsEntries.get(newMeetupsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MeetupsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MeetupsEntry meetupsEntry = (MeetupsEntry)object;

					Assert.assertNotNull(meetupsEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("meetupsEntryId",
				newMeetupsEntry.getMeetupsEntryId()));

		List<MeetupsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MeetupsEntry existingMeetupsEntry = result.get(0);

		Assert.assertEquals(existingMeetupsEntry, newMeetupsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("meetupsEntryId",
				RandomTestUtil.nextLong()));

		List<MeetupsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MeetupsEntry newMeetupsEntry = addMeetupsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"meetupsEntryId"));

		Object newMeetupsEntryId = newMeetupsEntry.getMeetupsEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("meetupsEntryId",
				new Object[] { newMeetupsEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMeetupsEntryId = result.get(0);

		Assert.assertEquals(existingMeetupsEntryId, newMeetupsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"meetupsEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("meetupsEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected MeetupsEntry addMeetupsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsEntry meetupsEntry = _persistence.create(pk);

		meetupsEntry.setCompanyId(RandomTestUtil.nextLong());

		meetupsEntry.setUserId(RandomTestUtil.nextLong());

		meetupsEntry.setUserName(RandomTestUtil.randomString());

		meetupsEntry.setCreateDate(RandomTestUtil.nextDate());

		meetupsEntry.setModifiedDate(RandomTestUtil.nextDate());

		meetupsEntry.setTitle(RandomTestUtil.randomString());

		meetupsEntry.setDescription(RandomTestUtil.randomString());

		meetupsEntry.setStartDate(RandomTestUtil.nextDate());

		meetupsEntry.setEndDate(RandomTestUtil.nextDate());

		meetupsEntry.setTotalAttendees(RandomTestUtil.nextInt());

		meetupsEntry.setMaxAttendees(RandomTestUtil.nextInt());

		meetupsEntry.setPrice(RandomTestUtil.nextDouble());

		meetupsEntry.setThumbnailId(RandomTestUtil.nextLong());

		_meetupsEntries.add(_persistence.update(meetupsEntry));

		return meetupsEntry;
	}

	private List<MeetupsEntry> _meetupsEntries = new ArrayList<MeetupsEntry>();
	private MeetupsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}