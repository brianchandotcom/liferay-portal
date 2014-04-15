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

import com.liferay.portal.NoSuchPortletException;
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
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class PortletPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Portlet> modelListener : _modelListeners) {
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

		for (ModelListener<Portlet> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Portlet portlet = _persistence.create(pk);

		Assert.assertNotNull(portlet);

		Assert.assertEquals(portlet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Portlet newPortlet = addPortlet();

		_persistence.remove(newPortlet);

		Portlet existingPortlet = _persistence.fetchByPrimaryKey(newPortlet.getPrimaryKey());

		Assert.assertNull(existingPortlet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortlet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Portlet newPortlet = _persistence.create(pk);

		newPortlet.setMvccVersion(ServiceTestUtil.nextLong());

		newPortlet.setCompanyId(ServiceTestUtil.nextLong());

		newPortlet.setPortletId(ServiceTestUtil.randomString());

		newPortlet.setRoles(ServiceTestUtil.randomString());

		newPortlet.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(newPortlet);

		Portlet existingPortlet = _persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		Assert.assertEquals(existingPortlet.getMvccVersion(),
			newPortlet.getMvccVersion());
		Assert.assertEquals(existingPortlet.getId(), newPortlet.getId());
		Assert.assertEquals(existingPortlet.getCompanyId(),
			newPortlet.getCompanyId());
		Assert.assertEquals(existingPortlet.getPortletId(),
			newPortlet.getPortletId());
		Assert.assertEquals(existingPortlet.getRoles(), newPortlet.getRoles());
		Assert.assertEquals(existingPortlet.getActive(), newPortlet.getActive());
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(ServiceTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_P() {
		try {
			_persistence.countByC_P(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_P(0L, StringPool.NULL);

			_persistence.countByC_P(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Portlet newPortlet = addPortlet();

		Portlet existingPortlet = _persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		Assert.assertEquals(existingPortlet, newPortlet);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchPortletException");
		}
		catch (NoSuchPortletException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Portlet", "mvccVersion",
			true, "id", true, "companyId", true, "portletId", true, "roles",
			true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Portlet newPortlet = addPortlet();

		Portlet existingPortlet = _persistence.fetchByPrimaryKey(newPortlet.getPrimaryKey());

		Assert.assertEquals(existingPortlet, newPortlet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Portlet missingPortlet = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortlet);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new PortletActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Portlet portlet = (Portlet)object;

					Assert.assertNotNull(portlet);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Portlet newPortlet = addPortlet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", newPortlet.getId()));

		List<Portlet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Portlet existingPortlet = result.get(0);

		Assert.assertEquals(existingPortlet, newPortlet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				ServiceTestUtil.nextLong()));

		List<Portlet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Portlet newPortlet = addPortlet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newPortlet.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingId = result.get(0);

		Assert.assertEquals(existingId, newId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Portlet newPortlet = addPortlet();

		_persistence.clearCache();

		PortletModelImpl existingPortletModelImpl = (PortletModelImpl)_persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		Assert.assertEquals(existingPortletModelImpl.getCompanyId(),
			existingPortletModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingPortletModelImpl.getPortletId(),
				existingPortletModelImpl.getOriginalPortletId()));
	}

	protected Portlet addPortlet() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Portlet portlet = _persistence.create(pk);

		portlet.setMvccVersion(ServiceTestUtil.nextLong());

		portlet.setCompanyId(ServiceTestUtil.nextLong());

		portlet.setPortletId(ServiceTestUtil.randomString());

		portlet.setRoles(ServiceTestUtil.randomString());

		portlet.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(portlet);

		return portlet;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletPersistenceTest.class);
	private ModelListener<Portlet>[] _modelListeners;
	private PortletPersistence _persistence = (PortletPersistence)PortalBeanLocatorUtil.locate(PortletPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}