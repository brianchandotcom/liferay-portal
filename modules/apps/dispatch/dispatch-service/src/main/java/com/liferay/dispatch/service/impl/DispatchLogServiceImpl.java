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

package com.liferay.dispatch.service.impl;

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.base.DispatchLogServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=dispatch",
		"json.web.service.context.path=DispatchLog"
	},
	service = AopService.class
)
public class DispatchLogServiceImpl extends DispatchLogServiceBaseImpl {

	@Override
	public void deleteDispatchLog(long dispatchLogId) throws PortalException {
		DispatchLog dispatchLog = dispatchLogLocalService.getDispatchLog(
			dispatchLogId);

		_dispatchTriggerModelResourcePermission.check(
			getPermissionChecker(), dispatchLog.getDispatchTriggerId(),
			ActionKeys.UPDATE);

		dispatchLogLocalService.deleteDispatchLog(dispatchLogId);
	}

	@Override
	public DispatchLog getDispatchLog(long dispatchLogId)
		throws PortalException {

		DispatchLog dispatchLog = dispatchLogLocalService.getDispatchLog(
			dispatchLogId);

		_dispatchTriggerModelResourcePermission.check(
			getPermissionChecker(), dispatchLog.getDispatchTriggerId(),
			ActionKeys.VIEW);

		return dispatchLog;
	}

	@Override
	public List<DispatchLog> getDispatchLogs(
			long dispatchTriggerId, int start, int end)
		throws PortalException {

		_dispatchTriggerModelResourcePermission.check(
			getPermissionChecker(), dispatchTriggerId, ActionKeys.VIEW);

		return dispatchLogLocalService.getDispatchLogs(
			dispatchTriggerId, start, end);
	}

	@Override
	public List<DispatchLog> getDispatchLogs(
			long dispatchTriggerId, int start, int end,
			OrderByComparator<DispatchLog> orderByComparator)
		throws PortalException {

		_dispatchTriggerModelResourcePermission.check(
			getPermissionChecker(), dispatchTriggerId, ActionKeys.VIEW);

		return dispatchLogPersistence.findByDispatchTriggerId(
			dispatchTriggerId, start, end, orderByComparator);
	}

	@Override
	public int getDispatchLogsCount(long dispatchTriggerId)
		throws PortalException {

		_dispatchTriggerModelResourcePermission.check(
			getPermissionChecker(), dispatchTriggerId, ActionKeys.VIEW);

		return dispatchLogLocalService.getDispatchLogsCount(dispatchTriggerId);
	}

	private static volatile ModelResourcePermission<DispatchTrigger>
		_dispatchTriggerModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DispatchTriggerServiceImpl.class,
				"_dispatchTriggerModelResourcePermission",
				DispatchTrigger.class);

}