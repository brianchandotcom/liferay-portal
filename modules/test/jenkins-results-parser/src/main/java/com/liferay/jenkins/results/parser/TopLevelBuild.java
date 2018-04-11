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

import com.liferay.jenkins.results.parser.failure.message.generator.DownstreamFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PoshiTestFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PoshiValidationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.RebaseFailureMessageGenerator;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	@Override
	public void addDownstreamBuilds(String... urls) {
		super.addDownstreamBuilds(urls);

		if (getDownstreamBuildCount("completed") <
				getDownstreamBuildCount(null)) {

			setResult(null);
		}
	}

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		timelineData.addTimelineData(this);

		if (getTopLevelBuild() == this) {
			addDownstreamBuildsTimelineData(timelineData);
		}
	}

	@Override
	public void archive(String archiveName) {
		super.archive(archiveName);

		if (getParentBuild() == null) {
			Properties archiveProperties = new Properties();

			archiveProperties.setProperty(
				"top.level.build.url", replaceBuildURL(getBuildURL()));

			try {
				StringWriter sw = new StringWriter();

				archiveProperties.store(sw, null);

				writeArchiveFile(
					sw.toString(), archiveName + "/archive.properties");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to write archive properties");
			}
		}

		try {
			writeArchiveFile(
				getJenkinsReport(), getArchivePath() + "/jenkins-report.html");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to archive Jenkins report", ioe);
		}
	}

	public String getAcceptanceUpstreamJobURL() {
		String jobName = getJobName();

		if (jobName.contains("pullrequest")) {
			String acceptanceUpstreamJobURL = JenkinsResultsParserUtil.combine(
				"https://test-1-1.liferay.com/job/",
				jobName.replace("pullrequest", "upstream"));

			try {
				JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.getLocalURL(
						acceptanceUpstreamJobURL),
					false, 0, 0, 0);
			}
			catch (IOException ioe) {
				return null;
			}

			return acceptanceUpstreamJobURL;
		}

		return null;
	}

	public Map<String, String> getBaseGitRepositoryDetailsTempMap() {
		String repositoryType = getBaseRepositoryType();

		String tempMapName = "git." + repositoryType + ".properties";

		return getTempMap(tempMapName);
	}

	public String getCompanionBranchName() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> repositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return repositoryGitDetailsTempMap.get("github.sender.branch.name");
	}

	public Map<String, String> getCompanionGitRepositoryDetailsTempMap() {
		String branchName = getBranchName();
		String branchType = "ee";
		String repositoryType = getBaseRepositoryType();

		if (branchName.endsWith("-private")) {
			branchType = "base";
		}

		String tempMapName = JenkinsResultsParserUtil.combine(
			"git.", repositoryType, ".", branchType, ".properties");

		return getTempMap(tempMapName);
	}

	public String getCompanionRepositorySHA() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> repositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return repositoryGitDetailsTempMap.get("github.sender.branch.sha");
	}

	public String getCompanionUsername() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> repositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return repositoryGitDetailsTempMap.get("github.sender.username");
	}

	@Override
	public String getDisplayName() {
		String displayName = super.getDisplayName();

		if (getParentBuild() != null) {
			displayName += "/" + getParameterValue("JENKINS_JOB_VARIANT");
		}

		return displayName;
	}

	@Override
	public Element getGitHubMessageElement() {
		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		if (getParentBuild() == null) {
			return getTopGitHubMessageElement();
		}

		return super.getGitHubMessageElement();
	}

	public String getJenkinsReport() {
		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(getJenkinsReportURL()));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get Jenkins report", ioe);
		}
	}

	public Element getJenkinsReportElement() {
		return Dom4JUtil.getNewElement(
			"html", null, getJenkinsReportHeadElement(),
			getJenkinsReportBodyElement());
	}

	public String getJenkinsReportURL() {
		if (fromArchive) {
			return getBuildURL() + "/jenkins-report.html";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			"https://", jenkinsMaster.getName(), ".liferay.com/",
			"userContent/jobs/", getJobName(), "/builds/",
			Integer.toString(getBuildNumber()), "/jenkins-report.html");
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

	@Override
	public String getStatusReport(int indentSize) {
		String statusReport = super.getStatusReport(indentSize);

		if (getDownstreamBuildCount(null) > 0) {
			while (statusReport.endsWith("\n")) {
				statusReport = statusReport.substring(
					0, statusReport.length() - 1);
			}

			statusReport += " / ";
		}

		return statusReport + "Update took " + _updateDuration +
			" milliseconds.\n";
	}

	@Override
	public String getStatusSummary() {
		long currentTimeMillis = System.currentTimeMillis();

		if ((currentTimeMillis - _DOWNSTREAM_BUILDS_LISTING_INTERVAL) >=
				_lastDownstreamBuildsListingTimestamp) {

			StringBuilder sb = new StringBuilder(super.getStatusSummary());

			sb.append("\nRunning Builds: ");

			_lastDownstreamBuildsListingTimestamp = System.currentTimeMillis();

			for (Build downstreamBuild : getDownstreamBuilds("running")) {
				sb.append("\n");
				sb.append(downstreamBuild.getBuildURL());
			}

			return sb.toString();
		}

		return super.getStatusSummary();
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		return null;
	}

	public BaseBuild.TimelineData getTimelineData() {
		return new BaseBuild.TimelineData(500, this);
	}

	public Element getValidationGitHubMessageElement() {
		ValidationBuild validationBuild = null;

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild instanceof ValidationBuild) {
				validationBuild = (ValidationBuild)downstreamBuild;
			}
		}

		if (validationBuild == null) {
			throw new RuntimeException("Unable to find a validation build");
		}

		return validationBuild.getGitHubMessageElement();
	}

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
		_compareToUpstream = compareToUpstream;
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;
	}

	protected TopLevelBuild(String url) {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected void archiveJSON() {
		super.archiveJSON();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String repositoryTypes = buildProperties.getProperty(
				"repository.types");

			if (jobName.startsWith(
					"test-subrepository-acceptance-pullrequest")) {

				repositoryTypes += "," + getBaseRepositoryName();
			}

			for (String repositoryType : repositoryTypes.split(",")) {
				try {
					JSONObject gitRepositoryDetailsJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							getGitRepositoryDetailsPropertiesTempMapURL(
								repositoryType));

					Set<?> set = gitRepositoryDetailsJSONObject.keySet();

					if (set.isEmpty()) {
						continue;
					}

					writeArchiveFile(
						gitRepositoryDetailsJSONObject.toString(4),
						getArchivePath() + "/git." + repositoryType +
							".properties.json");
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to create git." + repositoryType +
							".properties.json",
						ioe);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}
	}

	@Override
	protected void findDownstreamBuilds() {
		if (getParentBuild() != null) {
			return;
		}

		super.findDownstreamBuilds();

		String consoleText = getConsoleText();

		for (Build downstreamBuild : downstreamBuilds) {
			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			downstreamBaseBuild.checkForReinvocation(consoleText);
		}
	}

	@Override
	protected List<String> findDownstreamBuildsInConsoleText() {
		if (getParentBuild() != null) {
			return Collections.emptyList();
		}

		String consoleText = getConsoleText();

		List<String> foundDownstreamBuildURLs = new ArrayList<>();

		if ((consoleText == null) || consoleText.isEmpty()) {
			return foundDownstreamBuildURLs;
		}

		Set<String> downstreamBuildURLs = new HashSet<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			String downstreamBuildURL = downstreamBuild.getBuildURL();

			if (downstreamBuildURL != null) {
				downstreamBuildURLs.add(downstreamBuildURL);
			}
		}

		if (getBuildURL() != null) {
			int i = consoleText.lastIndexOf("\nstop-current-job:");

			if (i != -1) {
				consoleText = consoleText.substring(0, i);
			}

			Matcher downstreamBuildURLMatcher =
				downstreamBuildURLPattern.matcher(
					consoleText.substring(consoleReadCursor));

			consoleReadCursor = consoleText.length();

			while (downstreamBuildURLMatcher.find()) {
				String url = downstreamBuildURLMatcher.group("url");

				Pattern reinvocationPattern = Pattern.compile(
					Pattern.quote(url) + " restarted at (?<url>[^\\s]*)\\.");

				Matcher reinvocationMatcher = reinvocationPattern.matcher(
					consoleText);

				while (reinvocationMatcher.find()) {
					url = reinvocationMatcher.group("url");
				}

				if (!foundDownstreamBuildURLs.contains(url) &&
					!downstreamBuildURLs.contains(url)) {

					foundDownstreamBuildURLs.add(url);
				}
			}
		}

		return foundDownstreamBuildURLs;
	}

	protected Element getBaseBranchDetailsElement() {
		String baseBranchURL =
			"https://github.com/liferay/" + getBaseRepositoryName() + "/tree/" +
				getBranchName();

		String baseRepositoryName = getBaseRepositoryName();

		String baseRepositorySHA = null;

		if (!baseRepositoryName.equals("liferay-jenkins-ee") &&
			baseRepositoryName.endsWith("-ee")) {

			baseRepositorySHA = getBaseRepositorySHA(
				baseRepositoryName.substring(
					0, baseRepositoryName.length() - 3));
		}
		else {
			baseRepositorySHA = getBaseRepositorySHA(baseRepositoryName);
		}

		String baseRepositoryCommitURL =
			"https://github.com/liferay/" + baseRepositoryName + "/commit/" +
				baseRepositorySHA;

		Element baseBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(baseBranchURL, getBranchName()));

		if (baseRepositorySHA != null) {
			Dom4JUtil.addToElement(
				baseBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					baseRepositoryCommitURL, baseRepositorySHA));
		}

		return baseBranchDetailsElement;
	}

	protected Element[] getBuildFailureElements() {
		Map<Build, Element> downstreamBuildFailureMessages =
			getDownstreamBuildMessages("ABORTED", "FAILURE", "UNSTABLE");

		List<Element> allCurrentBuildFailureElements = new ArrayList<>();
		List<Element> upstreamBuildFailureElements = new ArrayList<>();

		int maxFailureCount = 5;

		for (Map.Entry<Build, Element> entry :
				downstreamBuildFailureMessages.entrySet()) {

			Build failedDownstreamBuild = entry.getKey();

			Element failureElement = entry.getValue();

			if (failureElement != null) {
				if (UpstreamFailureUtil.isBuildFailingInUpstreamJob(
						failedDownstreamBuild)) {

					upstreamBuildFailureElements.add(failureElement);

					continue;
				}

				if (isHighPriorityBuildFailureElement(failureElement)) {
					allCurrentBuildFailureElements.add(0, failureElement);

					continue;
				}

				allCurrentBuildFailureElements.add(failureElement);
			}

			Element upstreamJobFailureElement =
				failedDownstreamBuild.
					getGitHubMessageUpstreamJobFailureElement();

			if (upstreamJobFailureElement != null) {
				upstreamBuildFailureElements.add(upstreamJobFailureElement);
			}
		}

		List<Element> buildFailureElements = new ArrayList<>();

		buildFailureElements.add(Dom4JUtil.getNewElement("hr"));

		if (allCurrentBuildFailureElements.isEmpty() &&
			upstreamBuildFailureElements.isEmpty()) {

			allCurrentBuildFailureElements.add(
				0, super.getGitHubMessageElement());
		}

		if (allCurrentBuildFailureElements.isEmpty() &&
			!upstreamBuildFailureElements.isEmpty()) {

			buildFailureElements.add(
				Dom4JUtil.getNewElement(
					"h4", null, "This pull contains no unique failures."));
		}
		else {
			buildFailureElements.add(
				Dom4JUtil.getNewElement(
					"h4", null, "Failures unique to this pull:"));

			buildFailureElements.add(
				Dom4JUtil.getOrderedListElement(
					allCurrentBuildFailureElements, maxFailureCount));
		}

		String acceptanceUpstreamJobURL = getAcceptanceUpstreamJobURL();

		if ((allCurrentBuildFailureElements.size() < maxFailureCount) &&
			!upstreamBuildFailureElements.isEmpty()) {

			Element acceptanceUpstreamJobLinkElement =
				Dom4JUtil.getNewAnchorElement(
					acceptanceUpstreamJobURL, "acceptance upstream results");

			Element upstreamJobFailureElement = Dom4JUtil.getNewElement(
				"details", null,
				Dom4JUtil.getNewElement(
					"summary", null,
					Dom4JUtil.getNewElement(
						"strong", null, "Failures in common with ",
						acceptanceUpstreamJobLinkElement, " at ",
						UpstreamFailureUtil.getUpstreamJobFailuresSHA(this),
						":")));

			int remainingFailureCount =
				maxFailureCount - allCurrentBuildFailureElements.size();

			Dom4JUtil.getOrderedListElement(
				upstreamBuildFailureElements, upstreamJobFailureElement,
				remainingFailureCount);

			buildFailureElements.add(Dom4JUtil.getNewElement("hr"));

			buildFailureElements.add(upstreamJobFailureElement);
		}

		if (jobName.contains("pullrequest") &&
			upstreamBuildFailureElements.isEmpty() &&
			(acceptanceUpstreamJobURL != null)) {

			Element upstreamResultElement = Dom4JUtil.getNewElement("h4");

			Dom4JUtil.addToElement(
				upstreamResultElement, "For upstream results, click ",
				Dom4JUtil.getNewAnchorElement(acceptanceUpstreamJobURL, "here"),
				".");

			buildFailureElements.add(upstreamResultElement);
		}

		return buildFailureElements.toArray(
			new Element[buildFailureElements.size()]);
	}

	protected Element getCompanionBranchDetailsElement() {
		String baseRepositoryName = getBaseRepositoryName();
		String branchName = getBranchName();

		String companionRepositoryName = baseRepositoryName;

		if (branchName.equals("master")) {
			companionRepositoryName = companionRepositoryName + "-ee";
		}

		if (branchName.endsWith("-private")) {
			companionRepositoryName = baseRepositoryName.substring(
				0, baseRepositoryName.indexOf("-ee"));
		}

		String companionUsername = getCompanionUsername();

		String companionBranchURL = JenkinsResultsParserUtil.combine(
			"https://github.com/", companionUsername, "/",
			companionRepositoryName, "/tree/", getCompanionBranchName());

		String companionRepositorySHA = null;

		companionRepositorySHA = getCompanionRepositorySHA();

		String companionRepositoryCommitURL = JenkinsResultsParserUtil.combine(
			"https://github.com/", companionUsername, "/",
			companionRepositoryName, "/commit/", companionRepositorySHA);

		Element companionBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(
				companionBranchURL, getCompanionBranchName()));

		if (companionRepositorySHA != null) {
			Dom4JUtil.addToElement(
				companionBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					companionRepositoryCommitURL, companionRepositorySHA));
		}

		return companionBranchDetailsElement;
	}

	protected Element getDownstreamGitHubMessageElement() {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewAnchorElement(
				getBuildURL(), null, getDisplayName()));

		if (result.equals("ABORTED")) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));
		}

		if (result.equals("FAILURE")) {
			Element failureMessageElement = getFailureMessageElement();

			if (failureMessageElement != null) {
				messageElement.add(failureMessageElement);
			}
		}

		return messageElement;
	}

	@Override
	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected Element getFailedJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(false);

		int failCount =
			getDownstreamBuildCount(null) -
				getDownstreamBuildCountByResult("SUCCESS") + 1;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"h4", null, Integer.toString(failCount), " Failed Jobs:"),
			jobSummaryListElement);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		int failCount = getDownstreamBuildCount(null) - successCount + 1;

		return Dom4JUtil.getNewElement(
			"div", null, Dom4JUtil.getNewElement("h6", null, "Job Results:"),
			Dom4JUtil.getNewElement(
				"p", null, Integer.toString(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Jobs", " Job"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				Integer.toString(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Jobs", " Job"),
				" Failed."));
	}

	protected String getGitRepositoryDetailsPropertiesTempMapURL(
		String repositoryType) {

		if (fromArchive) {
			return JenkinsResultsParserUtil.combine(
				getBuildURL(), "git.", repositoryType, ".properties.json");
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		JenkinsMaster topLevelBuildJenkinsMaster =
			topLevelBuild.getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, topLevelBuildJenkinsMaster.getName(), "/",
			topLevelBuild.getJobName(), "/",
			Integer.toString(topLevelBuild.getBuildNumber()), "/",
			topLevelBuild.getJobName(), "/git.", repositoryType, ".properties");
	}

	protected Element getJenkinsReportBodyElement() {
		String buildURL = getBuildURL();

		Element headingElement = Dom4JUtil.getNewElement(
			"h1", null, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		Element subheadingElement = null;

		JSONObject jobJSONObject = getBuildJSONObject();

		String description = jobJSONObject.optString("description");

		if (!description.isEmpty()) {
			subheadingElement = Dom4JUtil.getNewElement("h2");

			try {
				Dom4JUtil.addRawXMLToElement(subheadingElement, description);
			}
			catch (DocumentException de) {
				throw new RuntimeException(
					"Unable to parse description HTML " + description, de);
			}
		}

		return Dom4JUtil.getNewElement(
			"body", null, headingElement, subheadingElement,
			getJenkinsReportSummaryElement(), getJenkinsReportTimelineElement(),
			getJenkinsReportTopLevelTableElement(),
			getJenkinsReportDownstreamElement());
	}

	@Override
	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "th";
	}

	protected Element getJenkinsReportChartJsScriptElement(
		String xData, String y1Data, String y2Data) {

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(
					"dependencies/chart_template.js");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource chart_template.js", ioe);
		}

		resourceFileContent = resourceFileContent.replace("'xData'", xData);

		resourceFileContent = resourceFileContent.replace("'y1Data'", y1Data);

		resourceFileContent = resourceFileContent.replace("'y2Data'", y2Data);

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addText(resourceFileContent);

		return scriptElement;
	}

	protected Element getJenkinsReportDownstreamElement() {
		return Dom4JUtil.getNewElement(
			"div", null,
			getJenkinsReportDownstreamTableElement(null, "queued", "Queued: "),
			getJenkinsReportDownstreamTableElement(
				null, "starting", "Starting: "),
			getJenkinsReportDownstreamTableElement(
				null, "running", "Running: "),
			getJenkinsReportDownstreamTableElement(
				null, "missing", "Missing: "),
			Dom4JUtil.getNewElement("h2", null, "Completed: "),
			getJenkinsReportDownstreamTableElement(
				"ABORTED", "completed", "---- Aborted: "),
			getJenkinsReportDownstreamTableElement(
				"FAILURE", "completed", "---- Failure: "),
			getJenkinsReportDownstreamTableElement(
				"UNSTABLE", "completed", "---- Unstable: "),
			getJenkinsReportDownstreamTableElement(
				"SUCCESS", "completed", "---- Success: "));
	}

	protected Element getJenkinsReportDownstreamTableElement(
		String result, String status, String captionText) {

		List<Element> tableRowElements = getJenkinsReportTableRowElements(
			result, status);

		if (tableRowElements.isEmpty()) {
			return null;
		}

		return Dom4JUtil.getNewElement(
			"table", null,
			Dom4JUtil.getNewElement(
				"caption", null, captionText,
				Integer.toString(getDownstreamBuildCount(result, status))),
			getJenkinsReportTableColumnHeadersElement(),
			tableRowElements.toArray(new Element[tableRowElements.size()]));
	}

	protected Element getJenkinsReportHeadElement() {
		Element headElement = Dom4JUtil.getNewElement("head");

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(
					"dependencies/jenkins_report.css");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource jenkins_report.css", ioe);
		}

		Dom4JUtil.addToElement(
			headElement,
			Dom4JUtil.getNewElement("style", null, resourceFileContent));

		return headElement;
	}

	protected Element getJenkinsReportSummaryElement() {
		Element summaryElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Start Time: ",
				toJenkinsReportDateString(
					new Date(getStartTime()), getJenkinsReportTimeZoneName())),
			Dom4JUtil.getNewElement(
				"p", null, "Build Time: ",
				JenkinsResultsParserUtil.toDurationString(getDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total CPU Usage Time: ",
				JenkinsResultsParserUtil.toDurationString(getTotalDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total number of Jenkins slaves used: ",
				Integer.toString(getTotalSlavesUsedCount())),
			Dom4JUtil.getNewElement(
				"p", null, "Average delay time for invoked build to start: ",
				JenkinsResultsParserUtil.toDurationString(
					getAverageDelayTime())));

		Build longestDelayedDownstreamBuild =
			getLongestDelayedDownstreamBuild();

		if (longestDelayedDownstreamBuild != null) {
			Dom4JUtil.getNewElement(
				"p", summaryElement,
				"Longest delay time for invoked build to start: ",
				Dom4JUtil.getNewAnchorElement(
					longestDelayedDownstreamBuild.getBuildURL(),
					longestDelayedDownstreamBuild.getDisplayName()),
				" in: ",
				JenkinsResultsParserUtil.toDurationString(
					longestDelayedDownstreamBuild.getDelayTime()));
		}

		Build longestRunningDownstreamBuild =
			getLongestRunningDownstreamBuild();

		if (longestRunningDownstreamBuild != null) {
			Dom4JUtil.getNewElement(
				"p", summaryElement, "Longest Running Downstream Build: ",
				Dom4JUtil.getNewAnchorElement(
					longestRunningDownstreamBuild.getBuildURL(),
					longestRunningDownstreamBuild.getDisplayName()),
				" in: ",
				JenkinsResultsParserUtil.toDurationString(
					longestRunningDownstreamBuild.getDuration()));
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String longestRunningTestEnabled = buildProperties.getProperty(
				"jenkins.report.longest.running.test.enabled", "false");

			if (longestRunningTestEnabled.equals("true")) {
				TestResult longestRunningTest = getLongestRunningTest();

				if (longestRunningTest != null) {
					Dom4JUtil.getNewElement(
						"p", summaryElement, "Longest Running Test: ",
						Dom4JUtil.getNewAnchorElement(
							longestRunningTest.getTestReportURL(),
							longestRunningTest.getDisplayName()),
						" in: ",
						JenkinsResultsParserUtil.toDurationString(
							longestRunningTest.getDuration()));
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		return summaryElement;
	}

	protected Element getJenkinsReportTableColumnHeadersElement() {
		Element nameElement = Dom4JUtil.getNewElement("th", null, "Name");

		Element consoleElement = Dom4JUtil.getNewElement("th", null, "Console");

		Element testReportElement = Dom4JUtil.getNewElement(
			"th", null, "Test Report");

		Element startTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Start Time");

		Element buildTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Build Time");

		Element statusElement = Dom4JUtil.getNewElement("th", null, "Status");

		Element resultElement = Dom4JUtil.getNewElement("th", null, "Result");

		Element tableColumnHeaderElement = Dom4JUtil.getNewElement("tr");

		Dom4JUtil.addToElement(
			tableColumnHeaderElement, nameElement, consoleElement,
			testReportElement, startTimeElement, buildTimeElement,
			statusElement, resultElement);

		return tableColumnHeaderElement;
	}

	protected Element getJenkinsReportTimelineElement() {
		Element canvasElement = Dom4JUtil.getNewElement("canvas");

		canvasElement.addAttribute("height", "300");
		canvasElement.addAttribute("id", "timeline");

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addAttribute("src", _URL_CHART_JS);
		scriptElement.addText("");

		BaseBuild.TimelineData timelineData = getTimelineData();

		Element chartJSScriptElement = getJenkinsReportChartJsScriptElement(
			Arrays.toString(timelineData.getIndexData()),
			Arrays.toString(timelineData.getSlaveUsageData()),
			Arrays.toString(timelineData.getInvocationsData()));

		return Dom4JUtil.getNewElement(
			"div", null, canvasElement, scriptElement, chartJSScriptElement);
	}

	protected Element getJenkinsReportTopLevelTableElement() {
		Element topLevelTableElement = Dom4JUtil.getNewElement("table");

		String result = getResult();

		if (result != null) {
			Dom4JUtil.getNewElement(
				"caption", topLevelTableElement, "Top Level Build - ",
				Dom4JUtil.getNewElement("strong", null, getResult()));
		}
		else {
			Dom4JUtil.getNewElement(
				"caption", topLevelTableElement, "Top Level Build - ",
				Dom4JUtil.getNewElement(
					"strong", null, StringUtils.upperCase(getStatus())));
		}

		Dom4JUtil.addToElement(
			topLevelTableElement, getJenkinsReportTableColumnHeadersElement(),
			getJenkinsReportTableRowElement());

		return topLevelTableElement;
	}

	protected Element getJobSummaryListElement() {
		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

		for (Build build : builds) {
			Element jobSummaryListItemElement = Dom4JUtil.getNewElement(
				"li", jobSummaryListElement);

			jobSummaryListItemElement.add(
				build.getGitHubMessageBuildAnchorElement());
		}

		return jobSummaryListElement;
	}

	protected Element getJobSummaryListElement(boolean success) {
		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

		for (Build build : builds) {
			String result = build.getResult();

			if (result.equals("SUCCESS") == success) {
				Element jobSummaryListItemElement = Dom4JUtil.getNewElement(
					"li", jobSummaryListElement);

				jobSummaryListItemElement.add(
					build.getGitHubMessageBuildAnchorElement());
			}
		}

		return jobSummaryListElement;
	}

	protected Element getMoreDetailsElement() {
		Element moreDetailsElement = Dom4JUtil.getNewElement(
			"h5", null, "For more details click ",
			Dom4JUtil.getNewAnchorElement(getJenkinsReportURL(), "here"), ".");

		return moreDetailsElement;
	}

	protected Element getResultElement() {
		StringBuilder sb = new StringBuilder();

		String result = getResult();

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;

			sb.append(":heavy_check_mark: ");
		}
		else {
			sb.append(":x: ");
		}

		sb.append("ci:test");

		String ciTestSuite = getParameterValue("CI_TEST_SUITE");

		if ((ciTestSuite != null) && !ciTestSuite.isEmpty() &&
			!ciTestSuite.equals("default")) {

			sb.append(":");
			sb.append(ciTestSuite);
		}

		sb.append(" - ");
		sb.append(Integer.toString(successCount));
		sb.append(" out of ");
		sb.append(Integer.toString(getDownstreamBuildCountByResult(null) + 1));
		sb.append(" jobs passed in ");
		sb.append(JenkinsResultsParserUtil.toDurationString(getDuration()));

		return Dom4JUtil.getNewElement("h3", null, sb.toString());
	}

	@Override
	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, jenkinsMaster.getName(), "/", getJobName(), "/",
			Integer.toString(getBuildNumber()), "/", getJobName(), "/",
			"start.properties");
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop.properties.json";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, jenkinsMaster.getName(), "/", getJobName(), "/",
			Integer.toString(getBuildNumber()), "/", getJobName(), "/",
			"stop.properties");
	}

	protected Element getSuccessfulJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(true);

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		String result = getResult();

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		return Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, Integer.toString(successCount),
					" Successful Jobs:")),
			jobSummaryListElement);
	}

	@Override
	protected String getTempMapURL(String tempMapName) {
		String tempMapURL = super.getTempMapURL(tempMapName);

		if (tempMapURL != null) {
			return tempMapURL;
		}

		Matcher matcher = gitRepositoryTempMapNamePattern.matcher(tempMapName);

		if (matcher.find()) {
			return getGitRepositoryDetailsPropertiesTempMapURL(
				matcher.group("repositoryType"));
		}

		return null;
	}

	@Override
	protected int getTestCountByStatus(String status) {
		int testCount = 0;

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			if (!(downstreamBuild instanceof BaseBuild)) {
				continue;
			}

			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			testCount += downstreamBaseBuild.getTestCountByStatus(status);
		}

		return testCount;
	}

	protected Element getTopGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement(
			"html", null, getResultElement());

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", rootElement,
			Dom4JUtil.getNewElement(
				"summary", null, "Click here for more details."),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement());

		String branchName = getBranchName();
		String companionBranchLabel = "Copied in Private Modules Branch:";

		if (branchName.endsWith("-private")) {
			companionBranchLabel = "Built off of Portal Core Branch:";
		}

		if (!branchName.startsWith("ee-") &&
			getBaseRepositoryName().contains("liferay-portal")) {

			Dom4JUtil.addToElement(
				detailsElement,
				Dom4JUtil.getNewElement("h4", null, companionBranchLabel),
				getCompanionBranchDetailsElement());
		}

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		String result = getResult();

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		Dom4JUtil.addToElement(
			detailsElement, Integer.toString(successCount), " out of ",
			Integer.toString(getDownstreamBuildCountByResult(null) + 1),
			" jobs PASSED");

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, getFailedJobSummaryElement());
		}

		if (getDownstreamBuildCountByResult("SUCCESS") > 0) {
			Dom4JUtil.addToElement(
				detailsElement, getSuccessfulJobSummaryElement());
		}

		Dom4JUtil.addToElement(detailsElement, getMoreDetailsElement());

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, (Object[])getBuildFailureElements());
		}

		return rootElement;
	}

	protected String getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if ((upstreamBranchSHA == null) || upstreamBranchSHA.isEmpty()) {
			Map<String, String> startPropertiesTempMap =
				getStartPropertiesTempMap();

			upstreamBranchSHA = startPropertiesTempMap.get(
				"GITHUB_UPSTREAM_BRANCH_SHA");
		}

		return upstreamBranchSHA;
	}

	@Override
	protected boolean isCompareToUpstream() {
		return _compareToUpstream;
	}

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<repositoryType>.*)\\.properties");

	private static final long _DOWNSTREAM_BUILDS_LISTING_INTERVAL =
		1000 * 60 * 5;

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new PoshiTestFailureMessageGenerator(),
			new PoshiValidationFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),

			new DownstreamFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};

	private static final String _URL_CHART_JS =
		"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js";

	private static ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(20, true);

	private boolean _compareToUpstream = true;
	private long _lastDownstreamBuildsListingTimestamp = -1L;
	private long _updateDuration;

}