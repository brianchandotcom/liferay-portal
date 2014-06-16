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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsEntryConstants;
import com.liferay.portlet.announcements.service.base.AnnouncementsDeliveryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDeliveryLocalServiceImpl
	extends AnnouncementsDeliveryLocalServiceBaseImpl {

	@Override
	public AnnouncementsDelivery addUserDelivery(long userId, String type)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long deliveryId = counterLocalService.increment();

		AnnouncementsDelivery delivery =
			announcementsDeliveryPersistence.create(deliveryId);

		delivery.setCompanyId(user.getCompanyId());
		delivery.setUserId(user.getUserId());
		delivery.setType(type);
		delivery.setEmail(false);
		delivery.setSms(false);
		delivery.setWebsite(true);

		try {
			announcementsDeliveryPersistence.update(delivery);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {userId=" + userId + ", type=" +
						type + "}");
			}

			delivery = announcementsDeliveryPersistence.fetchByU_T(
				userId, type, false);

			if (delivery == null) {
				throw se;
			}
		}

		return delivery;
	}

	@Override
	public void deleteDeliveries(long userId) {
		List<AnnouncementsDelivery> deliveries =
			announcementsDeliveryPersistence.findByUserId(userId);

		for (AnnouncementsDelivery delivery : deliveries) {
			deleteDelivery(delivery);
		}
	}

	@Override
	public void deleteDelivery(AnnouncementsDelivery delivery) {
		announcementsDeliveryPersistence.remove(delivery);
	}

	@Override
	public void deleteDelivery(long deliveryId) throws PortalException {
		AnnouncementsDelivery delivery =
			announcementsDeliveryPersistence.findByPrimaryKey(deliveryId);

		deleteDelivery(delivery);
	}

	@Override
	public void deleteDelivery(long userId, String type) {
		AnnouncementsDelivery delivery =
			announcementsDeliveryPersistence.fetchByU_T(userId, type);

		if (delivery != null) {
			deleteDelivery(delivery);
		}
	}

	@Override
	public AnnouncementsDelivery getDelivery(long deliveryId)
		throws PortalException {

		return announcementsDeliveryPersistence.findByPrimaryKey(deliveryId);
	}

	@Override
	public List<AnnouncementsDelivery> getUserDeliveries(long userId)
		throws PortalException {

		List<AnnouncementsDelivery> deliveries =
			new ArrayList<AnnouncementsDelivery>(
				AnnouncementsEntryConstants.TYPES.length);

		for (String type : AnnouncementsEntryConstants.TYPES) {
			deliveries.add(getUserDelivery(userId, type));
		}

		return deliveries;
	}

	@Override
	public AnnouncementsDelivery getUserDelivery(long userId, String type)
		throws PortalException {

		AnnouncementsDelivery delivery =
			announcementsDeliveryPersistence.fetchByU_T(userId, type);

		if (delivery == null) {
			delivery = announcementsDeliveryLocalService.addUserDelivery(
				userId, type);
		}

		return delivery;
	}

	@Override
	public AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms,
			boolean website)
		throws PortalException {

		AnnouncementsDelivery delivery = getUserDelivery(userId, type);

		delivery.setEmail(email);
		delivery.setSms(sms);
		delivery.setWebsite(website);

		announcementsDeliveryPersistence.update(delivery);

		return delivery;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AnnouncementsDeliveryLocalServiceImpl.class);

}