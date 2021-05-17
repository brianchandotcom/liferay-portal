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

package com.liferay.layout.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class LayoutReportsIssueTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		LayoutReportsIssue layoutReportsIssue1 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);
		LayoutReportsIssue layoutReportsIssue2 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);

		Assert.assertTrue(layoutReportsIssue1.equals(layoutReportsIssue2));
	}

	@Test
	public void testNewLayoutReportsIssue() {
		LayoutReportsIssue layoutReportsIssue = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);

		List<LayoutReportsIssue.Detail> details =
			layoutReportsIssue.getDetails();

		Assert.assertEquals(details.toString(), 1, details.size());

		LayoutReportsIssue.Detail detail = details.get(0);

		Assert.assertEquals(
			LayoutReportsIssue.Detail.Key.TITLE, detail.getKey());
		Assert.assertEquals(100, detail.getTotal());

		Assert.assertEquals(
			LayoutReportsIssue.Key.SEO, layoutReportsIssue.getKey());
		Assert.assertEquals(100, layoutReportsIssue.getTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewLayoutReportsIssueWithNullKey() {
		new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			null);
	}

	@Test
	public void testNotEqualsWithDifferentDetails() {
		LayoutReportsIssue layoutReportsIssue1 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);
		LayoutReportsIssue layoutReportsIssue2 = new LayoutReportsIssue(
			Arrays.asList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.HREFLANG, 50),
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 50)),
			LayoutReportsIssue.Key.SEO);

		Assert.assertFalse(layoutReportsIssue1.equals(layoutReportsIssue2));
	}

	@Test
	public void testNotEqualsWithDifferentKey() {
		LayoutReportsIssue layoutReportsIssue1 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);
		LayoutReportsIssue layoutReportsIssue2 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.ACCESSIBILITY);

		Assert.assertFalse(layoutReportsIssue1.equals(layoutReportsIssue2));
	}

	@Test
	public void testNotEqualsWithDifferentTotal() {
		LayoutReportsIssue layoutReportsIssue1 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);
		LayoutReportsIssue layoutReportsIssue2 = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 200)),
			LayoutReportsIssue.Key.SEO);

		Assert.assertFalse(layoutReportsIssue1.equals(layoutReportsIssue2));
	}

	@Test
	public void testToJSONObject() {
		LayoutReportsIssue layoutReportsIssue = new LayoutReportsIssue(
			Collections.singletonList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);

		JSONObject jsonObject = layoutReportsIssue.toJSONObject(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE);

		JSONArray detailsJSONArray = jsonObject.getJSONArray("details");

		Assert.assertEquals(1, detailsJSONArray.length());

		JSONObject detailJSONObject = detailsJSONArray.getJSONObject(0);

		Assert.assertEquals("title", detailJSONObject.getString("key"));
		Assert.assertEquals(100, detailJSONObject.getLong("total"));

		Assert.assertEquals(
			LayoutReportsIssue.Key.SEO.toString(), jsonObject.getString("key"));
		Assert.assertEquals(100, jsonObject.getLong("total"));
	}

	@Test
	public void testToString() {
		LayoutReportsIssue layoutReportsIssue = new LayoutReportsIssue(
			Arrays.asList(
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.CANONICAL_LINK, 99),
				new LayoutReportsIssue.Detail(
					LayoutReportsIssue.Detail.Key.TITLE, 100)),
			LayoutReportsIssue.Key.SEO);

		Assert.assertEquals(
			"{details={canonical-link=99, title=100}, key=seo, total=199}",
			layoutReportsIssue.toString());
	}

}