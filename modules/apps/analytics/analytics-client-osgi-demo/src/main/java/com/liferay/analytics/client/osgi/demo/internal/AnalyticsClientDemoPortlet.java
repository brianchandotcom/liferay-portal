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

package com.liferay.analytics.client.osgi.demo.internal;

import com.liferay.analytics.client.AnalyticsClient;
import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Analytics Client Demo Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class AnalyticsClientDemoPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			AnalyticsEventsMessage.Builder analyticsEventsMessageBuilder =
				AnalyticsEventsMessage.builder("AnalyticsDemo");

			AnalyticsEventsMessage.Event.Builder eventBuilder =
				AnalyticsEventsMessage.Event.builder(
					"com.analytics.client.osgi.demo", "view");

			analyticsEventsMessageBuilder.event(eventBuilder.build());

			analyticsEventsMessageBuilder.protocolVersion("1.0");

			_analyticsClient.sendAnalytics(
				analyticsEventsMessageBuilder.build());
		}
		catch (Exception e) {
			_log.error("Unable to send analytics", e);
		}
	}

	@Reference(unbind = "-")
	protected void setAnalyticsClient(AnalyticsClient analyticsClient) {
		_analyticsClient = analyticsClient;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsClientDemoPortlet.class);

	private AnalyticsClient _analyticsClient;

}