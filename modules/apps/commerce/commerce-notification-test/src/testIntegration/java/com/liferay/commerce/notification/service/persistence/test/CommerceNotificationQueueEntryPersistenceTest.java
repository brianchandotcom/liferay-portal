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

package com.liferay.commerce.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalServiceUtil;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationQueueEntryPersistence;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationQueueEntryUtil;
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
public class CommerceNotificationQueueEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.notification.service"));

	@Before
	public void setUp() {
		_persistence = CommerceNotificationQueueEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceNotificationQueueEntry> iterator =
			_commerceNotificationQueueEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			_persistence.create(pk);

		Assert.assertNotNull(commerceNotificationQueueEntry);

		Assert.assertEquals(commerceNotificationQueueEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		_persistence.remove(newCommerceNotificationQueueEntry);

		CommerceNotificationQueueEntry existingCommerceNotificationQueueEntry =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationQueueEntry.getPrimaryKey());

		Assert.assertNull(existingCommerceNotificationQueueEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceNotificationQueueEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			_persistence.create(pk);

		newCommerceNotificationQueueEntry.setGroupId(RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setUserId(RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setUserName(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationQueueEntry.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationQueueEntry.setClassNameId(
			RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setClassPK(RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setCommerceNotificationTemplateId(
			RandomTestUtil.nextLong());

		newCommerceNotificationQueueEntry.setFrom(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setFromName(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setTo(RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setToName(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setCc(RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setBcc(RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setSubject(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setBody(
			RandomTestUtil.randomString());

		newCommerceNotificationQueueEntry.setPriority(
			RandomTestUtil.nextDouble());

		newCommerceNotificationQueueEntry.setSent(
			RandomTestUtil.randomBoolean());

		newCommerceNotificationQueueEntry.setSentDate(
			RandomTestUtil.nextDate());

		_commerceNotificationQueueEntries.add(
			_persistence.update(newCommerceNotificationQueueEntry));

		CommerceNotificationQueueEntry existingCommerceNotificationQueueEntry =
			_persistence.findByPrimaryKey(
				newCommerceNotificationQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.
				getCommerceNotificationQueueEntryId(),
			newCommerceNotificationQueueEntry.
				getCommerceNotificationQueueEntryId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getGroupId(),
			newCommerceNotificationQueueEntry.getGroupId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getCompanyId(),
			newCommerceNotificationQueueEntry.getCompanyId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getUserId(),
			newCommerceNotificationQueueEntry.getUserId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getUserName(),
			newCommerceNotificationQueueEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationQueueEntry.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceNotificationQueueEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationQueueEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceNotificationQueueEntry.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getClassNameId(),
			newCommerceNotificationQueueEntry.getClassNameId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getClassPK(),
			newCommerceNotificationQueueEntry.getClassPK());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.
				getCommerceNotificationTemplateId(),
			newCommerceNotificationQueueEntry.
				getCommerceNotificationTemplateId());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getFrom(),
			newCommerceNotificationQueueEntry.getFrom());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getFromName(),
			newCommerceNotificationQueueEntry.getFromName());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getTo(),
			newCommerceNotificationQueueEntry.getTo());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getToName(),
			newCommerceNotificationQueueEntry.getToName());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getCc(),
			newCommerceNotificationQueueEntry.getCc());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getBcc(),
			newCommerceNotificationQueueEntry.getBcc());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getSubject(),
			newCommerceNotificationQueueEntry.getSubject());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.getBody(),
			newCommerceNotificationQueueEntry.getBody());
		AssertUtils.assertEquals(
			existingCommerceNotificationQueueEntry.getPriority(),
			newCommerceNotificationQueueEntry.getPriority());
		Assert.assertEquals(
			existingCommerceNotificationQueueEntry.isSent(),
			newCommerceNotificationQueueEntry.isSent());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationQueueEntry.getSentDate()),
			Time.getShortTimestamp(
				newCommerceNotificationQueueEntry.getSentDate()));
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCommerceNotificationTemplateId() throws Exception {
		_persistence.countByCommerceNotificationTemplateId(
			RandomTestUtil.nextLong());

		_persistence.countByCommerceNotificationTemplateId(0L);
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
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		CommerceNotificationQueueEntry existingCommerceNotificationQueueEntry =
			_persistence.findByPrimaryKey(
				newCommerceNotificationQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationQueueEntry,
			newCommerceNotificationQueueEntry);
	}

	@Test(expected = NoSuchNotificationQueueEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceNotificationQueueEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CommerceNotificationQueueEntry",
			"commerceNotificationQueueEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"commerceNotificationTemplateId", true, "from", true, "fromName",
			true, "to", true, "toName", true, "cc", true, "bcc", true,
			"subject", true, "priority", true, "sent", true, "sentDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		CommerceNotificationQueueEntry existingCommerceNotificationQueueEntry =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationQueueEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationQueueEntry,
			newCommerceNotificationQueueEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationQueueEntry missingCommerceNotificationQueueEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceNotificationQueueEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry1 =
			addCommerceNotificationQueueEntry();
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry2 =
			addCommerceNotificationQueueEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationQueueEntry1.getPrimaryKey());
		primaryKeys.add(newCommerceNotificationQueueEntry2.getPrimaryKey());

		Map<Serializable, CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceNotificationQueueEntries.size());
		Assert.assertEquals(
			newCommerceNotificationQueueEntry1,
			commerceNotificationQueueEntries.get(
				newCommerceNotificationQueueEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceNotificationQueueEntry2,
			commerceNotificationQueueEntries.get(
				newCommerceNotificationQueueEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationQueueEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationQueueEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationQueueEntries.size());
		Assert.assertEquals(
			newCommerceNotificationQueueEntry,
			commerceNotificationQueueEntries.get(
				newCommerceNotificationQueueEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationQueueEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationQueueEntry.getPrimaryKey());

		Map<Serializable, CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationQueueEntries.size());
		Assert.assertEquals(
			newCommerceNotificationQueueEntry,
			commerceNotificationQueueEntries.get(
				newCommerceNotificationQueueEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceNotificationQueueEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceNotificationQueueEntry>() {

				@Override
				public void performAction(
					CommerceNotificationQueueEntry
						commerceNotificationQueueEntry) {

					Assert.assertNotNull(commerceNotificationQueueEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationQueueEntryId",
				newCommerceNotificationQueueEntry.
					getCommerceNotificationQueueEntryId()));

		List<CommerceNotificationQueueEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceNotificationQueueEntry existingCommerceNotificationQueueEntry =
			result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationQueueEntry,
			newCommerceNotificationQueueEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationQueueEntryId", RandomTestUtil.nextLong()));

		List<CommerceNotificationQueueEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceNotificationQueueEntry newCommerceNotificationQueueEntry =
			addCommerceNotificationQueueEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationQueueEntryId"));

		Object newCommerceNotificationQueueEntryId =
			newCommerceNotificationQueueEntry.
				getCommerceNotificationQueueEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationQueueEntryId",
				new Object[] {newCommerceNotificationQueueEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceNotificationQueueEntryId = result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationQueueEntryId,
			newCommerceNotificationQueueEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationQueueEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationQueueEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationQueueEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceNotificationQueueEntry addCommerceNotificationQueueEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			_persistence.create(pk);

		commerceNotificationQueueEntry.setGroupId(RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setCompanyId(RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setUserId(RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setUserName(
			RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setCreateDate(RandomTestUtil.nextDate());

		commerceNotificationQueueEntry.setModifiedDate(
			RandomTestUtil.nextDate());

		commerceNotificationQueueEntry.setClassNameId(
			RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setClassPK(RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setCommerceNotificationTemplateId(
			RandomTestUtil.nextLong());

		commerceNotificationQueueEntry.setFrom(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setFromName(
			RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setTo(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setToName(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setCc(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setBcc(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setSubject(
			RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setBody(RandomTestUtil.randomString());

		commerceNotificationQueueEntry.setPriority(RandomTestUtil.nextDouble());

		commerceNotificationQueueEntry.setSent(RandomTestUtil.randomBoolean());

		commerceNotificationQueueEntry.setSentDate(RandomTestUtil.nextDate());

		_commerceNotificationQueueEntries.add(
			_persistence.update(commerceNotificationQueueEntry));

		return commerceNotificationQueueEntry;
	}

	private List<CommerceNotificationQueueEntry>
		_commerceNotificationQueueEntries =
			new ArrayList<CommerceNotificationQueueEntry>();
	private CommerceNotificationQueueEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}