/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class Dimensions implements Serializable {

	public static final Dimensions UNKNOWN = new Dimensions(-1, -1);

	public Dimensions(float height, float width) {
		_height = height;
		_width = width;
	}

	public float getHeight() {
		return _height;
	}

	public float getWidth() {
		return _width;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{height=", _height, ", width=", _width, "}");
	}

	private final float _height;
	private final float _width;

}