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

package com.liferay.powwow.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for PowwowMeeting. This utility wraps
 * {@link com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shinn Lok
 * @see PowwowMeetingLocalService
 * @see com.liferay.powwow.service.base.PowwowMeetingLocalServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl
 * @generated
 */
public class PowwowMeetingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the powwow meeting to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPowwowMeeting(powwowMeeting);
	}

	/**
	* Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	*
	* @param powwowMeetingId the primary key for the new powwow meeting
	* @return the new powwow meeting
	*/
	public static com.liferay.powwow.model.PowwowMeeting createPowwowMeeting(
		long powwowMeetingId) {
		return getService().createPowwowMeeting(powwowMeetingId);
	}

	/**
	* Deletes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting that was removed
	* @throws PortalException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePowwowMeeting(powwowMeetingId);
	}

	/**
	* Deletes the powwow meeting from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePowwowMeeting(powwowMeeting);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.powwow.model.PowwowMeeting fetchPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPowwowMeeting(powwowMeetingId);
	}

	/**
	* Returns the powwow meeting with the primary key.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting
	* @throws PortalException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeeting(powwowMeetingId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetings(start, end);
	}

	/**
	* Returns the number of powwow meetings.
	*
	* @return the number of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public static int getPowwowMeetingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetingsCount();
	}

	/**
	* Updates the powwow meeting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePowwowMeeting(powwowMeeting);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long userId, long groupId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPowwowMeeting(userId, groupId, powwowServerId, name,
			description, providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	public static void checkPowwowMeetings()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPowwowMeetings();
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getParticipantPowwowMeetings(
		long userId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getParticipantPowwowMeetings(userId, statuses, start, end,
			orderByComparator);
	}

	public static int getParticipantPowwowMeetingsCount(long userId,
		int[] statuses)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getParticipantPowwowMeetingsCount(userId, statuses);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetings(status);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetings(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, long userId, java.lang.String name,
		java.lang.String description, int status, boolean andSearch, int start,
		int end, java.lang.String orderByField, java.lang.String orderByType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPowwowMeetings(groupId, userId, name, description,
			status, andSearch, start, end, orderByField, orderByType);
	}

	public static int getPowwowMeetingsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetingsCount(groupId);
	}

	public static int getPowwowMeetingsCount(long powwowServerId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetingsCount(powwowServerId, status);
	}

	public static int getPowwowMeetingsCount(long groupId, long userId,
		java.lang.String name, java.lang.String description, int status,
		boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPowwowMeetingsCount(groupId, userId, name, description,
			status, andSearch);
	}

	public static int getUserPowwowMeetingsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserPowwowMeetingsCount(userId, status);
	}

	public static com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePowwowMeeting(powwowMeetingId, powwowServerId, name,
			description, providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	public static com.liferay.powwow.model.PowwowMeeting updateStatus(
		long powwowMeetingId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(powwowMeetingId, status);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowMeetingLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PowwowMeetingLocalService.class.getName());

			if (invokableLocalService instanceof PowwowMeetingLocalService) {
				_service = (PowwowMeetingLocalService)invokableLocalService;
			}
			else {
				_service = new PowwowMeetingLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PowwowMeetingLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PowwowMeetingLocalService service) {
	}

	private static PowwowMeetingLocalService _service;
}