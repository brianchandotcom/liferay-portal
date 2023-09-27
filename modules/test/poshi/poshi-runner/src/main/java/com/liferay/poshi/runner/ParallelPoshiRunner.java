/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.runner.junit.ParallelParameterized;
import com.liferay.poshi.runner.logger.ParallelPrintStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.nio.file.Files;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Kenji Heigel
 */
@RunWith(ParallelParameterized.class)
public class ParallelPoshiRunner extends PoshiRunner {

	public static ParallelPrintStream systemErrParallelPrintStream =
		new ParallelPrintStream(System.err);
	public static ParallelPrintStream systemOutParallelPrintStream =
		new ParallelPrintStream(System.out);

	@ParallelParameterized.Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		return PoshiRunner.getList();
	}

	@BeforeClass
	public static void setUpClass() {
		System.setErr(systemErrParallelPrintStream);
		System.setOut(systemOutParallelPrintStream);

		Logger rootLogger = Logger.getLogger("");

		for (Handler handler : rootLogger.getHandlers()) {
			rootLogger.removeHandler(handler);
		}

		CustomConsoleHandler customConsoleHandler = new CustomConsoleHandler();

		customConsoleHandler.setOutputStream(systemOutParallelPrintStream);

		rootLogger.addHandler(customConsoleHandler);
	}

	public ParallelPoshiRunner(String namespacedClassCommandName)
		throws Exception {

		super(namespacedClassCommandName);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		PrintStream originalSystemOutPrintStream =
			systemOutParallelPrintStream.getOriginalPrintStream();

		originalSystemOutPrintStream.println(
			"Writing log for " + getTestNamespacedClassCommandName() + " to " +
				ParallelPrintStream.getLogFile());

		PoshiProperties.addThreadBasedPoshiProperties();

		super.setUp();
	}

	@After
	@Override
	public void tearDown() throws Throwable {
		try {
			super.tearDown();
		}
		catch (Throwable throwable) {
			throw throwable;
		}
		finally {
			String testName = getTestNamespacedClassCommandName();

			testName = testName.replace("#", "_");

			File file = new File("test-results/" + testName + "/console.txt");

			File logFile = ParallelPrintStream.getLogFile();

			if (!file.exists()) {
				Files.copy(logFile.toPath(), file.toPath());
			}
			else {
				FileWriter fileWriter = new FileWriter(file, true);

				try (BufferedReader bufferedReader = new BufferedReader(
						new FileReader(logFile))) {

					String line = bufferedReader.readLine();

					while (line != null) {
						fileWriter.write("\n");
						fileWriter.write(line);

						fileWriter.flush();

						line = bufferedReader.readLine();
					}
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}

			ParallelPrintStream.resetPrintStream();
		}
	}

	public static class CustomConsoleHandler extends ConsoleHandler {

		@Override
		protected void setOutputStream(OutputStream outputStream)
			throws SecurityException {

			super.setOutputStream(outputStream);
		}

	}

}