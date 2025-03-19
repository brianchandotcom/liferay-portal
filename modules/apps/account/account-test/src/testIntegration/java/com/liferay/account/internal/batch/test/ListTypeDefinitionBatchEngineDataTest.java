/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.batch.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matyas Wollner
 */
@FeatureFlags("LPD-43000")
@RunWith(Arquillian.class)
public class ListTypeDefinitionBatchEngineDataTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAccountAddressSubtypes() throws Exception {
		Assert.assertNotNull(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_ACCOUNT_BILLING_ADDRESS_SUBTYPES",
					TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_ACCOUNT_BILLING_AND_SHIPPING_ADDRESS_SUBTYPES",
					TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_ACCOUNT_SHIPPING_ADDRESS_SUBTYPES",
					TestPropsValues.getCompanyId()));
	}

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

}