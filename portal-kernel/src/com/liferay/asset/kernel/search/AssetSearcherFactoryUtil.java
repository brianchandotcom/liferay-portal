/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.search;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.BaseSearcher;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetSearcherFactoryUtil {

	public static BaseSearcher createBaseSearcher(
		AssetEntryQuery assetEntryQuery) {

		AssetSearcherFactory assetSearcherFactory =
			_assetSearcherFactorySnapshot.get();

		return assetSearcherFactory.createBaseSearcher(assetEntryQuery);
	}

	private static final Snapshot<AssetSearcherFactory>
		_assetSearcherFactorySnapshot = new Snapshot<>(
			AssetSearcherFactoryUtil.class, AssetSearcherFactory.class);

}