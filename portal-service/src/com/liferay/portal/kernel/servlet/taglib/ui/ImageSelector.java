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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Sergio González
 */
public class ImageSelector {

	public ImageSelector(long imageId) {
		_imageId = imageId;

		_imageURL = null;
	}

	public ImageSelector(long imageId, String imageURL) {
		_imageId = imageId;

		_imageURL = imageURL;
	}

	public ImageSelector(String imageURL) {
		_imageId = 0;

		_imageURL = imageURL;
	}

	public long getImageId() {
		return _imageId;
	}

	public String getImageURL() {
		return _imageURL;
	}

	public boolean isRemoveSmallImage() {
		if ((_imageId == 0) && Validator.isNull(_imageURL)) {
			return true;
		}

		return false;
	}

	private long _imageId;
	private String _imageURL;

}