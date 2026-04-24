/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.mcp.tool.provider;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Lucas Miranda
 */
public class MCPToolProviderUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateMcpTransport() {
		_testCreateMcpTransport("http://127.0.0.1/mcp");
		_testCreateMcpTransport("http://169.254.169.254/mcp");
		_testCreateMcpTransport("http://192.168.1.1/mcp");
	}

	private void _testCreateMcpTransport(String url) {
		SecurityException securityException = Assert.assertThrows(
			SecurityException.class,
			() -> ReflectionTestUtil.invoke(
				MCPToolProviderUtil.class, "_createMcpTransport",
				new Class<?>[] {Map.class, String.class}, Map.of("url", url),
				""));

		Assert.assertEquals(
			"Local links are not allowed: " + url,
			securityException.getMessage());
	}

}