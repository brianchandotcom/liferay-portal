/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public abstract class BaseJDK implements JDK {

	@Override
	public String getJavaHome() {
		return JenkinsResultsParserUtil.combine(
			getJavaHomeRoot(), "/", getName());
	}

	protected String getJavaHomeRoot() {
		return "/opt/java";
	}

}