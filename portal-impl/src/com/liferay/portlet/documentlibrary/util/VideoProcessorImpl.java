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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.repository.event.FileVersionPreviewEventListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemEnv;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 * @author Ivica Cardic
 */
public class VideoProcessorImpl
	extends DLPreviewableProcessor implements VideoProcessor {

	@Override
	public void afterPropertiesSet() {
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

	@Override
	public void generateVideo(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		_generateVideo(sourceFileVersion, destinationFileVersion);
	}

	@Override
	public InputStream getPreviewAsStream(FileVersion fileVersion, String type)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, type);
	}

	@Override
	public long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, type);
	}

	@Override
	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, index);
	}

	@Override
	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, index);
	}

	@Override
	public String getType() {
		return DLProcessorConstants.VIDEO_PROCESSOR;
	}

	@Override
	public Set<String> getVideoMimeTypes() {
		return _videoMimeTypes;
	}

	@Override
	public boolean hasVideo(FileVersion fileVersion) {
		boolean hasVideo = false;

		try {
			hasVideo = _hasVideo(fileVersion);

			if (!hasVideo && isSupported(fileVersion)) {
				_queueGeneration(null, fileVersion);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return hasVideo;
	}

	@Override
	public boolean isSupported(String mimeType) {
		if (_videoMimeTypes.contains(mimeType) && XugglerUtil.isEnabled()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isVideoSupported(FileVersion fileVersion) {
		return isSupported(fileVersion);
	}

	@Override
	public boolean isVideoSupported(String mimeType) {
		return isSupported(mimeType);
	}

	@Override
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		super.trigger(sourceFileVersion, destinationFileVersion);

		_queueGeneration(sourceFileVersion, destinationFileVersion);
	}

	@Override
	protected void doExportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		exportThumbnails(
			portletDataContext, fileEntry, fileEntryElement, "video");

		exportPreviews(portletDataContext, fileEntry, fileEntryElement);
	}

	@Override
	protected void doImportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		importThumbnails(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"video");

		importPreviews(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	protected void exportPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!isSupported(fileVersion) || !hasPreviews(fileVersion)) {
			return;
		}

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			if ((_PREVIEW_TYPES.length == 0) || (_PREVIEW_TYPES.length > 2)) {
				return;
			}

			for (String previewType : _PREVIEW_TYPES) {
				if (previewType.equals("mp4") || previewType.equals("ogv")) {
					exportPreview(
						portletDataContext, fileEntry, fileEntryElement,
						"video", previewType);
				}
			}
		}
	}

	@Override
	protected List<Long> getFileVersionIds() {
		return _fileVersionIds;
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

	protected void importPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		if ((_PREVIEW_TYPES.length == 0) || (_PREVIEW_TYPES.length > 2)) {
			return;
		}

		for (String previewType : _PREVIEW_TYPES) {
			if (previewType.equals("mp4") || previewType.equals("ogv")) {
				importPreview(
					portletDataContext, fileEntry, importedFileEntry,
					fileEntryElement, "video", previewType);
			}
		}
	}

	@Override
	protected void storeThumbnailImages(FileVersion fileVersion, File file)
		throws Exception {

		if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_DEFAULT)) {
			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH,
				getThumbnailFilePath(fileVersion, THUMBNAIL_INDEX_DEFAULT),
				file);
		}

		if (isThumbnailEnabled(THUMBNAIL_INDEX_CUSTOM_1) ||
			isThumbnailEnabled(THUMBNAIL_INDEX_CUSTOM_2)) {

			ImageBag imageBag = ImageToolUtil.read(file);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			storeThumbnailImage(
				fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_1);
			storeThumbnailImage(
				fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_2);
		}
	}

	private void _generateThumbnailXuggler(
		FileVersion fileVersion, File file, int height, int width) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File thumbnailTempFile = getThumbnailTempFile(tempFileId);

		try {
			try {
				if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
					ProcessCallable<String> processCallable =
						new LiferayVideoThumbnailProcessCallable(
							ServerDetector.getServerId(),
							PropsUtil.get(PropsKeys.LIFERAY_HOME),
							Log4JUtil.getCustomLogSettings(), file,
							thumbnailTempFile, THUMBNAIL_TYPE, height, width,
							PropsValues.
								DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE);

					ProcessChannel<String> processChannel =
						_processExecutor.execute(
							PortalClassPathUtil.getPortalProcessConfig(),
							processCallable);

					Future<String> future =
						processChannel.getProcessNoticeableFuture();

					String processIdentity = String.valueOf(
						fileVersion.getFileVersionId());

					futures.put(processIdentity, future);

					future.get();
				}
				else {
					LiferayConverter liferayConverter =
						new LiferayVideoThumbnailConverter(
							file.getCanonicalPath(), thumbnailTempFile,
							THUMBNAIL_TYPE, height, width,
							PropsValues.
								DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE);

					liferayConverter.convert();
				}
			}
			catch (CancellationException cancellationException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Cancellation received for ",
							fileVersion.getFileVersionId(), " ",
							fileVersion.getTitle()));
				}
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to process ", fileVersion.getFileVersionId(),
						" ", fileVersion.getTitle(), "."),
					exception);
			}

			storeThumbnailImages(fileVersion, thumbnailTempFile);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Xuggler generated a thumbnail for ",
						fileVersion.getTitle(), " in ", stopWatch.getTime(),
						" ms"));
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			FileUtil.delete(thumbnailTempFile);
		}
	}

	private void _generateVideo(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (!XugglerUtil.isEnabled() || _hasVideo(destinationFileVersion)) {
			return;
		}

		File[] previewTempFiles = new File[_PREVIEW_TYPES.length];

		File videoTempFile = null;

		try {
			if (sourceFileVersion != null) {
				copy(sourceFileVersion, destinationFileVersion);

				return;
			}

			if (!hasPreviews(destinationFileVersion) ||
				!hasThumbnails(destinationFileVersion)) {

				try (InputStream inputStream =
						destinationFileVersion.getContentStream(false)) {

					videoTempFile = FileUtil.createTempFile(
						destinationFileVersion.getExtension());

					FileUtil.write(videoTempFile, inputStream);
				}
			}

			if (!hasPreviews(destinationFileVersion)) {
				String tempFileId = DLUtil.getTempFileId(
					destinationFileVersion.getFileEntryId(),
					destinationFileVersion.getVersion());

				for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
					previewTempFiles[i] = getPreviewTempFile(
						tempFileId, _PREVIEW_TYPES[i]);
				}

				try {
					_generateVideoXuggler(
						destinationFileVersion, videoTempFile,
						previewTempFiles);
				}
				catch (Exception exception) {
					_fileVersionPreviewEventListener.onFailure(
						destinationFileVersion);

					_log.error(exception, exception);
				}
			}

			if (!hasThumbnails(destinationFileVersion)) {
				try {
					_generateThumbnailXuggler(
						destinationFileVersion, videoTempFile,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException, noSuchFileEntryException);
			}

			_fileVersionPreviewEventListener.onFailure(destinationFileVersion);
		}
		finally {
			_fileVersionIds.remove(destinationFileVersion.getFileVersionId());

			for (File previewTempFile : previewTempFiles) {
				FileUtil.delete(previewTempFile);
			}

			FileUtil.delete(videoTempFile);
		}
	}

	private void _generateVideoXuggler(
			FileVersion fileVersion, File sourceFile, File destinationFile,
			String containerType)
		throws Exception {

		if (hasPreview(fileVersion, containerType)) {
			return;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
				ProcessCallable<String> processCallable =
					new LiferayVideoProcessCallable(
						ServerDetector.getServerId(),
						PropsUtil.get(PropsKeys.LIFERAY_HOME),
						Log4JUtil.getCustomLogSettings(), sourceFile,
						destinationFile, containerType,
						PropsUtil.getProperties(
							PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO, false),
						PropsUtil.getProperties(
							PropsKeys.XUGGLER_FFPRESET, true));

				ProcessChannel<String> processChannel =
					_processExecutor.execute(
						PortalClassPathUtil.getPortalProcessConfig(),
						processCallable);

				Future<String> future =
					processChannel.getProcessNoticeableFuture();

				String processIdentity = String.valueOf(
					fileVersion.getFileVersionId());

				futures.put(processIdentity, future);

				future.get();

				_fileVersionPreviewEventListener.onSuccess(fileVersion);
			}
			else {
				LiferayConverter liferayConverter = new LiferayVideoConverter(
					sourceFile.getCanonicalPath(),
					destinationFile.getCanonicalPath(), containerType,
					PropsUtil.getProperties(
						PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO, false),
					PropsUtil.getProperties(PropsKeys.XUGGLER_FFPRESET, true));

				liferayConverter.convert();

				_fileVersionPreviewEventListener.onSuccess(fileVersion);
			}
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to process ", fileVersion.getFileVersionId(), " ",
					fileVersion.getTitle(), "."),
				exception);

			_fileVersionPreviewEventListener.onFailure(fileVersion);
		}

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion, containerType), destinationFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Xuggler generated a ", containerType,
					" preview video for ", fileVersion.getTitle(), " in ",
					stopWatch.getTime(), " ms"));
		}
	}

	private void _generateVideoXuggler(
		FileVersion fileVersion, File sourceFile, File[] destinationFiles) {

		try {
			for (int i = 0; i < destinationFiles.length; i++) {
				_generateVideoXuggler(
					fileVersion, sourceFile, destinationFiles[i],
					_PREVIEW_TYPES[i]);
			}
		}
		catch (CancellationException cancellationException) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Cancellation received for ",
						fileVersion.getFileVersionId(), " ",
						fileVersion.getTitle()));
			}

			_fileVersionPreviewEventListener.onFailure(fileVersion);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_fileVersionPreviewEventListener.onFailure(fileVersion);
		}
	}

	private boolean _hasVideo(FileVersion fileVersion) throws Exception {
		if (!isSupported(fileVersion)) {
			return false;
		}

		if (hasPreviews(fileVersion) && hasThumbnails(fileVersion)) {
			return true;
		}

		return false;
	}

	private void _queueGeneration(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_fileVersionIds.contains(
				destinationFileVersion.getFileVersionId()) ||
			!isSupported(destinationFileVersion)) {

			return;
		}

		_fileVersionIds.add(destinationFileVersion.getFileVersionId());

		sendGenerationMessage(
			DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR,
			sourceFileVersion, destinationFileVersion);
	}

	private static final String[] _PREVIEW_TYPES =
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS;

	private static final Log _log = LogFactoryUtil.getLog(
		VideoProcessorImpl.class);

	private static volatile FileVersionPreviewEventListener
		_fileVersionPreviewEventListener =
			ServiceProxyFactory.newServiceTrackedInstance(
				FileVersionPreviewEventListener.class, VideoProcessorImpl.class,
				"_fileVersionPreviewEventListener", false, false);
	private static volatile ProcessExecutor _processExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ProcessExecutor.class, VideoProcessorImpl.class, "_processExecutor",
			true);

	private final List<Long> _fileVersionIds = new Vector<>();
	private final Set<String> _videoMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);

	private static class DestroyHangingThreadHelper {

		private static void _destroyHangingThread() {
			for (Thread thread : ThreadUtil.getThreads()) {
				if ((thread != null) && !thread.isDaemon() &&
					!StringUtil.equalsIgnoreCase(thread.getName(), "main")) {

					System.exit(-1);
				}
			}
		}

	}

	private static class LiferayVideoProcessCallable
		implements ProcessCallable<String> {

		public LiferayVideoProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, File inputFile,
			File outputFile, String videoContainer, Properties videoProperties,
			Properties ffpresetProperties) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputFile = inputFile;
			_outputFile = outputFile;
			_videoContainer = videoContainer;
			_videoProperties = videoProperties;
			_ffpresetProperties = ffpresetProperties;
		}

		@Override
		public String call() throws ProcessException {
			XugglerAutoInstallHelper.installNativeLibraries();

			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Class<?> clazz = getClass();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, clazz.getClassLoader(),
				new Log4jLogFactoryImpl(), _customLogSettings);

			try {
				LiferayConverter liferayConverter = new LiferayVideoConverter(
					_inputFile.getCanonicalPath(),
					_outputFile.getCanonicalPath(), _videoContainer,
					_videoProperties, _ffpresetProperties);

				liferayConverter.convert();
			}
			catch (Exception exception) {
				throw new ProcessException(exception);
			}
			finally {
				DestroyHangingThreadHelper._destroyHangingThread();
			}

			return StringPool.BLANK;
		}

		private static final long serialVersionUID = 1L;

		private Map<String, String> _customLogSettings;
		private final Properties _ffpresetProperties;

		@InputResource
		private File _inputFile;

		private String _liferayHome;

		@OutputResource
		private File _outputFile;

		private String _serverId;
		private final String _videoContainer;
		private final Properties _videoProperties;

	}

	private static class LiferayVideoThumbnailProcessCallable
		implements ProcessCallable<String> {

		public LiferayVideoThumbnailProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, File inputFile,
			File outputFile, String extension, int height, int width,
			int percentage) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputFile = inputFile;
			_outputFile = outputFile;
			_extension = extension;
			_height = height;
			_width = width;
			_percentage = percentage;
		}

		@Override
		public String call() throws ProcessException {
			XugglerAutoInstallHelper.installNativeLibraries();

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayConverter liferayConverter =
					new LiferayVideoThumbnailConverter(
						_inputFile.getCanonicalPath(), _outputFile, _extension,
						_height, _width, _percentage);

				liferayConverter.convert();
			}
			catch (Exception exception) {
				throw new ProcessException(exception);
			}
			finally {
				DestroyHangingThreadHelper._destroyHangingThread();
			}

			return StringPool.BLANK;
		}

		private static final long serialVersionUID = 1L;

		private Map<String, String> _customLogSettings;
		private final String _extension;
		private final int _height;

		@InputResource
		private File _inputFile;

		private String _liferayHome;

		@OutputResource
		private File _outputFile;

		private final int _percentage;
		private String _serverId;
		private final int _width;

	}

}