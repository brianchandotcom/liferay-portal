/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Joshua Cords
 */
public class AssetListTypePropertiesUtil {

	public static JSONArray getTypePropertiesJSONArray(
		long[] classNameIds, long[] classTypeIds, long companyId,
		Locale locale) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < classNameIds.length; i++) {
			long classTypeId = 0;

			if (i < classTypeIds.length) {
				classTypeId = classTypeIds[i];
			}

			ObjectDefinition objectDefinition = _resolveObjectDefinition(
				classNameIds[i], classTypeId, companyId);

			if (objectDefinition == null) {
				continue;
			}

			for (ObjectField objectField :
					ObjectFieldLocalServiceUtil.getObjectFields(
						objectDefinition.getObjectDefinitionId())) {

				if (objectField.isMetadata()) {
					continue;
				}

				String type = _toFilterType(objectField.getBusinessType());

				if (type == null) {
					continue;
				}

				jsonArray.put(
					_toPropertyJSONObject(
						classNameIds[i], classTypeId, locale, objectField,
						type));
			}
		}

		return jsonArray;
	}

	private static ObjectDefinition _resolveObjectDefinition(
		long classNameId, long classTypeId, long companyId) {

		if (classTypeId > 0) {
			ObjectDefinition objectDefinition =
				ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
					classTypeId);

			if (objectDefinition != null) {
				return objectDefinition;
			}
		}

		if (classNameId <= 0) {
			return null;
		}

		return ObjectDefinitionLocalServiceUtil.
			fetchObjectDefinitionByClassName(
				companyId, PortalUtil.getClassName(classNameId));
	}

	private static String _toFilterType(String businessType) {
		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN)) {
			return "boolean";
		}

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_DATE)) {
			return "date";
		}

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {
			return "date-time";
		}

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_DECIMAL) ||
			businessType.equals(
				ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL)) {

			return "double";
		}

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_INTEGER) ||
			businessType.equals(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)) {

			return "integer";
		}

		if (businessType.equals(
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST) ||
			businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return "picklist";
		}

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			businessType.equals(
				ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER) ||
			businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT) ||
			businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			return "string";
		}

		return null;
	}

	private static JSONObject _toPropertyJSONObject(
		long classNameId, long classTypeId, Locale locale,
		ObjectField objectField, String type) {

		JSONObject jsonObject = JSONUtil.put(
			"classNameId", classNameId
		).put(
			"classTypeId", classTypeId
		).put(
			"label", objectField.getLabel(locale, true)
		).put(
			"name", objectField.getName()
		).put(
			"type", type
		);

		if (!type.equals("picklist") ||
			(objectField.getListTypeDefinitionId() <= 0)) {

			return jsonObject;
		}

		JSONArray optionsJSONArray = JSONFactoryUtil.createJSONArray();

		for (ListTypeEntry listTypeEntry :
				ListTypeEntryLocalServiceUtil.getListTypeEntries(
					objectField.getListTypeDefinitionId())) {

			optionsJSONArray.put(
				JSONUtil.put(
					"label", listTypeEntry.getName(locale, true)
				).put(
					"value", listTypeEntry.getKey()
				));
		}

		return jsonObject.put("options", optionsJSONArray);
	}

}