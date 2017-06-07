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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.calendar.exception.CalendarBookingDurationException;
import com.liferay.calendar.exception.CalendarBookingRecurrenceException;
import com.liferay.calendar.exporter.CalendarDataFormat;
import com.liferay.calendar.exporter.CalendarDataHandler;
import com.liferay.calendar.exporter.CalendarDataHandlerFactory;
import com.liferay.calendar.internal.recurrence.RecurrenceSplit;
import com.liferay.calendar.internal.recurrence.RecurrenceSplitter;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.notification.impl.NotificationUtil;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.base.CalendarBookingLocalServiceBaseImpl;
import com.liferay.calendar.service.configuration.CalendarServiceConfigurationValues;
import com.liferay.calendar.social.CalendarActivityKeys;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.calendar.util.RecurrenceUtil;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.exception.TrashEntryException;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 * @author Marcellus Tavares
 * @author Pier Paolo Ramon
 */
public class CalendarBookingLocalServiceImpl
	extends CalendarBookingLocalServiceBaseImpl {

	@Override
	public CalendarBooking addCalendarBooking(
			long userId, long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		// Calendar booking

		User user = userLocalService.getUser(userId);
		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);

		long calendarBookingId = counterLocalService.increment();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			String sanitizedDescription = SanitizerUtil.sanitize(
				calendar.getCompanyId(), calendar.getGroupId(), userId,
				CalendarBooking.class.getName(), calendarBookingId,
				ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, entry.getValue(),
				null);

			descriptionMap.put(entry.getKey(), sanitizedDescription);
		}

		TimeZone timeZone = getTimeZone(calendar, allDay);

		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			startTime, timeZone);
		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
			endTime, timeZone);

		if (allDay) {
			startTimeJCalendar = JCalendarUtil.toMidnightJCalendar(
				startTimeJCalendar);
			endTimeJCalendar = JCalendarUtil.toLastHourJCalendar(
				endTimeJCalendar);
		}

		if (firstReminder < secondReminder) {
			long originalSecondReminder = secondReminder;

			secondReminder = firstReminder;
			firstReminder = originalSecondReminder;
		}

		Date now = new Date();

		validate(startTimeJCalendar, endTimeJCalendar, recurrence);

		CalendarBooking calendarBooking = calendarBookingPersistence.create(
			calendarBookingId);

		calendarBooking.setUuid(serviceContext.getUuid());
		calendarBooking.setGroupId(calendar.getGroupId());
		calendarBooking.setCompanyId(user.getCompanyId());
		calendarBooking.setUserId(user.getUserId());
		calendarBooking.setUserName(user.getFullName());
		calendarBooking.setCreateDate(serviceContext.getCreateDate(now));
		calendarBooking.setModifiedDate(serviceContext.getModifiedDate(now));
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendar.getCalendarResourceId());

		if (parentCalendarBookingId > 0) {
			calendarBooking.setParentCalendarBookingId(parentCalendarBookingId);
		}
		else {
			calendarBooking.setParentCalendarBookingId(calendarBookingId);
		}

		if (recurringCalendarBookingId > 0) {
			calendarBooking.setRecurringCalendarBookingId(
				recurringCalendarBookingId);
		}
		else {
			calendarBooking.setRecurringCalendarBookingId(calendarBookingId);
		}

		String vEventUid = (String)serviceContext.getAttribute("vEventUid");

		if (vEventUid == null) {
			vEventUid = PortalUUIDUtil.generate();
		}

		calendarBooking.setVEventUid(vEventUid);
		calendarBooking.setTitleMap(titleMap, serviceContext.getLocale());
		calendarBooking.setDescriptionMap(
			descriptionMap, serviceContext.getLocale());
		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTimeJCalendar.getTimeInMillis());
		calendarBooking.setEndTime(endTimeJCalendar.getTimeInMillis());
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType);
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType);
		calendarBooking.setExpandoBridgeAttributes(serviceContext);

		int status;

		if (calendarBooking.isMasterBooking()) {
			status = CalendarBookingWorkflowConstants.STATUS_DRAFT;
		}
		else if (hasExclusiveCalendarBooking(calendar, startTime, endTime)) {
			status = CalendarBookingWorkflowConstants.STATUS_DENIED;
		}
		else {
			status = CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING;
		}

		calendarBooking.setStatus(status);
		calendarBooking.setStatusDate(serviceContext.getModifiedDate(now));

		calendarBookingPersistence.update(calendarBooking);

		addChildCalendarBookings(
			calendarBooking, childCalendarIds, serviceContext);

		// Resources

		resourceLocalService.addModelResources(calendarBooking, serviceContext);

		// Asset

		updateAsset(
			userId, calendarBooking, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Social

		socialActivityLocalService.addActivity(
			userId, calendarBooking.getGroupId(),
			CalendarBooking.class.getName(), calendarBookingId,
			CalendarActivityKeys.ADD_CALENDAR_BOOKING,
			getExtraDataJSON(calendarBooking), 0);

		// Notifications

		sendNotification(
			calendarBooking, NotificationTemplateType.INVITE, serviceContext);

		// Workflow

		if (calendarBooking.isMasterBooking()) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				calendarBooking.getCompanyId(), calendarBooking.getGroupId(),
				userId, CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId(), calendarBooking,
				serviceContext);
		}

		return calendarBooking;
	}

	/**
	 * @deprecated As of 2.2.0, replaced by {@link #addCalendarBooking(long,
	 *             long, long[], long, long, Map, Map, String, long, long,
	 *             boolean, String, long, String, long, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CalendarBooking addCalendarBooking(
			long userId, long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String location, long startTime,
			long endTime, boolean allDay, String recurrence, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType, ServiceContext serviceContext)
		throws PortalException {

		return calendarBookingLocalService.addCalendarBooking(
			userId, calendarId, childCalendarIds, parentCalendarBookingId,
			CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			recurrence, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public void checkCalendarBookings() throws PortalException {
		Date now = new Date();

		List<CalendarBooking> calendarBookings =
			calendarBookingFinder.findByFutureReminders(now.getTime());

		long endTime = now.getTime() + Time.MONTH;

		calendarBookings = RecurrenceUtil.expandCalendarBookings(
			calendarBookings, now.getTime(), endTime, 1);

		for (CalendarBooking calendarBooking : calendarBookings) {
			try {
				Company company = companyLocalService.getCompany(
					calendarBooking.getCompanyId());

				if (company.isActive()) {
					NotificationUtil.notifyCalendarBookingReminders(
						calendarBooking, now.getTime());
				}
			}
			catch (PortalException pe) {
				throw pe;
			}
			catch (SystemException se) {
				throw se;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}
	}

	@Override
	public CalendarBooking deleteCalendarBooking(
			CalendarBooking calendarBooking)
		throws PortalException {

		return calendarBookingLocalService.deleteCalendarBooking(
			calendarBooking, false);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public CalendarBooking deleteCalendarBooking(
			CalendarBooking calendarBooking, boolean allRecurringInstances)
		throws PortalException {

		// Calendar bookings

		Set<CalendarBooking> recurringCalendarBookings = new HashSet<>();

		List<CalendarBooking> childCalendarBookings = new ArrayList<>();

		childCalendarBookings.addAll(
			getChildCalendarBookings(calendarBooking.getCalendarBookingId()));

		childCalendarBookings.add(calendarBooking);

		for (CalendarBooking childCalendarBooking : childCalendarBookings) {
			if (allRecurringInstances) {
				recurringCalendarBookings.addAll(
					getRecurringCalendarBookings(childCalendarBooking));
			}
			else {
				recurringCalendarBookings.add(childCalendarBooking);
			}
		}

		for (CalendarBooking recurringCalendarBooking :
				recurringCalendarBookings) {

			// Calendar booking

			calendarBookingPersistence.remove(recurringCalendarBooking);

			// Resources

			resourceLocalService.deleteResource(
				recurringCalendarBooking, ResourceConstants.SCOPE_INDIVIDUAL);

			// Subscriptions

			subscriptionLocalService.deleteSubscriptions(
				recurringCalendarBooking.getCompanyId(),
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			// Asset

			assetEntryLocalService.deleteEntry(
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			// Message boards

			mbMessageLocalService.deleteDiscussionMessages(
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			// Ratings

			ratingsStatsLocalService.deleteStats(
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			// Trash

			trashEntryLocalService.deleteEntry(
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				recurringCalendarBooking.getCompanyId(),
				recurringCalendarBooking.getGroupId(),
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());
		}

		return calendarBooking;
	}

	@Override
	public CalendarBooking deleteCalendarBooking(long calendarBookingId)
		throws PortalException {

		return deleteCalendarBooking(calendarBookingId, false);
	}

	@Override
	public CalendarBooking deleteCalendarBooking(
			long calendarBookingId, boolean allRecurringInstances)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		calendarBookingLocalService.deleteCalendarBooking(
			calendarBooking, allRecurringInstances);

		return calendarBooking;
	}

	@Override
	public void deleteCalendarBookingInstance(
			CalendarBooking calendarBooking, int instanceIndex,
			boolean allFollowing)
		throws PortalException {

		CalendarBooking calendarBookingInstance =
			RecurrenceUtil.getCalendarBookingInstance(
				calendarBooking, instanceIndex);

		deleteCalendarBookingInstance(
			calendarBooking, calendarBookingInstance.getStartTime(),
			allFollowing);
	}

	@Override
	public void deleteCalendarBookingInstance(
			CalendarBooking calendarBooking, int instanceIndex,
			boolean allFollowing, boolean deleteRecurringCalendarBookings)
		throws PortalException {

		CalendarBooking calendarBookingInstance =
			RecurrenceUtil.getCalendarBookingInstance(
				calendarBooking, instanceIndex);

		deleteCalendarBookingInstance(
			calendarBooking, calendarBookingInstance.getStartTime(),
			allFollowing, deleteRecurringCalendarBookings);
	}

	@Override
	public void deleteCalendarBookingInstance(
			CalendarBooking calendarBooking, long startTime,
			boolean allFollowing)
		throws PortalException {

		deleteCalendarBookingInstance(
			calendarBooking, startTime, allFollowing, false);
	}

	@Override
	public void deleteCalendarBookingInstance(
			CalendarBooking calendarBooking, long startTime,
			boolean allFollowing, boolean deleteRecurringCalendarBookings)
		throws PortalException {

		Date now = new Date();

		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			startTime, calendarBooking.getTimeZone());

		Recurrence recurrenceObj = calendarBooking.getRecurrenceObj();

		if (allFollowing) {
			if (deleteRecurringCalendarBookings) {
				List<CalendarBooking> recurringCalendarBookings =
					splitCalendarBookingInstances(calendarBooking, startTime);

				for (CalendarBooking recurringCalendarBooking :
						recurringCalendarBookings) {

					deleteCalendarBooking(recurringCalendarBooking, false);
				}
			}

			if (startTime == calendarBooking.getStartTime()) {
				calendarBookingLocalService.deleteCalendarBooking(
					calendarBooking, false);

				return;
			}

			if (recurrenceObj.getCount() > 0) {
				recurrenceObj.setCount(0);
			}

			startTimeJCalendar.add(java.util.Calendar.DATE, -1);

			recurrenceObj.setUntilJCalendar(startTimeJCalendar);
		}
		else {
			CalendarBooking calendarBookingInstance =
				RecurrenceUtil.getCalendarBookingInstance(calendarBooking, 1);

			if (calendarBookingInstance == null) {
				calendarBookingLocalService.deleteCalendarBooking(
					calendarBooking, false);

				return;
			}

			recurrenceObj.addExceptionJCalendar(startTimeJCalendar);
		}

		String recurrence = RecurrenceSerializer.serialize(recurrenceObj);

		updateChildCalendarBookings(calendarBooking, now, recurrence);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long calendarBookingId, long startTime, boolean allFollowing)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		deleteCalendarBookingInstance(calendarBooking, startTime, allFollowing);
	}

	@Override
	public void deleteCalendarBookings(long calendarId) throws PortalException {
		List<CalendarBooking> calendarBookings =
			calendarBookingPersistence.findByCalendarId(calendarId);

		for (CalendarBooking calendarBooking : calendarBookings) {
			calendarBookingLocalService.deleteCalendarBooking(calendarBooking);
		}
	}

	@Override
	public CalendarBooking deleteRecurringCalendarBooking(
			CalendarBooking calendarBooking)
		throws PortalException {

		return calendarBookingLocalService.deleteCalendarBooking(
			calendarBooking, true);
	}

	@Override
	public CalendarBooking deleteRecurringCalendarBooking(
			long calendarBookingId)
		throws PortalException {

		return calendarBookingLocalService.deleteCalendarBooking(
			calendarBookingId, true);
	}

	@Override
	public String exportCalendarBooking(long calendarBookingId, String type)
		throws Exception {

		CalendarDataFormat calendarDataFormat = CalendarDataFormat.parse(type);

		CalendarDataHandler calendarDataHandler =
			CalendarDataHandlerFactory.getCalendarDataHandler(
				calendarDataFormat);

		return calendarDataHandler.exportCalendarBooking(calendarBookingId);
	}

	@Override
	public CalendarBooking fetchCalendarBooking(
		long calendarId, String vEventUid) {

		return calendarBookingPersistence.fetchByC_V(calendarId, vEventUid);
	}

	@Override
	public CalendarBooking fetchCalendarBooking(String uuid, long groupId) {
		return calendarBookingPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public CalendarBooking getCalendarBooking(long calendarBookingId)
		throws PortalException {

		return calendarBookingPersistence.findByPrimaryKey(calendarBookingId);
	}

	@Override
	public CalendarBooking getCalendarBooking(
			long calendarId, long parentCalendarBookingId)
		throws PortalException {

		return calendarBookingPersistence.findByC_P(
			calendarId, parentCalendarBookingId);
	}

	@Override
	public CalendarBooking getCalendarBookingInstance(
			long calendarBookingId, int instanceIndex)
		throws PortalException {

		CalendarBooking calendarBooking = getCalendarBooking(calendarBookingId);

		return RecurrenceUtil.getCalendarBookingInstance(
			calendarBooking, instanceIndex);
	}

	@Override
	public List<CalendarBooking> getCalendarBookings(long calendarId) {
		return calendarBookingPersistence.findByCalendarId(calendarId);
	}

	@Override
	public List<CalendarBooking> getCalendarBookings(
		long calendarId, int[] statuses) {

		return calendarBookingPersistence.findByC_S(calendarId, statuses);
	}

	@Override
	public List<CalendarBooking> getCalendarBookings(
		long calendarId, long startTime, long endTime) {

		return getCalendarBookings(
			calendarId, startTime, endTime, QueryUtil.ALL_POS);
	}

	@Override
	public List<CalendarBooking> getCalendarBookings(
		long calendarId, long startTime, long endTime, int max) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CalendarBooking.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("calendarId");

		dynamicQuery.add(property.eq(calendarId));

		if (startTime >= 0) {
			Property propertyStartTime = PropertyFactoryUtil.forName(
				"startTime");

			dynamicQuery.add(propertyStartTime.gt(startTime));
		}

		if (endTime >= 0) {
			Property propertyEndTime = PropertyFactoryUtil.forName("endTime");

			dynamicQuery.add(propertyEndTime.lt(endTime));
		}

		if (max > 0) {
			dynamicQuery.setLimit(0, max);
		}

		return dynamicQuery(dynamicQuery);
	}

	@Override
	public int getCalendarBookingsCount(
		long calendarId, long parentCalendarBookingId) {

		return calendarBookingPersistence.countByC_P(
			calendarId, parentCalendarBookingId);
	}

	@Override
	public List<CalendarBooking> getChildCalendarBookings(
		long calendarBookingId) {

		return calendarBookingPersistence.findByParentCalendarBookingId(
			calendarBookingId);
	}

	@Override
	public List<CalendarBooking> getChildCalendarBookings(
		long parentCalendarBookingId, int status) {

		return calendarBookingPersistence.findByP_S(
			parentCalendarBookingId, status);
	}

	@Override
	public long[] getChildCalendarIds(long calendarBookingId, long calendarId)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		List<CalendarBooking> childCalendarBookings =
			calendarBookingPersistence.findByParentCalendarBookingId(
				calendarBookingId);

		long[] childCalendarIds = new long[childCalendarBookings.size()];

		for (int i = 0; i < childCalendarIds.length; i++) {
			CalendarBooking childCalendarBooking = childCalendarBookings.get(i);

			if (childCalendarBooking.getCalendarId() ==
					calendarBooking.getCalendarId()) {

				childCalendarIds[i] = calendarId;
			}
			else {
				childCalendarIds[i] = childCalendarBooking.getCalendarId();
			}
		}

		return childCalendarIds;
	}

	@Override
	public CalendarBooking getLastInstanceCalendarBooking(
		CalendarBooking calendarBooking) {

		List<CalendarBooking> calendarBookings = getRecurringCalendarBookings(
			calendarBooking);

		return RecurrenceUtil.getLastInstanceCalendarBooking(calendarBookings);
	}

	@Override
	public List<CalendarBooking> getRecurringCalendarBookings(
		CalendarBooking calendarBooking) {

		long recurringCalendarBookingId =
			calendarBooking.getRecurringCalendarBookingId();

		return calendarBookingPersistence.findByRecurringCalendarBookingId(
			recurringCalendarBookingId);
	}

	@Override
	public List<CalendarBooking> getRecurringCalendarBookings(
		CalendarBooking calendarBooking, long startTime) {

		List<CalendarBooking> recurringCalendarBookings =
			getRecurringCalendarBookings(calendarBooking);

		List<CalendarBooking> followingRecurringCalendarBookings =
			new ArrayList<>();

		for (CalendarBooking recurringCalendarBooking :
				recurringCalendarBookings) {

			if (recurringCalendarBooking.getStartTime() > startTime) {
				followingRecurringCalendarBookings.add(
					recurringCalendarBooking);
			}
		}

		return followingRecurringCalendarBookings;
	}

	public boolean hasExclusiveCalendarBooking(
			Calendar calendar, long startTime, long endTime)
		throws PortalException {

		CalendarResource calendarResource = calendar.getCalendarResource();

		if (calendarResource.isGroup() && calendarResource.isUser()) {
			return false;
		}

		int[] statuses = {
			CalendarBookingWorkflowConstants.STATUS_APPROVED,
			CalendarBookingWorkflowConstants.STATUS_PENDING
		};

		List<CalendarBooking> calendarBookings = getOverlappingCalendarBookings(
			calendar.getCalendarId(), startTime, endTime, statuses);

		if (!calendarBookings.isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	public CalendarBooking invokeTransition(
			long userId, CalendarBooking calendarBooking, long startTime,
			int status, boolean updateInstance, boolean allFollowing,
			ServiceContext serviceContext)
		throws PortalException {

		if (updateInstance) {
			long calendarId = calendarBooking.getCalendarId();

			long[] childCalendarIds = getChildCalendarIds(
				calendarBooking.getCalendarBookingId(), calendarId);

			long duration =
				calendarBooking.getEndTime() - calendarBooking.getStartTime();

			long endTime = startTime + duration;

			String recurrence = null;

			if (allFollowing) {
				List<CalendarBooking> recurringCalendarBookings =
					splitCalendarBookingInstances(calendarBooking, startTime);

				for (CalendarBooking recurringCalendarBooking :
						recurringCalendarBookings) {

					calendarBookingLocalService.updateStatus(
						userId, recurringCalendarBooking, status,
						serviceContext);
				}

				Recurrence recurrenceObj = calendarBooking.getRecurrenceObj();

				if (recurrenceObj != null) {
					int count = recurrenceObj.getCount();

					if (count > 0) {
						int instanceIndex = RecurrenceUtil.getIndexOfInstance(
							calendarBooking.getRecurrence(),
							calendarBooking.getStartTime(), startTime);

						recurrenceObj.setCount(count - instanceIndex);
					}
				}

				recurrence = RecurrenceSerializer.serialize(recurrenceObj);
			}

			deleteCalendarBookingInstance(
				calendarBooking, startTime, allFollowing);

			calendarBooking = addCalendarBooking(
				userId, calendarId, childCalendarIds, 0,
				calendarBooking.getRecurringCalendarBookingId(),
				calendarBooking.getTitleMap(),
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), startTime, endTime,
				calendarBooking.isAllDay(), recurrence,
				calendarBooking.getFirstReminder(),
				calendarBooking.getFirstReminderType(),
				calendarBooking.getSecondReminder(),
				calendarBooking.getSecondReminderType(), serviceContext);

			calendarBookingLocalService.updateStatus(
				userId, calendarBooking, status, serviceContext);
		}
		else {
			List<CalendarBooking> recurringCalendarBookings =
				getRecurringCalendarBookings(calendarBooking);

			for (CalendarBooking recurringCalendarBooking :
					recurringCalendarBookings) {

				calendarBookingLocalService.updateStatus(
					userId, recurringCalendarBooking, status, serviceContext);
			}
		}

		return calendarBooking;
	}

	@Override
	public CalendarBooking moveCalendarBookingToTrash(
			long userId, CalendarBooking calendarBooking)
		throws PortalException {

		if (calendarBooking.isInTrash()) {
			throw new TrashEntryException();
		}

		// Calendar booking

		if (!calendarBooking.isMasterBooking()) {
			return calendarBooking;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		List<CalendarBooking> recurringCalendarBookings =
			getRecurringCalendarBookings(calendarBooking);

		for (CalendarBooking recurringCalendarBooking :
				recurringCalendarBookings) {

			// Calendar booking

			calendarBookingLocalService.updateStatus(
				userId, recurringCalendarBooking,
				CalendarBookingWorkflowConstants.STATUS_IN_TRASH,
				serviceContext);

			// Social

			socialActivityCounterLocalService.disableActivityCounters(
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());

			socialActivityLocalService.addActivity(
				userId, recurringCalendarBooking.getGroupId(),
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId(),
				SocialActivityConstants.TYPE_MOVE_TO_TRASH,
				getExtraDataJSON(recurringCalendarBooking), 0);

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				recurringCalendarBooking.getCompanyId(),
				recurringCalendarBooking.getGroupId(),
				CalendarBooking.class.getName(),
				recurringCalendarBooking.getCalendarBookingId());
		}

		return calendarBooking;
	}

	@Override
	public CalendarBooking moveCalendarBookingToTrash(
			long userId, long calendarBookingId)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		return moveCalendarBookingToTrash(userId, calendarBooking);
	}

	@Override
	public CalendarBooking restoreCalendarBookingFromTrash(
			long userId, long calendarBookingId)
		throws PortalException {

		// Calendar booking

		CalendarBooking calendarBooking = getCalendarBooking(calendarBookingId);

		if (!calendarBooking.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		if (!calendarBooking.isMasterBooking()) {
			return calendarBooking;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			CalendarBooking.class.getName(), calendarBookingId);

		calendarBookingLocalService.updateStatus(
			userId, calendarBookingId, trashEntry.getStatus(), serviceContext);

		// Social

		socialActivityCounterLocalService.enableActivityCounters(
			CalendarBooking.class.getName(), calendarBookingId);

		socialActivityLocalService.addActivity(
			userId, calendarBooking.getGroupId(),
			CalendarBooking.class.getName(), calendarBookingId,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			getExtraDataJSON(calendarBooking), 0);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			calendarBooking.getCompanyId(), calendarBooking.getGroupId(),
			userId, CalendarBooking.class.getName(),
			calendarBooking.getCalendarBookingId(), calendarBooking,
			serviceContext);

		if (calendarBooking.isMasterRecurringBooking()) {
			List<CalendarBooking> recurringCalendarBookings =
				getRecurringCalendarBookings(calendarBooking);

			for (CalendarBooking recurringCalendarBooking :
					recurringCalendarBookings) {

				if (recurringCalendarBooking.equals(calendarBooking)) {
					continue;
				}

				calendarBookingLocalService.updateStatus(
					userId, recurringCalendarBooking, trashEntry.getStatus(),
					serviceContext);
			}
		}

		return calendarBooking;
	}

	@Override
	public List<CalendarBooking> search(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		String keywords, long startTime, long endTime, boolean recurring,
		int[] statuses, int start, int end,
		OrderByComparator<CalendarBooking> orderByComparator) {

		List<CalendarBooking> calendarBookings =
			calendarBookingFinder.findByKeywords(
				companyId, groupIds, calendarIds, calendarResourceIds,
				parentCalendarBookingId, keywords, startTime, endTime,
				recurring, statuses, start, end, orderByComparator);

		if (recurring) {
			calendarBookings = RecurrenceUtil.expandCalendarBookings(
				calendarBookings, startTime, endTime);
		}

		return calendarBookings;
	}

	@Override
	public List<CalendarBooking> search(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId, String title,
		String description, String location, long startTime, long endTime,
		boolean recurring, int[] statuses, boolean andOperator, int start,
		int end, OrderByComparator<CalendarBooking> orderByComparator) {

		List<CalendarBooking> calendarBookings =
			calendarBookingFinder.findByC_G_C_C_P_T_D_L_S_E_S(
				companyId, groupIds, calendarIds, calendarResourceIds,
				parentCalendarBookingId, title, description, location,
				startTime, endTime, recurring, statuses, andOperator, start,
				end, orderByComparator);

		if (recurring) {
			calendarBookings = RecurrenceUtil.expandCalendarBookings(
				calendarBookings, startTime, endTime);
		}

		return calendarBookings;
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		String keywords, long startTime, long endTime, int[] statuses) {

		return calendarBookingFinder.countByKeywords(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, statuses);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId, String title,
		String description, String location, long startTime, long endTime,
		int[] statuses, boolean andOperator) {

		return calendarBookingFinder.countByC_G_C_C_P_T_D_L_S_E_S(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, statuses, andOperator);
	}

	@Override
	public void updateAsset(
			long userId, CalendarBooking calendarBooking,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		boolean visible = false;

		Date publishDate = null;

		if (calendarBooking.isApproved()) {
			visible = true;

			publishDate = calendarBooking.getCreateDate();
		}

		String summary = HtmlUtil.extractText(
			StringUtil.shorten(calendarBooking.getDescription(), 500));

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, calendarBooking.getGroupId(),
			calendarBooking.getCreateDate(), calendarBooking.getModifiedDate(),
			CalendarBooking.class.getName(),
			calendarBooking.getCalendarBookingId(), calendarBooking.getUuid(),
			0, assetCategoryIds, assetTagNames, true, visible, null, null,
			publishDate, null, ContentTypes.TEXT_HTML,
			calendarBooking.getTitle(), calendarBooking.getDescription(),
			summary, null, null, 0, 0, priority);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public CalendarBooking updateCalendarBooking(
			long userId, long calendarBookingId, long calendarId,
			long[] childCalendarIds, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String location, long startTime,
			long endTime, boolean allDay, String recurrence, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType, ServiceContext serviceContext)
		throws PortalException {

		// Calendar booking

		Calendar calendar = calendarPersistence.findByPrimaryKey(calendarId);
		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			String sanitizedDescription = SanitizerUtil.sanitize(
				calendar.getCompanyId(), calendar.getGroupId(), userId,
				CalendarBooking.class.getName(), calendarBookingId,
				ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, entry.getValue(),
				null);

			descriptionMap.put(entry.getKey(), sanitizedDescription);
		}

		TimeZone timeZone = getTimeZone(calendar, allDay);

		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			startTime, timeZone);
		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
			endTime, timeZone);

		if (allDay) {
			startTimeJCalendar = JCalendarUtil.toMidnightJCalendar(
				startTimeJCalendar);
			endTimeJCalendar = JCalendarUtil.toLastHourJCalendar(
				endTimeJCalendar);
		}

		if (firstReminder < secondReminder) {
			long originalSecondReminder = secondReminder;

			secondReminder = firstReminder;
			firstReminder = originalSecondReminder;
		}

		validate(startTimeJCalendar, endTimeJCalendar, recurrence);

		calendarBooking.setGroupId(calendar.getGroupId());
		calendarBooking.setModifiedDate(serviceContext.getModifiedDate(null));
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendar.getCalendarResourceId());

		Map<Locale, String> updatedTitleMap = calendarBooking.getTitleMap();

		updatedTitleMap.putAll(titleMap);

		calendarBooking.setTitleMap(
			updatedTitleMap, serviceContext.getLocale());

		Map<Locale, String> updatedDescriptionMap =
			calendarBooking.getDescriptionMap();

		updatedDescriptionMap.putAll(descriptionMap);

		calendarBooking.setDescriptionMap(
			updatedDescriptionMap, serviceContext.getLocale());

		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTimeJCalendar.getTimeInMillis());
		calendarBooking.setEndTime(endTimeJCalendar.getTimeInMillis());
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType);
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType);

		if (calendarBooking.isMasterBooking() && !calendarBooking.isDraft() &&
			!calendarBooking.isPending()) {

			calendarBooking.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		calendarBooking.setExpandoBridgeAttributes(serviceContext);

		calendarBookingPersistence.update(calendarBooking);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			addChildCalendarBookings(
				calendarBooking, childCalendarIds, serviceContext);
		}

		// Asset

		updateAsset(
			userId, calendarBooking, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Social

		socialActivityLocalService.addActivity(
			userId, calendarBooking.getGroupId(),
			CalendarBooking.class.getName(), calendarBookingId,
			CalendarActivityKeys.UPDATE_CALENDAR_BOOKING,
			getExtraDataJSON(calendarBooking), 0);

		// Notifications

		sendNotification(
			calendarBooking, NotificationTemplateType.UPDATE, serviceContext);

		// Workflow

		if (calendarBooking.isMasterBooking()) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				calendarBooking.getCompanyId(), calendarBooking.getGroupId(),
				userId, CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId(), calendarBooking,
				serviceContext);
		}

		return calendarBooking;
	}

	@Override
	public CalendarBooking updateCalendarBooking(
			long userId, long calendarBookingId, long calendarId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		long[] childCalendarIds = getChildCalendarIds(
			calendarBookingId, calendarId);

		return updateCalendarBooking(
			userId, calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long userId, long calendarBookingId, int instanceIndex,
			long calendarId, long[] childCalendarIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			boolean allFollowing, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.fetchByPrimaryKey(calendarBookingId);

		return updateCalendarBookingInstance(
			userId, calendarBookingId, instanceIndex, calendarId,
			childCalendarIds, titleMap, descriptionMap, location, startTime,
			endTime, allDay, calendarBooking.getRecurrence(), allFollowing,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long userId, long calendarBookingId, int instanceIndex,
			long calendarId, long[] childCalendarIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, boolean allFollowing, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType, ServiceContext serviceContext)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		String oldRecurrence = calendarBooking.getRecurrence();

		deleteCalendarBookingInstance(
			calendarBooking, instanceIndex, allFollowing, false);

		Map<Locale, String> updatedTitleMap = calendarBooking.getTitleMap();

		updatedTitleMap.putAll(titleMap);

		Map<Locale, String> updatedDescriptionMap =
			calendarBooking.getDescriptionMap();

		updatedDescriptionMap.putAll(descriptionMap);

		if (allFollowing) {
			Calendar calendar = calendarLocalService.getCalendar(calendarId);

			List<CalendarBooking> recurringCalendarBookings =
				splitCalendarBookingInstances(calendarBooking, startTime);

			List<String> unmodifiedAttributesNames =
				getUnmodifiedAttributesNames(
					calendarBooking, calendarId, titleMap, descriptionMap,
					location, startTime, endTime, allDay, firstReminder,
					firstReminderType, secondReminder, secondReminderType);

			Recurrence recurrenceObj = RecurrenceSerializer.deserialize(
				recurrence, calendar.getTimeZone());

			if ((recurrenceObj != null) && oldRecurrence.equals(recurrence) &&
				(recurrenceObj.getCount() > 0)) {

				recurrenceObj.setCount(
					recurrenceObj.getCount() - instanceIndex);

				recurrence = RecurrenceSerializer.serialize(recurrenceObj);
			}

			updateCalendarBookingsByChanges(
				userId, calendarId, childCalendarIds, updatedTitleMap,
				updatedDescriptionMap, location, startTime, endTime, allDay,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext, recurringCalendarBookings,
				unmodifiedAttributesNames);
		}
		else {
			recurrence = StringPool.BLANK;
		}

		return addCalendarBooking(
			userId, calendarId, childCalendarIds,
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
			calendarBooking.getRecurringCalendarBookingId(), updatedTitleMap,
			updatedDescriptionMap, location, startTime, endTime, allDay,
			recurrence, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long userId, long calendarBookingId, int instanceIndex,
			long calendarId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String location, long startTime,
			long endTime, boolean allDay, String recurrence,
			boolean allFollowing, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		long[] childCalendarIds = getChildCalendarIds(
			calendarBookingId, calendarId);

		return updateCalendarBookingInstance(
			userId, calendarBookingId, instanceIndex, calendarId,
			childCalendarIds, titleMap, descriptionMap, location, startTime,
			endTime, allDay, recurrence, allFollowing, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public void updateLastInstanceCalendarBookingRecurrence(
		CalendarBooking calendarBooking, String recurrence) {

		CalendarBooking lastInstanceCalendarBooking =
			getLastInstanceCalendarBooking(calendarBooking);

		if (recurrence == null) {
			recurrence = StringPool.BLANK;
		}
		else {
			Recurrence oldRecurrenceObj =
				lastInstanceCalendarBooking.getRecurrenceObj();

			Recurrence recurrenceObj = RecurrenceSerializer.deserialize(
				recurrence, calendarBooking.getTimeZone());

			if ((oldRecurrenceObj != null) && (recurrenceObj != null)) {
				recurrenceObj.setExceptionJCalendars(
					oldRecurrenceObj.getExceptionJCalendars());

				recurrence = RecurrenceSerializer.serialize(recurrenceObj);
			}
		}

		if (!recurrence.equals(lastInstanceCalendarBooking.getRecurrence())) {
			lastInstanceCalendarBooking.setRecurrence(recurrence);

			calendarBookingPersistence.update(lastInstanceCalendarBooking);
		}
	}

	@Override
	public CalendarBooking updateRecurringCalendarBooking(
			long userId, long calendarBookingId, long calendarId,
			long[] childCalendarIds, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String location, long startTime,
			long endTime, boolean allDay, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType, ServiceContext serviceContext)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		List<CalendarBooking> recurringCalendarBookings =
			getRecurringCalendarBookings(calendarBooking);

		List<String> unmodifiedAttributesNames = getUnmodifiedAttributesNames(
			calendarBooking, calendarId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, firstReminder, firstReminderType,
			secondReminder, secondReminderType);

		updateCalendarBookingsByChanges(
			userId, calendarId, childCalendarIds, titleMap, descriptionMap,
			location, startTime, endTime, allDay, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext, recurringCalendarBookings,
			unmodifiedAttributesNames);

		return calendarBooking;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CalendarBooking updateStatus(
			long userId, CalendarBooking calendarBooking, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Calendar booking

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		int oldStatus = calendarBooking.getStatus();

		calendarBooking.setModifiedDate(serviceContext.getModifiedDate(now));
		calendarBooking.setStatus(status);
		calendarBooking.setStatusByUserId(user.getUserId());
		calendarBooking.setStatusByUserName(user.getFullName());
		calendarBooking.setStatusDate(serviceContext.getModifiedDate(now));

		calendarBookingPersistence.update(calendarBooking);

		// Child calendar bookings

		if (status == CalendarBookingWorkflowConstants.STATUS_IN_TRASH) {
			List<CalendarBooking> childCalendarBookings =
				calendarBooking.getChildCalendarBookings();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				if (childCalendarBooking.equals(calendarBooking)) {
					continue;
				}

				updateStatus(
					userId, childCalendarBooking,
					CalendarBookingWorkflowConstants.STATUS_IN_TRASH,
					serviceContext);
			}
		}
		else if (oldStatus ==
					CalendarBookingWorkflowConstants.STATUS_IN_TRASH) {

			List<CalendarBooking> childCalendarBookings =
				calendarBooking.getChildCalendarBookings();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				if (childCalendarBooking.equals(calendarBooking)) {
					continue;
				}

				updateStatus(
					userId, childCalendarBooking,
					CalendarBookingWorkflowConstants.STATUS_PENDING,
					serviceContext);
			}
		}
		else if (status == CalendarBookingWorkflowConstants.STATUS_APPROVED) {
			List<CalendarBooking> childCalendarBookings =
				calendarBooking.getChildCalendarBookings();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				if (childCalendarBooking.equals(calendarBooking)) {
					continue;
				}

				if (childCalendarBooking.getStatus() ==
						CalendarBookingWorkflowConstants.
							STATUS_MASTER_PENDING) {

					updateStatus(
						userId, childCalendarBooking,
						CalendarBookingWorkflowConstants.STATUS_PENDING,
						serviceContext);
				}
			}
		}
		else {
			List<CalendarBooking> childCalendarBookings =
				calendarBooking.getChildCalendarBookings();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				if (childCalendarBooking.equals(calendarBooking)) {
					continue;
				}

				updateStatus(
					userId, childCalendarBooking,
					CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
					serviceContext);
			}
		}

		// Asset

		if (status == CalendarBookingWorkflowConstants.STATUS_APPROVED) {
			assetEntryLocalService.updateVisible(
				CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId(), true);
		}
		else if (status == CalendarBookingWorkflowConstants.STATUS_IN_TRASH) {
			assetEntryLocalService.updateVisible(
				CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId(), false);
		}

		// Trash

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
			trashEntryLocalService.deleteEntry(
				CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId());
		}

		if (status == CalendarBookingWorkflowConstants.STATUS_IN_TRASH) {
			if (calendarBooking.isMasterRecurringBooking()) {
				if (calendarBooking.isMasterBooking()) {
					trashEntryLocalService.addTrashEntry(
						userId, calendarBooking.getGroupId(),
						CalendarBooking.class.getName(),
						calendarBooking.getCalendarBookingId(),
						calendarBooking.getUuid(), null, oldStatus, null, null);
				}
				else {
					trashEntryLocalService.addTrashEntry(
						userId, calendarBooking.getGroupId(),
						CalendarBooking.class.getName(),
						calendarBooking.getCalendarBookingId(),
						calendarBooking.getUuid(), null,
						CalendarBookingWorkflowConstants.STATUS_PENDING, null,
						null);
				}

				sendNotification(
					calendarBooking, NotificationTemplateType.MOVED_TO_TRASH,
					serviceContext);
			}
		}

		return calendarBooking;
	}

	@Override
	public CalendarBooking updateStatus(
			long userId, long calendarBookingId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		CalendarBooking calendarBooking =
			calendarBookingPersistence.findByPrimaryKey(calendarBookingId);

		return calendarBookingLocalService.updateStatus(
			userId, calendarBooking, status, serviceContext);
	}

	protected void addChildCalendarBookings(
			CalendarBooking calendarBooking, long[] childCalendarIds,
			ServiceContext serviceContext)
		throws PortalException {

		if (!calendarBooking.isMasterBooking()) {
			return;
		}

		long recurringCalendarBookingId =
			CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT;

		Map<Long, CalendarBooking> childCalendarBookingMap = new HashMap<>();

		List<CalendarBooking> childCalendarBookings =
			calendarBookingPersistence.findByParentCalendarBookingId(
				calendarBooking.getCalendarBookingId());

		for (CalendarBooking childCalendarBooking : childCalendarBookings) {
			if (childCalendarBooking.isMasterBooking() ||
				(childCalendarBooking.isDenied() &&
				 ArrayUtil.contains(
					 childCalendarIds, childCalendarBooking.getCalendarId()))) {

				continue;
			}

			deleteCalendarBooking(childCalendarBooking.getCalendarBookingId());

			childCalendarBookingMap.put(
				childCalendarBooking.getCalendarId(), childCalendarBooking);
		}

		for (long calendarId : childCalendarIds) {
			int count = calendarBookingPersistence.countByC_P(
				calendarId, calendarBooking.getCalendarBookingId());

			if (count > 0) {
				continue;
			}

			long firstReminder = calendarBooking.getFirstReminder();
			String firstReminderType = calendarBooking.getFirstReminderType();
			long secondReminder = calendarBooking.getSecondReminder();
			String secondReminderType = calendarBooking.getSecondReminderType();

			if (childCalendarBookingMap.containsKey(calendarId)) {
				CalendarBooking oldChildCalendarBooking =
					childCalendarBookingMap.get(calendarId);

				firstReminder = oldChildCalendarBooking.getFirstReminder();
				firstReminderType =
					oldChildCalendarBooking.getFirstReminderType();
				secondReminder = oldChildCalendarBooking.getSecondReminder();
				secondReminderType =
					oldChildCalendarBooking.getSecondReminderType();
			}

			if (!calendarBooking.isMasterRecurringBooking()) {
				CalendarBooking childMasterRecurringBooking =
					calendarBookingPersistence.fetchByC_P(
						calendarId,
						calendarBooking.getRecurringCalendarBookingId());

				if (childMasterRecurringBooking == null) {
					childMasterRecurringBooking = childCalendarBookingMap.get(
						calendarId);
				}

				recurringCalendarBookingId =
					childMasterRecurringBooking.getCalendarBookingId();
			}

			serviceContext.setAttribute("sendNotification", Boolean.FALSE);

			CalendarBooking childCalendarBooking = addCalendarBooking(
				calendarBooking.getUserId(), calendarId, new long[0],
				calendarBooking.getCalendarBookingId(),
				recurringCalendarBookingId, calendarBooking.getTitleMap(),
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), calendarBooking.getStartTime(),
				calendarBooking.getEndTime(), calendarBooking.getAllDay(),
				calendarBooking.getRecurrence(), firstReminder,
				firstReminderType, secondReminder, secondReminderType,
				serviceContext);

			serviceContext.setAttribute("sendNotification", Boolean.TRUE);

			int workflowAction = GetterUtil.getInteger(
				serviceContext.getAttribute("workflowAction"));

			if (childCalendarBookingMap.containsKey(calendarId)) {
				CalendarBooking oldChildCalendarBooking =
					childCalendarBookingMap.get(calendarId);

				if ((calendarBooking.getStartTime() ==
						oldChildCalendarBooking.getStartTime()) &&
					(calendarBooking.getEndTime() ==
						oldChildCalendarBooking.getEndTime()) &&
					(workflowAction != WorkflowConstants.ACTION_SAVE_DRAFT)) {

					updateStatus(
						childCalendarBooking.getUserId(), childCalendarBooking,
						oldChildCalendarBooking.getStatus(), serviceContext);
				}
			}

			NotificationTemplateType notificationTemplateType =
				NotificationTemplateType.INVITE;

			if (childCalendarBooking.isDenied()) {
				notificationTemplateType = NotificationTemplateType.DECLINE;
			}
			else if (childCalendarBookingMap.containsKey(
						childCalendarBooking.getCalendarId())) {

				notificationTemplateType = NotificationTemplateType.UPDATE;
			}

			sendNotification(
				childCalendarBooking, notificationTemplateType, serviceContext);
		}
	}

	protected String getExtraDataJSON(CalendarBooking calendarBooking) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("title", calendarBooking.getTitle());

		return jsonObject.toString();
	}

	protected List<CalendarBooking> getOverlappingCalendarBookings(
		long calendarId, long startTime, long endTime, int[] statuses) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CalendarBooking.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("calendarId");

		dynamicQuery.add(property.eq(calendarId));

		Property endTimeProperty = PropertyFactoryUtil.forName("endTime");

		Property startTimeProperty = PropertyFactoryUtil.forName("startTime");

		Criterion intervalCriterion = RestrictionsFactoryUtil.and(
			startTimeProperty.lt(endTime), endTimeProperty.gt(startTime));

		dynamicQuery.add(intervalCriterion);

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.in(statuses));

		return dynamicQuery(dynamicQuery);
	}

	protected TimeZone getTimeZone(Calendar calendar, boolean allDay) {
		TimeZone timeZone = calendar.getTimeZone();

		if (allDay) {
			timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		return timeZone;
	}

	protected List<String> getUnmodifiedAttributesNames(
		CalendarBooking calendarBooking, long calendarId,
		Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
		String location, long startTime, long endTime, boolean allDay,
		long firstReminder, String firstReminderType, long secondReminder,
		String secondReminderType) {

		List<String> unmodifiedAttributesNames = new ArrayList<>();

		if (calendarId == calendarBooking.getCalendarId()) {
			unmodifiedAttributesNames.add("calendarId");
		}

		Map<Locale, String> updatedTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> titleMapEntry : titleMap.entrySet()) {
			if (titleMapEntry.getValue() != null) {
				updatedTitleMap.put(
					titleMapEntry.getKey(), titleMapEntry.getValue());
			}
		}

		if (Objects.equals(updatedTitleMap, calendarBooking.getTitleMap())) {
			unmodifiedAttributesNames.add("titleMap");
		}

		Map<Locale, String> updatedDescriptionMap = new HashMap<>();

		for (Map.Entry<Locale, String> descriptionMapEntry :
				descriptionMap.entrySet()) {

			if (descriptionMapEntry.getValue() != null) {
				updatedDescriptionMap.put(
					descriptionMapEntry.getKey(),
					descriptionMapEntry.getValue());
			}
		}

		if (Objects.equals(
				updatedDescriptionMap, calendarBooking.getDescriptionMap())) {

			unmodifiedAttributesNames.add("descriptionMap");
		}

		if (Objects.equals(location, calendarBooking.getLocation())) {
			unmodifiedAttributesNames.add("location");
		}

		long newStartTime = JCalendarUtil.convertTimeToNewDay(
			calendarBooking.getStartTime(), startTime);

		long newEndTime = JCalendarUtil.convertTimeToNewDay(
			calendarBooking.getEndTime(), endTime);

		if ((startTime == newStartTime) && (endTime == newEndTime)) {
			unmodifiedAttributesNames.add("time");
		}

		if (allDay == calendarBooking.getAllDay()) {
			unmodifiedAttributesNames.add("allDay");
		}

		if (firstReminder == calendarBooking.getFirstReminder()) {
			unmodifiedAttributesNames.add("firstReminder");
		}

		if (Objects.equals(
				firstReminderType, calendarBooking.getFirstReminderType())) {

			unmodifiedAttributesNames.add("firstReminderType");
		}

		if (secondReminder == calendarBooking.getSecondReminder()) {
			unmodifiedAttributesNames.add("secondReminder");
		}

		if (Objects.equals(
				secondReminderType, calendarBooking.getSecondReminderType())) {

			unmodifiedAttributesNames.add("secondReminderType");
		}

		return unmodifiedAttributesNames;
	}

	protected void sendNotification(
		CalendarBooking calendarBooking,
		NotificationTemplateType notificationTemplateType,
		ServiceContext serviceContext) {

		boolean sendNotification = ParamUtil.getBoolean(
			serviceContext, "sendNotification", true);

		try {
			CalendarBooking parentCalendarBooking =
				calendarBooking.getParentCalendarBooking();

			CalendarResource calendarResource =
				parentCalendarBooking.getCalendarResource();

			Group group = groupLocalService.getGroup(
				calendarResource.getGroupId());

			if (!sendNotification || group.isStagingGroup()) {
				return;
			}

			User sender = userLocalService.fetchUser(
				serviceContext.getUserId());

			NotificationType notificationType =
				CalendarServiceConfigurationValues.
					CALENDAR_NOTIFICATION_DEFAULT_TYPE;

			NotificationUtil.notifyCalendarBookingRecipients(
				calendarBooking, notificationType, notificationTemplateType,
				sender);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected CalendarBooking splitCalendarBookingInstance(
			CalendarBooking calendarBooking, long startTime,
			Recurrence recurrence)
		throws PortalException {

		long[] childCalendarIds = getChildCalendarIds(
			calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId());

		long duration =
			calendarBooking.getEndTime() - calendarBooking.getStartTime();

		long endTime = startTime + duration;

		CalendarBooking calendarBookingInstance = addCalendarBooking(
			calendarBooking.getUserId(), calendarBooking.getCalendarId(),
			childCalendarIds,
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
			calendarBooking.getRecurringCalendarBookingId(),
			calendarBooking.getTitleMap(), calendarBooking.getDescriptionMap(),
			calendarBooking.getLocation(), startTime, endTime,
			calendarBooking.getAllDay(),
			RecurrenceSerializer.serialize(recurrence),
			calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(),
			ServiceContextThreadLocal.getServiceContext());

		deleteCalendarBookingInstance(calendarBooking, startTime, true, false);

		return calendarBookingInstance;
	}

	protected List<CalendarBooking> splitCalendarBookingInstances(
			CalendarBooking calendarBooking, long startTime)
		throws PortalException {

		List<CalendarBooking> recurringCalendarBookings =
			getRecurringCalendarBookings(calendarBooking);

		List<CalendarBooking> followingRecurringCalendarBookings =
			new ArrayList<>();

		java.util.Calendar splitJCalendar = null;

		boolean singleInstance = false;

		if (Validator.isNull(calendarBooking.getRecurrence())) {
			singleInstance = true;

			TimeZone timeZone = getTimeZone(
				calendarBooking.getCalendar(), calendarBooking.isAllDay());

			splitJCalendar = JCalendarUtil.getJCalendar(
				calendarBooking.getStartTime(), timeZone);

			splitJCalendar.add(java.util.Calendar.DATE, 1);
		}

		for (CalendarBooking recurringCalendarBooking :
				recurringCalendarBookings) {

			if (recurringCalendarBooking.getStartTime() > startTime) {
				followingRecurringCalendarBookings.add(
					recurringCalendarBooking);
			}
			else if (singleInstance) {
				Recurrence recurrenceObj =
					recurringCalendarBooking.getRecurrenceObj();

				if (Validator.isNotNull(recurrenceObj)) {
					java.util.Calendar startTimeJCalendar =
						JCalendarUtil.getJCalendar(
							recurringCalendarBooking.getStartTime(),
							recurringCalendarBooking.getTimeZone());

					RecurrenceSplit recurrenceSplit = recurrenceSplitter.split(
						recurrenceObj, startTimeJCalendar, splitJCalendar);

					if (recurrenceSplit.isSplit()) {
						java.util.Calendar newStartTimeJCalendar =
							JCalendarUtil.mergeJCalendar(
								splitJCalendar, startTimeJCalendar,
								recurringCalendarBooking.getTimeZone());

						CalendarBooking newCalendarBooking =
							splitCalendarBookingInstance(
								recurringCalendarBooking,
								newStartTimeJCalendar.getTimeInMillis(),
								recurrenceSplit.getSecondRecurrence());

						followingRecurringCalendarBookings.add(
							newCalendarBooking);
					}
				}
			}
		}

		return followingRecurringCalendarBookings;
	}

	protected void updateCalendarBookingsByChanges(
			long userId, long calendarId, long[] childCalendarIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType, ServiceContext serviceContext,
			List<CalendarBooking> calendarBookings,
			List<String> unmodifiedAttributeNames)
		throws PortalException {

		for (CalendarBooking calendarBooking : calendarBookings) {
			long calendarBookingId = calendarBooking.getCalendarBookingId();

			if (unmodifiedAttributeNames.contains("calendarId")) {
				calendarId = calendarBooking.getCalendarId();
			}

			if (unmodifiedAttributeNames.contains("titleMap")) {
				titleMap = calendarBooking.getTitleMap();
			}

			if (unmodifiedAttributeNames.contains("descriptionMap")) {
				descriptionMap = calendarBooking.getDescriptionMap();
			}

			if (unmodifiedAttributeNames.contains("location")) {
				location = calendarBooking.getLocation();
			}

			if (unmodifiedAttributeNames.contains("time")) {
				startTime = calendarBooking.getStartTime();
				endTime = calendarBooking.getEndTime();
			}
			else {
				startTime = JCalendarUtil.convertTimeToNewDay(
					startTime, calendarBooking.getStartTime());

				endTime = JCalendarUtil.convertTimeToNewDay(
					endTime, calendarBooking.getEndTime());
			}

			if (unmodifiedAttributeNames.contains("allDay")) {
				allDay = calendarBooking.getAllDay();
			}

			if (unmodifiedAttributeNames.contains("firstReminder")) {
				firstReminder = calendarBooking.getFirstReminder();
			}

			if (unmodifiedAttributeNames.contains("firstReminderType")) {
				firstReminderType = calendarBooking.getFirstReminderType();
			}

			if (unmodifiedAttributeNames.contains("secondReminder")) {
				secondReminder = calendarBooking.getSecondReminder();
			}

			if (unmodifiedAttributeNames.contains("secondReminderType")) {
				secondReminderType = calendarBooking.getSecondReminderType();
			}

			updateCalendarBooking(
				userId, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				calendarBooking.getRecurrence(), firstReminder,
				firstReminderType, secondReminder, secondReminderType,
				serviceContext);
		}
	}

	protected void updateChildCalendarBookings(
		CalendarBooking calendarBooking, Date modifiedDate, String recurrence) {

		List<CalendarBooking> childCalendarBookings = new ArrayList<>();

		List<CalendarBooking> recurringCalendarBookings = new ArrayList<>();

		if (calendarBooking.isMasterBooking()) {
			childCalendarBookings = getChildCalendarBookings(
				calendarBooking.getCalendarBookingId());
		}
		else {
			childCalendarBookings.add(calendarBooking);
		}

		for (CalendarBooking childCalendarBooking : childCalendarBookings) {
			recurringCalendarBookings.addAll(
				getRecurringCalendarBookings(childCalendarBooking));

			recurringCalendarBookings.remove(childCalendarBooking);

			childCalendarBooking.setModifiedDate(modifiedDate);
			childCalendarBooking.setRecurrence(recurrence);

			calendarBookingPersistence.update(childCalendarBooking);
		}
	}

	protected void validate(
			java.util.Calendar startTimeJCalendar,
			java.util.Calendar endTimeJCalendar, String recurrence)
		throws PortalException {

		if (startTimeJCalendar.after(endTimeJCalendar)) {
			throw new CalendarBookingDurationException();
		}

		if (Validator.isNull(recurrence)) {
			return;
		}

		Recurrence recurrenceObj = RecurrenceSerializer.deserialize(
			recurrence, startTimeJCalendar.getTimeZone());

		if ((recurrenceObj.getUntilJCalendar() != null) &&
			JCalendarUtil.isLaterDay(
				startTimeJCalendar, recurrenceObj.getUntilJCalendar())) {

			throw new CalendarBookingRecurrenceException();
		}
	}

	@ServiceReference(type = RecurrenceSplitter.class)
	protected RecurrenceSplitter recurrenceSplitter;

	@ServiceReference(type = SubscriptionLocalService.class)
	protected SubscriptionLocalService subscriptionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarBookingLocalServiceImpl.class);

}