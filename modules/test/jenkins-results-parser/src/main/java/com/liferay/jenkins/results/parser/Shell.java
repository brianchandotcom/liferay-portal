/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author Kenji Heigel
 */
public class Shell {

	protected static Process doExecute(
			File baseDir, boolean exitOnFirstFail, boolean printCommands,
			long timeout, String... commands)
		throws IOException, TimeoutException {

		if (printCommands) {
			System.out.print("Executing commands: ");

			for (String command : commands) {
				System.out.println(command);
			}
		}

		String[] bashCommands = new String[3];

		if (JenkinsResultsParserUtil.isWindows()) {
			bashCommands[0] = "C:\\Program Files\\Git\\bin\\sh.exe";
		}
		else {
			bashCommands[0] = "/bin/sh";
		}

		bashCommands[1] = "-c";

		String commandTerminator = ";";

		if (exitOnFirstFail) {
			commandTerminator = "&&";
		}

		StringBuffer sb = new StringBuffer();

		if (JenkinsResultsParserUtil.isWindows()) {
			sb.append("export GIT_ASK_YESNO=false");
			sb.append(commandTerminator);
			sb.append(" ");
		}

		for (String command : commands) {
			if (JenkinsResultsParserUtil.isWindows()) {
				command = command.replaceAll("\\(", "\\\\\\\\(");
				command = command.replaceAll("\\)", "\\\\\\\\)");
			}

			sb.append(command);
			sb.append(commandTerminator);
			sb.append(" ");
		}

		sb.append("echo Finished executing Bash commands.\n");

		bashCommands[2] = sb.toString();

		ProcessBuilder processBuilder = new ProcessBuilder(bashCommands);

		processBuilder.directory(baseDir.getAbsoluteFile());

		Process process = new BufferedProcess(processBuilder.start());

		Thread currentThread = Thread.currentThread();
		long duration = 0;
		long start = System.currentTimeMillis();
		int returnCode = -1;

		while (true) {
			try {
				if (currentThread.isInterrupted()) {
					returnCode = 1;

					process.destroyForcibly();

					break;
				}

				returnCode = process.exitValue();

				if (returnCode == 0) {
					String standardOut =
						JenkinsResultsParserUtil.readInputStream(
							process.getInputStream(), true);

					duration = System.currentTimeMillis() - start;

					while (!standardOut.contains(
								"Finished executing Bash commands.") &&
						   (duration < timeout)) {

						JenkinsResultsParserUtil.sleep(10);

						standardOut = JenkinsResultsParserUtil.readInputStream(
							process.getInputStream(), true);

						duration = System.currentTimeMillis() - start;
					}
				}

				break;
			}
			catch (IllegalThreadStateException illegalThreadStateException) {
				duration = System.currentTimeMillis() - start;

				if (duration >= timeout) {
					process.destroy();

					throw new TimeoutException(
						"Timeout occurred while executing Bash commands: " +
							Arrays.toString(commands));
				}

				returnCode = -1;

				JenkinsResultsParserUtil.sleep(100);
			}
		}

		if (JenkinsResultsParserUtil.debug) {
			System.out.println(
				"Output stream: " +
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream(), true));
		}

		if (JenkinsResultsParserUtil.debug && (returnCode != 0)) {
			System.out.println(
				"Error stream: " +
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream(), true));
		}

		return process;
	}

}
