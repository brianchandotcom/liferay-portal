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

package com.liferay.staging.bar.web.portlet.action;

import com.liferay.portal.LayoutSetBranchNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.LayoutSetBranchConstants;
import com.liferay.portal.service.LayoutSetBranchLocalService;
import com.liferay.portal.service.LayoutSetBranchService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.staging.bar.web.portlet.constants.StagingBarPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StagingBarPortletKeys.STAGING_BAR,
		"mvc.command.name=editLayoutSetBranch"
	},
	service = MVCActionCommand.class
)
public class EditLayoutSetBranchMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		long copyLayoutSetBranchId = ParamUtil.getLong(
			actionRequest, "copyLayoutSetBranchId",
			LayoutSetBranchConstants.ALL_BRANCHES);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			if (layoutSetBranchId <= 0) {
				_layoutSetBranchService.addLayoutSetBranch(
					groupId, privateLayout, name, description, false,
					copyLayoutSetBranchId, serviceContext);

				SessionMessages.add(actionRequest, "sitePageVariationAdded");
			}
			else {
				_layoutSetBranchLocalService.updateLayoutSetBranch(
					layoutSetBranchId, name, description, serviceContext);

				SessionMessages.add(actionRequest, "sitePageVariationUpdated");
			}

			ActionUtil.addLayoutBranchSessionMessages(
				actionRequest, actionResponse);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);

			if (e instanceof LayoutSetBranchNameException) {
				actionResponse.setRenderParameter(
					"mvcPath", "/edit_layout_set_branch.jsp");
			}
			else {
				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {

		_layoutSetBranchService = layoutSetBranchService;
	}

	private volatile LayoutSetBranchLocalService _layoutSetBranchLocalService;
	private volatile LayoutSetBranchService _layoutSetBranchService;

}