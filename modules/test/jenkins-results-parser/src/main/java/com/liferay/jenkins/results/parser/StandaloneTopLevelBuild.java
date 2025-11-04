/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class StandaloneTopLevelBuild extends BaseTopLevelBuild {

	public StandaloneTopLevelBuild(String buildURL) {
		super(buildURL);
	}

	public StandaloneTopLevelBuild(
		String buildURL, TopLevelBuild topLevelBuild) {

		super(buildURL, topLevelBuild);
	}

	@Override
	public String getResult() {
		String result = super.getResult();

		if ((getParentBuild() == null) && (result == null)) {
			result = "SUCCESS";
		}

		return result;
	}

}