/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;

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

			_checkMissingEmptyLinesAroundHeaders(fileName, parts);

			String[] breakingChanges = parts[1].split("\n----");

			for (String breakingChange : breakingChanges) {
				int alternativesCount = StringUtil.count(
					breakingChange, "## Alternatives");
				int breakingCount = StringUtil.count(
					breakingChange, "# breaking\n");
				int whatCount = StringUtil.count(breakingChange, "## What");
				int whyCount = StringUtil.count(breakingChange, "## Why");

				if ((alternativesCount > 1) || (breakingCount != 1) ||
					(whatCount != 1) || (whyCount != 1)) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"Each breaking change should have one, and only ",
							"one '# breaking', '## What', '## Why' and ## ",
							"'Alternatives'(Optional). Use '----' to split ",
							"each breaking change."));

					return;
				}

				int alternativesPosition = breakingChange.indexOf(
					"## Alternatives");
				int whatPosition = breakingChange.indexOf("## What");
				int whyPosition = breakingChange.indexOf("## Why");

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
					breakingChange, whatPosition);

				String trimmedLine = StringUtil.trimLeading(
					SourceUtil.getLine(breakingChange, lineNumber));

				if (trimmedLine.length() == 7) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"There should be one file path after '## What'"));

					return;
				}

				String filePath = StringUtil.trim(trimmedLine.substring(7));

				if (getPortalContent(filePath, absolutePath, true) == null) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": '",
							filePath, "' points to nonexistent file. '## ",
							"What' should be followed by only one path, which ",
							"is from ", _LIFERAY_PORTAL_MASTER_URL, "."));

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
		}

		for (String header : _BREAKING_CHANGE_HEADER_NAMES) {
			int x = parts[1].indexOf(header);

			if (x == -1) {
				continue;
			}

			if (header.equals("## Alternatives") || header.equals("## Why")) {
				char c = parts[1].charAt(x + header.length());

				if (c != CharPool.NEW_LINE) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect commit message in SHA ", parts[0], ": ",
							"There should be a line break after ' ", header,
							"'"));
				}
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

	private static final String[] _BREAKING_CHANGE_HEADER_NAMES = {
		"----", "## Alternatives", "# breaking", "## What", "## Why"
	};

	private static final String _LIFERAY_PORTAL_MASTER_URL =
		"https://github.com/liferay/liferay-portal/blob/master/";

	private static List<String> _currentBranchFileNames;

}