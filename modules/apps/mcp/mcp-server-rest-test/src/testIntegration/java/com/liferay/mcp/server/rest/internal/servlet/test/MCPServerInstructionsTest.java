/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mcp.server.rest.test.util.MCPServerTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class MCPServerInstructionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() {
		MCPServerTestUtil.processBatchEngineUnits();
	}

	@Test
	public void testMCPServerInstructions() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						"com.liferay.mcp.server.rest.internal.configuration." +
							"MCPServerConfiguration",
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", true
						).build())) {

			String mcpServerProfileName1 = RandomTestUtil.randomString();

			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), null, mcpServerProfileName1,
				"mcp-server-profiles getMCPServerProfilesPage");

			String instructions = RandomTestUtil.randomString();
			String mcpServerProfileName2 = RandomTestUtil.randomString();

			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), instructions,
				mcpServerProfileName2,
				"mcp-server-profiles getMCPServerProfilesPage");

			Assert.assertEquals(
				StringPool.BLANK,
				_getMCPServerInstructions(mcpServerProfileName1));

			Assert.assertEquals(
				instructions, _getMCPServerInstructions(mcpServerProfileName2));
		}
	}

	private String _getMCPServerInstructions(String mcpServerProfileName)
		throws Exception {

		McpSyncClient mcpSyncClient = MCPServerTestUtil.getMcpSyncClient(
			mcpServerProfileName);

		try {
			McpSchema.InitializeResult initializeResult =
				mcpSyncClient.initialize();

			return initializeResult.instructions();
		}
		finally {
			mcpSyncClient.closeGracefully();
		}
	}

}