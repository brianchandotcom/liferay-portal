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

package com.liferay.commerce.machine.learning.recommendation.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class
	FrequentPatternCommerceMLRecommendationRelatedInfoItemCollectionProviderTest
		extends BaseItemCollectionProviderTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_commerceCatalog = addCommerceCatalog();

		_frequentPatternCommerceMLRecommendations =
			_addFrequentPatternCommerceMLRecommendations();
	}

	@Override
	protected String getInfoItemCollectionProviderName() {
		return StringBundler.concat(
			"com.liferay.commerce.machine.learning.internal.recommendation.",
			"info.collection.provider.",
			"FrequentPatternCommerceMLRecommendation",
			"RelatedInfoItemCollectionProvider");
	}

	@Override
	protected CPDefinition getRandomRelatedItemObject() throws Exception {
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_frequentPatternCommerceMLRecommendations.size() - 1));

		return cpDefinitionLocalService.fetchCPDefinition(
			frequentPatternCommerceMLRecommendation.getAntecedentIds()[0]);
	}

	private List<FrequentPatternCommerceMLRecommendation>
			_addFrequentPatternCommerceMLRecommendations()
		throws Exception {

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < PRODUCT_COUNT; i++) {
			CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
				_commerceCatalog.getGroupId());

			for (int j = 0; j < RECOMMENDATION_COUNT; j++) {
				CPDefinition recommendedCPDefinition =
					CPTestUtil.addCPDefinition(_commerceCatalog.getGroupId());

				FrequentPatternCommerceMLRecommendation
					frequentPatternCommerceMLRecommendation =
						_frequentPatternCommerceMLRecommendationManager.
							create();

				frequentPatternCommerceMLRecommendation.setAntecedentIds(
					new long[] {cpDefinition.getCPDefinitionId()});
				frequentPatternCommerceMLRecommendation.setAntecedentIdsLength(
					1);
				frequentPatternCommerceMLRecommendation.setCompanyId(
					TestPropsValues.getCompanyId());
				frequentPatternCommerceMLRecommendation.setCreateDate(
					new Date());
				frequentPatternCommerceMLRecommendation.
					setRecommendedEntryClassPK(
						recommendedCPDefinition.getCPDefinitionId());
				frequentPatternCommerceMLRecommendation.setScore(1.0F);

				frequentPatternCommerceMLRecommendations.add(
					_frequentPatternCommerceMLRecommendationManager.
						addFrequentPatternCommerceMLRecommendation(
							frequentPatternCommerceMLRecommendation));
			}
		}

		return frequentPatternCommerceMLRecommendations;
	}

	private CommerceCatalog _commerceCatalog;

	@Inject
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

	private List<FrequentPatternCommerceMLRecommendation>
		_frequentPatternCommerceMLRecommendations;

}