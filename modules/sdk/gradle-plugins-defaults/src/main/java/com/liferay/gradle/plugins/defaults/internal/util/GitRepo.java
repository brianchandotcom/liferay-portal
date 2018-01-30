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

package com.liferay.gradle.plugins.defaults.internal.util;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.gradle.api.UncheckedIOException;

/**
 * @author Andrea Di Giorgi
 */
public class GitRepo {

	public static GitRepo getGitRepo(File dir) {
		dir = GradleUtil.getRootDir(dir, _GIT_REPO_FILE_NAME);

		if (dir == null) {
			return null;
		}

		String content;

		try {
			File file = new File(dir, _GIT_REPO_FILE_NAME);

			content = new String(
				Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		boolean readOnly = false;

		if (content.contains("mode = pull")) {
			readOnly = true;
		}

		return new GitRepo(dir, readOnly);
	}

	public final File dir;
	public final boolean readOnly;

	private GitRepo(File dir, boolean readOnly) {
		this.dir = dir;
		this.readOnly = readOnly;
	}

	private static final String _GIT_REPO_FILE_NAME = ".gitrepo";

}