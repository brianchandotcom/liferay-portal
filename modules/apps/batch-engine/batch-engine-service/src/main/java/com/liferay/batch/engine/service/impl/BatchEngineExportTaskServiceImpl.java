/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.base.BatchEngineExportTaskServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public BatchEngineExportTask addBatchEngineExportTask(
			String externalReferenceCode, long companyId, long userId,
			String callbackURL, String className, String contentType,
			String executeStatus, List<String> fieldNamesList,
			Map<String, Serializable> parameters, String taskItemDelegateName)
		throws PortalException {

		_checkCrossCompanyPermissions(companyId);

		return batchEngineExportTaskLocalService.addBatchEngineExportTask(
			externalReferenceCode, companyId, userId, callbackURL, className,
			contentType, executeStatus, fieldNamesList, parameters,
			taskItemDelegateName);
	}

	@Override
	public BatchEngineExportTask getBatchEngineExportTask(
			long batchEngineExportTaskId)
		throws PortalException {

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskLocalService.getBatchEngineExportTask(
				batchEngineExportTaskId);

		_checkCrossCompanyPermissions(batchEngineExportTask.getCompanyId());

		_checkTaskPermissions(batchEngineExportTask);

		return batchEngineExportTask;
	}

	@Override
	public BatchEngineExportTask
			getBatchEngineExportTaskByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		_checkCrossCompanyPermissions(companyId);

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskLocalService.
				getBatchEngineExportTaskByExternalReferenceCode(
					externalReferenceCode, companyId);

		_checkTaskPermissions(batchEngineExportTask);

		return batchEngineExportTask;
	}

	@Override
	public List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end)
		throws PortalException {

		_checkCrossCompanyPermissions(companyId);

		return _filterTaskListByPermissions(
			batchEngineExportTaskLocalService.getBatchEngineExportTasks(
				companyId, start, end));
	}

	@Override
	public List<BatchEngineExportTask> getBatchEngineExportTasks(
			long companyId, int start, int end,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws PortalException {

		_checkCrossCompanyPermissions(companyId);

		return _filterTaskListByPermissions(
			batchEngineExportTaskPersistence.findByCompanyId(
				companyId, start, end, orderByComparator));
	}

	@Override
	public int getBatchEngineExportTasksCount(long companyId)
		throws PortalException {

		_checkCrossCompanyPermissions(companyId);

		return _filterTaskListByPermissions(
			batchEngineExportTaskPersistence.findByCompanyId(companyId)
		).size();
	}

	private void _checkCrossCompanyPermissions(long companyId)
		throws PrincipalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((companyId != permissionChecker.getCompanyId()) &&
			!permissionChecker.isOmniadmin()) {

			throw new PrincipalException();
		}
	}

	private void _checkTaskPermissions(
			BatchEngineExportTask batchEngineExportTask)
		throws PrincipalException {

		if (!_hasTaskPermissions(
				batchEngineExportTask, getPermissionChecker())) {

			throw new PrincipalException();
		}
	}

	private List<BatchEngineExportTask> _filterTaskListByPermissions(
			List<BatchEngineExportTask> batchEngineExportTasks)
		throws PrincipalException {

		List<BatchEngineExportTask> filteredBatchEngineExportTasks =
			new ArrayList<>();

		PermissionChecker permissionChecker = getPermissionChecker();

		for (BatchEngineExportTask batchEngineExportTask :
				batchEngineExportTasks) {

			if (_hasTaskPermissions(batchEngineExportTask, permissionChecker)) {
				filteredBatchEngineExportTasks.add(batchEngineExportTask);
			}
		}

		return filteredBatchEngineExportTasks;
	}

	private boolean _hasTaskPermissions(
		BatchEngineExportTask batchEngineExportTask,
		PermissionChecker permissionChecker) {

		if (permissionChecker.isCompanyAdmin(
				batchEngineExportTask.getCompanyId()) ||
			(batchEngineExportTask.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return false;
	}

}