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

package com.liferay.portlet.calendar.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.permission.CalendarPermission;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class CalendarPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "calendar";

	public CalendarPortletDataHandler() {
		setDeletionEventClassNames(CalEvent.class.getName());
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "events", true, true, null,
				CalEvent.class.getName()));
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				CalendarPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		CalEventLocalServiceUtil.deleteEvents(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			CalendarPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		List<CalEvent> events = CalEventUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (CalEvent event : events) {
			exportEvent(portletDataContext, rootElement, event);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			CalendarPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element rootElement = portletDataContext.getImportDataRootElement();

		for (Element eventElement : rootElement.elements("event")) {
			String path = eventElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			CalEvent event = (CalEvent)portletDataContext.getZipEntryAsObject(
				path);

			importEvent(portletDataContext, eventElement, event);
		}

		return null;
	}

	protected void exportEvent(
			PortletDataContext portletDataContext, Element rootElement,
			CalEvent event)
		throws PortalException, SystemException {

		if (!portletDataContext.isWithinDateRange(event.getModifiedDate())) {
			return;
		}

		String path = getEventPath(portletDataContext, event);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element eventElement = rootElement.addElement("event");

		portletDataContext.addClassedModel(
			eventElement, path, event, NAMESPACE);
	}

	protected String getEventPath(
		PortletDataContext portletDataContext, CalEvent event) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.CALENDAR));
		sb.append("/events/");
		sb.append(event.getEventId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEvent(
			PortletDataContext portletDataContext, Element eventElement,
			CalEvent event)
		throws Exception {

		long userId = portletDataContext.getUserId(event.getUserUuid());

		Date startDate = event.getStartDate();

		int startDateMonth = 0;
		int startDateDay = 0;
		int startDateYear = 0;
		int startDateHour = 0;
		int startDateMinute = 0;

		if (startDate != null) {
			Locale locale = null;
			TimeZone timeZone = null;

			if (event.getTimeZoneSensitive()) {
				User user = UserLocalServiceUtil.getUser(userId);

				locale = user.getLocale();
				timeZone = user.getTimeZone();
			}
			else {
				locale = LocaleUtil.getDefault();
				timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
			}

			Calendar startCal = CalendarFactoryUtil.getCalendar(
				timeZone, locale);

			startCal.setTime(startDate);

			startDateMonth = startCal.get(Calendar.MONTH);
			startDateDay = startCal.get(Calendar.DATE);
			startDateYear = startCal.get(Calendar.YEAR);
			startDateHour = startCal.get(Calendar.HOUR);
			startDateMinute = startCal.get(Calendar.MINUTE);

			if (startCal.get(Calendar.AM_PM) == Calendar.PM) {
				startDateHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			eventElement, event, NAMESPACE);

		CalEvent importedEvent = null;

		if (portletDataContext.isDataStrategyMirror()) {
			CalEvent existingEvent = CalEventUtil.fetchByUUID_G(
				event.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEvent == null) {
				serviceContext.setUuid(event.getUuid());

				importedEvent = CalEventLocalServiceUtil.addEvent(
					userId, event.getTitle(), event.getDescription(),
					event.getLocation(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					event.getDurationHour(), event.getDurationMinute(),
					event.isAllDay(), event.isTimeZoneSensitive(),
					event.getType(), event.getRepeating(),
					event.getRecurrenceObj(), event.getRemindBy(),
					event.getFirstReminder(), event.getSecondReminder(),
					serviceContext);
			}
			else {
				importedEvent = CalEventLocalServiceUtil.updateEvent(
					userId, existingEvent.getEventId(), event.getTitle(),
					event.getDescription(), event.getLocation(), startDateMonth,
					startDateDay, startDateYear, startDateHour, startDateMinute,
					event.getDurationHour(), event.getDurationMinute(),
					event.isAllDay(), event.isTimeZoneSensitive(),
					event.getType(), event.getRepeating(),
					event.getRecurrenceObj(), event.getRemindBy(),
					event.getFirstReminder(), event.getSecondReminder(),
					serviceContext);
			}
		}
		else {
			importedEvent = CalEventLocalServiceUtil.addEvent(
				userId, event.getTitle(), event.getDescription(),
				event.getLocation(), startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute,
				event.getDurationHour(), event.getDurationMinute(),
				event.isAllDay(), event.isTimeZoneSensitive(), event.getType(),
				event.getRepeating(), event.getRecurrenceObj(),
				event.getRemindBy(), event.getFirstReminder(),
				event.getSecondReminder(), serviceContext);
		}

		portletDataContext.importClassedModel(event, importedEvent, NAMESPACE);
	}

}