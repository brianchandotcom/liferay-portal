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

import com.liferay.portal.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

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
public class UserGroupGroupRolePersistenceTest {
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
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		Assert.assertNotNull(userGroupGroupRole);

		Assert.assertEquals(userGroupGroupRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		_persistence.remove(newUserGroupGroupRole);

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.fetchByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		Assert.assertNull(existingUserGroupGroupRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserGroupGroupRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		UserGroupGroupRole newUserGroupGroupRole = _persistence.create(pk);

		newUserGroupGroupRole.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newUserGroupGroupRole);

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.findByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupGroupRole.getUserGroupId(),
			newUserGroupGroupRole.getUserGroupId());
		Assert.assertEquals(existingUserGroupGroupRole.getGroupId(),
			newUserGroupGroupRole.getGroupId());
		Assert.assertEquals(existingUserGroupGroupRole.getRoleId(),
			newUserGroupGroupRole.getRoleId());
		Assert.assertEquals(existingUserGroupGroupRole.getMvccVersion(),
			newUserGroupGroupRole.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.findByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserGroupGroupRoleException");
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.fetchByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		UserGroupGroupRole missingUserGroupGroupRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserGroupGroupRole);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new UserGroupGroupRoleActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)object;

					Assert.assertNotNull(userGroupGroupRole);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.userGroupId",
				newUserGroupGroupRole.getUserGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newUserGroupGroupRole.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newUserGroupGroupRole.getRoleId()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserGroupGroupRole existingUserGroupGroupRole = result.get(0);

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.userGroupId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				ServiceTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				ServiceTestUtil.nextLong()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.userGroupId"));

		Object newUserGroupId = newUserGroupGroupRole.getUserGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.userGroupId",
				new Object[] { newUserGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserGroupId = result.get(0);

		Assert.assertEquals(existingUserGroupId, newUserGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.userGroupId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserGroupGroupRole addUserGroupGroupRole()
		throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		userGroupGroupRole.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(userGroupGroupRole);

		return userGroupGroupRole;
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupGroupRolePersistenceTest.class);
	private UserGroupGroupRolePersistence _persistence = (UserGroupGroupRolePersistence)PortalBeanLocatorUtil.locate(UserGroupGroupRolePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}