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
		sb.append("\techo \"Running local tests.\"\n");
		sb.append("\techo \"\"\n");
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

		sb.append("\tlocal durations=()\n");
		sb.append("\tlocal failed_commands=0\n");
		sb.append("\tlocal results=()\n");
		sb.append("\tlocal total_start_time=${SECONDS}\n\n");

		Set<String> commands = new LinkedHashSet<>();

		_gitWorkingDirectory.setCacheBashCommands(true);
		_gitWorkingDirectory.setUseUpstreamMasterDiffBase(true);

		for (RelevantRule relevantRule :
				relevantRuleEngine.getMatchingRelevantRules(null)) {

			for (RelevantRule.TestScriptCommand testScriptCommandObj :
					relevantRule.getTestScriptCommands()) {

				String testScriptCommand = testScriptCommandObj.getCommand();

				if (testScriptCommand == null) {
					continue;
				}

				String testScriptCommandDir =
					testScriptCommandObj.getCommandDir();

				String relativeDir = "./";

				if (testScriptCommandDir != null) {
					relativeDir = "./" + testScriptCommandDir;
				}

				testScriptCommand = testScriptCommand.replace('\\', '/');
				relativeDir = relativeDir.replace('\\', '/');

				String escapedCommand = testScriptCommand.replace("\"", "\\\"");

				String escapedDir = relativeDir.replace("\"", "\\\"");

				commands.add(
					JenkinsResultsParserUtil.combine(
						"\t\t\"", escapedCommand, "\"\n\t\t\"", escapedDir,
						"\""));
			}
		}

		int total = commands.size();

		if (total == 0) {
			System.out.println("No test commands to execute.");

			return;
		}

		int index = 0;

		for (String command : commands) {
			sb.append("\tlocal command_");
			sb.append(index);
			sb.append("=(\n");
			sb.append(command);
			sb.append("\n\t)\n\n");

			index++;
		}

		sb.append("\tlocal commands_list=(\n");

		for (int i = 0; i < total; i++) {
			sb.append("\t\t\"command_");
			sb.append(i);
			sb.append("\"\n");
		}

		sb.append("\t)\n\n");
		sb.append("\tfor i in \"${!commands_list[@]}\"\n");
		sb.append("\tdo\n");
		sb.append("\t\tlocal command_name=\"${commands_list[${i}]}\"\n");
		sb.append("\t\tlocal command_start_time=${SECONDS}\n");
		sb.append("\t\tlocal exit_code=\"\"\n\n");
		sb.append("\t\tlocal command=\"${command_name}[0]\"\n");
		sb.append("\t\tlocal command_dir=\"${command_name}[1]\"\n\n");
		sb.append("\t\techo \"");
		sb.append(_SEPARATOR);
		sb.append("\"\n");
		sb.append("\t\techo \"Executing command [$((${i} + 1))/");
		sb.append("${#commands_list[@]}]: [${!command_dir}] ${!command}\"\n");
		sb.append("\t\techo \"");
		sb.append(_SEPARATOR);
		sb.append("\"\n\n");
		sb.append("\t\t(\n");
		sb.append("\t\t\tif [ \"${!command_dir}\" != \"./\" ]\n");
		sb.append("\t\t\tthen\n");
		sb.append("\t\t\t\tcd \"${!command_dir}\"\n");
		sb.append("\t\t\tfi\n\n");
		sb.append("\t\t\teval \"${!command}\"\n");
		sb.append("\t\t)\n\n");
		sb.append("\t\texit_code=${?}\n\n");
		sb.append("\t\tdurations[${i}]=$(_format_duration ");
		sb.append("$((${SECONDS} - ${command_start_time})))\n\n");
		sb.append("\t\tif [ \"${exit_code}\" -eq 0 ]\n");
		sb.append("\t\tthen\n");
		sb.append("\t\t\tresults[${i}]=\"SUCCESS\"\n");
		sb.append("\t\telse\n");
		sb.append("\t\t\tresults[${i}]=\"FAILED\"\n\n");
		sb.append("\t\t\tfailed_commands=$((${failed_commands} + 1))\n");
		sb.append("\t\tfi\n\n");
		sb.append("\t\techo \"\"\n");
		sb.append("\tdone\n\n");
		sb.append("\techo \"\"\n");
		sb.append("\techo \"");
		sb.append(_SEPARATOR);
		sb.append("\"\n");
		sb.append("\techo \"Summary of results (Total time: ");
		sb.append(
			"$(_format_duration $((${SECONDS} - ${total_start_time})))):\"\n");
		sb.append("\techo \"Current branch is ");
		sb.append(upstreamMasterAheadBehindDescription);
		sb.append(" upstream/master.\"\n");
		sb.append("\techo \"");
		sb.append(_SEPARATOR);
		sb.append("\"\n\n");
		sb.append("\tfor i in \"${!commands_list[@]}\"\n");
		sb.append("\tdo\n");
		sb.append("\t\tlocal command_name=\"${commands_list[${i}]}\"\n\n");
		sb.append("\t\tlocal command=\"${command_name}[0]\"\n");
		sb.append("\t\tlocal command_dir=\"${command_name}[1]\"\n\n");
		sb.append("\t\tif [ \"${results[${i}]}\" == \"SUCCESS\" ]\n");
		sb.append("\t\tthen\n");
		sb.append("\t\t\tlocal icon=\"✓\"\n");
		sb.append("\t\telse\n");
		sb.append("\t\t\tlocal icon=\"✗\"\n");
		sb.append("\t\tfi\n\n");
		sb.append("\t\tprintf \"[${icon}] %-7s (%s) - %s\\n\" ");
		sb.append("\"${results[${i}]}\" \"${durations[${i}]}\" ");
		sb.append("\"${!command_dir}\"\n");
		sb.append("\t\tprintf \"    Command: %s\\n\\n\" \"${!command}\"\n");
		sb.append("\tdone\n\n");
		sb.append("\techo \"");
		sb.append(_SEPARATOR);
		sb.append("\"\n");
		sb.append("\techo \"\"\n\n");
		sb.append("\tif [ \"${failed_commands}\" -eq 0 ]\n");
		sb.append("\tthen\n");
		sb.append("\t\techo \"All commands executed successfully.\"\n\n");
		sb.append("\t\texit 0\n");
		sb.append("\telse\n");
		sb.append("\t\techo \"${failed_commands} command(s) failed.\"\n\n");
		sb.append("\t\texit 1\n");
		sb.append("\tfi\n");
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

	private static String _getSeparator(int length) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append("=");
		}

		return sb.toString();
	}

	private static final String _SEPARATOR = _getSeparator(80);

	private static final String _TEST_SUITE_NAME = "relevant-local";

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final File _outputDir;

}