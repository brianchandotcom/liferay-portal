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
import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientException;
import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.notification.service.NotificationTemplateRecipientLocalServiceUtil;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientUtil;
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
public class NotificationTemplateRecipientPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.notification.service"));

	@Before
	public void setUp() {
		_persistence = NotificationTemplateRecipientUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationTemplateRecipient> iterator =
			_notificationTemplateRecipients.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipient notificationTemplateRecipient =
			_persistence.create(pk);

		Assert.assertNotNull(notificationTemplateRecipient);

		Assert.assertEquals(notificationTemplateRecipient.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		_persistence.remove(newNotificationTemplateRecipient);

		NotificationTemplateRecipient existingNotificationTemplateRecipient =
			_persistence.fetchByPrimaryKey(
				newNotificationTemplateRecipient.getPrimaryKey());

		Assert.assertNull(existingNotificationTemplateRecipient);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationTemplateRecipient();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipient newNotificationTemplateRecipient =
			_persistence.create(pk);

		newNotificationTemplateRecipient.setMvccVersion(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipient.setCompanyId(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipient.setUserId(RandomTestUtil.nextLong());

		newNotificationTemplateRecipient.setUserName(
			RandomTestUtil.randomString());

		newNotificationTemplateRecipient.setCreateDate(
			RandomTestUtil.nextDate());

		newNotificationTemplateRecipient.setModifiedDate(
			RandomTestUtil.nextDate());

		newNotificationTemplateRecipient.setNotificationTemplateId(
			RandomTestUtil.nextLong());

		newNotificationTemplateRecipient.setType(RandomTestUtil.randomString());

		_notificationTemplateRecipients.add(
			_persistence.update(newNotificationTemplateRecipient));

		NotificationTemplateRecipient existingNotificationTemplateRecipient =
			_persistence.findByPrimaryKey(
				newNotificationTemplateRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipient.getMvccVersion(),
			newNotificationTemplateRecipient.getMvccVersion());
		Assert.assertEquals(
			existingNotificationTemplateRecipient.
				getNotificationTemplateRecipientId(),
			newNotificationTemplateRecipient.
				getNotificationTemplateRecipientId());
		Assert.assertEquals(
			existingNotificationTemplateRecipient.getCompanyId(),
			newNotificationTemplateRecipient.getCompanyId());
		Assert.assertEquals(
			existingNotificationTemplateRecipient.getUserId(),
			newNotificationTemplateRecipient.getUserId());
		Assert.assertEquals(
			existingNotificationTemplateRecipient.getUserName(),
			newNotificationTemplateRecipient.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplateRecipient.getCreateDate()),
			Time.getShortTimestamp(
				newNotificationTemplateRecipient.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplateRecipient.getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationTemplateRecipient.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationTemplateRecipient.getNotificationTemplateId(),
			newNotificationTemplateRecipient.getNotificationTemplateId());
		Assert.assertEquals(
			existingNotificationTemplateRecipient.getType(),
			newNotificationTemplateRecipient.getType());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		NotificationTemplateRecipient existingNotificationTemplateRecipient =
			_persistence.findByPrimaryKey(
				newNotificationTemplateRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipient,
			newNotificationTemplateRecipient);
	}

	@Test(expected = NoSuchNotificationTemplateRecipientException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationTemplateRecipient>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NTemplateRecipient", "mvccVersion", true,
			"notificationTemplateRecipientId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "notificationTemplateId", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		NotificationTemplateRecipient existingNotificationTemplateRecipient =
			_persistence.fetchByPrimaryKey(
				newNotificationTemplateRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplateRecipient,
			newNotificationTemplateRecipient);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipient missingNotificationTemplateRecipient =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationTemplateRecipient);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationTemplateRecipient newNotificationTemplateRecipient1 =
			addNotificationTemplateRecipient();
		NotificationTemplateRecipient newNotificationTemplateRecipient2 =
			addNotificationTemplateRecipient();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplateRecipient1.getPrimaryKey());
		primaryKeys.add(newNotificationTemplateRecipient2.getPrimaryKey());

		Map<Serializable, NotificationTemplateRecipient>
			notificationTemplateRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, notificationTemplateRecipients.size());
		Assert.assertEquals(
			newNotificationTemplateRecipient1,
			notificationTemplateRecipients.get(
				newNotificationTemplateRecipient1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationTemplateRecipient2,
			notificationTemplateRecipients.get(
				newNotificationTemplateRecipient2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationTemplateRecipient>
			notificationTemplateRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationTemplateRecipients.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplateRecipient.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationTemplateRecipient>
			notificationTemplateRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationTemplateRecipients.size());
		Assert.assertEquals(
			newNotificationTemplateRecipient,
			notificationTemplateRecipients.get(
				newNotificationTemplateRecipient.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationTemplateRecipient>
			notificationTemplateRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationTemplateRecipients.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplateRecipient.getPrimaryKey());

		Map<Serializable, NotificationTemplateRecipient>
			notificationTemplateRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationTemplateRecipients.size());
		Assert.assertEquals(
			newNotificationTemplateRecipient,
			notificationTemplateRecipients.get(
				newNotificationTemplateRecipient.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationTemplateRecipientLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationTemplateRecipient>() {

				@Override
				public void performAction(
					NotificationTemplateRecipient
						notificationTemplateRecipient) {

					Assert.assertNotNull(notificationTemplateRecipient);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateRecipientId",
				newNotificationTemplateRecipient.
					getNotificationTemplateRecipientId()));

		List<NotificationTemplateRecipient> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationTemplateRecipient existingNotificationTemplateRecipient =
			result.get(0);

		Assert.assertEquals(
			existingNotificationTemplateRecipient,
			newNotificationTemplateRecipient);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateRecipientId", RandomTestUtil.nextLong()));

		List<NotificationTemplateRecipient> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationTemplateRecipient newNotificationTemplateRecipient =
			addNotificationTemplateRecipient();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationTemplateRecipientId"));

		Object newNotificationTemplateRecipientId =
			newNotificationTemplateRecipient.
				getNotificationTemplateRecipientId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateRecipientId",
				new Object[] {newNotificationTemplateRecipientId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationTemplateRecipientId = result.get(0);

		Assert.assertEquals(
			existingNotificationTemplateRecipientId,
			newNotificationTemplateRecipientId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplateRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationTemplateRecipientId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateRecipientId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationTemplateRecipient addNotificationTemplateRecipient()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationTemplateRecipient notificationTemplateRecipient =
			_persistence.create(pk);

		notificationTemplateRecipient.setMvccVersion(RandomTestUtil.nextLong());

		notificationTemplateRecipient.setCompanyId(RandomTestUtil.nextLong());

		notificationTemplateRecipient.setUserId(RandomTestUtil.nextLong());

		notificationTemplateRecipient.setUserName(
			RandomTestUtil.randomString());

		notificationTemplateRecipient.setCreateDate(RandomTestUtil.nextDate());

		notificationTemplateRecipient.setModifiedDate(
			RandomTestUtil.nextDate());

		notificationTemplateRecipient.setNotificationTemplateId(
			RandomTestUtil.nextLong());

		notificationTemplateRecipient.setType(RandomTestUtil.randomString());

		_notificationTemplateRecipients.add(
			_persistence.update(notificationTemplateRecipient));

		return notificationTemplateRecipient;
	}

	private List<NotificationTemplateRecipient>
		_notificationTemplateRecipients =
			new ArrayList<NotificationTemplateRecipient>();
	private NotificationTemplateRecipientPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}