/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

/**
 * @author André de Oliveira
 */
public class DistributableImpl implements Distributable {

	public DistributableImpl(String downloadURLString, String checksum) {
		_downloadURLString = downloadURLString;
		_checksum = checksum;
	}

	@Override
	public String getChecksum() {
		return _checksum;
	}

	@Override
	public String getDownloadURLString() {
		return _downloadURLString;
	}

	private final String _checksum;
	private final String _downloadURLString;

}