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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.util.PropsValues;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @author Adolfo Pérez
 */
public class RenderedImageUtil {

	public static byte[] getRenderedImageContentStream(
		RenderedImage renderedImage, String mimeType) {

		try (UnsyncByteArrayOutputStream baos =
				new UnsyncByteArrayOutputStream()) {

			ImageToolUtil.write(renderedImage, mimeType, baos);

			return baos.toByteArray();
		}
		catch (IOException ioException) {
			throw new AMRuntimeException.IOException(ioException);
		}
	}

	public static RenderedImage readImage(InputStream inputStream)
		throws ImageResolutionException, IOException {

		ImageInputStream imageInputStream = ImageIO.createImageInputStream(
			inputStream);

		Iterator<ImageReader> iterator = ImageIO.getImageReaders(
			imageInputStream);

		while (iterator.hasNext()) {
			ImageReader imageReader = null;

			try {
				imageReader = iterator.next();

				imageReader.setInput(imageInputStream);

				int height = imageReader.getHeight(0);
				int width = imageReader.getWidth(0);

				if (((PropsValues.IMAGE_TOOL_IMAGE_MAX_HEIGHT > 0) &&
					 (height > PropsValues.IMAGE_TOOL_IMAGE_MAX_HEIGHT)) ||
					((PropsValues.IMAGE_TOOL_IMAGE_MAX_WIDTH > 0) &&
					 (width > PropsValues.IMAGE_TOOL_IMAGE_MAX_WIDTH))) {

					throw new ImageResolutionException(
						StringBundler.concat(
							"Image's dimensions (", height, " px high and ",
							width, " px wide) exceed max dimensions (",
							PropsValues.IMAGE_TOOL_IMAGE_MAX_HEIGHT,
							" px high and ",
							PropsValues.IMAGE_TOOL_IMAGE_MAX_WIDTH,
							" px wide)"));
				}

				return imageReader.read(0);
			}
			catch (IOException ioException) {
			}
			finally {
				if (imageReader != null) {
					imageReader.dispose();
				}
			}
		}

		throw new IOException("Unsupported image type");
	}

}