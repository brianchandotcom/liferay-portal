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

import com.liferay.portal.NoSuchUserIdMapperException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserIdMapperPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserIdMapperPersistence)PortalBeanLocatorUtil.locate(UserIdMapperPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		assertNotNull(userIdMapper);

		assertEquals(userIdMapper.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		_persistence.remove(newUserIdMapper);

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertNull(existingUserIdMapper);
	}

	public void testUpdateNew() throws Exception {
		addUserIdMapper();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserIdMapper newUserIdMapper = _persistence.create(pk);

		newUserIdMapper.setUserId(nextLong());

		newUserIdMapper.setType(randomString());

		newUserIdMapper.setDescription(randomString());

		newUserIdMapper.setExternalUserId(randomString());

		_persistence.update(newUserIdMapper, false);

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper.getUserIdMapperId(),
			newUserIdMapper.getUserIdMapperId());
		assertEquals(existingUserIdMapper.getUserId(),
			newUserIdMapper.getUserId());
		assertEquals(existingUserIdMapper.getType(), newUserIdMapper.getType());
		assertEquals(existingUserIdMapper.getDescription(),
			newUserIdMapper.getDescription());
		assertEquals(existingUserIdMapper.getExternalUserId(),
			newUserIdMapper.getExternalUserId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserIdMapperException");
		}
		catch (NoSuchUserIdMapperException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserIdMapper missingUserIdMapper = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserIdMapper);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId",
				newUserIdMapper.getUserIdMapperId()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserIdMapper existingUserIdMapper = result.get(0);

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId", nextLong()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userIdMapperId"));

		Object newUserIdMapperId = newUserIdMapper.getUserIdMapperId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userIdMapperId",
				new Object[] { newUserIdMapperId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingUserIdMapperId = result.get(0);

		assertEquals(existingUserIdMapperId, newUserIdMapperId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userIdMapperId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userIdMapperId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserIdMapper newUserIdMapper = addUserIdMapper();

		_persistence.clearCache();

		UserIdMapperModelImpl existingUserIdMapperModelImpl = (UserIdMapperModelImpl)_persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapperModelImpl.getUserId(),
			existingUserIdMapperModelImpl.getOriginalUserId());
		assertTrue(Validator.equals(existingUserIdMapperModelImpl.getType(),
				existingUserIdMapperModelImpl.getOriginalType()));

		assertTrue(Validator.equals(existingUserIdMapperModelImpl.getType(),
				existingUserIdMapperModelImpl.getOriginalType()));
		assertTrue(Validator.equals(
				existingUserIdMapperModelImpl.getExternalUserId(),
				existingUserIdMapperModelImpl.getOriginalExternalUserId()));
	}

	protected UserIdMapper addUserIdMapper() throws Exception {
		long pk = nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		userIdMapper.setUserId(nextLong());

		userIdMapper.setType(randomString());

		userIdMapper.setDescription(randomString());

		userIdMapper.setExternalUserId(randomString());

		_persistence.update(userIdMapper, false);

		return userIdMapper;
	}

	private UserIdMapperPersistence _persistence;
}