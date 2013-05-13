/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class CalEventAssetRenderer extends BaseAssetRenderer {

	public CalEventAssetRenderer(CalEvent event) {
		_event = event;
	}

	@Override
	public String getClassName() {
		return CalEvent.class.getName();
	}

	@Override
	public long getClassPK() {
		return _event.getEventId();
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.CALENDAR_EVENT_COMMENTS_ENABLED) {
			return "edit_event_discussion";
		}
		else {
			return null;
		}
	}

	@Override
	public Date getDisplayDate() {
		return _event.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _event.getGroupId();
	}

	@Override
	public String getSummary(Locale locale) {
		return HtmlUtil.extractText(_event.getDescription());
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/calendar.png";
	}

	@Override
	public String getTitle(Locale locale) {
		return _event.getTitle();
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.CALENDAR,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/calendar/edit_event");
		portletURL.setParameter("eventId", String.valueOf(_event.getEventId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			PortletKeys.CALENDAR, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/calendar/view_event");
		portletURL.setParameter("eventId", String.valueOf(_event.getEventId()));
		portletURL.setWindowState(windowState);

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, "/calendar/find_event",
			"eventId", _event.getEventId());
	}

	@Override
	public long getUserId() {
		return _event.getUserId();
	}

	@Override
	public String getUserName() {
		return _event.getUserName();
	}

	@Override
	public String getUuid() {
		return _event.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return CalEventPermission.contains(
			permissionChecker, _event, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return CalEventPermission.contains(
			permissionChecker, _event, ActionKeys.VIEW);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.CALENDAR_EVENT, _event);

			return "/html/portlet/calendar/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/date.png";
	}

	private CalEvent _event;

}