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

package com.liferay.object.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.NoSuchObjectActionEntryException;
import com.liferay.object.model.ObjectActionEntry;
import com.liferay.object.service.ObjectActionEntryLocalServiceUtil;
import com.liferay.object.service.persistence.ObjectActionEntryPersistence;
import com.liferay.object.service.persistence.ObjectActionEntryUtil;
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
public class ObjectActionEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectActionEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectActionEntry> iterator = _objectActionEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectActionEntry objectActionEntry = _persistence.create(pk);

		Assert.assertNotNull(objectActionEntry);

		Assert.assertEquals(objectActionEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		_persistence.remove(newObjectActionEntry);

		ObjectActionEntry existingObjectActionEntry =
			_persistence.fetchByPrimaryKey(
				newObjectActionEntry.getPrimaryKey());

		Assert.assertNull(existingObjectActionEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectActionEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectActionEntry newObjectActionEntry = _persistence.create(pk);

		newObjectActionEntry.setMvccVersion(RandomTestUtil.nextLong());

		newObjectActionEntry.setUuid(RandomTestUtil.randomString());

		newObjectActionEntry.setCompanyId(RandomTestUtil.nextLong());

		newObjectActionEntry.setUserId(RandomTestUtil.nextLong());

		newObjectActionEntry.setUserName(RandomTestUtil.randomString());

		newObjectActionEntry.setCreateDate(RandomTestUtil.nextDate());

		newObjectActionEntry.setModifiedDate(RandomTestUtil.nextDate());

		newObjectActionEntry.setObjectDefinitionId(RandomTestUtil.nextLong());

		newObjectActionEntry.setActive(RandomTestUtil.randomBoolean());

		newObjectActionEntry.setTriggerName(RandomTestUtil.randomString());

		newObjectActionEntry.setName(RandomTestUtil.randomString());

		newObjectActionEntry.setSettings(RandomTestUtil.randomString());

		_objectActionEntries.add(_persistence.update(newObjectActionEntry));

		ObjectActionEntry existingObjectActionEntry =
			_persistence.findByPrimaryKey(newObjectActionEntry.getPrimaryKey());

		Assert.assertEquals(
			existingObjectActionEntry.getMvccVersion(),
			newObjectActionEntry.getMvccVersion());
		Assert.assertEquals(
			existingObjectActionEntry.getUuid(),
			newObjectActionEntry.getUuid());
		Assert.assertEquals(
			existingObjectActionEntry.getObjectActionEntryId(),
			newObjectActionEntry.getObjectActionEntryId());
		Assert.assertEquals(
			existingObjectActionEntry.getCompanyId(),
			newObjectActionEntry.getCompanyId());
		Assert.assertEquals(
			existingObjectActionEntry.getUserId(),
			newObjectActionEntry.getUserId());
		Assert.assertEquals(
			existingObjectActionEntry.getUserName(),
			newObjectActionEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectActionEntry.getCreateDate()),
			Time.getShortTimestamp(newObjectActionEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectActionEntry.getModifiedDate()),
			Time.getShortTimestamp(newObjectActionEntry.getModifiedDate()));
		Assert.assertEquals(
			existingObjectActionEntry.getObjectDefinitionId(),
			newObjectActionEntry.getObjectDefinitionId());
		Assert.assertEquals(
			existingObjectActionEntry.isActive(),
			newObjectActionEntry.isActive());
		Assert.assertEquals(
			existingObjectActionEntry.getTriggerName(),
			newObjectActionEntry.getTriggerName());
		Assert.assertEquals(
			existingObjectActionEntry.getName(),
			newObjectActionEntry.getName());
		Assert.assertEquals(
			existingObjectActionEntry.getSettings(),
			newObjectActionEntry.getSettings());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByO_A_T() throws Exception {
		_persistence.countByO_A_T(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByO_A_T(0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByO_A_T(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		ObjectActionEntry existingObjectActionEntry =
			_persistence.findByPrimaryKey(newObjectActionEntry.getPrimaryKey());

		Assert.assertEquals(existingObjectActionEntry, newObjectActionEntry);
	}

	@Test(expected = NoSuchObjectActionEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectActionEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectActionEntry", "mvccVersion", true, "uuid", true,
			"objectActionEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectDefinitionId", true, "active", true, "triggerName", true,
			"name", true, "settings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		ObjectActionEntry existingObjectActionEntry =
			_persistence.fetchByPrimaryKey(
				newObjectActionEntry.getPrimaryKey());

		Assert.assertEquals(existingObjectActionEntry, newObjectActionEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectActionEntry missingObjectActionEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingObjectActionEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectActionEntry newObjectActionEntry1 = addObjectActionEntry();
		ObjectActionEntry newObjectActionEntry2 = addObjectActionEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectActionEntry1.getPrimaryKey());
		primaryKeys.add(newObjectActionEntry2.getPrimaryKey());

		Map<Serializable, ObjectActionEntry> objectActionEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectActionEntries.size());
		Assert.assertEquals(
			newObjectActionEntry1,
			objectActionEntries.get(newObjectActionEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectActionEntry2,
			objectActionEntries.get(newObjectActionEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectActionEntry> objectActionEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectActionEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectActionEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectActionEntry> objectActionEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectActionEntries.size());
		Assert.assertEquals(
			newObjectActionEntry,
			objectActionEntries.get(newObjectActionEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectActionEntry> objectActionEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectActionEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectActionEntry.getPrimaryKey());

		Map<Serializable, ObjectActionEntry> objectActionEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectActionEntries.size());
		Assert.assertEquals(
			newObjectActionEntry,
			objectActionEntries.get(newObjectActionEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ObjectActionEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ObjectActionEntry>() {

				@Override
				public void performAction(ObjectActionEntry objectActionEntry) {
					Assert.assertNotNull(objectActionEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectActionEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectActionEntryId",
				newObjectActionEntry.getObjectActionEntryId()));

		List<ObjectActionEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectActionEntry existingObjectActionEntry = result.get(0);

		Assert.assertEquals(existingObjectActionEntry, newObjectActionEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectActionEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectActionEntryId", RandomTestUtil.nextLong()));

		List<ObjectActionEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectActionEntry newObjectActionEntry = addObjectActionEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectActionEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectActionEntryId"));

		Object newObjectActionEntryId =
			newObjectActionEntry.getObjectActionEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectActionEntryId", new Object[] {newObjectActionEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectActionEntryId = result.get(0);

		Assert.assertEquals(
			existingObjectActionEntryId, newObjectActionEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectActionEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectActionEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectActionEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectActionEntry addObjectActionEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectActionEntry objectActionEntry = _persistence.create(pk);

		objectActionEntry.setMvccVersion(RandomTestUtil.nextLong());

		objectActionEntry.setUuid(RandomTestUtil.randomString());

		objectActionEntry.setCompanyId(RandomTestUtil.nextLong());

		objectActionEntry.setUserId(RandomTestUtil.nextLong());

		objectActionEntry.setUserName(RandomTestUtil.randomString());

		objectActionEntry.setCreateDate(RandomTestUtil.nextDate());

		objectActionEntry.setModifiedDate(RandomTestUtil.nextDate());

		objectActionEntry.setObjectDefinitionId(RandomTestUtil.nextLong());

		objectActionEntry.setActive(RandomTestUtil.randomBoolean());

		objectActionEntry.setTriggerName(RandomTestUtil.randomString());

		objectActionEntry.setName(RandomTestUtil.randomString());

		objectActionEntry.setSettings(RandomTestUtil.randomString());

		_objectActionEntries.add(_persistence.update(objectActionEntry));

		return objectActionEntry;
	}

	private List<ObjectActionEntry> _objectActionEntries =
		new ArrayList<ObjectActionEntry>();
	private ObjectActionEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}