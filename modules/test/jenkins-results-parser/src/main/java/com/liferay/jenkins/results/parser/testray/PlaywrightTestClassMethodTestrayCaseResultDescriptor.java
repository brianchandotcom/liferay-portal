/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.PlaywrightSegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PlaywrightTestClassMethodTestrayCaseResultDescriptor
	extends BaseTestClassMethodTestrayCaseResultDescriptor
		<PlaywrightJUnitTestClass, PlaywrightTestClassMethod> {

	public PlaywrightTestClassMethodTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor,
		PlaywrightTestClassMethod playwrightTestClassMethod) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			playwrightTestClassMethod);
	}

	@Override
	public String getName() {
		PlaywrightJUnitTestClass playwrightJUnitTestClass = getTestClass();
		PlaywrightTestClassMethod playwrightTestClassMethod =
			getTestClassMethod();

		String variantSpecFilePath = _getVariantSpecFilePath(
			playwrightJUnitTestClass.getSpecFilePath());

		if (variantSpecFilePath == null) {
			return playwrightTestClassMethod.getName();
		}

		return JenkinsResultsParserUtil.combine(
			variantSpecFilePath, " > ",
			playwrightTestClassMethod.getTestName());
	}

	private String _getProjectName() {
		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup();

		SegmentTestClassGroup segmentTestClassGroup =
			axisTestClassGroup.getSegmentTestClassGroup();

		if (!(segmentTestClassGroup instanceof
				PlaywrightSegmentTestClassGroup)) {

			return null;
		}

		PlaywrightSegmentTestClassGroup playwrightSegmentTestClassGroup =
			(PlaywrightSegmentTestClassGroup)segmentTestClassGroup;

		return playwrightSegmentTestClassGroup.getProjectName();
	}

	private String _getVariantSpecFilePath(String specFilePath) {
		String projectName = _getProjectName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(projectName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(specFilePath)) {

			return null;
		}

		int specSlashIndex = specFilePath.lastIndexOf("/");

		if (specSlashIndex == -1) {
			return null;
		}

		String projectPath = projectName.replace(".", "/");

		int projectSlashIndex = projectPath.lastIndexOf("/");

		String variantName = projectPath.substring(projectSlashIndex + 1);

		String specDirPath = specFilePath.substring(0, specSlashIndex);

		List<String> specDirNames = Arrays.asList(specDirPath.split("/"));

		if (specDirNames.contains(variantName)) {
			return null;
		}

		String projectParentPath = projectPath.substring(
			0, projectSlashIndex + 1);

		if (specFilePath.startsWith(projectParentPath)) {
			String relativeSpecFilePath = specFilePath.substring(
				projectParentPath.length());

			int relativeSlashIndex = relativeSpecFilePath.indexOf("/");

			if (relativeSlashIndex == -1) {
				return JenkinsResultsParserUtil.combine(
					projectParentPath, variantName, "/", relativeSpecFilePath);
			}

			return JenkinsResultsParserUtil.combine(
				projectParentPath, variantName,
				relativeSpecFilePath.substring(relativeSlashIndex));
		}

		return JenkinsResultsParserUtil.combine(
			specDirPath.substring(0, specDirPath.lastIndexOf("/") + 1),
			variantName, specFilePath.substring(specSlashIndex));
	}

}