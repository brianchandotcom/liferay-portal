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
import com.liferay.frontend.token.definition.parsed.FrontendTokenCategory;
import com.liferay.frontend.token.definition.parsed.FrontendTokenSet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera
 */
public class FrontendTokenSetImpl implements FrontendTokenSet {

	public FrontendTokenSetImpl(
		FrontendTokenCategory frontendTokenCategory,
		JSONObject frontendTokenSetJSONObject) {

		_frontendTokenCategory = frontendTokenCategory;

		JSONArray frontendTokensJSONArray =
			frontendTokenSetJSONObject.getJSONArray("frontendTokens");

		if (frontendTokensJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokensJSONArray.length(); i++) {
			_frontendTokens.add(
				new FrontendTokenImpl(
					this, frontendTokensJSONArray.getJSONObject(i)));
		}
	}

	@Override
	public Collection<FrontendToken> getFrontendTokens() {
		return _frontendTokens;
	}

	private final FrontendTokenCategory _frontendTokenCategory;
	private Collection<FrontendToken> _frontendTokens = new ArrayList<>();

}