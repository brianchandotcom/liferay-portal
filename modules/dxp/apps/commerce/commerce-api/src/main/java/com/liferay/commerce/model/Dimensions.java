/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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