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

import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;

import java.nio.file.PathMatcher;

import java.util.List;

/**
 * @author Leslie Wong
 */
public class PMDBatchTestClassGroup extends BatchTestClassGroup {

	public int getAxisCount() {
		if (testAllFiles) {
			return 1;
		}

		List<File> modifiedJavaFiles =
			portalGitWorkingDirectory.getModifiedFilesList(
				false, _excludePathMatchers, _includePathMatchers);

		if (!modifiedJavaFiles.isEmpty()) {
			return 1;
		}

		return 0;
	}

	protected PMDBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if ((portalTestClassJob instanceof PortalAcceptancePullRequestJob) &&
			testRelevantChanges) {

			testAllFiles = false;
		}
		else {
			testAllFiles = true;
		}

		String pmdJavaExcludes = getFirstPropertyValue("pmd.java.excludes");

		_excludePathMatchers = getPathMatchers(
			pmdJavaExcludes, portalGitWorkingDirectory.getWorkingDirectory());

		String pmdJavaIncludes = getFirstPropertyValue("pmd.java.includes");

		_includePathMatchers = getPathMatchers(
			pmdJavaIncludes, portalGitWorkingDirectory.getWorkingDirectory());
	}

	protected boolean testAllFiles;

	private final List<PathMatcher> _excludePathMatchers;
	private final List<PathMatcher> _includePathMatchers;

}