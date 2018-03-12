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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;
import com.liferay.user.associated.data.web.internal.display.ManageUADApplicationsSummaryDisplay;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/manage_user_associated_data_applications_summary"
	},
	service = MVCRenderCommand.class
)
public class ManageUADApplicationsSummaryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long selUserId = ParamUtil.getLong(renderRequest, "selUserId");

		ManageUADApplicationsSummaryDisplay
			manageUADApplicationsSummaryDisplay =
				new ManageUADApplicationsSummaryDisplay();

		manageUADApplicationsSummaryDisplay.setSearchContainer(
			_uadApplicationSummaryHelper.createSearchContainer(
				renderRequest, renderResponse, selUserId));

		renderRequest.setAttribute(
			UserAssociatedDataWebKeys.MANAGE_UAD_APPLICATIONS_SUMMARY_DISPLAY,
			manageUADApplicationsSummaryDisplay);

		return "/manage_user_associated_data_applications_summary.jsp";
	}

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

}