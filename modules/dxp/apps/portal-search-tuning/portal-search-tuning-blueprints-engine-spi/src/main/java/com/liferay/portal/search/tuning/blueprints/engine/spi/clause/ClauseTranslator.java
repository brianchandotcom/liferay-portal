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

package com.liferay.portal.search.tuning.blueprints.engine.spi.clause;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface ClauseTranslator {

	public Optional<Query> translate(
		JSONObject configurationJSONObject, Blueprint blueprint,
		ParameterData parameterData, Messages messages);

}