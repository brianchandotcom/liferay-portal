/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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