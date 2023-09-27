/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

/**
 * @author Michael C. Han
 */
public class IndicesExistsIndexResponse implements IndexResponse {

	public IndicesExistsIndexResponse(boolean exists) {
		_exists = exists;
	}

	public boolean isExists() {
		return _exists;
	}

	private final boolean _exists;

}