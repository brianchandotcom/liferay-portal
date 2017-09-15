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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException;
import com.liferay.commerce.model.CAvailabilityRangeEntry;
import com.liferay.commerce.service.CAvailabilityRangeEntryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CAvailabilityRangeEntryPersistence;
import com.liferay.commerce.service.persistence.CAvailabilityRangeEntryUtil;

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
public class CAvailabilityRangeEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CAvailabilityRangeEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CAvailabilityRangeEntry> iterator = _cAvailabilityRangeEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CAvailabilityRangeEntry cAvailabilityRangeEntry = _persistence.create(pk);

		Assert.assertNotNull(cAvailabilityRangeEntry);

		Assert.assertEquals(cAvailabilityRangeEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		_persistence.remove(newCAvailabilityRangeEntry);

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = _persistence.fetchByPrimaryKey(newCAvailabilityRangeEntry.getPrimaryKey());

		Assert.assertNull(existingCAvailabilityRangeEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCAvailabilityRangeEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CAvailabilityRangeEntry newCAvailabilityRangeEntry = _persistence.create(pk);

		newCAvailabilityRangeEntry.setUuid(RandomTestUtil.randomString());

		newCAvailabilityRangeEntry.setGroupId(RandomTestUtil.nextLong());

		newCAvailabilityRangeEntry.setCompanyId(RandomTestUtil.nextLong());

		newCAvailabilityRangeEntry.setUserId(RandomTestUtil.nextLong());

		newCAvailabilityRangeEntry.setUserName(RandomTestUtil.randomString());

		newCAvailabilityRangeEntry.setCreateDate(RandomTestUtil.nextDate());

		newCAvailabilityRangeEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCAvailabilityRangeEntry.setCPDefinitionId(RandomTestUtil.nextLong());

		newCAvailabilityRangeEntry.setCommerceAvailabilityRangeId(RandomTestUtil.nextLong());

		newCAvailabilityRangeEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_cAvailabilityRangeEntries.add(_persistence.update(
				newCAvailabilityRangeEntry));

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = _persistence.findByPrimaryKey(newCAvailabilityRangeEntry.getPrimaryKey());

		Assert.assertEquals(existingCAvailabilityRangeEntry.getUuid(),
			newCAvailabilityRangeEntry.getUuid());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getCAvailabilityRangeEntryId(),
			newCAvailabilityRangeEntry.getCAvailabilityRangeEntryId());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getGroupId(),
			newCAvailabilityRangeEntry.getGroupId());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getCompanyId(),
			newCAvailabilityRangeEntry.getCompanyId());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getUserId(),
			newCAvailabilityRangeEntry.getUserId());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getUserName(),
			newCAvailabilityRangeEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCAvailabilityRangeEntry.getCreateDate()),
			Time.getShortTimestamp(newCAvailabilityRangeEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCAvailabilityRangeEntry.getModifiedDate()),
			Time.getShortTimestamp(newCAvailabilityRangeEntry.getModifiedDate()));
		Assert.assertEquals(existingCAvailabilityRangeEntry.getCPDefinitionId(),
			newCAvailabilityRangeEntry.getCPDefinitionId());
		Assert.assertEquals(existingCAvailabilityRangeEntry.getCommerceAvailabilityRangeId(),
			newCAvailabilityRangeEntry.getCommerceAvailabilityRangeId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCAvailabilityRangeEntry.getLastPublishDate()),
			Time.getShortTimestamp(
				newCAvailabilityRangeEntry.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = _persistence.findByPrimaryKey(newCAvailabilityRangeEntry.getPrimaryKey());

		Assert.assertEquals(existingCAvailabilityRangeEntry,
			newCAvailabilityRangeEntry);
	}

	@Test(expected = NoSuchCAvailabilityRangeEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CAvailabilityRangeEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CAvailabilityRangeEntry",
			"uuid", true, "CAvailabilityRangeEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPDefinitionId", true,
			"commerceAvailabilityRangeId", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = _persistence.fetchByPrimaryKey(newCAvailabilityRangeEntry.getPrimaryKey());

		Assert.assertEquals(existingCAvailabilityRangeEntry,
			newCAvailabilityRangeEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CAvailabilityRangeEntry missingCAvailabilityRangeEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCAvailabilityRangeEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry1 = addCAvailabilityRangeEntry();
		CAvailabilityRangeEntry newCAvailabilityRangeEntry2 = addCAvailabilityRangeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCAvailabilityRangeEntry1.getPrimaryKey());
		primaryKeys.add(newCAvailabilityRangeEntry2.getPrimaryKey());

		Map<Serializable, CAvailabilityRangeEntry> cAvailabilityRangeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cAvailabilityRangeEntries.size());
		Assert.assertEquals(newCAvailabilityRangeEntry1,
			cAvailabilityRangeEntries.get(
				newCAvailabilityRangeEntry1.getPrimaryKey()));
		Assert.assertEquals(newCAvailabilityRangeEntry2,
			cAvailabilityRangeEntries.get(
				newCAvailabilityRangeEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CAvailabilityRangeEntry> cAvailabilityRangeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cAvailabilityRangeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCAvailabilityRangeEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CAvailabilityRangeEntry> cAvailabilityRangeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cAvailabilityRangeEntries.size());
		Assert.assertEquals(newCAvailabilityRangeEntry,
			cAvailabilityRangeEntries.get(
				newCAvailabilityRangeEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CAvailabilityRangeEntry> cAvailabilityRangeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cAvailabilityRangeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCAvailabilityRangeEntry.getPrimaryKey());

		Map<Serializable, CAvailabilityRangeEntry> cAvailabilityRangeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cAvailabilityRangeEntries.size());
		Assert.assertEquals(newCAvailabilityRangeEntry,
			cAvailabilityRangeEntries.get(
				newCAvailabilityRangeEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CAvailabilityRangeEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CAvailabilityRangeEntry>() {
				@Override
				public void performAction(
					CAvailabilityRangeEntry cAvailabilityRangeEntry) {
					Assert.assertNotNull(cAvailabilityRangeEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CAvailabilityRangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CAvailabilityRangeEntryId",
				newCAvailabilityRangeEntry.getCAvailabilityRangeEntryId()));

		List<CAvailabilityRangeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = result.get(0);

		Assert.assertEquals(existingCAvailabilityRangeEntry,
			newCAvailabilityRangeEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CAvailabilityRangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CAvailabilityRangeEntryId", RandomTestUtil.nextLong()));

		List<CAvailabilityRangeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CAvailabilityRangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CAvailabilityRangeEntryId"));

		Object newCAvailabilityRangeEntryId = newCAvailabilityRangeEntry.getCAvailabilityRangeEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CAvailabilityRangeEntryId",
				new Object[] { newCAvailabilityRangeEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCAvailabilityRangeEntryId = result.get(0);

		Assert.assertEquals(existingCAvailabilityRangeEntryId,
			newCAvailabilityRangeEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CAvailabilityRangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CAvailabilityRangeEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CAvailabilityRangeEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CAvailabilityRangeEntry newCAvailabilityRangeEntry = addCAvailabilityRangeEntry();

		_persistence.clearCache();

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry = _persistence.findByPrimaryKey(newCAvailabilityRangeEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCAvailabilityRangeEntry.getUuid(),
				ReflectionTestUtil.invoke(existingCAvailabilityRangeEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalCPDefinitionId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalCPDefinitionId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCAvailabilityRangeEntry.getCommerceAvailabilityRangeId()),
			ReflectionTestUtil.<Long>invoke(existingCAvailabilityRangeEntry,
				"getOriginalCommerceAvailabilityRangeId", new Class<?>[0]));
	}

	protected CAvailabilityRangeEntry addCAvailabilityRangeEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CAvailabilityRangeEntry cAvailabilityRangeEntry = _persistence.create(pk);

		cAvailabilityRangeEntry.setUuid(RandomTestUtil.randomString());

		cAvailabilityRangeEntry.setGroupId(RandomTestUtil.nextLong());

		cAvailabilityRangeEntry.setCompanyId(RandomTestUtil.nextLong());

		cAvailabilityRangeEntry.setUserId(RandomTestUtil.nextLong());

		cAvailabilityRangeEntry.setUserName(RandomTestUtil.randomString());

		cAvailabilityRangeEntry.setCreateDate(RandomTestUtil.nextDate());

		cAvailabilityRangeEntry.setModifiedDate(RandomTestUtil.nextDate());

		cAvailabilityRangeEntry.setCPDefinitionId(RandomTestUtil.nextLong());

		cAvailabilityRangeEntry.setCommerceAvailabilityRangeId(RandomTestUtil.nextLong());

		cAvailabilityRangeEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_cAvailabilityRangeEntries.add(_persistence.update(
				cAvailabilityRangeEntry));

		return cAvailabilityRangeEntry;
	}

	private List<CAvailabilityRangeEntry> _cAvailabilityRangeEntries = new ArrayList<CAvailabilityRangeEntry>();
	private CAvailabilityRangeEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}