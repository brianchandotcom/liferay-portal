/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.url.builder.WebContextStylesheetAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class WebContextStylesheetAbsolutePortalURLBuilderImpl
	implements WebContextStylesheetAbsolutePortalURLBuilder {

	public WebContextStylesheetAbsolutePortalURLBuilderImpl(
		String cdnHost, HashedFilesRegistry hashedFilesRegistry,
		String pathModule, String pathProxy, String stylesheetPath,
		String webContextPath) {

		if (!stylesheetPath.startsWith(StringPool.SLASH)) {
			stylesheetPath = StringPool.SLASH + stylesheetPath;
		}

		if (!webContextPath.startsWith(StringPool.SLASH)) {
			webContextPath = StringPool.SLASH + webContextPath;
		}

		String prefix = pathModule + webContextPath;

		String hashedFileURI = hashedFilesRegistry.getHashedFileURI(
			prefix + stylesheetPath);

		if (hashedFileURI != null) {
			stylesheetPath = hashedFileURI.substring(prefix.length());
		}

		_cdnHost = cdnHost;
		_pathModule = pathModule;
		_pathProxy = pathProxy;
		_stylesheetPath = stylesheetPath;
		_webContextPath = webContextPath;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, _ignorePathProxy,
			_pathModule + _webContextPath, _pathProxy, _stylesheetPath);

		return sb.toString();
	}

	@Override
	public WebContextStylesheetAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	@Override
	public WebContextStylesheetAbsolutePortalURLBuilder ignorePathProxy() {
		_ignorePathProxy = true;

		return this;
	}

	private final String _cdnHost;
	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;
	private final String _pathModule;
	private final String _pathProxy;
	private final String _stylesheetPath;
	private final String _webContextPath;

}