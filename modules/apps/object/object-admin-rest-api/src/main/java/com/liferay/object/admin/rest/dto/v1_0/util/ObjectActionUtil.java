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

package com.liferay.object.admin.rest.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectActionUtil {

	public static ObjectAction toObjectAction(
		Map<String, Map<String, String>> actions, Locale locale,
		com.liferay.object.model.ObjectAction serviceBuilderObjectAction) {

		if (serviceBuilderObjectAction == null) {
			return null;
		}

		ObjectAction objectAction = new ObjectAction() {
			{
				active = serviceBuilderObjectAction.isActive();
				conditionExpression =
					serviceBuilderObjectAction.getConditionExpression();
				dateCreated = serviceBuilderObjectAction.getCreateDate();
				dateModified = serviceBuilderObjectAction.getModifiedDate();
				description = serviceBuilderObjectAction.getDescription();
				id = serviceBuilderObjectAction.getObjectActionId();
				name = serviceBuilderObjectAction.getName();
				objectActionExecutorKey =
					serviceBuilderObjectAction.getObjectActionExecutorKey();
				objectActionTriggerKey =
					serviceBuilderObjectAction.getObjectActionTriggerKey();
				parameters = toParameters(
					serviceBuilderObjectAction.
						getParametersUnicodeProperties());

				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-152180"))) {

					status = new Status() {
						{
							code = serviceBuilderObjectAction.getStatus();
							label = ObjectActionConstants.getStatusLabel(
								serviceBuilderObjectAction.getStatus());
							label_i18n = LanguageUtil.get(
								locale,
								ObjectActionConstants.getStatusLabel(
									serviceBuilderObjectAction.getStatus()));
						}
					};
				}
			}
		};

		objectAction.setActions(actions);

		return objectAction;
	}

	public static Map<String, Object> toParameters(
		UnicodeProperties parametersUnicodeProperties) {

		Map<String, Object> parameters = new HashMap<>();

		for (Map.Entry<String, String> entry :
				parametersUnicodeProperties.entrySet()) {

			Object value = entry.getValue();

			if (Objects.equals(entry.getKey(), "notificationTemplateId") ||
				Objects.equals(entry.getKey(), "objectDefinitionId")) {

				value = GetterUtil.getLong(value);
			}
			else if (Objects.equals(entry.getKey(), "predefinedValues")) {
				value = JSONFactoryUtil.looseDeserialize((String)value);
			}
			else if (Objects.equals(entry.getKey(), "relatedObjectEntries")) {
				value = GetterUtil.getBoolean(value);
			}

			parameters.put(entry.getKey(), value);
		}

		return parameters;
	}

}