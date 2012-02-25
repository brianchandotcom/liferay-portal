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

import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessagePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBMessagePersistence)PortalBeanLocatorUtil.locate(MBMessagePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBMessage mbMessage = _persistence.create(pk);

		assertNotNull(mbMessage);

		assertEquals(mbMessage.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBMessage newMBMessage = addMBMessage();

		_persistence.remove(newMBMessage);

		MBMessage existingMBMessage = _persistence.fetchByPrimaryKey(newMBMessage.getPrimaryKey());

		assertNull(existingMBMessage);
	}

	public void testUpdateNew() throws Exception {
		addMBMessage();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBMessage newMBMessage = _persistence.create(pk);

		newMBMessage.setUuid(randomString());

		newMBMessage.setGroupId(nextLong());

		newMBMessage.setCompanyId(nextLong());

		newMBMessage.setUserId(nextLong());

		newMBMessage.setUserName(randomString());

		newMBMessage.setCreateDate(nextDate());

		newMBMessage.setModifiedDate(nextDate());

		newMBMessage.setClassNameId(nextLong());

		newMBMessage.setClassPK(nextLong());

		newMBMessage.setCategoryId(nextLong());

		newMBMessage.setThreadId(nextLong());

		newMBMessage.setRootMessageId(nextLong());

		newMBMessage.setParentMessageId(nextLong());

		newMBMessage.setSubject(randomString());

		newMBMessage.setBody(randomString());

		newMBMessage.setFormat(randomString());

		newMBMessage.setAttachments(randomBoolean());

		newMBMessage.setAnonymous(randomBoolean());

		newMBMessage.setPriority(nextDouble());

		newMBMessage.setAllowPingbacks(randomBoolean());

		newMBMessage.setAnswer(randomBoolean());

		newMBMessage.setStatus(nextInt());

		newMBMessage.setStatusByUserId(nextLong());

		newMBMessage.setStatusByUserName(randomString());

		newMBMessage.setStatusDate(nextDate());

		_persistence.update(newMBMessage, false);

		MBMessage existingMBMessage = _persistence.findByPrimaryKey(newMBMessage.getPrimaryKey());

		assertEquals(existingMBMessage.getUuid(), newMBMessage.getUuid());
		assertEquals(existingMBMessage.getMessageId(),
			newMBMessage.getMessageId());
		assertEquals(existingMBMessage.getGroupId(), newMBMessage.getGroupId());
		assertEquals(existingMBMessage.getCompanyId(),
			newMBMessage.getCompanyId());
		assertEquals(existingMBMessage.getUserId(), newMBMessage.getUserId());
		assertEquals(existingMBMessage.getUserName(), newMBMessage.getUserName());
		assertEquals(Time.getShortTimestamp(existingMBMessage.getCreateDate()),
			Time.getShortTimestamp(newMBMessage.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingMBMessage.getModifiedDate()),
			Time.getShortTimestamp(newMBMessage.getModifiedDate()));
		assertEquals(existingMBMessage.getClassNameId(),
			newMBMessage.getClassNameId());
		assertEquals(existingMBMessage.getClassPK(), newMBMessage.getClassPK());
		assertEquals(existingMBMessage.getCategoryId(),
			newMBMessage.getCategoryId());
		assertEquals(existingMBMessage.getThreadId(), newMBMessage.getThreadId());
		assertEquals(existingMBMessage.getRootMessageId(),
			newMBMessage.getRootMessageId());
		assertEquals(existingMBMessage.getParentMessageId(),
			newMBMessage.getParentMessageId());
		assertEquals(existingMBMessage.getSubject(), newMBMessage.getSubject());
		assertEquals(existingMBMessage.getBody(), newMBMessage.getBody());
		assertEquals(existingMBMessage.getFormat(), newMBMessage.getFormat());
		assertEquals(existingMBMessage.getAttachments(),
			newMBMessage.getAttachments());
		assertEquals(existingMBMessage.getAnonymous(),
			newMBMessage.getAnonymous());
		assertEquals(existingMBMessage.getPriority(), newMBMessage.getPriority());
		assertEquals(existingMBMessage.getAllowPingbacks(),
			newMBMessage.getAllowPingbacks());
		assertEquals(existingMBMessage.getAnswer(), newMBMessage.getAnswer());
		assertEquals(existingMBMessage.getStatus(), newMBMessage.getStatus());
		assertEquals(existingMBMessage.getStatusByUserId(),
			newMBMessage.getStatusByUserId());
		assertEquals(existingMBMessage.getStatusByUserName(),
			newMBMessage.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingMBMessage.getStatusDate()),
			Time.getShortTimestamp(newMBMessage.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBMessage newMBMessage = addMBMessage();

		MBMessage existingMBMessage = _persistence.findByPrimaryKey(newMBMessage.getPrimaryKey());

		assertEquals(existingMBMessage, newMBMessage);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchMessageException");
		}
		catch (NoSuchMessageException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBMessage newMBMessage = addMBMessage();

		MBMessage existingMBMessage = _persistence.fetchByPrimaryKey(newMBMessage.getPrimaryKey());

		assertEquals(existingMBMessage, newMBMessage);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBMessage missingMBMessage = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBMessage);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBMessage newMBMessage = addMBMessage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMessage.class,
				MBMessage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("messageId",
				newMBMessage.getMessageId()));

		List<MBMessage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBMessage existingMBMessage = result.get(0);

		assertEquals(existingMBMessage, newMBMessage);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMessage.class,
				MBMessage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("messageId", nextLong()));

		List<MBMessage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBMessage newMBMessage = addMBMessage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMessage.class,
				MBMessage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("messageId"));

		Object newMessageId = newMBMessage.getMessageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("messageId",
				new Object[] { newMessageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingMessageId = result.get(0);

		assertEquals(existingMessageId, newMessageId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMessage.class,
				MBMessage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("messageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("messageId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBMessage newMBMessage = addMBMessage();

		_persistence.clearCache();

		MBMessageModelImpl existingMBMessageModelImpl = (MBMessageModelImpl)_persistence.findByPrimaryKey(newMBMessage.getPrimaryKey());

		assertTrue(Validator.equals(existingMBMessageModelImpl.getUuid(),
				existingMBMessageModelImpl.getOriginalUuid()));
		assertEquals(existingMBMessageModelImpl.getGroupId(),
			existingMBMessageModelImpl.getOriginalGroupId());
	}

	protected MBMessage addMBMessage() throws Exception {
		long pk = nextLong();

		MBMessage mbMessage = _persistence.create(pk);

		mbMessage.setUuid(randomString());

		mbMessage.setGroupId(nextLong());

		mbMessage.setCompanyId(nextLong());

		mbMessage.setUserId(nextLong());

		mbMessage.setUserName(randomString());

		mbMessage.setCreateDate(nextDate());

		mbMessage.setModifiedDate(nextDate());

		mbMessage.setClassNameId(nextLong());

		mbMessage.setClassPK(nextLong());

		mbMessage.setCategoryId(nextLong());

		mbMessage.setThreadId(nextLong());

		mbMessage.setRootMessageId(nextLong());

		mbMessage.setParentMessageId(nextLong());

		mbMessage.setSubject(randomString());

		mbMessage.setBody(randomString());

		mbMessage.setFormat(randomString());

		mbMessage.setAttachments(randomBoolean());

		mbMessage.setAnonymous(randomBoolean());

		mbMessage.setPriority(nextDouble());

		mbMessage.setAllowPingbacks(randomBoolean());

		mbMessage.setAnswer(randomBoolean());

		mbMessage.setStatus(nextInt());

		mbMessage.setStatusByUserId(nextLong());

		mbMessage.setStatusByUserName(randomString());

		mbMessage.setStatusDate(nextDate());

		_persistence.update(mbMessage, false);

		return mbMessage;
	}

	private MBMessagePersistence _persistence;
}