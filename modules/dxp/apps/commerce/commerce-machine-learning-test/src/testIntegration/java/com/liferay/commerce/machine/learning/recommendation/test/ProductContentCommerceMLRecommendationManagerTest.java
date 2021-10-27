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
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendationManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

		Stream<ProductContentCommerceMLRecommendation>
			productContentCommerceMLRecommendationStream =
				_productContentCommerceMLRecommendations.stream();

		List<ProductContentCommerceMLRecommendation>
			expectedProductContentCommerceMLRecommendations =
				productContentCommerceMLRecommendationStream.filter(
					recommendation ->
						recommendation.getEntryClassPK() ==
							productContentCommerceMLRecommendation.
								getEntryClassPK()
				).sorted(
					Comparator.comparingInt(
						ProductContentCommerceMLRecommendation::getRank)
				).collect(
					Collectors.toList()
				);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					productContentCommerceMLRecommendation.getEntryClassPK(),
					expectedProductContentCommerceMLRecommendations);

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