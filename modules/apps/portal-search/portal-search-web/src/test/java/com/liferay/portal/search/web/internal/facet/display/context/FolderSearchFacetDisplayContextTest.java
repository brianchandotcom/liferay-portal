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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.facet.display.context.builder.FolderSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.folder.facet.configuration.FolderFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.util.FacetDisplayContextTextUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
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

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

		Assert.assertEquals(
			StringPool.BLANK,
			folderSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithEmptyTermCollectors()
		throws Exception {

		Mockito.when(
			_facetCollector.getTermCollectors()
		).thenReturn(
			Collections.emptyList()
		);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null);

		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		_addFolder(folderId, title);

		String facetParam = String.valueOf(folderId);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(title, bucketDisplayContext.getBucketText());
		Assert.assertEquals(
			String.valueOf(folderId), bucketDisplayContext.getFilterValue());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, folderSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithUnmatchedTermCollector()
		throws Exception {

		Mockito.when(
			_facetCollector.getTermCollectors()
		).thenReturn(
			Arrays.asList(new DefaultTermCollector("0", 200))
		);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null);

		Assert.assertTrue(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		_addFolder(folderId, title);

		int count = RandomTestUtil.randomInt();

		FacetDisplayContextTextUtil.setUpTermCollector(
			_facetCollector, folderId, count);

		String facetParam = "";

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(title, bucketDisplayContext.getBucketText());
		Assert.assertEquals(
			String.valueOf(folderId), bucketDisplayContext.getFilterValue());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertFalse(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, folderSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		long folderId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		_addFolder(folderId, title);

		int count = RandomTestUtil.randomInt();

		FacetDisplayContextTextUtil.setUpTermCollector(
			_facetCollector, folderId, count);

		String facetParam = String.valueOf(folderId);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(title, bucketDisplayContext.getBucketText());
		Assert.assertEquals(
			String.valueOf(folderId), bucketDisplayContext.getFilterValue());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, folderSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(folderSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(folderSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			new String[] {"alpha", "charlie", "bravo", "delta"},
			new int[] {4, 5, 5, 6});

		FacetDisplayContextTextUtil.setUpTermCollectors(
			_facetCollector, termCollectors);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null, "count:asc");

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString =
			FacetDisplayContextTextUtil.buildNameFrequencyString(
				bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"alpha:4|bravo:5|charlie:5|delta:6", nameFrequencyString);
	}

	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			new String[] {"alpha", "charlie", "bravo", "delta"},
			new int[] {4, 5, 5, 6});

		FacetDisplayContextTextUtil.setUpTermCollectors(
			_facetCollector, termCollectors);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null, "count:desc");

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString =
			FacetDisplayContextTextUtil.buildNameFrequencyString(
				bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"delta:6|bravo:5|charlie:5|alpha:4", nameFrequencyString);
	}

	@Test
	public void testOrderByTermValueAscending() throws Exception {
		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			"zeroFolderId", "alpha", "bravo", "charlie", "bravo");

		FacetDisplayContextTextUtil.setUpTermCollectors(
			_facetCollector, termCollectors);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null, "key:asc");

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString =
			FacetDisplayContextTextUtil.buildNameFrequencyString(
				bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"alpha:2|bravo:5|bravo:3|charlie:4", nameFrequencyString);
	}

	@Test
	public void testOrderByTermValueDescending() throws Exception {
		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			"zeroFolderId", "alpha", "bravo", "charlie", "bravo");

		FacetDisplayContextTextUtil.setUpTermCollectors(
			_facetCollector, termCollectors);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null, "key:desc");

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString =
			FacetDisplayContextTextUtil.buildNameFrequencyString(
				bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"charlie:4|bravo:5|bravo:3|alpha:2", nameFrequencyString);
	}

	@Test
	public void testViewPermissionGrantedForSearchResultButDeniedForParentFolder()
		throws Exception {

		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			"zeroFolderId", null, "null", "", "   ", "assert", "volatile",
			"alpha");

		FacetDisplayContextTextUtil.setUpTermCollectors(
			_facetCollector, termCollectors);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			createDisplayContext(null);

		List<BucketDisplayContext> bucketDisplayContexts =
			folderSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString =
			FacetDisplayContextTextUtil.buildNameFrequencyString(
				bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(), "alpha:8|volatile:7|assert:6",
			nameFrequencyString);

		Assert.assertEquals(
			termCollectors.toString(), 36,
			_getTotalTermCollectorFrequencyCount(termCollectors));
		Assert.assertEquals(
			bucketDisplayContexts.toString(), 21,
			_getTotalBucketDisplayContextFrequencyCount(bucketDisplayContexts));
	}

	protected FolderSearchFacetDisplayContext createDisplayContext(
			String facetParam)
		throws Exception {

		return createDisplayContext(facetParam, "count:desc");
	}

	protected FolderSearchFacetDisplayContext createDisplayContext(
			String facetParam, String order)
		throws Exception {

		FolderSearchFacetDisplayContextBuilder
			folderSearchFacetDisplayContextBuilder =
				new FolderSearchFacetDisplayContextBuilder(
					FacetDisplayContextTextUtil.getRenderRequest(
						FolderFacetPortletInstanceConfiguration.class));

		folderSearchFacetDisplayContextBuilder.setFacet(_facet);
		folderSearchFacetDisplayContextBuilder.setFolderTitleLookup(
			_folderTitleLookup);
		folderSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		folderSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		folderSearchFacetDisplayContextBuilder.setMaxTerms(0);
		folderSearchFacetDisplayContextBuilder.setOrder(order);
		folderSearchFacetDisplayContextBuilder.setParameterName(
			_facet.getFieldId());
		folderSearchFacetDisplayContextBuilder.setParameterValue(facetParam);

		return folderSearchFacetDisplayContextBuilder.build();
	}

	private void _addFolder(long folderId, String title) throws Exception {
		Mockito.doReturn(
			title
		).when(
			_folderTitleLookup
		).getFolderTitle(
			folderId
		);
	}

	private List<TermCollector> _addFoldersAndCreateTermCollectors(
			String... folderNames)
		throws Exception {

		List<TermCollector> termCollectors = new ArrayList<>();

		int folderId = 0;

		for (String folderName : folderNames) {
			_addFolder(folderId, folderName);

			int frequency = folderId + 1;

			termCollectors.add(
				FacetDisplayContextTextUtil.createTermCollector(
					String.valueOf(folderId), frequency));

			folderId++;
		}

		return termCollectors;
	}

	private List<TermCollector> _addFoldersAndCreateTermCollectors(
			String[] folderNames, int[] frequencies)
		throws Exception {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 1; i <= folderNames.length; i++) {
			_addFolder(i, folderNames[i - 1]);

			termCollectors.add(
				FacetDisplayContextTextUtil.createTermCollector(
					String.valueOf(i), frequencies[i - 1]));
		}

		return termCollectors;
	}

	private int _getTotalBucketDisplayContextFrequencyCount(
		List<BucketDisplayContext> bucketDisplayContexts) {

		int total = 0;

		for (BucketDisplayContext bucketDisplayContext :
				bucketDisplayContexts) {

			total += bucketDisplayContext.getFrequency();
		}

		return total;
	}

	private int _getTotalTermCollectorFrequencyCount(
		List<TermCollector> termCollectors) {

		int total = 0;

		for (TermCollector termCollector : termCollectors) {
			total += termCollector.getFrequency();
		}

		return total;
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);
	private final FolderTitleLookup _folderTitleLookup = Mockito.mock(
		FolderTitleLookup.class);

}