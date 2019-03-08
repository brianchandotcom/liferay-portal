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

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayPageFriendlyURLResolverConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FriendlyURLResolver.class)
public class AssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getURLSeparator() {
		return AssetDisplayPageFriendlyURLResolverConstants.
			ASSET_DISPLAY_PAGE_URL_SEPARATOR;
	}

	@Override
	protected AssetDisplayContributor getAssetDisplayContributor(
			long groupId, String friendlyURL)
		throws PortalException {

		AssetEntry assetEntry = getAssetEntry(groupId, friendlyURL);

		AssetDisplayContributor assetDisplayContributor =
			assetDisplayContributorTracker.getAssetDisplayContributor(
				assetEntry.getClassName());

		if (assetDisplayContributor == null) {
			throw new PortalException(
				"Display page not available for className" +
					assetEntry.getClassName());
		}

		return assetDisplayContributor;
	}

	@Override
	protected AssetEntry getAssetEntry(long groupId, String friendlyURL)
		throws PortalException {

		long assetEntryId = 0;

		String urlSeparator = getURLSeparator();

		String path = friendlyURL.substring(urlSeparator.length());

		if (path.indexOf(CharPool.SLASH) != -1) {
			assetEntryId = GetterUtil.getLong(
				path.substring(0, path.indexOf(CharPool.SLASH)));
		}
		else {
			assetEntryId = GetterUtil.getLong(path);
		}

		return _assetEntryService.getEntry(assetEntryId);
	}

	@Override
	protected long getVersionClassPK(String friendlyURL) {
		long versionClassPK = 0L;

		String urlSeparator = getURLSeparator();

		String path = friendlyURL.substring(urlSeparator.length());

		if (path.indexOf(CharPool.SLASH) != -1) {
			versionClassPK = GetterUtil.getLong(
				path.substring(path.indexOf(CharPool.SLASH) + 1));
		}

		return versionClassPK;
	}

	@Reference
	private AssetEntryService _assetEntryService;

}