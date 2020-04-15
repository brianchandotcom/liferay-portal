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

package com.liferay.layout.content.page.editor.web.internal.constants;

/**
 * @author Jürgen Kappler
 */
public enum ViewportSize {

	DESKTOP("desktop", "desktop", "desktop", 992, 960, 1),
	MOBILE_LANDSCAPE(
		"landscapeMobile", "mobile-landscape", "mobile-landscape", 767, 576, 3),
	PORTRAIT_MOBILE(
		"portraitMobile", "mobile-portrait", "mobile-portrait", 575, 240, 4),
	TABLET("tablet", "tablet-portrait", "tablet", 991, 768, 2);

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

	public int getOrder() {
		return _order;
	}

	public String getViewportSizeId() {
		return _viewportSizeId;
	}

	private ViewportSize(
		String viewportSizeId, String icon, String label, int maxWidth,
		int minWidth, int order) {

		_viewportSizeId = viewportSizeId;
		_icon = icon;
		_label = label;
		_maxWidth = maxWidth;
		_minWidth = minWidth;
		_order = order;
	}

	private final String _icon;
	private final String _label;
	private final int _maxWidth;
	private final int _minWidth;
	private final int _order;
	private final String _viewportSizeId;

}