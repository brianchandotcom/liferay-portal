/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.FolderSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.folder.facet.configuration.FolderFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws Exception {

		return createFacetDisplayContext(parameterValue, "count:desc");
	}

	@Override
	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws Exception {

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(FolderFacetPortletInstanceConfiguration.class)
		);

		FolderSearchFacetDisplayContextBuilder
			folderSearchFacetDisplayContextBuilder =
				new FolderSearchFacetDisplayContextBuilder(getRenderRequest());

		folderSearchFacetDisplayContextBuilder.setFacet(facet);
		folderSearchFacetDisplayContextBuilder.setFolderTitleLookup(
			_folderTitleLookup);
		folderSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		folderSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		folderSearchFacetDisplayContextBuilder.setMaxTerms(0);
		folderSearchFacetDisplayContextBuilder.setOrder(order);
		folderSearchFacetDisplayContextBuilder.setParameterName(
			facet.getFieldId());
		folderSearchFacetDisplayContextBuilder.setParameterValue(
			parameterValue);

		return folderSearchFacetDisplayContextBuilder.build();
	}

	@Test
	public void testEmptySearchResultsWithEmptyTermCollectors()
		throws Exception {

		Mockito.when(
			facetCollector.getTermCollectors()
		).thenReturn(
			Collections.emptyList()
		);

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			null);

		Assert.assertTrue(facetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithUnmatchedTermCollector()
		throws Exception {

		Mockito.when(
			facetCollector.getTermCollectors()
		).thenReturn(
			Arrays.asList(new DefaultTermCollector("0", 200))
		);

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			null);

		Assert.assertTrue(facetDisplayContext.isRenderNothing());
	}

	@Test
	public void testViewPermissionGrantedForSearchResultButDeniedForParentFolder()
		throws Exception {

		List<TermCollector> termCollectors = _addFoldersAndCreateTermCollectors(
			"zeroFolderId", null, "null", "", "   ", "assert", "volatile",
			"alpha");

		setUpTermCollectors(facetCollector, termCollectors);

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			null);

		List<BucketDisplayContext> bucketDisplayContexts =
			facetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString = buildNameFrequencyString(
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

	@Override
	protected String getFilterValue(String term) {
		return String.valueOf(_folderId);
	}

	@Override
	protected void setUpAsset(String term) throws Exception {
		_folderId = RandomTestUtil.randomLong();

		_addFolder(_folderId, term);
	}

	@Override
	protected void testOrderBy(
			int[] expectedFrequencies, String[] expectedTerms,
			int[] frequencies, String order, String[] terms)
		throws Exception {

		setUpTermCollectors(
			facetCollector,
			_addFoldersAndCreateTermCollectors(terms, frequencies));

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			null, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedTerms,
			expectedFrequencies);
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
				createTermCollector(String.valueOf(folderId), frequency));

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
				createTermCollector(String.valueOf(i), frequencies[i - 1]));
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

	private long _folderId;
	private final FolderTitleLookup _folderTitleLookup = Mockito.mock(
		FolderTitleLookup.class);

}