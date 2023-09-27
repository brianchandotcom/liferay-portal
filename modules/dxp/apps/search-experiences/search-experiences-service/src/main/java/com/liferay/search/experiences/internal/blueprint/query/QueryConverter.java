/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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