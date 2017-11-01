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
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class MBStatsUserMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public MBStatsUserMBMessageLocalServiceWrapper() {
		super(null);
	}

	public MBStatsUserMBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {

		super(mbMessageLocalService);
	}

	@Override
	public MBMessage deleteMessage(MBMessage message) throws PortalException {
		MBMessage curMessage = super.deleteMessage(message);

		if (!curMessage.isDiscussion()) {
			_mbStatsUserLocalService.updateStatsUser(
				message.getGroupId(), message.getUserId());
		}

		return curMessage;
	}

	@Override
	public MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage curMessage = super.updateMessage(
			userId, messageId, subject, body, inputStreamOVPs, existingFiles,
			priority, allowPingbacks, serviceContext);

		if ((serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) &&
			!curMessage.isDiscussion()) {

			_mbStatsUserLocalService.updateStatsUser(
				curMessage.getGroupId(), userId, curMessage.getModifiedDate());
		}

		return curMessage;
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		MBMessage curMessage = super.updateStatus(
			userId, messageId, status, serviceContext, workflowContext);

		Date now = new Date();

		if (!curMessage.isDiscussion()) {
			_mbStatsUserLocalService.updateStatsUser(
				curMessage.getGroupId(), userId,
				serviceContext.getModifiedDate(now));
		}

		return curMessage;
	}

	@Reference
	private MBStatsUserLocalService _mbStatsUserLocalService;

}