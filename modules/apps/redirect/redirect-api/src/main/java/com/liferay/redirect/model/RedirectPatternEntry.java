/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model;

import com.google.re2j.Pattern;

/**
 * @author Alicia García
 */
public class RedirectPatternEntry {

	public RedirectPatternEntry(
		Pattern pattern, String destinationURL, String userAgent) {

		_pattern = pattern;
		_destinationURL = destinationURL;
		_userAgent = userAgent;
	}

	public String getDestinationURL() {
		return _destinationURL;
	}

	public Pattern getPattern() {
		return _pattern;
	}

	public String getUserAgent() {
		return _userAgent;
	}

	private final String _destinationURL;
	private final Pattern _pattern;
	private final String _userAgent;

}