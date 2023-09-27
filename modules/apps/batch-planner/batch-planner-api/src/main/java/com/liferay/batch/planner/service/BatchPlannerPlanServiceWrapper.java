/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchPlannerPlanService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanService
 * @generated
 */
public class BatchPlannerPlanServiceWrapper
	implements BatchPlannerPlanService,
			   ServiceWrapper<BatchPlannerPlanService> {

	public BatchPlannerPlanServiceWrapper() {
		this(null);
	}

	public BatchPlannerPlanServiceWrapper(
		BatchPlannerPlanService batchPlannerPlanService) {

		_batchPlannerPlanService = batchPlannerPlanService;
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan addBatchPlannerPlan(
			boolean export, String externalType, String externalURL,
			String internalClassName, String name, int size,
			String taskItemDelegateName, boolean template)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.addBatchPlannerPlan(
			export, externalType, externalURL, internalClassName, name, size,
			taskItemDelegateName, template);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
			deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.deleteBatchPlannerPlan(
			batchPlannerPlanId);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
			fetchBatchPlannerPlan(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.fetchBatchPlannerPlan(
			batchPlannerPlanId);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan getBatchPlannerPlan(
			long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.getBatchPlannerPlan(batchPlannerPlanId);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
		getBatchPlannerPlans(
			long companyId, boolean export, boolean template, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, export, template, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
			getBatchPlannerPlans(
				long companyId, boolean export, boolean template,
				String searchByKeyword, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, export, template, searchByKeyword, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
		getBatchPlannerPlans(
			long companyId, boolean template, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, template, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
			getBatchPlannerPlans(
				long companyId, boolean template, String searchByKeyword,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, template, searchByKeyword, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
		getBatchPlannerPlans(long companyId, int start, int end) {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
		getBatchPlannerPlans(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		return _batchPlannerPlanService.getBatchPlannerPlans(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId) {
		return _batchPlannerPlanService.getBatchPlannerPlansCount(companyId);
	}

	@Override
	public int getBatchPlannerPlansCount(long companyId, boolean template) {
		return _batchPlannerPlanService.getBatchPlannerPlansCount(
			companyId, template);
	}

	@Override
	public int getBatchPlannerPlansCount(
		long companyId, boolean export, boolean template) {

		return _batchPlannerPlanService.getBatchPlannerPlansCount(
			companyId, export, template);
	}

	@Override
	public int getBatchPlannerPlansCount(
			long companyId, boolean export, boolean template,
			String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.getBatchPlannerPlansCount(
			companyId, export, template, searchByKeyword);
	}

	@Override
	public int getBatchPlannerPlansCount(
			long companyId, boolean template, String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.getBatchPlannerPlansCount(
			companyId, template, searchByKeyword);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerPlanService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
			updateBatchPlannerPlan(
				long batchPlannerPlanId, String externalType,
				String internalClassName, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanService.updateBatchPlannerPlan(
			batchPlannerPlanId, externalType, internalClassName, name);
	}

	@Override
	public BatchPlannerPlanService getWrappedService() {
		return _batchPlannerPlanService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerPlanService batchPlannerPlanService) {

		_batchPlannerPlanService = batchPlannerPlanService;
	}

	private BatchPlannerPlanService _batchPlannerPlanService;

}