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
import com.liferay.frontend.view.state.exception.NoSuchFrontendDataSetEntryException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
import com.liferay.frontend.view.state.service.FVSFrontendDataSetEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetEntryUtil;
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
public class FVSFrontendDataSetEntryPersistenceTest {

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
		_persistence = FVSFrontendDataSetEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FVSFrontendDataSetEntry> iterator =
			_fvsFrontendDataSetEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = _persistence.create(
			pk);

		Assert.assertNotNull(fvsFrontendDataSetEntry);

		Assert.assertEquals(fvsFrontendDataSetEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		_persistence.remove(newFVSFrontendDataSetEntry);

		FVSFrontendDataSetEntry existingFVSFrontendDataSetEntry =
			_persistence.fetchByPrimaryKey(
				newFVSFrontendDataSetEntry.getPrimaryKey());

		Assert.assertNull(existingFVSFrontendDataSetEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFVSFrontendDataSetEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			_persistence.create(pk);

		newFVSFrontendDataSetEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFVSFrontendDataSetEntry.setUuid(RandomTestUtil.randomString());

		newFVSFrontendDataSetEntry.setCompanyId(RandomTestUtil.nextLong());

		newFVSFrontendDataSetEntry.setUserId(RandomTestUtil.nextLong());

		newFVSFrontendDataSetEntry.setUserName(RandomTestUtil.randomString());

		newFVSFrontendDataSetEntry.setCreateDate(RandomTestUtil.nextDate());

		newFVSFrontendDataSetEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFVSFrontendDataSetEntry.setFvsEntryId(RandomTestUtil.nextLong());

		newFVSFrontendDataSetEntry.setFdsName(RandomTestUtil.randomString());

		newFVSFrontendDataSetEntry.setName(RandomTestUtil.randomString());

		newFVSFrontendDataSetEntry.setPlid(RandomTestUtil.nextLong());

		newFVSFrontendDataSetEntry.setPortletId(RandomTestUtil.randomString());

		_fvsFrontendDataSetEntries.add(
			_persistence.update(newFVSFrontendDataSetEntry));

		FVSFrontendDataSetEntry existingFVSFrontendDataSetEntry =
			_persistence.findByPrimaryKey(
				newFVSFrontendDataSetEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getMvccVersion(),
			newFVSFrontendDataSetEntry.getMvccVersion());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getUuid(),
			newFVSFrontendDataSetEntry.getUuid());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getFvsFrontendDataSetEntryId(),
			newFVSFrontendDataSetEntry.getFvsFrontendDataSetEntryId());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getCompanyId(),
			newFVSFrontendDataSetEntry.getCompanyId());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getUserId(),
			newFVSFrontendDataSetEntry.getUserId());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getUserName(),
			newFVSFrontendDataSetEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFVSFrontendDataSetEntry.getCreateDate()),
			Time.getShortTimestamp(newFVSFrontendDataSetEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFVSFrontendDataSetEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newFVSFrontendDataSetEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getFvsEntryId(),
			newFVSFrontendDataSetEntry.getFvsEntryId());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getFdsName(),
			newFVSFrontendDataSetEntry.getFdsName());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getName(),
			newFVSFrontendDataSetEntry.getName());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getPlid(),
			newFVSFrontendDataSetEntry.getPlid());
		Assert.assertEquals(
			existingFVSFrontendDataSetEntry.getPortletId(),
			newFVSFrontendDataSetEntry.getPortletId());
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
	public void testCountByU_F_P_P() throws Exception {
		_persistence.countByU_F_P_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong(), "");

		_persistence.countByU_F_P_P(0L, "null", 0L, "null");

		_persistence.countByU_F_P_P(0L, (String)null, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		FVSFrontendDataSetEntry existingFVSFrontendDataSetEntry =
			_persistence.findByPrimaryKey(
				newFVSFrontendDataSetEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSFrontendDataSetEntry, newFVSFrontendDataSetEntry);
	}

	@Test(expected = NoSuchFrontendDataSetEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FVSFrontendDataSetEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"FVSFrontendDataSetEntry", "mvccVersion", true, "uuid", true,
			"fvsFrontendDataSetEntryId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"fvsEntryId", true, "fdsName", true, "name", true, "plid", true,
			"portletId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		FVSFrontendDataSetEntry existingFVSFrontendDataSetEntry =
			_persistence.fetchByPrimaryKey(
				newFVSFrontendDataSetEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSFrontendDataSetEntry, newFVSFrontendDataSetEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSetEntry missingFVSFrontendDataSetEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFVSFrontendDataSetEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry1 =
			addFVSFrontendDataSetEntry();
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry2 =
			addFVSFrontendDataSetEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSetEntry1.getPrimaryKey());
		primaryKeys.add(newFVSFrontendDataSetEntry2.getPrimaryKey());

		Map<Serializable, FVSFrontendDataSetEntry> fvsFrontendDataSetEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fvsFrontendDataSetEntries.size());
		Assert.assertEquals(
			newFVSFrontendDataSetEntry1,
			fvsFrontendDataSetEntries.get(
				newFVSFrontendDataSetEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFVSFrontendDataSetEntry2,
			fvsFrontendDataSetEntries.get(
				newFVSFrontendDataSetEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FVSFrontendDataSetEntry> fvsFrontendDataSetEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsFrontendDataSetEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSetEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FVSFrontendDataSetEntry> fvsFrontendDataSetEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsFrontendDataSetEntries.size());
		Assert.assertEquals(
			newFVSFrontendDataSetEntry,
			fvsFrontendDataSetEntries.get(
				newFVSFrontendDataSetEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FVSFrontendDataSetEntry> fvsFrontendDataSetEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsFrontendDataSetEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSetEntry.getPrimaryKey());

		Map<Serializable, FVSFrontendDataSetEntry> fvsFrontendDataSetEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsFrontendDataSetEntries.size());
		Assert.assertEquals(
			newFVSFrontendDataSetEntry,
			fvsFrontendDataSetEntries.get(
				newFVSFrontendDataSetEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FVSFrontendDataSetEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FVSFrontendDataSetEntry>() {

				@Override
				public void performAction(
					FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

					Assert.assertNotNull(fvsFrontendDataSetEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSetEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsFrontendDataSetEntryId",
				newFVSFrontendDataSetEntry.getFvsFrontendDataSetEntryId()));

		List<FVSFrontendDataSetEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FVSFrontendDataSetEntry existingFVSFrontendDataSetEntry = result.get(0);

		Assert.assertEquals(
			existingFVSFrontendDataSetEntry, newFVSFrontendDataSetEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSetEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsFrontendDataSetEntryId", RandomTestUtil.nextLong()));

		List<FVSFrontendDataSetEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FVSFrontendDataSetEntry newFVSFrontendDataSetEntry =
			addFVSFrontendDataSetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSetEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsFrontendDataSetEntryId"));

		Object newFvsFrontendDataSetEntryId =
			newFVSFrontendDataSetEntry.getFvsFrontendDataSetEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsFrontendDataSetEntryId",
				new Object[] {newFvsFrontendDataSetEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFvsFrontendDataSetEntryId = result.get(0);

		Assert.assertEquals(
			existingFvsFrontendDataSetEntryId, newFvsFrontendDataSetEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSetEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsFrontendDataSetEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsFrontendDataSetEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FVSFrontendDataSetEntry addFVSFrontendDataSetEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = _persistence.create(
			pk);

		fvsFrontendDataSetEntry.setMvccVersion(RandomTestUtil.nextLong());

		fvsFrontendDataSetEntry.setUuid(RandomTestUtil.randomString());

		fvsFrontendDataSetEntry.setCompanyId(RandomTestUtil.nextLong());

		fvsFrontendDataSetEntry.setUserId(RandomTestUtil.nextLong());

		fvsFrontendDataSetEntry.setUserName(RandomTestUtil.randomString());

		fvsFrontendDataSetEntry.setCreateDate(RandomTestUtil.nextDate());

		fvsFrontendDataSetEntry.setModifiedDate(RandomTestUtil.nextDate());

		fvsFrontendDataSetEntry.setFvsEntryId(RandomTestUtil.nextLong());

		fvsFrontendDataSetEntry.setFdsName(RandomTestUtil.randomString());

		fvsFrontendDataSetEntry.setName(RandomTestUtil.randomString());

		fvsFrontendDataSetEntry.setPlid(RandomTestUtil.nextLong());

		fvsFrontendDataSetEntry.setPortletId(RandomTestUtil.randomString());

		_fvsFrontendDataSetEntries.add(
			_persistence.update(fvsFrontendDataSetEntry));

		return fvsFrontendDataSetEntry;
	}

	private List<FVSFrontendDataSetEntry> _fvsFrontendDataSetEntries =
		new ArrayList<FVSFrontendDataSetEntry>();
	private FVSFrontendDataSetEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}