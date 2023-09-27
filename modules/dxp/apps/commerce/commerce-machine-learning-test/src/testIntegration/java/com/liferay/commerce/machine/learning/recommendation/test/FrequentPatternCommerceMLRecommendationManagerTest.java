/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.machine.learning.recommendation.test.util.comparator.FrequentPatternCommerceMLRecommendationComparator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class FrequentPatternCommerceMLRecommendationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_frequentPatternCommerceMLRecommendations =
			_addFrequentPatternCommerceMLRecommendations();
	}

	@Test
	public void testGetFrequentPatternCommerceMLRecommendations()
		throws Exception {

		FrequentPatternCommerceMLRecommendation
			randomFrequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_frequentPatternCommerceMLRecommendations.size() - 1));

		List<Long> antecedentIdList = ListUtil.fromArray(
			randomFrequentPatternCommerceMLRecommendation.getAntecedentIds());

		Collections.shuffle(antecedentIdList);

		antecedentIdList = antecedentIdList.subList(
			0, RandomTestUtil.randomInt(1, antecedentIdList.size()));

		long[] antecedentIds = ArrayUtil.toLongArray(antecedentIdList);

		Map<Long, FrequentPatternCommerceMLRecommendation>
			expectedFrequentPatternCommerceMLRecommendations =
				new LinkedHashMap<>();

		for (FrequentPatternCommerceMLRecommendation
				curFrequentPatternCommerceMLRecommendation :
					ListUtil.sort(
						ListUtil.filter(
							_frequentPatternCommerceMLRecommendations,
							frequentPatternCommerceMLRecommendation ->
								_filterFrequentPatternCommerceMLRecommendation(
									frequentPatternCommerceMLRecommendation,
									antecedentIds)),
						new FrequentPatternCommerceMLRecommendationComparator(
							antecedentIds))) {

			expectedFrequentPatternCommerceMLRecommendations.putIfAbsent(
				curFrequentPatternCommerceMLRecommendation.
					getRecommendedEntryClassPK(),
				curFrequentPatternCommerceMLRecommendation);
		}

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					antecedentIds,
					new ArrayList<>(
						expectedFrequentPatternCommerceMLRecommendations.
							values()));

				return null;
			});
	}

	private List<FrequentPatternCommerceMLRecommendation>
			_addFrequentPatternCommerceMLRecommendations()
		throws Exception {

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < _PRODUCT_COUNT; i++) {
			Set<Long> antecedentIds = new HashSet<>();

			for (int j = 0;
				 j < RandomTestUtil.randomInt(1, _MAX_ANTECEDENT_COUNT); j++) {

				antecedentIds.add(RandomTestUtil.randomLong());
			}

			for (int j = 0; j < _RECOMMENDATION_COUNT; j++) {
				float score = 1.0F - (j / 10.0F);

				frequentPatternCommerceMLRecommendations.add(
					_createFrequentPatternCommerceMLRecommendation(
						ArrayUtil.toLongArray(antecedentIds), score));
			}
		}

		Collections.shuffle(frequentPatternCommerceMLRecommendations);

		for (FrequentPatternCommerceMLRecommendation
				frequentPatternCommerceMLRecommendation :
					frequentPatternCommerceMLRecommendations) {

			_frequentPatternCommerceMLRecommendationManager.
				addFrequentPatternCommerceMLRecommendation(
					frequentPatternCommerceMLRecommendation);
		}

		return frequentPatternCommerceMLRecommendations;
	}

	private void _assetResultEquals(
			long[] antecedentIds,
			List<FrequentPatternCommerceMLRecommendation>
				expectedFrequentPatternCommerceMLRecommendations)
		throws Exception {

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations =
				_frequentPatternCommerceMLRecommendationManager.
					getFrequentPatternCommerceMLRecommendations(
						TestPropsValues.getCompanyId(), antecedentIds);

		int expectedRecommendationsSize = Math.min(
			10, expectedFrequentPatternCommerceMLRecommendations.size());

		Assert.assertEquals(
			"Recommendation list size", expectedRecommendationsSize,
			frequentPatternCommerceMLRecommendations.size());

		for (int i = 0; i < expectedRecommendationsSize; i++) {
			FrequentPatternCommerceMLRecommendation
				expectedFrequentPatternCommerceMLRecommendation =
					expectedFrequentPatternCommerceMLRecommendations.get(i);

			FrequentPatternCommerceMLRecommendation
				frequentPatternCommerceMLRecommendation =
					frequentPatternCommerceMLRecommendations.get(i);

			Assert.assertEquals(
				expectedFrequentPatternCommerceMLRecommendation.
					getRecommendedEntryClassPK(),
				frequentPatternCommerceMLRecommendation.
					getRecommendedEntryClassPK());

			Assert.assertEquals(
				expectedFrequentPatternCommerceMLRecommendation.getScore(),
				frequentPatternCommerceMLRecommendation.getScore(), 0.0);
		}
	}

	private FrequentPatternCommerceMLRecommendation
			_createFrequentPatternCommerceMLRecommendation(
				long[] antecedentIds, float score)
		throws Exception {

		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendationManager.create();

		frequentPatternCommerceMLRecommendation.setAntecedentIds(antecedentIds);
		frequentPatternCommerceMLRecommendation.setAntecedentIdsLength(
			antecedentIds.length);
		frequentPatternCommerceMLRecommendation.setCompanyId(
			TestPropsValues.getCompanyId());
		frequentPatternCommerceMLRecommendation.setCreateDate(new Date());
		frequentPatternCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.randomInt(1, _PRODUCT_COUNT));
		frequentPatternCommerceMLRecommendation.setScore(score);

		return frequentPatternCommerceMLRecommendation;
	}

	private boolean _filterAntecedentIds(
		long[] antecedentIds, long[] expectedAntecedentIds) {

		for (long expectedAntecedentId : expectedAntecedentIds) {
			if (ArrayUtil.contains(antecedentIds, expectedAntecedentId)) {
				return true;
			}
		}

		return false;
	}

	private boolean _filterFrequentPatternCommerceMLRecommendation(
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation,
		long[] expectedAntecedentIds) {

		if (ArrayUtil.contains(
				expectedAntecedentIds,
				frequentPatternCommerceMLRecommendation.
					getRecommendedEntryClassPK())) {

			return false;
		}

		return _filterAntecedentIds(
			frequentPatternCommerceMLRecommendation.getAntecedentIds(),
			expectedAntecedentIds);
	}

	private static final int _MAX_ANTECEDENT_COUNT = 6;

	private static final int _PRODUCT_COUNT = 5;

	private static final int _RECOMMENDATION_COUNT = 5;

	@Inject
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

	private List<FrequentPatternCommerceMLRecommendation>
		_frequentPatternCommerceMLRecommendations;

}