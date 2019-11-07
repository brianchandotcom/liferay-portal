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

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class URLCodec_IW {
	public static URLCodec_IW getInstance() {
		return _instance;
	}

	public java.lang.String decodeURL(java.lang.String encodedURLString) {
		return URLCodec.decodeURL(encodedURLString);
	}

	public java.lang.String decodeURL(java.lang.String encodedURLString,
		java.lang.String charsetName) {
		return URLCodec.decodeURL(encodedURLString, charsetName);
	}

	public java.lang.String encodeURL(java.lang.String rawURLString) {
		return URLCodec.encodeURL(rawURLString);
	}

	public java.lang.String encodeURL(java.lang.String rawURLString,
		boolean escapeSpaces) {
		return URLCodec.encodeURL(rawURLString, escapeSpaces);
	}

	public java.lang.String encodeURL(java.lang.String rawURLString,
		java.lang.String charsetName, boolean escapeSpaces) {
		return URLCodec.encodeURL(rawURLString, charsetName, escapeSpaces);
	}

	private URLCodec_IW() {
	}

	private static URLCodec_IW _instance = new URLCodec_IW();
}