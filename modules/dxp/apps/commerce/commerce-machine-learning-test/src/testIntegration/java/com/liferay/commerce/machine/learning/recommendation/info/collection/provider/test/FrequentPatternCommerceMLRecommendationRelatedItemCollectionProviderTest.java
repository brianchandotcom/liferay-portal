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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.pagination.InfoPage;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Date;
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
public class
	FrequentPatternCommerceMLRecommendationRelatedItemCollectionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_commerceCatalog = _addCommerceCatalog();

		_frequentPatternCommerceMLRecommendations =
			_addFrequentPatternCommerceMLRecommendations();
	}

	@Test
	public void testGetRelatedItemsInfoPage() throws Exception {
		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				_frequentPatternCommerceMLRecommendations.get(
					RandomTestUtil.randomInt(
						0,
						_frequentPatternCommerceMLRecommendations.size() - 1));

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			frequentPatternCommerceMLRecommendation.getAntecedentIds()[0]);

		RelatedInfoItemCollectionProvider<CPDefinition, CPDefinition>
			relatedInfoItemCollectionProvider =
				_infoItemServiceTracker.getInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					StringBundler.concat(
						"com.liferay.commerce.machine.learning.internal.",
						"recommendation.info.collection.provider.",
						"FrequentPatternCommerceMLRecommendation",
						"RelatedInfoItemCollectionProvider"));

		Assert.assertNotNull(relatedInfoItemCollectionProvider);

		CollectionQuery collectionQuery = new CollectionQuery();

		collectionQuery.setRelatedItemObject(cpDefinition);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS, 5, TimeUnit.SECONDS,
			() -> {
				_testGetRelatedItemsInfoPage(
					relatedInfoItemCollectionProvider, collectionQuery);

				return null;
			});
	}

	private CommerceCatalog _addCommerceCatalog() throws Exception {
		User user = UserTestUtil.addUser();

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				user.getUserId());

		return _commerceCatalogLocalService.addCommerceCatalog(
			null, RandomTestUtil.randomString(), commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(), serviceContext);
	}

	private List<FrequentPatternCommerceMLRecommendation>
			_addFrequentPatternCommerceMLRecommendations()
		throws Exception {

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations = new ArrayList<>();

		for (int i = 0; i < _DEFAULT_PRODUCT_COUNT; i++) {
			CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
				_commerceCatalog.getGroupId());

			for (int j = 0; j < _DEFAULT_RECOMMENDATION_COUNT; j++) {
				FrequentPatternCommerceMLRecommendation
					frequentPatternCommerceMLRecommendation =
						_frequentPatternCommerceMLRecommendationManager.
							addFrequentPatternCommerceMLRecommendation(
								_createFrequentPatternCommerceMLRecommendation(
									new long[] {
										cpDefinition.getCPDefinitionId()
									}));

				frequentPatternCommerceMLRecommendations.add(
					frequentPatternCommerceMLRecommendation);
			}
		}

		return frequentPatternCommerceMLRecommendations;
	}

	private FrequentPatternCommerceMLRecommendation
			_createFrequentPatternCommerceMLRecommendation(long[] antecedentIds)
		throws Exception {

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId());

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
			cpDefinition.getCPDefinitionId());
		frequentPatternCommerceMLRecommendation.setScore(1.0F);

		return frequentPatternCommerceMLRecommendation;
	}

	private void _testGetRelatedItemsInfoPage(
		RelatedInfoItemCollectionProvider<CPDefinition, CPDefinition>
			relatedInfoItemCollectionProvider,
		CollectionQuery collectionQuery) {

		InfoPage<CPDefinition> relatedItemsInfoPage =
			relatedInfoItemCollectionProvider.getCollectionInfoPage(
				collectionQuery);

		Assert.assertNotNull(relatedItemsInfoPage);

		List<? extends CPDefinition> pageItems =
			relatedItemsInfoPage.getPageItems();

		Assert.assertEquals(
			pageItems.toString(), _DEFAULT_RECOMMENDATION_COUNT,
			pageItems.size());
	}

	private static final int _DEFAULT_PRODUCT_COUNT = 2;

	private static final int _DEFAULT_RECOMMENDATION_COUNT = 3;

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

	private List<FrequentPatternCommerceMLRecommendation>
		_frequentPatternCommerceMLRecommendations;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

}