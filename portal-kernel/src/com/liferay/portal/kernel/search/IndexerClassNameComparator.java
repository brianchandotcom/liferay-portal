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

package com.liferay.portal.kernel.search;

import java.util.Comparator;

/**
 * @author Preston Crary
 */
public class IndexerClassNameComparator implements Comparator<Indexer<?>> {

	public IndexerClassNameComparator() {
		this(false);
	}

	public IndexerClassNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Indexer<?> indexer1, Indexer<?> indexer2) {
		String className1 = indexer1.getClassName();
		String className2 = indexer2.getClassName();

		int value = className1.compareTo(className2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}