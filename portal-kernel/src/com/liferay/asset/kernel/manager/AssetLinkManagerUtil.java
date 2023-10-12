/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Eudaldo Alonso
 */
public class AssetLinkManagerUtil {

	public static long[] getDirectLinksIds(long entryId) {
		AssetLinkManager assetLinkManager = _assetLinkManagerSnapshot.get();

		return assetLinkManager.getDirectLinksIds(entryId);
	}

	public static long[] getRelatedDirectLinksIds(long entryId) {
		AssetLinkManager assetLinkManager = _assetLinkManagerSnapshot.get();

		return assetLinkManager.getRelatedDirectLinksIds(entryId);
	}

	public static void updateLinks(
			long[] assetLinkEntryIds, long entryId, long userId)
		throws PortalException {

		AssetLinkManager assetLinkManager = _assetLinkManagerSnapshot.get();

		assetLinkManager.updateLinks(assetLinkEntryIds, entryId, userId);
	}

	private static final Snapshot<AssetLinkManager> _assetLinkManagerSnapshot =
		new Snapshot<>(AssetLinkManagerUtil.class, AssetLinkManager.class);

}