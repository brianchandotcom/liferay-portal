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

package com.liferay.web.hook.service.persistence.test;

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
import com.liferay.web.hook.exception.NoSuchEntryException;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalServiceUtil;
import com.liferay.web.hook.service.persistence.WebHookEntryPersistence;
import com.liferay.web.hook.service.persistence.WebHookEntryUtil;

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
public class WebHookEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.web.hook.service"));

	@Before
	public void setUp() {
		_persistence = WebHookEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WebHookEntry> iterator = _webHookEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebHookEntry webHookEntry = _persistence.create(pk);

		Assert.assertNotNull(webHookEntry);

		Assert.assertEquals(webHookEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		_persistence.remove(newWebHookEntry);

		WebHookEntry existingWebHookEntry = _persistence.fetchByPrimaryKey(
			newWebHookEntry.getPrimaryKey());

		Assert.assertNull(existingWebHookEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWebHookEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebHookEntry newWebHookEntry = _persistence.create(pk);

		newWebHookEntry.setMvccVersion(RandomTestUtil.nextLong());

		newWebHookEntry.setUuid(RandomTestUtil.randomString());

		newWebHookEntry.setCompanyId(RandomTestUtil.nextLong());

		newWebHookEntry.setUserId(RandomTestUtil.nextLong());

		newWebHookEntry.setUserName(RandomTestUtil.randomString());

		newWebHookEntry.setCreateDate(RandomTestUtil.nextDate());

		newWebHookEntry.setModifiedDate(RandomTestUtil.nextDate());

		newWebHookEntry.setName(RandomTestUtil.randomString());

		newWebHookEntry.setDestination(RandomTestUtil.randomString());

		newWebHookEntry.setUrl(RandomTestUtil.randomString());

		_webHookEntries.add(_persistence.update(newWebHookEntry));

		WebHookEntry existingWebHookEntry = _persistence.findByPrimaryKey(
			newWebHookEntry.getPrimaryKey());

		Assert.assertEquals(
			existingWebHookEntry.getMvccVersion(),
			newWebHookEntry.getMvccVersion());
		Assert.assertEquals(
			existingWebHookEntry.getUuid(), newWebHookEntry.getUuid());
		Assert.assertEquals(
			existingWebHookEntry.getWebHookEntryId(),
			newWebHookEntry.getWebHookEntryId());
		Assert.assertEquals(
			existingWebHookEntry.getCompanyId(),
			newWebHookEntry.getCompanyId());
		Assert.assertEquals(
			existingWebHookEntry.getUserId(), newWebHookEntry.getUserId());
		Assert.assertEquals(
			existingWebHookEntry.getUserName(), newWebHookEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingWebHookEntry.getCreateDate()),
			Time.getShortTimestamp(newWebHookEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingWebHookEntry.getModifiedDate()),
			Time.getShortTimestamp(newWebHookEntry.getModifiedDate()));
		Assert.assertEquals(
			existingWebHookEntry.getName(), newWebHookEntry.getName());
		Assert.assertEquals(
			existingWebHookEntry.getDestination(),
			newWebHookEntry.getDestination());
		Assert.assertEquals(
			existingWebHookEntry.getUrl(), newWebHookEntry.getUrl());
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
	public void testCountByC_D_U() throws Exception {
		_persistence.countByC_D_U(RandomTestUtil.nextLong(), "", "");

		_persistence.countByC_D_U(0L, "null", "null");

		_persistence.countByC_D_U(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		WebHookEntry existingWebHookEntry = _persistence.findByPrimaryKey(
			newWebHookEntry.getPrimaryKey());

		Assert.assertEquals(existingWebHookEntry, newWebHookEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WebHookEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"WebHookEntry", "mvccVersion", true, "uuid", true, "webHookEntryId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "name", true,
			"destination", true, "url", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		WebHookEntry existingWebHookEntry = _persistence.fetchByPrimaryKey(
			newWebHookEntry.getPrimaryKey());

		Assert.assertEquals(existingWebHookEntry, newWebHookEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebHookEntry missingWebHookEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWebHookEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		WebHookEntry newWebHookEntry1 = addWebHookEntry();
		WebHookEntry newWebHookEntry2 = addWebHookEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebHookEntry1.getPrimaryKey());
		primaryKeys.add(newWebHookEntry2.getPrimaryKey());

		Map<Serializable, WebHookEntry> webHookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, webHookEntries.size());
		Assert.assertEquals(
			newWebHookEntry1,
			webHookEntries.get(newWebHookEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newWebHookEntry2,
			webHookEntries.get(newWebHookEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WebHookEntry> webHookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webHookEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		WebHookEntry newWebHookEntry = addWebHookEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebHookEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WebHookEntry> webHookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webHookEntries.size());
		Assert.assertEquals(
			newWebHookEntry,
			webHookEntries.get(newWebHookEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WebHookEntry> webHookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webHookEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebHookEntry.getPrimaryKey());

		Map<Serializable, WebHookEntry> webHookEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webHookEntries.size());
		Assert.assertEquals(
			newWebHookEntry,
			webHookEntries.get(newWebHookEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			WebHookEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WebHookEntry>() {

				@Override
				public void performAction(WebHookEntry webHookEntry) {
					Assert.assertNotNull(webHookEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebHookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webHookEntryId", newWebHookEntry.getWebHookEntryId()));

		List<WebHookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		WebHookEntry existingWebHookEntry = result.get(0);

		Assert.assertEquals(existingWebHookEntry, newWebHookEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebHookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webHookEntryId", RandomTestUtil.nextLong()));

		List<WebHookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebHookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("webHookEntryId"));

		Object newWebHookEntryId = newWebHookEntry.getWebHookEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"webHookEntryId", new Object[] {newWebHookEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWebHookEntryId = result.get(0);

		Assert.assertEquals(existingWebHookEntryId, newWebHookEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebHookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("webHookEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"webHookEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WebHookEntry newWebHookEntry = addWebHookEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newWebHookEntry.getPrimaryKey()));
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

		WebHookEntry newWebHookEntry = addWebHookEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WebHookEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"webHookEntryId", newWebHookEntry.getWebHookEntryId()));

		List<WebHookEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(WebHookEntry webHookEntry) {
		Assert.assertEquals(
			Long.valueOf(webHookEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				webHookEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			webHookEntry.getDestination(),
			ReflectionTestUtil.invoke(
				webHookEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "destination"));
		Assert.assertEquals(
			webHookEntry.getUrl(),
			ReflectionTestUtil.invoke(
				webHookEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "url"));
	}

	protected WebHookEntry addWebHookEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebHookEntry webHookEntry = _persistence.create(pk);

		webHookEntry.setMvccVersion(RandomTestUtil.nextLong());

		webHookEntry.setUuid(RandomTestUtil.randomString());

		webHookEntry.setCompanyId(RandomTestUtil.nextLong());

		webHookEntry.setUserId(RandomTestUtil.nextLong());

		webHookEntry.setUserName(RandomTestUtil.randomString());

		webHookEntry.setCreateDate(RandomTestUtil.nextDate());

		webHookEntry.setModifiedDate(RandomTestUtil.nextDate());

		webHookEntry.setName(RandomTestUtil.randomString());

		webHookEntry.setDestination(RandomTestUtil.randomString());

		webHookEntry.setUrl(RandomTestUtil.randomString());

		_webHookEntries.add(_persistence.update(webHookEntry));

		return webHookEntry;
	}

	private List<WebHookEntry> _webHookEntries = new ArrayList<WebHookEntry>();
	private WebHookEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}