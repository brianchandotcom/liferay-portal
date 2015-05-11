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

import com.liferay.portal.NoSuchResourceBlockPermissionException;
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
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.persistence.ResourceBlockPermissionPersistence;
import com.liferay.portal.service.persistence.ResourceBlockPermissionUtil;
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
public class ResourceBlockPermissionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ResourceBlockPermissionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ResourceBlockPermission> iterator = _resourceBlockPermissions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		ResourceBlockPermission newResourceBlockPermission = _persistence.create(pk);

		newResourceBlockPermission.setMvccVersion(RandomTestUtil.nextLong());

		newResourceBlockPermission.setResourceBlockId(RandomTestUtil.nextLong());

		newResourceBlockPermission.setRoleId(RandomTestUtil.nextLong());

		newResourceBlockPermission.setActionIds(RandomTestUtil.nextLong());

		_resourceBlockPermissions.add(_persistence.update(
				newResourceBlockPermission));

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission.getMvccVersion(),
			newResourceBlockPermission.getMvccVersion());
		Assert.assertEquals(existingResourceBlockPermission.getResourceBlockPermissionId(),
			newResourceBlockPermission.getResourceBlockPermissionId());
		Assert.assertEquals(existingResourceBlockPermission.getResourceBlockId(),
			newResourceBlockPermission.getResourceBlockId());
		Assert.assertEquals(existingResourceBlockPermission.getRoleId(),
			newResourceBlockPermission.getRoleId());
		Assert.assertEquals(existingResourceBlockPermission.getActionIds(),
			newResourceBlockPermission.getActionIds());
	}

	@Test
	public void testCountByResourceBlockId() throws Exception {
		_persistence.countByResourceBlockId(RandomTestUtil.nextLong());

		_persistence.countByResourceBlockId(0L);
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testCountByR_R() throws Exception {
		_persistence.countByR_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByR_R(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission,
			newResourceBlockPermission);
	}

	@Test(expected = NoSuchResourceBlockPermissionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ResourceBlockPermission> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ResourceBlockPermission",
			"mvccVersion", true, "resourceBlockPermissionId", true,
			"resourceBlockId", true, "roleId", true, "actionIds", true);
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
		long pk = RandomTestUtil.nextLong();

		ResourceBlockPermission missingResourceBlockPermission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceBlockPermission);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission1 = addResourceBlockPermission();
		ResourceBlockPermission newResourceBlockPermission2 = addResourceBlockPermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlockPermission1.getPrimaryKey());
		primaryKeys.add(newResourceBlockPermission2.getPrimaryKey());

		Map<Serializable, ResourceBlockPermission> resourceBlockPermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourceBlockPermissions.size());
		Assert.assertEquals(newResourceBlockPermission1,
			resourceBlockPermissions.get(
				newResourceBlockPermission1.getPrimaryKey()));
		Assert.assertEquals(newResourceBlockPermission2,
			resourceBlockPermissions.get(
				newResourceBlockPermission2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourceBlockPermission> resourceBlockPermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlockPermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlockPermission.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourceBlockPermission> resourceBlockPermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlockPermissions.size());
		Assert.assertEquals(newResourceBlockPermission,
			resourceBlockPermissions.get(
				newResourceBlockPermission.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourceBlockPermission> resourceBlockPermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlockPermissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlockPermission.getPrimaryKey());

		Map<Serializable, ResourceBlockPermission> resourceBlockPermissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlockPermissions.size());
		Assert.assertEquals(newResourceBlockPermission,
			resourceBlockPermissions.get(
				newResourceBlockPermission.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourceBlockPermissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)object;

					Assert.assertNotNull(resourceBlockPermission);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"resourceBlockPermissionId", RandomTestUtil.nextLong()));

		List<ResourceBlockPermission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockPermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"resourceBlockPermissionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ResourceBlockPermission newResourceBlockPermission = addResourceBlockPermission();

		_persistence.clearCache();

		ResourceBlockPermission existingResourceBlockPermission = _persistence.findByPrimaryKey(newResourceBlockPermission.getPrimaryKey());

		Assert.assertEquals(existingResourceBlockPermission.getResourceBlockId(),
			ReflectionTestUtil.invoke(existingResourceBlockPermission,
				"getOriginalResourceBlockId", new Class<?>[0]));
		Assert.assertEquals(existingResourceBlockPermission.getRoleId(),
			ReflectionTestUtil.invoke(existingResourceBlockPermission,
				"getOriginalRoleId", new Class<?>[0]));
	}

	protected ResourceBlockPermission addResourceBlockPermission()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlockPermission resourceBlockPermission = _persistence.create(pk);

		resourceBlockPermission.setMvccVersion(RandomTestUtil.nextLong());

		resourceBlockPermission.setResourceBlockId(RandomTestUtil.nextLong());

		resourceBlockPermission.setRoleId(RandomTestUtil.nextLong());

		resourceBlockPermission.setActionIds(RandomTestUtil.nextLong());

		_resourceBlockPermissions.add(_persistence.update(
				resourceBlockPermission));

		return resourceBlockPermission;
	}

	private List<ResourceBlockPermission> _resourceBlockPermissions = new ArrayList<ResourceBlockPermission>();
	private ResourceBlockPermissionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}