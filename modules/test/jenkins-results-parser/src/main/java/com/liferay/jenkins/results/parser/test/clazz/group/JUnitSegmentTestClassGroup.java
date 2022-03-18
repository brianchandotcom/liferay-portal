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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JUnitSegmentTestClassGroup extends SegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		List<String> axisIndexes = new ArrayList<>();

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			axisIndexes.add(String.valueOf(axisIndex));

			AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup(
				axisIndex);

			List<File> testClassFiles = axisTestClassGroup.getTestClassFiles();

			sb.append("TEST_CLASS_GROUP_");
			sb.append(axisIndex);
			sb.append("=");

			for (File testClassFile : testClassFiles) {
				Matcher matcher = _pattern.matcher(testClassFile.toString());

				if (!matcher.find()) {
					continue;
				}

				String classFileName = matcher.group("classFileName");

				sb.append(classFileName.replace(".java", ".class"));

				sb.append(",");
			}

			if (!testClassFiles.isEmpty()) {
				sb.setLength(sb.length() - 1);
			}

			sb.append("\n");
		}

		sb.append("TEST_CLASS_GROUPS=");
		sb.append(JenkinsResultsParserUtil.join(" ", axisIndexes));
		sb.append("\n");

		return sb.toString();
	}

	protected JUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected JUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	private static final Pattern _pattern = Pattern.compile(
		".*/(?<classFileName>com/.*)");

}