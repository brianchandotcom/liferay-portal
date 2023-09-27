/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class GetFieldMappingIndexResponse implements IndexResponse {

	public GetFieldMappingIndexResponse(Map<String, String> fieldMappings) {
		_fieldMappings = fieldMappings;
	}

	public Map<String, String> getFieldMappings() {
		return _fieldMappings;
	}

	private final Map<String, String> _fieldMappings;

}