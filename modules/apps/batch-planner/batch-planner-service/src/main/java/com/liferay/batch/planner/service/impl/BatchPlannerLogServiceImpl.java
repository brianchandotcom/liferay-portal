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

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerLogTable;
import com.liferay.batch.planner.model.BatchPlannerPlanTable;
import com.liferay.batch.planner.service.base.BatchPlannerLogServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerLog"
	},
	service = AopService.class
)
public class BatchPlannerLogServiceImpl extends BatchPlannerLogServiceBaseImpl {

	@Override
	public BatchPlannerLog getBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		_batchPlannerLogModelResourcePermission.check(
			getPermissionChecker(), batchPlannerLogId, ActionKeys.VIEW);

		return batchPlannerLogLocalService.getBatchPlannerLog(
			batchPlannerLogId);
	}

	@Override
	public BatchPlannerLog getBatchPlannerPlanBatchPlannerLog(
			long batchPlannerPlanId)
		throws PortalException {

		BatchPlannerLog batchPlannerLog =
			batchPlannerLogLocalService.getBatchPlannerPlanBatchPlannerLog(
				batchPlannerPlanId);

		_batchPlannerLogModelResourcePermission.check(
			getPermissionChecker(), batchPlannerLog.getBatchPlannerLogId(),
			ActionKeys.VIEW);

		return batchPlannerLog;
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, boolean export, int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return batchPlannerLogPersistence.dslQuery(
			_getJoinStep(
				DSLQueryFactoryUtil.select(BatchPlannerLogTable.INSTANCE)
			).where(
				BatchPlannerLogTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
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
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return batchPlannerLogPersistence.dslQuery(
			_getJoinStep(
				DSLQueryFactoryUtil.select(BatchPlannerLogTable.INSTANCE)
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword, CharPool.PERCENT)
					)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
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
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return batchPlannerLogPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
			long companyId, String searchByField, String searchByKeyword,
			int start, int end,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws PortalException {

		return batchPlannerLogPersistence.dslQuery(
			_getJoinStep(
				DSLQueryFactoryUtil.select(BatchPlannerLogTable.INSTANCE)
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword, CharPool.PERCENT)
					)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
				)
			).orderBy(
				BatchPlannerLogTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId)
		throws PortalException {

		return batchPlannerLogPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId, boolean export)
		throws PortalException {

		return batchPlannerLogPersistence.dslQueryCount(
			_getJoinStep(
				DSLQueryFactoryUtil.count()
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
				)
			));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
			long companyId, boolean export, String searchByField,
			String searchByKeyword)
		throws PortalException {

		return batchPlannerLogPersistence.dslQueryCount(
			_getJoinStep(
				DSLQueryFactoryUtil.count()
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword, CharPool.PERCENT)
					)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
				)
			));
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(
			long companyId, String searchByField, String searchByKeyword)
		throws PortalException {

		return batchPlannerLogPersistence.dslQueryCount(
			_getJoinStep(
				DSLQueryFactoryUtil.count()
			).where(
				BatchPlannerLogTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.getColumn(
						searchByField
					).like(
						StringUtil.quote(searchByKeyword, CharPool.PERCENT)
					)
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerLog.class,
						BatchPlannerLogTable.INSTANCE.batchPlannerLogId)
				)
			));
	}

	private JoinStep _getJoinStep(FromStep fromStep) {
		return fromStep.from(
			BatchPlannerLogTable.INSTANCE
		).innerJoinON(
			BatchPlannerPlanTable.INSTANCE,
			BatchPlannerLogTable.INSTANCE.batchPlannerPlanId.eq(
				BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
		);
	}

	private static volatile ModelResourcePermission<BatchPlannerLog>
		_batchPlannerLogModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				BatchPlannerLogServiceImpl.class,
				"_batchPlannerLogModelResourcePermission",
				BatchPlannerLog.class);

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

}