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

package com.liferay.notifications.admin.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notifications.admin.exception.NoSuchNotificationsQueueEntryException;
import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.notifications.admin.service.NotificationsQueueEntryLocalServiceUtil;
import com.liferay.notifications.admin.service.persistence.NotificationsQueueEntryPersistence;
import com.liferay.notifications.admin.service.persistence.NotificationsQueueEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class NotificationsQueueEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.notifications.admin.service"));

	@Before
	public void setUp() {
		_persistence = NotificationsQueueEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationsQueueEntry> iterator =
			_notificationsQueueEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsQueueEntry notificationsQueueEntry = _persistence.create(
			pk);

		Assert.assertNotNull(notificationsQueueEntry);

		Assert.assertEquals(notificationsQueueEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		_persistence.remove(newNotificationsQueueEntry);

		NotificationsQueueEntry existingNotificationsQueueEntry =
			_persistence.fetchByPrimaryKey(
				newNotificationsQueueEntry.getPrimaryKey());

		Assert.assertNull(existingNotificationsQueueEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationsQueueEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsQueueEntry newNotificationsQueueEntry =
			_persistence.create(pk);

		newNotificationsQueueEntry.setMvccVersion(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setGroupId(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setCompanyId(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setUserId(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setUserName(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setCreateDate(RandomTestUtil.nextDate());

		newNotificationsQueueEntry.setModifiedDate(RandomTestUtil.nextDate());

		newNotificationsQueueEntry.setClassNameId(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setClassPK(RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setNotificationsTemplateId(
			RandomTestUtil.nextLong());

		newNotificationsQueueEntry.setFrom(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setFromName(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setTo(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setToName(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setCc(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setBcc(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setSubject(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setBody(RandomTestUtil.randomString());

		newNotificationsQueueEntry.setPriority(RandomTestUtil.nextDouble());

		newNotificationsQueueEntry.setSent(RandomTestUtil.randomBoolean());

		newNotificationsQueueEntry.setSentDate(RandomTestUtil.nextDate());

		_notificationsQueueEntries.add(
			_persistence.update(newNotificationsQueueEntry));

		NotificationsQueueEntry existingNotificationsQueueEntry =
			_persistence.findByPrimaryKey(
				newNotificationsQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsQueueEntry.getMvccVersion(),
			newNotificationsQueueEntry.getMvccVersion());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getNotificationsQueueEntryId(),
			newNotificationsQueueEntry.getNotificationsQueueEntryId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getGroupId(),
			newNotificationsQueueEntry.getGroupId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getCompanyId(),
			newNotificationsQueueEntry.getCompanyId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getUserId(),
			newNotificationsQueueEntry.getUserId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getUserName(),
			newNotificationsQueueEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationsQueueEntry.getCreateDate()),
			Time.getShortTimestamp(newNotificationsQueueEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationsQueueEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationsQueueEntry.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationsQueueEntry.getClassNameId(),
			newNotificationsQueueEntry.getClassNameId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getClassPK(),
			newNotificationsQueueEntry.getClassPK());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getNotificationsTemplateId(),
			newNotificationsQueueEntry.getNotificationsTemplateId());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getFrom(),
			newNotificationsQueueEntry.getFrom());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getFromName(),
			newNotificationsQueueEntry.getFromName());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getTo(),
			newNotificationsQueueEntry.getTo());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getToName(),
			newNotificationsQueueEntry.getToName());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getCc(),
			newNotificationsQueueEntry.getCc());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getBcc(),
			newNotificationsQueueEntry.getBcc());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getSubject(),
			newNotificationsQueueEntry.getSubject());
		Assert.assertEquals(
			existingNotificationsQueueEntry.getBody(),
			newNotificationsQueueEntry.getBody());
		AssertUtils.assertEquals(
			existingNotificationsQueueEntry.getPriority(),
			newNotificationsQueueEntry.getPriority());
		Assert.assertEquals(
			existingNotificationsQueueEntry.isSent(),
			newNotificationsQueueEntry.isSent());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationsQueueEntry.getSentDate()),
			Time.getShortTimestamp(newNotificationsQueueEntry.getSentDate()));
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByNotificationsTemplateId() throws Exception {
		_persistence.countByNotificationsTemplateId(RandomTestUtil.nextLong());

		_persistence.countByNotificationsTemplateId(0L);
	}

	@Test
	public void testCountBySent() throws Exception {
		_persistence.countBySent(RandomTestUtil.randomBoolean());

		_persistence.countBySent(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByLtSentDate() throws Exception {
		_persistence.countByLtSentDate(RandomTestUtil.nextDate());

		_persistence.countByLtSentDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByG_C_C_S() throws Exception {
		_persistence.countByG_C_C_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_S(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		NotificationsQueueEntry existingNotificationsQueueEntry =
			_persistence.findByPrimaryKey(
				newNotificationsQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsQueueEntry, newNotificationsQueueEntry);
	}

	@Test(expected = NoSuchNotificationsQueueEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationsQueueEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NotificationsQueueEntry", "mvccVersion", true,
			"notificationsQueueEntryId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"notificationsTemplateId", true, "from", true, "fromName", true,
			"to", true, "toName", true, "cc", true, "bcc", true, "subject",
			true, "body", true, "priority", true, "sent", true, "sentDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		NotificationsQueueEntry existingNotificationsQueueEntry =
			_persistence.fetchByPrimaryKey(
				newNotificationsQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsQueueEntry, newNotificationsQueueEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsQueueEntry missingNotificationsQueueEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationsQueueEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationsQueueEntry newNotificationsQueueEntry1 =
			addNotificationsQueueEntry();
		NotificationsQueueEntry newNotificationsQueueEntry2 =
			addNotificationsQueueEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsQueueEntry1.getPrimaryKey());
		primaryKeys.add(newNotificationsQueueEntry2.getPrimaryKey());

		Map<Serializable, NotificationsQueueEntry> notificationsQueueEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notificationsQueueEntries.size());
		Assert.assertEquals(
			newNotificationsQueueEntry1,
			notificationsQueueEntries.get(
				newNotificationsQueueEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationsQueueEntry2,
			notificationsQueueEntries.get(
				newNotificationsQueueEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationsQueueEntry> notificationsQueueEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationsQueueEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsQueueEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationsQueueEntry> notificationsQueueEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationsQueueEntries.size());
		Assert.assertEquals(
			newNotificationsQueueEntry,
			notificationsQueueEntries.get(
				newNotificationsQueueEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationsQueueEntry> notificationsQueueEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationsQueueEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsQueueEntry.getPrimaryKey());

		Map<Serializable, NotificationsQueueEntry> notificationsQueueEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationsQueueEntries.size());
		Assert.assertEquals(
			newNotificationsQueueEntry,
			notificationsQueueEntries.get(
				newNotificationsQueueEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationsQueueEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationsQueueEntry>() {

				@Override
				public void performAction(
					NotificationsQueueEntry notificationsQueueEntry) {

					Assert.assertNotNull(notificationsQueueEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationsQueueEntryId",
				newNotificationsQueueEntry.getNotificationsQueueEntryId()));

		List<NotificationsQueueEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationsQueueEntry existingNotificationsQueueEntry = result.get(0);

		Assert.assertEquals(
			existingNotificationsQueueEntry, newNotificationsQueueEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationsQueueEntryId", RandomTestUtil.nextLong()));

		List<NotificationsQueueEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationsQueueEntry newNotificationsQueueEntry =
			addNotificationsQueueEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationsQueueEntryId"));

		Object newNotificationsQueueEntryId =
			newNotificationsQueueEntry.getNotificationsQueueEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationsQueueEntryId",
				new Object[] {newNotificationsQueueEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationsQueueEntryId = result.get(0);

		Assert.assertEquals(
			existingNotificationsQueueEntryId, newNotificationsQueueEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationsQueueEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationsQueueEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationsQueueEntry addNotificationsQueueEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationsQueueEntry notificationsQueueEntry = _persistence.create(
			pk);

		notificationsQueueEntry.setMvccVersion(RandomTestUtil.nextLong());

		notificationsQueueEntry.setGroupId(RandomTestUtil.nextLong());

		notificationsQueueEntry.setCompanyId(RandomTestUtil.nextLong());

		notificationsQueueEntry.setUserId(RandomTestUtil.nextLong());

		notificationsQueueEntry.setUserName(RandomTestUtil.randomString());

		notificationsQueueEntry.setCreateDate(RandomTestUtil.nextDate());

		notificationsQueueEntry.setModifiedDate(RandomTestUtil.nextDate());

		notificationsQueueEntry.setClassNameId(RandomTestUtil.nextLong());

		notificationsQueueEntry.setClassPK(RandomTestUtil.nextLong());

		notificationsQueueEntry.setNotificationsTemplateId(
			RandomTestUtil.nextLong());

		notificationsQueueEntry.setFrom(RandomTestUtil.randomString());

		notificationsQueueEntry.setFromName(RandomTestUtil.randomString());

		notificationsQueueEntry.setTo(RandomTestUtil.randomString());

		notificationsQueueEntry.setToName(RandomTestUtil.randomString());

		notificationsQueueEntry.setCc(RandomTestUtil.randomString());

		notificationsQueueEntry.setBcc(RandomTestUtil.randomString());

		notificationsQueueEntry.setSubject(RandomTestUtil.randomString());

		notificationsQueueEntry.setBody(RandomTestUtil.randomString());

		notificationsQueueEntry.setPriority(RandomTestUtil.nextDouble());

		notificationsQueueEntry.setSent(RandomTestUtil.randomBoolean());

		notificationsQueueEntry.setSentDate(RandomTestUtil.nextDate());

		_notificationsQueueEntries.add(
			_persistence.update(notificationsQueueEntry));

		return notificationsQueueEntry;
	}

	private List<NotificationsQueueEntry> _notificationsQueueEntries =
		new ArrayList<NotificationsQueueEntry>();
	private NotificationsQueueEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}