/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator.csv;

import com.liferay.petra.string.StringPool;
import com.liferay.test.report.generator.csv.playwright.PlaywrightTestReport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.List;

/**
 * @author Davi Santos
 */
public class TestReportCSVWriter {

	public static void write(
		OutputStream outputStream,
		List<PlaywrightTestReport> playwrightTestReports) {

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream);

			BufferedWriter bufferedWriter = new BufferedWriter(
				outputStreamWriter);

			bufferedWriter.write(_CSV_HEADER);
			bufferedWriter.newLine();

			for (PlaywrightTestReport playwrightTestReport :
					playwrightTestReports) {

				bufferedWriter.write(
					String.format(
						_CSV_FORMAT, playwrightTestReport.getClassName(),
						playwrightTestReport.getTestName(),
						playwrightTestReport.isIgnored(),
						playwrightTestReport.getTestFilePath()));

				bufferedWriter.newLine();
			}

			bufferedWriter.flush();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to write Playwright test report to CSV",
				ioException.getCause());
		}
	}

	private static final String _CSV_FORMAT = "%s,%s,%s,%s";

	private static final String _CSV_HEADER = String.join(
		StringPool.COMMA, "Class Name", "Test Name", "Ignored", "File Path");

}