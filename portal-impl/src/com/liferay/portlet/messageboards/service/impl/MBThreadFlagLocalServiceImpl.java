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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.base.MBThreadFlagLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBThreadFlagLocalServiceImpl
	extends MBThreadFlagLocalServiceBaseImpl {

	@Override
	public MBThreadFlag addThreadFlag(
			long userId, MBThread thread, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return null;
		}

		long threadId = thread.getThreadId();

		MBThreadFlag threadFlag = mbThreadFlagPersistence.fetchByU_T(
			userId, threadId);

		if (threadFlag == null) {
			long threadFlagId = counterLocalService.increment();

			threadFlag = mbThreadFlagPersistence.create(threadFlagId);

			threadFlag.setUuid(serviceContext.getUuid());
			threadFlag.setGroupId(thread.getGroupId());
			threadFlag.setCompanyId(user.getCompanyId());
			threadFlag.setUserId(userId);
			threadFlag.setUserName(user.getFullName());
			threadFlag.setModifiedDate(
				serviceContext.getModifiedDate(thread.getLastPostDate()));
			threadFlag.setThreadId(threadId);

			try {
				mbThreadFlagPersistence.update(threadFlag);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {userId=" + userId + ", threadId=" +
							threadId + "}");
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

			mbThreadFlagPersistence.update(threadFlag);
		}

		return threadFlag;
	}

	@Override
	public void deleteThreadFlag(long threadFlagId) throws PortalException {
		MBThreadFlag threadFlag = mbThreadFlagPersistence.findByPrimaryKey(
			threadFlagId);

		mbThreadFlagLocalService.deleteThreadFlag(threadFlag);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteThreadFlag(MBThreadFlag threadFlag) {
		mbThreadFlagPersistence.remove(threadFlag);
	}

	@Override
	public void deleteThreadFlagsByThreadId(long threadId) {
		List<MBThreadFlag> threadFlags = mbThreadFlagPersistence.findByThreadId(
			threadId);

		for (MBThreadFlag threadFlag : threadFlags) {
			mbThreadFlagLocalService.deleteThreadFlag(threadFlag);
		}
	}

	@Override
	public void deleteThreadFlagsByUserId(long userId) {
		List<MBThreadFlag> threadFlags = mbThreadFlagPersistence.findByUserId(
			userId);

		for (MBThreadFlag threadFlag : threadFlags) {
			mbThreadFlagLocalService.deleteThreadFlag(threadFlag);
		}
	}

	@Override
	public MBThreadFlag getThreadFlag(long userId, MBThread thread)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return null;
		}

		return mbThreadFlagPersistence.fetchByU_T(userId, thread.getThreadId());
	}

	@Override
	public boolean hasThreadFlag(long userId, MBThread thread)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return true;
		}

		MBThreadFlag threadFlag = mbThreadFlagPersistence.fetchByU_T(
			userId, thread.getThreadId());

		if ((threadFlag != null) &&
			DateUtil.equals(
				threadFlag.getModifiedDate(), thread.getLastPostDate(), true)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBThreadFlagLocalServiceImpl.class);

}