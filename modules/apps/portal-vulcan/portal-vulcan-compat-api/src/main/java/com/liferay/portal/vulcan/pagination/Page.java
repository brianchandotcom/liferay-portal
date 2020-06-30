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

package com.liferay.portal.vulcan.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Hernández
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
@JacksonXmlRootElement(localName = "page")
public class Page<T> {

	public static <T> Page<T> of(Collection<T> items) {
		return new Page<>(items);
	}

	public static <T> Page<T> of(
		Collection<T> items, Pagination pagination, long totalCount) {

		return new Page<>(items, pagination, totalCount);
	}

	public static <T> Page<T> of(
		Map<String, Map<String, String>> actions, Collection<T> items) {

		return new Page<>(actions, items);
	}

	public static <T> Page<T> of(
		Map<String, Map<String, String>> actions, Collection<T> items,
		Pagination pagination, long totalCount) {

		return new Page<>(actions, items, pagination, totalCount);
	}

	@JsonProperty("actions")
	public Map<String, Map<String, String>> getActions() {
		return _actions;
	}

	@JacksonXmlElementWrapper(localName = "items")
	@JacksonXmlProperty(localName = "item")
	public Collection<T> getItems() {
		return new ArrayList<>(_items);
	}

	public long getLastPage() {
		if ((_pageSize == 0) || (_totalCount == 0)) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _pageSize);
	}

	@JsonProperty("page")
	public long getPage() {
		return _page;
	}

	@JsonProperty("pageSize")
	public long getPageSize() {
		return _pageSize;
	}

	public long getTotalCount() {
		return _totalCount;
	}

	public boolean hasNext() {
		if (getLastPage() > _page) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_page > 1) {
			return true;
		}

		return false;
	}

	private Page(Collection<T> items) {
		this(new HashMap<>(), items);
	}

	private Page(Collection<T> items, Pagination pagination, long totalCount) {
		this(new HashMap<>(), items, pagination, totalCount);
	}

	private Page(
		Map<String, Map<String, String>> actions, Collection<T> items) {

		_actions = actions;
		_items = items;
		_page = 1;
		_pageSize = items.size();
		_totalCount = items.size();
	}

	private Page(
		Map<String, Map<String, String>> actions, Collection<T> items,
		Pagination pagination, long totalCount) {

		_actions = actions;
		_items = items;

		if (pagination == null) {
			_page = 0;
			_pageSize = 0;
		}
		else {
			_page = pagination.getPage();
			_pageSize = pagination.getPageSize();
		}

		_totalCount = totalCount;
	}

	private final Map<String, Map<String, String>> _actions;
	private final Collection<T> _items;
	private final long _page;
	private final long _pageSize;
	private final long _totalCount;

}