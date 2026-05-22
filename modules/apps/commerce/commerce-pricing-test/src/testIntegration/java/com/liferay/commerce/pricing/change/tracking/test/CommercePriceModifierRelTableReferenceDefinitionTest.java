/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.test.util.price.list.CommercePriceListTestUtil;
import com.liferay.commerce.test.util.pricing.CommercePriceModifierRelTestUtil;
import com.liferay.commerce.test.util.pricing.CommercePriceModifierTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CommercePriceModifierRelTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		User user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getCompanyId(), group.getGroupId(), user.getUserId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				group.getGroupId(), user,
				CommerceCurrencyTestUtil.addCommerceCurrency(
					group.getCompanyId()),
				0.0, _serviceContext);

		_commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				group.getGroupId(), user,
				commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return CommercePriceModifierRelTestUtil.addCommercePriceModifierRel(
			_commercePriceModifier.getCommercePriceModifierId(),
			AssetCategory.class.getName(), _assetCategory.getCategoryId(),
			_serviceContext);
	}

	private AssetCategory _assetCategory;
	private CommercePriceModifier _commercePriceModifier;
	private ServiceContext _serviceContext;

}