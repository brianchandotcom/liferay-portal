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

package com.liferay.notification.web.internal.portlet;

import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
@Component(
	property = {
		"javax.portlet.name=" + NotificationPortletKeys.NOTIFICATION_TEMPLATES,
		"mvc.command.name=/notification_templates/notification_template_terms"
	},
	service = MVCResourceCommand.class
)
public class NotificationTemplateTermsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				ParamUtil.getLong(resourceRequest, "objectDefinitionId"));

		if (objectDefinition == null) {
			return;
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			TransformUtil.transform(
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId()),
				objectField -> JSONUtil.put(
					"name", objectField.getLabel(LocaleUtil.getDefault())
				).put(
					"term",
					ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
						objectDefinition.getShortName(), objectField.getName())
				)));
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}