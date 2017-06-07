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
import com.liferay.portal.kernel.service.InvokableLocalService;

/**
 * Provides the local service utility for PowwowParticipant. This utility wraps
 * {@link com.liferay.powwow.service.impl.PowwowParticipantLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shinn Lok
 * @see PowwowParticipantLocalService
 * @see com.liferay.powwow.service.base.PowwowParticipantLocalServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowParticipantLocalServiceImpl
 * @generated
 */
public class PowwowParticipantLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowParticipantLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the powwow participant to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant addPowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPowwowParticipant(powwowParticipant);
	}

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public static com.liferay.powwow.model.PowwowParticipant createPowwowParticipant(
		long powwowParticipantId) {
		return getService().createPowwowParticipant(powwowParticipantId);
	}

	/**
	* Deletes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws PortalException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePowwowParticipant(powwowParticipantId);
	}

	/**
	* Deletes the powwow participant from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePowwowParticipant(powwowParticipant);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPowwowParticipant(powwowParticipantId);
	}

	/**
	* Returns the powwow participant with the primary key.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws PortalException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant getPowwowParticipant(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipant(powwowParticipantId);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipants(start, end);
	}

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int getPowwowParticipantsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipantsCount();
	}

	/**
	* Updates the powwow participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePowwowParticipant(powwowParticipant);
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

	public static com.liferay.powwow.model.PowwowParticipant addPowwowParticipant(
		long userId, long groupId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status, com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPowwowParticipant(userId, groupId, powwowMeetingId,
			name, participantUserId, emailAddress, type, status, serviceContext);
	}

	public static com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchPowwowParticipant(powwowMeetingId, participantUserId);
	}

	public static com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPowwowParticipant(powwowMeetingId, emailAddress);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipants(powwowMeetingId);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipants(powwowMeetingId, type);
	}

	public static int getPowwowParticipantsCount(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipantsCount(powwowMeetingId);
	}

	public static int getPowwowParticipantsCount(long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowParticipantsCount(powwowMeetingId, type);
	}

	public static com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		long powwowParticipantId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status, com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePowwowParticipant(powwowParticipantId,
			powwowMeetingId, name, participantUserId, emailAddress, type,
			status, serviceContext);
	}

	public static com.liferay.powwow.model.PowwowParticipant updateStatus(
		long powwowParticipantId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(powwowParticipantId, status);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowParticipantLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PowwowParticipantLocalService.class.getName());

			if (invokableLocalService instanceof PowwowParticipantLocalService) {
				_service = (PowwowParticipantLocalService)invokableLocalService;
			}
			else {
				_service = new PowwowParticipantLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PowwowParticipantLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PowwowParticipantLocalService service) {
	}

	private static PowwowParticipantLocalService _service;
}