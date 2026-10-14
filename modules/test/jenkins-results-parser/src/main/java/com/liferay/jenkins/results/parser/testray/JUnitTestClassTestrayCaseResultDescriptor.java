/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class JUnitTestClassTestrayCaseResultDescriptor
	extends BaseTestClassTestrayCaseResultDescriptor<JUnitTestClass> {

	public JUnitTestClassTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup, JUnitTestClass jUnitTestClass,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			jUnitTestClass);
	}

	@Override
	public String getName() {
		JUnitTestClass jUnitTestClass = getTestClass();

		String testClassName = JenkinsResultsParserUtil.getCanonicalPath(
			jUnitTestClass.getTestClassFile());

		testClassName = testClassName.replaceAll(".*/(com/.*)\\.java", "$1");

		return testClassName.replace("/", ".");
	}

}