/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_jsonLocalizer = frontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

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
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenCategoryImpl.getFrontendTokenDefinition();
	}

	private final FrontendTokenCategoryImpl _frontendTokenCategoryImpl;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Collection<FrontendToken> _frontendTokens = new ArrayList<>();
	private final JSONLocalizer _jsonLocalizer;

}