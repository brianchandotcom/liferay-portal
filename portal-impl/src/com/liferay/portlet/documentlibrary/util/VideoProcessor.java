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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 */
public class VideoProcessor extends DLPreviewableProcessor {

	public static final String PREVIEW_TYPE = "flv";

	public static final String THUMBNAIL_TYPE = "jpg";

	public static void generateVideo(FileVersion fileVersion) {
		_instance._generateVideo(fileVersion);
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion);
	}

	public static InputStream getThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailAsStream(fileVersion);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailFileSize(fileVersion);
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

	protected String getPreviewType() {
		return PREVIEW_TYPE;
	}

	protected String getThumbnailType() {
		return THUMBNAIL_TYPE;
	}

	private void _generateThumbnailXuggler(
			FileVersion fileVersion, File srcFile, int height, int width)
		throws Exception {

		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File destFile = getThumbnailTempFile(id);

		try {
			IMediaReader iMediaReader = ToolFactory.makeReader(
				srcFile.getCanonicalPath());

			iMediaReader.setBufferedImageTypeToGenerate(
				BufferedImage.TYPE_3BYTE_BGR);

			CaptureFrameListener captureFrameListener =
				new CaptureFrameListener(
					destFile, THUMBNAIL_TYPE, height, width);

			iMediaReader.addListener(captureFrameListener);

			while (iMediaReader.readPacket() == null) {
			}

			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH,
				getThumbnailFilePath(fileVersion), destFile);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			FileUtil.delete(destFile);
		}
	}

	private void _generateVideo(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File file = _getVideoTempFile(id, fileVersion.getExtension());
		File previewTempFile = getPreviewTempFile(id);

		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasVideo(fileVersion)) {

				return;
			}

			if (_isGeneratePreview(fileVersion)) {
				InputStream inputStream = fileVersion.getContentStream(false);

				FileUtil.write(file, inputStream);

				try {
					_generateVideoXuggler(
						fileVersion, file, previewTempFile,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
				}
				catch (Exception e) {
					_log.error(e);
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"Xuggler generated a preview video for " + id);
				}
			}

			if (_isGenerateThumbnail(fileVersion)) {
				try {
					_generateThumbnailXuggler(
						fileVersion, previewTempFile,
						PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
						PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH);
				}
				catch (Exception e) {
					_log.error(e);
				}

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

			FileUtil.delete(previewTempFile);
			FileUtil.delete(file);
		}
	}

	private void _generateVideoXuggler(
			FileVersion fileVersion, File srcFile, File destFile, int height,
			int width)
		throws Exception {

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

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion), destFile);
	}

	private File _getVideoTempFile(String id, String targetExtension) {
		String filePath = _getVideoTempFilePath(id, targetExtension);

		return new File(filePath);
	}

	private String _getVideoTempFilePath(String id, String targetExtension) {
		StringBundler sb = new StringBundler(4);

		sb.append(PREVIEW_TMP_PATH);
		sb.append(id);

		if (PREVIEW_TYPE.equals(targetExtension)) {
			sb.append("_tmp");
		}

		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	private boolean _hasVideo(FileVersion fileVersion) throws Exception {
		long companyId = fileVersion.getCompanyId();

		boolean previewExists = DLStoreUtil.hasFile(
			companyId, REPOSITORY_ID, getPreviewFilePath(fileVersion));
		boolean thumbnailExists = DLStoreUtil.hasFile(
			companyId, REPOSITORY_ID, getThumbnailFilePath(fileVersion));

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewExists && thumbnailExists) {
				return true;
			}
		}
		else if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED && previewExists) {
			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailExists) {

			return true;
		}

		return false;
	}

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		long companyId = fileVersion.getCompanyId();

		String previewFilePath = getPreviewFilePath(fileVersion);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!DLStoreUtil.hasFile(companyId, REPOSITORY_ID, previewFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		long companyId = fileVersion.getCompanyId();

		String fileName = getThumbnailFilePath(fileVersion);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!DLStoreUtil.hasFile(companyId, REPOSITORY_ID, fileName)) {

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

	private static Log _log = LogFactoryUtil.getLog(VideoProcessor.class);

	private static VideoProcessor _instance = new VideoProcessor();

	private static Set<String> _videoMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);

	private List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);
	}

}