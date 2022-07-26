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

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.base.MBSuspiciousActivityLocalServiceBaseImpl;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
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
	public MBSuspiciousActivity addOrUpdateSuspiciousActivityByMessage(
		 long messageId, String reason, long userId)
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

		suspiciousActivity.setReason(reason);

		return mbSuspiciousActivityLocalService.updateMBSuspiciousActivity(
			suspiciousActivity);
	}


	@Override
	public MBSuspiciousActivity addOrUpdateSuspiciousActivityByThread(
		String reason, long threadId, long userId)
		throws PortalException {

		MBSuspiciousActivity suspiciousActivity =
			mbSuspiciousActivityPersistence.fetchByU_T(userId, threadId);

		if (suspiciousActivity == null) {
			long suspiciousActivityId = counterLocalService.increment();

			suspiciousActivity = mbSuspiciousActivityPersistence.create(
				suspiciousActivityId);

			MBThread thread = _mbThreadPersistence.findByPrimaryKey(
				threadId);

			suspiciousActivity.setGroupId(thread.getGroupId());

			User user = _userLocalService.getUser(userId);

			suspiciousActivity.setCompanyId(user.getCompanyId());
			suspiciousActivity.setUserId(user.getUserId());
			suspiciousActivity.setUserName(user.getFullName());

			suspiciousActivity.setMessageId(0);
			suspiciousActivity.setThreadId(thread.getThreadId());
		}

		suspiciousActivity.setReason(reason);

		return mbSuspiciousActivityLocalService.updateMBSuspiciousActivity(
			suspiciousActivity);
	}

	@Override
	public MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws PortalException {

		return mbSuspiciousActivityPersistence.remove(suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return mbSuspiciousActivityPersistence.findByMessageId(messageId);
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(long suspiciousActivityId)
		throws PortalException {

		return mbSuspiciousActivityPersistence.findByPrimaryKey(
			suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return mbSuspiciousActivityPersistence.findByThreadId(threadId);
	}

	@Override
	public MBSuspiciousActivity updateValidated(long suspiciousActivityId)
		throws PortalException {

		MBSuspiciousActivity suspiciousActivity =
			mbSuspiciousActivityPersistence.findByPrimaryKey(
				suspiciousActivityId);

		suspiciousActivity.setValidated(!suspiciousActivity.isValidated());

		return mbSuspiciousActivityPersistence.update(suspiciousActivity);
	}

	@Reference
	private MBMessagePersistence _mbMessagePersistence;

	@Reference
	private MBThreadPersistence _mbThreadPersistence;

	@Reference
	private UserLocalService _userLocalService;

}