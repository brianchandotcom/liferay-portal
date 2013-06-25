/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class UserNotificationDeliveryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserNotificationDeliveryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the user notification delivery to the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* Deletes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery that was removed
	* @throws PortalException if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Deletes the user notification delivery from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery deleteUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteUserNotificationDelivery(userNotificationDelivery);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchUserNotificationDelivery(userNotificationDeliveryId);
	}

	/**
	* Returns the user notification delivery with the primary key.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery
	* @throws PortalException if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserNotificationDelivery(userNotificationDeliveryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> getUserNotificationDeliveries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserNotificationDeliveries(start, end);
	}

	/**
	* Returns the number of user notification deliveries.
	*
	* @return the number of user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserNotificationDeliveriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserNotificationDeliveriesCount();
	}

	/**
	* Updates the user notification delivery in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDelivery the user notification delivery
	* @return the user notification delivery that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUserNotificationDelivery(userNotificationDelivery);
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

	public static com.liferay.portal.model.UserNotificationDelivery addUserNotificationDelivery(
		long userId, long classNameId, int type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUserNotificationDelivery(userId, classNameId, type,
			email, sms, website);
	}

	public static void deleteUserNotificationDeliveries(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserNotificationDeliveries(userId);
	}

	public static com.liferay.portal.model.UserNotificationDelivery fetchUserNotificationDelivery(
		long userId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchUserNotificationDelivery(userId, classNameId, type);
	}

	public static com.liferay.portal.model.UserNotificationDelivery getUserNotificationDelivery(
		long userId, long classNameId, int type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserNotificationDelivery(userId, classNameId, type,
			email, sms, website);
	}

	public static com.liferay.portal.model.UserNotificationDelivery updateUserNotificationDelivery(
		long userId, long classNameId, int type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUserNotificationDelivery(userId, classNameId, type,
			email, sms, website);
	}

	public static UserNotificationDeliveryLocalService getService() {
		if (_service == null) {
			_service = (UserNotificationDeliveryLocalService)PortalBeanLocatorUtil.locate(UserNotificationDeliveryLocalService.class.getName());

			ReferenceRegistry.registerReference(UserNotificationDeliveryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(UserNotificationDeliveryLocalService service) {
	}

	private static UserNotificationDeliveryLocalService _service;
}