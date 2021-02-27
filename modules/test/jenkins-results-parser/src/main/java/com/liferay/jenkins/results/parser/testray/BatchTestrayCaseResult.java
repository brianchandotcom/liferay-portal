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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class BatchTestrayCaseResult extends TestrayCaseResult {

	public BatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup) {

		super(testrayBuild, topLevelBuild);

		_axisTestClassGroup = axisTestClassGroup;
	}

	@Override
	public List<Attachment> getAttachments() {
		List<Attachment> attachments = new ArrayList<>();

		attachments.add(_getJenkinsConsoleAttachment());

		attachments.removeAll(Collections.singleton(null));

		return attachments;
	}

	public AxisBuild getAxisBuild() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.getDownstreamAxisBuild(
			_axisTestClassGroup.getAxisName());
	}

	public String getAxisName() {
		return _axisTestClassGroup.getAxisName();
	}

	public String getBatchName() {
		return _axisTestClassGroup.getBatchName();
	}

	public Build getBuild() {
		return getAxisBuild();
	}

	@Override
	public String getComponent() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.component", getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getName() {
		return getAxisName();
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if ((build == null) || build.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public String getTeam() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.team", getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getStatus());
		sb.append(" ");
		sb.append(getBatchName());
		sb.append(" ");
		sb.append(getName());
		sb.append(" ");

		Build build = getBuild();

		if (build != null) {
			sb.append(build.getBuildURL());
		}
		else {
			sb.append(" No build found");
		}

		for (TestrayCaseResult.Attachment attachment : getAttachments()) {
			sb.append("\n* ");
			sb.append(attachment.getName());
			sb.append("=");
			sb.append(attachment.getURL());
		}

		return sb.toString();
	}

	protected String getAxisBuildURLPath() {
		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Date date = new Date(topLevelBuild.getStartTime());

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				date, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());
		sb.append("/");
		sb.append(axisBuild.getAxisName());

		return sb.toString();
	}

	private Attachment _getJenkinsConsoleAttachment() {
		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild == null) {
			return null;
		}

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(),
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/jenkins-console.txt.gz"));

		return new Attachment(this, testrayS3Object, "Jenkins Console");
	}

	private final AxisTestClassGroup _axisTestClassGroup;

}