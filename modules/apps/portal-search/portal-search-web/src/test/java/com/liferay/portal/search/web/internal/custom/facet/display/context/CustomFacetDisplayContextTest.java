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

package com.liferay.portal.search.web.internal.custom.facet.display.context;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public class CustomFacetDisplayContextTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.doReturn(
			_facetCollector
		).when(
			_facet
		).getFacetCollector();
	}

	@Test
	public void testEmptyCustomDisplayCaption() throws Exception {
		String customDisplayCaption = "";
		String fieldToAggregate = "groupId";
		String parameterValue = "";

		CustomFacetDisplayContext customFacetDisplayContext =
			createDisplayContext(
				customDisplayCaption, fieldToAggregate, parameterValue);

		List<CustomFacetTermDisplayContext> customFacetTermDisplayContexts =
			customFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			customFacetTermDisplayContexts.toString(), 0,
			customFacetTermDisplayContexts.size());

		Assert.assertTrue(customFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(customFacetDisplayContext.isRenderNothing());
		Assert.assertEquals(
			fieldToAggregate, customFacetDisplayContext.getDisplayCaption());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String fieldName = RandomTestUtil.randomString();

		String parameterValue = fieldName;

		CustomFacetDisplayContext customFacetDisplayContext =
			createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<CustomFacetTermDisplayContext> customFacetTermDisplayContexts =
			customFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			customFacetTermDisplayContexts.toString(), 1,
			customFacetTermDisplayContexts.size());

		CustomFacetTermDisplayContext customFacetTermDisplayContext =
			customFacetTermDisplayContexts.get(0);

		Assert.assertEquals(0, customFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			fieldName, customFacetTermDisplayContext.getFieldName());
		Assert.assertTrue(customFacetTermDisplayContext.isSelected());
		Assert.assertTrue(customFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertFalse(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String fieldName = RandomTestUtil.randomString();
		int count = RandomTestUtil.randomInt();

		setUpOneTermCollector(fieldName, count);

		String parameterValue = "";

		CustomFacetDisplayContext customFacetDisplayContext =
			createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<CustomFacetTermDisplayContext> customFacetTermDisplayContexts =
			customFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			customFacetTermDisplayContexts.toString(), 1,
			customFacetTermDisplayContexts.size());

		CustomFacetTermDisplayContext customFacetTermDisplayContext =
			customFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			count, customFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			fieldName, customFacetTermDisplayContext.getFieldName());
		Assert.assertFalse(customFacetTermDisplayContext.isSelected());
		Assert.assertTrue(customFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertTrue(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String fieldName = RandomTestUtil.randomString();
		int count = RandomTestUtil.randomInt();

		setUpOneTermCollector(fieldName, count);

		String parameterValue = fieldName;

		CustomFacetDisplayContext customFacetDisplayContext =
			createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<CustomFacetTermDisplayContext> customFacetTermDisplayContexts =
			customFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			customFacetTermDisplayContexts.toString(), 1,
			customFacetTermDisplayContexts.size());

		CustomFacetTermDisplayContext customFacetTermDisplayContext =
			customFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			count, customFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			fieldName, customFacetTermDisplayContext.getFieldName());
		Assert.assertTrue(customFacetTermDisplayContext.isSelected());
		Assert.assertTrue(customFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertFalse(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	protected CustomFacetDisplayContext createDisplayContext(
		String customDisplayCaption, String fieldToAggregate,
		String parameterValue) {

		CustomFacetDisplayBuilder customFacetDisplayBuilder =
			new CustomFacetDisplayBuilder();

		customFacetDisplayBuilder.setFacet(_facet);
		customFacetDisplayBuilder.setParameterName("custom");
		customFacetDisplayBuilder.setParameterValue(parameterValue);
		customFacetDisplayBuilder.setFrequenciesVisible(true);

		customFacetDisplayBuilder.setFrequencyThreshold(0);
		customFacetDisplayBuilder.setMaxTerms(0);

		customFacetDisplayBuilder.setCustomDisplayCaption(
			Optional.ofNullable(customDisplayCaption));
		customFacetDisplayBuilder.setFieldToAggregate(fieldToAggregate);

		return customFacetDisplayBuilder.build();
	}

	protected TermCollector createTermCollector(String fieldName, int count) {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			count
		).when(
			termCollector
		).getFrequency();

		Mockito.doReturn(
			fieldName
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	protected void setUpOneTermCollector(String fieldName, int count) {
		Mockito.doReturn(
			Collections.singletonList(createTermCollector(fieldName, count))
		).when(
			_facetCollector
		).getTermCollectors();
	}

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

}