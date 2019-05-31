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

package com.liferay.calendar.service.impl;

import com.liferay.calendar.configuration.CalendarServiceConfigurationValues;
import com.liferay.calendar.exception.CalendarNameException;
import com.liferay.calendar.exception.RequiredCalendarException;
import com.liferay.calendar.exporter.CalendarDataFormat;
import com.liferay.calendar.exporter.CalendarDataHandler;
import com.liferay.calendar.exporter.CalendarDataHandlerFactory;
import com.liferay.calendar.internal.util.CalendarUtil;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.calendar.service.base.CalendarLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 * @author Andrea Di Giorgi
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.Calendar",
	service = AopService.class
)
public class CalendarLocalServiceImpl extends CalendarLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Calendar addCalendar(
			long userId, long groupId, long calendarResourceId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String timeZoneId, int color, boolean defaultCalendar,
			boolean enableComments, boolean enableRatings,
			ServiceContext serviceContext)
		throws PortalException {

		// Calendar

		User user = userLocalService.getUser(userId);

		if (color <= 0) {
			color = CalendarServiceConfigurationValues.CALENDAR_COLOR_DEFAULT;
		}

		Date now = new Date();

		validate(nameMap);

		long calendarId = counterLocalService.increment();

		Calendar calendar = calendarPersistence.create(calendarId);

		calendar.setUuid(serviceContext.getUuid());
		calendar.setGroupId(groupId);
		calendar.setCompanyId(user.getCompanyId());
		calendar.setUserId(user.getUserId());
		calendar.setUserName(user.getFullName());
		calendar.setCreateDate(serviceContext.getCreateDate(now));
		calendar.setModifiedDate(serviceContext.getModifiedDate(now));
		calendar.setCalendarResourceId(calendarResourceId);
		calendar.setNameMap(nameMap);
		calendar.setDescriptionMap(descriptionMap);
		calendar.setTimeZoneId(timeZoneId);
		calendar.setColor(color);
		calendar.setDefaultCalendar(defaultCalendar);
		calendar.setEnableComments(enableComments);
		calendar.setEnableRatings(enableRatings);

		calendarPersistence.update(calendar);

		// Resources

		resourceLocalService.addModelResources(calendar, serviceContext);

		// Calendar

		updateDefaultCalendar(calendar);

		return calendar;
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public Calendar deleteCalendar(Calendar calendar) throws PortalException {
		if (calendar.isDefaultCalendar()) {
			throw new RequiredCalendarException();
		}

		// Calendar

		calendarPersistence.remove(calendar);

		// Resources

		resourceLocalService.deleteResource(
			calendar, ResourceConstants.SCOPE_INDIVIDUAL);

		// Calendar bookings

		_calendarBookingLocalService.deleteCalendarBookings(
			calendar.getCalendarId());

		// Calendar notification templates

		_calendarNotificationTemplateLocalService.
			deleteCalendarNotificationTemplates(calendar.getCalendarId());

		return calendar;
	}

	@Override
	public Calendar deleteCalendar(long calendarId) throws PortalException {
		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		return calendarLocalService.deleteCalendar(calendar);
	}

	@Override
	public String exportCalendar(long calendarId, String type)
		throws Exception {

		CalendarDataFormat calendarDataFormat = CalendarDataFormat.parse(type);

		CalendarDataHandler calendarDataHandler =
			CalendarDataHandlerFactory.getCalendarDataHandler(
				calendarDataFormat);

		return calendarDataHandler.exportCalendar(calendarId);
	}

	@Override
	public Calendar fetchCalendar(long calendarId) {
		return calendarPersistence.fetchByPrimaryKey(calendarId);
	}

	@Override
	public Calendar fetchGroupCalendar(
		long companyId, long groupId, String name) {

		List<Calendar> calendars = calendarFinder.findByC_G_C_N_D(
			companyId, new long[] {groupId}, null, name, null, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (!calendars.isEmpty()) {
			return calendars.get(0);
		}

		return null;
	}

	@Override
	public Calendar getCalendar(long calendarId) throws PortalException {
		return calendarPersistence.findByPrimaryKey(calendarId);
	}

	@Override
	public List<Calendar> getCalendarResourceCalendars(
		long groupId, long calendarResourceId) {

		return calendarPersistence.findByG_C(groupId, calendarResourceId);
	}

	@Override
	public List<Calendar> getCalendarResourceCalendars(
		long groupId, long calendarResourceId, boolean defaultCalendar) {

		return calendarPersistence.findByG_C_D(
			groupId, calendarResourceId, defaultCalendar);
	}

	@Override
	public boolean hasStagingCalendar(Calendar calendar)
		throws PortalException {

		long liveGroupId = calendar.getGroupId();

		try {
			Group stagingGroup = groupLocalService.getStagingGroup(liveGroupId);

			Calendar stagedCalendar =
				calendarLocalService.fetchCalendarByUuidAndGroupId(
					calendar.getUuid(), stagingGroup.getGroupId());

			if (stagedCalendar == null) {
				return false;
			}

			return true;
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}

			return false;
		}
	}

	@Override
	public void importCalendar(long calendarId, String data, String type)
		throws Exception {

		CalendarDataFormat calendarDataFormat = CalendarDataFormat.parse(type);

		CalendarDataHandler calendarDataHandler =
			CalendarDataHandlerFactory.getCalendarDataHandler(
				calendarDataFormat);

		calendarDataHandler.importCalendar(calendarId, data);
	}

	@Override
	public boolean isStagingCalendar(Calendar calendar) {
		return CalendarUtil.isStagingCalendar(calendar, groupLocalService);
	}

	@Override
	public List<Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String keywords, boolean andOperator, int start, int end,
		OrderByComparator<Calendar> orderByComparator) {

		return calendarFinder.findByKeywords(
			companyId, groupIds, calendarResourceIds, keywords, start, end,
			orderByComparator);
	}

	@Override
	public List<Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String name, String description, boolean andOperator, int start,
		int end, OrderByComparator<Calendar> orderByComparator) {

		return calendarFinder.findByC_G_C_N_D(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String keywords, boolean andOperator) {

		return calendarFinder.countByKeywords(
			companyId, groupIds, calendarResourceIds, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String name, String description, boolean andOperator) {

		return calendarFinder.countByC_G_C_N_D(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator);
	}

	@Override
	public void updateCalendar(long calendarId, boolean defaultCalendar)
		throws PortalException {

		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		calendar.setDefaultCalendar(defaultCalendar);

		calendarPersistence.update(calendar);

		updateDefaultCalendar(calendar);
	}

	@Override
	public Calendar updateCalendar(
			long calendarId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int color,
			ServiceContext serviceContext)
		throws PortalException {

		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		return calendarLocalService.updateCalendar(
			calendarId, nameMap, descriptionMap, calendar.getTimeZoneId(),
			color, calendar.isDefaultCalendar(), calendar.isEnableComments(),
			calendar.isEnableRatings(), serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Calendar updateCalendar(
			long calendarId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String timeZoneId, int color,
			boolean defaultCalendar, boolean enableComments,
			boolean enableRatings, ServiceContext serviceContext)
		throws PortalException {

		// Calendar

		if (color <= 0) {
			color = CalendarServiceConfigurationValues.CALENDAR_COLOR_DEFAULT;
		}

		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		validate(nameMap);

		calendar.setModifiedDate(serviceContext.getModifiedDate(null));
		calendar.setNameMap(nameMap);
		calendar.setDescriptionMap(descriptionMap);
		calendar.setTimeZoneId(timeZoneId);
		calendar.setColor(color);
		calendar.setDefaultCalendar(defaultCalendar);
		calendar.setEnableComments(enableComments);
		calendar.setEnableRatings(enableRatings);

		calendarPersistence.update(calendar);

		// Calendar

		updateDefaultCalendar(calendar);

		return calendar;
	}

	@Override
	public Calendar updateColor(
			long calendarId, int color, ServiceContext serviceContext)
		throws PortalException {

		if (color <= 0) {
			color = CalendarServiceConfigurationValues.CALENDAR_COLOR_DEFAULT;
		}

		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		calendar.setModifiedDate(serviceContext.getModifiedDate(null));
		calendar.setColor(color);

		calendarPersistence.update(calendar);

		return calendar;
	}

	protected void updateDefaultCalendar(Calendar calendar)
		throws PortalException {

		if (!calendar.isDefaultCalendar()) {
			return;
		}

		List<Calendar> defaultCalendars = getCalendarResourceCalendars(
			calendar.getGroupId(), calendar.getCalendarResourceId(), true);

		for (Calendar defaultCalendar : defaultCalendars) {
			if (defaultCalendar.getCalendarId() == calendar.getCalendarId()) {
				continue;
			}

			updateCalendar(defaultCalendar.getCalendarId(), false);
		}
	}

	protected void validate(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(locale))) {
			throw new CalendarNameException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarLocalServiceImpl.class);

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private CalendarNotificationTemplateLocalService
		_calendarNotificationTemplateLocalService;

}