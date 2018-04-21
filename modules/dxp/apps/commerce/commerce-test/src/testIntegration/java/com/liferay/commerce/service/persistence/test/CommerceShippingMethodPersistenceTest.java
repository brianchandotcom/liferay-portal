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

import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingMethodUtil;

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
public class CommerceShippingMethodPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceShippingMethodUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceShippingMethod> iterator = _commerceShippingMethods.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingMethod commerceShippingMethod = _persistence.create(pk);

		Assert.assertNotNull(commerceShippingMethod);

		Assert.assertEquals(commerceShippingMethod.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		_persistence.remove(newCommerceShippingMethod);

		CommerceShippingMethod existingCommerceShippingMethod = _persistence.fetchByPrimaryKey(newCommerceShippingMethod.getPrimaryKey());

		Assert.assertNull(existingCommerceShippingMethod);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceShippingMethod();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingMethod newCommerceShippingMethod = _persistence.create(pk);

		newCommerceShippingMethod.setGroupId(RandomTestUtil.nextLong());

		newCommerceShippingMethod.setCompanyId(RandomTestUtil.nextLong());

		newCommerceShippingMethod.setUserId(RandomTestUtil.nextLong());

		newCommerceShippingMethod.setUserName(RandomTestUtil.randomString());

		newCommerceShippingMethod.setCreateDate(RandomTestUtil.nextDate());

		newCommerceShippingMethod.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceShippingMethod.setName(RandomTestUtil.randomString());

		newCommerceShippingMethod.setDescription(RandomTestUtil.randomString());

		newCommerceShippingMethod.setImageId(RandomTestUtil.nextLong());

		newCommerceShippingMethod.setEngineKey(RandomTestUtil.randomString());

		newCommerceShippingMethod.setPriority(RandomTestUtil.nextDouble());

		newCommerceShippingMethod.setActive(RandomTestUtil.randomBoolean());

		_commerceShippingMethods.add(_persistence.update(
				newCommerceShippingMethod));

		CommerceShippingMethod existingCommerceShippingMethod = _persistence.findByPrimaryKey(newCommerceShippingMethod.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingMethod.getCommerceShippingMethodId(),
			newCommerceShippingMethod.getCommerceShippingMethodId());
		Assert.assertEquals(existingCommerceShippingMethod.getGroupId(),
			newCommerceShippingMethod.getGroupId());
		Assert.assertEquals(existingCommerceShippingMethod.getCompanyId(),
			newCommerceShippingMethod.getCompanyId());
		Assert.assertEquals(existingCommerceShippingMethod.getUserId(),
			newCommerceShippingMethod.getUserId());
		Assert.assertEquals(existingCommerceShippingMethod.getUserName(),
			newCommerceShippingMethod.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceShippingMethod.getCreateDate()),
			Time.getShortTimestamp(newCommerceShippingMethod.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceShippingMethod.getModifiedDate()),
			Time.getShortTimestamp(newCommerceShippingMethod.getModifiedDate()));
		Assert.assertEquals(existingCommerceShippingMethod.getName(),
			newCommerceShippingMethod.getName());
		Assert.assertEquals(existingCommerceShippingMethod.getDescription(),
			newCommerceShippingMethod.getDescription());
		Assert.assertEquals(existingCommerceShippingMethod.getImageId(),
			newCommerceShippingMethod.getImageId());
		Assert.assertEquals(existingCommerceShippingMethod.getEngineKey(),
			newCommerceShippingMethod.getEngineKey());
		AssertUtils.assertEquals(existingCommerceShippingMethod.getPriority(),
			newCommerceShippingMethod.getPriority());
		Assert.assertEquals(existingCommerceShippingMethod.isActive(),
			newCommerceShippingMethod.isActive());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_E() throws Exception {
		_persistence.countByG_E(RandomTestUtil.nextLong(), "");

		_persistence.countByG_E(0L, "null");

		_persistence.countByG_E(0L, (String)null);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		CommerceShippingMethod existingCommerceShippingMethod = _persistence.findByPrimaryKey(newCommerceShippingMethod.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingMethod,
			newCommerceShippingMethod);
	}

	@Test(expected = NoSuchShippingMethodException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceShippingMethod> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceShippingMethod",
			"commerceShippingMethodId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true, "imageId",
			true, "engineKey", true, "priority", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		CommerceShippingMethod existingCommerceShippingMethod = _persistence.fetchByPrimaryKey(newCommerceShippingMethod.getPrimaryKey());

		Assert.assertEquals(existingCommerceShippingMethod,
			newCommerceShippingMethod);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingMethod missingCommerceShippingMethod = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceShippingMethod);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceShippingMethod newCommerceShippingMethod1 = addCommerceShippingMethod();
		CommerceShippingMethod newCommerceShippingMethod2 = addCommerceShippingMethod();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingMethod1.getPrimaryKey());
		primaryKeys.add(newCommerceShippingMethod2.getPrimaryKey());

		Map<Serializable, CommerceShippingMethod> commerceShippingMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceShippingMethods.size());
		Assert.assertEquals(newCommerceShippingMethod1,
			commerceShippingMethods.get(
				newCommerceShippingMethod1.getPrimaryKey()));
		Assert.assertEquals(newCommerceShippingMethod2,
			commerceShippingMethods.get(
				newCommerceShippingMethod2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceShippingMethod> commerceShippingMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShippingMethods.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingMethod.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceShippingMethod> commerceShippingMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShippingMethods.size());
		Assert.assertEquals(newCommerceShippingMethod,
			commerceShippingMethods.get(
				newCommerceShippingMethod.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceShippingMethod> commerceShippingMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShippingMethods.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShippingMethod.getPrimaryKey());

		Map<Serializable, CommerceShippingMethod> commerceShippingMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShippingMethods.size());
		Assert.assertEquals(newCommerceShippingMethod,
			commerceShippingMethods.get(
				newCommerceShippingMethod.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceShippingMethodLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceShippingMethod>() {
				@Override
				public void performAction(
					CommerceShippingMethod commerceShippingMethod) {
					Assert.assertNotNull(commerceShippingMethod);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceShippingMethodId",
				newCommerceShippingMethod.getCommerceShippingMethodId()));

		List<CommerceShippingMethod> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceShippingMethod existingCommerceShippingMethod = result.get(0);

		Assert.assertEquals(existingCommerceShippingMethod,
			newCommerceShippingMethod);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceShippingMethodId", RandomTestUtil.nextLong()));

		List<CommerceShippingMethod> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceShippingMethodId"));

		Object newCommerceShippingMethodId = newCommerceShippingMethod.getCommerceShippingMethodId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceShippingMethodId",
				new Object[] { newCommerceShippingMethodId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceShippingMethodId = result.get(0);

		Assert.assertEquals(existingCommerceShippingMethodId,
			newCommerceShippingMethodId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceShippingMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceShippingMethodId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceShippingMethodId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceShippingMethod newCommerceShippingMethod = addCommerceShippingMethod();

		_persistence.clearCache();

		CommerceShippingMethod existingCommerceShippingMethod = _persistence.findByPrimaryKey(newCommerceShippingMethod.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceShippingMethod.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceShippingMethod,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceShippingMethod.getEngineKey(),
				ReflectionTestUtil.invoke(existingCommerceShippingMethod,
					"getOriginalEngineKey", new Class<?>[0])));
	}

	protected CommerceShippingMethod addCommerceShippingMethod()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShippingMethod commerceShippingMethod = _persistence.create(pk);

		commerceShippingMethod.setGroupId(RandomTestUtil.nextLong());

		commerceShippingMethod.setCompanyId(RandomTestUtil.nextLong());

		commerceShippingMethod.setUserId(RandomTestUtil.nextLong());

		commerceShippingMethod.setUserName(RandomTestUtil.randomString());

		commerceShippingMethod.setCreateDate(RandomTestUtil.nextDate());

		commerceShippingMethod.setModifiedDate(RandomTestUtil.nextDate());

		commerceShippingMethod.setName(RandomTestUtil.randomString());

		commerceShippingMethod.setDescription(RandomTestUtil.randomString());

		commerceShippingMethod.setImageId(RandomTestUtil.nextLong());

		commerceShippingMethod.setEngineKey(RandomTestUtil.randomString());

		commerceShippingMethod.setPriority(RandomTestUtil.nextDouble());

		commerceShippingMethod.setActive(RandomTestUtil.randomBoolean());

		_commerceShippingMethods.add(_persistence.update(commerceShippingMethod));

		return commerceShippingMethod;
	}

	private List<CommerceShippingMethod> _commerceShippingMethods = new ArrayList<CommerceShippingMethod>();
	private CommerceShippingMethodPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}