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

package com.liferay.segments.odata.matcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.context.Context;
import com.liferay.segments.odata.matcher.ODataMatcher;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class ContextODataMatcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMatchesWithAnd() throws Exception {
		Context context = new Context();

		context.put("languageId", "en");
		context.put("userAgent", "chrome");

		Assert.assertTrue(
			_contextODataMatcher.matches(
				"(languageId eq 'en') and (userAgent eq 'chrome')", context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				"(languageId eq 'en') and (userAgent eq 'firefox')", context));
	}

	@Test
	public void testMatchesWithOr() throws Exception {
		Context context = new Context();

		context.put("languageId", "en");
		context.put("userAgent", "chrome");

		Assert.assertTrue(
			_contextODataMatcher.matches(
				"(languageId eq 'en') or (userAgent eq 'firefox')", context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				"(languageId eq 'fr') or (userAgent eq 'chrome')", context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				"(languageId eq 'fr') and (userAgent eq 'firefox')", context));
	}

	@Inject(
		filter = "target.class.name=com.liferay.segments.context.Context",
		type = ODataMatcher.class
	)
	private ODataMatcher<Context> _contextODataMatcher;

}