/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.url.builder.ServletAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class ServletAbsolutePortalURLBuilderImpl
	implements ServletAbsolutePortalURLBuilder {

	public ServletAbsolutePortalURLBuilderImpl(
		String pathModule, String pathProxy, String requestURL) {

		_pathModule = pathModule;
		_pathProxy = pathProxy;
		_requestURL = requestURL;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(sb, _pathModule, _pathProxy, _requestURL);

		// There is no need to add a cache param because API requests are
		// handled by servlets. The portal sends headers to prevent browsers
		// from caching them.

		return sb.toString();
	}

	private final String _pathModule;
	private final String _pathProxy;
	private final String _requestURL;

}