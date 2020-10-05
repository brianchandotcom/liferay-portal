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

package com.liferay.frontend.token.definition.internal.parsed;

import com.liferay.frontend.token.definition.parsed.FrontendToken;
import com.liferay.frontend.token.definition.parsed.FrontendTokenMapping;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Iván Zaera
 */
public class FrontendTokenMappingImpl implements FrontendTokenMapping {

	public FrontendTokenMappingImpl(
		FrontendToken frontendToken,
		JSONObject frontendTokenMappingJSONObject) {

		_frontendToken = frontendToken;

		_type = frontendTokenMappingJSONObject.getString("type");
		_value = frontendTokenMappingJSONObject.getString("value");
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final FrontendToken _frontendToken;
	private final String _type;
	private final String _value;

}