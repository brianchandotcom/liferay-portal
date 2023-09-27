/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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