/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.search;

import com.liferay.asset.kernel.search.AssetSearcherFactory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.search.BaseSearcher;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AssetSearcherFactory.class)
public class AssetSearcherFactoryImpl implements AssetSearcherFactory {

	@Override
	public BaseSearcher createBaseSearcher(AssetEntryQuery assetEntryQuery) {
		AssetSearcher assetSearcher =
			(AssetSearcher)AssetSearcher.getInstance();

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		return assetSearcher;
	}

}