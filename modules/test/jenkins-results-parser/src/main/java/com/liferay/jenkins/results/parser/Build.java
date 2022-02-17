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

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public interface Build {

	public static final String DEPENDENCIES_URL_TOKEN = "${dependencies.url}";

	public void addDownstreamBuilds(String... urls);

	public void archive(String archiveName);

	public String getArchiveName();

	public String getArchivePath();

	public File getArchiveRootDir();

	public URL getArtifactsBaseURL();

	public long getAverageDelayTime();

	public List<String> getBadBuildURLs();

	public String getBaseGitRepositoryName();

	public String getBaseGitRepositorySHA(String gitRepositoryName);

	public String getBranchName();

	public String getBuildDescription();

	public String getBuildDirPath();

	public JSONObject getBuildJSONObject();

	public int getBuildNumber();

	public Job.BuildProfile getBuildProfile();

	public JSONObject getBuildResultsJSONObject(
		String[] buildResults, String[] testStatuses, String[] dataTypes);

	public String getBuildURL();

	public String getBuildURLRegex();

	public String getConsoleText();

	public Long getDelayTime();

	public int getDepth();

	public String getDisplayName();

	public int getDownstreamBuildCount(String status);

	public int getDownstreamBuildCount(String result, String status);

	public List<Build> getDownstreamBuilds(String status);

	public List<Build> getDownstreamBuilds(String result, String status);

	public long getDuration();

	public String getFailureMessage();

	public Element getGitHubMessageBuildAnchorElement();

	public Element getGitHubMessageElement();

	public Element getGitHubMessageUpstreamJobFailureElement();

	public Map<String, String> getInjectedEnvironmentVariablesMap()
		throws IOException;

	public String getInvocationURL();

	public Long getInvokedTime();

	public JenkinsMaster getJenkinsMaster();

	public JenkinsSlave getJenkinsSlave();

	public Job getJob();

	public String getJobName();

	public String getJobURL();

	public String getJobVariant();

	public int getJobVariantsDownstreamBuildCount(
		List<String> jobVariants, String result, String status);

	public List<Build> getJobVariantsDownstreamBuilds(
		Iterable<String> jobVariants, String result, String status);

	public Long getLatestStartTimestamp();

	public Build getLongestDelayedDownstreamBuild();

	public Build getLongestRunningDownstreamBuild();

	public TestResult getLongestRunningTest();

	public Map<String, String> getMetricLabels();

	public List<Build> getModifiedDownstreamBuilds();

	public List<Build> getModifiedDownstreamBuildsByStatus(String status);

	public Map<String, String> getParameters();

	public String getParameterValue(String name);

	public Build getParentBuild();

	public String getResult();

	public Map<String, String> getStartPropertiesTempMap();

	public Long getStartTime();

	public String getStatus();

	public long getStatusAge();

	public long getStatusDuration(String status);

	public String getStatusSummary();

	public Map<String, String> getStopPropertiesTempMap();

	public TestClassResult getTestClassResult(String testClassName);

	public List<TestClassResult> getTestClassResults();

	public List<URL> getTestrayAttachmentURLs();

	public List<URL> getTestrayS3AttachmentURLs();

	public JSONObject getTestReportJSONObject(boolean checkCache);

	public List<TestResult> getTestResults();

	public List<TestResult> getTestResults(String testStatus);

	public String getTestSuiteName();

	public TopLevelBuild getTopLevelBuild();

	public long getTotalDuration();

	public int getTotalSlavesUsedCount();

	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly);

	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly, boolean ignoreCurrentBuild);

	public List<TestResult> getUniqueFailureTestResults();

	public List<TestResult> getUpstreamJobFailureTestResults();

	public boolean hasBuildURL(String buildURL);

	public boolean hasGenericCIFailure();

	public boolean hasModifiedDownstreamBuilds();

	public boolean isBuildModified();

	public boolean isCompareToUpstream();

	public boolean isCompleted();

	public boolean isFailing();

	public boolean isFromArchive();

	public boolean isFromCompletedBuild();

	public boolean isUniqueFailure();

	public void reinvoke();

	public void reinvoke(ReinvokeRule reinvokeRule);

	public void removeDownstreamBuild(Build build);

	public String replaceBuildURL(String text);

	public void setArchiveName(String archiveName);

	public void setArchiveRootDir(File archiveRootDir);

	public void setCompareToUpstream(boolean compareToUpstream);

	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule);

	public void update();

	public interface BranchInformation {

		public String getCachedRemoteGitRefName();

		public String getOriginName();

		public Integer getPullRequestNumber();

		public String getReceiverUsername();

		public String getRepositoryName();

		public String getSenderBranchName();

		public String getSenderBranchSHA();

		public RemoteGitRef getSenderRemoteGitRef();

		public String getSenderUsername();

		public String getUpstreamBranchName();

		public String getUpstreamBranchSHA();

	}

}