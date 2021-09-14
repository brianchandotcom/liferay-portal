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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutDisplayPageProvider.class)
public class AssetCategoryLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<AssetCategory> {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				infoItemReference.getClassPK());

		if (assetCategory == null) {
			return null;
		}

		try {
			return new AssetCategoryLayoutDisplayPageObjectProvider(
				assetCategory, _portal);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				GetterUtil.getLong(urlTitle));

		if (assetCategory == null) {
			return null;
		}

		try {
			return new AssetCategoryLayoutDisplayPageObjectProvider(
				assetCategory, _portal);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getParentLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				infoItemReference.getClassPK());

		if (assetCategory == null) {
			return null;
		}

		AssetCategory parentCategory = assetCategory.getParentCategory();

		if (parentCategory == null) {
			return null;
		}

		try {
			return new AssetCategoryLayoutDisplayPageObjectProvider(
				parentCategory, _portal);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return "/g/";
	}

	@Override
	public boolean inheritable() {
		return true;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private Portal _portal;

}