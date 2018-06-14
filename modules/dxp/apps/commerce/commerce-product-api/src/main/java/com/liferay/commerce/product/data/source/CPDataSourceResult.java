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

package com.liferay.commerce.product.data.source;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 */
public class CPDataSourceResult implements Serializable {

	public CPDataSourceResult(
		List<CPCatalogEntry> cpCatalogEntries, int length) {

		if (cpCatalogEntries == null) {
			_cpCatalogEntries = Collections.emptyList();
		}
		else {
			_cpCatalogEntries = cpCatalogEntries;
		}

		_length = length;
	}

	public List<CPCatalogEntry> getCPCatalogEntries() {
		return _cpCatalogEntries;
	}

	public int getLength() {
		return _length;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(2 * _cpCatalogEntries.size() + 4);

		sb.append("{data={");

		boolean first = true;

		for (CPCatalogEntry cpCatalogEntry : _cpCatalogEntries) {
			if (!first) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			first = false;

			sb.append(cpCatalogEntry);
		}

		sb.append(CharPool.CLOSE_BRACKET);

		sb.append(", length=");
		sb.append(_length);
		sb.append(CharPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private final List<CPCatalogEntry> _cpCatalogEntries;
	private final int _length;

}