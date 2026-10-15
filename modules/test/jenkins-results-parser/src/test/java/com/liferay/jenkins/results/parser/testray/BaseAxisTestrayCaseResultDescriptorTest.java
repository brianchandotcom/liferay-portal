/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class BaseAxisTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetName() {
		String axisName = RandomTestUtil.randomString();

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			axisName
		).when(
			axisTestClassGroup
		).getAxisName();

		BaseAxisTestrayCaseResultDescriptor
			baseAxisTestrayCaseResultDescriptor =
				new BaseAxisTestrayCaseResultDescriptor(
					axisTestClassGroup, null) {
				};

		testEquals(axisName, baseAxisTestrayCaseResultDescriptor.getName());
	}

}