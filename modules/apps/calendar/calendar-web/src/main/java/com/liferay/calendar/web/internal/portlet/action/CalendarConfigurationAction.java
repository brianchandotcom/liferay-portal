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

package com.liferay.calendar.web.internal.portlet.action;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
	service = ConfigurationAction.class
)
public class CalendarConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		updateDisplaySettings(actionRequest, actionResponse);
		updateUserSettings(actionRequest, actionResponse);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.calendar.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected void updateDisplaySettings(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		boolean displaySchedulerHeader = ParamUtil.getBoolean(
			actionRequest, "displaySchedulerHeader");
		boolean displaySchedulerOnly = ParamUtil.getBoolean(
			actionRequest, "displaySchedulerOnly");
		int eventsPerPage = ParamUtil.getInteger(
			actionRequest, "eventsPerPage");
		int maxDaysDisplayed = ParamUtil.getInteger(
			actionRequest, "maxDaysDisplayed");
		boolean showUserEvents = ParamUtil.getBoolean(
			actionRequest, "showUserEvents");
		boolean showAgendaView = ParamUtil.getBoolean(
			actionRequest, "showAgendaView");
		boolean showDayView = ParamUtil.getBoolean(
			actionRequest, "showDayView");
		boolean showWeekView = ParamUtil.getBoolean(
			actionRequest, "showWeekView");
		boolean showMonthView = ParamUtil.getBoolean(
			actionRequest, "showMonthView");

		portletPreferences.setValue(
			"displaySchedulerHeader", String.valueOf(displaySchedulerHeader));
		portletPreferences.setValue(
			"displaySchedulerOnly", String.valueOf(displaySchedulerOnly));
		portletPreferences.setValue(
			"eventsPerPage", String.valueOf(eventsPerPage));
		portletPreferences.setValue(
			"maxDaysDisplayed", String.valueOf(maxDaysDisplayed));
		portletPreferences.setValue(
			"showAgendaView", String.valueOf(showAgendaView));
		portletPreferences.setValue("showDayView", String.valueOf(showDayView));
		portletPreferences.setValue(
			"showWeekView", String.valueOf(showWeekView));
		portletPreferences.setValue(
			"showMonthView", String.valueOf(showMonthView));
		portletPreferences.setValue(
			"showUserEvents", String.valueOf(showUserEvents));

		portletPreferences.store();
	}

	protected void updateUserSettings(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		int defaultDuration = ParamUtil.getInteger(
			actionRequest, "defaultDuration");
		String defaultView = ParamUtil.getString(actionRequest, "defaultView");
		String timeFormat = ParamUtil.getString(actionRequest, "timeFormat");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		boolean usePortalTimeZone = ParamUtil.getBoolean(
			actionRequest, "usePortalTimeZone");
		int weekStartsOn = ParamUtil.getInteger(actionRequest, "weekStartsOn");

		portletPreferences.setValue(
			"defaultDuration", String.valueOf(defaultDuration));
		portletPreferences.setValue("defaultView", defaultView);
		portletPreferences.setValue("timeFormat", timeFormat);
		portletPreferences.setValue("timeZoneId", timeZoneId);
		portletPreferences.setValue(
			"usePortalTimeZone", String.valueOf(usePortalTimeZone));
		portletPreferences.setValue(
			"weekStartsOn", String.valueOf(weekStartsOn));

		SessionClicks.put(
			_portal.getHttpServletRequest(actionRequest),
			"com.liferay.calendar.web_defaultView", defaultView);

		portletPreferences.store();
	}

	@Reference
	private Portal _portal;

}