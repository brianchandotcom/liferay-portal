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

import com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalServiceUtil;
import com.liferay.commerce.service.persistence.CPDefinitionAvailabilityRangePersistence;
import com.liferay.commerce.service.persistence.CPDefinitionAvailabilityRangeUtil;

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
public class CPDefinitionAvailabilityRangePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionAvailabilityRangeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionAvailabilityRange> iterator = _cpDefinitionAvailabilityRanges.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionAvailabilityRange);

		Assert.assertEquals(cpDefinitionAvailabilityRange.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		_persistence.remove(newCPDefinitionAvailabilityRange);

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = _persistence.fetchByPrimaryKey(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionAvailabilityRange);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionAvailabilityRange();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = _persistence.create(pk);

		newCPDefinitionAvailabilityRange.setUuid(RandomTestUtil.randomString());

		newCPDefinitionAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		newCPDefinitionAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionAvailabilityRange.setUserName(RandomTestUtil.randomString());

		newCPDefinitionAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionAvailabilityRange.setCPDefinitionId(RandomTestUtil.nextLong());

		newCPDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(RandomTestUtil.nextLong());

		newCPDefinitionAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_cpDefinitionAvailabilityRanges.add(_persistence.update(
				newCPDefinitionAvailabilityRange));

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = _persistence.findByPrimaryKey(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getUuid(),
			newCPDefinitionAvailabilityRange.getUuid());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId(),
			newCPDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getGroupId(),
			newCPDefinitionAvailabilityRange.getGroupId());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getCompanyId(),
			newCPDefinitionAvailabilityRange.getCompanyId());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getUserId(),
			newCPDefinitionAvailabilityRange.getUserId());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getUserName(),
			newCPDefinitionAvailabilityRange.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionAvailabilityRange.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionAvailabilityRange.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionAvailabilityRange.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionAvailabilityRange.getModifiedDate()));
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getCPDefinitionId(),
			newCPDefinitionAvailabilityRange.getCPDefinitionId());
		Assert.assertEquals(existingCPDefinitionAvailabilityRange.getCommerceAvailabilityRangeId(),
			newCPDefinitionAvailabilityRange.getCommerceAvailabilityRangeId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionAvailabilityRange.getLastPublishDate()),
			Time.getShortTimestamp(
				newCPDefinitionAvailabilityRange.getLastPublishDate()));
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
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = _persistence.findByPrimaryKey(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionAvailabilityRange,
			newCPDefinitionAvailabilityRange);
	}

	@Test(expected = NoSuchCPDefinitionAvailabilityRangeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionAvailabilityRange> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPDefinitionAvailabilityRange",
			"uuid", true, "CPDefinitionAvailabilityRangeId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "CPDefinitionId", true,
			"commerceAvailabilityRangeId", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = _persistence.fetchByPrimaryKey(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionAvailabilityRange,
			newCPDefinitionAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionAvailabilityRange missingCPDefinitionAvailabilityRange = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange1 = addCPDefinitionAvailabilityRange();
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange2 = addCPDefinitionAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionAvailabilityRange1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionAvailabilityRange2.getPrimaryKey());

		Map<Serializable, CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionAvailabilityRanges.size());
		Assert.assertEquals(newCPDefinitionAvailabilityRange1,
			cpDefinitionAvailabilityRanges.get(
				newCPDefinitionAvailabilityRange1.getPrimaryKey()));
		Assert.assertEquals(newCPDefinitionAvailabilityRange2,
			cpDefinitionAvailabilityRanges.get(
				newCPDefinitionAvailabilityRange2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionAvailabilityRange.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionAvailabilityRanges.size());
		Assert.assertEquals(newCPDefinitionAvailabilityRange,
			cpDefinitionAvailabilityRanges.get(
				newCPDefinitionAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Map<Serializable, CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionAvailabilityRanges.size());
		Assert.assertEquals(newCPDefinitionAvailabilityRange,
			cpDefinitionAvailabilityRanges.get(
				newCPDefinitionAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPDefinitionAvailabilityRangeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPDefinitionAvailabilityRange>() {
				@Override
				public void performAction(
					CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
					Assert.assertNotNull(cpDefinitionAvailabilityRange);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPDefinitionAvailabilityRangeId",
				newCPDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId()));

		List<CPDefinitionAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = result.get(0);

		Assert.assertEquals(existingCPDefinitionAvailabilityRange,
			newCPDefinitionAvailabilityRange);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPDefinitionAvailabilityRangeId", RandomTestUtil.nextLong()));

		List<CPDefinitionAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionAvailabilityRangeId"));

		Object newCPDefinitionAvailabilityRangeId = newCPDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPDefinitionAvailabilityRangeId",
				new Object[] { newCPDefinitionAvailabilityRangeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionAvailabilityRangeId = result.get(0);

		Assert.assertEquals(existingCPDefinitionAvailabilityRangeId,
			newCPDefinitionAvailabilityRangeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionAvailabilityRangeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPDefinitionAvailabilityRangeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionAvailabilityRange newCPDefinitionAvailabilityRange = addCPDefinitionAvailabilityRange();

		_persistence.clearCache();

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange = _persistence.findByPrimaryKey(newCPDefinitionAvailabilityRange.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPDefinitionAvailabilityRange.getUuid(),
				ReflectionTestUtil.invoke(
					existingCPDefinitionAvailabilityRange, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionAvailabilityRange.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCPDefinitionAvailabilityRange, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionAvailabilityRange.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				existingCPDefinitionAvailabilityRange,
				"getOriginalCPDefinitionId", new Class<?>[0]));
	}

	protected CPDefinitionAvailabilityRange addCPDefinitionAvailabilityRange()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = _persistence.create(pk);

		cpDefinitionAvailabilityRange.setUuid(RandomTestUtil.randomString());

		cpDefinitionAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		cpDefinitionAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		cpDefinitionAvailabilityRange.setUserName(RandomTestUtil.randomString());

		cpDefinitionAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionAvailabilityRange.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(RandomTestUtil.nextLong());

		cpDefinitionAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_cpDefinitionAvailabilityRanges.add(_persistence.update(
				cpDefinitionAvailabilityRange));

		return cpDefinitionAvailabilityRange;
	}

	private List<CPDefinitionAvailabilityRange> _cpDefinitionAvailabilityRanges = new ArrayList<CPDefinitionAvailabilityRange>();
	private CPDefinitionAvailabilityRangePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}