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
import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientException;
import com.liferay.notification.model.NotificationQueueEntryRecipient;
import com.liferay.notification.service.NotificationQueueEntryRecipientLocalServiceUtil;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientPersistence;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientUtil;
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
public class NotificationQueueEntryRecipientPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.notification.service"));

	@Before
	public void setUp() {
		_persistence = NotificationQueueEntryRecipientUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationQueueEntryRecipient> iterator =
			_notificationQueueEntryRecipients.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipient notificationQueueEntryRecipient =
			_persistence.create(pk);

		Assert.assertNotNull(notificationQueueEntryRecipient);

		Assert.assertEquals(
			notificationQueueEntryRecipient.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		_persistence.remove(newNotificationQueueEntryRecipient);

		NotificationQueueEntryRecipient
			existingNotificationQueueEntryRecipient =
				_persistence.fetchByPrimaryKey(
					newNotificationQueueEntryRecipient.getPrimaryKey());

		Assert.assertNull(existingNotificationQueueEntryRecipient);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationQueueEntryRecipient();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			_persistence.create(pk);

		newNotificationQueueEntryRecipient.setMvccVersion(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipient.setCompanyId(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipient.setUserId(RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipient.setUserName(
			RandomTestUtil.randomString());

		newNotificationQueueEntryRecipient.setCreateDate(
			RandomTestUtil.nextDate());

		newNotificationQueueEntryRecipient.setModifiedDate(
			RandomTestUtil.nextDate());

		newNotificationQueueEntryRecipient.setNotificationQueueEntryId(
			RandomTestUtil.nextLong());

		newNotificationQueueEntryRecipient.setType(
			RandomTestUtil.randomString());

		_notificationQueueEntryRecipients.add(
			_persistence.update(newNotificationQueueEntryRecipient));

		NotificationQueueEntryRecipient
			existingNotificationQueueEntryRecipient =
				_persistence.findByPrimaryKey(
					newNotificationQueueEntryRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.getMvccVersion(),
			newNotificationQueueEntryRecipient.getMvccVersion());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.
				getNotificationQueueEntryRecipientId(),
			newNotificationQueueEntryRecipient.
				getNotificationQueueEntryRecipientId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.getCompanyId(),
			newNotificationQueueEntryRecipient.getCompanyId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.getUserId(),
			newNotificationQueueEntryRecipient.getUserId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.getUserName(),
			newNotificationQueueEntryRecipient.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationQueueEntryRecipient.getCreateDate()),
			Time.getShortTimestamp(
				newNotificationQueueEntryRecipient.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationQueueEntryRecipient.getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationQueueEntryRecipient.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.
				getNotificationQueueEntryId(),
			newNotificationQueueEntryRecipient.getNotificationQueueEntryId());
		Assert.assertEquals(
			existingNotificationQueueEntryRecipient.getType(),
			newNotificationQueueEntryRecipient.getType());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		NotificationQueueEntryRecipient
			existingNotificationQueueEntryRecipient =
				_persistence.findByPrimaryKey(
					newNotificationQueueEntryRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipient,
			newNotificationQueueEntryRecipient);
	}

	@Test(expected = NoSuchNotificationQueueEntryRecipientException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationQueueEntryRecipient>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NQueueEntryRecipient", "mvccVersion", true,
			"notificationQueueEntryRecipientId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "notificationQueueEntryId", true, "type",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		NotificationQueueEntryRecipient
			existingNotificationQueueEntryRecipient =
				_persistence.fetchByPrimaryKey(
					newNotificationQueueEntryRecipient.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationQueueEntryRecipient,
			newNotificationQueueEntryRecipient);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipient missingNotificationQueueEntryRecipient =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationQueueEntryRecipient);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient1 =
			addNotificationQueueEntryRecipient();
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient2 =
			addNotificationQueueEntryRecipient();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationQueueEntryRecipient1.getPrimaryKey());
		primaryKeys.add(newNotificationQueueEntryRecipient2.getPrimaryKey());

		Map<Serializable, NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, notificationQueueEntryRecipients.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipient1,
			notificationQueueEntryRecipients.get(
				newNotificationQueueEntryRecipient1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationQueueEntryRecipient2,
			notificationQueueEntryRecipients.get(
				newNotificationQueueEntryRecipient2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationQueueEntryRecipients.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationQueueEntryRecipient.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationQueueEntryRecipients.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipient,
			notificationQueueEntryRecipients.get(
				newNotificationQueueEntryRecipient.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationQueueEntryRecipients.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationQueueEntryRecipient.getPrimaryKey());

		Map<Serializable, NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationQueueEntryRecipients.size());
		Assert.assertEquals(
			newNotificationQueueEntryRecipient,
			notificationQueueEntryRecipients.get(
				newNotificationQueueEntryRecipient.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationQueueEntryRecipientLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationQueueEntryRecipient>() {

				@Override
				public void performAction(
					NotificationQueueEntryRecipient
						notificationQueueEntryRecipient) {

					Assert.assertNotNull(notificationQueueEntryRecipient);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationQueueEntryRecipientId",
				newNotificationQueueEntryRecipient.
					getNotificationQueueEntryRecipientId()));

		List<NotificationQueueEntryRecipient> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationQueueEntryRecipient
			existingNotificationQueueEntryRecipient = result.get(0);

		Assert.assertEquals(
			existingNotificationQueueEntryRecipient,
			newNotificationQueueEntryRecipient);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationQueueEntryRecipientId",
				RandomTestUtil.nextLong()));

		List<NotificationQueueEntryRecipient> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationQueueEntryRecipient newNotificationQueueEntryRecipient =
			addNotificationQueueEntryRecipient();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationQueueEntryRecipientId"));

		Object newNotificationQueueEntryRecipientId =
			newNotificationQueueEntryRecipient.
				getNotificationQueueEntryRecipientId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationQueueEntryRecipientId",
				new Object[] {newNotificationQueueEntryRecipientId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationQueueEntryRecipientId = result.get(0);

		Assert.assertEquals(
			existingNotificationQueueEntryRecipientId,
			newNotificationQueueEntryRecipientId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationQueueEntryRecipient.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"notificationQueueEntryRecipientId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationQueueEntryRecipientId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationQueueEntryRecipient
			addNotificationQueueEntryRecipient()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationQueueEntryRecipient notificationQueueEntryRecipient =
			_persistence.create(pk);

		notificationQueueEntryRecipient.setMvccVersion(
			RandomTestUtil.nextLong());

		notificationQueueEntryRecipient.setCompanyId(RandomTestUtil.nextLong());

		notificationQueueEntryRecipient.setUserId(RandomTestUtil.nextLong());

		notificationQueueEntryRecipient.setUserName(
			RandomTestUtil.randomString());

		notificationQueueEntryRecipient.setCreateDate(
			RandomTestUtil.nextDate());

		notificationQueueEntryRecipient.setModifiedDate(
			RandomTestUtil.nextDate());

		notificationQueueEntryRecipient.setNotificationQueueEntryId(
			RandomTestUtil.nextLong());

		notificationQueueEntryRecipient.setType(RandomTestUtil.randomString());

		_notificationQueueEntryRecipients.add(
			_persistence.update(notificationQueueEntryRecipient));

		return notificationQueueEntryRecipient;
	}

	private List<NotificationQueueEntryRecipient>
		_notificationQueueEntryRecipients =
			new ArrayList<NotificationQueueEntryRecipient>();
	private NotificationQueueEntryRecipientPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}