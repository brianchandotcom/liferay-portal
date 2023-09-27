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