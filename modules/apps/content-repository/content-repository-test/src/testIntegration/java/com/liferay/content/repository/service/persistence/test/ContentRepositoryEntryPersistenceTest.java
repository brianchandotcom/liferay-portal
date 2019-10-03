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

package com.liferay.content.repository.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.repository.exception.NoSuchEntryException;
import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.service.ContentRepositoryEntryLocalServiceUtil;
import com.liferay.content.repository.service.persistence.ContentRepositoryEntryPersistence;
import com.liferay.content.repository.service.persistence.ContentRepositoryEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class ContentRepositoryEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.content.repository.service"));

	@Before
	public void setUp() {
		_persistence = ContentRepositoryEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ContentRepositoryEntry> iterator =
			_contentRepositoryEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ContentRepositoryEntry contentRepositoryEntry = _persistence.create(pk);

		Assert.assertNotNull(contentRepositoryEntry);

		Assert.assertEquals(contentRepositoryEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		_persistence.remove(newContentRepositoryEntry);

		ContentRepositoryEntry existingContentRepositoryEntry =
			_persistence.fetchByPrimaryKey(
				newContentRepositoryEntry.getPrimaryKey());

		Assert.assertNull(existingContentRepositoryEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addContentRepositoryEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ContentRepositoryEntry newContentRepositoryEntry = _persistence.create(
			pk);

		newContentRepositoryEntry.setMvccVersion(RandomTestUtil.nextLong());

		newContentRepositoryEntry.setUuid(RandomTestUtil.randomString());

		newContentRepositoryEntry.setCompanyId(RandomTestUtil.nextLong());

		newContentRepositoryEntry.setUserId(RandomTestUtil.nextLong());

		newContentRepositoryEntry.setCreateDate(RandomTestUtil.nextDate());

		newContentRepositoryEntry.setModifiedDate(RandomTestUtil.nextDate());

		newContentRepositoryEntry.setGroupId(RandomTestUtil.nextLong());

		_contentRepositoryEntries.add(
			_persistence.update(newContentRepositoryEntry));

		ContentRepositoryEntry existingContentRepositoryEntry =
			_persistence.findByPrimaryKey(
				newContentRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(
			existingContentRepositoryEntry.getMvccVersion(),
			newContentRepositoryEntry.getMvccVersion());
		Assert.assertEquals(
			existingContentRepositoryEntry.getUuid(),
			newContentRepositoryEntry.getUuid());
		Assert.assertEquals(
			existingContentRepositoryEntry.getContentRepositoryEntryId(),
			newContentRepositoryEntry.getContentRepositoryEntryId());
		Assert.assertEquals(
			existingContentRepositoryEntry.getCompanyId(),
			newContentRepositoryEntry.getCompanyId());
		Assert.assertEquals(
			existingContentRepositoryEntry.getUserId(),
			newContentRepositoryEntry.getUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingContentRepositoryEntry.getCreateDate()),
			Time.getShortTimestamp(newContentRepositoryEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingContentRepositoryEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newContentRepositoryEntry.getModifiedDate()));
		Assert.assertEquals(
			existingContentRepositoryEntry.getGroupId(),
			newContentRepositoryEntry.getGroupId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		ContentRepositoryEntry existingContentRepositoryEntry =
			_persistence.findByPrimaryKey(
				newContentRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(
			existingContentRepositoryEntry, newContentRepositoryEntry);
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

	protected OrderByComparator<ContentRepositoryEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ContentRepositoryEntry", "mvccVersion", true, "uuid", true,
			"contentRepositoryEntryId", true, "companyId", true, "userId", true,
			"createDate", true, "modifiedDate", true, "groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		ContentRepositoryEntry existingContentRepositoryEntry =
			_persistence.fetchByPrimaryKey(
				newContentRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(
			existingContentRepositoryEntry, newContentRepositoryEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ContentRepositoryEntry missingContentRepositoryEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingContentRepositoryEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ContentRepositoryEntry newContentRepositoryEntry1 =
			addContentRepositoryEntry();
		ContentRepositoryEntry newContentRepositoryEntry2 =
			addContentRepositoryEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContentRepositoryEntry1.getPrimaryKey());
		primaryKeys.add(newContentRepositoryEntry2.getPrimaryKey());

		Map<Serializable, ContentRepositoryEntry> contentRepositoryEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, contentRepositoryEntries.size());
		Assert.assertEquals(
			newContentRepositoryEntry1,
			contentRepositoryEntries.get(
				newContentRepositoryEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newContentRepositoryEntry2,
			contentRepositoryEntries.get(
				newContentRepositoryEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ContentRepositoryEntry> contentRepositoryEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(contentRepositoryEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContentRepositoryEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ContentRepositoryEntry> contentRepositoryEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, contentRepositoryEntries.size());
		Assert.assertEquals(
			newContentRepositoryEntry,
			contentRepositoryEntries.get(
				newContentRepositoryEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ContentRepositoryEntry> contentRepositoryEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(contentRepositoryEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContentRepositoryEntry.getPrimaryKey());

		Map<Serializable, ContentRepositoryEntry> contentRepositoryEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, contentRepositoryEntries.size());
		Assert.assertEquals(
			newContentRepositoryEntry,
			contentRepositoryEntries.get(
				newContentRepositoryEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ContentRepositoryEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ContentRepositoryEntry>() {

				@Override
				public void performAction(
					ContentRepositoryEntry contentRepositoryEntry) {

					Assert.assertNotNull(contentRepositoryEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ContentRepositoryEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"contentRepositoryEntryId",
				newContentRepositoryEntry.getContentRepositoryEntryId()));

		List<ContentRepositoryEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ContentRepositoryEntry existingContentRepositoryEntry = result.get(0);

		Assert.assertEquals(
			existingContentRepositoryEntry, newContentRepositoryEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ContentRepositoryEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"contentRepositoryEntryId", RandomTestUtil.nextLong()));

		List<ContentRepositoryEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ContentRepositoryEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("contentRepositoryEntryId"));

		Object newContentRepositoryEntryId =
			newContentRepositoryEntry.getContentRepositoryEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"contentRepositoryEntryId",
				new Object[] {newContentRepositoryEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentRepositoryEntryId = result.get(0);

		Assert.assertEquals(
			existingContentRepositoryEntryId, newContentRepositoryEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ContentRepositoryEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("contentRepositoryEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"contentRepositoryEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ContentRepositoryEntry newContentRepositoryEntry =
			addContentRepositoryEntry();

		_persistence.clearCache();

		ContentRepositoryEntry existingContentRepositoryEntry =
			_persistence.findByPrimaryKey(
				newContentRepositoryEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingContentRepositoryEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingContentRepositoryEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingContentRepositoryEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingContentRepositoryEntry, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected ContentRepositoryEntry addContentRepositoryEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		ContentRepositoryEntry contentRepositoryEntry = _persistence.create(pk);

		contentRepositoryEntry.setMvccVersion(RandomTestUtil.nextLong());

		contentRepositoryEntry.setUuid(RandomTestUtil.randomString());

		contentRepositoryEntry.setCompanyId(RandomTestUtil.nextLong());

		contentRepositoryEntry.setUserId(RandomTestUtil.nextLong());

		contentRepositoryEntry.setCreateDate(RandomTestUtil.nextDate());

		contentRepositoryEntry.setModifiedDate(RandomTestUtil.nextDate());

		contentRepositoryEntry.setGroupId(RandomTestUtil.nextLong());

		_contentRepositoryEntries.add(
			_persistence.update(contentRepositoryEntry));

		return contentRepositoryEntry;
	}

	private List<ContentRepositoryEntry> _contentRepositoryEntries =
		new ArrayList<ContentRepositoryEntry>();
	private ContentRepositoryEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}