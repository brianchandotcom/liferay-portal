/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class ImageField {

	public ImageField(String alt, String shape, String size, String src) {
		_alt = alt;
		_shape = shape;
		_size = size;
		_src = src;
	}

	public String getAlt() {
		return _alt;
	}

	public String getShape() {
		return _shape;
	}

	public String getSize() {
		return _size;
	}

	public String getSrc() {
		return _src;
	}

	private final String _alt;
	private final String _shape;
	private final String _size;
	private final String _src;

}