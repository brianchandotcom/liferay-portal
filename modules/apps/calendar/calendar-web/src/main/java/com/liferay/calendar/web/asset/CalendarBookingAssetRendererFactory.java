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

package com.liferay.calendar.web.asset;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.permission.CalendarPermission;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabio Pezzutto
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CalendarPortletKeys.CALENDAR},
	service = AssetRendererFactory.class
)
public class CalendarBookingAssetRendererFactory
	extends BaseAssetRendererFactory<CalendarBooking> {

	public static final String TYPE = "calendar";

	public CalendarBookingAssetRendererFactory() {
		setLinkable(true);
		setClassName(CalendarBooking.class.getName());
		setPortletId(CalendarPortletKeys.CALENDAR);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<CalendarBooking> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(classPK);

		CalendarBookingAssetRenderer calendarBookingAssetRenderer =
			new CalendarBookingAssetRenderer(calendarBooking);

		calendarBookingAssetRenderer.setAssetRendererType(type);
		calendarBookingAssetRenderer.setServletContext(_servletContext);

		return calendarBookingAssetRenderer;
	}

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-calendar";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Deprecated
	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CalendarResource calendarResource =
			CalendarResourceUtil.getScopeGroupCalendarResource(
				liferayPortletRequest, themeDisplay.getScopeGroupId());

		if (calendarResource == null) {
			return null;
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, CalendarPortletKeys.CALENDAR,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_calendar_booking.jsp");

		Calendar calendar = calendarResource.getDefaultCalendar();

		portletURL.setParameter(
			"calendarId", String.valueOf(calendar.getCalendarId()));

		return portletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(permissionChecker.getCompanyId());

		CalendarResource calendarResource =
			CalendarResourceUtil.getScopeGroupCalendarResource(
				groupId, serviceContext);

		if (calendarResource == null) {
			return false;
		}

		Calendar calendar = calendarResource.getDefaultCalendar();

		return CalendarPermission.contains(
			permissionChecker, calendar.getCalendarId(),
			CalendarActionKeys.MANAGE_BOOKINGS);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(classPK);

		if (actionId.equals(ActionKeys.DELETE) ||
			actionId.equals(ActionKeys.UPDATE)) {

			actionId = CalendarActionKeys.MANAGE_BOOKINGS;
		}

		return CalendarPermission.contains(
			permissionChecker, calendarBooking.getCalendarId(), actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.calendar.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/date.png";
	}

	@Reference(unbind = "-")
	protected void setCalendarBookingLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	private volatile CalendarBookingLocalService _calendarBookingLocalService;
	private volatile ServletContext _servletContext;

}