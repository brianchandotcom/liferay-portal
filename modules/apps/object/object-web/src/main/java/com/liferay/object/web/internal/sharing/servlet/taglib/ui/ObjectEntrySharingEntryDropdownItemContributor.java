/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.sharing.servlet.taglib.ui;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.servlet.taglib.ui.SharingEntryDropdownItemContributor;

import java.util.Collections;
import java.util.List;

/**
 * @author Mikel Lorza
 */
public class ObjectEntrySharingEntryDropdownItemContributor
	implements SharingEntryDropdownItemContributor {

	public ObjectEntrySharingEntryDropdownItemContributor(
		AssetEntryLocalService assetEntryLocalService, Language language) {

		_assetEntryLocalService = assetEntryLocalService;
		_language = language;
	}

	@Override
	public List<DropdownItem> getSharingEntryDropdownItems(
		SharingEntry sharingEntry, ThemeDisplay themeDisplay) {

		if (!_isVisible(sharingEntry)) {
			return Collections.emptyList();
		}

		String urlDownload = _getURLDownload(sharingEntry, themeDisplay);

		if (Validator.isNull(urlDownload)) {
			return Collections.emptyList();
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(urlDownload);
				dropdownItem.setIcon("download");
				dropdownItem.setLabel(
					_language.get(themeDisplay.getLocale(), "download"));
			}
		).build();
	}

	private String _getURLDownload(
		SharingEntry sharingEntry, ThemeDisplay themeDisplay) {

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						sharingEntry.getClassName());

			if (assetRendererFactory == null) {
				return null;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					sharingEntry.getClassPK());

			if (assetRenderer == null) {
				return null;
			}

			return assetRenderer.getURLDownload(themeDisplay);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
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
		ObjectEntrySharingEntryDropdownItemContributor.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final Language _language;

}