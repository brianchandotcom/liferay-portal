/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The Image utility class.
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ImageToolUtil {

	/**
	 * Converts a CMYK image to RGB using ImageMagick. This must be run against
	 * the original <code>byte[]</code> and not one extracted from a {@link
	 * RenderedImage}. The latter may potentially have been already been read
	 * incorrectly.
	 *
	 * @param  bytes the image to convert
	 * @param  type of image (e.g., "gif", "jpg", etc.)
	 * @param  fork whether to fork the process
	 * @return converted image or <code>null</code> if ImageMagick was disabled
	 *         or if the conversion was not completed. The conversion may not
	 *         complete if (1) the image was not in the CMYK colorspace to begin
	 *         with or (2) there was an error in the conversion process.
	 */
	public static RenderedImage convertCMYKtoRGB(
		byte[] bytes, String type, boolean fork) {

		return getImageTool().convertCMYKtoRGB(bytes, type, fork);
	}

	/**
	 * Converts an image from one image type to another.
	 *
	 * @param  sourceImage the image to convert
	 * @param  type of image to convert to (e.g., "gif", "jpg", etc.)
	 * @return converted image
	 */
	public static BufferedImage convertImageType(
		BufferedImage sourceImage, int type) {

		return getImageTool().convertImageType(sourceImage, type);
	}

	/**
	 * Encodes an image using the GIF format.
	 *
	 * @param  renderedImage the image to encode
	 * @param  os the stream to write to
	 * @throws IOException if an error occurred during writing
	 */
	public static void encodeGIF(RenderedImage renderedImage, OutputStream os)
		throws IOException {

		getImageTool().encodeGIF(renderedImage, os);
	}

	/**
	 * Encodes an image using the WBMP format.
	 *
	 * @param  renderedImage the image to encode
	 * @param  os the stream to write to
	 * @throws IOException if an error occurred during writing
	 */
	public static void encodeWBMP(RenderedImage renderedImage, OutputStream os)
		throws IOException {

		getImageTool().encodeWBMP(renderedImage, os);
	}

	/**
	 * Returns a {@link BufferedImage} from a given {@link RenderedImage}.
	 *
	 * @param  renderedImage the original image
	 * @return the converted image
	 */
	public static BufferedImage getBufferedImage(RenderedImage renderedImage) {
		return getImageTool().getBufferedImage(renderedImage);
	}

	/**
	 * Returns the <code>byte[]</code> from an image.
	 *
	 * @param  renderedImage the image to read
	 * @param  contentType the content type (e.g., "image/jpeg") or image type
	 *         (e.g., "jpg") to use during encoding
	 * @return the encoded image
	 * @throws IOException if an error occurred during writing
	 */
	public static byte[] getBytes(
			RenderedImage renderedImage, String contentType)
		throws IOException {

		return getImageTool().getBytes(renderedImage, contentType);
	}

	public static ImageTool getImageTool() {
		return _imageTool;
	}

	/**
	 * Detects the image format and creates an {@link ImageBag} containing the
	 * {@link RenderedImage} and image type.
	 *
	 * @param  bytes the bytes to read
	 * @return the {@link ImageBag}
	 * @throws IOException if an error occurred during writing
	 */
	public static ImageBag read(byte[] bytes) throws IOException {
		return getImageTool().read(bytes);
	}

	/**
	 * Detects the image format and creates an {@link ImageBag} containing the
	 * {@link RenderedImage} and image type.
	 *
	 * @param  file the file to read
	 * @return the {@link ImageBag}
	 * @throws IOException if an error occurred during writing
	 */
	public static ImageBag read(File file) throws IOException {
		return getImageTool().read(file);
	}

	/**
	 * Scales the image based on the given width with the height calculated to
	 * preserve aspect ratio.
	 *
	 * @param  renderedImage image to scale
	 * @param  width used as new width and to calculate for new height
	 * @return scaled image
	 */
	public static RenderedImage scale(RenderedImage renderedImage, int width) {
		return getImageTool().scale(renderedImage, width);
	}

	/**
	 * Scales the image based on the maximum height and width given while
	 * preserving the aspect ratio. If the image is already larger in both
	 * dimensions, the image will not be scaled.
	 *
	 * @param  renderedImage image to scale
	 * @param  maxHeight maximum height allowed for image
	 * @param  maxWidth maximum width allowed for image
	 * @return scaled image
	 */
	public static RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth) {

		return getImageTool().scale(renderedImage, maxHeight, maxWidth);
	}

	/**
	 * Encodes an image using.
	 *
	 * @param  renderedImage the image to encode
	 * @param  contentType the content type (e.g., "image/jpeg") or image type
	 *         (e.g., "jpg") to use during encoding
	 * @param  os the stream to write to
	 * @throws IOException if an error occurred during writing
	 */
	public static void write(
			RenderedImage renderedImage, String contentType, OutputStream os)
		throws IOException {

		getImageTool().write(renderedImage, contentType, os);
	}

	public void setImageTool(ImageTool imageTool) {
		_imageTool = imageTool;
	}

	private static ImageTool _imageTool;

}