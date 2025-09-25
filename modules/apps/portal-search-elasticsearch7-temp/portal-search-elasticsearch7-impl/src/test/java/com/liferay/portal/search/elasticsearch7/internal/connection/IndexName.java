/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author André de Oliveira
 */
public class IndexName {

	public IndexName(String name) {
		_name = StringUtil.toLowerCase(name);
	}

	public String getName() {
		return _name;
	}

	private final String _name;

}