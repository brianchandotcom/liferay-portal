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
import com.liferay.layout.seo.exception.NoSuchOpenGraphEntryException;
import com.liferay.layout.seo.model.LayoutSEOOpenGraphEntry;
import com.liferay.layout.seo.service.LayoutSEOOpenGraphEntryLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutSEOOpenGraphEntryPersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOOpenGraphEntryUtil;
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
public class LayoutSEOOpenGraphEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutSEOOpenGraphEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSEOOpenGraphEntry> iterator =
			_layoutSEOOpenGraphEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = _persistence.create(
			pk);

		Assert.assertNotNull(layoutSEOOpenGraphEntry);

		Assert.assertEquals(layoutSEOOpenGraphEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		_persistence.remove(newLayoutSEOOpenGraphEntry);

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Assert.assertNull(existingLayoutSEOOpenGraphEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSEOOpenGraphEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			_persistence.create(pk);

		newLayoutSEOOpenGraphEntry.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSEOOpenGraphEntry.setUuid(RandomTestUtil.randomString());

		newLayoutSEOOpenGraphEntry.setGroupId(RandomTestUtil.nextLong());

		newLayoutSEOOpenGraphEntry.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSEOOpenGraphEntry.setUserId(RandomTestUtil.nextLong());

		newLayoutSEOOpenGraphEntry.setUserName(RandomTestUtil.randomString());

		newLayoutSEOOpenGraphEntry.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSEOOpenGraphEntry.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSEOOpenGraphEntry.setPrivateLayout(
			RandomTestUtil.randomBoolean());

		newLayoutSEOOpenGraphEntry.setLayoutId(RandomTestUtil.nextLong());

		newLayoutSEOOpenGraphEntry.setEnabled(RandomTestUtil.randomBoolean());

		newLayoutSEOOpenGraphEntry.setCanonicalURL(
			RandomTestUtil.randomString());

		newLayoutSEOOpenGraphEntry.setLastPublishDate(
			RandomTestUtil.nextDate());

		_layoutSEOOpenGraphEntries.add(
			_persistence.update(newLayoutSEOOpenGraphEntry));

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getMvccVersion(),
			newLayoutSEOOpenGraphEntry.getMvccVersion());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getUuid(),
			newLayoutSEOOpenGraphEntry.getUuid());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getLayoutSEOEntryId(),
			newLayoutSEOOpenGraphEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getGroupId(),
			newLayoutSEOOpenGraphEntry.getGroupId());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getCompanyId(),
			newLayoutSEOOpenGraphEntry.getCompanyId());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getUserId(),
			newLayoutSEOOpenGraphEntry.getUserId());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getUserName(),
			newLayoutSEOOpenGraphEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOOpenGraphEntry.getCreateDate()),
			Time.getShortTimestamp(newLayoutSEOOpenGraphEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOOpenGraphEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutSEOOpenGraphEntry.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.isPrivateLayout(),
			newLayoutSEOOpenGraphEntry.isPrivateLayout());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getLayoutId(),
			newLayoutSEOOpenGraphEntry.getLayoutId());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.isEnabled(),
			newLayoutSEOOpenGraphEntry.isEnabled());
		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry.getCanonicalURL(),
			newLayoutSEOOpenGraphEntry.getCanonicalURL());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOOpenGraphEntry.getLastPublishDate()),
			Time.getShortTimestamp(
				newLayoutSEOOpenGraphEntry.getLastPublishDate()));
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
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry, newLayoutSEOOpenGraphEntry);
	}

	@Test(expected = NoSuchOpenGraphEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutSEOOpenGraphEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutSEOOpenGraphEntry", "mvccVersion", true, "uuid", true,
			"layoutSEOEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "privateLayout", true, "layoutId", true,
			"enabled", true, "canonicalURL", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry, newLayoutSEOOpenGraphEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOOpenGraphEntry missingLayoutSEOOpenGraphEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSEOOpenGraphEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry1 =
			addLayoutSEOOpenGraphEntry();
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry2 =
			addLayoutSEOOpenGraphEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOOpenGraphEntry1.getPrimaryKey());
		primaryKeys.add(newLayoutSEOOpenGraphEntry2.getPrimaryKey());

		Map<Serializable, LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSEOOpenGraphEntries.size());
		Assert.assertEquals(
			newLayoutSEOOpenGraphEntry1,
			layoutSEOOpenGraphEntries.get(
				newLayoutSEOOpenGraphEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutSEOOpenGraphEntry2,
			layoutSEOOpenGraphEntries.get(
				newLayoutSEOOpenGraphEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOOpenGraphEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOOpenGraphEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOOpenGraphEntries.size());
		Assert.assertEquals(
			newLayoutSEOOpenGraphEntry,
			layoutSEOOpenGraphEntries.get(
				newLayoutSEOOpenGraphEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOOpenGraphEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Map<Serializable, LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOOpenGraphEntries.size());
		Assert.assertEquals(
			newLayoutSEOOpenGraphEntry,
			layoutSEOOpenGraphEntries.get(
				newLayoutSEOOpenGraphEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutSEOOpenGraphEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutSEOOpenGraphEntry>() {

				@Override
				public void performAction(
					LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry) {

					Assert.assertNotNull(layoutSEOOpenGraphEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOOpenGraphEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOEntryId",
				newLayoutSEOOpenGraphEntry.getLayoutSEOEntryId()));

		List<LayoutSEOOpenGraphEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry = result.get(0);

		Assert.assertEquals(
			existingLayoutSEOOpenGraphEntry, newLayoutSEOOpenGraphEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOOpenGraphEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOEntryId", RandomTestUtil.nextLong()));

		List<LayoutSEOOpenGraphEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOOpenGraphEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOEntryId"));

		Object newLayoutSEOEntryId =
			newLayoutSEOOpenGraphEntry.getLayoutSEOEntryId();

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
			LayoutSEOOpenGraphEntry.class, _dynamicQueryClassLoader);

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
		LayoutSEOOpenGraphEntry newLayoutSEOOpenGraphEntry =
			addLayoutSEOOpenGraphEntry();

		_persistence.clearCache();

		LayoutSEOOpenGraphEntry existingLayoutSEOOpenGraphEntry =
			_persistence.findByPrimaryKey(
				newLayoutSEOOpenGraphEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutSEOOpenGraphEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutSEOOpenGraphEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOOpenGraphEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOOpenGraphEntry, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOOpenGraphEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOOpenGraphEntry, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutSEOOpenGraphEntry.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutSEOOpenGraphEntry, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOOpenGraphEntry.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOOpenGraphEntry, "getOriginalLayoutId",
				new Class<?>[0]));
	}

	protected LayoutSEOOpenGraphEntry addLayoutSEOOpenGraphEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = _persistence.create(
			pk);

		layoutSEOOpenGraphEntry.setMvccVersion(RandomTestUtil.nextLong());

		layoutSEOOpenGraphEntry.setUuid(RandomTestUtil.randomString());

		layoutSEOOpenGraphEntry.setGroupId(RandomTestUtil.nextLong());

		layoutSEOOpenGraphEntry.setCompanyId(RandomTestUtil.nextLong());

		layoutSEOOpenGraphEntry.setUserId(RandomTestUtil.nextLong());

		layoutSEOOpenGraphEntry.setUserName(RandomTestUtil.randomString());

		layoutSEOOpenGraphEntry.setCreateDate(RandomTestUtil.nextDate());

		layoutSEOOpenGraphEntry.setModifiedDate(RandomTestUtil.nextDate());

		layoutSEOOpenGraphEntry.setPrivateLayout(
			RandomTestUtil.randomBoolean());

		layoutSEOOpenGraphEntry.setLayoutId(RandomTestUtil.nextLong());

		layoutSEOOpenGraphEntry.setEnabled(RandomTestUtil.randomBoolean());

		layoutSEOOpenGraphEntry.setCanonicalURL(RandomTestUtil.randomString());

		layoutSEOOpenGraphEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutSEOOpenGraphEntries.add(
			_persistence.update(layoutSEOOpenGraphEntry));

		return layoutSEOOpenGraphEntry;
	}

	private List<LayoutSEOOpenGraphEntry> _layoutSEOOpenGraphEntries =
		new ArrayList<LayoutSEOOpenGraphEntry>();
	private LayoutSEOOpenGraphEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}