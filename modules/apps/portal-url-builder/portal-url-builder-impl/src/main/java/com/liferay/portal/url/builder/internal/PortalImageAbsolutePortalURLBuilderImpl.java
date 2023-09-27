/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.url.builder.PortalImageAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class PortalImageAbsolutePortalURLBuilderImpl
	implements PortalImageAbsolutePortalURLBuilder {

	public PortalImageAbsolutePortalURLBuilderImpl(
		String cdnHost, String pathImage, String pathProxy,
		String relativeURL) {

		_cdnHost = cdnHost;
		_pathImage = pathImage;
		_pathProxy = pathProxy;
		_relativeURL = relativeURL;

		_ignoreCDNHost = false;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, _pathImage, _pathProxy, _relativeURL);

		return sb.toString();
	}

	@Override
	public PortalImageAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	private final String _cdnHost;
	private boolean _ignoreCDNHost;
	private final String _pathImage;
	private final String _pathProxy;
	private final String _relativeURL;

}