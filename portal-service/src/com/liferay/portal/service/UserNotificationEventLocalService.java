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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for UserNotificationEvent. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventLocalServiceUtil
 * @see com.liferay.portal.service.base.UserNotificationEventLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserNotificationEventLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface UserNotificationEventLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserNotificationEventLocalServiceUtil} to access the user notification event local service. Add custom service methods to {@link com.liferay.portal.service.impl.UserNotificationEventLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the user notification event to the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEvent the user notification event
	* @return the user notification event that was added
	*/
	public com.liferay.portal.model.UserNotificationEvent addUserNotificationEvent(
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent);

	/**
	* Creates a new user notification event with the primary key. Does not add the user notification event to the database.
	*
	* @param userNotificationEventId the primary key for the new user notification event
	* @return the new user notification event
	*/
	public com.liferay.portal.model.UserNotificationEvent createUserNotificationEvent(
		long userNotificationEventId);

	/**
	* Deletes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEventId the primary key of the user notification event
	* @return the user notification event that was removed
	* @throws PortalException if a user notification event with the primary key could not be found
	*/
	public com.liferay.portal.model.UserNotificationEvent deleteUserNotificationEvent(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the user notification event from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEvent the user notification event
	* @return the user notification event that was removed
	*/
	public com.liferay.portal.model.UserNotificationEvent deleteUserNotificationEvent(
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserNotificationEvent fetchUserNotificationEvent(
		long userNotificationEventId);

	/**
	* Returns the user notification event with the matching UUID and company.
	*
	* @param uuid the user notification event's UUID
	* @param companyId the primary key of the company
	* @return the matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserNotificationEvent fetchUserNotificationEventByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns the user notification event with the primary key.
	*
	* @param userNotificationEventId the primary key of the user notification event
	* @return the user notification event
	* @throws PortalException if a user notification event with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserNotificationEvent getUserNotificationEvent(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user notification event with the matching UUID and company.
	*
	* @param uuid the user notification event's UUID
	* @param companyId the primary key of the company
	* @return the matching user notification event
	* @throws PortalException if a matching user notification event could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserNotificationEvent getUserNotificationEventByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns a range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of user notification events
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getUserNotificationEvents(
		int start, int end);

	/**
	* Returns the number of user notification events.
	*
	* @return the number of user notification events
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserNotificationEventsCount();

	/**
	* Updates the user notification event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEvent the user notification event
	* @return the user notification event that was updated
	*/
	public com.liferay.portal.model.UserNotificationEvent updateUserNotificationEvent(
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent);

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.portal.model.UserNotificationEvent addUserNotificationEvent(
		long userId,
		com.liferay.portal.kernel.notifications.NotificationEvent notificationEvent)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.UserNotificationEvent addUserNotificationEvent(
		long userId, java.lang.String type, long timestamp, int deliveryType,
		long deliverBy, java.lang.String payload, boolean archived,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0 {@link #addUserNotificationEvent(long, String,
	long, int, long, String, boolean, ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.UserNotificationEvent addUserNotificationEvent(
		long userId, java.lang.String type, long timestamp, long deliverBy,
		java.lang.String payload, boolean archived,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.UserNotificationEvent> addUserNotificationEvents(
		long userId,
		java.util.Collection<com.liferay.portal.kernel.notifications.NotificationEvent> notificationEvents)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteUserNotificationEvent(java.lang.String uuid,
		long companyId);

	public void deleteUserNotificationEvents(
		java.util.Collection<java.lang.String> uuids, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean archived);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean actionRequired, boolean archived);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean archived, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean actionRequired, boolean archived, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getArchivedUserNotificationEventsCount(long userId,
		boolean archived);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getArchivedUserNotificationEventsCount(long userId,
		boolean actionRequired, boolean archived);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean actionRequired, boolean delivered);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean actionRequired, boolean delivered, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDeliveredUserNotificationEventsCount(long userId,
		boolean delivered);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDeliveredUserNotificationEventsCount(long userId,
		boolean actionRequired, boolean delivered);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getUserNotificationEvents(
		long userId);

	/**
	* @deprecated As of 6.2.0 {@link #getArchivedUserNotificationEvents(long,
	boolean)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getUserNotificationEvents(
		long userId, boolean archived);

	/**
	* @deprecated As of 6.2.0 {@link #getArchivedUserNotificationEvents(long,
	boolean, int, int)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getUserNotificationEvents(
		long userId, boolean archived, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserNotificationEvent> getUserNotificationEvents(
		long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserNotificationEventsCount(long userId);

	/**
	* @deprecated As of 6.2.0 {@link
	#getArchivedUserNotificationEventsCount(long, boolean)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserNotificationEventsCount(long userId, boolean archived);

	public com.liferay.portal.model.UserNotificationEvent sendUserNotificationEvents(
		long userId, java.lang.String portletId, int deliveryType,
		com.liferay.portal.kernel.json.JSONObject notificationEventJSONObject)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.UserNotificationEvent updateUserNotificationEvent(
		java.lang.String uuid, long companyId, boolean archive);

	public java.util.List<com.liferay.portal.model.UserNotificationEvent> updateUserNotificationEvents(
		java.util.Collection<java.lang.String> uuids, long companyId,
		boolean archive);
}