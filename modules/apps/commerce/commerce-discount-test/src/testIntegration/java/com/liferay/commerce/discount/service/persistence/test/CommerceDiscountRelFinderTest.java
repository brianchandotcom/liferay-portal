/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalService;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRelFinder;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
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
public class CommerceDiscountRelFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_user = UserLocalServiceUtil.getGuestUser(
			_serviceContext.getCompanyId());

		_calendar = CalendarFactoryUtil.getCalendar(_user.getTimeZone());
	}

	@After
	public void tearDown() throws Exception {
		if (_commerceDiscountRel != null) {
			_commerceDiscountRelLocalService.deleteCommerceDiscountRel(
				_commerceDiscountRel);
		}

		if (_commerceDiscount != null) {
			_commerceDiscountLocalService.deleteCommerceDiscount(
				_commerceDiscount.getCommerceDiscountId());
		}
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
			AssetVocabularyLocalServiceUtil.addVocabulary(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), _serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			_serviceContext);

		_commerceDiscountRel =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName(), assetCategory.getCategoryId(),
				null, _serviceContext);

		List<CommerceDiscountRel> commerceDiscountRelList =
			_commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
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

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId());

		_commerceDiscountRel =
			_commerceDiscountRelLocalService.addCommerceDiscountRel(
				_commerceDiscount.getCommerceDiscountId(),
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
				null, _serviceContext);

		List<CommerceDiscountRel> commerceDiscountRelList =
			_commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
				_commerceDiscount.getCommerceDiscountId(), null,
				cpDefinition.getDefaultLanguageId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceDiscountRelList.toString(), 1,
			commerceDiscountRelList.size());
	}

	private Calendar _calendar;
	private CommerceDiscount _commerceDiscount;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	private CommerceDiscountRel _commerceDiscountRel;

	@Inject
	private CommerceDiscountRelFinder _commerceDiscountRelFinder;

	@Inject
	private CommerceDiscountRelLocalService _commerceDiscountRelLocalService;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}