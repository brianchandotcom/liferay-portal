/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.hashed.files.HashedFilesRegistry;
import com.liferay.portal.url.builder.ESStylesheetAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class ESStylesheetAbsolutePortalURLBuilderImpl
	implements ESStylesheetAbsolutePortalURLBuilder {

	public ESStylesheetAbsolutePortalURLBuilderImpl(
		String cdnHost, String esStylesheetPath,
		HashedFilesRegistry hashedFilesRegistry, String pathModule,
		String pathProxy, String webContextPath) {

		if (!esStylesheetPath.startsWith(StringPool.SLASH)) {
			esStylesheetPath = StringPool.SLASH + esStylesheetPath;
		}

		if (!webContextPath.startsWith(StringPool.SLASH)) {
			webContextPath = StringPool.SLASH + webContextPath;
		}

		String prefix = pathModule + webContextPath;

		String hashedFileURI = hashedFilesRegistry.getHashedFileURI(
			prefix + esStylesheetPath);

		if (hashedFileURI != null) {
			esStylesheetPath = hashedFileURI.substring(prefix.length());
		}

		_cdnHost = cdnHost;
		_esStylesheetPath = esStylesheetPath;
		_pathModule = pathModule;
		_pathProxy = pathProxy;
		_webContextPath = webContextPath;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, _ignorePathProxy,
			_pathModule + _webContextPath, _pathProxy, _esStylesheetPath);

		return sb.toString();
	}

	@Override
	public ESStylesheetAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	@Override
	public ESStylesheetAbsolutePortalURLBuilder ignorePathProxy() {
		_ignorePathProxy = true;

		return this;
	}

	private final String _cdnHost;
	private final String _esStylesheetPath;
	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;
	private final String _pathModule;
	private final String _pathProxy;
	private final String _webContextPath;

}