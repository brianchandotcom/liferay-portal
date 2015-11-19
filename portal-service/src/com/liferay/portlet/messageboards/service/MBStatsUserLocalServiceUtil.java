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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for MBStatsUser. This utility wraps
 * {@link com.liferay.portlet.messageboards.service.impl.MBStatsUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBStatsUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class MBStatsUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBStatsUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the message boards stats user to the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was added
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser addMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return getService().addMBStatsUser(mbStatsUser);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser addStatsUser(
		long groupId, long userId) {
		return getService().addStatsUser(groupId, userId);
	}

	/**
	* Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	*
	* @param statsUserId the primary key for the new message boards stats user
	* @return the new message boards stats user
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser createMBStatsUser(
		long statsUserId) {
		return getService().createMBStatsUser(statsUserId);
	}

	/**
	* Deletes the message boards stats user from the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was removed
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return getService().deleteMBStatsUser(mbStatsUser);
	}

	/**
	* Deletes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user that was removed
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMBStatsUser(statsUserId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser statsUser) {
		getService().deleteStatsUser(statsUser);
	}

	public static void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStatsUser(statsUserId);
	}

	public static void deleteStatsUsersByGroupId(long groupId) {
		getService().deleteStatsUsersByGroupId(groupId);
	}

	public static void deleteStatsUsersByUserId(long userId) {
		getService().deleteStatsUsersByUserId(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser fetchMBStatsUser(
		long statsUserId) {
		return getService().fetchMBStatsUser(statsUserId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.Date getLastPostDateByUserId(long groupId,
		long userId) {
		return getService().getLastPostDateByUserId(groupId, userId);
	}

	/**
	* Returns the message boards stats user with the primary key.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser getMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMBStatsUser(statsUserId);
	}

	/**
	* Returns a range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of message boards stats users
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getMBStatsUsers(
		int start, int end) {
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
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, long userId) {
		return getService().getStatsUser(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStatsUsersByGroupId(groupId, start, end);
	}

	public static int getStatsUsersByGroupIdCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStatsUsersByGroupIdCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByUserId(
		long userId) {
		return getService().getStatsUsersByUserId(userId);
	}

	/**
	* Updates the message boards stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was updated
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return getService().updateMBStatsUser(mbStatsUser);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId) {
		return getService().updateStatsUser(groupId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate) {
		return getService().updateStatsUser(groupId, userId, lastPostDate);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, java.util.Date lastPostDate) {
		return getService()
				   .updateStatsUser(groupId, userId, messageCount, lastPostDate);
	}

	public static MBStatsUserLocalService getService() {
		if (_service == null) {
			_service = (MBStatsUserLocalService)PortalBeanLocatorUtil.locate(MBStatsUserLocalService.class.getName());

			ReferenceRegistry.registerReference(MBStatsUserLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(MBStatsUserLocalService service) {
	}

	private static MBStatsUserLocalService _service;
}