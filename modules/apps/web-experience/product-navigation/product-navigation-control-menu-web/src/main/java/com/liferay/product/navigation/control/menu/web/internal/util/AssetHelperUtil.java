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

package com.liferay.product.navigation.control.menu.web.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.portal.kernel.search.BaseModelSearchResult;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class AssetHelperUtil {

	public static BaseModelSearchResult<AssetEntry> searchAssetEntries(
			HttpServletRequest request, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		return _assetHelper.searchAssetEntries(
			request, assetEntryQuery, start, end);
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	private static AssetHelper _assetHelper;

}