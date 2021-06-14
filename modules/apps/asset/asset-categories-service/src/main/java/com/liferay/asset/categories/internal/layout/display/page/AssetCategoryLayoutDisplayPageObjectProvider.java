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

package com.liferay.asset.categories.internal.layout.display.page;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class AssetCategoryLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<AssetCategory> {

	public AssetCategoryLayoutDisplayPageObjectProvider(
			AssetCategory assetCategory, Portal portal)
		throws PortalException {

		_assetCategory = assetCategory;
		_portal = portal;
	}

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(AssetCategory.class.getName());
	}

	@Override
	public long getClassPK() {
		return _assetCategory.getCategoryId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetCategory.getDescription(locale);
	}

	@Override
	public AssetCategory getDisplayObject() {
		return _assetCategory;
	}

	@Override
	public long getGroupId() {
		return _assetCategory.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		return String.valueOf(_assetCategory.getCategoryId());
	}

	private final AssetCategory _assetCategory;
	private final Portal _portal;

}