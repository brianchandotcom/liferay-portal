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

package com.liferay.object.internal.action.executor;

import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.action.util.ObjectEntryVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = ObjectActionExecutor.class)
public class NotificationTemplateObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.getNotificationTemplate(
				GetterUtil.getLong(
					parametersUnicodeProperties.get("notificationTemplateId")));

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				notificationTemplate.getType());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		Map<String, Object> termValues =
			ObjectEntryVariablesUtil.getActionVariables(
				_dtoConverterRegistry, objectDefinition, payloadJSONObject,
				_systemObjectDefinitionMetadataRegistry);

		termValues = (Map<String, Object>)termValues.get("objectEntry");

		notificationType.sendNotification(
			new NotificationContextBuilder(
			).className(
				objectDefinition.getClassName()
			).classPK(
				GetterUtil.getLong(termValues.get("id"))
			).externalReferenceCode(
				GetterUtil.getString(termValues.get("externalReferenceCode"))
			).notificationTemplate(
				notificationTemplate
			).termValues(
				termValues
			).userId(
				userId
			).portletId(
				objectDefinition.isSystem() ? StringPool.BLANK :
					objectDefinition.getPortletId()
			).build());
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_NOTIFICATION;
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

}