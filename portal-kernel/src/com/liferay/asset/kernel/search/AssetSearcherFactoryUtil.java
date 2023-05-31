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

package com.liferay.asset.kernel.search;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetSearcherFactoryUtil {

	public static BaseSearcher createBaseSearcher(
		AssetEntryQuery assetEntryQuery) {

		return _assetSearcherFactory.createBaseSearcher(assetEntryQuery);
	}

	private static volatile AssetSearcherFactory _assetSearcherFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			AssetSearcherFactory.class, AssetSearcherFactoryUtil.class,
			"_assetSearcherFactory", true);

}