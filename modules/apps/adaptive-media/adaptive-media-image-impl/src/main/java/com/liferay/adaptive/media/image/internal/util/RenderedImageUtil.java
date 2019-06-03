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
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

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
			RenderedImage renderedImage, String mimeType)
		throws IOException {

		try (UnsyncByteArrayOutputStream baos =
				new UnsyncByteArrayOutputStream()) {

			ImageToolUtil.write(renderedImage, mimeType, baos);

			return baos.toByteArray();
		}
		catch (IOException ioe) {
			throw new AMRuntimeException.IOException(ioe);
		}
	}

	public static RenderedImage readImage(InputStream inputStream)
		throws IOException {

		ImageInputStream imageInputStream = ImageIO.createImageInputStream(
			inputStream);

		Iterator<ImageReader> iterator = ImageIO.getImageReaders(
			imageInputStream);

		while (iterator.hasNext()) {
			ImageReader imageReader = null;

			try {
				imageReader = iterator.next();

				imageReader.setInput(imageInputStream);

				return imageReader.read(0);
			}
			catch (Exception e) {
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