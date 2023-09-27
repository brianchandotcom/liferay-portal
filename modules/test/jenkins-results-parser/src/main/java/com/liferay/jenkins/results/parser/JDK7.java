/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class JDK7 extends BaseJDK {

	@Override
	public String getAntOpts() {
		String antOpts = System.getenv("ANT_OPTS");

		return antOpts.replace("MetaspaceSize", "PermSize");
	}

	@Override
	public String getName() {
		return "jdk7";
	}

}