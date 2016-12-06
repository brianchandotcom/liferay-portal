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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContextTest {

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
	public void testEmptySearchResults() throws Exception {
		String facetParam = null;

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts =
				folderSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(0, folderSearchFacetTermDisplayContexts.size());

		Assert.assertEquals(
			facetParam,
			folderSearchFacetDisplayContext.getFieldParamInputValue());
		Assert.assertTrue(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		addFolder(folderId, title);

		String facetParam = String.valueOf(folderId);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts =
				folderSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(1, folderSearchFacetTermDisplayContexts.size());

		FolderSearchFacetTermDisplayContext
			folderSearchFacetTermDisplayContext =
				folderSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			0, folderSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			title, folderSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			folderId, folderSearchFacetTermDisplayContext.getFolderId());
		Assert.assertTrue(folderSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			folderSearchFacetTermDisplayContext.isShowFrequency());

		Assert.assertEquals(
			facetParam,
			folderSearchFacetDisplayContext.getFieldParamInputValue());
		Assert.assertFalse(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		addFolder(folderId, title);

		int count = RandomTestUtil.randomInt();

		setUpOneTermCollector(folderId, count);

		String facetParam = "";

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts =
				folderSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(1, folderSearchFacetTermDisplayContexts.size());

		FolderSearchFacetTermDisplayContext
			folderSearchFacetTermDisplayContext =
				folderSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			count, folderSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			title, folderSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			folderId, folderSearchFacetTermDisplayContext.getFolderId());
		Assert.assertFalse(folderSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			folderSearchFacetTermDisplayContext.isShowFrequency());

		Assert.assertEquals(
			facetParam,
			folderSearchFacetDisplayContext.getFieldParamInputValue());
		Assert.assertTrue(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		addFolder(folderId, title);

		int count = RandomTestUtil.randomInt();

		setUpOneTermCollector(folderId, count);

		String facetParam = String.valueOf(folderId);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts =
				folderSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(1, folderSearchFacetTermDisplayContexts.size());

		FolderSearchFacetTermDisplayContext
			folderSearchFacetTermDisplayContext =
				folderSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			count, folderSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			title, folderSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			folderId, folderSearchFacetTermDisplayContext.getFolderId());
		Assert.assertTrue(folderSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			folderSearchFacetTermDisplayContext.isShowFrequency());

		Assert.assertEquals(
			facetParam,
			folderSearchFacetDisplayContext.getFieldParamInputValue());
		Assert.assertFalse(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	protected void addFolder(long folderId, String title) throws Exception {
		Mockito.doReturn(
			title
		).when(
			_folderTitleLookup
		).getFolderTitle(
			folderId
		);
	}

	protected FolderSearchFacetDisplayContext createDisplayContext(
			String facetParam)
		throws Exception {

		return new FolderSearchFacetDisplayContext(
			_facet, facetParam, 0, 0, true, _folderTitleLookup);
	}

	protected TermCollector createTermCollector(long folderId, int count) {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			count
		).when(
			termCollector
		).getFrequency();

		Mockito.doReturn(
			String.valueOf(folderId)
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	protected void setUpOneTermCollector(long folderId, int count) {
		Mockito.doReturn(
			Collections.singletonList(createTermCollector(folderId, count))
		).when(
			_facetCollector
		).getTermCollectors();
	}

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

	@Mock
	private FolderTitleLookup _folderTitleLookup;

}