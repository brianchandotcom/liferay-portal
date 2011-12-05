/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserGroup userGroup = _persistence.create(pk);

		assertNotNull(userGroup);

		assertEquals(userGroup.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		_persistence.remove(newUserGroup);

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		assertNull(existingUserGroup);
	}

	public void testUpdateNew() throws Exception {
		addUserGroup();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserGroup newUserGroup = _persistence.create(pk);

		newUserGroup.setCompanyId(nextLong());

		newUserGroup.setParentUserGroupId(nextLong());

		newUserGroup.setName(randomString());

		newUserGroup.setDescription(randomString());

		newUserGroup.setAddedByLDAPImport(randomBoolean());

		_persistence.update(newUserGroup, false);

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		assertEquals(existingUserGroup.getUserGroupId(),
			newUserGroup.getUserGroupId());
		assertEquals(existingUserGroup.getCompanyId(),
			newUserGroup.getCompanyId());
		assertEquals(existingUserGroup.getParentUserGroupId(),
			newUserGroup.getParentUserGroupId());
		assertEquals(existingUserGroup.getName(), newUserGroup.getName());
		assertEquals(existingUserGroup.getDescription(),
			newUserGroup.getDescription());
		assertEquals(existingUserGroup.getAddedByLDAPImport(),
			newUserGroup.getAddedByLDAPImport());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		assertEquals(existingUserGroup, newUserGroup);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserGroupException");
		}
		catch (NoSuchUserGroupException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		assertEquals(existingUserGroup, newUserGroup);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserGroup missingUserGroup = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserGroup);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId",
				newUserGroup.getUserGroupId()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserGroup existingUserGroup = result.get(0);

		assertEquals(existingUserGroup, newUserGroup);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId", nextLong()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		Object newUserGroupId = newUserGroup.getUserGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { newUserGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingUserGroupId = result.get(0);

		assertEquals(existingUserGroupId, newUserGroupId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserGroup newUserGroup = addUserGroup();

		_persistence.clearCache();

		UserGroupModelImpl existingUserGroupModelImpl = (UserGroupModelImpl)_persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		assertEquals(existingUserGroupModelImpl.getCompanyId(),
			existingUserGroupModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingUserGroupModelImpl.getName(),
				existingUserGroupModelImpl.getOriginalName()));
	}

	protected UserGroup addUserGroup() throws Exception {
		long pk = nextLong();

		UserGroup userGroup = _persistence.create(pk);

		userGroup.setCompanyId(nextLong());

		userGroup.setParentUserGroupId(nextLong());

		userGroup.setName(randomString());

		userGroup.setDescription(randomString());

		userGroup.setAddedByLDAPImport(randomBoolean());

		_persistence.update(userGroup, false);

		return userGroup;
	}

	private UserGroupPersistence _persistence;
}