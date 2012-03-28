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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalFeedPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalFeedPersistence)PortalBeanLocatorUtil.locate(JournalFeedPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalFeed journalFeed = _persistence.create(pk);

		assertNotNull(journalFeed);

		assertEquals(journalFeed.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		_persistence.remove(newJournalFeed);

		JournalFeed existingJournalFeed = _persistence.fetchByPrimaryKey(newJournalFeed.getPrimaryKey());

		assertNull(existingJournalFeed);
	}

	public void testUpdateNew() throws Exception {
		addJournalFeed();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalFeed newJournalFeed = _persistence.create(pk);

		newJournalFeed.setUuid(randomString());

		newJournalFeed.setGroupId(nextLong());

		newJournalFeed.setCompanyId(nextLong());

		newJournalFeed.setUserId(nextLong());

		newJournalFeed.setUserName(randomString());

		newJournalFeed.setCreateDate(nextDate());

		newJournalFeed.setModifiedDate(nextDate());

		newJournalFeed.setFeedId(randomString());

		newJournalFeed.setName(randomString());

		newJournalFeed.setDescription(randomString());

		newJournalFeed.setType(randomString());

		newJournalFeed.setStructureId(randomString());

		newJournalFeed.setTemplateId(randomString());

		newJournalFeed.setRendererTemplateId(randomString());

		newJournalFeed.setDelta(nextInt());

		newJournalFeed.setOrderByCol(randomString());

		newJournalFeed.setOrderByType(randomString());

		newJournalFeed.setTargetLayoutFriendlyUrl(randomString());

		newJournalFeed.setTargetPortletId(randomString());

		newJournalFeed.setContentField(randomString());

		newJournalFeed.setFeedType(randomString());

		newJournalFeed.setFeedVersion(nextDouble());

		_persistence.update(newJournalFeed, false);

		JournalFeed existingJournalFeed = _persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		assertEquals(existingJournalFeed.getUuid(), newJournalFeed.getUuid());
		assertEquals(existingJournalFeed.getId(), newJournalFeed.getId());
		assertEquals(existingJournalFeed.getGroupId(),
			newJournalFeed.getGroupId());
		assertEquals(existingJournalFeed.getCompanyId(),
			newJournalFeed.getCompanyId());
		assertEquals(existingJournalFeed.getUserId(), newJournalFeed.getUserId());
		assertEquals(existingJournalFeed.getUserName(),
			newJournalFeed.getUserName());
		assertEquals(Time.getShortTimestamp(existingJournalFeed.getCreateDate()),
			Time.getShortTimestamp(newJournalFeed.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalFeed.getModifiedDate()),
			Time.getShortTimestamp(newJournalFeed.getModifiedDate()));
		assertEquals(existingJournalFeed.getFeedId(), newJournalFeed.getFeedId());
		assertEquals(existingJournalFeed.getName(), newJournalFeed.getName());
		assertEquals(existingJournalFeed.getDescription(),
			newJournalFeed.getDescription());
		assertEquals(existingJournalFeed.getType(), newJournalFeed.getType());
		assertEquals(existingJournalFeed.getStructureId(),
			newJournalFeed.getStructureId());
		assertEquals(existingJournalFeed.getTemplateId(),
			newJournalFeed.getTemplateId());
		assertEquals(existingJournalFeed.getRendererTemplateId(),
			newJournalFeed.getRendererTemplateId());
		assertEquals(existingJournalFeed.getDelta(), newJournalFeed.getDelta());
		assertEquals(existingJournalFeed.getOrderByCol(),
			newJournalFeed.getOrderByCol());
		assertEquals(existingJournalFeed.getOrderByType(),
			newJournalFeed.getOrderByType());
		assertEquals(existingJournalFeed.getTargetLayoutFriendlyUrl(),
			newJournalFeed.getTargetLayoutFriendlyUrl());
		assertEquals(existingJournalFeed.getTargetPortletId(),
			newJournalFeed.getTargetPortletId());
		assertEquals(existingJournalFeed.getContentField(),
			newJournalFeed.getContentField());
		assertEquals(existingJournalFeed.getFeedType(),
			newJournalFeed.getFeedType());
		assertEquals(existingJournalFeed.getFeedVersion(),
			newJournalFeed.getFeedVersion());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		JournalFeed existingJournalFeed = _persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		assertEquals(existingJournalFeed, newJournalFeed);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFeedException");
		}
		catch (NoSuchFeedException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		JournalFeed existingJournalFeed = _persistence.fetchByPrimaryKey(newJournalFeed.getPrimaryKey());

		assertEquals(existingJournalFeed, newJournalFeed);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalFeed missingJournalFeed = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalFeed);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", newJournalFeed.getId()));

		List<JournalFeed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalFeed existingJournalFeed = result.get(0);

		assertEquals(existingJournalFeed, newJournalFeed);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<JournalFeed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalFeed.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingId = result.get(0);

		assertEquals(existingId, newId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalFeed newJournalFeed = addJournalFeed();

		_persistence.clearCache();

		JournalFeedModelImpl existingJournalFeedModelImpl = (JournalFeedModelImpl)_persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		assertTrue(Validator.equals(existingJournalFeedModelImpl.getUuid(),
				existingJournalFeedModelImpl.getOriginalUuid()));
		assertEquals(existingJournalFeedModelImpl.getGroupId(),
			existingJournalFeedModelImpl.getOriginalGroupId());

		assertEquals(existingJournalFeedModelImpl.getGroupId(),
			existingJournalFeedModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingJournalFeedModelImpl.getFeedId(),
				existingJournalFeedModelImpl.getOriginalFeedId()));
	}

	protected JournalFeed addJournalFeed() throws Exception {
		long pk = nextLong();

		JournalFeed journalFeed = _persistence.create(pk);

		journalFeed.setUuid(randomString());

		journalFeed.setGroupId(nextLong());

		journalFeed.setCompanyId(nextLong());

		journalFeed.setUserId(nextLong());

		journalFeed.setUserName(randomString());

		journalFeed.setCreateDate(nextDate());

		journalFeed.setModifiedDate(nextDate());

		journalFeed.setFeedId(randomString());

		journalFeed.setName(randomString());

		journalFeed.setDescription(randomString());

		journalFeed.setType(randomString());

		journalFeed.setStructureId(randomString());

		journalFeed.setTemplateId(randomString());

		journalFeed.setRendererTemplateId(randomString());

		journalFeed.setDelta(nextInt());

		journalFeed.setOrderByCol(randomString());

		journalFeed.setOrderByType(randomString());

		journalFeed.setTargetLayoutFriendlyUrl(randomString());

		journalFeed.setTargetPortletId(randomString());

		journalFeed.setContentField(randomString());

		journalFeed.setFeedType(randomString());

		journalFeed.setFeedVersion(nextDouble());

		_persistence.update(journalFeed, false);

		return journalFeed;
	}

	private JournalFeedPersistence _persistence;
}