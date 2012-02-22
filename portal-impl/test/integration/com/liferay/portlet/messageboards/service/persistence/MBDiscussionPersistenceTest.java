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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBDiscussionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBDiscussionPersistence)PortalBeanLocatorUtil.locate(MBDiscussionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBDiscussion mbDiscussion = _persistence.create(pk);

		assertNotNull(mbDiscussion);

		assertEquals(mbDiscussion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.remove(newMBDiscussion);

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		assertNull(existingMBDiscussion);
	}

	public void testUpdateNew() throws Exception {
		addMBDiscussion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBDiscussion newMBDiscussion = _persistence.create(pk);

		newMBDiscussion.setClassNameId(nextLong());

		newMBDiscussion.setClassPK(nextLong());

		newMBDiscussion.setThreadId(nextLong());

		_persistence.update(newMBDiscussion, false);

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		assertEquals(existingMBDiscussion.getDiscussionId(),
			newMBDiscussion.getDiscussionId());
		assertEquals(existingMBDiscussion.getClassNameId(),
			newMBDiscussion.getClassNameId());
		assertEquals(existingMBDiscussion.getClassPK(),
			newMBDiscussion.getClassPK());
		assertEquals(existingMBDiscussion.getThreadId(),
			newMBDiscussion.getThreadId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchDiscussionException");
		}
		catch (NoSuchDiscussionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBDiscussion missingMBDiscussion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBDiscussion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId",
				newMBDiscussion.getDiscussionId()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBDiscussion existingMBDiscussion = result.get(0);

		assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId", nextLong()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		Object newDiscussionId = newMBDiscussion.getDiscussionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { newDiscussionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDiscussionId = result.get(0);

		assertEquals(existingDiscussionId, newDiscussionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.clearCache();

		MBDiscussionModelImpl existingMBDiscussionModelImpl = (MBDiscussionModelImpl)_persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		assertEquals(existingMBDiscussionModelImpl.getThreadId(),
			existingMBDiscussionModelImpl.getOriginalThreadId());

		assertEquals(existingMBDiscussionModelImpl.getClassNameId(),
			existingMBDiscussionModelImpl.getOriginalClassNameId());
		assertEquals(existingMBDiscussionModelImpl.getClassPK(),
			existingMBDiscussionModelImpl.getOriginalClassPK());
	}

	protected MBDiscussion addMBDiscussion() throws Exception {
		long pk = nextLong();

		MBDiscussion mbDiscussion = _persistence.create(pk);

		mbDiscussion.setClassNameId(nextLong());

		mbDiscussion.setClassPK(nextLong());

		mbDiscussion.setThreadId(nextLong());

		_persistence.update(mbDiscussion, false);

		return mbDiscussion;
	}

	private MBDiscussionPersistence _persistence;
}