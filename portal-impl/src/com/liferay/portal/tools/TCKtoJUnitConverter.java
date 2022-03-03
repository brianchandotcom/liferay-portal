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

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class TCKtoJUnitConverter {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		if (args.length == 2) {
			new TCKtoJUnitConverter(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public TCKtoJUnitConverter(String inputFile, String outputDir) {
		try {
			_convert(new File(inputFile), new File(outputDir));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _convert(File inputFile, File outputDir) throws Exception {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new FileReader(inputFile))) {

			String s = StringPool.BLANK;

			while ((s = unsyncBufferedReader.readLine()) != null) {
				if (!s.startsWith("Test finished: ")) {
					continue;
				}

				int x = s.indexOf(StringPool.POUND);

				int y = s.lastIndexOf(StringPool.SLASH, x);

				String className = s.substring(15, y);

				className = StringUtil.replace(
					className, CharPool.SLASH, CharPool.PERIOD);

				y = s.indexOf(StringPool.COLON, y);

				if (y == -1) {
					y = s.length();
				}

				className += StringPool.PERIOD + s.substring(x + 1, y);

				String message = s.substring(y + 2);

				_convert(className, message, outputDir);
			}
		}
	}

	private void _convert(String className, String message, File outputDir)
		throws Exception {

		boolean passed = false;

		if (message.startsWith("Passed.")) {
			passed = true;
		}

		String hostname = GetterUtil.getString(
			System.getProperty("env.USERDOMAIN"));

		hostname = StringUtil.toLowerCase(hostname);

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");

		sb.append("<testsuite errors=\"");

		if (passed) {
			sb.append("0");
		}
		else {
			sb.append("1");
		}

		sb.append("\" failures=\"");

		if (passed) {
			sb.append("1");
		}
		else {
			sb.append("0");
		}

		sb.append("\" hostname=\"");
		sb.append(hostname);
		sb.append("\" name=\"");
		sb.append(className);
		sb.append("\" tests=\"1\" time=\"0.0\" timestamp=\"");
		sb.append(System.currentTimeMillis());
		sb.append("\">\n");
		sb.append("\t<properties>\n");

		Properties properties = System.getProperties();

		List<String> propertyNames = new ArrayList<>(
			properties.stringPropertyNames());

		propertyNames.sort(null);

		for (String propertyName : propertyNames) {
			sb.append("\t\t<property name=\"");
			sb.append(HtmlUtil.escape(propertyName));
			sb.append("\" value=\"");
			sb.append(HtmlUtil.escape(properties.getProperty(propertyName)));
			sb.append("\" />\n");
		}

		sb.append("\t</properties>\n");
		sb.append("\t<testcase classname=\"");
		sb.append(className);
		sb.append("\" name=\"test\" time=\"0.0\"");

		if (passed) {
			sb.append(" />\n");
		}
		else {
			String failureMessage = HtmlUtil.escape(message.substring(8));

			sb.append(">\n");
			sb.append("\t\t<failure message=\"");
			sb.append(failureMessage);
			sb.append("\" type=\"junit.framework.AssertionFailedError\">\n");
			sb.append(failureMessage);
			sb.append("\n\t\t</failure>\n");
			sb.append("\t</testcase>\n");
		}

		sb.append("\t<system-out><![CDATA[]]></system-out>\n");
		sb.append("\t<system-err><![CDATA[]]></system-err>\n");
		sb.append("</testsuite>");

		FileUtil.write(
			StringBundler.concat(outputDir, "/TEST-", className, ".xml"),
			sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TCKtoJUnitConverter.class);

}