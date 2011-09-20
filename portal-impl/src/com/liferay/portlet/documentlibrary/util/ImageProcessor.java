/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Sergio González
 */
public class ImageProcessor implements DLProcessor {

	public static void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public static Set<String> getImageMimeTypes() {
		return _imageMimeTypes;
	}

	public static boolean hasImages(FileVersion fileVersion) {
		if (_hasImages(fileVersion.getSmallImageId()) &&
			_hasImages(fileVersion.getLargeImageId())) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean hasLargeImage(FileVersion fileVersion) {
		return _hasImages(fileVersion.getLargeImageId());
	}

	public static boolean hasSmallImage(FileVersion fileVersion) {
		return _hasImages(fileVersion.getSmallImageId());
	}

	public void trigger(FileEntry fileEntry) {
		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			trigger(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	private static boolean _hasImages(long imageId) {
		boolean hasImages = false;

		try {
			Image image = ImageLocalServiceUtil.getImage(imageId);

			if (image != null) {
				hasImages = true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	private void _generateImages(FileVersion fileVersion) {
		try {
			InputStream inputStream =
				DLFileEntryLocalServiceUtil.getFileAsStream(
					fileVersion.getUserId(), fileVersion.getFileEntryId(),
					fileVersion.getVersion(), false);

			byte[] bytes = FileUtil.getBytes(inputStream);

			ImageBag imageBag = ImageProcessorUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			if (renderedImage != null) {
				_saveImages(fileVersion.getLargeImageId(), renderedImage,
					fileVersion.getSmallImageId(),
					fileVersion.getCustom1ImageId(),
					fileVersion.getCustom2ImageId(), bytes,
					fileVersion.getMimeType());
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			_fileEntries.remove(fileVersion.getFileVersionId());
		}
	}

	private boolean _isSupportedImage(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		return _imageMimeTypes.contains(fileVersion.getMimeType());
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileEntries.contains(fileVersion.getFileVersionId()) &&
			_isSupportedImage(fileVersion) && !hasImages(fileVersion)) {
			_fileEntries.add(fileVersion.getFileVersionId());

			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
				fileVersion);
		}
	}

	private void _saveImages(
			long largeImageId, RenderedImage renderedImage, long smallImageId,
			long custom1ImageId, long custom2ImageId, byte[] bytes,
			String contentType)
		throws PortalException, SystemException {

		try {

			// Image

			ImageLocalServiceUtil.updateImage(largeImageId, bytes);

			// Thumbnail and custom sizes

			_saveScaledImage(
				renderedImage, smallImageId, contentType,
				PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION));

			if (custom1ImageId > 0) {
				_saveScaledImage(
					renderedImage, custom1ImageId, contentType,
					PropsValues.IG_IMAGE_CUSTOM_1_MAX_DIMENSION);
			}

			if (custom2ImageId > 0) {
				_saveScaledImage(
					renderedImage, custom2ImageId, contentType,
					PropsValues.IG_IMAGE_CUSTOM_2_MAX_DIMENSION);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private void _saveScaledImage(
			RenderedImage renderedImage, long imageId, String contentType,
			int dimension)
		throws IOException, PortalException, SystemException {

		RenderedImage thumbnail = ImageProcessorUtil.scale(
			renderedImage, dimension, dimension);

		ImageLocalServiceUtil.updateImage(
			imageId, ImageProcessorUtil.getBytes(thumbnail, contentType));
	}

	private static Log _log = LogFactoryUtil.getLog(ImageProcessor.class);

	private static ImageProcessor _instance = new ImageProcessor();

	private static Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.IG_IMAGE_THUMBNAIL_MIME_TYPES);

	private static List<Long> _fileEntries = new Vector<Long>();

}