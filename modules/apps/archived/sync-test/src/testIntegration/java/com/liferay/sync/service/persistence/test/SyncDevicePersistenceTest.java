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

package com.liferay.sync.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.sync.exception.NoSuchDeviceException;
import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDeviceLocalServiceUtil;
import com.liferay.sync.service.persistence.SyncDevicePersistence;
import com.liferay.sync.service.persistence.SyncDeviceUtil;

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
public class SyncDevicePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.sync.service"));

	@Before
	public void setUp() {
		_persistence = SyncDeviceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SyncDevice> iterator = _syncDevices.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDevice syncDevice = _persistence.create(pk);

		Assert.assertNotNull(syncDevice);

		Assert.assertEquals(syncDevice.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		_persistence.remove(newSyncDevice);

		SyncDevice existingSyncDevice = _persistence.fetchByPrimaryKey(
			newSyncDevice.getPrimaryKey());

		Assert.assertNull(existingSyncDevice);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSyncDevice();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDevice newSyncDevice = _persistence.create(pk);

		newSyncDevice.setUuid(RandomTestUtil.randomString());

		newSyncDevice.setCompanyId(RandomTestUtil.nextLong());

		newSyncDevice.setUserId(RandomTestUtil.nextLong());

		newSyncDevice.setUserName(RandomTestUtil.randomString());

		newSyncDevice.setCreateDate(RandomTestUtil.nextDate());

		newSyncDevice.setModifiedDate(RandomTestUtil.nextDate());

		newSyncDevice.setType(RandomTestUtil.randomString());

		newSyncDevice.setBuildNumber(RandomTestUtil.nextLong());

		newSyncDevice.setFeatureSet(RandomTestUtil.nextInt());

		newSyncDevice.setHostname(RandomTestUtil.randomString());

		newSyncDevice.setStatus(RandomTestUtil.nextInt());

		_syncDevices.add(_persistence.update(newSyncDevice));

		SyncDevice existingSyncDevice = _persistence.findByPrimaryKey(
			newSyncDevice.getPrimaryKey());

		Assert.assertEquals(
			existingSyncDevice.getUuid(), newSyncDevice.getUuid());
		Assert.assertEquals(
			existingSyncDevice.getSyncDeviceId(),
			newSyncDevice.getSyncDeviceId());
		Assert.assertEquals(
			existingSyncDevice.getCompanyId(), newSyncDevice.getCompanyId());
		Assert.assertEquals(
			existingSyncDevice.getUserId(), newSyncDevice.getUserId());
		Assert.assertEquals(
			existingSyncDevice.getUserName(), newSyncDevice.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSyncDevice.getCreateDate()),
			Time.getShortTimestamp(newSyncDevice.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSyncDevice.getModifiedDate()),
			Time.getShortTimestamp(newSyncDevice.getModifiedDate()));
		Assert.assertEquals(
			existingSyncDevice.getType(), newSyncDevice.getType());
		Assert.assertEquals(
			existingSyncDevice.getBuildNumber(),
			newSyncDevice.getBuildNumber());
		Assert.assertEquals(
			existingSyncDevice.getFeatureSet(), newSyncDevice.getFeatureSet());
		Assert.assertEquals(
			existingSyncDevice.getHostname(), newSyncDevice.getHostname());
		Assert.assertEquals(
			existingSyncDevice.getStatus(), newSyncDevice.getStatus());
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
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByC_U() throws Exception {
		_persistence.countByC_U(RandomTestUtil.nextLong(), "");

		_persistence.countByC_U(0L, "null");

		_persistence.countByC_U(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		SyncDevice existingSyncDevice = _persistence.findByPrimaryKey(
			newSyncDevice.getPrimaryKey());

		Assert.assertEquals(existingSyncDevice, newSyncDevice);
	}

	@Test(expected = NoSuchDeviceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SyncDevice> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SyncDevice", "uuid", true, "syncDeviceId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "type", true, "buildNumber", true,
			"featureSet", true, "hostname", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		SyncDevice existingSyncDevice = _persistence.fetchByPrimaryKey(
			newSyncDevice.getPrimaryKey());

		Assert.assertEquals(existingSyncDevice, newSyncDevice);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDevice missingSyncDevice = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSyncDevice);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SyncDevice newSyncDevice1 = addSyncDevice();
		SyncDevice newSyncDevice2 = addSyncDevice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDevice1.getPrimaryKey());
		primaryKeys.add(newSyncDevice2.getPrimaryKey());

		Map<Serializable, SyncDevice> syncDevices =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, syncDevices.size());
		Assert.assertEquals(
			newSyncDevice1, syncDevices.get(newSyncDevice1.getPrimaryKey()));
		Assert.assertEquals(
			newSyncDevice2, syncDevices.get(newSyncDevice2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SyncDevice> syncDevices =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDevices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SyncDevice newSyncDevice = addSyncDevice();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDevice.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SyncDevice> syncDevices =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDevices.size());
		Assert.assertEquals(
			newSyncDevice, syncDevices.get(newSyncDevice.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SyncDevice> syncDevices =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(syncDevices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSyncDevice.getPrimaryKey());

		Map<Serializable, SyncDevice> syncDevices =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, syncDevices.size());
		Assert.assertEquals(
			newSyncDevice, syncDevices.get(newSyncDevice.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SyncDeviceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SyncDevice>() {

				@Override
				public void performAction(SyncDevice syncDevice) {
					Assert.assertNotNull(syncDevice);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDevice.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"syncDeviceId", newSyncDevice.getSyncDeviceId()));

		List<SyncDevice> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SyncDevice existingSyncDevice = result.get(0);

		Assert.assertEquals(existingSyncDevice, newSyncDevice);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDevice.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"syncDeviceId", RandomTestUtil.nextLong()));

		List<SyncDevice> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SyncDevice newSyncDevice = addSyncDevice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDevice.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("syncDeviceId"));

		Object newSyncDeviceId = newSyncDevice.getSyncDeviceId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"syncDeviceId", new Object[] {newSyncDeviceId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncDeviceId = result.get(0);

		Assert.assertEquals(existingSyncDeviceId, newSyncDeviceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SyncDevice.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("syncDeviceId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"syncDeviceId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SyncDevice addSyncDevice() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SyncDevice syncDevice = _persistence.create(pk);

		syncDevice.setUuid(RandomTestUtil.randomString());

		syncDevice.setCompanyId(RandomTestUtil.nextLong());

		syncDevice.setUserId(RandomTestUtil.nextLong());

		syncDevice.setUserName(RandomTestUtil.randomString());

		syncDevice.setCreateDate(RandomTestUtil.nextDate());

		syncDevice.setModifiedDate(RandomTestUtil.nextDate());

		syncDevice.setType(RandomTestUtil.randomString());

		syncDevice.setBuildNumber(RandomTestUtil.nextLong());

		syncDevice.setFeatureSet(RandomTestUtil.nextInt());

		syncDevice.setHostname(RandomTestUtil.randomString());

		syncDevice.setStatus(RandomTestUtil.nextInt());

		_syncDevices.add(_persistence.update(syncDevice));

		return syncDevice;
	}

	private List<SyncDevice> _syncDevices = new ArrayList<SyncDevice>();
	private SyncDevicePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}