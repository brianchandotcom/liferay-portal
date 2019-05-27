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

package com.liferay.asset.info.display.internal.contributor.field;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = InfoDisplayContributorField.class
)
public class AssetEntryCategoriesInfoDisplayContributorField
	implements InfoDisplayContributorField<AssetEntry> {

	@Override
	public String getKey() {
		return "categories";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "categories");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.TEXT;
	}

	@Override
	public String getValue(AssetEntry assetEntry, Locale locale) {
		return ListUtil.toString(
			assetEntry.getCategories(),
			new Accessor<AssetCategory, String>() {

				@Override
				public String get(AssetCategory assetCategory) {
					String title = assetCategory.getTitle(locale);

					if (Validator.isNull(title)) {
						return assetCategory.getName();
					}

					return title;
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<AssetCategory> getTypeClass() {
					return AssetCategory.class;
				}

			});
	}

}