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
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.activator.DDMFormWebConfigurationActivator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carolina Barbosa
 */
@Component(service = DDMFormUploadValidator.class)
public class DDMFormUploadValidator {

	public String[] getGuestUploadFileExtensions() {
		DDMFormWebConfiguration ddmFormWebConfiguration =
			_ddmFormWebConfigurationActivator.getDDMFormWebConfiguration();

		return StringUtil.split(
			ddmFormWebConfiguration.guestUploadFileExtensions());
	}

	public long getGuestUploadMaximumFileSize() {
		DDMFormWebConfiguration ddmFormWebConfiguration =
			_ddmFormWebConfigurationActivator.getDDMFormWebConfiguration();

		return ddmFormWebConfiguration.guestUploadMaximumFileSize() *
			_FILE_LENGTH_MB;
	}

	public void validateFileExtension(String fileName)
		throws FileExtensionException {

		List<String> guestUploadFileExtensions = Arrays.asList(
			getGuestUploadFileExtensions());

		Stream<String> guestUploadFileExtensionStream =
			guestUploadFileExtensions.stream();

		Optional<String> guestUploadFileExtensionOptional =
			guestUploadFileExtensionStream.filter(
				guestUploadFileExtension -> StringUtil.equalsIgnoreCase(
					FileUtil.getExtension(fileName),
					StringUtil.trim(guestUploadFileExtension))
			).findFirst();

		if (!guestUploadFileExtensionOptional.isPresent()) {
			throw new FileExtensionException(
				"Invalid file extension for " + fileName);
		}
	}

	public void validateFileName(String fileName) throws FileNameException {
		Matcher matcher = _fileNamePattern.matcher(fileName);

		if (!matcher.matches()) {
			throw new FileNameException("Invalid file name " + fileName);
		}
	}

	public void validateFileSize(File file, String fileName)
		throws FileSizeException {

		if (file == null) {
			throw new FileSizeException("File is null for " + fileName);
		}

		long guestUploadMaximumFileSize = getGuestUploadMaximumFileSize();

		if (file.length() > guestUploadMaximumFileSize) {
			throw new FileSizeException(
				StringBundler.concat(
					"File ", fileName,
					" exceeds the maximum permitted size of ",
					(double)guestUploadMaximumFileSize / _FILE_LENGTH_MB,
					" MB"));
		}
	}

	protected void unsetDDMFormWebConfigurationActivator(
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator) {

		_ddmFormWebConfigurationActivator = null;
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	private static final Pattern _fileNamePattern = Pattern.compile(
		"^[a-zA-Z0-9]{1,200}\\.[a-zA-Z0-9]{1,10}\\b");

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetDDMFormWebConfigurationActivator"
	)
	private volatile DDMFormWebConfigurationActivator
		_ddmFormWebConfigurationActivator;

}