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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchRegionException;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceRegionPersistence;
import com.liferay.commerce.service.persistence.CommerceRegionUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CommerceRegionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceRegionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceRegion> iterator = _commerceRegions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceRegion commerceRegion = _persistence.create(pk);

		Assert.assertNotNull(commerceRegion);

		Assert.assertEquals(commerceRegion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		_persistence.remove(newCommerceRegion);

		CommerceRegion existingCommerceRegion = _persistence.fetchByPrimaryKey(newCommerceRegion.getPrimaryKey());

		Assert.assertNull(existingCommerceRegion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceRegion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceRegion newCommerceRegion = _persistence.create(pk);

		newCommerceRegion.setUuid(RandomTestUtil.randomString());

		newCommerceRegion.setGroupId(RandomTestUtil.nextLong());

		newCommerceRegion.setCompanyId(RandomTestUtil.nextLong());

		newCommerceRegion.setUserId(RandomTestUtil.nextLong());

		newCommerceRegion.setUserName(RandomTestUtil.randomString());

		newCommerceRegion.setCreateDate(RandomTestUtil.nextDate());

		newCommerceRegion.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceRegion.setCommerceCountryId(RandomTestUtil.nextLong());

		newCommerceRegion.setName(RandomTestUtil.randomString());

		newCommerceRegion.setCode(RandomTestUtil.randomString());

		newCommerceRegion.setPriority(RandomTestUtil.nextDouble());

		newCommerceRegion.setActive(RandomTestUtil.randomBoolean());

		newCommerceRegion.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceRegions.add(_persistence.update(newCommerceRegion));

		CommerceRegion existingCommerceRegion = _persistence.findByPrimaryKey(newCommerceRegion.getPrimaryKey());

		Assert.assertEquals(existingCommerceRegion.getUuid(),
			newCommerceRegion.getUuid());
		Assert.assertEquals(existingCommerceRegion.getCommerceRegionId(),
			newCommerceRegion.getCommerceRegionId());
		Assert.assertEquals(existingCommerceRegion.getGroupId(),
			newCommerceRegion.getGroupId());
		Assert.assertEquals(existingCommerceRegion.getCompanyId(),
			newCommerceRegion.getCompanyId());
		Assert.assertEquals(existingCommerceRegion.getUserId(),
			newCommerceRegion.getUserId());
		Assert.assertEquals(existingCommerceRegion.getUserName(),
			newCommerceRegion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceRegion.getCreateDate()),
			Time.getShortTimestamp(newCommerceRegion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceRegion.getModifiedDate()),
			Time.getShortTimestamp(newCommerceRegion.getModifiedDate()));
		Assert.assertEquals(existingCommerceRegion.getCommerceCountryId(),
			newCommerceRegion.getCommerceCountryId());
		Assert.assertEquals(existingCommerceRegion.getName(),
			newCommerceRegion.getName());
		Assert.assertEquals(existingCommerceRegion.getCode(),
			newCommerceRegion.getCode());
		AssertUtils.assertEquals(existingCommerceRegion.getPriority(),
			newCommerceRegion.getPriority());
		Assert.assertEquals(existingCommerceRegion.isActive(),
			newCommerceRegion.isActive());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceRegion.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceRegion.getLastPublishDate()));
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
	public void testCountByCommerceCountryId() throws Exception {
		_persistence.countByCommerceCountryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceCountryId(0L);
	}

	@Test
	public void testCountByC_A() throws Exception {
		_persistence.countByC_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(), "");

		_persistence.countByC_C(0L, "null");

		_persistence.countByC_C(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		CommerceRegion existingCommerceRegion = _persistence.findByPrimaryKey(newCommerceRegion.getPrimaryKey());

		Assert.assertEquals(existingCommerceRegion, newCommerceRegion);
	}

	@Test(expected = NoSuchRegionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceRegion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceRegion", "uuid",
			true, "commerceRegionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceCountryId", true, "name", true,
			"code", true, "priority", true, "active", true, "lastPublishDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		CommerceRegion existingCommerceRegion = _persistence.fetchByPrimaryKey(newCommerceRegion.getPrimaryKey());

		Assert.assertEquals(existingCommerceRegion, newCommerceRegion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceRegion missingCommerceRegion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceRegion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceRegion newCommerceRegion1 = addCommerceRegion();
		CommerceRegion newCommerceRegion2 = addCommerceRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceRegion1.getPrimaryKey());
		primaryKeys.add(newCommerceRegion2.getPrimaryKey());

		Map<Serializable, CommerceRegion> commerceRegions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceRegions.size());
		Assert.assertEquals(newCommerceRegion1,
			commerceRegions.get(newCommerceRegion1.getPrimaryKey()));
		Assert.assertEquals(newCommerceRegion2,
			commerceRegions.get(newCommerceRegion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceRegion> commerceRegions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceRegions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceRegion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceRegion> commerceRegions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceRegions.size());
		Assert.assertEquals(newCommerceRegion,
			commerceRegions.get(newCommerceRegion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceRegion> commerceRegions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceRegions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceRegion.getPrimaryKey());

		Map<Serializable, CommerceRegion> commerceRegions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceRegions.size());
		Assert.assertEquals(newCommerceRegion,
			commerceRegions.get(newCommerceRegion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceRegionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceRegion>() {
				@Override
				public void performAction(CommerceRegion commerceRegion) {
					Assert.assertNotNull(commerceRegion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceRegion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceRegionId",
				newCommerceRegion.getCommerceRegionId()));

		List<CommerceRegion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceRegion existingCommerceRegion = result.get(0);

		Assert.assertEquals(existingCommerceRegion, newCommerceRegion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceRegion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceRegionId",
				RandomTestUtil.nextLong()));

		List<CommerceRegion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceRegion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceRegionId"));

		Object newCommerceRegionId = newCommerceRegion.getCommerceRegionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceRegionId",
				new Object[] { newCommerceRegionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceRegionId = result.get(0);

		Assert.assertEquals(existingCommerceRegionId, newCommerceRegionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceRegion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceRegionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceRegionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceRegion newCommerceRegion = addCommerceRegion();

		_persistence.clearCache();

		CommerceRegion existingCommerceRegion = _persistence.findByPrimaryKey(newCommerceRegion.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceRegion.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceRegion,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceRegion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceRegion,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCommerceRegion.getCommerceCountryId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceRegion,
				"getOriginalCommerceCountryId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingCommerceRegion.getCode(),
				ReflectionTestUtil.invoke(existingCommerceRegion,
					"getOriginalCode", new Class<?>[0])));
	}

	protected CommerceRegion addCommerceRegion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceRegion commerceRegion = _persistence.create(pk);

		commerceRegion.setUuid(RandomTestUtil.randomString());

		commerceRegion.setGroupId(RandomTestUtil.nextLong());

		commerceRegion.setCompanyId(RandomTestUtil.nextLong());

		commerceRegion.setUserId(RandomTestUtil.nextLong());

		commerceRegion.setUserName(RandomTestUtil.randomString());

		commerceRegion.setCreateDate(RandomTestUtil.nextDate());

		commerceRegion.setModifiedDate(RandomTestUtil.nextDate());

		commerceRegion.setCommerceCountryId(RandomTestUtil.nextLong());

		commerceRegion.setName(RandomTestUtil.randomString());

		commerceRegion.setCode(RandomTestUtil.randomString());

		commerceRegion.setPriority(RandomTestUtil.nextDouble());

		commerceRegion.setActive(RandomTestUtil.randomBoolean());

		commerceRegion.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceRegions.add(_persistence.update(commerceRegion));

		return commerceRegion;
	}

	private List<CommerceRegion> _commerceRegions = new ArrayList<CommerceRegion>();
	private CommerceRegionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}