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

package com.liferay.layout.seo.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.exception.NoSuchSiteEntryException;
import com.liferay.layout.seo.model.LayoutSEOSiteEntry;
import com.liferay.layout.seo.service.LayoutSEOSiteEntryLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutSEOSiteEntryPersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOSiteEntryUtil;
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
public class LayoutSEOSiteEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutSEOSiteEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSEOSiteEntry> iterator =
			_layoutSEOSiteEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSiteEntry layoutSEOSiteEntry = _persistence.create(pk);

		Assert.assertNotNull(layoutSEOSiteEntry);

		Assert.assertEquals(layoutSEOSiteEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		_persistence.remove(newLayoutSEOSiteEntry);

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOSiteEntry.getPrimaryKey());

		Assert.assertNull(existingLayoutSEOSiteEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSEOSiteEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSiteEntry newLayoutSEOSiteEntry = _persistence.create(pk);

		newLayoutSEOSiteEntry.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSEOSiteEntry.setUuid(RandomTestUtil.randomString());

		newLayoutSEOSiteEntry.setGroupId(RandomTestUtil.nextLong());

		newLayoutSEOSiteEntry.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSEOSiteEntry.setUserId(RandomTestUtil.nextLong());

		newLayoutSEOSiteEntry.setUserName(RandomTestUtil.randomString());

		newLayoutSEOSiteEntry.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSEOSiteEntry.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSEOSiteEntry.setOpenGraphEnabled(
			RandomTestUtil.randomBoolean());

		newLayoutSEOSiteEntry.setOpenGraphImageFileEntryId(
			RandomTestUtil.nextLong());

		_layoutSEOSiteEntries.add(_persistence.update(newLayoutSEOSiteEntry));

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOSiteEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getMvccVersion(),
			newLayoutSEOSiteEntry.getMvccVersion());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getUuid(),
			newLayoutSEOSiteEntry.getUuid());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getLayoutSEOSiteEntryId(),
			newLayoutSEOSiteEntry.getLayoutSEOSiteEntryId());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getGroupId(),
			newLayoutSEOSiteEntry.getGroupId());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getCompanyId(),
			newLayoutSEOSiteEntry.getCompanyId());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getUserId(),
			newLayoutSEOSiteEntry.getUserId());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getUserName(),
			newLayoutSEOSiteEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOSiteEntry.getCreateDate()),
			Time.getShortTimestamp(newLayoutSEOSiteEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOSiteEntry.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSEOSiteEntry.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.isOpenGraphEnabled(),
			newLayoutSEOSiteEntry.isOpenGraphEnabled());
		Assert.assertEquals(
			existingLayoutSEOSiteEntry.getOpenGraphImageFileEntryId(),
			newLayoutSEOSiteEntry.getOpenGraphImageFileEntryId());
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOSiteEntry.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOSiteEntry, newLayoutSEOSiteEntry);
	}

	@Test(expected = NoSuchSiteEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutSEOSiteEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutSEOSiteEntry", "mvccVersion", true, "uuid", true,
			"layoutSEOSiteEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "openGraphEnabled", true,
			"openGraphImageFileEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOSiteEntry.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOSiteEntry, newLayoutSEOSiteEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSiteEntry missingLayoutSEOSiteEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSEOSiteEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutSEOSiteEntry newLayoutSEOSiteEntry1 = addLayoutSEOSiteEntry();
		LayoutSEOSiteEntry newLayoutSEOSiteEntry2 = addLayoutSEOSiteEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSiteEntry1.getPrimaryKey());
		primaryKeys.add(newLayoutSEOSiteEntry2.getPrimaryKey());

		Map<Serializable, LayoutSEOSiteEntry> layoutSEOSiteEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSEOSiteEntries.size());
		Assert.assertEquals(
			newLayoutSEOSiteEntry1,
			layoutSEOSiteEntries.get(newLayoutSEOSiteEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutSEOSiteEntry2,
			layoutSEOSiteEntries.get(newLayoutSEOSiteEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSEOSiteEntry> layoutSEOSiteEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOSiteEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSiteEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSEOSiteEntry> layoutSEOSiteEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOSiteEntries.size());
		Assert.assertEquals(
			newLayoutSEOSiteEntry,
			layoutSEOSiteEntries.get(newLayoutSEOSiteEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSEOSiteEntry> layoutSEOSiteEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOSiteEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSiteEntry.getPrimaryKey());

		Map<Serializable, LayoutSEOSiteEntry> layoutSEOSiteEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOSiteEntries.size());
		Assert.assertEquals(
			newLayoutSEOSiteEntry,
			layoutSEOSiteEntries.get(newLayoutSEOSiteEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutSEOSiteEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutSEOSiteEntry>() {

				@Override
				public void performAction(
					LayoutSEOSiteEntry layoutSEOSiteEntry) {

					Assert.assertNotNull(layoutSEOSiteEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSiteEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOSiteEntryId",
				newLayoutSEOSiteEntry.getLayoutSEOSiteEntryId()));

		List<LayoutSEOSiteEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry = result.get(0);

		Assert.assertEquals(existingLayoutSEOSiteEntry, newLayoutSEOSiteEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSiteEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOSiteEntryId", RandomTestUtil.nextLong()));

		List<LayoutSEOSiteEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSiteEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOSiteEntryId"));

		Object newLayoutSEOSiteEntryId =
			newLayoutSEOSiteEntry.getLayoutSEOSiteEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOSiteEntryId",
				new Object[] {newLayoutSEOSiteEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSEOSiteEntryId = result.get(0);

		Assert.assertEquals(
			existingLayoutSEOSiteEntryId, newLayoutSEOSiteEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSiteEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOSiteEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOSiteEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSEOSiteEntry newLayoutSEOSiteEntry = addLayoutSEOSiteEntry();

		_persistence.clearCache();

		LayoutSEOSiteEntry existingLayoutSEOSiteEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOSiteEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutSEOSiteEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutSEOSiteEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOSiteEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOSiteEntry, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOSiteEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOSiteEntry, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected LayoutSEOSiteEntry addLayoutSEOSiteEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSiteEntry layoutSEOSiteEntry = _persistence.create(pk);

		layoutSEOSiteEntry.setMvccVersion(RandomTestUtil.nextLong());

		layoutSEOSiteEntry.setUuid(RandomTestUtil.randomString());

		layoutSEOSiteEntry.setGroupId(RandomTestUtil.nextLong());

		layoutSEOSiteEntry.setCompanyId(RandomTestUtil.nextLong());

		layoutSEOSiteEntry.setUserId(RandomTestUtil.nextLong());

		layoutSEOSiteEntry.setUserName(RandomTestUtil.randomString());

		layoutSEOSiteEntry.setCreateDate(RandomTestUtil.nextDate());

		layoutSEOSiteEntry.setModifiedDate(RandomTestUtil.nextDate());

		layoutSEOSiteEntry.setOpenGraphEnabled(RandomTestUtil.randomBoolean());

		layoutSEOSiteEntry.setOpenGraphImageFileEntryId(
			RandomTestUtil.nextLong());

		_layoutSEOSiteEntries.add(_persistence.update(layoutSEOSiteEntry));

		return layoutSEOSiteEntry;
	}

	private List<LayoutSEOSiteEntry> _layoutSEOSiteEntries =
		new ArrayList<LayoutSEOSiteEntry>();
	private LayoutSEOSiteEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}