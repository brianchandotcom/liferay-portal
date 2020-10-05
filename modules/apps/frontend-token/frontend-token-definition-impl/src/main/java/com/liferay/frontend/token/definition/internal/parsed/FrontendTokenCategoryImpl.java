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

import com.liferay.frontend.token.definition.parsed.FrontendTokenCategory;
import com.liferay.frontend.token.definition.parsed.FrontendTokenSet;
import com.liferay.frontend.token.definition.parsed.ParsedFrontendTokenDefinition;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera
 */
public class FrontendTokenCategoryImpl implements FrontendTokenCategory {

	public FrontendTokenCategoryImpl(
		ParsedFrontendTokenDefinition parsedFrontendTokenDefinition,
		JSONObject frontendTokenCategoryJSONObject) {

		_parsedFrontendTokenDefinition = parsedFrontendTokenDefinition;

		JSONArray frontendTokenSetsJSONArray =
			frontendTokenCategoryJSONObject.getJSONArray("frontendTokenSets");

		if (frontendTokenSetsJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokenSetsJSONArray.length(); i++) {
			_frontendTokenSets.add(
				new FrontendTokenSetImpl(
					this, frontendTokenSetsJSONArray.getJSONObject(i)));
		}
	}

	@Override
	public Collection<FrontendTokenSet> getFrontendTokenSets() {
		return _frontendTokenSets;
	}

	private Collection<FrontendTokenSet> _frontendTokenSets = new ArrayList<>();
	private final ParsedFrontendTokenDefinition _parsedFrontendTokenDefinition;

}