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

package com.liferay.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientSettingException;
import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryRecipientSettingLocalServiceUtil;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientSettingPersistence;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientSettingUtil;
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
public class NotificationQueueEntryRecipientSettingPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.notification.service"));

	@Before
	public void setUp() {
		_persistence =
			NotificationQueueEntryRecipientSettingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationQueueEntryRecipientSetting> iterator =
			_notificationQueueEntryRecipientSettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting = _persistence.create(pk);

		Assert.assertNotNull(notificationQueueEntryRecipientSetting);

		Assert.assertEquals(
			notificationQueueEntryRecipientSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		_persistence.remove(newNotificationQueueEntryRecipientSetting);

		NotificationQueueEntryRecipientSetting
			existingNotificationQueueEntryRecipientSetting =
				_persistence.fetchByPrimaryKey(
					newNotificationQueueEntryRecipientSetting.getPrimaryKey());

		Assert.assertNull(existingNotificationQueueEntryRecipientSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationQueueEntryRecipientSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting = _persistence.create(pk);

		newNotificationQueueEntryRecipientSetting.setMvccVersion(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipientSetting.setCompanyId(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipientSetting.setUserId(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipientSetting.setUserName(
			RandomTestUtil.randomString());

		newNotificationQueueEntryRecipientSetting.setCreateDate(
			RandomTestUtil.nextDate());

		newNotificationQueueEntryRecipientSetting.setModifiedDate(
			RandomTestUtil.nextDate());

		newNotificationQueueEntryRecipientSetting.
			setNotificationQueueEntryRecipientId(RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipientSetting.setName(
			RandomTestUtil.randomString());

		newNotificationQueueEntryRecipientSetting.setValue(
			RandomTestUtil.randomString());

		_notificationQueueEntryRecipientSettings.add(
			_persistence.update(newNotificationQueueEntryRecipientSetting));

		NotificationQueueEntryRecipientSetting
			existingNotificationQueueEntryRecipientSetting =
				_persistence.findByPrimaryKey(
					newNotificationQueueEntryRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getMvccVersion(),
			newNotificationQueueEntryRecipientSetting.getMvccVersion());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.
				getNotificationQueueEntryRecipientSettingId(),
			newNotificationQueueEntryRecipientSetting.
				getNotificationQueueEntryRecipientSettingId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getCompanyId(),
			newNotificationQueueEntryRecipientSetting.getCompanyId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getUserId(),
			newNotificationQueueEntryRecipientSetting.getUserId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getUserName(),
			newNotificationQueueEntryRecipientSetting.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationQueueEntryRecipientSetting.getCreateDate()),
			Time.getShortTimestamp(
				newNotificationQueueEntryRecipientSetting.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationQueueEntryRecipientSetting.
					getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationQueueEntryRecipientSetting.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.
				getNotificationQueueEntryRecipientId(),
			newNotificationQueueEntryRecipientSetting.
				getNotificationQueueEntryRecipientId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getName(),
			newNotificationQueueEntryRecipientSetting.getName());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting.getValue(),
			newNotificationQueueEntryRecipientSetting.getValue());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		NotificationQueueEntryRecipientSetting
			existingNotificationQueueEntryRecipientSetting =
				_persistence.findByPrimaryKey(
					newNotificationQueueEntryRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting,
			newNotificationQueueEntryRecipientSetting);
	}

	@Test(
		expected = NoSuchNotificationQueueEntryRecipientSettingException.class
	)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationQueueEntryRecipientSetting>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NQueueEntryRecipientSetting", "mvccVersion", true,
			"notificationQueueEntryRecipientSettingId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "notificationQueueEntryRecipientId", true,
			"name", true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		NotificationQueueEntryRecipientSetting
			existingNotificationQueueEntryRecipientSetting =
				_persistence.fetchByPrimaryKey(
					newNotificationQueueEntryRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting,
			newNotificationQueueEntryRecipientSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipientSetting
			missingNotificationQueueEntryRecipientSetting =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationQueueEntryRecipientSetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting1 =
				addNotificationQueueEntryRecipientSetting();
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting2 =
				addNotificationQueueEntryRecipientSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationQueueEntryRecipientSetting1.getPrimaryKey());
		primaryKeys.add(
			newNotificationQueueEntryRecipientSetting2.getPrimaryKey());

		Map<Serializable, NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notificationQueueEntryRecipientSettings.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipientSetting1,
			notificationQueueEntryRecipientSettings.get(
				newNotificationQueueEntryRecipientSetting1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationQueueEntryRecipientSetting2,
			notificationQueueEntryRecipientSettings.get(
				newNotificationQueueEntryRecipientSetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationQueueEntryRecipientSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationQueueEntryRecipientSetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationQueueEntryRecipientSettings.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipientSetting,
			notificationQueueEntryRecipientSettings.get(
				newNotificationQueueEntryRecipientSetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationQueueEntryRecipientSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationQueueEntryRecipientSetting.getPrimaryKey());

		Map<Serializable, NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationQueueEntryRecipientSettings.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipientSetting,
			notificationQueueEntryRecipientSettings.get(
				newNotificationQueueEntryRecipientSetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationQueueEntryRecipientSettingLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationQueueEntryRecipientSetting>() {

				@Override
				public void performAction(
					NotificationQueueEntryRecipientSetting
						notificationQueueEntryRecipientSetting) {

					Assert.assertNotNull(
						notificationQueueEntryRecipientSetting);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationQueueEntryRecipientSettingId",
				newNotificationQueueEntryRecipientSetting.
					getNotificationQueueEntryRecipientSettingId()));

		List<NotificationQueueEntryRecipientSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationQueueEntryRecipientSetting
			existingNotificationQueueEntryRecipientSetting = result.get(0);

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSetting,
			newNotificationQueueEntryRecipientSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationQueueEntryRecipientSettingId",
				RandomTestUtil.nextLong()));

		List<NotificationQueueEntryRecipientSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationQueueEntryRecipientSetting
			newNotificationQueueEntryRecipientSetting =
				addNotificationQueueEntryRecipientSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationQueueEntryRecipientSettingId"));

		Object newNotificationQueueEntryRecipientSettingId =
			newNotificationQueueEntryRecipientSetting.
				getNotificationQueueEntryRecipientSettingId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationQueueEntryRecipientSettingId",
				new Object[] {newNotificationQueueEntryRecipientSettingId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationQueueEntryRecipientSettingId = result.get(0);

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientSettingId,
			newNotificationQueueEntryRecipientSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationQueueEntryRecipientSettingId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationQueueEntryRecipientSettingId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationQueueEntryRecipientSetting
			addNotificationQueueEntryRecipientSetting()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting = _persistence.create(pk);

		notificationQueueEntryRecipientSetting.setMvccVersion(
			RandomTestUtil.nextLong());

		notificationQueueEntryRecipientSetting.setCompanyId(
			RandomTestUtil.nextLong());

		notificationQueueEntryRecipientSetting.setUserId(
			RandomTestUtil.nextLong());

		notificationQueueEntryRecipientSetting.setUserName(
			RandomTestUtil.randomString());

		notificationQueueEntryRecipientSetting.setCreateDate(
			RandomTestUtil.nextDate());

		notificationQueueEntryRecipientSetting.setModifiedDate(
			RandomTestUtil.nextDate());

		notificationQueueEntryRecipientSetting.
			setNotificationQueueEntryRecipientId(RandomTestUtil.nextLong());

		notificationQueueEntryRecipientSetting.setName(
			RandomTestUtil.randomString());

		notificationQueueEntryRecipientSetting.setValue(
			RandomTestUtil.randomString());

		_notificationQueueEntryRecipientSettings.add(
			_persistence.update(notificationQueueEntryRecipientSetting));

		return notificationQueueEntryRecipientSetting;
	}

	private List<NotificationQueueEntryRecipientSetting>
		_notificationQueueEntryRecipientSettings =
			new ArrayList<NotificationQueueEntryRecipientSetting>();
	private NotificationQueueEntryRecipientSettingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}