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

	protected static void evaluatePoshiConsole() {
		System.out.println("Executing task: evaluatePoshiConsole");
	}

	protected static void runPoshi(String[] args) {
		System.out.println("Executing task: runPoshi");
	}

	protected static void taskHelp() {
		System.out.println("List of PoshiRunner tasks:");
		System.out.println("--------------------------");
		System.out.println(
			"evaluatePoshiConsole - Evaluate the console output errors.");
		System.out.println("runPoshi - Execute tests using Poshi Runner.");
		System.out.println("validatePoshi - Validates the Poshi files syntax.");
		System.out.println(
			"writePoshiProperties - Write the Poshi properties files.");
	}

	protected static void validatePoshi() {
		System.out.println("Executing task: validatePoshi");
	}

	protected static void writePoshiProperties() {
		System.out.println("Executing task: writePoshiProperties");
	}

}