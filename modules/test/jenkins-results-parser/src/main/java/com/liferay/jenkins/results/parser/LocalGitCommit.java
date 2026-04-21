/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface LocalGitCommit extends GitCommit {

	public List<File> getChangedFiles();

	public GitWorkingDirectory getGitWorkingDirectory();

	public String getPatch();

	public boolean isFileChanged(File file);

	public String toDisplayString();

}