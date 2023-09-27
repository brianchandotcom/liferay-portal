/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.document.library.internal.servlet.taglib.ui;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.servlet.taglib.ui.SharingEntryDropdownItemContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
	},
	service = SharingEntryDropdownItemContributor.class
)
public class FileEntrySharingEntryDropdownItemContributor
	implements SharingEntryDropdownItemContributor {

	@Override
	public List<DropdownItem> getSharingEntryDropdownItems(
		SharingEntry sharingEntry, ThemeDisplay themeDisplay) {

		return DropdownItemListBuilder.add(
			() -> _isVisible(sharingEntry),
			dropdownItem -> {
				dropdownItem.setIcon("download");
				dropdownItem.setLabel(
					_language.get(themeDisplay.getLocale(), "download"));

				AssetRenderer<?> assetRenderer = _getAssetEntryRenderer(
					sharingEntry);

				dropdownItem.setHref(
					assetRenderer.getURLDownload(themeDisplay));
			}
		).build();
	}

	private AssetRenderer<?> _getAssetEntryRenderer(SharingEntry sharingEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				sharingEntry.getClassName());

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

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Language _language;

}