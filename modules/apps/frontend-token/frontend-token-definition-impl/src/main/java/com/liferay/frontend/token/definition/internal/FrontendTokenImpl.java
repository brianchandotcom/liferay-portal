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

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.frontend.token.definition.internal.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class FrontendTokenImpl implements FrontendToken {

	public FrontendTokenImpl(
		FrontendTokenSetImpl frontendTokenSetImpl, JSONObject jsonObject) {

		_frontendTokenSetImpl = frontendTokenSetImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenSetImpl.getFrontendTokenDefinition();

		_jsonLocalizer = frontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		_name = jsonObject.getString("name");

		_type = Type.parse(jsonObject.getString("type"));

		if (_type == Type.BOOLEAN) {
			_defaultValue = jsonObject.getBoolean("defaultValue");
		}
		else if (_type == Type.INT) {
			_defaultValue = jsonObject.getInt("defaultValue");
		}
		else if (_type == Type.DOUBLE) {
			_defaultValue = jsonObject.getDouble("defaultValue");
		}
		else if (_type == Type.STRING) {
			_defaultValue = jsonObject.getString("defaultValue");
		}
		else {
			throw new RuntimeException(
				"Unsupported frontend token type " + _type);
		}

		JSONArray mappingsJSONArray = jsonObject.getJSONArray("mappings");

		if (mappingsJSONArray == null) {
			return;
		}

		for (int i = 0; i < mappingsJSONArray.length(); i++) {
			FrontendTokenMapping frontendTokenMapping =
				new FrontendTokenMappingImpl(
					this, mappingsJSONArray.getJSONObject(i));

			_frontendTokenMappings.add(frontendTokenMapping);

			List<FrontendTokenMapping> frontendTokenMappings =
				_frontendTokenMappingsMap.computeIfAbsent(
					frontendTokenMapping.getType(), type -> new ArrayList<>());

			frontendTokenMappings.add(frontendTokenMapping);
		}
	}

	@Override
	public <T> T getDefaultValue() {
		return (T)_defaultValue;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings(
		String type) {

		return _frontendTokenMappingsMap.get(type);
	}

	@Override
	public FrontendTokenSet getFrontendTokenSet() {
		return _frontendTokenSetImpl;
	}

	@Override
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Type getType() {
		return _type;
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenSetImpl.getFrontendTokenDefinition();
	}

	private final Object _defaultValue;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Map<String, List<FrontendTokenMapping>>
		_frontendTokenMappingsMap = new HashMap<>();
	private final FrontendTokenSetImpl _frontendTokenSetImpl;
	private final JSONLocalizer _jsonLocalizer;
	private final String _name;
	private final Type _type;

}