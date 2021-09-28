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

import java.lang.reflect.Proxy;

/**
 * @author Michael Hashimoto
 */
public class TestBatchFactory {

	public static TestBatch newTestBatch(
		BatchBuildData batchBuildData, Workspace workspace) {

		TestBatch testBatch = null;

		if (batchBuildData instanceof PortalBatchBuildData) {
			PortalBatchBuildData portalBatchBuildData =
				(PortalBatchBuildData)batchBuildData;

			String batchName = batchBuildData.getBatchName();

			if (batchName.contains("functional")) {
				testBatch = new FunctionalPortalTestBatch(
					portalBatchBuildData, workspace);
			}
			else if (batchName.contains("integration") ||
					 batchName.contains("unit")) {

				testBatch = new JunitPortalTestBatch(
					portalBatchBuildData, workspace);
			}
			else {
				testBatch = new DefaultPortalTestBatch(
					portalBatchBuildData, workspace);
			}
		}

		if (testBatch == null) {
			throw new RuntimeException("Unsuppported batch");
		}

		return (TestBatch)Proxy.newProxyInstance(
			TestBatch.class.getClassLoader(), new Class<?>[] {TestBatch.class},
			new MethodLogger(testBatch));
	}

}