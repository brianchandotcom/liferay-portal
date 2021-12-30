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

package com.liferay.commerce.health.status.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.health.status.CommerceHealthHttpStatus;
import com.liferay.commerce.health.status.CommerceHealthHttpStatusRegistry;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_HEALTH_CHECK,
		"mvc.command.name=/commerce_health_check/fix_commerce_health_status_issue"
	},
	service = MVCActionCommand.class
)
public class FixCommerceHealthStatusIssueMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		String key = ParamUtil.getString(actionRequest, "key");

		try {
			CommerceHealthHttpStatus commerceHealthHttpStatus =
				_commerceHealthHttpStatusRegistry.getCommerceHealthStatus(key);

			if (commerceHealthHttpStatus != null) {
				commerceHealthHttpStatus.fixIssue(httpServletRequest);

				Thread.sleep(2000);

				jsonObject.put(
					"success",
					commerceHealthHttpStatus.isFixed(
						_portal.getCompanyId(httpServletRequest),
						_portal.getScopeGroupId(httpServletRequest)));
			}
		}
		catch (Exception exception) {
			hideDefaultErrorMessage(actionRequest);

			_log.error(exception, exception);

			jsonObject.put(
				"error", exception.getMessage()
			).put(
				"success", false
			);
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		_writeJSON(actionResponse, jsonObject);

		hideDefaultSuccessMessage(actionRequest);
	}

	private void _writeJSON(ActionResponse actionResponse, Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FixCommerceHealthStatusIssueMVCActionCommand.class);

	@Reference
	private CommerceHealthHttpStatusRegistry _commerceHealthHttpStatusRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}