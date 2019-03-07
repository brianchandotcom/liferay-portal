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

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(service = Sorts.class)
public class SortsImpl implements Sorts {

	@Override
	public FieldSort field(String field) {
		return new FieldSortImpl(field);
	}

	@Override
	public FieldSort field(String field, SortOrder sortOrder) {
		FieldSortImpl fieldSortImpl = new FieldSortImpl(field);

		fieldSortImpl.setSortOrder(sortOrder);

		return fieldSortImpl;
	}

	@Override
	public GeoDistanceSort geoDistance(String field) {
		return new GeoDistanceSortImpl(field);
	}

	@Override
	public NestedSort nested(String path) {
		return new NestedSortImpl(path);
	}

	@Override
	public ScoreSort score() {
		return new ScoreSortImpl();
	}

	@Override
	public ScriptSort script(
		Script script, ScriptSort.ScriptSortType scriptSortType) {

		return new ScriptSortImpl(script, scriptSortType);
	}

}