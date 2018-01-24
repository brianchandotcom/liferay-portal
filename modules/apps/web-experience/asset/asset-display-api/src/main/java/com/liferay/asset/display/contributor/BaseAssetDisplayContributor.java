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
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.LinkedHashSet;
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
		Set<AssetDisplayField> assetDisplayFields = new LinkedHashSet<>();

		String[] assetEntryModelFields = getAssetEntryModelFields();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		for (String assetEntryModelField : assetEntryModelFields) {
			assetDisplayFields.add(
				new AssetDisplayField(
					assetEntryModelField,
					LanguageUtil.get(resourceBundle, assetEntryModelField)));
		}

		return assetDisplayFields;
	}

	protected abstract String[] getAssetEntryModelFields();

	protected ResourceBundleLoader resourceBundleLoader;

}