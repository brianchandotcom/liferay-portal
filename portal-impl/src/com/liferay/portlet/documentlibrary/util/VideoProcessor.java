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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 */
public class VideoProcessor implements DLProcessor {

	public static final String PREVIEW_TYPE = "flv";

	public static final String THUMBNAIL_TYPE = "jpg";

	public static void generateVideo(FileVersion fileVersion) {
		_instance._generateVideo(fileVersion);
	}

	public static File getPreviewFile(String id) {
		return _instance._getPreviewFile(id);
	}

	public static File getThumbnailFile(String id) {
		return _instance._getThumbnailFile(id);
	}

	public static boolean hasVideo(FileEntry fileEntry, String version) {
		boolean hasVideo = false;

		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			hasVideo = _instance._hasVideo(fileVersion);

			if (!hasVideo) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasVideo;
	}

	public static boolean isSupportedVideo(
		FileEntry fileEntry, String version) {

		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			return _instance._isSupportedVideo(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public void trigger(FileEntry fileEntry) {
		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			_instance._queueGeneration(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
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

	private void _generateVideo(FileVersion fileVersion) {
		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasVideo(fileVersion)) {

				return;
			}

			String id = DLUtil.getTempFileId(
				fileVersion.getFileEntryId(), fileVersion.getVersion());

			File previewFile = _getPreviewFile(id);

			if (_isGeneratePreview(id)) {
				previewFile.createNewFile();

				File tmpFile = _getVideoTmpFile(id, fileVersion.getExtension());

				try {
					InputStream inputStream = fileVersion.getContentStream(
						false);

					FileUtil.write(tmpFile, inputStream);

					_generateVideoXuggler(
						fileVersion, tmpFile, previewFile,
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
			_fileEntries.remove(fileVersion.getFileVersionId());
		}
	}

	private void _generateVideoXuggler(
			FileVersion fileVersion, File srcFile, File destFile, int height,
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

		if (PREVIEW_TYPE.equals(targetExtension)) {
			sb.append("_tmp");
		}

		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	private boolean _hasVideo(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File previewFile = _getPreviewFile(id);

		Date statusDate = fileVersion.getStatusDate();

		if (previewFile.lastModified() < statusDate.getTime()) {
			previewFile.delete();
		}

		File thumbnailFile = _getThumbnailFile(id);

		if (thumbnailFile.lastModified() < statusDate.getTime()) {
			thumbnailFile.delete();
		}

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

	private boolean _isSupportedVideo(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		return _videoMimeTypes.contains(fileVersion.getMimeType());
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileEntries.contains(fileVersion.getFileVersionId()) &&
			_isSupportedVideo(fileVersion)) {

			_fileEntries.add(fileVersion.getFileVersionId());

			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR,
				fileVersion);
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

	private static List<String> _videoMimeTypes =
		Arrays.asList(PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);

	private List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(_PREVIEW_PATH);
		FileUtil.mkdirs(_THUMBNAIL_PATH);
	}

}