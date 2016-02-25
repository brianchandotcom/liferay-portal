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