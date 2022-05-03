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

		// There's no need to add any cache param because API requests are
		// handled by servlets, thus the portal sends headers to prevent
		// browsers from caching them.

		return sb.toString();
	}

	private final String _pathModule;
	private final String _pathProxy;
	private final String _requestURL;

}