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

package com.liferay.info.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.exception.NoSuchItemUsageException;
import com.liferay.info.model.InfoItemUsage;
import com.liferay.info.service.InfoItemUsageLocalServiceUtil;
import com.liferay.info.service.persistence.InfoItemUsagePersistence;
import com.liferay.info.service.persistence.InfoItemUsageUtil;
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
public class InfoItemUsagePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.info.service"));

	@Before
	public void setUp() {
		_persistence = InfoItemUsageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<InfoItemUsage> iterator = _infoItemUsages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		InfoItemUsage infoItemUsage = _persistence.create(pk);

		Assert.assertNotNull(infoItemUsage);

		Assert.assertEquals(infoItemUsage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		_persistence.remove(newInfoItemUsage);

		InfoItemUsage existingInfoItemUsage = _persistence.fetchByPrimaryKey(
			newInfoItemUsage.getPrimaryKey());

		Assert.assertNull(existingInfoItemUsage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addInfoItemUsage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		InfoItemUsage newInfoItemUsage = _persistence.create(pk);

		newInfoItemUsage.setMvccVersion(RandomTestUtil.nextLong());

		newInfoItemUsage.setUuid(RandomTestUtil.randomString());

		newInfoItemUsage.setGroupId(RandomTestUtil.nextLong());

		newInfoItemUsage.setCreateDate(RandomTestUtil.nextDate());

		newInfoItemUsage.setModifiedDate(RandomTestUtil.nextDate());

		newInfoItemUsage.setClassNameId(RandomTestUtil.nextLong());

		newInfoItemUsage.setClassPK(RandomTestUtil.nextLong());

		newInfoItemUsage.setContainerKey(RandomTestUtil.randomString());

		newInfoItemUsage.setContainerType(RandomTestUtil.nextLong());

		newInfoItemUsage.setPlid(RandomTestUtil.nextLong());

		newInfoItemUsage.setType(RandomTestUtil.nextInt());

		newInfoItemUsage.setLastPublishDate(RandomTestUtil.nextDate());

		_infoItemUsages.add(_persistence.update(newInfoItemUsage));

		InfoItemUsage existingInfoItemUsage = _persistence.findByPrimaryKey(
			newInfoItemUsage.getPrimaryKey());

		Assert.assertEquals(
			existingInfoItemUsage.getMvccVersion(),
			newInfoItemUsage.getMvccVersion());
		Assert.assertEquals(
			existingInfoItemUsage.getUuid(), newInfoItemUsage.getUuid());
		Assert.assertEquals(
			existingInfoItemUsage.getInfoItemUsageId(),
			newInfoItemUsage.getInfoItemUsageId());
		Assert.assertEquals(
			existingInfoItemUsage.getGroupId(), newInfoItemUsage.getGroupId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingInfoItemUsage.getCreateDate()),
			Time.getShortTimestamp(newInfoItemUsage.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingInfoItemUsage.getModifiedDate()),
			Time.getShortTimestamp(newInfoItemUsage.getModifiedDate()));
		Assert.assertEquals(
			existingInfoItemUsage.getClassNameId(),
			newInfoItemUsage.getClassNameId());
		Assert.assertEquals(
			existingInfoItemUsage.getClassPK(), newInfoItemUsage.getClassPK());
		Assert.assertEquals(
			existingInfoItemUsage.getContainerKey(),
			newInfoItemUsage.getContainerKey());
		Assert.assertEquals(
			existingInfoItemUsage.getContainerType(),
			newInfoItemUsage.getContainerType());
		Assert.assertEquals(
			existingInfoItemUsage.getPlid(), newInfoItemUsage.getPlid());
		Assert.assertEquals(
			existingInfoItemUsage.getType(), newInfoItemUsage.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(existingInfoItemUsage.getLastPublishDate()),
			Time.getShortTimestamp(newInfoItemUsage.getLastPublishDate()));
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
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		InfoItemUsage existingInfoItemUsage = _persistence.findByPrimaryKey(
			newInfoItemUsage.getPrimaryKey());

		Assert.assertEquals(existingInfoItemUsage, newInfoItemUsage);
	}

	@Test(expected = NoSuchItemUsageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<InfoItemUsage> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"InfoItemUsage", "mvccVersion", true, "uuid", true,
			"infoItemUsageId", true, "groupId", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"containerKey", true, "containerType", true, "plid", true, "type",
			true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		InfoItemUsage existingInfoItemUsage = _persistence.fetchByPrimaryKey(
			newInfoItemUsage.getPrimaryKey());

		Assert.assertEquals(existingInfoItemUsage, newInfoItemUsage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		InfoItemUsage missingInfoItemUsage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingInfoItemUsage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		InfoItemUsage newInfoItemUsage1 = addInfoItemUsage();
		InfoItemUsage newInfoItemUsage2 = addInfoItemUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newInfoItemUsage1.getPrimaryKey());
		primaryKeys.add(newInfoItemUsage2.getPrimaryKey());

		Map<Serializable, InfoItemUsage> infoItemUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, infoItemUsages.size());
		Assert.assertEquals(
			newInfoItemUsage1,
			infoItemUsages.get(newInfoItemUsage1.getPrimaryKey()));
		Assert.assertEquals(
			newInfoItemUsage2,
			infoItemUsages.get(newInfoItemUsage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, InfoItemUsage> infoItemUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(infoItemUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newInfoItemUsage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, InfoItemUsage> infoItemUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, infoItemUsages.size());
		Assert.assertEquals(
			newInfoItemUsage,
			infoItemUsages.get(newInfoItemUsage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, InfoItemUsage> infoItemUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(infoItemUsages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newInfoItemUsage.getPrimaryKey());

		Map<Serializable, InfoItemUsage> infoItemUsages =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, infoItemUsages.size());
		Assert.assertEquals(
			newInfoItemUsage,
			infoItemUsages.get(newInfoItemUsage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			InfoItemUsageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<InfoItemUsage>() {

				@Override
				public void performAction(InfoItemUsage infoItemUsage) {
					Assert.assertNotNull(infoItemUsage);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			InfoItemUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"infoItemUsageId", newInfoItemUsage.getInfoItemUsageId()));

		List<InfoItemUsage> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		InfoItemUsage existingInfoItemUsage = result.get(0);

		Assert.assertEquals(existingInfoItemUsage, newInfoItemUsage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			InfoItemUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"infoItemUsageId", RandomTestUtil.nextLong()));

		List<InfoItemUsage> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			InfoItemUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("infoItemUsageId"));

		Object newInfoItemUsageId = newInfoItemUsage.getInfoItemUsageId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"infoItemUsageId", new Object[] {newInfoItemUsageId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingInfoItemUsageId = result.get(0);

		Assert.assertEquals(existingInfoItemUsageId, newInfoItemUsageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			InfoItemUsage.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("infoItemUsageId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"infoItemUsageId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		InfoItemUsage newInfoItemUsage = addInfoItemUsage();

		_persistence.clearCache();

		InfoItemUsage existingInfoItemUsage = _persistence.findByPrimaryKey(
			newInfoItemUsage.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingInfoItemUsage.getUuid(),
				ReflectionTestUtil.invoke(
					existingInfoItemUsage, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingInfoItemUsage.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingInfoItemUsage, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingInfoItemUsage.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingInfoItemUsage, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingInfoItemUsage.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingInfoItemUsage, "getOriginalClassPK", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingInfoItemUsage.getContainerKey(),
				ReflectionTestUtil.invoke(
					existingInfoItemUsage, "getOriginalContainerKey",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingInfoItemUsage.getContainerType()),
			ReflectionTestUtil.<Long>invoke(
				existingInfoItemUsage, "getOriginalContainerType",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingInfoItemUsage.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				existingInfoItemUsage, "getOriginalPlid", new Class<?>[0]));
	}

	protected InfoItemUsage addInfoItemUsage() throws Exception {
		long pk = RandomTestUtil.nextLong();

		InfoItemUsage infoItemUsage = _persistence.create(pk);

		infoItemUsage.setMvccVersion(RandomTestUtil.nextLong());

		infoItemUsage.setUuid(RandomTestUtil.randomString());

		infoItemUsage.setGroupId(RandomTestUtil.nextLong());

		infoItemUsage.setCreateDate(RandomTestUtil.nextDate());

		infoItemUsage.setModifiedDate(RandomTestUtil.nextDate());

		infoItemUsage.setClassNameId(RandomTestUtil.nextLong());

		infoItemUsage.setClassPK(RandomTestUtil.nextLong());

		infoItemUsage.setContainerKey(RandomTestUtil.randomString());

		infoItemUsage.setContainerType(RandomTestUtil.nextLong());

		infoItemUsage.setPlid(RandomTestUtil.nextLong());

		infoItemUsage.setType(RandomTestUtil.nextInt());

		infoItemUsage.setLastPublishDate(RandomTestUtil.nextDate());

		_infoItemUsages.add(_persistence.update(infoItemUsage));

		return infoItemUsage;
	}

	private List<InfoItemUsage> _infoItemUsages =
		new ArrayList<InfoItemUsage>();
	private InfoItemUsagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}