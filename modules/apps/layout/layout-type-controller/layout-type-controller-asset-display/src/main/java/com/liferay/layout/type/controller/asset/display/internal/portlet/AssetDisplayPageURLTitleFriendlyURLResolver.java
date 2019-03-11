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
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayPageFriendlyURLResolverConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = FriendlyURLResolver.class)
public class AssetDisplayPageURLTitleFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getURLSeparator() {
		return AssetDisplayPageFriendlyURLResolverConstants.
			ASSET_DISPLAY_PAGE_URL_TITLE_URL_SEPARATOR;
	}

	@Override
	protected AssetDisplayContributor getAssetDisplayContributor(
			long groupId, String friendlyURL)
		throws PortalException {

		String friendlyURLShortcut = _getFriendlyURLShortcut(friendlyURL);

		AssetDisplayContributor assetDisplayContributor =
			assetDisplayContributorTracker.
				getAssetDisplayContributorByFriendlyURLShortcut(
					friendlyURLShortcut);

		if (assetDisplayContributor == null) {
			throw new PortalException(
				"Display page not available for " + friendlyURLShortcut);
		}

		return assetDisplayContributor;
	}

	@Override
	protected AssetEntry getAssetEntry(long groupId, String friendlyURL)
		throws PortalException {

		AssetDisplayContributor assetDisplayContributor =
			getAssetDisplayContributor(groupId, friendlyURL);

		String className = assetDisplayContributor.getClassName();

		long classNameId = portal.getClassNameId(className);

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, classNameId, _getUrlTitle(friendlyURL));

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		return assetRendererFactory.getAssetEntry(
			className, friendlyURLEntry.getClassPK());
	}

	private String _getFriendlyURLShortcut(String friendlyURL) {
		String urlSeparator = getURLSeparator();

		return friendlyURL.substring(
			urlSeparator.length(), friendlyURL.lastIndexOf(StringPool.SLASH));
	}

	private String _getUrlTitle(String friendlyURL) {
		return friendlyURL.substring(
			friendlyURL.lastIndexOf(StringPool.SLASH) + 1);
	}

}