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
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.Format;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class CalendarBookingCTDisplayRenderer
	extends BaseCTDisplayRenderer<CalendarBooking> {

	@Override
	public String[] getAvailableLanguageIds(CalendarBooking calendarBooking) {
		return calendarBooking.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(CalendarBooking calendarBooking) {
		return calendarBooking.getDefaultLanguageId();
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			CalendarBooking calendarBooking)
		throws Exception {

		Group group = _groupLocalService.getGroup(calendarBooking.getGroupId());

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
			"/edit_calendar_booking.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"calendarBookingId", calendarBooking.getCalendarBookingId()
		).buildString();
	}

	@Override
	public Class<CalendarBooking> getModelClass() {
		return CalendarBooking.class;
	}

	@Override
	public String getTitle(Locale locale, CalendarBooking calendarBooking) {
		return calendarBooking.getTitle(locale);
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<CalendarBooking> displayBuilder) {

		CalendarBooking calendarBooking = displayBuilder.getModel();

		DisplayContext<CalendarBooking> displayContext =
			displayBuilder.getDisplayContext();

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		TimeZone timeZone = themeDisplay.getTimeZone();

		if (calendarBooking.isAllDay()) {
			timeZone = TimeZone.getTimeZone(StringPool.UTC);
		}

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			displayBuilder.getLocale(), timeZone);

		displayBuilder.display(
			"title", calendarBooking.getTitle(displayBuilder.getLocale())
		).display(
			"description",
			calendarBooking.getDescription(displayBuilder.getLocale()), false
		).display(
			"status", calendarBooking.getStatus()
		).display(
			"starts", dateFormatDateTime.format(calendarBooking.getStartTime())
		).display(
			"ends", dateFormatDateTime.format(calendarBooking.getEndTime())
		).display(
			"location",
			() -> {
				String location = calendarBooking.getLocation();

				if (Validator.isNotNull(location)) {
					return location;
				}

				return null;
			}
		).display(
			"repeat",
			() -> {
				if (Validator.isNotNull(calendarBooking.getRecurrence())) {
					Recurrence recurrence = calendarBooking.getRecurrenceObj();

					Frequency frequency = recurrence.getFrequency();

					return frequency.getValue();
				}

				return null;
			}
		).display(
			"resources",
			() -> {
				List<CalendarBooking> childCalendarBookings =
					calendarBooking.getChildCalendarBookings();

				if (!childCalendarBookings.isEmpty()) {
					StringBundler sb = new StringBundler(
						2 * childCalendarBookings.size());

					for (CalendarBooking childCalendarBooking :
							childCalendarBookings) {

						CalendarResource calendarResource =
							childCalendarBooking.getCalendarResource();

						sb.append(
							calendarResource.getName(
								displayBuilder.getLocale()));

						sb.append(StringPool.COMMA_AND_SPACE);
					}

					sb.setIndex(sb.index() - 1);

					return sb.toString();
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