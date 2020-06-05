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

package com.liferay.frontend.taglib.clay.sample.web.internal.data.set;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "clay.data.provider.key=Sample",
	service = ClayDataSetDataProvider.class
)
public class SampleClayDataSetProvider
	implements ClayDataSetDataProvider<SampleEntity> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		List<SampleEntity> items = _filter(_getAll(), filter);

		return items.size();
	}

	@Override
	public List<SampleEntity> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		return _paginate(_filter(_getAll(), filter), pagination);
	}

	private List<SampleEntity> _filter(
		List<SampleEntity> items, Filter filter) {

		Stream<SampleEntity> stream = items.stream();

		return stream.filter(
			item -> item.name.indexOf(filter.getKeywords()) > -1
		).collect(
			Collectors.toList()
		);
	}

	private List<SampleEntity> _getAll() {
		List<SampleEntity> entries = new ArrayList<>();

		entries.add(new SampleEntity("Name 1", "Description 1"));
		entries.add(new SampleEntity("Name 2", "Description 2"));
		entries.add(new SampleEntity("Name 3", "Description 3"));
		entries.add(new SampleEntity("Name 4", "Description 4"));
		entries.add(new SampleEntity("Name 5", "Description 5"));
		entries.add(new SampleEntity("Name 6", "Description 6"));
		entries.add(new SampleEntity("Name 7", "Description 7"));
		entries.add(new SampleEntity("Name 8", "Description 8"));
		entries.add(new SampleEntity("Name 9", "Description 9"));
		entries.add(new SampleEntity("Name 10", "Description 10"));

		return entries;
	}

	private List<SampleEntity> _paginate(
		List<SampleEntity> items, Pagination pagination) {

		if (pagination == null) {
			return items;
		}

		return items.subList(
			pagination.getStartPosition(), pagination.getEndPosition());
	}

}