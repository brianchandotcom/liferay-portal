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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.FolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = DLValidator.class
)
public final class DLValidatorImpl implements DLValidator {

	@Override
	public String fixName(String name) {
		if (Validator.isNull(name)) {
			return StringPool.UNDERLINE;
		}

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			name = StringUtil.replace(
				name, blacklistChar, StringPool.UNDERLINE);
		}

		name = _replaceDLCharLastBlacklist(name);

		name = _replaceDLNameBlacklist(name);

		return _replaceDLWebDAVSubstitutionChar(name);
	}

	@Override
	public long getMaxAllowableSize() {
		long dlFileMaxSize = _dlConfiguration.fileMaxSize();
		long uploadServletRequestFileMaxSize =
			_uploadServletRequestConfigurationHelper.getMaxSize();

		if (dlFileMaxSize == 0) {
			return uploadServletRequestFileMaxSize;
		}

		if (uploadServletRequestFileMaxSize == 0) {
			return dlFileMaxSize;
		}

		return Math.min(dlFileMaxSize, uploadServletRequestFileMaxSize);
	}

	@Override
	public boolean isValidName(String name) {
		if (Validator.isNull(name)) {
			return false;
		}

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			if (name.contains(blacklistChar)) {
				return false;
			}
		}

		for (String blacklistLastChar : PropsValues.DL_CHAR_LAST_BLACKLIST) {
			if (blacklistLastChar.startsWith(UnicodeFormatter.UNICODE_PREFIX)) {
				blacklistLastChar = UnicodeFormatter.parseString(
					blacklistLastChar);
			}

			if (name.endsWith(blacklistLastChar)) {
				return false;
			}
		}

		String nameWithoutExtension = FileUtil.stripExtension(name);

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			if (StringUtil.equalsIgnoreCase(
					nameWithoutExtension, blacklistName)) {

				return false;
			}
		}

		return true;
	}

	@Override
	public void validateDirectoryName(String directoryName)
		throws FolderNameException {

		if (!isValidName(directoryName)) {
			throw new FolderNameException(
				"Invalid folder name " + directoryName);
		}
	}

	@Override
	public void validateFileExtension(String fileName)
		throws FileExtensionException {

		boolean validFileExtension = false;

		for (String fileExtension : _dlConfiguration.fileExtensions()) {
			String fileNameExtension = StringUtil.toLowerCase(
				FileUtil.getExtension(fileName));

			if (StringPool.STAR.equals(fileExtension) ||
				StringUtil.equals(
					fileNameExtension,
					StringUtil.toLowerCase(
						StringUtil.replace(
							fileExtension, CharPool.PERIOD,
							StringPool.BLANK)))) {

				validFileExtension = true;

				break;
			}
		}

		if (!validFileExtension) {
			throw new FileExtensionException(
				"Invalid file extension for " + fileName);
		}
	}

	@Override
	public void validateFileName(String fileName) throws FileNameException {
		if (!isValidName(fileName)) {
			throw new FileNameException("Invalid file name " + fileName);
		}

		if (!DLWebDAVUtil.isRepresentableTitle(fileName)) {
			throw new FileNameException(
				"Unrepresentable WebDAV title for file name " + fileName);
		}
	}

	@Override
	public void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException {

		if (bytes == null) {
			throw new FileSizeException("File size is zero for " + fileName);
		}

		validateFileSize(fileName, bytes.length);
	}

	@Override
	public void validateFileSize(String fileName, File file)
		throws FileSizeException {

		if (file == null) {
			throw new FileSizeException("File is null for " + fileName);
		}

		validateFileSize(fileName, file.length());
	}

	@Override
	public void validateFileSize(String fileName, InputStream is)
		throws FileSizeException {

		try {
			if (is == null) {
				throw new FileSizeException(
					"Input stream is null for " + fileName);
			}

			validateFileSize(fileName, is.available());
		}
		catch (IOException ioException) {
			throw new FileSizeException(ioException);
		}
	}

	@Override
	public void validateFileSize(String fileName, long size)
		throws FileSizeException {

		long maxSize = getMaxAllowableSize();

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException(
				StringBundler.concat(
					size, " exceeds the maximum permitted size of ", maxSize,
					" for file ", fileName));
		}
	}

	@Override
	public void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		String sourceFileExtension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNotNull(sourceFileName) &&
			PropsValues.DL_FILE_EXTENSIONS_STRICT_CHECK &&
			!fileExtension.equals(sourceFileExtension)) {

			throw new SourceFileNameException(
				StringBundler.concat(
					"File extension ", sourceFileExtension,
					" is invalid for file name ", sourceFileName));
		}
	}

	@Override
	public void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		if (Validator.isNull(versionLabel)) {
			return;
		}

		if (!DLUtil.isValidVersion(versionLabel)) {
			throw new InvalidFileVersionException(
				"Invalid version label " + versionLabel);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	protected void setDLConfiguration(DLConfiguration dlConfiguration) {
		_dlConfiguration = dlConfiguration;
	}

	private String _replaceDLCharLastBlacklist(String title) {
		String previousTitle = null;

		while (!title.equals(previousTitle)) {
			previousTitle = title;

			for (String blacklistLastChar :
					PropsValues.DL_CHAR_LAST_BLACKLIST) {

				if (blacklistLastChar.startsWith(
						UnicodeFormatter.UNICODE_PREFIX)) {

					blacklistLastChar = UnicodeFormatter.parseString(
						blacklistLastChar);
				}

				if (title.endsWith(blacklistLastChar)) {
					title = StringUtil.replaceLast(
						title, blacklistLastChar, StringPool.BLANK);
				}
			}
		}

		return title;
	}

	private String _replaceDLNameBlacklist(String title) {
		String extension = FileUtil.getExtension(title);
		String nameWithoutExtension = FileUtil.stripExtension(title);

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			if (StringUtil.equalsIgnoreCase(
					nameWithoutExtension, blacklistName)) {

				if (Validator.isNull(extension)) {
					return nameWithoutExtension + StringPool.UNDERLINE;
				}

				return StringBundler.concat(
					nameWithoutExtension, StringPool.UNDERLINE,
					StringPool.PERIOD, extension);
			}
		}

		return title;
	}

	private String _replaceDLWebDAVSubstitutionChar(String title) {
		return StringUtil.replace(
			title, PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR,
			StringPool.UNDERLINE);
	}

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private UploadServletRequestConfigurationHelper
		_uploadServletRequestConfigurationHelper;

}