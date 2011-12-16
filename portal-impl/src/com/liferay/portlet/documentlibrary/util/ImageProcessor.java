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

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Sergio González
 * @author Alexander Chow
 */
public class ImageProcessor extends DLPreviewableProcessor {

	public void cleanUp(FileEntry fileEntry) {
		deleteFiles(fileEntry, null);
	}

	public void cleanUp(FileVersion fileVersion) {
		String type = _instance.getThumbnailType(fileVersion);

		deleteFiles(fileVersion, type);
	}

	public static void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public static Set<String> getImageMimeTypes() {
		return _instance._imageMimeTypes;
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return _instance.doGetThumbnailAsStream(fileVersion, thumbnailIndex);
	}

	public static long getThumbnailFileSize(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return _instance.doGetThumbnailFileSize(fileVersion, thumbnailIndex);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		if (!PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
			return false;
		}

		boolean hasImages = false;

		try {
			hasImages = _instance._hasImages(fileVersion);

			if (!hasImages && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	public static boolean isImageSupported(FileVersion fileVersion) {
		return _instance.isSupported(fileVersion);
	}

	public static boolean isImageSupported(String mimeType) {
		return _instance.isSupported(mimeType);
	}

	public static void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId,
			InputStream is, String type)
		throws Exception {

		_instance._storeThumbnail(
			companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
			custom2ImageId, is, type);
	}

	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		return _imageMimeTypes.contains(mimeType);
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	private void _generateImages(FileVersion fileVersion) {
		try {
			if (!PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
				return;
			}

			InputStream inputStream = fileVersion.getContentStream(false);

			byte[] bytes = FileUtil.getBytes(inputStream);

			ImageBag imageBag = ImageProcessorUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			if (renderedImage == null) {
				return;
			}

			storeThumbnailImages(fileVersion, renderedImage);
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			_fileVersionIds.remove(fileVersion.getFileVersionId());
		}
	}

	@Override
	protected String getPreviewType(FileVersion fileVersion) {
		return null;
	}

	@Override
	protected String getThumbnailType(FileVersion fileVersion) {
		String type = fileVersion.getExtension();

		if (type.equals("jpeg")) {
			type = "jpg";
		}

		return type;
	}

	private boolean _hasImages(FileVersion fileVersion) {
		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
			if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_DEFAULT)) {
				return false;
			}
		}

		try {
			if (isCustomThumbnailsEnabled(1)) {
				if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_CUSTOM_1)) {
					return false;
				}
			}

			if (isCustomThumbnailsEnabled(2)) {
				if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_CUSTOM_2)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileVersionIds.contains(fileVersion.getFileVersionId()) &&
			isSupported(fileVersion) && !_hasImages(fileVersion)) {
			_fileVersionIds.add(fileVersion.getFileVersionId());

			if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
				try {
					MessageBusUtil.sendSynchronousMessage(
						DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
						fileVersion);
				}
				catch (MessageBusException mbe) {
					if (_log.isWarnEnabled()) {
						_log.warn(mbe, mbe);
					}
				}
			}
			else {
				MessageBusUtil.sendMessage(
					DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
					fileVersion);
			}
		}
	}

	private void _storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(getPathSegment(groupId, fileEntryId, fileVersionId, false));

		if (custom1ImageId != 0) {
			sb.append(StringPool.DASH);
			sb.append(1);
		}
		else if (custom2ImageId != 0) {
			sb.append(StringPool.DASH);
			sb.append(2);
		}

		sb.append(StringPool.PERIOD);
		sb.append(type);

		String filePath = sb.toString();

		File file = null;

		try {
			file = FileUtil.createTempFile(is);

			addFileToStore(companyId, THUMBNAIL_PATH, filePath, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImageProcessor.class);

	private static ImageProcessor _instance = new ImageProcessor();

	private List<Long> _fileVersionIds = new Vector<Long>();
	private Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES);

}