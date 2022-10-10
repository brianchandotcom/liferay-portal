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

package com.liferay.notification.type;

import com.liferay.notification.exception.NotificationTemplateAttachmentObjectFieldIdException;
import com.liferay.notification.exception.NotificationTemplateNameException;
import com.liferay.notification.exception.NotificationTemplateObjectDefinitionIdException;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseNotificationType implements NotificationType {

	@Override
	public String getType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void validateNotificationTemplate(
			NotificationContext notificationContext)
		throws PortalException {

		if (Validator.isNull(
				notificationContext.getNotificationTemplateName())) {

			throw new NotificationTemplateNameException("Name is null");
		}

		if (notificationContext.getObjectDefinitionId() > 0) {
			ObjectDefinition objectDefinition =
				ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
					notificationContext.getObjectDefinitionId());

			if (objectDefinition == null) {
				throw new NotificationTemplateObjectDefinitionIdException();
			}
		}

		for (long attachmentObjectFieldId :
				notificationContext.getAttachmentObjectFieldIds()) {

			ObjectField objectField =
				ObjectFieldLocalServiceUtil.fetchObjectField(
					attachmentObjectFieldId);

			if ((objectField == null) ||
				!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				!Objects.equals(
					objectField.getObjectDefinitionId(),
					notificationContext.getObjectDefinitionId())) {

				throw new NotificationTemplateAttachmentObjectFieldIdException();
			}
		}
	}

}