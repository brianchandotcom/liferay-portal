/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

/**
 * @author Felipe Lorenz
 */
public class StatsIndexResponse implements IndexResponse {

	public StatsIndexResponse(Long totalSpace) {
		_totalSpace = totalSpace;
	}

	public Long getTotalSpace() {
		return _totalSpace;
	}

	private final Long _totalSpace;

}