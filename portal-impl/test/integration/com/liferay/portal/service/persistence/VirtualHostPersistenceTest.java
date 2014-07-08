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

import com.liferay.portal.NoSuchVirtualHostException;
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
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
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
public class VirtualHostPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<VirtualHost> modelListener : _modelListeners) {
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

		for (ModelListener<VirtualHost> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		Assert.assertNotNull(virtualHost);

		Assert.assertEquals(virtualHost.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.remove(newVirtualHost);

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertNull(existingVirtualHost);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVirtualHost();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost newVirtualHost = _persistence.create(pk);

		newVirtualHost.setMvccVersion(RandomTestUtil.nextLong());

		newVirtualHost.setCompanyId(RandomTestUtil.nextLong());

		newVirtualHost.setLayoutSetId(RandomTestUtil.nextLong());

		newVirtualHost.setHostname(RandomTestUtil.randomString());

		_persistence.update(newVirtualHost);

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost.getMvccVersion(),
			newVirtualHost.getMvccVersion());
		Assert.assertEquals(existingVirtualHost.getVirtualHostId(),
			newVirtualHost.getVirtualHostId());
		Assert.assertEquals(existingVirtualHost.getCompanyId(),
			newVirtualHost.getCompanyId());
		Assert.assertEquals(existingVirtualHost.getLayoutSetId(),
			newVirtualHost.getLayoutSetId());
		Assert.assertEquals(existingVirtualHost.getHostname(),
			newVirtualHost.getHostname());
	}

	@Test
	public void testCountByHostname() {
		try {
			_persistence.countByHostname(StringPool.BLANK);

			_persistence.countByHostname(StringPool.NULL);

			_persistence.countByHostname((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_L() {
		try {
			_persistence.countByC_L(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_L(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchVirtualHostException");
		}
		catch (NoSuchVirtualHostException nsee) {
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
		return OrderByComparatorFactoryUtil.create("VirtualHost",
			"mvccVersion", true, "virtualHostId", true, "companyId", true,
			"layoutSetId", true, "hostname", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost missingVirtualHost = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVirtualHost);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		VirtualHost newVirtualHost1 = addVirtualHost();
		VirtualHost newVirtualHost2 = addVirtualHost();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost1.getPrimaryKey());
		primaryKeys.add(newVirtualHost2.getPrimaryKey());

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, virtualHosts.size());
		Assert.assertEquals(newVirtualHost1,
			virtualHosts.get(newVirtualHost1.getPrimaryKey()));
		Assert.assertEquals(newVirtualHost2,
			virtualHosts.get(newVirtualHost2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(virtualHosts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, virtualHosts.size());
		Assert.assertEquals(newVirtualHost,
			virtualHosts.get(newVirtualHost.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(virtualHosts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost.getPrimaryKey());

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, virtualHosts.size());
		Assert.assertEquals(newVirtualHost,
			virtualHosts.get(newVirtualHost.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = VirtualHostLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					VirtualHost virtualHost = (VirtualHost)object;

					Assert.assertNotNull(virtualHost);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId",
				newVirtualHost.getVirtualHostId()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		VirtualHost existingVirtualHost = result.get(0);

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId",
				RandomTestUtil.nextLong()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		Object newVirtualHostId = newVirtualHost.getVirtualHostId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { newVirtualHostId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVirtualHostId = result.get(0);

		Assert.assertEquals(existingVirtualHostId, newVirtualHostId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.clearCache();

		VirtualHostModelImpl existingVirtualHostModelImpl = (VirtualHostModelImpl)_persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingVirtualHostModelImpl.getHostname(),
				existingVirtualHostModelImpl.getOriginalHostname()));

		Assert.assertEquals(existingVirtualHostModelImpl.getCompanyId(),
			existingVirtualHostModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingVirtualHostModelImpl.getLayoutSetId(),
			existingVirtualHostModelImpl.getOriginalLayoutSetId());
	}

	protected VirtualHost addVirtualHost() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		virtualHost.setMvccVersion(RandomTestUtil.nextLong());

		virtualHost.setCompanyId(RandomTestUtil.nextLong());

		virtualHost.setLayoutSetId(RandomTestUtil.nextLong());

		virtualHost.setHostname(RandomTestUtil.randomString());

		_persistence.update(virtualHost);

		return virtualHost;
	}

	private static Log _log = LogFactoryUtil.getLog(VirtualHostPersistenceTest.class);
	private ModelListener<VirtualHost>[] _modelListeners;
	private VirtualHostPersistence _persistence = (VirtualHostPersistence)PortalBeanLocatorUtil.locate(VirtualHostPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}