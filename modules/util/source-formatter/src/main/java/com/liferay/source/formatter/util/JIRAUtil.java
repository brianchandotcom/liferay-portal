/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String[] parts = commitMessage.split(":", 2);

			String commitMessageSubject = parts[1];

			int x = parts[1].indexOf("\n");

			if (x != -1) {
				commitMessageSubject = commitMessageSubject.substring(0, x);
			}

			if (commitMessageSubject.startsWith("Revert ") ||
				commitMessageSubject.startsWith("artifact:ignore") ||
				commitMessageSubject.startsWith("build.gradle auto SF") ||
				commitMessageSubject.endsWith("/ci-merge.")) {

				continue;
			}

			for (String projectName : projectNames) {
				if (commitMessageSubject.startsWith(projectName)) {
					continue outerLoop;
				}
			}

			throw new Exception(
				StringBundler.concat(
					"Found formatting issues in SHA ", parts[0], "\n",
					"The commit message is missing a reference to a required ",
					"JIRA project: ",
					StringUtil.merge(projectNames, StringPool.COMMA_AND_SPACE),
					". Please verify that the JIRA project keys are specified",
					"in source-formatter.properties in the liferay-portal ",
					"repository."));
		}
	}

}