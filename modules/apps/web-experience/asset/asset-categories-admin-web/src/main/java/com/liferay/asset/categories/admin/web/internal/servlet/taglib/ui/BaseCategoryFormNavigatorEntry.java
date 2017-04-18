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

package com.liferay.asset.categories.admin.web.internal.servlet.taglib.ui;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesConstants;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseCategoryFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<AssetCategory> {

	@Override
	public String getCategoryKey() {
		return AssetCategoriesConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return AssetCategoriesConstants.FORM_NAVIGATOR_ID_EDIT_CATEGORY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

}