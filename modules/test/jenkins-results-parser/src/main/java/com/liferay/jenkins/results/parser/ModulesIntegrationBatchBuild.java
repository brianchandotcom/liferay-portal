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
public class ModulesIntegrationBatchBuild extends BatchBuild {

	public ModulesIntegrationBatchBuild(String url) throws Exception {
		super(url);

		// TODO Auto-generated constructor stub

	}

	public ModulesIntegrationBatchBuild(String url, TopLevelBuild topLevelBuild)
		throws Exception {

		super(url, topLevelBuild);

		// TODO Auto-generated constructor stub

	}

	@Override
	public void update() {
		super.update();

		Build arquillianErrorAxisBuild = null;

		for (Build axisBuild : getDownstreamBuilds("completed")) {
			String axisBuildResult = axisBuild.getResult();

			if (axisBuildResult.equals("SUCCESS")) {
				continue;
			}

			String axisBuildConsoleText = axisBuild.getConsoleText();

			if (axisBuildConsoleText.contains(_ARQUILLIAN_ERROR)) {
				arquillianErrorAxisBuild = axisBuild;

				break;
			}
		}

		if (arquillianErrorAxisBuild != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Arquillian broken connection failure detected at ");
			sb.append(arquillianErrorAxisBuild.getBuildURL());
			sb.append(" This batch will be re-invoked.");

			System.out.println(sb);

			reinvoke();
		}
	}

	private static final String _ARQUILLIAN_ERROR =
		"org.jboss.arquillian.protocol.jmx.JMXMethodExecutor.invoke" +
			"(JMXMethodExecutor.java";

}