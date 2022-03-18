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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalAWSJob;
import com.liferay.jenkins.results.parser.PortalEnvironmentJob;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;
import com.liferay.jenkins.results.parser.RootCauseAnalysisToolJob;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestClassGroupFactory {

	public static AxisTestClassGroup newAxisTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		return newAxisTestClassGroup(batchTestClassGroup, null);
	}

	public static AxisTestClassGroup newAxisTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, File testBaseDir) {

		if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup) {
			return new FunctionalAxisTestClassGroup(
				(FunctionalBatchTestClassGroup)batchTestClassGroup,
				testBaseDir);
		}

		if (batchTestClassGroup instanceof JUnitBatchTestClassGroup) {
			return new JUnitAxisTestClassGroup(
				(JUnitBatchTestClassGroup)batchTestClassGroup);
		}

		if (batchTestClassGroup instanceof PluginsGulpBatchTestClassGroup) {
			return new PluginsGulpAxisTestClassGroup(
				(PluginsGulpBatchTestClassGroup)batchTestClassGroup);
		}

		return new AxisTestClassGroup(batchTestClassGroup);
	}

	public static AxisTestClassGroup newAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		BatchTestClassGroup batchTestClassGroup =
			segmentTestClassGroup.getParentBatchTestClassGroup();

		if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup) {
			return new FunctionalAxisTestClassGroup(
				jsonObject, segmentTestClassGroup);
		}

		if (batchTestClassGroup instanceof JUnitBatchTestClassGroup) {
			return new JUnitAxisTestClassGroup(
				jsonObject, segmentTestClassGroup);
		}

		if (batchTestClassGroup instanceof PluginsGulpBatchTestClassGroup) {
			return new PluginsGulpAxisTestClassGroup(
				jsonObject, segmentTestClassGroup);
		}

		return new AxisTestClassGroup(jsonObject, segmentTestClassGroup);
	}

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, Job job) {

		String key = JenkinsResultsParserUtil.combine(
			batchName, "_", String.valueOf(job.getBuildProfile()), "_",
			job.getJobName());

		if (_batchTestClassGroups.containsKey(key)) {
			return _batchTestClassGroups.get(key);
		}

		BatchTestClassGroup batchTestClassGroup = null;

		if (job instanceof PortalEnvironmentJob) {
			batchTestClassGroup = new EnvironmentFunctionalBatchTestClassGroup(
				batchName, (PortalEnvironmentJob)job);
		}

		if ((batchTestClassGroup == null) &&
			(job instanceof RootCauseAnalysisToolJob)) {

			if (batchName.startsWith("functional-")) {
				batchTestClassGroup = new FunctionalRCABatchTestClassGroup(
					batchName, (RootCauseAnalysisToolJob)job);
			}
			else if (batchName.startsWith("integration-") ||
					 batchName.startsWith("modules-integration-") ||
					 batchName.startsWith("modules-unit-") ||
					 batchName.startsWith("unit-")) {

				batchTestClassGroup = new JUnitRCABatchTestClassGroup(
					batchName, (RootCauseAnalysisToolJob)job);
			}
			else {
				batchTestClassGroup = new RCABatchTestClassGroup(
					batchName, (RootCauseAnalysisToolJob)job);
			}
		}

		if ((batchTestClassGroup == null) &&
			(job instanceof PortalTestClassJob)) {

			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			if (batchName.startsWith("functional-") ||
				batchName.startsWith("modules-functional-") ||
				batchName.startsWith("subrepository-functional-")) {

				batchTestClassGroup = new FunctionalBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("integration-") ||
					 batchName.startsWith("junit-test-") ||
					 batchName.startsWith(
						 "modules-integration-project-templates-") ||
					 batchName.startsWith("modules-unit-project-templates-") ||
					 batchName.startsWith("unit-")) {

				batchTestClassGroup = new JUnitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("js-test-") ||
					 batchName.startsWith("portal-frontend-js-")) {

				batchTestClassGroup = new NPMTestBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("js-unit-")) {
				batchTestClassGroup = new JSUnitModulesBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("modules-compile-")) {
				batchTestClassGroup = new CompileModulesBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if ((batchName.startsWith("modules-integration-") &&
					  !batchName.startsWith(
						  "modules-integration-project-templates-")) ||
					 (batchName.startsWith("modules-unit-") &&
					  !batchName.startsWith(
						  "modules-unit-project-templates-")) ||
					 batchName.startsWith("subrepository-integration-") ||
					 batchName.startsWith("subrepository-unit-")) {

				batchTestClassGroup = new ModulesJUnitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("modules-semantic-versioning-")) {
				batchTestClassGroup = new SemVerModulesBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("plugins-compile-")) {
				batchTestClassGroup = new PluginsBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("plugins-functional-")) {
				batchTestClassGroup = new PluginsFunctionalBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("plugins-gulp-")) {
				batchTestClassGroup = new PluginsGulpBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("qa-websites-functional-") &&
					 (job instanceof QAWebsitesGitRepositoryJob)) {

				batchTestClassGroup =
					new QAWebsitesFunctionalBatchTestClassGroup(
						batchName, (QAWebsitesGitRepositoryJob)job);
			}
			else if (batchName.startsWith("rest-builder-")) {
				batchTestClassGroup = new RESTBuilderModulesBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("service-builder-")) {
				batchTestClassGroup =
					new ServiceBuilderModulesBatchTestClassGroup(
						batchName, portalTestClassJob);
			}
			else if (batchName.startsWith("tck-")) {
				batchTestClassGroup = new TCKJunitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
			else {
				batchTestClassGroup = new DefaultBatchTestClassGroup(
					batchName, portalTestClassJob);
			}
		}

		if (batchTestClassGroup == null) {
			throw new IllegalArgumentException("Unknown test class group");
		}

		_batchTestClassGroups.put(key, batchTestClassGroup);

		return batchTestClassGroup;
	}

	public static SegmentTestClassGroup newSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		if (batchTestClassGroup instanceof
				EnvironmentFunctionalBatchTestClassGroup) {

			return new EnvironmentFunctionalSegmentTestClassGroup(
				(EnvironmentFunctionalBatchTestClassGroup)batchTestClassGroup);
		}

		if (batchTestClassGroup instanceof
				QAWebsitesFunctionalBatchTestClassGroup) {

			return new QAWebsitesFunctionalSegmentTestClassGroup(
				(QAWebsitesFunctionalBatchTestClassGroup)batchTestClassGroup);
		}

		if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup ||
			batchTestClassGroup instanceof FunctionalRCABatchTestClassGroup) {

			Job job = batchTestClassGroup.getJob();

			if (job instanceof PortalAWSJob) {
				return new AWSFunctionalSegmentTestClassGroup(
					batchTestClassGroup);
			}

			return new FunctionalSegmentTestClassGroup(batchTestClassGroup);
		}
		else if (batchTestClassGroup instanceof JUnitBatchTestClassGroup ||
				 batchTestClassGroup instanceof JUnitRCABatchTestClassGroup) {

			return new JUnitSegmentTestClassGroup(batchTestClassGroup);
		}
		else if (batchTestClassGroup instanceof ModulesBatchTestClassGroup) {
			if (batchTestClassGroup instanceof
					ServiceBuilderModulesBatchTestClassGroup) {

				return new ServiceBuilderModulesSegmentTestClassGroup(
					batchTestClassGroup);
			}

			return new ModulesSegmentTestClassGroup(batchTestClassGroup);
		}
		else if (batchTestClassGroup instanceof PluginsBatchTestClassGroup) {
			return new PluginsSegmentTestClassGroup(
				(PluginsBatchTestClassGroup)batchTestClassGroup);
		}
		else if (batchTestClassGroup instanceof
					PluginsGulpBatchTestClassGroup) {

			return new PluginsGulpSegmentTestClassGroup(
				(PluginsGulpBatchTestClassGroup)batchTestClassGroup);
		}

		return new SegmentTestClassGroup(batchTestClassGroup);
	}

	private static final Map<String, BatchTestClassGroup>
		_batchTestClassGroups = new HashMap<>();

}