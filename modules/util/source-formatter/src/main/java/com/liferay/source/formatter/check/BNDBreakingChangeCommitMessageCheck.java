/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.io.File;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * @author Alan Huang
 */
public class BNDBreakingChangeCommitMessageCheck extends BaseFileCheck {

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

		if (_hasMajorVersionBump(absolutePath, sourceFormatterArgs)) {
			_checkCommitMessages(fileName, sourceFormatterArgs);
		}

		return content;
	}

	private void _checkCommitMessages(
			String fileName, SourceFormatterArgs sourceFormatterArgs)
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

			_checkMissingEmptyLinesAroundHeaders(fileName, parts);

			String[] breakingChangeReports = parts[1].split("\n----");

			for (String breakingChangeReport : breakingChangeReports) {
				int alternativesCount = StringUtil.count(
					breakingChangeReport, "## Alternatives");
				int breakingCount = StringUtil.count(
					breakingChangeReport, "# breaking\n");
				int whatCount = StringUtil.count(
					breakingChangeReport, "## What");
				int whyCount = StringUtil.count(breakingChangeReport, "## Why");

				if ((alternativesCount > 1) || (breakingCount != 1) ||
					(whatCount != 1) || (whyCount != 1)) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"Each breaking change report should have one, and ",
							"only one '# breaking', '## What', '## Why' and ",
							"'## Alternatives'(Optional). Use '----' to split ",
							"each breaking change."));

					return;
				}

				int alternativesPosition = breakingChangeReport.indexOf(
					"## Alternatives");
				int whatPosition = breakingChangeReport.indexOf("## What");
				int whyPosition = breakingChangeReport.indexOf("## Why");

				if ((whatPosition > whyPosition) ||
					((alternativesPosition != -1) &&
					 (whyPosition > alternativesPosition))) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"The correct order of headers should be '## What' ",
							"| '## Why' | '## Alternatives'"));

					return;
				}

				int lineNumber = SourceUtil.getLineNumber(
					breakingChangeReport, whatPosition);

				String trimmedLine = StringUtil.trimLeading(
					SourceUtil.getLine(breakingChangeReport, lineNumber));

				if (trimmedLine.length() == 7) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"There should be one file path after '## What'"));

					return;
				}

				String filePath = StringUtil.trim(trimmedLine.substring(7));

				File portalDir = getPortalDir();

				File file = new File(portalDir, filePath);

				if (file.exists()) {
					continue;
				}

				List<String> currentBranchDeletedFileNames =
					GitUtil.getCurrentBranchDeletedFileNames(
						sourceFormatterArgs.getBaseDirName(),
						sourceFormatterArgs.getGitWorkingBranchName());

				if (!currentBranchDeletedFileNames.contains(filePath)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"'## What' should be followed by only one path, ",
							"which is from ", portalDir.getAbsolutePath()));

					return;
				}
			}
		}
	}

	private void _checkMissingEmptyLinesAroundHeaders(
		String fileName, String[] parts) {

		if (!parts[1].endsWith("\n\n----")) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Incorrect commit message in SHA ", parts[0], ": The ",
					"commit message contains '# breaking' should end with ",
					"'\\n\\n----'"));

			return;
		}

		for (String header : _BREAKING_CHANGE_REPORT_HEADER_NAMES) {
			int x = parts[1].indexOf(header);

			if (x == -1) {
				continue;
			}

			int lineNumber = SourceUtil.getLineNumber(parts[1], x);

			String nextLine = SourceUtil.getLine(parts[1], lineNumber + 1);
			String previousLine = SourceUtil.getLine(parts[1], lineNumber - 1);

			if (Validator.isNotNull(nextLine) ||
				Validator.isNotNull(previousLine)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect commit message in SHA ", parts[0], ": ",
						"There should be an empty line after/before '----', ",
						"'# breaking', '## What', '## Why' and '## ",
						"Alternatives'"));

				return;
			}
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

	private static final String[] _BREAKING_CHANGE_REPORT_HEADER_NAMES = {
		"----", "## Alternatives", "# breaking", "## What", "## Why"
	};

	private static List<String> _currentBranchFileNames;

}