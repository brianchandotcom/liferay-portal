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

import java.util.List;

/**
 * Models a Sort Query.
 *
 * @author Cristina González
 * @review
 */
public class SortQuery {

	/**
	 * Creates a new sort query from a list of sort query params.
	 *
	 * @param  sortQueryParts - list of sort query part
	 * @review
	 */
	public SortQuery(List<SortQueryPart> sortQueryParts) {
		_sortQueryParts = sortQueryParts;
	}

	/**
	 * Returns the list of sort query parts.
	 *
	 * @return - the list of sort query parts
	 * @review
	 */
	public List<SortQueryPart> getSortQueryParts() {
		return _sortQueryParts;
	}

	/**
	 * Models a Sort Query part.
	 *
	 * @review
	 */
	public static class SortQueryPart {

		/**
		 * Creates a new sort query part.
		 *
		 * @param  fieldName - the name of the field
		 * @param  asc - if the sort directive is ascending
		 * @review
		 */
		public SortQueryPart(String fieldName, boolean asc) {
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
		 * Returns if the sort query part is ascending or not.
		 *
		 * @return - if the sort query part is ascending or not
		 * @review
		 */
		public boolean isAscending() {
			return _asc;
		}

		private final boolean _asc;
		private final String _fieldName;

	}

	private final List<SortQueryPart> _sortQueryParts;

}