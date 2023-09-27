/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Carolina Barbosa
 */
public class DDMFormUploadValidator {

	public static String[] getGuestUploadFileExtensions()
		throws ConfigurationException {

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				DDMFormWebConfiguration.class,
				CompanyThreadLocal.getCompanyId());

		return StringUtil.split(
			ddmFormWebConfiguration.guestUploadFileExtensions());
	}

	public static long getGuestUploadMaximumFileSize()
		throws ConfigurationException {

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				DDMFormWebConfiguration.class,
				CompanyThreadLocal.getCompanyId());

		return ddmFormWebConfiguration.guestUploadMaximumFileSize() *
			_FILE_LENGTH_MB;
	}

	public static void validateFileExtension(String fileName)
		throws ConfigurationException, FileExtensionException {

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

	public static void validateFileSize(File file, String fileName)
		throws ConfigurationException, FileSizeException, InvalidFileException {

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

}