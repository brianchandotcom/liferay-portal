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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
		"mvc.command.name=/notification_templates/get_object_field_notification_template_terms"
	},
	service = MVCResourceCommand.class
)
public class GetObjectFieldNotificationTemplateTermsMVCResourceCommand
	extends BaseNotificationTemplateTermsMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(
			ParamUtil.getLong(resourceRequest, "objectDefinitionId"));

		if (_objectDefinition == null) {
			return;
		}

		themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled("LPS-165849")) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				getNotificationTemplateTermsJSONArray());
		}

		JSONArray relationshipSectionsJSONArray = jsonFactory.createJSONArray();

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.
					getObjectRelationshipsByObjectDefinitionId2(
						_objectDefinition.getObjectDefinitionId())) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			relationshipSectionsJSONArray.put(
				JSONUtil.put(
					"relationshipId",
					objectRelationship.getObjectRelationshipId()
				).put(
					"sectionLabel",
					StringBundler.concat(
						objectRelationship.getLabel(themeDisplay.getLocale()),
						StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
						StringUtil.upperCase(
							objectDefinition.getLabel(
								themeDisplay.getLocale())),
						StringPool.CLOSE_PARENTHESIS)
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"relationshipSections", relationshipSectionsJSONArray
			).put(
				"terms", getNotificationTemplateTermsJSONArray()
			));
	}

	@Override
	protected Set<Map.Entry<String, String>> getTermNamesEntries() {
		return getObjectFieldNotificationTermNamesEntries(
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getShortName());
	}

	private ObjectDefinition _objectDefinition;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}