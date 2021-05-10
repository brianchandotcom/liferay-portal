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
import com.liferay.frontend.view.state.exception.NoSuchActiveEntryException;
import com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry;
import com.liferay.frontend.view.state.service.FrontendViewStateActiveEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FrontendViewStateActiveEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FrontendViewStateActiveEntryUtil;
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
public class FrontendViewStateActiveEntryPersistenceTest {

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
		_persistence = FrontendViewStateActiveEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FrontendViewStateActiveEntry> iterator =
			_frontendViewStateActiveEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateActiveEntry frontendViewStateActiveEntry =
			_persistence.create(pk);

		Assert.assertNotNull(frontendViewStateActiveEntry);

		Assert.assertEquals(frontendViewStateActiveEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		_persistence.remove(newFrontendViewStateActiveEntry);

		FrontendViewStateActiveEntry existingFrontendViewStateActiveEntry =
			_persistence.fetchByPrimaryKey(
				newFrontendViewStateActiveEntry.getPrimaryKey());

		Assert.assertNull(existingFrontendViewStateActiveEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFrontendViewStateActiveEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			_persistence.create(pk);

		newFrontendViewStateActiveEntry.setMvccVersion(
			RandomTestUtil.nextLong());

		newFrontendViewStateActiveEntry.setUuid(RandomTestUtil.randomString());

		newFrontendViewStateActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		newFrontendViewStateActiveEntry.setUserId(RandomTestUtil.nextLong());

		newFrontendViewStateActiveEntry.setUserName(
			RandomTestUtil.randomString());

		newFrontendViewStateActiveEntry.setCreateDate(
			RandomTestUtil.nextDate());

		newFrontendViewStateActiveEntry.setModifiedDate(
			RandomTestUtil.nextDate());

		newFrontendViewStateActiveEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		newFrontendViewStateActiveEntry.setFrontendViewStateEntryId(
			RandomTestUtil.nextLong());

		newFrontendViewStateActiveEntry.setPlid(RandomTestUtil.nextLong());

		newFrontendViewStateActiveEntry.setPortletId(
			RandomTestUtil.randomString());

		_frontendViewStateActiveEntries.add(
			_persistence.update(newFrontendViewStateActiveEntry));

		FrontendViewStateActiveEntry existingFrontendViewStateActiveEntry =
			_persistence.findByPrimaryKey(
				newFrontendViewStateActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getMvccVersion(),
			newFrontendViewStateActiveEntry.getMvccVersion());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getUuid(),
			newFrontendViewStateActiveEntry.getUuid());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.
				getFrontendViewStateActiveEntryId(),
			newFrontendViewStateActiveEntry.
				getFrontendViewStateActiveEntryId());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getCompanyId(),
			newFrontendViewStateActiveEntry.getCompanyId());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getUserId(),
			newFrontendViewStateActiveEntry.getUserId());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getUserName(),
			newFrontendViewStateActiveEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFrontendViewStateActiveEntry.getCreateDate()),
			Time.getShortTimestamp(
				newFrontendViewStateActiveEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFrontendViewStateActiveEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newFrontendViewStateActiveEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getDatasetDisplayId(),
			newFrontendViewStateActiveEntry.getDatasetDisplayId());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getFrontendViewStateEntryId(),
			newFrontendViewStateActiveEntry.getFrontendViewStateEntryId());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getPlid(),
			newFrontendViewStateActiveEntry.getPlid());
		Assert.assertEquals(
			existingFrontendViewStateActiveEntry.getPortletId(),
			newFrontendViewStateActiveEntry.getPortletId());
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
	public void testCountByU_D_P_P() throws Exception {
		_persistence.countByU_D_P_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong(), "");

		_persistence.countByU_D_P_P(0L, "null", 0L, "null");

		_persistence.countByU_D_P_P(0L, (String)null, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		FrontendViewStateActiveEntry existingFrontendViewStateActiveEntry =
			_persistence.findByPrimaryKey(
				newFrontendViewStateActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateActiveEntry,
			newFrontendViewStateActiveEntry);
	}

	@Test(expected = NoSuchActiveEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FrontendViewStateActiveEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"FrontendViewStateActiveEntry", "mvccVersion", true, "uuid", true,
			"frontendViewStateActiveEntryId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"datasetDisplayId", true, "frontendViewStateEntryId", true, "plid",
			true, "portletId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		FrontendViewStateActiveEntry existingFrontendViewStateActiveEntry =
			_persistence.fetchByPrimaryKey(
				newFrontendViewStateActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFrontendViewStateActiveEntry,
			newFrontendViewStateActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FrontendViewStateActiveEntry missingFrontendViewStateActiveEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFrontendViewStateActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry1 =
			addFrontendViewStateActiveEntry();
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry2 =
			addFrontendViewStateActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateActiveEntry1.getPrimaryKey());
		primaryKeys.add(newFrontendViewStateActiveEntry2.getPrimaryKey());

		Map<Serializable, FrontendViewStateActiveEntry>
			frontendViewStateActiveEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, frontendViewStateActiveEntries.size());
		Assert.assertEquals(
			newFrontendViewStateActiveEntry1,
			frontendViewStateActiveEntries.get(
				newFrontendViewStateActiveEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFrontendViewStateActiveEntry2,
			frontendViewStateActiveEntries.get(
				newFrontendViewStateActiveEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FrontendViewStateActiveEntry>
			frontendViewStateActiveEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(frontendViewStateActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateActiveEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FrontendViewStateActiveEntry>
			frontendViewStateActiveEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, frontendViewStateActiveEntries.size());
		Assert.assertEquals(
			newFrontendViewStateActiveEntry,
			frontendViewStateActiveEntries.get(
				newFrontendViewStateActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FrontendViewStateActiveEntry>
			frontendViewStateActiveEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(frontendViewStateActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFrontendViewStateActiveEntry.getPrimaryKey());

		Map<Serializable, FrontendViewStateActiveEntry>
			frontendViewStateActiveEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, frontendViewStateActiveEntries.size());
		Assert.assertEquals(
			newFrontendViewStateActiveEntry,
			frontendViewStateActiveEntries.get(
				newFrontendViewStateActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FrontendViewStateActiveEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FrontendViewStateActiveEntry>() {

				@Override
				public void performAction(
					FrontendViewStateActiveEntry frontendViewStateActiveEntry) {

					Assert.assertNotNull(frontendViewStateActiveEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"frontendViewStateActiveEntryId",
				newFrontendViewStateActiveEntry.
					getFrontendViewStateActiveEntryId()));

		List<FrontendViewStateActiveEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FrontendViewStateActiveEntry existingFrontendViewStateActiveEntry =
			result.get(0);

		Assert.assertEquals(
			existingFrontendViewStateActiveEntry,
			newFrontendViewStateActiveEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"frontendViewStateActiveEntryId", RandomTestUtil.nextLong()));

		List<FrontendViewStateActiveEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("frontendViewStateActiveEntryId"));

		Object newFrontendViewStateActiveEntryId =
			newFrontendViewStateActiveEntry.getFrontendViewStateActiveEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"frontendViewStateActiveEntryId",
				new Object[] {newFrontendViewStateActiveEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFrontendViewStateActiveEntryId = result.get(0);

		Assert.assertEquals(
			existingFrontendViewStateActiveEntryId,
			newFrontendViewStateActiveEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("frontendViewStateActiveEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"frontendViewStateActiveEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newFrontendViewStateActiveEntry.getPrimaryKey()));
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

		FrontendViewStateActiveEntry newFrontendViewStateActiveEntry =
			addFrontendViewStateActiveEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FrontendViewStateActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"frontendViewStateActiveEntryId",
				newFrontendViewStateActiveEntry.
					getFrontendViewStateActiveEntryId()));

		List<FrontendViewStateActiveEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry) {

		Assert.assertEquals(
			Long.valueOf(frontendViewStateActiveEntry.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				frontendViewStateActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "userId"));
		Assert.assertEquals(
			frontendViewStateActiveEntry.getDatasetDisplayId(),
			ReflectionTestUtil.invoke(
				frontendViewStateActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "datasetDisplayId"));
		Assert.assertEquals(
			Long.valueOf(frontendViewStateActiveEntry.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				frontendViewStateActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "plid"));
		Assert.assertEquals(
			frontendViewStateActiveEntry.getPortletId(),
			ReflectionTestUtil.invoke(
				frontendViewStateActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "portletId"));
	}

	protected FrontendViewStateActiveEntry addFrontendViewStateActiveEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		FrontendViewStateActiveEntry frontendViewStateActiveEntry =
			_persistence.create(pk);

		frontendViewStateActiveEntry.setMvccVersion(RandomTestUtil.nextLong());

		frontendViewStateActiveEntry.setUuid(RandomTestUtil.randomString());

		frontendViewStateActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		frontendViewStateActiveEntry.setUserId(RandomTestUtil.nextLong());

		frontendViewStateActiveEntry.setUserName(RandomTestUtil.randomString());

		frontendViewStateActiveEntry.setCreateDate(RandomTestUtil.nextDate());

		frontendViewStateActiveEntry.setModifiedDate(RandomTestUtil.nextDate());

		frontendViewStateActiveEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		frontendViewStateActiveEntry.setFrontendViewStateEntryId(
			RandomTestUtil.nextLong());

		frontendViewStateActiveEntry.setPlid(RandomTestUtil.nextLong());

		frontendViewStateActiveEntry.setPortletId(
			RandomTestUtil.randomString());

		_frontendViewStateActiveEntries.add(
			_persistence.update(frontendViewStateActiveEntry));

		return frontendViewStateActiveEntry;
	}

	private List<FrontendViewStateActiveEntry> _frontendViewStateActiveEntries =
		new ArrayList<FrontendViewStateActiveEntry>();
	private FrontendViewStateActiveEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}