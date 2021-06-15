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

package com.liferay.batch.planner.service.test.util;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogServiceUtil;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Igor Beslic
 */
public class BatchPlannerLogTestUtil {

	public static BatchPlannerLog addBatchPlannerLog(boolean export, int status)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(300);

		BatchPlannerLog batchPlannerLog = _randomBatchPlannerLog(
			export, status);

		return BatchPlannerLogServiceUtil.addBatchPlannerLog(
			batchPlannerPlan.getBatchPlannerPlanId(),
			batchPlannerLog.getBatchEngineExportTaskERC(),
			batchPlannerLog.getBatchEngineImportTaskERC(),
			batchPlannerLog.getDispatchTriggerERC(), batchPlannerLog.getSize(),
			batchPlannerLog.getStatus());
	}

	private static BatchPlannerLog _randomBatchPlannerLog(
		boolean export, int status) {

		BatchPlannerLogPersistence batchPlannerLogPersistence =
			BatchPlannerLogUtil.getPersistence();

		BatchPlannerLog batchPlannerLog = batchPlannerLogPersistence.create(
			RandomTestUtil.nextLong());

		if (export) {
			batchPlannerLog.setBatchEngineExportTaskERC(
				RandomTestUtil.randomString(45));
		}
		else {
			batchPlannerLog.setBatchEngineImportTaskERC(
				RandomTestUtil.randomString(45));
		}

		batchPlannerLog.setDispatchTriggerERC(RandomTestUtil.randomString(45));
		batchPlannerLog.setSize(RandomTestUtil.randomInt());
		batchPlannerLog.setStatus(status);

		return batchPlannerLog;
	}

}