/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupGroupRolePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserGroupGroupRolePersistence)PortalBeanLocatorUtil.locate(UserGroupGroupRolePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(nextLong(),
				nextLong(), nextLong());

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		assertNotNull(userGroupGroupRole);

		assertEquals(userGroupGroupRole.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		_persistence.remove(newUserGroupGroupRole);

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.fetchByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		assertNull(existingUserGroupGroupRole);
	}

	public void testUpdateNew() throws Exception {
		addUserGroupGroupRole();
	}

	public void testUpdateExisting() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(nextLong(),
				nextLong(), nextLong());

		UserGroupGroupRole newUserGroupGroupRole = _persistence.create(pk);

		_persistence.update(newUserGroupGroupRole, false);

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.findByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		assertEquals(existingUserGroupGroupRole.getUserGroupId(),
			newUserGroupGroupRole.getUserGroupId());
		assertEquals(existingUserGroupGroupRole.getGroupId(),
			newUserGroupGroupRole.getGroupId());
		assertEquals(existingUserGroupGroupRole.getRoleId(),
			newUserGroupGroupRole.getRoleId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.findByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(nextLong(),
				nextLong(), nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchUserGroupGroupRoleException");
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole = _persistence.fetchByPrimaryKey(newUserGroupGroupRole.getPrimaryKey());

		assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(nextLong(),
				nextLong(), nextLong());

		UserGroupGroupRole missingUserGroupGroupRole = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserGroupGroupRole);
	}

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

		assertEquals(1, result.size());

		UserGroupGroupRole existingUserGroupGroupRole = result.get(0);

		assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.userGroupId", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId", nextLong()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingUserGroupId = result.get(0);

		assertEquals(existingUserGroupId, newUserGroupId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupGroupRole.class,
				UserGroupGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.userGroupId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected UserGroupGroupRole addUserGroupGroupRole()
		throws Exception {
		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(nextLong(),
				nextLong(), nextLong());

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		_persistence.update(userGroupGroupRole, false);

		return userGroupGroupRole;
	}

	private UserGroupGroupRolePersistence _persistence;
}