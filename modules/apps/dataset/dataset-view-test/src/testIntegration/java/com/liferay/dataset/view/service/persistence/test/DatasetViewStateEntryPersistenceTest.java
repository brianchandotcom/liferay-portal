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
import com.liferay.dataset.view.exception.NoSuchStateEntryException;
import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.dataset.view.service.DatasetViewStateEntryLocalServiceUtil;
import com.liferay.dataset.view.service.persistence.DatasetViewStateEntryPersistence;
import com.liferay.dataset.view.service.persistence.DatasetViewStateEntryUtil;
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
public class DatasetViewStateEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.dataset.view.service"));

	@Before
	public void setUp() {
		_persistence = DatasetViewStateEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DatasetViewStateEntry> iterator =
			_datasetViewStateEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewStateEntry datasetViewStateEntry = _persistence.create(pk);

		Assert.assertNotNull(datasetViewStateEntry);

		Assert.assertEquals(datasetViewStateEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		_persistence.remove(newDatasetViewStateEntry);

		DatasetViewStateEntry existingDatasetViewStateEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetViewStateEntry.getPrimaryKey());

		Assert.assertNull(existingDatasetViewStateEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDatasetViewStateEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewStateEntry newDatasetViewStateEntry = _persistence.create(
			pk);

		newDatasetViewStateEntry.setMvccVersion(RandomTestUtil.nextLong());

		newDatasetViewStateEntry.setUuid(RandomTestUtil.randomString());

		newDatasetViewStateEntry.setCompanyId(RandomTestUtil.nextLong());

		newDatasetViewStateEntry.setUserId(RandomTestUtil.nextLong());

		newDatasetViewStateEntry.setUserName(RandomTestUtil.randomString());

		newDatasetViewStateEntry.setCreateDate(RandomTestUtil.nextDate());

		newDatasetViewStateEntry.setModifiedDate(RandomTestUtil.nextDate());

		newDatasetViewStateEntry.setViewState(RandomTestUtil.randomString());

		_datasetViewStateEntries.add(
			_persistence.update(newDatasetViewStateEntry));

		DatasetViewStateEntry existingDatasetViewStateEntry =
			_persistence.findByPrimaryKey(
				newDatasetViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewStateEntry.getMvccVersion(),
			newDatasetViewStateEntry.getMvccVersion());
		Assert.assertEquals(
			existingDatasetViewStateEntry.getUuid(),
			newDatasetViewStateEntry.getUuid());
		Assert.assertEquals(
			existingDatasetViewStateEntry.getDatasetViewStateEntryId(),
			newDatasetViewStateEntry.getDatasetViewStateEntryId());
		Assert.assertEquals(
			existingDatasetViewStateEntry.getCompanyId(),
			newDatasetViewStateEntry.getCompanyId());
		Assert.assertEquals(
			existingDatasetViewStateEntry.getUserId(),
			newDatasetViewStateEntry.getUserId());
		Assert.assertEquals(
			existingDatasetViewStateEntry.getUserName(),
			newDatasetViewStateEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetViewStateEntry.getCreateDate()),
			Time.getShortTimestamp(newDatasetViewStateEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetViewStateEntry.getModifiedDate()),
			Time.getShortTimestamp(newDatasetViewStateEntry.getModifiedDate()));
		Assert.assertEquals(
			existingDatasetViewStateEntry.getViewState(),
			newDatasetViewStateEntry.getViewState());
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
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		DatasetViewStateEntry existingDatasetViewStateEntry =
			_persistence.findByPrimaryKey(
				newDatasetViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewStateEntry, newDatasetViewStateEntry);
	}

	@Test(expected = NoSuchStateEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DatasetViewStateEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DatasetViewStateEntry", "mvccVersion", true, "uuid", true,
			"datasetViewStateEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		DatasetViewStateEntry existingDatasetViewStateEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetViewStateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetViewStateEntry, newDatasetViewStateEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetViewStateEntry missingDatasetViewStateEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDatasetViewStateEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DatasetViewStateEntry newDatasetViewStateEntry1 =
			addDatasetViewStateEntry();
		DatasetViewStateEntry newDatasetViewStateEntry2 =
			addDatasetViewStateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewStateEntry1.getPrimaryKey());
		primaryKeys.add(newDatasetViewStateEntry2.getPrimaryKey());

		Map<Serializable, DatasetViewStateEntry> datasetViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, datasetViewStateEntries.size());
		Assert.assertEquals(
			newDatasetViewStateEntry1,
			datasetViewStateEntries.get(
				newDatasetViewStateEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newDatasetViewStateEntry2,
			datasetViewStateEntries.get(
				newDatasetViewStateEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DatasetViewStateEntry> datasetViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetViewStateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewStateEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DatasetViewStateEntry> datasetViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetViewStateEntries.size());
		Assert.assertEquals(
			newDatasetViewStateEntry,
			datasetViewStateEntries.get(
				newDatasetViewStateEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DatasetViewStateEntry> datasetViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetViewStateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetViewStateEntry.getPrimaryKey());

		Map<Serializable, DatasetViewStateEntry> datasetViewStateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetViewStateEntries.size());
		Assert.assertEquals(
			newDatasetViewStateEntry,
			datasetViewStateEntries.get(
				newDatasetViewStateEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DatasetViewStateEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DatasetViewStateEntry>() {

				@Override
				public void performAction(
					DatasetViewStateEntry datasetViewStateEntry) {

					Assert.assertNotNull(datasetViewStateEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetViewStateEntryId",
				newDatasetViewStateEntry.getDatasetViewStateEntryId()));

		List<DatasetViewStateEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DatasetViewStateEntry existingDatasetViewStateEntry = result.get(0);

		Assert.assertEquals(
			existingDatasetViewStateEntry, newDatasetViewStateEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetViewStateEntryId", RandomTestUtil.nextLong()));

		List<DatasetViewStateEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DatasetViewStateEntry newDatasetViewStateEntry =
			addDatasetViewStateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetViewStateEntryId"));

		Object newDatasetViewStateEntryId =
			newDatasetViewStateEntry.getDatasetViewStateEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetViewStateEntryId",
				new Object[] {newDatasetViewStateEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDatasetViewStateEntryId = result.get(0);

		Assert.assertEquals(
			existingDatasetViewStateEntryId, newDatasetViewStateEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetViewStateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetViewStateEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetViewStateEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected DatasetViewStateEntry addDatasetViewStateEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		DatasetViewStateEntry datasetViewStateEntry = _persistence.create(pk);

		datasetViewStateEntry.setMvccVersion(RandomTestUtil.nextLong());

		datasetViewStateEntry.setUuid(RandomTestUtil.randomString());

		datasetViewStateEntry.setCompanyId(RandomTestUtil.nextLong());

		datasetViewStateEntry.setUserId(RandomTestUtil.nextLong());

		datasetViewStateEntry.setUserName(RandomTestUtil.randomString());

		datasetViewStateEntry.setCreateDate(RandomTestUtil.nextDate());

		datasetViewStateEntry.setModifiedDate(RandomTestUtil.nextDate());

		datasetViewStateEntry.setViewState(RandomTestUtil.randomString());

		_datasetViewStateEntries.add(
			_persistence.update(datasetViewStateEntry));

		return datasetViewStateEntry;
	}

	private List<DatasetViewStateEntry> _datasetViewStateEntries =
		new ArrayList<DatasetViewStateEntry>();
	private DatasetViewStateEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}