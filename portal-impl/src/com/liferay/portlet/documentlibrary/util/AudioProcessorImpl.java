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

import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemEnv;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.util.log4j.Log4JUtil;

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
public class AudioProcessorImpl
	extends DLPreviewableProcessor implements AudioProcessor {

	@Override
	public void afterPropertiesSet() {
		boolean valid = true;

		if ((_PREVIEW_TYPES.length == 0) || (_PREVIEW_TYPES.length > 2)) {
			valid = false;
		}
		else {
			for (String previewType : _PREVIEW_TYPES) {
				if (!previewType.equals("mp3") && !previewType.equals("ogg")) {
					valid = false;

					break;
				}
			}
		}

		if (!valid && _log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Liferay is incorrectly configured to generate video ");
			sb.append("previews using video containers other than MP3 or ");
			sb.append("OGG. Please change the property ");
			sb.append(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS);
			sb.append(" in portal-ext.properties.");

			_log.warn(sb.toString());
		}

		FileUtil.mkdirs(PREVIEW_TMP_PATH);
	}

	@Override
	public void generateAudio(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		_generateAudio(sourceFileVersion, destinationFileVersion);
	}

	@Override
	public Set<String> getAudioMimeTypes() {
		return _audioMimeTypes;
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
	public boolean hasAudio(FileVersion fileVersion) {
		boolean hasAudio = false;

		try {
			hasAudio = _hasAudio(fileVersion);

			if (!hasAudio && isSupported(fileVersion)) {
				_queueGeneration(null, fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasAudio;
	}

	@Override
	public boolean isAudioSupported(FileVersion fileVersion) {
		return isSupported(fileVersion);
	}

	@Override
	public boolean isAudioSupported(String mimeType) {
		return isSupported(mimeType);
	}

	@Override
	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		try {
			if (XugglerUtil.isEnabled()) {
				return _audioMimeTypes.contains(mimeType);
			}
		}
		catch (Exception e) {
		}

		return false;
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

		exportPreviews(portletDataContext, fileEntry, fileEntryElement);
	}

	@Override
	protected void doImportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

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
				if (previewType.equals("mp3") || previewType.equals("ogg")) {
					exportPreview(
						portletDataContext, fileEntry, fileEntryElement,
						"audio", previewType);
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
		return null;
	}

	protected void importPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		if ((_PREVIEW_TYPES.length == 0) || (_PREVIEW_TYPES.length > 2)) {
			return;
		}

		for (String previewType : _PREVIEW_TYPES) {
			if (previewType.equals("mp3") || previewType.equals("ogg")) {
				importPreview(
					portletDataContext, fileEntry, importedFileEntry,
					fileEntryElement, "audio", previewType);
			}
		}
	}

	private void _generateAudio(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		String tempFileId = DLUtil.getTempFileId(
			destinationFileVersion.getFileEntryId(),
			destinationFileVersion.getVersion());

		File[] previewTempFiles = new File[_PREVIEW_TYPES.length];

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			previewTempFiles[i] = getPreviewTempFile(
				tempFileId, _PREVIEW_TYPES[i]);
		}

		File audioTempFile = null;

		InputStream inputStream = null;

		try {
			if (sourceFileVersion != null) {
				copy(sourceFileVersion, destinationFileVersion);

				return;
			}

			if (!XugglerUtil.isEnabled() || _hasAudio(destinationFileVersion)) {
				return;
			}

			audioTempFile = FileUtil.createTempFile(
				destinationFileVersion.getExtension());

			if (!hasPreviews(destinationFileVersion)) {
				File file = null;

				if (destinationFileVersion instanceof LiferayFileVersion) {
					try {
						LiferayFileVersion liferayFileVersion =
							(LiferayFileVersion)destinationFileVersion;

						file = liferayFileVersion.getFile(false);
					}
					catch (UnsupportedOperationException uoe) {
					}
				}

				if (file == null) {
					inputStream = destinationFileVersion.getContentStream(
						false);

					FileUtil.write(audioTempFile, inputStream);

					file = audioTempFile;
				}

				try {
					_generateAudioXuggler(
						destinationFileVersion, file, previewTempFiles);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			_fileVersionIds.remove(destinationFileVersion.getFileVersionId());

			for (int i = 0; i < previewTempFiles.length; i++) {
				FileUtil.delete(previewTempFiles[i]);
			}

			FileUtil.delete(audioTempFile);
		}
	}

	private void _generateAudioXuggler(
			FileVersion fileVersion, File srcFile, File destFile,
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
					new LiferayAudioProcessCallable(
						ServerDetector.getServerId(),
						PropsUtil.get(PropsKeys.LIFERAY_HOME),
						Log4JUtil.getCustomLogSettings(), srcFile, destFile,
						containerType,
						PropsUtil.getProperties(
							PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO, false));

				ProcessChannel<String> processChannel =
					ProcessExecutorUtil.execute(
						ClassPathUtil.getPortalProcessConfig(),
						processCallable);

				Future<String> future =
					processChannel.getProcessNoticeableFuture();

				String processIdentity = String.valueOf(
					fileVersion.getFileVersionId());

				futures.put(processIdentity, future);

				future.get();
			}
			else {
				LiferayConverter liferayConverter = new LiferayAudioConverter(
					srcFile.getCanonicalPath(), destFile.getCanonicalPath(),
					containerType,
					PropsUtil.getProperties(
						PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO, false));

				liferayConverter.convert();
			}
		}
		catch (CancellationException ce) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Cancellation received for " +
						fileVersion.getFileVersionId() + " " +
							fileVersion.getTitle());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion, containerType), destFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Xuggler generated a " + containerType + " preview audio for " +
					fileVersion.getFileVersionId() + " in " +
						stopWatch.getTime() + "ms");
		}
	}

	private void _generateAudioXuggler(
		FileVersion fileVersion, File srcFile, File[] destFiles) {

		try {
			for (int i = 0; i < destFiles.length; i++) {
				_generateAudioXuggler(
					fileVersion, srcFile, destFiles[i], _PREVIEW_TYPES[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private boolean _hasAudio(FileVersion fileVersion) throws Exception {
		if (!isSupported(fileVersion)) {
			return false;
		}

		return hasPreviews(fileVersion);
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
			DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR,
			sourceFileVersion, destinationFileVersion);
	}

	private static final String[] _PREVIEW_TYPES =
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS;

	private static Log _log = LogFactoryUtil.getLog(AudioProcessorImpl.class);

	private Set<String> _audioMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);
	private List<Long> _fileVersionIds = new Vector<Long>();

	private static class LiferayAudioProcessCallable
		implements ProcessCallable<String> {

		public LiferayAudioProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, File inputFile,
			File outputFile, String audioContainer,
			Properties audioProperties) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputFile = inputFile;
			_outputFile = outputFile;
			_audioContainer = audioContainer;
			_audioProperties = audioProperties;
		}

		@Override
		public String call() throws ProcessException {
			XugglerAutoInstallHelper.installNativeLibraries();

			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayConverter liferayConverter = new LiferayAudioConverter(
					_inputFile.getCanonicalPath(),
					_outputFile.getCanonicalPath(), _audioContainer,
					_audioProperties);

				liferayConverter.convert();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private static final long serialVersionUID = 1L;

		private String _audioContainer;
		private Properties _audioProperties;
		private Map<String, String> _customLogSettings;

		@InputResource
		private File _inputFile;

		private String _liferayHome;

		@OutputResource
		private File _outputFile;

		private String _serverId;

	}

}