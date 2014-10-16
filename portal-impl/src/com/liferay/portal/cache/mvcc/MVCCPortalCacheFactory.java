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

package com.liferay.portal.cache.mvcc;

import com.liferay.portal.kernel.cache.LowLevelCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheWrapper;
import com.liferay.portal.model.MVCCModel;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class MVCCPortalCacheFactory {

	public static <K extends Serializable>
		PortalCache<K, ?> createMVCCEhcachePortalCache(
			PortalCache<K, ?> portalCache) {

		if (portalCache instanceof LowLevelCache) {
			return new MVCCPortalCache<K, MVCCModel>(
				(LowLevelCache<K, MVCCModel>)portalCache);
		}

		PortalCache<K, ?> currentPortalCache = portalCache;

		while (currentPortalCache instanceof PortalCacheWrapper) {
			PortalCacheWrapper<K, MVCCModel> portalCacheWrapper =
				(PortalCacheWrapper<K, MVCCModel>)currentPortalCache;

			PortalCache<K, MVCCModel> nextPortalCache =
				portalCacheWrapper.getWrappedPortalCache();

			if (nextPortalCache instanceof LowLevelCache) {
				nextPortalCache = new MVCCPortalCache<K, MVCCModel>(
					(LowLevelCache<K, MVCCModel>)nextPortalCache);

				portalCacheWrapper.setPortalCache(nextPortalCache);

				break;
			}
			else {
				currentPortalCache = nextPortalCache;
			}
		}

		return portalCache;
	}

}