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
import com.liferay.portal.url.builder.PortalMainResourceAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class PortalMainResourceAbsolutePortalURLBuilderImpl
	implements PortalMainResourceAbsolutePortalURLBuilder {

	public PortalMainResourceAbsolutePortalURLBuilderImpl(
		String pathMain, String pathProxy, String relativeURL) {

		_pathMain = pathMain;
		_pathProxy = pathProxy;
		_relativeURL = relativeURL;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(sb, _pathMain, _pathProxy, _relativeURL);

		return sb.toString();
	}

	private final String _pathMain;
	private final String _pathProxy;
	private final String _relativeURL;

}