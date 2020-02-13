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

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.base.CalendarServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Iterator;
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
	property = {
		"json.web.service.context.name=calendar",
		"json.web.service.context.path=Calendar"
	},
	service = AopService.class
)
public class CalendarServiceImpl extends CalendarServiceBaseImpl {

	@Override
	public Calendar addCalendar(
			long groupId, long calendarResourceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String timeZoneId, int color,
			boolean defaultCalendar, boolean enableComments,
			boolean enableRatings, ServiceContext serviceContext)
		throws PortalException {

		_calendarResourceModelResourcePermission.check(
			getPermissionChecker(), calendarResourceId,
			CalendarActionKeys.ADD_CALENDAR);

		return calendarLocalService.addCalendar(
			getUserId(), groupId, calendarResourceId, nameMap, descriptionMap,
			timeZoneId, color, defaultCalendar, enableComments, enableRatings,
			serviceContext);
	}

	@Override
	public Calendar deleteCalendar(long calendarId) throws PortalException {
		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.DELETE);

		return calendarLocalService.deleteCalendar(calendarId);
	}

	@Override
	public String exportCalendar(long calendarId, String type)
		throws Exception {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId,
			CalendarActionKeys.VIEW_BOOKING_DETAILS);

		return calendarLocalService.exportCalendar(calendarId, type);
	}

	@Override
	public Calendar fetchCalendar(long calendarId) throws PortalException {
		Calendar calendar = calendarPersistence.fetchByPrimaryKey(calendarId);

		if (calendar == null) {
			return null;
		}

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendar, ActionKeys.VIEW);

		return calendar;
	}

	@Override
	public Calendar getCalendar(long calendarId) throws PortalException {
		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.VIEW);

		return calendarLocalService.getCalendar(calendarId);
	}

	@Override
	public List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId)
		throws PortalException {

		_calendarResourceModelResourcePermission.check(
			getPermissionChecker(), calendarResourceId, ActionKeys.VIEW);

		return calendarLocalService.getCalendarResourceCalendars(
			groupId, calendarResourceId);
	}

	@Override
	public List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId, boolean defaultCalendar)
		throws PortalException {

		_calendarResourceModelResourcePermission.check(
			getPermissionChecker(), calendarResourceId, ActionKeys.VIEW);

		return calendarLocalService.getCalendarResourceCalendars(
			groupId, calendarResourceId, defaultCalendar);
	}

	@Override
	public void importCalendar(long calendarId, String data, String type)
		throws Exception {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.UPDATE);

		calendarLocalService.importCalendar(calendarId, data, type);
	}

	@Override
	public boolean isManageableFromGroup(long calendarId, long groupId)
		throws PortalException {

		if (!_calendarModelResourcePermission.contains(
				getPermissionChecker(), calendarId,
				CalendarActionKeys.MANAGE_BOOKINGS)) {

			return false;
		}

		Calendar calendar = null;

		try {
			calendar = getCalendar(calendarId);
		}
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(principalException, principalException);
			}

			return false;
		}

		if (calendarLocalService.hasStagingCalendar(calendar)) {
			return false;
		}

		if (calendarLocalService.isStagingCalendar(calendar)) {
			if (calendar.getGroupId() == groupId) {
				return true;
			}

			return false;
		}

		return true;
	}

	@Override
	public List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			OrderByComparator<Calendar> orderByComparator)
		throws PortalException {

		return search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			start, end, orderByComparator, ActionKeys.VIEW);
	}

	@Override
	public List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			OrderByComparator<Calendar> orderByComparator, String actionId)
		throws PortalException {

		List<Calendar> calendars = calendarFinder.findByKeywords(
			companyId, groupIds, calendarResourceIds, keywords, start, end,
			orderByComparator);

		return filterCalendars(calendars, actionId);
	}

	@Override
	public List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end, OrderByComparator<Calendar> orderByComparator)
		throws PortalException {

		return search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator, ActionKeys.VIEW);
	}

	@Override
	public List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end, OrderByComparator<Calendar> orderByComparator,
			String actionId)
		throws PortalException {

		List<Calendar> calendars = calendarFinder.findByC_G_C_N_D(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator);

		return filterCalendars(calendars, actionId);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator)
		throws PortalException {

		return searchCount(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			ActionKeys.VIEW);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, String actionId)
		throws PortalException {

		List<Calendar> calendars = search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			(OrderByComparator<Calendar>)null);

		return calendars.size();
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator)
		throws PortalException {

		return searchCount(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, ActionKeys.VIEW);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator,
			String actionId)
		throws PortalException {

		List<Calendar> calendars = search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			(OrderByComparator<Calendar>)null, actionId);

		return calendars.size();
	}

	@Override
	public Calendar updateCalendar(
			long calendarId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int color,
			ServiceContext serviceContext)
		throws PortalException {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.UPDATE);

		return calendarLocalService.updateCalendar(
			calendarId, nameMap, descriptionMap, color, serviceContext);
	}

	@Override
	public Calendar updateCalendar(
			long calendarId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String timeZoneId, int color,
			boolean defaultCalendar, boolean enableComments,
			boolean enableRatings, ServiceContext serviceContext)
		throws PortalException {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.UPDATE);

		return calendarLocalService.updateCalendar(
			calendarId, nameMap, descriptionMap, timeZoneId, color,
			defaultCalendar, enableComments, enableRatings, serviceContext);
	}

	@Override
	public Calendar updateColor(
			long calendarId, int color, ServiceContext serviceContext)
		throws PortalException {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.UPDATE);

		return calendarLocalService.updateColor(
			calendarId, color, serviceContext);
	}

	protected List<Calendar> filterCalendars(
			List<Calendar> calendars, String actionId)
		throws PortalException {

		calendars = ListUtil.copy(calendars);

		Iterator<Calendar> itr = calendars.iterator();

		while (itr.hasNext()) {
			Calendar calendar = itr.next();

			if (!_calendarModelResourcePermission.contains(
					getPermissionChecker(), calendar, actionId)) {

				itr.remove();
			}
		}

		return calendars;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarServiceImpl.class.getName());

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.CalendarResource)"
	)
	private ModelResourcePermission<CalendarResource>
		_calendarResourceModelResourcePermission;

}