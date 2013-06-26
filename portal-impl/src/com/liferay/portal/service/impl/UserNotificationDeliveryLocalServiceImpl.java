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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.service.base.UserNotificationDeliveryLocalServiceBaseImpl;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDeliveryLocalServiceImpl
	extends UserNotificationDeliveryLocalServiceBaseImpl {

	@Override
	public UserNotificationDelivery addUserNotificationDelivery(
			long userId, long classNameId, int type, boolean email, boolean sms,
			boolean website)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long userNotificationDeliveryId = counterLocalService.increment();

		UserNotificationDelivery userNotificationDelivery =
			userNotificationDeliveryPersistence.create(
				userNotificationDeliveryId);

		userNotificationDelivery.setCompanyId(user.getCompanyId());
		userNotificationDelivery.setUserId(user.getUserId());
		userNotificationDelivery.setClassNameId(classNameId);
		userNotificationDelivery.setType(type);
		userNotificationDelivery.setEmail(email);
		userNotificationDelivery.setSms(sms);
		userNotificationDelivery.setWebsite(website);

		return userNotificationDeliveryPersistence.update(
			userNotificationDelivery);
	}

	@Override
	public void deleteUserNotificationDeliveries(long userId)
		throws SystemException {

		userNotificationDeliveryPersistence.removeByUserId(userId);
	}

	@Override
	public UserNotificationDelivery fetchUserNotificationDelivery(
			long userId, long classNameId, int type)
		throws SystemException {

		return userNotificationDeliveryPersistence.fetchByU_C_T(
			userId, classNameId, type);
	}

	@Override
	public UserNotificationDelivery getUserNotificationDelivery(
			long userId, long classNameId, int type, boolean email, boolean sms,
			boolean website)
		throws PortalException, SystemException {

		UserNotificationDelivery userNotificationDelivery =
			fetchUserNotificationDelivery(userId, classNameId, type);

		if (userNotificationDelivery != null) {
			return userNotificationDelivery;
		}

		return userNotificationDeliveryLocalService.addUserNotificationDelivery(
			userId, classNameId, type, email, sms, website);
	}

	@Override
	public UserNotificationDelivery updateUserNotificationDelivery(
			long userId, long classNameId, int type, boolean email, boolean sms,
			boolean website)
		throws PortalException, SystemException {

		UserNotificationDelivery userNotificationDelivery =
			userNotificationDeliveryPersistence.fetchByU_C_T(
				userId, classNameId, type);

		if (userNotificationDelivery == null) {
			return addUserNotificationDelivery(
				userId, classNameId, type, email, sms, website);
		}

		userNotificationDelivery.setEmail(email);
		userNotificationDelivery.setSms(sms);
		userNotificationDelivery.setWebsite(website);

		return userNotificationDeliveryPersistence.update(
			userNotificationDelivery);
	}

}