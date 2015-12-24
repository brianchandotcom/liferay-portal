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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for UserNotificationDelivery. This utility wraps
 * {@link com.liferay.portal.service.impl.UserNotificationDeliveryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryLocalService
 * @see com.liferay.portal.service.base.UserNotificationDeliveryLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserNotificationDeliveryLocalServiceImpl
 * @generated
 */
@ProviderType
public class UserNotificationDeliveryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserNotificationDeliveryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType, boolean deliver)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addUserNotificationDelivery(userId, portletId, classNameId,
			notificationType, deliveryType, deliver);
	}

	/**
	* Adds the user notification delivery to the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was added
	*/
	public static com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return getService().addUserNotificationDelivery(userNotificationDelivery);
	}

	/**
	* Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	*
	* @param userNotificationDeliveryId the primary key for the new user notification delivery
	* @return the new user notification delivery
	*/
	public static com.liferay.portal.model.UserNotificationDelivery createUserNotificationDelivery(
		long userNotificationDeliveryId) {
		return getService()
				   .createUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteUserNotificationDeliveries(long userId) {
		getService().deleteUserNotificationDeliveries(userId);
	}

	public static void deleteUserNotificationDelivery(long userId,
		java.lang.String portletId, long classNameId, int notificationType,
		int deliveryType) {
		getService()
			.deleteUserNotificationDelivery(userId, portletId, classNameId,
			notificationType, deliveryType);
	}

	/**
	* Deletes the user notification delivery from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was removed
	*/
	public static com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return getService()
				   .deleteUserNotificationDelivery(userNotificationDelivery);
	}

	/**
	* Deletes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery that was removed
	* @throws PortalException if a user notification delivery with the primary key could not be found
	*/
	public static com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteUserNotificationDelivery(userNotificationDeliveryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType) {
		return getService()
				   .fetchUserNotificationDelivery(userId, portletId,
			classNameId, notificationType, deliveryType);
	}

	public static com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userNotificationDeliveryId) {
		return getService()
				   .fetchUserNotificationDelivery(userNotificationDeliveryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
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
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> getUserNotificationDeliveries(
		int start, int end) {
		return getService().getUserNotificationDeliveries(start, end);
	}

	/**
	* Returns the number of user notification deliveries.
	*
	* @return the number of user notification deliveries
	*/
	public static int getUserNotificationDeliveriesCount() {
		return getService().getUserNotificationDeliveriesCount();
	}

	public static com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType, boolean deliver)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserNotificationDelivery(userId, portletId, classNameId,
			notificationType, deliveryType, deliver);
	}

	/**
	* Returns the user notification delivery with the primary key.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery
	* @throws PortalException if a user notification delivery with the primary key could not be found
	*/
	public static com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Updates the user notification delivery in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was updated
	*/
	public static com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return getService()
				   .updateUserNotificationDelivery(userNotificationDelivery);
	}

	public static com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		long userNotificationDeliveryId, boolean deliver) {
		return getService()
				   .updateUserNotificationDelivery(userNotificationDeliveryId,
			deliver);
	}

	public static UserNotificationDeliveryLocalService getService() {
		if (_service == null) {
			_service = (UserNotificationDeliveryLocalService)PortalBeanLocatorUtil.locate(UserNotificationDeliveryLocalService.class.getName());

			ReferenceRegistry.registerReference(UserNotificationDeliveryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static UserNotificationDeliveryLocalService _service;
}