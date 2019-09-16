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

package com.liferay.segments.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;
import com.liferay.segments.service.persistence.SegmentsEntryUtil;

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
public class SegmentsEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.segments.service"));

	@Before
	public void setUp() {
		_persistence = SegmentsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SegmentsEntry> iterator = _segmentsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntry segmentsEntry = _persistence.create(pk);

		Assert.assertNotNull(segmentsEntry);

		Assert.assertEquals(segmentsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		_persistence.remove(newSegmentsEntry);

		SegmentsEntry existingSegmentsEntry = _persistence.fetchByPrimaryKey(
			newSegmentsEntry.getPrimaryKey());

		Assert.assertNull(existingSegmentsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSegmentsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntry newSegmentsEntry = _persistence.create(pk);

		newSegmentsEntry.setMvccVersion(RandomTestUtil.nextLong());

		newSegmentsEntry.setUuid(RandomTestUtil.randomString());

		newSegmentsEntry.setGroupId(RandomTestUtil.nextLong());

		newSegmentsEntry.setCompanyId(RandomTestUtil.nextLong());

		newSegmentsEntry.setUserId(RandomTestUtil.nextLong());

		newSegmentsEntry.setUserName(RandomTestUtil.randomString());

		newSegmentsEntry.setCreateDate(RandomTestUtil.nextDate());

		newSegmentsEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSegmentsEntry.setSegmentsEntryKey(RandomTestUtil.randomString());

		newSegmentsEntry.setName(RandomTestUtil.randomString());

		newSegmentsEntry.setDescription(RandomTestUtil.randomString());

		newSegmentsEntry.setActive(RandomTestUtil.randomBoolean());

		newSegmentsEntry.setCriteria(RandomTestUtil.randomString());

		newSegmentsEntry.setSource(RandomTestUtil.randomString());

		newSegmentsEntry.setType(RandomTestUtil.randomString());

		newSegmentsEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_segmentsEntries.add(_persistence.update(newSegmentsEntry));

		SegmentsEntry existingSegmentsEntry = _persistence.findByPrimaryKey(
			newSegmentsEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSegmentsEntry.getMvccVersion(),
			newSegmentsEntry.getMvccVersion());
		Assert.assertEquals(
			existingSegmentsEntry.getUuid(), newSegmentsEntry.getUuid());
		Assert.assertEquals(
			existingSegmentsEntry.getSegmentsEntryId(),
			newSegmentsEntry.getSegmentsEntryId());
		Assert.assertEquals(
			existingSegmentsEntry.getGroupId(), newSegmentsEntry.getGroupId());
		Assert.assertEquals(
			existingSegmentsEntry.getCompanyId(),
			newSegmentsEntry.getCompanyId());
		Assert.assertEquals(
			existingSegmentsEntry.getUserId(), newSegmentsEntry.getUserId());
		Assert.assertEquals(
			existingSegmentsEntry.getUserName(),
			newSegmentsEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsEntry.getCreateDate()),
			Time.getShortTimestamp(newSegmentsEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsEntry.getModifiedDate()),
			Time.getShortTimestamp(newSegmentsEntry.getModifiedDate()));
		Assert.assertEquals(
			existingSegmentsEntry.getSegmentsEntryKey(),
			newSegmentsEntry.getSegmentsEntryKey());
		Assert.assertEquals(
			existingSegmentsEntry.getName(), newSegmentsEntry.getName());
		Assert.assertEquals(
			existingSegmentsEntry.getDescription(),
			newSegmentsEntry.getDescription());
		Assert.assertEquals(
			existingSegmentsEntry.isActive(), newSegmentsEntry.isActive());
		Assert.assertEquals(
			existingSegmentsEntry.getCriteria(),
			newSegmentsEntry.getCriteria());
		Assert.assertEquals(
			existingSegmentsEntry.getSource(), newSegmentsEntry.getSource());
		Assert.assertEquals(
			existingSegmentsEntry.getType(), newSegmentsEntry.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsEntry.getLastPublishDate()),
			Time.getShortTimestamp(newSegmentsEntry.getLastPublishDate()));
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
	public void testCountBySource() throws Exception {
		_persistence.countBySource("");

		_persistence.countBySource("null");

		_persistence.countBySource((String)null);
	}

	@Test
	public void testCountByType() throws Exception {
		_persistence.countByType("");

		_persistence.countByType("null");

		_persistence.countByType((String)null);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(), "");

		_persistence.countByG_S(0L, "null");

		_persistence.countByG_S(0L, (String)null);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_AArrayable() throws Exception {
		_persistence.countByG_A(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByA_T() throws Exception {
		_persistence.countByA_T(RandomTestUtil.randomBoolean(), "");

		_persistence.countByA_T(RandomTestUtil.randomBoolean(), "null");

		_persistence.countByA_T(RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByG_A_T() throws Exception {
		_persistence.countByG_A_T(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByG_A_T(0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByG_A_T(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByG_A_TArrayable() throws Exception {
		_persistence.countByG_A_T(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString());
	}

	@Test
	public void testCountByG_A_S_T() throws Exception {
		_persistence.countByG_A_S_T(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "", "");

		_persistence.countByG_A_S_T(
			0L, RandomTestUtil.randomBoolean(), "null", "null");

		_persistence.countByG_A_S_T(
			0L, RandomTestUtil.randomBoolean(), (String)null, (String)null);
	}

	@Test
	public void testCountByG_A_S_TArrayable() throws Exception {
		_persistence.countByG_A_S_T(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		SegmentsEntry existingSegmentsEntry = _persistence.findByPrimaryKey(
			newSegmentsEntry.getPrimaryKey());

		Assert.assertEquals(existingSegmentsEntry, newSegmentsEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SegmentsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SegmentsEntry", "mvccVersion", true, "uuid", true,
			"segmentsEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "segmentsEntryKey", true, "name", true,
			"description", true, "active", true, "source", true, "type", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		SegmentsEntry existingSegmentsEntry = _persistence.fetchByPrimaryKey(
			newSegmentsEntry.getPrimaryKey());

		Assert.assertEquals(existingSegmentsEntry, newSegmentsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntry missingSegmentsEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSegmentsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SegmentsEntry newSegmentsEntry1 = addSegmentsEntry();
		SegmentsEntry newSegmentsEntry2 = addSegmentsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntry1.getPrimaryKey());
		primaryKeys.add(newSegmentsEntry2.getPrimaryKey());

		Map<Serializable, SegmentsEntry> segmentsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, segmentsEntries.size());
		Assert.assertEquals(
			newSegmentsEntry1,
			segmentsEntries.get(newSegmentsEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSegmentsEntry2,
			segmentsEntries.get(newSegmentsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SegmentsEntry> segmentsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SegmentsEntry> segmentsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsEntries.size());
		Assert.assertEquals(
			newSegmentsEntry,
			segmentsEntries.get(newSegmentsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SegmentsEntry> segmentsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntry.getPrimaryKey());

		Map<Serializable, SegmentsEntry> segmentsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsEntries.size());
		Assert.assertEquals(
			newSegmentsEntry,
			segmentsEntries.get(newSegmentsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SegmentsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SegmentsEntry>() {

				@Override
				public void performAction(SegmentsEntry segmentsEntry) {
					Assert.assertNotNull(segmentsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsEntryId", newSegmentsEntry.getSegmentsEntryId()));

		List<SegmentsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SegmentsEntry existingSegmentsEntry = result.get(0);

		Assert.assertEquals(existingSegmentsEntry, newSegmentsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsEntryId", RandomTestUtil.nextLong()));

		List<SegmentsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsEntryId"));

		Object newSegmentsEntryId = newSegmentsEntry.getSegmentsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsEntryId", new Object[] {newSegmentsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSegmentsEntryId = result.get(0);

		Assert.assertEquals(existingSegmentsEntryId, newSegmentsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SegmentsEntry newSegmentsEntry = addSegmentsEntry();

		_persistence.clearCache();

		SegmentsEntry existingSegmentsEntry = _persistence.findByPrimaryKey(
			newSegmentsEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingSegmentsEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingSegmentsEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingSegmentsEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingSegmentsEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingSegmentsEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingSegmentsEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingSegmentsEntry.getSegmentsEntryKey(),
				ReflectionTestUtil.invoke(
					existingSegmentsEntry, "getOriginalSegmentsEntryKey",
					new Class<?>[0])));
	}

	protected SegmentsEntry addSegmentsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntry segmentsEntry = _persistence.create(pk);

		segmentsEntry.setMvccVersion(RandomTestUtil.nextLong());

		segmentsEntry.setUuid(RandomTestUtil.randomString());

		segmentsEntry.setGroupId(RandomTestUtil.nextLong());

		segmentsEntry.setCompanyId(RandomTestUtil.nextLong());

		segmentsEntry.setUserId(RandomTestUtil.nextLong());

		segmentsEntry.setUserName(RandomTestUtil.randomString());

		segmentsEntry.setCreateDate(RandomTestUtil.nextDate());

		segmentsEntry.setModifiedDate(RandomTestUtil.nextDate());

		segmentsEntry.setSegmentsEntryKey(RandomTestUtil.randomString());

		segmentsEntry.setName(RandomTestUtil.randomString());

		segmentsEntry.setDescription(RandomTestUtil.randomString());

		segmentsEntry.setActive(RandomTestUtil.randomBoolean());

		segmentsEntry.setCriteria(RandomTestUtil.randomString());

		segmentsEntry.setSource(RandomTestUtil.randomString());

		segmentsEntry.setType(RandomTestUtil.randomString());

		segmentsEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_segmentsEntries.add(_persistence.update(segmentsEntry));

		return segmentsEntry;
	}

	private List<SegmentsEntry> _segmentsEntries =
		new ArrayList<SegmentsEntry>();
	private SegmentsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}