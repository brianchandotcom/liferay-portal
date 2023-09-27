/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.image;

import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.concurrent.Future;

/**
 * The Image utility class.
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ImageToolUtil {

	/**
	 * Returns the CMYK image converted to RGB using ImageMagick. This must be
	 * run against the original <code>byte[]</code> and not one extracted from a
	 * {@link RenderedImage}. The latter may potentially have been already been
	 * read incorrectly.
	 *
	 * @param  bytes the image to convert
	 * @param  type the image type (e.g., "gif", "jpg", etc.)
	 * @return the asynchronous process converting the image or <code>null
	 *         </code> if ImageMagick was disabled or if the conversion could
	 *         not be completed. The conversion may not complete if (1) the
	 *         image was not in the CMYK colorspace to begin with or (2) there
	 *         was an error in the conversion process.
	 */
	public static Future<RenderedImage> convertCMYKtoRGB(
		byte[] bytes, String type) {

		return CMYKImageToolHolder._cmykImageTool.convertCMYKtoRGB(bytes, type);
	}

	/**
	 * Returns the image converted to the type.
	 *
	 * @param  sourceImage the image to convert
	 * @param  type the image type to convert to (e.g., "gif", "jpg", etc.)
	 * @return the converted image
	 */
	public static BufferedImage convertImageType(
		BufferedImage sourceImage, int type) {

		return _imageTool.convertImageType(sourceImage, type);
	}

	public static RenderedImage crop(
		RenderedImage renderedImage, int height, int width, int x, int y) {

		return _imageTool.crop(renderedImage, height, width, x, y);
	}

	/**
	 * Encodes the image using the GIF format.
	 *
	 * @param  renderedImage the image to encode
	 * @param  outputStream the stream to write to
	 * @throws IOException if an IO exception occurred
	 */
	public static void encodeGIF(
			RenderedImage renderedImage, OutputStream outputStream)
		throws IOException {

		_imageTool.encodeGIF(renderedImage, outputStream);
	}

	/**
	 * Encodes the image using the WBMP format.
	 *
	 * @param  renderedImage the image to encode
	 * @param  outputStream the stream to write to
	 * @throws IOException if an IO exception occurred
	 */
	public static void encodeWBMP(
			RenderedImage renderedImage, OutputStream outputStream)
		throws IOException {

		_imageTool.encodeWBMP(renderedImage, outputStream);
	}

	public static RenderedImage flipHorizontal(RenderedImage renderedImage) {
		return _imageTool.flipHorizontal(renderedImage);
	}

	public static RenderedImage flipVertical(RenderedImage renderedImage) {
		return _imageTool.flipVertical(renderedImage);
	}

	/**
	 * Returns the rendered image as a {@link BufferedImage}.
	 *
	 * @param  renderedImage the original image
	 * @return the converted image
	 */
	public static BufferedImage getBufferedImage(RenderedImage renderedImage) {
		return _imageTool.getBufferedImage(renderedImage);
	}

	/**
	 * Returns the image as a <code>byte[]</code>.
	 *
	 * @param  renderedImage the image to read
	 * @param  contentType the content type (e.g., "image/jpeg") or image type
	 *         (e.g., "jpg") to use during encoding
	 * @return the encoded image
	 * @throws IOException if an IO exception occurred
	 */
	public static byte[] getBytes(
			RenderedImage renderedImage, String contentType)
		throws IOException {

		return _imageTool.getBytes(renderedImage, contentType);
	}

	public static Image getDefaultCompanyLogo() {
		return _imageTool.getDefaultCompanyLogo();
	}

	public static Image getDefaultOrganizationLogo() {
		return _imageTool.getDefaultOrganizationLogo();
	}

	public static Image getDefaultSpacer() {
		return _imageTool.getDefaultSpacer();
	}

	public static Image getDefaultUserFemalePortrait() {
		return _imageTool.getDefaultUserFemalePortrait();
	}

	public static Image getDefaultUserMalePortrait() {
		return _imageTool.getDefaultUserMalePortrait();
	}

	public static Image getDefaultUserPortrait() {
		return _imageTool.getDefaultUserPortrait();
	}

	public static Image getImage(byte[] bytes)
		throws ImageResolutionException, IOException {

		return _imageTool.getImage(bytes);
	}

	public static Image getImage(File file)
		throws ImageResolutionException, IOException {

		return _imageTool.getImage(file);
	}

	public static Image getImage(InputStream inputStream)
		throws ImageResolutionException, IOException {

		return _imageTool.getImage(inputStream);
	}

	public static Image getImage(InputStream inputStream, boolean cleanUpStream)
		throws ImageResolutionException, IOException {

		return _imageTool.getImage(inputStream, cleanUpStream);
	}

	public static ImageTool getImageTool() {
		return _imageTool;
	}

	public static boolean isNullOrDefaultSpacer(byte[] bytes) {
		return _imageTool.isNullOrDefaultSpacer(bytes);
	}

	/**
	 * Detects the image format and creates an {@link ImageBag} containing the
	 * {@link RenderedImage} and image type.
	 *
	 * @param  bytes the bytes to read
	 * @return the {@link ImageBag}
	 * @throws ImageResolutionException if the image's dimensions were larger
	 *         than those specified by portal properties
	 *         <code>image.tool.image.max.height</code> and
	 *         <code>image.tool.image.max.width</code>
	 * @throws IOException if an IO exception occurred
	 */
	public static ImageBag read(byte[] bytes)
		throws ImageResolutionException, IOException {

		return _imageTool.read(bytes);
	}

	/**
	 * Detects the image format and creates an {@link ImageBag} containing the
	 * {@link RenderedImage} and image type.
	 *
	 * @param  file the file to read
	 * @return the {@link ImageBag}
	 * @throws ImageResolutionException if the image's dimensions were larger
	 *         than those specified by portal properties
	 *         <code>image.tool.image.max.height</code> and
	 *         <code>image.tool.image.max.width</code>
	 * @throws IOException if an IO exception occurred
	 */
	public static ImageBag read(File file)
		throws ImageResolutionException, IOException {

		return _imageTool.read(file);
	}

	public static ImageBag read(InputStream inputStream)
		throws ImageResolutionException, IOException {

		return _imageTool.read(inputStream);
	}

	public static RenderedImage rotate(
		RenderedImage renderedImage, int degrees) {

		return _imageTool.rotate(renderedImage, degrees);
	}

	/**
	 * Returns the scaled image based on the given width with the height
	 * calculated to preserve aspect ratio.
	 *
	 * @param  renderedImage the image to scale
	 * @param  width the new width; also used to calculate the new height
	 * @return the scaled image
	 */
	public static RenderedImage scale(RenderedImage renderedImage, int width) {
		return _imageTool.scale(renderedImage, width);
	}

	/**
	 * Returns the scaled image based on the maximum height and width given
	 * while preserving the aspect ratio. If the image is already larger in both
	 * dimensions, the image will not be scaled.
	 *
	 * @param  renderedImage the image to scale
	 * @param  maxHeight the maximum height allowed for image
	 * @param  maxWidth the maximum width allowed for image
	 * @return the scaled image
	 */
	public static RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth) {

		return _imageTool.scale(renderedImage, maxHeight, maxWidth);
	}

	/**
	 * Encodes the image using the content or image type.
	 *
	 * @param  renderedImage the image to encode
	 * @param  contentType the content type (e.g., "image/jpeg") or image type
	 *         (e.g., "jpg") to use during encoding
	 * @param  outputStream the stream to write to
	 * @throws IOException if an IO exception occurred
	 */
	public static void write(
			RenderedImage renderedImage, String contentType,
			OutputStream outputStream)
		throws IOException {

		_imageTool.write(renderedImage, contentType, outputStream);
	}

	public void setImageTool(ImageTool imageTool) {
		_imageTool = imageTool;
	}

	private static ImageTool _imageTool;

	private static class CMYKImageToolHolder {

		private static volatile CMYKImageTool _cmykImageTool =
			ServiceProxyFactory.newServiceTrackedInstance(
				CMYKImageTool.class, CMYKImageToolHolder.class,
				"_cmykImageTool", false);

	}

}