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
import com.liferay.remote.app.exception.NoSuchRemoteAppEntryException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalServiceUtil;
import com.liferay.remote.app.service.persistence.RemoteAppEntryPersistence;
import com.liferay.remote.app.service.persistence.RemoteAppEntryUtil;

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
public class RemoteAppEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.remote.app.service"));

	@Before
	public void setUp() {
		_persistence = RemoteAppEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RemoteAppEntry> iterator = _remoteAppEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntry remoteAppEntry = _persistence.create(pk);

		Assert.assertNotNull(remoteAppEntry);

		Assert.assertEquals(remoteAppEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		_persistence.remove(newRemoteAppEntry);

		RemoteAppEntry existingRemoteAppEntry = _persistence.fetchByPrimaryKey(
			newRemoteAppEntry.getPrimaryKey());

		Assert.assertNull(existingRemoteAppEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRemoteAppEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntry newRemoteAppEntry = _persistence.create(pk);

		newRemoteAppEntry.setMvccVersion(RandomTestUtil.nextLong());

		newRemoteAppEntry.setUuid(RandomTestUtil.randomString());

		newRemoteAppEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newRemoteAppEntry.setCompanyId(RandomTestUtil.nextLong());

		newRemoteAppEntry.setUserId(RandomTestUtil.nextLong());

		newRemoteAppEntry.setUserName(RandomTestUtil.randomString());

		newRemoteAppEntry.setCreateDate(RandomTestUtil.nextDate());

		newRemoteAppEntry.setModifiedDate(RandomTestUtil.nextDate());

		newRemoteAppEntry.setCustomElementCSSURLs(
			RandomTestUtil.randomString());

		newRemoteAppEntry.setCustomElementHTMLElementName(
			RandomTestUtil.randomString());

		newRemoteAppEntry.setCustomElementURLs(RandomTestUtil.randomString());

		newRemoteAppEntry.setCustomElementUseESM(
			RandomTestUtil.randomBoolean());

		newRemoteAppEntry.setDescription(RandomTestUtil.randomString());

		newRemoteAppEntry.setFriendlyURLMapping(RandomTestUtil.randomString());

		newRemoteAppEntry.setIFrameURL(RandomTestUtil.randomString());

		newRemoteAppEntry.setInstanceable(RandomTestUtil.randomBoolean());

		newRemoteAppEntry.setName(RandomTestUtil.randomString());

		newRemoteAppEntry.setPortletCategoryName(RandomTestUtil.randomString());

		newRemoteAppEntry.setProperties(RandomTestUtil.randomString());

		newRemoteAppEntry.setSourceCodeURL(RandomTestUtil.randomString());

		newRemoteAppEntry.setType(RandomTestUtil.randomString());

		newRemoteAppEntry.setStatus(RandomTestUtil.nextInt());

		newRemoteAppEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newRemoteAppEntry.setStatusByUserName(RandomTestUtil.randomString());

		newRemoteAppEntry.setStatusDate(RandomTestUtil.nextDate());

		_remoteAppEntries.add(_persistence.update(newRemoteAppEntry));

		RemoteAppEntry existingRemoteAppEntry = _persistence.findByPrimaryKey(
			newRemoteAppEntry.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteAppEntry.getMvccVersion(),
			newRemoteAppEntry.getMvccVersion());
		Assert.assertEquals(
			existingRemoteAppEntry.getUuid(), newRemoteAppEntry.getUuid());
		Assert.assertEquals(
			existingRemoteAppEntry.getExternalReferenceCode(),
			newRemoteAppEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingRemoteAppEntry.getRemoteAppEntryId(),
			newRemoteAppEntry.getRemoteAppEntryId());
		Assert.assertEquals(
			existingRemoteAppEntry.getCompanyId(),
			newRemoteAppEntry.getCompanyId());
		Assert.assertEquals(
			existingRemoteAppEntry.getUserId(), newRemoteAppEntry.getUserId());
		Assert.assertEquals(
			existingRemoteAppEntry.getUserName(),
			newRemoteAppEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingRemoteAppEntry.getCreateDate()),
			Time.getShortTimestamp(newRemoteAppEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingRemoteAppEntry.getModifiedDate()),
			Time.getShortTimestamp(newRemoteAppEntry.getModifiedDate()));
		Assert.assertEquals(
			existingRemoteAppEntry.getCustomElementCSSURLs(),
			newRemoteAppEntry.getCustomElementCSSURLs());
		Assert.assertEquals(
			existingRemoteAppEntry.getCustomElementHTMLElementName(),
			newRemoteAppEntry.getCustomElementHTMLElementName());
		Assert.assertEquals(
			existingRemoteAppEntry.getCustomElementURLs(),
			newRemoteAppEntry.getCustomElementURLs());
		Assert.assertEquals(
			existingRemoteAppEntry.isCustomElementUseESM(),
			newRemoteAppEntry.isCustomElementUseESM());
		Assert.assertEquals(
			existingRemoteAppEntry.getDescription(),
			newRemoteAppEntry.getDescription());
		Assert.assertEquals(
			existingRemoteAppEntry.getFriendlyURLMapping(),
			newRemoteAppEntry.getFriendlyURLMapping());
		Assert.assertEquals(
			existingRemoteAppEntry.getIFrameURL(),
			newRemoteAppEntry.getIFrameURL());
		Assert.assertEquals(
			existingRemoteAppEntry.isInstanceable(),
			newRemoteAppEntry.isInstanceable());
		Assert.assertEquals(
			existingRemoteAppEntry.getName(), newRemoteAppEntry.getName());
		Assert.assertEquals(
			existingRemoteAppEntry.getPortletCategoryName(),
			newRemoteAppEntry.getPortletCategoryName());
		Assert.assertEquals(
			existingRemoteAppEntry.getProperties(),
			newRemoteAppEntry.getProperties());
		Assert.assertEquals(
			existingRemoteAppEntry.getSourceCodeURL(),
			newRemoteAppEntry.getSourceCodeURL());
		Assert.assertEquals(
			existingRemoteAppEntry.getType(), newRemoteAppEntry.getType());
		Assert.assertEquals(
			existingRemoteAppEntry.getStatus(), newRemoteAppEntry.getStatus());
		Assert.assertEquals(
			existingRemoteAppEntry.getStatusByUserId(),
			newRemoteAppEntry.getStatusByUserId());
		Assert.assertEquals(
			existingRemoteAppEntry.getStatusByUserName(),
			newRemoteAppEntry.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingRemoteAppEntry.getStatusDate()),
			Time.getShortTimestamp(newRemoteAppEntry.getStatusDate()));
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
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		RemoteAppEntry existingRemoteAppEntry = _persistence.findByPrimaryKey(
			newRemoteAppEntry.getPrimaryKey());

		Assert.assertEquals(existingRemoteAppEntry, newRemoteAppEntry);
	}

	@Test(expected = NoSuchRemoteAppEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<RemoteAppEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"RemoteAppEntry", "mvccVersion", true, "uuid", true,
			"externalReferenceCode", true, "remoteAppEntryId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "customElementHTMLElementName", true,
			"customElementUseESM", true, "friendlyURLMapping", true,
			"iFrameURL", true, "instanceable", true, "name", true,
			"portletCategoryName", true, "sourceCodeURL", true, "type", true,
			"status", true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		RemoteAppEntry existingRemoteAppEntry = _persistence.fetchByPrimaryKey(
			newRemoteAppEntry.getPrimaryKey());

		Assert.assertEquals(existingRemoteAppEntry, newRemoteAppEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntry missingRemoteAppEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingRemoteAppEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		RemoteAppEntry newRemoteAppEntry1 = addRemoteAppEntry();
		RemoteAppEntry newRemoteAppEntry2 = addRemoteAppEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntry1.getPrimaryKey());
		primaryKeys.add(newRemoteAppEntry2.getPrimaryKey());

		Map<Serializable, RemoteAppEntry> remoteAppEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, remoteAppEntries.size());
		Assert.assertEquals(
			newRemoteAppEntry1,
			remoteAppEntries.get(newRemoteAppEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newRemoteAppEntry2,
			remoteAppEntries.get(newRemoteAppEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RemoteAppEntry> remoteAppEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(remoteAppEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RemoteAppEntry> remoteAppEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, remoteAppEntries.size());
		Assert.assertEquals(
			newRemoteAppEntry,
			remoteAppEntries.get(newRemoteAppEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RemoteAppEntry> remoteAppEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(remoteAppEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntry.getPrimaryKey());

		Map<Serializable, RemoteAppEntry> remoteAppEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, remoteAppEntries.size());
		Assert.assertEquals(
			newRemoteAppEntry,
			remoteAppEntries.get(newRemoteAppEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			RemoteAppEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<RemoteAppEntry>() {

				@Override
				public void performAction(RemoteAppEntry remoteAppEntry) {
					Assert.assertNotNull(remoteAppEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryId", newRemoteAppEntry.getRemoteAppEntryId()));

		List<RemoteAppEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		RemoteAppEntry existingRemoteAppEntry = result.get(0);

		Assert.assertEquals(existingRemoteAppEntry, newRemoteAppEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryId", RandomTestUtil.nextLong()));

		List<RemoteAppEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteAppEntryId"));

		Object newRemoteAppEntryId = newRemoteAppEntry.getRemoteAppEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteAppEntryId", new Object[] {newRemoteAppEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRemoteAppEntryId = result.get(0);

		Assert.assertEquals(existingRemoteAppEntryId, newRemoteAppEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteAppEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteAppEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newRemoteAppEntry.getPrimaryKey()));
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

		RemoteAppEntry newRemoteAppEntry = addRemoteAppEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryId", newRemoteAppEntry.getRemoteAppEntryId()));

		List<RemoteAppEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(RemoteAppEntry remoteAppEntry) {
		Assert.assertEquals(
			Long.valueOf(remoteAppEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				remoteAppEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			remoteAppEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				remoteAppEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected RemoteAppEntry addRemoteAppEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntry remoteAppEntry = _persistence.create(pk);

		remoteAppEntry.setMvccVersion(RandomTestUtil.nextLong());

		remoteAppEntry.setUuid(RandomTestUtil.randomString());

		remoteAppEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		remoteAppEntry.setCompanyId(RandomTestUtil.nextLong());

		remoteAppEntry.setUserId(RandomTestUtil.nextLong());

		remoteAppEntry.setUserName(RandomTestUtil.randomString());

		remoteAppEntry.setCreateDate(RandomTestUtil.nextDate());

		remoteAppEntry.setModifiedDate(RandomTestUtil.nextDate());

		remoteAppEntry.setCustomElementCSSURLs(RandomTestUtil.randomString());

		remoteAppEntry.setCustomElementHTMLElementName(
			RandomTestUtil.randomString());

		remoteAppEntry.setCustomElementURLs(RandomTestUtil.randomString());

		remoteAppEntry.setCustomElementUseESM(RandomTestUtil.randomBoolean());

		remoteAppEntry.setDescription(RandomTestUtil.randomString());

		remoteAppEntry.setFriendlyURLMapping(RandomTestUtil.randomString());

		remoteAppEntry.setIFrameURL(RandomTestUtil.randomString());

		remoteAppEntry.setInstanceable(RandomTestUtil.randomBoolean());

		remoteAppEntry.setName(RandomTestUtil.randomString());

		remoteAppEntry.setPortletCategoryName(RandomTestUtil.randomString());

		remoteAppEntry.setProperties(RandomTestUtil.randomString());

		remoteAppEntry.setSourceCodeURL(RandomTestUtil.randomString());

		remoteAppEntry.setType(RandomTestUtil.randomString());

		remoteAppEntry.setStatus(RandomTestUtil.nextInt());

		remoteAppEntry.setStatusByUserId(RandomTestUtil.nextLong());

		remoteAppEntry.setStatusByUserName(RandomTestUtil.randomString());

		remoteAppEntry.setStatusDate(RandomTestUtil.nextDate());

		_remoteAppEntries.add(_persistence.update(remoteAppEntry));

		return remoteAppEntry;
	}

	private List<RemoteAppEntry> _remoteAppEntries =
		new ArrayList<RemoteAppEntry>();
	private RemoteAppEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}