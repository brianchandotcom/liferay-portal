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

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.exception.RequiredBatchPlannerLogFieldException;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerLogLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerLog",
	service = AopService.class
)
public class BatchPlannerLogLocalServiceImpl
	extends BatchPlannerLogLocalServiceBaseImpl {

	@Override
	public BatchPlannerLog addBatchPlannerLog(
			long userId, long batchPlannerPlanId, String batchEngineExportERC,
			String batchEngineImportERC, String dispatchTriggerERC, int size,
			int status)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		if (batchPlannerPlan.isExport()) {
			_validateMutuallyExclusiveField(
				"batchEngineImportERC", batchEngineImportERC,
				batchPlannerPlan.isExport());

			_validateRequiredField(
				"batchEngineExportERC", batchEngineExportERC);
		}
		else {
			_validateMutuallyExclusiveField(
				"batchEngineExportERC", batchEngineExportERC,
				batchPlannerPlan.isExport());

			_validateRequiredField(
				"batchEngineImportERC", batchEngineImportERC);
		}

		BatchPlannerLog batchPlannerLog = batchPlannerLogPersistence.create(
			counterLocalService.increment(BatchPlannerLog.class.getName()));

		User user = userLocalService.getUser(userId);

		batchPlannerLog.setCompanyId(user.getCompanyId());

		batchPlannerLog.setUserId(userId);
		batchPlannerLog.setBatchPlannerPlanId(batchPlannerPlanId);
		batchPlannerLog.setBatchEngineExportTaskERC(batchEngineExportERC);
		batchPlannerLog.setBatchEngineImportTaskERC(batchEngineImportERC);
		batchPlannerLog.setDispatchTriggerERC(dispatchTriggerERC);
		batchPlannerLog.setSize(size);
		batchPlannerLog.setStatus(status);

		return batchPlannerLogPersistence.update(batchPlannerLog);
	}

	@Override
	public BatchPlannerLog updateBatchPlannerLogSize(
			long batchPlannerLogId, int size)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			batchPlannerLogPersistence.findByPrimaryKey(batchPlannerLogId);

		batchPlannerLog.setSize(size);

		return batchPlannerLogPersistence.update(batchPlannerLog);
	}

	@Override
	public BatchPlannerLog updateBatchPlannerLogStatus(
			long batchPlannerLogId, int status)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			batchPlannerLogPersistence.findByPrimaryKey(batchPlannerLogId);

		batchPlannerLog.setStatus(status);

		return batchPlannerLogPersistence.update(batchPlannerLog);
	}

	private void _validateMutuallyExclusiveField(
			String name, String value, boolean export)
		throws PortalException {

		if (Validator.isNull(value)) {
			return;
		}

		throw new RequiredBatchPlannerLogFieldException(
			StringBundler.concat(
				"Batch planner log field \"", name, "\" must be null if field ",
				"\"export\" is ", export));
	}

	private void _validateRequiredField(String name, String value)
		throws PortalException {

		if (Validator.isNull(value)) {
			throw new RequiredBatchPlannerLogFieldException(
				StringBundler.concat(
					"Batch planner log field \"", name, "\" is null"));
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), name);

		if (value.length() > maxLength) {
			throw new RequiredBatchPlannerLogFieldException(
				StringBundler.concat(
					"Batch planner log field \"", name,
					"\" must not be longer than ", maxLength));
		}
	}

}