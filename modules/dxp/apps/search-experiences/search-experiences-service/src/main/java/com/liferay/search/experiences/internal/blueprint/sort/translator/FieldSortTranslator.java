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

package com.liferay.search.experiences.internal.blueprint.sort.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataImpl;
import com.liferay.search.experiences.internal.blueprint.query.util.ClauseHelper;
import com.liferay.search.experiences.internal.blueprint.util.SetterHelper;

import java.util.Collections;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=field", service = SortTranslator.class
)
public class FieldSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		String field, JSONObject jsonObject, SortOrder sortOrder) {

		FieldSort fieldSort = _sorts.field(field, sortOrder);

		_setterHelper.setObjectValue(
			jsonObject, "missing", fieldSort::setMissing);

		if (jsonObject.has("mode")) {
			fieldSort.setSortMode(_getSortMode(jsonObject));
		}

		if (jsonObject.has("nested")) {
			fieldSort.setNestedSort(_getNestedSort(jsonObject));
		}

		return Optional.of(fieldSort);
	}

	private NestedSort _getNestedSort(JSONObject jsonObject) {
		JSONObject nestedJSONObject = jsonObject.getJSONObject("nested");

		String path = nestedJSONObject.getString("path");

		NestedSort nestedSort = _sorts.nested(path);

		if (jsonObject.has("filter")) {
			
			Optional<Query> optional = _clauseHelper.getQueryOptional(
				jsonObject, new SXPParameterDataImpl("", Collections.emptySet()));

			if (optional.isPresent()) {
				nestedSort.setFilterQuery(optional.get());
			}
		}

		if (nestedJSONObject.has("nested")) {
			nestedSort.setNestedSort(_getNestedSort(nestedJSONObject));
		}

		return nestedSort;
	}

	private SortMode _getSortMode(JSONObject jsonObject) {
		String s = jsonObject.getString("mode");

		return SortMode.valueOf(StringUtil.toUpperCase(s));
	}

	@Reference
	private ClauseHelper _clauseHelper;

	@Reference
	private SetterHelper _setterHelper;

	@Reference
	private Sorts _sorts;

}