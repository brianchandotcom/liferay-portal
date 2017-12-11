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

package com.liferay.commerce.wizard.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.product.util.CommerceStarterRegistry;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_WIZARD,
		"mvc.command.name=applyCommerceStarter"
	},
	service = MVCActionCommand.class
)
public class ApplyCommerceStarterMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			String commerceStarterKey = ParamUtil.getString(
				actionRequest, "commerceStarterKey");

			CommerceStarter commerceStarter =
				_commerceStarterRegistry.getCommerceStarter(commerceStarterKey);

			commerceStarter.create(httpServletRequest);

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e);

			jsonObject.put("message", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		writeJSON(actionResponse, jsonObject);
	}

	protected void writeJSON(ActionResponse actionResponse, Object jsonObj)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonObj.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplyCommerceStarterMVCActionCommand.class);

	@Reference
	private CommerceStarterRegistry _commerceStarterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}