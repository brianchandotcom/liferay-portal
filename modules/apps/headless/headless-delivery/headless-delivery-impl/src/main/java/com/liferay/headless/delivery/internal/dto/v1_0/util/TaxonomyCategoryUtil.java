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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategory;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class TaxonomyCategoryUtil {

	public static TaxonomyCategory toTaxonomyCategory(
		boolean acceptAllLanguages, AssetCategory assetCategory,
		Locale locale) {

		return new TaxonomyCategory() {
			{
				taxonomyCategoryId = assetCategory.getCategoryId();
				taxonomyCategoryName = assetCategory.getTitle(locale);
				taxonomyCategoryName_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, assetCategory.getTitleMap());
			}
		};
	}

}