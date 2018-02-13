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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.string.StringPool;

/**
 * @author Adolfo Pérez
 */
public enum WildcardMode {

	LEADING(StringPool.PERCENT, StringPool.BLANK),
	SURROUND(StringPool.PERCENT, StringPool.PERCENT),
	TRAILING(StringPool.BLANK, StringPool.PERCENT);

	public String getLeadingWildcard() {
		return _leadingWildcard;
	}

	public String getTrailingWildcard() {
		return _trailingWildcard;
	}

	private WildcardMode(String leadingWildcard, String trailingWildcard) {
		_leadingWildcard = leadingWildcard;
		_trailingWildcard = trailingWildcard;
	}

	private final String _leadingWildcard;
	private final String _trailingWildcard;

}