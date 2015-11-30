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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for CalEvent. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CalEventLocalServiceUtil
 * @see com.liferay.portlet.calendar.service.base.CalEventLocalServiceBaseImpl
 * @see com.liferay.portlet.calendar.service.impl.CalEventLocalServiceImpl
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CalEventLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CalEventLocalServiceUtil} to access the cal event local service. Add custom service methods to {@link com.liferay.portlet.calendar.service.impl.CalEventLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cal event to the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent);

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.calendar.model.CalEvent addEvent(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int durationHour, int durationMinute, boolean allDay,
		boolean timeZoneSensitive, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEvent(long, String,
	String, String, int, int, int, int, int, int, int, boolean,
	boolean, String, boolean, TZSRecurrence, int, int, int,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
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
		throws PortalException;

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	public void addEventResources(long eventId, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addEventResources(long eventId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	public void checkEvents();

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId);

	/**
	* Deletes the cal event from the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent);

	/**
	* Deletes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws PortalException if a cal event with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.calendar.model.CalEvent deleteCalEvent(
		long eventId) throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(
		com.liferay.portlet.calendar.model.CalEvent event)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.calendar.model.CalEvent deleteEvent(long eventId)
		throws PortalException;

	public void deleteEvents(long groupId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	public java.io.File exportEvent(long userId, long eventId)
		throws PortalException;

	public java.io.File exportEvents(long userId,
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> events,
		java.lang.String fileName) throws PortalException;

	public java.io.File exportGroupEvents(long userId, long groupId,
		java.lang.String fileName) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.calendar.model.CalEvent fetchCalEvent(
		long eventId);

	/**
	* Returns the cal event matching the UUID and group.
	*
	* @param uuid the cal event's UUID
	* @param groupId the primary key of the group
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.calendar.model.CalEvent fetchCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cal event with the primary key.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws PortalException if a cal event with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.calendar.model.CalEvent getCalEvent(long eventId)
		throws PortalException;

	/**
	* Returns the cal event matching the UUID and group.
	*
	* @param uuid the cal event's UUID
	* @param groupId the primary key of the group
	* @return the matching cal event
	* @throws PortalException if a matching cal event could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.calendar.model.CalEvent getCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end);

	/**
	* Returns all the cal events matching the UUID and company.
	*
	* @param uuid the UUID of the cal events
	* @param companyId the primary key of the company
	* @return the matching cal events, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEventsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEventsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.calendar.model.CalEvent> orderByComparator);

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCalEventsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCompanyEvents(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyEventsCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String[] types, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEventsCount(long groupId, java.lang.String type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEventsCount(long groupId, java.lang.String[] types);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getNoAssetEvents();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasEvents(long groupId, java.util.Calendar cal);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String[] types);

	public void importICal4j(long userId, long groupId,
		java.io.InputStream inputStream) throws PortalException;

	public void updateAsset(long userId,
		com.liferay.portlet.calendar.model.CalEvent event,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds) throws PortalException;

	/**
	* Updates the cal event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event
	* @return the cal event that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent);

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
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
		throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEvent(long, long,
	String, String, String, int, int, int, int, int, int, int,
	boolean, boolean, String, boolean, TZSRecurrence, int, int,
	int, ServiceContext)}
	*/
	@java.lang.Deprecated
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
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
		throws PortalException;
}