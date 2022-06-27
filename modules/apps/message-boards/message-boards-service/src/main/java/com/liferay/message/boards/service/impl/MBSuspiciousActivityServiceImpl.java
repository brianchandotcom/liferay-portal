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
import com.liferay.portal.kernel.model.User;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

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

		return mbSuspiciousActivityLocalService.addOrUpdateSuspiciousActivity(userId, messageId, description, type);
	}
	@Override
	public MBSuspiciousActivity findByPrimaryKey(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.findByPrimaryKey(suspiciousActivityId);
	}

	@Override
	public List<MBSuspiciousActivity> findAll() {

		return _mbSuspiciousActivityLocalService.findAll();
	}

	@Override
	public MBSuspiciousActivity findByU_M(long userId, long messageId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.findByU_M(userId, messageId);
	}

	@Override
	public MBSuspiciousActivity remove(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityLocalService.remove(suspiciousActivityId);
	}

	@Override
	public int countAll() {
		return _mbSuspiciousActivityLocalService.countAll();
	}

	@Reference
	MBSuspiciousActivityLocalService _mbSuspiciousActivityLocalService;
}