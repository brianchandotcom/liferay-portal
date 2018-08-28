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
import com.liferay.structured.content.apio.architect.sort.SortParser;
import com.liferay.structured.content.apio.architect.sort.SortQuery;
import com.liferay.structured.content.apio.architect.sort.SortQuery.SortQueryPart;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Utility for parsing Sort expressions. It uses a model to create a
 * {@link SortQuery}.
 *
 * @author Cristina González
 * @review
 */
@Component(immediate = true, service = SortParser.class)
public class SortParserImpl implements SortParser {

	/**
	 * Returns a {@link SortQuery} obtained from a comma-separated
	 * list of field names and sort directions.
	 *
	 * Sort directions supported are desc and asc and can be appended to each
	 * sort field, separated by the ':' character.
	 *
	 * If a sort direction is not provided, the sort key will be 'asc'.
	 *
	 * For example:
	 * - field1,field2,field3
	 * - field1:asc,field2:desc,field3
	 * - field1:asc,field2,field3:desc
	 *
	 * @param  sortQueryString - String to be parsed
	 * @return a {@link SortQuery}
	 * @review
	 */
	public SortQuery parse(String sortQueryString) {
		if (sortQueryString == null) {
			return new SortQuery(Collections.emptyList());
		}

		List<String> sortQueryList = StringUtil.split(sortQueryString);

		Stream<String> stream = sortQueryList.stream();

		return new SortQuery(
			stream.map(
				this::getSortQueryPart
			).flatMap(
				sortKeyOptional ->
					sortKeyOptional.map(Stream::of).orElseGet(Stream::empty)
			).collect(
				Collectors.toList()
			));
	}

	protected Optional<SortQueryPart> getSortQueryPart(
		String sortQueryPartString) {

		List<String> sortQueryPartList = StringUtil.split(
			sortQueryPartString, ':');

		if (sortQueryPartList.isEmpty()) {
			return Optional.empty();
		}

		if (sortQueryPartList.size() > 2) {
			throw new RuntimeException("Unable to parse sort expression");
		}

		String fieldName = sortQueryPartList.get(0);

		boolean ascending = _ASC_DEFAULT;

		if (sortQueryPartList.size() > 1) {
			ascending = isAscending(sortQueryPartList.get(1));
		}

		return Optional.of(new SortQueryPart(fieldName, ascending));
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