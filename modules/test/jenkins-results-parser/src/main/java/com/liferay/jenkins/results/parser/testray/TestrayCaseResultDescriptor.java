/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public interface TestrayCaseResultDescriptor {

	public AxisTestClassGroup getAxisTestClassGroup();

	public String getBatchName();

	public String getComponentName();

	public Job getJob();

	public String getName();

	public TestrayCaseResultDescriptor getParentTestrayCaseResultDescriptor();

	public int getPriority();

	public String getSubcomponentNames();

	public String getTeamName();

	public File getTestBaseDir();

	public TestClass getTestClass();

	public TestClassMethod getTestClassMethod();

	public String getTestSuiteName();

	public String getType();

}