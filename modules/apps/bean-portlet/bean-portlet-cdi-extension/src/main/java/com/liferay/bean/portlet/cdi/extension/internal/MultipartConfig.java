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

package com.liferay.bean.portlet.cdi.extension.internal;

/**
 * @author Shuyang Zhou
 */
public class MultipartConfig {

	public static final MultipartConfig UNSUPPORTED = new MultipartConfig(
		false, 0, null, -1, -1);

	public MultipartConfig(
		int fileSizeThreshold, String location, long maxFileSize,
		long maxRequestSize) {

		this(true, fileSizeThreshold, location, maxFileSize, maxRequestSize);
	}

	public int getFileSizeThreshold() {
		return _fileSizeThreshold;
	}

	public String getLocation() {
		return _location;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public long getMaxRequestSize() {
		return _maxRequestSize;
	}

	public boolean isSupported() {
		return _supported;
	}

	public MultipartConfig merge(MultipartConfig multipartConfig) {
		if (!_supported && !multipartConfig._supported) {
			return this;
		}

		int fileSizeThreshold = _fileSizeThreshold;

		if (multipartConfig._fileSizeThreshold > 0) {
			fileSizeThreshold = multipartConfig._fileSizeThreshold;
		}

		String location = _location;

		if (multipartConfig._location != null) {
			location = multipartConfig._location;
		}

		long maxFileSize = _maxFileSize;

		if (multipartConfig._maxFileSize > 0) {
			maxFileSize = multipartConfig._maxFileSize;
		}

		long maxRequestSize = _maxRequestSize;

		if (multipartConfig._maxRequestSize > 0) {
			maxRequestSize = multipartConfig._maxRequestSize;
		}

		return new MultipartConfig(
			fileSizeThreshold, location, maxFileSize, maxRequestSize);
	}

	private MultipartConfig(
		boolean supported, int fileSizeThreshold, String location,
		long maxFileSize, long maxRequestSize) {

		_supported = supported;
		_fileSizeThreshold = fileSizeThreshold;
		_location = location;
		_maxFileSize = maxFileSize;
		_maxRequestSize = maxRequestSize;
	}

	private final int _fileSizeThreshold;
	private final String _location;
	private final long _maxFileSize;
	private final long _maxRequestSize;
	private final boolean _supported;

}