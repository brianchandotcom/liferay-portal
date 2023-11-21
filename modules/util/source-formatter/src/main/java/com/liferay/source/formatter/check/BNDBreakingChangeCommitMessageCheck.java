/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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

		if (!sourceFormatterArgs.isFormatCurrentBranch()) {
			return content;
		}

		String currentBranchFileDiff = GitUtil.getCurrentBranchFileDiff(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName(), absolutePath);

		String oldVersion = null;
		String newVersion = null;

		for (String line : StringUtil.splitLines(currentBranchFileDiff)) {
			if (!line.contains("Bundle-Version:")) {
				continue;
			}

			int pos = line.indexOf(":");

			String version = StringUtil.trim(line.substring(pos + 1));

			if (line.startsWith(StringPool.PLUS)) {
				newVersion = version;
			}
			else if (line.startsWith(StringPool.DASH)) {
				oldVersion = version;
			}
		}

		List<String> commitMessages = GitUtil.getCurrentBranchCommitMessages(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		if ((newVersion != null) && (oldVersion != null)) {
			ArtifactVersion newArtifactVersion = new DefaultArtifactVersion(
				newVersion);
			ArtifactVersion oldArtifactVersion = new DefaultArtifactVersion(
				oldVersion);

			if (newArtifactVersion.getMajorVersion() <=
					oldArtifactVersion.getMajorVersion()) {

				return content;
			}

			for (String commitMessage : commitMessages) {
				String[] parts = commitMessage.split(":", 2);

				if (parts[1].contains("# breaking_change_report")) {
					return content;
				}
			}

			int pos = fileName.lastIndexOf(StringPool.SLASH);

			String shortFileName = fileName.substring(pos + 1);

			throw new Exception(
				StringBundler.concat(
					"When ", shortFileName, " updated,\n",
					"# breaking_change_report is necessary"));
		}

		for (String commitMessage : commitMessages) {
			String[] parts = commitMessage.split(":", 2);

			if (!parts[1].contains("# breaking_change_report")) {
				continue;
			}

			_checkMissingEmptyLinesAroundHeaders(parts);

			String[] breakingChangeReports = parts[1].split("\n----");

			for (String breakingChangeReport : breakingChangeReports) {
				int alternativesCount = StringUtil.count(
					breakingChangeReport, "## Alternatives");
				int breakingChangeReportCount = StringUtil.count(
					breakingChangeReport, "# breaking_change_report");
				int whatCount = StringUtil.count(
					breakingChangeReport, "## What");
				int whyCount = StringUtil.count(breakingChangeReport, "## Why");

				if ((alternativesCount > 1) ||
					(breakingChangeReportCount != 1) || (whatCount != 1) ||
					(whyCount != 1)) {

					throw new Exception(
						StringBundler.concat(
							"Found formatting issues in SHA ", parts[0], ":\n",
							"Each breaking change report should have one, and ",
							"only one '# breaking_change_report', '## What', ",
							"'## Why' and '## Alternatives'(Optional). Use ",
							"'----' to split each breaking change."));
				}

				int alternativesPosition = breakingChangeReport.indexOf(
					"## Alternatives");
				int whatPosition = breakingChangeReport.indexOf("## What");
				int whyPosition = breakingChangeReport.indexOf("## Why");

				if ((whatPosition > whyPosition) ||
					((alternativesPosition != -1) &&
					 (whyPosition > alternativesPosition))) {

					throw new Exception(
						StringBundler.concat(
							"Found formatting issues in SHA ", parts[0], ":\n",
							"Incorrect order of headers: The correct order ",
							"should be '## What' | '## Why' | '## ",
							"Alternatives'"));
				}

				int lineNumber = SourceUtil.getLineNumber(
					breakingChangeReport, whatPosition);

				String trimmedLine = StringUtil.trimLeading(
					SourceUtil.getLine(breakingChangeReport, lineNumber));

				if (trimmedLine.length() == 7) {
					throw new Exception(
						StringBundler.concat(
							"Found formatting issues in SHA ", parts[0],
							":\nThere should be one file path after '## ",
							"What'"));
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
					throw new Exception(
						StringBundler.concat(
							"Found formatting issues in SHA ", parts[0], ":\n",
							"'## What' should be followed by only one ",
							"relative path, which is from ",
							portalDir.getAbsolutePath()));
				}
			}
		}

		return content;
	}

	private void _checkMissingEmptyLinesAroundHeaders(String[] parts)
		throws Exception {

		if (!parts[1].endsWith("\n\n----")) {
			throw new Exception(
				StringBundler.concat(
					"Found formatting issues in SHA ", parts[0], ":\n",
					"The commit message contains '# breaking_change_report",
					"' should end with '\\n\\n----'"));
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

				throw new Exception(
					StringBundler.concat(
						"Found formatting issues in SHA ", parts[0], ":\n",
						"There should be an empty line after/before '----', ",
						"'# breaking_change_report', '## What', '## Why' and ",
						"'## Alternatives'"));
			}
		}
	}

	private static final String[] _BREAKING_CHANGE_REPORT_HEADER_NAMES = {
		"----", "## Alternatives", "# breaking_change_report", "## What",
		"## Why"
	};

}