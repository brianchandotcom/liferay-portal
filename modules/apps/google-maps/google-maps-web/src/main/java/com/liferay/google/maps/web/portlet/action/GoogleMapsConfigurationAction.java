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

package com.liferay.google.maps.web.portlet.action;

import com.liferay.google.maps.web.constants.GoogleMapsPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mark Wong
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + GoogleMapsPortletKeys.GOOGLE_MAPS},
	service = ConfigurationAction.class
)
public class GoogleMapsConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletSession portletSession = actionRequest.getPortletSession();

		portletSession.removeAttribute(
			PortalUtil.getPortletNamespace(portletResource) + "mapAddress",
			PortletSession.APPLICATION_SCOPE);

		portletSession.removeAttribute(
			PortalUtil.getPortletNamespace(portletResource) +
				"directionsAddress",
			PortletSession.APPLICATION_SCOPE);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}