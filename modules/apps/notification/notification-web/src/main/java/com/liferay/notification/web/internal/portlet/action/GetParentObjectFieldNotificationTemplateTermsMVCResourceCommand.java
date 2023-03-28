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

package com.liferay.notification.web.internal.portlet.action;

import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + NotificationPortletKeys.NOTIFICATION_TEMPLATES,
		"mvc.command.name=/notification_templates/get_parent_object_field_notification_template_terms"
	},
	service = MVCResourceCommand.class
)
public class GetParentObjectFieldNotificationTemplateTermsMVCResourceCommand
	extends BaseNotificationTemplateTermsMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_objectRelationship =
			_objectRelationshipLocalService.fetchObjectRelationship(
				ParamUtil.getLong(resourceRequest, "objectRelationshipId"));

		if (_objectRelationship == null) {
			return;
		}

		super.doServeResource(resourceRequest, resourceResponse);
	}

	@Override
	protected Set<Map.Entry<String, String>> getTermNamesEntries() {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectRelationship.getObjectDefinitionId1());

		if (objectDefinition == null) {
			return Collections.emptySet();
		}

		Map<String, String> termNames = new LinkedHashMap<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			if (StringUtil.equals(objectField.getName(), "creator") &&
				FeatureFlagManagerUtil.isEnabled("LPS-171625")) {

				authorObjectFieldNames.forEach(
					(termLabel, objectFieldName) -> termNames.put(
						termLabel, _getTermName(objectFieldName)));
			}
			else {
				termNames.put(
					objectField.getLabel(user.getLocale()),
					_getTermName(objectField.getName()));
			}
		}

		return termNames.entrySet();
	}

	private String _getTermName(String objectFieldName) {
		return ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
			_objectRelationship.getName(), objectFieldName);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private ObjectRelationship _objectRelationship;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}