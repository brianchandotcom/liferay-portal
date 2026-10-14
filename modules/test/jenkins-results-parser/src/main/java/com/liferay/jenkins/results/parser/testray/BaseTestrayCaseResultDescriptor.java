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
public abstract class BaseTestrayCaseResultDescriptor
	implements TestrayCaseResultDescriptor {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup() {
		return _axisTestClassGroup;
	}

	@Override
	public Job getJob() {
		return _job;
	}

	@Override
	public TestrayCaseResultDescriptor getParentTestrayCaseResultDescriptor() {
		return _parentTestrayCaseResultDescriptor;
	}

	@Override
	public String getSubcomponentNames() {
		return "";
	}

	@Override
	public File getTestBaseDir() {
		if (_axisTestClassGroup == null) {
			return null;
		}

		return _axisTestClassGroup.getTestBaseDir();
	}

	@Override
	public TestClass getTestClass() {
		return _testClass;
	}

	@Override
	public TestClassMethod getTestClassMethod() {
		return _testClassMethod;
	}

	@Override
	public String getTestSuiteName() {
		if (_job == null) {
			return null;
		}

		return _job.getTestSuiteName();
	}

	protected BaseTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor,
		TestClass testClass, TestClassMethod testClassMethod) {

		_axisTestClassGroup = axisTestClassGroup;
		_parentTestrayCaseResultDescriptor = parentTestrayCaseResultDescriptor;
		_testClass = testClass;
		_testClassMethod = testClassMethod;

		_job = axisTestClassGroup.getJob();
	}

	protected BaseTestrayCaseResultDescriptor(
		Job job,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		_job = job;
		_parentTestrayCaseResultDescriptor = parentTestrayCaseResultDescriptor;

		_axisTestClassGroup = null;
		_testClass = null;
		_testClassMethod = null;
	}

	private final AxisTestClassGroup _axisTestClassGroup;
	private final Job _job;
	private final TestrayCaseResultDescriptor
		_parentTestrayCaseResultDescriptor;
	private final TestClass _testClass;
	private final TestClassMethod _testClassMethod;

}