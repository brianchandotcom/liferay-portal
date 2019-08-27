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

package com.liferay.calendar.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.web.internal.constants.CalendarWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Fabio Pezzutto
 * @author Eduardo Lundgren
 * @author Pier Paolo Ramon
 */
public class CalendarBookingAssetRenderer
	extends BaseJSPAssetRenderer<CalendarBooking> implements TrashRenderer {

	public CalendarBookingAssetRenderer(
		CalendarBooking calendarBooking,
		ModelResourcePermission<Calendar> modelResourcePermission) {

		_calendarBooking = calendarBooking;
		_calendarModelResourcePermission = modelResourcePermission;
	}

	@Override
	public CalendarBooking getAssetObject() {
		return _calendarBooking;
	}

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	public long getClassPK() {
		return _calendarBooking.getCalendarBookingId();
	}

	@Override
	public long getGroupId() {
		return _calendarBooking.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<CalendarBooking> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _calendarBooking.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String summary = _calendarBooking.getDescription(
			getLocale(portletRequest));

		return StringUtil.shorten(HtmlUtil.stripHtml(summary), 200);
	}

	@Override
	public String getTitle(Locale locale) {
		return _calendarBooking.getTitle(locale);
	}

	@Override
	public String getType() {
		return CalendarBookingAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			_calendarBooking.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, CalendarPortletKeys.CALENDAR, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_calendar_booking.jsp");
		portletURL.setParameter(
			"calendarBookingId",
			String.valueOf(_calendarBooking.getCalendarBookingId()));

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			PortletURL portletURL = liferayPortletResponse.createRenderURL(
				CalendarPortletKeys.CALENDAR);

			portletURL.setParameter("mvcPath", "/view_calendar_booking.jsp");
			portletURL.setParameter(
				"returnToFullPageURL",
				PortalUtil.getCurrentURL(liferayPortletRequest));
			portletURL.setParameter(
				"calendarBookingId",
				String.valueOf(_calendarBooking.getCalendarBookingId()));
			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to get view in context URL", e);
		}

		return null;
	}

	@Override
	public long getUserId() {
		return _calendarBooking.getUserId();
	}

	@Override
	public String getUserName() {
		return _calendarBooking.getUserName();
	}

	@Override
	public String getUuid() {
		return _calendarBooking.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _calendarModelResourcePermission.contains(
			permissionChecker, _calendarBooking.getCalendar(),
			CalendarActionKeys.MANAGE_BOOKINGS);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _calendarModelResourcePermission.contains(
			permissionChecker, _calendarBooking.getCalendar(), ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(
			CalendarWebKeys.CALENDAR_BOOKING, _calendarBooking);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isCommentable() {
		try {
			Calendar calendar = _calendarBooking.getCalendar();

			return calendar.isEnableComments();
		}
		catch (Exception e) {
			_log.error("Unable to check commentable", e);
		}

		return false;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
	public boolean isRatable() {
		try {
			Calendar calendar = _calendarBooking.getCalendar();

			return calendar.isEnableRatings();
		}
		catch (Exception e) {
			_log.error("Unable to check ratable", e);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarBookingAssetRenderer.class);

	private final CalendarBooking _calendarBooking;
	private final ModelResourcePermission<Calendar>
		_calendarModelResourcePermission;

}