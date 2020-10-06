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
import com.liferay.frontend.token.definition.internal.util.CachedJSONTranslator;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class FrontendTokenMappingImpl implements FrontendTokenMapping {

	public FrontendTokenMappingImpl(
		FrontendTokenImpl frontendTokenImpl, JSONObject jsonObject) {

		_frontendTokenImpl = frontendTokenImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenImpl.getFrontendTokenDefinition();

		_cachedJSONTranslator =
			frontendTokenDefinitionImpl.createCachedJSONTranslator(jsonObject);

		_type = jsonObject.getString("type");

		_value = jsonObject.getString("value");
	}

	@Override
	public FrontendToken getFrontendToken() {
		return _frontendTokenImpl;
	}

	@Override
	public String getJSON(Locale locale) {
		return _cachedJSONTranslator.getJSON(locale);
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final CachedJSONTranslator _cachedJSONTranslator;
	private final FrontendTokenImpl _frontendTokenImpl;
	private final String _type;
	private final String _value;

}