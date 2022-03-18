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

import com.liferay.batch.planner.exception.BatchPlannerLogBatchEngineExportTaskERCException;
import com.liferay.batch.planner.exception.BatchPlannerLogBatchEngineImportTaskERCException;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerLogTable;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPlanTable;
import com.liferay.batch.planner.service.base.BatchPlannerLogLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

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
			if (Validator.isNotNull(batchEngineImportERC)) {
				throw new BatchPlannerLogBatchEngineImportTaskERCException(
					"Batch engine import task external reference code must " +
						"not be set during export");
			}

			if (Validator.isNull(batchEngineExportERC)) {
				throw new BatchPlannerLogBatchEngineExportTaskERCException(
					"Batch engine export task external reference code must " +
						"be set during export");
			}

			int maxLength = ModelHintsUtil.getMaxLength(
				BatchPlannerLog.class.getName(), "batchEngineExportERC");

			if (batchEngineExportERC.length() > maxLength) {
				throw new BatchPlannerLogBatchEngineExportTaskERCException(
					"Batch engine export task external reference code is too " +
						"long");
			}
		}
		else {
			if (Validator.isNotNull(batchEngineExportERC)) {
				throw new BatchPlannerLogBatchEngineExportTaskERCException(
					"Batch engine export task external reference code must " +
						"not be set during import");
			}

			if (Validator.isNull(batchEngineImportERC)) {
				throw new BatchPlannerLogBatchEngineImportTaskERCException(
					"Batch engine import task external reference code must " +
						"be set during import");
			}

			int maxLength = ModelHintsUtil.getMaxLength(
				BatchPlannerLog.class.getName(), "batchEngineImportERC");

			if (batchEngineImportERC.length() > maxLength) {
				throw new BatchPlannerLogBatchEngineImportTaskERCException(
					"Batch engine import task external reference code is too " +
						"long");
			}
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

		batchPlannerLog = batchPlannerLogPersistence.update(batchPlannerLog);

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), BatchPlannerLog.class.getName(),
			batchPlannerLog.getBatchPlannerLogId(), false, true, false);

		return batchPlannerLog;
	}

	@Override
	public BatchPlannerLog deleteBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		BatchPlannerLog batchPlannerLog = batchPlannerLogPersistence.remove(
			batchPlannerLogId);

		resourceLocalService.deleteResource(
			batchPlannerLog, ResourceConstants.SCOPE_INDIVIDUAL);

		return batchPlannerLog;
	}

	@Override
	public BatchPlannerLog fetchBatchPlannerLog(
		String batchEngineTaskERC, boolean export) {

		if (export) {
			return batchPlannerLogPersistence.fetchByBatchEngineExportTaskERC(
				batchEngineTaskERC);
		}

		return batchPlannerLogPersistence.fetchByBatchEngineImportTaskERC(
			batchEngineTaskERC);
	}

	@Override
	public BatchPlannerLog fetchBatchPlannerPlanBatchPlannerLog(
		long batchPlannerPlanId) {

		return batchPlannerLogPersistence.fetchByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public int getBatchPlannerLogsCount(long batchPlannerPlanId) {
		return batchPlannerLogPersistence.countByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerLog getBatchPlannerPlanBatchPlannerLog(
			long batchPlannerPlanId)
		throws PortalException {

		return batchPlannerLogPersistence.findByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return batchPlannerLogPersistence.dslQuery(
			_getSelectJoinStep(
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				)
			).orderBy(
				BatchPlannerLogTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, boolean export, String searchByField,
		String searchByKeyword, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return batchPlannerLogPersistence.dslQuery(
			_getSelectJoinStep(
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword)
					)
				)
			).orderBy(
				BatchPlannerLogTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return batchPlannerLogPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, String searchByField, String searchByKeyword, int start,
		int end, OrderByComparator<BatchPlannerLog> orderByComparator) {

		return batchPlannerLogPersistence.dslQuery(
			_getSelectJoinStep(
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword)
					)
				)
			).orderBy(
				BatchPlannerLogTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId) {
		return batchPlannerLogPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId, boolean export) {
		return dslQueryCount(
			_getCountJoinStep().where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				)));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
		long companyId, boolean export, String searchByField,
		String searchByKeyword) {

		return dslQueryCount(
			_getCountJoinStep().where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword)
					)
				)));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
		long companyId, String searchByField, String searchByKeyword) {

		return dslQueryCount(
			_getCountJoinStep().where(
				BatchPlannerLogTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword)
					)
				)));
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

	private JoinStep _getCountJoinStep() {
		return DSLQueryFactoryUtil.count(
		).from(
			BatchPlannerLogTable.INSTANCE
		).innerJoinON(
			BatchPlannerPlanTable.INSTANCE,
			BatchPlannerLogTable.INSTANCE.batchPlannerPlanId.eq(
				BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
		);
	}

	private JoinStep _getSelectJoinStep() {
		return DSLQueryFactoryUtil.select(
			BatchPlannerLogTable.INSTANCE
		).from(
			BatchPlannerLogTable.INSTANCE
		).innerJoinON(
			BatchPlannerPlanTable.INSTANCE,
			BatchPlannerLogTable.INSTANCE.batchPlannerPlanId.eq(
				BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
		);
	}

}