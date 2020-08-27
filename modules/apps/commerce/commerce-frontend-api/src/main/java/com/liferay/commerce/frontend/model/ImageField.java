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