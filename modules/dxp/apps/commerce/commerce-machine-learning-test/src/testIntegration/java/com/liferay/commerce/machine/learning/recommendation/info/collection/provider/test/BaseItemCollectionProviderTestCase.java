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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.pagination.InfoPage;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseItemCollectionProviderTestCase {

	@Test
	public void testGetRelatedItemsInfoPage() throws Exception {
		CPDefinition cpDefinition = getRandomRelatedItemObject();

		RelatedInfoItemCollectionProvider<CPDefinition, CPDefinition>
			relatedInfoItemCollectionProvider =
				infoItemServiceTracker.getInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					getInfoItemCollectionProviderName());

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

	protected CommerceCatalog addCommerceCatalog() throws Exception {
		User user = UserTestUtil.addUser();

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				TestPropsValues.getCompanyId());

		return _commerceCatalogLocalService.addCommerceCatalog(
			null, RandomTestUtil.randomString(), commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				user.getUserId()));
	}

	protected abstract String getInfoItemCollectionProviderName();

	protected abstract CPDefinition getRandomRelatedItemObject()
		throws Exception;

	protected static final int PRODUCT_COUNT = 3;

	protected static final int RECOMMENDATION_COUNT = 5;

	@Inject
	protected CPDefinitionLocalService cpDefinitionLocalService;

	@Inject
	protected InfoItemServiceTracker infoItemServiceTracker;

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
			pageItems.toString(), RECOMMENDATION_COUNT, pageItems.size());
	}

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}