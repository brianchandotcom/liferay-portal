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

package com.liferay.gradle.plugins.cache;

import com.liferay.gradle.plugins.cache.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class WriteDigestTask extends SourceTask {

	public String getDigest() {
		return FileUtil.getDigest(
			getProject(), getSource(), isExcludeIgnoredFiles());
	}

	@OutputFile
	public File getDigestFile() {
		return GradleUtil.toFile(getProject(), _digestFile);
	}

	public String getOldDigest() {
		try {
			File digestFile = getDigestFile();

			if (!digestFile.exists()) {
				return null;
			}

			return new String(
				Files.readAllBytes(digestFile.toPath()),
				StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	@Input
	public boolean isExcludeIgnoredFiles() {
		return _excludeIgnoredFiles;
	}

	public void setDigestFile(Object digestFile) {
		_digestFile = digestFile;
	}

	public void setExcludeIgnoredFiles(boolean excludeIgnoredFiles) {
		_excludeIgnoredFiles = excludeIgnoredFiles;
	}

	@TaskAction
	public void writeDigest() {
		try {
			Logger logger = getLogger();

			String digest = getDigest();
			File digestFile = getDigestFile();

			Files.write(
				digestFile.toPath(), digest.getBytes(StandardCharsets.UTF_8));

			if (logger.isInfoEnabled()) {
				logger.info("Updated {} to {}", digestFile, digest);
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private Object _digestFile = ".digest";
	private boolean _excludeIgnoredFiles = true;

}