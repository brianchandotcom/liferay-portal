/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.base.MBThreadFlagLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBThreadFlagLocalServiceImpl
	extends MBThreadFlagLocalServiceBaseImpl {

	public void addFlag(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return;
		}

		long threadId = thread.getThreadId();

		MBThreadFlag threadFlag = mbThreadFlagPersistence.fetchByU_T(
			userId, threadId);

		if (threadFlag == null) {
			long messageFlagId = counterLocalService.increment();

			threadFlag = mbThreadFlagPersistence.create(messageFlagId);

			threadFlag.setUserId(userId);
			threadFlag.setModifiedDate(thread.getLastPostDate());
			threadFlag.setThreadId(threadId);

			try {
				mbThreadFlagPersistence.update(threadFlag, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {userId=" + userId +
							", threadId=" + threadId + "}");
				}

				threadFlag = mbThreadFlagPersistence.fetchByU_T(
					userId, threadId, false);

				if (threadFlag == null) {
					throw se;
				}
			}
		}
		else if (!DateUtil.equals(
					threadFlag.getModifiedDate(), thread.getLastPostDate(),
					true)) {

			threadFlag.setModifiedDate(thread.getLastPostDate());

			mbThreadFlagPersistence.update(threadFlag, false);
		}
	}

	public void deleteFlag(long threadFlagId)
		throws PortalException,	SystemException {

		MBThreadFlag messageFlag =
			mbThreadFlagPersistence.findByPrimaryKey(threadFlagId);

		deleteFlag(messageFlag);
	}

	public void deleteFlag(MBThreadFlag threadFlag) throws SystemException {
		mbThreadFlagPersistence.remove(threadFlag);
	}

	public void deleteFlagsByUserId(long userId) throws SystemException {
		mbThreadFlagPersistence.removeByUserId(userId);
	}

	public void deleteFlagsByThreadId(long threadId) throws SystemException {
		mbThreadFlagPersistence.removeByThreadId(threadId);
	}

	public MBThreadFlag getFlag(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return null;
		}

		return mbThreadFlagPersistence.fetchByU_T(
			userId, thread.getThreadId());
	}

	public boolean hasFlag(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return true;
		}

		MBThreadFlag threadFlag = mbThreadFlagPersistence.fetchByU_T(
			userId, thread.getThreadId());

		if ((threadFlag != null) &&
			(DateUtil.equals(
				threadFlag.getModifiedDate(), thread.getLastPostDate(),
				true))) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBThreadFlagLocalServiceImpl.class);

}