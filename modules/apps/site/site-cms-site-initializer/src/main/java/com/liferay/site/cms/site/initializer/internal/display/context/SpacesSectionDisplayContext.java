/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public class SpacesSectionDisplayContext {

	public SpacesSectionDisplayContext(
		AssetLibraryResource.Factory assetLibraryResourceFactory,
		HttpServletRequest httpServletRequest) {

		_assetLibraryResourceFactory = assetLibraryResourceFactory;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Page<AssetLibrary> getPage() throws Exception {
		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		AssetLibraryResource assetLibraryResource = builder.user(
			_themeDisplay.getUser()
		).build();

		Page<AssetLibrary> assetLibrariesPage =
			assetLibraryResource.getAssetLibrariesPage(
				null, null, null, Pagination.of(1, 5), null);

		return Page.of(
			assetLibrariesPage.getActions(),
			_getAssetLibraries(
				assetLibrariesPage,
				assetLibraryResource.getAssetLibrariesPinnedByMePage(
					Pagination.of(1, 5))),
			Pagination.of(1, 5), assetLibrariesPage.getTotalCount());
	}

	private Collection<AssetLibrary> _getAssetLibraries(
		Page<AssetLibrary> assetLibrariesPage,
		Page<AssetLibrary> pinnedByMeAssetLibrariesPage) {

		if (assetLibrariesPage.getTotalCount() == 0) {
			Collections.emptyList();
		}

		if (pinnedByMeAssetLibrariesPage.getTotalCount() == 5) {
			return pinnedByMeAssetLibrariesPage.getItems();
		}

		List<AssetLibrary> assetLibraries = new ArrayList<>(
			pinnedByMeAssetLibrariesPage.getItems());

		List<Long> assetLibraryIds = ListUtil.toList(
			assetLibraries, assetLibrary -> assetLibrary.getId());

		for (AssetLibrary assetLibrary : assetLibrariesPage.getItems()) {
			if (!assetLibraryIds.contains(assetLibrary.getId())) {
				assetLibraries.add(assetLibrary);
			}

			if (assetLibraries.size() == 5) {
				return assetLibraries;
			}
		}

		return assetLibraries;
	}

	private final AssetLibraryResource.Factory _assetLibraryResourceFactory;
	private final ThemeDisplay _themeDisplay;

}