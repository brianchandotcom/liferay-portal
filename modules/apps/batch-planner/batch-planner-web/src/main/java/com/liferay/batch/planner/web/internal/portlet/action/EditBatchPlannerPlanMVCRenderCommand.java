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

package com.liferay.batch.planner.web.internal.portlet.action;

import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.web.internal.display.context.EditBatchPlannerPlanDisplayContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/edit_export_batch_planner_plan",
		"mvc.command.name=/batch_planner/edit_import_batch_planner_plan"
	},
	service = MVCRenderCommand.class
)
public class EditBatchPlannerPlanMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			return _render(renderRequest);
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, PortalException.class);

			_log.error("Unable to process render request", portalException);
		}

		return "/view.jsp";
	}

	private void _addHeadlessEndpoints(
		ApplicationDTO applicationDTO, Map<String, String> headlessEndpoints,
		ResourceMethodInfoDTO resourceMethodInfoDTO) {

		String headlessEndpoint = StringBundler.concat(
			"/o", applicationDTO.base, resourceMethodInfoDTO.path);

		if (!headlessEndpoint.contains("openapi")) {
			return;
		}

		headlessEndpoints.put(
			applicationDTO.base,
			headlessEndpoint.replaceAll("\\{.+\\}", "json"));
	}

	private Map<String, String> _getHeadlessEndpoints() {
		Map<String, String> headlessEndpoints = new HashMap<>();

		RuntimeDTO runtimeDTO = _jaxrsServiceRuntime.getRuntimeDTO();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
				for (ResourceMethodInfoDTO resourceMethodInfoDTO :
						resourceDTO.resourceMethods) {

					_addHeadlessEndpoints(
						applicationDTO, headlessEndpoints,
						resourceMethodInfoDTO);
				}
			}

			for (ResourceMethodInfoDTO resourceMethod :
					applicationDTO.resourceMethods) {

				_addHeadlessEndpoints(
					applicationDTO, headlessEndpoints, resourceMethod);
			}
		}

		return headlessEndpoints;
	}

	private boolean _isExport(String value) {
		if (value.equals("export")) {
			return true;
		}

		return false;
	}

	private String _render(RenderRequest renderRequest) throws PortalException {
		long batchPlannerPlanId = ParamUtil.getLong(
			renderRequest, "batchPlannerPlanId");

		if (batchPlannerPlanId == 0) {
			if (Validator.isNull(
					ParamUtil.getString(renderRequest, "navigation"))) {

				return "/view.jsp";
			}

			if (_isExport(ParamUtil.getString(renderRequest, "navigation"))) {
				renderRequest.setAttribute(
					WebKeys.PORTLET_DISPLAY_CONTEXT,
					new EditBatchPlannerPlanDisplayContext(
						_batchPlannerPlanService.getBatchPlannerPlans(
							_portal.getCompanyId(renderRequest), true, true,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
						_getHeadlessEndpoints(), null));

				return "/export/edit_batch_planner_plan.jsp";
			}

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new EditBatchPlannerPlanDisplayContext(
					_batchPlannerPlanService.getBatchPlannerPlans(
						_portal.getCompanyId(renderRequest), false, true,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
					_getHeadlessEndpoints(), null));

			return "/import/edit_batch_planner_plan.jsp";
		}

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.getBatchPlannerPlan(batchPlannerPlanId);

		if (batchPlannerPlan.isExport()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new EditBatchPlannerPlanDisplayContext(
					_batchPlannerPlanService.getBatchPlannerPlans(
						_portal.getCompanyId(renderRequest), true, true,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
					_getHeadlessEndpoints(), batchPlannerPlan));

			return "/export/edit_batch_planner_plan.jsp";
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new EditBatchPlannerPlanDisplayContext(
				_batchPlannerPlanService.getBatchPlannerPlans(
					_portal.getCompanyId(renderRequest), false, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				_getHeadlessEndpoints(), batchPlannerPlan));

		return "/import/edit_batch_planner_plan.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditBatchPlannerPlanMVCRenderCommand.class);

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	@Reference
	private Portal _portal;

}