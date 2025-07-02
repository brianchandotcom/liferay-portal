/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator;

import com.liferay.test.report.generator.csv.TestReportCSVWriter;
import com.liferay.test.report.generator.csv.playwright.PlaywrightTestSelector;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Davi Santos
 */
public class TestReportGenerator {

	public static void main(String[] args) throws Exception {
		OutputStream outputStream = new FileOutputStream(
			"record-test-playwright-report.csv");

		PlaywrightTestSelector playwrightTestSelector =
			new PlaywrightTestSelector();

		TestReportCSVWriter.write(
			outputStream, playwrightTestSelector.getPlaywrightTests());
	}

}