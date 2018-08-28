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

package com.liferay.structured.content.apio.architect.sort;

import java.io.Serializable;

import java.util.List;

/**
 * Models a Sort param for sorting structured content by different fields and
 * sort directives.
 *
 * @author Cristina González
 * @review
 */
public class Sort {

	/**
	 * Creates a new Sort from a list of sort keys.
	 *
	 * @param  sortKeys - list of sort keys
	 * @review
	 */
	public Sort(List<SortKey> sortKeys) {
		_sortKeys = sortKeys;
	}

	/**
	 * Returns the list of sort keys.
	 *
	 * @return - the list of sort keys
	 * @review
	 */
	public List<SortKey> getSortKeys() {
		return _sortKeys;
	}

	/**
	 * Models a Sort Key.
	 *
	 * @review
	 */
	public static class SortKey implements Serializable {

		/**
		 * Creates a new sort key.
		 *
		 * @param  fieldName - the name of the field
		 * @param  asc - if the sort should be ascending
		 * @review
		 */
		public SortKey(String fieldName, boolean asc) {
			_fieldName = fieldName;
			_asc = asc;
		}

		/**
		 * Returns the name of the field.
		 *
		 * @return - the name of the field
		 * @review
		 */
		public String getFieldName() {
			return _fieldName;
		}

		/**
		 * Returns if the sort key is ascending or not.
		 *
		 * @return - if the sort key is ascending or not
		 * @review
		 */
		public boolean isAscending() {
			return _asc;
		}

		private final boolean _asc;
		private final String _fieldName;

	}

	private final List<SortKey> _sortKeys;

}