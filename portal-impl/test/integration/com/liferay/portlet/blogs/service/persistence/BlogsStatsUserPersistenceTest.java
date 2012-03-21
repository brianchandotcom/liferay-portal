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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.assertion.AssertUtils;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BlogsStatsUserPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (BlogsStatsUserPersistence)PortalBeanLocatorUtil.locate(BlogsStatsUserPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		Assert.assertNotNull(blogsStatsUser);

		Assert.assertEquals(blogsStatsUser.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.remove(newBlogsStatsUser);

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertNull(existingBlogsStatsUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBlogsStatsUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BlogsStatsUser newBlogsStatsUser = _persistence.create(pk);

		newBlogsStatsUser.setGroupId(ServiceTestUtil.nextLong());

		newBlogsStatsUser.setCompanyId(ServiceTestUtil.nextLong());

		newBlogsStatsUser.setUserId(ServiceTestUtil.nextLong());

		newBlogsStatsUser.setEntryCount(ServiceTestUtil.nextInt());

		newBlogsStatsUser.setLastPostDate(ServiceTestUtil.nextDate());

		newBlogsStatsUser.setRatingsTotalEntries(ServiceTestUtil.nextInt());

		newBlogsStatsUser.setRatingsTotalScore(ServiceTestUtil.nextDouble());

		newBlogsStatsUser.setRatingsAverageScore(ServiceTestUtil.nextDouble());

		_persistence.update(newBlogsStatsUser, false);

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser.getStatsUserId(),
			newBlogsStatsUser.getStatsUserId());
		Assert.assertEquals(existingBlogsStatsUser.getGroupId(),
			newBlogsStatsUser.getGroupId());
		Assert.assertEquals(existingBlogsStatsUser.getCompanyId(),
			newBlogsStatsUser.getCompanyId());
		Assert.assertEquals(existingBlogsStatsUser.getUserId(),
			newBlogsStatsUser.getUserId());
		Assert.assertEquals(existingBlogsStatsUser.getEntryCount(),
			newBlogsStatsUser.getEntryCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newBlogsStatsUser.getLastPostDate()));
		Assert.assertEquals(existingBlogsStatsUser.getRatingsTotalEntries(),
			newBlogsStatsUser.getRatingsTotalEntries());
		AssertUtils.assertEquals(existingBlogsStatsUser.getRatingsTotalScore(),
			newBlogsStatsUser.getRatingsTotalScore());
		AssertUtils.assertEquals(existingBlogsStatsUser.getRatingsAverageScore(),
			newBlogsStatsUser.getRatingsAverageScore());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchStatsUserException");
		}
		catch (NoSuchStatsUserException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BlogsStatsUser missingBlogsStatsUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBlogsStatsUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newBlogsStatsUser.getStatsUserId()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BlogsStatsUser existingBlogsStatsUser = result.get(0);

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				ServiceTestUtil.nextLong()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingStatsUserId = result.get(0);

		Assert.assertEquals(existingStatsUserId, newStatsUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.clearCache();

		BlogsStatsUserModelImpl existingBlogsStatsUserModelImpl = (BlogsStatsUserModelImpl)_persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUserModelImpl.getGroupId(),
			existingBlogsStatsUserModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingBlogsStatsUserModelImpl.getUserId(),
			existingBlogsStatsUserModelImpl.getOriginalUserId());
	}

	protected BlogsStatsUser addBlogsStatsUser() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		blogsStatsUser.setGroupId(ServiceTestUtil.nextLong());

		blogsStatsUser.setCompanyId(ServiceTestUtil.nextLong());

		blogsStatsUser.setUserId(ServiceTestUtil.nextLong());

		blogsStatsUser.setEntryCount(ServiceTestUtil.nextInt());

		blogsStatsUser.setLastPostDate(ServiceTestUtil.nextDate());

		blogsStatsUser.setRatingsTotalEntries(ServiceTestUtil.nextInt());

		blogsStatsUser.setRatingsTotalScore(ServiceTestUtil.nextDouble());

		blogsStatsUser.setRatingsAverageScore(ServiceTestUtil.nextDouble());

		_persistence.update(blogsStatsUser, false);

		return blogsStatsUser;
	}

	private BlogsStatsUserPersistence _persistence;
}