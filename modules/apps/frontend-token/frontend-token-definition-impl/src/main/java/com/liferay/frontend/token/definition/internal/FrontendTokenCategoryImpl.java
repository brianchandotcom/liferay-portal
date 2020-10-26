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
import com.liferay.frontend.token.definition.FrontendTokenCategory;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.frontend.token.definition.internal.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class FrontendTokenCategoryImpl implements FrontendTokenCategory {

	public FrontendTokenCategoryImpl(
		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl,
		JSONObject jsonObject) {

		_frontendTokenDefinitionImpl = frontendTokenDefinitionImpl;

		_jsonLocalizer = frontendTokenDefinitionImpl.createCachedJSONTranslator(
			jsonObject);

		JSONArray frontendTokenSetsJSONArray = jsonObject.getJSONArray(
			"frontendTokenSets");

		if (frontendTokenSetsJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokenSetsJSONArray.length(); i++) {
			FrontendTokenSet frontendTokenSet = new FrontendTokenSetImpl(
				this, frontendTokenSetsJSONArray.getJSONObject(i));

			_frontendTokenSets.add(frontendTokenSet);

			_frontendTokens.addAll(frontendTokenSet.getFrontendTokens());

			_frontendTokenMappings.addAll(
				frontendTokenSet.getFrontendTokenMappings());
		}
	}

	@Override
	public FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenDefinitionImpl;
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
	public Collection<FrontendTokenSet> getFrontendTokenSets() {
		return _frontendTokenSets;
	}

	@Override
	public String getJSON(Locale locale) {
		return _jsonLocalizer.getJSON(locale);
	}

	private final FrontendTokenDefinitionImpl _frontendTokenDefinitionImpl;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Collection<FrontendToken> _frontendTokens = new ArrayList<>();
	private final Collection<FrontendTokenSet> _frontendTokenSets =
		new ArrayList<>();
	private final JSONLocalizer _jsonLocalizer;

}