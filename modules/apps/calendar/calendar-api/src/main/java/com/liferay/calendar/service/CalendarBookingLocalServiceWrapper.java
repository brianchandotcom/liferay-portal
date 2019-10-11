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

package com.liferay.calendar.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarBookingLocalService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingLocalService
 * @generated
 */
public class CalendarBookingLocalServiceWrapper
	implements CalendarBookingLocalService,
			   ServiceWrapper<CalendarBookingLocalService> {

	public CalendarBookingLocalServiceWrapper(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	/**
	 * Adds the calendar booking to the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarBooking the calendar booking
	 * @return the calendar booking that was added
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking addCalendarBooking(
		com.liferay.calendar.model.CalendarBooking calendarBooking) {

		return _calendarBookingLocalService.addCalendarBooking(calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			long userId, long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.addCalendarBooking(
			userId, calendarId, childCalendarIds, parentCalendarBookingId,
			recurringCalendarBookingId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addCalendarBooking(long, long, long[], long, long, Map, Map,
	 String, long, long, boolean, String, long, String, long,
	 String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			long userId, long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.addCalendarBooking(
			userId, calendarId, childCalendarIds, parentCalendarBookingId,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			recurrence, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public void checkCalendarBookings()
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.checkCalendarBookings();
	}

	/**
	 * Creates a new calendar booking with the primary key. Does not add the calendar booking to the database.
	 *
	 * @param calendarBookingId the primary key for the new calendar booking
	 * @return the new calendar booking
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking createCalendarBooking(
		long calendarBookingId) {

		return _calendarBookingLocalService.createCalendarBooking(
			calendarBookingId);
	}

	/**
	 * Deletes the calendar booking from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarBooking the calendar booking
	 * @return the calendar booking that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking deleteCalendarBooking(
			com.liferay.calendar.model.CalendarBooking calendarBooking)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteCalendarBooking(
			calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking deleteCalendarBooking(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			boolean allRecurringInstances)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteCalendarBooking(
			calendarBooking, allRecurringInstances);
	}

	/**
	 * Deletes the calendar booking with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarBookingId the primary key of the calendar booking
	 * @return the calendar booking that was removed
	 * @throws PortalException if a calendar booking with the primary key could not be found
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking deleteCalendarBooking(
			long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteCalendarBooking(
			calendarBookingId);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking deleteCalendarBooking(
			long calendarBookingId, boolean allRecurringInstances)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteCalendarBooking(
			calendarBookingId, allRecurringInstances);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #deleteCalendarBookingInstance(long, CalendarBooking, int,
	 boolean)}
	 */
	@Deprecated
	@Override
	public void deleteCalendarBookingInstance(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			int instanceIndex, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			calendarBooking, instanceIndex, allFollowing);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #deleteCalendarBookingInstance(long, CalendarBooking, int,
	 boolean, boolean)}
	 */
	@Deprecated
	@Override
	public void deleteCalendarBookingInstance(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			int instanceIndex, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			calendarBooking, instanceIndex, allFollowing,
			deleteRecurringCalendarBookings);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #deleteCalendarBookingInstance(long, CalendarBooking, long,
	 boolean)}
	 */
	@Deprecated
	@Override
	public void deleteCalendarBookingInstance(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			calendarBooking, startTime, allFollowing);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #deleteCalendarBookingInstance(long, CalendarBooking, long,
	 boolean, boolean)}
	 */
	@Deprecated
	@Override
	public void deleteCalendarBookingInstance(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			calendarBooking, startTime, allFollowing,
			deleteRecurringCalendarBookings);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			int instanceIndex, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			userId, calendarBooking, instanceIndex, allFollowing);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			int instanceIndex, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			userId, calendarBooking, instanceIndex, allFollowing,
			deleteRecurringCalendarBookings);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			userId, calendarBooking, startTime, allFollowing);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			userId, calendarBooking, startTime, allFollowing,
			deleteRecurringCalendarBookings);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #deleteCalendarBookingInstance(long, long, long, boolean)}
	 */
	@Deprecated
	@Override
	public void deleteCalendarBookingInstance(
			long calendarBookingId, long startTime, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			calendarBookingId, startTime, allFollowing);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long userId, long calendarBookingId, long startTime,
			boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookingInstance(
			userId, calendarBookingId, startTime, allFollowing);
	}

	@Override
	public void deleteCalendarBookings(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.deleteCalendarBookings(calendarId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			deleteRecurringCalendarBooking(
				com.liferay.calendar.model.CalendarBooking calendarBooking)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteRecurringCalendarBooking(
			calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			deleteRecurringCalendarBooking(long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.deleteRecurringCalendarBooking(
			calendarBookingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _calendarBookingLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _calendarBookingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.calendar.model.impl.CalendarBookingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _calendarBookingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.calendar.model.impl.CalendarBookingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _calendarBookingLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _calendarBookingLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _calendarBookingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public String exportCalendarBooking(long calendarBookingId, String type)
		throws Exception {

		return _calendarBookingLocalService.exportCalendarBooking(
			calendarBookingId, type);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking fetchCalendarBooking(
		long calendarBookingId) {

		return _calendarBookingLocalService.fetchCalendarBooking(
			calendarBookingId);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking fetchCalendarBooking(
		long calendarId, String vEventUid) {

		return _calendarBookingLocalService.fetchCalendarBooking(
			calendarId, vEventUid);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking fetchCalendarBooking(
		String uuid, long groupId) {

		return _calendarBookingLocalService.fetchCalendarBooking(uuid, groupId);
	}

	/**
	 * Returns the calendar booking matching the UUID and group.
	 *
	 * @param uuid the calendar booking's UUID
	 * @param groupId the primary key of the group
	 * @return the matching calendar booking, or <code>null</code> if a matching calendar booking could not be found
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking
		fetchCalendarBookingByUuidAndGroupId(String uuid, long groupId) {

		return _calendarBookingLocalService.
			fetchCalendarBookingByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _calendarBookingLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the calendar booking with the primary key.
	 *
	 * @param calendarBookingId the primary key of the calendar booking
	 * @return the calendar booking
	 * @throws PortalException if a calendar booking with the primary key could not be found
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getCalendarBooking(
			calendarBookingId);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			long calendarId, long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getCalendarBooking(
			calendarId, parentCalendarBookingId);
	}

	/**
	 * Returns the calendar booking matching the UUID and group.
	 *
	 * @param uuid the calendar booking's UUID
	 * @param groupId the primary key of the group
	 * @return the matching calendar booking
	 * @throws PortalException if a matching calendar booking could not be found
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking
			getCalendarBookingByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getCalendarBookingByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			getCalendarBookingInstance(
				long calendarBookingId, int instanceIndex)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getCalendarBookingInstance(
			calendarBookingId, instanceIndex);
	}

	/**
	 * Returns a range of all the calendar bookings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.calendar.model.impl.CalendarBookingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar bookings
	 * @param end the upper bound of the range of calendar bookings (not inclusive)
	 * @return the range of calendar bookings
	 */
	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookings(int start, int end) {

		return _calendarBookingLocalService.getCalendarBookings(start, end);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookings(long calendarId) {

		return _calendarBookingLocalService.getCalendarBookings(calendarId);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookings(long calendarId, int[] statuses) {

		return _calendarBookingLocalService.getCalendarBookings(
			calendarId, statuses);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookings(long calendarId, long startTime, long endTime) {

		return _calendarBookingLocalService.getCalendarBookings(
			calendarId, startTime, endTime);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookings(
			long calendarId, long startTime, long endTime, int max) {

		return _calendarBookingLocalService.getCalendarBookings(
			calendarId, startTime, endTime, max);
	}

	/**
	 * Returns all the calendar bookings matching the UUID and company.
	 *
	 * @param uuid the UUID of the calendar bookings
	 * @param companyId the primary key of the company
	 * @return the matching calendar bookings, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookingsByUuidAndCompanyId(String uuid, long companyId) {

		return _calendarBookingLocalService.
			getCalendarBookingsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of calendar bookings matching the UUID and company.
	 *
	 * @param uuid the UUID of the calendar bookings
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of calendar bookings
	 * @param end the upper bound of the range of calendar bookings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching calendar bookings, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getCalendarBookingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.CalendarBooking>
					orderByComparator) {

		return _calendarBookingLocalService.
			getCalendarBookingsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of calendar bookings.
	 *
	 * @return the number of calendar bookings
	 */
	@Override
	public int getCalendarBookingsCount() {
		return _calendarBookingLocalService.getCalendarBookingsCount();
	}

	@Override
	public int getCalendarBookingsCount(
		long calendarId, long parentCalendarBookingId) {

		return _calendarBookingLocalService.getCalendarBookingsCount(
			calendarId, parentCalendarBookingId);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getChildCalendarBookings(long calendarBookingId) {

		return _calendarBookingLocalService.getChildCalendarBookings(
			calendarBookingId);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getChildCalendarBookings(long parentCalendarBookingId, int status) {

		return _calendarBookingLocalService.getChildCalendarBookings(
			parentCalendarBookingId, status);
	}

	@Override
	public long[] getChildCalendarIds(long calendarBookingId, long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getChildCalendarIds(
			calendarBookingId, calendarId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _calendarBookingLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _calendarBookingLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
		getLastInstanceCalendarBooking(
			com.liferay.calendar.model.CalendarBooking calendarBooking) {

		return _calendarBookingLocalService.getLastInstanceCalendarBooking(
			calendarBooking);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _calendarBookingLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getRecurringCalendarBookings(
			com.liferay.calendar.model.CalendarBooking calendarBooking) {

		return _calendarBookingLocalService.getRecurringCalendarBookings(
			calendarBooking);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking>
		getRecurringCalendarBookings(
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime) {

		return _calendarBookingLocalService.getRecurringCalendarBookings(
			calendarBooking, startTime);
	}

	@Override
	public boolean hasExclusiveCalendarBooking(
			com.liferay.calendar.model.Calendar calendar, long startTime,
			long endTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.hasExclusiveCalendarBooking(
			calendar, startTime, endTime);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking invokeTransition(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long startTime, int status, boolean updateInstance,
			boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.invokeTransition(
			userId, calendarBooking, startTime, status, updateInstance,
			allFollowing, serviceContext);
	}

	@Override
	public boolean isStagingCalendarBooking(
			com.liferay.calendar.model.CalendarBooking calendarBooking)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.isStagingCalendarBooking(
			calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			moveCalendarBookingToTrash(
				long userId,
				com.liferay.calendar.model.CalendarBooking calendarBooking)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.moveCalendarBookingToTrash(
			userId, calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			moveCalendarBookingToTrash(long userId, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.moveCalendarBookingToTrash(
			userId, calendarBookingId);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			restoreCalendarBookingFromTrash(long userId, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.restoreCalendarBookingFromTrash(
			userId, calendarBookingId);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking> search(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		String keywords, long startTime, long endTime, boolean recurring,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.calendar.model.CalendarBooking> orderByComparator) {

		return _calendarBookingLocalService.search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, recurring,
			statuses, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.calendar.model.CalendarBooking> search(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId, String title,
		String description, String location, long startTime, long endTime,
		boolean recurring, int[] statuses, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.calendar.model.CalendarBooking> orderByComparator) {

		return _calendarBookingLocalService.search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, recurring, statuses, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		String keywords, long startTime, long endTime, int[] statuses) {

		return _calendarBookingLocalService.searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, statuses);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId, String title,
		String description, String location, long startTime, long endTime,
		int[] statuses, boolean andOperator) {

		return _calendarBookingLocalService.searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, statuses, andOperator);
	}

	@Override
	public void updateAsset(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingLocalService.updateAsset(
			userId, calendarBooking, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, priority);
	}

	/**
	 * Updates the calendar booking in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param calendarBooking the calendar booking
	 * @return the calendar booking that was updated
	 */
	@Override
	public com.liferay.calendar.model.CalendarBooking updateCalendarBooking(
		com.liferay.calendar.model.CalendarBooking calendarBooking) {

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking updateCalendarBooking(
			long userId, long calendarBookingId, long calendarId,
			long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateCalendarBooking(
			userId, calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking updateCalendarBooking(
			long userId, long calendarBookingId, long calendarId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateCalendarBooking(
			userId, calendarBookingId, calendarId, titleMap, descriptionMap,
			location, startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				long userId, long calendarBookingId, int instanceIndex,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateCalendarBookingInstance(
			userId, calendarBookingId, instanceIndex, calendarId,
			childCalendarIds, titleMap, descriptionMap, location, startTime,
			endTime, allDay, allFollowing, firstReminder, firstReminderType,
			secondReminder, secondReminderType, serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				long userId, long calendarBookingId, int instanceIndex,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateCalendarBookingInstance(
			userId, calendarBookingId, instanceIndex, calendarId,
			childCalendarIds, titleMap, descriptionMap, location, startTime,
			endTime, allDay, recurrence, allFollowing, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				long userId, long calendarBookingId, int instanceIndex,
				long calendarId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateCalendarBookingInstance(
			userId, calendarBookingId, instanceIndex, calendarId, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			allFollowing, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public void updateLastInstanceCalendarBookingRecurrence(
		com.liferay.calendar.model.CalendarBooking calendarBooking,
		String recurrence) {

		_calendarBookingLocalService.
			updateLastInstanceCalendarBookingRecurrence(
				calendarBooking, recurrence);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking
			updateRecurringCalendarBooking(
				long userId, long calendarBookingId, long calendarId,
				long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateRecurringCalendarBooking(
			userId, calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking updateStatus(
			long userId,
			com.liferay.calendar.model.CalendarBooking calendarBooking,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateStatus(
			userId, calendarBooking, status, serviceContext);
	}

	@Override
	public com.liferay.calendar.model.CalendarBooking updateStatus(
			long userId, long calendarBookingId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingLocalService.updateStatus(
			userId, calendarBookingId, status, serviceContext);
	}

	@Override
	public CalendarBookingLocalService getWrappedService() {
		return _calendarBookingLocalService;
	}

	@Override
	public void setWrappedService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	private CalendarBookingLocalService _calendarBookingLocalService;

}