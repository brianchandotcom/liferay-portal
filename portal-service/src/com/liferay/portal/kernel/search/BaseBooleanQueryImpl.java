/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public abstract class BaseBooleanQueryImpl
	extends BaseQueryImpl implements BooleanQuery {

	@Override
	public void addTerms(String[] fields, String values) throws ParseException {
		if (Validator.isNull(values)) {
			return;
		}

		if (fields == null) {
			fields = new String[0];
		}

		for (String field : fields) {
			addTerm(field, values);
		}
	}

	@Override
	public void addTerms(String[] fields, String value, boolean like)
		throws ParseException {

		if (Validator.isNull(value)) {
			return;
		}

		for (String field : fields) {
			addTerm(field, value, like);
		}
	}

	protected void addTerms(
			String[] fields, Map<String, List<String>> termFieldsValuesMap)
		throws ParseException {

		for (String field : fields) {
			List<String> valuesList = termFieldsValuesMap.get(field);

			for (String value : valuesList) {
				addTerm(field, value);
			}
		}
	}

	protected String getTermFieldRemainderValues(
		String field, String values, List<String> valuesList, String pattern,
		String replacement) {

		if (Validator.isNull(values)) {
			return values;
		}

		if (Validator.isNull(pattern) || Validator.isNull(replacement)) {
			return values;
		}

		if (Validator.isNotNull(field)) {
			field += ":";
		}
		else {
			field = StringPool.BLANK;
		}

		while (values.matches(pattern)) {
			String value = values.replaceAll(pattern, replacement);

			valuesList.add(value);

			String duplicate =
				"(?i)\\s*" + Pattern.quote(field + value) + "\\s*";

			values = values.replaceAll(duplicate, StringPool.SPACE);
			values = values.trim();
		}

		return values;
	}

	protected Map<String, List<String>> getTermFieldsValuesMap(
		String[] fields, String values) {

		Map<String, List<String>> termFieldsValuesMap =
			new HashMap<String, List<String>>();

		for (String field : fields) {
			List<String> valuesList = new ArrayList<String>();

			values = getTermFieldRemainderValues(
				field, values, valuesList,
				"(?i)^.*" + field + ":([\"\'])(.+?)(\\1).*$", "$1$2$3");

			values = getTermFieldRemainderValues(
				field, values, valuesList,
				"(?i)^.*" + field + ":([^\\s\"']*).*$", "$1");

			termFieldsValuesMap.put(field, valuesList);
		}

		values = values.trim();

		List<String> valuesList = new ArrayList<String>();

		if (Validator.isNotNull(values)) {
			values = getTermFieldRemainderValues(
				null, values, valuesList,
				"^[^\"\']*([\"\'])(.+?)(\\1)[^\"\']*$", "$1$2$3");

			valuesList.add(values);
		}

		termFieldsValuesMap.put("no_field", valuesList);

		return termFieldsValuesMap;
	}

	protected String[] parseKeywords(String values) {
		if (!values.contains(StringPool.QUOTE)) {
			return StringUtil.split(values, CharPool.SPACE);
		}

		List<String> keywords = new ArrayList<String>();

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

		return keywords.toArray(new String[keywords.size()]);
	}

}