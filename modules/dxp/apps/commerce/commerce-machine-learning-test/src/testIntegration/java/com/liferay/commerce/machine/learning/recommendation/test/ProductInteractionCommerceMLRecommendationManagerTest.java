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
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendationManager;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class ProductInteractionCommerceMLRecommendationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_productInteractionCommerceMLRecommendations = _populateEntries(4, 11);
	}

	@Test
	public void testGetProductInteractionCommerceMLRecommendations()
		throws Exception {

		Stream<ProductInteractionCommerceMLRecommendation>
			productInteractionCommerceMLRecommendationStream =
				_productInteractionCommerceMLRecommendations.stream();

		List<ProductInteractionCommerceMLRecommendation>
			expectedProductInteractionCommerceMLRecommendations =
				productInteractionCommerceMLRecommendationStream.filter(
					recommendation -> recommendation.getEntryClassPK() == 2
				).sorted(
					Comparator.comparingInt(
						ProductInteractionCommerceMLRecommendation::getRank)
				).collect(
					Collectors.toList()
				);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					2, expectedProductInteractionCommerceMLRecommendations);

				return null;
			});
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
						_company.getCompanyId(), entryClassPK);

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
			long entryClassPK, int rank, float score) {

		ProductInteractionCommerceMLRecommendation
			productInteractionCommerceMLRecommendation =
				_productInteractionCommerceMLRecommendationManager.create();

		productInteractionCommerceMLRecommendation.setEntryClassPK(
			entryClassPK);
		productInteractionCommerceMLRecommendation.setRank(rank);
		productInteractionCommerceMLRecommendation.setCompanyId(
			_company.getCompanyId());
		productInteractionCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.nextLong());
		productInteractionCommerceMLRecommendation.setScore(score);

		return productInteractionCommerceMLRecommendation;
	}

	private List<ProductInteractionCommerceMLRecommendation> _populateEntries(
			int productCount, int recommendationCount)
		throws Exception {

		List<ProductInteractionCommerceMLRecommendation>
			productInteractionCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < productCount; i++) {
			for (int rank = 0; rank < recommendationCount; rank++) {
				float score = 1.0F - (rank / 10.0F);

				productInteractionCommerceMLRecommendations.add(
					_createProductInteractionCommerceMLRecommendation(
						i, rank, score));
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

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private ProductInteractionCommerceMLRecommendationManager
		_productInteractionCommerceMLRecommendationManager;

	private List<ProductInteractionCommerceMLRecommendation>
		_productInteractionCommerceMLRecommendations;

}