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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JIRAUtil {

	public static void validateJIRAProjectNames(
			List<String> commitMessages, List<String> projectNames)
		throws Exception {

		if (projectNames.isEmpty()) {
			return;
		}

		outerLoop:
		for (String commitMessage : commitMessages) {
			if (commitMessage.startsWith("Revert ") ||
				commitMessage.startsWith("artifact:ignore") ||
				commitMessage.startsWith("build.gradle auto SF") ||
				commitMessage.endsWith("/ci-merge.")) {

				continue;
			}

			for (String projectName : projectNames) {
				if (commitMessage.startsWith(projectName)) {
					continue outerLoop;
				}
			}

			throw new Exception(
				StringBundler.concat(
					"Found formatting issues:\n",
					"At least one commit message is missing a reference to a ",
					"required JIRA project: ",
					StringUtil.merge(projectNames, StringPool.COMMA_AND_SPACE),
					". Please verify that the JIRA project keys are specified",
					"in ci.properties in the liferay-portal repository."));
		}
	}

}