/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.web.internal.change.tracking.spi.display;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class AssetEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<AssetEntry> {

	@Override
	public String[] getAvailableLanguageIds(AssetEntry assetEntry) {
		return assetEntry.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(AssetEntry assetEntry) {
		return assetEntry.getDefaultLanguageId();
	}

	@Override
	public Class<AssetEntry> getModelClass() {
		return AssetEntry.class;
	}

	@Override
	public String getTitle(Locale locale, AssetEntry assetEntry) {
		return assetEntry.getTitle(locale);
	}

	@Override
	public boolean isHideable(AssetEntry assetEntry) {
		return true;
	}

}