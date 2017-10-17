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
public class PoshiRunnerTaskExecutor {

	public static void main(String[] args) throws Exception {
		String task;

		if (args.length < 1) {
			task = "help";
		}
		else {
			task = args[0];
		}

		switch (task) {
			case "evaluatePoshiConsole":
				evaluatePoshiConsole();
				break;

			case "help":
				taskHelp();
				break;

			case "runPoshi":
				runPoshi(args);
				break;

			case "validatePoshi":
				validatePoshi();
				break;

			case "writePoshiProperties":
				writePoshiProperties();
				break;

			default:
				System.out.println("ERROR: Unrecognized task name: " + task);
				taskHelp();
				break;
		}
	}

	protected static void evaluatePoshiConsole() throws Exception {
		System.out.println("Executing task: evaluatePoshiConsole");

		PoshiRunnerConsoleEvaluator.main(null);
	}

	protected static void runPoshi(String[] args) throws Exception {
		System.out.println("Executing task: runPoshi");

		JUnitCore.runClasses(PoshiRunner.class);
	}

	protected static void taskHelp() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("List of PoshiRunner tasks:\n");
		sb.append("--------------------------\n");
		sb.append(
			"evaluatePoshiConsole - Evaluate the console output errors.\n");
		sb.append("runPoshi - Execute tests using Poshi Runner.\n");
		sb.append("validatePoshi - Validates the Poshi files syntax.\n");
		sb.append("writePoshiProperties - Write the Poshi properties files.\n");

		System.out.println(sb.toString());
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