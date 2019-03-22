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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseAddLayoutMVCActionCommand
	extends BaseMVCActionCommand {

	protected String getContentRedirectURL(
			ActionRequest actionRequest, Layout layout)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutFullURL = portal.getLayoutFullURL(layout, themeDisplay);

		Layout draftLayout = layoutLocalService.fetchLayout(
			portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			layoutFullURL = portal.getLayoutFullURL(draftLayout, themeDisplay);
		}

		layoutFullURL = HttpUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);

		String backURL = ParamUtil.getString(actionRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", backURL);
		}

		return layoutFullURL;
	}

	protected String getRedirectURL(
		ActionRequest actionRequest, ActionResponse actionResponse,
		Layout layout) {

		LiferayPortletResponse liferayPortletResponse =
			portal.getLiferayPortletResponse(actionResponse);

		PortletURL configureLayoutURL =
			liferayPortletResponse.createRenderURL();

		configureLayoutURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");

		String backURL = ParamUtil.getString(actionRequest, "backURL");

		if (Validator.isNull(backURL)) {
			PortletURL redirectURL = liferayPortletResponse.createRenderURL();

			backURL = HttpUtil.setParameter(
				redirectURL.toString(), "p_p_state",
				WindowState.NORMAL.toString());
		}

		configureLayoutURL.setParameter("redirect", backURL);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		configureLayoutURL.setParameter("portletResource", portletResource);

		configureLayoutURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		configureLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		configureLayoutURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return configureLayoutURL.toString();
	}

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

}