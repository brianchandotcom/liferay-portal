/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.site.staticexport.StaticSiteExportResource;

/**
 * @author Víctor Galán
 */
public class StaticSiteExportResourceImpl implements StaticSiteExportResource {

	public StaticSiteExportResourceImpl(byte[] content, String url) {
		_content = content;
		_url = url;
	}

	@Override
	public byte[] getContent() {
		return _content;
	}

	@Override
	public String getURL() {
		return _url;
	}

	private final byte[] _content;
	private final String _url;

}