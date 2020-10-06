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
import com.liferay.frontend.token.definition.internal.util.CachedJSONTranslator;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class FrontendTokenImpl implements FrontendToken {

	public FrontendTokenImpl(
		FrontendTokenSetImpl frontendTokenSetImpl, JSONObject jsonObject) {

		_frontendTokenSetImpl = frontendTokenSetImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenSetImpl.getFrontendTokenDefinition();

		_cachedJSONTranslator =
			frontendTokenDefinitionImpl.createCachedJSONTranslator(jsonObject);

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
				"Unsupported frontend token type " + _type.toString());
		}

		JSONArray mappingsJSONArray = jsonObject.getJSONArray("mappings");

		if (mappingsJSONArray == null) {
			return;
		}

		for (int i = 0; i < mappingsJSONArray.length(); i++) {
			_frontendTokenMappings.add(
				new FrontendTokenMappingImpl(
					this, mappingsJSONArray.getJSONObject(i)));
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
	public FrontendTokenSet getFrontendTokenSet() {
		return _frontendTokenSetImpl;
	}

	@Override
	public String getJSON(Locale locale) {
		return _cachedJSONTranslator.getJSON(locale);
	}

	@Override
	public Type getType() {
		return _type;
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenSetImpl.getFrontendTokenDefinition();
	}

	private final CachedJSONTranslator _cachedJSONTranslator;
	private final Object _defaultValue;
	private Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final FrontendTokenSetImpl _frontendTokenSetImpl;
	private final Type _type;

}