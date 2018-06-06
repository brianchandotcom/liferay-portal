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

import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

/**
 * @author Leslie Wong
 */
public abstract class ModulesBatchTestClassGroup extends BatchTestClassGroup {

	public static class ModulesBatchTestClass extends BaseTestClass {

		protected static ModulesBatchTestClass getInstance(
			String batchName, File moduleBaseDir) {

			return new ModulesBatchTestClass(batchName, moduleBaseDir);
		}

		protected ModulesBatchTestClass(String batchName, File moduleBaseDir) {
			super(moduleBaseDir);

			addTestMethod(batchName);
		}

	}

	protected ModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		try {
			File modulesDir = new File(
				portalGitWorkingDirectory.getWorkingDirectory(), "modules");

			excludesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue("modules.excludes"), modulesDir));

			includesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue("modules.includes"), modulesDir));

			String includedModulesRequired = getFirstPropertyValue(
				"modules.includes.required");

			if (includedModulesRequired != null) {
				includesPathMatchers.addAll(
					getPathMatchers(
						getFirstPropertyValue(includedModulesRequired),
						modulesDir));
			}

			setTestClasses();

			setAxisTestClassGroups();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected abstract void setTestClasses() throws IOException;

}