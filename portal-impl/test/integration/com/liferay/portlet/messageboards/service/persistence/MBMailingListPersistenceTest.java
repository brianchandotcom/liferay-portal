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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMailingListPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBMailingListPersistence)PortalBeanLocatorUtil.locate(MBMailingListPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		assertNotNull(mbMailingList);

		assertEquals(mbMailingList.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		_persistence.remove(newMBMailingList);

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertNull(existingMBMailingList);
	}

	public void testUpdateNew() throws Exception {
		addMBMailingList();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBMailingList newMBMailingList = _persistence.create(pk);

		newMBMailingList.setUuid(randomString());

		newMBMailingList.setGroupId(nextLong());

		newMBMailingList.setCompanyId(nextLong());

		newMBMailingList.setUserId(nextLong());

		newMBMailingList.setUserName(randomString());

		newMBMailingList.setCreateDate(nextDate());

		newMBMailingList.setModifiedDate(nextDate());

		newMBMailingList.setCategoryId(nextLong());

		newMBMailingList.setEmailAddress(randomString());

		newMBMailingList.setInProtocol(randomString());

		newMBMailingList.setInServerName(randomString());

		newMBMailingList.setInServerPort(nextInt());

		newMBMailingList.setInUseSSL(randomBoolean());

		newMBMailingList.setInUserName(randomString());

		newMBMailingList.setInPassword(randomString());

		newMBMailingList.setInReadInterval(nextInt());

		newMBMailingList.setOutEmailAddress(randomString());

		newMBMailingList.setOutCustom(randomBoolean());

		newMBMailingList.setOutServerName(randomString());

		newMBMailingList.setOutServerPort(nextInt());

		newMBMailingList.setOutUseSSL(randomBoolean());

		newMBMailingList.setOutUserName(randomString());

		newMBMailingList.setOutPassword(randomString());

		newMBMailingList.setAllowAnonymous(randomBoolean());

		newMBMailingList.setActive(randomBoolean());

		_persistence.update(newMBMailingList, false);

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList.getUuid(), newMBMailingList.getUuid());
		assertEquals(existingMBMailingList.getMailingListId(),
			newMBMailingList.getMailingListId());
		assertEquals(existingMBMailingList.getGroupId(),
			newMBMailingList.getGroupId());
		assertEquals(existingMBMailingList.getCompanyId(),
			newMBMailingList.getCompanyId());
		assertEquals(existingMBMailingList.getUserId(),
			newMBMailingList.getUserId());
		assertEquals(existingMBMailingList.getUserName(),
			newMBMailingList.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getCreateDate()),
			Time.getShortTimestamp(newMBMailingList.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getModifiedDate()),
			Time.getShortTimestamp(newMBMailingList.getModifiedDate()));
		assertEquals(existingMBMailingList.getCategoryId(),
			newMBMailingList.getCategoryId());
		assertEquals(existingMBMailingList.getEmailAddress(),
			newMBMailingList.getEmailAddress());
		assertEquals(existingMBMailingList.getInProtocol(),
			newMBMailingList.getInProtocol());
		assertEquals(existingMBMailingList.getInServerName(),
			newMBMailingList.getInServerName());
		assertEquals(existingMBMailingList.getInServerPort(),
			newMBMailingList.getInServerPort());
		assertEquals(existingMBMailingList.getInUseSSL(),
			newMBMailingList.getInUseSSL());
		assertEquals(existingMBMailingList.getInUserName(),
			newMBMailingList.getInUserName());
		assertEquals(existingMBMailingList.getInPassword(),
			newMBMailingList.getInPassword());
		assertEquals(existingMBMailingList.getInReadInterval(),
			newMBMailingList.getInReadInterval());
		assertEquals(existingMBMailingList.getOutEmailAddress(),
			newMBMailingList.getOutEmailAddress());
		assertEquals(existingMBMailingList.getOutCustom(),
			newMBMailingList.getOutCustom());
		assertEquals(existingMBMailingList.getOutServerName(),
			newMBMailingList.getOutServerName());
		assertEquals(existingMBMailingList.getOutServerPort(),
			newMBMailingList.getOutServerPort());
		assertEquals(existingMBMailingList.getOutUseSSL(),
			newMBMailingList.getOutUseSSL());
		assertEquals(existingMBMailingList.getOutUserName(),
			newMBMailingList.getOutUserName());
		assertEquals(existingMBMailingList.getOutPassword(),
			newMBMailingList.getOutPassword());
		assertEquals(existingMBMailingList.getAllowAnonymous(),
			newMBMailingList.getAllowAnonymous());
		assertEquals(existingMBMailingList.getActive(),
			newMBMailingList.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchMailingListException");
		}
		catch (NoSuchMailingListException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBMailingList missingMBMailingList = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBMailingList);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId",
				newMBMailingList.getMailingListId()));

		List<MBMailingList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBMailingList existingMBMailingList = result.get(0);

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId", nextLong()));

		List<MBMailingList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"mailingListId"));

		Object newMailingListId = newMBMailingList.getMailingListId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("mailingListId",
				new Object[] { newMailingListId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingMailingListId = result.get(0);

		assertEquals(existingMailingListId, newMailingListId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"mailingListId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("mailingListId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBMailingList newMBMailingList = addMBMailingList();

		_persistence.clearCache();

		MBMailingListModelImpl existingMBMailingListModelImpl = (MBMailingListModelImpl)_persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertTrue(Validator.equals(existingMBMailingListModelImpl.getUuid(),
				existingMBMailingListModelImpl.getOriginalUuid()));
		assertEquals(existingMBMailingListModelImpl.getGroupId(),
			existingMBMailingListModelImpl.getOriginalGroupId());

		assertEquals(existingMBMailingListModelImpl.getGroupId(),
			existingMBMailingListModelImpl.getOriginalGroupId());
		assertEquals(existingMBMailingListModelImpl.getCategoryId(),
			existingMBMailingListModelImpl.getOriginalCategoryId());
	}

	protected MBMailingList addMBMailingList() throws Exception {
		long pk = nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		mbMailingList.setUuid(randomString());

		mbMailingList.setGroupId(nextLong());

		mbMailingList.setCompanyId(nextLong());

		mbMailingList.setUserId(nextLong());

		mbMailingList.setUserName(randomString());

		mbMailingList.setCreateDate(nextDate());

		mbMailingList.setModifiedDate(nextDate());

		mbMailingList.setCategoryId(nextLong());

		mbMailingList.setEmailAddress(randomString());

		mbMailingList.setInProtocol(randomString());

		mbMailingList.setInServerName(randomString());

		mbMailingList.setInServerPort(nextInt());

		mbMailingList.setInUseSSL(randomBoolean());

		mbMailingList.setInUserName(randomString());

		mbMailingList.setInPassword(randomString());

		mbMailingList.setInReadInterval(nextInt());

		mbMailingList.setOutEmailAddress(randomString());

		mbMailingList.setOutCustom(randomBoolean());

		mbMailingList.setOutServerName(randomString());

		mbMailingList.setOutServerPort(nextInt());

		mbMailingList.setOutUseSSL(randomBoolean());

		mbMailingList.setOutUserName(randomString());

		mbMailingList.setOutPassword(randomString());

		mbMailingList.setAllowAnonymous(randomBoolean());

		mbMailingList.setActive(randomBoolean());

		_persistence.update(mbMailingList, false);

		return mbMailingList;
	}

	private MBMailingListPersistence _persistence;
}