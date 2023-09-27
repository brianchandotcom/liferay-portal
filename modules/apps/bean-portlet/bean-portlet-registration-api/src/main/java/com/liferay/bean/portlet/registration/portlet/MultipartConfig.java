/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

/**
 * @author Shuyang Zhou
 */
public class MultipartConfig {

	public static final MultipartConfig UNSUPPORTED = new MultipartConfig(
		0, null, -1, -1, false);

	public MultipartConfig(
		int fileSizeThreshold, String location, long maxFileSize,
		long maxRequestSize) {

		this(fileSizeThreshold, location, maxFileSize, maxRequestSize, true);
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
		int fileSizeThreshold, String location, long maxFileSize,
		long maxRequestSize, boolean supported) {

		_fileSizeThreshold = fileSizeThreshold;
		_location = location;
		_maxFileSize = maxFileSize;
		_maxRequestSize = maxRequestSize;
		_supported = supported;
	}

	private final int _fileSizeThreshold;
	private final String _location;
	private final long _maxFileSize;
	private final long _maxRequestSize;
	private final boolean _supported;

}