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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeServiceTrackerListCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		List<String> importNames = javaTerm.getImportNames();

		String javaTermContent = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.osgi.service.tracker.collections.list." +
					"ServiceTrackerList")) {

			return javaTermContent;
		}

		Matcher matcher = _serviceTrackerListVariablePattern.matcher(
			javaTermContent);

		if (matcher.find()) {
			javaTermContent = StringUtil.replace(
				javaTermContent, matcher.group(1), matcher.group(2));
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_VARIABLE};
	}

	private static final Pattern _serviceTrackerListVariablePattern =
		Pattern.compile("ServiceTrackerList\\s*<((.+), .+)>");

}