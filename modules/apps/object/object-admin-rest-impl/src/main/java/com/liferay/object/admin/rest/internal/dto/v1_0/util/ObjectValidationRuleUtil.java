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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.util.LocalizedMapUtil;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectValidationRuleUtil {

	public static ObjectValidationRule toObjectValidationRule(
		Map<String, Map<String, String>> actions,
		com.liferay.object.model.ObjectValidationRule
			serviceBuilderObjectValidationRule) {

		if (serviceBuilderObjectValidationRule == null) {
			return null;
		}

		ObjectValidationRule objectValidationRule = new ObjectValidationRule() {
			{
				active = serviceBuilderObjectValidationRule.isActive();
				dateCreated =
					serviceBuilderObjectValidationRule.getCreateDate();
				dateModified =
					serviceBuilderObjectValidationRule.getModifiedDate();
				engine = serviceBuilderObjectValidationRule.getEngine();
				errorLabel = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectValidationRule.getErrorLabelMap());
				id =
					serviceBuilderObjectValidationRule.
						getObjectValidationRuleId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectValidationRule.getNameMap());
				objectDefinitionId =
					serviceBuilderObjectValidationRule.getObjectDefinitionId();
				script = serviceBuilderObjectValidationRule.getScript();
			}
		};

		objectValidationRule.setActions(actions);

		return objectValidationRule;
	}

}