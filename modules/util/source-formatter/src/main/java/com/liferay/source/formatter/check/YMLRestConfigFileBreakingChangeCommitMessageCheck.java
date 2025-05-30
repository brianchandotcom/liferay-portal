/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class YMLRestConfigFileBreakingChangeCommitMessageCheck
	extends BaseBreakingChangesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/rest-config.yaml")) {
			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		if (_hasCompatibilityVersionBump(absolutePath, sourceFormatterArgs)) {
			_checkCommitMessages(fileName, absolutePath, sourceFormatterArgs);
		}

		return content;
	}

	private void _checkCommitMessages(
			String fileName, String absolutePath,
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		List<String> commitMessages = GitUtil.getCurrentBranchCommitMessages(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		Iterator<String> iterator = commitMessages.iterator();

		while (iterator.hasNext()) {
			String commitMessage = iterator.next();

			String[] parts = commitMessage.split(":", 2);

			if (!parts[1].contains("# breaking")) {
				iterator.remove();
			}
		}

		if (commitMessages.isEmpty()) {
			addMessage(
				fileName,
				"Incorrect commit message: Missing breaking change in commit " +
					"messages when compatibilityVersion bumps up");

			return;
		}

		for (String commitMessage : commitMessages) {
			String[] parts = commitMessage.split(":", 2);

			if (!parts[1].contains("# breaking")) {
				continue;
			}

			String message =
				"Incorrect commit message in SHA " + parts[0] + ": ";

			checkMissingEmptyLinesAroundHeaders(fileName, parts[1], message);

			checkBreakingChanges(
				fileName, absolutePath, parts[1].split("\n----"), message,
				true);
		}
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

	private boolean _hasCompatibilityVersionBump(
			String absolutePath, SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		for (String currentBranchFileName :
				_getCurrentBranchFileNames(sourceFormatterArgs)) {

			if (!absolutePath.endsWith(currentBranchFileName)) {
				continue;
			}

			String newCompatibilityVersion = null;
			String oldCompatibilityVersion = null;

			String[] lines = StringUtil.splitLines(
				GitUtil.getCurrentBranchFileDiff(
					sourceFormatterArgs.getBaseDirName(),
					sourceFormatterArgs.getGitWorkingBranchName(),
					absolutePath));

			for (String line : lines) {
				if (!line.contains("compatibilityVersion:")) {
					continue;
				}

				int pos = line.indexOf(":");

				String version = StringUtil.trim(line.substring(pos + 1));

				if (line.startsWith(StringPool.PLUS)) {
					newCompatibilityVersion = version;
				}
				else if (line.startsWith(StringPool.DASH)) {
					oldCompatibilityVersion = version;
				}
			}

			return !StringUtil.equals(
				newCompatibilityVersion, oldCompatibilityVersion);
		}

		return false;
	}

	private static List<String> _currentBranchFileNames;

}