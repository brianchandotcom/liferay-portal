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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchCPMediaTypeException;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.CPMediaTypeLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPMediaTypePersistence;
import com.liferay.commerce.product.service.persistence.CPMediaTypeUtil;

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
public class CPMediaTypePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPMediaTypeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPMediaType> iterator = _cpMediaTypes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPMediaType cpMediaType = _persistence.create(pk);

		Assert.assertNotNull(cpMediaType);

		Assert.assertEquals(cpMediaType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		_persistence.remove(newCPMediaType);

		CPMediaType existingCPMediaType = _persistence.fetchByPrimaryKey(newCPMediaType.getPrimaryKey());

		Assert.assertNull(existingCPMediaType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPMediaType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPMediaType newCPMediaType = _persistence.create(pk);

		newCPMediaType.setUuid(RandomTestUtil.randomString());

		newCPMediaType.setGroupId(RandomTestUtil.nextLong());

		newCPMediaType.setCompanyId(RandomTestUtil.nextLong());

		newCPMediaType.setUserId(RandomTestUtil.nextLong());

		newCPMediaType.setUserName(RandomTestUtil.randomString());

		newCPMediaType.setCreateDate(RandomTestUtil.nextDate());

		newCPMediaType.setModifiedDate(RandomTestUtil.nextDate());

		newCPMediaType.setTitle(RandomTestUtil.randomString());

		newCPMediaType.setDescription(RandomTestUtil.randomString());

		newCPMediaType.setPriority(RandomTestUtil.nextInt());

		_cpMediaTypes.add(_persistence.update(newCPMediaType));

		CPMediaType existingCPMediaType = _persistence.findByPrimaryKey(newCPMediaType.getPrimaryKey());

		Assert.assertEquals(existingCPMediaType.getUuid(),
			newCPMediaType.getUuid());
		Assert.assertEquals(existingCPMediaType.getCPMediaTypeId(),
			newCPMediaType.getCPMediaTypeId());
		Assert.assertEquals(existingCPMediaType.getGroupId(),
			newCPMediaType.getGroupId());
		Assert.assertEquals(existingCPMediaType.getCompanyId(),
			newCPMediaType.getCompanyId());
		Assert.assertEquals(existingCPMediaType.getUserId(),
			newCPMediaType.getUserId());
		Assert.assertEquals(existingCPMediaType.getUserName(),
			newCPMediaType.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPMediaType.getCreateDate()),
			Time.getShortTimestamp(newCPMediaType.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPMediaType.getModifiedDate()),
			Time.getShortTimestamp(newCPMediaType.getModifiedDate()));
		Assert.assertEquals(existingCPMediaType.getTitle(),
			newCPMediaType.getTitle());
		Assert.assertEquals(existingCPMediaType.getDescription(),
			newCPMediaType.getDescription());
		Assert.assertEquals(existingCPMediaType.getPriority(),
			newCPMediaType.getPriority());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		CPMediaType existingCPMediaType = _persistence.findByPrimaryKey(newCPMediaType.getPrimaryKey());

		Assert.assertEquals(existingCPMediaType, newCPMediaType);
	}

	@Test(expected = NoSuchCPMediaTypeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPMediaType> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPMediaType", "uuid", true,
			"CPMediaTypeId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "title", true, "description", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		CPMediaType existingCPMediaType = _persistence.fetchByPrimaryKey(newCPMediaType.getPrimaryKey());

		Assert.assertEquals(existingCPMediaType, newCPMediaType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPMediaType missingCPMediaType = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPMediaType);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPMediaType newCPMediaType1 = addCPMediaType();
		CPMediaType newCPMediaType2 = addCPMediaType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPMediaType1.getPrimaryKey());
		primaryKeys.add(newCPMediaType2.getPrimaryKey());

		Map<Serializable, CPMediaType> cpMediaTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpMediaTypes.size());
		Assert.assertEquals(newCPMediaType1,
			cpMediaTypes.get(newCPMediaType1.getPrimaryKey()));
		Assert.assertEquals(newCPMediaType2,
			cpMediaTypes.get(newCPMediaType2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPMediaType> cpMediaTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpMediaTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPMediaType.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPMediaType> cpMediaTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpMediaTypes.size());
		Assert.assertEquals(newCPMediaType,
			cpMediaTypes.get(newCPMediaType.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPMediaType> cpMediaTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpMediaTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPMediaType.getPrimaryKey());

		Map<Serializable, CPMediaType> cpMediaTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpMediaTypes.size());
		Assert.assertEquals(newCPMediaType,
			cpMediaTypes.get(newCPMediaType.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPMediaTypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPMediaType>() {
				@Override
				public void performAction(CPMediaType cpMediaType) {
					Assert.assertNotNull(cpMediaType);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPMediaType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPMediaTypeId",
				newCPMediaType.getCPMediaTypeId()));

		List<CPMediaType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPMediaType existingCPMediaType = result.get(0);

		Assert.assertEquals(existingCPMediaType, newCPMediaType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPMediaType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPMediaTypeId",
				RandomTestUtil.nextLong()));

		List<CPMediaType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPMediaType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPMediaTypeId"));

		Object newCPMediaTypeId = newCPMediaType.getCPMediaTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPMediaTypeId",
				new Object[] { newCPMediaTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPMediaTypeId = result.get(0);

		Assert.assertEquals(existingCPMediaTypeId, newCPMediaTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPMediaType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPMediaTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPMediaTypeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPMediaType newCPMediaType = addCPMediaType();

		_persistence.clearCache();

		CPMediaType existingCPMediaType = _persistence.findByPrimaryKey(newCPMediaType.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPMediaType.getUuid(),
				ReflectionTestUtil.invoke(existingCPMediaType,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCPMediaType.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPMediaType,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CPMediaType addCPMediaType() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPMediaType cpMediaType = _persistence.create(pk);

		cpMediaType.setUuid(RandomTestUtil.randomString());

		cpMediaType.setGroupId(RandomTestUtil.nextLong());

		cpMediaType.setCompanyId(RandomTestUtil.nextLong());

		cpMediaType.setUserId(RandomTestUtil.nextLong());

		cpMediaType.setUserName(RandomTestUtil.randomString());

		cpMediaType.setCreateDate(RandomTestUtil.nextDate());

		cpMediaType.setModifiedDate(RandomTestUtil.nextDate());

		cpMediaType.setTitle(RandomTestUtil.randomString());

		cpMediaType.setDescription(RandomTestUtil.randomString());

		cpMediaType.setPriority(RandomTestUtil.nextInt());

		_cpMediaTypes.add(_persistence.update(cpMediaType));

		return cpMediaType;
	}

	private List<CPMediaType> _cpMediaTypes = new ArrayList<CPMediaType>();
	private CPMediaTypePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}