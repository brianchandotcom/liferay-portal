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

package com.liferay.webhook.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.webhook.exception.NoSuchWebhookEntryException;
import com.liferay.webhook.model.WebhookEntry;
import com.liferay.webhook.service.WebhookEntryLocalServiceUtil;
import com.liferay.webhook.service.persistence.WebhookEntryPersistence;
import com.liferay.webhook.service.persistence.WebhookEntryUtil;

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
public class WebhookEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.webhook.service"));

	@Before
	public void setUp() {
		_persistence = WebhookEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WebhookEntry> iterator = _webhookEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebhookEntry webhookEntry = _persistence.create(pk);

		Assert.assertNotNull(webhookEntry);

		Assert.assertEquals(webhookEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		_persistence.remove(newWebhookEntry);

		WebhookEntry existingWebhookEntry = _persistence.fetchByPrimaryKey(
			newWebhookEntry.getPrimaryKey());

		Assert.assertNull(existingWebhookEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWebhookEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebhookEntry newWebhookEntry = _persistence.create(pk);

		newWebhookEntry.setMvccVersion(RandomTestUtil.nextLong());

		newWebhookEntry.setUuid(RandomTestUtil.randomString());

		newWebhookEntry.setCompanyId(RandomTestUtil.nextLong());

		newWebhookEntry.setUserId(RandomTestUtil.nextLong());

		newWebhookEntry.setUserName(RandomTestUtil.randomString());

		newWebhookEntry.setCreateDate(RandomTestUtil.nextDate());

		newWebhookEntry.setModifiedDate(RandomTestUtil.nextDate());

		newWebhookEntry.setActive(RandomTestUtil.randomBoolean());

		newWebhookEntry.setDestinationName(RandomTestUtil.randomString());

		newWebhookEntry.setDestinationWebhookEventKeys(
			RandomTestUtil.randomString());

		newWebhookEntry.setName(RandomTestUtil.randomString());

		newWebhookEntry.setSecret(RandomTestUtil.randomString());

		newWebhookEntry.setURL(RandomTestUtil.randomString());

		_webhookEntries.add(_persistence.update(newWebhookEntry));

		WebhookEntry existingWebhookEntry = _persistence.findByPrimaryKey(
			newWebhookEntry.getPrimaryKey());

		Assert.assertEquals(
			existingWebhookEntry.getMvccVersion(),
			newWebhookEntry.getMvccVersion());
		Assert.assertEquals(
			existingWebhookEntry.getUuid(), newWebhookEntry.getUuid());
		Assert.assertEquals(
			existingWebhookEntry.getWebhookEntryId(),
			newWebhookEntry.getWebhookEntryId());
		Assert.assertEquals(
			existingWebhookEntry.getCompanyId(),
			newWebhookEntry.getCompanyId());
		Assert.assertEquals(
			existingWebhookEntry.getUserId(), newWebhookEntry.getUserId());
		Assert.assertEquals(
			existingWebhookEntry.getUserName(), newWebhookEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingWebhookEntry.getCreateDate()),
			Time.getShortTimestamp(newWebhookEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingWebhookEntry.getModifiedDate()),
			Time.getShortTimestamp(newWebhookEntry.getModifiedDate()));
		Assert.assertEquals(
			existingWebhookEntry.isActive(), newWebhookEntry.isActive());
		Assert.assertEquals(
			existingWebhookEntry.getDestinationName(),
			newWebhookEntry.getDestinationName());
		Assert.assertEquals(
			existingWebhookEntry.getDestinationWebhookEventKeys(),
			newWebhookEntry.getDestinationWebhookEventKeys());
		Assert.assertEquals(
			existingWebhookEntry.getName(), newWebhookEntry.getName());
		Assert.assertEquals(
			existingWebhookEntry.getSecret(), newWebhookEntry.getSecret());
		Assert.assertEquals(
			existingWebhookEntry.getURL(), newWebhookEntry.getURL());
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
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), "");

		_persistence.countByC_N(0L, "null");

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		WebhookEntry existingWebhookEntry = _persistence.findByPrimaryKey(
			newWebhookEntry.getPrimaryKey());

		Assert.assertEquals(existingWebhookEntry, newWebhookEntry);
	}

	@Test(expected = NoSuchWebhookEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WebhookEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"WebhookEntry", "mvccVersion", true, "uuid", true, "webhookEntryId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "active", true,
			"destinationName", true, "destinationWebhookEventKeys", true,
			"name", true, "secret", true, "url", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		WebhookEntry existingWebhookEntry = _persistence.fetchByPrimaryKey(
			newWebhookEntry.getPrimaryKey());

		Assert.assertEquals(existingWebhookEntry, newWebhookEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebhookEntry missingWebhookEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWebhookEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		WebhookEntry newWebhookEntry1 = addWebhookEntry();
		WebhookEntry newWebhookEntry2 = addWebhookEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebhookEntry1.getPrimaryKey());
		primaryKeys.add(newWebhookEntry2.getPrimaryKey());

		Map<Serializable, WebhookEntry> webhookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, webhookEntries.size());
		Assert.assertEquals(
			newWebhookEntry1,
			webhookEntries.get(newWebhookEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newWebhookEntry2,
			webhookEntries.get(newWebhookEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WebhookEntry> webhookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webhookEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		WebhookEntry newWebhookEntry = addWebhookEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebhookEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WebhookEntry> webhookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webhookEntries.size());
		Assert.assertEquals(
			newWebhookEntry,
			webhookEntries.get(newWebhookEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WebhookEntry> webhookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webhookEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebhookEntry.getPrimaryKey());

		Map<Serializable, WebhookEntry> webhookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webhookEntries.size());
		Assert.assertEquals(
			newWebhookEntry,
			webhookEntries.get(newWebhookEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			WebhookEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WebhookEntry>() {

				@Override
				public void performAction(WebhookEntry webhookEntry) {
					Assert.assertNotNull(webhookEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebhookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webhookEntryId", newWebhookEntry.getWebhookEntryId()));

		List<WebhookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		WebhookEntry existingWebhookEntry = result.get(0);

		Assert.assertEquals(existingWebhookEntry, newWebhookEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebhookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webhookEntryId", RandomTestUtil.nextLong()));

		List<WebhookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebhookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("webhookEntryId"));

		Object newWebhookEntryId = newWebhookEntry.getWebhookEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"webhookEntryId", new Object[] {newWebhookEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWebhookEntryId = result.get(0);

		Assert.assertEquals(existingWebhookEntryId, newWebhookEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebhookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("webhookEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"webhookEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WebhookEntry newWebhookEntry = addWebhookEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newWebhookEntry.getPrimaryKey()));
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

		WebhookEntry newWebhookEntry = addWebhookEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebhookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webhookEntryId", newWebhookEntry.getWebhookEntryId()));

		List<WebhookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(WebhookEntry webhookEntry) {
		Assert.assertEquals(
			Long.valueOf(webhookEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				webhookEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			webhookEntry.getName(),
			ReflectionTestUtil.invoke(
				webhookEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "name"));
	}

	protected WebhookEntry addWebhookEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebhookEntry webhookEntry = _persistence.create(pk);

		webhookEntry.setMvccVersion(RandomTestUtil.nextLong());

		webhookEntry.setUuid(RandomTestUtil.randomString());

		webhookEntry.setCompanyId(RandomTestUtil.nextLong());

		webhookEntry.setUserId(RandomTestUtil.nextLong());

		webhookEntry.setUserName(RandomTestUtil.randomString());

		webhookEntry.setCreateDate(RandomTestUtil.nextDate());

		webhookEntry.setModifiedDate(RandomTestUtil.nextDate());

		webhookEntry.setActive(RandomTestUtil.randomBoolean());

		webhookEntry.setDestinationName(RandomTestUtil.randomString());

		webhookEntry.setDestinationWebhookEventKeys(
			RandomTestUtil.randomString());

		webhookEntry.setName(RandomTestUtil.randomString());

		webhookEntry.setSecret(RandomTestUtil.randomString());

		webhookEntry.setURL(RandomTestUtil.randomString());

		_webhookEntries.add(_persistence.update(webhookEntry));

		return webhookEntry;
	}

	private List<WebhookEntry> _webhookEntries = new ArrayList<WebhookEntry>();
	private WebhookEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}