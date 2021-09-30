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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchUserNotificationDeliveryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.service.base.UserNotificationDeliveryLocalServiceBaseImpl;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDeliveryLocalServiceImpl
	extends UserNotificationDeliveryLocalServiceBaseImpl {

	@Override
	public UserNotificationDelivery addUserNotificationDelivery(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType, boolean deliver)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		long userNotificationDeliveryId = counterLocalService.increment();

		UserNotificationDelivery userNotificationDelivery =
			userNotificationDeliveryPersistence.create(
				userNotificationDeliveryId);

		userNotificationDelivery.setCompanyId(user.getCompanyId());
		userNotificationDelivery.setUserId(user.getUserId());
		userNotificationDelivery.setPortletId(portletId);
		userNotificationDelivery.setClassNameId(classNameId);
		userNotificationDelivery.setNotificationType(notificationType);
		userNotificationDelivery.setDeliveryType(deliveryType);
		userNotificationDelivery.setDeliver(deliver);

		return userNotificationDeliveryPersistence.update(
			userNotificationDelivery);
	}

	@Override
	public void deleteUserNotificationDeliveries(long userId) {
		userNotificationDeliveryPersistence.removeByUserId(userId);
	}

	@Override
	public void deleteUserNotificationDelivery(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		try {
			userNotificationDeliveryPersistence.removeByU_P_C_N_D(
				userId, portletId, classNameId, notificationType, deliveryType);
		}
		catch (NoSuchUserNotificationDeliveryException
					noSuchUserNotificationDeliveryException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchUserNotificationDeliveryException,
					noSuchUserNotificationDeliveryException);
			}
		}
	}

	@Override
	public UserNotificationDelivery fetchUserNotificationDelivery(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		return userNotificationDeliveryPersistence.fetchByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);
	}

	@Override
	public UserNotificationDelivery getUserNotificationDelivery(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType, boolean deliver)
		throws PortalException {

		UserNotificationDelivery userNotificationDelivery =
			userNotificationDeliveryPersistence.fetchByU_P_C_N_D(
				userId, portletId, classNameId, notificationType, deliveryType);

		if (userNotificationDelivery != null) {
			return userNotificationDelivery;
		}

		return userNotificationDeliveryLocalService.addUserNotificationDelivery(
			userId, portletId, classNameId, notificationType, deliveryType,
			deliver);
	}

	@Override
	public UserNotificationDelivery updateUserNotificationDelivery(
		long userNotificationDeliveryId, boolean deliver) {

		UserNotificationDelivery userNotificationDelivery =
			fetchUserNotificationDelivery(userNotificationDeliveryId);

		userNotificationDelivery.setDeliver(deliver);

		return userNotificationDeliveryPersistence.update(
			userNotificationDelivery);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationDeliveryLocalServiceImpl.class);

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}