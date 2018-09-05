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

package com.liferay.portal.kernel.util;

/**
 * @author Sampsa Sohlman
 */
public class PrefixPredicateFilter implements PredicateFilter<String> {

	public PrefixPredicateFilter(String prefix) {
		this(prefix, false);
	}

	public PrefixPredicateFilter(String prefix, boolean include) {
		_prefix = prefix;
		_include = include;
	}

	@Override
	public boolean filter(String string) {
		if (_include) {
			return string.startsWith(_prefix);
		}

		return !string.startsWith(_prefix);
	}

	private final boolean _include;
	private final String _prefix;

}