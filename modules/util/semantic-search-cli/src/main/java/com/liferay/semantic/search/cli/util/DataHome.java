/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import com.liferay.petra.string.StringBundler;

import java.io.File;

/**
 * Computes the runtime-data directory for this tool — outside the
 * project checkout, as a sibling. The layout is
 * {@code <parent>/<basename>-tools[-<worktree-suffix>]/search/}.
 *
 * Override with the SEARCH_DATA_HOME env var.
 *
 * @author JR Houn
 */
public class DataHome {

	public static File compute(File projectRoot) {
		String override = System.getenv("SEARCH_DATA_HOME");

		if ((override != null) && !override.isEmpty()) {
			File overrideFile = new File(override);

			return overrideFile.getAbsoluteFile();
		}

		File parent = projectRoot.getParentFile();

		String baseName = projectRoot.getName();

		String toolsDir;

		if (baseName.equals(_REPO_BASE_NAME)) {
			toolsDir = _REPO_BASE_NAME + _TOOLS_DIR_SUFFIX;
		}
		else if (baseName.startsWith(_REPO_BASE_NAME + "-")) {
			String worktreeSuffix = baseName.substring(
				_REPO_BASE_NAME.length() + 1);

			toolsDir = StringBundler.concat(
				_REPO_BASE_NAME, _TOOLS_DIR_SUFFIX, "-", worktreeSuffix);
		}
		else {
			toolsDir = baseName + _TOOLS_DIR_SUFFIX;
		}

		return new File(parent, toolsDir + "/" + _TOOL_NAME);
	}

	private static final String _REPO_BASE_NAME = "liferay-learn";

	private static final String _TOOL_NAME = "search";

	private static final String _TOOLS_DIR_SUFFIX = "-tools";

}