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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsStatsUserPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BlogsStatsUserPersistence)PortalBeanLocatorUtil.locate(BlogsStatsUserPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		assertNotNull(blogsStatsUser);

		assertEquals(blogsStatsUser.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.remove(newBlogsStatsUser);

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertNull(existingBlogsStatsUser);
	}

	public void testUpdateNew() throws Exception {
		addBlogsStatsUser();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BlogsStatsUser newBlogsStatsUser = _persistence.create(pk);

		newBlogsStatsUser.setGroupId(nextLong());

		newBlogsStatsUser.setCompanyId(nextLong());

		newBlogsStatsUser.setUserId(nextLong());

		newBlogsStatsUser.setEntryCount(nextInt());

		newBlogsStatsUser.setLastPostDate(nextDate());

		newBlogsStatsUser.setRatingsTotalEntries(nextInt());

		newBlogsStatsUser.setRatingsTotalScore(nextDouble());

		newBlogsStatsUser.setRatingsAverageScore(nextDouble());

		_persistence.update(newBlogsStatsUser, false);

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser.getStatsUserId(),
			newBlogsStatsUser.getStatsUserId());
		assertEquals(existingBlogsStatsUser.getGroupId(),
			newBlogsStatsUser.getGroupId());
		assertEquals(existingBlogsStatsUser.getCompanyId(),
			newBlogsStatsUser.getCompanyId());
		assertEquals(existingBlogsStatsUser.getUserId(),
			newBlogsStatsUser.getUserId());
		assertEquals(existingBlogsStatsUser.getEntryCount(),
			newBlogsStatsUser.getEntryCount());
		assertEquals(Time.getShortTimestamp(
				existingBlogsStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newBlogsStatsUser.getLastPostDate()));
		assertEquals(existingBlogsStatsUser.getRatingsTotalEntries(),
			newBlogsStatsUser.getRatingsTotalEntries());
		assertEquals(existingBlogsStatsUser.getRatingsTotalScore(),
			newBlogsStatsUser.getRatingsTotalScore());
		assertEquals(existingBlogsStatsUser.getRatingsAverageScore(),
			newBlogsStatsUser.getRatingsAverageScore());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStatsUserException");
		}
		catch (NoSuchStatsUserException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BlogsStatsUser missingBlogsStatsUser = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBlogsStatsUser);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newBlogsStatsUser.getStatsUserId()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BlogsStatsUser existingBlogsStatsUser = result.get(0);

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId", nextLong()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		Object newStatsUserId = newBlogsStatsUser.getStatsUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { newStatsUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingStatsUserId = result.get(0);

		assertEquals(existingStatsUserId, newStatsUserId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.clearCache();

		BlogsStatsUserModelImpl existingBlogsStatsUserModelImpl = (BlogsStatsUserModelImpl)_persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUserModelImpl.getGroupId(),
			existingBlogsStatsUserModelImpl.getOriginalGroupId());
		assertEquals(existingBlogsStatsUserModelImpl.getUserId(),
			existingBlogsStatsUserModelImpl.getOriginalUserId());
	}

	protected BlogsStatsUser addBlogsStatsUser() throws Exception {
		long pk = nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		blogsStatsUser.setGroupId(nextLong());

		blogsStatsUser.setCompanyId(nextLong());

		blogsStatsUser.setUserId(nextLong());

		blogsStatsUser.setEntryCount(nextInt());

		blogsStatsUser.setLastPostDate(nextDate());

		blogsStatsUser.setRatingsTotalEntries(nextInt());

		blogsStatsUser.setRatingsTotalScore(nextDouble());

		blogsStatsUser.setRatingsAverageScore(nextDouble());

		_persistence.update(blogsStatsUser, false);

		return blogsStatsUser;
	}

	private BlogsStatsUserPersistence _persistence;
}