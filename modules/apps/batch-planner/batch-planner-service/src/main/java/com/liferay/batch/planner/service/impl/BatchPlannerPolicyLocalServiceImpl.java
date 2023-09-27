/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.base.BatchPlannerPolicyLocalServiceBaseImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerPlanPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerPolicy",
	service = AopService.class
)
public class BatchPlannerPolicyLocalServiceImpl
	extends BatchPlannerPolicyLocalServiceBaseImpl {

	@Override
	public BatchPlannerPolicy addBatchPlannerPolicy(
			long userId, long batchPlannerPlanId, String name, String value)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new BatchPlannerPlanNameException(
				"Batch planner policy name is null for batch planner plan ID " +
					batchPlannerPlanId);
		}

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPolicyPersistence.create(
				counterLocalService.increment());

		batchPlannerPolicy.setCompanyId(batchPlannerPlan.getCompanyId());
		batchPlannerPolicy.setUserId(userId);

		User user = _userLocalService.getUser(userId);

		batchPlannerPolicy.setUserName(user.getFullName());

		batchPlannerPolicy.setBatchPlannerPlanId(
			batchPlannerPlan.getBatchPlannerPlanId());
		batchPlannerPolicy.setName(StringUtil.trim(name));
		batchPlannerPolicy.setValue(StringUtil.trim(value));

		return batchPlannerPolicyPersistence.update(batchPlannerPolicy);
	}

	@Override
	public BatchPlannerPolicy deleteBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		return batchPlannerPolicyPersistence.removeByBPPI_N(
			batchPlannerPlanId, name);
	}

	@Override
	public BatchPlannerPolicy fetchBatchPlannerPolicy(
		long batchPlannerPlanId, String name) {

		return batchPlannerPolicyPersistence.fetchByBPPI_N(
			batchPlannerPlanId, name);
	}

	@Override
	public List<BatchPlannerPolicy> getBatchPlannerPolicies(
		long batchPlannerPlanId) {

		return batchPlannerPolicyPersistence.findByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerPolicy getBatchPlannerPolicy(
			long batchPlannerPlanId, String name)
		throws PortalException {

		return batchPlannerPolicyPersistence.findByBPPI_N(
			batchPlannerPlanId, name);
	}

	@Override
	public boolean hasBatchPlannerPolicy(long batchPlannerPlanId, String name) {
		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPolicyPersistence.fetchByBPPI_N(
				batchPlannerPlanId, name);

		if (batchPlannerPolicy != null) {
			return true;
		}

		return false;
	}

	@Override
	public BatchPlannerPolicy updateBatchPlannerPolicy(
			long batchPlannerPlanId, String name, String value)
		throws PortalException {

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPolicyPersistence.findByBPPI_N(
				batchPlannerPlanId, name);

		batchPlannerPolicy.setValue(StringUtil.trim(value));

		return batchPlannerPolicyPersistence.update(batchPlannerPolicy);
	}

	@Reference
	private BatchPlannerPlanPersistence _batchPlannerPlanPersistence;

	@Reference
	private UserLocalService _userLocalService;

}