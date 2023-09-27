/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendationManager;
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
public class ProductInteractionCommerceMLRecommendationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_productInteractionCommerceMLRecommendations =
			_addProductInteractionCommerceMLRecommendations();
	}

	@Test
	public void testGetProductInteractionCommerceMLRecommendations()
		throws Exception {

		ProductInteractionCommerceMLRecommendation
			productInteractionCommerceMLRecommendation =
				_productInteractionCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_productInteractionCommerceMLRecommendations.size() -
							1));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					productInteractionCommerceMLRecommendation.
						getEntryClassPK(),
					ListUtil.sort(
						ListUtil.filter(
							_productInteractionCommerceMLRecommendations,
							recommendation ->
								recommendation.getEntryClassPK() ==
									productInteractionCommerceMLRecommendation.
										getEntryClassPK()),
						Comparator.comparingInt(
							ProductInteractionCommerceMLRecommendation::
								getRank)));

				return null;
			});
	}

	private List<ProductInteractionCommerceMLRecommendation>
			_addProductInteractionCommerceMLRecommendations()
		throws Exception {

		List<ProductInteractionCommerceMLRecommendation>
			productInteractionCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < _PRODUCT_COUNT; i++) {
			long entryClassPK = RandomTestUtil.randomLong();

			for (int rank = 0; rank < _RECOMMENDATION_COUNT; rank++) {
				float score = 1.0F - (rank / 10.0F);

				productInteractionCommerceMLRecommendations.add(
					_createProductInteractionCommerceMLRecommendation(
						entryClassPK, rank, score));
			}
		}

		Collections.shuffle(productInteractionCommerceMLRecommendations);

		for (ProductInteractionCommerceMLRecommendation
				productInteractionCommerceMLRecommendation :
					productInteractionCommerceMLRecommendations) {

			_productInteractionCommerceMLRecommendationManager.
				addProductInteractionCommerceMLRecommendation(
					productInteractionCommerceMLRecommendation);
		}

		return productInteractionCommerceMLRecommendations;
	}

	private void _assetResultEquals(
			long entryClassPK,
			List<ProductInteractionCommerceMLRecommendation>
				expectedProductInteractionCommerceMLRecommendations)
		throws Exception {

		List<ProductInteractionCommerceMLRecommendation>
			productInteractionCommerceMLRecommendations =
				_productInteractionCommerceMLRecommendationManager.
					getProductInteractionCommerceMLRecommendations(
						TestPropsValues.getCompanyId(), entryClassPK);

		int expectedRecommendationsSize = Math.min(
			10, expectedProductInteractionCommerceMLRecommendations.size());

		Assert.assertEquals(
			"Recommendation list size", expectedRecommendationsSize,
			productInteractionCommerceMLRecommendations.size());

		for (int i = 0; i < expectedRecommendationsSize; i++) {
			ProductInteractionCommerceMLRecommendation
				expectedProductInteractionCommerceMLRecommendation =
					expectedProductInteractionCommerceMLRecommendations.get(i);

			ProductInteractionCommerceMLRecommendation
				productInteractionCommerceMLRecommendation =
					productInteractionCommerceMLRecommendations.get(i);

			Assert.assertEquals(
				expectedProductInteractionCommerceMLRecommendation.
					getEntryClassPK(),
				productInteractionCommerceMLRecommendation.getEntryClassPK());

			Assert.assertEquals(
				expectedProductInteractionCommerceMLRecommendation.getRank(),
				productInteractionCommerceMLRecommendation.getRank());
		}
	}

	private ProductInteractionCommerceMLRecommendation
			_createProductInteractionCommerceMLRecommendation(
				long entryClassPK, int rank, float score)
		throws Exception {

		ProductInteractionCommerceMLRecommendation
			productInteractionCommerceMLRecommendation =
				_productInteractionCommerceMLRecommendationManager.create();

		productInteractionCommerceMLRecommendation.setEntryClassPK(
			entryClassPK);
		productInteractionCommerceMLRecommendation.setRank(rank);
		productInteractionCommerceMLRecommendation.setCompanyId(
			TestPropsValues.getCompanyId());
		productInteractionCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.randomLong());
		productInteractionCommerceMLRecommendation.setScore(score);

		return productInteractionCommerceMLRecommendation;
	}

	private static final int _PRODUCT_COUNT = 4;

	private static final int _RECOMMENDATION_COUNT = 11;

	@Inject
	private ProductInteractionCommerceMLRecommendationManager
		_productInteractionCommerceMLRecommendationManager;

	private List<ProductInteractionCommerceMLRecommendation>
		_productInteractionCommerceMLRecommendations;

}