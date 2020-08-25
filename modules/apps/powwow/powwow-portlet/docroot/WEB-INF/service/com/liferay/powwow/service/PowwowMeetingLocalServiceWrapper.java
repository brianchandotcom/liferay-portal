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

import com.liferay.portal.kernel.service.ServiceWrapper;

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

	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
			long userId, long groupId, long powwowServerId, String name,
			String description, String providerType,
			java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			java.util.List<com.liferay.powwow.model.PowwowParticipant>
				powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.addPowwowMeeting(
			userId, groupId, powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	/**
	 * Adds the powwow meeting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was added
	 */
	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting) {

		return _powwowMeetingLocalService.addPowwowMeeting(powwowMeeting);
	}

	@Override
	public void checkPowwowMeetings()
		throws com.liferay.portal.kernel.exception.PortalException {

		_powwowMeetingLocalService.checkPowwowMeetings();
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
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws PortalException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
			long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.deletePowwowMeeting(powwowMeetingId);
	}

	/**
	 * Deletes the powwow meeting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
			com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.PortalException {

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
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _powwowMeetingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
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

		return _powwowMeetingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
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

		return _powwowMeetingLocalService.dynamicQuery(
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

		return _powwowMeetingLocalService.dynamicQueryCount(dynamicQuery);
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

		return _powwowMeetingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting fetchPowwowMeeting(
		long powwowMeetingId) {

		return _powwowMeetingLocalService.fetchPowwowMeeting(powwowMeetingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _powwowMeetingLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _powwowMeetingLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _powwowMeetingLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting>
		getParticipantPowwowMeetings(
			long userId, int[] statuses, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				orderByComparator) {

		return _powwowMeetingLocalService.getParticipantPowwowMeetings(
			userId, statuses, start, end, orderByComparator);
	}

	@Override
	public int getParticipantPowwowMeetingsCount(long userId, int[] statuses) {
		return _powwowMeetingLocalService.getParticipantPowwowMeetingsCount(
			userId, statuses);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the powwow meeting with the primary key.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting
	 * @throws PortalException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
			long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.getPowwowMeeting(powwowMeetingId);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting>
		getPowwowMeetings(int status) {

		return _powwowMeetingLocalService.getPowwowMeetings(status);
	}

	/**
	 * Returns a range of all the powwow meetings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of powwow meetings
	 */
	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting>
		getPowwowMeetings(int start, int end) {

		return _powwowMeetingLocalService.getPowwowMeetings(start, end);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting>
		getPowwowMeetings(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator obc) {

		return _powwowMeetingLocalService.getPowwowMeetings(
			groupId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting>
		getPowwowMeetings(
			long groupId, long userId, String name, String description,
			int status, boolean andSearch, int start, int end,
			String orderByField, String orderByType) {

		return _powwowMeetingLocalService.getPowwowMeetings(
			groupId, userId, name, description, status, andSearch, start, end,
			orderByField, orderByType);
	}

	/**
	 * Returns the number of powwow meetings.
	 *
	 * @return the number of powwow meetings
	 */
	@Override
	public int getPowwowMeetingsCount() {
		return _powwowMeetingLocalService.getPowwowMeetingsCount();
	}

	@Override
	public int getPowwowMeetingsCount(long groupId) {
		return _powwowMeetingLocalService.getPowwowMeetingsCount(groupId);
	}

	@Override
	public int getPowwowMeetingsCount(long powwowServerId, int status) {
		return _powwowMeetingLocalService.getPowwowMeetingsCount(
			powwowServerId, status);
	}

	@Override
	public int getPowwowMeetingsCount(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch) {

		return _powwowMeetingLocalService.getPowwowMeetingsCount(
			groupId, userId, name, description, status, andSearch);
	}

	@Override
	public int getUserPowwowMeetingsCount(long userId, int status) {
		return _powwowMeetingLocalService.getUserPowwowMeetingsCount(
			userId, status);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			java.util.List<com.liferay.powwow.model.PowwowParticipant>
				powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.updatePowwowMeeting(
			powwowMeetingId, powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	/**
	 * Updates the powwow meeting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was updated
	 */
	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting) {

		return _powwowMeetingLocalService.updatePowwowMeeting(powwowMeeting);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updateStatus(
			long powwowMeetingId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _powwowMeetingLocalService.updateStatus(powwowMeetingId, status);
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