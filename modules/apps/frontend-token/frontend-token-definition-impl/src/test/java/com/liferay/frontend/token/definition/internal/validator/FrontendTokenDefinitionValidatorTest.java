/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal.validator;

import com.liferay.frontend.token.definition.internal.FrontendTokenDefinitionRegistryImplTest;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class FrontendTokenDefinitionValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEmptyFrontendTokenDefinition()
		throws IOException, JSONValidatorException {

		_frontendTokenDefinitionValidator.validateFrontendTokenDefinition(
			URLUtil.toString(
				FrontendTokenDefinitionRegistryImplTest.class.getResource(
					"dependencies/empty-categories-frontend-token-" +
						"definition.json")));

		_frontendTokenDefinitionValidator.validateFrontendTokenDefinition("");

		_frontendTokenDefinitionValidator.validateFrontendTokenDefinition("{}");
	}

	@Test(expected = JSONValidatorException.class)
	public void testInvalidFrontendTokenDefinition()
		throws IOException, JSONValidatorException {

		_frontendTokenDefinitionValidator.validateFrontendTokenDefinition(
			URLUtil.toString(
				FrontendTokenDefinitionRegistryImplTest.class.getResource(
					"dependencies/invalid-frontend-token-definition.json")));
	}

	@Test
	public void testValidFrontendTokenDefinition()
		throws IOException, JSONValidatorException {

		_frontendTokenDefinitionValidator.validateFrontendTokenDefinition(
			URLUtil.toString(
				FrontendTokenDefinitionRegistryImplTest.class.getResource(
					"dependencies/frontend-token-definition.json")));
	}

	private final FrontendTokenDefinitionValidator
		_frontendTokenDefinitionValidator =
			new FrontendTokenDefinitionValidator();

}