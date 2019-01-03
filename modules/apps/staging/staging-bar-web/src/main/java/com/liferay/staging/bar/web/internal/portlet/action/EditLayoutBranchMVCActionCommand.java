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

package com.liferay.staging.bar.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.LayoutBranchNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutBranchService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.staging.bar.web.internal.portlet.constants.StagingBarPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StagingBarPortletKeys.STAGING_BAR,
		"mvc.command.name=editLayoutBranch"
	},
	service = MVCActionCommand.class
)
public class EditLayoutBranchMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "copyLayoutRevisionId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			if (layoutBranchId <= 0) {
				_layoutBranchService.addLayoutBranch(
					layoutRevisionId, name, description, false, serviceContext);

				SessionMessages.add(actionRequest, "pageVariationAdded");
			}
			else {
				_layoutBranchService.updateLayoutBranch(
					layoutBranchId, name, description, serviceContext);

				SessionMessages.add(actionRequest, "pageVariationUpdated");
			}

			ActionUtil.addLayoutBranchSessionMessages(
				actionRequest, actionResponse);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);

			if (e instanceof LayoutBranchNameException) {
				actionResponse.setRenderParameter(
					"mvcPath", "/edit_layout_branch.jsp");
			}
			else {
				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutBranchService(
		LayoutBranchService layoutBranchService) {

		_layoutBranchService = layoutBranchService;
	}

	private LayoutBranchService _layoutBranchService;

}