/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.plugin;

/**
 * @author Jorge Ferrer
 */
public class Screenshot {

	public String getLargeImageURL() {
		return _largeImageURL;
	}

	public String getThumbnailURL() {
		return _thumbnailURL;
	}

	public void setLargeImageURL(String largeImageURL) {
		_largeImageURL = largeImageURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		_thumbnailURL = thumbnailURL;
	}

	private String _largeImageURL;
	private String _thumbnailURL;

}