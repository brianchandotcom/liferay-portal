/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.util.comparator;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.security.permission.comparator.ModelResourceComparator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Bryan Engler
 */
public class IndexerClassNameModelResourceComparator
	implements Comparator<Indexer<?>> {

	public IndexerClassNameModelResourceComparator(
		boolean ascending, Locale locale) {

		_ascending = ascending;
		_locale = locale;
	}

	@Override
	public int compare(Indexer<?> indexer1, Indexer<?> indexer2) {
		String className1 = indexer1.getClassName();
		String className2 = indexer2.getClassName();

		Comparator<String> comparator = new ModelResourceComparator(_locale);

		int value = comparator.compare(className1, className2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final Locale _locale;

}