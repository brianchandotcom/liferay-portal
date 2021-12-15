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

package com.liferay.search.experiences.internal.blueprint.query;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.search.experiences.internal.blueprint.property.PropertyValidator;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public class QueryConverter {

	public QueryConverter(Queries queries) {
		_queries = queries;
	}

	public Query toQuery(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		Iterator<String> iterator = jsonObject.keys();

		if (!iterator.hasNext()) {
			return null;
		}

		String type = iterator.next();

		if (Objects.equals(type, "term")) {
			return _toTermQuery(jsonObject.getJSONObject(type));
		}

		return _queries.wrapper(
			PropertyValidator.validate(JSONUtil.toString(jsonObject)));
	}

	private Query _toTermQuery(JSONObject jsonObject1) {
		Iterator<String> iterator = jsonObject1.keys();

		String field = iterator.next();

		Object object = jsonObject1.get(field);

		if (object instanceof JSONObject) {
			JSONObject jsonObject2 = (JSONObject)object;

			Query query = _queries.term(
				field,
				PropertyValidator.validate(
					Objects.requireNonNull(
						jsonObject2.get("value"),
						"The key \"value\" is not set")));

			if (jsonObject2.get("boost") != null) {
				query.setBoost((float)jsonObject2.getDouble("boost"));
			}

			return query;
		}

		return _queries.term(field, PropertyValidator.validate(object));
	}

	private final Queries _queries;

}