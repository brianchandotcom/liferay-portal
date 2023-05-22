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

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.activator.DDMFormWebConfigurationActivator;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(service = DDMFormUploadValidator.class)
public class DDMFormUploadValidator {

	public String[] getGuestUploadFileExtensions() {
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator =
			_ddmFormWebConfigurationActivatorSnapshot.get();

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ddmFormWebConfigurationActivator.getDDMFormWebConfiguration();

		return StringUtil.split(
			ddmFormWebConfiguration.guestUploadFileExtensions());
	}

	public long getGuestUploadMaximumFileSize() {
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator =
			_ddmFormWebConfigurationActivatorSnapshot.get();

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ddmFormWebConfigurationActivator.getDDMFormWebConfiguration();

		return ddmFormWebConfiguration.guestUploadMaximumFileSize() *
			_FILE_LENGTH_MB;
	}

	public void validateFileExtension(String fileName)
		throws FileExtensionException {

		String extension = null;

		for (String guestUploadFileExtension : getGuestUploadFileExtensions()) {
			if (StringUtil.equalsIgnoreCase(
					FileUtil.getExtension(fileName),
					StringUtil.trim(guestUploadFileExtension))) {

				extension = guestUploadFileExtension;

				break;
			}
		}

		if (extension == null) {
			throw new FileExtensionException(
				"Invalid file extension for " + fileName);
		}
	}

	public void validateFileSize(File file, String fileName)
		throws FileSizeException, InvalidFileException {

		if (file == null) {
			throw new InvalidFileException("File is null for " + fileName);
		}

		long guestUploadMaximumFileSize = getGuestUploadMaximumFileSize();

		if (file.length() > guestUploadMaximumFileSize) {
			throw new FileSizeException(
				StringBundler.concat(
					"File ", fileName,
					" exceeds the maximum permitted size of ",
					(double)guestUploadMaximumFileSize / _FILE_LENGTH_MB,
					" MB"),
				guestUploadMaximumFileSize);
		}
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	private static final Snapshot<DDMFormWebConfigurationActivator>
		_ddmFormWebConfigurationActivatorSnapshot = new Snapshot<>(
			DDMFormUploadValidator.class,
			DDMFormWebConfigurationActivator.class, null, true);

}