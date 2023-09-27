/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.url.builder.BrowserModuleAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class BrowserModuleAbsolutePortalURLBuilderImpl
	implements BrowserModuleAbsolutePortalURLBuilder {

	public BrowserModuleAbsolutePortalURLBuilderImpl(
		String browserModulePath, String pathContext, String pathProxy) {

		_browserModulePath = browserModulePath;
		_pathContext = pathContext;
		_pathProxy = pathProxy;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(sb, _pathContext, _pathProxy, _browserModulePath);

		return sb.toString();
	}

	private final String _browserModulePath;
	private final String _pathContext;
	private final String _pathProxy;

}