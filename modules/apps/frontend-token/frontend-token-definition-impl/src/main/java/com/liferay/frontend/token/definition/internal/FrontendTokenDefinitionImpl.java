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
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.frontend.token.definition.internal.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class FrontendTokenDefinitionImpl implements FrontendTokenDefinition {

	public FrontendTokenDefinitionImpl(
		JSONObject jsonObject, JSONFactory jsonFactory,
		ResourceBundleLoader resourceBundleLoader, String themeId) {

		_jsonFactory = jsonFactory;
		_resourceBundleLoader = resourceBundleLoader;
		_themeId = themeId;

		_jsonLocalizer = createCachedJSONTranslator(jsonObject);

		JSONArray frontendTokenCategoriesJSONArray = jsonObject.getJSONArray(
			"frontendTokenCategories");

		if (frontendTokenCategoriesJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			FrontendTokenCategory frontendTokenCategory =
				new FrontendTokenCategoryImpl(
					this, frontendTokenCategoriesJSONArray.getJSONObject(i));

			_frontendTokenCategories.add(frontendTokenCategory);

			_frontendTokenSets.addAll(
				frontendTokenCategory.getFrontendTokenSets());

			_frontendTokens.addAll(frontendTokenCategory.getFrontendTokens());

			_frontendTokenMappings.addAll(
				frontendTokenCategory.getFrontendTokenMappings());
		}
	}

	@Override
	public Collection<FrontendTokenCategory> getFrontendTokenCategories() {
		return _frontendTokenCategories;
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

	public String getThemeId() {
		return _themeId;
	}

	protected JSONLocalizer createCachedJSONTranslator(JSONObject jsonObject) {
		return new JSONLocalizer(
			_jsonFactory.looseSerializeDeep(jsonObject), _jsonFactory,
			_resourceBundleLoader, _themeId);
	}

	private final Collection<FrontendTokenCategory> _frontendTokenCategories =
		new ArrayList<>();
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Collection<FrontendToken> _frontendTokens = new ArrayList<>();
	private final Collection<FrontendTokenSet> _frontendTokenSets =
		new ArrayList<>();
	private final JSONFactory _jsonFactory;
	private final JSONLocalizer _jsonLocalizer;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final String _themeId;

}