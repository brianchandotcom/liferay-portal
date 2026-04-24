/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class RESTBuilderAntTargetTestClass extends BaseAntTargetTestClass {

	protected RESTBuilderAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);
	}

	protected RESTBuilderAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String antTargetName) {

		super(batchTestClassGroup, testClassFile, antTargetName);
	}

	protected RESTBuilderAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	@Override
	protected void addTestClassMethods() {
		addTestClassMethod("build-rests");
	}

}