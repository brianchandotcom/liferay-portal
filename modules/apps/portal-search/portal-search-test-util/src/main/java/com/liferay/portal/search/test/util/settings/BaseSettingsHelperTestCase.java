/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.settings;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.SettingsHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public abstract class BaseSettingsHelperTestCase {

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetReturnsNull() {
		Assert.assertNull(settingsHelper.get("test"));
	}

	@Test
	public void testLoadFromSourceJSON() throws Exception {
		settingsHelper.loadFromSource(_getSource("settings.json"));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			settingsHelper.get("analysis.analyzer.1_10_ngram.filter"));

		Assert.assertEquals(
			"[\"lowercase\",\"1_10_ngram\"]", jsonArray.toString());

		Assert.assertEquals(
			"0-all", settingsHelper.get("index.auto_expand_replicas"));
		Assert.assertEquals(
			"timestamp", settingsHelper.get("index.default_pipeline"));
		Assert.assertEquals(
			"7500", settingsHelper.get("index.mapping.total_fields.limit"));
		Assert.assertEquals("9", settingsHelper.get("index.max_ngram_diff"));

		settingsHelper.loadFromSource("{\"index\":{\"max_ngram_diff\":8}}");

		Assert.assertEquals("8", settingsHelper.get("index.max_ngram_diff"));
	}

	@Test
	public void testLoadFromSourceYAML() {
		settingsHelper.loadFromSource(_getSource("settings.yml"));

		Assert.assertEquals(
			"false", settingsHelper.get("monitor.jvm.gc.enabled"));
		Assert.assertEquals("1", settingsHelper.get("thread_pool.warmer.core"));
		Assert.assertEquals(
			"2m", settingsHelper.get("thread_pool.warmer.keep_alive"));
		Assert.assertEquals("8", settingsHelper.get("thread_pool.warmer.max"));
		Assert.assertEquals(
			"100", settingsHelper.get("thread_pool.write.queue_size"));

		settingsHelper.loadFromSource("monitor.jvm.gc.enabled: true");

		Assert.assertEquals(
			"true", settingsHelper.get("monitor.jvm.gc.enabled"));
	}

	@Test
	public void testPutKeyValue() {
		settingsHelper.put("one", "two");
		settingsHelper.put("three.four.five", "six");

		Assert.assertEquals("two", settingsHelper.get("one"));
		Assert.assertEquals("six", settingsHelper.get("three.four.five"));

		settingsHelper.put("one", "two_overidden");

		Assert.assertEquals("two_overidden", settingsHelper.get("one"));
	}

	protected SettingsHelper settingsHelper;

	private String _getSource(String resourceName) {
		return StringUtil.read(getClass(), resourceName);
	}

}