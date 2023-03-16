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

package com.liferay.object.field.setting.util;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingUtil {

	public static String getDefaultValueAsString(
		DDMExpressionFactory ddmExpressionFactory, long objectFieldId,
		ObjectFieldSettingLocalService objectFieldSettingLocalService,
		Map<String, Object> values) {

		ObjectFieldSetting objectFieldSetting =
			objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId,
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);

		if (objectFieldSetting == null) {
			return StringPool.BLANK;
		}

		ObjectFieldSetting objectFieldSettingDefaultValue =
			objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, ObjectFieldSettingConstants.NAME_DEFAULT_VALUE);

		if (StringUtil.equals(
				objectFieldSetting.getValue(),
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE)) {

			return objectFieldSettingDefaultValue.getValue();
		}

		if ((ddmExpressionFactory == null) ||
			!StringUtil.equals(
				objectFieldSetting.getValue(),
				ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER)) {

			return StringPool.BLANK;
		}

		try {
			DDMExpression<String> ddmExpression =
				ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						objectFieldSettingDefaultValue.getValue()
					).build());

			ddmExpression.setVariables(values);

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new RuntimeException(ddmExpressionException);
		}
	}

	public static String getValue(String name, ObjectField objectField) {
		for (ObjectFieldSetting objectFieldSetting :
				objectField.getObjectFieldSettings()) {

			if (Objects.equals(objectFieldSetting.getName(), name)) {
				return objectFieldSetting.getValue();
			}
		}

		return null;
	}

}