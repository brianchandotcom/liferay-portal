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

import com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException;
import com.liferay.commerce.product.model.CPAvailabilityRange;
import com.liferay.commerce.product.service.CPAvailabilityRangeLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPAvailabilityRangePersistence;
import com.liferay.commerce.product.service.persistence.CPAvailabilityRangeUtil;

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
public class CPAvailabilityRangePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPAvailabilityRangeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPAvailabilityRange> iterator = _cpAvailabilityRanges.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPAvailabilityRange cpAvailabilityRange = _persistence.create(pk);

		Assert.assertNotNull(cpAvailabilityRange);

		Assert.assertEquals(cpAvailabilityRange.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		_persistence.remove(newCPAvailabilityRange);

		CPAvailabilityRange existingCPAvailabilityRange = _persistence.fetchByPrimaryKey(newCPAvailabilityRange.getPrimaryKey());

		Assert.assertNull(existingCPAvailabilityRange);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPAvailabilityRange();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPAvailabilityRange newCPAvailabilityRange = _persistence.create(pk);

		newCPAvailabilityRange.setUuid(RandomTestUtil.randomString());

		newCPAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		newCPAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		newCPAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		newCPAvailabilityRange.setUserName(RandomTestUtil.randomString());

		newCPAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		newCPAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		newCPAvailabilityRange.setCPDefinitionId(RandomTestUtil.nextLong());

		newCPAvailabilityRange.setTitle(RandomTestUtil.randomString());

		newCPAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_cpAvailabilityRanges.add(_persistence.update(newCPAvailabilityRange));

		CPAvailabilityRange existingCPAvailabilityRange = _persistence.findByPrimaryKey(newCPAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPAvailabilityRange.getUuid(),
			newCPAvailabilityRange.getUuid());
		Assert.assertEquals(existingCPAvailabilityRange.getCPAvailabilityRangeId(),
			newCPAvailabilityRange.getCPAvailabilityRangeId());
		Assert.assertEquals(existingCPAvailabilityRange.getGroupId(),
			newCPAvailabilityRange.getGroupId());
		Assert.assertEquals(existingCPAvailabilityRange.getCompanyId(),
			newCPAvailabilityRange.getCompanyId());
		Assert.assertEquals(existingCPAvailabilityRange.getUserId(),
			newCPAvailabilityRange.getUserId());
		Assert.assertEquals(existingCPAvailabilityRange.getUserName(),
			newCPAvailabilityRange.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPAvailabilityRange.getCreateDate()),
			Time.getShortTimestamp(newCPAvailabilityRange.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPAvailabilityRange.getModifiedDate()),
			Time.getShortTimestamp(newCPAvailabilityRange.getModifiedDate()));
		Assert.assertEquals(existingCPAvailabilityRange.getCPDefinitionId(),
			newCPAvailabilityRange.getCPDefinitionId());
		Assert.assertEquals(existingCPAvailabilityRange.getTitle(),
			newCPAvailabilityRange.getTitle());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPAvailabilityRange.getLastPublishDate()),
			Time.getShortTimestamp(newCPAvailabilityRange.getLastPublishDate()));
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		CPAvailabilityRange existingCPAvailabilityRange = _persistence.findByPrimaryKey(newCPAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPAvailabilityRange, newCPAvailabilityRange);
	}

	@Test(expected = NoSuchCPAvailabilityRangeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPAvailabilityRange> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPAvailabilityRange",
			"uuid", true, "CPAvailabilityRangeId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPDefinitionId", true, "title", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		CPAvailabilityRange existingCPAvailabilityRange = _persistence.fetchByPrimaryKey(newCPAvailabilityRange.getPrimaryKey());

		Assert.assertEquals(existingCPAvailabilityRange, newCPAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPAvailabilityRange missingCPAvailabilityRange = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPAvailabilityRange);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPAvailabilityRange newCPAvailabilityRange1 = addCPAvailabilityRange();
		CPAvailabilityRange newCPAvailabilityRange2 = addCPAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPAvailabilityRange1.getPrimaryKey());
		primaryKeys.add(newCPAvailabilityRange2.getPrimaryKey());

		Map<Serializable, CPAvailabilityRange> cpAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpAvailabilityRanges.size());
		Assert.assertEquals(newCPAvailabilityRange1,
			cpAvailabilityRanges.get(newCPAvailabilityRange1.getPrimaryKey()));
		Assert.assertEquals(newCPAvailabilityRange2,
			cpAvailabilityRanges.get(newCPAvailabilityRange2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPAvailabilityRange> cpAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPAvailabilityRange.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPAvailabilityRange> cpAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpAvailabilityRanges.size());
		Assert.assertEquals(newCPAvailabilityRange,
			cpAvailabilityRanges.get(newCPAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPAvailabilityRange> cpAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpAvailabilityRanges.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPAvailabilityRange.getPrimaryKey());

		Map<Serializable, CPAvailabilityRange> cpAvailabilityRanges = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpAvailabilityRanges.size());
		Assert.assertEquals(newCPAvailabilityRange,
			cpAvailabilityRanges.get(newCPAvailabilityRange.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPAvailabilityRangeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPAvailabilityRange>() {
				@Override
				public void performAction(
					CPAvailabilityRange cpAvailabilityRange) {
					Assert.assertNotNull(cpAvailabilityRange);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPAvailabilityRangeId",
				newCPAvailabilityRange.getCPAvailabilityRangeId()));

		List<CPAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPAvailabilityRange existingCPAvailabilityRange = result.get(0);

		Assert.assertEquals(existingCPAvailabilityRange, newCPAvailabilityRange);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPAvailabilityRangeId",
				RandomTestUtil.nextLong()));

		List<CPAvailabilityRange> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPAvailabilityRangeId"));

		Object newCPAvailabilityRangeId = newCPAvailabilityRange.getCPAvailabilityRangeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPAvailabilityRangeId",
				new Object[] { newCPAvailabilityRangeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPAvailabilityRangeId = result.get(0);

		Assert.assertEquals(existingCPAvailabilityRangeId,
			newCPAvailabilityRangeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPAvailabilityRange.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPAvailabilityRangeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPAvailabilityRangeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPAvailabilityRange newCPAvailabilityRange = addCPAvailabilityRange();

		_persistence.clearCache();

		CPAvailabilityRange existingCPAvailabilityRange = _persistence.findByPrimaryKey(newCPAvailabilityRange.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPAvailabilityRange.getUuid(),
				ReflectionTestUtil.invoke(existingCPAvailabilityRange,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPAvailabilityRange.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPAvailabilityRange,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CPAvailabilityRange addCPAvailabilityRange()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPAvailabilityRange cpAvailabilityRange = _persistence.create(pk);

		cpAvailabilityRange.setUuid(RandomTestUtil.randomString());

		cpAvailabilityRange.setGroupId(RandomTestUtil.nextLong());

		cpAvailabilityRange.setCompanyId(RandomTestUtil.nextLong());

		cpAvailabilityRange.setUserId(RandomTestUtil.nextLong());

		cpAvailabilityRange.setUserName(RandomTestUtil.randomString());

		cpAvailabilityRange.setCreateDate(RandomTestUtil.nextDate());

		cpAvailabilityRange.setModifiedDate(RandomTestUtil.nextDate());

		cpAvailabilityRange.setCPDefinitionId(RandomTestUtil.nextLong());

		cpAvailabilityRange.setTitle(RandomTestUtil.randomString());

		cpAvailabilityRange.setLastPublishDate(RandomTestUtil.nextDate());

		_cpAvailabilityRanges.add(_persistence.update(cpAvailabilityRange));

		return cpAvailabilityRange;
	}

	private List<CPAvailabilityRange> _cpAvailabilityRanges = new ArrayList<CPAvailabilityRange>();
	private CPAvailabilityRangePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}