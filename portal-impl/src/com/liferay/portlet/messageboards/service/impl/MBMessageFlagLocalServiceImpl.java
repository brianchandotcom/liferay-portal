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
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBMessageFlagConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.base.MBMessageFlagLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageFlagLocalServiceImpl
	extends MBMessageFlagLocalServiceBaseImpl {

	public void addQuestionFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (!message.isRoot()) {
			return;
		}

		MBMessageFlag questionMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagConstants.QUESTION_FLAG);

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagConstants.ANSWER_FLAG);

		if ((questionMessageFlag == null) && (answerMessageFlag == null)) {
			long messageFlagId = counterLocalService.increment();

			questionMessageFlag = mbMessageFlagPersistence.create(
				messageFlagId);

			questionMessageFlag.setUserId(message.getUserId());
			questionMessageFlag.setModifiedDate(new Date());
			questionMessageFlag.setThreadId(message.getThreadId());
			questionMessageFlag.setMessageId(message.getMessageId());
			questionMessageFlag.setFlag(MBMessageFlagConstants.QUESTION_FLAG);

			mbMessageFlagPersistence.update(questionMessageFlag, false);
		}
	}

	public void addReadFlags(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return;
		}

		long messageId = thread.getRootMessageId();
		int flag = MBMessageFlagConstants.READ_FLAG;

		MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
			userId, messageId, flag);

		if (messageFlag == null) {
			long messageFlagId = counterLocalService.increment();

			messageFlag = mbMessageFlagPersistence.create(messageFlagId);

			messageFlag.setUserId(userId);
			messageFlag.setModifiedDate(thread.getLastPostDate());
			messageFlag.setThreadId(thread.getThreadId());
			messageFlag.setMessageId(messageId);
			messageFlag.setFlag(flag);

			try {
				mbMessageFlagPersistence.update(messageFlag, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {userId=" + userId +
							", messageId=" + messageId + ",flag=" + flag +
								"}");
				}

				messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
					userId, messageId, flag, false);

				if (messageFlag == null) {
					throw se;
				}
			}
		}
		else if (!DateUtil.equals(
					messageFlag.getModifiedDate(), thread.getLastPostDate(),
					true)) {

			messageFlag.setModifiedDate(thread.getLastPostDate());

			mbMessageFlagPersistence.update(messageFlag, false);
		}
	}

	public void deleteAnswerFlags(long threadId, long messageId)
		throws SystemException {

		List<MBMessageFlag> messageFlags = mbMessageFlagPersistence.findByM_F(
			messageId, MBMessageFlagConstants.ANSWER_FLAG);

		for (MBMessageFlag messageFlag : messageFlags) {
			deleteFlag(messageFlag);
		}

		List<MBMessage> messages = mbMessagePersistence.findByT_P(
			threadId, messageId);

		for (MBMessage message : messages) {
			deleteAnswerFlags(threadId, message.getMessageId());
		}
	}

	public void deleteFlag(long messageFlagId)
		throws PortalException,	SystemException {

		MBMessageFlag messageFlag =
			mbMessageFlagPersistence.findByPrimaryKey(messageFlagId);

		deleteFlag(messageFlag);
	}

	public void deleteFlag(MBMessageFlag messageFlag) throws SystemException {
		mbMessageFlagPersistence.remove(messageFlag);
	}

	public void deleteFlags(long userId) throws SystemException {
		List<MBMessageFlag> messageFlags =
			mbMessageFlagPersistence.findByUserId(userId);

		for (MBMessageFlag messageFlag : messageFlags) {
			deleteFlag(messageFlag);
		}
	}

	public void deleteFlags(long messageId, int flag) throws SystemException {
		List<MBMessageFlag> messageFlags = mbMessageFlagPersistence.findByM_F(
			messageId, flag);

		for (MBMessageFlag messageFlag : messageFlags) {
			deleteFlag(messageFlag);
		}
	}

	public void deleteQuestionAndAnswerFlags(long threadId)
		throws SystemException {

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			if (message.isRoot()) {
				List<MBMessageFlag> messageFlags =
					mbMessageFlagPersistence.findByM_F(
						message.getMessageId(),
						MBMessageFlagConstants.QUESTION_FLAG);

				for (MBMessageFlag messageFlag : messageFlags) {
					deleteFlag(messageFlag);
				}
			}

			List<MBMessageFlag> messageFlags =
				mbMessageFlagPersistence.findByM_F(
					message.getMessageId(), MBMessageFlagConstants.ANSWER_FLAG);

			for (MBMessageFlag messageFlag : messageFlags) {
				deleteFlag(messageFlag);
			}
		}
	}

	public void deleteThreadFlags(long threadId) throws SystemException {
		List<MBMessageFlag> messageFlags =
			mbMessageFlagPersistence.findByThreadId(threadId);

		for (MBMessageFlag messageFlag : messageFlags) {
			deleteFlag(messageFlag);
		}
	}

	public MBMessageFlag getReadFlag(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return null;
		}

		return mbMessageFlagPersistence.fetchByU_M_F(
			userId, thread.getRootMessageId(),
			MBMessageFlagConstants.READ_FLAG);
	}

	public boolean hasAnswerFlag(long messageId) throws SystemException {
		int count = mbMessageFlagPersistence.countByM_F(
			messageId, MBMessageFlagConstants.ANSWER_FLAG);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasQuestionFlag(long messageId) throws SystemException {
		int count = mbMessageFlagPersistence.countByM_F(
			messageId, MBMessageFlagConstants.QUESTION_FLAG);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasReadFlag(long userId, MBThread thread)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return true;
		}

		MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
			userId, thread.getRootMessageId(),
			MBMessageFlagConstants.READ_FLAG);

		if ((messageFlag != null) &&
			(DateUtil.equals(
				messageFlag.getModifiedDate(), thread.getLastPostDate(),
				true))) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBMessageFlagLocalServiceImpl.class);

}