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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler(7);

		sb.append("{width=");
		sb.append(_width);
		sb.append(", height=");
		sb.append(_height);
		sb.append(", depth=");
		sb.append(_depth);
		sb.append("}");

		return sb.toString();
	}

	private final double _depth;
	private final double _height;
	private final double _width;

}