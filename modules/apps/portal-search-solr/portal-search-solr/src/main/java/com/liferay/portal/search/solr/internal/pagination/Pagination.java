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

package com.liferay.portal.search.solr.internal.pagination;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;

import java.util.Optional;

/**
 * @author André de Oliveira
 * @author Preston Crary
 */
public class Pagination {

	public Pagination(int start, int end) {
		if (start < QueryUtil.ALL_POS) {
			throw new IllegalArgumentException(
				"Start cannot be negative: " + start);
		}

		if (end < QueryUtil.ALL_POS) {
			throw new IllegalArgumentException(
				"End cannot be negative: " + end);
		}

		if ((end != QueryUtil.ALL_POS) && (end < start)) {
			throw new IllegalArgumentException(
				"End cannot be less than start: " + end + ", " + start);
		}

		_start = start;
		_end = end;
	}

	public int getEnd() {
		return _end;
	}

	public Optional<Integer> getFrom() {
		if (isUnboundedStart()) {
			return Optional.empty();
		}

		return Optional.of(_start);
	}

	public Optional<Integer> getSize() {
		if (isUnboundedEnd()) {
			return Optional.empty();
		}

		if (isUnboundedStart()) {
			return Optional.of(_end);
		}

		return Optional.of(_end - _start);
	}

	public int getStart() {
		return _start;
	}

	public boolean isUnboundedEnd() {
		if (_end == QueryUtil.ALL_POS) {
			return true;
		}

		return false;
	}

	public boolean isUnboundedStart() {
		if (_start == QueryUtil.ALL_POS) {
			return true;
		}

		return false;
	}

	public Optional<Pagination> repageToLast(int total) {
		if (total <= 0) {
			return Optional.empty();
		}

		Optional<Integer> sizeOptional = getSize();

		if (!sizeOptional.isPresent()) {
			return Optional.of(
				new Pagination(QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		if (sizeOptional.get() == 0) {
			return Optional.empty();
		}

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			_start, _end, total);

		return Optional.of(new Pagination(startAndEnd[0], startAndEnd[1]));
	}

	private final int _end;
	private final int _start;

}