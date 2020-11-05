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

package com.liferay.layout.internal.upgrade.v1_2_1;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author István András Dézsi
 */
public class UpgradeLayoutAsset extends UpgradeProcess {

	public UpgradeLayoutAsset(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetTagLocalService assetTagLocalService,
		LayoutLocalService layoutLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetTagLocalService = assetTagLocalService;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long[] assetCategoryIds = new long[0];

		String[] assetTagNames = new String[0];

		List<Layout> layouts = _layoutLocalService.getLayouts(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Layout layout : layouts) {
			assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
				Layout.class.getName(), layout.getPlid());

			assetTagNames = _assetTagLocalService.getTagNames(
				Layout.class.getName(), layout.getPlid());

			_layoutLocalService.updateAsset(
				layout.getUserId(), layout, assetCategoryIds, assetTagNames);
		}
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetTagLocalService _assetTagLocalService;
	private final LayoutLocalService _layoutLocalService;

}