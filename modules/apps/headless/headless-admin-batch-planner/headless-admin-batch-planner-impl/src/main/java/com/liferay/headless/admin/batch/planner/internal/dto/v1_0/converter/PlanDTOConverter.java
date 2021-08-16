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

package com.liferay.headless.admin.batch.planner.internal.dto.v1_0.converter;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.headless.admin.batch.planner.dto.v1_0.Mapping;
import com.liferay.headless.admin.batch.planner.dto.v1_0.Plan;
import com.liferay.headless.admin.batch.planner.dto.v1_0.Policy;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = "dto.class.name=com.liferay.batch.planner.model.BatchPlannerPlan",
	service = {DTOConverter.class, PlanDTOConverter.class}
)
public class PlanDTOConverter implements DTOConverter<BatchPlannerPlan, Plan> {

	@Override
	public String getContentType() {
		return BatchPlannerPlan.class.getSimpleName();
	}

	@Override
	public Plan toDTO(BatchPlannerPlan batchPlannerPlan) throws Exception {
		return new Plan() {
			{
				active = batchPlannerPlan.isActive();
				export = batchPlannerPlan.isExport();
				externalType = batchPlannerPlan.getExternalType();
				externalURL = batchPlannerPlan.getExternalURL();
				id = batchPlannerPlan.getBatchPlannerPlanId();
				internalClassName = batchPlannerPlan.getInternalClassName();
				mappings = _getMappings(batchPlannerPlan);
				name = batchPlannerPlan.getName();
				policies = _getPolicies(batchPlannerPlan);
			}
		};
	}

	public List<Plan> toDTOs(List<BatchPlannerPlan> batchPlannerPlans)
		throws Exception {

		List<Plan> plans = new ArrayList<>();

		for (BatchPlannerPlan batchPlannerPlan : batchPlannerPlans) {
			plans.add(toDTO(batchPlannerPlan));
		}

		return plans;
	}

	private Mapping[] _getMappings(BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		List<BatchPlannerMapping> batchPlannerMappings =
			_batchPlannerMappingService.getBatchPlannerMappings(
				batchPlannerPlan.getBatchPlannerPlanId());

		return batchPlannerMappings.toArray(new Mapping[0]);
	}

	private Policy[] _getPolicies(BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		List<BatchPlannerPolicy> batchPlannerPolicies =
			_batchPlannerPolicyService.getBatchPlannerPolicies(
				batchPlannerPlan.getBatchPlannerPlanId());

		return batchPlannerPolicies.toArray(new Policy[0]);
	}

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

}