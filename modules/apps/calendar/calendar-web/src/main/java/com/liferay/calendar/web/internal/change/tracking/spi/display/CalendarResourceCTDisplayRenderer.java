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

package com.liferay.calendar.web.internal.change.tracking.spi.display;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class CalendarResourceCTDisplayRenderer
	extends BaseCTDisplayRenderer<CalendarResource> {

	@Override
	public String[] getAvailableLanguageIds(CalendarResource calendarResource) {
		return calendarResource.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(CalendarResource calendarResource) {
		return calendarResource.getDefaultLanguageId();
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			CalendarResource calendarResource)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			calendarResource.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, CalendarPortletKeys.CALENDAR, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_calendar_resource.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"calendarResourceId", calendarResource.getCalendarResourceId()
		).buildString();
	}

	@Override
	public Class<CalendarResource> getModelClass() {
		return CalendarResource.class;
	}

	@Override
	public String getTitle(Locale locale, CalendarResource calendarResource) {
		return calendarResource.getName(locale);
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<CalendarResource> displayBuilder) {

		CalendarResource calendarResource = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", calendarResource.getName(locale)
		).display(
			"description", calendarResource.getDescription(locale)
		).display(
			"active", calendarResource.isActive()
		).display(
			"default-calendar",
			() -> {
				Calendar defaultCalendar =
					calendarResource.getDefaultCalendar();

				if (defaultCalendar != null) {
					return defaultCalendar.getName(locale);
				}

				return null;
			}
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}