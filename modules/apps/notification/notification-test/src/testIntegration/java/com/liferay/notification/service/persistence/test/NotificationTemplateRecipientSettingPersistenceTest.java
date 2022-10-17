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
import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientSettingException;
import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.notification.service.NotificationTemplateRecipientSettingLocalServiceUtil;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientSettingPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientSettingUtil;
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
public class NotificationTemplateRecipientSettingPersistenceTest {

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
			NotificationTemplateRecipientSettingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationTemplateRecipientSetting> iterator =
			_notificationTemplateRecipientSettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = _persistence.create(pk);

		Assert.assertNotNull(notificationTemplateRecipientSetting);

		Assert.assertEquals(
			notificationTemplateRecipientSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		_persistence.remove(newNotificationTemplateRecipientSetting);

		NotificationTemplateRecipientSetting
			existingNotificationTemplateRecipientSetting =
				_persistence.fetchByPrimaryKey(
					newNotificationTemplateRecipientSetting.getPrimaryKey());

		Assert.assertNull(existingNotificationTemplateRecipientSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationTemplateRecipientSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting = _persistence.create(pk);

		newNotificationTemplateRecipientSetting.setMvccVersion(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipientSetting.setCompanyId(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipientSetting.setUserId(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipientSetting.setUserName(
			RandomTestUtil.randomString());

		newNotificationTemplateRecipientSetting.setCreateDate(
			RandomTestUtil.nextDate());

		newNotificationTemplateRecipientSetting.setModifiedDate(
			RandomTestUtil.nextDate());

		newNotificationTemplateRecipientSetting.
			setNotificationTemplateRecipientId(RandomTestUtil.nextLong());

		newNotificationTemplateRecipientSetting.setName(
			RandomTestUtil.randomString());

		newNotificationTemplateRecipientSetting.setValue(
			RandomTestUtil.randomString());

		_notificationTemplateRecipientSettings.add(
			_persistence.update(newNotificationTemplateRecipientSetting));

		NotificationTemplateRecipientSetting
			existingNotificationTemplateRecipientSetting =
				_persistence.findByPrimaryKey(
					newNotificationTemplateRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getMvccVersion(),
			newNotificationTemplateRecipientSetting.getMvccVersion());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.
				getNotificationTemplateRecipientSettingId(),
			newNotificationTemplateRecipientSetting.
				getNotificationTemplateRecipientSettingId());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getCompanyId(),
			newNotificationTemplateRecipientSetting.getCompanyId());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getUserId(),
			newNotificationTemplateRecipientSetting.getUserId());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getUserName(),
			newNotificationTemplateRecipientSetting.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplateRecipientSetting.getCreateDate()),
			Time.getShortTimestamp(
				newNotificationTemplateRecipientSetting.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplateRecipientSetting.getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationTemplateRecipientSetting.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.
				getNotificationTemplateRecipientId(),
			newNotificationTemplateRecipientSetting.
				getNotificationTemplateRecipientId());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getName(),
			newNotificationTemplateRecipientSetting.getName());
		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting.getValue(),
			newNotificationTemplateRecipientSetting.getValue());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		NotificationTemplateRecipientSetting
			existingNotificationTemplateRecipientSetting =
				_persistence.findByPrimaryKey(
					newNotificationTemplateRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting,
			newNotificationTemplateRecipientSetting);
	}

	@Test(expected = NoSuchNotificationTemplateRecipientSettingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationTemplateRecipientSetting>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NTemplateRecipientSetting", "mvccVersion", true,
			"notificationTemplateRecipientSettingId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "notificationTemplateRecipientId", true,
			"name", true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		NotificationTemplateRecipientSetting
			existingNotificationTemplateRecipientSetting =
				_persistence.fetchByPrimaryKey(
					newNotificationTemplateRecipientSetting.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting,
			newNotificationTemplateRecipientSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipientSetting
			missingNotificationTemplateRecipientSetting =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationTemplateRecipientSetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting1 =
				addNotificationTemplateRecipientSetting();
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting2 =
				addNotificationTemplateRecipientSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationTemplateRecipientSetting1.getPrimaryKey());
		primaryKeys.add(
			newNotificationTemplateRecipientSetting2.getPrimaryKey());

		Map<Serializable, NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notificationTemplateRecipientSettings.size());
		Assert.assertEquals(
			newNotificationTemplateRecipientSetting1,
			notificationTemplateRecipientSettings.get(
				newNotificationTemplateRecipientSetting1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationTemplateRecipientSetting2,
			notificationTemplateRecipientSettings.get(
				newNotificationTemplateRecipientSetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationTemplateRecipientSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationTemplateRecipientSetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationTemplateRecipientSettings.size());
		Assert.assertEquals(
			newNotificationTemplateRecipientSetting,
			notificationTemplateRecipientSettings.get(
				newNotificationTemplateRecipientSetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationTemplateRecipientSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newNotificationTemplateRecipientSetting.getPrimaryKey());

		Map<Serializable, NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationTemplateRecipientSettings.size());
		Assert.assertEquals(
			newNotificationTemplateRecipientSetting,
			notificationTemplateRecipientSettings.get(
				newNotificationTemplateRecipientSetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationTemplateRecipientSettingLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationTemplateRecipientSetting>() {

				@Override
				public void performAction(
					NotificationTemplateRecipientSetting
						notificationTemplateRecipientSetting) {

					Assert.assertNotNull(notificationTemplateRecipientSetting);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateRecipientSettingId",
				newNotificationTemplateRecipientSetting.
					getNotificationTemplateRecipientSettingId()));

		List<NotificationTemplateRecipientSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationTemplateRecipientSetting
			existingNotificationTemplateRecipientSetting = result.get(0);

		Assert.assertEquals(
			existingNotificationTemplateRecipientSetting,
			newNotificationTemplateRecipientSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateRecipientSettingId",
				RandomTestUtil.nextLong()));

		List<NotificationTemplateRecipientSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationTemplateRecipientSetting
			newNotificationTemplateRecipientSetting =
				addNotificationTemplateRecipientSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationTemplateRecipientSettingId"));

		Object newNotificationTemplateRecipientSettingId =
			newNotificationTemplateRecipientSetting.
				getNotificationTemplateRecipientSettingId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateRecipientSettingId",
				new Object[] {newNotificationTemplateRecipientSettingId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationTemplateRecipientSettingId = result.get(0);

		Assert.assertEquals(
			existingNotificationTemplateRecipientSettingId,
			newNotificationTemplateRecipientSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipientSetting.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationTemplateRecipientSettingId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateRecipientSettingId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationTemplateRecipientSetting
			addNotificationTemplateRecipientSetting()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = _persistence.create(pk);

		notificationTemplateRecipientSetting.setMvccVersion(
			RandomTestUtil.nextLong());

		notificationTemplateRecipientSetting.setCompanyId(
			RandomTestUtil.nextLong());

		notificationTemplateRecipientSetting.setUserId(
			RandomTestUtil.nextLong());

		notificationTemplateRecipientSetting.setUserName(
			RandomTestUtil.randomString());

		notificationTemplateRecipientSetting.setCreateDate(
			RandomTestUtil.nextDate());

		notificationTemplateRecipientSetting.setModifiedDate(
			RandomTestUtil.nextDate());

		notificationTemplateRecipientSetting.setNotificationTemplateRecipientId(
			RandomTestUtil.nextLong());

		notificationTemplateRecipientSetting.setName(
			RandomTestUtil.randomString());

		notificationTemplateRecipientSetting.setValue(
			RandomTestUtil.randomString());

		_notificationTemplateRecipientSettings.add(
			_persistence.update(notificationTemplateRecipientSetting));

		return notificationTemplateRecipientSetting;
	}

	private List<NotificationTemplateRecipientSetting>
		_notificationTemplateRecipientSettings =
			new ArrayList<NotificationTemplateRecipientSetting>();
	private NotificationTemplateRecipientSettingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}