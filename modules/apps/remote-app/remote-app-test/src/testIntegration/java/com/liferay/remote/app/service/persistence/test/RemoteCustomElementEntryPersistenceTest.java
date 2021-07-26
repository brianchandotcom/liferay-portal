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

package com.liferay.remote.app.service.persistence.test;

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
import com.liferay.remote.app.exception.NoSuchRemoteCustomElementEntryException;
import com.liferay.remote.app.model.RemoteCustomElementEntry;
import com.liferay.remote.app.service.RemoteCustomElementEntryLocalServiceUtil;
import com.liferay.remote.app.service.persistence.RemoteCustomElementEntryPersistence;
import com.liferay.remote.app.service.persistence.RemoteCustomElementEntryUtil;

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
public class RemoteCustomElementEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.remote.app.service"));

	@Before
	public void setUp() {
		_persistence = RemoteCustomElementEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RemoteCustomElementEntry> iterator =
			_remoteCustomElementEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteCustomElementEntry remoteCustomElementEntry = _persistence.create(
			pk);

		Assert.assertNotNull(remoteCustomElementEntry);

		Assert.assertEquals(remoteCustomElementEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		_persistence.remove(newRemoteCustomElementEntry);

		RemoteCustomElementEntry existingRemoteCustomElementEntry =
			_persistence.fetchByPrimaryKey(
				newRemoteCustomElementEntry.getPrimaryKey());

		Assert.assertNull(existingRemoteCustomElementEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRemoteCustomElementEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteCustomElementEntry newRemoteCustomElementEntry =
			_persistence.create(pk);

		newRemoteCustomElementEntry.setMvccVersion(RandomTestUtil.nextLong());

		newRemoteCustomElementEntry.setUuid(RandomTestUtil.randomString());

		newRemoteCustomElementEntry.setCompanyId(RandomTestUtil.nextLong());

		newRemoteCustomElementEntry.setUserId(RandomTestUtil.nextLong());

		newRemoteCustomElementEntry.setUserName(RandomTestUtil.randomString());

		newRemoteCustomElementEntry.setCreateDate(RandomTestUtil.nextDate());

		newRemoteCustomElementEntry.setModifiedDate(RandomTestUtil.nextDate());

		newRemoteCustomElementEntry.setName(RandomTestUtil.randomString());

		newRemoteCustomElementEntry.setTagName(RandomTestUtil.randomString());

		newRemoteCustomElementEntry.setUrl(RandomTestUtil.randomString());

		_remoteCustomElementEntries.add(
			_persistence.update(newRemoteCustomElementEntry));

		RemoteCustomElementEntry existingRemoteCustomElementEntry =
			_persistence.findByPrimaryKey(
				newRemoteCustomElementEntry.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteCustomElementEntry.getMvccVersion(),
			newRemoteCustomElementEntry.getMvccVersion());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getUuid(),
			newRemoteCustomElementEntry.getUuid());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getRemoteCustomElementEntryId(),
			newRemoteCustomElementEntry.getRemoteCustomElementEntryId());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getCompanyId(),
			newRemoteCustomElementEntry.getCompanyId());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getUserId(),
			newRemoteCustomElementEntry.getUserId());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getUserName(),
			newRemoteCustomElementEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingRemoteCustomElementEntry.getCreateDate()),
			Time.getShortTimestamp(
				newRemoteCustomElementEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingRemoteCustomElementEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newRemoteCustomElementEntry.getModifiedDate()));
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getName(),
			newRemoteCustomElementEntry.getName());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getTagName(),
			newRemoteCustomElementEntry.getTagName());
		Assert.assertEquals(
			existingRemoteCustomElementEntry.getUrl(),
			newRemoteCustomElementEntry.getUrl());
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
	public void testCountByC_U() throws Exception {
		_persistence.countByC_U(RandomTestUtil.nextLong(), "");

		_persistence.countByC_U(0L, "null");

		_persistence.countByC_U(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		RemoteCustomElementEntry existingRemoteCustomElementEntry =
			_persistence.findByPrimaryKey(
				newRemoteCustomElementEntry.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteCustomElementEntry, newRemoteCustomElementEntry);
	}

	@Test(expected = NoSuchRemoteCustomElementEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<RemoteCustomElementEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"RemoteCustomElementEntry", "mvccVersion", true, "uuid", true,
			"remoteCustomElementEntryId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true, "tagName", true, "url", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		RemoteCustomElementEntry existingRemoteCustomElementEntry =
			_persistence.fetchByPrimaryKey(
				newRemoteCustomElementEntry.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteCustomElementEntry, newRemoteCustomElementEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteCustomElementEntry missingRemoteCustomElementEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRemoteCustomElementEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		RemoteCustomElementEntry newRemoteCustomElementEntry1 =
			addRemoteCustomElementEntry();
		RemoteCustomElementEntry newRemoteCustomElementEntry2 =
			addRemoteCustomElementEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteCustomElementEntry1.getPrimaryKey());
		primaryKeys.add(newRemoteCustomElementEntry2.getPrimaryKey());

		Map<Serializable, RemoteCustomElementEntry> remoteCustomElementEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, remoteCustomElementEntries.size());
		Assert.assertEquals(
			newRemoteCustomElementEntry1,
			remoteCustomElementEntries.get(
				newRemoteCustomElementEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newRemoteCustomElementEntry2,
			remoteCustomElementEntries.get(
				newRemoteCustomElementEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RemoteCustomElementEntry> remoteCustomElementEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(remoteCustomElementEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteCustomElementEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RemoteCustomElementEntry> remoteCustomElementEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, remoteCustomElementEntries.size());
		Assert.assertEquals(
			newRemoteCustomElementEntry,
			remoteCustomElementEntries.get(
				newRemoteCustomElementEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RemoteCustomElementEntry> remoteCustomElementEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(remoteCustomElementEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteCustomElementEntry.getPrimaryKey());

		Map<Serializable, RemoteCustomElementEntry> remoteCustomElementEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, remoteCustomElementEntries.size());
		Assert.assertEquals(
			newRemoteCustomElementEntry,
			remoteCustomElementEntries.get(
				newRemoteCustomElementEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			RemoteCustomElementEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<RemoteCustomElementEntry>() {

				@Override
				public void performAction(
					RemoteCustomElementEntry remoteCustomElementEntry) {

					Assert.assertNotNull(remoteCustomElementEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteCustomElementEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteCustomElementEntryId",
				newRemoteCustomElementEntry.getRemoteCustomElementEntryId()));

		List<RemoteCustomElementEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RemoteCustomElementEntry existingRemoteCustomElementEntry = result.get(
			0);

		Assert.assertEquals(
			existingRemoteCustomElementEntry, newRemoteCustomElementEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteCustomElementEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteCustomElementEntryId", RandomTestUtil.nextLong()));

		List<RemoteCustomElementEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteCustomElementEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteCustomElementEntryId"));

		Object newRemoteCustomElementEntryId =
			newRemoteCustomElementEntry.getRemoteCustomElementEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteCustomElementEntryId",
				new Object[] {newRemoteCustomElementEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRemoteCustomElementEntryId = result.get(0);

		Assert.assertEquals(
			existingRemoteCustomElementEntryId, newRemoteCustomElementEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteCustomElementEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteCustomElementEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteCustomElementEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newRemoteCustomElementEntry.getPrimaryKey()));
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

		RemoteCustomElementEntry newRemoteCustomElementEntry =
			addRemoteCustomElementEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteCustomElementEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteCustomElementEntryId",
				newRemoteCustomElementEntry.getRemoteCustomElementEntryId()));

		List<RemoteCustomElementEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		RemoteCustomElementEntry remoteCustomElementEntry) {

		Assert.assertEquals(
			Long.valueOf(remoteCustomElementEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				remoteCustomElementEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			remoteCustomElementEntry.getUrl(),
			ReflectionTestUtil.invoke(
				remoteCustomElementEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "url"));
	}

	protected RemoteCustomElementEntry addRemoteCustomElementEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		RemoteCustomElementEntry remoteCustomElementEntry = _persistence.create(
			pk);

		remoteCustomElementEntry.setMvccVersion(RandomTestUtil.nextLong());

		remoteCustomElementEntry.setUuid(RandomTestUtil.randomString());

		remoteCustomElementEntry.setCompanyId(RandomTestUtil.nextLong());

		remoteCustomElementEntry.setUserId(RandomTestUtil.nextLong());

		remoteCustomElementEntry.setUserName(RandomTestUtil.randomString());

		remoteCustomElementEntry.setCreateDate(RandomTestUtil.nextDate());

		remoteCustomElementEntry.setModifiedDate(RandomTestUtil.nextDate());

		remoteCustomElementEntry.setName(RandomTestUtil.randomString());

		remoteCustomElementEntry.setTagName(RandomTestUtil.randomString());

		remoteCustomElementEntry.setUrl(RandomTestUtil.randomString());

		_remoteCustomElementEntries.add(
			_persistence.update(remoteCustomElementEntry));

		return remoteCustomElementEntry;
	}

	private List<RemoteCustomElementEntry> _remoteCustomElementEntries =
		new ArrayList<RemoteCustomElementEntry>();
	private RemoteCustomElementEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}