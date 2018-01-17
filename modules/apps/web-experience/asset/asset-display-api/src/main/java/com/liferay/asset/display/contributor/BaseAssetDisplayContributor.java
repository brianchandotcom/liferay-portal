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

package com.liferay.asset.display.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetDisplayContributor
	implements AssetDisplayContributor {

	@Override
	public Set<AssetDisplayField> getAssetEntryFields(Locale locale) {
		Set<AssetDisplayField> assetDisplayFields = new HashSet<>();

		String[] assetEntryFields = _BASIC_ASSET_ENTRY_FIELDS;
		String[] extendedAssetEntryFields = getExtendedAssetEntryFields();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		if (ArrayUtil.isNotEmpty(extendedAssetEntryFields)) {
			assetEntryFields = ArrayUtil.append(
				assetEntryFields, extendedAssetEntryFields);
		}

		for (String assetDisplayField : assetEntryFields) {
			assetDisplayFields.add(
				new AssetDisplayField(
					assetDisplayField,
					LanguageUtil.get(resourceBundle, assetDisplayField)));
		}

		return assetDisplayFields;
	}

	protected String[] getExtendedAssetEntryFields() {
		return null;
	}

	protected ResourceBundleLoader resourceBundleLoader;

	private static final String[] _BASIC_ASSET_ENTRY_FIELDS =
		{"title", "userName"};

}