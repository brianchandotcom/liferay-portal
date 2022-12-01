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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLoadFinderCacheEntryException;
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry;
import com.liferay.portal.tools.service.builder.test.service.LoadFinderCacheEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.LoadFinderCacheEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LoadFinderCacheEntryUtil;

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
public class LoadFinderCacheEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = LoadFinderCacheEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LoadFinderCacheEntry> iterator =
			_loadFinderCacheEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LoadFinderCacheEntry loadFinderCacheEntry = _persistence.create(pk);

		Assert.assertNotNull(loadFinderCacheEntry);

		Assert.assertEquals(loadFinderCacheEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		_persistence.remove(newLoadFinderCacheEntry);

		LoadFinderCacheEntry existingLoadFinderCacheEntry =
			_persistence.fetchByPrimaryKey(
				newLoadFinderCacheEntry.getPrimaryKey());

		Assert.assertNull(existingLoadFinderCacheEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLoadFinderCacheEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LoadFinderCacheEntry newLoadFinderCacheEntry = _persistence.create(pk);

		newLoadFinderCacheEntry.setGroupId(RandomTestUtil.nextLong());

		newLoadFinderCacheEntry.setCompanyId(RandomTestUtil.nextLong());

		newLoadFinderCacheEntry.setUniqueName(RandomTestUtil.randomString());

		_loadFinderCacheEntries.add(
			_persistence.update(newLoadFinderCacheEntry));

		LoadFinderCacheEntry existingLoadFinderCacheEntry =
			_persistence.findByPrimaryKey(
				newLoadFinderCacheEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLoadFinderCacheEntry.getLoadFinderCacheEntryId(),
			newLoadFinderCacheEntry.getLoadFinderCacheEntryId());
		Assert.assertEquals(
			existingLoadFinderCacheEntry.getGroupId(),
			newLoadFinderCacheEntry.getGroupId());
		Assert.assertEquals(
			existingLoadFinderCacheEntry.getCompanyId(),
			newLoadFinderCacheEntry.getCompanyId());
		Assert.assertEquals(
			existingLoadFinderCacheEntry.getUniqueName(),
			newLoadFinderCacheEntry.getUniqueName());
	}

	@Test
	public void testCountByUniqueName() throws Exception {
		_persistence.countByUniqueName("");

		_persistence.countByUniqueName("null");

		_persistence.countByUniqueName((String)null);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByC_G() throws Exception {
		_persistence.countByC_G(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_G(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		LoadFinderCacheEntry existingLoadFinderCacheEntry =
			_persistence.findByPrimaryKey(
				newLoadFinderCacheEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLoadFinderCacheEntry, newLoadFinderCacheEntry);
	}

	@Test(expected = NoSuchLoadFinderCacheEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LoadFinderCacheEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LoadFinderCacheEntry", "loadFinderCacheEntryId", true, "groupId",
			true, "companyId", true, "uniqueName", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		LoadFinderCacheEntry existingLoadFinderCacheEntry =
			_persistence.fetchByPrimaryKey(
				newLoadFinderCacheEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLoadFinderCacheEntry, newLoadFinderCacheEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LoadFinderCacheEntry missingLoadFinderCacheEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLoadFinderCacheEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LoadFinderCacheEntry newLoadFinderCacheEntry1 =
			addLoadFinderCacheEntry();
		LoadFinderCacheEntry newLoadFinderCacheEntry2 =
			addLoadFinderCacheEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLoadFinderCacheEntry1.getPrimaryKey());
		primaryKeys.add(newLoadFinderCacheEntry2.getPrimaryKey());

		Map<Serializable, LoadFinderCacheEntry> loadFinderCacheEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, loadFinderCacheEntries.size());
		Assert.assertEquals(
			newLoadFinderCacheEntry1,
			loadFinderCacheEntries.get(
				newLoadFinderCacheEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLoadFinderCacheEntry2,
			loadFinderCacheEntries.get(
				newLoadFinderCacheEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LoadFinderCacheEntry> loadFinderCacheEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(loadFinderCacheEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLoadFinderCacheEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LoadFinderCacheEntry> loadFinderCacheEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, loadFinderCacheEntries.size());
		Assert.assertEquals(
			newLoadFinderCacheEntry,
			loadFinderCacheEntries.get(
				newLoadFinderCacheEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LoadFinderCacheEntry> loadFinderCacheEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(loadFinderCacheEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLoadFinderCacheEntry.getPrimaryKey());

		Map<Serializable, LoadFinderCacheEntry> loadFinderCacheEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, loadFinderCacheEntries.size());
		Assert.assertEquals(
			newLoadFinderCacheEntry,
			loadFinderCacheEntries.get(
				newLoadFinderCacheEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LoadFinderCacheEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LoadFinderCacheEntry>() {

				@Override
				public void performAction(
					LoadFinderCacheEntry loadFinderCacheEntry) {

					Assert.assertNotNull(loadFinderCacheEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LoadFinderCacheEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"loadFinderCacheEntryId",
				newLoadFinderCacheEntry.getLoadFinderCacheEntryId()));

		List<LoadFinderCacheEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LoadFinderCacheEntry existingLoadFinderCacheEntry = result.get(0);

		Assert.assertEquals(
			existingLoadFinderCacheEntry, newLoadFinderCacheEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LoadFinderCacheEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"loadFinderCacheEntryId", RandomTestUtil.nextLong()));

		List<LoadFinderCacheEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LoadFinderCacheEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("loadFinderCacheEntryId"));

		Object newLoadFinderCacheEntryId =
			newLoadFinderCacheEntry.getLoadFinderCacheEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"loadFinderCacheEntryId",
				new Object[] {newLoadFinderCacheEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLoadFinderCacheEntryId = result.get(0);

		Assert.assertEquals(
			existingLoadFinderCacheEntryId, newLoadFinderCacheEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LoadFinderCacheEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("loadFinderCacheEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"loadFinderCacheEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newLoadFinderCacheEntry.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		LoadFinderCacheEntry newLoadFinderCacheEntry =
			addLoadFinderCacheEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LoadFinderCacheEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"loadFinderCacheEntryId",
				newLoadFinderCacheEntry.getLoadFinderCacheEntryId()));

		List<LoadFinderCacheEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		LoadFinderCacheEntry loadFinderCacheEntry) {

		Assert.assertEquals(
			loadFinderCacheEntry.getUniqueName(),
			ReflectionTestUtil.invoke(
				loadFinderCacheEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uniqueName"));
	}

	protected LoadFinderCacheEntry addLoadFinderCacheEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LoadFinderCacheEntry loadFinderCacheEntry = _persistence.create(pk);

		loadFinderCacheEntry.setGroupId(RandomTestUtil.nextLong());

		loadFinderCacheEntry.setCompanyId(RandomTestUtil.nextLong());

		loadFinderCacheEntry.setUniqueName(RandomTestUtil.randomString());

		_loadFinderCacheEntries.add(_persistence.update(loadFinderCacheEntry));

		return loadFinderCacheEntry;
	}

	private List<LoadFinderCacheEntry> _loadFinderCacheEntries =
		new ArrayList<LoadFinderCacheEntry>();
	private LoadFinderCacheEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}