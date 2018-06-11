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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public abstract class BaseAddLayoutMVCActionCommand
	extends BaseMVCActionCommand {

	protected String getRedirectURL(
		ActionResponse actionResponse, Layout layout) {

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(actionResponse);

		PortletURL configureLayoutURL =
			liferayPortletResponse.createRenderURL();

		configureLayoutURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter("mvcRenderCommandName", "/layout/view");

		configureLayoutURL.setParameter("redirect", redirectURL.toString());

		configureLayoutURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		configureLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		configureLayoutURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return configureLayoutURL.toString();
	}

}