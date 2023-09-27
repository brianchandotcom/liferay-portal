/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.data.source;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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
		StringBundler sb = new StringBundler(
			(2 * _cpCatalogEntries.size()) + 4);

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