/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Allegrini
 */
public class EmptyFDSDisplayContext {

	public EmptyFDSDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/c/fdssamples?filter=('color' eq 'empty')";
	}

	private final HttpServletRequest _httpServletRequest;

}