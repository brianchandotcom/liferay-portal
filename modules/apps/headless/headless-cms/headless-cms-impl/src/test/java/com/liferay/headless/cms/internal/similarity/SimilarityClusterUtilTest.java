/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.similarity;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mikel Lorza
 */
public class SimilarityClusterUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetClustersChainsAssetsThroughSharedBands() {
		List<List<Long>> clusters = SimilarityClusterUtil.getClusters(
			_BAND_FIELD,
			Arrays.asList(
				_mockDocument(1L, "b1", "b2", "b3"),
				_mockDocument(2L, "b1", "b2", "b3", "b4", "b5", "b6"),
				_mockDocument(3L, "b4", "b5", "b6")),
			_toSet("b1", "b2", "b3", "b4", "b5", "b6"));

		Assert.assertEquals(clusters.toString(), 1, clusters.size());
		Assert.assertEquals(Arrays.asList(1L, 2L, 3L), clusters.get(0));
	}

	@Test
	public void testGetClustersDropsAssetsSharingNoBand() {
		List<List<Long>> clusters = SimilarityClusterUtil.getClusters(
			_BAND_FIELD,
			Arrays.asList(
				_mockDocument(1L, "b1", "b2", "b3"),
				_mockDocument(2L, "b1", "b2", "b3"), _mockDocument(3L, "b7")),
			_toSet("b1", "b2", "b3"));

		Assert.assertEquals(clusters.toString(), 1, clusters.size());
		Assert.assertEquals(Arrays.asList(1L, 2L), clusters.get(0));
	}

	@Test
	public void testGetClustersIgnoresBandsThatAreNotShared() {
		Assert.assertEquals(
			Collections.emptyList(),
			SimilarityClusterUtil.getClusters(
				_BAND_FIELD,
				Arrays.asList(_mockDocument(1L, "b9"), _mockDocument(2L, "b9")),
				_toSet("b1")));
	}

	@Test
	public void testGetClustersNeedsMoreThanTwoSharedBands() {
		Assert.assertEquals(
			Collections.emptyList(),
			SimilarityClusterUtil.getClusters(
				_BAND_FIELD,
				Arrays.asList(
					_mockDocument(1L, "b1", "b2"),
					_mockDocument(2L, "b1", "b2")),
				_toSet("b1", "b2")));
	}

	@Test
	public void testGetMinObjectEntryId() {
		Assert.assertEquals(
			Long.valueOf(2),
			SimilarityClusterUtil.getMinObjectEntryId(
				Arrays.asList(7L, 2L, 5L)));
	}

	private Document _mockDocument(Long objectEntryId, String... bands) {
		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.getLong("objectEntryId")
		).thenReturn(
			objectEntryId
		);

		Mockito.when(
			document.getStrings(_BAND_FIELD)
		).thenReturn(
			ListUtil.fromArray(bands)
		);

		return document;
	}

	private Set<String> _toSet(String... bands) {
		return new HashSet<>(Arrays.asList(bands));
	}

	private static final String _BAND_FIELD = RandomTestUtil.randomString();

}