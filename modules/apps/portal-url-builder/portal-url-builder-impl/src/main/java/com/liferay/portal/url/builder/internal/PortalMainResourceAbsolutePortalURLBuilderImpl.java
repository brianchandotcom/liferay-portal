/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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