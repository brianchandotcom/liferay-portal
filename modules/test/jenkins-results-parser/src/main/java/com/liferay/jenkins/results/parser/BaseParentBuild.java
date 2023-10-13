/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseParentBuild extends BaseBuild implements ParentBuild {

	public void addDownstreamBuilds(Map<String, String> urlAxisNames) {
		final Build thisBuild = this;

		List<Callable<Build>> callables = new ArrayList<>(urlAxisNames.size());

		for (Map.Entry<String, String> urlEntry : urlAxisNames.entrySet()) {
			String url = urlEntry.getKey();

			try {
				url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.decode(url));
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				throw new IllegalArgumentException(
					"Unable to decode " + url, unsupportedEncodingException);
			}

			if (!hasBuildURL(url)) {
				final String axisName = urlEntry.getValue();
				final String buildURL = url;

				Callable<Build> callable = new Callable<Build>() {

					@Override
					public Build call() {
						try {
							return BuildFactory.newBuild(
								buildURL, thisBuild, axisName);
						}
						catch (RuntimeException runtimeException) {
							if (!isFromArchive()) {
								NotificationUtil.sendSlackNotification(
									runtimeException.getMessage() +
										"\nBuild URL: " + buildURL,
									"ci-notifications", "Build Object Failure");
							}

							return null;
						}
					}

				};

				callables.add(callable);
			}
		}

		ParallelExecutor<Build> parallelExecutor = new ParallelExecutor<>(
			callables, true, getExecutorService());

		addDownstreamBuilds(parallelExecutor.execute(1000L * 60L * 60L * 3L));
	}

	@Override
	public void addDownstreamBuilds(String... urls) {
		Map<String, String> urlAxisNames = new HashMap<>();

		for (String url : urls) {
			urlAxisNames.put(url, null);
		}

		addDownstreamBuilds(urlAxisNames);
	}

	@Override
	public int getDownstreamBuildCount(String status) {
		return getDownstreamBuildCount(null, status);
	}

	@Override
	public int getDownstreamBuildCount(String result, String status) {
		List<Build> downstreamBuilds = getDownstreamBuilds(result, status);

		return downstreamBuilds.size();
	}

	@Override
	public List<Build> getDownstreamBuilds() {
		if (_downstreamBuilds != null) {
			return new ArrayList<>(_downstreamBuilds);
		}

		_downstreamBuilds = new ArrayList<>();

		return new ArrayList<>(_downstreamBuilds);
	}

	@Override
	public List<Build> getDownstreamBuilds(String status) {
		return getDownstreamBuilds(null, status);
	}

	@Override
	public List<Build> getDownstreamBuilds(String result, String status) {
		List<Build> filteredDownstreamBuilds = Collections.synchronizedList(
			new ArrayList<Build>());

		List<Build> downstreamBuilds = getDownstreamBuilds();

		if ((result == null) && (status == null)) {
			filteredDownstreamBuilds.addAll(downstreamBuilds);

			return filteredDownstreamBuilds;
		}

		for (Build downstreamBuild : downstreamBuilds) {
			if (((status == null) ||
				 status.equals(downstreamBuild.getStatus())) &&
				((result == null) ||
				 result.equals(downstreamBuild.getResult()))) {

				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public Long getLatestStartTimestamp() {
		Long latestStartTimestamp = getStartTime();

		if (latestStartTimestamp == null) {
			return null;
		}

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			Long downstreamBuildLatestStartTimestamp = null;

			if (downstreamBuild instanceof ParentBuild) {
				ParentBuild parentBuild = (ParentBuild)downstreamBuild;

				downstreamBuildLatestStartTimestamp =
					parentBuild.getLatestStartTimestamp();
			}
			else {
				downstreamBuildLatestStartTimestamp =
					downstreamBuild.getStartTime();
			}

			if (downstreamBuildLatestStartTimestamp == null) {
				return null;
			}

			latestStartTimestamp = Math.max(
				latestStartTimestamp, downstreamBuildLatestStartTimestamp);
		}

		return latestStartTimestamp;
	}

	@Override
	public Build getLongestDelayedDownstreamBuild() {
		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		if (downstreamBuilds.isEmpty()) {
			return this;
		}

		Build longestDelayedBuild = downstreamBuilds.get(0);

		for (Build downstreamBuild : downstreamBuilds) {
			Build longestDelayedDownstreamBuild = downstreamBuild;

			if (downstreamBuild instanceof ParentBuild) {
				ParentBuild parentBuild = (ParentBuild)downstreamBuild;

				longestDelayedDownstreamBuild =
					parentBuild.getLongestDelayedDownstreamBuild();
			}

			if (downstreamBuild.getDelayTime() >
					longestDelayedDownstreamBuild.getDelayTime()) {

				longestDelayedDownstreamBuild = downstreamBuild;
			}

			if (longestDelayedDownstreamBuild.getDelayTime() >
					longestDelayedBuild.getDelayTime()) {

				longestDelayedBuild = longestDelayedDownstreamBuild;
			}
		}

		return longestDelayedBuild;
	}

	@Override
	public Build getLongestRunningDownstreamBuild() {
		Build longestRunningDownstreamBuild = null;

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			if ((longestRunningDownstreamBuild == null) ||
				(downstreamBuild.getDuration() >
					longestRunningDownstreamBuild.getDuration())) {

				longestRunningDownstreamBuild = downstreamBuild;
			}
		}

		return longestRunningDownstreamBuild;
	}

	@Override
	public List<Build> getModifiedDownstreamBuilds() {
		return getModifiedDownstreamBuildsByStatus(null);
	}

	@Override
	public List<Build> getModifiedDownstreamBuildsByStatus(String status) {
		List<Build> modifiedDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : getDownstreamBuilds()) {
			if (downstreamBuild.isBuildModified()) {
				modifiedDownstreamBuilds.add(downstreamBuild);

				continue;
			}

			if (!(downstreamBuild instanceof ParentBuild)) {
				continue;
			}

			ParentBuild parentBuild = (ParentBuild)downstreamBuild;

			if (parentBuild.hasModifiedDownstreamBuilds()) {
				modifiedDownstreamBuilds.add(parentBuild);
			}
		}

		if (status != null) {
			modifiedDownstreamBuilds.retainAll(getDownstreamBuilds(status));
		}

		return modifiedDownstreamBuilds;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		List<TestResult> testResults = new ArrayList<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			List<TestResult> downstreamTestResults =
				downstreamBuild.getTestResults(testStatus);

			if (!(downstreamTestResults == null)) {
				testResults.addAll(downstreamTestResults);
			}
		}

		return testResults;
	}

	@Override
	public long getTotalDuration() {
		long totalDuration = getDuration();

		for (Build downstreamBuild :
				JenkinsResultsParserUtil.flatten(getDownstreamBuilds(null))) {

			totalDuration += downstreamBuild.getDuration();
		}

		return totalDuration;
	}

	@Override
	public int getTotalSlavesUsedCount() {
		return getTotalSlavesUsedCount(null, false);
	}

	@Override
	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly) {

		return getTotalSlavesUsedCount(status, modifiedBuildsOnly, false);
	}

	@Override
	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly, boolean ignoreCurrentBuild) {

		int totalSlavesUsedCount = 1;

		if (ignoreCurrentBuild || (modifiedBuildsOnly && !isBuildModified()) ||
			((status != null) && !status.equals(getStatus()))) {

			totalSlavesUsedCount = 0;
		}

		List<Build> downstreamBuilds;

		if (modifiedBuildsOnly) {
			downstreamBuilds = getModifiedDownstreamBuildsByStatus(status);
		}
		else {
			downstreamBuilds = getDownstreamBuilds(status);
		}

		for (Build downstreamBuild : downstreamBuilds) {
			if (!(downstreamBuild instanceof ParentBuild)) {
				continue;
			}

			ParentBuild parentBuild = (ParentBuild)downstreamBuild;

			totalSlavesUsedCount += parentBuild.getTotalSlavesUsedCount(
				status, modifiedBuildsOnly);
		}

		return totalSlavesUsedCount;
	}

	@Override
	public List<TestResult> getUniqueFailureTestResults() {
		List<TestResult> uniqueFailureTestResults = new ArrayList<>();

		for (Build downstreamBuild : getFailedDownstreamBuilds()) {
			uniqueFailureTestResults.addAll(
				downstreamBuild.getUniqueFailureTestResults());
		}

		return uniqueFailureTestResults;
	}

	@Override
	public List<TestResult> getUpstreamJobFailureTestResults() {
		List<TestResult> upstreamFailureTestResults = new ArrayList<>();

		for (Build downstreamBuild : getFailedDownstreamBuilds()) {
			upstreamFailureTestResults.addAll(
				downstreamBuild.getUpstreamJobFailureTestResults());
		}

		return upstreamFailureTestResults;
	}

	@Override
	public boolean hasBuildURL(String buildURL) {
		if (super.hasBuildURL(buildURL)) {
			return true;
		}

		for (Build downstreamBuild : getDownstreamBuilds()) {
			if (downstreamBuild.hasBuildURL(buildURL)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasDownstreamBuilds() {
		if (getDownstreamBuildCount(null, null) > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasModifiedDownstreamBuilds() {
		for (Build downstreamBuild : getDownstreamBuilds()) {
			if (downstreamBuild.isBuildModified()) {
				return true;
			}

			if (!(downstreamBuild instanceof ParentBuild)) {
				continue;
			}

			ParentBuild parentBuild = (ParentBuild)downstreamBuild;

			if (parentBuild.hasModifiedDownstreamBuilds()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void removeDownstreamBuild(Build build) {
		if (_downstreamBuilds == null) {
			getDownstreamBuilds();
		}

		_downstreamBuilds.remove(build);
	}

	@Override
	public String replaceBuildURL(String text) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(text)) {
			return text;
		}

		text = super.replaceBuildURL(text);

		for (Build downstreamBuild : getDownstreamBuilds("complete")) {
			Build downstreamBaseBuild = downstreamBuild;

			text = downstreamBaseBuild.replaceBuildURL(text);
		}

		return super.replaceBuildURL(text);
	}

	@Override
	public void update() {
		if (skipUpdate()) {
			return;
		}

		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		List<Callable<Object>> callables = new ArrayList<>();

		for (final Build downstreamBuild : downstreamBuilds) {
			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					downstreamBuild.update();

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, getExecutorService());

		parallelExecutor.execute();

		findDownstreamBuilds();

		super.update();
	}

	protected BaseParentBuild(String url) {
		super(url);
	}

	protected BaseParentBuild(String url, Build parentBuild) {
		super(url, parentBuild);
	}

	protected void addDownstreamBuilds(Collection<Build> builds) {
		if (builds == null) {
			return;
		}

		builds.removeAll(Collections.singleton(null));

		if (_downstreamBuilds == null) {
			getDownstreamBuilds();
		}

		_downstreamBuilds.addAll(builds);
	}

	protected void addDownstreamBuildsTimelineData(TimelineData timelineData) {
		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			downstreamBuild.addTimelineData(timelineData);
		}
	}

	protected abstract void findDownstreamBuilds();

	@Override
	protected List<Callable<Object>> getArchiveCallables() {
		List<Callable<Object>> archiveCallables = super.getArchiveCallables();

		List<Build> downstreamBuilds = getDownstreamBuilds();

		if ((downstreamBuilds != null) && !downstreamBuilds.isEmpty()) {
			for (Build downstreamBuild : downstreamBuilds) {
				if (downstreamBuild instanceof BaseBuild) {
					BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

					archiveCallables.addAll(
						downstreamBaseBuild.getArchiveCallables());
				}
			}
		}

		return archiveCallables;
	}

	protected int getDownstreamBuildCountByResult(String result) {
		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		if (result == null) {
			return downstreamBuilds.size();
		}

		int count = 0;

		for (Build downstreamBuild : downstreamBuilds) {
			String downstreamBuildResult = downstreamBuild.getResult();

			if (Objects.equals(downstreamBuildResult, result)) {
				count++;
			}
		}

		return count;
	}

	protected Map<Build, Element> getDownstreamBuildMessages(
		List<Build> downstreamBuilds) {

		List<Callable<Element>> callables = new ArrayList<>();

		for (final Build downstreamBuild : downstreamBuilds) {
			Callable<Element> callable = new Callable<Element>() {

				public Element call() {
					return downstreamBuild.getGitHubMessageElement();
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Element> parallelExecutor = new ParallelExecutor<>(
			callables, getExecutorService());

		List<Element> elements = parallelExecutor.execute();

		Map<Build, Element> elementsMap = new LinkedHashMap<>();

		for (int i = 0; i < elements.size(); i++) {
			elementsMap.put(downstreamBuilds.get(i), elements.get(i));
		}

		return elementsMap;
	}

	protected List<Build> getFailedDownstreamBuilds() {
		List<Build> failedDownstreamBuilds = new ArrayList<>();

		failedDownstreamBuilds.addAll(getDownstreamBuilds("ABORTED", null));
		failedDownstreamBuilds.addAll(getDownstreamBuilds("MISSING", null));
		failedDownstreamBuilds.addAll(getDownstreamBuilds("FAILURE", null));
		failedDownstreamBuilds.addAll(getDownstreamBuilds("UNSTABLE", null));

		return failedDownstreamBuilds;
	}

	protected List<Element> getJenkinsReportTableRowElements(
		String result, String status) {

		List<Element> tableRowElements = super.getJenkinsReportTableRowElements(
			result, status);

		List<Build> builds = getDownstreamBuilds(result, status);

		Collections.sort(builds, new BaseBuild.BuildDisplayNameComparator());

		String batchName = null;

		for (Build build : builds) {
			if (!(build instanceof BaseBuild)) {
				continue;
			}

			if (build instanceof DownstreamBuild) {
				DownstreamBuild downstreamBuild = (DownstreamBuild)build;

				String downstreamBatchName = downstreamBuild.getBatchName();

				if (!Objects.equals(batchName, downstreamBatchName)) {
					tableRowElements.add(
						Dom4JUtil.getNewElement(
							"th", null, downstreamBatchName));

					batchName = downstreamBatchName;
				}
			}

			BaseBuild baseBuild = (BaseBuild)build;

			tableRowElements.addAll(
				baseBuild.getJenkinsReportTableRowElements(result, status));
		}

		return tableRowElements;
	}

	protected boolean isJenkinsBuildCompleted() {
		if (!super.isJenkinsBuildCompleted()) {
			return false;
		}

		for (Build downstreamBuild : getDownstreamBuilds()) {
			if (!downstreamBuild.isCompleted()) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected void reset() {
		super.reset();

		if (_downstreamBuilds == null) {
			getDownstreamBuilds();
		}

		_downstreamBuilds.clear();
	}

	@Override
	protected boolean skipUpdate() {
		if (isBuildModified() || hasModifiedDownstreamBuilds()) {
			return false;
		}

		if (!Objects.equals(getStatus(), "completed")) {
			return false;
		}

		return true;
	}

	protected void sortDownstreamBuilds() {
		Collections.sort(
			_downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());
	}

	private List<Build> _downstreamBuilds;

}