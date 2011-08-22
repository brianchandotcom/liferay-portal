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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadFlagPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBThreadFlagPersistence)PortalBeanLocatorUtil.locate(MBThreadFlagPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		assertNotNull(mbThreadFlag);

		assertEquals(mbThreadFlag.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.remove(newMBThreadFlag);

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		assertNull(existingMBThreadFlag);
	}

	public void testUpdateNew() throws Exception {
		addMBThreadFlag();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBThreadFlag newMBThreadFlag = _persistence.create(pk);

		newMBThreadFlag.setUserId(nextLong());

		newMBThreadFlag.setModifiedDate(nextDate());

		newMBThreadFlag.setThreadId(nextLong());

		_persistence.update(newMBThreadFlag, false);

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		assertEquals(existingMBThreadFlag.getThreadFlagId(),
			newMBThreadFlag.getThreadFlagId());
		assertEquals(existingMBThreadFlag.getUserId(),
			newMBThreadFlag.getUserId());
		assertEquals(Time.getShortTimestamp(
				existingMBThreadFlag.getModifiedDate()),
			Time.getShortTimestamp(newMBThreadFlag.getModifiedDate()));
		assertEquals(existingMBThreadFlag.getThreadId(),
			newMBThreadFlag.getThreadId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchThreadFlagException");
		}
		catch (NoSuchThreadFlagException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBThreadFlag missingMBThreadFlag = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBThreadFlag);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId",
				newMBThreadFlag.getThreadFlagId()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBThreadFlag existingMBThreadFlag = result.get(0);

		assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId", nextLong()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		Object newThreadFlagId = newMBThreadFlag.getThreadFlagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { newThreadFlagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingThreadFlagId = result.get(0);

		assertEquals(existingThreadFlagId, newThreadFlagId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.clearCache();

		MBThreadFlagModelImpl existingMBThreadFlagModelImpl = (MBThreadFlagModelImpl)_persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		assertEquals(existingMBThreadFlagModelImpl.getUserId(),
			existingMBThreadFlagModelImpl.getOriginalUserId());
		assertEquals(existingMBThreadFlagModelImpl.getThreadId(),
			existingMBThreadFlagModelImpl.getOriginalThreadId());
	}

	protected MBThreadFlag addMBThreadFlag() throws Exception {
		long pk = nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		mbThreadFlag.setUserId(nextLong());

		mbThreadFlag.setModifiedDate(nextDate());

		mbThreadFlag.setThreadId(nextLong());

		_persistence.update(mbThreadFlag, false);

		return mbThreadFlag;
	}

	private MBThreadFlagPersistence _persistence;
}