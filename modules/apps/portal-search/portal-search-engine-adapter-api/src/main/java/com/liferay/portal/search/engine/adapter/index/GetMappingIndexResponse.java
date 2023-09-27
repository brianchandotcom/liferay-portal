/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class GetMappingIndexResponse implements IndexResponse {

	public GetMappingIndexResponse(Map<String, String> indexMappings) {
		_indexMappings = indexMappings;
	}

	public Map<String, String> getIndexMappings() {
		return _indexMappings;
	}

	private final Map<String, String> _indexMappings;

}