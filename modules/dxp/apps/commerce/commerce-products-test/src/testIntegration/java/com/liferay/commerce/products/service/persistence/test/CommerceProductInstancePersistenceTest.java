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

package com.liferay.commerce.products.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.products.exception.NoSuchProductInstanceException;
import com.liferay.commerce.products.model.CommerceProductInstance;
import com.liferay.commerce.products.service.CommerceProductInstanceLocalServiceUtil;
import com.liferay.commerce.products.service.persistence.CommerceProductInstancePersistence;
import com.liferay.commerce.products.service.persistence.CommerceProductInstanceUtil;

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
public class CommerceProductInstancePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.products.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductInstanceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductInstance> iterator = _commerceProductInstances.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductInstance commerceProductInstance = _persistence.create(pk);

		Assert.assertNotNull(commerceProductInstance);

		Assert.assertEquals(commerceProductInstance.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		_persistence.remove(newCommerceProductInstance);

		CommerceProductInstance existingCommerceProductInstance = _persistence.fetchByPrimaryKey(newCommerceProductInstance.getPrimaryKey());

		Assert.assertNull(existingCommerceProductInstance);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductInstance();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductInstance newCommerceProductInstance = _persistence.create(pk);

		newCommerceProductInstance.setUuid(RandomTestUtil.randomString());

		newCommerceProductInstance.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductInstance.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductInstance.setUserId(RandomTestUtil.nextLong());

		newCommerceProductInstance.setUserName(RandomTestUtil.randomString());

		newCommerceProductInstance.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductInstance.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductInstance.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		newCommerceProductInstance.setSKU(RandomTestUtil.randomString());

		newCommerceProductInstance.setDDMContent(RandomTestUtil.randomString());

		newCommerceProductInstance.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceProductInstance.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceProductInstance.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceProductInstance.setStatus(RandomTestUtil.nextInt());

		newCommerceProductInstance.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceProductInstance.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceProductInstance.setStatusDate(RandomTestUtil.nextDate());

		_commerceProductInstances.add(_persistence.update(
				newCommerceProductInstance));

		CommerceProductInstance existingCommerceProductInstance = _persistence.findByPrimaryKey(newCommerceProductInstance.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductInstance.getUuid(),
			newCommerceProductInstance.getUuid());
		Assert.assertEquals(existingCommerceProductInstance.getCommerceProductInstanceId(),
			newCommerceProductInstance.getCommerceProductInstanceId());
		Assert.assertEquals(existingCommerceProductInstance.getGroupId(),
			newCommerceProductInstance.getGroupId());
		Assert.assertEquals(existingCommerceProductInstance.getCompanyId(),
			newCommerceProductInstance.getCompanyId());
		Assert.assertEquals(existingCommerceProductInstance.getUserId(),
			newCommerceProductInstance.getUserId());
		Assert.assertEquals(existingCommerceProductInstance.getUserName(),
			newCommerceProductInstance.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getCreateDate()),
			Time.getShortTimestamp(newCommerceProductInstance.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getModifiedDate()),
			Time.getShortTimestamp(newCommerceProductInstance.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductInstance.getCommerceProductDefinitionId(),
			newCommerceProductInstance.getCommerceProductDefinitionId());
		Assert.assertEquals(existingCommerceProductInstance.getSKU(),
			newCommerceProductInstance.getSKU());
		Assert.assertEquals(existingCommerceProductInstance.getDDMContent(),
			newCommerceProductInstance.getDDMContent());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getDisplayDate()),
			Time.getShortTimestamp(newCommerceProductInstance.getDisplayDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getExpirationDate()),
			Time.getShortTimestamp(
				newCommerceProductInstance.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceProductInstance.getLastPublishDate()));
		Assert.assertEquals(existingCommerceProductInstance.getStatus(),
			newCommerceProductInstance.getStatus());
		Assert.assertEquals(existingCommerceProductInstance.getStatusByUserId(),
			newCommerceProductInstance.getStatusByUserId());
		Assert.assertEquals(existingCommerceProductInstance.getStatusByUserName(),
			newCommerceProductInstance.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductInstance.getStatusDate()),
			Time.getShortTimestamp(newCommerceProductInstance.getStatusDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCommerceProductDefinitionId()
		throws Exception {
		_persistence.countByCommerceProductDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCommerceProductDefinitionId(0L);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_S(0L, StringPool.NULL);

		_persistence.countByC_S(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		CommerceProductInstance existingCommerceProductInstance = _persistence.findByPrimaryKey(newCommerceProductInstance.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductInstance,
			newCommerceProductInstance);
	}

	@Test(expected = NoSuchProductInstanceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductInstance> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductInstance",
			"uuid", true, "commerceProductInstanceId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceProductDefinitionId", true,
			"SKU", true, "DDMContent", true, "displayDate", true,
			"expirationDate", true, "lastPublishDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		CommerceProductInstance existingCommerceProductInstance = _persistence.fetchByPrimaryKey(newCommerceProductInstance.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductInstance,
			newCommerceProductInstance);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductInstance missingCommerceProductInstance = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductInstance);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductInstance newCommerceProductInstance1 = addCommerceProductInstance();
		CommerceProductInstance newCommerceProductInstance2 = addCommerceProductInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductInstance1.getPrimaryKey());
		primaryKeys.add(newCommerceProductInstance2.getPrimaryKey());

		Map<Serializable, CommerceProductInstance> commerceProductInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductInstances.size());
		Assert.assertEquals(newCommerceProductInstance1,
			commerceProductInstances.get(
				newCommerceProductInstance1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductInstance2,
			commerceProductInstances.get(
				newCommerceProductInstance2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductInstance> commerceProductInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductInstance.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductInstance> commerceProductInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductInstances.size());
		Assert.assertEquals(newCommerceProductInstance,
			commerceProductInstances.get(
				newCommerceProductInstance.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductInstance> commerceProductInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductInstance.getPrimaryKey());

		Map<Serializable, CommerceProductInstance> commerceProductInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductInstances.size());
		Assert.assertEquals(newCommerceProductInstance,
			commerceProductInstances.get(
				newCommerceProductInstance.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductInstanceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductInstance>() {
				@Override
				public void performAction(
					CommerceProductInstance commerceProductInstance) {
					Assert.assertNotNull(commerceProductInstance);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductInstanceId",
				newCommerceProductInstance.getCommerceProductInstanceId()));

		List<CommerceProductInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductInstance existingCommerceProductInstance = result.get(0);

		Assert.assertEquals(existingCommerceProductInstance,
			newCommerceProductInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductInstanceId", RandomTestUtil.nextLong()));

		List<CommerceProductInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductInstanceId"));

		Object newCommerceProductInstanceId = newCommerceProductInstance.getCommerceProductInstanceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductInstanceId",
				new Object[] { newCommerceProductInstanceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductInstanceId = result.get(0);

		Assert.assertEquals(existingCommerceProductInstanceId,
			newCommerceProductInstanceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductInstanceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductInstanceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceProductInstance newCommerceProductInstance = addCommerceProductInstance();

		_persistence.clearCache();

		CommerceProductInstance existingCommerceProductInstance = _persistence.findByPrimaryKey(newCommerceProductInstance.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommerceProductInstance.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceProductInstance,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommerceProductInstance.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceProductInstance,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCommerceProductInstance.getCommerceProductDefinitionId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceProductInstance,
				"getOriginalCommerceProductDefinitionId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceProductInstance.getSKU(),
				ReflectionTestUtil.invoke(existingCommerceProductInstance,
					"getOriginalSKU", new Class<?>[0])));
	}

	protected CommerceProductInstance addCommerceProductInstance()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductInstance commerceProductInstance = _persistence.create(pk);

		commerceProductInstance.setUuid(RandomTestUtil.randomString());

		commerceProductInstance.setGroupId(RandomTestUtil.nextLong());

		commerceProductInstance.setCompanyId(RandomTestUtil.nextLong());

		commerceProductInstance.setUserId(RandomTestUtil.nextLong());

		commerceProductInstance.setUserName(RandomTestUtil.randomString());

		commerceProductInstance.setCreateDate(RandomTestUtil.nextDate());

		commerceProductInstance.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductInstance.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		commerceProductInstance.setSKU(RandomTestUtil.randomString());

		commerceProductInstance.setDDMContent(RandomTestUtil.randomString());

		commerceProductInstance.setDisplayDate(RandomTestUtil.nextDate());

		commerceProductInstance.setExpirationDate(RandomTestUtil.nextDate());

		commerceProductInstance.setLastPublishDate(RandomTestUtil.nextDate());

		commerceProductInstance.setStatus(RandomTestUtil.nextInt());

		commerceProductInstance.setStatusByUserId(RandomTestUtil.nextLong());

		commerceProductInstance.setStatusByUserName(RandomTestUtil.randomString());

		commerceProductInstance.setStatusDate(RandomTestUtil.nextDate());

		_commerceProductInstances.add(_persistence.update(
				commerceProductInstance));

		return commerceProductInstance;
	}

	private List<CommerceProductInstance> _commerceProductInstances = new ArrayList<CommerceProductInstance>();
	private CommerceProductInstancePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}