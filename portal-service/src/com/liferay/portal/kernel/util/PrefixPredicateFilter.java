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
	 * @param prefix prefix to match for filtering
	 * @param exclude true to exclude matches, false to include matches
	 */
	public PrefixPredicateFilter(String prefix, boolean exclude) {
		_prefix = prefix;
		_exclude = exclude;
	}

	@Override
	public boolean filter(String t) {
		if (_exclude) {
			return !t.startsWith(_prefix);
		}
		else {
			return t.startsWith(_prefix);
		}
	}

	private boolean _exclude;
	private String _prefix;

}