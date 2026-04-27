/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class RESTBuilderAndServiceBuilderModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	protected RESTBuilderAndServiceBuilderModulesBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected RESTBuilderAndServiceBuilderModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalImplBuildFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"portal-impl/build.xml");

		addTestClass(
			TestClassFactory.newAntTargetTestClass(
				this, portalImplBuildFile, "build-rests"));

		addTestClass(
			TestClassFactory.newAntTargetTestClass(
				this, portalImplBuildFile, "build-services"));
	}

}