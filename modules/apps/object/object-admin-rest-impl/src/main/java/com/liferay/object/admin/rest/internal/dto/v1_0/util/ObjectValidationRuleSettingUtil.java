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

import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRuleSetting;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleSettingLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Carolina Barbosa
 */
public class ObjectValidationRuleSettingUtil {

	public static List<com.liferay.object.model.ObjectValidationRuleSetting>
		toObjectValidationRuleSettings(
			long objectDefinitionId,
			ObjectFieldLocalService objectFieldLocalService,
			ObjectValidationRuleSettingLocalService
				objectValidationRuleSettingLocalService,
			ObjectValidationRuleSetting[] objectValidationRuleSettings) {

		return TransformUtil.transformToList(
			objectValidationRuleSettings,
			objectValidationRuleSetting -> {
				com.liferay.object.model.ObjectValidationRuleSetting
					serviceBuilderObjectValidationRuleSetting =
						objectValidationRuleSettingLocalService.
							createObjectValidationRuleSetting(0L);

				if (StringUtil.equals(
						objectValidationRuleSetting.getName(),
						ObjectValidationRuleSettingConstants.
							NAME_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE)) {

					serviceBuilderObjectValidationRuleSetting.setName(
						ObjectValidationRuleSettingConstants.
							NAME_OBJECT_FIELD_ID);

					ObjectField objectField =
						objectFieldLocalService.getObjectField(
							String.valueOf(
								objectValidationRuleSetting.getValue()),
							objectDefinitionId);

					serviceBuilderObjectValidationRuleSetting.setValue(
						String.valueOf(objectField.getObjectFieldId()));

					return serviceBuilderObjectValidationRuleSetting;
				}

				serviceBuilderObjectValidationRuleSetting.setName(
					objectValidationRuleSetting.getName());
				serviceBuilderObjectValidationRuleSetting.setValue(
					String.valueOf(objectValidationRuleSetting.getValue()));

				return serviceBuilderObjectValidationRuleSetting;
			});
	}

}