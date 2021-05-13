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

import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.plan.PlanExternalType;
import com.liferay.batch.planner.service.base.BatchPlannerPlanLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerPlan",
	service = AopService.class
)
public class BatchPlannerPlanLocalServiceImpl
	extends BatchPlannerPlanLocalServiceBaseImpl {

	@Override
	public BatchPlannerPlan addBatchPlannerPlan(
			long userId, String name, PlanExternalType planExternalType)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_validate(0, user.getCompanyId(), name);

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.create(
			counterLocalService.increment());

		batchPlannerPlan.setCompanyId(user.getCompanyId());
		batchPlannerPlan.setUserId(userId);
		batchPlannerPlan.setUserName(user.getFullName());
		batchPlannerPlan.setCreateDate(new Date());
		batchPlannerPlan.setModifiedDate(batchPlannerPlan.getCreateDate());
		batchPlannerPlan.setExternalType(planExternalType.name());
		batchPlannerPlan.setName(name);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, long userId, String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		User user = userLocalService.getUser(userId);

		_validate(batchPlannerPlanId, user.getCompanyId(), name);

		if (userId != batchPlannerPlan.getUserId()) {
			batchPlannerPlan.setUserId(userId);
			batchPlannerPlan.setUserName(user.getFullName());
		}

		batchPlannerPlan.setModifiedDate(new Date());
		batchPlannerPlan.setName(name);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	private void _validate(long batchPlannerPlanId, long companyId, String name)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerPlan.class.getName(), "name");

		if (Validator.isNull(name) || (name.length() > maxLength)) {
			throw new BatchPlannerPlanNameException(
				"Batch planner plan name must not be empty or longer than " +
					maxLength);
		}

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.fetchByC_N(companyId, name);

		if ((batchPlannerPlan == null) ||
			(batchPlannerPlan.getBatchPlannerPlanId() == batchPlannerPlanId)) {

			return;
		}

		throw new DuplicateBatchPlannerPlanException(
			StringBundler.concat(
				"Batch planner plan name \"", name,
				"\" already exists for company ID ", companyId));
	}

}