/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemEnv;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PrefsPropsUtil;
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
import java.util.concurrent.Future;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 */
public class AudioProcessorImpl
	extends DLPreviewableProcessor implements AudioProcessor {

	public static AudioProcessorImpl getInstance() {
		return _instance;
	}

	public void generateAudio(FileVersion fileVersion) throws Exception {
		_instance._generateAudio(fileVersion);
	}

	public Set<String> getAudioMimeTypes() {
		return _instance._audioMimeTypes;
	}

	public InputStream getPreviewAsStream(FileVersion fileVersion, String type)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion, type);
	}

	public long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion, type);
	}

	public boolean hasAudio(FileVersion fileVersion) {
		boolean hasAudio = false;

		try {
			hasAudio = _instance._hasAudio(fileVersion);

			if (!hasAudio && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasAudio;
	}

	public boolean isAudioSupported(FileVersion fileVersion) {
		return _instance.isSupported(fileVersion);
	}

	public boolean isAudioSupported(String mimeType) {
		return _instance.isSupported(mimeType);
	}

	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		try {
			if (PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED)) {

				return _audioMimeTypes.contains(mimeType);
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

	private AudioProcessorImpl() {
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

	private void _generateAudio(FileVersion fileVersion) throws Exception {
		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File[] previewTempFiles = new File[_PREVIEW_TYPES.length];

		for (int i = 0; i < _PREVIEW_TYPES.length; i++) {
			previewTempFiles[i] = getPreviewTempFile(
				tempFileId, _PREVIEW_TYPES[i]);
		}

		File audioTempFile = null;

		InputStream inputStream = null;

		try {
			audioTempFile = FileUtil.createTempFile(fileVersion.getExtension());

			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasAudio(fileVersion)) {

				return;
			}

			if (!hasPreviews(fileVersion)) {
				File file = null;

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
					inputStream = fileVersion.getContentStream(false);

					FileUtil.write(audioTempFile, inputStream);

					file = audioTempFile;
				}

				try {
					_generateAudioXuggler(fileVersion, file, previewTempFiles);
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

			_fileVersionIds.remove(fileVersion.getFileVersionId());

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

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
				ProcessCallable<String> processCallable =
					new LiferayAudioProcessCallable(
						ServerDetector.getServerId(),
						PropsUtil.get(PropsKeys.LIFERAY_HOME),
						Log4JUtil.getCustomLogSettings(),
						srcFile.getCanonicalPath(), destFile.getCanonicalPath(),
						containerType, PropsUtil.getProperties(
							PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO, false));

				Future<String> future = ProcessExecutor.execute(
					ClassPathUtil.getPortalClassPath(), processCallable);

				future.get();
			}
			else {
				LiferayConverter liferayConverter = new LiferayAudioConverter(
					srcFile.getCanonicalPath(), destFile.getCanonicalPath(),
					containerType, PropsUtil.getProperties(
						PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO, false));

				liferayConverter.convert();
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

	private void _queueGeneration(FileVersion fileVersion) {
		if (_fileVersionIds.contains(fileVersion.getFileVersionId()) ||
			!isSupported(fileVersion)) {

			return;
		}

		_fileVersionIds.add(fileVersion.getFileVersionId());

		if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
			try {
				MessageBusUtil.sendSynchronousMessage(
					DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR,
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
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR, fileVersion);
		}
	}

	private static final String[] _PREVIEW_TYPES =
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS;

	private static Log _log = LogFactoryUtil.getLog(AudioProcessor.class);

	private static AudioProcessorImpl _instance = new AudioProcessorImpl();

	static {
		InstancePool.put(AudioProcessorImpl.class.getName(), _instance);
	}

	private Set<String> _audioMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);
	private List<Long> _fileVersionIds = new Vector<Long>();

	private static class LiferayAudioProcessCallable
		implements ProcessCallable<String> {

		public LiferayAudioProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, String inputURL,
			String outputURL, String audioContainer,
			Properties audioProperties) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputURL = inputURL;
			_outputURL = outputURL;
			_audioContainer = audioContainer;
			_audioProperties = audioProperties;
		}

		public String call() throws ProcessException {
			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayConverter liferayConverter = new LiferayAudioConverter(
					_inputURL, _outputURL, _audioContainer, _audioProperties);

				liferayConverter.convert();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private Map<String, String> _customLogSettings;
		private String _inputURL;
		private String _liferayHome;
		private String _outputURL;
		private String _serverId;
		private String _audioContainer;
		private Properties _audioProperties;

	}

}