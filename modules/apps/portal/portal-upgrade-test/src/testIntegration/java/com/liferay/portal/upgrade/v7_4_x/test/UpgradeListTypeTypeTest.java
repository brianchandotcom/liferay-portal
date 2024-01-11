/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeListTypeType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class UpgradeListTypeTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyId = CompanyThreadLocal.getCompanyId();

		_listTypeLocalService.addListType(
			_companyId, _LIST_TYPE_INTRANET, _LIST_TYPE_ACCOUNT_ENTRY);

		_listTypeLocalService.addListType(
			_companyId, _LIST_TYPE_INTRANET, _LIST_TYPE_COMPANY);
	}

	@Test
	public void testUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeListTypeType();

		upgradeProcess.upgrade();

		FinderCacheUtil.clearCache();

		ListType accountEntryListType = _listTypeLocalService.getListType(
			_companyId, _LIST_TYPE_INTRANET, _LIST_TYPE_ACCOUNT_ENTRY);

		ListType companyListType = _listTypeLocalService.getListType(
			_companyId, _LIST_TYPE_INTRANET, _LIST_TYPE_COMPANY);

		Assert.assertNull(accountEntryListType);

		Assert.assertNotNull(companyListType);
	}

	private static final String _LIST_TYPE_ACCOUNT_ENTRY =
		"com.liferay.account.model.AccountEntry.address";

	private static final String _LIST_TYPE_COMPANY =
		"com.liferay.portal.kernel.model.Company.website";

	private static final String _LIST_TYPE_INTRANET = "intranet";

	private static Long _companyId;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

}