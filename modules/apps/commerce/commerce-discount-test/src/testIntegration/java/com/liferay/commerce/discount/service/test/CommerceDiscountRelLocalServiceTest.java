/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalService;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRelFinder;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lianne Louie
 */
@RunWith(Arquillian.class)
public class CommerceDiscountRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_user = UserLocalServiceUtil.getGuestUser(
			_serviceContext.getCompanyId());

		_calendar = CalendarFactoryUtil.getCalendar(_user.getTimeZone());
	}

	@Test
	public void testFindCategoriesByCommerceDiscountId()
		throws PortalException {

		_commerceDiscount = _commerceDiscountLocalService.addCommerceDiscount(
			_user.getUserId(), RandomTestUtil.randomString(),
			CommerceDiscountConstants.TARGET_CATEGORIES, true,
			RandomTestUtil.randomString(), true, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
			_calendar.get(Calendar.MONTH), _calendar.get(Calendar.DAY_OF_MONTH),
			_calendar.get(Calendar.YEAR), _calendar.get(Calendar.HOUR_OF_DAY),
			_calendar.get(Calendar.MINUTE), _calendar.get(Calendar.MONTH),
			_calendar.get(Calendar.DAY_OF_MONTH), _calendar.get(Calendar.YEAR),
			_calendar.get(Calendar.HOUR_OF_DAY), _calendar.get(Calendar.MINUTE),
			true, _serviceContext);

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), _serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			_serviceContext);

		List<CommerceDiscountRel> commerceDiscountRelList =
			_commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 0,
			commerceDiscountRelList.size());

		_commerceDiscountRel1 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName(), assetCategory1.getCategoryId(),
				null, _serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			_serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		_commerceDiscountRel2 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName(), assetCategory2.getCategoryId(),
				null, _serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 2,
			commerceDiscountRelList.size());
	}

	@Test
	public void testFindCPDefinitionsByCommerceDiscountId()
		throws PortalException {

		_commerceDiscount = _commerceDiscountLocalService.addCommerceDiscount(
			_user.getUserId(), RandomTestUtil.randomString(),
			CommerceDiscountConstants.TARGET_PRODUCTS, true,
			RandomTestUtil.randomString(), true, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
			_calendar.get(Calendar.MONTH), _calendar.get(Calendar.DAY_OF_MONTH),
			_calendar.get(Calendar.YEAR), _calendar.get(Calendar.HOUR_OF_DAY),
			_calendar.get(Calendar.MINUTE), _calendar.get(Calendar.MONTH),
			_calendar.get(Calendar.DAY_OF_MONTH), _calendar.get(Calendar.YEAR),
			_calendar.get(Calendar.HOUR_OF_DAY), _calendar.get(Calendar.MINUTE),
			true, _serviceContext);

		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinition(
			_group.getGroupId());

		List<CommerceDiscountRel> commerceDiscountRelList =
			_commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				cpDefinition1.getDefaultLanguageId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 0,
			commerceDiscountRelList.size());

		_commerceDiscountRel1 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				CPDefinition.class.getName(), cpDefinition1.getCPDefinitionId(),
				null, _serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				cpDefinition1.getDefaultLanguageId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		CPDefinition cpDefinition2 = CPTestUtil.addCPDefinition(
			_group.getGroupId());

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				cpDefinition1.getDefaultLanguageId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		_commerceDiscountRel2 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				CPDefinition.class.getName(), cpDefinition2.getCPDefinitionId(),
				null, _serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				cpDefinition1.getDefaultLanguageId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 2,
			commerceDiscountRelList.size());
	}

	@Test
	public void testFindPricingClassesByCommerceDiscountId()
		throws PortalException {

		_commerceDiscount = _commerceDiscountLocalService.addCommerceDiscount(
			_user.getUserId(), RandomTestUtil.randomString(),
			CommerceDiscountConstants.TARGET_PRODUCT_GROUPS, true,
			RandomTestUtil.randomString(), true, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
			_calendar.get(Calendar.MONTH), _calendar.get(Calendar.DAY_OF_MONTH),
			_calendar.get(Calendar.YEAR), _calendar.get(Calendar.HOUR_OF_DAY),
			_calendar.get(Calendar.MINUTE), _calendar.get(Calendar.MONTH),
			_calendar.get(Calendar.DAY_OF_MONTH), _calendar.get(Calendar.YEAR),
			_calendar.get(Calendar.HOUR_OF_DAY), _calendar.get(Calendar.MINUTE),
			true, _serviceContext);

		CommercePricingClass commercePricingClass1 =
			_commercePricingClassLocalService.addCommercePricingClass(
				_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		List<CommerceDiscountRel> commerceDiscountRelList =
			_commerceDiscountRelFinder.findPricingClassesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 0,
			commerceDiscountRelList.size());

		_commerceDiscountRel1 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				CommercePricingClass.class.getName(),
				commercePricingClass1.getCommercePricingClassId(), null,
				_serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findPricingClassesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		CommercePricingClass commercePricingClass2 =
			_commercePricingClassLocalService.addCommercePricingClass(
				_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		commerceDiscountRelList =
			_commerceDiscountRelFinder.findPricingClassesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());

		_commerceDiscountRel2 =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				CommercePricingClass.class.getName(),
				commercePricingClass2.getCommercePricingClassId(), null,
				_serviceContext);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 2,
			commerceDiscountRelList.size());
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private Calendar _calendar;

	@DeleteAfterTestRun
	private CommerceDiscount _commerceDiscount;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@DeleteAfterTestRun
	private CommerceDiscountRel _commerceDiscountRel1;

	@DeleteAfterTestRun
	private CommerceDiscountRel _commerceDiscountRel2;

	@Inject
	private CommerceDiscountRelFinder _commerceDiscountRelFinder;

	@Inject
	private CommerceDiscountRelLocalService _commerceDiscountRelLocalService;

	@Inject
	private CommercePricingClassLocalService _commercePricingClassLocalService;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}