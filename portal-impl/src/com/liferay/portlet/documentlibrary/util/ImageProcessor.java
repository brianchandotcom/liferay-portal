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
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Sergio González
 * @author Alexander Chow
 */
public class ImageProcessor extends DLPreviewableProcessor {

	public static void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public static InputStream getCustom1AsStream(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance._getCustomAsStream(fileVersion, type, 1);
	}

	public static long getCustom1FileSize(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance._getCustomFileSize(fileVersion, type, 1);
	}

	public static InputStream getCustom2AsStream(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance._getCustomAsStream(fileVersion, type, 2);
	}

	public static long getCustom2FileSize(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance._getCustomFileSize(fileVersion, type, 2);
	}

	public static Set<String> getImageMimeTypes() {
		return _imageMimeTypes;
	}

	public static InputStream getThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance.doGetThumbnailAsStream(fileVersion, type);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		String type = _instance._getImageType(fileVersion);

		return _instance.doGetThumbnailFileSize(fileVersion, type);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		boolean hasImages = false;

		try {
			hasImages = _instance._hasImages(fileVersion);

			if (!hasImages) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	public static boolean hasThumbnailImage(FileVersion fileVersion) {
		return _instance._hasThumbnailImage(fileVersion);
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

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	private void _generateImages(FileVersion fileVersion) {
		try {
			InputStream inputStream = fileVersion.getContentStream(false);

			byte[] bytes = FileUtil.getBytes(inputStream);

			ImageBag imageBag = ImageProcessorUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			if (renderedImage == null) {
				return;
			}

			String type = _instance._getImageType(fileVersion);

			_saveThumbnailImage(
				fileVersion, renderedImage,
				PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION,
				getThumbnailFilePath(fileVersion, type));

			if (PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_CUSTOM_1_MAX_DIMENSION) > 0) {

				_saveThumbnailImage(
					fileVersion, renderedImage,
					PropsKeys.IG_IMAGE_CUSTOM_1_MAX_DIMENSION,
					_getCustom1FilePath(fileVersion, type));
			}

			if (PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_CUSTOM_2_MAX_DIMENSION) > 0) {

				_saveThumbnailImage(
					fileVersion, renderedImage,
					PropsKeys.IG_IMAGE_CUSTOM_2_MAX_DIMENSION,
					_getCustom2FilePath(fileVersion, type));
			}
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

	private String _getCustom1FilePath(
		FileVersion fileVersion, String type) {

		return _getCustomFilePath(fileVersion, type, 1);
	}

	private String _getCustom2FilePath(
		FileVersion fileVersion, String type) {

		return _getCustomFilePath(fileVersion, type, 2);
	}

	private InputStream _getCustomAsStream(
			FileVersion fileVersion, String type, int index)
		throws Exception {

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			_getCustomFilePath(fileVersion, type, index));
	}

	private String _getCustomFilePath(
		FileVersion fileVersion, String type, int index) {

		StringBundler sb = new StringBundler(5);

		sb.append(getPathSegment(fileVersion, false));
		sb.append(StringPool.DASH);
		sb.append(index);
		sb.append(StringPool.PERIOD);
		sb.append(type);

		return sb.toString();
	}

	private long _getCustomFileSize(
			FileVersion fileVersion, String type, int index)
		throws Exception {

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			_getCustomFilePath(fileVersion, type, index));
	}

	private String _getImageType(FileVersion fileVersion) {
		String type = fileVersion.getExtension();

		if (type.equals("jpeg")) {
			type = "jpg";
		}

		return type;
	}

	private boolean _hasCustomImage(FileVersion fileVersion, int index) {
		try {
			String type = _getImageType(fileVersion);

			return DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				_getCustomFilePath(fileVersion, type, index));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	private boolean _hasImages(FileVersion fileVersion) {
		if (!_hasThumbnailImage(fileVersion)) {
			return false;
		}

		try {
			if (PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_CUSTOM_1_MAX_DIMENSION) > 0) {

				if (!_hasCustomImage(fileVersion, 1)) {
					return false;
				}
			}

			if (PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_CUSTOM_2_MAX_DIMENSION) > 0) {

				if (!_hasCustomImage(fileVersion, 2)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	private boolean _hasThumbnailImage(FileVersion fileVersion) {
		try {
			String imageType = _getImageType(fileVersion);

			return DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				getThumbnailFilePath(fileVersion, imageType));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	private boolean _isSupportedImage(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		return _imageMimeTypes.contains(fileVersion.getMimeType());
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileVersionIds.contains(fileVersion.getFileVersionId()) &&
			_isSupportedImage(fileVersion) && !_hasImages(fileVersion)) {
			_fileVersionIds.add(fileVersion.getFileVersionId());

			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
				fileVersion);
		}
	}

	private void _saveThumbnailImage(
			FileVersion fileVersion, RenderedImage renderedImage,
			String maxDimensionPropsKey, String filePath)
		throws Exception {

		File file = _scaleImage(
			renderedImage, fileVersion.getMimeType(),
			PrefsPropsUtil.getInteger(maxDimensionPropsKey));

		try {
			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH, filePath, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private File _scaleImage(
			RenderedImage renderedImage, String contentType, int dimension)
		throws IOException {

		RenderedImage thumbnail = ImageProcessorUtil.scale(
			renderedImage, dimension, dimension);

		byte[] bytes = ImageProcessorUtil.getBytes(thumbnail, contentType);

		return FileUtil.createTempFile(bytes);
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

	private static List<Long> _fileVersionIds = new Vector<Long>();
	private static Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.IG_IMAGE_THUMBNAIL_MIME_TYPES);

}