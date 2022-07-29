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

import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.base.MBSuspiciousActivityServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=mb",
		"json.web.service.context.path=MBSuspiciousActivity"
	},
	service = AopService.class
)
public class MBSuspiciousActivityServiceImpl
	extends MBSuspiciousActivityServiceBaseImpl {

	@Override
	public MBSuspiciousActivity addOrUpdateMessageSuspiciousActivity(
			long messageId, String reason)
		throws PortalException {

		return mbSuspiciousActivityLocalService.
			addOrUpdateMessageSuspiciousActivity(
				getUserId(),messageId, reason);
	}


	@Override
	public MBSuspiciousActivity addOrUpdateThreadSuspiciousActivity(
			String reason, long threadId)
		throws PortalException {

		return mbSuspiciousActivityLocalService.
			addOrUpdateThreadSuspiciousActivity(
				getUserId(),reason, threadId);
	}

	@Override
	public MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws PortalException {

		return mbSuspiciousActivityLocalService.deleteSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return mbSuspiciousActivityLocalService.getMessageSuspiciousActivities(
			messageId);
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(long suspiciousActivityId)
		throws PortalException {

		return mbSuspiciousActivityLocalService.getSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return mbSuspiciousActivityLocalService.getThreadSuspiciousActivities(
			threadId);
	}

	@Override
	public MBSuspiciousActivity updateValidated(long suspiciousActivityId)
		throws PortalException {

		return mbSuspiciousActivityLocalService.updateValidated(
			suspiciousActivityId);
	}

}