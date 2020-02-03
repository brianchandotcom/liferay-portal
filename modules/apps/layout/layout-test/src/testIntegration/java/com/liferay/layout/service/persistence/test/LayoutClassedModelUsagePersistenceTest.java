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

package com.liferay.layout.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.exception.NoSuchClassedModelUsageException;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalServiceUtil;
import com.liferay.layout.service.persistence.LayoutClassedModelUsagePersistence;
import com.liferay.layout.service.persistence.LayoutClassedModelUsageUtil;
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
public class LayoutClassedModelUsagePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.service"));

	@Before
	public void setUp() {
		_persistence = LayoutClassedModelUsageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutClassedModelUsage> iterator =
			_layoutClassedModelUsages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutClassedModelUsage layoutClassedModelUsage = _persistence.create(
			pk);

		Assert.assertNotNull(layoutClassedModelUsage);

		Assert.assertEquals(layoutClassedModelUsage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		_persistence.remove(newLayoutClassedModelUsage);

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_persistence.fetchByPrimaryKey(
				newLayoutClassedModelUsage.getPrimaryKey());

		Assert.assertNull(existingLayoutClassedModelUsage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutClassedModelUsage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutClassedModelUsage newLayoutClassedModelUsage =
			_persistence.create(pk);

		newLayoutClassedModelUsage.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setUuid(RandomTestUtil.randomString());

		newLayoutClassedModelUsage.setGroupId(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setCompanyId(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setCreateDate(RandomTestUtil.nextDate());

		newLayoutClassedModelUsage.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutClassedModelUsage.setClassNameId(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setClassPK(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setContainerKey(
			RandomTestUtil.randomString());

		newLayoutClassedModelUsage.setContainerType(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setPlid(RandomTestUtil.nextLong());

		newLayoutClassedModelUsage.setType(RandomTestUtil.nextInt());

		newLayoutClassedModelUsage.setLastPublishDate(
			RandomTestUtil.nextDate());

		_layoutClassedModelUsages.add(
			_persistence.update(newLayoutClassedModelUsage));

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_persistence.findByPrimaryKey(
				newLayoutClassedModelUsage.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutClassedModelUsage.getMvccVersion(),
			newLayoutClassedModelUsage.getMvccVersion());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getUuid(),
			newLayoutClassedModelUsage.getUuid());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getLayoutClassedModelUsageId(),
			newLayoutClassedModelUsage.getLayoutClassedModelUsageId());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getGroupId(),
			newLayoutClassedModelUsage.getGroupId());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getCompanyId(),
			newLayoutClassedModelUsage.getCompanyId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutClassedModelUsage.getCreateDate()),
			Time.getShortTimestamp(newLayoutClassedModelUsage.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutClassedModelUsage.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutClassedModelUsage.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getClassNameId(),
			newLayoutClassedModelUsage.getClassNameId());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getClassPK(),
			newLayoutClassedModelUsage.getClassPK());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getContainerKey(),
			newLayoutClassedModelUsage.getContainerKey());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getContainerType(),
			newLayoutClassedModelUsage.getContainerType());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getPlid(),
			newLayoutClassedModelUsage.getPlid());
		Assert.assertEquals(
			existingLayoutClassedModelUsage.getType(),
			newLayoutClassedModelUsage.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutClassedModelUsage.getLastPublishDate()),
			Time.getShortTimestamp(
				newLayoutClassedModelUsage.getLastPublishDate()));
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
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_T() throws Exception {
		_persistence.countByC_C_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_C_T(0L, 0L, 0);
	}

	@Test
	public void testCountByCK_CT_P() throws Exception {
		_persistence.countByCK_CT_P(
			"", RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByCK_CT_P("null", 0L, 0L);

		_persistence.countByCK_CT_P((String)null, 0L, 0L);
	}

	@Test
	public void testCountByC_C_CK_CT_P() throws Exception {
		_persistence.countByC_C_CK_CT_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C_CK_CT_P(0L, 0L, "null", 0L, 0L);

		_persistence.countByC_C_CK_CT_P(0L, 0L, (String)null, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_persistence.findByPrimaryKey(
				newLayoutClassedModelUsage.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutClassedModelUsage, newLayoutClassedModelUsage);
	}

	@Test(expected = NoSuchClassedModelUsageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutClassedModelUsage>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutClassedModelUsage", "mvccVersion", true, "uuid", true,
			"layoutClassedModelUsageId", true, "groupId", true, "companyId",
			true, "createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "containerKey", true, "containerType", true,
			"plid", true, "type", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_persistence.fetchByPrimaryKey(
				newLayoutClassedModelUsage.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutClassedModelUsage, newLayoutClassedModelUsage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutClassedModelUsage missingLayoutClassedModelUsage =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutClassedModelUsage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutClassedModelUsage newLayoutClassedModelUsage1 =
			addLayoutClassedModelUsage();
		LayoutClassedModelUsage newLayoutClassedModelUsage2 =
			addLayoutClassedModelUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutClassedModelUsage1.getPrimaryKey());
		primaryKeys.add(newLayoutClassedModelUsage2.getPrimaryKey());

		Map<Serializable, LayoutClassedModelUsage> layoutClassedModelUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutClassedModelUsages.size());
		Assert.assertEquals(
			newLayoutClassedModelUsage1,
			layoutClassedModelUsages.get(
				newLayoutClassedModelUsage1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutClassedModelUsage2,
			layoutClassedModelUsages.get(
				newLayoutClassedModelUsage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutClassedModelUsage> layoutClassedModelUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutClassedModelUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutClassedModelUsage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutClassedModelUsage> layoutClassedModelUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutClassedModelUsages.size());
		Assert.assertEquals(
			newLayoutClassedModelUsage,
			layoutClassedModelUsages.get(
				newLayoutClassedModelUsage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutClassedModelUsage> layoutClassedModelUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutClassedModelUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutClassedModelUsage.getPrimaryKey());

		Map<Serializable, LayoutClassedModelUsage> layoutClassedModelUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutClassedModelUsages.size());
		Assert.assertEquals(
			newLayoutClassedModelUsage,
			layoutClassedModelUsages.get(
				newLayoutClassedModelUsage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutClassedModelUsageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutClassedModelUsage>() {

				@Override
				public void performAction(
					LayoutClassedModelUsage layoutClassedModelUsage) {

					Assert.assertNotNull(layoutClassedModelUsage);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutClassedModelUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutClassedModelUsageId",
				newLayoutClassedModelUsage.getLayoutClassedModelUsageId()));

		List<LayoutClassedModelUsage> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutClassedModelUsage existingLayoutClassedModelUsage = result.get(0);

		Assert.assertEquals(
			existingLayoutClassedModelUsage, newLayoutClassedModelUsage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutClassedModelUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutClassedModelUsageId", RandomTestUtil.nextLong()));

		List<LayoutClassedModelUsage> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutClassedModelUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutClassedModelUsageId"));

		Object newLayoutClassedModelUsageId =
			newLayoutClassedModelUsage.getLayoutClassedModelUsageId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutClassedModelUsageId",
				new Object[] {newLayoutClassedModelUsageId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutClassedModelUsageId = result.get(0);

		Assert.assertEquals(
			existingLayoutClassedModelUsageId, newLayoutClassedModelUsageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutClassedModelUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutClassedModelUsageId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutClassedModelUsageId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutClassedModelUsage newLayoutClassedModelUsage =
			addLayoutClassedModelUsage();

		_persistence.clearCache();

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_persistence.findByPrimaryKey(
				newLayoutClassedModelUsage.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutClassedModelUsage.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutClassedModelUsage, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutClassedModelUsage.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutClassedModelUsage, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutClassedModelUsage.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutClassedModelUsage, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutClassedModelUsage.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutClassedModelUsage, "getOriginalClassPK",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayoutClassedModelUsage.getContainerKey(),
				ReflectionTestUtil.invoke(
					existingLayoutClassedModelUsage, "getOriginalContainerKey",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutClassedModelUsage.getContainerType()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutClassedModelUsage, "getOriginalContainerType",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutClassedModelUsage.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutClassedModelUsage, "getOriginalPlid",
				new Class<?>[0]));
	}

	protected LayoutClassedModelUsage addLayoutClassedModelUsage()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutClassedModelUsage layoutClassedModelUsage = _persistence.create(
			pk);

		layoutClassedModelUsage.setMvccVersion(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setUuid(RandomTestUtil.randomString());

		layoutClassedModelUsage.setGroupId(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setCompanyId(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setCreateDate(RandomTestUtil.nextDate());

		layoutClassedModelUsage.setModifiedDate(RandomTestUtil.nextDate());

		layoutClassedModelUsage.setClassNameId(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setClassPK(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setContainerKey(RandomTestUtil.randomString());

		layoutClassedModelUsage.setContainerType(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setPlid(RandomTestUtil.nextLong());

		layoutClassedModelUsage.setType(RandomTestUtil.nextInt());

		layoutClassedModelUsage.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutClassedModelUsages.add(
			_persistence.update(layoutClassedModelUsage));

		return layoutClassedModelUsage;
	}

	private List<LayoutClassedModelUsage> _layoutClassedModelUsages =
		new ArrayList<LayoutClassedModelUsage>();
	private LayoutClassedModelUsagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}