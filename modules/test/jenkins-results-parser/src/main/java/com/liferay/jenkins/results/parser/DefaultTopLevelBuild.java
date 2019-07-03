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

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class DefaultTopLevelBuild extends TopLevelBuild {

	public DefaultTopLevelBuild(String url) {
		super(url);

		// TODO Auto-generated constructor stub

	}

	public DefaultTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		// TODO Auto-generated constructor stub

	}

	@Override
	public String getResult() {
		String result = super.getResult();

		if (!downstreamBuilds.isEmpty() && (result == null)) {
			boolean hasFailure = false;

			for (Build downstreamBuild : downstreamBuilds) {
				String downstreamBuildResult = downstreamBuild.getResult();

				if (downstreamBuildResult == null) {
					setResult(null);

					return null;
				}

				if (!downstreamBuildResult.equals("SUCCESS")) {
					hasFailure = true;
				}
			}

			if (result == null) {
				if (hasFailure) {
					return "FAILURE";
				}

				return "SUCCESS";
			}
		}

		return super.getResult();
	}

}