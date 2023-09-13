/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.planner.batch.engine.task.TaskItemUtil;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.rest.dto.v1_0.Mapping;
import com.liferay.batch.planner.rest.dto.v1_0.Plan;
import com.liferay.batch.planner.rest.dto.v1_0.Policy;
import com.liferay.batch.planner.rest.internal.vulcan.batch.engine.FieldProvider;
import com.liferay.batch.planner.rest.resource.v1_0.PlanResource;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Matija Petanjek
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/plan.properties",
	scope = ServiceScope.PROTOTYPE, service = PlanResource.class
)
public class PlanResourceImpl extends BasePlanResourceImpl {

	@Override
	public void deletePlan(Long id) throws Exception {
		_batchPlannerPlanService.deleteBatchPlannerPlan(id);
	}

	@Override
	public Plan getPlan(Long id) throws Exception {
		return _toPlan(_batchPlannerPlanService.getBatchPlannerPlan(id));
	}

	@Override
	public Page<Plan> getPlansPage(Pagination pagination) throws Exception {
		return Page.of(
			transform(
				_batchPlannerPlanService.getBatchPlannerPlans(
					contextCompany.getCompanyId(),
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toPlan),
			pagination,
			_batchPlannerPlanService.getBatchPlannerPlansCount(
				contextCompany.getCompanyId()));
	}

	@Override
	public Response getPlanTemplate(String internalClassNameKey)
		throws Exception {

		return _getResponse(
			TaskItemUtil.getSimpleClassName(internalClassNameKey),
			_fieldProvider.getFields(
				contextCompany.getCompanyId(), internalClassNameKey,
				contextUriInfo));
	}

	@Override
	public Plan patchPlan(Long id, Plan plan) throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.updateBatchPlannerPlan(
				id, plan.getExternalType(), plan.getInternalClassName(),
				plan.getName());

		Mapping[] mappings = plan.getMappings();

		if (mappings != null) {
			for (Mapping mapping : plan.getMappings()) {
				_batchPlannerMappingService.updateBatchPlannerMapping(
					mapping.getId(), mapping.getExternalFieldName(),
					mapping.getExternalFieldType(), mapping.getScript());
			}
		}

		Policy[] policies = plan.getPolicies();

		if (policies != null) {
			for (Policy policy : plan.getPolicies()) {
				_batchPlannerPolicyService.updateBatchPlannerPolicy(
					id, policy.getName(), policy.getValue());
			}
		}

		return _toPlan(batchPlannerPlan);
	}

	@Override
	public Plan postPlan(Plan plan) throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				plan.getExport(), plan.getExternalType(), plan.getExternalURL(),
				plan.getInternalClassName(), plan.getName(), 0,
				plan.getTaskItemDelegateName(), plan.getTemplate());

		Mapping[] mappings = plan.getMappings();

		if (mappings != null) {
			for (Mapping mapping : mappings) {
				_batchPlannerMappingService.addBatchPlannerMapping(
					batchPlannerPlan.getBatchPlannerPlanId(),
					mapping.getExternalFieldName(),
					mapping.getExternalFieldType(),
					mapping.getInternalFieldName(),
					mapping.getInternalFieldType(), mapping.getScript());
			}
		}

		Policy[] policies = plan.getPolicies();

		if (policies != null) {
			for (Policy policy : plan.getPolicies()) {
				_batchPlannerPolicyService.addBatchPlannerPolicy(
					batchPlannerPlan.getBatchPlannerPlanId(), policy.getName(),
					policy.getValue());
			}
		}

		return _toPlan(batchPlannerPlan);
	}

	private Response _getResponse(String dtoEntityName, List<Field> fields) {
		fields = _fieldProvider.filter(fields, Field.AccessType.READ);

		Iterator<Field> iterator = fields.iterator();

		StringBundler headerSB = new StringBundler(fields.size() * 2);
		StringBundler lineSB = new StringBundler(fields.size() * 2);

		while (iterator.hasNext()) {
			Field field = iterator.next();

			String fieldName = field.getName();

			if (fieldName.endsWith("_i18n")) {
				fieldName = StringBundler.concat(
					fieldName, StringPool.UNDERLINE,
					contextAcceptLanguage.getPreferredLanguageId());
			}

			headerSB.append(fieldName);

			lineSB.append(field.getType());

			if (iterator.hasNext()) {
				headerSB.append(StringPool.COMMA);

				lineSB.append(StringPool.COMMA);
			}
		}

		return Response.ok(
			StringBundler.concat(headerSB, System.lineSeparator(), lineSB)
		).header(
			"content-disposition",
			StringBundler.concat(
				"attachment; filename=", StringUtil.toLowerCase(dtoEntityName),
				"-", StringUtil.randomString(), ".csv")
		).build();
	}

	private Mapping _toMapping(BatchPlannerMapping batchPlannerMapping) {
		return new Mapping() {
			{
				externalFieldName = batchPlannerMapping.getExternalFieldName();
				externalFieldType = batchPlannerMapping.getExternalFieldType();
				id = batchPlannerMapping.getBatchPlannerMappingId();
				internalFieldName = batchPlannerMapping.getInternalFieldName();
				internalFieldType = batchPlannerMapping.getInternalFieldType();
				planId = batchPlannerMapping.getBatchPlannerPlanId();
				script = batchPlannerMapping.getScript();
			}
		};
	}

	private Plan _toPlan(BatchPlannerPlan batchPlannerPlan) throws Exception {
		return new Plan() {
			{
				active = batchPlannerPlan.isActive();
				export = batchPlannerPlan.isExport();
				externalType = batchPlannerPlan.getExternalType();
				externalURL = batchPlannerPlan.getExternalURL();
				id = batchPlannerPlan.getBatchPlannerPlanId();
				internalClassName = batchPlannerPlan.getInternalClassName();
				internalClassNameKey = TaskItemUtil.getInternalClassNameKey(
					batchPlannerPlan.getInternalClassName(),
					batchPlannerPlan.getTaskItemDelegateName());
				mappings = transformToArray(
					_batchPlannerMappingService.getBatchPlannerMappings(
						batchPlannerPlan.getBatchPlannerPlanId()),
					batchPlannerMapping -> _toMapping(batchPlannerMapping),
					Mapping.class);
				name = batchPlannerPlan.getName();
				policies = transformToArray(
					_batchPlannerPolicyService.getBatchPlannerPolicies(
						batchPlannerPlan.getBatchPlannerPlanId()),
					batchPlannerPolicy -> _toPolicy(batchPlannerPolicy),
					Policy.class);
				taskItemDelegateName =
					batchPlannerPlan.getTaskItemDelegateName();
				template = batchPlannerPlan.isTemplate();
			}
		};
	}

	private Policy _toPolicy(BatchPlannerPolicy batchPlannerPolicy) {
		return new Policy() {
			{
				id = batchPlannerPolicy.getBatchPlannerPolicyId();
				name = batchPlannerPolicy.getName();
				planId = batchPlannerPolicy.getBatchPlannerPlanId();
				value = batchPlannerPolicy.getValue();
			}
		};
	}

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

	@Reference
	private FieldProvider _fieldProvider;

}