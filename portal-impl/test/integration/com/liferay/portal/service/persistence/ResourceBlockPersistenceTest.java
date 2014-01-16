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

import com.liferay.portal.NoSuchResourceBlockException;
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
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;
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
public class ResourceBlockPersistenceTest {
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

		ResourceBlock resourceBlock = _persistence.create(pk);

		Assert.assertNotNull(resourceBlock);

		Assert.assertEquals(resourceBlock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.remove(newResourceBlock);

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertNull(existingResourceBlock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceBlock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlock newResourceBlock = _persistence.create(pk);

		newResourceBlock.setCompanyId(ServiceTestUtil.nextLong());

		newResourceBlock.setGroupId(ServiceTestUtil.nextLong());

		newResourceBlock.setName(ServiceTestUtil.randomString());

		newResourceBlock.setPermissionsHash(ServiceTestUtil.randomString());

		newResourceBlock.setReferenceCount(ServiceTestUtil.nextLong());

		newResourceBlock.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newResourceBlock);

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock.getResourceBlockId(),
			newResourceBlock.getResourceBlockId());
		Assert.assertEquals(existingResourceBlock.getCompanyId(),
			newResourceBlock.getCompanyId());
		Assert.assertEquals(existingResourceBlock.getGroupId(),
			newResourceBlock.getGroupId());
		Assert.assertEquals(existingResourceBlock.getName(),
			newResourceBlock.getName());
		Assert.assertEquals(existingResourceBlock.getPermissionsHash(),
			newResourceBlock.getPermissionsHash());
		Assert.assertEquals(existingResourceBlock.getReferenceCount(),
			newResourceBlock.getReferenceCount());
		Assert.assertEquals(existingResourceBlock.getMvccVersion(),
			newResourceBlock.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourceBlockException");
		}
		catch (NoSuchResourceBlockException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ResourceBlock",
			"resourceBlockId", true, "companyId", true, "groupId", true,
			"name", true, "permissionsHash", true, "referenceCount", true,
			"mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlock missingResourceBlock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceBlock);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ResourceBlockActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ResourceBlock resourceBlock = (ResourceBlock)object;

					Assert.assertNotNull(resourceBlock);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				newResourceBlock.getResourceBlockId()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceBlock existingResourceBlock = result.get(0);

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				ServiceTestUtil.nextLong()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		Object newResourceBlockId = newResourceBlock.getResourceBlockId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { newResourceBlockId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceBlockId = result.get(0);

		Assert.assertEquals(existingResourceBlockId, newResourceBlockId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.clearCache();

		ResourceBlockModelImpl existingResourceBlockModelImpl = (ResourceBlockModelImpl)_persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockModelImpl.getCompanyId(),
			existingResourceBlockModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingResourceBlockModelImpl.getGroupId(),
			existingResourceBlockModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingResourceBlockModelImpl.getName(),
				existingResourceBlockModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingResourceBlockModelImpl.getPermissionsHash(),
				existingResourceBlockModelImpl.getOriginalPermissionsHash()));
	}

	protected ResourceBlock addResourceBlock() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		resourceBlock.setCompanyId(ServiceTestUtil.nextLong());

		resourceBlock.setGroupId(ServiceTestUtil.nextLong());

		resourceBlock.setName(ServiceTestUtil.randomString());

		resourceBlock.setPermissionsHash(ServiceTestUtil.randomString());

		resourceBlock.setReferenceCount(ServiceTestUtil.nextLong());

		resourceBlock.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(resourceBlock);

		return resourceBlock;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceBlockPersistenceTest.class);
	private ResourceBlockPersistence _persistence = (ResourceBlockPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}