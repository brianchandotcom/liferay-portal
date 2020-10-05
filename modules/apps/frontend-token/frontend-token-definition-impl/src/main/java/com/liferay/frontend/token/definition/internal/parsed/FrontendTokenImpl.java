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
import com.liferay.frontend.token.definition.parsed.FrontendTokenSet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera
 */
public class FrontendTokenImpl implements FrontendToken {

	public FrontendTokenImpl(
		FrontendTokenSet frontendTokenSet, JSONObject frontendTokenJSONObject) {

		_frontendTokenSet = frontendTokenSet;

		_defaultValue = frontendTokenJSONObject.getString("defaultValue");

		JSONArray frontendTokenMappingsJSONArray =
			frontendTokenJSONObject.getJSONArray("mappings");

		if (frontendTokenMappingsJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokenMappingsJSONArray.length(); i++) {
			_frontendTokenMappings.add(
				new FrontendTokenMappingImpl(
					this, frontendTokenMappingsJSONArray.getJSONObject(i)));
		}
	}

	@Override
	public String getDefaultValue() {
		return _defaultValue;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	private final String _defaultValue;
	private Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final FrontendTokenSet _frontendTokenSet;

}