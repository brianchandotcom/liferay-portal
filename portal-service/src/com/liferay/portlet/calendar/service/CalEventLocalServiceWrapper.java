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

package com.liferay.portlet.calendar.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalEventLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CalEventLocalService
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
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
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent) {
		return _calEventLocalService.addCalEvent(calEvent);
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent addEvent(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int durationHour, int durationMinute, boolean allDay,
		boolean timeZoneSensitive, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.addEvent(userId, title, description,
			location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	@Override
	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.addEventResources(event, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.addEventResources(event, modelPermissions);
	}

	@Override
	public void addEventResources(long eventId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.addEventResources(eventId, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEventResources(long eventId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.addEventResources(eventId, modelPermissions);
	}

	@Override
	public void checkEvents() {
		_calEventLocalService.checkEvents();
	}

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId) {
		return _calEventLocalService.createCalEvent(eventId);
	}

	/**
	* Deletes the cal event from the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was removed
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent) {
		return _calEventLocalService.deleteCalEvent(calEvent);
	}

	/**
	* Deletes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws PortalException if a cal event with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.deleteCalEvent(eventId);
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(
		com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.deleteEvent(event);
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.deleteEvent(eventId);
	}

	@Override
	public void deleteEvents(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.deleteEvents(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _calEventLocalService.dynamicQuery();
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _calEventLocalService.dynamicQueryCount(dynamicQuery);
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
		return _calEventLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public java.io.File exportEvent(long userId, long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.exportEvent(userId, eventId);
	}

	@Override
	public java.io.File exportEvents(long userId,
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> events,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.exportEvents(userId, events, fileName);
	}

	@Override
	public java.io.File exportGroupEvents(long userId, long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.exportGroupEvents(userId, groupId, fileName);
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent fetchCalEvent(
		long eventId) {
		return _calEventLocalService.fetchCalEvent(eventId);
	}

	/**
	* Returns the cal event matching the UUID and group.
	*
	* @param uuid the cal event's UUID
	* @param groupId the primary key of the group
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent fetchCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _calEventLocalService.fetchCalEventByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _calEventLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cal event with the primary key.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws PortalException if a cal event with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent getCalEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.getCalEvent(eventId);
	}

	/**
	* Returns the cal event matching the UUID and group.
	*
	* @param uuid the cal event's UUID
	* @param groupId the primary key of the group
	* @return the matching cal event
	* @throws PortalException if a matching cal event could not be found
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent getCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
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
	*/
	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end) {
		return _calEventLocalService.getCalEvents(start, end);
	}

	/**
	* Returns all the cal events matching the UUID and company.
	*
	* @param uuid the UUID of the cal events
	* @param companyId the primary key of the company
	* @return the matching cal events, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEventsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _calEventLocalService.getCalEventsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cal events matching the UUID and company.
	*
	* @param uuid the UUID of the cal events
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cal events, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEventsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.calendar.model.CalEvent> orderByComparator) {
		return _calEventLocalService.getCalEventsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	*/
	@Override
	public int getCalEventsCount() {
		return _calEventLocalService.getCalEventsCount();
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCompanyEvents(
		long companyId, int start, int end) {
		return _calEventLocalService.getCompanyEvents(companyId, start, end);
	}

	@Override
	public int getCompanyEventsCount(long companyId) {
		return _calEventLocalService.getCompanyEventsCount(companyId);
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.getEvent(eventId);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal) {
		return _calEventLocalService.getEvents(groupId, cal);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type) {
		return _calEventLocalService.getEvents(groupId, cal, type);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types) {
		return _calEventLocalService.getEvents(groupId, cal, types);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end) {
		return _calEventLocalService.getEvents(groupId, type, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String[] types, int start, int end) {
		return _calEventLocalService.getEvents(groupId, types, start, end);
	}

	@Override
	public int getEventsCount(long groupId, java.lang.String type) {
		return _calEventLocalService.getEventsCount(groupId, type);
	}

	@Override
	public int getEventsCount(long groupId, java.lang.String[] types) {
		return _calEventLocalService.getEventsCount(groupId, types);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _calEventLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _calEventLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getNoAssetEvents() {
		return _calEventLocalService.getNoAssetEvents();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _calEventLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId) {
		return _calEventLocalService.getRepeatingEvents(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types) {
		return _calEventLocalService.getRepeatingEvents(groupId, cal, types);
	}

	@Override
	public boolean hasEvents(long groupId, java.util.Calendar cal) {
		return _calEventLocalService.hasEvents(groupId, cal);
	}

	@Override
	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type) {
		return _calEventLocalService.hasEvents(groupId, cal, type);
	}

	@Override
	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String[] types) {
		return _calEventLocalService.hasEvents(groupId, cal, types);
	}

	@Override
	public void importICal4j(long userId, long groupId,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.importICal4j(userId, groupId, inputStream);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.portlet.calendar.model.CalEvent event,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calEventLocalService.updateAsset(userId, event, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	/**
	* Updates the cal event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was updated
	*/
	@Override
	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent) {
		return _calEventLocalService.updateCalEvent(calEvent);
	}

	@Override
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
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calEventLocalService.updateEvent(userId, eventId, title,
			description, location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	@Override
	public CalEventLocalService getWrappedService() {
		return _calEventLocalService;
	}

	@Override
	public void setWrappedService(CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	private CalEventLocalService _calEventLocalService;
}