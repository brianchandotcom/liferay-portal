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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationDeliveryLocalServiceUtil;

/**
 * @author Jonathan Lee
 */
public abstract class BaseUserNotificationHandler
	implements UserNotificationHandler {

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getSelector() {
		return _selector;
	}

	@Override
	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			UserNotificationFeedEntry userNotificationFeedEntry = doInterpret(
				userNotificationEvent, serviceContext);

			userNotificationFeedEntry.setPortletId(getPortletId());

			return userNotificationFeedEntry;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Override
	public boolean deliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return doDeliver(
			userId, classNameId, notificationType, deliveryType,
			serviceContext);
	}

	protected UserNotificationFeedEntry doInterpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String body = getBody(userNotificationEvent, serviceContext);

		if (Validator.isNull(body)) {
			return null;
		}

		String link = getLink(userNotificationEvent, serviceContext);

		return new UserNotificationFeedEntry(body, link);
	}

	protected boolean doDeliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		UserNotificationDefinition userNotificationDefinition =
			UserNotificationManagerUtil.fetchUserNotificationDefinition(
				_portletId, classNameId, notificationType);

		UserNotificationDeliveryType userNotificationDeliveryType =
			userNotificationDefinition.getDeliveryType(deliveryType);

		UserNotificationDelivery userNotificationDelivery =
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, _portletId, classNameId, notificationType,
					deliveryType, userNotificationDeliveryType.isDefault()
				);

		return userNotificationDelivery.isDeliver();
	}

	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected void setSelector(String selector) {
		_selector = selector;
	}

	private String _portletId;
	private String _selector = StringPool.BLANK;

}