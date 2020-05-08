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

package com.liferay.layout.responsive;

import com.liferay.petra.string.StringPool;

/**
 * @author Jürgen Kappler
 */
public enum ViewportSize {

	DESKTOP("desktop", "-lg-", "desktop", "desktop", 992, 960, 3, 1),
	MOBILE_LANDSCAPE(
		"landscapeMobile", "-sm-", "mobile-landscape", "mobile-landscape", 767,
		576, 2, 3),
	PORTRAIT_MOBILE(
		"portraitMobile", StringPool.DASH, "mobile-portrait", "mobile-portrait",
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