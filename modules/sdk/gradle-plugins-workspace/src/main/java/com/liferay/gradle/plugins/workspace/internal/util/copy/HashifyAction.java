/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util.copy;

import com.liferay.gradle.util.hash.HashUtil;
import com.liferay.gradle.util.hash.HashValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.file.FileCopyDetails;

/**
 * @author Peter Shin
 */
public class HashifyAction implements Action<FileCopyDetails> {

	public HashifyAction(String regex) {
		_fileNamePattern = Pattern.compile(regex);
	}

	@Override
	public void execute(FileCopyDetails fileCopyDetails) {
		String fileName = fileCopyDetails.getName();

		Matcher matcher = _fileNamePattern.matcher(fileName);

		if (!matcher.matches()) {
			return;
		}

		fileCopyDetails.setName(_getHashedFileName(fileCopyDetails));
	}

	private String _getHashedFileName(FileCopyDetails fileCopyDetails) {
		HashValue hashValue = HashUtil.sha1(fileCopyDetails.getFile());

		String fileName = fileCopyDetails.getName();

		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return fileName + '.' + hashValue.asHexString();
		}

		String extension = fileName.substring(pos + 1);
		String shortFileName = fileName.substring(0, pos);

		return shortFileName + '.' + hashValue.asHexString() + '.' + extension;
	}

	private final Pattern _fileNamePattern;

}