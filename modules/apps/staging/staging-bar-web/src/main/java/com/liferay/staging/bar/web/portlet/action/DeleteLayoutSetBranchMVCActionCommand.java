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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.LayoutSetBranchService;
import com.liferay.portal.util.PortalUtil;
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
		"mvc.command.name=deleteLayoutSetBranch"
	},
	service = MVCActionCommand.class
)
public class DeleteLayoutSetBranchMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long currentLayoutBranchId = ParamUtil.getLong(
			actionRequest, "currentLayoutBranchId");

		if (layoutSetBranchId == currentLayoutBranchId) {
			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE);
		}

		try {
			_layoutSetBranchService.deleteLayoutSetBranch(layoutSetBranchId);

			SessionMessages.add(actionRequest, "sitePageVariationDeleted");

			ActionUtil.addLayoutBranchSessionMessages(
				actionRequest, actionResponse);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {

		_layoutSetBranchService = layoutSetBranchService;
	}

	private volatile LayoutSetBranchService _layoutSetBranchService;

}