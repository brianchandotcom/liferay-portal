/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.constants.BatchPlannerActionKeys;
import com.liferay.batch.planner.constants.BatchPlannerConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanInternalClassNameException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPlanTable;
import com.liferay.batch.planner.service.base.BatchPlannerPlanServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerPlan"
	},
	service = AopService.class
)
public class BatchPlannerPlanServiceImpl
	extends BatchPlannerPlanServiceBaseImpl {

	@Override
	public BatchPlannerPlan addBatchPlannerPlan(
			boolean export, String externalType, String externalURL,
			String internalClassName, String name, int size,
			String taskItemDelegateName, boolean template)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			BatchPlannerActionKeys.ADD_BATCH_PLANNER_PLAN);

		return batchPlannerPlanLocalService.addBatchPlannerPlan(
			permissionChecker.getUserId(), export, externalType, externalURL,
			internalClassName, name, size, taskItemDelegateName, template);
	}

	@Override
	public BatchPlannerPlan deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.DELETE);

		return batchPlannerPlanLocalService.deleteBatchPlannerPlan(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerPlan fetchBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.fetchByPrimaryKey(batchPlannerPlanId);

		if (batchPlannerPlan == null) {
			return null;
		}

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPlan;
	}

	@Override
	public BatchPlannerPlan getBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.VIEW);

		return batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByC_E_T(
			companyId, export, template, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
			long companyId, boolean export, boolean template,
			String searchByKeyword, int start, int end,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws PortalException {

		_checkAmbiguousKeyword(searchByKeyword);

		searchByKeyword = StringUtil.quote(searchByKeyword, CharPool.PERCENT);

		return batchPlannerPlanPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BatchPlannerPlanTable.INSTANCE
			).from(
				BatchPlannerPlanTable.INSTANCE
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.template.eq(template)
				).and(
					BatchPlannerPlanTable.INSTANCE.internalClassName.like(
						searchByKeyword
					).or(
						BatchPlannerPlanTable.INSTANCE.name.like(
							searchByKeyword)
					).withParentheses()
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerPlan.class,
						BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
				)
			).orderBy(
				BatchPlannerPlanTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByC_T(
			companyId, template, start, end, orderByComparator);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
			long companyId, boolean template, String searchByKeyword, int start,
			int end, OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws PortalException {

		_checkAmbiguousKeyword(searchByKeyword);

		searchByKeyword = StringUtil.quote(searchByKeyword, CharPool.PERCENT);

		return batchPlannerPlanPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BatchPlannerPlanTable.INSTANCE
			).from(
				BatchPlannerPlanTable.INSTANCE
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.template.eq(template)
				).and(
					BatchPlannerPlanTable.INSTANCE.internalClassName.like(
						searchByKeyword
					).or(
						BatchPlannerPlanTable.INSTANCE.name.like(
							searchByKeyword)
					).withParentheses()
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerPlan.class,
						BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
				)
			).orderBy(
				BatchPlannerPlanTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end) {

		return batchPlannerPlanPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<BatchPlannerPlan> getBatchPlannerPlans(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return batchPlannerPlanPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId) {
		return batchPlannerPlanPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId, boolean template) {
		return batchPlannerPlanPersistence.filterCountByC_T(
			companyId, template);
	}

	@Override
	public int getBatchPlannerPlansCount(
		long companyId, boolean export, boolean template) {

		return batchPlannerPlanPersistence.filterCountByC_E_T(
			companyId, export, template);
	}

	@Override
	public int getBatchPlannerPlansCount(
			long companyId, boolean export, boolean template,
			String searchByKeyword)
		throws PortalException {

		_checkAmbiguousKeyword(searchByKeyword);

		searchByKeyword = StringUtil.quote(searchByKeyword, CharPool.PERCENT);

		return batchPlannerPlanPersistence.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				BatchPlannerPlanTable.INSTANCE
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.export.eq(export)
				).and(
					BatchPlannerPlanTable.INSTANCE.template.eq(template)
				).and(
					BatchPlannerPlanTable.INSTANCE.internalClassName.like(
						searchByKeyword
					).or(
						BatchPlannerPlanTable.INSTANCE.name.like(
							searchByKeyword)
					).withParentheses()
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerPlan.class,
						BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
				)
			));
	}

	@Override
	public int getBatchPlannerPlansCount(
			long companyId, boolean template, String searchByKeyword)
		throws PortalException {

		_checkAmbiguousKeyword(searchByKeyword);

		searchByKeyword = StringUtil.quote(searchByKeyword, CharPool.PERCENT);

		return batchPlannerPlanPersistence.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				BatchPlannerPlanTable.INSTANCE
			).where(
				BatchPlannerPlanTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BatchPlannerPlanTable.INSTANCE.template.eq(template)
				).and(
					BatchPlannerPlanTable.INSTANCE.internalClassName.like(
						searchByKeyword
					).or(
						BatchPlannerPlanTable.INSTANCE.name.like(
							searchByKeyword)
					).withParentheses()
				).and(
					_inlineSQLHelper.getPermissionWherePredicate(
						BatchPlannerPlan.class,
						BatchPlannerPlanTable.INSTANCE.batchPlannerPlanId)
				)
			));
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, String externalType,
			String internalClassName, String name)
		throws PortalException {

		_batchPlannerPlanModelResourcePermission.check(
			getPermissionChecker(), batchPlannerPlanId, ActionKeys.UPDATE);

		return batchPlannerPlanLocalService.updateBatchPlannerPlan(
			batchPlannerPlanId, externalType, internalClassName, name);
	}

	private void _checkAmbiguousKeyword(String keyword) throws PortalException {
		if (Validator.isNull(keyword) ||
			!_AMBIGUOUS_SEARCH_KEYWORDS.contains(
				StringUtil.toLowerCase(keyword))) {

			return;
		}

		throw new BatchPlannerPlanInternalClassNameException(
			StringBundler.concat("Search term ", keyword, " is too ambiguous"));
	}

	private static final String _AMBIGUOUS_SEARCH_KEYWORDS = "com.liferay";

	@Reference(
		target = "(model.class.name=com.liferay.batch.planner.model.BatchPlannerPlan)"
	)
	private ModelResourcePermission<BatchPlannerPlan>
		_batchPlannerPlanModelResourcePermission;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference(
		target = "(resource.name=" + BatchPlannerConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}