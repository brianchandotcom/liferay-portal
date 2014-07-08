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

import com.liferay.portal.NoSuchRoleException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.service.RoleLocalServiceUtil;
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
public class RolePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Role> modelListener : _modelListeners) {
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

		for (ModelListener<Role> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Role role = _persistence.create(pk);

		Assert.assertNotNull(role);

		Assert.assertEquals(role.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Role newRole = addRole();

		_persistence.remove(newRole);

		Role existingRole = _persistence.fetchByPrimaryKey(newRole.getPrimaryKey());

		Assert.assertNull(existingRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Role newRole = _persistence.create(pk);

		newRole.setMvccVersion(RandomTestUtil.nextLong());

		newRole.setUuid(RandomTestUtil.randomString());

		newRole.setCompanyId(RandomTestUtil.nextLong());

		newRole.setUserId(RandomTestUtil.nextLong());

		newRole.setUserName(RandomTestUtil.randomString());

		newRole.setCreateDate(RandomTestUtil.nextDate());

		newRole.setModifiedDate(RandomTestUtil.nextDate());

		newRole.setClassNameId(RandomTestUtil.nextLong());

		newRole.setClassPK(RandomTestUtil.nextLong());

		newRole.setName(RandomTestUtil.randomString());

		newRole.setTitle(RandomTestUtil.randomString());

		newRole.setDescription(RandomTestUtil.randomString());

		newRole.setType(RandomTestUtil.nextInt());

		newRole.setSubtype(RandomTestUtil.randomString());

		_persistence.update(newRole);

		Role existingRole = _persistence.findByPrimaryKey(newRole.getPrimaryKey());

		Assert.assertEquals(existingRole.getMvccVersion(),
			newRole.getMvccVersion());
		Assert.assertEquals(existingRole.getUuid(), newRole.getUuid());
		Assert.assertEquals(existingRole.getRoleId(), newRole.getRoleId());
		Assert.assertEquals(existingRole.getCompanyId(), newRole.getCompanyId());
		Assert.assertEquals(existingRole.getUserId(), newRole.getUserId());
		Assert.assertEquals(existingRole.getUserName(), newRole.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(existingRole.getCreateDate()),
			Time.getShortTimestamp(newRole.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRole.getModifiedDate()),
			Time.getShortTimestamp(newRole.getModifiedDate()));
		Assert.assertEquals(existingRole.getClassNameId(),
			newRole.getClassNameId());
		Assert.assertEquals(existingRole.getClassPK(), newRole.getClassPK());
		Assert.assertEquals(existingRole.getName(), newRole.getName());
		Assert.assertEquals(existingRole.getTitle(), newRole.getTitle());
		Assert.assertEquals(existingRole.getDescription(),
			newRole.getDescription());
		Assert.assertEquals(existingRole.getType(), newRole.getType());
		Assert.assertEquals(existingRole.getSubtype(), newRole.getSubtype());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByName() {
		try {
			_persistence.countByName(StringPool.BLANK);

			_persistence.countByName(StringPool.NULL);

			_persistence.countByName((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByType() {
		try {
			_persistence.countByType(RandomTestUtil.nextInt());

			_persistence.countByType(0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountBySubtype() {
		try {
			_persistence.countBySubtype(StringPool.BLANK);

			_persistence.countBySubtype(StringPool.NULL);

			_persistence.countBySubtype((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
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
	public void testCountByC_T() {
		try {
			_persistence.countByC_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_TArrayable() {
		try {
			_persistence.countByC_T(RandomTestUtil.nextLong(),
				new int[] { RandomTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_S() {
		try {
			_persistence.countByT_S(RandomTestUtil.nextInt(), StringPool.BLANK);

			_persistence.countByT_S(0, StringPool.NULL);

			_persistence.countByT_S(0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_C() {
		try {
			_persistence.countByC_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByC_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Role newRole = addRole();

		Role existingRole = _persistence.findByPrimaryKey(newRole.getPrimaryKey());

		Assert.assertEquals(existingRole, newRole);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRoleException");
		}
		catch (NoSuchRoleException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Role_", "mvccVersion",
			true, "uuid", true, "roleId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "name", true, "title", true,
			"description", true, "type", true, "subtype", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Role newRole = addRole();

		Role existingRole = _persistence.fetchByPrimaryKey(newRole.getPrimaryKey());

		Assert.assertEquals(existingRole, newRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Role missingRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Role newRole1 = addRole();
		Role newRole2 = addRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRole1.getPrimaryKey());
		primaryKeys.add(newRole2.getPrimaryKey());

		Map<Serializable, Role> roles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, roles.size());
		Assert.assertEquals(newRole1, roles.get(newRole1.getPrimaryKey()));
		Assert.assertEquals(newRole2, roles.get(newRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Role> roles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(roles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Role newRole = addRole();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Role> roles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, roles.size());
		Assert.assertEquals(newRole, roles.get(newRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Role> roles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(roles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Role newRole = addRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRole.getPrimaryKey());

		Map<Serializable, Role> roles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, roles.size());
		Assert.assertEquals(newRole, roles.get(newRole.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = RoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Role role = (Role)object;

					Assert.assertNotNull(role);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Role newRole = addRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("roleId",
				newRole.getRoleId()));

		List<Role> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Role existingRole = result.get(0);

		Assert.assertEquals(existingRole, newRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("roleId",
				RandomTestUtil.nextLong()));

		List<Role> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Role newRole = addRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("roleId"));

		Object newRoleId = newRole.getRoleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("roleId",
				new Object[] { newRoleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRoleId = result.get(0);

		Assert.assertEquals(existingRoleId, newRoleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("roleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("roleId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Role newRole = addRole();

		_persistence.clearCache();

		RoleModelImpl existingRoleModelImpl = (RoleModelImpl)_persistence.findByPrimaryKey(newRole.getPrimaryKey());

		Assert.assertEquals(existingRoleModelImpl.getCompanyId(),
			existingRoleModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(existingRoleModelImpl.getName(),
				existingRoleModelImpl.getOriginalName()));

		Assert.assertEquals(existingRoleModelImpl.getCompanyId(),
			existingRoleModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingRoleModelImpl.getClassNameId(),
			existingRoleModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingRoleModelImpl.getClassPK(),
			existingRoleModelImpl.getOriginalClassPK());
	}

	protected Role addRole() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Role role = _persistence.create(pk);

		role.setMvccVersion(RandomTestUtil.nextLong());

		role.setUuid(RandomTestUtil.randomString());

		role.setCompanyId(RandomTestUtil.nextLong());

		role.setUserId(RandomTestUtil.nextLong());

		role.setUserName(RandomTestUtil.randomString());

		role.setCreateDate(RandomTestUtil.nextDate());

		role.setModifiedDate(RandomTestUtil.nextDate());

		role.setClassNameId(RandomTestUtil.nextLong());

		role.setClassPK(RandomTestUtil.nextLong());

		role.setName(RandomTestUtil.randomString());

		role.setTitle(RandomTestUtil.randomString());

		role.setDescription(RandomTestUtil.randomString());

		role.setType(RandomTestUtil.nextInt());

		role.setSubtype(RandomTestUtil.randomString());

		_persistence.update(role);

		return role;
	}

	private static Log _log = LogFactoryUtil.getLog(RolePersistenceTest.class);
	private ModelListener<Role>[] _modelListeners;
	private RolePersistence _persistence = (RolePersistence)PortalBeanLocatorUtil.locate(RolePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}