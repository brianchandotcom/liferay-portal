/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Truong
 */
public class FriendlyURLEntryReportUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRenderFriendlyURLPublicMappingConflictReportEmpty()
		throws Exception {

		JSONArray conflictsJSONArray = JSONFactoryUtil.createJSONArray();

		String report = _normalize(
			FriendlyURLEntryReportUtil.
				renderFriendlyURLPublicMappingConflictReport(
					conflictsJSONArray, 20120)
		).trim();

		Assert.assertEquals(_read("expected-empty-report.txt"), report);
	}

	@Test
	public void testRenderFriendlyURLPublicMappingConflictReportSample()
		throws Exception {

		JSONArray conflictsJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"category", "Friendly URL Collision"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"name", "Precheck Conflict Demo"
					).put(
						"pageId", 41807
					).put(
						"siteFriendlyURL", "/guest"
					).put(
						"siteName", "Liferay DXP Site"
					).put(
						"type", "PAGE"
					),
					JSONUtil.put(
						"friendlyURL", "/precheck-conflict-demo"
					).put(
						"name", "Precheck Conflict Demo"
					).put(
						"siteId", 41205
					).put(
						"type", "SITE"
					))
			).put(
				"path", "/precheck-conflict-demo"
			),
			JSONUtil.put(
				"category", "Reserved Path Conflict"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"name", "Group Resources"
					).put(
						"pageId", 39112
					).put(
						"siteFriendlyURL", "/intranet"
					).put(
						"siteName", "Intranet"
					).put(
						"type", "PAGE"
					))
			).put(
				"path", "/group"
			));

		String report = _normalize(
			FriendlyURLEntryReportUtil.
				renderFriendlyURLPublicMappingConflictReport(
					conflictsJSONArray, 20120)
		).trim();

		Assert.assertEquals(_read("expected-sample-report.txt"), report);
	}

	private String _normalize(String report) {
		return report.replaceAll(
			"Generated:        \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} UTC",
			"Generated:        TIMESTAMP");
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(
			getClass().getResourceAsStream("dependencies/" + fileName));
	}

}