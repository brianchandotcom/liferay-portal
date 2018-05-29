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

/**
 * @author Michael Hashimoto
 */
public class TestClassGroupFactory {

	public static BatchTestClassGroup newBatchTestClassGroup(
		String batchName, Job job) {

		if (batchName.contains("functional-")) {
			return new FunctionalBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("integration-") ||
			batchName.startsWith("unit-")) {

			return new JUnitBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("modules-integration-") ||
			batchName.startsWith("modules-unit-")) {

			return new ModulesJUnitBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("plugins-compile-")) {
			return new PluginsBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("portal-frontend-js-")) {
			return new NPMTestBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("semantic-versioning-")) {
			return new SemVerBaselineBatchTestClassGroup(batchName, job);
		}

		if (batchName.startsWith("tck-")) {
			return new TCKJunitBatchTestClassGroup(batchName, job);
		}

		return new DefaultBatchTestClassGroup(batchName, job);
	}

}