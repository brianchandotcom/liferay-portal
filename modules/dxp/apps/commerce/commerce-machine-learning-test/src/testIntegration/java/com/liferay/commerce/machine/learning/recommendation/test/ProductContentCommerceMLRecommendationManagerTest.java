/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendationManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
public class ProductContentCommerceMLRecommendationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_productContentCommerceMLRecommendations =
			_addProductContentCommerceMLRecommendations();
	}

	@Test
	public void testGetProductContentCommerceMLRecommendations()
		throws Exception {

		ProductContentCommerceMLRecommendation
			productContentCommerceMLRecommendation =
				_productContentCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_productContentCommerceMLRecommendations.size() - 1));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					productContentCommerceMLRecommendation.getEntryClassPK(),
					ListUtil.sort(
						ListUtil.filter(
							_productContentCommerceMLRecommendations,
							recommendation ->
								recommendation.getEntryClassPK() ==
									productContentCommerceMLRecommendation.
										getEntryClassPK()),
						Comparator.comparingInt(
							ProductContentCommerceMLRecommendation::getRank)));

				return null;
			});
	}

	private List<ProductContentCommerceMLRecommendation>
			_addProductContentCommerceMLRecommendations()
		throws Exception {

		List<ProductContentCommerceMLRecommendation>
			productContentCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < _PRODUCT_COUNT; i++) {
			long entryClassPK = RandomTestUtil.randomLong();

			for (int j = 0; j < _RECOMMENDATION_COUNT; j++) {
				int rank = RandomTestUtil.randomInt(1, 10);

				float score = 1.0F - (rank / 10.0F);

				productContentCommerceMLRecommendations.add(
					_createProductContentCommerceMLRecommendation(
						entryClassPK, rank, score));
			}
		}

		Collections.shuffle(productContentCommerceMLRecommendations);

		for (ProductContentCommerceMLRecommendation
				productContentCommerceMLRecommendation :
					productContentCommerceMLRecommendations) {

			_productContentCommerceMLRecommendationManager.
				addProductContentCommerceMLRecommendation(
					productContentCommerceMLRecommendation);
		}

		return productContentCommerceMLRecommendations;
	}

	private void _assetResultEquals(
			long entryClassPK,
			List<ProductContentCommerceMLRecommendation>
				expectedProductContentCommerceMLRecommendations)
		throws Exception {

		List<ProductContentCommerceMLRecommendation>
			productContentCommerceMLRecommendations =
				_productContentCommerceMLRecommendationManager.
					getProductContentCommerceMLRecommendations(
						TestPropsValues.getCompanyId(), entryClassPK);

		int expectedRecommendationsSize = Math.min(
			10, expectedProductContentCommerceMLRecommendations.size());

		Assert.assertEquals(
			"Recommendation list size", expectedRecommendationsSize,
			productContentCommerceMLRecommendations.size());

		for (int i = 0; i < expectedRecommendationsSize; i++) {
			ProductContentCommerceMLRecommendation
				expectedProductContentCommerceMLRecommendation =
					expectedProductContentCommerceMLRecommendations.get(i);

			ProductContentCommerceMLRecommendation
				productContentCommerceMLRecommendation =
					productContentCommerceMLRecommendations.get(i);

			Assert.assertEquals(
				expectedProductContentCommerceMLRecommendation.
					getEntryClassPK(),
				productContentCommerceMLRecommendation.getEntryClassPK());

			Assert.assertEquals(
				expectedProductContentCommerceMLRecommendation.getRank(),
				productContentCommerceMLRecommendation.getRank());
		}
	}

	private ProductContentCommerceMLRecommendation
			_createProductContentCommerceMLRecommendation(
				long entryClassPK, int rank, float score)
		throws Exception {

		ProductContentCommerceMLRecommendation
			productContentCommerceMLRecommendation =
				_productContentCommerceMLRecommendationManager.create();

		productContentCommerceMLRecommendation.setEntryClassPK(entryClassPK);
		productContentCommerceMLRecommendation.setRank(rank);
		productContentCommerceMLRecommendation.setCompanyId(
			TestPropsValues.getCompanyId());
		productContentCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.randomLong());
		productContentCommerceMLRecommendation.setScore(score);

		return productContentCommerceMLRecommendation;
	}

	private static final int _PRODUCT_COUNT = 4;

	private static final int _RECOMMENDATION_COUNT = 11;

	@Inject
	private ProductContentCommerceMLRecommendationManager
		_productContentCommerceMLRecommendationManager;

	private List<ProductContentCommerceMLRecommendation>
		_productContentCommerceMLRecommendations;

}