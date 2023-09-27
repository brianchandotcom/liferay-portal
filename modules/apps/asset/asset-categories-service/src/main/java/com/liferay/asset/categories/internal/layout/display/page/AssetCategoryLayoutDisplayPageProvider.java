/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.layout.display.page;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
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

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			_getClassPKInfoItemIdentifier(infoItemReference);

		if (classPKInfoItemIdentifier == null) {
			return null;
		}

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				classPKInfoItemIdentifier.getClassPK());

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

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			_getClassPKInfoItemIdentifier(infoItemReference);

		if (classPKInfoItemIdentifier == null) {
			return null;
		}

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				classPKInfoItemIdentifier.getClassPK());

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
		return FriendlyURLResolverConstants.URL_SEPARATOR_ASSET_CATEGORY;
	}

	@Override
	public boolean inheritable() {
		return true;
	}

	private ClassPKInfoItemIdentifier _getClassPKInfoItemIdentifier(
		InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		return (ClassPKInfoItemIdentifier)
			infoItemReference.getInfoItemIdentifier();
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private Portal _portal;

}