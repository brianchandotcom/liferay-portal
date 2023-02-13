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

package com.liferay.asset.internal.search;

import com.liferay.asset.kernel.search.AssetSearcherFactory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portlet.asset.util.AssetSearcher;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AssetSearcherFactory.class)
public class AssetSearcherFactoryImpl implements AssetSearcherFactory {

	@Override
	public BaseSearcher createAssetSearcher(AssetEntryQuery assetEntryQuery) {
		AssetSearcher assetSearcher =
			(AssetSearcher)AssetSearcher.getInstance();

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		return assetSearcher;
	}

}