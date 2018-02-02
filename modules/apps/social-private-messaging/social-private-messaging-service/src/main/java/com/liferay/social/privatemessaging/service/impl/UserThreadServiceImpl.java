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

package com.liferay.social.privatemessaging.service.impl;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.util.comparator.MessageCreateDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.service.base.UserThreadServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class UserThreadServiceImpl extends UserThreadServiceBaseImpl {

	@Override
	public MBMessage getLastThreadMessage(long mbThreadId)
		throws PortalException {

		List<MBMessage> mbMessages = getThreadMessages(
			mbThreadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, false);

		MBMessage lastMBMessage = mbMessages.get(0);

		return lastMBMessage;
	}

	@Override
	public List<MBMessage> getThreadMessages(
			long mbThreadId, int start, int end, boolean ascending)
		throws PortalException {

		UserThread userThread = userThreadLocalService.getUserThread(
			getUserId(), mbThreadId);

		MBMessage topMBMessage = mbMessageLocalService.getMBMessage(
			userThread.getTopMBMessageId());

		List<MBMessage> mbMessages = mbMessageLocalService.getThreadMessages(
			mbThreadId, WorkflowConstants.STATUS_ANY,
			new MessageCreateDateComparator(ascending));

		List<MBMessage> filteredMBMessages = new ArrayList<>();

		for (MBMessage mbMessage : mbMessages) {
			int compareTo = DateUtil.compareTo(
				topMBMessage.getCreateDate(), mbMessage.getCreateDate());

			if (compareTo <= 0) {
				filteredMBMessages.add(mbMessage);
			}
		}

		if (filteredMBMessages.isEmpty()) {
			return filteredMBMessages;
		}
		else if ((start == QueryUtil.ALL_POS) || (end == QueryUtil.ALL_POS)) {
			return filteredMBMessages;
		}
		else if (end > filteredMBMessages.size()) {
			end = filteredMBMessages.size();
		}

		return filteredMBMessages.subList(start, end);
	}

	@Override
	public int getThreadMessagesCount(long mbThreadId) throws PortalException {
		List<MBMessage> mbMessages = getThreadMessages(
			mbThreadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, true);

		return mbMessages.size();
	}

	@Override
	public List<UserThread> getUserUserThreads(boolean deleted)
		throws PrincipalException {

		return userThreadLocalService.getUserUserThreads(getUserId(), deleted);
	}

	@ServiceReference(type = MBMessageLocalService.class)
	protected MBMessageLocalService mbMessageLocalService;

}