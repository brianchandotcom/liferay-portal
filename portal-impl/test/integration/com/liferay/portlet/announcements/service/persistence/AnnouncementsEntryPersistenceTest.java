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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsEntryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry announcementsEntry = _persistence.create(pk);

		assertNotNull(announcementsEntry);

		assertEquals(announcementsEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		_persistence.remove(newAnnouncementsEntry);

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.fetchByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertNull(existingAnnouncementsEntry);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry newAnnouncementsEntry = _persistence.create(pk);

		newAnnouncementsEntry.setUuid(randomString());

		newAnnouncementsEntry.setCompanyId(nextLong());

		newAnnouncementsEntry.setUserId(nextLong());

		newAnnouncementsEntry.setUserName(randomString());

		newAnnouncementsEntry.setCreateDate(nextDate());

		newAnnouncementsEntry.setModifiedDate(nextDate());

		newAnnouncementsEntry.setClassNameId(nextLong());

		newAnnouncementsEntry.setClassPK(nextLong());

		newAnnouncementsEntry.setTitle(randomString());

		newAnnouncementsEntry.setContent(randomString());

		newAnnouncementsEntry.setUrl(randomString());

		newAnnouncementsEntry.setType(randomString());

		newAnnouncementsEntry.setDisplayDate(nextDate());

		newAnnouncementsEntry.setExpirationDate(nextDate());

		newAnnouncementsEntry.setPriority(nextInt());

		newAnnouncementsEntry.setAlert(randomBoolean());

		_persistence.update(newAnnouncementsEntry, false);

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.findByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry.getUuid(),
			newAnnouncementsEntry.getUuid());
		assertEquals(existingAnnouncementsEntry.getEntryId(),
			newAnnouncementsEntry.getEntryId());
		assertEquals(existingAnnouncementsEntry.getCompanyId(),
			newAnnouncementsEntry.getCompanyId());
		assertEquals(existingAnnouncementsEntry.getUserId(),
			newAnnouncementsEntry.getUserId());
		assertEquals(existingAnnouncementsEntry.getUserName(),
			newAnnouncementsEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getCreateDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getModifiedDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getModifiedDate()));
		assertEquals(existingAnnouncementsEntry.getClassNameId(),
			newAnnouncementsEntry.getClassNameId());
		assertEquals(existingAnnouncementsEntry.getClassPK(),
			newAnnouncementsEntry.getClassPK());
		assertEquals(existingAnnouncementsEntry.getTitle(),
			newAnnouncementsEntry.getTitle());
		assertEquals(existingAnnouncementsEntry.getContent(),
			newAnnouncementsEntry.getContent());
		assertEquals(existingAnnouncementsEntry.getUrl(),
			newAnnouncementsEntry.getUrl());
		assertEquals(existingAnnouncementsEntry.getType(),
			newAnnouncementsEntry.getType());
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getDisplayDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getDisplayDate()));
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getExpirationDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getExpirationDate()));
		assertEquals(existingAnnouncementsEntry.getPriority(),
			newAnnouncementsEntry.getPriority());
		assertEquals(existingAnnouncementsEntry.getAlert(),
			newAnnouncementsEntry.getAlert());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.findByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
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
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.fetchByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry missingAnnouncementsEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newAnnouncementsEntry.getEntryId()));

		List<AnnouncementsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AnnouncementsEntry existingAnnouncementsEntry = result.get(0);

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<AnnouncementsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newAnnouncementsEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		assertEquals(existingEntryId, newEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AnnouncementsEntry addAnnouncementsEntry()
		throws Exception {
		long pk = nextLong();

		AnnouncementsEntry announcementsEntry = _persistence.create(pk);

		announcementsEntry.setUuid(randomString());

		announcementsEntry.setCompanyId(nextLong());

		announcementsEntry.setUserId(nextLong());

		announcementsEntry.setUserName(randomString());

		announcementsEntry.setCreateDate(nextDate());

		announcementsEntry.setModifiedDate(nextDate());

		announcementsEntry.setClassNameId(nextLong());

		announcementsEntry.setClassPK(nextLong());

		announcementsEntry.setTitle(randomString());

		announcementsEntry.setContent(randomString());

		announcementsEntry.setUrl(randomString());

		announcementsEntry.setType(randomString());

		announcementsEntry.setDisplayDate(nextDate());

		announcementsEntry.setExpirationDate(nextDate());

		announcementsEntry.setPriority(nextInt());

		announcementsEntry.setAlert(randomBoolean());

		_persistence.update(announcementsEntry, false);

		return announcementsEntry;
	}

	private AnnouncementsEntryPersistence _persistence;
}