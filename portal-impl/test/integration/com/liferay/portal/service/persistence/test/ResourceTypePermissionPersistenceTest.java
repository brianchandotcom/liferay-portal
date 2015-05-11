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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchResourceTypePermissionException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.service.persistence.ResourceTypePermissionPersistence;
import com.liferay.portal.service.persistence.ResourceTypePermissionUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
public class ResourceTypePermissionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ResourceTypePermissionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ResourceTypePermission> iterator = _resourceTypePermissions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceTypePermission resourceTypePermission = _persistence.create(pk);

		Assert.assertNotNull(resourceTypePermission);

		Assert.assertEquals(resourceTypePermission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		_persistence.remove(newResourceTypePermission);

		ResourceTypePermission existingResourceTypePermission = _persistence.fetchByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		Assert.assertNull(existingResourceTypePermission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceTypePermission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceTypePermission newResourceTypePermission = _persistence.create(pk);

		newResourceTypePermission.setMvccVersion(RandomTestUtil.nextLong());

		newResourceTypePermission.setCompanyId(RandomTestUtil.nextLong());

		newResourceTypePermission.setGroupId(RandomTestUtil.nextLong());

		newResourceTypePermission.setName(RandomTestUtil.randomString());

		newResourceTypePermission.setRoleId(RandomTestUtil.nextLong());

		newResourceTypePermission.setActionIds(RandomTestUtil.nextLong());

		_resourceTypePermissions.add(_persistence.update(
				newResourceTypePermission));

		ResourceTypePermission existingResourceTypePermission = _persistence.findByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		Assert.assertEquals(existingResourceTypePermission.getMvccVersion(),
			newResourceTypePermission.getMvccVersion());
		Assert.assertEquals(existingResourceTypePermission.getResourceTypePermissionId(),
			newResourceTypePermission.getResourceTypePermissionId());
		Assert.assertEquals(existingResourceTypePermission.getCompanyId(),
			newResourceTypePermission.getCompanyId());
		Assert.assertEquals(existingResourceTypePermission.getGroupId(),
			newResourceTypePermission.getGroupId());
		Assert.assertEquals(existingResourceTypePermission.getName(),
			newResourceTypePermission.getName());
		Assert.assertEquals(existingResourceTypePermission.getRoleId(),
			newResourceTypePermission.getRoleId());
		Assert.assertEquals(existingResourceTypePermission.getActionIds(),
			newResourceTypePermission.getActionIds());
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testCountByC_N_R() throws Exception {
		_persistence.countByC_N_R(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextLong());

		_persistence.countByC_N_R(0L, StringPool.NULL, 0L);

		_persistence.countByC_N_R(0L, (String)null, 0L);
	}

	@Test
	public void testCountByC_G_N_R() throws Exception {
		_persistence.countByC_G_N_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextLong());

		_persistence.countByC_G_N_R(0L, 0L, StringPool.NULL, 0L);

		_persistence.countByC_G_N_R(0L, 0L, (String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		ResourceTypePermission existingResourceTypePermission = _persistence.findByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		Assert.assertEquals(existingResourceTypePermission,
			newResourceTypePermission);
	}

	@Test(expected = NoSuchResourceTypePermissionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ResourceTypePermission> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ResourceTypePermission",
			"mvccVersion", true, "resourceTypePermissionId", true, "companyId",
			true, "groupId", true, "name", true, "roleId", true, "actionIds",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		ResourceTypePermission existingResourceTypePermission = _persistence.fetchByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		Assert.assertEquals(existingResourceTypePermission,
			newResourceTypePermission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceTypePermission missingResourceTypePermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceTypePermission);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourceTypePermission newResourceTypePermission1 = addResourceTypePermission();
		ResourceTypePermission newResourceTypePermission2 = addResourceTypePermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceTypePermission1.getPrimaryKey());
		primaryKeys.add(newResourceTypePermission2.getPrimaryKey());

		Map<Serializable, ResourceTypePermission> resourceTypePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourceTypePermissions.size());
		Assert.assertEquals(newResourceTypePermission1,
			resourceTypePermissions.get(
				newResourceTypePermission1.getPrimaryKey()));
		Assert.assertEquals(newResourceTypePermission2,
			resourceTypePermissions.get(
				newResourceTypePermission2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourceTypePermission> resourceTypePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceTypePermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceTypePermission.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourceTypePermission> resourceTypePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceTypePermissions.size());
		Assert.assertEquals(newResourceTypePermission,
			resourceTypePermissions.get(
				newResourceTypePermission.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourceTypePermission> resourceTypePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceTypePermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceTypePermission.getPrimaryKey());

		Map<Serializable, ResourceTypePermission> resourceTypePermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceTypePermissions.size());
		Assert.assertEquals(newResourceTypePermission,
			resourceTypePermissions.get(
				newResourceTypePermission.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourceTypePermissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourceTypePermission resourceTypePermission = (ResourceTypePermission)object;

					Assert.assertNotNull(resourceTypePermission);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceTypePermissionId",
				newResourceTypePermission.getResourceTypePermissionId()));

		List<ResourceTypePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceTypePermission existingResourceTypePermission = result.get(0);

		Assert.assertEquals(existingResourceTypePermission,
			newResourceTypePermission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceTypePermissionId", RandomTestUtil.nextLong()));

		List<ResourceTypePermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceTypePermissionId"));

		Object newResourceTypePermissionId = newResourceTypePermission.getResourceTypePermissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceTypePermissionId",
				new Object[] { newResourceTypePermissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceTypePermissionId = result.get(0);

		Assert.assertEquals(existingResourceTypePermissionId,
			newResourceTypePermissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceTypePermission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceTypePermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceTypePermissionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ResourceTypePermission newResourceTypePermission = addResourceTypePermission();

		_persistence.clearCache();

		ResourceTypePermission existingResourceTypePermission = _persistence.findByPrimaryKey(newResourceTypePermission.getPrimaryKey());

		Assert.assertEquals(existingResourceTypePermission.getCompanyId(),
			ReflectionTestUtil.invoke(existingResourceTypePermission,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(existingResourceTypePermission.getGroupId(),
			ReflectionTestUtil.invoke(existingResourceTypePermission,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingResourceTypePermission.getName(),
				ReflectionTestUtil.invoke(existingResourceTypePermission,
					"getOriginalName", new Class<?>[0])));
		Assert.assertEquals(existingResourceTypePermission.getRoleId(),
			ReflectionTestUtil.invoke(existingResourceTypePermission,
				"getOriginalRoleId", new Class<?>[0]));
	}

	protected ResourceTypePermission addResourceTypePermission()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceTypePermission resourceTypePermission = _persistence.create(pk);

		resourceTypePermission.setMvccVersion(RandomTestUtil.nextLong());

		resourceTypePermission.setCompanyId(RandomTestUtil.nextLong());

		resourceTypePermission.setGroupId(RandomTestUtil.nextLong());

		resourceTypePermission.setName(RandomTestUtil.randomString());

		resourceTypePermission.setRoleId(RandomTestUtil.nextLong());

		resourceTypePermission.setActionIds(RandomTestUtil.nextLong());

		_resourceTypePermissions.add(_persistence.update(resourceTypePermission));

		return resourceTypePermission;
	}

	private List<ResourceTypePermission> _resourceTypePermissions = new ArrayList<ResourceTypePermission>();
	private ResourceTypePermissionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}