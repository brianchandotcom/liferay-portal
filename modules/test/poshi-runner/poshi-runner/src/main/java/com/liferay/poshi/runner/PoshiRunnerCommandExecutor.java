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

package com.liferay.poshi.runner;

import org.junit.runner.JUnitCore;

/**
 * @author Yi-Chen Tsai
 */
public class PoshiRunnerCommandExecutor {

	public static void main(String[] args) throws Exception {
		String command = null;

		if (args.length < 1) {
			command = "help";
		}
		else {
			command = args[0];
		}

		if (command.equals("evaluatePoshiConsole")) {
			evaluatePoshiConsole();
		}
		else if (command.equals("help")) {
			commandHelp();
		}
		else if (command.equals("runPoshi")) {
			runPoshi(args);
		}
		else if (command.equals("validatePoshi")) {
			validatePoshi();
		}
		else if (command.equals("writePoshiProperties")) {
			writePoshiProperties();
		}
		else {
			System.out.println("Unrecognized command name: " + command);

			commandHelp();
		}
	}

	protected static void commandHelp() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("Usage: PoshiRunnerCommandExecutor <command> <args>\n");
		sb.append("\n");
		sb.append("command options include:\n");
		sb.append(
			"\tevaluatePoshiConsole\tEvaluate the console output errors.\n");
		sb.append("\trunPoshi\t\t\tExecute tests using Poshi Runner.\n");
		sb.append("\tvalidatePoshi\t\tValidates the Poshi files syntax.\n");
		sb.append(
			"\twritePoshiProperties\tWrite the Poshi properties files.\n");

		System.out.println(sb.toString());
	}

	protected static void evaluatePoshiConsole() throws Exception {
		System.out.println("Executing task: evaluatePoshiConsole");

		PoshiRunnerConsoleEvaluator.main(null);
	}

	protected static void runPoshi(String[] args) throws Exception {
		System.out.println("Executing task: runPoshi");

		JUnitCore.runClasses(PoshiRunner.class);
	}

	protected static void validatePoshi() throws Exception {
		System.out.println("Executing task: validatePoshi");

		PoshiRunnerValidation.main(null);
	}

	protected static void writePoshiProperties() throws Exception {
		System.out.println("Executing task: writePoshiProperties");

		PoshiRunnerContext.main(null);
	}

}