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
			checkCommitMessages(
				fileName, absolutePath, sourceFormatterArgs,
				"the major version bumps up");
		}

		return content;
	}

	private boolean _hasMajorVersionBump(
			String absolutePath, SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		for (String currentBranchFileName :
				getCurrentBranchFileNames(sourceFormatterArgs)) {

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

}