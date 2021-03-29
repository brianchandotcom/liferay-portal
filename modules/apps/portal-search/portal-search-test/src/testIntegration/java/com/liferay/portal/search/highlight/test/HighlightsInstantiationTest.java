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

package com.liferay.portal.search.highlight.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.highlight.Highlights;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class HighlightsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBuilders() {
		HighlightBuilder highlightBuilder = _highlightBuilderFactory.builder();

		Assert.assertNotNull(highlightBuilder.build());

		FieldConfigBuilder fieldConfigBuilder =
			_fieldConfigBuilderFactory.builder("field");

		Assert.assertNotNull(fieldConfigBuilder.build());
	}

	@Test
	public void testFactories() {
		Assert.assertNotNull(_highlightBuilderFactory.builder());

		FieldConfigBuilder fieldConfigBuilder =
			_fieldConfigBuilderFactory.builder("field");

		Assert.assertNotNull(fieldConfigBuilder);

		Assert.assertNotNull(_highlights.highlight(fieldConfigBuilder.build()));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	private static FieldConfigBuilderFactory _fieldConfigBuilderFactory;

	@Inject
	private static HighlightBuilderFactory _highlightBuilderFactory;

	@Inject
	private static Highlights _highlights;

}