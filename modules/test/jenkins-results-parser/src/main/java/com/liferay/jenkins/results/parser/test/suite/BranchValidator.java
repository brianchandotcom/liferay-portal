/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitRemote;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.LocalGitBranch;

import java.io.File;
import java.io.IOException;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class BranchValidator {

	public static void generateValidateBranchScript(
			String gitRepositoryDirPath, String outputDirPath)
		throws IOException {

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryDirPath)) {
			throw new IllegalArgumentException(
				"Git repository directory is not set");
		}

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				"master", gitRepositoryDirPath, "liferay-portal");

		File outputDir = new File(outputDirPath);

		RelevantRuleEngine relevantRuleEngine = new RelevantRuleEngine(
			gitWorkingDirectory, _TEST_SUITE_NAME);

		StringBuilder sb = new StringBuilder();

		sb.append("#!/bin/bash\n\n");
		sb.append("function main {\n");
		sb.append("\techo \"\"\n");
		sb.append("\techo \"This script was generated on branch ");
		sb.append(gitWorkingDirectory.getCurrentBranchName());
		sb.append(" (");

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		String sha = localGitBranch.getSHA();

		sha = sha.substring(0, Math.min(sha.length(), 7));

		sb.append(sha);

		sb.append(") which is ");

		String upstreamMasterAheadBehindDescription =
			gitWorkingDirectory.getUpstreamMasterAheadBehindDescription();

		sb.append(upstreamMasterAheadBehindDescription);

		sb.append(" upstream/master.\"\n");
		sb.append("\techo \"\"\n");

		if (upstreamMasterAheadBehindDescription.contains("behind")) {
			sb.append("\techo \"Warning: Your branch is behind ");
			sb.append("upstream/master. It is recommended to rebase your ");
			sb.append("branch before running tests.\"\n");
			sb.append("\techo -n \"Do you want to continue anyway? (y/N) \"\n");
			sb.append("\tread -n 1 -r reply\n");
			sb.append("\techo \"\"\n\n");
			sb.append("\tif [ \"${reply}\" != \"y\" ] && ");
			sb.append("[ \"${reply}\" != \"Y\" ]\n");
			sb.append("\tthen\n");
			sb.append("\t\texit 1\n");
			sb.append("\tfi\n\n");
		}
		else {
			sb.append("\n");
		}

		Set<String> commands = new LinkedHashSet<>();

		gitWorkingDirectory.setCacheBashCommands(true);
		gitWorkingDirectory.setUseUpstreamMasterDiffBase(true);

		for (RelevantRule relevantRule :
				relevantRuleEngine.getMatchingRelevantRules(null)) {

			for (RelevantRule.TestScriptCommand testScriptCommand :
					relevantRule.getTestScriptCommands()) {

				String command = testScriptCommand.getCommand();

				if (command == null) {
					continue;
				}

				String commandDirPath = testScriptCommand.getCommandDirPath();

				if ((commandDirPath != null) && !commandDirPath.equals(".")) {
					command = "cd " + commandDirPath + " && " + command;
				}

				command = command.replace('\\', '/');

				command = command.replace("\"", "\\\"");

				commands.add(
					JenkinsResultsParserUtil.combine("\"", command, "\""));
			}
		}

		int commandCount = commands.size();

		if (commandCount == 0) {
			System.out.println("No test commands to execute.");

			return;
		}

		sb.append("\tcd \"$(git rev-parse --show-toplevel)\" || exit 1\n\n");
		sb.append("\tlocal command\n");
		sb.append("\tlocal exit_code=0\n");
		sb.append("\tlocal results=\"\\nResults:\\n\\n\"\n\n");
		sb.append("\tfor command in \\\n");

		int i = 0;

		for (String command : commands) {
			sb.append("\t\t");
			sb.append(command);

			if (i < (commandCount - 1)) {
				sb.append(" \\\n\t\t\\");
			}

			sb.append("\n");

			i++;
		}

		sb.append("\tdo\n");
		sb.append("\t\tif [ \"${exit_code}\" -ne 0 ]\n");
		sb.append("\t\tthen\n");
		sb.append("\t\t\tresults+=\"[DID NOT RUN] ${command}\\n\"\n\n");
		sb.append("\t\t\tcontinue\n");
		sb.append("\t\tfi\n\n");
		sb.append("\t\tlocal start_time=${SECONDS}\n\n");
		sb.append("\t\techo \"\"\n");
		sb.append("\t\techo \"Running: ${command}\"\n");
		sb.append("\t\techo \"\"\n\n");
		sb.append("\t\t(eval \"${command}\")\n\n");
		sb.append("\t\texit_code=${?}\n\n");
		sb.append("\t\tif [ \"${exit_code}\" -ne 0 ]\n");
		sb.append("\t\tthen\n");
		sb.append("\t\t\tresults+=\"[FAILED \"\n");
		sb.append("\t\telse\n");
		sb.append("\t\t\tresults+=\"[SUCCESS \"\n");
		sb.append("\t\tfi\n\n");
		sb.append("\t\tlocal duration=$((SECONDS - ");
		sb.append("start_time))\n\n");
		sb.append("\t\tresults+=\"in $((duration / 60))m ");
		sb.append("$((duration % 60))s] ${command}\\n\"\n");
		sb.append("\tdone\n\n");
		sb.append("\techo -e \"${results}\"\n\n");
		sb.append("\texit ${exit_code}\n");
		sb.append("}\n\n");
		sb.append("main");

		String generatedScript = sb.toString();

		File scriptFile = new File(outputDir, "validate_branch.sh");

		JenkinsResultsParserUtil.write(scriptFile, generatedScript);

		scriptFile.setExecutable(true);
	}

	public static void postValidateBranchCommitStatus(
		String gitRepositoryDirPath) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryDirPath)) {
			throw new IllegalArgumentException(
				"Git repository directory is not set");
		}

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				"master", gitRepositoryDirPath, "liferay-portal");

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				"command -v gh");

			if (process.waitFor() != 0) {
				return;
			}

			GitRemote gitRemote = gitWorkingDirectory.getGitRemote("origin");

			if (gitRemote == null) {
				return;
			}

			String gitHubUsername = GitWorkingDirectory.getGitHubUserName(
				gitRemote);

			if (gitHubUsername == null) {
				return;
			}

			gitHubUsername = gitHubUsername.trim();

			if (gitHubUsername.isEmpty()) {
				return;
			}

			String branchName = gitWorkingDirectory.getCurrentBranchName();

			String remoteGitBranchSHA =
				gitWorkingDirectory.getRemoteGitBranchSHA(
					branchName, gitRemote);

			LocalGitBranch localGitBranch =
				gitWorkingDirectory.getCurrentLocalGitBranch();

			if ((remoteGitBranchSHA == null) ||
				!remoteGitBranchSHA.equals(localGitBranch.getSHA())) {

				return;
			}

			File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

			String command = JenkinsResultsParserUtil.combine(
				"gh api repos/", gitHubUsername, "/liferay-portal/statuses/",
				localGitBranch.getSHA(),
				" -f context=\"gw validateBranch\" -f description=\"All tasks ",
				"and tests passed local testing\" -f state=\"success\"");

			process = JenkinsResultsParserUtil.executeBashCommands(
				workingDirectory, command);

			process.waitFor();
		}
		catch (Exception exception) {
			System.out.println("Unable to update GitHub post commit status");
		}
	}

	private static final String _TEST_SUITE_NAME = "relevant-local";

}