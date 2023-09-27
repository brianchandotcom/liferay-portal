/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.string.StringBundler;

/**
 * @author Andrea Di Giorgi
 */
public class Dimensions {

	public Dimensions(double width, double height, double depth) {
		_width = width;
		_height = height;
		_depth = depth;
	}

	public double getDepth() {
		return _depth;
	}

	public double getHeight() {
		return _height;
	}

	public double getWidth() {
		return _width;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{width=", _width, ", height=", _height, ", depth=", _depth, "}");
	}

	private final double _depth;
	private final double _height;
	private final double _width;

}