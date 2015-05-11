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

import com.liferay.portal.NoSuchResourcePermissionException;
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
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.service.persistence.ResourcePermissionUtil;
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
public class ResourcePermissionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ResourcePermissionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ResourcePermission> iterator = _resourcePermissions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
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

		_resourcePermissions.add(_persistence.update(newResourcePermission));

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
	public void testCountByScope() throws Exception {
		_persistence.countByScope(RandomTestUtil.nextInt());

		_persistence.countByScope(0);
	}

	@Test
	public void testCountByScopeArrayable() throws Exception {
		_persistence.countByScope(new int[] { RandomTestUtil.nextInt(), 0 });
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testCountByC_LikeP() throws Exception {
		_persistence.countByC_LikeP(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_LikeP(0L, StringPool.NULL);

		_persistence.countByC_LikeP(0L, (String)null);
	}

	@Test
	public void testCountByC_N_S() throws Exception {
		_persistence.countByC_N_S(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextInt());

		_persistence.countByC_N_S(0L, StringPool.NULL, 0);

		_persistence.countByC_N_S(0L, (String)null, 0);
	}

	@Test
	public void testCountByC_N_S_P() throws Exception {
		_persistence.countByC_N_S_P(RandomTestUtil.nextLong(),
			StringPool.BLANK, RandomTestUtil.nextInt(), StringPool.BLANK);

		_persistence.countByC_N_S_P(0L, StringPool.NULL, 0, StringPool.NULL);

		_persistence.countByC_N_S_P(0L, (String)null, 0, (String)null);
	}

	@Test
	public void testCountByC_N_S_P_R() throws Exception {
		_persistence.countByC_N_S_P_R(RandomTestUtil.nextLong(),
			StringPool.BLANK, RandomTestUtil.nextInt(), StringPool.BLANK,
			RandomTestUtil.nextLong());

		_persistence.countByC_N_S_P_R(0L, StringPool.NULL, 0, StringPool.NULL,
			0L);

		_persistence.countByC_N_S_P_R(0L, (String)null, 0, (String)null, 0L);
	}

	@Test
	public void testCountByC_N_S_P_RArrayable() throws Exception {
		_persistence.countByC_N_S_P_R(RandomTestUtil.nextLong(),
			RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomString(),
			new long[] { RandomTestUtil.nextLong(), 0L });
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission, newResourcePermission);
	}

	@Test(expected = NoSuchResourcePermissionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ResourcePermission> getOrderByComparator() {
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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePermissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePermissionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.clearCache();

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		Assert.assertEquals(existingResourcePermission.getCompanyId(),
			ReflectionTestUtil.invoke(existingResourcePermission,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingResourcePermission.getName(),
				ReflectionTestUtil.invoke(existingResourcePermission,
					"getOriginalName", new Class<?>[0])));
		Assert.assertEquals(existingResourcePermission.getScope(),
			ReflectionTestUtil.invoke(existingResourcePermission,
				"getOriginalScope", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingResourcePermission.getPrimKey(),
				ReflectionTestUtil.invoke(existingResourcePermission,
					"getOriginalPrimKey", new Class<?>[0])));
		Assert.assertEquals(existingResourcePermission.getRoleId(),
			ReflectionTestUtil.invoke(existingResourcePermission,
				"getOriginalRoleId", new Class<?>[0]));
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

		_resourcePermissions.add(_persistence.update(resourcePermission));

		return resourcePermission;
	}

	private List<ResourcePermission> _resourcePermissions = new ArrayList<ResourcePermission>();
	private ResourcePermissionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}