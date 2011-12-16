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
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.util.log4j.Log4JUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 */
public class VideoProcessor extends DefaultPreviewableProcessor {

	public static final String THUMBNAIL_TYPE = "jpg";

	public static void generateVideo(FileVersion fileVersion)
		throws Exception {

		_instance._generateVideo(fileVersion);
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion);
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion, type);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion, type);
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

	public static Set<String> getVideoMimeTypes() {
		return _instance._videoMimeTypes;
	}

	public static boolean hasVideo(FileVersion fileVersion) {
		boolean hasVideo = false;

		try {
			hasVideo = _instance._hasVideo(fileVersion);

			if (!hasVideo && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasVideo;
	}

	public static boolean isVideoSupported(FileVersion fileVersion) {
		return _instance.isSupported(fileVersion);
	}

	public static boolean isVideoSupported(String mimeType) {
		return _instance.isSupported(mimeType);
	}

	public VideoProcessor() {
		boolean valid = true;

		if ((_PREVIEW_TYPES.length == 0) || (_PREVIEW_TYPES.length > 2)) {
			valid = false;
		}
		else {
			for (String previewType : _PREVIEW_TYPES) {
				if (!previewType.equals("mp4") && !previewType.equals("ogv")) {
					valid = false;

					break;
				}
			}
		}

		if (!valid && _log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Liferay is incorrectly configured to generate video ");
			sb.append("previews using video containers other than MP4 or ");
			sb.append("OGV. Please change the property ");
			sb.append(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS);
			sb.append(" in portal-ext.properties.");

			_log.warn(sb.toString());
		}

		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);
	}

	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		try {
			if (PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED)) {

				return _videoMimeTypes.contains(mimeType);
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	@Override
	protected String getPreviewType(FileVersion fileVersion) {
		return _PREVIEW_TYPES[0];
	}

	@Override
	protected String[] getPreviewTypes() {
		return _PREVIEW_TYPES;
	}

	@Override
	protected String getThumbnailType(FileVersion fileVersion) {
		return THUMBNAIL_TYPE;
	}

	@Override
	protected void storeThumbnailImages(FileVersion fileVersion, File file)
		throws Exception {

		addFileToStore(
			fileVersion.getCompanyId(), THUMBNAIL_PATH,
			getThumbnailFilePath(fileVersion, THUMBNAIL_INDEX_DEFAULT), file);

		if (isCustomThumbnailsEnabled(1) || isCustomThumbnailsEnabled(2)) {
			ImageBag imageBag = ImageProcessorUtil.read(file);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			storeThumbnailmage(
				fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_1);

			storeThumbnailmage(
				fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_2);
		}
	}

	private void _generateThumbnailXuggler(
			FileVersion fileVersion, File file, int height, int width)
		throws Exception {

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File thumbnailTempFile = getThumbnailTempFile(tempFileId);

		try {
			try {
				ProcessCallable<String> processCallable =
					new LiferayVideoThumbnailProcessCallable(
						ServerDetector.getServerId(),
						PropsUtil.get(PropsKeys.LIFERAY_HOME),
						Log4JUtil.getCustomLogSettings(),
						file.getCanonicalPath(),
						thumbnailTempFile, THUMBNAIL_TYPE, height, width,
						PropsValues.
							DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE);

				ProcessExecutor.execute(
					processCallable, ClassPathUtil.getPortalClassPath());
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			storeThumbnailImages(fileVersion, thumbnailTempFile);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Xuggler generated a thumbnail for " +
						fileVersion.getTitle() + " in " + stopWatch);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			FileUtil.delete(thumbnailTempFile);
		}
	}

	private void _generateVideo(FileVersion fileVersion) throws Exception {
		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File videoTempFile = _getVideoTempFile(
			tempFileId, fileVersion.getExtension());

		File[] previewTempFiles = new File[_PREVIEW_TYPES.length];

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			previewTempFiles[i] = getPreviewTempFile(
				tempFileId, _PREVIEW_TYPES[i]);
		}

		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasVideo(fileVersion)) {

				return;
			}

			File file = null;

			if (_isGeneratePreview(fileVersion) ||
				_isGenerateThumbnail(fileVersion)) {

				if (fileVersion instanceof LiferayFileVersion) {
					try {
						LiferayFileVersion liferayFileVersion =
							(LiferayFileVersion)fileVersion;

						file = liferayFileVersion.getFile(false);
					}
					catch (UnsupportedOperationException uoe) {
					}
				}

				if (file == null) {
					InputStream inputStream = fileVersion.getContentStream(
						false);

					FileUtil.write(videoTempFile, inputStream);

					file = videoTempFile;
				}
			}

			if (_isGeneratePreview(fileVersion)) {
				try {
					_generateVideoXuggler(
						fileVersion, file, previewTempFiles,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			if (_isGenerateThumbnail(fileVersion)) {
				try {
					_generateThumbnailXuggler(
						fileVersion, file,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		finally {
			_fileVersionIds.remove(fileVersion.getFileVersionId());

			for (int i = 0; i < previewTempFiles.length; i++) {
				FileUtil.delete(previewTempFiles[i]);
			}

			FileUtil.delete(videoTempFile);
		}
	}

	private void _generateVideoXuggler(
			FileVersion fileVersion, File srcFile, File destFile,
			String containerType)
		throws Exception {

		if (!_isGeneratePreview(fileVersion, containerType)) {
			return;
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		ProcessCallable<String> processCallable =
			new LiferayVideoProcessCallable(
				ServerDetector.getServerId(),
				PropsUtil.get(PropsKeys.LIFERAY_HOME),
				Log4JUtil.getCustomLogSettings(), srcFile.getCanonicalPath(),
				destFile.getCanonicalPath(), FileUtil.createTempFileName(),
				PropsUtil.getProperties(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO,
					false),
				PropsUtil.getProperties(PropsKeys.XUGGLER_FFPRESET, true));

		ProcessExecutor.execute(
			processCallable, ClassPathUtil.getPortalClassPath());

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion, containerType), destFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Xuggler generated a " + containerType + " preview video for " +
					fileVersion.getTitle() + " in " + stopWatch);
		}
	}

	private void _generateVideoXuggler(
		FileVersion fileVersion, File srcFile, File[] destFiles, int height,
		int width) {

		try {
			for (int i = 0; i < destFiles.length; i++) {
				_generateVideoXuggler(
					fileVersion, srcFile, destFiles[i], _PREVIEW_TYPES[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private File _getVideoTempFile(String tempFileId, String targetExtension) {
		String videoTempFilePath = _getVideoTempFilePath(
			tempFileId, targetExtension);

		return new File(videoTempFilePath);
	}

	private String _getVideoTempFilePath(
		String tempFileId, String targetExtension) {

		StringBundler sb = new StringBundler(5);

		sb.append(PREVIEW_TMP_PATH);
		sb.append(tempFileId);

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			if (_PREVIEW_TYPES[i].equals(targetExtension)) {
				sb.append("_tmp");

				break;
			}
		}

		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	private boolean _hasVideo(FileVersion fileVersion) throws Exception {
		if (!isSupported(fileVersion)) {
			return false;
		}

		int previewsCount = 0;

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			String previewFilePath = getPreviewFilePath(
				fileVersion, _PREVIEW_TYPES[i]);

			if (DLStoreUtil.hasFile(
					fileVersion.getCompanyId(), REPOSITORY_ID,
					previewFilePath)) {

				previewsCount++;
			}
		}

		if (previewsCount != _PREVIEW_TYPES.length) {
			return false;
		}

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

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		int contPreviewsCreated = 0;

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			if (!_isGeneratePreview(fileVersion, _PREVIEW_TYPES[i])) {
				contPreviewsCreated++;
			}
		}

		if (contPreviewsCreated < _PREVIEW_TYPES.length) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGeneratePreview(
			FileVersion fileVersion, String previewType)
		throws Exception {

		String previewFilePath = getPreviewFilePath(fileVersion, previewType);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, previewFilePath)) {

			return true;
		}

		return false;
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		String thumbnailFilePath = getThumbnailFilePath(
			fileVersion, THUMBNAIL_INDEX_DEFAULT);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, thumbnailFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (_fileVersionIds.contains(fileVersion.getFileVersionId()) ||
			!isSupported(fileVersion)) {

			return;
		}

		_fileVersionIds.add(fileVersion.getFileVersionId());

		if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
			try {
				MessageBusUtil.sendSynchronousMessage(
					DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR,
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
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR, fileVersion);
		}
	}

	private static final String[] _PREVIEW_TYPES =
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS;

	private static Log _log = LogFactoryUtil.getLog(VideoProcessor.class);

	private static VideoProcessor _instance = new VideoProcessor();

	private Set<String> _videoMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);
	private List<Long> _fileVersionIds = new Vector<Long>();

	private static class LiferayVideoProcessCallable
		implements ProcessCallable<String> {

		public LiferayVideoProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, String inputURL,
			String outputURL, String tempFileName, Properties videoProperties,
			Properties ffpresetProperties) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputURL = inputURL;
			_outputURL = outputURL;
			_tempFileName = tempFileName;
			_videoProperties = videoProperties;
			_ffpresetProperties = ffpresetProperties;
		}

		public String call() throws ProcessException {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayConverter liferayConverter = new LiferayVideoConverter(
					_inputURL, _outputURL, _tempFileName, _videoProperties,
					_ffpresetProperties);

				liferayConverter.convert();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private Map<String, String> _customLogSettings;
		private Properties _ffpresetProperties;
		private String _inputURL;
		private String _liferayHome;
		private String _outputURL;
		private String _serverId;
		private String _tempFileName;
		private Properties _videoProperties;

	}

	private static class LiferayVideoThumbnailProcessCallable
		implements ProcessCallable<String> {

		public LiferayVideoThumbnailProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, String inputURL,
			File outputFile, String extension, int height, int width,
			int percentage) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputURL = inputURL;
			_outputFile = outputFile;
			_extension = extension;
			_height = height;
			_width = width;
			_percentage = percentage;
		}

		public String call() throws ProcessException {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayConverter liferayConverter =
					new LiferayVideoThumbnailConverter(
						_inputURL, _outputFile, _extension, _height, _width,
						_percentage);

				liferayConverter.convert();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private Map<String, String> _customLogSettings;
		private String _extension;
		private int _height;
		private String _inputURL;
		private String _liferayHome;
		private File _outputFile;
		private int _percentage;
		private String _serverId;
		private int _width;

	}

}