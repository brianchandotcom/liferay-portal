/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.info.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = InfoItemDetailsProvider.class)
public class AssetCategoryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<AssetCategory> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(AssetCategory.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(AssetCategory assetCategory) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				AssetCategory.class.getName(), assetCategory.getCategoryId()));
	}

}