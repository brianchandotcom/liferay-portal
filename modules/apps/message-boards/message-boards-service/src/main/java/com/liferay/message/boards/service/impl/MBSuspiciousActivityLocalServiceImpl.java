/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.exception.NoSuchSuspiciousActivityException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.base.MBSuspiciousActivityLocalServiceBaseImpl;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
import com.liferay.message.boards.service.persistence.MBSuspiciousActivityPersistence;
import com.liferay.message.boards.service.persistence.MBThreadPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBSuspiciousActivity",
	service = AopService.class
)
public class MBSuspiciousActivityLocalServiceImpl
	extends MBSuspiciousActivityLocalServiceBaseImpl {

	@Override
	public MBSuspiciousActivity addOrUpdateSuspiciousActivity(
			long userId, long messageId, String description, String type)
		throws PortalException {

		MBSuspiciousActivity suspiciousActivity =
			mbSuspiciousActivityPersistence.fetchByU_M(userId, messageId);

		if (suspiciousActivity == null) {
			long suspiciousActivityId = counterLocalService.increment();

			suspiciousActivity = mbSuspiciousActivityPersistence.create(
				suspiciousActivityId);

			MBMessage message = _mbMessagePersistence.findByPrimaryKey(
				messageId);

			MBThread thread = _mbThreadPersistence.findByPrimaryKey(
				message.getThreadId());

			suspiciousActivity.setGroupId(thread.getGroupId());

			User user = _userLocalService.getUser(userId);

			suspiciousActivity.setCompanyId(user.getCompanyId());
			suspiciousActivity.setUserId(user.getUserId());
			suspiciousActivity.setUserName(user.getFullName());

			suspiciousActivity.setMessageId(messageId);
			suspiciousActivity.setThreadId(thread.getThreadId());
		}

		suspiciousActivity.setDescription(description);
		suspiciousActivity.setType(type);

		return mbSuspiciousActivityLocalService.updateMBSuspiciousActivity(
			suspiciousActivity);
	}

	@Override
	public MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return mbSuspiciousActivityPersistence.remove(suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return mbSuspiciousActivityPersistence.findByMessageId(messageId);
	}


	@Override
	public MBSuspiciousActivity getSuspiciousActivity(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return mbSuspiciousActivityPersistence.findByPrimaryKey(
			suspiciousActivityId);
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(
			long userId, long messageId)
		throws NoSuchSuspiciousActivityException {

		return mbSuspiciousActivityPersistence.findByU_M(userId, messageId);
	}

	@Override
	public int getSuspiciousActivityCount() {
		return mbSuspiciousActivityPersistence.countAll();
	}

	@Override
	public List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return mbSuspiciousActivityPersistence.findByThreadId(threadId);
	}
	@Override
	public MBSuspiciousActivity updateValidated(
			long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity suspiciousActivity = getSuspiciousActivity(
			suspiciousActivityId);

		suspiciousActivity.setValidated(!suspiciousActivity.isValidated());

		return mbSuspiciousActivityLocalService.updateMBSuspiciousActivity(
			suspiciousActivity);
	}

	@Reference
	private MBMessagePersistence _mbMessagePersistence;

	@Reference
	private MBThreadPersistence _mbThreadPersistence;

	@Reference
	private UserLocalService _userLocalService;

}