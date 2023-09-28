/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/get_object_field_delete_info"
	},
	service = MVCResourceCommand.class
)
public class GetObjectFieldDeleteInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			ParamUtil.getLong(resourceRequest, "objectFieldId"));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectField.getObjectDefinitionId());

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"deleteLastPublishedObjectDefinitionObjectField",
				() -> _shouldDeleteLastPublishedObjectDefinitionObjectField(
					objectDefinition, objectField)
			).put(
				"deleteObjectFieldObjectValidationRuleSetting",
				() -> _shouldDeleteObjectFieldObjectValidationRuleSetting(
					objectField)
			).put(
				"showDeletionModal",
				() -> {
					if (objectDefinition.isApproved() &&
						_shouldDeleteLastPublishedObjectDefinitionObjectField(
							objectDefinition, objectField) &&
						_shouldDeleteObjectFieldObjectValidationRuleSetting(
							objectField)) {

						return true;
					}

					return false;
				}
			));
	}

	private boolean _shouldDeleteLastPublishedObjectDefinitionObjectField(
		ObjectDefinition objectDefinition, ObjectField objectField) {

		if (!objectDefinition.isApproved() || objectDefinition.isSystem()) {
			return true;
		}

		int customObjectFieldsCount =
			_objectFieldLocalService.getObjectFieldsCount(
				objectField.getObjectDefinitionId(), false);

		if (customObjectFieldsCount <= 1) {
			return false;
		}

		return true;
	}

	private boolean _shouldDeleteObjectFieldObjectValidationRuleSetting(
		ObjectField objectField) {

		for (ObjectValidationRule objectValidationRule :
				_objectValidationRuleLocalService.
					getObjectValidationRulesByODI_E(
						objectField.getObjectDefinitionId(),
						ObjectValidationRuleConstants.
							ENGINE_TYPE_COMPOSITE_KEY)) {

			for (ObjectValidationRuleSetting objectValidationRuleSetting :
					objectValidationRule.getObjectValidationRuleSettings()) {

				if (!objectValidationRuleSetting.compareName(
						ObjectValidationRuleSettingConstants.
							NAME_COMPOSITE_KEY_OBJECT_FIELD_ID)) {

					continue;
				}

				long objectFieldId = GetterUtil.getLong(
					objectValidationRuleSetting.getValue());

				if (objectFieldId == objectField.getObjectFieldId()) {
					return false;
				}
			}
		}

		return true;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

}