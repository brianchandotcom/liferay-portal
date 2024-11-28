/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildUpdater implements BuildUpdater {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public void reset() {
	}

	@Override
	public void update() {
		String status = _build.getStatus();

		if (status.equals("completed")) {
			runCompleted();
		}
		else if (status.equals("missing")) {
			runMissing();
		}
		else if (status.equals("queued")) {
			runQueued();
		}
		else if (status.equals("reporting")) {
			runReporting();
		}
		else if (status.equals("running")) {
			runRunning();
		}
		else if (status.equals("starting")) {
			runStarting();
		}
	}

	protected BaseBuildUpdater(Build build) {
		_build = build;
	}

	protected abstract boolean isBuildCompleted();

	protected abstract boolean isBuildFailing();

	protected abstract boolean isBuildQueued();

	protected abstract boolean isBuildRunning();

	protected void runCompleted() {
		_build.setStatus("completed");
	}

	protected void runMissing() {
		if (isBuildQueued()) {
			_build.setStatus("queued");

			runQueued();

			return;
		}

		if (isBuildRunning()) {
			_build.setStatus("running");

			runRunning();

			return;
		}

		if (isBuildCompleted()) {
			_build.setStatus("completed");

			return;
		}

		if (!_build.hasMaximumInvocationCount()) {
			_build.setStatus("starting");

			_build.reset();

			runStarting();

			return;
		}

		runReporting();
	}

	protected void runQueued() {
		if (isBuildQueued()) {
			return;
		}

		if (isBuildRunning()) {
			_build.setStatus("running");

			runRunning();

			return;
		}

		if (isBuildCompleted()) {
			_build.setStatus("completed");

			return;
		}

		_build.setStatus("missing");
	}

	protected void runReporting() {
		if (isBuildFailing()) {
			_isApplySlaveOfflineRules();

			if (_isApplyReinvokeRules()) {
				_build.setStatus("queued");

				return;
			}
		}

		runCompleted();
	}

	protected void runRunning() {
		if (!isBuildCompleted()) {
			_build.setStatus("running");

			return;
		}

		_build.setStatus("reporting");

		runReporting();
	}

	protected void runStarting() {
		Build.Invocation previousInvocation = _build.getPreviousInvocation();

		if (previousInvocation != null) {
			reinvoke();
		}
		else {
			invoke();
		}

		_build.setStatus("queued");
	}

	private boolean _isApplyReinvokeRules() {
		Build build = getBuild();

		if (build instanceof AxisBuild || build instanceof ParentBuild) {
			return false;
		}

		if ((isBuildCompleted() && !isBuildFailing()) || !isBuildCompleted() ||
			build.isFromArchive() || build.hasMaximumInvocationCount()) {

			return false;
		}

		for (ReinvokeRule reinvokeRule : ReinvokeRule.getReinvokeRules()) {
			if (!reinvokeRule.matches(build)) {
				continue;
			}

			_reinvoke(reinvokeRule);

			return true;
		}

		return false;
	}

	private boolean _isApplySlaveOfflineRules() {
		Build build = getBuild();

		if (build instanceof BatchBuild) {
			return false;
		}

		if ((isBuildCompleted() && !isBuildFailing()) || !isBuildCompleted() ||
			build.isFromArchive()) {

			return false;
		}

		JenkinsSlave jenkinsSlave = build.getJenkinsSlave();

		if (jenkinsSlave == null) {
			return false;
		}

		jenkinsSlave.update();

		if (jenkinsSlave.isOffline()) {
			return false;
		}

		List<SlaveOfflineRule> slaveOfflineRules = new ArrayList<>(
			SlaveOfflineRule.getSlaveOfflineRules());

		for (SlaveOfflineRule slaveOfflineRule : slaveOfflineRules) {
			if (!slaveOfflineRule.matches(build)) {
				continue;
			}

			_takeSlaveOffline(slaveOfflineRule);

			return true;
		}

		return false;
	}

	private void _reinvoke(ReinvokeRule reinvokeRule) {
		Build build = getBuild();

		if (build instanceof AxisBuild || build instanceof ParentBuild ||
			build.hasMaximumInvocationCount()) {

			return;
		}

		Build parentBuild = build.getParentBuild();

		if (parentBuild == null) {
			return;
		}

		String parentBuildStatus = parentBuild.getStatus();

		if (!parentBuildStatus.equals("running") ||
			!JenkinsResultsParserUtil.isCINode() ||
			build.isFromCompletedBuild()) {

			return;
		}

		if ((reinvokeRule != null) && !build.isFromArchive()) {
			String message = JenkinsResultsParserUtil.combine(
				reinvokeRule.getName(), " failure detected at ",
				build.getBuildURL(), ". This build will be reinvoked.\n\n",
				reinvokeRule.toString(), "\n\n");

			System.out.println(message);

			TopLevelBuild topLevelBuild = build.getTopLevelBuild();

			if (topLevelBuild != null) {
				message = JenkinsResultsParserUtil.combine(
					message, "Top Level Build URL: ",
					topLevelBuild.getBuildURL());
			}

			String notificationRecipients =
				reinvokeRule.getNotificationRecipients();

			if ((notificationRecipients != null) &&
				!notificationRecipients.isEmpty()) {

				NotificationUtil.sendEmail(
					message, "jenkins", "Build reinvoked",
					reinvokeRule.notificationRecipients);
			}
		}

		reinvoke();
	}

	private void _takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
		Build build = getBuild();

		if ((slaveOfflineRule == null) || build.isFromArchive()) {
			return;
		}

		slaveOfflineRule.takeSlaveOffline(build);
	}

	private final Build _build;

}