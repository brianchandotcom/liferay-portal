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

import com.liferay.commerce.product.exception.NoSuchCPGroupException;
import com.liferay.commerce.product.model.CPGroup;
import com.liferay.commerce.product.service.CPGroupLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPGroupPersistence;
import com.liferay.commerce.product.service.persistence.CPGroupUtil;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CPGroupPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPGroupUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPGroup> iterator = _cpGroups.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPGroup cpGroup = _persistence.create(pk);

		Assert.assertNotNull(cpGroup);

		Assert.assertEquals(cpGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPGroup newCPGroup = addCPGroup();

		_persistence.remove(newCPGroup);

		CPGroup existingCPGroup = _persistence.fetchByPrimaryKey(newCPGroup.getPrimaryKey());

		Assert.assertNull(existingCPGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPGroup newCPGroup = _persistence.create(pk);

		newCPGroup.setGroupId(RandomTestUtil.nextLong());

		newCPGroup.setCompanyId(RandomTestUtil.nextLong());

		newCPGroup.setUserId(RandomTestUtil.nextLong());

		newCPGroup.setUserName(RandomTestUtil.randomString());

		newCPGroup.setCreateDate(RandomTestUtil.nextDate());

		newCPGroup.setModifiedDate(RandomTestUtil.nextDate());

		_cpGroups.add(_persistence.update(newCPGroup));

		CPGroup existingCPGroup = _persistence.findByPrimaryKey(newCPGroup.getPrimaryKey());

		Assert.assertEquals(existingCPGroup.getCPGroupId(),
			newCPGroup.getCPGroupId());
		Assert.assertEquals(existingCPGroup.getGroupId(),
			newCPGroup.getGroupId());
		Assert.assertEquals(existingCPGroup.getCompanyId(),
			newCPGroup.getCompanyId());
		Assert.assertEquals(existingCPGroup.getUserId(), newCPGroup.getUserId());
		Assert.assertEquals(existingCPGroup.getUserName(),
			newCPGroup.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPGroup.getCreateDate()),
			Time.getShortTimestamp(newCPGroup.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPGroup.getModifiedDate()),
			Time.getShortTimestamp(newCPGroup.getModifiedDate()));
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPGroup newCPGroup = addCPGroup();

		CPGroup existingCPGroup = _persistence.findByPrimaryKey(newCPGroup.getPrimaryKey());

		Assert.assertEquals(existingCPGroup, newCPGroup);
	}

	@Test(expected = NoSuchCPGroupException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPGroup> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPGroup", "CPGroupId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPGroup newCPGroup = addCPGroup();

		CPGroup existingCPGroup = _persistence.fetchByPrimaryKey(newCPGroup.getPrimaryKey());

		Assert.assertEquals(existingCPGroup, newCPGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPGroup missingCPGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPGroup newCPGroup1 = addCPGroup();
		CPGroup newCPGroup2 = addCPGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPGroup1.getPrimaryKey());
		primaryKeys.add(newCPGroup2.getPrimaryKey());

		Map<Serializable, CPGroup> cpGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpGroups.size());
		Assert.assertEquals(newCPGroup1,
			cpGroups.get(newCPGroup1.getPrimaryKey()));
		Assert.assertEquals(newCPGroup2,
			cpGroups.get(newCPGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPGroup> cpGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPGroup newCPGroup = addCPGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPGroup> cpGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpGroups.size());
		Assert.assertEquals(newCPGroup, cpGroups.get(newCPGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPGroup> cpGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPGroup newCPGroup = addCPGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPGroup.getPrimaryKey());

		Map<Serializable, CPGroup> cpGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpGroups.size());
		Assert.assertEquals(newCPGroup, cpGroups.get(newCPGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPGroup>() {
				@Override
				public void performAction(CPGroup cpGroup) {
					Assert.assertNotNull(cpGroup);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPGroup newCPGroup = addCPGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPGroupId",
				newCPGroup.getCPGroupId()));

		List<CPGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPGroup existingCPGroup = result.get(0);

		Assert.assertEquals(existingCPGroup, newCPGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPGroupId",
				RandomTestUtil.nextLong()));

		List<CPGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPGroup newCPGroup = addCPGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CPGroupId"));

		Object newCPGroupId = newCPGroup.getCPGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPGroupId",
				new Object[] { newCPGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPGroupId = result.get(0);

		Assert.assertEquals(existingCPGroupId, newCPGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("CPGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPGroupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPGroup newCPGroup = addCPGroup();

		_persistence.clearCache();

		CPGroup existingCPGroup = _persistence.findByPrimaryKey(newCPGroup.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingCPGroup.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPGroup,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CPGroup addCPGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPGroup cpGroup = _persistence.create(pk);

		cpGroup.setGroupId(RandomTestUtil.nextLong());

		cpGroup.setCompanyId(RandomTestUtil.nextLong());

		cpGroup.setUserId(RandomTestUtil.nextLong());

		cpGroup.setUserName(RandomTestUtil.randomString());

		cpGroup.setCreateDate(RandomTestUtil.nextDate());

		cpGroup.setModifiedDate(RandomTestUtil.nextDate());

		_cpGroups.add(_persistence.update(cpGroup));

		return cpGroup;
	}

	private List<CPGroup> _cpGroups = new ArrayList<CPGroup>();
	private CPGroupPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}