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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.powwow.model.PowwowMeeting;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for PowwowMeeting. This utility wraps
 * <code>com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shinn Lok
 * @see PowwowMeetingLocalService
 * @generated
 */
public class PowwowMeetingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PowwowMeeting addPowwowMeeting(
			long userId, long groupId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addPowwowMeeting(
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
	public static PowwowMeeting addPowwowMeeting(PowwowMeeting powwowMeeting) {
		return getService().addPowwowMeeting(powwowMeeting);
	}

	public static void checkPowwowMeetings() throws PortalException {
		getService().checkPowwowMeetings();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	 *
	 * @param powwowMeetingId the primary key for the new powwow meeting
	 * @return the new powwow meeting
	 */
	public static PowwowMeeting createPowwowMeeting(long powwowMeetingId) {
		return getService().createPowwowMeeting(powwowMeetingId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException {

		return getService().deletePowwowMeeting(powwowMeetingId);
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
	public static PowwowMeeting deletePowwowMeeting(PowwowMeeting powwowMeeting)
		throws PortalException {

		return getService().deletePowwowMeeting(powwowMeeting);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static PowwowMeeting fetchPowwowMeeting(long powwowMeetingId) {
		return getService().fetchPowwowMeeting(powwowMeetingId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<PowwowMeeting> getParticipantPowwowMeetings(
		long userId, int[] statuses, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {

		return getService().getParticipantPowwowMeetings(
			userId, statuses, start, end, orderByComparator);
	}

	public static int getParticipantPowwowMeetingsCount(
		long userId, int[] statuses) {

		return getService().getParticipantPowwowMeetingsCount(userId, statuses);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the powwow meeting with the primary key.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting
	 * @throws PortalException if a powwow meeting with the primary key could not be found
	 */
	public static PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException {

		return getService().getPowwowMeeting(powwowMeetingId);
	}

	public static List<PowwowMeeting> getPowwowMeetings(int status) {
		return getService().getPowwowMeetings(status);
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
	public static List<PowwowMeeting> getPowwowMeetings(int start, int end) {
		return getService().getPowwowMeetings(start, end);
	}

	public static List<PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {

		return getService().getPowwowMeetings(
			groupId, start, end, orderByComparator);
	}

	public static List<PowwowMeeting> getPowwowMeetings(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch, int start, int end, String orderByField,
		String orderByType) {

		return getService().getPowwowMeetings(
			groupId, userId, name, description, status, andSearch, start, end,
			orderByField, orderByType);
	}

	/**
	 * Returns the number of powwow meetings.
	 *
	 * @return the number of powwow meetings
	 */
	public static int getPowwowMeetingsCount() {
		return getService().getPowwowMeetingsCount();
	}

	public static int getPowwowMeetingsCount(long groupId) {
		return getService().getPowwowMeetingsCount(groupId);
	}

	public static int getPowwowMeetingsCount(long powwowServerId, int status) {
		return getService().getPowwowMeetingsCount(powwowServerId, status);
	}

	public static int getPowwowMeetingsCount(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch) {

		return getService().getPowwowMeetingsCount(
			groupId, userId, name, description, status, andSearch);
	}

	public static int getUserPowwowMeetingsCount(long userId, int status) {
		return getService().getUserPowwowMeetingsCount(userId, status);
	}

	public static PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updatePowwowMeeting(
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
	public static PowwowMeeting updatePowwowMeeting(
		PowwowMeeting powwowMeeting) {

		return getService().updatePowwowMeeting(powwowMeeting);
	}

	public static PowwowMeeting updateStatus(long powwowMeetingId, int status)
		throws PortalException {

		return getService().updateStatus(powwowMeetingId, status);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowMeetingLocalService getService() {
		return _service;
	}

	private static volatile PowwowMeetingLocalService _service;

}