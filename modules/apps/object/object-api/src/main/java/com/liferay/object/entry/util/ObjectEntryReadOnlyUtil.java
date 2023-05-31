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

package com.liferay.object.entry.util;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.dynamic.data.mapping.expression.ObjectEntryDDMExpressionFieldAccessor;
import com.liferay.object.exception.ObjectFieldReadOnlyException;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Paulo Albuquerque
 */
public class ObjectEntryReadOnlyUtil {

	public static void validateReadOnly(
			Map<String, Object> existingValues, Map<String, Object> values,
			DDMExpressionFactory ddmExpressionFactory,
			List<ObjectField> objectFields)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-170122")) {
			return;
		}

		if (MapUtil.isEmpty(existingValues)) {
			for (ObjectField objectField : objectFields) {
				existingValues.put(
					objectField.getName(),
					ObjectFieldSettingUtil.getDefaultValueAsString(
						null, objectField.getObjectFieldId(),
						ObjectFieldSettingLocalServiceUtil.getService(), null));
			}
		}

		existingValues.put("currentUserId", PrincipalThreadLocal.getUserId());

		Map<String, ObjectField> objectFieldMap = new HashMap<>();

		for (ObjectField objectField : objectFields) {
			objectFieldMap.put(objectField.getName(), objectField);
		}

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (Objects.equals(entry.getKey(), "status")) {
				continue;
			}

			ObjectField objectField = objectFieldMap.get(entry.getKey());

			if (Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_FALSE)) {

				continue;
			}

			if (Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_TRUE)) {

				_verifyReadOnlyTrue(
					entry.getKey(), entry.getValue(), existingValues);

				continue;
			}

			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						objectField.getReadOnlyConditionExpression()
					).withDDMExpressionFieldAccessor(
						new ObjectEntryDDMExpressionFieldAccessor(
							existingValues)
					).build());

			ddmExpression.setVariables(existingValues);

			if (ddmExpression.evaluate()) {
				_verifyReadOnlyTrue(
					entry.getKey(), entry.getValue(), existingValues);
			}
		}
	}

	private static void _verifyReadOnlyTrue(
			String objectFieldName, Object value,
			Map<String, Object> existingValues)
		throws PortalException {

		Object existingValue = existingValues.get(objectFieldName);

		if (!((Validator.isNull(existingValue) && Validator.isNull(value)) ||
			  Objects.equals(value, existingValue))) {

			throw new ObjectFieldReadOnlyException(
				"The object field " + objectFieldName + " is readOnly");
		}
	}

}