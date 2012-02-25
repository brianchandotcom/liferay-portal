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

import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBThread mbThread = _persistence.create(pk);

		assertNotNull(mbThread);

		assertEquals(mbThread.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBThread newMBThread = addMBThread();

		_persistence.remove(newMBThread);

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		assertNull(existingMBThread);
	}

	public void testUpdateNew() throws Exception {
		addMBThread();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBThread newMBThread = _persistence.create(pk);

		newMBThread.setGroupId(nextLong());

		newMBThread.setCompanyId(nextLong());

		newMBThread.setCategoryId(nextLong());

		newMBThread.setRootMessageId(nextLong());

		newMBThread.setRootMessageUserId(nextLong());

		newMBThread.setMessageCount(nextInt());

		newMBThread.setViewCount(nextInt());

		newMBThread.setLastPostByUserId(nextLong());

		newMBThread.setLastPostDate(nextDate());

		newMBThread.setPriority(nextDouble());

		newMBThread.setQuestion(randomBoolean());

		newMBThread.setStatus(nextInt());

		newMBThread.setStatusByUserId(nextLong());

		newMBThread.setStatusByUserName(randomString());

		newMBThread.setStatusDate(nextDate());

		_persistence.update(newMBThread, false);

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread.getThreadId(), newMBThread.getThreadId());
		assertEquals(existingMBThread.getGroupId(), newMBThread.getGroupId());
		assertEquals(existingMBThread.getCompanyId(), newMBThread.getCompanyId());
		assertEquals(existingMBThread.getCategoryId(),
			newMBThread.getCategoryId());
		assertEquals(existingMBThread.getRootMessageId(),
			newMBThread.getRootMessageId());
		assertEquals(existingMBThread.getRootMessageUserId(),
			newMBThread.getRootMessageUserId());
		assertEquals(existingMBThread.getMessageCount(),
			newMBThread.getMessageCount());
		assertEquals(existingMBThread.getViewCount(), newMBThread.getViewCount());
		assertEquals(existingMBThread.getLastPostByUserId(),
			newMBThread.getLastPostByUserId());
		assertEquals(Time.getShortTimestamp(existingMBThread.getLastPostDate()),
			Time.getShortTimestamp(newMBThread.getLastPostDate()));
		assertEquals(existingMBThread.getPriority(), newMBThread.getPriority());
		assertEquals(existingMBThread.getQuestion(), newMBThread.getQuestion());
		assertEquals(existingMBThread.getStatus(), newMBThread.getStatus());
		assertEquals(existingMBThread.getStatusByUserId(),
			newMBThread.getStatusByUserId());
		assertEquals(existingMBThread.getStatusByUserName(),
			newMBThread.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingMBThread.getStatusDate()),
			Time.getShortTimestamp(newMBThread.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread, newMBThread);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchThreadException");
		}
		catch (NoSuchThreadException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread, newMBThread);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBThread missingMBThread = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBThread);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThread newMBThread = addMBThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId",
				newMBThread.getThreadId()));

		List<MBThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBThread existingMBThread = result.get(0);

		assertEquals(existingMBThread, newMBThread);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId", nextLong()));

		List<MBThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBThread newMBThread = addMBThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("threadId"));

		Object newThreadId = newMBThread.getThreadId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadId",
				new Object[] { newThreadId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingThreadId = result.get(0);

		assertEquals(existingThreadId, newThreadId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("threadId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBThread newMBThread = addMBThread();

		_persistence.clearCache();

		MBThreadModelImpl existingMBThreadModelImpl = (MBThreadModelImpl)_persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThreadModelImpl.getRootMessageId(),
			existingMBThreadModelImpl.getOriginalRootMessageId());
	}

	protected MBThread addMBThread() throws Exception {
		long pk = nextLong();

		MBThread mbThread = _persistence.create(pk);

		mbThread.setGroupId(nextLong());

		mbThread.setCompanyId(nextLong());

		mbThread.setCategoryId(nextLong());

		mbThread.setRootMessageId(nextLong());

		mbThread.setRootMessageUserId(nextLong());

		mbThread.setMessageCount(nextInt());

		mbThread.setViewCount(nextInt());

		mbThread.setLastPostByUserId(nextLong());

		mbThread.setLastPostDate(nextDate());

		mbThread.setPriority(nextDouble());

		mbThread.setQuestion(randomBoolean());

		mbThread.setStatus(nextInt());

		mbThread.setStatusByUserId(nextLong());

		mbThread.setStatusByUserName(randomString());

		mbThread.setStatusDate(nextDate());

		_persistence.update(mbThread, false);

		return mbThread;
	}

	private MBThreadPersistence _persistence;
}