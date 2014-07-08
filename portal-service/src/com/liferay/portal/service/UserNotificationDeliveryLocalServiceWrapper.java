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

/**
 * Provides a wrapper for {@link UserNotificationDeliveryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryLocalService
 * @generated
 */
@ProviderType
public class UserNotificationDeliveryLocalServiceWrapper
	implements UserNotificationDeliveryLocalService,
		ServiceWrapper<UserNotificationDeliveryLocalService> {
	public UserNotificationDeliveryLocalServiceWrapper(
		UserNotificationDeliveryLocalService userNotificationDeliveryLocalService) {
		_userNotificationDeliveryLocalService = userNotificationDeliveryLocalService;
	}

	/**
	* Adds the user notification delivery to the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was added
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return _userNotificationDeliveryLocalService.addUserNotificationDelivery(userNotificationDelivery);
	}

	/**
	* Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	*
	* @param userNotificationDeliveryId the primary key for the new user notification delivery
	* @return the new user notification delivery
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery createUserNotificationDelivery(
		long userNotificationDeliveryId) {
		return _userNotificationDeliveryLocalService.createUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Deletes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery that was removed
	* @throws PortalException if a user notification delivery with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.deleteUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Deletes the user notification delivery from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was removed
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return _userNotificationDeliveryLocalService.deleteUserNotificationDelivery(userNotificationDelivery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userNotificationDeliveryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _userNotificationDeliveryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _userNotificationDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _userNotificationDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _userNotificationDeliveryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _userNotificationDeliveryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userNotificationDeliveryId) {
		return _userNotificationDeliveryLocalService.fetchUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Returns the user notification delivery with the primary key.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery
	* @throws PortalException if a user notification delivery with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.getUserNotificationDelivery(userNotificationDeliveryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _userNotificationDeliveryLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the user notification deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification deliveries
	* @param end the upper bound of the range of user notification deliveries (not inclusive)
	* @return the range of user notification deliveries
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> getUserNotificationDeliveries(
		int start, int end) {
		return _userNotificationDeliveryLocalService.getUserNotificationDeliveries(start,
			end);
	}

	/**
	* Returns the number of user notification deliveries.
	*
	* @return the number of user notification deliveries
	*/
	@Override
	public int getUserNotificationDeliveriesCount() {
		return _userNotificationDeliveryLocalService.getUserNotificationDeliveriesCount();
	}

	/**
	* Updates the user notification delivery in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was updated
	*/
	@Override
	public com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return _userNotificationDeliveryLocalService.updateUserNotificationDelivery(userNotificationDelivery);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userNotificationDeliveryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userNotificationDeliveryLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType, boolean deliver)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.addUserNotificationDelivery(userId,
			portletId, classNameId, notificationType, deliveryType, deliver);
	}

	@Override
	public void deleteUserNotificationDeliveries(long userId) {
		_userNotificationDeliveryLocalService.deleteUserNotificationDeliveries(userId);
	}

	@Override
	public void deleteUserNotificationDelivery(long userId,
		java.lang.String portletId, long classNameId, int notificationType,
		int deliveryType) {
		_userNotificationDeliveryLocalService.deleteUserNotificationDelivery(userId,
			portletId, classNameId, notificationType, deliveryType);
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType) {
		return _userNotificationDeliveryLocalService.fetchUserNotificationDelivery(userId,
			portletId, classNameId, notificationType, deliveryType);
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType, boolean deliver)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationDeliveryLocalService.getUserNotificationDelivery(userId,
			portletId, classNameId, notificationType, deliveryType, deliver);
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		long userNotificationDeliveryId, boolean deliver) {
		return _userNotificationDeliveryLocalService.updateUserNotificationDelivery(userNotificationDeliveryId,
			deliver);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public UserNotificationDeliveryLocalService getWrappedUserNotificationDeliveryLocalService() {
		return _userNotificationDeliveryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedUserNotificationDeliveryLocalService(
		UserNotificationDeliveryLocalService userNotificationDeliveryLocalService) {
		_userNotificationDeliveryLocalService = userNotificationDeliveryLocalService;
	}

	@Override
	public UserNotificationDeliveryLocalService getWrappedService() {
		return _userNotificationDeliveryLocalService;
	}

	@Override
	public void setWrappedService(
		UserNotificationDeliveryLocalService userNotificationDeliveryLocalService) {
		_userNotificationDeliveryLocalService = userNotificationDeliveryLocalService;
	}

	private UserNotificationDeliveryLocalService _userNotificationDeliveryLocalService;
}