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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.util.SystemProperties;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author Juan González
 * @author Sergio González
 */
public class VideoProcessor extends DLProcessor {

	public static final String PREVIEW_TYPE = "flv";

	public static final String THUMBNAIL_TYPE = "jpg";

	public static void generateVideo(FileEntry fileEntry) {
		_instance._generateVideo(fileEntry);
	}

	public static File getPreviewFile(String id) {
		return _instance._getPreviewFile(id);
	}

	public static File getThumbnailFile(String id) {
		return _instance._getThumbnailFile(id);
	}

	public static boolean hasVideo(FileEntry fileEntry) {
		boolean hasVideo = _instance._hasVideo(fileEntry);

		if (!hasVideo) {
			_instance._queueGeneration(fileEntry);
		}

		return hasVideo;
	}

	public static boolean isSupportedVideo(FileEntry fileEntry) {
		return _instance._isSupportedVideo(fileEntry);
	}

	@Override
	public void trigger(FileEntry fileEntry) {
		_instance._queueGeneration(fileEntry);
	}

	private void _generateThumbnailXuggler(
			File srcFile, File thumbnailFile, int height, int width)
		throws SystemException {

		try {
			IMediaReader iMediaReader = ToolFactory.makeReader(
				srcFile.getCanonicalPath());

			iMediaReader.setBufferedImageTypeToGenerate(
				BufferedImage.TYPE_3BYTE_BGR);

			CaptureFrameListener captureFrameListener =
				new CaptureFrameListener(
					thumbnailFile, THUMBNAIL_TYPE, height, width);

			iMediaReader.addListener(captureFrameListener);

			while (iMediaReader.readPacket() == null) {
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private void _generateVideo(FileEntry fileEntry) {
		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasVideo(fileEntry)) {

				return;
			}

			String id = DLUtil.getTempFileId(
				fileEntry.getFileEntryId(),	fileEntry.getVersion());

			File previewFile = _getPreviewFile(id);

			if (_isGeneratePreview(id)) {
				previewFile.createNewFile();

				File tmpFile = _getVideoTmpFile(id, fileEntry.getExtension());

				try {
					InputStream inputStream =
						DLFileEntryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(), fileEntry.getFileEntryId(),
							fileEntry.getVersion(), false);

					FileUtil.write(tmpFile, inputStream);

					_generateVideoXuggler(
						fileEntry, tmpFile, previewFile,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);

					if (_log.isInfoEnabled()) {
						_log.info(
							"Xuggler generated a preview video for " + id);
					}
				}
				finally {
					FileUtil.delete(tmpFile);
				}
			}

			if (_isGenerateThumbnail(id)) {
				File fileThumbnail = _getThumbnailFile(id);

				fileThumbnail.createNewFile();

				_generateThumbnailXuggler(
					previewFile, fileThumbnail,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH);

				if (_log.isInfoEnabled()) {
					_log.info("Xuggler generated a thumbnail for " + id);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			_fileEntries.remove(fileEntry.getFileEntryId());
		}
	}

	private void _generateVideoXuggler(
			FileEntry fileEntry, File srcFile, File destFile, int height,
			int width)
		throws SystemException {

		try {
			IMediaReader iMediaReader = ToolFactory.makeReader(
				srcFile.getCanonicalPath());

			VideoResizer videoResizer = new VideoResizer(height, width);

			iMediaReader.addListener(videoResizer);

			AudioListener audioListener = new AudioListener();

			videoResizer.addListener(audioListener);

			IMediaWriter iMediaWriter = ToolFactory.makeWriter(
				destFile.getCanonicalPath(), iMediaReader);

			audioListener.addListener(iMediaWriter);

			VideoListener videoListener = new VideoListener(height, width);

			iMediaWriter.addListener(videoListener);

			while (iMediaReader.readPacket() == null) {
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private File _getPreviewFile(String id) {
		String filePath = _getPreviewFilePath(id);

		return new File(filePath);
	}

	private String _getPreviewFilePath(String id) {
		StringBundler sb = new StringBundler(4);

		sb.append(_PREVIEW_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(PREVIEW_TYPE);

		return sb.toString();
	}

	private File _getThumbnailFile(String id) {
		String filePath = _getVideoThumbnailPath(id);

		return new File(filePath);
	}

	private String _getVideoThumbnailPath(String id) {
		StringBundler sb = new StringBundler(4);

		sb.append(_THUMBNAIL_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(THUMBNAIL_TYPE);

		return sb.toString();
	}

	private File _getVideoTmpFile(String id, String targetExtension) {
		String filePath = _getVideoTmpFilePath(id, targetExtension);

		return new File(filePath);
	}

	private String _getVideoTmpFilePath(String id, String targetExtension) {
		StringBundler sb = new StringBundler(4);

		sb.append(_PREVIEW_PATH);
		sb.append(id);
		sb.append("_tmp");
		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	private boolean _hasVideo(FileEntry fileEntry) {
		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		File previewFile = _getPreviewFile(id);

		File thumbnailFile = _getThumbnailFile(id);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewFile.exists() && thumbnailFile.exists()) {
				return true;
			}
		}
		else if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
				 previewFile.exists()) {

			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailFile.exists()) {

			return true;
		}

		return false;
	}

	private boolean _isGeneratePreview(String id) {
		File previewFile = _getPreviewFile(id);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!previewFile.exists()) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(String id) {
		File thumbnailFile = _getThumbnailFile(id);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!thumbnailFile.exists()) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isSupportedVideo(FileEntry fileEntry) {
		if (fileEntry == null) {
			return false;
		}

		return _videoMimeTypes.contains(fileEntry.getMimeType());
	}

	private void _queueGeneration(FileEntry fileEntry) {
		if (!_fileEntries.contains(fileEntry.getFileEntryId()) &&
			_isSupportedVideo(fileEntry)) {

			_fileEntries.add(fileEntry.getFileEntryId());

			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR,
				fileEntry);
		}
	}

	private static final String _PREVIEW_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_preview/";

	private static final String _THUMBNAIL_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_thumbnail/";

	private static Log _log = LogFactoryUtil.getLog(VideoProcessor.class);

	private static VideoProcessor _instance = new VideoProcessor();

	private static List<String> _videoMimeTypes = Arrays.asList(
		"video/avi", "video/mp4", "video/mpeg", "video/quicktime",
		"video/x-flv", "video/x-ms-wmv", "video/x-msvideo"
	);

	private List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(_PREVIEW_PATH);
		FileUtil.mkdirs(_THUMBNAIL_PATH);
	}

}