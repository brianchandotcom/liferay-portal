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

package com.liferay.portal.search.web.internal.result.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;

import java.io.Serializable;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SearchResultContentDisplayContext implements Serializable {

	public AssetEntry getAssetEntry() {
		return _assetEntry;
	}

	public AssetRenderer<?> getAssetRenderer() {
		return _assetRenderer;
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	public String getHeaderTitle() {
		return _headerTitle;
	}

	public String getIconEditTarget() {
		return _iconEditTarget;
	}

	public String getIconURLString() {
		return _iconURLString;
	}

	public boolean hasEditPermission() {
		return _hasEditPermission;
	}

	public boolean isShowExtraInfo() {
		return _showExtraInfo;
	}

	public boolean isVisible() {
		return _visible;
	}

	public void setAssetEntry(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public void setAssetRenderer(AssetRenderer<?> assetRenderer) {
		_assetRenderer = assetRenderer;
	}

	public void setAssetRendererFactory(
		AssetRendererFactory<?> assetRendererFactory) {

		_assetRendererFactory = assetRendererFactory;
	}

	public void setHasEditPermission(boolean hasEditPermission) {
		_hasEditPermission = hasEditPermission;
	}

	public void setHeaderTitle(String headerTitle) {
		_headerTitle = headerTitle;
	}

	public void setIconEditTarget(String iconEditTarget) {
		_iconEditTarget = iconEditTarget;
	}

	public void setIconURLString(String iconURLString) {
		_iconURLString = iconURLString;
	}

	public void setShowExtraInfo(boolean showExtraInfo) {
		_showExtraInfo = showExtraInfo;
	}

	public void setVisible(boolean visible) {
		_visible = visible;
	}

	private AssetEntry _assetEntry;
	private AssetRenderer<?> _assetRenderer;
	private AssetRendererFactory<?> _assetRendererFactory;
	private boolean _hasEditPermission;
	private String _headerTitle;
	private String _iconEditTarget;
	private String _iconURLString;
	private boolean _showExtraInfo;
	private boolean _visible;

}