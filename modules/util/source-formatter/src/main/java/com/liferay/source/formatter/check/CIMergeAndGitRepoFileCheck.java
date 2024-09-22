/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.util.List;

/**
 * @author Alan Huang
 */
public class CIMergeAndGitRepoFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!isSubrepository()) {
			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		List<String> currentBranchFileNames = _getCurrentBranchFileNames(
			sourceProcessor.getSourceFormatterArgs());

		for (String currentBranchFileName : currentBranchFileNames) {
			if (absolutePath.endsWith(currentBranchFileName)) {
				addMessage(
					fileName, "Do not add/modify ci-merge and .gitrepo files");

				return content;
			}
		}

		return content;
	}

	private synchronized List<String> _getCurrentBranchFileNames(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchFileNames != null) {
			return _currentBranchFileNames;
		}

		_currentBranchFileNames = GitUtil.getCurrentBranchFileNames(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchFileNames;
	}

	private static List<String> _currentBranchFileNames;

}