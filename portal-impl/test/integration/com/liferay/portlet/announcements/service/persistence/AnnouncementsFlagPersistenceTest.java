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
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsFlagPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(AnnouncementsFlagPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		assertNotNull(announcementsFlag);

		assertEquals(announcementsFlag.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		_persistence.remove(newAnnouncementsFlag);

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertNull(existingAnnouncementsFlag);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsFlag();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag newAnnouncementsFlag = _persistence.create(pk);

		newAnnouncementsFlag.setUserId(nextLong());

		newAnnouncementsFlag.setCreateDate(nextDate());

		newAnnouncementsFlag.setEntryId(nextLong());

		newAnnouncementsFlag.setValue(nextInt());

		_persistence.update(newAnnouncementsFlag, false);

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag.getFlagId(),
			newAnnouncementsFlag.getFlagId());
		assertEquals(existingAnnouncementsFlag.getUserId(),
			newAnnouncementsFlag.getUserId());
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsFlag.getCreateDate()),
			Time.getShortTimestamp(newAnnouncementsFlag.getCreateDate()));
		assertEquals(existingAnnouncementsFlag.getEntryId(),
			newAnnouncementsFlag.getEntryId());
		assertEquals(existingAnnouncementsFlag.getValue(),
			newAnnouncementsFlag.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFlagException");
		}
		catch (NoSuchFlagException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag missingAnnouncementsFlag = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsFlag);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				AnnouncementsFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("flagId",
				newAnnouncementsFlag.getFlagId()));

		List<AnnouncementsFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AnnouncementsFlag existingAnnouncementsFlag = result.get(0);

		assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				AnnouncementsFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("flagId", nextLong()));

		List<AnnouncementsFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				AnnouncementsFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("flagId"));

		Object newFlagId = newAnnouncementsFlag.getFlagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("flagId",
				new Object[] { newFlagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFlagId = result.get(0);

		assertEquals(existingFlagId, newFlagId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				AnnouncementsFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("flagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("flagId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		_persistence.clearCache();

		AnnouncementsFlagModelImpl existingAnnouncementsFlagModelImpl = (AnnouncementsFlagModelImpl)_persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlagModelImpl.getUserId(),
			existingAnnouncementsFlagModelImpl.getOriginalUserId());
		assertEquals(existingAnnouncementsFlagModelImpl.getEntryId(),
			existingAnnouncementsFlagModelImpl.getOriginalEntryId());
		assertEquals(existingAnnouncementsFlagModelImpl.getValue(),
			existingAnnouncementsFlagModelImpl.getOriginalValue());
	}

	protected AnnouncementsFlag addAnnouncementsFlag()
		throws Exception {
		long pk = nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		announcementsFlag.setUserId(nextLong());

		announcementsFlag.setCreateDate(nextDate());

		announcementsFlag.setEntryId(nextLong());

		announcementsFlag.setValue(nextInt());

		_persistence.update(announcementsFlag, false);

		return announcementsFlag;
	}

	private AnnouncementsFlagPersistence _persistence;
}