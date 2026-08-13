/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.similarity;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	public void testGetClustersChainsAssetsThroughSharedSimilarityKeys() {
		Map<Long, List<Long>> objectEntryIdsByClusterId =
			SimilarityClusterUtil.getClusters(
				Arrays.asList(
					_mockDocument(1L, "k1", "k2", "k3"),
					_mockDocument(2L, "k1", "k2", "k3", "k4", "k5", "k6"),
					_mockDocument(3L, "k4", "k5", "k6")),
				_toSet("k1", "k2", "k3", "k4", "k5", "k6"));

		Assert.assertEquals(
			objectEntryIdsByClusterId.toString(), 1,
			objectEntryIdsByClusterId.size());
		Assert.assertEquals(
			Arrays.asList(1L, 2L, 3L), objectEntryIdsByClusterId.get(1L));
	}

	@Test
	public void testGetClustersDropsAssetsSharingNoSimilarityKey() {
		Map<Long, List<Long>> objectEntryIdsByClusterId =
			SimilarityClusterUtil.getClusters(
				Arrays.asList(
					_mockDocument(1L, "k1", "k2", "k3"),
					_mockDocument(2L, "k1", "k2", "k3"),
					_mockDocument(3L, "k7")),
				_toSet("k1", "k2", "k3"));

		Assert.assertEquals(
			objectEntryIdsByClusterId.toString(), 1,
			objectEntryIdsByClusterId.size());
		Assert.assertEquals(
			Arrays.asList(1L, 2L), objectEntryIdsByClusterId.get(1L));
	}

	@Test
	public void testGetClustersIgnoresSimilarityKeysThatAreNotShared() {
		Assert.assertEquals(
			Collections.emptyMap(),
			SimilarityClusterUtil.getClusters(
				Arrays.asList(_mockDocument(1L, "k9"), _mockDocument(2L, "k9")),
				_toSet("k1")));
	}

	@Test
	public void testGetClustersKeysClustersByLowestObjectEntryId() {
		Map<Long, List<Long>> objectEntryIdsByClusterId =
			SimilarityClusterUtil.getClusters(
				Arrays.asList(
					_mockDocument(7L, "k1", "k2", "k3"),
					_mockDocument(2L, "k1", "k2", "k3"),
					_mockDocument(5L, "k1", "k2", "k3")),
				_toSet("k1", "k2", "k3"));

		Assert.assertEquals(
			Arrays.asList(2L, 5L, 7L), objectEntryIdsByClusterId.get(2L));
	}

	@Test
	public void testGetClustersNeedsMoreThanTwoSharedSimilarityKeys() {
		Assert.assertEquals(
			Collections.emptyMap(),
			SimilarityClusterUtil.getClusters(
				Arrays.asList(
					_mockDocument(1L, "k1", "k2"),
					_mockDocument(2L, "k1", "k2")),
				_toSet("k1", "k2")));
	}

	@Test
	public void testGetClustersOrdersBiggestClustersFirst() {
		Map<Long, List<Long>> objectEntryIdsByClusterId =
			SimilarityClusterUtil.getClusters(
				Arrays.asList(
					_mockDocument(1L, "k1", "k2", "k3"),
					_mockDocument(2L, "k1", "k2", "k3"),
					_mockDocument(3L, "k4", "k5", "k6"),
					_mockDocument(4L, "k4", "k5", "k6"),
					_mockDocument(5L, "k7", "k8", "k9"),
					_mockDocument(6L, "k7", "k8", "k9"),
					_mockDocument(7L, "k7", "k8", "k9")),
				_toSet("k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9"));

		Assert.assertEquals(
			Arrays.asList(5L, 1L, 3L),
			new ArrayList<>(objectEntryIdsByClusterId.keySet()));
	}

	private Document _mockDocument(
		Long objectEntryId, String... similarityKeys) {

		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.getLong("objectEntryId")
		).thenReturn(
			objectEntryId
		);

		Mockito.when(
			document.getStrings("textSimilarityKeys")
		).thenReturn(
			ListUtil.fromArray(similarityKeys)
		);

		return document;
	}

	private Set<String> _toSet(String... similarityKeys) {
		return new HashSet<>(Arrays.asList(similarityKeys));
	}

}