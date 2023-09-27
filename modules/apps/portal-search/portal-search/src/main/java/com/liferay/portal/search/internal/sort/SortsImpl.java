/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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