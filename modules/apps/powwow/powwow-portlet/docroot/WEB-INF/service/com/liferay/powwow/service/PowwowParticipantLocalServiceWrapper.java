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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PowwowParticipantLocalService}.
 *
 * @author Shinn Lok
 * @see PowwowParticipantLocalService
 * @generated
 */
@ProviderType
public class PowwowParticipantLocalServiceWrapper
	implements PowwowParticipantLocalService,
		ServiceWrapper<PowwowParticipantLocalService> {
	public PowwowParticipantLocalServiceWrapper(
		PowwowParticipantLocalService powwowParticipantLocalService) {
		_powwowParticipantLocalService = powwowParticipantLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _powwowParticipantLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _powwowParticipantLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _powwowParticipantLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the powwow participant to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was added
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant addPowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant) {
		return _powwowParticipantLocalService.addPowwowParticipant(powwowParticipant);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant addPowwowParticipant(
		long userId, long groupId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.addPowwowParticipant(userId,
			groupId, powwowMeetingId, name, participantUserId, emailAddress,
			type, status, serviceContext);
	}

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant createPowwowParticipant(
		long powwowParticipantId) {
		return _powwowParticipantLocalService.createPowwowParticipant(powwowParticipantId);
	}

	/**
	* Deletes the powwow participant from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was removed
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant) {
		return _powwowParticipantLocalService.deletePowwowParticipant(powwowParticipant);
	}

	/**
	* Deletes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws PortalException if a powwow participant with the primary key could not be found
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.deletePowwowParticipant(powwowParticipantId);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, java.lang.String emailAddress) {
		return _powwowParticipantLocalService.fetchPowwowParticipant(powwowMeetingId,
			emailAddress);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, long participantUserId) {
		return _powwowParticipantLocalService.fetchPowwowParticipant(powwowMeetingId,
			participantUserId);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant fetchPowwowParticipant(
		long powwowParticipantId) {
		return _powwowParticipantLocalService.fetchPowwowParticipant(powwowParticipantId);
	}

	/**
	* Returns the powwow participant with the primary key.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws PortalException if a powwow participant with the primary key could not be found
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant getPowwowParticipant(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.getPowwowParticipant(powwowParticipantId);
	}

	/**
	* Updates the powwow participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was updated
	*/
	@Override
	public com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant) {
		return _powwowParticipantLocalService.updatePowwowParticipant(powwowParticipant);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		long powwowParticipantId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.updatePowwowParticipant(powwowParticipantId,
			powwowMeetingId, name, participantUserId, emailAddress, type,
			status, serviceContext);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant updateStatus(
		long powwowParticipantId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantLocalService.updateStatus(powwowParticipantId,
			status);
	}

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	*/
	@Override
	public int getPowwowParticipantsCount() {
		return _powwowParticipantLocalService.getPowwowParticipantsCount();
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId) {
		return _powwowParticipantLocalService.getPowwowParticipantsCount(powwowMeetingId);
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId, int type) {
		return _powwowParticipantLocalService.getPowwowParticipantsCount(powwowMeetingId,
			type);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowParticipantLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _powwowParticipantLocalService.getOSGiServiceIdentifier();
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
		return _powwowParticipantLocalService.dynamicQuery(dynamicQuery);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _powwowParticipantLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _powwowParticipantLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
	*/
	@Override
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		int start, int end) {
		return _powwowParticipantLocalService.getPowwowParticipants(start, end);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId) {
		return _powwowParticipantLocalService.getPowwowParticipants(powwowMeetingId);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId, int type) {
		return _powwowParticipantLocalService.getPowwowParticipants(powwowMeetingId,
			type);
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
		return _powwowParticipantLocalService.dynamicQueryCount(dynamicQuery);
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
		return _powwowParticipantLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public PowwowParticipantLocalService getWrappedService() {
		return _powwowParticipantLocalService;
	}

	@Override
	public void setWrappedService(
		PowwowParticipantLocalService powwowParticipantLocalService) {
		_powwowParticipantLocalService = powwowParticipantLocalService;
	}

	private PowwowParticipantLocalService _powwowParticipantLocalService;
}