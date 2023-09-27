/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.demo.data.creator.internal;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.demo.data.creator.MBMessageDemoDataCreator;
import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
public abstract class BaseMBMessageDemoDataCreator
	implements MBMessageDemoDataCreator {

	public MBMessage createMessage(
			long userId, long groupId, long categoryId, long parentMessageId,
			String title, String content)
		throws IOException, PortalException {

		long threadId = 0;

		if (parentMessageId != MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			MBMessage parentMessage = mbMessageLocalService.getMessage(
				parentMessageId);

			threadId = parentMessage.getThreadId();
		}

		User user = userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		MBMessage mbMessage = mbMessageLocalService.addMessage(
			user.getUserId(), user.getFullName(), groupId, categoryId, threadId,
			parentMessageId, title, content, MBMessageConstants.DEFAULT_FORMAT,
			Collections.emptyList(), false, 0.0, false, serviceContext);

		messageIds.add(mbMessage.getMessageId());

		return mbMessage;
	}

	@Override
	public void delete() throws PortalException {
		for (long messageId : messageIds) {
			try {
				mbMessageLocalService.deleteMessage(messageId);
			}
			catch (NoSuchMessageException noSuchMessageException) {
				if (_log.isWarnEnabled()) {
					_log.warn(noSuchMessageException);
				}
			}

			messageIds.remove(messageId);
		}
	}

	@Reference
	protected MBMessageLocalService mbMessageLocalService;

	protected final List<Long> messageIds = new CopyOnWriteArrayList<>();

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMBMessageDemoDataCreator.class);

}