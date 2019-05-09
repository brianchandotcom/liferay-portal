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

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public abstract class BaseBooleanQueryImpl
	extends BaseQueryImpl implements BooleanQuery {

	@Override
	public Map<String, Query> addTerms(String[] fields, String values)
		throws ParseException {

		if (Validator.isNull(values)) {
			return Collections.emptyMap();
		}

		if (fields == null) {
			fields = new String[0];
		}

		Map<String, Query> queries = new HashMap<>((int)(fields.length / .75));

		for (String field : fields) {
			Query query = addTerm(field, values);

			queries.put(field, query);
		}

		return queries;
	}

	@Override
	public Map<String, Query> addTerms(
			String[] fields, String value, boolean like)
		throws ParseException {

		if (Validator.isNull(value)) {
			return Collections.emptyMap();
		}

		Map<String, Query> queries = new HashMap<>((int)(fields.length / .75));

		for (String field : fields) {
			Query query = addTerm(field, value, like);

			queries.put(field, query);
		}

		return queries;
	}

	protected Map<String, List<Query>> addTerms(
			String[] fields, Map<String, List<String>> termFieldsValuesMap)
		throws ParseException {

		Map<String, List<Query>> queries = new HashMap<>(
			(int)(fields.length / .75));

		for (String field : fields) {
			List<String> valuesList = termFieldsValuesMap.get(field);

			List<Query> queriesList = new ArrayList<>(valuesList.size());

			queries.put(field, queriesList);

			for (String value : valuesList) {
				Query query = addTerm(field, value);

				queriesList.add(query);
			}
		}

		return queries;
	}

	protected String[] parseKeywords(String values) {
		if (!values.contains(StringPool.QUOTE)) {
			return StringUtil.split(values, CharPool.SPACE);
		}

		List<String> keywords = new ArrayList<>();

		while (values.length() > 0) {
			if (values.startsWith(StringPool.QUOTE)) {
				values = values.substring(1);

				if (values.contains(StringPool.QUOTE)) {
					int pos = values.indexOf(StringPool.QUOTE);

					keywords.add(values.substring(0, pos));

					values = values.substring(pos + 1);

					values = values.trim();
				}
			}
			else {
				if (values.contains(StringPool.SPACE)) {
					int pos = values.indexOf(StringPool.SPACE);

					keywords.add(values.substring(0, pos));

					values = values.substring(pos + 1);

					values = values.trim();
				}
				else {
					keywords.add(values);

					break;
				}
			}
		}

		return keywords.toArray(new String[0]);
	}

}