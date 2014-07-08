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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
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
public class ResourceBlockPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ResourceBlock> modelListener : _modelListeners) {
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

		for (ModelListener<ResourceBlock> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		ResourceBlock newResourceBlock = _persistence.create(pk);

		newResourceBlock.setMvccVersion(RandomTestUtil.nextLong());

		newResourceBlock.setCompanyId(RandomTestUtil.nextLong());

		newResourceBlock.setGroupId(RandomTestUtil.nextLong());

		newResourceBlock.setName(RandomTestUtil.randomString());

		newResourceBlock.setPermissionsHash(RandomTestUtil.randomString());

		newResourceBlock.setReferenceCount(RandomTestUtil.nextLong());

		_persistence.update(newResourceBlock);

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock.getMvccVersion(),
			newResourceBlock.getMvccVersion());
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
	public void testCountByC_G_N() {
		try {
			_persistence.countByC_G_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_G_N(0L, 0L, StringPool.NULL);

			_persistence.countByC_G_N(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_G_N_P() {
		try {
			_persistence.countByC_G_N_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK, StringPool.BLANK);

			_persistence.countByC_G_N_P(0L, 0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByC_G_N_P(0L, 0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
			"mvccVersion", true, "resourceBlockId", true, "companyId", true,
			"groupId", true, "name", true, "permissionsHash", true,
			"referenceCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlock missingResourceBlock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceBlock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourceBlock newResourceBlock1 = addResourceBlock();
		ResourceBlock newResourceBlock2 = addResourceBlock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock1.getPrimaryKey());
		primaryKeys.add(newResourceBlock2.getPrimaryKey());

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock1,
			resourceBlocks.get(newResourceBlock1.getPrimaryKey()));
		Assert.assertEquals(newResourceBlock2,
			resourceBlocks.get(newResourceBlock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock,
			resourceBlocks.get(newResourceBlock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock.getPrimaryKey());

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock,
			resourceBlocks.get(newResourceBlock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourceBlockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourceBlock resourceBlock = (ResourceBlock)object;

					Assert.assertNotNull(resourceBlock);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

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
		long pk = RandomTestUtil.nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		resourceBlock.setMvccVersion(RandomTestUtil.nextLong());

		resourceBlock.setCompanyId(RandomTestUtil.nextLong());

		resourceBlock.setGroupId(RandomTestUtil.nextLong());

		resourceBlock.setName(RandomTestUtil.randomString());

		resourceBlock.setPermissionsHash(RandomTestUtil.randomString());

		resourceBlock.setReferenceCount(RandomTestUtil.nextLong());

		_persistence.update(resourceBlock);

		return resourceBlock;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceBlockPersistenceTest.class);
	private ModelListener<ResourceBlock>[] _modelListeners;
	private ResourceBlockPersistence _persistence = (ResourceBlockPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}