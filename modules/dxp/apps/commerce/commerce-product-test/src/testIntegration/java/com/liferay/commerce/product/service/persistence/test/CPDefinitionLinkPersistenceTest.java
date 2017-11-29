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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkUtil;

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
public class CPDefinitionLinkPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionLink> iterator = _cpDefinitionLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionLink cpDefinitionLink = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionLink);

		Assert.assertEquals(cpDefinitionLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		_persistence.remove(newCPDefinitionLink);

		CPDefinitionLink existingCPDefinitionLink = _persistence.fetchByPrimaryKey(newCPDefinitionLink.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionLink newCPDefinitionLink = _persistence.create(pk);

		newCPDefinitionLink.setUuid(RandomTestUtil.randomString());

		newCPDefinitionLink.setGroupId(RandomTestUtil.nextLong());

		newCPDefinitionLink.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionLink.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionLink.setUserName(RandomTestUtil.randomString());

		newCPDefinitionLink.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionLink.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionLink.setCPDefinitionId1(RandomTestUtil.nextLong());

		newCPDefinitionLink.setCPDefinitionId2(RandomTestUtil.nextLong());

		newCPDefinitionLink.setPriority(RandomTestUtil.nextDouble());

		newCPDefinitionLink.setType(RandomTestUtil.nextInt());

		_cpDefinitionLinks.add(_persistence.update(newCPDefinitionLink));

		CPDefinitionLink existingCPDefinitionLink = _persistence.findByPrimaryKey(newCPDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionLink.getUuid(),
			newCPDefinitionLink.getUuid());
		Assert.assertEquals(existingCPDefinitionLink.getCPDefinitionLinkId(),
			newCPDefinitionLink.getCPDefinitionLinkId());
		Assert.assertEquals(existingCPDefinitionLink.getGroupId(),
			newCPDefinitionLink.getGroupId());
		Assert.assertEquals(existingCPDefinitionLink.getCompanyId(),
			newCPDefinitionLink.getCompanyId());
		Assert.assertEquals(existingCPDefinitionLink.getUserId(),
			newCPDefinitionLink.getUserId());
		Assert.assertEquals(existingCPDefinitionLink.getUserName(),
			newCPDefinitionLink.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionLink.getCreateDate()),
			Time.getShortTimestamp(newCPDefinitionLink.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionLink.getModifiedDate()),
			Time.getShortTimestamp(newCPDefinitionLink.getModifiedDate()));
		Assert.assertEquals(existingCPDefinitionLink.getCPDefinitionId1(),
			newCPDefinitionLink.getCPDefinitionId1());
		Assert.assertEquals(existingCPDefinitionLink.getCPDefinitionId2(),
			newCPDefinitionLink.getCPDefinitionId2());
		AssertUtils.assertEquals(existingCPDefinitionLink.getPriority(),
			newCPDefinitionLink.getPriority());
		Assert.assertEquals(existingCPDefinitionLink.getType(),
			newCPDefinitionLink.getType());
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
	public void testCountByCPDefinitionId1() throws Exception {
		_persistence.countByCPDefinitionId1(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId1(0L);
	}

	@Test
	public void testCountByCPDefinitionId2() throws Exception {
		_persistence.countByCPDefinitionId2(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId2(0L);
	}

	@Test
	public void testCountByC1_T() throws Exception {
		_persistence.countByC1_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC1_T(0L, 0);
	}

	@Test
	public void testCountByC2_T() throws Exception {
		_persistence.countByC2_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC2_T(0L, 0);
	}

	@Test
	public void testCountByC1_C2_T() throws Exception {
		_persistence.countByC1_C2_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC1_C2_T(0L, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		CPDefinitionLink existingCPDefinitionLink = _persistence.findByPrimaryKey(newCPDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionLink, newCPDefinitionLink);
	}

	@Test(expected = NoSuchCPDefinitionLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPDefinitionLink", "uuid",
			true, "CPDefinitionLinkId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CPDefinitionId1", true, "CPDefinitionId2",
			true, "priority", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		CPDefinitionLink existingCPDefinitionLink = _persistence.fetchByPrimaryKey(newCPDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionLink, newCPDefinitionLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionLink missingCPDefinitionLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPDefinitionLink newCPDefinitionLink1 = addCPDefinitionLink();
		CPDefinitionLink newCPDefinitionLink2 = addCPDefinitionLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionLink1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionLink2.getPrimaryKey());

		Map<Serializable, CPDefinitionLink> cpDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionLinks.size());
		Assert.assertEquals(newCPDefinitionLink1,
			cpDefinitionLinks.get(newCPDefinitionLink1.getPrimaryKey()));
		Assert.assertEquals(newCPDefinitionLink2,
			cpDefinitionLinks.get(newCPDefinitionLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionLink> cpDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionLink> cpDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionLinks.size());
		Assert.assertEquals(newCPDefinitionLink,
			cpDefinitionLinks.get(newCPDefinitionLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionLink> cpDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionLink.getPrimaryKey());

		Map<Serializable, CPDefinitionLink> cpDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionLinks.size());
		Assert.assertEquals(newCPDefinitionLink,
			cpDefinitionLinks.get(newCPDefinitionLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPDefinitionLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPDefinitionLink>() {
				@Override
				public void performAction(CPDefinitionLink cpDefinitionLink) {
					Assert.assertNotNull(cpDefinitionLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPDefinitionLinkId",
				newCPDefinitionLink.getCPDefinitionLinkId()));

		List<CPDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionLink existingCPDefinitionLink = result.get(0);

		Assert.assertEquals(existingCPDefinitionLink, newCPDefinitionLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPDefinitionLinkId",
				RandomTestUtil.nextLong()));

		List<CPDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionLinkId"));

		Object newCPDefinitionLinkId = newCPDefinitionLink.getCPDefinitionLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPDefinitionLinkId",
				new Object[] { newCPDefinitionLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionLinkId = result.get(0);

		Assert.assertEquals(existingCPDefinitionLinkId, newCPDefinitionLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPDefinitionLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionLink newCPDefinitionLink = addCPDefinitionLink();

		_persistence.clearCache();

		CPDefinitionLink existingCPDefinitionLink = _persistence.findByPrimaryKey(newCPDefinitionLink.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPDefinitionLink.getUuid(),
				ReflectionTestUtil.invoke(existingCPDefinitionLink,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCPDefinitionLink.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPDefinitionLink,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionLink.getCPDefinitionId1()),
			ReflectionTestUtil.<Long>invoke(existingCPDefinitionLink,
				"getOriginalCPDefinitionId1", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionLink.getCPDefinitionId2()),
			ReflectionTestUtil.<Long>invoke(existingCPDefinitionLink,
				"getOriginalCPDefinitionId2", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(existingCPDefinitionLink.getType()),
			ReflectionTestUtil.<Integer>invoke(existingCPDefinitionLink,
				"getOriginalType", new Class<?>[0]));
	}

	protected CPDefinitionLink addCPDefinitionLink() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionLink cpDefinitionLink = _persistence.create(pk);

		cpDefinitionLink.setUuid(RandomTestUtil.randomString());

		cpDefinitionLink.setGroupId(RandomTestUtil.nextLong());

		cpDefinitionLink.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionLink.setUserId(RandomTestUtil.nextLong());

		cpDefinitionLink.setUserName(RandomTestUtil.randomString());

		cpDefinitionLink.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionLink.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionLink.setCPDefinitionId1(RandomTestUtil.nextLong());

		cpDefinitionLink.setCPDefinitionId2(RandomTestUtil.nextLong());

		cpDefinitionLink.setPriority(RandomTestUtil.nextDouble());

		cpDefinitionLink.setType(RandomTestUtil.nextInt());

		_cpDefinitionLinks.add(_persistence.update(cpDefinitionLink));

		return cpDefinitionLink;
	}

	private List<CPDefinitionLink> _cpDefinitionLinks = new ArrayList<CPDefinitionLink>();
	private CPDefinitionLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}