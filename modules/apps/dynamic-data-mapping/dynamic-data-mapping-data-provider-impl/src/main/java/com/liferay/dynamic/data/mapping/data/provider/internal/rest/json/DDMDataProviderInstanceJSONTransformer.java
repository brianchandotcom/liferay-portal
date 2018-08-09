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

package com.liferay.dynamic.data.mapping.data.provider.internal.rest.json;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.json.transformer.ObjectTransformer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance",
	service = JSONTransformer.class
)
public class DDMDataProviderInstanceJSONTransformer extends ObjectTransformer {

	@Override
	public void transform(JSONContext jsonContext, Object object) {
		DDMDataProviderInstance ddmDataProviderInstance =
			(DDMDataProviderInstance)object;

		if (!StringUtil.equals(ddmDataProviderInstance.getType(), "rest")) {
			super.transform(jsonContext, object);

			return;
		}

		try {
			JSONObject definitionJSONObject = JSONFactoryUtil.createJSONObject(
				ddmDataProviderInstance.getDefinition());

			JSONArray fieldValuesJSONArray = definitionJSONObject.getJSONArray(
				"fieldValues");

			JSONArray newJSONArray = JSONFactoryUtil.createJSONArray();

			for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
				JSONObject fieldValueJSONObject =
					fieldValuesJSONArray.getJSONObject(i);

				if (!StringUtil.equals(
						fieldValueJSONObject.getString("name"), "password")) {

					newJSONArray.put(fieldValueJSONObject);
				}
			}

			definitionJSONObject.put("fieldValues", newJSONArray);

			ddmDataProviderInstance.setDefinition(
				definitionJSONObject.toJSONString());
		}
		catch (JSONException jsone) {
			throw new RuntimeException(jsone);
		}

		super.transform(jsonContext, object);
	}

}