/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class CommerceInventoryReplenishmentItemLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey() {
		List<CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_commerceInventoryReplenishmentItemLocalService.
					getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
						_group.getCompanyId(), null, null, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, false);

		Assert.assertEquals(
			commerceInventoryReplenishmentItems.toString(), 0,
			commerceInventoryReplenishmentItems.size());
	}

	@Inject
	private CommerceInventoryReplenishmentItemLocalService
		_commerceInventoryReplenishmentItemLocalService;

	private Group _group;

}