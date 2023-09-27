/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.petra.string.StringPool;

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