/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

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
public class TestScriptGenerator {

	public static void main(String[] args) throws IOException {
		String gitRepositoryDirPath = null;

		if (args.length > 0) {
			gitRepositoryDirPath = args[0];
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryDirPath)) {
			throw new IllegalArgumentException(
				"Git repository directory is null or empty");
		}

		String outputDirPath = null;

		if (args.length > 1) {
			outputDirPath = args[1];
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(outputDirPath)) {
			throw new IllegalArgumentException(
				"Output directory is null or empty");
		}

		TestScriptGenerator testScriptGenerator = new TestScriptGenerator(
			gitRepositoryDirPath, outputDirPath);

		testScriptGenerator.generate();
	}

	public TestScriptGenerator(
		String gitRepositoryDirPath, String outputDirPath) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitRepositoryDirPath)) {
			throw new IllegalArgumentException(
				"Git repository directory is not set");
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				"master", gitRepositoryDirPath);

		_outputDir = new File(outputDirPath);
	}

	public void generate() throws IOException {
		RelevantRuleEngine relevantRuleEngine = new RelevantRuleEngine(
			_gitWorkingDirectory, _TEST_SUITE_NAME);

		StringBuilder sb = new StringBuilder();

		sb.append("#!/bin/bash\n\n");
		sb.append("function main {\n");
		sb.append("\tlocal base_dir=$(git rev-parse --show-toplevel)\n\n");
		sb.append("\tif [ -z \"${base_dir}\" ]\n");
		sb.append("\tthen\n");
		sb.append("\t\techo \"Unable to find base directory.\"\n\n");
		sb.append("\t\texit 1\n");
		sb.append("\tfi\n\n");
		sb.append("\tcd \"${base_dir}\" || exit 1\n\n");
		sb.append("\techo \"This script was generated on branch ");
		sb.append(_gitWorkingDirectory.getCurrentBranchName());
		sb.append(" (");

		LocalGitBranch localGitBranch =
			_gitWorkingDirectory.getCurrentLocalGitBranch();

		String sha = localGitBranch.getSHA();

		sha = sha.substring(0, Math.min(sha.length(), 7));

		sb.append(sha);

		sb.append(") which is ");

		String upstreamMasterAheadBehindDescription =
			_gitWorkingDirectory.getUpstreamMasterAheadBehindDescription();

		sb.append(upstreamMasterAheadBehindDescription);

		sb.append(" upstream/master.\"\n");

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

		sb.append("\tlocal exit_code=0\n\tlocal results_output=\"\"\n\n");

		Set<String> commands = new LinkedHashSet<>();

		_gitWorkingDirectory.setCacheBashCommands(true);
		_gitWorkingDirectory.setUseUpstreamMasterDiffBase(true);

		for (RelevantRule relevantRule :
				relevantRuleEngine.getMatchingRelevantRules(null)) {

			for (RelevantRule.TestScriptCommand testScriptCommand :
					relevantRule.getTestScriptCommands()) {

				String command = testScriptCommand.getCommand();

				if (command == null) {
					continue;
				}

				String commandDir = testScriptCommand.getCommandDir();

				if ((commandDir != null) && !commandDir.equals(".")) {
					command = "cd " + commandDir + " && " + command;
				}

				command = command.replace('\\', '/');

				command = command.replace("\"", "\\\"");

				commands.add(
					JenkinsResultsParserUtil.combine("\"", command, "\""));
			}
		}

		int total = commands.size();

		if (total == 0) {
			System.out.println("No test commands to execute.");

			return;
		}

		for (String command : commands) {
			sb.append("\t_execute_command ");
			sb.append(command);
			sb.append(" \"results_output\" || exit_code=1\n\n");
		}

		sb.append("\techo \"Results:\"\n");
		sb.append("\techo \"\"\n");
		sb.append("\techo -e \"${results_output}\"\n\n");
		sb.append("\texit ${exit_code}\n");
		sb.append("}\n\n");
		sb.append("function _execute_command {\n");
		sb.append("\tlocal command_exit_code=\"\"\n");
		sb.append("\tlocal command_start_time=${SECONDS}\n\n");
		sb.append("\techo \"\"\n");
		sb.append("\techo \"Running: ${1}\"\n\n");
		sb.append("\t(\n");
		sb.append("\t\teval \"${1}\"\n");
		sb.append("\t)\n\n");
		sb.append("\tcommand_exit_code=${?}\n\n");
		sb.append("\tlocal duration=$(_format_duration ");
		sb.append("$((${SECONDS} - ${command_start_time})))\n\n");
		sb.append("\tlocal result=\"SUCCESS\"\n\n");
		sb.append("\tif [ \"${command_exit_code}\" -ne 0 ]\n");
		sb.append("\tthen\n");
		sb.append("\t\tresult=\"FAILED\"\n");
		sb.append("\tfi\n\n");
		sb.append("\teval \"${2}+=\\\"\\${1}\\n    \\${result} in ");
		sb.append("\\${duration}\\n\\n\\\"\"\n\n");
		sb.append("\treturn ${command_exit_code}\n");
		sb.append("}\n\n");
		sb.append("function _format_duration {\n");
		sb.append("\tif [ \"$((${1} / 60))\" -gt 0 ]\n");
		sb.append("\tthen\n");
		sb.append("\t\techo \"$((${1} / 60))m $((${1} % 60))s\"\n");
		sb.append("\telse\n");
		sb.append("\t\techo \"$((${1} % 60))s\"\n");
		sb.append("\tfi\n");
		sb.append("}\n\n");
		sb.append("main");

		String generatedScript = sb.toString();

		File scriptFile = new File(_outputDir, "validate_branch.sh");

		JenkinsResultsParserUtil.write(scriptFile, generatedScript);

		scriptFile.setExecutable(true);
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	private static final String _TEST_SUITE_NAME = "relevant-local";

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final File _outputDir;

}