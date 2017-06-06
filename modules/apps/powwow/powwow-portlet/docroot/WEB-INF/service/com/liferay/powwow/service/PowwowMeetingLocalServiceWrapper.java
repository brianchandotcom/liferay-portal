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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PowwowMeetingLocalService}.
 *
 * @author Shinn Lok
 * @see PowwowMeetingLocalService
 * @generated
 */
public class PowwowMeetingLocalServiceWrapper
	implements PowwowMeetingLocalService,
		ServiceWrapper<PowwowMeetingLocalService> {
	public PowwowMeetingLocalServiceWrapper(
		PowwowMeetingLocalService powwowMeetingLocalService) {
		_powwowMeetingLocalService = powwowMeetingLocalService;
	}

	/**
	* Adds the powwow meeting to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.addPowwowMeeting(powwowMeeting);
	}

	/**
	* Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	*
	* @param powwowMeetingId the primary key for the new powwow meeting
	* @return the new powwow meeting
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting createPowwowMeeting(
		long powwowMeetingId) {
		return _powwowMeetingLocalService.createPowwowMeeting(powwowMeetingId);
	}

	/**
	* Deletes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting that was removed
	* @throws PortalException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.deletePowwowMeeting(powwowMeetingId);
	}

	/**
	* Deletes the powwow meeting from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.deletePowwowMeeting(powwowMeeting);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _powwowMeetingLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting fetchPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.fetchPowwowMeeting(powwowMeetingId);
	}

	/**
	* Returns the powwow meeting with the primary key.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting
	* @throws PortalException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeeting(powwowMeetingId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetings(start, end);
	}

	/**
	* Returns the number of powwow meetings.
	*
	* @return the number of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getPowwowMeetingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetingsCount();
	}

	/**
	* Updates the powwow meeting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowMeeting the powwow meeting
	* @return the powwow meeting that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.updatePowwowMeeting(powwowMeeting);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _powwowMeetingLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_powwowMeetingLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowMeetingLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long userId, long groupId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.addPowwowMeeting(userId, groupId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	@Override
	public void checkPowwowMeetings()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_powwowMeetingLocalService.checkPowwowMeetings();
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getParticipantPowwowMeetings(
		long userId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getParticipantPowwowMeetings(userId,
			statuses, start, end, orderByComparator);
	}

	@Override
	public int getParticipantPowwowMeetingsCount(long userId, int[] statuses)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getParticipantPowwowMeetingsCount(userId,
			statuses);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetings(status);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetings(groupId, start,
			end, obc);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, long userId, java.lang.String name,
		java.lang.String description, int status, boolean andSearch, int start,
		int end, java.lang.String orderByField, java.lang.String orderByType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetings(groupId, userId,
			name, description, status, andSearch, start, end, orderByField,
			orderByType);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetingsCount(groupId);
	}

	@Override
	public int getPowwowMeetingsCount(long powwowServerId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetingsCount(powwowServerId,
			status);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId, long userId,
		java.lang.String name, java.lang.String description, int status,
		boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getPowwowMeetingsCount(groupId,
			userId, name, description, status, andSearch);
	}

	@Override
	public int getUserPowwowMeetingsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.getUserPowwowMeetingsCount(userId,
			status);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.updatePowwowMeeting(powwowMeetingId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updateStatus(
		long powwowMeetingId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingLocalService.updateStatus(powwowMeetingId, status);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PowwowMeetingLocalService getWrappedPowwowMeetingLocalService() {
		return _powwowMeetingLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPowwowMeetingLocalService(
		PowwowMeetingLocalService powwowMeetingLocalService) {
		_powwowMeetingLocalService = powwowMeetingLocalService;
	}

	@Override
	public PowwowMeetingLocalService getWrappedService() {
		return _powwowMeetingLocalService;
	}

	@Override
	public void setWrappedService(
		PowwowMeetingLocalService powwowMeetingLocalService) {
		_powwowMeetingLocalService = powwowMeetingLocalService;
	}

	private PowwowMeetingLocalService _powwowMeetingLocalService;
}