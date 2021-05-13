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
import com.liferay.document.library.kernel.util.AudioConverter;
import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.repository.event.FileVersionPreviewEventListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

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
	public String getType() {
		return DLProcessorConstants.AUDIO_PROCESSOR;
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
		catch (Exception exception) {
			_log.error(exception, exception);
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
		if (_audioMimeTypes.contains(mimeType) && _audioConverter.isEnabled()) {
			return true;
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
			FileVersion fileVersion, File srcFile, File destFile,
			String containerType)
		throws Exception {

		if (hasPreview(fileVersion, containerType)) {
			return;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			FileUtil.write(
				destFile,
				_audioConverter.generateAudioPreview(srcFile, containerType));
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to process ", fileVersion.getFileVersionId(), " ",
					fileVersion.getTitle()),
				exception);
		}

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion, containerType), destFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Generated a ", containerType, " preview audio for ",
					fileVersion.getFileVersionId(), " in ", stopWatch.getTime(),
					"ms"));
		}
	}

	private void _generateAudio(
		FileVersion fileVersion, File srcFile, File[] destFiles) {

		try {
			for (int i = 0; i < destFiles.length; i++) {
				_generateAudio(
					fileVersion, srcFile, destFiles[i], _PREVIEW_TYPES[i]);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
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

		try {
			if (sourceFileVersion != null) {
				copy(sourceFileVersion, destinationFileVersion);

				return;
			}

			if (!_audioConverter.isEnabled() ||
				_hasAudio(destinationFileVersion)) {

				return;
			}

			audioTempFile = FileUtil.createTempFile(
				destinationFileVersion.getExtension());

			if (!hasPreviews(destinationFileVersion)) {
				try (InputStream inputStream =
						destinationFileVersion.getContentStream(false)) {

					FileUtil.write(audioTempFile, inputStream);
				}

				try {
					_generateAudio(
						destinationFileVersion, audioTempFile,
						previewTempFiles);

					_fileVersionPreviewEventListener.onSuccess(
						destinationFileVersion);
				}
				catch (Exception exception) {
					_fileVersionPreviewEventListener.onFailure(
						destinationFileVersion);

					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to process ",
								destinationFileVersion.getFileVersionId(), " ",
								destinationFileVersion.getTitle()));
					}

					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}

					throw exception;
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

			FileUtil.delete(audioTempFile);
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

	private static final Log _log = LogFactoryUtil.getLog(
		AudioProcessorImpl.class);

	private static volatile AudioConverter _audioConverter =
		ServiceProxyFactory.newServiceTrackedInstance(
			AudioConverter.class, AudioProcessorImpl.class, "_audioConverter",
			false);
	private static volatile FileVersionPreviewEventListener
		_fileVersionPreviewEventListener =
			ServiceProxyFactory.newServiceTrackedInstance(
				FileVersionPreviewEventListener.class, AudioProcessorImpl.class,
				"_fileVersionPreviewEventListener", false, false);

	private final Set<String> _audioMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);
	private final List<Long> _fileVersionIds = new Vector<>();

}