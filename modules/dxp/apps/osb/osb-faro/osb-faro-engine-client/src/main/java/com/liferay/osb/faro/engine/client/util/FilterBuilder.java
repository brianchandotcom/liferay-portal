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

package com.liferay.osb.faro.engine.client.util;

import com.liferay.osb.faro.engine.client.constants.FilterConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matthew Kong
 */
public class FilterBuilder implements Cloneable {

	public void addBlankFilter(
		String fieldName, String operator, String fieldNameContext) {

		_requiredFilters.add(
			FilterUtil.getBlankFilter(
				FilterUtil.getFieldName(fieldName, fieldNameContext),
				operator));
	}

	public void addFilter(FilterBuilder filterBuilder, boolean required) {
		if (required) {
			_requiredFilters.add(filterBuilder.build());
		}
		else {
			_filters.add(filterBuilder.build());
		}
	}

	public void addFilter(String filter) {
		if (Validator.isNotNull(filter)) {
			_requiredFilters.add(filter);
		}
	}

	public void addFilter(String fieldName, String operator, Object value) {
		addFilter(fieldName, operator, value, true);
	}

	public void addFilter(
		String fieldName, String operator, Object value, boolean required) {

		addFilter(fieldName, operator, value, false, required);
	}

	public void addFilter(
		String fieldName, String operator, Object value, boolean negate,
		boolean required) {

		if (Validator.isNull(fieldName) || Validator.isNull(operator) ||
			Validator.isNull(value)) {

			return;
		}

		if (value instanceof String) {
			value = getValue(operator, value);
		}

		String filterString = FilterUtil.getFilter(fieldName, operator, value);

		if (negate) {
			filterString = FilterUtil.negate(filterString);
		}

		if (required) {
			_requiredFilters.add(filterString);
		}
		else {
			_filters.add(filterString);
		}
	}

	public void addFilter(
		String fieldName, String operator, Object value,
		String fieldNameContext) {

		addFilter(
			FilterUtil.getFieldName(fieldName, fieldNameContext), operator,
			value, true);
	}

	public void addInterestFilter(String interestName, boolean interested) {
		addFilter(FilterUtil.getInterestFilter(interestName, interested));
	}

	public void addNullFilter(String fieldName, String operator) {
		_requiredFilters.add(FilterUtil.getNullFilter(fieldName, operator));
	}

	public void addNullFilter(
		String fieldName, String operator, String fieldNameContext) {

		addNullFilter(
			FilterUtil.getFieldName(fieldName, fieldNameContext), operator);
	}

	public void addSearchFilter(
		String query, List<String> fieldNames, String fieldNameContext) {

		if (Validator.isNull(query) || fieldNames.isEmpty()) {
			return;
		}

		String[] keywords = StringUtil.split(query, StringPool.SPACE);

		boolean nameSearch = false;

		if ((keywords.length > 1) && fieldNames.containsAll(_nameFieldNames)) {
			nameSearch = true;
		}

		if (nameSearch) {
			FilterBuilder fieldNameFilterBuilder = new FilterBuilder();

			for (String fieldName : _nameFieldNames) {
				FilterBuilder keywordsFilterBuilder = new FilterBuilder();

				for (String keyword : keywords) {
					keywordsFilterBuilder.addFilter(
						FilterUtil.getFieldName(fieldName, fieldNameContext),
						FilterConstants.STRING_FUNCTION_CONTAINS, keyword,
						false);
				}

				fieldNameFilterBuilder.addFilter(keywordsFilterBuilder, true);
			}

			_filters.add(fieldNameFilterBuilder.build());
		}

		for (String fieldName : fieldNames) {
			if (!nameSearch || !_nameFieldNames.contains(fieldName)) {
				FilterBuilder fieldNameFilterBuilder = new FilterBuilder();

				fieldNameFilterBuilder.addSearchFilter(
					query, fieldName, fieldNameContext);

				_filters.add(fieldNameFilterBuilder.build());
			}
		}
	}

	public void addSearchFilter(String query, String fieldName) {
		addSearchFilter(query, fieldName, null);
	}

	public void addSearchFilter(
		String query, String fieldName, String fieldNameContext) {

		String[] keywords = StringUtil.split(query, StringPool.SPACE);

		for (String keyword : keywords) {
			addFilter(
				FilterUtil.getFieldName(fieldName, fieldNameContext),
				FilterConstants.STRING_FUNCTION_CONTAINS, keyword);
		}
	}

	public String build() {
		StringBundler sb = new StringBundler();

		if (!_filters.isEmpty()) {
			buildQueries(sb, _filters, FilterConstants.LOGICAL_OPERATOR_OR);

			if (!_requiredFilters.isEmpty()) {
				sb.append(FilterConstants.LOGICAL_OPERATOR_AND);
			}
		}

		if (!_requiredFilters.isEmpty()) {
			buildQueries(
				sb, _requiredFilters, FilterConstants.LOGICAL_OPERATOR_AND);
		}

		if (sb.length() > 0) {
			return sb.toString();
		}

		return null;
	}

	@Override
	public FilterBuilder clone() throws CloneNotSupportedException {
		FilterBuilder filterBuilder = (FilterBuilder)super.clone();

		List<String> requiredFilters = filterBuilder._requiredFilters;

		requiredFilters.add(build());

		return filterBuilder;
	}

	protected void buildQueries(
		StringBundler sb, List<String> filterQueries, String operator) {

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (String filterQuery : filterQueries) {
			sb.append(filterQuery);
			sb.append(operator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected String getValue(String operator, Object value) {
		String valueString = (String)value;

		return valueString.replaceAll(
			StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
	}

	private static final List<String> _nameFieldNames = Arrays.asList(
		"familyName", "givenName");

	private final List<String> _filters = new ArrayList<>();
	private final List<String> _requiredFilters = new ArrayList<>();

}