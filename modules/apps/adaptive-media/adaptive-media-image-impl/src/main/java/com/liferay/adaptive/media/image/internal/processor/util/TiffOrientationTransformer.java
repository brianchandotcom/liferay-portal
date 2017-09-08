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

package com.liferay.adaptive.media.image.internal.processor.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.internal.util.RenderedImageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;

import java.io.IOException;
import java.io.InputStream;

import java.util.Hashtable;
import java.util.Optional;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = TiffOrientationTransformer.class)
public class TiffOrientationTransformer {

	public RenderedImage transform(Supplier<InputStream> inputStreamSupplier)
		throws PortalException {

		try {
			Optional<Integer> tiffOrientationValueOptional =
				_getTiffOrientationValue(inputStreamSupplier);

			if (tiffOrientationValueOptional.isPresent()) {
				return _transform(
					inputStreamSupplier, tiffOrientationValueOptional.get());
			}

			return RenderedImageUtil.readImage(inputStreamSupplier.get());
		}
		catch (IOException ioe) {
			throw new AMRuntimeException.IOException(ioe);
		}
	}

	private RenderedImage _flipHorizontal(RenderedImage renderedImage) {
		BufferedImage bufferedImage = _getBufferedImage(renderedImage);

		AffineTransform affineTransform = AffineTransform.getScaleInstance(
			-1.0, 1.0);

		affineTransform.translate(-bufferedImage.getWidth(), 0);

		AffineTransformOp affineTransformOp = new AffineTransformOp(
			affineTransform, null);

		return affineTransformOp.filter(bufferedImage, null);
	}

	private RenderedImage _flipVertical(RenderedImage renderedImage) {
		BufferedImage bufferedImage = _getBufferedImage(renderedImage);

		AffineTransform affineTransform = AffineTransform.getScaleInstance(
			1.0, -1.0);

		affineTransform.translate(0, -bufferedImage.getHeight());

		AffineTransformOp affineTransformOp = new AffineTransformOp(
			affineTransform, null);

		return affineTransformOp.filter(bufferedImage, null);
	}

	private BufferedImage _getBufferedImage(RenderedImage renderedImage) {
		if (renderedImage instanceof BufferedImage) {
			return (BufferedImage)renderedImage;
		}

		ColorModel colorModel = renderedImage.getColorModel();

		WritableRaster writableRaster =
			colorModel.createCompatibleWritableRaster(
				renderedImage.getWidth(), renderedImage.getHeight());

		Hashtable<String, Object> properties = new Hashtable<>();

		String[] keys = renderedImage.getPropertyNames();

		if (!ArrayUtil.isEmpty(keys)) {
			for (String key : keys) {
				properties.put(key, renderedImage.getProperty(key));
			}
		}

		BufferedImage bufferedImage = new BufferedImage(
			colorModel, writableRaster, colorModel.isAlphaPremultiplied(),
			properties);

		renderedImage.copyData(writableRaster);

		return bufferedImage;
	}

	private Optional<Integer> _getTiffOrientationValue(
			Supplier<InputStream> inputStreamSupplier)
		throws PortalException {

		try (InputStream inputStream = inputStreamSupplier.get()) {
			Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

			ExifIFD0Directory exifIFD0Directory =
				metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

			if ((exifIFD0Directory == null) ||
				!exifIFD0Directory.containsTag(
					ExifIFD0Directory.TAG_ORIENTATION)) {

				return Optional.empty();
			}

			return Optional.of(
				exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return Optional.empty();
	}

	private RenderedImage _rotate(RenderedImage renderedImage, int degrees) {
		BufferedImage bufferedImage = _getBufferedImage(renderedImage);

		int imageWidth = bufferedImage.getWidth();
		int imageHeight = bufferedImage.getHeight();

		double radians = Math.toRadians(degrees);

		double absoluteSin = Math.abs(Math.sin(radians));
		double absoluteCos = Math.abs(Math.cos(radians));

		int rotatedImageWidth = (int)Math.floor(
			(imageWidth * absoluteCos) + (imageHeight * absoluteSin));
		int rotatedImageHeight = (int)Math.floor(
			(imageHeight * absoluteCos) + (imageWidth * absoluteSin));

		BufferedImage rotatedBufferedImage = new BufferedImage(
			rotatedImageWidth, rotatedImageHeight, bufferedImage.getType());

		AffineTransform affineTransform = new AffineTransform();

		affineTransform.translate(
			rotatedImageWidth / 2, rotatedImageHeight / 2);
		affineTransform.rotate(radians);
		affineTransform.translate(imageWidth / (-2), imageHeight / (-2));

		Graphics2D graphics = rotatedBufferedImage.createGraphics();

		graphics.drawImage(bufferedImage, affineTransform, null);

		graphics.dispose();

		return rotatedBufferedImage;
	}

	private RenderedImage _transform(
			Supplier<InputStream> inputStreamSupplier, int tiffOrientationValue)
		throws IOException {

		RenderedImage renderedImage = RenderedImageUtil.readImage(
			inputStreamSupplier.get());

		if (tiffOrientationValue == _ORIENTATION_VALUE_HORIZONTAL_NORMAL) {
			return renderedImage;
		}
		else if (tiffOrientationValue == _ORIENTATION_VALUE_MIRROR_HORIZONTAL) {
			return _flipHorizontal(renderedImage);
		}
		else if (tiffOrientationValue ==
					_ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_90_CW) {

			return _flipVertical(_rotate(renderedImage, 90));
		}
		else if (tiffOrientationValue ==
					_ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_270_CW) {

			return _flipVertical(_rotate(renderedImage, 270));
		}
		else if (tiffOrientationValue == _ORIENTATION_VALUE_MIRROR_VERTICAL) {
			return _flipVertical(renderedImage);
		}
		else if (tiffOrientationValue == _ORIENTATION_VALUE_ROTATE_90_CW) {
			return _rotate(renderedImage, 90);
		}
		else if (tiffOrientationValue == _ORIENTATION_VALUE_ROTATE_180) {
			return _rotate(renderedImage, 180);
		}
		else if (tiffOrientationValue == _ORIENTATION_VALUE_ROTATE_270_CW) {
			return _rotate(renderedImage, 270);
		}

		return renderedImage;
	}

	private static final int _ORIENTATION_VALUE_HORIZONTAL_NORMAL = 1;

	private static final int _ORIENTATION_VALUE_MIRROR_HORIZONTAL = 2;

	private static final int _ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_90_CW =
		7;

	private static final int
		_ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_270_CW = 5;

	private static final int _ORIENTATION_VALUE_MIRROR_VERTICAL = 4;

	private static final int _ORIENTATION_VALUE_ROTATE_90_CW = 6;

	private static final int _ORIENTATION_VALUE_ROTATE_180 = 3;

	private static final int _ORIENTATION_VALUE_ROTATE_270_CW = 8;

	private static final Log _log = LogFactoryUtil.getLog(
		TiffOrientationTransformer.class);

}