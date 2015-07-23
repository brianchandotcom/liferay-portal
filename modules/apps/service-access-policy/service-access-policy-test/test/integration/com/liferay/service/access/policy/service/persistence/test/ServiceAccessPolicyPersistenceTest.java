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

package com.liferay.service.access.policy.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;
import com.liferay.service.access.policy.model.ServiceAccessPolicy;
import com.liferay.service.access.policy.service.ServiceAccessPolicyLocalServiceUtil;
import com.liferay.service.access.policy.service.persistence.ServiceAccessPolicyPersistence;
import com.liferay.service.access.policy.service.persistence.ServiceAccessPolicyUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class ServiceAccessPolicyPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ServiceAccessPolicyUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ServiceAccessPolicy> iterator = _serviceAccessPolicies.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceAccessPolicy serviceAccessPolicy = _persistence.create(pk);

		Assert.assertNotNull(serviceAccessPolicy);

		Assert.assertEquals(serviceAccessPolicy.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		_persistence.remove(newServiceAccessPolicy);

		ServiceAccessPolicy existingServiceAccessPolicy = _persistence.fetchByPrimaryKey(newServiceAccessPolicy.getPrimaryKey());

		Assert.assertNull(existingServiceAccessPolicy);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addServiceAccessPolicy();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceAccessPolicy newServiceAccessPolicy = _persistence.create(pk);

		newServiceAccessPolicy.setUuid(RandomTestUtil.randomString());

		newServiceAccessPolicy.setCompanyId(RandomTestUtil.nextLong());

		newServiceAccessPolicy.setUserId(RandomTestUtil.nextLong());

		newServiceAccessPolicy.setUserName(RandomTestUtil.randomString());

		newServiceAccessPolicy.setCreateDate(RandomTestUtil.nextDate());

		newServiceAccessPolicy.setModifiedDate(RandomTestUtil.nextDate());

		newServiceAccessPolicy.setAllowedServiceSignatures(RandomTestUtil.randomString());

		newServiceAccessPolicy.setDefaultServiceAccessPolicy(RandomTestUtil.randomBoolean());

		newServiceAccessPolicy.setName(RandomTestUtil.randomString());

		newServiceAccessPolicy.setTitle(RandomTestUtil.randomString());

		_serviceAccessPolicies.add(_persistence.update(newServiceAccessPolicy));

		ServiceAccessPolicy existingServiceAccessPolicy = _persistence.findByPrimaryKey(newServiceAccessPolicy.getPrimaryKey());

		Assert.assertEquals(existingServiceAccessPolicy.getUuid(),
			newServiceAccessPolicy.getUuid());
		Assert.assertEquals(existingServiceAccessPolicy.getServiceAccessPolicyId(),
			newServiceAccessPolicy.getServiceAccessPolicyId());
		Assert.assertEquals(existingServiceAccessPolicy.getCompanyId(),
			newServiceAccessPolicy.getCompanyId());
		Assert.assertEquals(existingServiceAccessPolicy.getUserId(),
			newServiceAccessPolicy.getUserId());
		Assert.assertEquals(existingServiceAccessPolicy.getUserName(),
			newServiceAccessPolicy.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingServiceAccessPolicy.getCreateDate()),
			Time.getShortTimestamp(newServiceAccessPolicy.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingServiceAccessPolicy.getModifiedDate()),
			Time.getShortTimestamp(newServiceAccessPolicy.getModifiedDate()));
		Assert.assertEquals(existingServiceAccessPolicy.getAllowedServiceSignatures(),
			newServiceAccessPolicy.getAllowedServiceSignatures());
		Assert.assertEquals(existingServiceAccessPolicy.getDefaultServiceAccessPolicy(),
			newServiceAccessPolicy.getDefaultServiceAccessPolicy());
		Assert.assertEquals(existingServiceAccessPolicy.getName(),
			newServiceAccessPolicy.getName());
		Assert.assertEquals(existingServiceAccessPolicy.getTitle(),
			newServiceAccessPolicy.getTitle());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_N(0L, StringPool.NULL);

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		ServiceAccessPolicy existingServiceAccessPolicy = _persistence.findByPrimaryKey(newServiceAccessPolicy.getPrimaryKey());

		Assert.assertEquals(existingServiceAccessPolicy, newServiceAccessPolicy);
	}

	@Test(expected = NoSuchServiceAccessPolicyException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ServiceAccessPolicy> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ServiceAccessPolicy",
			"uuid", true, "serviceAccessPolicyId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "allowedServiceSignatures", true,
			"defaultServiceAccessPolicy", true, "name", true, "title", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		ServiceAccessPolicy existingServiceAccessPolicy = _persistence.fetchByPrimaryKey(newServiceAccessPolicy.getPrimaryKey());

		Assert.assertEquals(existingServiceAccessPolicy, newServiceAccessPolicy);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceAccessPolicy missingServiceAccessPolicy = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingServiceAccessPolicy);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy1 = addServiceAccessPolicy();
		ServiceAccessPolicy newServiceAccessPolicy2 = addServiceAccessPolicy();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceAccessPolicy1.getPrimaryKey());
		primaryKeys.add(newServiceAccessPolicy2.getPrimaryKey());

		Map<Serializable, ServiceAccessPolicy> serviceAccessPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, serviceAccessPolicies.size());
		Assert.assertEquals(newServiceAccessPolicy1,
			serviceAccessPolicies.get(newServiceAccessPolicy1.getPrimaryKey()));
		Assert.assertEquals(newServiceAccessPolicy2,
			serviceAccessPolicies.get(newServiceAccessPolicy2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ServiceAccessPolicy> serviceAccessPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceAccessPolicies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceAccessPolicy.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ServiceAccessPolicy> serviceAccessPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceAccessPolicies.size());
		Assert.assertEquals(newServiceAccessPolicy,
			serviceAccessPolicies.get(newServiceAccessPolicy.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ServiceAccessPolicy> serviceAccessPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceAccessPolicies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceAccessPolicy.getPrimaryKey());

		Map<Serializable, ServiceAccessPolicy> serviceAccessPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceAccessPolicies.size());
		Assert.assertEquals(newServiceAccessPolicy,
			serviceAccessPolicies.get(newServiceAccessPolicy.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ServiceAccessPolicyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)object;

					Assert.assertNotNull(serviceAccessPolicy);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceAccessPolicy.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceAccessPolicyId",
				newServiceAccessPolicy.getServiceAccessPolicyId()));

		List<ServiceAccessPolicy> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ServiceAccessPolicy existingServiceAccessPolicy = result.get(0);

		Assert.assertEquals(existingServiceAccessPolicy, newServiceAccessPolicy);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceAccessPolicy.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceAccessPolicyId",
				RandomTestUtil.nextLong()));

		List<ServiceAccessPolicy> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceAccessPolicy.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceAccessPolicyId"));

		Object newServiceAccessPolicyId = newServiceAccessPolicy.getServiceAccessPolicyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceAccessPolicyId",
				new Object[] { newServiceAccessPolicyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingServiceAccessPolicyId = result.get(0);

		Assert.assertEquals(existingServiceAccessPolicyId,
			newServiceAccessPolicyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceAccessPolicy.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceAccessPolicyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceAccessPolicyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ServiceAccessPolicy newServiceAccessPolicy = addServiceAccessPolicy();

		_persistence.clearCache();

		ServiceAccessPolicy existingServiceAccessPolicy = _persistence.findByPrimaryKey(newServiceAccessPolicy.getPrimaryKey());

		Assert.assertEquals(existingServiceAccessPolicy.getCompanyId(),
			ReflectionTestUtil.invoke(existingServiceAccessPolicy,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingServiceAccessPolicy.getName(),
				ReflectionTestUtil.invoke(existingServiceAccessPolicy,
					"getOriginalName", new Class<?>[0])));
	}

	protected ServiceAccessPolicy addServiceAccessPolicy()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceAccessPolicy serviceAccessPolicy = _persistence.create(pk);

		serviceAccessPolicy.setUuid(RandomTestUtil.randomString());

		serviceAccessPolicy.setCompanyId(RandomTestUtil.nextLong());

		serviceAccessPolicy.setUserId(RandomTestUtil.nextLong());

		serviceAccessPolicy.setUserName(RandomTestUtil.randomString());

		serviceAccessPolicy.setCreateDate(RandomTestUtil.nextDate());

		serviceAccessPolicy.setModifiedDate(RandomTestUtil.nextDate());

		serviceAccessPolicy.setAllowedServiceSignatures(RandomTestUtil.randomString());

		serviceAccessPolicy.setDefaultServiceAccessPolicy(RandomTestUtil.randomBoolean());

		serviceAccessPolicy.setName(RandomTestUtil.randomString());

		serviceAccessPolicy.setTitle(RandomTestUtil.randomString());

		_serviceAccessPolicies.add(_persistence.update(serviceAccessPolicy));

		return serviceAccessPolicy;
	}

	private List<ServiceAccessPolicy> _serviceAccessPolicies = new ArrayList<ServiceAccessPolicy>();
	private ServiceAccessPolicyPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}