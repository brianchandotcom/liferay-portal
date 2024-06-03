/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * @author Alan Huang
 */
public class BNDBreakingChangeCommitMessageCheck
	extends BaseBreakingChangesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/bnd.bnd") || absolutePath.contains("-test/")) {
			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		String gitWorkingBranchName =
			sourceFormatterArgs.getGitWorkingBranchName();

		if (gitWorkingBranchName.matches("release-\\d{4}\\.q[1-4]")) {
			return content;
		}

		if (_hasMajorVersionBump(absolutePath, sourceFormatterArgs)) {
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
					"messages when the major version bumps up");

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

	private boolean _hasMajorVersionBump(
			String absolutePath, SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		for (String currentBranchFileName :
				_getCurrentBranchFileNames(sourceFormatterArgs)) {

			if (!absolutePath.endsWith(currentBranchFileName)) {
				continue;
			}

			ArtifactVersion newArtifactVersion = null;
			ArtifactVersion oldArtifactVersion = null;

			for (String line :
					StringUtil.splitLines(
						GitUtil.getCurrentBranchFileDiff(
							sourceFormatterArgs.getBaseDirName(),
							sourceFormatterArgs.getGitWorkingBranchName(),
							absolutePath))) {

				if (!line.contains("Bundle-Version:")) {
					continue;
				}

				int pos = line.indexOf(":");

				String version = StringUtil.trim(line.substring(pos + 1));

				if (line.startsWith(StringPool.PLUS)) {
					newArtifactVersion = new DefaultArtifactVersion(version);
				}
				else if (line.startsWith(StringPool.DASH)) {
					oldArtifactVersion = new DefaultArtifactVersion(version);
				}
			}

			if ((newArtifactVersion != null) && (oldArtifactVersion != null) &&
				(newArtifactVersion.getMajorVersion() >
					oldArtifactVersion.getMajorVersion())) {

				return true;
			}
		}

		return false;
	}

	private static List<String> _currentBranchFileNames;

}