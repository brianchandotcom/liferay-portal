/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

/**
 * @author Bryan Engler
 */
public class OpenPointInTimeResponse implements SearchResponse {

	public OpenPointInTimeResponse(String pointInTimeId) {
		_pointInTimeId = pointInTimeId;
	}

	public String pitId() {
		return _pointInTimeId;
	}

	private final String _pointInTimeId;

}