/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Eudaldo Alonso
 */
public class AssetLinkManagerUtil {

	public static long[] getDirectLinksIds(long entryId) {
		return _assetLinkManager.getDirectLinksIds(entryId);
	}

	public static long[] getRelatedDirectLinksIds(long entryId) {
		return _assetLinkManager.getRelatedDirectLinksIds(entryId);
	}

	public static void updateLinks(
			long[] assetLinkEntryIds, long entryId, long userId)
		throws PortalException {

		_assetLinkManager.updateLinks(assetLinkEntryIds, entryId, userId);
	}

	private static volatile AssetLinkManager _assetLinkManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			AssetLinkManager.class, AssetLinkManagerUtil.class,
			"_assetLinkManager", false);

}