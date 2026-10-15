/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class IntentUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetIntentVerbs() {
		Assert.assertArrayEquals(
			new String[0], IntentUtil.getIntentVerbs(StringPool.BLANK));
		Assert.assertArrayEquals(
			new String[] {"clone", "copy", "duplicate"},
			IntentUtil.getIntentVerbs("copy"));

		List<String> listVerbs = Arrays.asList(
			IntentUtil.getIntentVerbs(IntentUtil.INTENT_LIST));

		Assert.assertFalse(listVerbs.contains("get"));
		Assert.assertTrue(listVerbs.contains("show"));

		List<String> readVerbs = Arrays.asList(
			IntentUtil.getIntentVerbs(IntentUtil.INTENT_READ));

		Assert.assertTrue(readVerbs.contains("get"));
		Assert.assertFalse(readVerbs.contains("show"));
	}

	@Test
	public void testGetOperationIntent() {
		Assert.assertEquals(
			IntentUtil.INTENT_READ,
			IntentUtil.getOperationIntent(false, "get", null));
		Assert.assertEquals(
			IntentUtil.INTENT_LIST,
			IntentUtil.getOperationIntent(true, "get", null));
		Assert.assertEquals(
			StringPool.BLANK,
			IntentUtil.getOperationIntent(false, "head", null));
		Assert.assertEquals(
			IntentUtil.INTENT_CREATE,
			IntentUtil.getOperationIntent(false, "post", null));
		Assert.assertEquals(
			"subscribe",
			IntentUtil.getOperationIntent(false, "put", "Subscribe"));
	}

	@Test
	public void testGetOtherIntents() {
		List<String> intents = IntentUtil.getOtherIntents(
			Collections.emptyList());

		List<String> otherIntents = IntentUtil.getOtherIntents(
			Arrays.asList(IntentUtil.INTENT_LIST, IntentUtil.INTENT_READ));

		Assert.assertEquals(
			otherIntents.toString(), intents.size() - 2, otherIntents.size());
		Assert.assertTrue(otherIntents.contains(IntentUtil.INTENT_CREATE));
		Assert.assertFalse(otherIntents.contains(IntentUtil.INTENT_LIST));
		Assert.assertFalse(otherIntents.contains(IntentUtil.INTENT_READ));
	}

	@Test
	public void testGetSearchIntents() {
		Assert.assertTrue(
			_getSearchIntents(
				StringPool.BLANK
			).isEmpty());
		Assert.assertEquals(
			Arrays.asList("delete"), _getSearchIntents("delete twenty users"));
		Assert.assertTrue(
			_getSearchIntents(
				"hello world"
			).isEmpty());
		Assert.assertEquals(
			Arrays.asList(IntentUtil.INTENT_LIST, IntentUtil.INTENT_READ),
			_getSearchIntents("Show me the blog postings"));
	}

	@Test
	public void testGetSearchIntentsWithAPhrase() {
		Assert.assertEquals(
			Arrays.asList("delete"), _getSearchIntents("get rid of a user"));
		Assert.assertEquals(
			Arrays.asList("publish"), _getSearchIntents("make live the page"));
	}

	private List<String> _getSearchIntents(String search) {
		return IntentUtil.getSearchIntents(WordUtil.toSearchWords(search));
	}

}