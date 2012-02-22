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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBBanPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBBan mbBan = _persistence.create(pk);

		assertNotNull(mbBan);

		assertEquals(mbBan.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBBan newMBBan = addMBBan();

		_persistence.remove(newMBBan);

		MBBan existingMBBan = _persistence.fetchByPrimaryKey(newMBBan.getPrimaryKey());

		assertNull(existingMBBan);
	}

	public void testUpdateNew() throws Exception {
		addMBBan();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBBan newMBBan = _persistence.create(pk);

		newMBBan.setGroupId(nextLong());

		newMBBan.setCompanyId(nextLong());

		newMBBan.setUserId(nextLong());

		newMBBan.setUserName(randomString());

		newMBBan.setCreateDate(nextDate());

		newMBBan.setModifiedDate(nextDate());

		newMBBan.setBanUserId(nextLong());

		_persistence.update(newMBBan, false);

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		assertEquals(existingMBBan.getBanId(), newMBBan.getBanId());
		assertEquals(existingMBBan.getGroupId(), newMBBan.getGroupId());
		assertEquals(existingMBBan.getCompanyId(), newMBBan.getCompanyId());
		assertEquals(existingMBBan.getUserId(), newMBBan.getUserId());
		assertEquals(existingMBBan.getUserName(), newMBBan.getUserName());
		assertEquals(Time.getShortTimestamp(existingMBBan.getCreateDate()),
			Time.getShortTimestamp(newMBBan.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingMBBan.getModifiedDate()),
			Time.getShortTimestamp(newMBBan.getModifiedDate()));
		assertEquals(existingMBBan.getBanUserId(), newMBBan.getBanUserId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		assertEquals(existingMBBan, newMBBan);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchBanException");
		}
		catch (NoSuchBanException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.fetchByPrimaryKey(newMBBan.getPrimaryKey());

		assertEquals(existingMBBan, newMBBan);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBBan missingMBBan = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBBan);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBBan newMBBan = addMBBan();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("banId", newMBBan.getBanId()));

		List<MBBan> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBBan existingMBBan = result.get(0);

		assertEquals(existingMBBan, newMBBan);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("banId", nextLong()));

		List<MBBan> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBBan newMBBan = addMBBan();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("banId"));

		Object newBanId = newMBBan.getBanId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("banId",
				new Object[] { newBanId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingBanId = result.get(0);

		assertEquals(existingBanId, newBanId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("banId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("banId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBBan newMBBan = addMBBan();

		_persistence.clearCache();

		MBBanModelImpl existingMBBanModelImpl = (MBBanModelImpl)_persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		assertEquals(existingMBBanModelImpl.getGroupId(),
			existingMBBanModelImpl.getOriginalGroupId());
		assertEquals(existingMBBanModelImpl.getBanUserId(),
			existingMBBanModelImpl.getOriginalBanUserId());
	}

	protected MBBan addMBBan() throws Exception {
		long pk = nextLong();

		MBBan mbBan = _persistence.create(pk);

		mbBan.setGroupId(nextLong());

		mbBan.setCompanyId(nextLong());

		mbBan.setUserId(nextLong());

		mbBan.setUserName(randomString());

		mbBan.setCreateDate(nextDate());

		mbBan.setModifiedDate(nextDate());

		mbBan.setBanUserId(nextLong());

		_persistence.update(mbBan, false);

		return mbBan;
	}

	private MBBanPersistence _persistence;
}