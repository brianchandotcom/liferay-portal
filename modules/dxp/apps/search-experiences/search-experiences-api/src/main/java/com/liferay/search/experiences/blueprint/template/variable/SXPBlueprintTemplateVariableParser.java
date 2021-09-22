/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.blueprint.template.variable;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface SXPBlueprintTemplateVariableParser {

	public Optional<Object> parse(
		String className, Object object, SXPParameterData sxpParameterData);

	public Optional<JSONArray> parseJSONArray(
		String className, JSONArray jsonArray,
		SXPParameterData sxpParameterData);

	public Optional<JSONObject> parseJSONObject(
		String className, JSONObject jsonObject,
		SXPParameterData sxpParameterData);

}