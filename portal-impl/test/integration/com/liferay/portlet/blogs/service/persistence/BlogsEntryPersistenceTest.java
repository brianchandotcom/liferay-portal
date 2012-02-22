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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BlogsEntryPersistence)PortalBeanLocatorUtil.locate(BlogsEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BlogsEntry blogsEntry = _persistence.create(pk);

		assertNotNull(blogsEntry);

		assertEquals(blogsEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		_persistence.remove(newBlogsEntry);

		BlogsEntry existingBlogsEntry = _persistence.fetchByPrimaryKey(newBlogsEntry.getPrimaryKey());

		assertNull(existingBlogsEntry);
	}

	public void testUpdateNew() throws Exception {
		addBlogsEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BlogsEntry newBlogsEntry = _persistence.create(pk);

		newBlogsEntry.setUuid(randomString());

		newBlogsEntry.setGroupId(nextLong());

		newBlogsEntry.setCompanyId(nextLong());

		newBlogsEntry.setUserId(nextLong());

		newBlogsEntry.setUserName(randomString());

		newBlogsEntry.setCreateDate(nextDate());

		newBlogsEntry.setModifiedDate(nextDate());

		newBlogsEntry.setTitle(randomString());

		newBlogsEntry.setUrlTitle(randomString());

		newBlogsEntry.setDescription(randomString());

		newBlogsEntry.setContent(randomString());

		newBlogsEntry.setDisplayDate(nextDate());

		newBlogsEntry.setAllowPingbacks(randomBoolean());

		newBlogsEntry.setAllowTrackbacks(randomBoolean());

		newBlogsEntry.setTrackbacks(randomString());

		newBlogsEntry.setSmallImage(randomBoolean());

		newBlogsEntry.setSmallImageId(nextLong());

		newBlogsEntry.setSmallImageURL(randomString());

		newBlogsEntry.setStatus(nextInt());

		newBlogsEntry.setStatusByUserId(nextLong());

		newBlogsEntry.setStatusByUserName(randomString());

		newBlogsEntry.setStatusDate(nextDate());

		_persistence.update(newBlogsEntry, false);

		BlogsEntry existingBlogsEntry = _persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		assertEquals(existingBlogsEntry.getUuid(), newBlogsEntry.getUuid());
		assertEquals(existingBlogsEntry.getEntryId(), newBlogsEntry.getEntryId());
		assertEquals(existingBlogsEntry.getGroupId(), newBlogsEntry.getGroupId());
		assertEquals(existingBlogsEntry.getCompanyId(),
			newBlogsEntry.getCompanyId());
		assertEquals(existingBlogsEntry.getUserId(), newBlogsEntry.getUserId());
		assertEquals(existingBlogsEntry.getUserName(),
			newBlogsEntry.getUserName());
		assertEquals(Time.getShortTimestamp(existingBlogsEntry.getCreateDate()),
			Time.getShortTimestamp(newBlogsEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingBlogsEntry.getModifiedDate()),
			Time.getShortTimestamp(newBlogsEntry.getModifiedDate()));
		assertEquals(existingBlogsEntry.getTitle(), newBlogsEntry.getTitle());
		assertEquals(existingBlogsEntry.getUrlTitle(),
			newBlogsEntry.getUrlTitle());
		assertEquals(existingBlogsEntry.getDescription(),
			newBlogsEntry.getDescription());
		assertEquals(existingBlogsEntry.getContent(), newBlogsEntry.getContent());
		assertEquals(Time.getShortTimestamp(existingBlogsEntry.getDisplayDate()),
			Time.getShortTimestamp(newBlogsEntry.getDisplayDate()));
		assertEquals(existingBlogsEntry.getAllowPingbacks(),
			newBlogsEntry.getAllowPingbacks());
		assertEquals(existingBlogsEntry.getAllowTrackbacks(),
			newBlogsEntry.getAllowTrackbacks());
		assertEquals(existingBlogsEntry.getTrackbacks(),
			newBlogsEntry.getTrackbacks());
		assertEquals(existingBlogsEntry.getSmallImage(),
			newBlogsEntry.getSmallImage());
		assertEquals(existingBlogsEntry.getSmallImageId(),
			newBlogsEntry.getSmallImageId());
		assertEquals(existingBlogsEntry.getSmallImageURL(),
			newBlogsEntry.getSmallImageURL());
		assertEquals(existingBlogsEntry.getStatus(), newBlogsEntry.getStatus());
		assertEquals(existingBlogsEntry.getStatusByUserId(),
			newBlogsEntry.getStatusByUserId());
		assertEquals(existingBlogsEntry.getStatusByUserName(),
			newBlogsEntry.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingBlogsEntry.getStatusDate()),
			Time.getShortTimestamp(newBlogsEntry.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		BlogsEntry existingBlogsEntry = _persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		BlogsEntry existingBlogsEntry = _persistence.fetchByPrimaryKey(newBlogsEntry.getPrimaryKey());

		assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BlogsEntry missingBlogsEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBlogsEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newBlogsEntry.getEntryId()));

		List<BlogsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BlogsEntry existingBlogsEntry = result.get(0);

		assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<BlogsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newBlogsEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		assertEquals(existingEntryId, newEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BlogsEntry newBlogsEntry = addBlogsEntry();

		_persistence.clearCache();

		BlogsEntryModelImpl existingBlogsEntryModelImpl = (BlogsEntryModelImpl)_persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		assertTrue(Validator.equals(existingBlogsEntryModelImpl.getUuid(),
				existingBlogsEntryModelImpl.getOriginalUuid()));
		assertEquals(existingBlogsEntryModelImpl.getGroupId(),
			existingBlogsEntryModelImpl.getOriginalGroupId());

		assertEquals(existingBlogsEntryModelImpl.getGroupId(),
			existingBlogsEntryModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingBlogsEntryModelImpl.getUrlTitle(),
				existingBlogsEntryModelImpl.getOriginalUrlTitle()));
	}

	protected BlogsEntry addBlogsEntry() throws Exception {
		long pk = nextLong();

		BlogsEntry blogsEntry = _persistence.create(pk);

		blogsEntry.setUuid(randomString());

		blogsEntry.setGroupId(nextLong());

		blogsEntry.setCompanyId(nextLong());

		blogsEntry.setUserId(nextLong());

		blogsEntry.setUserName(randomString());

		blogsEntry.setCreateDate(nextDate());

		blogsEntry.setModifiedDate(nextDate());

		blogsEntry.setTitle(randomString());

		blogsEntry.setUrlTitle(randomString());

		blogsEntry.setDescription(randomString());

		blogsEntry.setContent(randomString());

		blogsEntry.setDisplayDate(nextDate());

		blogsEntry.setAllowPingbacks(randomBoolean());

		blogsEntry.setAllowTrackbacks(randomBoolean());

		blogsEntry.setTrackbacks(randomString());

		blogsEntry.setSmallImage(randomBoolean());

		blogsEntry.setSmallImageId(nextLong());

		blogsEntry.setSmallImageURL(randomString());

		blogsEntry.setStatus(nextInt());

		blogsEntry.setStatusByUserId(nextLong());

		blogsEntry.setStatusByUserName(randomString());

		blogsEntry.setStatusDate(nextDate());

		_persistence.update(blogsEntry, false);

		return blogsEntry;
	}

	private BlogsEntryPersistence _persistence;
}