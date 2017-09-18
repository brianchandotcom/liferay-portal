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

package com.liferay.modern.site.building.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalServiceUtil;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentEntryPersistence;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentEntryUtil;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class MSBFragmentEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.modern.site.building.fragment.service"));

	@Before
	public void setUp() {
		_persistence = MSBFragmentEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MSBFragmentEntry> iterator = _msbFragmentEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentEntry msbFragmentEntry = _persistence.create(pk);

		Assert.assertNotNull(msbFragmentEntry);

		Assert.assertEquals(msbFragmentEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		_persistence.remove(newMSBFragmentEntry);

		MSBFragmentEntry existingMSBFragmentEntry = _persistence.fetchByPrimaryKey(newMSBFragmentEntry.getPrimaryKey());

		Assert.assertNull(existingMSBFragmentEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMSBFragmentEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentEntry newMSBFragmentEntry = _persistence.create(pk);

		newMSBFragmentEntry.setGroupId(RandomTestUtil.nextLong());

		newMSBFragmentEntry.setCompanyId(RandomTestUtil.nextLong());

		newMSBFragmentEntry.setUserId(RandomTestUtil.nextLong());

		newMSBFragmentEntry.setUserName(RandomTestUtil.randomString());

		newMSBFragmentEntry.setCreateDate(RandomTestUtil.nextDate());

		newMSBFragmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		newMSBFragmentEntry.setMsbFragmentCollectionId(RandomTestUtil.nextLong());

		newMSBFragmentEntry.setName(RandomTestUtil.randomString());

		newMSBFragmentEntry.setCss(RandomTestUtil.randomString());

		newMSBFragmentEntry.setHtml(RandomTestUtil.randomString());

		newMSBFragmentEntry.setJs(RandomTestUtil.randomString());

		_msbFragmentEntries.add(_persistence.update(newMSBFragmentEntry));

		MSBFragmentEntry existingMSBFragmentEntry = _persistence.findByPrimaryKey(newMSBFragmentEntry.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentEntry.getMsbFragmentEntryId(),
			newMSBFragmentEntry.getMsbFragmentEntryId());
		Assert.assertEquals(existingMSBFragmentEntry.getGroupId(),
			newMSBFragmentEntry.getGroupId());
		Assert.assertEquals(existingMSBFragmentEntry.getCompanyId(),
			newMSBFragmentEntry.getCompanyId());
		Assert.assertEquals(existingMSBFragmentEntry.getUserId(),
			newMSBFragmentEntry.getUserId());
		Assert.assertEquals(existingMSBFragmentEntry.getUserName(),
			newMSBFragmentEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMSBFragmentEntry.getCreateDate()),
			Time.getShortTimestamp(newMSBFragmentEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMSBFragmentEntry.getModifiedDate()),
			Time.getShortTimestamp(newMSBFragmentEntry.getModifiedDate()));
		Assert.assertEquals(existingMSBFragmentEntry.getMsbFragmentCollectionId(),
			newMSBFragmentEntry.getMsbFragmentCollectionId());
		Assert.assertEquals(existingMSBFragmentEntry.getName(),
			newMSBFragmentEntry.getName());
		Assert.assertEquals(existingMSBFragmentEntry.getCss(),
			newMSBFragmentEntry.getCss());
		Assert.assertEquals(existingMSBFragmentEntry.getHtml(),
			newMSBFragmentEntry.getHtml());
		Assert.assertEquals(existingMSBFragmentEntry.getJs(),
			newMSBFragmentEntry.getJs());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByMSBFragmentCollectionId() throws Exception {
		_persistence.countByMSBFragmentCollectionId(RandomTestUtil.nextLong());

		_persistence.countByMSBFragmentCollectionId(0L);
	}

	@Test
	public void testCountByG_MSBFCI() throws Exception {
		_persistence.countByG_MSBFCI(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_MSBFCI(0L, 0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_MSBFCI_LikeN() throws Exception {
		_persistence.countByG_MSBFCI_LikeN(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_MSBFCI_LikeN(0L, 0L, StringPool.NULL);

		_persistence.countByG_MSBFCI_LikeN(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		MSBFragmentEntry existingMSBFragmentEntry = _persistence.findByPrimaryKey(newMSBFragmentEntry.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentEntry, newMSBFragmentEntry);
	}

	@Test(expected = NoSuchFragmentEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MSBFragmentEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MSBFragmentEntry",
			"msbFragmentEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "msbFragmentCollectionId", true, "name",
			true, "css", true, "html", true, "js", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		MSBFragmentEntry existingMSBFragmentEntry = _persistence.fetchByPrimaryKey(newMSBFragmentEntry.getPrimaryKey());

		Assert.assertEquals(existingMSBFragmentEntry, newMSBFragmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentEntry missingMSBFragmentEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMSBFragmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MSBFragmentEntry newMSBFragmentEntry1 = addMSBFragmentEntry();
		MSBFragmentEntry newMSBFragmentEntry2 = addMSBFragmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentEntry1.getPrimaryKey());
		primaryKeys.add(newMSBFragmentEntry2.getPrimaryKey());

		Map<Serializable, MSBFragmentEntry> msbFragmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, msbFragmentEntries.size());
		Assert.assertEquals(newMSBFragmentEntry1,
			msbFragmentEntries.get(newMSBFragmentEntry1.getPrimaryKey()));
		Assert.assertEquals(newMSBFragmentEntry2,
			msbFragmentEntries.get(newMSBFragmentEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MSBFragmentEntry> msbFragmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(msbFragmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MSBFragmentEntry> msbFragmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, msbFragmentEntries.size());
		Assert.assertEquals(newMSBFragmentEntry,
			msbFragmentEntries.get(newMSBFragmentEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MSBFragmentEntry> msbFragmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(msbFragmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMSBFragmentEntry.getPrimaryKey());

		Map<Serializable, MSBFragmentEntry> msbFragmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, msbFragmentEntries.size());
		Assert.assertEquals(newMSBFragmentEntry,
			msbFragmentEntries.get(newMSBFragmentEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MSBFragmentEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<MSBFragmentEntry>() {
				@Override
				public void performAction(MSBFragmentEntry msbFragmentEntry) {
					Assert.assertNotNull(msbFragmentEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("msbFragmentEntryId",
				newMSBFragmentEntry.getMsbFragmentEntryId()));

		List<MSBFragmentEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MSBFragmentEntry existingMSBFragmentEntry = result.get(0);

		Assert.assertEquals(existingMSBFragmentEntry, newMSBFragmentEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("msbFragmentEntryId",
				RandomTestUtil.nextLong()));

		List<MSBFragmentEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"msbFragmentEntryId"));

		Object newMsbFragmentEntryId = newMSBFragmentEntry.getMsbFragmentEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("msbFragmentEntryId",
				new Object[] { newMsbFragmentEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMsbFragmentEntryId = result.get(0);

		Assert.assertEquals(existingMsbFragmentEntryId, newMsbFragmentEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MSBFragmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"msbFragmentEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("msbFragmentEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MSBFragmentEntry newMSBFragmentEntry = addMSBFragmentEntry();

		_persistence.clearCache();

		MSBFragmentEntry existingMSBFragmentEntry = _persistence.findByPrimaryKey(newMSBFragmentEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingMSBFragmentEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingMSBFragmentEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingMSBFragmentEntry.getName(),
				ReflectionTestUtil.invoke(existingMSBFragmentEntry,
					"getOriginalName", new Class<?>[0])));
	}

	protected MSBFragmentEntry addMSBFragmentEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MSBFragmentEntry msbFragmentEntry = _persistence.create(pk);

		msbFragmentEntry.setGroupId(RandomTestUtil.nextLong());

		msbFragmentEntry.setCompanyId(RandomTestUtil.nextLong());

		msbFragmentEntry.setUserId(RandomTestUtil.nextLong());

		msbFragmentEntry.setUserName(RandomTestUtil.randomString());

		msbFragmentEntry.setCreateDate(RandomTestUtil.nextDate());

		msbFragmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		msbFragmentEntry.setMsbFragmentCollectionId(RandomTestUtil.nextLong());

		msbFragmentEntry.setName(RandomTestUtil.randomString());

		msbFragmentEntry.setCss(RandomTestUtil.randomString());

		msbFragmentEntry.setHtml(RandomTestUtil.randomString());

		msbFragmentEntry.setJs(RandomTestUtil.randomString());

		_msbFragmentEntries.add(_persistence.update(msbFragmentEntry));

		return msbFragmentEntry;
	}

	private List<MSBFragmentEntry> _msbFragmentEntries = new ArrayList<MSBFragmentEntry>();
	private MSBFragmentEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}