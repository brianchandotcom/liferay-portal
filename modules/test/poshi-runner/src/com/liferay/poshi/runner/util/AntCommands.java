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

import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.selenium.LiferaySelenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 */
public class AntCommands implements Callable<Void> {

	public AntCommands(
		LiferaySelenium liferaySelenium, String fileName, String target) {

		_liferaySelenium = liferaySelenium;
		_fileName = fileName;
		_target = target;
	}

	@Override
	public Void call() throws Exception {
		Runtime runtime = Runtime.getRuntime();

		StringBuilder sb = new StringBuilder();

		String projectDirName = PoshiRunnerGetterUtil.getProjectDirName();

		if (!OSDetector.isWindows()) {
			projectDirName = StringUtil.replace(projectDirName, "\\", "//");

			sb.append("/bin/bash ant -f ");
			sb.append(_fileName);
			sb.append(" ");
			sb.append(_target);
			sb.append(" -Dtest.ant.launched.by.selenium=true -Dtest.class=");
			sb.append(PropsValues.TEST_NAME);
		}
		else {
			sb.append("cmd /c ant -f ");
			sb.append(_fileName);
			sb.append(" ");
			sb.append(_target);
			sb.append(" -Dtest.ant.launched.by.selenium=true -Dtest.class=");
			sb.append(PropsValues.TEST_NAME);
		}

		Process process = runtime.exec(
			sb.toString(), null, new File(projectDirName));

		InputStreamReader inputStreamReader = new InputStreamReader(
			process.getInputStream());

		BufferedReader inputBufferedReader = new BufferedReader(
			inputStreamReader);

		String line = null;

		while ((line = inputBufferedReader.readLine()) != null) {
			System.out.println(line);
		}

		InputStreamReader errorStreamReader = new InputStreamReader(
			process.getErrorStream());

		BufferedReader errorBufferedReader = new BufferedReader(
			errorStreamReader);

		if (errorBufferedReader.ready()) {
			while ((line = errorBufferedReader.readLine()) != null) {
				System.out.println(line);
			}

			throw new Exception();
		}

		return null;
	}

	private final String _fileName;
	private final LiferaySelenium _liferaySelenium;
	private final String _target;

}