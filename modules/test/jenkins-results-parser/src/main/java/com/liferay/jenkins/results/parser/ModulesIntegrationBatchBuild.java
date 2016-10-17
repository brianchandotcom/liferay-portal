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

import java.util.ArrayList;
import java.util.List;

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
	public void reinvoke() {
		super.reinvoke();

		verifiedAxisBuilds.clear();
	}

	@Override
	public void update() {
		super.update();

		if (badBuildNumbers.size() > 0) {
			return;
		}

		Build arquillianErrorAxisBuild = null;

		for (Build axisBuild : getDownstreamBuilds("completed")) {
			if (verifiedAxisBuilds.contains(axisBuild)) {
				continue;
			}

			String axisBuildResult = axisBuild.getResult();

			if ((axisBuildResult == null) || axisBuildResult.equals("SUCCESS")) {
				continue;
			}

			String axisBuildConsoleText = axisBuild.getConsoleText();

			if (axisBuildConsoleText.contains(_ARQUILLIAN_ERROR_MARKER)) {
				arquillianErrorAxisBuild = axisBuild;

				break;
			}

			verifiedAxisBuilds.add(axisBuild);
		}

		if (arquillianErrorAxisBuild != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Arquillian broken connection failure ");
			sb.append("detected at ");
			sb.append(arquillianErrorAxisBuild.getBuildURL());
			sb.append(" This batch will be re-invoked.");

			System.out.println(sb);

			reinvoke();
		}
	}

	protected List<Build> verifiedAxisBuilds = new ArrayList<>();

	private static final String _ARQUILLIAN_ERROR_MARKER =
		"org.jboss.arquillian.protocol.jmx.JMXMethodExecutor.invoke" +
			"(JMXMethodExecutor.java";

}