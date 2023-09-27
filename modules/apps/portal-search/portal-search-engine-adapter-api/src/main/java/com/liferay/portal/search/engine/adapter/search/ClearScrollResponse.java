/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

/**
 * @author Gustavo Lima
 */
public class ClearScrollResponse implements SearchResponse {

	public ClearScrollResponse(int numFreed) {
		_numFreed = numFreed;
	}

	public int getNumFreed() {
		return _numFreed;
	}

	private final int _numFreed;

}