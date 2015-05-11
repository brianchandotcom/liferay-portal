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

package com.liferay.service.access.control.profile.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.service.access.control.profile.exception.NoSuchEntryException;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.SACPEntryLocalServiceUtil;
import com.liferay.service.access.control.profile.service.persistence.SACPEntryPersistence;
import com.liferay.service.access.control.profile.service.persistence.SACPEntryUtil;

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
public class SACPEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SACPEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SACPEntry> iterator = _sacpEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SACPEntry sacpEntry = _persistence.create(pk);

		Assert.assertNotNull(sacpEntry);

		Assert.assertEquals(sacpEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		_persistence.remove(newSACPEntry);

		SACPEntry existingSACPEntry = _persistence.fetchByPrimaryKey(newSACPEntry.getPrimaryKey());

		Assert.assertNull(existingSACPEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSACPEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SACPEntry newSACPEntry = _persistence.create(pk);

		newSACPEntry.setUuid(RandomTestUtil.randomString());

		newSACPEntry.setCompanyId(RandomTestUtil.nextLong());

		newSACPEntry.setUserId(RandomTestUtil.nextLong());

		newSACPEntry.setUserName(RandomTestUtil.randomString());

		newSACPEntry.setCreateDate(RandomTestUtil.nextDate());

		newSACPEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSACPEntry.setAllowedServices(RandomTestUtil.randomString());

		newSACPEntry.setName(RandomTestUtil.randomString());

		newSACPEntry.setTitle(RandomTestUtil.randomString());

		_sacpEntries.add(_persistence.update(newSACPEntry));

		SACPEntry existingSACPEntry = _persistence.findByPrimaryKey(newSACPEntry.getPrimaryKey());

		Assert.assertEquals(existingSACPEntry.getUuid(), newSACPEntry.getUuid());
		Assert.assertEquals(existingSACPEntry.getSacpEntryId(),
			newSACPEntry.getSacpEntryId());
		Assert.assertEquals(existingSACPEntry.getCompanyId(),
			newSACPEntry.getCompanyId());
		Assert.assertEquals(existingSACPEntry.getUserId(),
			newSACPEntry.getUserId());
		Assert.assertEquals(existingSACPEntry.getUserName(),
			newSACPEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSACPEntry.getCreateDate()),
			Time.getShortTimestamp(newSACPEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSACPEntry.getModifiedDate()),
			Time.getShortTimestamp(newSACPEntry.getModifiedDate()));
		Assert.assertEquals(existingSACPEntry.getAllowedServices(),
			newSACPEntry.getAllowedServices());
		Assert.assertEquals(existingSACPEntry.getName(), newSACPEntry.getName());
		Assert.assertEquals(existingSACPEntry.getTitle(),
			newSACPEntry.getTitle());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_N(0L, StringPool.NULL);

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		SACPEntry existingSACPEntry = _persistence.findByPrimaryKey(newSACPEntry.getPrimaryKey());

		Assert.assertEquals(existingSACPEntry, newSACPEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SACPEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SACPEntry", "uuid", true,
			"sacpEntryId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "allowedServices",
			true, "name", true, "title", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		SACPEntry existingSACPEntry = _persistence.fetchByPrimaryKey(newSACPEntry.getPrimaryKey());

		Assert.assertEquals(existingSACPEntry, newSACPEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SACPEntry missingSACPEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSACPEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SACPEntry newSACPEntry1 = addSACPEntry();
		SACPEntry newSACPEntry2 = addSACPEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSACPEntry1.getPrimaryKey());
		primaryKeys.add(newSACPEntry2.getPrimaryKey());

		Map<Serializable, SACPEntry> sacpEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, sacpEntries.size());
		Assert.assertEquals(newSACPEntry1,
			sacpEntries.get(newSACPEntry1.getPrimaryKey()));
		Assert.assertEquals(newSACPEntry2,
			sacpEntries.get(newSACPEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SACPEntry> sacpEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sacpEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSACPEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SACPEntry> sacpEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sacpEntries.size());
		Assert.assertEquals(newSACPEntry,
			sacpEntries.get(newSACPEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SACPEntry> sacpEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sacpEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSACPEntry.getPrimaryKey());

		Map<Serializable, SACPEntry> sacpEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sacpEntries.size());
		Assert.assertEquals(newSACPEntry,
			sacpEntries.get(newSACPEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SACPEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SACPEntry sacpEntry = (SACPEntry)object;

					Assert.assertNotNull(sacpEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SACPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("sacpEntryId",
				newSACPEntry.getSacpEntryId()));

		List<SACPEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SACPEntry existingSACPEntry = result.get(0);

		Assert.assertEquals(existingSACPEntry, newSACPEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SACPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("sacpEntryId",
				RandomTestUtil.nextLong()));

		List<SACPEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SACPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("sacpEntryId"));

		Object newSacpEntryId = newSACPEntry.getSacpEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("sacpEntryId",
				new Object[] { newSacpEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSacpEntryId = result.get(0);

		Assert.assertEquals(existingSacpEntryId, newSacpEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SACPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("sacpEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("sacpEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SACPEntry newSACPEntry = addSACPEntry();

		_persistence.clearCache();

		SACPEntry existingSACPEntry = _persistence.findByPrimaryKey(newSACPEntry.getPrimaryKey());

		Assert.assertEquals(existingSACPEntry.getCompanyId(),
			ReflectionTestUtil.invoke(existingSACPEntry,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingSACPEntry.getName(),
				ReflectionTestUtil.invoke(existingSACPEntry, "getOriginalName",
					new Class<?>[0])));
	}

	protected SACPEntry addSACPEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SACPEntry sacpEntry = _persistence.create(pk);

		sacpEntry.setUuid(RandomTestUtil.randomString());

		sacpEntry.setCompanyId(RandomTestUtil.nextLong());

		sacpEntry.setUserId(RandomTestUtil.nextLong());

		sacpEntry.setUserName(RandomTestUtil.randomString());

		sacpEntry.setCreateDate(RandomTestUtil.nextDate());

		sacpEntry.setModifiedDate(RandomTestUtil.nextDate());

		sacpEntry.setAllowedServices(RandomTestUtil.randomString());

		sacpEntry.setName(RandomTestUtil.randomString());

		sacpEntry.setTitle(RandomTestUtil.randomString());

		_sacpEntries.add(_persistence.update(sacpEntry));

		return sacpEntry;
	}

	private List<SACPEntry> _sacpEntries = new ArrayList<SACPEntry>();
	private SACPEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}