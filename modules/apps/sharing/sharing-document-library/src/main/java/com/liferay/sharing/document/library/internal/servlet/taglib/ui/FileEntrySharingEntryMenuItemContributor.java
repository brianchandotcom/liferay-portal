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

package com.liferay.sharing.document.library.internal.servlet.taglib.ui;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.servlet.taglib.ui.SharingEntryMenuItemContributor;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
	},
	service = SharingEntryMenuItemContributor.class
)
public class FileEntrySharingEntryMenuItemContributor
	implements SharingEntryMenuItemContributor {

	@Override
	public Collection<MenuItem> getSharingEntryMenuItems(
		SharingEntry sharingEntry, ThemeDisplay themeDisplay) {

		try {
			if (!_isVisible(sharingEntry)) {
				return Collections.emptyList();
			}

			return Collections.singleton(
				_createDownloadMenuItem(sharingEntry, themeDisplay));
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return Collections.emptyList();
		}
	}

	private URLMenuItem _createDownloadMenuItem(
			SharingEntry sharingEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("download");
		urlMenuItem.setLabel(
			LanguageUtil.get(themeDisplay.getLocale(), "download"));

		AssetRenderer assetRenderer = _getAssetEntryRenderer(sharingEntry);

		urlMenuItem.setURL(assetRenderer.getURLDownload(themeDisplay));

		return urlMenuItem;
	}

	private AssetRenderer _getAssetEntryRenderer(SharingEntry sharingEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					sharingEntry.getClassNameId());

		return assetRendererFactory.getAssetRenderer(sharingEntry.getClassPK());
	}

	private boolean _isVisible(SharingEntry sharingEntry) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			sharingEntry.getClassNameId(), sharingEntry.getClassPK());

		if ((assetEntry != null) && assetEntry.isVisible()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntrySharingEntryMenuItemContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private SharingPermission _sharingPermission;

}