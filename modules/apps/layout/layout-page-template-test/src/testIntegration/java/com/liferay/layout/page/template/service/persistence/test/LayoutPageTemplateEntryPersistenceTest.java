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

package com.liferay.layout.page.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryUtil;
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
public class LayoutPageTemplateEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateEntry> iterator =
			_layoutPageTemplateEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntry layoutPageTemplateEntry = _persistence.create(
			pk);

		Assert.assertNotNull(layoutPageTemplateEntry);

		Assert.assertEquals(layoutPageTemplateEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		_persistence.remove(newLayoutPageTemplateEntry);

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateEntry.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			_persistence.create(pk);

		newLayoutPageTemplateEntry.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setCtCollectionId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setUuid(RandomTestUtil.randomString());

		newLayoutPageTemplateEntry.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setUserName(RandomTestUtil.randomString());

		newLayoutPageTemplateEntry.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateEntry.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setLayoutPageTemplateEntryKey(
			RandomTestUtil.randomString());

		newLayoutPageTemplateEntry.setClassNameId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setClassTypeId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setName(RandomTestUtil.randomString());

		newLayoutPageTemplateEntry.setType(RandomTestUtil.nextInt());

		newLayoutPageTemplateEntry.setPreviewFileEntryId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setDefaultTemplate(
			RandomTestUtil.randomBoolean());

		newLayoutPageTemplateEntry.setLayoutPrototypeId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setPlid(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setLastPublishDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateEntry.setStatus(RandomTestUtil.nextInt());

		newLayoutPageTemplateEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntry.setStatusByUserName(
			RandomTestUtil.randomString());

		newLayoutPageTemplateEntry.setStatusDate(RandomTestUtil.nextDate());

		_layoutPageTemplateEntries.add(
			_persistence.update(newLayoutPageTemplateEntry));

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getMvccVersion(),
			newLayoutPageTemplateEntry.getMvccVersion());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getCtCollectionId(),
			newLayoutPageTemplateEntry.getCtCollectionId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getUuid(),
			newLayoutPageTemplateEntry.getUuid());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			newLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getGroupId(),
			newLayoutPageTemplateEntry.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getCompanyId(),
			newLayoutPageTemplateEntry.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getUserId(),
			newLayoutPageTemplateEntry.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getUserName(),
			newLayoutPageTemplateEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntry.getCreateDate()),
			Time.getShortTimestamp(newLayoutPageTemplateEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateEntry.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getLayoutPageTemplateCollectionId(),
			newLayoutPageTemplateEntry.getLayoutPageTemplateCollectionId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryKey(),
			newLayoutPageTemplateEntry.getLayoutPageTemplateEntryKey());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getClassNameId(),
			newLayoutPageTemplateEntry.getClassNameId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getClassTypeId(),
			newLayoutPageTemplateEntry.getClassTypeId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getName(),
			newLayoutPageTemplateEntry.getName());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getType(),
			newLayoutPageTemplateEntry.getType());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getPreviewFileEntryId(),
			newLayoutPageTemplateEntry.getPreviewFileEntryId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.isDefaultTemplate(),
			newLayoutPageTemplateEntry.isDefaultTemplate());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getLayoutPrototypeId(),
			newLayoutPageTemplateEntry.getLayoutPrototypeId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getPlid(),
			newLayoutPageTemplateEntry.getPlid());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntry.getLastPublishDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateEntry.getLastPublishDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getStatus(),
			newLayoutPageTemplateEntry.getStatus());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getStatusByUserId(),
			newLayoutPageTemplateEntry.getStatusByUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntry.getStatusByUserName(),
			newLayoutPageTemplateEntry.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntry.getStatusDate()),
			Time.getShortTimestamp(newLayoutPageTemplateEntry.getStatusDate()));
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
	public void testCountByLayoutPrototypeId() throws Exception {
		_persistence.countByLayoutPrototypeId(RandomTestUtil.nextLong());

		_persistence.countByLayoutPrototypeId(0L);
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByG_L() throws Exception {
		_persistence.countByG_L(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_L(0L, 0L);
	}

	@Test
	public void testCountByG_LPTEK() throws Exception {
		_persistence.countByG_LPTEK(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LPTEK(0L, "null");

		_persistence.countByG_LPTEK(0L, (String)null);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), "");

		_persistence.countByG_N(0L, "null");

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_T(0L, 0);
	}

	@Test
	public void testCountByG_L_LikeN() throws Exception {
		_persistence.countByG_L_LikeN(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_L_LikeN(0L, 0L, "null");

		_persistence.countByG_L_LikeN(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_L_T() throws Exception {
		_persistence.countByG_L_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_L_T(0L, 0L, 0);
	}

	@Test
	public void testCountByG_L_S() throws Exception {
		_persistence.countByG_L_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_L_S(0L, 0L, 0);
	}

	@Test
	public void testCountByG_N_T() throws Exception {
		_persistence.countByG_N_T(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByG_N_T(0L, "null", 0);

		_persistence.countByG_N_T(0L, (String)null, 0);
	}

	@Test
	public void testCountByG_T_LikeN() throws Exception {
		_persistence.countByG_T_LikeN(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByG_T_LikeN(0L, "null", 0);

		_persistence.countByG_T_LikeN(0L, (String)null, 0);
	}

	@Test
	public void testCountByG_T_S() throws Exception {
		_persistence.countByG_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_T_S(0L, 0, 0);
	}

	@Test
	public void testCountByG_L_LikeN_S() throws Exception {
		_persistence.countByG_L_LikeN_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_L_LikeN_S(0L, 0L, "null", 0);

		_persistence.countByG_L_LikeN_S(0L, 0L, (String)null, 0);
	}

	@Test
	public void testCountByG_C_C_T() throws Exception {
		_persistence.countByG_C_C_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_C_C_T(0L, 0L, 0L, 0);
	}

	@Test
	public void testCountByG_C_C_D() throws Exception {
		_persistence.countByG_C_C_D(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_D(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_C_T_D() throws Exception {
		_persistence.countByG_C_T_D(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.randomBoolean());

		_persistence.countByG_C_T_D(0L, 0L, 0, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_T_LikeN_S() throws Exception {
		_persistence.countByG_T_LikeN_S(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_T_LikeN_S(0L, "null", 0, 0);

		_persistence.countByG_T_LikeN_S(0L, (String)null, 0, 0);
	}

	@Test
	public void testCountByG_T_D_S() throws Exception {
		_persistence.countByG_T_D_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

		_persistence.countByG_T_D_S(0L, 0, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByG_C_C_LikeN_T() throws Exception {
		_persistence.countByG_C_C_LikeN_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByG_C_C_LikeN_T(0L, 0L, 0L, "null", 0);

		_persistence.countByG_C_C_LikeN_T(0L, 0L, 0L, (String)null, 0);
	}

	@Test
	public void testCountByG_C_C_T_S() throws Exception {
		_persistence.countByG_C_C_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_C_C_T_S(0L, 0L, 0L, 0, 0);
	}

	@Test
	public void testCountByG_C_C_D_S() throws Exception {
		_persistence.countByG_C_C_D_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextInt());

		_persistence.countByG_C_C_D_S(
			0L, 0L, 0L, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByG_C_C_LikeN_T_S() throws Exception {
		_persistence.countByG_C_C_LikeN_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_C_C_LikeN_T_S(0L, 0L, 0L, "null", 0, 0);

		_persistence.countByG_C_C_LikeN_T_S(0L, 0L, 0L, (String)null, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntry, newLayoutPageTemplateEntry);
	}

	@Test(expected = NoSuchPageTemplateEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateEntry", "mvccVersion", true, "ctCollectionId",
			true, "uuid", true, "layoutPageTemplateEntryId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true,
			"layoutPageTemplateCollectionId", true,
			"layoutPageTemplateEntryKey", true, "classNameId", true,
			"classTypeId", true, "name", true, "type", true,
			"previewFileEntryId", true, "defaultTemplate", true,
			"layoutPrototypeId", true, "plid", true, "lastPublishDate", true,
			"status", true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntry, newLayoutPageTemplateEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntry missingLayoutPageTemplateEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateEntry newLayoutPageTemplateEntry1 =
			addLayoutPageTemplateEntry();
		LayoutPageTemplateEntry newLayoutPageTemplateEntry2 =
			addLayoutPageTemplateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntry1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateEntry2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateEntries.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntry1,
			layoutPageTemplateEntries.get(
				newLayoutPageTemplateEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateEntry2,
			layoutPageTemplateEntries.get(
				newLayoutPageTemplateEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateEntries.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntry,
			layoutPageTemplateEntries.get(
				newLayoutPageTemplateEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntry.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateEntries.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntry,
			layoutPageTemplateEntries.get(
				newLayoutPageTemplateEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateEntry>() {

				@Override
				public void performAction(
					LayoutPageTemplateEntry layoutPageTemplateEntry) {

					Assert.assertNotNull(layoutPageTemplateEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateEntryId",
				newLayoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		List<LayoutPageTemplateEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateEntry, newLayoutPageTemplateEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateEntryId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateEntryId"));

		Object newLayoutPageTemplateEntryId =
			newLayoutPageTemplateEntry.getLayoutPageTemplateEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateEntryId",
				new Object[] {newLayoutPageTemplateEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateEntryId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateEntryId, newLayoutPageTemplateEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateEntry newLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry();

		_persistence.clearCache();

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateEntry, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateEntry.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateEntry, "getOriginalPlid",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateEntry, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryKey(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateEntry,
					"getOriginalLayoutPageTemplateEntryKey", new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateEntry, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateEntry.getName(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateEntry, "getOriginalName",
					new Class<?>[0])));
		Assert.assertEquals(
			Integer.valueOf(existingLayoutPageTemplateEntry.getType()),
			ReflectionTestUtil.<Integer>invoke(
				existingLayoutPageTemplateEntry, "getOriginalType",
				new Class<?>[0]));
	}

	protected LayoutPageTemplateEntry addLayoutPageTemplateEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntry layoutPageTemplateEntry = _persistence.create(
			pk);

		layoutPageTemplateEntry.setMvccVersion(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setCtCollectionId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setUuid(RandomTestUtil.randomString());

		layoutPageTemplateEntry.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateEntry.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateEntry.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setLayoutPageTemplateEntryKey(
			RandomTestUtil.randomString());

		layoutPageTemplateEntry.setClassNameId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setClassTypeId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setName(RandomTestUtil.randomString());

		layoutPageTemplateEntry.setType(RandomTestUtil.nextInt());

		layoutPageTemplateEntry.setPreviewFileEntryId(
			RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setDefaultTemplate(
			RandomTestUtil.randomBoolean());

		layoutPageTemplateEntry.setLayoutPrototypeId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setPlid(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setLastPublishDate(RandomTestUtil.nextDate());

		layoutPageTemplateEntry.setStatus(RandomTestUtil.nextInt());

		layoutPageTemplateEntry.setStatusByUserId(RandomTestUtil.nextLong());

		layoutPageTemplateEntry.setStatusByUserName(
			RandomTestUtil.randomString());

		layoutPageTemplateEntry.setStatusDate(RandomTestUtil.nextDate());

		_layoutPageTemplateEntries.add(
			_persistence.update(layoutPageTemplateEntry));

		return layoutPageTemplateEntry;
	}

	private List<LayoutPageTemplateEntry> _layoutPageTemplateEntries =
		new ArrayList<LayoutPageTemplateEntry>();
	private LayoutPageTemplateEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}