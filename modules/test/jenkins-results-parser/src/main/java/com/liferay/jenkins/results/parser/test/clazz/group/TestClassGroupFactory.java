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

import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

/**
 * @author Michael Hashimoto
 */
public class TestClassGroupFactory {

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, Job job) {

		if (job instanceof PortalTestClassJob) {
			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			if (batchName.contains("functional-") ||
				batchName.contains("subrepository-functional-")) {

				return new FunctionalBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("integration-") ||
				batchName.startsWith("junit-test-") ||
				batchName.startsWith("subrepository-integration-") ||
				batchName.startsWith("subrepository-unit-") ||
				batchName.startsWith("unit-")) {

				return new JUnitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("modules-compile-")) {
				return new ModulesCompileBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("modules-integration-") ||
				batchName.startsWith("modules-unit-")) {

				return new ModulesJUnitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("modules-semantic-versioning-")) {
				return new ModulesSemVerBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("plugins-compile-")) {
				return new PluginsBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("js-test-") ||
				batchName.startsWith("portal-frontend-js-")) {

				return new NPMTestBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("service-builder-")) {
				return new ServiceBuilderBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			if (batchName.startsWith("tck-")) {
				return new TCKJunitBatchTestClassGroup(
					batchName, portalTestClassJob);
			}

			return new DefaultBatchTestClassGroup(
				batchName, portalTestClassJob);
		}

		throw new IllegalArgumentException("Unknown test class group");
	}

}