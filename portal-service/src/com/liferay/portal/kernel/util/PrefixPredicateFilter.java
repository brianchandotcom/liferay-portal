/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

/**
 * @author Sampsa Sohlman
 */
public class PrefixPredicateFilter implements PredicateFilter<String> {

	/**
	 * @param prefix prefix to be filtered in our out
	 * @param isFilterOut true to filter out false to filter in
	 */
	public PrefixPredicateFilter(String prefix, boolean isFilterOut) {
		_prefix = prefix;
		_isFilterOut = isFilterOut;
	}

	@Override
	public boolean filter(String t) {
		if (_isFilterOut) {
			return !t.startsWith(_prefix);
		}
		else {
			return t.startsWith(_prefix);
		}
	}

	private boolean _isFilterOut;
	private String _prefix;

}