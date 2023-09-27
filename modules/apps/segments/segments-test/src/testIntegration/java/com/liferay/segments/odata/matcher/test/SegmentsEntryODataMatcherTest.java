/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.odata.matcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.odata.matcher.ODataMatcher;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class SegmentsEntryODataMatcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMatchesIdEquals() throws Exception {
		long segmentsEntryId = RandomTestUtil.nextLong();

		Map<String, String> segmentsEntryIds = Collections.singletonMap(
			"segmentsEntryIds",
			StringUtil.merge(
				new long[] {segmentsEntryId, RandomTestUtil.nextLong()}, ","));

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(segmentsEntryIds eq '", segmentsEntryId, "')"),
				segmentsEntryIds));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(segmentsEntryIds eq '", RandomTestUtil.nextLong(), "')"),
				segmentsEntryIds));
	}

	@Test
	public void testMatchesIdIn() throws Exception {
		long segmentsEntryId = RandomTestUtil.nextLong();

		Map<String, String> segmentsEntryIds = Collections.singletonMap(
			"segmentsEntryIds",
			StringUtil.merge(
				new long[] {segmentsEntryId, RandomTestUtil.nextLong()}, ","));

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(segmentsEntryIds in ('", segmentsEntryId, "'))"),
				segmentsEntryIds));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(segmentsEntryIds in ('", RandomTestUtil.nextLong(),
					"'))"),
				segmentsEntryIds));
	}

	@Test
	public void testMatchesStringNotContains() throws Exception {
		long segmentsEntryId = RandomTestUtil.nextLong();

		Map<String, String> segmentsEntryIds = Collections.singletonMap(
			"segmentsEntryIds",
			StringUtil.merge(
				new long[] {segmentsEntryId, RandomTestUtil.nextLong()}, ","));

		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (segmentsEntryIds eq '", segmentsEntryId, "')"),
				segmentsEntryIds));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (segmentsEntryIds eq '", RandomTestUtil.nextLong(),
					"')"),
				segmentsEntryIds));
	}

	@Inject(
		filter = "target.class.name=com.liferay.segments.model.SegmentsEntry",
		type = ODataMatcher.class
	)
	private ODataMatcher<Map<?, ?>> _contextODataMatcher;

}