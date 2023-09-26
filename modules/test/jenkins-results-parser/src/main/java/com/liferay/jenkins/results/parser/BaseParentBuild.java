/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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

		downstreamBuilds.addAll(
			parallelExecutor.execute(1000L * 60L * 60L * 3L));
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
	public List<Callable<Object>> getArchiveCallables() {
		List<Callable<Object>> archiveCallables = super.getArchiveCallables();

		if ((downstreamBuilds != null) && !downstreamBuilds.isEmpty()) {
			for (Build downstreamBuild : downstreamBuilds) {
				archiveCallables.addAll(downstreamBuild.getArchiveCallables());
			}
		}

		return archiveCallables;
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
	public boolean hasDownstreamBuilds() {
		if (getDownstreamBuildCount(null, null) > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void removeDownstreamBuild(Build build) {
		downstreamBuilds.remove(build);
	}

	protected BaseParentBuild(String url) {
		super(url);
	}

	protected BaseParentBuild(String url, Build parentBuild) {
		super(url, parentBuild);
	}

}