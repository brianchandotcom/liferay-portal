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

package com.liferay.headless.user.notification.internal.dto.v1_0;

import com.liferay.headless.user.notification.dto.v1_0.UserNotification;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.UserNotificationEvent",
	service = {DTOConverter.class, UserNotificationDTOConverter.class}
)
public class UserNotificationDTOConverter
	implements DTOConverter<UserNotificationEvent, UserNotification> {

	@Override
	public String getContentType() {
		return UserNotification.class.getSimpleName();
	}

	@Override
	public UserNotificationEvent getObject(String externalReferenceCode)
		throws Exception {

		return _userNotificationEventLocalService.getUserNotificationEvent(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public UserNotification toDTO(
			DTOConverterContext dtoConverterContext,
			UserNotificationEvent userNotificationEvent)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		return new UserNotification() {
			{
				if (dtoConverterContext != null) {
					actions = dtoConverterContext.getActions();
				}

				dateCreated = new Date(userNotificationEvent.getTimestamp());
				id = userNotificationEvent.getUserNotificationEventId();
				message = userNotificationEvent.getPayload();
				read = userNotificationEvent.isArchived();

				if (jsonObject.has("notificationType")) {
					type = jsonObject.getInt("notificationType");
				}
			}
		};
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}