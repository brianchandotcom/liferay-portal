/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.rest.dto;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.FrameworkConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprintConfiguration;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class SXPBlueprintConfigurationTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_restDTOTestHelper = new RestDTOTestHelper(this, testName);
	}

	@Test
	public void testToJSONString() throws Exception {
		SXPBlueprintConfiguration sxpBlueprintConfiguration =
			new SXPBlueprintConfiguration();

		FrameworkConfiguration frameworkConfiguration =
			new FrameworkConfiguration();

		frameworkConfiguration.setApplyIndexerClauses(true);

		sxpBlueprintConfiguration.setFrameworkConfiguration(
			frameworkConfiguration);

		_restDTOTestHelper.assertJSONString(sxpBlueprintConfiguration);
	}

	@Rule
	public TestName testName = new TestName();

	private RestDTOTestHelper _restDTOTestHelper;

}