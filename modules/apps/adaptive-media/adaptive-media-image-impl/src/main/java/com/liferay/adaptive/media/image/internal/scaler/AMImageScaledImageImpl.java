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

package com.liferay.adaptive.media.image.internal.scaler;

import com.liferay.adaptive.media.image.scaler.AMImageScaledImage;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public class AMImageScaledImageImpl implements AMImageScaledImage {

	public AMImageScaledImageImpl(byte[] bytes, int height, int width) {
		_bytes = bytes;
		_height = height;
		_width = width;

		_mimeType = null;
	}

	public AMImageScaledImageImpl(
		byte[] bytes, int height, int width, String mimeType) {

		_bytes = bytes;
		_height = height;
		_width = width;
		_mimeType = mimeType;
	}

	@Override
	public int getHeight() {
		return _height;
	}

	@Override
	public InputStream getInputStream() {
		return new UnsyncByteArrayInputStream(_bytes);
	}

	@Override
	public String getMimeType() {
		return _mimeType;
	}

	@Override
	public long getSize() {
		return _bytes.length;
	}

	@Override
	public int getWidth() {
		return _width;
	}

	private final byte[] _bytes;
	private final int _height;
	private final String _mimeType;
	private final int _width;

}