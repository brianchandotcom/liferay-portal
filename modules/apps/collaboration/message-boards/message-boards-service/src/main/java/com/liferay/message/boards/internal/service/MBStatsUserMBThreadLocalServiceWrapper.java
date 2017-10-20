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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceWrapper;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class MBStatsUserMBThreadLocalServiceWrapper
	extends MBThreadLocalServiceWrapper {

	public MBStatsUserMBThreadLocalServiceWrapper() {
		super(null);
	}

	public MBStatsUserMBThreadLocalServiceWrapper(
		MBThreadLocalService mbThreadLocalService) {

		super(mbThreadLocalService);
	}

	@Override
	public void deleteThread(MBThread thread) throws PortalException {
		List<MBMessage> messages = _mbMessageLocalService.getThreadMessages(
			thread.getGroupId(), WorkflowConstants.STATUS_ANY);

		super.deleteThread(thread);

		for (MBMessage message : messages) {
			if (!message.isDiscussion()) {
				_mbStatsUserLocalService.updateStatsUser(
					message.getGroupId(), message.getUserId());
			}
		}
	}

	@Override
	public void moveDependentsToTrash(
			long groupId, long threadId, long trashEntryId)
		throws PortalException {

		List<MBMessage> messages = _mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY);

		super.moveDependentsToTrash(groupId, threadId, trashEntryId);

		Stream<MBMessage> stream = messages.stream();

		stream.filter(
			message -> !message.isDiscussion()
		).mapToLong(
			message -> message.getUserId()
		).distinct(
		).forEach(
			userId -> _mbStatsUserLocalService.updateStatsUser(groupId, userId)
		);
	}

	@Override
	public void restoreDependentsFromTrash(long groupId, long threadId)
		throws PortalException {

		List<MBMessage> messages = _mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY);

		super.restoreDependentsFromTrash(groupId, threadId);

		Stream<MBMessage> stream = messages.stream();

		stream.filter(
			mbMessage -> !mbMessage.isDiscussion()
		).mapToLong(
			message -> message.getUserId()
		).distinct(
		).forEach(
			userId -> _mbStatsUserLocalService.updateStatsUser(groupId, userId)
		);
	}

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBStatsUserLocalService _mbStatsUserLocalService;

}