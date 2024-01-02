/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.web.internal.model;

/**
 * @author Andrea Sbarra
 */
public class VirtualFile {

	public VirtualFile(long virtualFileEntryId, String url, String version) {
		_url = url;
		_version = version;

		_id = virtualFileEntryId;
	}

	public long getId() {
		return _id;
	}

	public String getURL() {
		return _url;
	}

	public String getVersion() {
		return _version;
	}

	private final long _id;
	private final String _url;
	private final String _version;

}