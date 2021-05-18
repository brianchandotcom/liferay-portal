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

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for MBStatsUser. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBStatsUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalService
 * @generated
 */
public class MBStatsUserLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBStatsUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the message boards stats user to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MBStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mbStatsUser the message boards stats user
	 * @return the message boards stats user that was added
	 */
	public static MBStatsUser addMBStatsUser(MBStatsUser mbStatsUser) {
		return getService().addMBStatsUser(mbStatsUser);
	}

	public static MBStatsUser addStatsUser(long groupId, long userId) {
		return getService().addStatsUser(groupId, userId);
	}

	/**
	 * Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	 *
	 * @param statsUserId the primary key for the new message boards stats user
	 * @return the new message boards stats user
	 */
	public static MBStatsUser createMBStatsUser(long statsUserId) {
		return getService().createMBStatsUser(statsUserId);
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
	 * Deletes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MBStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws PortalException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser deleteMBStatsUser(long statsUserId)
		throws PortalException {

		return getService().deleteMBStatsUser(statsUserId);
	}

	/**
	 * Deletes the message boards stats user from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MBStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mbStatsUser the message boards stats user
	 * @return the message boards stats user that was removed
	 */
	public static MBStatsUser deleteMBStatsUser(MBStatsUser mbStatsUser) {
		return getService().deleteMBStatsUser(mbStatsUser);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteStatsUser(long statsUserId)
		throws PortalException {

		getService().deleteStatsUser(statsUserId);
	}

	public static void deleteStatsUser(MBStatsUser statsUser) {
		getService().deleteStatsUser(statsUser);
	}

	public static void deleteStatsUsersByGroupId(long groupId) {
		getService().deleteStatsUsersByGroupId(groupId);
	}

	public static void deleteStatsUsersByUserId(long userId) {
		getService().deleteStatsUsersByUserId(userId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBStatsUserModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBStatsUserModelImpl</code>.
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

	public static MBStatsUser fetchMBStatsUser(long statsUserId) {
		return getService().fetchMBStatsUser(statsUserId);
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

	public static java.util.Date getLastPostDateByUserId(
		long groupId, long userId) {

		return getService().getLastPostDateByUserId(groupId, userId);
	}

	/**
	 * Returns the message boards stats user with the primary key.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws PortalException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser getMBStatsUser(long statsUserId)
		throws PortalException {

		return getService().getMBStatsUser(statsUserId);
	}

	/**
	 * Returns a range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of message boards stats users
	 */
	public static List<MBStatsUser> getMBStatsUsers(int start, int end) {
		return getService().getMBStatsUsers(start, end);
	}

	/**
	 * Returns the number of message boards stats users.
	 *
	 * @return the number of message boards stats users
	 */
	public static int getMBStatsUsersCount() {
		return getService().getMBStatsUsersCount();
	}

	public static long getMessageCountByGroupId(long groupId) {
		return getService().getMessageCountByGroupId(groupId);
	}

	public static long getMessageCountByUserId(long userId) {
		return getService().getMessageCountByUserId(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static MBStatsUser getStatsUser(long groupId, long userId) {
		return getService().getStatsUser(groupId, userId);
	}

	public static List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException {

		return getService().getStatsUsersByGroupId(groupId, start, end);
	}

	public static int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		return getService().getStatsUsersByGroupIdCount(groupId);
	}

	public static List<MBStatsUser> getStatsUsersByUserId(long userId) {
		return getService().getStatsUsersByUserId(userId);
	}

	public static String[] getUserRank(
			long groupId, String languageId, long userId)
		throws PortalException {

		return getService().getUserRank(groupId, languageId, userId);
	}

	/**
	 * Updates the message boards stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MBStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mbStatsUser the message boards stats user
	 * @return the message boards stats user that was updated
	 */
	public static MBStatsUser updateMBStatsUser(MBStatsUser mbStatsUser) {
		return getService().updateMBStatsUser(mbStatsUser);
	}

	public static MBStatsUser updateStatsUser(long groupId, long userId) {
		return getService().updateStatsUser(groupId, userId);
	}

	public static MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate) {

		return getService().updateStatsUser(groupId, userId, lastPostDate);
	}

	public static MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount,
		java.util.Date lastPostDate) {

		return getService().updateStatsUser(
			groupId, userId, messageCount, lastPostDate);
	}

	public static MBStatsUserLocalService getService() {
		return _service;
	}

	private static volatile MBStatsUserLocalService _service;

}