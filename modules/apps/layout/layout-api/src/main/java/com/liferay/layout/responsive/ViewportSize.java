/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.responsive;

import com.liferay.petra.string.StringPool;

/**
 * @author Jürgen Kappler
 */
public enum ViewportSize {

	DESKTOP("desktop", "-lg-", "display", "desktop", 992, 960, 3, 1),
	MOBILE_LANDSCAPE(
		"landscapeMobile", "-sm-", "mobile-landscape", "landscape-phone", 767,
		576, 2, 3),
	PORTRAIT_MOBILE(
		"portraitMobile", StringPool.DASH, "mobile-portrait", "portrait-phone",
		575, 240, 1, 4),
	TABLET("tablet", "-md-", "tablet-portrait", "tablet", 991, 768, 3, 2);

	public String getCssClassPrefix() {
		return _cssClassPrefix;
	}

	public String getIcon() {
		return _icon;
	}

	public String getLabel() {
		return _label;
	}

	public int getMaxWidth() {
		return _maxWidth;
	}

	public int getMinWidth() {
		return _minWidth;
	}

	public int getModulesPerRow() {
		return _modulesPerRow;
	}

	public int getOrder() {
		return _order;
	}

	public String getViewportSizeId() {
		return _viewportSizeId;
	}

	private ViewportSize(
		String viewportSizeId, String cssClassPrefix, String icon, String label,
		int maxWidth, int minWidth, int modulesPerRow, int order) {

		_viewportSizeId = viewportSizeId;
		_cssClassPrefix = cssClassPrefix;
		_icon = icon;
		_label = label;
		_maxWidth = maxWidth;
		_minWidth = minWidth;
		_modulesPerRow = modulesPerRow;
		_order = order;
	}

	private final String _cssClassPrefix;
	private final String _icon;
	private final String _label;
	private final int _maxWidth;
	private final int _minWidth;
	private final int _modulesPerRow;
	private final int _order;
	private final String _viewportSizeId;

}