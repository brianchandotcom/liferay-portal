/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;

import java.util.List;

/**
 * @author Peter Yoo
 */
public interface TestClassGroup {

	public Job getJob();

	public List<TestClass> getTestClasses();

	public List<File> getTestClassFiles();

	public boolean hasTestClasses();

}