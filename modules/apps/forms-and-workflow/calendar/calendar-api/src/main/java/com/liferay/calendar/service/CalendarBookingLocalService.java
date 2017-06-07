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

import aQute.bnd.annotation.ProviderType;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PermissionedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CalendarBooking. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingLocalServiceUtil
 * @see com.liferay.calendar.service.base.CalendarBookingLocalServiceBaseImpl
 * @see com.liferay.calendar.service.impl.CalendarBookingLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CalendarBookingLocalService extends BaseLocalService,
	PermissionedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CalendarBookingLocalServiceUtil} to access the calendar booking local service. Add custom service methods to {@link com.liferay.calendar.service.impl.CalendarBookingLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasExclusiveCalendarBooking(Calendar calendar,
		long startTime, long endTime) throws PortalException;

	/**
	* Adds the calendar booking to the database. Also notifies the appropriate model listeners.
	*
	* @param calendarBooking the calendar booking
	* @return the calendar booking that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CalendarBooking addCalendarBooking(CalendarBooking calendarBooking);

	/**
	* @deprecated As of 2.2.0, replaced by {@link #addCalendarBooking(long,
	long, long[], long, long, Map, Map, String, long, long,
	boolean, String, long, String, long, String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public CalendarBooking addCalendarBooking(long userId, long calendarId,
		long[] childCalendarIds, long parentCalendarBookingId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, long firstReminder,
		java.lang.String firstReminderType, long secondReminder,
		java.lang.String secondReminderType, ServiceContext serviceContext)
		throws PortalException;

	public CalendarBooking addCalendarBooking(long userId, long calendarId,
		long[] childCalendarIds, long parentCalendarBookingId,
		long recurringCalendarBookingId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, long firstReminder,
		java.lang.String firstReminderType, long secondReminder,
		java.lang.String secondReminderType, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new calendar booking with the primary key. Does not add the calendar booking to the database.
	*
	* @param calendarBookingId the primary key for the new calendar booking
	* @return the new calendar booking
	*/
	public CalendarBooking createCalendarBooking(long calendarBookingId);

	/**
	* Deletes the calendar booking from the database. Also notifies the appropriate model listeners.
	*
	* @param calendarBooking the calendar booking
	* @return the calendar booking that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public CalendarBooking deleteCalendarBooking(
		CalendarBooking calendarBooking) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public CalendarBooking deleteCalendarBooking(
		CalendarBooking calendarBooking, boolean allRecurringInstances)
		throws PortalException;

	/**
	* Deletes the calendar booking with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param calendarBookingId the primary key of the calendar booking
	* @return the calendar booking that was removed
	* @throws PortalException if a calendar booking with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CalendarBooking deleteCalendarBooking(long calendarBookingId)
		throws PortalException;

	public CalendarBooking deleteCalendarBooking(long calendarBookingId,
		boolean allRecurringInstances) throws PortalException;

	public CalendarBooking deleteRecurringCalendarBooking(
		CalendarBooking calendarBooking) throws PortalException;

	public CalendarBooking deleteRecurringCalendarBooking(
		long calendarBookingId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking fetchCalendarBooking(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking fetchCalendarBooking(long calendarBookingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking fetchCalendarBooking(long calendarId,
		java.lang.String vEventUid);

	/**
	* Returns the calendar booking matching the UUID and group.
	*
	* @param uuid the calendar booking's UUID
	* @param groupId the primary key of the group
	* @return the matching calendar booking, or <code>null</code> if a matching calendar booking could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking fetchCalendarBookingByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the calendar booking with the primary key.
	*
	* @param calendarBookingId the primary key of the calendar booking
	* @return the calendar booking
	* @throws PortalException if a calendar booking with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking getCalendarBooking(long calendarBookingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking getCalendarBooking(long calendarId,
		long parentCalendarBookingId) throws PortalException;

	/**
	* Returns the calendar booking matching the UUID and group.
	*
	* @param uuid the calendar booking's UUID
	* @param groupId the primary key of the group
	* @return the matching calendar booking
	* @throws PortalException if a matching calendar booking could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking getCalendarBookingByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking getCalendarBookingInstance(long calendarBookingId,
		int instanceIndex) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CalendarBooking getLastInstanceCalendarBooking(
		CalendarBooking calendarBooking);

	public CalendarBooking invokeTransition(long userId,
		CalendarBooking calendarBooking, long startTime, int status,
		boolean updateInstance, boolean allFollowing,
		ServiceContext serviceContext) throws PortalException;

	public CalendarBooking moveCalendarBookingToTrash(long userId,
		CalendarBooking calendarBooking) throws PortalException;

	public CalendarBooking moveCalendarBookingToTrash(long userId,
		long calendarBookingId) throws PortalException;

	public CalendarBooking restoreCalendarBookingFromTrash(long userId,
		long calendarBookingId) throws PortalException;

	/**
	* Updates the calendar booking in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param calendarBooking the calendar booking
	* @return the calendar booking that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CalendarBooking updateCalendarBooking(
		CalendarBooking calendarBooking);

	public CalendarBooking updateCalendarBooking(long userId,
		long calendarBookingId, long calendarId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, long firstReminder,
		java.lang.String firstReminderType, long secondReminder,
		java.lang.String secondReminderType, ServiceContext serviceContext)
		throws PortalException;

	public CalendarBooking updateCalendarBooking(long userId,
		long calendarBookingId, long calendarId, long[] childCalendarIds,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, long firstReminder,
		java.lang.String firstReminderType, long secondReminder,
		java.lang.String secondReminderType, ServiceContext serviceContext)
		throws PortalException;

	public CalendarBooking updateCalendarBookingInstance(long userId,
		long calendarBookingId, int instanceIndex, long calendarId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, boolean allFollowing,
		long firstReminder, java.lang.String firstReminderType,
		long secondReminder, java.lang.String secondReminderType,
		ServiceContext serviceContext) throws PortalException;

	public CalendarBooking updateCalendarBookingInstance(long userId,
		long calendarBookingId, int instanceIndex, long calendarId,
		long[] childCalendarIds, Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, boolean allFollowing, long firstReminder,
		java.lang.String firstReminderType, long secondReminder,
		java.lang.String secondReminderType, ServiceContext serviceContext)
		throws PortalException;

	public CalendarBooking updateCalendarBookingInstance(long userId,
		long calendarBookingId, int instanceIndex, long calendarId,
		long[] childCalendarIds, Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, java.lang.String recurrence, boolean allFollowing,
		long firstReminder, java.lang.String firstReminderType,
		long secondReminder, java.lang.String secondReminderType,
		ServiceContext serviceContext) throws PortalException;

	public CalendarBooking updateRecurringCalendarBooking(long userId,
		long calendarBookingId, long calendarId, long[] childCalendarIds,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String location, long startTime, long endTime,
		boolean allDay, long firstReminder, java.lang.String firstReminderType,
		long secondReminder, java.lang.String secondReminderType,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CalendarBooking updateStatus(long userId,
		CalendarBooking calendarBooking, int status,
		ServiceContext serviceContext) throws PortalException;

	public CalendarBooking updateStatus(long userId, long calendarBookingId,
		int status, ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the number of calendar bookings.
	*
	* @return the number of calendar bookings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCalendarBookingsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCalendarBookingsCount(long calendarId,
		long parentCalendarBookingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		java.lang.String keywords, long startTime, long endTime, int[] statuses);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds, long[] calendarIds,
		long[] calendarResourceIds, long parentCalendarBookingId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, long startTime, long endTime,
		int[] statuses, boolean andOperator);

	public java.lang.String exportCalendarBooking(long calendarBookingId,
		java.lang.String type) throws java.lang.Exception;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the calendar bookings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of calendar bookings
	* @param end the upper bound of the range of calendar bookings (not inclusive)
	* @return the range of calendar bookings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookings(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookings(long calendarId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookings(long calendarId,
		int[] statuses);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookings(long calendarId,
		long startTime, long endTime);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookings(long calendarId,
		long startTime, long endTime, int max);

	/**
	* Returns all the calendar bookings matching the UUID and company.
	*
	* @param uuid the UUID of the calendar bookings
	* @param companyId the primary key of the company
	* @return the matching calendar bookings, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookingsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getCalendarBookingsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarBooking> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getChildCalendarBookings(
		long calendarBookingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getChildCalendarBookings(
		long parentCalendarBookingId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getRecurringCalendarBookings(
		CalendarBooking calendarBooking);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> getRecurringCalendarBookings(
		CalendarBooking calendarBooking, long startTime);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> search(long companyId, long[] groupIds,
		long[] calendarIds, long[] calendarResourceIds,
		long parentCalendarBookingId, java.lang.String keywords,
		long startTime, long endTime, boolean recurring, int[] statuses,
		int start, int end, OrderByComparator<CalendarBooking> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CalendarBooking> search(long companyId, long[] groupIds,
		long[] calendarIds, long[] calendarResourceIds,
		long parentCalendarBookingId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		long startTime, long endTime, boolean recurring, int[] statuses,
		boolean andOperator, int start, int end,
		OrderByComparator<CalendarBooking> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getChildCalendarIds(long calendarBookingId, long calendarId)
		throws PortalException;

	public void checkCalendarBookings() throws PortalException;

	public void deleteCalendarBookingInstance(CalendarBooking calendarBooking,
		int instanceIndex, boolean allFollowing) throws PortalException;

	public void deleteCalendarBookingInstance(CalendarBooking calendarBooking,
		int instanceIndex, boolean allFollowing,
		boolean deleteRecurringCalendarBookings) throws PortalException;

	public void deleteCalendarBookingInstance(CalendarBooking calendarBooking,
		long startTime, boolean allFollowing) throws PortalException;

	public void deleteCalendarBookingInstance(CalendarBooking calendarBooking,
		long startTime, boolean allFollowing,
		boolean deleteRecurringCalendarBookings) throws PortalException;

	public void deleteCalendarBookingInstance(long calendarBookingId,
		long startTime, boolean allFollowing) throws PortalException;

	public void deleteCalendarBookings(long calendarId)
		throws PortalException;

	public void updateAsset(long userId, CalendarBooking calendarBooking,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws PortalException;

	public void updateLastInstanceCalendarBookingRecurrence(
		CalendarBooking calendarBooking, java.lang.String recurrence);
}