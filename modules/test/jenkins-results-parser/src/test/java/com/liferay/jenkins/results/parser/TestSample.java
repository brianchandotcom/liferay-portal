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

import java.io.File;

import java.util.List;

/**
 * @author Peter Yoo
 */
public class TestSample {

	public TestSample(List<File> baseSamplesDirs, String sampleKey) {
		this.sampleKey = sampleKey;

		for (File baseSamplesDir : baseSamplesDirs) {
			sampleDir = new File(baseSamplesDir, sampleKey);

			if (!sampleDir.exists()) {
				sampleDir = null;

				continue;
			}

			sampleDirName = baseSamplesDir.getName() + "/" + sampleKey;

			break;
		}
	}

	public File getSampleDir() {
		return sampleDir;
	}

	public String getSampleDirName() {
		return sampleDirName;
	}

	public String getSampleKey() {
		return sampleKey;
	}

	protected File sampleDir;
	protected String sampleDirName;
	protected String sampleKey;

}