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

package com.liferay.asset.web.internal.change.tracking.spi.display;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
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