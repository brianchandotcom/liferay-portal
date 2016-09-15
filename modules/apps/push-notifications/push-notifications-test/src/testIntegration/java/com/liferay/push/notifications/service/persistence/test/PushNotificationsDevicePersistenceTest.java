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

package com.liferay.push.notifications.service.persistence.test;

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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.push.notifications.exception.NoSuchDeviceException;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalServiceUtil;
import com.liferay.push.notifications.service.persistence.PushNotificationsDevicePersistence;
import com.liferay.push.notifications.service.persistence.PushNotificationsDeviceUtil;

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
public class PushNotificationsDevicePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PushNotificationsDeviceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PushNotificationsDevice> iterator = _pushNotificationsDevices.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PushNotificationsDevice pushNotificationsDevice = _persistence.create(pk);

		Assert.assertNotNull(pushNotificationsDevice);

		Assert.assertEquals(pushNotificationsDevice.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		_persistence.remove(newPushNotificationsDevice);

		PushNotificationsDevice existingPushNotificationsDevice = _persistence.fetchByPrimaryKey(newPushNotificationsDevice.getPrimaryKey());

		Assert.assertNull(existingPushNotificationsDevice);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPushNotificationsDevice();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PushNotificationsDevice newPushNotificationsDevice = _persistence.create(pk);

		newPushNotificationsDevice.setCompanyId(RandomTestUtil.nextLong());

		newPushNotificationsDevice.setUserId(RandomTestUtil.nextLong());

		newPushNotificationsDevice.setCreateDate(RandomTestUtil.nextDate());

		newPushNotificationsDevice.setPlatform(RandomTestUtil.randomString());

		newPushNotificationsDevice.setToken(RandomTestUtil.randomString());

		_pushNotificationsDevices.add(_persistence.update(
				newPushNotificationsDevice));

		PushNotificationsDevice existingPushNotificationsDevice = _persistence.findByPrimaryKey(newPushNotificationsDevice.getPrimaryKey());

		Assert.assertEquals(existingPushNotificationsDevice.getPushNotificationsDeviceId(),
			newPushNotificationsDevice.getPushNotificationsDeviceId());
		Assert.assertEquals(existingPushNotificationsDevice.getCompanyId(),
			newPushNotificationsDevice.getCompanyId());
		Assert.assertEquals(existingPushNotificationsDevice.getUserId(),
			newPushNotificationsDevice.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPushNotificationsDevice.getCreateDate()),
			Time.getShortTimestamp(newPushNotificationsDevice.getCreateDate()));
		Assert.assertEquals(existingPushNotificationsDevice.getPlatform(),
			newPushNotificationsDevice.getPlatform());
		Assert.assertEquals(existingPushNotificationsDevice.getToken(),
			newPushNotificationsDevice.getToken());
	}

	@Test
	public void testCountByToken() throws Exception {
		_persistence.countByToken(StringPool.BLANK);

		_persistence.countByToken(StringPool.NULL);

		_persistence.countByToken((String)null);
	}

	@Test
	public void testCountByU_P() throws Exception {
		_persistence.countByU_P(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByU_P(0L, StringPool.NULL);

		_persistence.countByU_P(0L, (String)null);
	}

	@Test
	public void testCountByU_PArrayable() throws Exception {
		_persistence.countByU_P(new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.randomString());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		PushNotificationsDevice existingPushNotificationsDevice = _persistence.findByPrimaryKey(newPushNotificationsDevice.getPrimaryKey());

		Assert.assertEquals(existingPushNotificationsDevice,
			newPushNotificationsDevice);
	}

	@Test(expected = NoSuchDeviceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<PushNotificationsDevice> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PushNotificationsDevice",
			"pushNotificationsDeviceId", true, "companyId", true, "userId",
			true, "createDate", true, "platform", true, "token", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		PushNotificationsDevice existingPushNotificationsDevice = _persistence.fetchByPrimaryKey(newPushNotificationsDevice.getPrimaryKey());

		Assert.assertEquals(existingPushNotificationsDevice,
			newPushNotificationsDevice);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PushNotificationsDevice missingPushNotificationsDevice = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPushNotificationsDevice);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PushNotificationsDevice newPushNotificationsDevice1 = addPushNotificationsDevice();
		PushNotificationsDevice newPushNotificationsDevice2 = addPushNotificationsDevice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPushNotificationsDevice1.getPrimaryKey());
		primaryKeys.add(newPushNotificationsDevice2.getPrimaryKey());

		Map<Serializable, PushNotificationsDevice> pushNotificationsDevices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pushNotificationsDevices.size());
		Assert.assertEquals(newPushNotificationsDevice1,
			pushNotificationsDevices.get(
				newPushNotificationsDevice1.getPrimaryKey()));
		Assert.assertEquals(newPushNotificationsDevice2,
			pushNotificationsDevices.get(
				newPushNotificationsDevice2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PushNotificationsDevice> pushNotificationsDevices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pushNotificationsDevices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPushNotificationsDevice.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PushNotificationsDevice> pushNotificationsDevices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pushNotificationsDevices.size());
		Assert.assertEquals(newPushNotificationsDevice,
			pushNotificationsDevices.get(
				newPushNotificationsDevice.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PushNotificationsDevice> pushNotificationsDevices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pushNotificationsDevices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPushNotificationsDevice.getPrimaryKey());

		Map<Serializable, PushNotificationsDevice> pushNotificationsDevices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pushNotificationsDevices.size());
		Assert.assertEquals(newPushNotificationsDevice,
			pushNotificationsDevices.get(
				newPushNotificationsDevice.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PushNotificationsDeviceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<PushNotificationsDevice>() {
				@Override
				public void performAction(
					PushNotificationsDevice pushNotificationsDevice) {
					Assert.assertNotNull(pushNotificationsDevice);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PushNotificationsDevice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"pushNotificationsDeviceId",
				newPushNotificationsDevice.getPushNotificationsDeviceId()));

		List<PushNotificationsDevice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PushNotificationsDevice existingPushNotificationsDevice = result.get(0);

		Assert.assertEquals(existingPushNotificationsDevice,
			newPushNotificationsDevice);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PushNotificationsDevice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"pushNotificationsDeviceId", RandomTestUtil.nextLong()));

		List<PushNotificationsDevice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PushNotificationsDevice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pushNotificationsDeviceId"));

		Object newPushNotificationsDeviceId = newPushNotificationsDevice.getPushNotificationsDeviceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"pushNotificationsDeviceId",
				new Object[] { newPushNotificationsDeviceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPushNotificationsDeviceId = result.get(0);

		Assert.assertEquals(existingPushNotificationsDeviceId,
			newPushNotificationsDeviceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PushNotificationsDevice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pushNotificationsDeviceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"pushNotificationsDeviceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PushNotificationsDevice newPushNotificationsDevice = addPushNotificationsDevice();

		_persistence.clearCache();

		PushNotificationsDevice existingPushNotificationsDevice = _persistence.findByPrimaryKey(newPushNotificationsDevice.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingPushNotificationsDevice.getToken(),
				ReflectionTestUtil.invoke(existingPushNotificationsDevice,
					"getOriginalToken", new Class<?>[0])));
	}

	protected PushNotificationsDevice addPushNotificationsDevice()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		PushNotificationsDevice pushNotificationsDevice = _persistence.create(pk);

		pushNotificationsDevice.setCompanyId(RandomTestUtil.nextLong());

		pushNotificationsDevice.setUserId(RandomTestUtil.nextLong());

		pushNotificationsDevice.setCreateDate(RandomTestUtil.nextDate());

		pushNotificationsDevice.setPlatform(RandomTestUtil.randomString());

		pushNotificationsDevice.setToken(RandomTestUtil.randomString());

		_pushNotificationsDevices.add(_persistence.update(
				pushNotificationsDevice));

		return pushNotificationsDevice;
	}

	private List<PushNotificationsDevice> _pushNotificationsDevices = new ArrayList<PushNotificationsDevice>();
	private PushNotificationsDevicePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}