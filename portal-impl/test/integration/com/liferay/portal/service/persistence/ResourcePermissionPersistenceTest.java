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

import com.liferay.portal.NoSuchResourcePermissionException;
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
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
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
public class ResourcePermissionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ResourcePermission> modelListener : _modelListeners) {
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

		for (ModelListener<ResourcePermission> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		Assert.assertNotNull(resourcePermission);

		Assert.assertEquals(resourcePermission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.remove(newResourcePermission);

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertNull(existingResourcePermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourcePermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourcePermission newResourcePermission = _persistence.create(pk);

		newResourcePermission.setMvccVersion(RandomTestUtil.nextLong());

		newResourcePermission.setCompanyId(RandomTestUtil.nextLong());

		newResourcePermission.setName(RandomTestUtil.randomString());

		newResourcePermission.setScope(RandomTestUtil.nextInt());

		newResourcePermission.setPrimKey(RandomTestUtil.randomString());

		newResourcePermission.setRoleId(RandomTestUtil.nextLong());

		newResourcePermission.setOwnerId(RandomTestUtil.nextLong());

		newResourcePermission.setActionIds(RandomTestUtil.nextLong());

		_persistence.update(newResourcePermission);

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission.getMvccVersion(),
			newResourcePermission.getMvccVersion());
		Assert.assertEquals(existingResourcePermission.getResourcePermissionId(),
			newResourcePermission.getResourcePermissionId());
		Assert.assertEquals(existingResourcePermission.getCompanyId(),
			newResourcePermission.getCompanyId());
		Assert.assertEquals(existingResourcePermission.getName(),
			newResourcePermission.getName());
		Assert.assertEquals(existingResourcePermission.getScope(),
			newResourcePermission.getScope());
		Assert.assertEquals(existingResourcePermission.getPrimKey(),
			newResourcePermission.getPrimKey());
		Assert.assertEquals(existingResourcePermission.getRoleId(),
			newResourcePermission.getRoleId());
		Assert.assertEquals(existingResourcePermission.getOwnerId(),
			newResourcePermission.getOwnerId());
		Assert.assertEquals(existingResourcePermission.getActionIds(),
			newResourcePermission.getActionIds());
	}

	@Test
	public void testCountByScope() {
		try {
			_persistence.countByScope(RandomTestUtil.nextInt());

			_persistence.countByScope(0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByScopeArrayable() {
		try {
			_persistence.countByScope(new int[] { RandomTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByRoleId() {
		try {
			_persistence.countByRoleId(RandomTestUtil.nextLong());

			_persistence.countByRoleId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_LikeP() {
		try {
			_persistence.countByC_LikeP(RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByC_LikeP(0L, StringPool.NULL);

			_persistence.countByC_LikeP(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N_S() {
		try {
			_persistence.countByC_N_S(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

			_persistence.countByC_N_S(0L, StringPool.NULL, 0);

			_persistence.countByC_N_S(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N_S_P() {
		try {
			_persistence.countByC_N_S_P(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt(), StringPool.BLANK);

			_persistence.countByC_N_S_P(0L, StringPool.NULL, 0, StringPool.NULL);

			_persistence.countByC_N_S_P(0L, (String)null, 0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N_S_P_R() {
		try {
			_persistence.countByC_N_S_P_R(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt(), StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByC_N_S_P_R(0L, StringPool.NULL, 0,
				StringPool.NULL, 0L);

			_persistence.countByC_N_S_P_R(0L, (String)null, 0, (String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N_S_P_RArrayable() {
		try {
			_persistence.countByC_N_S_P_R(RandomTestUtil.nextLong(),
				RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
				RandomTestUtil.randomString(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourcePermissionException");
		}
		catch (NoSuchResourcePermissionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ResourcePermission",
			"mvccVersion", true, "resourcePermissionId", true, "companyId",
			true, "name", true, "scope", true, "primKey", true, "roleId", true,
			"ownerId", true, "actionIds", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourcePermission missingResourcePermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourcePermission);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourcePermission newResourcePermission1 = addResourcePermission();
		ResourcePermission newResourcePermission2 = addResourcePermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourcePermission1.getPrimaryKey());
		primaryKeys.add(newResourcePermission2.getPrimaryKey());

		Map<Serializable, ResourcePermission> resourcePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourcePermissions.size());
		Assert.assertEquals(newResourcePermission1,
			resourcePermissions.get(newResourcePermission1.getPrimaryKey()));
		Assert.assertEquals(newResourcePermission2,
			resourcePermissions.get(newResourcePermission2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourcePermission> resourcePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourcePermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourcePermission.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourcePermission> resourcePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourcePermissions.size());
		Assert.assertEquals(newResourcePermission,
			resourcePermissions.get(newResourcePermission.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourcePermission> resourcePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourcePermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourcePermission.getPrimaryKey());

		Map<Serializable, ResourcePermission> resourcePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourcePermissions.size());
		Assert.assertEquals(newResourcePermission,
			resourcePermissions.get(newResourcePermission.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourcePermissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourcePermission resourcePermission = (ResourcePermission)object;

					Assert.assertNotNull(resourcePermission);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePermissionId",
				newResourcePermission.getResourcePermissionId()));

		List<ResourcePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourcePermission existingResourcePermission = result.get(0);

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePermissionId",
				RandomTestUtil.nextLong()));

		List<ResourcePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePermissionId"));

		Object newResourcePermissionId = newResourcePermission.getResourcePermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePermissionId",
				new Object[] { newResourcePermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourcePermissionId = result.get(0);

		Assert.assertEquals(existingResourcePermissionId,
			newResourcePermissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourcePermission.class,
				ResourcePermission.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePermissionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.clearCache();

		ResourcePermissionModelImpl existingResourcePermissionModelImpl = (ResourcePermissionModelImpl)_persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermissionModelImpl.getCompanyId(),
			existingResourcePermissionModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingResourcePermissionModelImpl.getName(),
				existingResourcePermissionModelImpl.getOriginalName()));
		Assert.assertEquals(existingResourcePermissionModelImpl.getScope(),
			existingResourcePermissionModelImpl.getOriginalScope());
		Assert.assertTrue(Validator.equals(
				existingResourcePermissionModelImpl.getPrimKey(),
				existingResourcePermissionModelImpl.getOriginalPrimKey()));
		Assert.assertEquals(existingResourcePermissionModelImpl.getRoleId(),
			existingResourcePermissionModelImpl.getOriginalRoleId());
	}

	protected ResourcePermission addResourcePermission()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		resourcePermission.setMvccVersion(RandomTestUtil.nextLong());

		resourcePermission.setCompanyId(RandomTestUtil.nextLong());

		resourcePermission.setName(RandomTestUtil.randomString());

		resourcePermission.setScope(RandomTestUtil.nextInt());

		resourcePermission.setPrimKey(RandomTestUtil.randomString());

		resourcePermission.setRoleId(RandomTestUtil.nextLong());

		resourcePermission.setOwnerId(RandomTestUtil.nextLong());

		resourcePermission.setActionIds(RandomTestUtil.nextLong());

		_persistence.update(resourcePermission);

		return resourcePermission;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourcePermissionPersistenceTest.class);
	private ModelListener<ResourcePermission>[] _modelListeners;
	private ResourcePermissionPersistence _persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}