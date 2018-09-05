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

package com.liferay.structured.content.apio.internal.architect.sort;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.structured.content.apio.architect.sort.InvalidSortException;
import com.liferay.structured.content.apio.architect.sort.SortField;
import com.liferay.structured.content.apio.architect.sort.SortParser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Utility for parsing Sort strings. It uses a model to create a list of {@link
 * SortField}.
 *
 * @author Cristina González
 * @review
 */
@Component(immediate = true, service = SortParser.class)
public class SortParserImpl implements SortParser {

	/**
	 * Returns a List of {@link SortField} obtained from a comma-separated list
	 * of field names and sort directions.
	 *
	 * Sort directions supported are desc and asc and can be appended to each
	 * sort field, separated by the ':' character.
	 *
	 * If a sort direction is not provided, the sort field will be 'asc'.
	 *
	 * For example:
	 * - field1,field2,field3
	 * - field1:asc,field2:desc,field3
	 * - field1:asc,field2,field3:desc
	 *
	 * @param  sortString - String to be parsed
	 * @return a  {@link List<SortField>}
	 * @review
	 */
	public List<SortField> parse(String sortString) {
		if (Validator.isNull(sortString)) {
			return Collections.emptyList();
		}

		List<String> list = StringUtil.split(sortString);

		Stream<String> stream = list.stream();

		return stream.map(
			this::getSortFieldOptional
		).flatMap(
			sortFieldOptional -> sortFieldOptional.map(
				Stream::of
			).orElseGet(
				Stream::empty
			)
		).collect(
			Collectors.toList()
		);
	}

	protected Optional<SortField> getSortFieldOptional(String sortString) {
		List<String> list = StringUtil.split(sortString, ':');

		if (list.isEmpty()) {
			return Optional.empty();
		}

		if (list.size() > 2) {
			throw new InvalidSortException(
				"Unable to parse sort string: " + sortString);
		}

		String fieldName = list.get(0);

		boolean ascending = _ASC_DEFAULT;

		if (list.size() > 1) {
			ascending = isAscending(list.get(1));
		}

		return Optional.of(new SortField(fieldName, ascending));
	}

	protected boolean isAscending(String orderBy) {
		if (orderBy == null) {
			return _ASC_DEFAULT;
		}

		if (_ORDER_BY_ASC.equals(
				com.liferay.portal.kernel.util.StringUtil.toLowerCase(
					orderBy))) {

			return true;
		}

		if (_ORDER_BY_DESC.equals(
				com.liferay.portal.kernel.util.StringUtil.toLowerCase(
					orderBy))) {

			return false;
		}

		return _ASC_DEFAULT;
	}

	private static final boolean _ASC_DEFAULT = true;

	private static final String _ORDER_BY_ASC = "asc";

	private static final String _ORDER_BY_DESC = "desc";

}