/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.internal.manager;

import com.liferay.asset.kernel.manager.AssetLinkManager;
import com.liferay.asset.link.constants.AssetLinkConstants;
import com.liferay.asset.link.model.AssetLink;
import com.liferay.asset.link.service.AssetLinkLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = AssetLinkManager.class)
public class AssetLinkManagerImpl implements AssetLinkManager {

	@Override
	public long[] getDirectLinksIds(long entryId) {
		List<AssetLink> assetLinks = _assetLinkLocalService.getDirectLinks(
			entryId, false);

		return ListUtil.toLongArray(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);
	}

	@Override
	public long[] getRelatedDirectLinksIds(long entryId) {
		List<AssetLink> assetLinks = _assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_RELATED, false);

		return ListUtil.toLongArray(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);
	}

	@Override
	public void updateLinks(long[] assetLinkEntryIds, long entryId, long userId)
		throws PortalException {

		_assetLinkLocalService.updateLinks(
			userId, entryId, assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

}