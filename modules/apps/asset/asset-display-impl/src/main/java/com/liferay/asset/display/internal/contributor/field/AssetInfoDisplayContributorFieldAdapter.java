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

package com.liferay.asset.display.internal.contributor.field;

import com.liferay.asset.display.contributor.AssetDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class AssetInfoDisplayContributorFieldAdapter
	implements InfoDisplayContributorField {

	public AssetInfoDisplayContributorFieldAdapter(
		AssetDisplayContributorField assetDisplayContributorField) {

		_assetDisplayContributorField = assetDisplayContributorField;
	}

	@Override
	public String getKey() {
		return _assetDisplayContributorField.getKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return _assetDisplayContributorField.getLabel(locale);
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.parse(
			_assetDisplayContributorField.getType());
	}

	@Override
	public Object getValue(Object model, Locale locale) {
		return _assetDisplayContributorField.getValue(model, locale);
	}

	private final AssetDisplayContributorField _assetDisplayContributorField;

}