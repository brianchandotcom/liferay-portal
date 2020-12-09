/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.machine.learning.recommendation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.machine.learning.recommendation.test.util.FrequentPatternCommerceMLRecommendationComparator;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		_company = CompanyTestUtil.addCompany();

		_frequentPatternCommerceMLRecommendations = _populateEntries(5, 5, 6);
	}

	@Test
	public void testGetFrequentPatternCommerceMLRecommendations()
		throws Exception {

		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_frequentPatternCommerceMLRecommendations.size() - 1));

		List<Long> antecedentIdList = ListUtil.fromArray(
			frequentPatternCommerceMLRecommendation.getAntecedentIds());

		Collections.shuffle(antecedentIdList);

		antecedentIdList = antecedentIdList.subList(
			0, RandomTestUtil.randomInt(1, antecedentIdList.size()));

		long[] antecedentIds = ArrayUtil.toLongArray(antecedentIdList);

		Stream<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendationStream =
				_frequentPatternCommerceMLRecommendations.stream();

		Map<Long, FrequentPatternCommerceMLRecommendation>
			expectedFrequentPatternCommerceMLRecommendationsMap =
				frequentPatternCommerceMLRecommendationStream.filter(
					recommendation ->
						_filterFrequentPatternCommerceMLRecommendation(
							recommendation, antecedentIds)
				).sorted(
					new FrequentPatternCommerceMLRecommendationComparator(
						antecedentIds)
				).collect(
					Collectors.toMap(
						FrequentPatternCommerceMLRecommendation::
							getRecommendedEntryClassPK,
						Function.identity(), (item1, item2) -> item1,
						LinkedHashMap::new)
				);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS, 5, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					antecedentIds,
					new ArrayList(
						expectedFrequentPatternCommerceMLRecommendationsMap.
							values()));

				return null;
			});
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
						_company.getCompanyId(), antecedentIds);

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
			long[] antecedentIds, int productCount) {

		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendationManager.create();

		frequentPatternCommerceMLRecommendation.setAntecedentIds(antecedentIds);
		frequentPatternCommerceMLRecommendation.setAntecedentIdsLength(
			antecedentIds.length);
		frequentPatternCommerceMLRecommendation.setCompanyId(
			_company.getCompanyId());
		frequentPatternCommerceMLRecommendation.setCreateDate(new Date());
		frequentPatternCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.randomInt(1, productCount));
		frequentPatternCommerceMLRecommendation.setScore(1.0F);

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

	private List<FrequentPatternCommerceMLRecommendation> _populateEntries(
			int productCount, int recommendationCount, int maxAntecedentCount)
		throws Exception {

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < productCount; i++) {
			for (int j = 0; j < recommendationCount; j++) {
				Set<Long> antecedentIds = new HashSet<>();

				int assetCategoryIdsSize = RandomTestUtil.randomInt(
					1, maxAntecedentCount);

				for (int k = 0; k <= assetCategoryIdsSize; k++) {
					antecedentIds.add(
						(long)RandomTestUtil.randomInt(1, maxAntecedentCount));
				}

				frequentPatternCommerceMLRecommendations.add(
					_createFrequentPatternCommerceMLRecommendation(
						ArrayUtil.toLongArray(antecedentIds), productCount));
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

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

	private List<FrequentPatternCommerceMLRecommendation>
		_frequentPatternCommerceMLRecommendations;

}