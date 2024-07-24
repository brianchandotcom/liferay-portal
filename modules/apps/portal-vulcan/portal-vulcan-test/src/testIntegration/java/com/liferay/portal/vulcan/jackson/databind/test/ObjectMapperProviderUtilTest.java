/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jackson.databind.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.jackson.databind.ObjectMapperProviderUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ObjectMapperProviderUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		ObjectMapper batchEngineObjectMapper =
			ObjectMapperProviderUtil.getBatchEngineObjectMapper();

		Assert.assertNotNull(batchEngineObjectMapper);
		Assert.assertSame(
			batchEngineObjectMapper,
			ObjectMapperProviderUtil.getBatchEngineObjectMapper());

		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		Assert.assertNotNull(objectMapper);
		Assert.assertSame(
			objectMapper, ObjectMapperProviderUtil.getObjectMapper());
	}

}