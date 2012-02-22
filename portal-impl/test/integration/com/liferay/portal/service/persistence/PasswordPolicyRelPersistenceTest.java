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

import com.liferay.portal.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.impl.PasswordPolicyRelModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyRelPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PasswordPolicyRelPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyRelPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		assertNotNull(passwordPolicyRel);

		assertEquals(passwordPolicyRel.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		_persistence.remove(newPasswordPolicyRel);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertNull(existingPasswordPolicyRel);
	}

	public void testUpdateNew() throws Exception {
		addPasswordPolicyRel();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel newPasswordPolicyRel = _persistence.create(pk);

		newPasswordPolicyRel.setPasswordPolicyId(nextLong());

		newPasswordPolicyRel.setClassNameId(nextLong());

		newPasswordPolicyRel.setClassPK(nextLong());

		_persistence.update(newPasswordPolicyRel, false);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel.getPasswordPolicyRelId(),
			newPasswordPolicyRel.getPasswordPolicyRelId());
		assertEquals(existingPasswordPolicyRel.getPasswordPolicyId(),
			newPasswordPolicyRel.getPasswordPolicyId());
		assertEquals(existingPasswordPolicyRel.getClassNameId(),
			newPasswordPolicyRel.getClassNameId());
		assertEquals(existingPasswordPolicyRel.getClassPK(),
			newPasswordPolicyRel.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchPasswordPolicyRelException");
		}
		catch (NoSuchPasswordPolicyRelException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel missingPasswordPolicyRel = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPasswordPolicyRel);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				newPasswordPolicyRel.getPasswordPolicyRelId()));

		List<PasswordPolicyRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PasswordPolicyRel existingPasswordPolicyRel = result.get(0);

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				nextLong()));

		List<PasswordPolicyRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyRelId"));

		Object newPasswordPolicyRelId = newPasswordPolicyRel.getPasswordPolicyRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyRelId",
				new Object[] { newPasswordPolicyRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPasswordPolicyRelId = result.get(0);

		assertEquals(existingPasswordPolicyRelId, newPasswordPolicyRelId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyRelId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		_persistence.clearCache();

		PasswordPolicyRelModelImpl existingPasswordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)_persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRelModelImpl.getClassNameId(),
			existingPasswordPolicyRelModelImpl.getOriginalClassNameId());
		assertEquals(existingPasswordPolicyRelModelImpl.getClassPK(),
			existingPasswordPolicyRelModelImpl.getOriginalClassPK());

		assertEquals(existingPasswordPolicyRelModelImpl.getPasswordPolicyId(),
			existingPasswordPolicyRelModelImpl.getOriginalPasswordPolicyId());
		assertEquals(existingPasswordPolicyRelModelImpl.getClassNameId(),
			existingPasswordPolicyRelModelImpl.getOriginalClassNameId());
		assertEquals(existingPasswordPolicyRelModelImpl.getClassPK(),
			existingPasswordPolicyRelModelImpl.getOriginalClassPK());
	}

	protected PasswordPolicyRel addPasswordPolicyRel()
		throws Exception {
		long pk = nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		passwordPolicyRel.setPasswordPolicyId(nextLong());

		passwordPolicyRel.setClassNameId(nextLong());

		passwordPolicyRel.setClassPK(nextLong());

		_persistence.update(passwordPolicyRel, false);

		return passwordPolicyRel;
	}

	private PasswordPolicyRelPersistence _persistence;
}