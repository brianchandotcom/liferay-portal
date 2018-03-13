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
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.ViewUADApplicationsSummaryDisplay;
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
		"mvc.command.name=/view_uad_applications_summary"
	},
	service = MVCRenderCommand.class
)
public class ViewUADApplicationsSummaryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long selUserId = ParamUtil.getLong(renderRequest, "selUserId");

		ViewUADApplicationsSummaryDisplay viewUADApplicationsSummaryDisplay =
			new ViewUADApplicationsSummaryDisplay();

		viewUADApplicationsSummaryDisplay.setSearchContainer(
			_uadApplicationSummaryHelper.createSearchContainer(
				renderRequest, renderResponse, selUserId));

		renderRequest.setAttribute(
			UADWebKeys.VIEW_UAD_APPLICATIONS_SUMMARY_DISPLAY,
			viewUADApplicationsSummaryDisplay);

		return "/view_uad_applications_summary.jsp";
	}

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

}