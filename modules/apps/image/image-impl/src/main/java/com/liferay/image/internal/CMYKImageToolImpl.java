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

package com.liferay.image.internal;

import com.liferay.portal.kernel.concurrent.FutureConverter;
import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.image.CMYKImageTool;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;

import org.im4java.core.IMOperation;

import org.monte.media.jpeg.CMYKJPEGImageReaderSpi;

/**
 * @author Guilherme Camacho
 */
public class CMYKImageToolImpl implements CMYKImageTool {

	public static CMYKImageTool getInstance() {
		return _instance;
	}

	@Override
	public Future<RenderedImage> convertCMYKtoRGB(
		byte[] bytes, final String type) {

		ImageMagick imageMagick = getImageMagick();

		if (!imageMagick.isEnabled()) {
			return null;
		}

		File inputFile = _fileImpl.createTempFile(type);
		final File outputFile = _fileImpl.createTempFile(type);

		try {
			_fileImpl.write(inputFile, bytes);

			IMOperation imOperation = new IMOperation();

			imOperation.addRawArgs("-format", "%[colorspace]");
			imOperation.addImage(inputFile.getPath());

			String[] output = imageMagick.identify(imOperation.getCmdArgs());

			if ((output.length == 1) &&
				StringUtil.equalsIgnoreCase(output[0], "CMYK")) {

				if (_log.isInfoEnabled()) {
					_log.info("The image is in the CMYK colorspace");
				}

				imOperation = new IMOperation();

				imOperation.addRawArgs("-colorspace", "RGB");
				imOperation.addImage(inputFile.getPath());
				imOperation.addImage(outputFile.getPath());

				Future<Object> future = (Future<Object>)imageMagick.convert(
					imOperation.getCmdArgs());

				return new FutureConverter<RenderedImage, Object>(future) {

					@Override
					protected RenderedImage convert(Object object) {
						RenderedImage renderedImage = null;

						try {
							ImageBag imageBag = ImageToolUtil.read(
								_fileImpl.getBytes(outputFile));

							renderedImage = imageBag.getRenderedImage();
						}
						catch (ImageResolutionException | IOException
									exception) {

							if (_log.isDebugEnabled()) {
								_log.debug(
									"Unable to convert " + type, exception);
							}
						}

						return renderedImage;
					}

				};
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
		finally {
			_fileImpl.delete(inputFile);
			_fileImpl.delete(outputFile);
		}

		return null;
	}

	protected ImageMagick getImageMagick() {
		if (_imageMagick == null) {
			_imageMagick = ImageMagickImpl.getInstance();

			_imageMagick.reset();
		}

		return _imageMagick;
	}

	protected void orderImageReaderSpis() {
		IIORegistry defaultIIORegistry = IIORegistry.getDefaultInstance();

		ImageReaderSpi firstImageReaderSpi = null;
		ImageReaderSpi secondImageReaderSpi = null;

		Iterator<ImageReaderSpi> iterator =
			defaultIIORegistry.getServiceProviders(ImageReaderSpi.class, true);

		while (iterator.hasNext()) {
			ImageReaderSpi imageReaderSpi = iterator.next();

			if (imageReaderSpi instanceof CMYKJPEGImageReaderSpi) {
				secondImageReaderSpi = imageReaderSpi;
			}
			else {
				String[] formatNames = imageReaderSpi.getFormatNames();

				if (ArrayUtil.contains(
						formatNames, ImageTool.TYPE_JPEG, true) ||
					ArrayUtil.contains(formatNames, "jpeg", true)) {

					firstImageReaderSpi = imageReaderSpi;
				}
			}
		}

		if ((firstImageReaderSpi != null) && (secondImageReaderSpi != null)) {
			defaultIIORegistry.setOrdering(
				ImageReaderSpi.class, firstImageReaderSpi,
				secondImageReaderSpi);
		}
	}

	private CMYKImageToolImpl() {
		ImageIO.setUseCache(PropsValues.IMAGE_IO_USE_DISK_CACHE);

		orderImageReaderSpis();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMYKImageToolImpl.class);

	private static final CMYKImageTool _instance = new CMYKImageToolImpl();

	private static final FileImpl _fileImpl = FileImpl.getInstance();
	private static ImageMagick _imageMagick;

}