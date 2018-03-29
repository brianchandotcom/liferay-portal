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

/**
 * @author Michael Hashimoto
 */
public class AxisTestClassGroup extends BaseTestClassGroup {

	public String getBatchName() {
		return _batchTestClassGroup.getBatchName();
	}

	public BatchTestClassGroup getBatchTestClassGroup() {
		return _batchTestClassGroup;
	}

	public int getId() {
		return _id;
	}

	protected AxisTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, int id) {

		_batchTestClassGroup = batchTestClassGroup;
		_id = id;
	}

	protected void addTestClassFile(File testClassFile) {
		testClassFiles.add(testClassFile);
	}

	private final BatchTestClassGroup _batchTestClassGroup;
	private final int _id;

}