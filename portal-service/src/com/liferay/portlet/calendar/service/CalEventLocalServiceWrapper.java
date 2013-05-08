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

package com.liferay.portlet.calendar.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalEventLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventLocalService
 * @generated
 */
public class CalEventLocalServiceWrapper implements CalEventLocalService,
	ServiceWrapper<CalEventLocalService> {
	public CalEventLocalServiceWrapper(
		CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	/**
	* Adds the cal event to the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.addCalEvent(calEvent);
	}

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId) {
		return _calEventLocalService.createCalEvent(eventId);
	}

	/**
	* Deletes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws PortalException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.deleteCalEvent(eventId);
	}

	/**
	* Deletes the cal event from the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.deleteCalEvent(calEvent);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _calEventLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.calendar.model.CalEvent fetchCalEvent(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.fetchCalEvent(eventId);
	}

	/**
	* Returns the cal event with the primary key.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws PortalException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent getCalEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEvent(eventId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the cal event matching the UUID and group.
	*
	* @param uuid the cal event's UUID
	* @param groupId the primary key of the group
	* @return the matching cal event
	* @throws PortalException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent getCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEventByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEvents(start, end);
	}

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	* @throws SystemException if a system exception occurred
	*/
	public int getCalEventsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEventsCount();
	}

	/**
	* Updates the cal event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateCalEvent(calEvent);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _calEventLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_calEventLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.calendar.model.CalEvent addEvent(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int durationHour, int durationMinute, boolean allDay,
		boolean timeZoneSensitive, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.addEvent(userId, title, description,
			location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEvent(long, String,
	String, String, int, int, int, int, int, int, int, boolean,
	boolean, String, boolean, TZSRecurrence, int, int, int,
	ServiceContext)}
	*/
	public com.liferay.portlet.calendar.model.CalEvent addEvent(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.addEvent(userId, title, description,
			location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(event, addGroupPermissions,
			addGuestPermissions);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(event, groupPermissions,
			guestPermissions);
	}

	public void addEventResources(long eventId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(eventId, addGroupPermissions,
			addGuestPermissions);
	}

	public void addEventResources(long eventId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(eventId, groupPermissions,
			guestPermissions);
	}

	public void checkEvents()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.checkEvents();
	}

	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(
		com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.deleteEvent(event);
	}

	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.deleteEvent(eventId);
	}

	public void deleteEvents(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteEvents(groupId);
	}

	public java.io.File exportEvent(long userId, long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.exportEvent(userId, eventId);
	}

	public java.io.File exportEvents(long userId,
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> events,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.exportEvents(userId, events, fileName);
	}

	public java.io.File exportGroupEvents(long userId, long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.exportGroupEvents(userId, groupId, fileName);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCompanyEvents(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCompanyEvents(companyId, start, end);
	}

	public int getCompanyEventsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCompanyEventsCount(companyId);
	}

	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvent(eventId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal, type);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal, types);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, type, start, end);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String[] types, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, types, start, end);
	}

	public int getEventsCount(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEventsCount(groupId, type);
	}

	public int getEventsCount(long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEventsCount(groupId, types);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getNoAssetEvents()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getNoAssetEvents();
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getRepeatingEvents(groupId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getRepeatingEvents(groupId, cal, types);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal, type);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal, types);
	}

	public void importICal4j(long userId, long groupId,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.importICal4j(userId, groupId, inputStream);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.calendar.model.CalEvent event,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.updateAsset(userId, event, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	public com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long userId, long eventId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateEvent(userId, eventId, title,
			description, location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEvent(long, long,
	String, String, String, int, int, int, int, int, int, int,
	boolean, boolean, String, boolean, TZSRecurrence, int, int,
	int, ServiceContext)}
	*/
	public com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long userId, long eventId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateEvent(userId, eventId, title,
			description, location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public CalEventLocalService getWrappedCalEventLocalService() {
		return _calEventLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedCalEventLocalService(
		CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	public CalEventLocalService getWrappedService() {
		return _calEventLocalService;
	}

	public void setWrappedService(CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	private CalEventLocalService _calEventLocalService;
}