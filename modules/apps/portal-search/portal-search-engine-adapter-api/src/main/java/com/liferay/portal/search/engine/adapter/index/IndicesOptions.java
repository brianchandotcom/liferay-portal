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

package com.liferay.portal.search.engine.adapter.index;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class IndicesOptions {

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		IndicesOptions indicesOptions = (IndicesOptions)object;

		if (indicesOptions.isAllowNoIndices() != isAllowNoIndices()) {
			return false;
		}

		if (indicesOptions.isExpandToClosedIndices() !=
				isExpandToClosedIndices()) {

			return false;
		}

		if (indicesOptions.isExpandToOpenIndices() != isExpandToOpenIndices()) {
			return false;
		}

		if (indicesOptions.isIgnoreUnavailable() != isIgnoreUnavailable()) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_allowNoIndices, _expandToClosedIndices, _expandToOpenIndices,
			_ignoreUnavailable);
	}

	public boolean isAllowNoIndices() {
		return _allowNoIndices;
	}

	public boolean isExpandToClosedIndices() {
		return _expandToClosedIndices;
	}

	public boolean isExpandToOpenIndices() {
		return _expandToOpenIndices;
	}

	public boolean isIgnoreUnavailable() {
		return _ignoreUnavailable;
	}

	public void setAllowNoIndices(boolean allowNoIndices) {
		_allowNoIndices = allowNoIndices;
	}

	public void setExpandToClosedIndices(boolean expandToClosedIndices) {
		_expandToClosedIndices = expandToClosedIndices;
	}

	public void setExpandToOpenIndices(boolean expandToOpenIndices) {
		_expandToOpenIndices = expandToOpenIndices;
	}

	public void setIgnoreUnavailable(boolean ignoreUnavailable) {
		_ignoreUnavailable = ignoreUnavailable;
	}

	private boolean _allowNoIndices;
	private boolean _expandToClosedIndices;
	private boolean _expandToOpenIndices;
	private boolean _ignoreUnavailable;

}