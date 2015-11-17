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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBStatsUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalService
 * @generated
 */
@ProviderType
public class MBStatsUserLocalServiceWrapper implements MBStatsUserLocalService,
	ServiceWrapper<MBStatsUserLocalService> {
	public MBStatsUserLocalServiceWrapper(
		MBStatsUserLocalService mbStatsUserLocalService) {
		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	/**
	* Adds the message boards stats user to the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was added
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser addMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUserLocalService.addMBStatsUser(mbStatsUser);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser addStatsUser(
		long groupId, long userId) {
		return _mbStatsUserLocalService.addStatsUser(groupId, userId);
	}

	/**
	* Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	*
	* @param statsUserId the primary key for the new message boards stats user
	* @return the new message boards stats user
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser createMBStatsUser(
		long statsUserId) {
		return _mbStatsUserLocalService.createMBStatsUser(statsUserId);
	}

	/**
	* Deletes the message boards stats user from the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was removed
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUserLocalService.deleteMBStatsUser(mbStatsUser);
	}

	/**
	* Deletes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user that was removed
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.deleteMBStatsUser(statsUserId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser statsUser) {
		_mbStatsUserLocalService.deleteStatsUser(statsUser);
	}

	@Override
	public void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	@Override
	public void deleteStatsUsersByGroupId(long groupId) {
		_mbStatsUserLocalService.deleteStatsUsersByGroupId(groupId);
	}

	@Override
	public void deleteStatsUsersByUserId(long userId) {
		_mbStatsUserLocalService.deleteStatsUsersByUserId(userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbStatsUserLocalService.dynamicQuery();
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
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _mbStatsUserLocalService.dynamicQueryCount(dynamicQuery);
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
		return _mbStatsUserLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser fetchMBStatsUser(
		long statsUserId) {
		return _mbStatsUserLocalService.fetchMBStatsUser(statsUserId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbStatsUserLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mbStatsUserLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.Date getLastPostDateByUserId(long groupId, long userId) {
		return _mbStatsUserLocalService.getLastPostDateByUserId(groupId, userId);
	}

	/**
	* Returns the message boards stats user with the primary key.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser getMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.getMBStatsUser(statsUserId);
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
	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getMBStatsUsers(
		int start, int end) {
		return _mbStatsUserLocalService.getMBStatsUsers(start, end);
	}

	/**
	* Returns the number of message boards stats users.
	*
	* @return the number of message boards stats users
	*/
	@Override
	public int getMBStatsUsersCount() {
		return _mbStatsUserLocalService.getMBStatsUsersCount();
	}

	@Override
	public long getMessageCountByGroupId(long groupId) {
		return _mbStatsUserLocalService.getMessageCountByGroupId(groupId);
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		return _mbStatsUserLocalService.getMessageCountByUserId(userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mbStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, long userId) {
		return _mbStatsUserLocalService.getStatsUser(groupId, userId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.getStatsUsersByGroupId(groupId, start,
			end);
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbStatsUserLocalService.getStatsUsersByGroupIdCount(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByUserId(
		long userId) {
		return _mbStatsUserLocalService.getStatsUsersByUserId(userId);
	}

	/**
	* Updates the message boards stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was updated
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUserLocalService.updateMBStatsUser(mbStatsUser);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId) {
		return _mbStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate) {
		return _mbStatsUserLocalService.updateStatsUser(groupId, userId,
			lastPostDate);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, java.util.Date lastPostDate) {
		return _mbStatsUserLocalService.updateStatsUser(groupId, userId,
			messageCount, lastPostDate);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public MBStatsUserLocalService getWrappedMBStatsUserLocalService() {
		return _mbStatsUserLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedMBStatsUserLocalService(
		MBStatsUserLocalService mbStatsUserLocalService) {
		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	@Override
	public MBStatsUserLocalService getWrappedService() {
		return _mbStatsUserLocalService;
	}

	@Override
	public void setWrappedService(
		MBStatsUserLocalService mbStatsUserLocalService) {
		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	private MBStatsUserLocalService _mbStatsUserLocalService;
}