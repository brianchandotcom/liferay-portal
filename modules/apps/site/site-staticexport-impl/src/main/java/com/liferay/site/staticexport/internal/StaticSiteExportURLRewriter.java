/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public class StaticSiteExportURLRewriter {

	public StaticSiteExportURLRewriter(
		Map<String, String> pagePaths, Set<String> portalHostNames,
		Map<String, String> resourcePaths) {

		_pagePaths = pagePaths;
		_portalHostNames = portalHostNames;
		_resourcePaths = resourcePaths;
	}

	public void rewrite(StaticSiteExportDocument staticSiteExportDocument) {
		staticSiteExportDocument.rewrite(this::_getPath);
	}

	private String _getPath(String url) {
		if (Validator.isNull(url)) {
			return null;
		}

		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			_portalHostNames, url);

		String path = _resourcePaths.get(staticSiteExportURL.getURL());

		if (path == null) {
			path = _pagePaths.get(staticSiteExportURL.getURL());
		}

		if (path == null) {
			return null;
		}

		return StringPool.SLASH + path + staticSiteExportURL.getURIFragment();
	}

	private final Map<String, String> _pagePaths;
	private final Set<String> _portalHostNames;
	private final Map<String, String> _resourcePaths;

}