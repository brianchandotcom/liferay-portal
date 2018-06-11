/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.data.source;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 */
public class CPDataSourceResult implements Serializable {

	public CPDataSourceResult(List<CPCatalogEntry> results, int length) {
		if (results == null) {
			_results = Collections.emptyList();
		}
		else {
			_results = results;
		}

		_length = length;
	}

	public int getLength() {
		return _length;
	}

	public List<CPCatalogEntry> getResult() {
		return _results;
	}

	@Override
	public String toString() {
		if (_results.isEmpty()) {
			return "{data={}, length=".concat(
				String.valueOf(_length)).concat(StringPool.CLOSE_BRACKET);
		}

		StringBundler sb = new StringBundler(2 * _results.size() + 3);

		sb.append("{data={");

		for (CPCatalogEntry entry : _results) {
			sb.append(entry);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setStringAt(StringPool.CLOSE_BRACKET, sb.index() - 1);

		sb.append(", length=");
		sb.append(_length);
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private final int _length;
	private final List<CPCatalogEntry> _results;

}