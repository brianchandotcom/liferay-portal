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
import com.xuggle.xuggler.ICodec;

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
public class AudioProcessor extends DLPreviewableProcessor {

	public static final String PREVIEW_TYPE = "mp3";

	public static void generateAudio(FileVersion fileVersion) {
		_instance._generateAudio(fileVersion);
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion);
	}

	public static boolean hasAudio(FileEntry fileEntry, String version) {
		boolean hasAudio = false;

		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			hasAudio = _instance._hasAudio(fileVersion);

			if (!hasAudio) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasAudio;
	}

	public static boolean isSupportedAudio(
		FileEntry fileEntry, String version) {

		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			return _instance._isSupportedAudio(fileVersion);
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

	private void _generateAudio(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File file = _getAudioTempFile(id, fileVersion.getExtension());

		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasAudio(fileVersion)) {

				return;
			}

			if (_isGeneratePreview(fileVersion)) {
				InputStream inputStream = fileVersion.getContentStream(false);

				FileUtil.write(file, inputStream);

				try {
					_generateAudioXuggler(fileVersion, file);
				}
				catch (Exception e) {
					_log.error(e);
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"Xuggler generated a preview audio for " + id);
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

			FileUtil.delete(file);
		}
	}

	private void _generateAudioXuggler(FileVersion fileVersion, File srcFile)
		throws Exception {

		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File destFile = getPreviewTempFile(id);

		try {
			IMediaReader iMediaReader = ToolFactory.makeReader(
				srcFile.getCanonicalPath());

			IMediaWriter iMediaWriter = ToolFactory.makeWriter(
				destFile.getCanonicalPath(), iMediaReader);

			iMediaWriter.addAudioStream(
				0, 0, ICodec.ID.CODEC_ID_MP3, _CHANNELS, _SAMPLE_RATE);

			iMediaReader.addListener(iMediaWriter);

			while (iMediaReader.readPacket() == null) {
			}

			addFileToStore(
				fileVersion.getCompanyId(), PREVIEW_PATH,
				getPreviewFilePath(fileVersion), destFile);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			FileUtil.delete(destFile);
		}
	}

	private File _getAudioTempFile(String id, String targetExtension) {
		String filePath = _getAudioTempFilePath(id, targetExtension);

		return new File(filePath);
	}

	private String _getAudioTempFilePath(String id, String targetExtension) {
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

	private boolean _hasAudio(FileVersion fileVersion) throws Exception {
		long companyId = fileVersion.getCompanyId();

		boolean previewExists = DLStoreUtil.hasFile(
			companyId, REPOSITORY_ID, getPreviewFilePath(fileVersion));

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
			if (previewExists) {
				return true;
			}
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

	private boolean _isSupportedAudio(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		return _audioMimeTypes.contains(fileVersion.getMimeType());
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileEntries.contains(fileVersion.getFileVersionId()) &&
			_isSupportedAudio(fileVersion)) {

			_fileEntries.add(fileVersion.getFileVersionId());

			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR,
				fileVersion);
		}
	}

	private static final int _CHANNELS = 2;

	private static int _SAMPLE_RATE = 44100;

	private static Log _log = LogFactoryUtil.getLog(AudioProcessor.class);

	private static AudioProcessor _instance = new AudioProcessor();

	private static Set<String> _audioMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);

	private List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
	}

}