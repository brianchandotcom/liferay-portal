/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public String getClassName() {
		return AssetCategory.class.getName();
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