/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal.summary;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.summary.Summary;
import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SummaryBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContent() {
		String content = RandomTestUtil.randomString();

		_summaryBuilder.setContent(content);

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(content, summary.getContent());
	}

	@Test
	public void testContentHighlight() {
		_summaryBuilder.setContent(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHT_TAG_OPEN,
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHT_TAG_CLOSE,
				"GGG<strong>HHH</strong>III"));
		_summaryBuilder.setHighlight(true);

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(
			StringBundler.concat(
				"AAA&lt;strong&gt;BBB&lt;/strong&gt;CCC",
				HighlightUtil.HIGHLIGHTS[0],
				"DDD&lt;strong&gt;EEE&lt;/strong&gt;FFF",
				HighlightUtil.HIGHLIGHTS[1],
				"GGG&lt;strong&gt;HHH&lt;/strong&gt;III"),
			summary.getContent());
	}

	@Test
	public void testContentHighlightUnescaped() {
		_summaryBuilder.setContent(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHT_TAG_OPEN,
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHT_TAG_CLOSE,
				"GGG<strong>HHH</strong>III"));
		_summaryBuilder.setEscape(false);
		_summaryBuilder.setHighlight(true);

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHTS[0],
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHTS[1],
				"GGG<strong>HHH</strong>III"),
			summary.getContent());
	}

	@Test
	public void testMaxContentLength() {
		String content = "12345678";

		testMaxContentLength(content, -99, content);
		testMaxContentLength(content, 0, content);
		testMaxContentLength(content, 2, "12");
		testMaxContentLength(content, 3, "...");
		testMaxContentLength(content, 4, "1...");
		testMaxContentLength(content, 7, "1234...");
		testMaxContentLength(content, 8, content);
		testMaxContentLength(content, 99, content);
	}

	@Test
	public void testMaxContentLengthIgnoredForTitle() {
		String title = RandomTestUtil.randomString(8);

		_summaryBuilder.setTitle(title);

		_summaryBuilder.setMaxContentLength(1);

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(title, summary.getTitle());
	}

	@Test
	public void testMaxContentLengthWithHighlight() {
		String content = StringBundler.concat(
			"alpha ", HighlightUtil.HIGHLIGHT_TAG_OPEN, "bravo",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " charlie");

		testMaxContentLength(
			content, -1, true,
			StringBundler.concat(
				"alpha ", HighlightUtil.HIGHLIGHTS[0], "bravo",
				HighlightUtil.HIGHLIGHTS[1], " charlie"));
		testMaxContentLength(
			content, 0, true,
			StringBundler.concat(
				"alpha ", HighlightUtil.HIGHLIGHTS[0], "bravo",
				HighlightUtil.HIGHLIGHTS[1], " charlie"));
		testMaxContentLength(content, 1, true, "a");
		testMaxContentLength(content, 2, true, "al");
		testMaxContentLength(content, 3, true, "...");
		testMaxContentLength(content, 4, true, "a...");
		testMaxContentLength(content, 13, true, "alpha...");
		testMaxContentLength(
			content, 14, true,
			StringBundler.concat(
				"alpha ", HighlightUtil.HIGHLIGHTS[0], "bravo",
				HighlightUtil.HIGHLIGHTS[1], "..."));
		testMaxContentLength(
			content, 18, true,
			StringBundler.concat(
				"alpha ", HighlightUtil.HIGHLIGHTS[0], "bravo",
				HighlightUtil.HIGHLIGHTS[1], "..."));
		testMaxContentLength(
			content, 19, true,
			StringBundler.concat(
				"alpha ", HighlightUtil.HIGHLIGHTS[0], "bravo",
				HighlightUtil.HIGHLIGHTS[1], " charlie"));
	}

	@Test
	public void testTitle() {
		String title = RandomTestUtil.randomString();

		_summaryBuilder.setTitle(title);

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(title, summary.getTitle());
	}

	@Test
	public void testTitleHighlight() {
		_summaryBuilder.setHighlight(true);
		_summaryBuilder.setTitle(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHT_TAG_OPEN,
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHT_TAG_CLOSE,
				"GGG<strong>HHH</strong>III"));

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(
			StringBundler.concat(
				"AAA&lt;strong&gt;BBB&lt;/strong&gt;CCC",
				HighlightUtil.HIGHLIGHTS[0],
				"DDD&lt;strong&gt;EEE&lt;/strong&gt;FFF",
				HighlightUtil.HIGHLIGHTS[1],
				"GGG&lt;strong&gt;HHH&lt;/strong&gt;III"),
			summary.getTitle());
	}

	@Test
	public void testTitleHighlightUnescaped() {
		_summaryBuilder.setEscape(false);
		_summaryBuilder.setHighlight(true);
		_summaryBuilder.setTitle(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHT_TAG_OPEN,
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHT_TAG_CLOSE,
				"GGG<strong>HHH</strong>III"));

		Summary summary = _summaryBuilder.build();

		Assert.assertEquals(
			StringBundler.concat(
				"AAA<strong>BBB</strong>CCC", HighlightUtil.HIGHLIGHTS[0],
				"DDD<strong>EEE</strong>FFF", HighlightUtil.HIGHLIGHTS[1],
				"GGG<strong>HHH</strong>III"),
			summary.getTitle());
	}

	protected void testMaxContentLength(
		String content, int maxContentLength, boolean highlight,
		String expected) {

		SummaryBuilder summaryBuilder = new SummaryBuilderImpl();

		summaryBuilder.setContent(content);
		summaryBuilder.setHighlight(highlight);
		summaryBuilder.setMaxContentLength(maxContentLength);

		Summary summary = summaryBuilder.build();

		Assert.assertEquals(expected, summary.getContent());
	}

	protected void testMaxContentLength(
		String content, int maxContentLength, String expected) {

		testMaxContentLength(content, maxContentLength, false, expected);
	}

	private final SummaryBuilder _summaryBuilder = new SummaryBuilderImpl();

}