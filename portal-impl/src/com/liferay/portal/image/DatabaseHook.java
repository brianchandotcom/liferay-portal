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

package com.liferay.portal.image;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.Image;

import java.io.InputStream;

/**
 * @author Jorge Ferrer
 */
public class DatabaseHook extends BaseHook {

	@Override
	public void deleteImage(Image image) {
	}

	@Override
	public byte[] getImageAsBytes(Image image) {
		return image.getTextObj();
	}

	@Override
	public InputStream getImageAsStream(Image image) {
		return new UnsyncByteArrayInputStream(getImageAsBytes(image));
	}

	@Override
	public void updateImage(Image image, String type, byte[] bytes) {
		image.setTextObj(bytes);
	}

}