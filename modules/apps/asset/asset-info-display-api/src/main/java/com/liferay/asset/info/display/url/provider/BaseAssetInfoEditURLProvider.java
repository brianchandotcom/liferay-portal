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

package com.liferay.asset.info.display.url.provider;

import com.liferay.asset.info.display.url.provider.util.AssetInfoEditURLProviderUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.petra.string.StringPool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Mueller (7.2.x), in favour of {@link
 *             InfoEditURLProvider}
 */
@Deprecated
public class BaseAssetInfoEditURLProvider
	implements InfoEditURLProvider<AssetEntry> {

	@Override
	public String getURL(
			AssetEntry assetEntry, HttpServletRequest httpServletRequest)
		throws Exception {

		if (assetEntry == null) {
			return StringPool.BLANK;
		}

		return AssetInfoEditURLProviderUtil.getURL(
			assetEntry.getClassName(), assetEntry.getClassPK(),
			httpServletRequest);
	}

}