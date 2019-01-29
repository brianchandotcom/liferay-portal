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

package com.liferay.portal.search.sort;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public class FieldSort extends Sort {

	public FieldSort(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(SortVisitor<T> sortVisitor) {
		return sortVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public Object getMissing() {
		return _missing;
	}

	public NestedSort getNestedSort() {
		return _nestedSort;
	}

	public SortMode getSortMode() {
		return _sortMode;
	}

	public void setMissing(Object missing) {
		_missing = missing;
	}

	public void setNestedSort(NestedSort nestedSort) {
		_nestedSort = nestedSort;
	}

	public void setSortMode(SortMode sortMode) {
		_sortMode = sortMode;
	}

	private final String _field;
	private Object _missing;
	private NestedSort _nestedSort;
	private SortMode _sortMode;

}