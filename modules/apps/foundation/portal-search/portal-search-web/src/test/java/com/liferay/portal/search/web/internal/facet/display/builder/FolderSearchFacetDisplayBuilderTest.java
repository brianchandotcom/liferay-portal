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

package com.liferay.portal.search.web.internal.facet.display.builder;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetDisplayContext;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class FolderSearchFacetDisplayBuilderTest {

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
	public void testEmptySearchResults() {
		FolderSearchFacetDisplayBuilder folderSearchFacetDisplayBuilder =
			createDisplayBuilder();

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			folderSearchFacetDisplayBuilder.build();

		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithEmptyTermCollectors() {
		Mockito.when(
			_facetCollector.getTermCollectors()
		).thenReturn(
			Collections.emptyList()
		);

		FolderSearchFacetDisplayBuilder folderSearchFacetDisplayBuilder =
			createDisplayBuilder();

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			folderSearchFacetDisplayBuilder.build();

		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithNullTermCollector() {
		Mockito.when(
			_facetCollector.getTermCollectors()
		).thenReturn(
			Arrays.asList(new DefaultTermCollector("0", 200))
		);

		FolderSearchFacetDisplayBuilder folderSearchFacetDisplayBuilder =
			createDisplayBuilder();

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			folderSearchFacetDisplayBuilder.build();

		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	protected FolderSearchFacetDisplayBuilder createDisplayBuilder() {
		FolderSearchFacetDisplayBuilder folderSearchFacetDisplayBuilder =
			new FolderSearchFacetDisplayBuilder();

		folderSearchFacetDisplayBuilder.setFacet(_facet);
		folderSearchFacetDisplayBuilder.setFrequenciesVisible(true);
		folderSearchFacetDisplayBuilder.setFrequencyThreshold(1);
		folderSearchFacetDisplayBuilder.setMaxTerms(10);
		folderSearchFacetDisplayBuilder.setParameterName("folderId");

		return folderSearchFacetDisplayBuilder;
	}

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

}