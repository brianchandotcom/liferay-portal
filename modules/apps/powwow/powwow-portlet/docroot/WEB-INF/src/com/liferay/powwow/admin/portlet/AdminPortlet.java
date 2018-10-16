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

package com.liferay.powwow.admin.portlet;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.service.PowwowServerLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Shinn Lok
 */
public class AdminPortlet extends MVCPortlet {

	public void deletePowwowServer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long powwowServerId = ParamUtil.getLong(
			actionRequest, "powwowServerId");

		PowwowServerLocalServiceUtil.deletePowwowServer(powwowServerId);
	}

	public void updatePowwowServer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long powwowServerId = ParamUtil.getLong(
			actionRequest, "powwowServerId");

		String name = ParamUtil.getString(actionRequest, "name");
		String providerType = ParamUtil.getString(
			actionRequest, "providerType");
		String url = ParamUtil.getString(actionRequest, "url");
		String apiKey = ParamUtil.getString(actionRequest, "apiKey");
		String secret = ParamUtil.getString(actionRequest, "secret");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PowwowServer.class.getName(), actionRequest);

		if (powwowServerId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PowwowServerLocalServiceUtil.addPowwowServer(
				themeDisplay.getUserId(), name, providerType, url, apiKey,
				secret, serviceContext);
		}
		else {
			PowwowServerLocalServiceUtil.updatePowwowServer(
				powwowServerId, name, providerType, url, apiKey, secret,
				serviceContext);
		}
	}

}