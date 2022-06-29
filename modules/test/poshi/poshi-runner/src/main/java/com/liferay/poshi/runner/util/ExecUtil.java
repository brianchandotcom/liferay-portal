/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.util;

import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.OSDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class ExecUtil {

	public static String executeCommand(String command)
		throws IOException, TimeoutException {

		if (!_isWhitelistedExecutable(command)) {
			throw new RuntimeException(
				"Unable to run command: " + command +
					"\nPlease use a whitelisted executable: " +
						_whitelistedExecutableNames);
		}

		Process process = executeCommands(
			true, new File("."), 1000 * 60 * _timeoutDuration, command);

		if (process.exitValue() != 0) {
			return readInputStream(process.getErrorStream(), true);
		}

		return readInputStream(process.getInputStream(), true);
	}

	public static Process executeCommands(
			boolean exitOnFirstFail, File baseDir, long timeout,
			String... commands)
		throws IOException, TimeoutException {

		System.out.print("Executing commands: ");

		for (String command : commands) {
			System.out.println(command);
		}

		String commandTerminator = ";";

		if (exitOnFirstFail) {
			commandTerminator = "&&";
		}

		StringBuffer sb = new StringBuffer();

		for (String command : commands) {
			sb.append(command);
			sb.append(" ");
			sb.append(commandTerminator);
			sb.append(" ");
		}

		sb.append("echo Finished executing commands.\n");

		String[] bashCommands = new String[3];

		if (OSDetector.isWindows()) {
			bashCommands[0] = "CMD";
			bashCommands[1] = "/C";
		}
		else {
			bashCommands[0] = "/bin/sh";
			bashCommands[1] = "-c";
		}

		bashCommands[2] = sb.toString();

		ProcessBuilder processBuilder = new ProcessBuilder(bashCommands);

		processBuilder.directory(baseDir.getAbsoluteFile());

		Process process = new BufferedProcess(2000000, processBuilder.start());

		long duration = 0;
		int returnCode = -1;
		long start = System.currentTimeMillis();

		while (true) {
			try {
				returnCode = process.exitValue();

				if (returnCode == 0) {
					String standardOut = readInputStream(
						process.getInputStream(), true);

					duration = System.currentTimeMillis() - start;

					while (!standardOut.contains(
								"Finished executing commands.") &&
						   (duration < timeout)) {

						sleep(10);

						standardOut = readInputStream(
							process.getInputStream(), true);

						duration = System.currentTimeMillis() - start;
					}
				}

				break;
			}
			catch (IllegalThreadStateException illegalThreadStateException) {
				duration = System.currentTimeMillis() - start;

				if (duration >= timeout) {
					System.out.print(
						"Timeout occurred while executing commands: " +
							Arrays.toString(commands));

					throw illegalThreadStateException;
				}

				sleep(100);
			}
		}

		return process;
	}

	public static Process executeCommands(
			boolean exitOnFirstFail, String... commands)
		throws IOException, TimeoutException {

		return executeCommands(
			exitOnFirstFail, new File("."), _BASH_COMMAND_TIMEOUT_DEFAULT,
			commands);
	}

	public static Process executeCommands(String... commands)
		throws IOException, TimeoutException {

		return executeCommands(
			true, new File("."), _BASH_COMMAND_TIMEOUT_DEFAULT, commands);
	}

	public static String executeScript(String filePath) throws IOException {
		List<String> commands = new ArrayList<>();
		String line = null;

		File file = new File(filePath);

		BufferedReader bufferedReader = new BufferedReader(
			new FileReader(file));

		while ((line = bufferedReader.readLine()) != null) {
			commands.add(line);
		}

		StringBuilder sb = new StringBuilder();

		try {
			for (String command : commands) {
				sb.append(executeCommand(command));
			}
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		return sb.toString();
	}

	public static String readInputStream(InputStream inputStream)
		throws IOException {

		return readInputStream(inputStream, false);
	}

	public static String readInputStream(
			InputStream inputStream, boolean resetAfterReading)
		throws IOException {

		if (resetAfterReading && !inputStream.markSupported()) {
			Class<?> inputStreamClass = inputStream.getClass();

			System.out.println(
				"Unable to reset after reading input stream " +
					inputStreamClass.getName());
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.mark(Integer.MAX_VALUE);
		}

		StringBuffer sb = new StringBuffer();

		byte[] bytes = new byte[1024];

		int size = inputStream.read(bytes);

		while (size > 0) {
			sb.append(new String(Arrays.copyOf(bytes, size)));

			size = inputStream.read(bytes);
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.reset();
		}

		return sb.toString();
	}

	public static void setTimeoutDuration(String timeoutDuration) {
		Long timeoutDurationLong = GetterUtil.getLong(timeoutDuration);

		_timeoutDuration = timeoutDurationLong;
	}

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	private static boolean _isWhitelistedExecutable(String command) {
		for (String executableName : _whitelistedExecutableNames) {
			if (command.startsWith(executableName)) {
				return true;
			}
		}

		return false;
	}

	private static final long _BASH_COMMAND_TIMEOUT_DEFAULT = 1000 * 60 * 60;

	private static long _timeoutDuration = 15;
	private static final List<String> _whitelistedExecutableNames =
		Arrays.asList("git", "grep", "sed");

}