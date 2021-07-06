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

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.base.BatchEngineExportTaskServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"json.web.service.context.name=batchengine",
		"json.web.service.context.path=BatchEngineExportTask"
	},
	service = AopService.class
)
public class BatchEngineExportTaskServiceImpl
	extends BatchEngineExportTaskServiceBaseImpl {

	@Override
	public List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((companyId != permissionChecker.getCompanyId()) &&
			!permissionChecker.isOmniadmin()) {

			throw new PrincipalException();
		}

		return batchEngineExportTaskLocalService.getBatchEngineExportTasks(
			companyId, start, end);
	}

	@Override
	public List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((companyId != permissionChecker.getCompanyId()) &&
			!permissionChecker.isOmniadmin()) {

			throw new PrincipalException();
		}

		return batchEngineExportTaskPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchEngineExportTasksCount(long companyId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((companyId != permissionChecker.getCompanyId()) &&
			!permissionChecker.isOmniadmin()) {

			throw new PrincipalException();
		}

		return batchEngineExportTaskLocalService.getBatchEngineExportTasksCount(
			companyId);
	}

}