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

package com.liferay.portal.search.tuning.blueprints.engine.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface BlueprintTemplateVariableParser {

	public Optional<JSONObject> parse(
		JSONObject jsonObject, ParameterData parameterData, Messages messages);

	public Optional<Object> parseObject(
		Object object, ParameterData parameterData, Messages messages);

}