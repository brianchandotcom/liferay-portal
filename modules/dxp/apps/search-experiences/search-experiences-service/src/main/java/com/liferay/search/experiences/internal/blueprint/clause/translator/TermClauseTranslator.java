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

package com.liferay.search.experiences.internal.blueprint.clause.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.search.experiences.internal.blueprint.clause.ClauseTranslator;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.SXPJSONUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=term", service = ClauseTranslator.class
)
public class TermClauseTranslator implements ClauseTranslator {

	@Override
	public Optional<Query> translate(
		JSONObject jsonObject, SXPParameterData sxpParameterData) {

		Optional<String> optional = SXPJSONUtil.getFirstKeyOptional(jsonObject);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		String field = optional.get();

		Object object = jsonObject.get(field);

		String keywords = null;

		Float boost = 0.0F;

		if (_isShortForm(object)) {
			keywords = (String)object;
		}
		else {
			JSONObject fieldJSONObject = (JSONObject)object;

			keywords = fieldJSONObject.getString("value");

			boost = GetterUtil.getFloat(fieldJSONObject.get("boost"));
		}

		if (Validator.isBlank(keywords)) {
			return Optional.empty();
		}

		TermQuery termQuery = _queries.term(field, keywords);

		if (boost > 0.0) {
			termQuery.setBoost(boost);
		}

		return Optional.of(termQuery);
	}

	private boolean _isShortForm(Object object) {
		return object instanceof String;
	}

	@Reference
	private Queries _queries;

}