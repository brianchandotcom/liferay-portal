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

import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommercePaymentMethodLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommercePaymentMethodPersistence;
import com.liferay.commerce.service.persistence.CommercePaymentMethodUtil;

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
public class CommercePaymentMethodPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommercePaymentMethodUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePaymentMethod> iterator = _commercePaymentMethods.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentMethod commercePaymentMethod = _persistence.create(pk);

		Assert.assertNotNull(commercePaymentMethod);

		Assert.assertEquals(commercePaymentMethod.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		_persistence.remove(newCommercePaymentMethod);

		CommercePaymentMethod existingCommercePaymentMethod = _persistence.fetchByPrimaryKey(newCommercePaymentMethod.getPrimaryKey());

		Assert.assertNull(existingCommercePaymentMethod);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePaymentMethod();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentMethod newCommercePaymentMethod = _persistence.create(pk);

		newCommercePaymentMethod.setGroupId(RandomTestUtil.nextLong());

		newCommercePaymentMethod.setCompanyId(RandomTestUtil.nextLong());

		newCommercePaymentMethod.setUserId(RandomTestUtil.nextLong());

		newCommercePaymentMethod.setUserName(RandomTestUtil.randomString());

		newCommercePaymentMethod.setCreateDate(RandomTestUtil.nextDate());

		newCommercePaymentMethod.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePaymentMethod.setName(RandomTestUtil.randomString());

		newCommercePaymentMethod.setDescription(RandomTestUtil.randomString());

		newCommercePaymentMethod.setEngineKey(RandomTestUtil.randomString());

		newCommercePaymentMethod.setPriority(RandomTestUtil.nextDouble());

		newCommercePaymentMethod.setActive(RandomTestUtil.randomBoolean());

		_commercePaymentMethods.add(_persistence.update(
				newCommercePaymentMethod));

		CommercePaymentMethod existingCommercePaymentMethod = _persistence.findByPrimaryKey(newCommercePaymentMethod.getPrimaryKey());

		Assert.assertEquals(existingCommercePaymentMethod.getCommercePaymentMethodId(),
			newCommercePaymentMethod.getCommercePaymentMethodId());
		Assert.assertEquals(existingCommercePaymentMethod.getGroupId(),
			newCommercePaymentMethod.getGroupId());
		Assert.assertEquals(existingCommercePaymentMethod.getCompanyId(),
			newCommercePaymentMethod.getCompanyId());
		Assert.assertEquals(existingCommercePaymentMethod.getUserId(),
			newCommercePaymentMethod.getUserId());
		Assert.assertEquals(existingCommercePaymentMethod.getUserName(),
			newCommercePaymentMethod.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePaymentMethod.getCreateDate()),
			Time.getShortTimestamp(newCommercePaymentMethod.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommercePaymentMethod.getModifiedDate()),
			Time.getShortTimestamp(newCommercePaymentMethod.getModifiedDate()));
		Assert.assertEquals(existingCommercePaymentMethod.getName(),
			newCommercePaymentMethod.getName());
		Assert.assertEquals(existingCommercePaymentMethod.getDescription(),
			newCommercePaymentMethod.getDescription());
		Assert.assertEquals(existingCommercePaymentMethod.getEngineKey(),
			newCommercePaymentMethod.getEngineKey());
		AssertUtils.assertEquals(existingCommercePaymentMethod.getPriority(),
			newCommercePaymentMethod.getPriority());
		Assert.assertEquals(existingCommercePaymentMethod.getActive(),
			newCommercePaymentMethod.getActive());
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
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		CommercePaymentMethod existingCommercePaymentMethod = _persistence.findByPrimaryKey(newCommercePaymentMethod.getPrimaryKey());

		Assert.assertEquals(existingCommercePaymentMethod,
			newCommercePaymentMethod);
	}

	@Test(expected = NoSuchPaymentMethodException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommercePaymentMethod> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommercePaymentMethod",
			"commercePaymentMethodId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true,
			"engineKey", true, "priority", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		CommercePaymentMethod existingCommercePaymentMethod = _persistence.fetchByPrimaryKey(newCommercePaymentMethod.getPrimaryKey());

		Assert.assertEquals(existingCommercePaymentMethod,
			newCommercePaymentMethod);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentMethod missingCommercePaymentMethod = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePaymentMethod);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommercePaymentMethod newCommercePaymentMethod1 = addCommercePaymentMethod();
		CommercePaymentMethod newCommercePaymentMethod2 = addCommercePaymentMethod();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentMethod1.getPrimaryKey());
		primaryKeys.add(newCommercePaymentMethod2.getPrimaryKey());

		Map<Serializable, CommercePaymentMethod> commercePaymentMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePaymentMethods.size());
		Assert.assertEquals(newCommercePaymentMethod1,
			commercePaymentMethods.get(
				newCommercePaymentMethod1.getPrimaryKey()));
		Assert.assertEquals(newCommercePaymentMethod2,
			commercePaymentMethods.get(
				newCommercePaymentMethod2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePaymentMethod> commercePaymentMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePaymentMethods.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentMethod.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePaymentMethod> commercePaymentMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePaymentMethods.size());
		Assert.assertEquals(newCommercePaymentMethod,
			commercePaymentMethods.get(newCommercePaymentMethod.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePaymentMethod> commercePaymentMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePaymentMethods.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentMethod.getPrimaryKey());

		Map<Serializable, CommercePaymentMethod> commercePaymentMethods = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePaymentMethods.size());
		Assert.assertEquals(newCommercePaymentMethod,
			commercePaymentMethods.get(newCommercePaymentMethod.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommercePaymentMethodLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePaymentMethod>() {
				@Override
				public void performAction(
					CommercePaymentMethod commercePaymentMethod) {
					Assert.assertNotNull(commercePaymentMethod);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePaymentMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commercePaymentMethodId",
				newCommercePaymentMethod.getCommercePaymentMethodId()));

		List<CommercePaymentMethod> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePaymentMethod existingCommercePaymentMethod = result.get(0);

		Assert.assertEquals(existingCommercePaymentMethod,
			newCommercePaymentMethod);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePaymentMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commercePaymentMethodId",
				RandomTestUtil.nextLong()));

		List<CommercePaymentMethod> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePaymentMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePaymentMethodId"));

		Object newCommercePaymentMethodId = newCommercePaymentMethod.getCommercePaymentMethodId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commercePaymentMethodId",
				new Object[] { newCommercePaymentMethodId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePaymentMethodId = result.get(0);

		Assert.assertEquals(existingCommercePaymentMethodId,
			newCommercePaymentMethodId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommercePaymentMethod.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commercePaymentMethodId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commercePaymentMethodId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommercePaymentMethod newCommercePaymentMethod = addCommercePaymentMethod();

		_persistence.clearCache();

		CommercePaymentMethod existingCommercePaymentMethod = _persistence.findByPrimaryKey(newCommercePaymentMethod.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommercePaymentMethod.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommercePaymentMethod,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommercePaymentMethod.getEngineKey(),
				ReflectionTestUtil.invoke(existingCommercePaymentMethod,
					"getOriginalEngineKey", new Class<?>[0])));
	}

	protected CommercePaymentMethod addCommercePaymentMethod()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentMethod commercePaymentMethod = _persistence.create(pk);

		commercePaymentMethod.setGroupId(RandomTestUtil.nextLong());

		commercePaymentMethod.setCompanyId(RandomTestUtil.nextLong());

		commercePaymentMethod.setUserId(RandomTestUtil.nextLong());

		commercePaymentMethod.setUserName(RandomTestUtil.randomString());

		commercePaymentMethod.setCreateDate(RandomTestUtil.nextDate());

		commercePaymentMethod.setModifiedDate(RandomTestUtil.nextDate());

		commercePaymentMethod.setName(RandomTestUtil.randomString());

		commercePaymentMethod.setDescription(RandomTestUtil.randomString());

		commercePaymentMethod.setEngineKey(RandomTestUtil.randomString());

		commercePaymentMethod.setPriority(RandomTestUtil.nextDouble());

		commercePaymentMethod.setActive(RandomTestUtil.randomBoolean());

		_commercePaymentMethods.add(_persistence.update(commercePaymentMethod));

		return commercePaymentMethod;
	}

	private List<CommercePaymentMethod> _commercePaymentMethods = new ArrayList<CommercePaymentMethod>();
	private CommercePaymentMethodPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}