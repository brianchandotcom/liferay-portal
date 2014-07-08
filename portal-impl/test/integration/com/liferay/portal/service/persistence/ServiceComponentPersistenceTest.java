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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchServiceComponentException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ServiceComponentPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ServiceComponent> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<ServiceComponent> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		Assert.assertNotNull(serviceComponent);

		Assert.assertEquals(serviceComponent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		_persistence.remove(newServiceComponent);

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		Assert.assertNull(existingServiceComponent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addServiceComponent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceComponent newServiceComponent = _persistence.create(pk);

		newServiceComponent.setMvccVersion(RandomTestUtil.nextLong());

		newServiceComponent.setBuildNamespace(RandomTestUtil.randomString());

		newServiceComponent.setBuildNumber(RandomTestUtil.nextLong());

		newServiceComponent.setBuildDate(RandomTestUtil.nextLong());

		newServiceComponent.setData(RandomTestUtil.randomString());

		_persistence.update(newServiceComponent);

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		Assert.assertEquals(existingServiceComponent.getMvccVersion(),
			newServiceComponent.getMvccVersion());
		Assert.assertEquals(existingServiceComponent.getServiceComponentId(),
			newServiceComponent.getServiceComponentId());
		Assert.assertEquals(existingServiceComponent.getBuildNamespace(),
			newServiceComponent.getBuildNamespace());
		Assert.assertEquals(existingServiceComponent.getBuildNumber(),
			newServiceComponent.getBuildNumber());
		Assert.assertEquals(existingServiceComponent.getBuildDate(),
			newServiceComponent.getBuildDate());
		Assert.assertEquals(existingServiceComponent.getData(),
			newServiceComponent.getData());
	}

	@Test
	public void testCountByBuildNamespace() {
		try {
			_persistence.countByBuildNamespace(StringPool.BLANK);

			_persistence.countByBuildNamespace(StringPool.NULL);

			_persistence.countByBuildNamespace((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByBNS_BNU() {
		try {
			_persistence.countByBNS_BNU(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByBNS_BNU(StringPool.NULL, 0L);

			_persistence.countByBNS_BNU((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		Assert.assertEquals(existingServiceComponent, newServiceComponent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchServiceComponentException");
		}
		catch (NoSuchServiceComponentException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ServiceComponent",
			"mvccVersion", true, "serviceComponentId", true, "buildNamespace",
			true, "buildNumber", true, "buildDate", true, "data", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		Assert.assertEquals(existingServiceComponent, newServiceComponent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceComponent missingServiceComponent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingServiceComponent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ServiceComponent newServiceComponent1 = addServiceComponent();
		ServiceComponent newServiceComponent2 = addServiceComponent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceComponent1.getPrimaryKey());
		primaryKeys.add(newServiceComponent2.getPrimaryKey());

		Map<Serializable, ServiceComponent> serviceComponents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, serviceComponents.size());
		Assert.assertEquals(newServiceComponent1,
			serviceComponents.get(newServiceComponent1.getPrimaryKey()));
		Assert.assertEquals(newServiceComponent2,
			serviceComponents.get(newServiceComponent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ServiceComponent> serviceComponents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceComponents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceComponent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ServiceComponent> serviceComponents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceComponents.size());
		Assert.assertEquals(newServiceComponent,
			serviceComponents.get(newServiceComponent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ServiceComponent> serviceComponents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(serviceComponents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newServiceComponent.getPrimaryKey());

		Map<Serializable, ServiceComponent> serviceComponents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, serviceComponents.size());
		Assert.assertEquals(newServiceComponent,
			serviceComponents.get(newServiceComponent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ServiceComponentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ServiceComponent serviceComponent = (ServiceComponent)object;

					Assert.assertNotNull(serviceComponent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				newServiceComponent.getServiceComponentId()));

		List<ServiceComponent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ServiceComponent existingServiceComponent = result.get(0);

		Assert.assertEquals(existingServiceComponent, newServiceComponent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				RandomTestUtil.nextLong()));

		List<ServiceComponent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceComponentId"));

		Object newServiceComponentId = newServiceComponent.getServiceComponentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceComponentId",
				new Object[] { newServiceComponentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingServiceComponentId = result.get(0);

		Assert.assertEquals(existingServiceComponentId, newServiceComponentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"serviceComponentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("serviceComponentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ServiceComponent newServiceComponent = addServiceComponent();

		_persistence.clearCache();

		ServiceComponentModelImpl existingServiceComponentModelImpl = (ServiceComponentModelImpl)_persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingServiceComponentModelImpl.getBuildNamespace(),
				existingServiceComponentModelImpl.getOriginalBuildNamespace()));
		Assert.assertEquals(existingServiceComponentModelImpl.getBuildNumber(),
			existingServiceComponentModelImpl.getOriginalBuildNumber());
	}

	protected ServiceComponent addServiceComponent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		serviceComponent.setMvccVersion(RandomTestUtil.nextLong());

		serviceComponent.setBuildNamespace(RandomTestUtil.randomString());

		serviceComponent.setBuildNumber(RandomTestUtil.nextLong());

		serviceComponent.setBuildDate(RandomTestUtil.nextLong());

		serviceComponent.setData(RandomTestUtil.randomString());

		_persistence.update(serviceComponent);

		return serviceComponent;
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceComponentPersistenceTest.class);
	private ModelListener<ServiceComponent>[] _modelListeners;
	private ServiceComponentPersistence _persistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}