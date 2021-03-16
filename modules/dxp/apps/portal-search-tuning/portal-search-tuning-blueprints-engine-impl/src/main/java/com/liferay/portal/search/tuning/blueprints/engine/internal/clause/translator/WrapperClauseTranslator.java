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

package com.liferay.portal.search.tuning.blueprints.engine.internal.clause.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.WrapperQueryConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ClauseTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=wrapper",
	service = ClauseTranslator.class
)
public class WrapperClauseTranslator implements ClauseTranslator {

	@Override
	public Optional<Query> translate(
		JSONObject configurationJSONObject, Blueprint blueprint,
		ParameterData parameterData, Messages messages) {

		String query = configurationJSONObject.getString(
			WrapperQueryConfigurationKeys.QUERY.getJsonKey());

		WrapperQuery wrapperQuery = _queries.wrapper(query);

		return Optional.of(wrapperQuery);
	}

	@Reference
	private Queries _queries;

}