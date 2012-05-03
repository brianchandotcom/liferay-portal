/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.poller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.xuggler.XugglerInstallStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Sergio González
 */
public class AdminPollerProcessor extends BasePollerProcessor {

	@Override
	protected void doReceive(
		PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		getStatus(pollerRequest, pollerResponse);
	}

	@Override
	protected void doSend(PollerRequest pollerRequest) throws Exception {
		return;
	}

	protected void getStatus(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		HttpServletRequest originalRequest = pollerRequest.getOriginalRequest();
		HttpSession session = originalRequest.getSession();

		XugglerInstallStatus xugglerInstallStatus =
			(XugglerInstallStatus)session.getAttribute("xugglerInstallStatus");

		String status = "unknown";

		if (xugglerInstallStatus != null) {
			status = xugglerInstallStatus.getCurrentStatus();
		}

		boolean success = false;

		if (!status.equals("unknown")) {
			success = true;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("success", success);
		jsonObject.put("status", status);

		pollerResponse.setParameter("status", jsonObject);

		pollerResponse.setParameter(
			PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY,
			Boolean.TRUE.toString());
	}

}