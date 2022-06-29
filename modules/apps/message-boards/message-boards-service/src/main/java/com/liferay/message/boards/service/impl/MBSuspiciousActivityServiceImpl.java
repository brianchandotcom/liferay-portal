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
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.MBSuspiciousActivityLocalService;
import com.liferay.message.boards.service.base.MBSuspiciousActivityServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	public MBSuspiciousActivity addOrUpdateSuspiciousActivity(
			long userId, long messageId, String description, String type)
		throws PortalException {

		return _mbSuspiciousActivityLocalService.addOrUpdateSuspiciousActivity(
			userId, messageId, description, type);
	}

	@Override
	public MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.deleteSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return _mbSuspiciousActivityLocalService.getMessageSuspiciousActivities(
			messageId);
	}

	@Override
	public List<MBSuspiciousActivity> getSuspiciousActivities() {
		return _mbSuspiciousActivityLocalService.getSuspiciousActivities();
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.getSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(
			long userId, long messageId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.getSuspiciousActivity(
			userId, messageId);
	}

	@Override
	public int getSuspiciousActivityCount() {
		return _mbSuspiciousActivityLocalService.getSuspiciousActivityCount();
	}

	@Override
	public List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return _mbSuspiciousActivityLocalService.getThreadSuspiciousActivities(
			threadId);
	}

	@Override
	public MBSuspiciousActivity updateValidated(
			long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.
			updateValidated(suspiciousActivityId);
	}

	@Reference
	private MBSuspiciousActivityLocalService _mbSuspiciousActivityLocalService;

}