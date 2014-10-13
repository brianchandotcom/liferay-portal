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

package com.liferay.portal.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class SpriteImage implements Serializable {

	public SpriteImage(
		String spriteFileName, String imageFileName, int offset, int height,
		int width) {

		_spriteFileName = spriteFileName;
		_imageFileName = imageFileName;
		_offset = offset;
		_height = height;
		_width = width;
	}

	public int getHeight() {
		return _height;
	}

	public String getImageFileName() {
		return _imageFileName;
	}

	public int getOffset() {
		return _offset;
	}

	public String getSpriteFileName() {
		return _spriteFileName;
	}

	public int getWidth() {
		return _width;
	}

	private final int _height;
	private final String _imageFileName;
	private final int _offset;
	private final String _spriteFileName;
	private final int _width;

}