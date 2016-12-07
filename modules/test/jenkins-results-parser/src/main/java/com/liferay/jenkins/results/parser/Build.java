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

import java.util.List;
import java.util.Map;

/**
 * @author Kevin Yen
 */
public interface Build {

	public void addDownstreamBuilds(String... urls);

	public void archive(String archiveName);

	public String getArchivePath();

	public List<String> getBadBuildURLs();

	public int getBuildNumber();

	public String getBuildURL();

	public String getBuildURLRegex();

	public String getConsoleText();

	public int getDownstreamBuildCount(String status);

	public List<Build> getDownstreamBuilds(String status);

	public String getInvocationURL();

	public String getJobName();

	public String getJobURL();

	public String getMaster();

	public Map<String, String> getParameters();

	public String getParameterValue(String name);

	public Build getParentBuild();

	public String getResult();

	public Map<String, String> getStartPropertiesMap();

	public String getStatus();

	public long getStatusAge();

	public String getStatusReport();

	public String getStatusReport(int indentSize);

	public String getStatusSummary();

	public Map<String, String> getStopPropertiesMap();

	public boolean hasBuildURL(String buildURL);

	public void reinvoke();

	public String replaceBuildURL(String text);

	public void update();

}