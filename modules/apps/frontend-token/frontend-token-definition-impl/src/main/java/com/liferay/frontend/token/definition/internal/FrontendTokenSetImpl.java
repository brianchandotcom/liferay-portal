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
public class FrontendTokenSetImpl implements FrontendTokenSet {

	public FrontendTokenSetImpl(
		FrontendTokenCategoryImpl frontendTokenCategoryImpl,
		JSONObject jsonObject) {

		_frontendTokenCategoryImpl = frontendTokenCategoryImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenCategoryImpl.getFrontendTokenDefinition();

		_cachedJSONTranslator =
			frontendTokenDefinitionImpl.createCachedJSONTranslator(jsonObject);

		JSONArray frontendTokensJSONArray = jsonObject.getJSONArray(
			"frontendTokens");

		if (frontendTokensJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokensJSONArray.length(); i++) {
			FrontendToken frontendToken = new FrontendTokenImpl(
				this, frontendTokensJSONArray.getJSONObject(i));

			_frontendTokens.add(frontendToken);

			_frontendTokenMappings.addAll(
				frontendToken.getFrontendTokenMappings());
		}
	}

	@Override
	public FrontendTokenCategoryImpl getFrontendTokenCategory() {
		return _frontendTokenCategoryImpl;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	@Override
	public Collection<FrontendToken> getFrontendTokens() {
		return _frontendTokens;
	}

	@Override
	public String getJSON(Locale locale) {
		return _cachedJSONTranslator.getJSON(locale);
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenCategoryImpl.getFrontendTokenDefinition();
	}

	private final CachedJSONTranslator _cachedJSONTranslator;
	private final FrontendTokenCategoryImpl _frontendTokenCategoryImpl;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Collection<FrontendToken> _frontendTokens = new ArrayList<>();

}