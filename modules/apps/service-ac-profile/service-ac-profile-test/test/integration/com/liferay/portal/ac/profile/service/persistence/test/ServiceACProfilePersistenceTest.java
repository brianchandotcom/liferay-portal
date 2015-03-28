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

package com.liferay.portal.ac.profile.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;
import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.ac.profile.service.ServiceACProfileLocalServiceUtil;
import com.liferay.portal.ac.profile.service.persistence.ServiceACProfilePersistence;
import com.liferay.portal.ac.profile.service.persistence.ServiceACProfileUtil;
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
import com.liferay.portal.util.PropsValues;

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
public class ServiceACProfilePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ServiceACProfileUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ServiceACProfile> iterator = _serviceACProfiles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceACProfile serviceACProfile = _persistence.create(pk);

		Assert.assertNotNull(serviceACProfile);

		Assert.assertEquals(serviceACProfile.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		_persistence.remove(newServiceACProfile);

		ServiceACProfile existingServiceACProfile = _persistence.fetchByPrimaryKey(newServiceACProfile.getPrimaryKey());

		Assert.assertNull(existingServiceACProfile);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addServiceACProfile();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceACProfile newServiceACProfile = _persistence.create(pk);

		newServiceACProfile.setUuid(RandomTestUtil.randomString());

		newServiceACProfile.setCompanyId(RandomTestUtil.nextLong());

		newServiceACProfile.setUserId(RandomTestUtil.nextLong());

		newServiceACProfile.setUserName(RandomTestUtil.randomString());

		newServiceACProfile.setCreateDate(RandomTestUtil.nextDate());

		newServiceACProfile.setModifiedDate(RandomTestUtil.nextDate());

		newServiceACProfile.setAllowedServices(RandomTestUtil.randomString());

		newServiceACProfile.setName(RandomTestUtil.randomString());

		newServiceACProfile.setTitle(RandomTestUtil.randomString());

		_serviceACProfiles.add(_persistence.update(newServiceACProfile));

		ServiceACProfile existingServiceACProfile = _persistence.findByPrimaryKey(newServiceACProfile.getPrimaryKey());

		Assert.assertEquals(existingServiceACProfile.getUuid(),
			newServiceACProfile.getUuid());
		Assert.assertEquals(existingServiceACProfile.getServiceACProfileId(),
			newServiceACProfile.getServiceACProfileId());
		Assert.assertEquals(existingServiceACProfile.getCompanyId(),
			newServiceACProfile.getCompanyId());
		Assert.assertEquals(existingServiceACProfile.getUserId(),
			newServiceACProfile.getUserId());
		Assert.assertEquals(existingServiceACProfile.getUserName(),
			newServiceACProfile.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingServiceACProfile.getCreateDate()),
			Time.getShortTimestamp(newServiceACProfile.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingServiceACProfile.getModifiedDate()),
			Time.getShortTimestamp(newServiceACProfile.getModifiedDate()));
		Assert.assertEquals(existingServiceACProfile.getAllowedServices(),
			newServiceACProfile.getAllowedServices());
		Assert.assertEquals(existingServiceACProfile.getName(),
			newServiceACProfile.getName());
		Assert.assertEquals(existingServiceACProfile.getTitle(),
			newServiceACProfile.getTitle());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N() {
		try {
			_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_N(0L, StringPool.NULL);

			_persistence.countByC_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		ServiceACProfile existingServiceACProfile = _persistence.findByPrimaryKey(newServiceACProfile.getPrimaryKey());

		Assert.assertEquals(existingServiceACProfile, newServiceACProfile);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchServiceACProfileException");
		}
		catch (NoSuchServiceACProfileException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<ServiceACProfile> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ServiceACProfile", "uuid",
			true, "serviceACProfileId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"allowedServices", true, "name", true, "title", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		ServiceACProfile existingServiceACProfile = _persistence.fetchByPrimaryKey(newServiceACProfile.getPrimaryKey());

		Assert.assertEquals(existingServiceACProfile, newServiceACProfile);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceACProfile missingServiceACProfile = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingServiceACProfile);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ServiceACProfile newServiceACProfile1 = addServiceACProfile();
		ServiceACProfile newServiceACProfile2 = addServiceACProfile();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceACProfile1.getPrimaryKey());
		primaryKeys.add(newServiceACProfile2.getPrimaryKey());

		Map<Serializable, ServiceACProfile> serviceACProfiles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, serviceACProfiles.size());
		Assert.assertEquals(newServiceACProfile1,
			serviceACProfiles.get(newServiceACProfile1.getPrimaryKey()));
		Assert.assertEquals(newServiceACProfile2,
			serviceACProfiles.get(newServiceACProfile2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ServiceACProfile> serviceACProfiles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceACProfiles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceACProfile.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ServiceACProfile> serviceACProfiles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceACProfiles.size());
		Assert.assertEquals(newServiceACProfile,
			serviceACProfiles.get(newServiceACProfile.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ServiceACProfile> serviceACProfiles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceACProfiles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceACProfile.getPrimaryKey());

		Map<Serializable, ServiceACProfile> serviceACProfiles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceACProfiles.size());
		Assert.assertEquals(newServiceACProfile,
			serviceACProfiles.get(newServiceACProfile.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ServiceACProfileLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ServiceACProfile serviceACProfile = (ServiceACProfile)object;

					Assert.assertNotNull(serviceACProfile);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceACProfile.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceACProfileId",
				newServiceACProfile.getServiceACProfileId()));

		List<ServiceACProfile> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ServiceACProfile existingServiceACProfile = result.get(0);

		Assert.assertEquals(existingServiceACProfile, newServiceACProfile);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceACProfile.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceACProfileId",
				RandomTestUtil.nextLong()));

		List<ServiceACProfile> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ServiceACProfile newServiceACProfile = addServiceACProfile();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceACProfile.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceACProfileId"));

		Object newServiceACProfileId = newServiceACProfile.getServiceACProfileId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceACProfileId",
				new Object[] { newServiceACProfileId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingServiceACProfileId = result.get(0);

		Assert.assertEquals(existingServiceACProfileId, newServiceACProfileId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceACProfile.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceACProfileId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceACProfileId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ServiceACProfile newServiceACProfile = addServiceACProfile();

		_persistence.clearCache();

		ServiceACProfile existingServiceACProfile = _persistence.findByPrimaryKey(newServiceACProfile.getPrimaryKey());

		Assert.assertEquals(existingServiceACProfile.getCompanyId(),
			ReflectionTestUtil.invoke(existingServiceACProfile,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingServiceACProfile.getName(),
				ReflectionTestUtil.invoke(existingServiceACProfile,
					"getOriginalName", new Class<?>[0])));
	}

	protected ServiceACProfile addServiceACProfile() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceACProfile serviceACProfile = _persistence.create(pk);

		serviceACProfile.setUuid(RandomTestUtil.randomString());

		serviceACProfile.setCompanyId(RandomTestUtil.nextLong());

		serviceACProfile.setUserId(RandomTestUtil.nextLong());

		serviceACProfile.setUserName(RandomTestUtil.randomString());

		serviceACProfile.setCreateDate(RandomTestUtil.nextDate());

		serviceACProfile.setModifiedDate(RandomTestUtil.nextDate());

		serviceACProfile.setAllowedServices(RandomTestUtil.randomString());

		serviceACProfile.setName(RandomTestUtil.randomString());

		serviceACProfile.setTitle(RandomTestUtil.randomString());

		_serviceACProfiles.add(_persistence.update(serviceACProfile));

		return serviceACProfile;
	}

	private List<ServiceACProfile> _serviceACProfiles = new ArrayList<ServiceACProfile>();
	private ServiceACProfilePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}