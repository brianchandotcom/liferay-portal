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

package com.liferay.dataset.view.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dataset.view.exception.NoSuchActiveEntryException;
import com.liferay.dataset.view.model.DatasetViewActiveEntry;
import com.liferay.dataset.view.service.DatasetViewActiveEntryLocalServiceUtil;
import com.liferay.dataset.view.service.persistence.DatasetViewActiveEntryPersistence;
import com.liferay.dataset.view.service.persistence.DatasetViewActiveEntryUtil;
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
public class DatasetViewActiveEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.dataset.view.service"));

	@Before
	public void setUp() {
		_persistence = DatasetViewActiveEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DatasetViewActiveEntry> iterator =
			_datasetViewActiveEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewActiveEntry datasetViewActiveEntry = _persistence.create(pk);

		Assert.assertNotNull(datasetViewActiveEntry);

		Assert.assertEquals(datasetViewActiveEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		_persistence.remove(newDatasetViewActiveEntry);

		DatasetViewActiveEntry existingDatasetViewActiveEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetViewActiveEntry.getPrimaryKey());

		Assert.assertNull(existingDatasetViewActiveEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDatasetViewActiveEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewActiveEntry newDatasetViewActiveEntry = _persistence.create(
			pk);

		newDatasetViewActiveEntry.setMvccVersion(RandomTestUtil.nextLong());

		newDatasetViewActiveEntry.setUuid(RandomTestUtil.randomString());

		newDatasetViewActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		newDatasetViewActiveEntry.setUserId(RandomTestUtil.nextLong());

		newDatasetViewActiveEntry.setUserName(RandomTestUtil.randomString());

		newDatasetViewActiveEntry.setCreateDate(RandomTestUtil.nextDate());

		newDatasetViewActiveEntry.setModifiedDate(RandomTestUtil.nextDate());

		newDatasetViewActiveEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		newDatasetViewActiveEntry.setDatasetViewStateEntryId(
			RandomTestUtil.nextLong());

		newDatasetViewActiveEntry.setPlid(RandomTestUtil.nextLong());

		newDatasetViewActiveEntry.setPortletId(RandomTestUtil.randomString());

		_datasetViewActiveEntries.add(
			_persistence.update(newDatasetViewActiveEntry));

		DatasetViewActiveEntry existingDatasetViewActiveEntry =
			_persistence.findByPrimaryKey(
				newDatasetViewActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewActiveEntry.getMvccVersion(),
			newDatasetViewActiveEntry.getMvccVersion());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getUuid(),
			newDatasetViewActiveEntry.getUuid());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getDatasetViewActiveEntryId(),
			newDatasetViewActiveEntry.getDatasetViewActiveEntryId());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getCompanyId(),
			newDatasetViewActiveEntry.getCompanyId());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getUserId(),
			newDatasetViewActiveEntry.getUserId());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getUserName(),
			newDatasetViewActiveEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetViewActiveEntry.getCreateDate()),
			Time.getShortTimestamp(newDatasetViewActiveEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetViewActiveEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newDatasetViewActiveEntry.getModifiedDate()));
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getDatasetDisplayId(),
			newDatasetViewActiveEntry.getDatasetDisplayId());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getDatasetViewStateEntryId(),
			newDatasetViewActiveEntry.getDatasetViewStateEntryId());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getPlid(),
			newDatasetViewActiveEntry.getPlid());
		Assert.assertEquals(
			existingDatasetViewActiveEntry.getPortletId(),
			newDatasetViewActiveEntry.getPortletId());
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
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		DatasetViewActiveEntry existingDatasetViewActiveEntry =
			_persistence.findByPrimaryKey(
				newDatasetViewActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewActiveEntry, newDatasetViewActiveEntry);
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

	protected OrderByComparator<DatasetViewActiveEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DatasetViewActiveEntry", "mvccVersion", true, "uuid", true,
			"datasetViewActiveEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"datasetDisplayId", true, "datasetViewStateEntryId", true, "plid",
			true, "portletId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		DatasetViewActiveEntry existingDatasetViewActiveEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetViewActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewActiveEntry, newDatasetViewActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewActiveEntry missingDatasetViewActiveEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDatasetViewActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DatasetViewActiveEntry newDatasetViewActiveEntry1 =
			addDatasetViewActiveEntry();
		DatasetViewActiveEntry newDatasetViewActiveEntry2 =
			addDatasetViewActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewActiveEntry1.getPrimaryKey());
		primaryKeys.add(newDatasetViewActiveEntry2.getPrimaryKey());

		Map<Serializable, DatasetViewActiveEntry> datasetViewActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, datasetViewActiveEntries.size());
		Assert.assertEquals(
			newDatasetViewActiveEntry1,
			datasetViewActiveEntries.get(
				newDatasetViewActiveEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newDatasetViewActiveEntry2,
			datasetViewActiveEntries.get(
				newDatasetViewActiveEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DatasetViewActiveEntry> datasetViewActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetViewActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewActiveEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DatasetViewActiveEntry> datasetViewActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetViewActiveEntries.size());
		Assert.assertEquals(
			newDatasetViewActiveEntry,
			datasetViewActiveEntries.get(
				newDatasetViewActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DatasetViewActiveEntry> datasetViewActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetViewActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewActiveEntry.getPrimaryKey());

		Map<Serializable, DatasetViewActiveEntry> datasetViewActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetViewActiveEntries.size());
		Assert.assertEquals(
			newDatasetViewActiveEntry,
			datasetViewActiveEntries.get(
				newDatasetViewActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DatasetViewActiveEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DatasetViewActiveEntry>() {

				@Override
				public void performAction(
					DatasetViewActiveEntry datasetViewActiveEntry) {

					Assert.assertNotNull(datasetViewActiveEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetViewActiveEntryId",
				newDatasetViewActiveEntry.getDatasetViewActiveEntryId()));

		List<DatasetViewActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DatasetViewActiveEntry existingDatasetViewActiveEntry = result.get(0);

		Assert.assertEquals(
			existingDatasetViewActiveEntry, newDatasetViewActiveEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetViewActiveEntryId", RandomTestUtil.nextLong()));

		List<DatasetViewActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetViewActiveEntryId"));

		Object newDatasetViewActiveEntryId =
			newDatasetViewActiveEntry.getDatasetViewActiveEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetViewActiveEntryId",
				new Object[] {newDatasetViewActiveEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDatasetViewActiveEntryId = result.get(0);

		Assert.assertEquals(
			existingDatasetViewActiveEntryId, newDatasetViewActiveEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetViewActiveEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetViewActiveEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newDatasetViewActiveEntry.getPrimaryKey()));
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

		DatasetViewActiveEntry newDatasetViewActiveEntry =
			addDatasetViewActiveEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetViewActiveEntryId",
				newDatasetViewActiveEntry.getDatasetViewActiveEntryId()));

		List<DatasetViewActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		DatasetViewActiveEntry datasetViewActiveEntry) {

		Assert.assertEquals(
			Long.valueOf(datasetViewActiveEntry.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				datasetViewActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "userId"));
		Assert.assertEquals(
			datasetViewActiveEntry.getDatasetDisplayId(),
			ReflectionTestUtil.invoke(
				datasetViewActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "datasetDisplayId"));
		Assert.assertEquals(
			Long.valueOf(datasetViewActiveEntry.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				datasetViewActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "plid"));
		Assert.assertEquals(
			datasetViewActiveEntry.getPortletId(),
			ReflectionTestUtil.invoke(
				datasetViewActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "portletId"));
	}

	protected DatasetViewActiveEntry addDatasetViewActiveEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		DatasetViewActiveEntry datasetViewActiveEntry = _persistence.create(pk);

		datasetViewActiveEntry.setMvccVersion(RandomTestUtil.nextLong());

		datasetViewActiveEntry.setUuid(RandomTestUtil.randomString());

		datasetViewActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		datasetViewActiveEntry.setUserId(RandomTestUtil.nextLong());

		datasetViewActiveEntry.setUserName(RandomTestUtil.randomString());

		datasetViewActiveEntry.setCreateDate(RandomTestUtil.nextDate());

		datasetViewActiveEntry.setModifiedDate(RandomTestUtil.nextDate());

		datasetViewActiveEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		datasetViewActiveEntry.setDatasetViewStateEntryId(
			RandomTestUtil.nextLong());

		datasetViewActiveEntry.setPlid(RandomTestUtil.nextLong());

		datasetViewActiveEntry.setPortletId(RandomTestUtil.randomString());

		_datasetViewActiveEntries.add(
			_persistence.update(datasetViewActiveEntry));

		return datasetViewActiveEntry;
	}

	private List<DatasetViewActiveEntry> _datasetViewActiveEntries =
		new ArrayList<DatasetViewActiveEntry>();
	private DatasetViewActiveEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}