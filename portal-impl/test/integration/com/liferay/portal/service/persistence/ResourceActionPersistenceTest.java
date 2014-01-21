/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourceActionException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.ResourceActionModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
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
public class ResourceActionPersistenceTest {
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
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		Assert.assertNotNull(resourceAction);

		Assert.assertEquals(resourceAction.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		_persistence.remove(newResourceAction);

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertNull(existingResourceAction);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceAction();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceAction newResourceAction = _persistence.create(pk);

		newResourceAction.setName(ServiceTestUtil.randomString());

		newResourceAction.setActionId(ServiceTestUtil.randomString());

		newResourceAction.setBitwiseValue(ServiceTestUtil.nextLong());

		newResourceAction.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newResourceAction);

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction.getResourceActionId(),
			newResourceAction.getResourceActionId());
		Assert.assertEquals(existingResourceAction.getName(),
			newResourceAction.getName());
		Assert.assertEquals(existingResourceAction.getActionId(),
			newResourceAction.getActionId());
		Assert.assertEquals(existingResourceAction.getBitwiseValue(),
			newResourceAction.getBitwiseValue());
		Assert.assertEquals(existingResourceAction.getMvccVersion(),
			newResourceAction.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourceActionException");
		}
		catch (NoSuchResourceActionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ResourceAction",
			"resourceActionId", true, "name", true, "actionId", true,
			"bitwiseValue", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceAction missingResourceAction = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceAction);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ResourceActionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ResourceAction resourceAction = (ResourceAction)object;

					Assert.assertNotNull(resourceAction);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				newResourceAction.getResourceActionId()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceAction existingResourceAction = result.get(0);

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				ServiceTestUtil.nextLong()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		Object newResourceActionId = newResourceAction.getResourceActionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { newResourceActionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceActionId = result.get(0);

		Assert.assertEquals(existingResourceActionId, newResourceActionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceAction newResourceAction = addResourceAction();

		_persistence.clearCache();

		ResourceActionModelImpl existingResourceActionModelImpl = (ResourceActionModelImpl)_persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingResourceActionModelImpl.getName(),
				existingResourceActionModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingResourceActionModelImpl.getActionId(),
				existingResourceActionModelImpl.getOriginalActionId()));
	}

	protected ResourceAction addResourceAction() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		resourceAction.setName(ServiceTestUtil.randomString());

		resourceAction.setActionId(ServiceTestUtil.randomString());

		resourceAction.setBitwiseValue(ServiceTestUtil.nextLong());

		resourceAction.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(resourceAction);

		return resourceAction;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceActionPersistenceTest.class);
	private ResourceActionPersistence _persistence = (ResourceActionPersistence)PortalBeanLocatorUtil.locate(ResourceActionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}