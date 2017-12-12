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

package com.liferay.lcs.messaging.echo.sample1.web.internal.portlet;

import com.liferay.lcs.messaging.bus.LCSMessageBusService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"javax.portlet.display-name=LCS Messaging Echo Sample1",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=LCS Messaging Echo Sample1",
		"javax.portlet.info.short-title=LCS Messaging Echo Sample1",
		"javax.portlet.info.title=LCS Messaging Echo Sample1",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.mime-type=text/html",
		"javax.portlet.name=com_liferay_lcs_messaging_echo_sample1_web_portlet_EchoPortlet",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class EchoPortlet extends MVCPortlet {

	public void sendMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		if (_log.isInfoEnabled()) {
			_log.info("Sending message to OSB LCS Echo service");
		}

		_lcsMessageBusService.sendMessage(
			"osb_lcs_echo", null, "LCS Echo Simple", "lcs_echo");
	}

	private static final Log _log = LogFactoryUtil.getLog(EchoPortlet.class);

	@Reference
	private LCSMessageBusService _lcsMessageBusService;

}