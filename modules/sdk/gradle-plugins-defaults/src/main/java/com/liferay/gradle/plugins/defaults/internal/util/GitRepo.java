/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static final String GIT_REPO_FILE_NAME = ".gitrepo";

	public static GitRepo getGitRepo(File dir) {
		dir = GradleUtil.getRootDir(dir, GIT_REPO_FILE_NAME);

		if (dir == null) {
			return null;
		}

		String content;

		try {
			File file = new File(dir, GIT_REPO_FILE_NAME);

			content = new String(
				Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
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

}