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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class BookmarksEntryPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		Assert.assertNotNull(bookmarksEntry);

		Assert.assertEquals(bookmarksEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.remove(newBookmarksEntry);

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertNull(existingBookmarksEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBookmarksEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksEntry newBookmarksEntry = _persistence.create(pk);

		newBookmarksEntry.setUuid(ServiceTestUtil.randomString());

		newBookmarksEntry.setGroupId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setCompanyId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setUserId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setUserName(ServiceTestUtil.randomString());

		newBookmarksEntry.setCreateDate(ServiceTestUtil.nextDate());

		newBookmarksEntry.setModifiedDate(ServiceTestUtil.nextDate());

		newBookmarksEntry.setResourceBlockId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setFolderId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setName(ServiceTestUtil.randomString());

		newBookmarksEntry.setUrl(ServiceTestUtil.randomString());

		newBookmarksEntry.setDescription(ServiceTestUtil.randomString());

		newBookmarksEntry.setVisits(ServiceTestUtil.nextInt());

		newBookmarksEntry.setPriority(ServiceTestUtil.nextInt());

		newBookmarksEntry.setStatus(ServiceTestUtil.nextInt());

		newBookmarksEntry.setStatusByUserId(ServiceTestUtil.nextLong());

		newBookmarksEntry.setStatusByUserName(ServiceTestUtil.randomString());

		newBookmarksEntry.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newBookmarksEntry);

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry.getUuid(),
			newBookmarksEntry.getUuid());
		Assert.assertEquals(existingBookmarksEntry.getEntryId(),
			newBookmarksEntry.getEntryId());
		Assert.assertEquals(existingBookmarksEntry.getGroupId(),
			newBookmarksEntry.getGroupId());
		Assert.assertEquals(existingBookmarksEntry.getCompanyId(),
			newBookmarksEntry.getCompanyId());
		Assert.assertEquals(existingBookmarksEntry.getUserId(),
			newBookmarksEntry.getUserId());
		Assert.assertEquals(existingBookmarksEntry.getUserName(),
			newBookmarksEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getCreateDate()),
			Time.getShortTimestamp(newBookmarksEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getModifiedDate()),
			Time.getShortTimestamp(newBookmarksEntry.getModifiedDate()));
		Assert.assertEquals(existingBookmarksEntry.getResourceBlockId(),
			newBookmarksEntry.getResourceBlockId());
		Assert.assertEquals(existingBookmarksEntry.getFolderId(),
			newBookmarksEntry.getFolderId());
		Assert.assertEquals(existingBookmarksEntry.getName(),
			newBookmarksEntry.getName());
		Assert.assertEquals(existingBookmarksEntry.getUrl(),
			newBookmarksEntry.getUrl());
		Assert.assertEquals(existingBookmarksEntry.getDescription(),
			newBookmarksEntry.getDescription());
		Assert.assertEquals(existingBookmarksEntry.getVisits(),
			newBookmarksEntry.getVisits());
		Assert.assertEquals(existingBookmarksEntry.getPriority(),
			newBookmarksEntry.getPriority());
		Assert.assertEquals(existingBookmarksEntry.getStatus(),
			newBookmarksEntry.getStatus());
		Assert.assertEquals(existingBookmarksEntry.getStatusByUserId(),
			newBookmarksEntry.getStatusByUserId());
		Assert.assertEquals(existingBookmarksEntry.getStatusByUserName(),
			newBookmarksEntry.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBookmarksEntry.getStatusDate()),
			Time.getShortTimestamp(newBookmarksEntry.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		BookmarksEntry existingBookmarksEntry = _persistence.fetchByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksEntry missingBookmarksEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBookmarksEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newBookmarksEntry.getEntryId()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BookmarksEntry existingBookmarksEntry = result.get(0);

		Assert.assertEquals(existingBookmarksEntry, newBookmarksEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				ServiceTestUtil.nextLong()));

		List<BookmarksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newBookmarksEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BookmarksEntry.class,
				BookmarksEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BookmarksEntry newBookmarksEntry = addBookmarksEntry();

		_persistence.clearCache();

		BookmarksEntryModelImpl existingBookmarksEntryModelImpl = (BookmarksEntryModelImpl)_persistence.findByPrimaryKey(newBookmarksEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingBookmarksEntryModelImpl.getUuid(),
				existingBookmarksEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingBookmarksEntryModelImpl.getGroupId(),
			existingBookmarksEntryModelImpl.getOriginalGroupId());
	}

	protected BookmarksEntry addBookmarksEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		BookmarksEntry bookmarksEntry = _persistence.create(pk);

		bookmarksEntry.setUuid(ServiceTestUtil.randomString());

		bookmarksEntry.setGroupId(ServiceTestUtil.nextLong());

		bookmarksEntry.setCompanyId(ServiceTestUtil.nextLong());

		bookmarksEntry.setUserId(ServiceTestUtil.nextLong());

		bookmarksEntry.setUserName(ServiceTestUtil.randomString());

		bookmarksEntry.setCreateDate(ServiceTestUtil.nextDate());

		bookmarksEntry.setModifiedDate(ServiceTestUtil.nextDate());

		bookmarksEntry.setResourceBlockId(ServiceTestUtil.nextLong());

		bookmarksEntry.setFolderId(ServiceTestUtil.nextLong());

		bookmarksEntry.setName(ServiceTestUtil.randomString());

		bookmarksEntry.setUrl(ServiceTestUtil.randomString());

		bookmarksEntry.setDescription(ServiceTestUtil.randomString());

		bookmarksEntry.setVisits(ServiceTestUtil.nextInt());

		bookmarksEntry.setPriority(ServiceTestUtil.nextInt());

		bookmarksEntry.setStatus(ServiceTestUtil.nextInt());

		bookmarksEntry.setStatusByUserId(ServiceTestUtil.nextLong());

		bookmarksEntry.setStatusByUserName(ServiceTestUtil.randomString());

		bookmarksEntry.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(bookmarksEntry);

		return bookmarksEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksEntryPersistenceTest.class);
	private BookmarksEntryPersistence _persistence = (BookmarksEntryPersistence)PortalBeanLocatorUtil.locate(BookmarksEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}