/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mcp.server.rest.client.dto.v1_0.ToolSummary;
import com.liferay.mcp.server.rest.client.pagination.Page;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.test.rule.FeatureFlag;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class ToolSummaryResourceTest extends BaseToolSummaryResourceTestCase {

	@Override
	@Test
	public void testGetToolSetToolSetNameToolSummariesPage() throws Exception {
		Page<ToolSummary> page =
			toolSummaryResource.getToolSetToolSetNameToolSummariesPage(
				"mcp-server-v1.0");

		List<String> names = TransformUtil.transform(
			page.getItems(), ToolSummary::getName);

		Assert.assertTrue(
			names.toString(), names.contains("getToolSetToolSetNameTool"));
		Assert.assertTrue(names.toString(), names.contains("getToolSetsPage"));
		Assert.assertTrue(
			names.toString(),
			names.contains("getToolSetToolSetNameToolSummariesPage"));
		Assert.assertTrue(
			names.toString(),
			names.contains("postToolSetToolSetNameToolInvoke"));
	}

}