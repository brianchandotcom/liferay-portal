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

	public int getBuildNumber();

	public String getBuildURL();

	public String getConsoleText();

	public void addDownstreamBuilds(String... urls);

	public int getDownstreamBuildCount(String status);

	public List<Build> getDownstreamBuilds(String status);

	public String getInvocationURL();

	public String getJobName();

	public String getJobURL();

	public String getMaster();

	public Map<String,String> getParameters();

	public String getParameterValue(String name);

	public Build getParent();

	public String getResult();

	public String getStatus();

	public long getStatusAge();

	public String getStatusReport(int indentSize);

	public String getStatusReport();

	public void reinvoke();

	public void update();

}