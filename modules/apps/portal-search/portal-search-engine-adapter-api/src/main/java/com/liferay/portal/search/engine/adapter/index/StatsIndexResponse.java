/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import java.util.Map;

/**
 * @author Felipe Lorenz
 */
public class StatsIndexResponse implements IndexResponse {

	public StatsIndexResponse(
		Map<String, Long> indexSizes, long sizeOfLargestIndex) {

		_indexSizes = indexSizes;
		_sizeOfLargestIndex = sizeOfLargestIndex;
	}

	public long getIndexSizeInBytes(String indexName) {
		return _indexSizes.get(indexName);
	}

	public long getSizeOfLargestIndexInBytes() {
		return _sizeOfLargestIndex;
	}

	private final Map<String, Long> _indexSizes;
	private final long _sizeOfLargestIndex;

}