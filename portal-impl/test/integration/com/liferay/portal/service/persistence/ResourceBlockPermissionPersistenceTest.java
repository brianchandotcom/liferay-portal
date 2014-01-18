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

import com.liferay.portal.NoSuchResourceBlockPermissionException;
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
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl;
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
public class ResourceBlockPermissionPersistenceTest {
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

		ResourceBlockPermission resourceBlockPermission = _persistence.create(pk);

		Assert.assertNotNull(resourceBlockPermission);

		Assert.assertEquals(resourceBlockPermission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		_persistence.remove(newResourceBlockPermission);

		ResourceBlockPermission existingResourceBlockPermission = _persistence.fetchByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertNull(existingResourceBlockPermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceBlockPermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlockPermission newResourceBlockPermission = _persistence.create(pk);

		newResourceBlockPermission.setResourceBlockId(ServiceTestUtil.nextLong());

		newResourceBlockPermission.setRoleId(ServiceTestUtil.nextLong());

		newResourceBlockPermission.setActionIds(ServiceTestUtil.nextLong());

		newResourceBlockPermission.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newResourceBlockPermission);

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission.getResourceBlockPermissionId(),
			newResourceBlockPermission.getResourceBlockPermissionId());
		Assert.assertEquals(existingResourceBlockPermission.getResourceBlockId(),
			newResourceBlockPermission.getResourceBlockId());
		Assert.assertEquals(existingResourceBlockPermission.getRoleId(),
			newResourceBlockPermission.getRoleId());
		Assert.assertEquals(existingResourceBlockPermission.getActionIds(),
			newResourceBlockPermission.getActionIds());
		Assert.assertEquals(existingResourceBlockPermission.getMvccVersion(),
			newResourceBlockPermission.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission,
			newResourceBlockPermission);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourceBlockPermissionException");
		}
		catch (NoSuchResourceBlockPermissionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ResourceBlockPermission",
			"resourceBlockPermissionId", true, "resourceBlockId", true,
			"roleId", true, "actionIds", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.fetchByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission,
			newResourceBlockPermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlockPermission missingResourceBlockPermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceBlockPermission);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ResourceBlockPermissionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)object;

					Assert.assertNotNull(resourceBlockPermission);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceBlockPermissionId",
				newResourceBlockPermission.getResourceBlockPermissionId()));

		List<ResourceBlockPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceBlockPermission existingResourceBlockPermission = result.get(0);

		Assert.assertEquals(existingResourceBlockPermission,
			newResourceBlockPermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceBlockPermissionId", ServiceTestUtil.nextLong()));

		List<ResourceBlockPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockPermissionId"));

		Object newResourceBlockPermissionId = newResourceBlockPermission.getResourceBlockPermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceBlockPermissionId",
				new Object[] { newResourceBlockPermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceBlockPermissionId = result.get(0);

		Assert.assertEquals(existingResourceBlockPermissionId,
			newResourceBlockPermissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				ResourceBlockPermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockPermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceBlockPermissionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		_persistence.clearCache();

		ResourceBlockPermissionModelImpl existingResourceBlockPermissionModelImpl =
			(ResourceBlockPermissionModelImpl)_persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermissionModelImpl.getResourceBlockId(),
			existingResourceBlockPermissionModelImpl.getOriginalResourceBlockId());
		Assert.assertEquals(existingResourceBlockPermissionModelImpl.getRoleId(),
			existingResourceBlockPermissionModelImpl.getOriginalRoleId());
	}

	protected ResourceBlockPermission addResourceBlockPermission()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlockPermission resourceBlockPermission = _persistence.create(pk);

		resourceBlockPermission.setResourceBlockId(ServiceTestUtil.nextLong());

		resourceBlockPermission.setRoleId(ServiceTestUtil.nextLong());

		resourceBlockPermission.setActionIds(ServiceTestUtil.nextLong());

		resourceBlockPermission.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(resourceBlockPermission);

		return resourceBlockPermission;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceBlockPermissionPersistenceTest.class);
	private ResourceBlockPermissionPersistence _persistence = (ResourceBlockPermissionPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPermissionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}