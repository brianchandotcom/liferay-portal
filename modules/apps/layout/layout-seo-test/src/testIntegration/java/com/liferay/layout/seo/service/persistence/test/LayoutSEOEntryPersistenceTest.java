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
import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryPersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryUtil;
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
public class LayoutSEOEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutSEOEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSEOEntry> iterator = _layoutSEOEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOEntry layoutSEOEntry = _persistence.create(pk);

		Assert.assertNotNull(layoutSEOEntry);

		Assert.assertEquals(layoutSEOEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		_persistence.remove(newLayoutSEOEntry);

		LayoutSEOEntry existingLayoutSEOEntry = _persistence.fetchByPrimaryKey(
			newLayoutSEOEntry.getPrimaryKey());

		Assert.assertNull(existingLayoutSEOEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSEOEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOEntry newLayoutSEOEntry = _persistence.create(pk);

		newLayoutSEOEntry.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSEOEntry.setUuid(RandomTestUtil.randomString());

		newLayoutSEOEntry.setGroupId(RandomTestUtil.nextLong());

		newLayoutSEOEntry.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSEOEntry.setUserId(RandomTestUtil.nextLong());

		newLayoutSEOEntry.setUserName(RandomTestUtil.randomString());

		newLayoutSEOEntry.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSEOEntry.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSEOEntry.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutSEOEntry.setLayoutId(RandomTestUtil.nextLong());

		newLayoutSEOEntry.setEnabled(RandomTestUtil.randomBoolean());

		newLayoutSEOEntry.setCanonicalURL(RandomTestUtil.randomString());

		newLayoutSEOEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutSEOEntries.add(_persistence.update(newLayoutSEOEntry));

		LayoutSEOEntry existingLayoutSEOEntry = _persistence.findByPrimaryKey(
			newLayoutSEOEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOEntry.getMvccVersion(),
			newLayoutSEOEntry.getMvccVersion());
		Assert.assertEquals(
			existingLayoutSEOEntry.getUuid(), newLayoutSEOEntry.getUuid());
		Assert.assertEquals(
			existingLayoutSEOEntry.getLayoutSEOEntryId(),
			newLayoutSEOEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			existingLayoutSEOEntry.getGroupId(),
			newLayoutSEOEntry.getGroupId());
		Assert.assertEquals(
			existingLayoutSEOEntry.getCompanyId(),
			newLayoutSEOEntry.getCompanyId());
		Assert.assertEquals(
			existingLayoutSEOEntry.getUserId(), newLayoutSEOEntry.getUserId());
		Assert.assertEquals(
			existingLayoutSEOEntry.getUserName(),
			newLayoutSEOEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOEntry.getCreateDate()),
			Time.getShortTimestamp(newLayoutSEOEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOEntry.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSEOEntry.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutSEOEntry.isPrivateLayout(),
			newLayoutSEOEntry.isPrivateLayout());
		Assert.assertEquals(
			existingLayoutSEOEntry.getLayoutId(),
			newLayoutSEOEntry.getLayoutId());
		Assert.assertEquals(
			existingLayoutSEOEntry.isEnabled(), newLayoutSEOEntry.isEnabled());
		Assert.assertEquals(
			existingLayoutSEOEntry.getCanonicalURL(),
			newLayoutSEOEntry.getCanonicalURL());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOEntry.getLastPublishDate()),
			Time.getShortTimestamp(newLayoutSEOEntry.getLastPublishDate()));
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
	public void testCountByG_P_L() throws Exception {
		_persistence.countByG_P_L(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_L(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		LayoutSEOEntry existingLayoutSEOEntry = _persistence.findByPrimaryKey(
			newLayoutSEOEntry.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOEntry, newLayoutSEOEntry);
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

	protected OrderByComparator<LayoutSEOEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutSEOEntry", "mvccVersion", true, "uuid", true,
			"layoutSEOEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "privateLayout", true, "layoutId", true,
			"enabled", true, "canonicalURL", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		LayoutSEOEntry existingLayoutSEOEntry = _persistence.fetchByPrimaryKey(
			newLayoutSEOEntry.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOEntry, newLayoutSEOEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOEntry missingLayoutSEOEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingLayoutSEOEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutSEOEntry newLayoutSEOEntry1 = addLayoutSEOEntry();
		LayoutSEOEntry newLayoutSEOEntry2 = addLayoutSEOEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOEntry1.getPrimaryKey());
		primaryKeys.add(newLayoutSEOEntry2.getPrimaryKey());

		Map<Serializable, LayoutSEOEntry> layoutSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSEOEntries.size());
		Assert.assertEquals(
			newLayoutSEOEntry1,
			layoutSEOEntries.get(newLayoutSEOEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutSEOEntry2,
			layoutSEOEntries.get(newLayoutSEOEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSEOEntry> layoutSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSEOEntry> layoutSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOEntries.size());
		Assert.assertEquals(
			newLayoutSEOEntry,
			layoutSEOEntries.get(newLayoutSEOEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSEOEntry> layoutSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOEntry.getPrimaryKey());

		Map<Serializable, LayoutSEOEntry> layoutSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOEntries.size());
		Assert.assertEquals(
			newLayoutSEOEntry,
			layoutSEOEntries.get(newLayoutSEOEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutSEOEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LayoutSEOEntry>() {

				@Override
				public void performAction(LayoutSEOEntry layoutSEOEntry) {
					Assert.assertNotNull(layoutSEOEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOEntryId", newLayoutSEOEntry.getLayoutSEOEntryId()));

		List<LayoutSEOEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSEOEntry existingLayoutSEOEntry = result.get(0);

		Assert.assertEquals(existingLayoutSEOEntry, newLayoutSEOEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOEntryId", RandomTestUtil.nextLong()));

		List<LayoutSEOEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOEntryId"));

		Object newLayoutSEOEntryId = newLayoutSEOEntry.getLayoutSEOEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOEntryId", new Object[] {newLayoutSEOEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSEOEntryId = result.get(0);

		Assert.assertEquals(existingLayoutSEOEntryId, newLayoutSEOEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSEOEntry newLayoutSEOEntry = addLayoutSEOEntry();

		_persistence.clearCache();

		LayoutSEOEntry existingLayoutSEOEntry = _persistence.findByPrimaryKey(
			newLayoutSEOEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutSEOEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutSEOEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutSEOEntry.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutSEOEntry, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOEntry.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOEntry, "getOriginalLayoutId",
				new Class<?>[0]));
	}

	protected LayoutSEOEntry addLayoutSEOEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOEntry layoutSEOEntry = _persistence.create(pk);

		layoutSEOEntry.setMvccVersion(RandomTestUtil.nextLong());

		layoutSEOEntry.setUuid(RandomTestUtil.randomString());

		layoutSEOEntry.setGroupId(RandomTestUtil.nextLong());

		layoutSEOEntry.setCompanyId(RandomTestUtil.nextLong());

		layoutSEOEntry.setUserId(RandomTestUtil.nextLong());

		layoutSEOEntry.setUserName(RandomTestUtil.randomString());

		layoutSEOEntry.setCreateDate(RandomTestUtil.nextDate());

		layoutSEOEntry.setModifiedDate(RandomTestUtil.nextDate());

		layoutSEOEntry.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutSEOEntry.setLayoutId(RandomTestUtil.nextLong());

		layoutSEOEntry.setEnabled(RandomTestUtil.randomBoolean());

		layoutSEOEntry.setCanonicalURL(RandomTestUtil.randomString());

		layoutSEOEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutSEOEntries.add(_persistence.update(layoutSEOEntry));

		return layoutSEOEntry;
	}

	private List<LayoutSEOEntry> _layoutSEOEntries =
		new ArrayList<LayoutSEOEntry>();
	private LayoutSEOEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}