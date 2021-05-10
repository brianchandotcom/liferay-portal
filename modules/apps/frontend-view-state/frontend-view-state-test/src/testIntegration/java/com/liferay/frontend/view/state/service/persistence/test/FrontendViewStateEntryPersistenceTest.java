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
import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
import com.liferay.frontend.view.state.service.FrontendViewStateEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FrontendViewStateEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FrontendViewStateEntryUtil;
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
public class FrontendViewStateEntryPersistenceTest {

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
		_persistence = FrontendViewStateEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FrontendViewStateEntry> iterator =
			_frontendViewStateEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateEntry frontendViewStateEntry = _persistence.create(pk);

		Assert.assertNotNull(frontendViewStateEntry);

		Assert.assertEquals(frontendViewStateEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		_persistence.remove(newFrontendViewStateEntry);

		FrontendViewStateEntry existingFrontendViewStateEntry =
			_persistence.fetchByPrimaryKey(
				newFrontendViewStateEntry.getPrimaryKey());

		Assert.assertNull(existingFrontendViewStateEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFrontendViewStateEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateEntry newFrontendViewStateEntry = _persistence.create(
			pk);

		newFrontendViewStateEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFrontendViewStateEntry.setUuid(RandomTestUtil.randomString());

		newFrontendViewStateEntry.setCompanyId(RandomTestUtil.nextLong());

		newFrontendViewStateEntry.setUserId(RandomTestUtil.nextLong());

		newFrontendViewStateEntry.setUserName(RandomTestUtil.randomString());

		newFrontendViewStateEntry.setCreateDate(RandomTestUtil.nextDate());

		newFrontendViewStateEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFrontendViewStateEntry.setViewState(RandomTestUtil.randomString());

		_frontendViewStateEntries.add(
			_persistence.update(newFrontendViewStateEntry));

		FrontendViewStateEntry existingFrontendViewStateEntry =
			_persistence.findByPrimaryKey(
				newFrontendViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateEntry.getMvccVersion(),
			newFrontendViewStateEntry.getMvccVersion());
		Assert.assertEquals(
			existingFrontendViewStateEntry.getUuid(),
			newFrontendViewStateEntry.getUuid());
		Assert.assertEquals(
			existingFrontendViewStateEntry.getFrontendViewStateEntryId(),
			newFrontendViewStateEntry.getFrontendViewStateEntryId());
		Assert.assertEquals(
			existingFrontendViewStateEntry.getCompanyId(),
			newFrontendViewStateEntry.getCompanyId());
		Assert.assertEquals(
			existingFrontendViewStateEntry.getUserId(),
			newFrontendViewStateEntry.getUserId());
		Assert.assertEquals(
			existingFrontendViewStateEntry.getUserName(),
			newFrontendViewStateEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFrontendViewStateEntry.getCreateDate()),
			Time.getShortTimestamp(newFrontendViewStateEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFrontendViewStateEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newFrontendViewStateEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFrontendViewStateEntry.getViewState(),
			newFrontendViewStateEntry.getViewState());
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
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		FrontendViewStateEntry existingFrontendViewStateEntry =
			_persistence.findByPrimaryKey(
				newFrontendViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateEntry, newFrontendViewStateEntry);
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

	protected OrderByComparator<FrontendViewStateEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FrontendViewStateEntry", "mvccVersion", true, "uuid", true,
			"frontendViewStateEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		FrontendViewStateEntry existingFrontendViewStateEntry =
			_persistence.fetchByPrimaryKey(
				newFrontendViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateEntry, newFrontendViewStateEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateEntry missingFrontendViewStateEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFrontendViewStateEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FrontendViewStateEntry newFrontendViewStateEntry1 =
			addFrontendViewStateEntry();
		FrontendViewStateEntry newFrontendViewStateEntry2 =
			addFrontendViewStateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateEntry1.getPrimaryKey());
		primaryKeys.add(newFrontendViewStateEntry2.getPrimaryKey());

		Map<Serializable, FrontendViewStateEntry> frontendViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, frontendViewStateEntries.size());
		Assert.assertEquals(
			newFrontendViewStateEntry1,
			frontendViewStateEntries.get(
				newFrontendViewStateEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFrontendViewStateEntry2,
			frontendViewStateEntries.get(
				newFrontendViewStateEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FrontendViewStateEntry> frontendViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(frontendViewStateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FrontendViewStateEntry> frontendViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, frontendViewStateEntries.size());
		Assert.assertEquals(
			newFrontendViewStateEntry,
			frontendViewStateEntries.get(
				newFrontendViewStateEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FrontendViewStateEntry> frontendViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(frontendViewStateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateEntry.getPrimaryKey());

		Map<Serializable, FrontendViewStateEntry> frontendViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, frontendViewStateEntries.size());
		Assert.assertEquals(
			newFrontendViewStateEntry,
			frontendViewStateEntries.get(
				newFrontendViewStateEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FrontendViewStateEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FrontendViewStateEntry>() {

				@Override
				public void performAction(
					FrontendViewStateEntry frontendViewStateEntry) {

					Assert.assertNotNull(frontendViewStateEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"frontendViewStateEntryId",
				newFrontendViewStateEntry.getFrontendViewStateEntryId()));

		List<FrontendViewStateEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FrontendViewStateEntry existingFrontendViewStateEntry = result.get(0);

		Assert.assertEquals(
			existingFrontendViewStateEntry, newFrontendViewStateEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"frontendViewStateEntryId", RandomTestUtil.nextLong()));

		List<FrontendViewStateEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FrontendViewStateEntry newFrontendViewStateEntry =
			addFrontendViewStateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("frontendViewStateEntryId"));

		Object newFrontendViewStateEntryId =
			newFrontendViewStateEntry.getFrontendViewStateEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"frontendViewStateEntryId",
				new Object[] {newFrontendViewStateEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFrontendViewStateEntryId = result.get(0);

		Assert.assertEquals(
			existingFrontendViewStateEntryId, newFrontendViewStateEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("frontendViewStateEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"frontendViewStateEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FrontendViewStateEntry addFrontendViewStateEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		FrontendViewStateEntry frontendViewStateEntry = _persistence.create(pk);

		frontendViewStateEntry.setMvccVersion(RandomTestUtil.nextLong());

		frontendViewStateEntry.setUuid(RandomTestUtil.randomString());

		frontendViewStateEntry.setCompanyId(RandomTestUtil.nextLong());

		frontendViewStateEntry.setUserId(RandomTestUtil.nextLong());

		frontendViewStateEntry.setUserName(RandomTestUtil.randomString());

		frontendViewStateEntry.setCreateDate(RandomTestUtil.nextDate());

		frontendViewStateEntry.setModifiedDate(RandomTestUtil.nextDate());

		frontendViewStateEntry.setViewState(RandomTestUtil.randomString());

		_frontendViewStateEntries.add(
			_persistence.update(frontendViewStateEntry));

		return frontendViewStateEntry;
	}

	private List<FrontendViewStateEntry> _frontendViewStateEntries =
		new ArrayList<FrontendViewStateEntry>();
	private FrontendViewStateEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}