/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ExportImportObjectDefinitionUtil {

	public static void apply(
		JSONObject objectDefinitionJSONObject,
		UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction) {

		_apply(
			objectDefinitionJSONObject, unsafeFunction, "objectLayouts",
			"objectLayoutTabs", "objectLayoutBoxes", "objectLayoutRows",
			"objectLayoutColumns");
	}

	public static void prepareObjectDefinitionForExport(
		JSONFactory jsonFactory, ObjectDefinition objectDefinition) {

		for (ObjectAction objectAction : objectDefinition.getObjectActions()) {
			Map<String, Object> parameters =
				(Map<String, Object>)objectAction.getParameters();

			Object object = parameters.get("predefinedValues");

			if (object == null) {
				continue;
			}

			parameters.put(
				"predefinedValues",
				ListUtil.toList(
					(ArrayList<LinkedHashMap>)object,
					jsonFactory::createJSONObject));
		}

		objectDefinition.setObjectFields(
			ArrayUtil.filter(
				objectDefinition.getObjectFields(),
				objectField -> Validator.isNull(
					objectField.getRelationshipType())));
	}

	private static void _apply(
		JSONObject jsonObject,
		UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction,
		String... properties) {

		JSONArray jsonArray = (JSONArray)jsonObject.get(properties[0]);

		if (properties.length != 1) {
			for (int i = 0; i < jsonArray.length(); i++) {
				_apply(
					(JSONObject)jsonArray.get(i), unsafeFunction,
					Arrays.copyOfRange(properties, 1, properties.length));
			}

			return;
		}

		JSONArray newJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject internalJSONObject = (JSONObject)jsonArray.get(i);

			newJSONArray.put(() -> unsafeFunction.apply(internalJSONObject));
		}

		jsonObject.put(properties[0], newJSONArray);
	}

}