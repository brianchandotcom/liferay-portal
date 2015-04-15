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

package com.liferay.web.proxy.web.portlet.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.web.proxy.web.constants.WebProxyPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + WebProxyPortletKeys.WEB_PROXY},
	service = ConfigurationAction.class
)
public class WebProxyConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String initUrl = getParameter(actionRequest, "initUrl");

		if (!initUrl.startsWith("/") &&
			!StringUtil.startsWith(initUrl, "http://") &&
			!StringUtil.startsWith(initUrl, "https://") &&
			!StringUtil.startsWith(initUrl, "mhtml://")) {

			initUrl = HttpUtil.getProtocol(actionRequest) + "://" + initUrl;
		}

		setPreference(actionRequest, "initUrl", initUrl);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}