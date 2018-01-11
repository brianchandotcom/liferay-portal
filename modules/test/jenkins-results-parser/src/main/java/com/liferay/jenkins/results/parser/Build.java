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

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public interface Build {

	public void addDownstreamBuilds(String... urls);

	public void archive(String archiveName);

	public String getAppServer();

	public String getArchivePath();

	public long getAverageDelayTime();

	public List<String> getBadBuildURLs();

	public String getBaseRepositoryName();

	public String getBaseRepositorySHA(String repositoryName);

	public String getBranchName();

	public String getBrowser();

	public JSONObject getBuildJSONObject();

	public int getBuildNumber();

	public String getBuildURL();

	public String getBuildURLRegex();

	public String getConsoleText();

	public String getDatabase();

	public Long getDelayTime();

	public String getDisplayName();

	public int getDownstreamBuildCount(String status);

	public int getDownstreamBuildCount(String result, String status);

	public List<Build> getDownstreamBuilds(String status);

	public List<Build> getDownstreamBuilds(String result, String status);

	public long getDuration();

	public Element getGitHubMessageBuildAnchorElement();

	public Element getGitHubMessageElement();

	public Element getGitHubMessageUpstreamJobFailureElement();

	public String getInvocationURL();

	public Long getInvokedTime();

	public String getJDK();

	public JenkinsMaster getJenkinsMaster();

	public JenkinsSlave getJenkinsSlave();

	public String getJobName();

	public String getJobURL();

	public String getJobVariant();

	public int getJobVariantsDownstreamBuildCount(List<String> jobVariants);

	public List<Build> getJobVariantsDownstreamBuilds(List<String> jobVariants);

	public Long getLatestStartTimestamp();

	public Build getLongestDelayedDownstreamBuild();

	public Build getLongestRunningDownstreamBuild();

	public TestResult getLongestRunningTest();

	public String getOperatingSystem();

	public Map<String, String> getParameters();

	public String getParameterValue(String name);

	public Build getParentBuild();

	public String getResult();

	public Map<String, String> getStartPropertiesTempMap();

	public Long getStartTime();

	public String getStatus();

	public long getStatusAge();

	public String getStatusReport();

	public String getStatusReport(int indentSize);

	public String getStatusSummary();

	public Map<String, String> getStopPropertiesTempMap();

	public JSONObject getTestReportJSONObject();

	public List<TestResult> getTestResults(String testStatus);

	public TopLevelBuild getTopLevelBuild();

	public long getTotalDuration();

	public int getTotalSlavesUsedCount();

	public boolean hasBuildURL(String buildURL);

	public void reinvoke();

	public void reinvoke(ReinvokeRule reinvokeRule);

	public String replaceBuildURL(String text);

	public void setCompareToUpstream(boolean compareToUpstream);

	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule);

	public void update();

}