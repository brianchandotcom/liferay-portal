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
import com.liferay.frontend.token.definition.parsed.ParsedFrontendTokenDefinition;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class ParsedFrontendTokenDefinitionImpl
	implements ParsedFrontendTokenDefinition {

	public ParsedFrontendTokenDefinitionImpl(
		Locale locale, JSONObject frontendTokenDefinitionJSONObject) {

		_locale = locale;

		JSONArray frontendTokenCategoriesJSONArray =
			frontendTokenDefinitionJSONObject.getJSONArray(
				"frontendTokenCategories");

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			_frontendTokenCategories.add(
				new FrontendTokenCategoryImpl(
					this, frontendTokenCategoriesJSONArray.getJSONObject(i)));
		}
	}

	@Override
	public Collection<FrontendTokenCategory> getFrontendTokenCategories() {
		return _frontendTokenCategories;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	private Collection<FrontendTokenCategory> _frontendTokenCategories =
		new ArrayList<>();
	private final Locale _locale;

}