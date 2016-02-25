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

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class ImageSelector {

	public ImageSelector() {
		this(
			null, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK);
	}

	public ImageSelector(
		byte[] imageBytes, String imageTitle, String imageMimeType,
		String imageCropRegion) {

		this(
			imageBytes, imageTitle, imageMimeType, imageCropRegion,
			StringPool.BLANK);
	}

	public ImageSelector(String imageURL) {
		this(
			null, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			imageURL);
	}

	public byte[] getImageBytes() {
		return _imageBytes;
	}

	public String getImageCropRegion() {
		return _imageCropRegion;
	}

	public String getImageMimeType() {
		return _imageMimeType;
	}

	public String getImageTitle() {
		return _imageTitle;
	}

	public String getImageURL() {
		return _imageURL;
	}

	private ImageSelector(
		byte[] imageBytes, String imageTitle, String imageMimeType,
		String imageCropRegion, String imageURL) {

		_imageBytes = imageBytes;
		_imageTitle = imageTitle;
		_imageMimeType = imageMimeType;
		_imageCropRegion = imageCropRegion;
		_imageURL = imageURL;
	}

	private final byte[] _imageBytes;
	private final String _imageCropRegion;
	private final String _imageMimeType;
	private final String _imageTitle;
	private final String _imageURL;

}