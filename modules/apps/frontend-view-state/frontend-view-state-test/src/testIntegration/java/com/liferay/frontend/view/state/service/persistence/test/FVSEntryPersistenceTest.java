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

package com.liferay.frontend.view.state.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.view.state.exception.NoSuchEntryException;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.FVSEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FVSEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSEntryUtil;
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
public class FVSEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.frontend.view.state.service"));

	@Before
	public void setUp() {
		_persistence = FVSEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FVSEntry> iterator = _fvsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSEntry fvsEntry = _persistence.create(pk);

		Assert.assertNotNull(fvsEntry);

		Assert.assertEquals(fvsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		_persistence.remove(newFVSEntry);

		FVSEntry existingFVSEntry = _persistence.fetchByPrimaryKey(
			newFVSEntry.getPrimaryKey());

		Assert.assertNull(existingFVSEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFVSEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSEntry newFVSEntry = _persistence.create(pk);

		newFVSEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFVSEntry.setUuid(RandomTestUtil.randomString());

		newFVSEntry.setCompanyId(RandomTestUtil.nextLong());

		newFVSEntry.setUserId(RandomTestUtil.nextLong());

		newFVSEntry.setUserName(RandomTestUtil.randomString());

		newFVSEntry.setCreateDate(RandomTestUtil.nextDate());

		newFVSEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFVSEntry.setViewState(RandomTestUtil.randomString());

		_fvsEntries.add(_persistence.update(newFVSEntry));

		FVSEntry existingFVSEntry = _persistence.findByPrimaryKey(
			newFVSEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSEntry.getMvccVersion(), newFVSEntry.getMvccVersion());
		Assert.assertEquals(existingFVSEntry.getUuid(), newFVSEntry.getUuid());
		Assert.assertEquals(
			existingFVSEntry.getFvsEntryId(), newFVSEntry.getFvsEntryId());
		Assert.assertEquals(
			existingFVSEntry.getCompanyId(), newFVSEntry.getCompanyId());
		Assert.assertEquals(
			existingFVSEntry.getUserId(), newFVSEntry.getUserId());
		Assert.assertEquals(
			existingFVSEntry.getUserName(), newFVSEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSEntry.getCreateDate()),
			Time.getShortTimestamp(newFVSEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSEntry.getModifiedDate()),
			Time.getShortTimestamp(newFVSEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFVSEntry.getViewState(), newFVSEntry.getViewState());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		FVSEntry existingFVSEntry = _persistence.findByPrimaryKey(
			newFVSEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSEntry, newFVSEntry);
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

	protected OrderByComparator<FVSEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FVSEntry", "mvccVersion", true, "uuid", true, "fvsEntryId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		FVSEntry existingFVSEntry = _persistence.fetchByPrimaryKey(
			newFVSEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSEntry, newFVSEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSEntry missingFVSEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFVSEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FVSEntry newFVSEntry1 = addFVSEntry();
		FVSEntry newFVSEntry2 = addFVSEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSEntry1.getPrimaryKey());
		primaryKeys.add(newFVSEntry2.getPrimaryKey());

		Map<Serializable, FVSEntry> fvsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fvsEntries.size());
		Assert.assertEquals(
			newFVSEntry1, fvsEntries.get(newFVSEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFVSEntry2, fvsEntries.get(newFVSEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FVSEntry> fvsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FVSEntry newFVSEntry = addFVSEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FVSEntry> fvsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsEntries.size());
		Assert.assertEquals(
			newFVSEntry, fvsEntries.get(newFVSEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FVSEntry> fvsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSEntry.getPrimaryKey());

		Map<Serializable, FVSEntry> fvsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsEntries.size());
		Assert.assertEquals(
			newFVSEntry, fvsEntries.get(newFVSEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FVSEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FVSEntry>() {

				@Override
				public void performAction(FVSEntry fvsEntry) {
					Assert.assertNotNull(fvsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsEntryId", newFVSEntry.getFvsEntryId()));

		List<FVSEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FVSEntry existingFVSEntry = result.get(0);

		Assert.assertEquals(existingFVSEntry, newFVSEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsEntryId", RandomTestUtil.nextLong()));

		List<FVSEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FVSEntry newFVSEntry = addFVSEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsEntryId"));

		Object newFvsEntryId = newFVSEntry.getFvsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsEntryId", new Object[] {newFvsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFvsEntryId = result.get(0);

		Assert.assertEquals(existingFvsEntryId, newFvsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FVSEntry addFVSEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSEntry fvsEntry = _persistence.create(pk);

		fvsEntry.setMvccVersion(RandomTestUtil.nextLong());

		fvsEntry.setUuid(RandomTestUtil.randomString());

		fvsEntry.setCompanyId(RandomTestUtil.nextLong());

		fvsEntry.setUserId(RandomTestUtil.nextLong());

		fvsEntry.setUserName(RandomTestUtil.randomString());

		fvsEntry.setCreateDate(RandomTestUtil.nextDate());

		fvsEntry.setModifiedDate(RandomTestUtil.nextDate());

		fvsEntry.setViewState(RandomTestUtil.randomString());

		_fvsEntries.add(_persistence.update(fvsEntry));

		return fvsEntry;
	}

	private List<FVSEntry> _fvsEntries = new ArrayList<FVSEntry>();
	private FVSEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}